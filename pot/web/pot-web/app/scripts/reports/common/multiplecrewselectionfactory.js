'use strict';

app.factory('MultipleCrewSelectionFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjCrewListService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, ProjCrewListService, GenericAlertService) {
	var crewPopup;
	var service = {};
	service.crewPopup = function (crewReq) {
		var deferred = $q.defer();
		ProjCrewListService.getMultipleProjsCrewList(crewReq).then(function (data) {
			var popupdata = service.openPopup(data.projCrewTOs, crewReq);
			popupdata.then(function (resultData) {
				deferred.resolve(resultData);
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  Crew Details", "Error");
		});
		return deferred.promise;

	}, service.openPopup = function (projCrewTOs, crewReq) {
		var deferred = $q.defer();
		var crewPopup = ngDialog.open({
			template: 'views/reports/common/multiplecrewselection.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				var selectedCrews = [];
				var selectedCrewIds = [];
				$scope.crewReq = crewReq.projIds;
				$scope.crews = projCrewTOs;
				var crewName = '';
				var workShiftName = '';
				$scope.selectedCrews = function (crew) {
					if (crew.select) {
						selectedCrews.push(crew);
					} else {
						selectedCrews.pop(crew);
					}
				};
				$scope.selectAllCrews = function () {
					if ($scope.selectAll) {

						angular.forEach($scope.crews, function (value, key) {
							value.select = true;
							selectedCrews.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.crews, function (value, key) {
							value.select = false;
						});
						selectedCrews = [];
					}
				};
				$scope.addCrews = function () {
					if(selectedCrews.length <=0){
						GenericAlertService.alertMessage("Atleast one Crew should be selected", 'Warning');
						return;
					}
					angular.forEach(selectedCrews, function (value, key) {
						selectedCrewIds.push(value.id);
						crewName = crewName + value.code + ",";
						if (value.projWorkShiftTO)
							workShiftName = workShiftName + value.projWorkShiftTO.code + ",";
					});

					var returnPopObj = {
						"selectedCrews": {
							"crewIds": selectedCrewIds,
							"crewName": crewName.slice(0, -1),
							"workShiftName": workShiftName.slice(0, -1)
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
