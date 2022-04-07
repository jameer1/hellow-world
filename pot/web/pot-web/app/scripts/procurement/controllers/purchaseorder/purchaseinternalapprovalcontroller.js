'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("pointernalapproval", {
		url : '/pointernalapproval',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/pointernalapproval.html',
			}
		}
	})
}]).controller("PurchaseInternalApprovalController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PurchaseDocumentsFactory", "PreContractInternalScheduleItemsFactory", "PrecontractReqApprovalFactory", "POReferenceDocumentsFactory", "PurchaseInternalFactory", "GenericAlertService", "PurchaseOrderService", "PreContractInternalService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,PurchaseDocumentsFactory ,PreContractInternalScheduleItemsFactory ,PrecontractReqApprovalFactory, POReferenceDocumentsFactory ,PurchaseInternalFactory, GenericAlertService,PurchaseOrderService ,PreContractInternalService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.getInternalPrecontractDetails = function() {
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
		var req = {
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
			"status" : 1
		};
		PreContractInternalService.getInternalPreContractPopupOnLoad(req).then(function(data) {
			$scope.preContractTOs = [data.preContractTO];
			$scope.gridOptions.data = angular.copy($scope.preContractTOs);
			$scope.projEmpClassMap = data.projEmpClassMap;
			$scope.projPlantMap = data.projPlantMap;
			$scope.projMaterialClassMap = data.projMaterialClassMap;
			$scope.projServiceMap = data.projServiceMap;
			$scope.projStoreMap = data.projStoreMap;
			$scope.storeMap = data.storeMap;
			$scope.projCostItemMap = data.projCostItemMap;
			$scope.userMap = data.usersMap;
			$scope.companyMap = data.companyMap;
			$scope.projSOWMap = data.projSOWMap;
			$scope.procureCategoryMap = data.procureCategoryMap;
			$scope.expandCollapseManpower = function(manpowerFlag) {
				$scope.manpowerFlag = !manpowerFlag;
			}, $scope.expandCollapsePlant = function(plantFlag) {
				$scope.plantFlag = !plantFlag;
			}, $scope.expandCollapseMaterial = function(materialFlag) {
				$scope.materialFlag = !materialFlag;
			}, $scope.expandCollapseService = function(serviceFlag) {
				$scope.serviceFlag = !serviceFlag;
			}, $scope.expandCollapseSow = function(sowFlag) {
				$scope.sowFlag = !sowFlag;
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting manpower details", 'Error');
		})

	},
	$scope.viewInternalRequestById = function(preContractObj) {
		var resultData = $scope.getInternalDetailsById(preContractObj);
		resultData.then(function(data) {
			var popupDetails = PreContractInternalScheduleItemsFactory.procInternalApprovalPopUp(data.preContractObj, 'showActionButtons');
			popupDetails.then(function(data) {
				$scope.getInternalPreContracts1();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
		})

	}, $scope.getInternalDetailsById = function(preContractObj) {
		var internalDefer = $q.defer();
		var onLoadReq = {
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"status" : 1
		};
		PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
			var returnObj = {
				"preContractObj" : angular.copy(data)
			};
			internalDefer.resolve(returnObj);
		});
		return internalDefer.promise;

	},
$scope.getInternalPrecontractDetails();
	$scope.getDocumentDetails = function() {
		var onLoadReq = {
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
			"status" : 1
		};
		PreContractInternalService.getPreContratDocs(onLoadReq).then(function(data) {
			$scope.refdocumentslist = data;
		},
		 function(error) {
		GenericAlertService.alertMessage("Error occurred while getting pre-contract documents", 'Error');
	})

},$scope.getRefDocument = function(precontract) {
	var refreq = POReferenceDocumentsFactory.referenceDocumentDetails(precontract);
	refreq.then(function(data) {
		$scope.refdocumentslist = data.preContractDocsTOs;
	}, function(error) {
		GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
	})

},$scope.getApprovelDetailss = function(req) {
	var req = {
		"contractId" : req.id,
		"status" : 1
	}
	var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req,$scope.userMap);
	popupDetails.then(function(data) {
	}, function(error) {
		GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
	})
}
$scope.$on("resetStage1appoval", function() {
	$scope.preContractTOs = [];
});
$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name",},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'code', displayName: "Pre-Contract ID", headerTooltip: "Pre-Contract ID", },
						{ field: 'desc', displayName: "Pre-Contract Description", headerTooltip: "Pre-Contract Description", },
						
						{ field: 'preContractReqApprTO.reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", },
						{ field: 'preContractReqApprTO.reqUserLabelkeyTO.code', displayName: "Requestor", headerTooltip: "Requestor"},
						{ field: 'preContractReqApprTO.apprUserLabelkeyTO.code', displayName: "Approver", headerTooltip: "Approver", },
						{ field: 'preContractType', displayName: "Pre-Contract Type", headerTooltip: "Pre-Contract Type", },
						{ name: 'Schedule Items', displayName: "Schedule Items", headerTooltip: "Schedule Items",cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.viewInternalRequestById(row.entity, req)" data-test="PurchaseOrders_Stage1Requestscheduleview">View</button>'},
						{ field: 'workFlowStatusTO.desc', displayName: "Response Status", headerTooltip: "Response Status", },
						{ name: 'Approver Details', displayName: "Approver Details", headerTooltip: "Approver Details", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.getApprovelDetailss(row.entity, req)" data-test="PurchaseOrders_Stage1Requestapprovedetailsview">View</button>'},
						{ name: 'Document',displayName: "Reference Documents", headerTooltip : "Reference Documents", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.getRefDocument(row.entity, precontract)" data-test="PurchaseOrders_Stage1Requestrefdocument" class="btn btn-primary btn-sm" >Ref Document</button>'}
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement_purchaseOrderList_Stage1Approval");
				}
		});
}]);