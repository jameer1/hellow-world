'use strict';

app.factory('PlantRepairMaterialProjDocketFactory', ["ngDialog", "$q", "GenericAlertService", "MaterialRegisterService", function (ngDialog, $q, GenericAlertService, MaterialRegisterService) {
	var service = {};
	service.getPlantMaterialProjDocketDetails = function (projId, repairTO) {
		var deferred = $q.defer();
		var req = {
			"status": 1,
			"projId": projId,
			"fromDate": null,
			"toDate": null
		};
		MaterialRegisterService.getProjectIssueDockets(req).then(function (data) {
			var popupData = service.openPopup(data.materialProjDocketTOs, repairTO);
			popupData.then(function (data) {
				deferred.resolve(data);
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting plant material project docket details", "Error");
		});
		return deferred.promise;
	}, service.openPopup = function (materialProjDocketTOs, repairTO) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template: 'views/projresources/projplantreg/plantservicehistoryrepairs/plantregrepairmaterialdoccketpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.materialProjDocketTOs = materialProjDocketTOs;
				$scope.projDockets = [];

				angular.forEach($scope.materialProjDocketTOs, function (value, key) {
					$scope.data = value.materialProjSchItemTOs;
					angular.forEach($scope.data, function (value1, key1) {
						if (repairTO.materialId == value1.purchaseSchLabelKeyTO.displayNamesMap.purClassId) {
							value1.date = value.projdocketDate;
							value1.email = value.projdocketNum;
							$scope.projDockets.push(value1);
						}
					});
				});
				$scope.selectMaterialProjDocket = function (docket) {
					var returnObj = {
						"docNum": docket.email
					}
					deferred.resolve(returnObj);
					$scope.closeThisDialog();
				};
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
