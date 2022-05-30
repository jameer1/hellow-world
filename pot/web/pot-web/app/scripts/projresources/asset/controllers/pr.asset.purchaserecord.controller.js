'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("assetpurchase", {
		url: '/assetpurchase',
		data: {
			roles: []
		},
		views: {
			'current@': {
				templateUrl: 'views/projresources/projassetreg/purchaserecords/purchaserecords.html',
				controller: 'AssetPurchaseRecordController'
			}
		}
	})
}]).controller("AssetPurchaseRecordController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "PurchaseRecordFactory", "AssetRegisterService", "GenericAlertService","EmpRegisterService","stylesService", "ngGridService", 
function ($rootScope, $scope, $q, $state, $location, ngDialog, PurchaseRecordFactory, AssetRegisterService, GenericAlertService,EmpRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.purchaseRecordData = [];
	$scope.purchaseRecordsmoreFlag = 0;
	var selectedPurchaseRecords = [];

	$scope.clickForwardPurchaseRecords = function (moreFlag) {
		if ($scope.purchaseRecordsmoreFlag < 1) {
			$scope.purchaseRecordsmoreFlag = moreFlag + 1;
		}
	}, $scope.clickBackwardPurchaseRecords = function (moreFlag) {
		if ($scope.purchaseRecordsmoreFlag > 0) {
			$scope.purchaseRecordsmoreFlag = moreFlag - 1;
		}
	}

	$scope.purchaseRecordPopUpRowSelect = function (purchaseRecord) {
		if (purchaseRecord.selected) {
			selectedPurchaseRecords.push(purchaseRecord);
		} else {
			selectedPurchaseRecords.pop(purchaseRecord);
		}
	},

		$scope.getPurchaseRecordOnLoad = function () {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var materialsOnLoadReq = {
				"status": 1,
				"fixedAssetid": $scope.fixedAssetid
			};
			AssetRegisterService.getPurchaseRecordOnLoad(materialsOnLoadReq).then(function (data) {
				$scope.fixedAssetAqulisitionRecordsDtlTOs = data.fixedAssetAqulisitionRecordsDtlTOs;
				$scope.gridOptions.data = angular.copy($scope.fixedAssetAqulisitionRecordsDtlTOs);
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Material details", "Error");
			});
		},
		//edit Purchase Record
		$scope.addPurchaseRecordList = function (actionType) {

			angular.forEach(selectedPurchaseRecords, function (value) {
				value.selected = false;
			});
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}

			if (actionType == 'Edit' && selectedPurchaseRecords.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
				return;
			}
			var purchaserecordpopup = PurchaseRecordFactory.purchaseRecordPopUpOnLoad(actionType, selectedPurchaseRecords);
			purchaserecordpopup.then(function (data) {
				$scope.getPurchaseRecordOnLoad();
				selectedPurchaseRecords = [];

			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while fetching Purchase Record details", 'Error');
			});

		},

		$scope.downloadRecordPurchaseFile = function(purchaseRecordId,fileName) {
			let req = {
				"recordId" : purchaseRecordId,
				"category" : "Purchase/Acquisition",
				"fileName" : fileName
			}
			EmpRegisterService.downloadRegisterDocs(req);
		},
		$scope.deletePurchaseRecord = function () {
			if (selectedPurchaseRecords.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
				return;
			}
			var deactivateIds = [];
			if ($scope.selectedAll) {
				$scope.selectedPurchaseRecords = [];
			} else {
				angular.forEach(selectedPurchaseRecords, function (value, key) {
					if (value.id) {
						deactivateIds.push(value.id);
					}
				});
				var purchaseDeactiveReq = {
					"purchaseRecordsIds": deactivateIds,
					"status": 2,
					"fixedAssetid": $scope.fixedAssetid
				}
				GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
					AssetRegisterService.deletePurchaseRecords(purchaseDeactiveReq).then(function (data) {
						GenericAlertService.alertMessageModal('Purchase Record(s) Deactivated successfully', 'Info');
						$scope.getPurchaseRecordOnLoad();
						selectedPurchaseRecords = [];
					});
					angular.forEach(selectedPurchaseRecords, function (value, key) {
						$scope.selectedPurchaseRecords.splice($scope.selectedPurchaseRecords.indexOf(value), 1);
						selectedPurchaseRecords = [];
						$scope.deactivateIds = [];
					}, function (error) {
						GenericAlertService.alertMessage('Purchase  Record(s) is/are  failed to Deactivate', "Error");
					});
				}, function (data) {
					angular.forEach(selectedPurchaseRecords, function (value) {
						value.selected = false;
					})
				})

			}
		},

		$scope.$on("defaultAssetPurchaseRecordTab", function () {
			$scope.purchaseRecordData = [];
			$scope.getPurchaseRecord();
		});
	$rootScope.$on('defaultAssetPurchaseRecord', function (event) {

		if ($rootScope.selectedEmployeeData) {
			$scope.getRevenueSale();
		}
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.purchaseRecordPopUpRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name:'select', displayName: "Select", headerTooltip: "Select", cellTemplate:linkCellTemplate,},
						{ field: 'dateOfPurchase', displayName: "Date of Purchase", headerTooltip: "Date of Purchase"},
						{ field: 'purchaseType', displayName: "Purchase Type", headerTooltip: "Purchase Type", },
						{ field: 'vendorName', displayName: "Vendor Name", headerTooltip: "Vendor Name"},
						{ field: 'vendorAddress', displayName: "Vendor Address", headerTooltip: "Vendor Address",},
						{ field: 'landSurveyLotDetails', displayName: "Land - Survey LOT Details", headerTooltip: "Land - Survey LOT Details",},
						
						{ field: 'lotIdentificationDetails', displayName: "LOT Identification Details", headerTooltip: "LOT Identification Details"},
						{ field: 'landSizeAndDimesions', displayName: "Land Size and Dimensions", headerTooltip: "Land Size and Dimensions", },
						{ field: 'structureDetails', displayName: "Structure Details", headerTooltip: "Structure Details"},
						{ field: 'plantAndEquipmentDetails', displayName: "Plant & Equipment Details", headerTooltip: "Plant & Equipment Details",},
						{ field: 'landRegisterOfficeDetails', displayName: "Land Register Office Details", headerTooltip: "Land Register Office Details",},
						{ field: 'landValuation', displayName: "Land Valuation", headerTooltip: "Land Valuation"},
						
						{ field: 'structureValuation', displayName: "Structure Valuation", headerTooltip: "Structure Valuation"},
						{ field: 'plantAndEquipmentValuation', displayName: "Plant & Equipment Valuation", headerTooltip: "Plant & Equipment Valuation", },
						{ field: 'totalValuation', displayName: "Total Valuation", headerTooltip: "Total Valuation"},
						{ field: 'totalPurchaseAmount', displayName: "Total Purchase Amount", headerTooltip: "Total Purchase Amount",},
						
						{ field: 'initialMarginAmountPaid', displayName: "Initial Margin Amount Paid", headerTooltip: "Initial Margin Amount Paid"},
						{ field: 'taxAmountWithHeld', displayName: "Tax Amount With Held", headerTooltip: "Tax Amount With Held",},
						{ field: 'loanAmount', displayName: "Loan Amount", headerTooltip: "Loan Amount"},
						{ field: 'loanAvailedFrom', displayName: "Loan Availed From", headerTooltip: "Loan Availed From",},
						{ field: 'loanPeriod', displayName: "Loan Period", headerTooltip: "Loan Period"},
						
						{ field: 'fixedRateOfInterest', displayName: "Fixed/Variable Rate", headerTooltip: "Fixed/Variable Rate"},
						{ field: 'annualRateOfInterest', displayName: "Annual RateOf Interest", headerTooltip: "Annual RateOf Interest",},
						{ field: 'buyerSolicitor', displayName: "Buyer Solicitor", headerTooltip: "Buyer Solicitor"},
						{ field: 'vendorSolicitor', displayName: "Vendor Solicitor", headerTooltip: "Vendor Solicitor",},
						{ name: 'Document', displayName: "Purchase Records(Upload & View)", headerTooltip: "Purchase Records(Upload & View)",cellClass:'justify-center',headerCellClass:'justify-center',
						cellTemplate:'<div ng-click="grid.appScope.downloadRecordPurchaseFile(row.entity.id,row.entity.purchaseRecordsDetailsFileName)" class="fa fa-download"></div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resource_ImmovableAssets_PurchaseAcquition");
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
					$scope.getPurchaseRecordOnLoad();
				}
			});
}]);
