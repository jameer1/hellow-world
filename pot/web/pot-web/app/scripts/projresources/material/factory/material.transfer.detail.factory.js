'use strict';
app.factory('MaterialTransferDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "MaterialRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, MaterialRegisterService) {
	var service = {};
	service.getMaterialDetailsForTransfer = function(req, materialTransferExistingMap) {
		var deferred = $q.defer();
		var resultData = MaterialRegisterService.getMaterialDetailsForTransfer(req);
		resultData.then(function(data) {
			var resultPopupData = service.openPopup(data.labelKeyTOs, materialTransferExistingMap);
			resultPopupData.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting project materials", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting project materials", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function(labelKeyTOs, materialTransferExistingMap) {
		var deferred = $q.defer();
		var plantDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projmaterialreg/reqmaterialtransfer/matrialregdetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.labelKeyTOs = labelKeyTOs;
				$scope.materialTransferExistingMap = materialTransferExistingMap;
				var selectedMaterials = [];
				var materialReqTransMap = [];
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedMaterials.push(tab);
						materialReqTransMap[tab.id] = tab;
					} else {
						selectedMaterials.pop(tab);
						materialReqTransMap[tab.id] = null;
					}
				}
				$scope.addToTransferRequest = function() {
					var returnPopObj = {
						"materialTransferReqApprDtlTOs" : angular.copy(selectedMaterials),
						"materialReqTransMap" : materialReqTransMap
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
