'use strict';
app.factory('AssestLifeStatusFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,generalservice,assetidservice) {
	var assetStatusPopUp;
	var service = {};
	service.assetStatusPopUp = function(actionType, editAssetLifeStatusData,assetLifeStatusRecords) {
		var deferred = $q.defer();
		assetStatusPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/assetlifestatus/assetlifestatuspopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.buildStructure = assetidservice.fixedAssetYearBuild;				
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.currentDate = new Date();
				var structureTotal =0;
				var plantEquipmentTotal =0;
				
				if(assetLifeStatusRecords[0]) {
					structureTotal = (assetLifeStatusRecords[0].structureTotal);
					plantEquipmentTotal = (assetLifeStatusRecords[0].plantEquipmentTotal);
				}							
				
				$scope.addAssetLifeStatusData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.assetLifeStatus = [];				
				var selectedAssetLifeStatus = [];
				$scope.assetLifeStatusDoc={};
				$scope.assetLifeStatusDoc.isValid=true;
				
				var addAssetLifeStatus = {
						"id" : null,
						"effectiveDate":null,
						"buildStructure":$scope.buildStructure,	
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"structureTotal":structureTotal,	
						"plantEquipmentTotal":plantEquipmentTotal,
						"ageStructure":null,
						"ageEquipment":null,
						"remainingStruture":null,
						"remainingEquipment":null,						
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"docKey":null
					};
				
				if (actionType === 'Add') {
					$scope.assetLifeStatus.push(addAssetLifeStatus);
				} else {
					$scope.assetLifeStatus = angular.copy(editAssetLifeStatusData)
				}
				
				$scope.addRows = function() {					
					$scope.assetLifeStatus.push({
						"id" : null,
						"effectiveDate":null,
						"buildStructure":$scope.buildStructure,	
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"structureTotal":null,	
						"plantEquipmentTotal":null,
						"ageStructure":null,
						"ageEquipment":null,
						"remainingStruture":null,
						"remainingEquipment":null,						
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"docKey":null
						
					});
				},
				
				$scope.ageStructureplantEquipmentTotal = function(lifeStatus) {
					if(lifeStatus.effectiveDate && lifeStatus.buildStructure){
						var effectiveDateYear = new Date(lifeStatus.effectiveDate).getFullYear();						
						lifeStatus.ageStructure = effectiveDateYear -(lifeStatus.buildStructure); 						
					}else{
						lifeStatus.ageStructure =0;
					}
					if(lifeStatus.effectiveDate && lifeStatus.plantCommissioningDate){
						var effectiveDateYear = new Date(lifeStatus.effectiveDate).getFullYear();
						var plantCommissioningDateYear = new Date(lifeStatus.plantCommissioningDate).getFullYear();						
						lifeStatus.ageEquipment = effectiveDateYear -plantCommissioningDateYear;
					}else{						
						lifeStatus.ageEquipment =0;
					}					
					
				}
				
				$scope.remainingStructureEquipment = function(lifeStatus) {
					if(lifeStatus.structureTotal || lifeStatus.ageStructure) {
						lifeStatus.remainingStruture = lifeStatus.structureTotal -lifeStatus.ageStructure;
					}else{
						lifeStatus.remainingStruture =0;						
					}
					
					if(lifeStatus.plantEquipmentTotal || lifeStatus.ageEquipment) {
						lifeStatus.remainingEquipment = lifeStatus.plantEquipmentTotal -lifeStatus.ageEquipment;
					}else{
						lifeStatus.remainingEquipment =0;						
					}
				}
				
				
				$scope.assetLifeStatusPopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedAssetLifeStatus.push(asset);
					} else {
						selectedAssetLifeStatus.pop(asset);
					}
				},
				
				$scope.deleteRows = function() {
					if (selectedAssetLifeStatus.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedAssetLifeStatus.length < $scope.assetLifeStatus.length) {
						angular.forEach(selectedAssetLifeStatus, function(value, key) {
							$scope.assetLifeStatus.splice($scope.assetLifeStatus.indexOf(value), 1);
						});
						selectedAssetLifeStatus = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedAssetLifeStatus.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedAssetLifeStatus.length == 1) {
						$scope.assetLifeStatus[0] = {
								"effectiveDate":null,
								"buildStructure":null,	
								"plantCommissioningDate":null,
								"structureTotal":null,	
								"plantEquipmentTotal":null,
								"ageStructure":null,
								"ageEquipment":null,
								"remainingStruture":null,
								"remainingEquipment":null,						
								"status" : "1",
								"fixedAssetid" : $scope.fixedAssetid,
								"docKey":null

						};
						selectedAssetLifeStatus = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},
				
				
				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.assetLifeStatusDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";	
						$scope.assetLifeStatusDoc.isValid=false;
					}else if( (file.size > 5000000)){
						$scope.assetLifeStatusDoc.errorMessage="Supported Max. File size 1MB";	
						$scope.assetLifeStatusDoc.isValid=false;
					}else{
						$scope.assetLifeStatusDoc.isValid=true;
					}
		
				}
				
				$scope.saveAssetsLifeStatus = function() {
									
						/*if (!$scope.assetLifeStatusDoc.isValid) {
							GenericAlertService.alertMessage("Please upload valid file !!", 'Warning');
							return ;
						}*/
									
						var assetsLifeStatusSaveReq = {					
							"assetLifeStatusDtlTOs" : $scope.assetLifeStatus,
							"folderCategory" : "Asset Life Status",
							"userId" : $rootScope.account.userId				
						};	
						console.log(assetsLifeStatusSaveReq);
						console.log($scope.fileInfo);
									AssetRegisterService.saveAssetLifeStatus($scope.fileInfo,assetsLifeStatusSaveReq).then(function(data) {									
										var succMsg = GenericAlertService.alertMessageModal('Asset Life Status Detail(s) Saved successfully','Info');
										succMsg.then(function() {
										var returnPopObj = {
											"assetLifeStatusDtlTOs" : data.assetLifeStatusDtlTOs
										};
										deferred.resolve(returnPopObj);
									});
								}, function(error) {
									if (error.status != null && error.status != undefined) {
										GenericAlertService.alertMessageModal('Asset Life Status Detail(s) is/are failed to Save/Update ', "Error");
									} 
								});
								    ngDialog.close();
							}
						} ]
					});
					return deferred.promise;
				}
				
					
	service.assetLifeStatusPopUpOnLoad = function(actionType, editAssetLifeStatusData) {
		var deferred = $q.defer();
		var assetStatusPopUp = service.assetStatusPopUp(actionType, editAssetLifeStatusData);
		assetStatusPopUp.then(function(data) {

			var returnPopObj = {
				"assetLifeStatusDtlTOs" : data.assetLifeStatusDtlTOs,

			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);

		