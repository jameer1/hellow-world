'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("longtermlease", {
		url : '/longtermlease',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/longtermleaserental/longtermlease.html',
				controller : 'LongTermLeaseRentalController'
			}
		}
	})
}]).controller("LongTermLeaseRentalController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "AssetRegisterService", "LongTermLeaseFactory", "GenericAlertService", "assetidservice","EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog,AssetRegisterService,LongTermLeaseFactory, GenericAlertService,assetidservice,EmpRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	var selectedLongTermLease = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;
	var longTermRecords = [];
	var rentalHistoryRecords = [];
 $scope.longTermLeaseLastRecord1=[];

	var longtermleaseValueGetReq={
		"status" : 1,
		"fixedAssetid" : $scope.fixedAssetid
	};

	AssetRegisterService.getLongtermleaselastrecord(longtermleaseValueGetReq).then(function(data) {

		if (data.longTermLeasingDtlTOs.length == 0 ) {
				$scope.longTermLeaselastrecord1=JSON.stringify(data.longTermLeasingDtlTOs);
				
		}else{
		$scope.longTermLeaselastrecord1 = data.longTermLeasingDtlTOs;
		
		}

	}, function(error) {
		GenericAlertService.alertMessage("Error occured while fetching LongTermLease Value/Revenue(s) details", "Error");
	});

	var longtermleasevaluereq={
		"status" : 1,
		"fixedAssetid" : $scope.fixedAssetid
	};

	AssetRegisterService.getLongTermLeaseActualRetruns(longtermleasevaluereq).then(function(data) {

		if (data.longTermLeaseActualRetrunsDtlTOs.length == 0 ) {
				$scope.longTermLeaselastrecordreq=JSON.stringify(data.longTermLeaseActualRetrunsDtlTOs);
				
		}else{
	$scope.longTermLeaselastrecordreq = data.longTermLeaseActualRetrunsDtlTOs;
	$scope.gridOptions.data = angular.copy($scope.longTermLeaselastrecordreq);
		}

	}, function(error) {
		GenericAlertService.alertMessage("Error occured while fetching LongTermLease Value/Revenue(s) details", "Error");
	});

	$scope.getLongTermLeaseActualRetruns = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var longTermLeaseGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getLongTermLeaseActualRetruns(longTermLeaseGetReq).then(function(data) {
			$scope.longTermLeaseActualRetrunsDtlTOs = data.longTermLeaseActualRetrunsDtlTOs;
			if($scope.longTermLeaseActualRetrunsDtlTOs.length > 0){
				longTermRecords.push(angular.copy($scope.longTermLeaseActualRetrunsDtlTOs[$scope.longTermLeaseActualRetrunsDtlTOs.length-1]));
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  Lease:Rental-Actual Returns Details", "Error");
		});
	},

	$scope.getRentalHistory = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var assetLongTermLeaseGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getRentalHistory(assetLongTermLeaseGetReq).then(function(data) {
			console.log(data);
			$scope.longTermLeasingDtlTOs = data.longTermLeasingDtlTOs;
			 if($scope.longTermLeasingDtlTOs.length >0){
				 rentalHistoryRecords.push(angular.copy($scope.longTermLeasingDtlTOs[$scope.longTermLeasingDtlTOs.length-1]));
			}

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Rental History(s) details", "Error");
		});
	},

	$scope.longTermLeasePopUpRowSelect = function(longTerm) {
		if (longTerm.selected) {
			selectedLongTermLease.push(longTerm);
		} else {
			selectedLongTermLease.splice(selectedLongTermLease.indexOf(longTerm), 1);
		}
	},

		$scope.createLongTermLease = function(actionType) {

				if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
					GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
					return;
				}

				angular.forEach(selectedLongTermLease, function(value, key) {
					value.selected=false;
				});
				if(selectedLongTermLease.length >0 && actionType=="Add"){
					selectedLongTermLease=[];
					GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
				return;
				}
				if (actionType == 'Edit' && selectedLongTermLease.length <= 0) {
					GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
					return;
				}
