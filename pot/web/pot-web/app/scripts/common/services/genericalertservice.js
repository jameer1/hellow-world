'use strict';
app.factory('GenericAlertService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI) {
	var alertPopup, rPopup, confirmPopup;
	var service = {};
	service.alertMessage = function(message, header) {
		alertPopup = ngDialog.open({
			template : 'views/alerts/alert-popup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope, reqService) {
				$scope.header = header || 'Alert !';
				$scope.message = message;
				$scope.closePopup = function() {
					$scope.closeThisDialog(alertPopup);
					blockUI.stop();
				};
				$scope.confirm = function() {
					$scope.closeThisDialog(alertPopup);
					blockUI.stop();
				}
				// setTimeout(function(){
				// 	alertPopup.close();
				//  }, 1000)
			} ]
		});
	};
	service.alertMessageReturns = function(message, header) {
		var deferred = $q.defer();

		alertPopup = ngDialog.open({
			template : 'views/alerts/alert-popup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			showClose : false,
			closeByDocument : false,
			controller : ['$scope', function($scope, reqService) {
				$scope.header = header || 'Alert !';
				$scope.message = message;
				$scope.closePopup = function() {
					$scope.closeThisDialog(alertPopup);
					deferred.resolve();
					blockUI.stop();
				};
				$scope.confirm = function() {
					$scope.closeThisDialog(alertPopup);
					deferred.resolve();
					blockUI.stop();
				}
			}]
		});

		return deferred.promise;
	};
	service.alertMessageModal = function(message, header) {
		var deferred = $q.defer();
		alertPopup = ngDialog.openConfirm({
			template : 'views/alerts/alert-popup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			showClose : false,
			closeByEscape: false,
			closeByDocument : false,
			preCloseCallback:function(value){
				if(typeof value === 'string')
				  deferred.resolve();
			},
			controller : [ '$scope', function($scope, reqService) {
				$scope.header = header || 'Alert !';
				$scope.message = message;
				$scope.closePopup = function() {
					deferred.reject();
				};
				$scope.confirm = function() {
					$scope.closeThisDialog(alertPopup);
					deferred.resolve();
					blockUI.stop();
				}
			} ]

		});
		return deferred.promise;
	};
	service.confirmMessageModal = function(message, header, button1, button2) {
		var deferred = $q.defer();
		alertPopup = ngDialog.openConfirm({
			template : 'views/alerts/confirm-popup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope, reqService) {
				$scope.header = header || 'Confirm !';
				$scope.message = message;
				$scope.button1 = button1;
				$scope.button2 = button2;

				$scope.closePopup = function() {
					$scope.closeThisDialog();
					deferred.reject();
				};
				$scope.confirm = function() {
					if($scope.button2=='NO'){
						$scope.closeThisDialog();
					deferred.reject();
					}
				}
				$scope.continueAction = function() {
					if($scope.button1=='YES'){
						$scope.closeThisDialog();
						deferred.resolve();
					}
				}

			} ]
		});
		return deferred.promise;
	};
	service.comments = function(remarks) {
		ngDialog.open({
			template : 'views/common/commentspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.comments = remarks;
			} ]
		});
	};

	return service;

}]);
