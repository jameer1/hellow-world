'use strict';
app.factory('MaterialIssueDocketFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, MaterialRegisterService, GenericAlertService) {
	var materialissuedocketpopup;
	var service = {};
	service.materialIssueDocketDetails = function(itemData) {
		var deferred = $q.defer();
		materialissuedocketpopup = ngDialog.open({
			template : 'views/projresources/common/schduleitems.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			controller : [ '$scope', function($scope) {
				var selectedIssueDocket = [];
				$scope.addStoreIssueDocket = itemData.materialProjSchItemTOs;
				$scope.userProjMap = itemData.userProjMap;
				$scope.companyMap = itemData.companyMap;
				$scope.classificationMap = itemData.classificationMap;
				$scope.existSchMap = itemData.existSchMap;
				
				$scope.issueDocketPopUpRowSelect = function(storedocket) {
					if (storedocket.select) {
						selectedIssueDocket.push(storedocket);
					} else {
						selectedIssueDocket.pop(storedocket);
					}
				}
				$scope.saveStoreIssueDocket = function() {
					var returnPopObj = {
						"materialProjSchItemTOs" : angular.copy(selectedIssueDocket)
					};
					$scope.addStoreIssueDocket = [];
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}, service.getMaterialSchItemsByProjectAndLoc = function(req, existSchMap) {
		var deferred = $q.defer();
		MaterialRegisterService.getMaterialSchItemsByProjectAndLoc(req).then(function(data) {
			var itemData = {
				"materialProjSchItemTOs" : data.materialProjSchItemTOs,
				"existSchMap" : existSchMap
			}
			service.materialIssueDocketDetails(itemData).then(function(data) {
				var returnPopObj = {
					"materialProjSchItemTOs" : data.materialProjSchItemTOs,
					"companyMap" : itemData.companyMap,
					"classificationMap" : itemData.classificationMap
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting  Project Schedule Items", 'Error');
			});
		}, function(error) {
			if (error.message != null && error.status != null) {
				GenericAlertService.alertMessage(error.message, error.status);
			} else {
				GenericAlertService.alertMessage("Error occured while gettting  Project Schedule Items", 'Error');
			}
		});
		return deferred.promise;
	}, service.getMaterialSchItemsByProject = function(materialProjSchItemTOs) {
		var deferred = $q.defer();
		MaterialRegisterService.getMaterialSchItemsByProject(materialProjSchItemTOs).then(function(data) {
			var itemData = {
				"materialProjSchItemTOs" : data.materialProjSchItemTOs,
				"userProjMap" : data.userProjMap,
				"companyMap" : data.registerOnLoadTO.companyMap,
				"classificationMap" : data.registerOnLoadTO.classificationMap
			}
			service.materialIssueDocketDetails(dataitemData).then(function(data) {
			}, function(error) {

			});
		}, function(error) {
			if (error.message != null && error.status != null) {
				GenericAlertService.alertMessage(error.message, error.status);
			} else {
				GenericAlertService.alertMessage("Error occured while gettting  project schedule items", 'Error');
			}
		});
		return deferred.promise;
	}
	return service;
}]);
