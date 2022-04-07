'use strict';
app.factory('CompanyListPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "CompanyService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, CompanyService) {
	var companyListPopUp;
	var service = {};
	service.companyDetailListPopUp = function(companyTOs) {
		var deferred = $q.defer();
		companyListPopUp = ngDialog.open({
			template : 'views/centrallib/companylist/companypopuplist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.companyTOs = companyTOs;
				$scope.selectRecord = function(record) {
					var returnRecord = {
						"selectedRecord" : record
					};
					deferred.resolve(returnRecord);
					$scope.closeThisDialog();

				}
			} ]

		});
		return deferred.promise;
	},

	service.getCompanies = function(companyReq) {
		var deferred = $q.defer();
		var companyDetailsPromise =  CompanyService.getCompanies(companyReq)
		companyDetailsPromise.then(function(data) {
			var companyListPopUp = service.companyDetailListPopUp(data.companyTOs);
			companyListPopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Company List Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Company List Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]);