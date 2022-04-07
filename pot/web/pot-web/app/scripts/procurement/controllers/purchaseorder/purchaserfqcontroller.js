'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("porfq", {
		url : '/porfq',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/purchaseorderrfq.html',
			}
		}
	})
}]).controller("PurchaseRfqController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "RFQService", "RfqScheduleItemsEditFactor", "PreContractExternalService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,RFQService,RfqScheduleItemsEditFactor ,PreContractExternalService, GenericAlertService,stylesService, ngGridService,) {

	$scope.getPreContractCmpQuoteDetails = function() {
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
		var req = {
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
			"preContractCmpId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.id,
			"status" : 1
		};
		PreContractExternalService.getExternalPreContractPopupOnLoad(req).then(function(data) {
			$scope.preContractCmpTOs = data.preContractTO.preContractCmpTOs;
			var preContact = angular.copy($scope.preContractCmpTOs).map((item) => {
				if(item.status == 1){
					item.status = "Yes"
				}else{
					item.status = "No"
				}return item;
				
			});
			$scope.gridOptions.data = preContact;
			$scope.repeatPOPreContractTOs = data.repeatPOPreContractTO;
			$scope.projEmpClassMap = data.projEmpClassMap;
			$scope.projPlantMap = data.projPlantMap;
			$scope.projMaterialClassMap = data.projMaterialClassMap;
			$scope.projServiceMap = data.projServiceMap;
			$scope.projStoreMap = data.projStoreMap;
			$scope.storeMap = data.storeMap;
			$scope.projCostItemMap = data.projCostItemMap;
			$scope.usersMap = data.usersMap;
			$scope.companyMap = data.companyMap;
			$scope.projSOWMap = data.projSOWMap;
			$scope.procureCategoryMap = data.procureCategoryMap;
		},
		 function(error) {
		GenericAlertService.alertMessage("Error occurred while getting RFQ details", 'Error');
	})

},
$scope.editScheduleItemsDetails = function(preContractCmpTO) {
	var resultData = $scope.getPrecontractSchItems(preContractCmpTO);
	resultData.then(function(data) {
		var popupDetails = RfqScheduleItemsEditFactor.editPrecontractSchItems(data.preContractObj, preContractCmpTO, $scope.rfq);
		popupDetails.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting precontract details", 'Error');
		})
	}, function(error) {
		GenericAlertService.alertMessage("Error occurred while getting precontract details", 'Error');
	})
}, $scope.getPrecontractSchItems = function(preContractCmpTO) {
	var externalDefer = $q.defer();
	var onLoadReq = {
		"projId" : $rootScope.selectedPurchaseOrderData.projId,
		"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
		"preContractCmpId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.id,
		"status" : 1
	};
	RFQService.getPreContractCmpQuoteDetails(onLoadReq).then(function(data) {
		var returnObj = {
			"preContractObj" : angular.copy(data)
		};
		externalDefer.resolve(returnObj);
	});
	return externalDefer.promise;
}
$scope.getPreContractCmpQuoteDetails();
$scope.$on("resetRFQ", function() {
	$scope.preContractCmpTOs = [];
});

 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'preContractTO.epsName',displayName: 'EPS Name', headerTooltip :'EPS Name', groupingShowAggregationMenu: false,},
				{ field: 'preContractTO.projName',displayName:'Project Name',headerTooltip: "Project Name",  groupingShowAggregationMenu: false,},				
				{ field: 'preContractTO.code',displayName:'Pre-Contract ID',headerTooltip: "Pre-Contract ID",groupingShowAggregationMenu: false},
				{ field: 'preContractTO.desc',displayName:'Contract Description',headerTooltip: "Contract Description",  groupingShowAggregationMenu: false},	
				{ field: 'preContractTO.preContractReqApprTO.reqUserLabelkeyTO.code',displayName:'Requester',headerTooltip: "Requester", groupingShowAggregationMenu: false},	
				{ field: 'preContractTO.preContractReqApprTO.apprUserLabelkeyTO.code',displayName:'Approver',headerTooltip: "Approver", groupingShowAggregationMenu: false},	
				{ field: 'companyTO.cmpName',displayName:'Vendor',headerTooltip: "Vendor",  groupingShowAggregationMenu: false},
				{ field: 'preContractTO.revisedCloseDate',displayName:'Tender Close Date',headerTooltip: "Tender Close Date",  groupingShowAggregationMenu: false},	
				{ name: 'S of Items',displayName:'Schedule of Items',headerTooltip: "Schedule of Items",groupingShowAggregationMenu: false,cellClass:'justify-center',headerCellClass:'justify-center',
				  cellTemplate:'<button ng-click="grid.appScope.viewScheduleItemsDetails(row.entity)" class="btn btn-primary ng-scope">View</button>'},
				{ name: 'ToVendor',displayName:'Correspondence-To Vendor',headerTooltip: "Correspondence-To Vendor",groupingShowAggregationMenu: false,cellClass:'justify-center',headerCellClass:'justify-center',
				  cellTemplate:'<button ng-click="grid.appScope.getToVendorDetails(row.entity)" class="btn btn-primary ng-scope">View</button>'},
				{ name: 'FromVendor',displayName:'Correspondence-From Vendor',headerTooltip: "Correspondence-From Vendor",groupingShowAggregationMenu: false,cellClass:'justify-center',headerCellClass:'justify-center',
				  cellTemplate:'<button ng-click="grid.appScope.getFromVendorDetails(row.entity)" class="btn btn-primary ng-scope">View</button>'},
				{ name: 'status',displayName:'Quotes Received',headerTooltip: "Quotes Received", groupingShowAggregationMenu: false,cellClass:'justify-center',headerCellClass:'justify-center',},
				{ name: 'edit quotes from Vendor',displayName:'Edit Quotes from Vendor',headerTooltip: "Edit Quotes from Vendor", groupingShowAggregationMenu: false,cellClass:'justify-center',headerCellClass:'justify-center',
				  cellTemplate:'<button ng-click="grid.appScope.editScheduleItemsDetails(row.entity)" class="btn btn-primary ng-scope">View/edit</button>'},
				{ field: 'biddingStatus',displayName:'RFQ/Bidding Status',headerTooltip: "RFQ/Bidding Status", groupingShowAggregationMenu: false},				
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement_purchase orders_RFQ Tendering");
			$scope.gridOptions.showColumnFooter = false;
		}
	});
}]);
