'use strict';
app.factory('AssetDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProfitCentrePopUpFactory", "AssetCategorySelectFactory", "ProjectSettingsService", "TaxCountryFactory", "AssetRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService,ProfitCentrePopUpFactory,AssetCategorySelectFactory, ProjectSettingsService,TaxCountryFactory, AssetRegisterService) {
	var assetDetailsPopUp;
	var service = {};
	service.assetDetailsPopUp = function(actionType, editAssetData) {
		var deferred = $q.defer();
		assetDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/assetdetails/assetdetailspopup.html',
			closeByDocument : false,
			showClose : false,
			className  : 'ngdialog-theme-plain ng-dialogueCustom0',
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
						"searchProject":'',
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
						"searchProject":'',
						"subAssetCategory":'',

						"childProjs" : []
					});
					$scope.itemClick1(null, false);
				}

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
						"searchProject":'',
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


