'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetmortgage", {
		url : '/assetmortgage',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/mortgagepayments/mortgagepayments.html',
				controller : 'AssetMortgagePaymentController'
			}
		}
	})
}]).controller("AssetMortgagePaymentController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "MortgagePaymentFactory", "AssetRegisterService", "GenericAlertService", "assetidservice", "stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $location,ngDialog, MortgagePaymentFactory,AssetRegisterService, GenericAlertService,assetidservice, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	var selectedMortgagees = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;	
	$scope.getMortgage = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var getRegisterReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid
		};

		AssetRegisterService.getMortgageePayments(getRegisterReq).then(function(data) {
			$scope.mortgageValueDtlTOs = data.mortgageValueDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.mortgageValueDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Mortgagee(s) Details", "Error");
		});
	},

	$scope.mortgageRecordPopUpRowSelect = function(mortgagee) {
		if (mortgagee.selected) {
			selectedMortgagees.push(mortgagee);
		} else {
			selectedMortgagees.splice(selectedMortgagees.indexOf(mortgagee), 1);
		}
	},
	
	$scope.deleteMortgage = function() {
		if (selectedMortgagees.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedMortgagees = [];
		} else {
			angular.forEach(selectedMortgagees, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateRentalReq = {
					"mortagageePaymentsIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.mortgageePaymentsDeactive(deactivateRentalReq).then(function(data) {
					GenericAlertService.alertMessageModal('Mortgagee(s) Deactivated successfully', 'Info');
					$scope.getMortgage();
					selectedMortgagees = [];
				       });
				
				angular.forEach(selectedMortgagees, function(value, key) {
					$scope.selectedMortgagees.splice($scope.selectedMortgagees.indexOf(value), 1);
					selectedMortgagees = [];
					$scope.deactivateIds = [];
					
				}, function(error) {
					GenericAlertService.alertMessage('Mortgagee(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedMortgagees, function(value) {
					value.selected = false;
				})
			})

		}
	},
	
	$scope.addMortgageList = function(actionType) {
		
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		
		angular.forEach(selectedMortgagees, function(value, key) {
			value.selected=false;
		});
		if(selectedMortgagees.length >0 && actionType=="Add"){
			selectedMortgagees=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		if (actionType == 'Edit' && selectedMortgagees.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
			var mortgagePopUp = MortgagePaymentFactory.mortgagePopUpOnLoad(actionType, selectedMortgagees);
			mortgagePopUp.then(function(data) {				
				$scope.mortgageValueDtlTOs = data.mortgageValueDtlTOs;
				selectedMortgagees=[];
				$scope.getMortgage();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Mortgagee(s) details", 'Error');
			});		
	};
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.mortgageRecordPopUpRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
						{ field: 'effectiveDate', displayName: "Effective Date", headerTooltip: "Effective Date", groupingShowAggregationMenu: false},
						{ field: 'mortgageeName', displayName: "Mortgage Name", headerTooltip: "Mortgage Name", groupingShowAggregationMenu: false},
						{ field: 'mortgageeAdress', displayName: "Mortgage Address & Contact Details", headerTooltip: "Mortgage Address & Contact Details", groupingShowAggregationMenu: false},
						{ field: 'originalLoanPrincipalAmount', displayName: "Original Loan Principal Amount", headerTooltip: "Original Loan Principal Amount",groupingShowAggregationMenu: false},
						{ field: 'remainingLoanPrinicipalAmount', displayName: "Remaining Loan Principal Amount", headerTooltip: "Remaining Loan Principal Amount",groupingShowAggregationMenu: false},
						{ field: 'rateOfInterestPerAnnum', displayName: "Rate of Interest per Annum", headerTooltip: "Rate of Interest per Annum",groupingShowAggregationMenu: false},
						{ field: 'paymentCycle', displayName: "Payment Cycle", headerTooltip: "Payment Cycle", groupingShowAggregationMenu: false},
						{ field: 'paymentCycleDueDate', displayName: "Payment Cycle Due Date", headerTooltip: "Payment Cycle Due Date", groupingShowAggregationMenu: false},
						{ field: 'paymentAmountPerCycel', displayName: "Gross Payment Amount per Cycle", headerTooltip: "Gross Payment Amount per Cycle", groupingShowAggregationMenu: false},
						{ field: 'taxAmount', displayName: "Tax Amount", headerTooltip: "Tax Amount",groupingShowAggregationMenu: false},
						{ field: 'netAmount', displayName: "Net Amount", headerTooltip: "Net Amount",groupingShowAggregationMenu: false},
						{ field: 'receiverBank', displayName: "Receiver Bank", headerTooltip: "Receiver Bank",groupingShowAggregationMenu: false},
						{ field: 'receiverBankCode', displayName: "Receiver Bank Code #", headerTooltip: "Receiver Bank Code #", groupingShowAggregationMenu: false},
						{ field: 'receiverBankAC', displayName: "Receiver Bank AC #", headerTooltip: "Receiver Bank AC #",groupingShowAggregationMenu: false},
						{ field: 'accountStatus', displayName: "Account Status", headerTooltip: "Account Status", groupingShowAggregationMenu: false},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_Mortagage");
					$scope.getMortgage();
				}
			});
	$scope.$on("resetMortgageemAssetSales", function() {
		$scope.revenueSalesDtlTOs = [];
	});
	$rootScope.$on('mortgageefromassetsales', function (event) {
		
		if($rootScope.fixedAssetId){
			$scope.getMortgage();	
		}
	});
	
	
}]);
