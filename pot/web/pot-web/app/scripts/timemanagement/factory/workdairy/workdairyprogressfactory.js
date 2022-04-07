'use strict';

app.factory('WorkDairyProgressFactory', ["ngDialog", "$q", "GenericAlertService", "ProjSOWService", "WorkDiaryService", "TreeService", function (ngDialog, $q, GenericAlertService,
	ProjSOWService, WorkDiaryService, TreeService) {

	var service = {};
	service.getSOWDetails = function (workDairySearchReq, workDairyCostCodeTOs, workProgressMap) {

		var deferred = $q.defer();
		let costCodeIds = [];
		workDairyCostCodeTOs.map((costCode) => {
			costCodeIds.push(costCode.costId);
		});
		var req = {
			"status": 1,
			"projId": workDairySearchReq.projectLabelKeyTO.projId,
			"costCodeIds": costCodeIds,
			"workDairyId" : workDairySearchReq.workDairyId
		};

		var populateData = function (data, level, projects) {
			return TreeService.populateTreeData(data, level, projects, 'id', 'childSOWItemTOs');
		}

		var popup = ProjSOWService.getSowDetailsExceptCostCode(req);

		popup.then(function (data) {
			var resultData = service.getSORwDetails(populateData(data.projSOWItemTOs, 0, []), 
			workDairySearchReq, workDairyCostCodeTOs, workProgressMap);

			resultData.then(function (data) {
				deferred.resolve(data);

			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  Scope Of Work details", 'Error');
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting  Scope Of Work details", 'Error');
		});
		return deferred.promise;

	},

		service.getSORwDetails = function (projSOWItemTOs, workDairySearchReq, 
			workDairyCostCodeTOs, workProgressMap) {

			var deferred = $q.defer();
			var sowDtlsPopup = ngDialog.open({


				template: 'views/timemanagement/workdairy/createworkdairy/workdairyprogresssow.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				controller: ['$scope', function ($scope) {

					$scope.workDairyCostCodeMap = []
					angular.forEach(workDairyCostCodeTOs, function (value, key) {
						$scope.workDairyCostCodeMap[value.costId] = true;
					});
					var selectedSowTOs = [];
					$scope.workProgressMap = workProgressMap;
					$scope.sowData = projSOWItemTOs;

					$scope.itemId = 1;
					$scope.isExpand = true;
					
					$scope.itemClick = function (itemId, collapse) {
						TreeService.treeItemClick(itemId, collapse, 'childSOWItemTOs');
					}

					$scope.sowData.map(sow => {
						$scope.itemClick(sow, false);
					});

					$scope.sowpopup = function (tab) {
						if (tab.select) {
							selectedSowTOs.push(tab);
						} else {
							selectedSowTOs.pop(tab);
						}
					},

						$scope.addSowItemsToWorkdairy = function () {
							var selectedSowitems = [];
							var selectedcostCodes = [];
							angular.forEach(selectedSowTOs, function (value, key) {
								selectedSowitems.push({
									"sowId": value.id,
									"costId": value.projCostId,
									"quantity": value.quantity,
									"workDairyId": workDairySearchReq.workDairyId,
									"status": 1
								});
								selectedcostCodes.push({
									"status": 1,
									"costId": value.projCostId,
									"crewId": workDairySearchReq.crewLabelKeyTO.id,
									"projId": workDairySearchReq.projectLabelKeyTO.projId,
									"workDairyId": workDairySearchReq.workDairyId
								});
							});

							var saveReq = {
								"workDairyProgressDtlTOs": selectedSowitems,
								"status": 1,
								"projId": workDairySearchReq.projectLabelKeyTO.projId,
								"workDairyTO": {
									"status": 1,
									"id": workDairySearchReq.workDairyId,
									"projId": workDairySearchReq.projectLabelKeyTO.projId,
									"crewId": workDairySearchReq.crewLabelKeyTO.id,
									"clientApproval": workDairySearchReq.clientApproval,
									"apprStatus": workDairySearchReq.apprStatus,
									"newRequired": false
								},
								"workDairyCostCodeSaveReq": {
									"workDairyCostCodeTOs": selectedcostCodes,
									"crewId": workDairySearchReq.crewLabelKeyTO.id,
									"projId": workDairySearchReq.projectLabelKeyTO.projId,
									"workDairyId": workDairySearchReq.workDairyId,
									"status": 1
								}
							};
							if (workDairySearchReq.workDairyId != undefined && workDairySearchReq.workDairyId != null) {
								WorkDiaryService.saveMoreSowCostCodes(saveReq).then(function (data) {
									var resultData = GenericAlertService.alertMessageModal("Scope of Work Details added to Work Diary", "Info");
									resultData.then(function () {
										$scope.closeThisDialog();
										deferred.resolve(data);

									}, function (error) {
										GenericAlertService.alertMessage("Scope Of Work Details are Failed to Save ", "Warning");
									});
								});
							}

						}

				}]
			});
			return deferred.promise;
		};
	return service;
}]);