'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantregistor", {
		url: '/plantregistor',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projresources/projplantreg/plantdetails/plantregdetails.html',
				controller: 'PlantRegController'
			}
		}
	})
}]).controller("PlantRegController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "PlantDetailsFactory", "PlantRegisterService", "EpsProjectMultiSelectFactory","GenericAlertService", "EpsProjectSelectFactory","stylesService", "ngGridService", function ($rootScope, $scope, $q, $state, $location, ngDialog, PlantDetailsFactory, PlantRegisterService,
	EpsProjectMultiSelectFactory, GenericAlertService, EpsProjectSelectFactory, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantRegisterDtlTOs = [];
	var editPlantData = [];
	var projId = 0;
	$rootScope.selectedPlantData = null;
	$scope.isDisabled = false;

	$scope.searchProject = {};
	$scope.plantDatamoreFlag = 0;
	$scope.prPlantCurrentStatus = [];
	$scope.userProjMap = [];

	$scope.clickForwardPlantData = function (plantDatamoreFlag1) {
		if ($scope.plantDatamoreFlag < 1) {
			$scope.plantDatamoreFlag = plantDatamoreFlag1 + 1;
		}
	}, $scope.clickBackwardPlantData = function (plantDatamoreFlag1) {
		if ($scope.plantDatamoreFlag > 0) {
			$scope.plantDatamoreFlag = plantDatamoreFlag1 - 1;
		}
	}
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project", 'Error');
		});
	}, $scope.setSelected = function (row) {
		$scope.rowSelect = row;
		$scope.selectedRow = row;
	}

	$scope.plantDetailsTabs = [{
		title: ' Procurement & Delivery  Details',
		url: 'views/projresources/projplantreg/plantprocrdelivery/plantregprocrdeliverydtl.html',
		value: 'Procurement&Delivery Details',
		resetMethod: 'resetProcurDelDet',
		plantappCodeTemplateKey: 'RESOURCE_PLANTPROCDELDETLS_VIEW',
		plantSelenumLocator: 'Resources_PlantandEquipment'
	}, {
		title: 'Deployment History',
		url: 'views/projresources/projplantreg/plantdeployment/plantregdeployment.html',
		value: 'DeploymentHistory',
		resetMethod: 'resetDeploymentHistory',
		plantappCodeTemplateKey: 'RESOURCE_PLANTDEPHISTORY_VIEW',
		plantSelenumLocator: 'PlantandEquipment_DeploymentHistory'

	},{
		title: 'Payable Rates',
		url: 'views/projresources/projplantreg/payablerates/payablerates.html',
		value: 'PayableRates',
		resetMethod: 'resetPayableRates',
		plantappCodeTemplateKey: 'RESOURCE_PLANTPAYABLERATES_VIEW',
		plantSelenumLocator: 'PlantandEquipment_PayableRates'
	}, 
	{
		title: 'Charge out Rates',
		url: 'views/projresources/projplantreg/plantcharge/plantregcharge.html',
		value: 'ChargeoutRates',
		resetMethod: 'resetChargeoutRates',
		plantappCodeTemplateKey: 'RESOURCE_PLANTCHARGEOUTRATE_VIEW',
		plantSelenumLocator: 'PlantandEquipment_ChargeoutRates'

	}, {
		title: 'Utilisation Records',
		url: 'views/projresources/projplantreg/plantutilization/plantregutilization.html',
		value: 'UtilizationRecords',
		resetMethod: 'resetUtilizationRecords',
		plantappCodeTemplateKey: 'RESOURCE_PLANTUTILIZATIONRCD_VIEW',
		plantSelenumLocator: 'PlantandEquipment_UtilizationRecords'

	}, {
		title: 'Log Book Records',
		url: 'views/projresources/projplantreg/planttabs/plantreglogbooktabs.html',
		plantappCodeTemplateKey: 'RESOURCE_PLANTLONGBOOKRECORDS_VIEW',
		plantSelenumLocator: 'PlantandEquipment_LogBookRecords'
		,
		logBookSubTabs: [{
			title: 'Log Book',
			url: 'views/projresources/projplantreg/plantlogbook/plantreglogbook.html',
			value: 'LogBook',
			resetMethod: 'resetLogBook',
			plantappCodeTemplateKey: 'RESOURCE_PLANTLONGBOOK_VIEW',
			plantSelenumLocator: 'PlantandEquipment_LogBookRecords_LogBook'

		}, {
			title: 'Plant Working Days',
			url: 'views/projresources/projplantreg/plantlogbook/plantregworkingdays.html',
			value: 'PlantWorkingDays',
			resetMethod: 'resetPlantWorkingDays',
			plantappCodeTemplateKey: 'RESOURCE_PLANTWORKINGDAYS_VIEW',
			plantSelenumLocator: 'PlantandEquipment_LogBookRecords_PlantWorkingDays'

		}]
	}, {
		title: 'Service History & Repairs',
		url: 'views/projresources/projplantreg/planttabs/plantregservicehistorytabs.html',
		plantappCodeTemplateKey: 'RESOURCE_PLANTSERVICEHISTORYREPAIR_VIEW',
		plantSelenumLocator: 'PlantandEquipment_ServiceHistoryRepairs',
		serviceHistorySubTabs: [{
			title: 'Service History',
			url: 'views/projresources/projplantreg/plantservicehistory/plantregservicehistory.html',
			value: 'ServiceHistory',
			plantappCodeTemplateKey: 'RESOURCE_PLANTSERVICEHISTORY_VIEW',
			plantSelenumLocator: 'PlantandEquipment_ServiceHistoryRepairs_ServiceHistory',
			resetMethod: 'resetServiceHistory'
		}, {
			title: 'Records of Repair',
			url: 'views/projresources/projplantreg/plantservicehistoryrepairs/plantregservicehistoryrepair.html',
			value: 'RecordsofRepair',
			plantappCodeTemplateKey: 'RESOURCE_PLANTRECDOFREPAIR_VIEW',
			plantSelenumLocator: 'PlantandEquipment_ServiceHistoryRepairs_RecordsofRepair',
			resetMethod: 'resetRecordsofRepair'
		}]
	}, {
		title: 'Depreciation and Salvage Value',
		url: 'views/projresources/projplantreg/plantdepreciation/plantregdepreciation.html',
		plantappCodeTemplateKey: 'RESOURCE_PLANTDEPRANDSALVEVALUE_VIEW',
		plantSelenumLocator: 'PlantandEquipment_DepreciationandSalvageValue',
		value: 'DepandSalValue',
		resetMethod: 'resetDepandSalValue'
	}, {
		title: 'Request For Transfer',
		url: 'views/projresources/projplantreg/requestForTransfer/plantregtrasnferreq.html',
		value: 'Request',
		plantappCodeTemplateKey: 'RESOURCE_PLANTREQFORTRANSFER_VIEW',
		plantSelenumLocator: 'PlantandEquipment_RequestForTransfer',
		resetMethod: 'resetRequest'
	}, {
		title: 'Approval For Transfer',
		url: 'views/projresources/projplantreg/approvalForTransfer/plantregtransferapproval.html',
		value: 'Approval',
		plantappCodeTemplateKey: 'RESOURCE_PLANTAPPRFORTRANSFER_VIEW',
		plantSelenumLocator: 'PlantandEquipment_ApprovalForTransfer',
		resetMethod: 'resetApproval'
	}];

	$scope.resetSearchData = function () {
		console.log('reset tab');
		$scope.disableFlag = 2;
		$scope.selectedRow = null;
		$scope.searchProject = {};
		$rootScope.selectedPlantData = null;
		editPlantData = [];
		$scope.plantRegisterDtlTOs = [];
		$scope.currentTab = $scope.plantDetailsTabs[0];
		console.log('reset tab currentTab ', $scope.currentTab);
		$scope.getSearchPlantRegisters();
		$rootScope.$broadcast($scope.currentTab.resetMethod);
	},

		$scope.getSearchPlantRegisters = function (click) {
			$scope.currentTab = '';
			$scope.currentPlantDetailsTab = '';
			$scope.selectedRow = null;
			$rootScope.selectedPlantData = null;
			editPlantData = [];
			$scope.plantRegisterDtlTOs = [];
					var deferred = $q.defer();
					editPlantData = [];
					var projIds = null;
					$scope.selectedRow = null;
					if ($scope.searchProject.projIds != null && $scope.searchProject.projIds.length > 0) {
			         projIds = [];
			         projIds = $scope.searchProject.projIds;
		            }
					var getPlantRegisterReq = {
						"status": "1",
						"projIds": projIds
					};
					if(click=='click'){
						if ($scope.searchProject.projIds == null || $scope.searchProject.projIds == undefined) {
							GenericAlertService.alertMessage("Please select Project", "Warning");
							return;
						}
					}
					PlantRegisterService.getPlantRegistersOnLoad(getPlantRegisterReq).then(function (data) {
					//	$scope.plantRegisterDtlTOs = data.plantRegisterDtlTOs;
						if(click == undefined){
						  $scope.plantRegisterDtlTOs = data.plantRegisterDtlTOs;	
						}						
						if(click=='click'){
							for(var i=0;i<data.plantRegisterDtlTOs.length;i++){
								if(data.plantRegisterDtlTOs[i].projId != null){
									$scope.plantRegisterDtlTOs.push(data.plantRegisterDtlTOs[i]);
								}
							}
						}
						$scope.gridOptions.data = angular.copy($scope.plantRegisterDtlTOs);
						$scope.assertTypes = data.assertTypes;
						deferred.resolve();
						if(click=='click'){
							if ($scope.plantRegisterDtlTOs.length <= 0) {
								GenericAlertService.alertMessage("Plant & Equipment details not available for the search criteria", "Warning");
								return;
							}
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting plant details", "Error");
					});
					if ($scope.searchProject.projIds != null && $scope.searchProject.projIds != undefined) {
						$scope.disableFlag = 1;
					} else {
						$scope.disableFlag = 2;
					}
					return deferred.promise;
		}

	$scope.getPlantCurrentStatus = function (plant) {
		var req = {
			"status": 1,
			"projIds": $scope.searchProject.projIds,
			"plantId": plant.id
		};
		PlantRegisterService.getPlantCurrentStatus(req).then(function (data) {
			$scope.prPlantCurrentStatus = data.plantCurrentStatusList;
			$scope.userProjMap = data.userProjMap;

		}, function (error) {
			if (error.status != undefined && error.status != null) {
				GenericAlertService.alertMessage(error.message, error.status);
			} else {
				GenericAlertService.alertMessage("Error occured while selecting the plant details", 'Error');
			}
		});

	}
	/* =========plantDetails tabs======= */
	$scope.currentPlantDetailsTab = 'views/projresources/projplantreg/plantdetails/plantregdetails.html';
	$scope.currentTab = $scope.plantDetailsTabs[0];
	console.log('$scope.currentTab  ',$scope.currentTab);
	$scope.onClickPlantDetailsTab = function (tab) {
		console.log('onClickPlantDetailsTab  ', tab);
		if (!$rootScope.selectedPlantData) {
			GenericAlertService.alertMessage("Please select plant !!", 'Warning');
			return;
		}
		
		console.log('$scope.currentPlantDetailsTab  ', $scope.currentPlantDetailsTab);
		// Procurement & Delivery  Details are not available for existing plant
		if ($rootScope.selectedPlantData.assetType === 'Existing Plant' 
			&& $scope.plantDetailsTabs[0].title === tab.title) {
			GenericAlertService.alertMessage(tab.title + " are not available for existing plant", 'Info');
			$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[1]);
		}
		if ($rootScope.selectedPlantData.assetType == 'Existing Plant' && $scope.plantDetailsTabs[2].title === tab.title) {
			GenericAlertService.alertMessage(tab.title + " is not available for existing plant", 'Info');
			$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[1]);
		}
		else if ($scope.plantDetailsTabs.indexOf(tab) == 5) { //$scope.plantDetailsTabs.indexOf(tab) == 4
			$scope.currentTab = tab;
			$scope.currentPlantDetailsTab = tab.url;
			$scope.onClickLogBookTab(tab.logBookSubTabs[0]);
		} else if ($scope.plantDetailsTabs.indexOf(tab) == 6) {
			$scope.currentTab = tab;
			$scope.currentPlantDetailsTab = tab.url;
			$scope.onClickServiceHistoryTab(tab.serviceHistorySubTabs[0]);
		} else {
			console.log('else condition ', tab);
			$scope.currentTab = tab;
			$scope.currentPlantDetailsTab = tab.url;
		}
	},
		$scope.isActivePlantDetailsTab = function (masterTabsUrl) {
			return masterTabsUrl == $scope.currentPlantDetailsTab;
		},
		$scope.go = function (plantRegisterDtlTOs, indexValue, plant) {
			console.log('plantRegisterDtlTOs  ', plantRegisterDtlTOs);
			console.log('indexValue  ', indexValue);
			console.log('plant  ', plant);
			angular.forEach(plantRegisterDtlTOs, function (plant, index) {
				if (indexValue != index) {
					plant.selected = false;
				}
				if (plant.selected) {
					plant.selected = false;
				}
			});
			$rootScope.selectedPlantData = plant;
			$scope.moreFlag = 0;
			$scope.setSelected(indexValue);
			if (plant.assetType === "Existing Plant")
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[1]);
			else
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[2]);
			    $rootScope.$broadcast('defaultPayableRatesTab');

			if (plant.assetType === "Existing Plant")
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[1]);
			else
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[0]);
			$rootScope.$broadcast('defaultPlantProjDeliveryTab');
		};
	/* =========Inner tabs======= */
	$scope.currentPlantDetailsTab = $scope.plantDetailsTabs[0].url;
	$scope.onClickTab = function (plantDetailsTabs) {
		$scope.currentServiceHistoryTab = null;
		$scope.currentPlantDetailsTab = plantDetailsTabs.url;
		if ($scope.currentPlantDetailsTab == 'views/projresources/projplantreg/planttabs/plantregservicehistorytabs.html') {
			$scope.onClickServiceHistoryTab();
		}
		if ($scope.currentPlantDetailsTab == 'views/projresources/projplantreg/planttabs/plantreglogbooktabs.html') {
			$scope.onClickLogBookTab();
		}

	}, $scope.isActiveTab = function (plantDetailsTabsUrl) {
		//console.log('isActiveTab ', plantDetailsTabsUrl);
		return plantDetailsTabsUrl == $scope.currentTab.url;
	}, $scope.moreFlag = 0;
	$scope.clickMore = function (moreFlag1) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[0]);
	}, $scope.clickMore1 = function (moreFlag1) {
		if ($scope.moreFlag < 1) {
			$scope.moreFlag = moreFlag1 + 1;
		} else {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[7]);
	}


	// =========Service History Sub Tabs=======
	$scope.onClickServiceHistoryTab = function (serviceHistorySubTabs) {
		$scope.currentServiceHistoryTab = 'views/projresources/projplantreg/plantservicehistory/plantregservicehistory.html';
		$scope.currentServiceHistoryTab = serviceHistorySubTabs.url;
	}, $scope.isActiveServiceHistoryTab = function (serviceHistorySubTabsUrl) {
		return serviceHistorySubTabsUrl == $scope.currentServiceHistoryTab;
	},


		// =========Log Book Sub Tabs=======

		$scope.onClickLogBookTab = function (logBookSubTabs) {
			$scope.currentLogBookTab = 'views/projresources/projplantreg/plantlogbook/plantreglogbook.html';
			$scope.currentLogBookTab = logBookSubTabs.url;
		}, $scope.isActiveLogBookTab = function (logBookSubTabs) {
			return logBookSubTabs == $scope.currentLogBookTab;
		}
	$scope.plantRowSelect = function (position, plants) {
			if (plants.selected) {
				console.log(plants.selected);
				if ($scope.selectedRow) {
					$scope.selectedRow = null;
					$rootScope.$broadcast($scope.currentTab.resetMethod);
				}
				$scope.selectedRow = null;
				editPlantData.push(plants);
				$rootScope.$broadcast($scope.currentTab.resetMethod);
			} else {
				editPlantData.splice(editPlantData.indexOf(plants), 1);
				$rootScope.$broadcast($scope.currentTab.resetMethod);
			}
	}
	/*$scope.plantRowSelect = function (position, plantRegisterDtlTOs) {
		angular.forEach(plantRegisterDtlTOs, function (plant, index) {
			if (position != index) {
				plant.selected = false;
				return;
			}
			editPlantData.splice(editPlantData.indexOf(plant), 1);
			if (plant.selected) {
				if ($scope.selectedRow) {
					$scope.selectedRow = null;
					$rootScope.$broadcast($scope.currentTab.resetMethod);
				}
				$scope.selectedRow = null;
				editPlantData.push(plant);
				$rootScope.$broadcast($scope.currentTab.resetMethod);
			} else {
				editPlantData.splice(editPlantData.indexOf(plant), 1);
				$rootScope.$broadcast($scope.currentTab.resetMethod);
			}
		});
	}*/
	$scope.addplantList = function (actionType) {
		if (actionType == 'Edit' && editPlantData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
		var plantdetailspopup = PlantDetailsFactory.plantDetailsPopUp($scope.assertTypes, actionType, editPlantData);
		plantdetailspopup.then(function (data) {
			editPlantData = [];
			$scope.tableData = data.plantRegisterDtlTOs;
			$scope.plantRegisterDtlTOs = data.plantRegisterDtlTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching Plant  details", 'Error');
		});

	}, $scope.deletePlantRegisters = function () {
		if (editPlantData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editPlantData, function (value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"plantRegIds": deleteIds,
				"status": 2
			};

			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				PlantRegisterService.deletePlantRegisters(req).then(function (data) {
					// $scope.resetData();
					GenericAlertService.alertMessage('Plant Registration Details are  Deactivated Successfully', 'Info');
					editPlantData = [];
					// editTableData = [];
					$scope.deleteIds = [];
                   $scope.getSearchPlantRegisters();
				});

				angular.forEach(editPlantData, function (value, key) {
					$scope.tableData.splice($scope.tableData.indexOf(value), 1);
				}, function (error) {
					GenericAlertService.alertMessage('Plant Registration Details are failed to Deactivate', "Error");
				});


			}, function (data) {
				angular.forEach(editPlantData, function (value) {
					value.selected = false;
				})
			})
		}
	}

	$scope.$on("LogBookTab", function () {
		projId = $scope.searchProject.projId;
		if (projId == null || projId == undefined) {
			return;
		} else {
			$scope.getSearchPlantRegisters(projId);
			$scope.setSelected($scope.rowSelect);
			$scope.currentPlantDetailsTab = 'views/projresources/projplantreg/planttabs/plantreglogbooktabs.html'
		}
	});

	$rootScope.$on('plantRefresh', function (event, value) {
		var selectedIndex = $scope.selectedRow;
		$scope.getSearchPlantRegisters().then(function () {
			$scope.go($scope.plantRegisterDtlTOs, selectedIndex, $scope.plantRegisterDtlTOs[selectedIndex], value);
		});
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.plantRowSelect($index,row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellClass: bgColor, displayName: "Select", headerTooltip : "Select", cellTemplate:linkCellTemplate },
						{ field: 'assertId', cellClass: bgColor, displayName: "Asset ID", headerTooltip: "Asset ID",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.assertId}}</div>'},
						{ field: 'regNumber', cellClass: bgColor, displayName: "Registration Number", headerTooltip: "Registration Number", 
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.regNumber}}</div>'},
						{ field: 'plantClassMstrName', cellClass: bgColor, displayName: "Classification Name", headerTooltip : "Classification Name", 
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantClassMstrName}}</div>'},
						
						{ field: 'desc', cellClass: bgColor, displayName: "Description", headerTooltip : "Description", 
						cellTemplate: '<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.desc}}</div>'},
						{ field: 'manfacture', cellClass: bgColor, displayName: "Make", headerTooltip: "Make",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.manfacture}}</div>'},
						{ field: 'model', cellClass: bgColor, displayName: "Model", headerTooltip: "Model",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.model}}</div>'},
						{ field: 'assetType', cellClass: bgColor, displayName: "Exng or New Asset", headerTooltip : "Exng or New Asset",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.assetType}}</div>' },
						
						{ field: 'cmpName', cellClass: bgColor, displayName: "Supplier Name", headerTooltip : "Supplier Name",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.cmpName}}</div>' },
						{ field: 'ownerCmpName', cellClass: bgColor, displayName: "Owner Name", headerTooltip: "Owner Name",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.ownerCmpName}}</div>'},
						{ field: 'procurecatgName', cellClass: bgColor, displayName: "Purchase Category", headerTooltip: "Purchase Category", 
						cellTemplate: '<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.procurecatgName}}</div>'},
						{ field: 'plantClassMeasureName', cellClass: bgColor, displayName: "Unit of Measure", headerTooltip : "Unit of Measure", 
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantClassMeasureName}}</div>'},
						
						{ field: 'plantRegProjTO.parentName', cellClass: bgColor, displayName: "EPS", headerTooltip : "EPS", 
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.parentName}}</div>'},
						{ field: 'plantRegProjTO.name', cellClass: bgColor, displayName: "Project", headerTooltip: "Project",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.name}}</div>'},
						{ field: 'plantRegProjTO.postDeMobStatus', cellClass: bgColor, displayName: "Current Status", headerTooltip: "Current Status",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.postDeMobStatus}}</div>'},
						{ field: 'plantRegProjTO.commissionDate', cellClass: bgColor, displayName: "Purchase/Commn Date", headerTooltip : "Purchase/Commn Date",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.commissionDate}}</div>' },
						
						{ field: 'plantRegProjTO.mobDate', cellClass: bgColor, displayName: "Mob Date", headerTooltip: "Mob Date",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.mobDate}}</div>'},
						{ field: 'plantRegProjTO.anticipatedDeMobDate', cellClass: bgColor, displayName: "Exp De-Mob Date", headerTooltip : "Exp De-Mob Date",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.anticipatedDeMobDate}}</div>' },
						{ field: 'plantRegProjTO.deMobDate', cellClass: bgColor, displayName: "Act De-Mob Date", headerTooltip: "Act De-Mob Date",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.deMobDate}}</div>'},
						{ field: 'plantRegProjTO.odoMeter', cellClass: bgColor, displayName: "Current OdoMeter Reading", headerTooltip : "Current OdoMeter Reading",
						cellTemplate:'<div ng-click="grid.appScope.go(plantRegisterDtlTOs,$index,row.entity)" ng-class="{selected: $index ==selectedRow}">{{row.entity.plantRegProjTO.odoMeter}}</div>' },
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_PlantAndEquipments");
					$scope.getSearchPlantRegisters();
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});
	
	/*$scope.search = function () {
		if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
			GenericAlertService.alertMessage("Please select Project", "Warning");
			return;
		}
			
	}*/
	function bgColor(grid, row, col, rowRenderIndex, colRenderIndex) {
		if (row.entity.plantRegProjTO.parentName !== null && row.entity.projId === null && {'bgColor':'yellow'}) {
		  return 'yellow';
		}
		/*if (row.entity.apprStatus === 'Approved' || {'bgColor':'red'}) {
		  return 'red';
		}*/
	  }
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/resourceshelp/planthelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);
