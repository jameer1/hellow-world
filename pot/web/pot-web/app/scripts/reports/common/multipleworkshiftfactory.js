'use strict';

app.factory('MultipleWorkShiftFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjCrewListService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, ProjCrewListService, GenericAlertService) {
	var shiftPopup;
	var service = {};
	service.shiftPopup = function (req) {
		var deferred = $q.defer();
		ProjCrewListService.getMultipleProjWorkShifts(req).then(function (data) {
			var popupdata = service.openPopup(data.projWorkShiftTOs, req);
			popupdata.then(function (resultData) {
				deferred.resolve(resultData);
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  Work Shift Details", "Error");
		});
		return deferred.promise;
	}, service.openPopup = function (projWorkShiftTOs, req) {
		var deferred = $q.defer();
		var shiftPopup = ngDialog.open({
			template: 'views/reports/common/multipleshiftselection.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				var selectedShifts = [];
				var selectedShiftIds = [];
				$scope.req = req.projIds;
				$scope.workShifts = projWorkShiftTOs;
				var workShiftName = '';
				$scope.selectedShifts = function (workShift) {
					if (workShift.select) {
						selectedShifts.push(workShift);
					} else {
						selectedShifts.pop(workShift);
					}
				};

				$scope.selectAllShifts = function () {
					if ($scope.selectAll) {
						angular.forEach($scope.workShifts, function (value, key) {
							value.select = true;
							selectedShifts.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.workShifts, function (value, key) {
							value.select = false;
						});
						selectedShifts = [];
					}
				};

				$scope.addWorkShifts = function () {
					angular.forEach(selectedShifts, function (value, key) {
						selectedShiftIds.push(value.id);
						workShiftName = workShiftName + value.code + ",";
					});
					var returnPopObj = {
						"selectedShifts": {
							"shiftIds": selectedShiftIds,
							"workShiftName": workShiftName.slice(0, -1),
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				};
			}]
		});
		return deferred.promise;
	};
	return service;
}]);
