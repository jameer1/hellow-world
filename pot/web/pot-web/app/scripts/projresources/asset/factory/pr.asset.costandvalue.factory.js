'use strict';
app.factory('AssestCostValueStatusFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,generalservice,assetidservice) {
	var assestCostValueStatusPopUp;
	var service = {};
	service.assestCostValueStatusPopUp = function(actionType,editAssetCostValueStatusData,purchaseRecords,assetLifeStatusRecords) {
		var deferred = $q.defer();
		assestCostValueStatusPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/assetcostandvluestatus/assetcostvaluestatuspopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				
				$scope.assetCostStatusDoc={};			
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.addAssestCostValueStatusData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.assestCostValueStatusValue = [];					
				$scope.assetCostStatusDoc={};
				$scope.assetCostStatusDoc.isValid=true;
				
				var addAssestCostValueStatus = {
						"id" : null,
						"effectiveDate" : assetLifeStatusRecords[0]? assetLifeStatusRecords[0].effectiveDate:null,
						"currency" : assetidservice.currency,
						"landCost" : purchaseRecords[0] ? purchaseRecords[0].landValuation : null,
						"structureCost" : purchaseRecords[0] ? purchaseRecords[0].structureValuation : null,
						"plantEquipmentCost" :purchaseRecords[0] ? purchaseRecords[0].plantAndEquipmentValuation : null,
						"totalCost" : purchaseRecords[0] ? purchaseRecords[0].totalValuation : null,
						"assetMarket" : null,
						"structureScrap" : null,
						"equipmentScrapValue" :null,
						"cummStructure":null,
						"cummPlant":null,
						"cummAsset":null,
						"yearlyStructure":null,
					    "yearlyPlant" :null,
						"yearlyTotalAmount" :null,
						"remainAssetCost":null,
						"valuationDepreciationRecords":null,
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid
					};
				
						
				if (actionType === 'Add') {
					$scope.assestCostValueStatusValue.push(addAssestCostValueStatus);
				} else {
					$scope.assestCostValueStatusValue = angular.copy(editAssetCostValueStatusData)
				}
				
				$scope.calCummStructure = function(assetCostValueStatus){
					var assetLifeStructureTotal = 0;
					var assetLifeAgeStructure = 0;
					if(assetLifeStatusRecords[0]){
						assetLifeStructureTotal = parseInt(assetLifeStatusRecords[0].structureTotal);
						assetLifeAgeStructure = parseInt(assetLifeStatusRecords[0].ageStructure);
					}
					assetCostValueStatus.cummStructure = 0;		
					var structureScrap = parseInt(assetCostValueStatus.structureScrap);
					if(assetLifeStatusRecords[0].structureTotal && assetLifeStatusRecords[0].ageStructure  && assetCostValueStatus.structureCost && assetCostValueStatus.structureScrap){
					assetCostValueStatus.cummStructure = (((assetCostValueStatus.structureCost - structureScrap)/assetLifeStructureTotal)*assetLifeAgeStructure);
					}					
				}
				
				$scope.calCummPlant = function(assetCostValueStatus){
					var assetLifePlantEquipmentTotal = 0;
					var assetLifeAgeEquipment = 0;
					if(assetLifeStatusRecords[0]){
						assetLifePlantEquipmentTotal = parseInt(assetLifeStatusRecords[0].plantEquipmentTotal);
						assetLifeAgeEquipment = parseInt(assetLifeStatusRecords[0].ageEquipment);
					}					
					var equipmentScrapValue = parseInt(assetCostValueStatus.equipmentScrapValue);				
					assetCostValueStatus.cummPlant =0;					
					if(assetLifeStatusRecords[0].plantEquipmentTotal && assetLifeStatusRecords[0].ageEquipment && assetCostValueStatus.plantEquipmentCost && assetCostValueStatus.equipmentScrapValue){
					assetCostValueStatus.cummPlant = (((assetCostValueStatus.plantEquipmentCost - equipmentScrapValue)/assetLifePlantEquipmentTotal)*assetLifeAgeEquipment);
					}					
				}
				
				$scope.calCummAsset = function(assetCostValueStatus){
					assetCostValueStatus.cummAsset = 0;
					if(assetCostValueStatus.cummStructure || assetCostValueStatus.cummPlant) {
						assetCostValueStatus.cummAsset = assetCostValueStatus.cummStructure+assetCostValueStatus.cummPlant;
					}
					
				}
				
				$scope.calYearlyDepStructure = function(assetCostValueStatus){					
					var assetLifeStructureTotal = 0;
					if(assetLifeStatusRecords[0]){
						assetLifeStructureTotal = parseInt(assetLifeStatusRecords[0].structureTotal);
					}
					assetCostValueStatus.yearlyStructure =0;
					var structureScrap = parseInt(assetCostValueStatus.structureScrap);
					
					if(assetLifeStatusRecords[0].structureTotal && assetCostValueStatus.structureCost && assetCostValueStatus.structureScrap ){						
						assetCostValueStatus.yearlyStructure = ((assetCostValueStatus.structureCost - structureScrap)/assetLifeStructureTotal)						
					}	
				}
				
				$scope.calYearlyDepPlant = function(assetCostValueStatus){					
					var assetLifePlantEquipmentTotal = 0;
					if(assetLifeStatusRecords[0]){
						assetLifePlantEquipmentTotal = parseInt(assetLifeStatusRecords[0].plantEquipmentTotal);
					}			
					assetCostValueStatus.yearlyPlant =0;
					var equipmentScrapValue =  parseInt(assetCostValueStatus.equipmentScrapValue);
					if(assetLifeStatusRecords[0].plantEquipmentTotal && assetCostValueStatus.plantEquipmentCost && assetCostValueStatus.equipmentScrapValue){						
						assetCostValueStatus.yearlyPlant = ((assetCostValueStatus.plantEquipmentCost - equipmentScrapValue)/assetLifePlantEquipmentTotal)						
					}	
				}
				$scope.calYearlyDepTotal = function(assetCostValueStatus){
					assetCostValueStatus.yearlyTotalAmount = 0;
					if(assetCostValueStatus.yearlyStructure || assetCostValueStatus.yearlyPlant ){
						assetCostValueStatus.yearlyTotalAmount = assetCostValueStatus.yearlyStructure+assetCostValueStatus.yearlyPlant;
					}
					
				}				
				$scope.calRemaingAssetCost = function(assetCostValueStatus){
					assetCostValueStatus.remainAssetCost = 0;	
					var totalCost = parseInt(assetCostValueStatus.totalCost);
					var cummAsset = parseInt(assetCostValueStatus.cummAsset);
					var structureScrap = parseInt(assetCostValueStatus.structureScrap);
					var equipmentScrapValue = parseInt(assetCostValueStatus.equipmentScrapValue);
					assetCostValueStatus.remainAssetCost = ((totalCost-cummAsset)+(structureScrap+equipmentScrapValue));
				}
				
				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.assetCostStatusDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";	
						$scope.assetCostStatusDoc.isValid=false;
					}else if( (file.size > 5000000)){
						$scope.assetCostStatusDoc.errorMessage="Supported Max. File size 5MB";	
						$scope.assetCostStatusDoc.isValid=false;
					}else{
						$scope.assetCostStatusDoc.isValid=true;
					}
		
				}
				
					$scope.saveAssetsCost = function() {					
						var assetCostValueStatusSaveReq = {
							"assetCostValueStatusDtlTOs" : $scope.assestCostValueStatusValue,
							"folderCategory" : "Asset Cost and Value Status",
							"userId" : $rootScope.account.userId
							};
							console.log("saveAssetsCost function");
							console.log(assetCostValueStatusSaveReq);
							console.log($scope.fileInfo);
						    AssetRegisterService.saveAssetCostValueStatus($scope.fileInfo,assetCostValueStatusSaveReq).then(function(data) {
						    	var succMsg = GenericAlertService.alertMessageModal('Asset Cost Value Detail(s) Saved Successfully','Info');
								succMsg.then(function() {
								var returnPopObj = {
									"assetCostValueStatusDtlTOs" : data.assetCostValueStatusDtlTOs
								};
								deferred.resolve(returnPopObj);
							});
						}, function(error) {
							if (error.status != null && error.status != undefined) {
								GenericAlertService.alertMessageModal('Asset Cost Value Detail(s) is/are Failed to Save/Update ', "Error");
							} 
						});
						    ngDialog.close();
					}
			} ]
		});
		return deferred.promise;
	}
	
	service.assestCostValueStatusPopOnLoad = function(actionType, editAssetCostValueStatusData) {
		var deferred = $q.defer();
		var assestCostValueStatusPopUp = service.assestCostValueStatusPopUp(actionType, editAssetCostValueStatusData);
		assestCostValueStatusPopUp.then(function(data) {

			var returnPopObj = {
				"assetCostValueStatusDtlTOs" : data.assetCostValueStatusDtlTOs,

			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);
		