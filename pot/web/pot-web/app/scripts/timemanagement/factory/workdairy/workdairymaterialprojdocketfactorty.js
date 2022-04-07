'use strict';
app.factory('WorkDairyMaterialProjDocketFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "WorkDairyProjDocketPopup", "WorkDiaryService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, WorkDairyProjDocketPopup, WorkDiaryService,
		GenericAlertService) {
	var service = {};
	service.getMaterialProjDockets = function(workDairySearchReq, workDairyCostCodes, companyMap, classificationMap,toProjLabelkeyTO, userProjectMap,workMaterialProjectDocketMap) {
		var deferred = $q.defer();
		var materialFactoryPopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairyprojectdockets.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : ['$scope', function($scope) {

				$scope.workDairyMaterialDtlTOs = []
				$scope.workDairySearchReq = workDairySearchReq;
				var selectedMaterials = [];
				$scope.epsProjName = workDairySearchReq.projectLabelKeyTO.parentName;
				$scope.projName = workDairySearchReq.projectLabelKeyTO.projName;
				$scope.projId = workDairySearchReq.projectLabelKeyTO.projId;
				$scope.companyMap = companyMap;
				$scope.classificationMap = classificationMap;
				$scope.userProjectMap = userProjectMap;
               $scope.workMaterialProjectDocketMap=workMaterialProjectDocketMap;
				$scope.projectDocket = {
					"id" : null,
					"code" : null,
					"date" : null
				};
				$scope.docketTpye = 'Project Docket';
				$scope.projectDocketId = null;
				$scope.storeLocation = {
					"code" : 'SM',
					"name" : 'Store Yard'
				};
				$scope.storeLocationTypes = [{
					"code" : 'SM',
					"name" : 'Store Yard'
				}, {
					"code" : 'SPM',
					"name" : 'Stock Piled'
				}];
				$scope.materialProjDocketItems = [];

				$scope.addToWorkDairy = function() {
					if (selectedMaterials.length <= 0) {
						GenericAlertService.alertMessage("Please select atleast one Schedule item to add to work diary", "Warning");
						return;
					}

					var workDairyMaterialDtlTOs = [];
					var workDairyMaterialStatusTOs = [];
					var workDairyMaterialCostTOs = [];
					angular.forEach(workDairyCostCodes, function(value, key) {
						workDairyMaterialCostTOs.push(angular.copy({
							"costId" : value.costId,
							"quantity" : null,
							"workDairyId" : workDairySearchReq.workDairyId,
							"status" : 1
						}))
					});

					workDairyMaterialStatusTOs.push({
						"apprStatus" : null,
						"materialDtlId" : null,
						"status" : 1,
						"comments" : null,
						"apprStatus" : null,
						"workDairyMaterialCostTOs" : angular.copy(workDairyMaterialCostTOs)
					});

					angular.forEach(selectedMaterials, function(value, key) {
						
						workDairyMaterialDtlTOs.push(angular.copy({
							"workDairyId" : workDairySearchReq.workDairyId,
							"status" : 1,
							"id" : null,
							"projId" : $scope.workDairySearchReq.projectLabelKeyTO.projId,
							"sourceType" : 'Project Delivery',
							"docketType" : 'Project Docket',
							"projDocketSchId" : value.id,
							"scheduleItemId" : value.schItemId,
							"schItemCmpId" : value.purchaseSchLabelKeyTO.displayNamesMap.schItemCmpId,
							"deliveryDocketId" : null,
							"docketId":$scope.projectDocket.id,
							"docketNum" : $scope.projectDocket.code,
							"docketDate" : $scope.projectDocket.date,
							"deliveryPlace" : $scope.toProjLabelkeyTO.code,
							"purchaseLabelKeyTO" : {
								id : value.purchaseSchLabelKeyTO.displayNamesMap['purId'],
								code : value.purchaseSchLabelKeyTO.displayNamesMap['purCode'],
								name : null
							},
							"purchaseSchLabelKeyTO" : {
								id : value.schItemId,
								code : value.purchaseSchLabelKeyTO.code,
								name : value.purchaseSchLabelKeyTO.name,
								displayNamesMap : value.purchaseSchLabelKeyTO.displayNamesMap
							},
							"stockLabelKeyTO" : {
								id : null,
								code : null,
								name : null,
								type : null
							},
							"receivedQty" : value.issueQty,
							"defectComments" : value.comments,
							"transitQty" : value.transitQty,
							"consumptionQty" : '',
							"unitOfRate" : '',
							"comments" : '',
							"workDairyMaterialStatusTOs" : workDairyMaterialStatusTOs,
							"availableQty" : value.workDairyAvlQty

						}));
					});
					var req = {
						"workDairyMaterialDtlTOs" : workDairyMaterialDtlTOs,
						"companyMap" : $scope.companyMap,
						"classificationMap" : $scope.classificationMap
					};
					GenericAlertService.alertMessageModal("Materials added to Work Diary", "Info");
					$scope.closeThisDialog();
					deferred.resolve(req);
				}
				$scope.deleteMaterials = function() {
					if (selectedMaterials.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedMaterials.length < $scope.workDairyMaterialDtlTOs.length) {
						angular.forEach(selectedMaterials, function(value, key) {
							$scope.workDairyMaterialDtlTOs.splice($scope.workDairyMaterialDtlTOs.indexOf(value), 1);
						});
						selectedMaterials = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.materialStoreRowSelect = function(materialObj) {
					if (materialObj.select) {
						selectedMaterials.push(materialObj);
					} else {
						selectedMaterials.pop(materialObj);
					}
				}, $scope.getStoreLocation = function() {

				}, $scope.getMaterialProjDocketsByDoctype = function() {
					var req = {
						"status" : 1,
						"sourceType" : $scope.storeLocation.code,
						"projId" : $scope.projId,
						"workDairyDate" : $scope.workDairySearchReq.workDairyDate
					};
					var resultData = WorkDairyProjDocketPopup.getMaterialProjDockets(req,$scope.workMaterialProjectDocketMap);
					resultData.then(function(data) {
						$scope.projectDocket.id = data.id;
						$scope.projectDocket.code = data.projdocketNum;
						$scope.projectDocket.date = data.projdocketDate;
						$scope.toProjLabelkeyTO = data.toProjLabelkeyTO.displayNamesMap;
						
						if($scope.toProjLabelkeyTO.SM_ID == 0){
							$scope.toProjLabelkeyTO.id = $scope.toProjLabelkeyTO.SPM_ID;
							$scope.toProjLabelkeyTO.code = $scope.toProjLabelkeyTO.SPM_CODE;
						}
						
						if($scope.toProjLabelkeyTO.SPM_ID == 0){
							$scope.toProjLabelkeyTO.id = $scope.toProjLabelkeyTO.SM_ID;
							$scope.toProjLabelkeyTO.code = $scope.toProjLabelkeyTO.SM_CODE;
						}
						$scope.getMaterialProjDocketSchItems();
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Docket Number", 'Error');
					});
				}, $scope.getMaterialProjDocketSchItems = function() {
					var req = {
						"status" : 1,
						"docketId" : $scope.projectDocket.id
					};
					var resultData = WorkDiaryService.getMaterialProjDocketSchItems(req);

					resultData.then(function(data) {
						$scope.materialProjDocketItems = data.materialProjSchItemTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Schedule Details", 'Error');
					});
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
