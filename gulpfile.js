// 開発時依存性のインポート
var gulp = require('gulp');
var concat = require('gulp-concat');
var del = require('del');
var fs = require('fs');
var merge = require('merge-stream');
var rename = require('gulp-rename');
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');
var uglifycss = require('gulp-uglifycss');

// ビルドに使用するパス情報を定義
var paths = (function() {
	
	/**
	 * モジュール名とファイル名パターンを連結して実行時依存性のjs,css,fontsのパスのパターンをつくる.
	 * @param modName モジュール名（例："angular"）
	 * @param namePattern ファイル名パターン（例："*.js"）
	 * @returns パス・パターン
	 */
	function npmConcat(modName, namePattern) {
		if (fs.existsSync('node_modules/' + modName + '/dist')) {
			return 'node_modules/' + modName + '/dist/' + namePattern;
		} else {
			return 'node_modules/' + modName + '/' + namePattern;
		}
	}
	
	/**
	 * 一連の配列もしくは文字列を受け取って結合し単一の新しい配列をつくる.
	 * @param args 可変長引数（要素は文字列もしくは配列）
	 * @returns
	 */
	function arrConcat(args) {
		var r = [], i, j;
		for (i in arguments) {
			var e = arguments[i];
			if (e instanceof Array) {
				for (j in e) {
					r.push(e[j]);
				}
			} else {
				r.push(e);
			}
		}
		return r;
	}
	
	/**
	 * ビルド時に使用するパスの定義一式をプロパティにもつオブジェクトを構築する.
	 * @param mainOrTest "main"もしくは"test"
	 * @returns パス情報を持つオブジェクト
	 */
	function createPaths(mainOrTest) {
		// src系のディレクトリの定義
		var srcDir = 'src/' + mainOrTest + '/resources/static';
		var jsSrcDir = srcDir + '/js';
		var cssSrcDir = srcDir + '/css';
		var fontsSrcDir = srcDir + '/fonts';
		// target系のディレクトリの定義
		var targetDir = 'target/' + (mainOrTest === 'test' ? 'test-' : '') + 'classes';
		var jsTargetDir = targetDir + '/js';
		var cssTargetDir = targetDir + '/css';
		var fontsTargetDir = targetDir + '/fonts';
		// 連結後のファイル名
		var jsConcatName = 'app.pack.js';
		var cssConcatName = 'app.pack.css';
		// オブファスケート後の接尾辞
		var uglifySuffix = '.min';

		// ビルド対象とするJSファイルのパスの配列
		var jsFiles = arrConcat(
				npmConcat('angular', 'angular.js'),
				npmConcat('angular-route', 'angular-route.js'),
				npmConcat('angular-resource', 'angular-resource.js'),
				npmConcat('angular-ui-bootstrap', 'ui-bootstrap-tpls.js'),
				jsSrcDir + '/app.js');
		// ビルド対象とするCSSファイルのパスの配列
		var cssFiles = arrConcat(
				npmConcat('bootstrap', 'css/bootstrap.css'),
				npmConcat('bootstrap', 'css/bootstrap-theme.css'),
				cssSrcDir + '/app.css');
		// Bootstrapのfonts/配下のファイルのパス
		var bootstrapFonts = [
			npmConcat('bootstrap', 'fonts/*.*')
		];

		// 以上で定義したパス情報をオブジェクトにまとめて返す
		return {
			srcDir: srcDir,
			jsSrcDir: jsSrcDir,
			cssSrcDir: cssSrcDir,
			fontsSrcDir: fontsSrcDir,
			targetDir: targetDir,
			jsTargetDir: jsTargetDir,
			cssTargetDir: cssTargetDir,
			fontsTargetDir: fontsTargetDir,
			jsFiles: jsFiles,
			cssFiles: cssFiles,
			jsConcatName: jsConcatName,
			cssConcatName: cssConcatName,
			uglifySuffix: uglifySuffix,
			bootstrapFonts: bootstrapFonts
		};
	}

	return {
		main: createPaths('main'),
		test: createPaths('test')
	};
})();

gulp.task('clean', function() {
	return del([
		paths.main.jsTargetDir, paths.main.cssTargetDir,
		paths.test.jsTargetDir, paths.test.cssTargetDir,
		paths.main.fontsSrcDir, paths.test.fontsSrcDir]);
});

gulp.task('scripts', ['clean'], function() {
	return gulp
		// 依存性を考慮して順序付けされたパス配列をソースとして指定
		.src(paths.main.jsFiles)
		// 加工前のコードをsourcemap経由で参照できるようすべてtarget/配下にコピー
		.pipe(gulp.dest(paths.main.jsTargetDir))
		// sourcemapの初期化を行う
		.pipe(sourcemaps.init())
		// 一連のファイルを1つのファイルに連結
		.pipe(concat(paths.main.jsConcatName))
		// 加工前のコードをsourcemap経由で参照できるようすべてtarget/配下にコピー
		.pipe(gulp.dest(paths.main.jsTargetDir))
		// オブファスケートを行う
		.pipe(uglify({/*mangle: false,*/ preserveComments: 'license'}))
		// オブファスケート済みファイルのリネームを行う
		.pipe(rename({suffix: paths.main.uglifySuffix}))
		// sourcemapの出力を行う
		.pipe(sourcemaps.write('.'))
		// 加工後のファイルの出力を行う
		.pipe(gulp.dest(paths.main.jsTargetDir));
});

gulp.task('styles', ['clean'], function() {
	return merge(
		// 1つめのストリーム
		gulp
			// 依存性を考慮して順序付けされたパス配列をソースとして指定
			.src(paths.main.cssFiles)
			// 加工前のコードをsourcemap経由で参照できるようすべてtarget/配下にコピー
			.pipe(gulp.dest(paths.main.cssTargetDir))
			// sourcemapの初期化を行う
			.pipe(sourcemaps.init())
			// 一連のファイルを1つのファイルに連結
			.pipe(concat(paths.main.cssConcatName))
			// 加工前のコードをsourcemap経由で参照できるようすべてtarget/配下にコピー
			.pipe(gulp.dest(paths.main.cssTargetDir))
			// オブファスケートを行う
			.pipe(uglifycss())
			// オブファスケート済みファイルのリネームを行う
			.pipe(rename({suffix: paths.main.uglifySuffix}))
			// sourcemapの出力を行う
			.pipe(sourcemaps.write('.'))
			// 加工後のファイルの出力を行う
			.pipe(gulp.dest(paths.main.cssTargetDir)),
		// 2つめのストリーム
		gulp
			// Bootstrapのfonts/配下のファイルをソースとして指定
			.src(paths.main.bootstrapFonts)
			// すべてtarget/配下にコピー
			.pipe(gulp.dest(paths.main.fontsTargetDir)));
});

gulp.task('watch', ['clean'], function functionName() {
	gulp.watch('scripts');
	gulp.watch('styles');
});

gulp.task('default', ['scripts', 'styles']);
