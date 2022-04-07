'use strict';

app.factory('PrecontractMaterialFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "MaterialClassService", "ProjectBudgetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, MaterialClassService, ProjectBudgetService, GenericAlertService) {
	var service = {};
	service.getMaterialClasses = function(projId, exitingSchMaterilMap, preContractObj) {
		var deferred = $q.defer();
		var req = {
			"status" : 1
		};
		
		MaterialClassService.getMaterialGroups(req).then(function(data) {
			var popupData = service.openPopup(projId, data.materialClassTOs, exitingSchMaterilMap, preContractObj);
			popupData.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Material Groups", 'Error');
		});
		return deferred.promise;

	},

	service.openPopup = function(projId, materialClassTOs, exitingSchMaterilMap, preContractObj) {
		var deferred = $q.defer();
		var projMaterialPopup = ngDialog.open({
			template : '/views/procurement/pre-contracts/internalApproval/precontractmaterialspopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedMeterials = [];
				$scope.exitingSchMaterilMap = exitingSchMaterilMap;
				$scope.materialClassTOs = materialClassTOs;
				$scope.itemId = 1;
				$scope.isExpand = true;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};

				$scope.addMaterialsToPreContact = function() {
					angular.forEach(selectedMeterials, function(value, key) {
						preContractObj.preContractMaterialDtlTOs.push({
							"select" : false,
							"itemCode" : '',
							"itemDesc" : '',
							"preContractId" : preContractObj.id,
							"projMaterialLabelKey" : {
								"id" : value.id,
								"code" : value.code,
								"name" : value.name,
								"unitOfMeasure" : null
							},
							"storeLabelKey" : {
								"id" : null,
								"code" : null,
								"name" : null
							},
							"projStoreLabelKey" : {
								"id" : null,
								"code" : null,
								"name" : null
							},
							"projCostLabelKey" : {
								"id" : null,
								"code" : null,
								"name" : null
							},
							"quantity" : null,
							"startDate" : '',
							"finishDate" : '',
							"apprStatus" : null,
							"projId" : projId,
							"status" : 1
						});
					});
					var returnPopObj = {
						"preContractMaterialDtlTOs" : preContractObj.preContractMaterialDtlTOs
					}
					if(returnPopObj.preContractMaterialDtlTOs.length > 0){
						GenericAlertService.alertMessage("Materials added to precontract successfully", 'Info');
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();
					}else{
						GenericAlertService.alertMessage("Please select a material!", 'Info');
					}
					
				}, $scope.materialRowSelect = function(meterialTO) {
					if (meterialTO.select) {
						selectedMeterials.push(meterialTO);
					} else {
						selectedMeterials.pop(meterialTO);
					}
				}
			} ]

		});
		return deferred.promise;
	}
	return service;

}]).filter('procurementMaterialClassFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand, exitingSchMaterilMap) {
		angular.forEach(obj, function(o, key) {
			if (o.childMaterialItemTOs && o.childMaterialItemTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				if (o.id == itemId) {
					o.expand = isExpand;
				}
				if (o.expand = isExpand) {
					recursive(o.childMaterialItemTOs, newObj, o.level + 1, itemId, isExpand, exitingSchMaterilMap);
				}
			} else {
				o.level = level;
				if (o.item && exitingSchMaterilMap[o.id] == null) {
					o.leaf = true;
					newObj.push(o);
				} else {
					obj.splice(obj.indexOf(o), 1);
				}
				return false;
			}
		});
	}

	return function(obj, itemId, isExpand, exitingSchMaterilMap) {
		var newObj = [];
		recursive(obj, newObj, 0, itemId, isExpand, exitingSchMaterilMap);
		return newObj;
	}
});