
var app;
(function(app) {
	app.module = angular.module('app', ['ngResource', 'ngRoute', 'ui.bootstrap']);
	app.module
	.config(configureLogging)
	.config(cofigureLocation)
	.config(configureRoutes)
	.directive('userPasswordValidate', createUserPasswordValidate)
	.controller('parent', controlParent)
	.controller('index', controlIndex);

	function configureLogging($logProvider) {
		$logProvider.debugEnabled(true);
  }

	function cofigureLocation($locationProvider) {
		$locationProvider.html5Mode(true);
	}

	function configureRoutes($routeProvider) {
		$routeProvider.when('/', {
			templateUrl: 'templates/index.html'
		}).when('/tasks', {
			templateUrl: 'templates/tasks.html'
		}).otherwise({
			redirectTo: '/'
		});
	}

	function createUserPasswordValidate() {
        var d = {};
	    d.require = 'ngModel';
	    d.link = function (scope, elm, attrs, ctrl) {
	        ctrl.$parsers.push(function(viewValue) {
	            const len = viewValue.length;
	            if (len == 0 || len >= 8) {
	            	ctrl.$setValidity('userPassword', true);
	                return viewValue;
	            } else {
	            	ctrl.$setValidity('userPassword', false);
	                return undefined;
	            }
	        });
	    };
	    return d;
	}

	function controlParent($scope, $log) {
		$scope.meta = app.meta;

		// Paginationディレクティブのためのデフォルト値
		// ＊外部スコープにてページネーションに関わる値─とくにtotalSizeを初期化することで、
		// 画面初期表示時にページ番号が強制的にリセットされてしまう問題への対策としている。
		$scope.size = 25;
		$scope.totalSize = Number.MAX_VALUE;
	}

	function controlIndex($scope, $log) {
		$scope.message = 'hello';
	}

})(app || (app = {}));
