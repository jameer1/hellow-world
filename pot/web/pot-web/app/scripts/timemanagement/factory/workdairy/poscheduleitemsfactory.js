'use strict';

app.factory('POScheduleItemsFactory', ["ngDialog", "$q", "GenericAlertService", "ProjSOWService", "TreeService", function(ngDialog, $q, GenericAlertService, ProjSOWService, TreeService) {

	var sowDtlsPopup;
	var service = {};

	service.getSOWDetails = function(workDairySearchReq, workDairyCostCodeTOs, workProgressMap, procureSowMap) {
		var deferred = $q.defer();

		let costCodeIds = [];
		workDairyCostCodeTOs.map((costCode) => {
			costCodeIds.push(costCode.costId);
		});
		var req = {
			"status" : 1,
			"projId" : workDairySearchReq.projectLabelKeyTO.projId,
			"costCodeIds" : costCodeIds
		};

		let populateData = function (data, level, projects) {
			return TreeService.populateTreeData(data, level, projects, 'id', 'childSOWItemTOs');
		}

		var popup = ProjSOWService.getSowDetailsByCostCode(req)

		popup.then(function(data) {
			var resultData = service.getSORwDetails(populateData(data.projSOWItemTOs, 0, []), workDairySearchReq, workDairyCostCodeTOs, workProgressMap, procureSowMap);
			resultData.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting  Scope Of Work details", 'Error');
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting  Scope Of Work details", 'Error');
		});
		return deferred.promise;

	},

	service.getSORwDetails = function(projSOWItemTOs, workDairySearchReq, workDairyCostCodeTOs, workProgressMap, procureSowMap) {
		var deferred = $q.defer();
		var sowDtlsPopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairyprocuresow.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.workDairyCostCodeMap = []
				angular.forEach(workDairyCostCodeTOs, function(value, key) {
					$scope.workDairyCostCodeMap[value.costId] = true;
				});

				var selectedSowTOs = [];
				$scope.workProgressMap = workProgressMap;
				$scope.procureSowMap = procureSowMap;
				$scope.SOWData = projSOWItemTOs;

				$scope.itemId = 1;
				$scope.isExpand = true;
				$scope.itemClick = function(itemId, expand) {
					$scope.itemId = itemId;
					$scope.isExpand = !expand;
				};

				$scope.itemClick = function (itemId, collapse) {
					TreeService.treeItemClick(itemId, collapse, 'childSOWItemTOs');
				}

				$scope.SOWData.map(sow => {
					$scope.itemClick(sow, false);
				});

				$scope.sowpopup = function(tab) {
					if (tab.select) {
						selectedSowTOs.push(tab);
					} else {
						selectedSowTOs.pop(tab);
					}
				},

				$scope.addSowItemsToWorkdairy = function() {
					var saveSowReqDetails = [];
					angular.forEach(selectedSowTOs, function(value, key) {
						saveSowReqDetails.push({
							"sowId" : value.id,
							"sowCode" : value.code,
							"sowDesc" : value.name,
							"soeId" : value.projSOEItemTO.id,
							"soeCode" : value.projSOEItemTO.code,
							"sorId" : value.projSORItemTO.id,
							"sorCode" : value.projSORItemTO.code,
							"costId" : value.projCostItemTO.id,
							"costCode" : value.projCostItemTO.code,
							"measureId" : value.measureUnitTO.id,
							"measureCode" : value.measureUnitTO.code,
							"quantity": value.quantity,
							"status" : 1
						});
						if ($scope.workDairyCostCodeMap[value.projCostId] == null) {
							$scope.workDairyCostCodeMap[value.projCostId] = true;
							workDairyCostCodeTOs.push({
								"status" : 1,
								"costId" : value.projCostId
							});
						}
					});
					var returnPopObj = {
						"projSOWItemTOs" : saveSowReqDetails,
						"workDairyCostCodeTOs" : workDairyCostCodeTOs
					};

					GenericAlertService.alertMessageModal("Scope Of Work Details are added to Workdairy", "Info");
					$scope.closeThisDialog();
					deferred.resolve(returnPopObj);
				}

			} ]
		});
		return deferred.promise;
	};
	return service;
}]);