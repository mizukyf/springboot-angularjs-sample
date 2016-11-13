
var gulp = require('gulp');
var concat = require('gulp-concat');
var del = require('del');
var fs = require('fs');
var merge = require('merge-stream');
var rename = require('gulp-rename');
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');
var uglifycss = require('gulp-uglifycss');

var paths = (function() {
	function npmConcat(modName, namePattern) {
		if (fs.existsSync('node_modules/' + modName + '/dist')) {
			return 'node_modules/' + modName + '/dist/' + namePattern;
		} else {
			return 'node_modules/' + modName + '/' + namePattern;
		}
	}

	function createPaths(mainOrTest) {
		var srcDir = 'src/' + mainOrTest + '/resources/static';
		var jsSrcDir = srcDir + '/js';
		var cssSrcDir = srcDir + '/css';
		var fontsSrcDir = srcDir + '/fonts';

		var targetDir = 'target/' + (mainOrTest === 'test' ? 'test-' : '') + 'classes';
		var jsTargetDir = targetDir + '/js';
		var cssTargetDir = targetDir + '/css';
		var fontsTargetDir = targetDir + '/fonts';

		var jsEntryPoint = jsSrcDir + '/app.js';
		var cssEntryPoint = cssSrcDir + '/app.css';

		var jsFiles = jsSrcDir + '/*.js';
		var cssFiles = cssSrcDir + '/*.css';
		var jsArtifacts = jsSrcDir + '/*.*.js';
		var cssArtifacts = cssSrcDir + '/*.*.css';

		var jsUnifiedName = 'app.pack.js';
		var cssUnifiedName = 'app.pack.css';
		var minifiedFileSuffix = '.min';

		var jsUnifyTargets = [
			npmConcat('angular', 'angular.js'),
			npmConcat('angular-route', 'angular-route.js'),
			npmConcat('angular-resource', 'angular-resource.js'),
			npmConcat('angular-ui-bootstrap', 'ui-bootstrap-tpls.js'),
			jsEntryPoint
		];
		var cssUnifyTargets = [
			npmConcat('bootstrap', 'css/bootstrap.css'),
			npmConcat('bootstrap', 'css/bootstrap-theme.css'),
			cssEntryPoint
		];
		var bootstrapFonts = [
			npmConcat('bootstrap', 'fonts/*.*')
		];

		return {
			srcDir: srcDir,
			jsSrcDir: jsSrcDir,
			cssSrcDir: cssSrcDir,
			fontsSrcDir: fontsSrcDir,
			targetDir: targetDir,
			jsTargetDir: jsTargetDir,
			cssTargetDir: cssTargetDir,
			fontsTargetDir: fontsTargetDir,
			jsEntryPoint: jsEntryPoint,
			jsFiles: jsFiles,
			cssFiles: cssFiles,
			jsArtifacts: jsArtifacts,
			cssArtifacts: cssArtifacts,
			jsUnifiedName: jsUnifiedName,
			cssUnifiedName: cssUnifiedName,
			minifiedFileSuffix: minifiedFileSuffix,
			jsUnifyTargets: jsUnifyTargets,
			cssUnifyTargets: cssUnifyTargets,
			bootstrapFonts: bootstrapFonts
		};
	}

	return {
		main: createPaths('main'),
		test: createPaths('test')
	};
})();

var uglifyConfig = (function() {
	return {
		mangle: false,
		preserveComments: 'license'
	};
})();

gulp.task('clean', function() {
	return del([paths.main.jsArtifacts, paths.main.cssArtifacts,
		paths.test.jsArtifacts, paths.test.cssArtifacts]);
});

gulp.task('concatScripts', ['clean'], function() {
	return gulp
		.src(paths.main.jsUnifyTargets)
		.pipe(gulp.dest(paths.main.jsSrcDir))
		.pipe(sourcemaps.init())
		.pipe(concat(paths.main.jsUnifiedName))
		.pipe(sourcemaps.write('.'))
		.pipe(gulp.dest(paths.main.jsSrcDir))
		.pipe(gulp.dest(paths.main.jsTargetDir));
});

gulp.task('uglifyScripts', ['concatScripts'], function() {
	return gulp
		.src([paths.main.jsUnifiedName])
		.pipe(sourcemaps.init())
		.pipe(uglify(uglifyConfig))
		.pipe(rename({suffix: paths.main.minifiedFileSuffix}))
		.pipe(sourcemaps.write('.'))
		.pipe(gulp.dest(paths.main.jsSrcDir))
		.pipe(gulp.dest(paths.main.jsTargetDir));
});

gulp.task('styles', ['clean'], function() {
	return merge(
		gulp
			.src(paths.main.cssUnifyTargets)
			.pipe(gulp.dest(paths.main.cssSrcDir))
			.pipe(sourcemaps.init())
			.pipe(concat(paths.main.cssUnifiedName))
			.pipe(sourcemaps.write('.'))
			.pipe(gulp.dest(paths.main.cssSrcDir))
			.pipe(gulp.dest(paths.main.cssTargetDir))
			.pipe(sourcemaps.init())
			.pipe(uglifycss())
			.pipe(rename({suffix: paths.main.minifiedFileSuffix}))
			.pipe(sourcemaps.write('.'))
			.pipe(gulp.dest(paths.main.cssSrcDir))
			.pipe(gulp.dest(paths.main.cssTargetDir)),
		gulp
			.src(paths.main.bootstrapFonts)
			.pipe(gulp.dest(paths.main.fontsSrcDir))
			.pipe(gulp.dest(paths.main.fontsTargetDir)));
});

gulp.task('watch', ['clean'], function functionName() {
	gulp.task('scripts');
	gulp.task('styles');
});

gulp.task('default', ['uglifyScripts', 'styles']);
