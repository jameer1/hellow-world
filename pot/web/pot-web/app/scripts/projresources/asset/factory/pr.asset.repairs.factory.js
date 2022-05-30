'use strict';
app.factory('RepairsNonScheduleFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "AssetMaintenanceCategorySelectFactory", "GenericAlertService", "AssetRegisterService", "assetidservice","MaterialRegisterService","RegisterPurchaseOrderFactory","EmployeeMasterDetailsFactory", function(ngDialog, $q, $filter, $timeout, $rootScope,AssetMaintenanceCategorySelectFactory, GenericAlertService,AssetRegisterService,assetidservice,MaterialRegisterService,RegisterPurchaseOrderFactory,EmployeeMasterDetailsFactory) {
	var repairsPopUp;
	var service = {};
	service.repairsPopUp = function(actionType,editRepairsRecordData) {
		var deferred = $q.defer();
		repairsPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/repairsandnonschedule/repairsandschedulepopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.projectId = assetidservice.projectId;
				$scope.RepairsDoc={};
				$scope.repairsRecord = [];
				var addRepairsRecord={
					"status" : 1,
					"id" : null,
					"selected" : false,
					"startDate" : null,
					"finishDate" : null,
					"responsibleSupervisor" : null,
					"maintenanceCategory" : null,
					"maintenanceSubCategory" : null,
					"materialsConsumptionRecords" : null,
					"completionRecords" : null,
					"purchaseOrderNumber":null,
					'fixedAssetid' : $scope.fixedAssetid,
					'docKey':null
					};

				if (actionType === 'Add') {
					$scope.repairsRecord.push(addRepairsRecord);
				} else {
					$scope.repairsRecord = angular.copy(editRepairsRecordData)
					//editRepairsRecord = [];
				}

				 //Assets Maintenance  Category Select Popup
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
				
						promotion.responsibleSupervisor = name;

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
					});
				}

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
					//	alert(asset.purchaseOrderNumber);
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
				}

				 //Assets Maintenance  Sub Category Select Popup
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

				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.RepairsDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";
						$scope.RepairsDoc.isValid=false;
					}else if( (file.size > 1000000)){
						$scope.RepairsDoc.errorMessage="Supported Max. File size 1MB";
						$scope.RepairsDoc.isValid=false;
					}else{
						$scope.RepairsDoc.isValid=true;
					}

				},
//------------------------------------------------------------------------------------------------------------------------------
$scope.getMaterialProjDockets = function(asset) {
					var material_req = {
						"projId": $scope.projectId
					}
					
					var plantPurchaseOrderPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/repairsandnonschedule/repairsprojdocketpop.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {

			$scope.materialProjDocketList = [];	
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
//------------------------------------------------------------------------------------------------------------------------------
				$scope.saveRepairsSchedules = function() {
										
					//console.log("$scope.repairsRecord"+ JSON.stringify($scope.repairsRecord));
										
					var saveRepairsReq = {
						"assetsRepaisSchedulesTO" : $scope.repairsRecord,
						"folderCategory" : "Repairs and Non Schedule maintenance Records",
						"userId" : $rootScope.account.userId
					};
					console.log(saveRepairsReq);
					console.log($scope.fileInfo);
					AssetRegisterService.saveRepairsRecord($scope.fileInfo, saveRepairsReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Repairs Record Detail(s) Saved Successfully','Info');
						succMsg.then(function() {
							var returnPopObj = {
								"fixedAssetAqulisitionRecordsDtlTO" : data.fixedAssetAqulisitionRecordsDtlTO
							};

							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						if (error.status != null && error.status != undefined) {
							alertMessageModal.alertMessage('Asset Repairs Record Detail(s) is/are Failed to Save/Update ', "Error");
						}
					});
					 ngDialog.close();

				}
				} ]
		});
		return deferred.promise;
	}

	service.repairRecordPopUpOnLoad = function(actionType, editRepairRecordData) {
		var deferred = $q.defer();
		var repairRecordPopUp = service.repairsPopUp(actionType, editRepairRecordData);
		repairRecordPopUp.then(function(data) {

			var returnPopObj = {
				"fixedAssetRepairsRecordsDtlTO" : data.fixedAssetRepairsRecordsDtlTOs,
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);
