'use strict';

app
	.config(["$stateProvider", function ($stateProvider) {
		$stateProvider
			.state('main', {
				parent: 'site',
				url: '/',
				data: {
					roles: []
				},
				views: {
					'content@': {
						templateUrl: 'login.html',
						controller: 'LoginController'
					}
				}
			});
	}])
	.controller('MainController', ["$rootScope", "$scope", "$state", "Auth", "Principal", "localStorageService", "stylesService", "slideBar", "NotificationService", "potSearchService", "$q", "$timeout", function ($rootScope, $scope, $state, Auth, Principal, localStorageService,
		stylesService, slideBar, NotificationService,potSearchService,$q,$timeout) {


		$scope.account = '';
		$rootScope.moduleParentId = null;
		$rootScope.moduleId = null;
		$rootScope.moduleState = null;
		$scope.stylesSvc = stylesService;

		$scope.showSearchBar = false;

		let searchPages = potSearchService.searchPages;

		function getNotificationCount() {
			NotificationService.getCountNotification().then(function (data) {
				$scope.notificationcount = data.count;
			});
		}

		$scope.pageSearch = function (query) {
			let filteredPages = [];
			angular.forEach(searchPages, function(value,key){
					if(value.searchkeyWord.toLowerCase().includes(query.toLowerCase())){
						filteredPages.push(value);
					}
				});
			return filteredPages;
		}

		$scope.loadPage = function(item) {
			if (item != undefined)
				$state.go(item.url, item.params);
		}

		$scope.authenticated = { 'usrAuthSuccess': 'N' };
		$scope.homeButton = function () {
			$rootScope.moduleId = null;
			$state.go("home");
		}

		$scope.$on("loginsuccess", function () {
			loadPrincipal();
		});

		if (localStorageService.get('loginSuccess')) {
			loadPrincipal();
		}
		$scope.hoverme = true;

		$scope.logout = function () {
			Auth.logout().then(function(){
				setLocalStorageLoginSuccess(false);
				$scope.authenticated.usrAuthSuccess = 'N';
				$state.go('main');
				// lets just live with it for now!! this line makes the footer line always stays at bottom for login page.
				$(".content-wrapper").height(stylesService.contentHeight);
				slideBar.resetSlideBar();
			});
		};

		function loadPrincipal() {
			Principal.identity().then(function (account) {
				slideBar.initializeSlidebar();
				$scope.account = account;
				$rootScope.account = account;
				$scope.authenticated.usrAuthSuccess = 'Y';
				setLocalStorageLoginSuccess(true);
				getNotificationCount();
			}, function(err){
				$scope.account = null;
				$rootScope.account = null;
				$scope.authenticated.usrAuthSuccess = 'N';
			});
		}

		function setLocalStorageLoginSuccess(value){
			localStorageService.set('loginSuccess', value);
		}

		$rootScope.$on("tokenExpired", function () {
			setLocalStorageLoginSuccess(false);
			localStorageService.set('pottoken', null);
			$scope.account = null;
			$rootScope.account = null;
			$scope.authenticated.usrAuthSuccess = 'N';
		});

	}]);
