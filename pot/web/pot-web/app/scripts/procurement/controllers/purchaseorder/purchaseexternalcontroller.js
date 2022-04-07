'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("poexternal", {
		url : '/poexternal',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/poexternal.html',
			}
		}
	})
}]).controller("PurchaseExternalController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PreContractExternalFactory", "PurchaseBidanalysisFactory", "PrecontractReqApprovalFactory", "PreContractExternalService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,PreContractExternalFactory, PurchaseBidanalysisFactory,PrecontractReqApprovalFactory,PreContractExternalService, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.getExternalPreContractDetails = function() {
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
		$scope.preContractTOs = null;
		var req = {
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
			"status" : 1
		};
		PreContractExternalService.getExternalPreContractPopupOnLoad(req).then(function(data) {
			$scope.preContractTOs = [data.preContractTO];
			$scope.gridOptions.data = angular.copy($scope.preContractTOs);
			$scope.repeatPOPreContractTOs = [data.repeatPOPreContractTO];
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

	}, $scope.getBidAnalysisDetails = function(preContractId) {
		var bidDetails = $scope.getCompanyBidDetails(preContractId);
		bidDetails.then(function(data) {
			var popupDetails = PurchaseBidanalysisFactory.getCompanyBidDetails(data.preContractObj);
			popupDetails.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
		})

	},$scope.getCompanyBidDetails = function(preContractObj) {
		var onLoadReq = {
			"projId" : preContractObj.projId,
			"contractId" : preContractObj.id,
			"status" : 1

		};

		var bidAnalysisDefer = $q.defer();
		PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
			var returnPreContractObj = {
				"preContractObj" : angular.copy(data)
			};
			bidAnalysisDefer.resolve(returnPreContractObj);
		});
		return bidAnalysisDefer.promise;

	}, $scope.getApprovelDetailss = function(req) {
		var req = {
			"contractId" : req.id,
			"status" : 1
		}
		var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req,$scope.userMap);
		popupDetails.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
		})
	},
	$scope.addExternalRequest = function() {
		var resultData = $scope.getExternalPrecontractDetails();
		resultData.then(function(data) {
			var popupDetails = PreContractExternalFactory.procExternalApprovalPopUp(data.preContractObj);
			popupDetails.then(function(data) {
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
		})
	},

	$scope.getExternalPrecontractDetails = function() {
		var externalDefer = $q.defer();
		var onLoadReq = {
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"status" : 1
		};
		PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
			var returnObj = {
				"preContractObj" : angular.copy(data)
			};
			externalDefer.resolve(returnObj);
		});
		return externalDefer.promise;
	}
	$scope.getExternalPreContractDetails();
	$scope.$on("resetBidAnalysis", function() {
		$scope.preContractTOs = [];
	});
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name",},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", },
						{ field: 'code', displayName: "Contract ID", headerTooltip: "Contract ID", },
						{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description", },
						{ field: 'preContractType', displayName: "Contract Type", headerTooltip: "Contract Type", },
						{ field: 'preContractReqApprTO.reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", },
						{ field: 'preContractReqApprTO.reqUserLabelkeyTO.code', displayName: "Requestor", headerTooltip: "Requestor"},
						
						{ field: 'preContractReqApprTO.apprUserLabelkeyTO.code', displayName: "Approver", headerTooltip: "Approver", },
						{ name: 'Schedule Items', displayName: "Schedule Items", headerTooltip: "Schedule Items",cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.addExternalRequest(row.entity, req)" data-test="PurchaseOrders_stage2request_scheduleitemview">View</button>'},
						{ field: 'workFlowStatusTO.desc', displayName: "Response Status", headerTooltip: "Response Status", },
						{ name: 'Approver Details', displayName: "Approver Details", headerTooltip: "Approver Details", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.getApprovelDetailss(row.entity, req)" data-test="PurchaseOrders_stage2request_approverdetailsview">View</button>'},
						{ name: 'purchaseOrder', displayName: "Bid Analysis", headerTooltip: "Bid Analysis",cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.getBidAnalysisDetails(row.entity, req)"  data-test="PurchaseOrders_stage2request_bidanalysisview">View</button>'},
						{ name: 'Document',displayName: "Reference Documents", headerTooltip : "Reference Documents", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<button ng-click="grid.appScope.getRefDocument(row.entity, precontract)" class="btn btn-primary btn-sm" >Ref Document</button>'}
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement_purchaseOrderList_Stage2Request");
					$scope.getExternalPreContractDetails();
				}
		});
}]);
