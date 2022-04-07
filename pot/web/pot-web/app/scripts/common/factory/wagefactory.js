'use strict';

app.factory('WageFactory', ["ngDialog", "$q", "blockUI", "WageService", "GenericAlertService", function (ngDialog, $q, blockUI, WageService, GenericAlertService) {
	var service = {};
	service.wageFactorDetailsList = function (getReq, existingWageFactorMap) {
		var deferred1 = $q.defer();
		blockUI.start();
		var result = WageService.getEmpWages().then(function (data) {
			blockUI.stop();
			var popup = service.openUserPopup(data.employeeWageRateTOs, existingWageFactorMap);
			popup.then(function (data) {

				var returnPopObj = {
					"employeeWageRateTO": data
				};
				deferred1.resolve(returnPopObj);
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while gettting EmployeeWages", 'Error');
			});

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting EmployeeWages", 'Error');
		});
		return deferred1.promise;

	}, service.openUserPopup = function (wages, existingWageFactorMap) {
		var deferred = $q.defer();
		var approverUserPopup = ngDialog.open({
			template: 'views/timemanagement/workdairy/createworkdairy/wageFactor.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.wages = wages;
				$scope.existingWageFactorMap = existingWageFactorMap;
				$scope.wageFactorpopup = function (wage) {
					deferred.resolve(wage);
					$scope.closeThisDialog();
				}

			}]
		});
		return deferred.promise;
	};
	return service;

}]);