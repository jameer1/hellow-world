'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("pocontractlist", {
		url : '/pocontractlist',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/purchaseprecontractdocuments.html',
			}
		}
	})
}]).controller("PurchaseContractListController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "POReferenceDocumentsFactory", "PurchaseDocumentsFactory", "GenericAlertService", "PurchaseOrderService", "PreContractInternalService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, POReferenceDocumentsFactory,PurchaseDocumentsFactory, GenericAlertService, PurchaseOrderService, PreContractInternalService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.getInternalPreContracts = function() {
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
		var req = {
			"preContractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
			"contractCmpId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.id,
			"projId" : $rootScope.selectedPurchaseOrderData.projId,
		}
		PurchaseOrderService.getPurchaseOrderDetails(req).then(function(data) {
			$scope.userProjMap = data.userProjMap;
			$scope.preContractTO = [data.preContractTO];
			$scope.gridOptions.data = angular.copy($scope.preContractTO);
			if ($scope.preContractTOs.length <= 0) {
				GenericAlertService.alertMessage("Precontracts are not avialable for selected purchase order id", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});
		$scope.getRefDocument = function(precontract) {
			var refreq = POReferenceDocumentsFactory.referenceDocumentDetails(precontract);
			refreq.then(function(data) {
				$scope.refdocumentslist = data.preContractDocsTOs;
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
			})

		}

	}, $scope.getInternalPreContracts();
	$scope.$on("resetPreContactList", function() {
		$scope.preContractTO = [];
	});
	
$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS",},
						{ field: 'projName', displayName: "Project", headerTooltip: "Project"},
						{ field: 'code', displayName: "PreContract ID", headerTooltip: "PreContract ID", },
						{ field: 'preContractType', displayName: "Contract Type", headerTooltip: "Contract Type", },
						{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description", },
						{ field: 'contractStageStatus', displayName: "PO Issued", headerTooltip: "PO Issued", },
						{ field: 'purchaseOrderStatus', displayName: "PostContract<br>(PurchaseOrderStatus)", headerTooltip: "PostContract<br>(PurchaseOrderStatus)"},
						{ name: 'Document',displayName: "Reference Documents", headerTooltip : "Reference Documents", cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<div ng-click="grid.appScope.getRefDocument(row.entity, precontract)" data-test="PurchaseOrders_precontractslistandDocuments_Ref" class="btn btn-primary btn-sm" >Ref Document</div>'}
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Request&Approvals_Request_EmployeeTransferRequest");
					$scope.getInternalPreContracts();
				}
			});
}]);