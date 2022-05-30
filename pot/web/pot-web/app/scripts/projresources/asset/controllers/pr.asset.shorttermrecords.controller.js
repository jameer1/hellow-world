'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("shorttermrecords", {
		url : '/shorttermrecords',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/shorttermcasualoccupancyrecords/shorttermcasualrecords.html',
				controller : 'ShortTermCasualRecordsController'
			}
		}
	})
}]).controller("ShortTermCasualRecordsController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "AssetRegisterService", "GenericAlertService", "assetidservice", "ShortTermCasualRecordsFactory","EmpRegisterService", "stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $filter,ngDialog, AssetRegisterService, GenericAlertService,assetidservice, ShortTermCasualRecordsFactory,EmpRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	var selectedShortTermRecords = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate1 = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate1 = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	
	$scope.fromDate = $filter('date')(($scope.fromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')(($scope.toDate), "dd-MMM-yyyy");

	
	$scope.getShortTermRecords = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var shortTermRecordsGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};

		AssetRegisterService.getShortTermRecords(shortTermRecordsGetReq).then(function(data) {
			$scope.stcorrReturnsDtlTOs = data.stcorrReturnsDtlTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Short Term Records", "Error");
		});
	},
	
	$scope.shortTermPopUpRowSelect = function(shortTermRecord) {
		if (shortTermRecord.selected) {
			selectedShortTermRecords.push(shortTermRecord);
		} else {
			selectedShortTermRecords.splice(selectedShortTermRecords.indexOf(shortTermRecord), 1);
		}
	},
	
	
	$scope.getShortTermRecordsSearch = function(fDate,tDate) {
		if(fDate != null || fDate!= undefined ){
		 var fDate = new Date(fDate);
		}
		if(tDate != null || tDate!= undefined ){
		  var tDate = new Date(tDate);
		}
		
		if (fDate || tDate == null || fDate || tDate == undefined) {
			GenericAlertService.alertMessage("Please enter input dates", "Warning");
			return;
		}
		if (fDate > tDate) {
			GenericAlertService.alertMessage('From date can not greater than to date', 'Warning');
			return;
		}
		var shortTermSearchRecordsGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
				"fromDate" : fDate,
				"toDate" : tDate
			};

			AssetRegisterService.getShortTermSearchRecords(shortTermSearchRecordsGetReq).then(function(data) {
				$scope.stcorrReturnsDtlTOs = data.stcorrReturnsDtlTOs;
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Time Sheet details", "Error");
			});
	},
	
	$scope.deleteShortTermRecord = function() {
		if (selectedShortTermRecords.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedShortTermRecords = [];
		} else {
			angular.forEach(selectedShortTermRecords, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateShortTermRecordReq = {
					"stcorrReturnsIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.deactivateShorTermRecords(deactivateShortTermRecordReq).then(function(data) {
					$scope.getShortTermRecords();
					selectedShortTermRecords = [];
				       });				
				GenericAlertService.alertMessageModal('Short Term Record(s) deactivated successfully', 'Info');
				angular.forEach(selectedShortTermRecords, function(value, key) {
					$scope.selectedShortTermRecords.splice($scope.selectedShortTermRecords.indexOf(value), 1);
					selectedShortTermRecords = [];
					$scope.deactivateIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Short Term Record(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedShortTermRecords, function(value) {
					value.selected = false;
				})
			})

		}
	},
	
	$scope.downloadShortTermRecordFile = function(shortTermRecordId,file_name) {
		//AssetRegisterService.downloadShortTermRecordFile(shortTermRecordId);
		let req = {
			"recordId" : shortTermRecordId,
			"category" : "Short Term/Casual Occupancy Records and Rental Returns",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadShortTermRecordFile");
	}
	
	var assetLongTermLeaseGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		}
		
		AssetRegisterService.getShortTermRecords(assetLongTermLeaseGetReq).then(function(data) {
		
			$scope.stcorrReturnsDtlTOs = data.stcorrReturnsDtlTOs;
			for (let stcorrTerm of $scope.stcorrReturnsDtlTOs) {
				stcorrTerm.curStatus = stcorrTerm.currentStatus.substr(12);
			}
			console.log(' $scope.stcorrReturnsDtlTOs inside '+JSON.stringify($scope.stcorrReturnsDtlTOs))
			$scope.gridOptions.data = angular.copy($scope.stcorrReturnsDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Short Term Records", "Error");
		});
		AssetRegisterService.getRentalHistory(assetLongTermLeaseGetReq).then(function(data) {
			$scope.longTermLeasingDtlTOs = data.longTermLeasingDtlTOs;
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Rental History(s) Details", "Error");
		});
	
	$scope.addShortTermRecords = function(actionType){
		
		var leaseActualfinishdate;
		var shorttermfinishdate;
		var bothcommandate;
		var leaseactualfinishdate=true;
		
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		
		angular.forEach(selectedShortTermRecords, function(value, key) {
			value.selected=false;
		});
		if(selectedShortTermRecords.length >0 && actionType=="Add"){
			selectedShortTermRecords=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
		return;
		}
		if (actionType == 'Edit' && selectedShortTermRecords.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
		
		console.log(' $scope.longTermLeasingDtlTOs '+JSON.stringify($scope.longTermLeasingDtlTOs))
		angular.forEach($scope.longTermLeasingDtlTOs, function(value, key) {
		//	value.selected;
		leaseActualfinishdate=value.leaseActualFinishFinshDate;
		});	
		
		console.log(' $scope.stcorrReturnsDtlTOs '+JSON.stringify($scope.stcorrReturnsDtlTOs))
		angular.forEach($scope.stcorrReturnsDtlTOs, function(value, key) {
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
			
		}else if(d1 <= d2){
			bothcommandate=d2;
			
		}
		}
		
		if(leaseactualfinishdate){
			var shorttermpopup = ShortTermCasualRecordsFactory.shorttermPopUpOnLoad(actionType, selectedShortTermRecords,bothcommandate);			
			shorttermpopup.then(function(data) {
				$scope.stcorrReturnsDtlTOs = data.stcorrReturnsDtlTOs;
				selectedShortTermRecords=[];
				$scope.getShortTermRecords();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Short Term Records details", 'Error');
			});	
			selectedShortTermRecords=[];
		}
		leaseactualfinishdate=true;
	},
	
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'startDate', displayName: "Start Date", headerTooltip: "Start Date"},
						{ field: 'finishDate', displayName: "Finish Date", headerTooltip: "Finish Date", },
						{ field: 'tenantId', displayName: "Tenant ID", headerTooltip: "Tenant ID"},
						{ field: 'tenantName', displayName: "Tenant Name", headerTooltip: "Tenant Name",},
						{ field: 'tenantRegisteredAddress', displayName: "Tenant Registered Address", headerTooltip: "Tenant Registered Address",},
						{ field: 'emailAddrees', displayName: "Email Address", headerTooltip: "Email Address",},
						{ field: 'tenantPhoneNumber', displayName: "Tenant Phone Number", headerTooltip: "Tenant Phone Number"},
						{ field: 'checkIn', displayName: "Check In(Date)", headerTooltip: "Check In(Date)", },
						{ field: 'checkOut', displayName: "Check Out(Date)", headerTooltip: "Check Out(Date)"},
						{ field: 'noOfDays', displayName: "Number Of Days", headerTooltip: "Number Of Days",},
						{ field: 'dailyNetRent', displayName: "Daily Net Rent", headerTooltip: "Daily Net Rent",},
						{ field: 'tax', displayName: "Tax %", headerTooltip: "Tax %",},
						{ field: 'rentAmountReceivable', displayName: "Rent Amount Receivable", headerTooltip: "Rent Amount Receivable",},
						{ field: 'taxAmount', displayName: "Tax Amount Receivable", headerTooltip: "Tax Amount Receivable",},
						{ field: 'grossAmount', displayName: "Gross Rent Amount Receivable", headerTooltip: "Gross Rent Amount Receivable"},
						{ field: 'advancePaid', displayName: "Advance Rent Paid", headerTooltip: "Advance Rent Paid", },
						{ field: 'subsequentRentalRecepits', displayName: "Subsequent Rent Paid", headerTooltip: "Subsequent Rent Paid"},
						{ field: 'refundofRemainingAdvanceamount', displayName: "Refund Of Remaining Advance Amount", headerTooltip: "Refund Of Remaining Advance Amount",},
						{ field: 'netTaxAmountReceived', displayName: "Net Tax Amount Received", headerTooltip: "Net Tax Amount Received",},
						{ field: 'netAmountOfRentRecived', displayName: "Net Amount Of Rent Received", headerTooltip: "Net Amount Of Rent Received",},
						{ name: 'Tenant Records', displayName: "Tenant Records if any", headerTooltip: "Tenant Records if any",
						cellTemplate: '<div ng-if="row.entity.tenantRecordDetailsFileName" ng-click="grid.appScope.downloadShortTermRecordFile(row.entity.id, row.entity.tenantRecordDetailsFileName)" class="fa fa-download" ></div>'},
						{ field: 'curStatus', displayName: "Current Status", headerTooltip: "Current Status",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_ShortTermRecords");
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
				}
			});
			var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.shortTermPopUpRowSelect(row.entity)">';
	$scope.$on("resetShortTermFromAssetSales", function() {
		$scope.saleRecordDataTOs = [];
	});
	$rootScope.$on('shortTermFromassetsales', function (event) {
		
		if($scope.fixedAssetid){
			$scope.getSaleRecord();	
		}
	});

}]);