console.log('$scope.longTermLeaselastrecord1'+JSON.stringify($scope.longTermLeaselastrecord1));

					var longTermLeaseopup = LongTermLeaseFactory.longTermLeasePopUpOnLoad(actionType,selectedLongTermLease,longTermRecords,rentalHistoryRecords,$scope.longTermLeaselastrecord1,$scope.longTermLeaselastrecordreq);
					longTermLeaseopup.then(function(data) {
						$scope.longTermLeaseActualRetrunsDtlTOs = data.longTermLeaseActualRetrunsDtlTOs;
						selectedLongTermLease=[];
						$scope.getLongTermLeaseActualRetruns();
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching Lease:Rental-Actual Returns Details", 'Error');
					});
			}


	$scope.deleteLongTermLease = function() {
		if (selectedLongTermLease.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedLongTermLease = [];
		} else {
			angular.forEach(selectedLongTermLease, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateLongTermLeaseReq = {
					"longTermLeaseAcutalIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.deleteLongTermLeaseRevenue(deactivateLongTermLeaseReq).then(function(data) {
					GenericAlertService.alertMessageModal('Long Term Lease Revenue Record(s) deactivated successfully', 'Info');
					$scope.getLongTermLeaseActualRetruns();
					selectedLongTermLease = [];
				       });
				angular.forEach(selectedLongTermLease, function(value, key) {
					$scope.selectedLongTermLease.splice($scope.selectedLongTermLease.indexOf(value), 1);
					selectedLongTermLease = [];
					$scope.deactivateIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Long Term Lease Revenue Record(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedLongTermLease, function(value) {
					value.selected = false;
				})
			})

		}
	},

	$scope.downloadLongTermLeaseActualRetrunsFile = function(longTermLeaseActualId,file_name) {
		//AssetRegisterService.downloadLongTermLeaseActualRetrunsFile(longTermLeaseActualId); // this is used for aws
		// the below lines of code for downloading the file from Local
		let req = {
			"recordId" : longTermLeaseActualId,
			"category" : "Long Term Lease:Rental/Revenue-Actual Revenue",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadLongTermLeaseActualRetrunsFile function");
	},


	$scope.$on("longTermLeaseActualRetruns", function() {
		$scope.longTermLeaseActualRetrunsDtlTOs = [];
	});
	$rootScope.$on('longtermleaseactualretruns', function (event) {

		if($rootScope.fixedAssetId){
			$scope.getLongTermLeaseActualRetruns();
		}
	});
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.longTermLeasePopUpRowSelect(row.entity)">';
$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'date', displayName: "Date", headerTooltip: "Date"},
						{ field: 'lease', displayName: "Lease Sr No.", headerTooltip: "Lease Sr No.", },
						{ field: 'leaseAgreement', displayName: "Lease agreement #", headerTooltip: "Lease agreement #"},
						{ field: 'tenantId', displayName: "Tenant ID", headerTooltip: "Tenant ID",},
						{ field: 'tenantName', displayName: "Tenant Name", headerTooltip: "Tenant Name",},
						{ field: 'modeOfPayment', displayName: "Mode of Payment", headerTooltip: "Mode of Payment",},
						{ field: 'receiverBankName', displayName: "Receiver Bank Name", headerTooltip: "Receiver Bank Name",},
						{ field: 'receiverBankCode', displayName: "Receiver Bank Code", headerTooltip: "Receiver Bank Code"},
						{ field: 'receiverBankAC', displayName: "Receiver Bank AC", headerTooltip: "Receiver Bank AC"},
						{ field: 'transferReceiptNo', displayName: "Transfer Receipt No", headerTooltip: "Transfer Receipt No", },
						{ field: 'rentalAmountReceived', displayName: "Net Rental Amount Received", headerTooltip: "Net Rental Amount Received"},
						{ field: 'taxAmountReceived', displayName: "Tax amount Received", headerTooltip: "Tax amount Received",},
						{ field: 'cumulativeNetRent', displayName: "Cumulative Net Rent Amount Received -Tenant wise", headerTooltip: "Cumulative Net Rent Amount Received -Tenant wise",},
						{ field: 'cumulativeTaxAmount', displayName: "Cumulative Tax Amount Received-Tenant wise", headerTooltip: "Cumulative Tax Amount Received-Tenant wise",},
						{ field: 'cumulativeNetRentTenant', displayName: "Cumulative Rent amount due from The Tenant", headerTooltip: "Cumulative Rent amount due from The Tenant",},
						{ field: 'cumulativeTaxAmountTenant', displayName: "Cumulative Tax amount due from The Tenant", headerTooltip: "Cumulative Tax amount due from The Tenant"},
						{ field: 'shortFallRent', displayName: "Short fall in net Rent Receipts from Tenant", headerTooltip: "Short fall in net Rent Receipts from Tenant", },
						{ field: 'shortFallTax', displayName: "Short fall in Tax Receipts From Tenant", headerTooltip: "Short fall in Tax Receipts From Tenant"},
						{ name: 'Upload Documents', displayName: "Upload Money Transaction Documents", headerTooltip: "Upload Money Transaction Documents", cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate:'<div ng-click="grid.appScope.downloadLongTermLeaseActualRetrunsFile(row.entity.id, row.entity.uploadMoneyDocumentFileName)" ng-if="row.entity.uploadMoneyDocumentFileName" class="fa fa-download">{{uploadMoneyDocumentFileName}}</div>'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_LongTermLease");
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});

}]);
