'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("materialregistor", {
		url: '/materialregistor',
		data: {
			roles: []
		},

		views: {
			'content@': {
				templateUrl: 'views/projresources/projmaterialreg/material.html',
				controller: 'MaterialController'
			}
		}
	})
}]).controller('MaterialController', ["$scope", "$rootScope", "$state", "$q", "ngDialog","EpsProjectSelectFactory", "GenericAlertService", "MaterialRegisterService", function ($scope, $rootScope, $state,$q, ngDialog, EpsProjectSelectFactory, GenericAlertService, MaterialRegisterService) {

	$scope.currentTab = null;
	let toDate = new Date().toString('dd-MMM-yyyy');
	let fromDate = new Date();
	fromDate.setMonth(fromDate.getMonth() - 1);
	$rootScope.materialSearchProject = {
		"selectedProject": null,
		"fromDate": moment(fromDate).format('DD-MMM-YYYY'),
		"toDate": moment(toDate).format('DD-MMM-YYYY')
	};

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$rootScope.materialSearchProject.selectedProject = data.searchProject;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},

		$scope.MaterialTabs = [{
			title: 'Ledger',
			url: 'views/projresources/projmaterialreg/ledger/ledger.html',
			value: 'Ledger',
			materialSeleniumLocator: 'Resources_Materials_Ledger',
			materialappCodeTemplateKey: 'RESOURCE_MATRLLEDGERREGVIEW',
			resetMethod: 'resetLedger',
			searchMethod: 'searchLedger'
		}, {
			title: 'Delivery/Supply Details',
			url: 'views/projresources/projmaterialreg/deliverysuply/materialdeliverysupplydetails.html',
			value: 'DeliverySupplyDetails',
			materialSeleniumLocator: 'Resources_Materials_DeliverySupplyDetails',
			materialappCodeTemplateKey: 'RESOURCE_MATRLDELSUPPDETAILS_VIEW',
			resetMethod: 'resetDeliverySupplyDetails',
			searchMethod: 'searchDeliverySupplyDetails'
		}, {
			title: 'Project Dockets',
			url: 'views/projresources/projmaterialreg/projdocket/materialprojdockets.html',
			materialSeleniumLocator: 'Resources_Materials_ProjectDockets',
			materialappCodeTemplateKey: 'RESOURCE_MATRLPROJDOCK_VIEW',
			value: 'MaterialProjDocketController',
			resetMethod: 'resetProjectDocket',
			searchMethod: 'searchProjectDocket'
		}, {
			title: 'Store/Materials Daily Issue Records',
			url: 'views/projresources/projmaterialreg/dailyissue/dailyissuerecords.html',
			materialSeleniumLocator: 'Resources_Materials_StoreMaterialsDailyIssueRecords',
			materialappCodeTemplateKey: 'RESOURCE_MATRLSTOREDAILYISSUERCD_VIEW',
			value: 'StoreOrMaterialsDailyIssueRecords',
			resetMethod: 'resetStoreOrMaterialsDailyIssueRecords',
			searchMethod: 'searchStoreOrMaterialsDailyIssueRecords'
		}, {
			title: 'Consumption Records',
			url: 'views/projresources/projmaterialreg/datewiseconsumption/datewiseconsumptionquantity.html',
			materialSeleniumLocator: 'Resources_Materials_ConsumptionRecords',
			materialappCodeTemplateKey: 'RESOURCE_MATRLCONSUMRECD_VIEW',
			value: 'ConsumptionRecords',
			resetMethod: 'resetConsumptionRecords',
			searchMethod: 'searchConsumptionRecords'
		}, {
			title: 'Store Items Stock OnSite-Intransit',
			url: 'views/projresources/projmaterialreg/storeitemstockbal/storesitemstockbalance.html',
			materialSeleniumLocator: 'Resources_Materials_StoreItemsStockOnSiteIntransit',
			materialappCodeTemplateKey: 'RESOURCE_MATRLSTORITMSTCKONSITINTRANS_VIEW',
			value: 'StoreItemsStockonSiteIntransit',
			resetMethod: 'resetStoreItemsStockonSiteIntransit',
			searchMethod: 'searchStoreItemsStockonSiteIntransit'
		}, {
			title: 'Materials Stock on Site-Stock Piled Items',
			url: 'views/projresources/projmaterialreg/stockpiles/stockpilesitems.html',
			materialSeleniumLocator: 'Resources_Materials_MaterialsStockonSiteStockPiledItems',
			materialappCodeTemplateKey: 'RESOURCE_MATRLSTCKSITEPILEDITM_VIEW',
			value: 'MaterialsStockonSiteStockPiledItems',
			resetMethod: 'resetMaterialsStockonSiteStockPiledItems',
			searchMethod: 'searchMaterialsStockonSiteStockPiledItems'
		}, {
			title: 'Request For Material Transfer',
			url: 'views/projresources/projmaterialreg/reqmaterialtransfer/materialregtrasnrequest.html',
			materialSeleniumLocator: 'Resources_Materials_RequestForMaterialTransfer',
			materialappCodeTemplateKey: 'RESOURCE_MATRLREQFORMATTRANSFER_VIEW',
			value: 'MaterialTransferReqApprController',
			resetMethod: 'resetRequestForMaterialTransfer',
			searchMethod: 'searchRequestForMaterialTransfer'
		}, {
			title: 'Approval For Material Transfer',
			url: 'views/projresources/projmaterialreg/approvalmaterialtransfer/materialregtransapproval.html',
			materialSeleniumLocator: 'Resources_Materials_ApprovaForMaterialTransfer',
			materialappCodeTemplateKey: 'RESOURCE_MATRLAPPRFORTRANSFER_VIEW',
			value: 'ApprovalForMaterialTransfer',
			resetMethod: 'resetApprovalForMaterialTransfer',
			searchMethod: 'searchApprovalForMaterialTransfer'
		}];

	$scope.currentMaterialTabs = 'views/projresources/projmaterialreg/ledger/ledger.html';
	$scope.currentTab = $scope.MaterialTabs[0];
	$scope.onClickMaterialTabs = function (masterTabs) {
		$scope.currentTab = masterTabs;
		$scope.currentMaterialTabs = masterTabs.url;
	},

		$scope.isActiveMaterialTabs = function (masterTabsUrl) {
			return masterTabsUrl == $scope.currentMaterialTabs;
		}
	$scope.moreFlag = 0;
	$scope.clickMore = function (moreFlag1) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickMaterialTabs($scope.MaterialTabs[0]);
	}, $scope.clickMore1 = function (moreFlag1) {
		if ($scope.moreFlag < 1) {
			$scope.moreFlag = moreFlag1 + 1;
		}
		$scope.onClickMaterialTabs($scope.MaterialTabs[6]);
	}

	$scope.materialSearch = function () {
		/*if ($rootScope.materialSearchProject.selectedProject == null || $rootScope.materialSearchProject.selectedProject == undefined) {
			GenericAlertService.alertMessage("Select project", 'Warning');
			return;
		}*/
		var fromObject = new Date($rootScope.materialSearchProject.fromDate);

		var toObject = new Date($rootScope.materialSearchProject.toDate);
		if (fromObject <= toObject) {
			$rootScope.$broadcast($scope.currentTab.value);
		} else {
			GenericAlertService.alertMessage("From Date can not be greater than To Date", 'Warning');
			return;
		}

		$rootScope.$broadcast($scope.currentTab.searchMethod);
	}

	$scope.materialReset = function () {
		/*var fromObject = new Date($rootScope.materialSearchProject.fromDate);

		var toObject = new Date($rootScope.materialSearchProject.toDate);
		$rootScope.materialSearchProject = {
			"selectedProject" : null,
			
		};*/
		$rootScope.materialSearchProject = {
			"selectedProject": null,
			"fromDate": moment(fromDate).format('DD-MMM-YYYY'),
			"toDate": moment(toDate).format('DD-MMM-YYYY')
		}
		// $scope.materialHomedefaultSearch();
		$rootScope.$broadcast($scope.currentTab.resetMethod);
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
			template: 'views/help&tutorials/resourceshelp/materialshelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}])
