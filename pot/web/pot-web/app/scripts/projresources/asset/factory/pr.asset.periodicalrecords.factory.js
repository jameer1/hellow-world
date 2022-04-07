'use strict';
app.factory('PeriodicalScheduleFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "AssetMaintenanceCategorySelectFactory", "GenericAlertService", "AssetRegisterService", "assetidservice","RegisterPurchaseOrderFactory","EmployeeMasterDetailsFactory", function(ngDialog, $q, $filter, $timeout, $rootScope,AssetMaintenanceCategorySelectFactory, GenericAlertService,AssetRegisterService,assetidservice,RegisterPurchaseOrderFactory,EmployeeMasterDetailsFactory) {
	var periodicalPopUp;
	var service = {};
	service.periodicalPopUp = function(actionType,editPeriodicalRecordData) {
		var deferred = $q.defer();
		periodicalPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/periodicalandschedulerecords/periodicalpopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.projectId = assetidservice.projectId;
				$scope.periodicalDoc={};
				$scope.periodicalsRecord = [];
				$scope.materialProjDocketList = [];
				var addperiodicalsRecord={
					"status" : 1,
					"id" : null,
					"selected" : false,
					"dueDate":null,
					"maintenanceCategory" : null,
					"maintenanceSubCategory" : null,
					"planResponsibleSupervisor" : null,
					"currentStatus":null,
					"startDate" : null,
					"finishDate" : null,
					"actualResponsibleSupervisor" : null,
					"materialsConsumptionRecords" : null,
					"purchaseOrderNumber":null,
					'fixedAssetid' : $scope.fixedAssetid,
					'actualDocKey':null,
					'planDocKey':null
					};
				
				if (actionType === 'Add') {					
					$scope.periodicalsRecord.push(addperiodicalsRecord);
				} else {
					$scope.periodicalsRecord = angular.copy(editPeriodicalRecordData)
				}
				
				$scope.checkPlanFile = function (file) {
					$scope.filePlanInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.periodicalsPlanDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";	
						$scope.periodicalsPlanDoc.isValid=false;
					}else if( (file.size > 1000000)){
						$scope.periodicalsPlanDoc.errorMessage="Supported Max. File size 1MB";	
						$scope.periodicalsPlanDoc.isValid=false;
					}else{
						$scope.periodicalsPlanDoc.isValid=true;
					}
		
				},
				$scope.employeeDetails = function(promotion) {

					console.log(' promotion '+ JSON.stringify(promotion))
					
					var getEmployeeRegisterReq = {
						"projId" : $scope.projectId
					};
					console.log(' getEmployeeRegisterReq '+ getEmployeeRegisterReq)
					EmployeeMasterDetailsFactory.getEmployeeMasterDetails(getEmployeeRegisterReq).then(function(data) {

						var name = data.employeeMasterTO.firstName+","+data.employeeMasterTO.lastName;

						var req = {
							"id" : data.employeeMasterTO.id,
							"code" : data.employeeMasterTO.code,
							"name" : name
						};
						
					
						promotion.actualResponsibleSupervisor = name;
					//	promotion.PlanResponsibleSupervisor = name;
					
						

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
					});
				}
			//	-------------------------------------------------------
			$scope.planemployeeDetails = function(promotion) {
				console.log(' promotion '+ JSON.stringify(promotion))
				
					var getEmployeeRegisterReq = {
						"projId" : $scope.projectId
						};
			console.log(' getEmployeeRegisterReq '+ getEmployeeRegisterReq)
					EmployeeMasterDetailsFactory.getEmployeeMasterDetails(getEmployeeRegisterReq).then(function(data) {
						var name = data.employeeMasterTO.firstName+","+data.employeeMasterTO.lastName;
							var req = {
							"id" : data.employeeMasterTO.id,
							"code" : data.employeeMasterTO.code,
							"name" : name
						};
				//	promotion.actualResponsibleSupervisor = name;
					promotion.planResponsibleSupervisor = name;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
					});
				}
			//-------------------------------------------------------------------	
				// Purchase Order
				$scope.getProjectPoDetails = function (asset) {
					var req = {
						"status": 1,
						"projId": $scope.projectId,
						"procureType": '#M,#M#S,#S#M,#S'
					};
					console.log(' req '+ JSON.stringify(req));
					$scope.purchaseOrderTO = null;
					RegisterPurchaseOrderFactory.getProjectPODetails(req).then(function (data) {
				//	$scope.repairsRecord.purId = data.purchaseOrderTO.id;
						asset.purchaseOrderNumber = data.purchaseOrderTO.code;
									//		$scope.addMaterialData.cmpName = data.purchaseOrderTO.displayNamesMap.cmpName;
					//	$scope.materialProjSchItemTOs = [];
				//		$scope.selectedMaterialProjSchItemTO.materialPODeliveryDocketTOs = [];
					//	var schItemReq = {
					///		"status": 1,
						//	"purId": $scope.addMaterialData.purId
					//	};
					//	var exitsingSchItems = MaterialRegisterService.getMaterialSchItemsByPurchaseOrder(schItemReq);
					//	exitsingSchItems.then(function (data) {
						//	angular.forEach(data.materialProjDtlTOs, function (value, key) {
						//		$scope.selectedMaterialProjSchItemTO.stockId = value.stockId;
							//	$scope.selectedMaterialProjSchItemTO.projStockId = value.projStockId;
						//		$scope.materialProjSchItemTOs.push(value);
						//	});
					//	}, function (error) {
				//			GenericAlertService.alertMessage("Error occured while getting purchase existing schedule items", "Error");
					//	});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting purchase orders", "Error");
					});
				},
				$scope.checkActualFile = function (file) {
					$scope.fileActualInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.periodicalsActualDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";	
						$scope.periodicalsActualDoc.isValid=false;
					}else if( (file.size > 1000000)){
						$scope.periodicalsActualDoc.errorMessage="Supported Max. File size 1MB";	
						$scope.periodicalsActualDoc.isValid=false;
					}else{
						$scope.periodicalsActualDoc.isValid=true;
					}
		
				},
				$scope.periodicalStatus= [ {
					id : 1,
					name : "Open"
				}, {
					id : 2,
					name : "Closed"
				} ];
				
				$scope.getAssetMaintenanceCategory = function(assetMaintenanceCategoryTO,item) {
					var req = {
						"status" : 1
						
					};
					var categoryPopup = AssetMaintenanceCategorySelectFactory.getAssetMaintenanceCategory(req);
					categoryPopup.then(function(data) {
						item.maintenanceCategory = data.selectedRecord;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
				/*$scope.getPeriodicalRecords=function() {
					if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
						GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
						return;
					}
					var periodicalRecordsGetReq = {
						"status" : 1,
						"fixedAssetid" : $scope.fixedAssetid,
					};
			
					AssetRegisterService.getPeriodicalRecordsOnLoad(periodicalRecordsGetReq).then(function(data) {
						$scope.PeriodicalScheduleMaintenanceDtlTO = data.periodicalScheduleMaintenanceDtlTOs;
						
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Time Sheet Details", "Error");
					});
				},*/
				$scope.getAssetMaintenanceSubCategory = function(assetMaintenanceCategoryTO,item) {
					
					var req = {
						"status" : 1,
					};
					var categoryPopup = AssetMaintenanceCategorySelectFactory.getAssetMaintenanceCategory(req);
					categoryPopup.then(function(data) {
						item.maintenanceSubCategory = data.selectedRecord;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
				
				$scope.savePeriodicalsRecords = function() {
					var savePeriodicalsReq = {
						"periodicalScheduleMaintenanceDtlTO" : $scope.periodicalsRecord,
						"fixedAssetId" : $scope.fixedAssetid,
						"userId" : $rootScope.account.userId,
						"folderCategory" : "Periodical and Schedule maintenance Records"
					};
					console.log("savePeriodicalsReq ",savePeriodicalsReq);
					console.log($scope.filePlanInfo);
					console.log($scope.fileActualInfo);
					AssetRegisterService.savePeriodicalsRecord($scope.filePlanInfo,$scope.fileActualInfo,savePeriodicalsReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Periodicals Record Detail(s) Saved Successfully ','Info');
						succMsg.then(function() {
							var returnPopObj = {
				//				"fixedAssetAqulisitionRecordsDtlTO" : data.fixedAssetAqulisitionRecordsDtlTO
								"fixedAssetPeriodicalsRecordsDtlTO" : data.fixedAssetPeriodicalsRecordsDtlTOs
			
							};
							
							deferred.resolve(returnPopObj);
							$scope.getPeriodicalRecords();
						});
					}, function(error) {
						if (error.status != null && error.status != undefined) {
							GenericAlertService.alertMessageModal('Periodicals Record Detail(s) is/are Failed to Save/Update ', "Error");
						} 
					});
					 ngDialog.close();

				},
				$scope.getMaterialProjDockets = function(asset) {
					var material_req = {
						"projId": $scope.projectId
					}
					
					var plantPurchaseOrderPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/periodicalandschedulerecords/materialprojdocketpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {

			$scope.materialProjDocketList = [];
			/*	$scope.plantPurchaseOrderDetails = projectPurchaseOrderDetails;
				$scope.plantPurchaseOrderPopUp = function(purchaseOrderTO) {
					var returnPurchaseOrderTO = {
						"purchaseOrderTO" : purchaseOrderTO
					};
					deferred.resolve(returnPurchaseOrderTO);
					$scope.closeThisDialog();

				}
*/				
					
					console.log(material_req)
					AssetRegisterService.getMaterialProjDocketsList(material_req).then(function(data) {
						for(var j=0;j<data.materialProjDocketTOs.length;j++)
						{
							if( data.materialProjDocketTOs[j].apprStatus == 'Generated' )
							{
								$scope.materialProjDocketList.push(data.materialProjDocketTOs[j]);
							}
							$scope.materialProjDocketsDate = data.materialProjDocketTOs[j].projdocketDate;
							$scope.materialProjDocketNum = data.materialProjDocketTOs[j].projdocketNum; 
						}				
					});
					$scope.selectMaterialProjDocket = function(projDocket){
						console.log(projDocket)
						asset.materialsConsumptionRecords = projDocket.projdocketNum
						plantPurchaseOrderPopUp.close()
					}
					} ]

		});	
					
				}
				} ]
		});
		return deferred.promise;
	}	
	service.periodicalsRecordPopUpOnLoad = function(actionType, editPeriodicalRecordData) {
		var deferred = $q.defer();
		var periodicalsRecordPopUp = service.periodicalPopUp(actionType, editPeriodicalRecordData);
		periodicalsRecordPopUp.then(function(data) {

			var returnPopObj = {
				"fixedAssetPeriodicalsRecordsDtlTO" : data.fixedAssetPeriodicalsRecordsDtlTOs,
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);	

