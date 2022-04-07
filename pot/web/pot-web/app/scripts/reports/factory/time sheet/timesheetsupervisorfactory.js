'use strict';

app.factory('TimeSheetSupervisorFactory', ["ngDialog", "$q", "blockUI", "GenericAlertService", "TimeSheetReportService", function (ngDialog, $q, blockUI, GenericAlertService, TimeSheetReportService) {
	var service = {};
	service.getTimeSheetSupervisorData = function (selectedProjIds) {
		var deferred = $q.defer();
		var getReq = {
			"projIds": selectedProjIds
		};
		blockUI.start();
		TimeSheetReportService.getTimeSheetReqUser(getReq).then(function (data) {
			blockUI.stop();
			var popupdata = service.openPopup(data);
			popupdata.then(function (resultData) {
				deferred.resolve(resultData);
			});
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occured while getting Supervisor Details", 'Error');
		});

		return deferred.promise;
	}, service.openPopup = function (labelKeyTOs) {
		var deferred = $q.defer();
		var epsProjTreePopup = ngDialog.open({
			template: 'views/reports/time sheet/timesheetsupervisorlist.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.labelKeyTOs = labelKeyTOs;
				$scope.selectedItems = function (timeSheet) {
					selectedTimeSheets.push(timeSheet);
				};

				$scope.selectAllUsers = function () {
					$scope.labelKeyTOs.map(o => o.select = $scope.selectAll);
				};

				$scope.addSupervisorsToTimeSheet = function () {
					deferred.resolve($scope.labelKeyTOs.filter(o => o.select));
					$scope.closeThisDialog();
				}

			}]
		});
		return deferred.promise;
	};

	return service;

}]);