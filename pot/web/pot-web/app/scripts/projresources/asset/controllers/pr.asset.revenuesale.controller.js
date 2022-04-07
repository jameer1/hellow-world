'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetrevenuesale", {
		url : '/assetrevenuesale',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/revenuesales/revenuesales.html',
				controller : 'AssetRevenueSaleController'
			}
		}
	})
}]).controller("AssetRevenueSaleController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "RevenueSaleFactory", "AssetRegisterService", "GenericAlertService", "assetidservice","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $location,ngDialog, RevenueSaleFactory,AssetRegisterService, GenericAlertService,assetidservice, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	var editRevenueSaleData = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;

	$scope.getRevenueSales = function() {
		
		var revenueSalesGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getRevenueSales(revenueSalesGetReq).then(function(data) {
			$scope.revenueSalesDtlTOs = data.revenueSalesDtlTOs;
		
             for(var assetR of $scope.revenueSalesDtlTOs){
	            assetR.Nopp= assetR.paymentTermsForRemainingAmount != "Single" ? "NA" :assetR.dueDateForSinglePayent ;
	            assetR.Pcri= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.paymentCycleForInstallments ;
	            assetR.Ddpc= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.dueDatePerCycle ;
	            assetR.Fid= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.firstInstallmentDueDate ;
	            assetR.Lidd= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.lastInstallmentsDueDate ;
	            assetR.Papc= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.principalAmountPerCycle ;
	            assetR.Roip= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.rateOfInterestPerAmount ;
	            assetR.Gadp= assetR.paymentTermsForRemainingAmount != "Installments" ? "NA" :assetR.grossAmountDuePerCycle ;
            }

			$scope.gridOptions.data = angular.copy($scope.revenueSalesDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Revenue Asset Sale details", "Error");
		});
	}
	
	
	
	$scope.revenueSaleRowSelect = function(revene) {
		if (revene.selected) {
			editRevenueSaleData.push(revene);
		} else {
			editRevenueSaleData.splice(editRevenueSaleData.indexOf(revene), 1);
		}
	},
	
	
	$scope.deleteRevenueSale = function() {
		if (editRevenueSaleData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var revenueSalesIds = [];
		if ($scope.selectedAll) {
			$scope.editRevenueSaleData = [];
		} else {
			angular.forEach(editRevenueSaleData, function(value, key) {
				if (value.id) {
					revenueSalesIds.push(value.id);
				}
			});
			var deactivateRevenueReq = {
					"revenueSalesIds" : revenueSalesIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.revenueSalesDeactive(deactivateRevenueReq).then(function(data) {
					GenericAlertService.alertMessageModal('Revenue Asset Sale(s) deactivated successfully', 'Info');
					$scope.getRevenueSales();
					editRevenueSaleData = [];
				       });				
				angular.forEach(editRevenueSaleData, function(value, key) {
					$scope.editRevenueSaleData.splice($scope.editRevenueSaleData.indexOf(value), 1);
					editRevenueSaleData = [];
					$scope.revenueSalesIds = [];					
				}, function(error) {
					GenericAlertService.alertMessage('Revenue Asset Sale(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(editRevenueSaleData, function(value) {
					value.selected = false;
				})
			})

		}
	},
	
	$scope.addRevenueSaleList = function(actionType) {
		
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		
		angular.forEach(editRevenueSaleData, function(value, key) {
			value.selected=false;
		});
		if(editRevenueSaleData.length >0 && actionType=="Add"){
			editRevenueSaleData=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		if (actionType == 'Edit' && editRevenueSaleData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
			var revenuesalepopup = RevenueSaleFactory.revenueSalePopUpOnLoad(actionType, editRevenueSaleData);
			revenuesalepopup.then(function(data) {				
				$scope.revenueSalesDtlTOs = data.revenueSalesDtlTOs;
				editRevenueSaleData=[];
				$scope.getRevenueSales();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Revenue Asset Sale Details", 'Error');
			});		
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.revenueSaleRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false },
						{ field: 'effectiveDate', displayName: "Effective Date", headerTooltip: "Effective Date", groupingShowAggregationMenu: false},
						{ field: 'buyerName', displayName: "Buyer Name", headerTooltip: "Buyer Name",groupingShowAggregationMenu: false },
						{ field: 'buyerAddress', displayName: "Buyer Address", headerTooltip: "Buyer Address", groupingShowAggregationMenu: false},
						{ field: 'totalSaleAmount', displayName: "Total Sale Amount", headerTooltip: "Total Sale Amount",groupingShowAggregationMenu: false},
						{ field: 'initialDepositAmount', displayName: "Initial Deposit Amount", headerTooltip: "Initial Deposit Amount",groupingShowAggregationMenu: false},
						{ field: 'remainingSaleAmount', displayName: "Remaining Sale Amount", headerTooltip: "Remaining Sale Amount",groupingShowAggregationMenu: false},
						{ field: 'paymentTermsForRemainingAmount', displayName: "Payment Terms for Remaining Amount", headerTooltip: "Payment Terms for Remaining Amount", groupingShowAggregationMenu: false},
						
						/*{ name: 'Due Date Single Paymen', displayName: "Due Date for Single Payment", headerTooltip: "Due Date for Single Payment",
						cellTemplate: '<div ng-if="status.paymentTermsForRemainingAmount ===Single">{{status.dueDateForSinglePayent}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Installments">NA</div>' },*/
						
						{ name: 'Nopp', displayName: "Number of Part Payments", headerTooltip: "Number of Part Payments",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="status.paymentTermsForRemainingAmount ===Single">{{row.entity.dueDateForSinglePayent}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Installments">NA</div>'},
*/						
						{ name: 'Pcri', displayName: "Payment Cycle for Installments", headerTooltip: "Payment Cycle for Installments",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="status.paymentTermsForRemainingAmount ===Installments">{{row.entity.paymentCycleForInstallments}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ name: 'Ddpc', displayName: "Due Date per Cycle", headerTooltip: "Due Date per Cycle",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="status.paymentTermsForRemainingAmount ===Installments">{{row.entity.dueDatePerCycle}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ name: 'Fid', displayName: "First Installment Due Date", headerTooltip: "First Installment Due Date",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="status.paymentTermsForRemainingAmount ===Installments">{{row.entity.firstInstallmentDueDate}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ name: 'Lidd', displayName: "Last Installment Due Date", headerTooltip: "Last Installment Due Date", groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="status.paymentTermsForRemainingAmount ===Installments">{{row.entity.lastInstallmentsDueDate}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ name: 'Papc', displayName: "Principal Amount per Cycle", headerTooltip: "Principal Amount per Cycle",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="row.entity.paymentTermsForRemainingAmount ===Installments">{{row.entity.principalAmountPerCycle}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ name: 'Roip', displayName: "Rate of Interest per Annum", headerTooltip: "Rate of Interest per Annum",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="row.entity.paymentTermsForRemainingAmount ===Installments">{{row.entity.rateOfInterestPerAmount}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ name: 'Gadp', displayName: "Gross Amount Due per Cycle", headerTooltip: "Gross Amount Due per Cycle",groupingShowAggregationMenu: false},
/*						cellTemplate: '<div ng-if="row.entity.paymentTermsForRemainingAmount ===Installments">{{row.entity.grossAmountDuePerCycle}}</div><div ng-if="status.paymentTermsForRemainingAmount ===Single">NA</div>'},
*/						
						{ field: 'payerBankName', displayName: "Payer Bank Name", headerTooltip: "Payer Bank Name",groupingShowAggregationMenu: false},
						{ field: 'payerBankCode', displayName: "Payer Bank code", headerTooltip: "Payer Bank code", groupingShowAggregationMenu: false},
						{ field: 'payerBankAccount', displayName: "Payer Bank Account", headerTooltip: "Payer Bank Account", groupingShowAggregationMenu: false},
						{ field: 'accountStatus', displayName: "Account Status", headerTooltip: "Account Status", groupingShowAggregationMenu: false},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_RevenueAssetSales");
					$scope.getRevenueSales();
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});

		$scope.$on("resetRevenueFromAssetSales", function() {
			$scope.revenueSalesDtlTOs = [];
		});
		$rootScope.$on('revenuefromassetsales', function (event) {
			
			if($rootScope.fixedAssetId){
				$scope.getRevenueSales();	
			}
		});
	
}]);
