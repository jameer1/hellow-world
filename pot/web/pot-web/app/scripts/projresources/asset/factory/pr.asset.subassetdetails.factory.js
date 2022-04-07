'use strict';
app.factory('SubAssetDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "ProjectSettingsService", "TaxCountryFactory", "AssetRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService,ProfitCentrePopUpFactory,AssetCategorySelectFactory, ProjectSettingsService,TaxCountryFactory, AssetRegisterService) {
	var generatePopUpDetails;
	var service = {};
	service.generatePopUpDetails = function(actionType, editAssetData) {
		var deferred = $q.defer();
		generatePopUpDetails = ngDialog.open({
			template : 'views/projresources/projassetreg/assetdetails/subassetdetailspopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.editTreeData = [];
				$scope.fixedAssetid=null;
				$scope.assetId=null;
				$scope.assetDesc=null;
				$scope.assetCategoryTOName =null;
				$scope.isoAlpha3=null;				
				$scope.provisionName=null;
				$scope.address=null;
				$scope.profitCentreTOName=null;
				$scope.yearBuild=null;				
				$scope.dateCommissioned=null;				
				$scope.currency=null;
				$scope.assetData = [];
				$scope.assetData=[{
					
					'assetId':null,					
					'assetDesc' : null,
					'assetCategoryTOName' : null,
					'address' : null,
					'profitCentreTOName' : null,
					'yearBuild' : null,						
					'dateCommissioned' : null,
					'currency' : null,
					'status' : 1
				}];
				$scope.itemId1 = 1;
				$scope.isExpand1 = true;
				$scope.itemClick1 = function(itemId, expand) {
					$scope.itemId1 = itemId;
					$scope.isExpand1 = !expand;
				};

				if (actionType === 'Add') {
					$scope.editTreeData.push({
						"selected" : false,
						"status" : 1,
						"deleteFlag" : false,
						"projCode" : '',
						"projName" : '',
						"assetCategoryName" : '',
						"assetDescription" : '',
						"address" : '',
						"currency" : '',
						"profitCentre" : '',
						"yearBuild" :'',
						"dateCommissioned" :'',
						"geonameId" :'',
						"isoAlpha3":'',
						"countryName":'',
						"provisionName":'',
						"subAssetId":'',
						"subAssetDescription":'',
						"subAssetCategory":'',
						"childProjs" : []
					});
					$scope.itemClick1(null, false);
				} else {
					$scope.getAssetSubById = function() {
						var epsEditReq = {
							"status" : 1,
							"fixedAssetid" : itemData.fixedAssetid
						};
						
						AssetRegisterService.getAssetSubById(epsEditReq).then(function(data) {
							$scope.editTreeData = data.fixedAssetDtlTOs;
						});
					}
				}
				$scope.addTreeGroup = function() {
					$scope.editTreeData.push({
						"selected" : false,
						"status" : 1,
						"assignedStatus" : '',
						"deleteFlag" : false,
						"projCode" : '',
						"projName" : '',
						"assetCategoryName" : '',
						"assetDescription" : '',
						"address" : '',
						"currency" : '',
						"profitCentre" : '',
						"yearBuild" :'',
						"dateCommissioned" :'',
						"geonameId" :'',
						"isoAlpha3":'',
						"countryName":'',
						"provisionName":'',
						"subAssetId":'',
						"subAssetDescription":'',
						"subAssetCategory":'',

						"childProjs" : []
					});
					$scope.itemClick1(null, false);
				}
				console.log("======="+$scope.editTreeData)

				$scope.addTreeSubGroup = function(tabData) {
					tabData.childProjs.push(angular.copy({
						"selected" : false,
						"parentId" : tabData.fixedAssetid,
						"status" : 1,
						"assignedStatus" : '',
						"deleteFlag" : false,
						"projCode" : '',
						"projName" : '',
						"assetCategoryName" : '',
						"assetDescription" : '',
						"address" : '',
						"currency" : '',
						"profitCentre" : '',
						"yearBuild" :'',
						"dateCommissioned" :'',
						"geonameId" :'',
						"isoAlpha3":'',
						"countryName":'',
						"provisionName":'',
						"subAssetId":'',
						"subAssetDescription":'',
						"subAssetCategory":'',

						"childProjs" : []
					}));

					$scope.itemClick1(tabData.fixedAssetid, false);

				}
				
                    if (actionType === 'Edit') {
					
					$scope.fixedAssetid= editAssetData[0].fixedAssetid;
					$scope.assetId=editAssetData[0].assetId;
					$scope.assetDesc=editAssetData[0].assetDescription;
					$scope.assetCategoryTOName=editAssetData[0].assetCategoryName;
					$scope.isoAlpha3=editAssetData[0].countryName;
					$scope.provisionName=editAssetData[0].provisionName;
					$scope.address=editAssetData[0].address;
					$scope.profitCentreTOName=editAssetData[0].profitCentre;
					$scope.yearBuild=editAssetData[0].yearBuild;
					$scope.dateCommissioned=editAssetData[0].dateCommissioned;
					$scope.currency=editAssetData[0].currency;
				
				} 	
				
				$scope.countryProvisionObj = {
					"countryLabelKeyTO" : {
						"id" : null,
						"code" : null,
						"name" : null

					},
					"proisionLabelKeyTO" : {
						"id" : null,
						"code" : null,
						"name" : null

					}
				};
				$scope.addAssetData = [];
				var selectedAssetData = [];
				$scope.currencies = [];
				$scope.reslut=[];
				
				$scope.provisionTOs = [];

				$scope.countries = [];
	
				
		
				
				
				ProjectSettingsService.getCountries().then(function(data) {
					$scope.countries = data.data.geonames;
					
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
				});
				
					

				$scope.getCountryDetailsById = function(country) {
					
					if(country.isoAlpha3.countryCode){
						$scope.currency=country.isoAlpha3.countryCode;
					}
					ProjectSettingsService.getProvince(country.isoAlpha3.geonameId).then(function(data) {
						$scope.provisionTOs = data.data.geonames;
						
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Province Details", "Error");
					});

				},
				$scope.yearRange = function() {
				    var result = [];
				    for (var i = 1900; i <= 2099; i++) {
				        result.push(i);
				    }
				    $scope.result=result;
				},
				
			
				$scope.getProfitCenterList = function(profitCentreTO) {
					var profitCentreService = {};
					var onLoadReq = {
						"status" : 1
					};
					var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
					profitCentrePopup.then(function(data) {
						$scope.profitCentreTOId = data.selectedRecord.id;
						$scope.profitCentreTOCode = data.selectedRecord.code;
						$scope.profitCentreTOName = data.selectedRecord.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
             //Assets Category Select Popup
				$scope.getAssetCategory = function(assetCategoryTO) {
					var assetCategoryService = {};
					var onLoadReq = {
						"status" : 1
					};
					var categoryPopup = AssetCategorySelectFactory.getAssetCategory(onLoadReq);
					categoryPopup.then(function(data) {
						$scope.assetCategoryTOId = data.selectedRecord.id;
						$scope.assetCategoryTOCode = data.selectedRecord.code;
						$scope.assetCategoryTOName = data.selectedRecord.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
				//Assets Sub Category Select Popup
				
				$scope.getAssetSubCategory = function(assetCategoryTO) {
					var assetCategoryService = {};
					var onLoadReq = {
						"status" : 1
					};
					var categoryPopup = AssetCategorySelectFactory.getAssetCategory(onLoadReq);
					categoryPopup.then(function(data) {
						$scope.assetCategoryTOId = data.selectedRecord.id;
						$scope.assetCategoryTOCode = data.selectedRecord.code;
						$scope.assetCategoryTOName = data.selectedRecord.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
				
				
				
				$scope.save = function() {			

					var saveAssetDetailsReq = {
						"fixedAssetRegisterTOs" : [
							{
							"fixedAssetid":$scope.fixedAssetid,
							"assetId":$scope.assetId,	
							"assetDescription":$scope.assetDesc,
							"assetCategoryName":$scope.assetCategoryTOName,	
							"address":$scope.address,
							"currency":$scope.currency,
							"profitCentre":$scope.profitCentreTOName,
							"yearBuild":$scope.yearBuild,
							"dateCommissioned":$scope.dateCommissioned,
							"assetLifeStatus":"",
							"ownershipStatus":"",							
							"countryName":$scope.asset.isoAlpha3.isoAlpha3.countryName,
							"provisionName":$scope.asset.provisionName.name.name,
							}
						]
					}

					AssetRegisterService.saveFixedAssetRegisters(saveAssetDetailsReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Register Detail(s) is/are  ' + data.message, data.status);
						succMsg.then(function() {
							var returnPopObj = {
								"assetRegTO" : data.fixedAssetDtlTOs
							};

							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						if (error.status != null && error.status != undefined) {
							GenericAlertService.alertMessage(error.message, error.status);
						} else {
							GenericAlertService.alertMessage('Asset Register Detail(s) is/are Failed to Save ', "Error");
						}
					});

				}
			} ]
		});
		return deferred.promise;
	}

	service.assetDetailsPopUpOnLoad = function(actionType, editAssetData) {
		var deferred = $q.defer();
		var assetDetailsPopUp = service.assetDetailsPopUp(actionType, editAssetData);
		assetDetailsPopUp.then(function(data) {

			var returnPopObj = {
				"assetDtlTOs" : data.assetDtlTOs,
				"userProjectMap" : data.userProjectMap,
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}

	return service;
}]);


