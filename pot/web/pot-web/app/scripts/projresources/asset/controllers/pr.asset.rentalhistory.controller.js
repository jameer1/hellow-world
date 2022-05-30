'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetrentalhistory", {
		url : '/assetrentalhistory',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/rentalhsitory/rentalhistory.html',
				controller : 'AssetRentalHistoryController'
			}
		}
	})
}]).controller("AssetRentalHistoryController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "RentalHistoryFactory", "AssetRegisterService", "GenericAlertService", "assetidservice","EmpRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $location,ngDialog, RentalHistoryFactory,AssetRegisterService, GenericAlertService,assetidservice,EmpRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	var selectedRentalHistory = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;

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
			$scope.longTermLeasingDtlTOs = data.longTermLeasingDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.longTermLeasingDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Rental History(s) Details", "Error");
		});
	},
	$scope.rentalHistoryPopUpRowSelect = function(rentalHistory) {
		if (rentalHistory.selected) {
			selectedRentalHistory.push(rentalHistory);
		} else {
			selectedRentalHistory.splice(selectedRentalHistory.indexOf(rentalHistory), 1);
		}
	},
	$scope.deleteRentalHistory = function() {
		if (selectedRentalHistory.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedRentalHistory = [];
		} else {
			angular.forEach(selectedRentalHistory, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateRentalHistroyReq = {
					"longTermLeasingIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.deleteRentalHistory(deactivateRentalHistroyReq).then(function(data) {
					GenericAlertService.alertMessageModal('Rental History(s) deactivated successfully', 'Info');
					$scope.getRentalHistory();
					selectedRentalHistory = [];
				       });

				angular.forEach(selectedRentalHistory, function(value, key) {
					$scope.selectedRentalHistory.splice($scope.selectedRentalHistory.indexOf(value), 1);
					selectedRentalHistory = [];
					$scope.deactivateIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Rental History(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedRentalHistory, function(value) {
					value.selected = false;
				})
			})

		}
	},
	$scope.downloadRentalHistoryFile = function(rentalHistoryId,file_name) {
		//AssetRegisterService.downloadRentalHistoryFile(rentalHistoryId);
		let req = {
			"recordId" : rentalHistoryId,
			"category" : "Long Term Leasing/Rental History",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadRentalHistoryFile");
	}

	var assetLongTermLeaseGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		}

		AssetRegisterService.getShortTermRecords(assetLongTermLeaseGetReq).then(function(data) {
			$scope.stcorrReturnsDtlTOs = data.stcorrReturnsDtlTOs;
			/*$scope.gridOptions.data = angular.copy($scope.stcorrReturnsDtlTOs);*/
			console.log(' $scope.stcorrReturnsDtlTOs inside '+JSON.stringify($scope.stcorrReturnsDtlTOs))

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Short Term Records", "Error");
		});
		AssetRegisterService.getRentalHistory(assetLongTermLeaseGetReq).then(function(data) {
			$scope.longTermLeasingDtlTOs = data.longTermLeasingDtlTOs;
			for (let longTerm of $scope.longTermLeasingDtlTOs) {
				longTerm.maintenanceCharge = longTerm.maintenanceCharges.substr(12);
				longTerm.payCycle = longTerm.paymentCycle.substr(12);
			}
			$scope.gridOptions.data = angular.copy($scope.longTermLeasingDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Rental History(s) Details", "Error");
		});



	$scope.addRentalHistoryList = function(actionType) {

		var leaseActualfinishdate;
		var shorttermfinishdate;
		var bothcommandate;


		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var leaseactualfinishdate=true;




		angular.forEach(selectedRentalHistory, function(value, key) {
			value.selected=false;
		});

		if(selectedRentalHistory.length >0 && actionType=="Add"){
			selectedRentalHistory=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
		return;
		}
		if (actionType == 'Edit' && selectedRentalHistory.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}


/*		if(leaseActualfinishdate==null || leaseActualfinishdate == undefined){
				if(actionType=="Add"){
			leaseactualfinishdate=false;
			GenericAlertService.alertMessage("Rental History lease Actual FinshDate is Not Null value", 'Warning');
		}
	}*/
		console.log(' $scope.longTermLeasingDtlTOs '+JSON.stringify($scope.longTermLeasingDtlTOs))
		angular.forEach($scope.longTermLeasingDtlTOs, function(value, key) {
		//	value.selected;
		leaseActualfinishdate=value.leaseActualFinishFinshDate;
		});

		console.log(' $scope.stcorrReturnsDtlTOs '+JSON.stringify($scope.stcorrReturnsDtlTOs))
		angular.forEach($scope.stcorrReturnsDtlTOs, function(value, key) {
			console.log("value" + JSON.stringify(value))
			shorttermfinishdate=value.finishDate;
			});

		if($scope.stcorrReturnsDtlTOs.length==0){
			bothcommandate=leaseActualfinishdate
		
		}else if($scope.longTermLeasingDtlTOs.length==0) {
			bothcommandate=shorttermfinishdate
		}else{

		var d1 = new Date(shorttermfinishdate);
		var d2 = new Date(leaseActualfinishdate);
		if(d1 >= d2){
			bothcommandate=d1;
			console.log(' shorttermfinishdate if '+d1)
		}else if(d1 <= d2){
			bothcommandate=d2;
			console.log('leaseActualfinishdate'+ d2)
		}
	}
		console.log("bothbooleanvalue " + bothcommandate);

if(leaseactualfinishdate){
			var rentalhistorypopup = RentalHistoryFactory.rentalHistoryPopUpOnLoad(actionType, selectedRentalHistory,bothcommandate);
			rentalhistorypopup.then(function(data) {
				$scope.longTermLeasingDtlTOs = data.longTermLeasingDtlTOs;
				selectedRentalHistory=[];
				$scope.getRentalHistory();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Rental History(s) details", 'Error');
			});
}
leaseactualfinishdate=true;
	}

	$scope.$on("rentalHistoryFromassetsales", function() {
		$scope.longTermLeasingDtlTOs = [];
	});
	$rootScope.$on('rentalHistoryFromassetsales', function (event) {

		if($scope.fixedAssetid){
			$scope.getRentalHistory();
		}
	});

	var commonService = {};
	$scope.groupPage = function () {
		var group = commonService.grouping();
		group.then(function (data) {

		}, function (error) {
			GenericAlertService.alertMessage("Error", 'Info');
		})
	}
	$scope.sortPage = function () {
		var sort = commonService.sorting();
		sort.then(function (data) {

		}, function (error) {
			GenericAlertService.alertMessage("Error", 'Info');
		})
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rentalHistoryPopUpRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'leaseAgreement', displayName: "Lease Agreement", headerTooltip: "Lease Agreement"},
						{ field: 'leaseStart', displayName: "Lease Start", headerTooltip: "Lease Start", },
						{ field: 'leaseFinish', displayName: "Lease Finish", headerTooltip: "Lease Finish"},
						{ field: 'tenantid', displayName: "Tenant ID", headerTooltip: "Tenant ID",},
						{ field: 'tenantName', displayName: "Tenant Name", headerTooltip: "Tenant Name",},
						{ field: 'tenantAddress', displayName: "Tenant Address & Contact Details", headerTooltip: "Tenant Address & Contact Details",},
						{ field: 'paymentCycle', displayName: "Payment Cycle", headerTooltip: "Payment Cycle"},
						{ field: 'netRentAmountPerCycle', displayName: "Net Rent Amount per Cycle", headerTooltip: "Net Rent Amount per Cycle", },
						{ field: 'maintenanceCharges', displayName: "Maintenance Charges", headerTooltip: "Maintenance Charges"},
						{ field: 'assetMaintenanceCharges', displayName: "Asset Maintenance Charges", headerTooltip: "Asset Maintenance Charges",},
						{ field: 'taxableAmount', displayName: "Taxable Amount", headerTooltip: "Taxable Amount",},
						{ field: 'tax', displayName: "Tax%", headerTooltip: "Tax%",},
						{ field: 'taxAmount', displayName: "Tax Amount", headerTooltip: "Tax Amount"},
						{ field: 'grossRent', displayName: "Gross Rent", headerTooltip: "Gross Rent",},
						{ field: 'paymentCycleDueDate', displayName: "Payment Cycle Due Date", headerTooltip: "Payment Cycle Due Date"},
						{ field: 'leaseExtendedFinshDate', displayName: "Lease Extended Finish Date(if any)", headerTooltip: "Lease Extended Finish Date(if any)",},
						{ field: 'leaseActualFinishFinshDate', displayName: "Lease Actual Finish Date", headerTooltip: "Lease Actual Finish Date"},
						{ name: 'Lease Documents', displayName: "Lease Documents", headerTooltip: "Lease Documents",
						cellTemplate: '<div  ng-if="row.entity.leaseDocumentDetailsFileName" ng-click="grid.appScope.downloadRentalHistoryFile(row.entity.id, row.entity.leaseDocumentDetailsFileName)"  class="fa fa-download"></div>'},
						{ field: 'currentStatus', displayName: "Current Status", headerTooltip: "Current Status"},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_RentalHistory");
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});
	var grouppagepopup;
	var sortpagepopup;
	commonService.grouping = function () {
		var deferred = $q.defer();
		grouppagepopup = ngDialog.open({
			template: 'views/groupingsorting/resources/immovableassets/rentalhistorygroup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
	commonService.sorting = function () {
		var deferred = $q.defer();
		sortpagepopup = ngDialog.open({
			template: 'views/groupingsorting/resources/immovableassets/rentalhistorysort.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
	
}]);
