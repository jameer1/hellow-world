'use strict';


app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetregister", {
		url : '/assetregister',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projassetreg/assetdetails/assetdetails.html',
				controller : 'AssetRegController'
			}
		}
	})
}])

.filter('trimParent', function () {
	return function (input) {
		return input.substr( 0, input.indexOf('-', (input.indexOf('-') + 1)) );
	}
})

.controller('AssetRegController', ["$rootScope", "$scope", "$state", "$filter", "$location", "$q", "ngDialog", "EpsService", "blockUI", "ProjEmpClassService", "GenericAlertService", "UserEPSProjectService", "AssetRegisterService", "EpsProjectSelectFactory", "ProjectSettingsService", "SubAssetDetailsFactory", "AssetDetailsFactory", "AssetCategorySelectFactory", "ProfitCentrePopUpFactory", "assetidservice", "CountryService", "TreeService", "AssetCompanySelectFactory", function($rootScope, $scope, $state, $filter,$location, $q, ngDialog, EpsService,
 blockUI, ProjEmpClassService, GenericAlertService, UserEPSProjectService,AssetRegisterService,
 EpsProjectSelectFactory,ProjectSettingsService,SubAssetDetailsFactory, AssetDetailsFactory,
 AssetCategorySelectFactory,ProfitCentrePopUpFactory,assetidservice,CountryService, TreeService,AssetCompanySelectFactory) {

	$scope.assetData = [];
	var deleteTreeData = [];
	$scope.assetDetailsTabs = [];
	$scope.moreFlag = 0;
	$scope.currentTab = null;
    $scope.rentalValueDtlTOs = [];
    $scope.revenueSalesDtlTOs =[];
    $scope.mortgageValueDtlTOs = [];
	$scope.editing = false;

	//asset
	$scope.provisionMap = [];
	$scope.provisionTOs = [];

	$scope.countryProvision = {
		"id" : null,
		"code" : null,
		"countryLabelKeyTO" : {
			"id" : null,
			"code" : null,
			"name" : null
		},
		"proisionLabelKeyTO" : {
			"id" : null,
			"code" : null,
			"name" : null
		},

	};
	$scope.assetValues = {};
	$scope.countries = [];
	$scope.currencies = [];
	$scope.timeZones = [];
	$scope.provisions = [];
	$scope.countryMap = [];
	$scope.assetData = [];
	var editAssetDetails = [];
	$scope.assetdetailsmoreFlag = 0;
	$scope.clickForwardAssetDetails = function(moreFlag) {
		if ($scope.assetdetailsmoreFlag < 1) {
			$scope.assetdetailsmoreFlag = moreFlag + 1;
		}
	}
	$scope.clickBackwardAssetDetails = function(moreFlag) {
		if ($scope.assetdetailsmoreFlag > 0) {
			$scope.assetdetailsmoreFlag = moreFlag - 1;
		}
	}
	$scope.setSelected = function(row) {
		$scope.selectedRow = row;
	}
	$scope.assetDetailsTabs = [ {
		title : 'Purchase/Acquisition Records',
		resetMethod : 'defaultAssetPurchaseRecord',
		SelenumLocator: 'ImmovableAssets_PurchaseAcquisitionRecords_Tab_Click',
		url : 'views/projresources/projassetreg/purchaserecords/purchaserecords.html'
	}, {
		title : 'Rental Value/Revenue',
		resetMethod : 'rentalfromassetsales',
		SelenumLocator: 'ImmovableAssets_RentalValueRevenue_Tab_Click',
		url : 'views/projresources/projassetreg/rentalvalue/rentalvalue.html',
	}, {
		title : 'Asset Sale Records',
		resetMethod : 'saleRecordFromassetsales',
		SelenumLocator: 'ImmovableAssets_AssetSaleRecords_Tab_Click',
		url : 'views/projresources/projassetreg/salerecords/salerecords.html'
	}, {
		title : 'Mortgage Payments',
		resetMethod : 'mortgageefromassetsales',
		SelenumLocator: 'ImmovableAssets_MortgaggePayments_Tab_Click',
		url : 'views/projresources/projassetreg/mortgagepayments/mortgagepayments.html'
	}, {
		title : 'Revenue from Asset Sales',
		resetMethod : 'revenuefromassetsales',
		SelenumLocator: 'ImmovableAssets_RevenuefromAssetSales_Tab_Click',
		url : 'views/projresources/projassetreg/revenuesales/revenuesales.html'
	}, {
		title : ' Term Leasing/Rental History',
		resetMethod : 'rentalHistoryFromassetsales',
		SelenumLocator: 'ImmovableAssets_TermLeasingRentalHistory_Tab_Click',
		url : 'views/projresources/projassetreg/rentalhsitory/rentalhistory.html',
	}, {
		title : 'Short Term/Casual Occupancy Records & Rental Returns',
		SelenumLocator: 'ImmovableAssets_ShortTermCasualOccupancyRecords_Tab_Click',
		url : 'views/projresources/projassetreg/shorttermcasualoccupancyrecords/shorttermcasualrecords.html',
	}, {
		title : ' Term Lease:Rental/Revenue-Actual Returns',
		resetMethod : 'longtermleaseactualretruns',
		SelenumLocator: 'ImmovableAssets_TermLeaseRentalRevenueActualReturns_Tab_Click',
		url : 'views/projresources/projassetreg/longtermleaserental/longtermlease.html'
	}, {
		title : 'Car Parking - Actual Revenue',
		SelenumLocator: 'ImmovableAssets_CarParkingAndTollCollections_ActualRevenue_Tab_Click',
		url : 'views/projresources/projassetreg/carparkingandtollcollections/carparkingtollcollections.html'
	}, {
		title : 'Toll Collections - Actual Revenue',
		SelenumLocator: 'ImmovableAssets_TollCollections_ActualRevenue_Tab_Click',
		url : 'views/projresources/projassetreg/tollcollections/tollcollections.html'
	},  {
		title : 'Occupancy/Utilisation Records-Statistics',
		SelenumLocator: 'ImmovableAssets_OccupancyUtilisationRecords_Statistics_Tab_Click',
		url : 'views/projresources/projassetreg/occupancyutilisationrecords/occupancyutilisation.html'
	}, {
		title : 'Periodical and Schedule MaintenanceRecords',
		SelenumLocator: 'ImmovableAssets_PeriodicalandScheduleMaintenanceRecords_Tab_Click',
		url : 'views/projresources/projassetreg/periodicalandschedulerecords/periodicalrecords.html'
	},{
		title : 'Repairs and NonSchedule',
		SelenumLocator: 'ImmovableAssets_RepairsandNonSchedule_Tab_Click',
		url : 'views/projresources/projassetreg/repairsandnonschedule/repairsandnonschedule.html'
	},{
		title : 'Asset Life Status',
		SelenumLocator: 'ImmovableAssets_AssetLifeStatus_Tab_Click',
		url : 'views/projresources/projassetreg/assetlifestatus/assetlifestatus.html'
	},{
		title : 'Asset Cost and Value Status',
		SelenumLocator: 'ImmovableAssets_AssetCostandValueStatus_Tab_Click',
		url : 'views/projresources/projassetreg/assetcostandvluestatus/assetcostvaluestatus.html'
	}];
	$scope.resetData = function() {
		$scope.searchProject = {};
		$scope.assetData = [];
		$scope.profitCentre=null;
		$scope.countries=null;
		$scope.provisionName=null;
		// $rootScope.selectedAssetData = null;
		$scope.getSearchAssetRegisters();
		// $scope.onClickTab($scope.assetDetailsTabs[0]);
		$scope.getCountries();
	}

	$scope.openSettings = function(row, projObj) {
		$scope.fixedAssetid = row;
		$scope.setSelected(projObj);
		$scope.onClickTab($scope.assetDetailsTabs[0]);
		$scope.getPurchaseRecordOnLoad();

	}

	/*---------Setting Tabs----------*/
	$scope.editing = false;
	$scope.onClickTab = function(innerTab) {
		$scope.editing = true;
		if ($scope.assetDetailsTabs[0].title == innerTab.title) {
			$scope.getPurchaseRecordOnLoad();
		} else if ($scope.assetDetailsTabs[1].title === innerTab.title) {
			$scope.getRentalValue();
		} else if ($scope.assetDetailsTabs[2].title === innerTab.title) {
			$scope.getSalesRecord();
		} else if ($scope.assetDetailsTabs[3].title === innerTab.title) {
			$scope.getMortgageePayments();
		} else if ($scope.assetDetailsTabs[4].title === innerTab.title) {
			$scope.getRevenueSales();
		} else if ($scope.assetDetailsTabs[5].title === innerTab.title) {
			$scope.getRentalHistory();
		} else if ($scope.assetDetailsTabs[6].title === innerTab.title) {
			$scope.getShortTermRecords();
		} else if ($scope.assetDetailsTabs[7].title === innerTab.title) {
			$scope.getLongTermLeaseActualRetruns();
		} else if ($scope.assetDetailsTabs[8].title === innerTab.title) {
			$scope.carParkingTollCollectionsRecords();
		} else if ($scope.assetDetailsTabs[9].title === innerTab.title) {
			$scope.TollCollectionsRecords();
		} else if ($scope.assetDetailsTabs[10].title === innerTab.title) {
			console.log($scope.assetDetailsTabs[10].title)
			$scope.getOccupancyUtilityRecords();
		} else if ($scope.assetDetailsTabs[11].title === innerTab.title) {
			$scope.getPeriodicalRecords();
		} else if ($scope.assetDetailsTabs[12].title === innerTab.title) {
			$scope.getPlantRepairRecordsOnLoad();
		} else if ($scope.assetDetailsTabs[13].title === innerTab.title) {
			$scope.getAssetLifeStatus();
		} else if ($scope.assetDetailsTabs[14].title === innerTab.title) {
			$scope.getAssetCostValueStatus();
		}
		$scope.currentTab = innerTab.url;
	}

/* =========assetDetails tabs======= */
$scope.currentAssetDetailsTab = 'views/projresources/projassetreg/assetdetails/assetdetails.html';
$scope.onClickAssetDetailsTab = function(masterTabs) {
	$scope.currentAssetDetailsTab = masterTabs.url;
	console.log($scope.currentAssetDetailsTab)
}
$scope.isActiveAssetDetailsTab = function(masterTabsUrl) {
	return masterTabsUrl == $scope.currentAssetDetailsTab;
}

$scope.go = function(asset,indexValue) {
	$rootScope.selectedAssetData = asset;
	$scope.moreFlag = 0;
	$scope.setSelected(indexValue);
	$scope.onClickTab($scope.assetDetailsTabs[0]);
	$rootScope.$broadcast('defaultAssetPurchaseRecordTab');

};
/*---------Time Sheet Tabs----------*/
$scope.onClickTimeTab = function(timeTabs) {
	$scope.currentRevenueSalesTab = timeTabs.url;
}, $scope.isActiveTimeTab = function(timeTaburlValue) {
	return timeTaburlValue == $scope.currentRevenueSalesTab;
},



	/* =========Inner tabs======= */
	$scope.currentTab = 'views/projresources/projassetreg/purchaserecords/purchaserecords.html';
	/* $scope.onClickTab = function(assetDetailsTabs) {
		$scope.currentTab = assetDetailsTabs.url;
	}, */
	$scope.isActiveTab = function(assetDetailsTabsUrl) {
		return assetDetailsTabsUrl == $scope.currentTab;
	}, $scope.moreFlag = 0;
	$scope.clickMore = function(moreFlag1) {
		if ($scope.moreFlag > 0) {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickTab($scope.assetDetailsTabs[0]);
	}, $scope.clickMore1 = function(moreFlag1) {
		if ($scope.moreFlag < 1) {
			$scope.moreFlag = moreFlag1 + 1;
		} else {
			$scope.moreFlag = moreFlag1 - 1;
		}
		$scope.onClickTab($scope.assetDetailsTabs[6]);
	},$scope.clickMore2 = function(moreFlag2) {
		if ($scope.moreFlag < 2) {
			$scope.moreFlag = moreFlag2 + 1;
		} else {
			$scope.moreFlag = moreFlag2 - 1;
		}
		$scope.onClickTab($scope.assetDetailsTabs[10]);
	},

	//Purchase Record Get Values 1*/
	$scope.getPurchaseRecordOnLoad = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var materialsOnLoadReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid
		};
		AssetRegisterService.getPurchaseRecordOnLoad(materialsOnLoadReq).then(function(data) {
			console.log(data);
			$scope.fixedAssetAqulisitionRecordsDtlTOs = data.fixedAssetAqulisitionRecordsDtlTOs;

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Purrchase Details", "Error");
		});
	},

	/* Asset Sales Rental Value 2*/
	$scope.getRentalValue = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var rentalValueGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid,
		};
		AssetRegisterService.getRentalValue(rentalValueGetReq).then(function(data) {
			$scope.rentalValueDtlTOs = data.rentalValueDtlTOs;
            $scope.maxDate = null;
	        var dates = [];
	        angular.forEach($scope.rentalValueDtlTOs, function(value, key) {		
	        dates.push(value.effectiveDate);
	        var max = dates[0],
                min = dates[0];
            dates.forEach(function(date) {
            max = new Date(date) > new Date(max)? date: max;
            $scope.maxDate = new Date(date) > new Date(max)? date: max;
            });									
	    });
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Rental Value Details", "Error");
		});
	},

	// Get Asset Status and using data in other tab
	$scope.getAssetStatus = function(item){
		var rentalValueGetReq = {
			"status" : 1,
			"fixedAssetid" : item.fixedAssetid,
		};

		let assetStatus = [];
		AssetRegisterService.getRentalValue(rentalValueGetReq).then(function(data) {
			$scope.rentalValueDtlTOs = data.rentalValueDtlTOs;
			assetStatus.push(angular.copy($scope.rentalValueDtlTOs[$scope.rentalValueDtlTOs.length-1]));
			item.assetCurrentLifeSataus=assetStatus[0].assetCurrentLifeSataus;
			item.owenerShipStatus=assetStatus[0].owenerShipStatus;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while fetching Asset Status Details", "Error");
		});
	}


	   /* Asset Sales Record Value 3*/
		$scope.getSalesRecord=function(){
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var salesRecordsGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.getSalesRecord(salesRecordsGetReq).then(function(data) {
				$scope.salesRecordsDtlTOs = data.salesRecordsDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Asset Sale Record Details", "Error");
			});
		},

		/* Get Mortgagee Records 4*/

		$scope.getMortgageePayments=function(){
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var mortgageePaymentsGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.getMortgageePayments(mortgageePaymentsGetReq).then(function(data) {
				$scope.mortgageValueDtlTOs = data.mortgageValueDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Mortgagee Details", "Error");
			});
		},


	/* Get Revenue Sales 5*/
		$scope.getRevenueSales = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var revenueSalesGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};
			AssetRegisterService.getRevenueSales(revenueSalesGetReq).then(function(data) {
				$scope.revenueSalesDtlTOs = data.revenueSalesDtlTOs;
				}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Revenue Details", "Error");
			});
		},

		/* Get Rental History  6*/
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
				console.log("getRentalHistory function");
				console.log(data);
				$scope.longTermLeasingDtlTOs = data.longTermLeasingDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Time Sheet Details", "Error");
			});
		},

		/* Get Short Term Records 7 */
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
				console.log("getShortTermRecords function");
				console.log(data);
				$scope.stcorrReturnsDtlTOs = data.stcorrReturnsDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Short Term Details", "Error");
			});
		},

		/* Get Long Term Lease 8 */
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
				console.log("getLongTermLeaseActualRetruns function");
				console.log(data);
				$scope.longTermLeaseActualRetrunsDtlTOs  = data.longTermLeaseActualRetrunsDtlTOs;
				console.log(" $scope.longTermLeaseActualRetrunsDtlTOs "+JSON.stringify($scope.longTermLeaseActualRetrunsDtlTOs)) 

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Long Term Lease/Actual Returns Details", "Error");
			});
		},

		/* Get Car Parking Toll Collections Records 9 */
		$scope.carParkingTollCollectionsRecords = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var carParkingTollGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.carParkingTollCollectionsRecords(carParkingTollGetReq).then(function(data) {
				console.log("carParkingTollCollectionsRecords function");
				console.log(data);
				$scope.CarParkingTollCollectionDtlTO = data.carParkingTollCollectionDtlTOs;
				console.log($scope.CarParkingTollCollectionDtlTO, 55555555)
				$scope.gridOptions.data = angular.copy($scope.CarParkingTollCollectionDtlTO);
				
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Car Parking Details", "Error");
			});
		},

		/* Get Toll Collections Records- Latest tab said by Lakshmi */
		/* Get Toll collection Records 10 */
		$scope.TollCollectionsRecords = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var carParkingTollGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.TollCollectionsRecords(carParkingTollGetReq).then(function(data) {
				console.log("TollCollectionsRecords function");
				console.log(data);
				$scope.TollCollectionDtlTOs = data.tollCollectionDtlTOs;
			   $scope.gridOptions.data = angular.copy($scope.TollCollectionDtlTOs);

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Car Parking Details", "Error");
			});
		},
		
		$scope.vacantlong = function(proceedingYearLogVacant){
			var longdate;
			var proceedingYearVacantDays = 0;
			var pastYearVacantDays = 0;
			var currentYearVacantDays = 0;
			var date = new Date();
		    var currentYear = date.getFullYear();
		    var pastYear = date.getFullYear()-1;
		    var proceedingYear = date.getFullYear()-2;
			var leasefinishpervious;
			angular.forEach($scope.longTermLeasingDtlTOs, function(value, key) {
				longdate=value.leaseStart;
				console.log(" value long longdate "+ longdate);
				
				var lStart = new Date(value.leaseStart);
				var lFinish = new Date(value.leaseFinish);

				
				if(proceedingYear >= lStart.getFullYear()){
					var lStart = new Date(value.leaseStart);
					var d = new Date(assetidservice.fixedAssetDateCommissioned);
				console.log(' proceedingYearLogVacant'+ proceedingYearLogVacant)
						if(proceedingYearLogVacant){
							leasefinishpervious=lStart;
					if(d.getTime()!=lStart.getTime()){
						proceedingYearLogVacant=false;
						var longProceedingTimeDiff = Math.abs(d.getTime()- lStart.getTime());
						var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
					console.log(' longCurrentNoDays '+ longProceedingNoDays)
						proceedingYearVacantDays +=(longProceedingNoDays);
					}
						}else{
					
							var longProceedingTimeDiff = Math.abs(lStart.getTime()- leasefinishpervious.getTime());
							var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
							console.log(' longCurrentNoDays '+ longProceedingNoDays)
							proceedingYearVacantDays +=(longProceedingNoDays);
							leasefinishpervious=lFinish;
							proceedingYearLogVacant=false;
									}
					}
			});
			return proceedingYearVacantDays;
		},
		
		
		$scope.vacantshort=function (proceedingYearLogVacant){
			var proceedingYearVacantDays = 0;
			var pastYearVacantDays = 0;
			var currentYearVacantDays = 0;
			var shortdate;
			var date = new Date();
		    var currentYear = date.getFullYear();
		    var pastYear = date.getFullYear()-1;
		    var proceedingYear = date.getFullYear()-2;
		    var leasefinishpervious;
			angular.forEach($scope.stcorrReturnsDtlTOs, function(value, key) {
				shortdate=value.startDate
				console.log(" value short shortdate "+ shortdate);
				
				var sDate = new Date(value.startDate);
				var fDate = new Date(value.finishDate);
				
				if(proceedingYear >= sDate.getFullYear()){
					console.log( ' leasefinishpervious '+ leasefinishpervious)
					var d = new Date(assetidservice.fixedAssetDateCommissioned);
					

						if(proceedingYearLogVacant){
							
							leasefinishpervious=fDate;
							if(d.getTime()!=sDate.getTime()){
								
								var longProceedingTimeDiff = Math.abs(d.getTime()- sDate.getTime());
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								console.log(' longCurrentNoDays first proceed short true '+ longProceedingNoDays)
								proceedingYearVacantDays +=(longProceedingNoDays);
							}
							proceedingYearLogVacant=false;
						}else{
					
							var longProceedingTimeDiff = Math.abs(sDate.getTime()- leasefinishpervious.getTime());
							var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
							console.log(' longCurrentNoDays proceeding short '+ longProceedingNoDays)
							proceedingYearVacantDays +=(longProceedingNoDays);
							leasefinishperviousshort=fDate;
							proceedingYearLogVacant=false;
				

				}
			     }
			});
			return proceedingYearVacantDays;

		},
		/* Get Long Term Lease 10 */
		$scope.getOccupancyUtilityRecords = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}

			$scope.sumOccupied = 0;
			$scope.sumVacant = 0;
			$scope.sumRepair = 0;

			$scope.utilisationrecords = [];
			var date = new Date();
		    var currentYear = date.getFullYear();
		    var pastYear = date.getFullYear()-1;
		    var proceedingYear = date.getFullYear()-2;

			$scope.getShortTermRecords();
			$scope.getRentalHistory();
			$scope.getRentalValue();

	    /*		Occupied Column		*/
			var proceedingYearOccupedDays = 0;
			var pastYearOccupedDays = 0;
			var currentYearOccupedDays = 0;
			var longproceedingyearOccupeddays=0;
			var longProceedingTimeDiff;
			var proceedingYearOccupedDays1=0;
			var proceedingYearOccupedDays2=0;
			


			angular.forEach($scope.stcorrReturnsDtlTOs, function(value, key) {
				var currentYear = date.getFullYear();
				if(value.startDate!=null && value.finishDate!=null){
					var sDate = new Date(value.startDate);
					var fDate = new Date(value.finishDate);
					if(currentYear == sDate.getFullYear()){
					var longCurrentTimeDiff = Math.abs(date.getTime() - sDate.getTime());
					var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24))+1;
						currentYearOccupedDays += longCurrentNoDays;
					}else if(pastYear == sDate.getFullYear()){
						var date1 = new Date();
						var fDate1=fDate;

						if (fDate != null){
								 var currentYear = fDate.getFullYear();
						}
						console.log(' lFinish '+fDate1)
						if(currentYear!=pastYear)
						{
							var k=Date.parse("DEC 31 "+pastYear);
							var lFinish=k;
							var longPastTimeDiff = Math.abs(lFinish- sDate.getTime());
						var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
						pastYearOccupedDays +=longPastNoDays;
						if(currentYear==date1.getFullYear()){
							var k ="JAN 01 "+date1.getFullYear()
										
							var longCurrentTimeDiff = Math.abs(Date.parse(k)-lFinish1.getTime());
							var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
							currentYearOccupedDays += longCurrentNoDays;
						}
					}else{

						var longPastTimeDiff = Math.abs(sDate.getTime()-fDate.getTime());
						var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
						pastYearOccupedDays +=longPastNoDays;
						}
						if(1970==currentYear)
						{
							var yearchangevalue=pastYear+1;
							var k=Date.parse("JAN 01 "+yearchangevalue);
							var lFinish=k;
							var longCurrentTimeDiff = Math.abs(lFinish - date.getTime());
						var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
						currentYearOccupedDays +=longCurrentNoDays;
						}

					}
					else if(sDate.getFullYear() <= proceedingYear){

						if (fDate != null){
								 var currentYear = fDate.getFullYear();
						}
						var proceedingOccuped = false;
						for (var i=sDate.getFullYear(); i<=currentYear; i++) {
							
								 	var longProceedingNoDays =0;

							if(proceedingYear >= i){
								var	k="DEC 31 "+i;
								var m="JAN 01 "+i;
					
								if(i==sDate.getFullYear()){

									if(sDate.getFullYear()==fDate.getFullYear()){
										proceedingOccuped=false;
										longProceedingTimeDiff = Math.abs(fDate.getTime()- sDate.getTime());
									}else {
											proceedingOccuped=true;
										 longProceedingTimeDiff = Math.abs(Date.parse(k)- sDate.getTime());
								}
								}else{
								

									if(i==fDate.getFullYear()){
											proceedingOccuped=false;
											 longProceedingTimeDiff = Math.abs(Date.parse(m)- fDate.getTime());
									}else{
												proceedingOccuped=true;
												if(i!=fDate.getFullYear() && i!=sDate.getFullYear()){
													longProceedingTimeDiff = Math.abs(Date.parse(k)- Date.parse(m)+1);
												}else{
											 longProceedingTimeDiff = Math.abs(Date.parse(k)- sDate.getTime());
										 }
									}
								}


								 longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));


							if(proceedingOccuped){
							proceedingYearOccupedDays1+=longProceedingNoDays;
				
							}else{
								proceedingYearOccupedDays2+=longProceedingNoDays;
					
							}
					
								proceedingYearOccupedDays=proceedingYearOccupedDays1+	proceedingYearOccupedDays2;
							proceedingOccuped=false;

							}else if(i==pastYear){
								if (fDate != null){
										 var	j="JAN 01 "+pastYear;
										 var	k=lFinish.getTime();
								}else{
									var	j="JAN 01 "+pastYear;
									var l=Date.parese("DEC 31 "+pastYear);
									var	k=l;
									}

								var longPastTimeDiff = Math.abs(Date.parse(j)- k);
								var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24))+1;
								pastYearOccupedDays +=longPastNoDays;
	
							}else if(i == currentYear) {
								i="JAN 01 "+currentYear;
								var longCurrentTimeDiff = Math.abs(date - Date.parse(i));
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
								currentYearOccupedDays += longCurrentNoDays
							}
    			}

					}
				}
			});

			
			angular.forEach($scope.longTermLeasingDtlTOs, function(value, key) {
				 var currentYear = date.getFullYear();

				if(value.leaseStart!=null){
					var lStart = new Date(value.leaseStart);
					var lFinish = new Date(value.leaseFinish);
				
						if(currentYear == lStart.getFullYear()){
						var longCurrentTimeDiff = Math.abs(date.getTime() - lStart.getTime());
						
						var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24))+1;
						currentYearOccupedDays += longCurrentNoDays;
					}else if(pastYear == lStart.getFullYear()){
						var date1 = new Date();
						var lFinish1=lFinish;
						
						if (lFinish != null){
						 var currentYear = lFinish.getFullYear();
						}
						if(currentYear!=pastYear)
						{
							var k=Date.parse("DEC 31 "+pastYear);
							var lFinish=k;
							var longPastTimeDiff = Math.abs(lFinish- lStart.getTime());
						var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
						pastYearOccupedDays +=longPastNoDays;
						if(currentYear==date1.getFullYear()){
							var k ="JAN 01 "+date1.getFullYear()
						
						
							var longCurrentTimeDiff = Math.abs(Date.parse(k)-lFinish1.getTime());
							var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
							currentYearOccupedDays += longCurrentNoDays;
						}
					}else{
					
						var longPastTimeDiff = Math.abs(lStart.getTime()-lFinish.getTime());
					var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
					pastYearOccupedDays +=longPastNoDays;

					}
						if(1970==currentYear)
						{
							var yearchangevalue=pastYear+1;
							var k=Date.parse("JAN 01 "+yearchangevalue);
							var lFinish=k;
							var longCurrentTimeDiff = Math.abs(lFinish - date.getTime());
						var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
						currentYearOccupedDays +=longCurrentNoDays;
						}

					}
					else if(lStart.getFullYear() <= proceedingYear){

						if (lFinish != null){
								 var currentYear = lFinish.getFullYear();
						}
						var proceedingOccuped = false;
						for (var i=lStart.getFullYear(); i<=currentYear; i++) {

								 	var longProceedingNoDays =0;
								if(proceedingYear >= i){
								var	k="DEC 31 "+i;
								var m="JAN 01 "+i;
							if(i==lStart.getFullYear()){

									if(lStart.getFullYear()==lFinish.getFullYear()){
										proceedingOccuped=false;
												 longProceedingTimeDiff = Math.abs(lFinish.getTime()- lStart.getTime());
									}else {
											proceedingOccuped=true;
										 longProceedingTimeDiff = Math.abs(Date.parse(k)- lStart.getTime());
								}
			
								}else{
								

									if(i==lFinish.getFullYear()){
											proceedingOccuped=false;
											 longProceedingTimeDiff = Math.abs(Date.parse(m)- lFinish.getTime());
									}else{
												proceedingOccuped=true;
												if(i!=lFinish.getFullYear() && i!=lStart.getFullYear()){
													longProceedingTimeDiff = Math.abs(Date.parse(k)- Date.parse(m)+1);
												}else{
											 longProceedingTimeDiff = Math.abs(Date.parse(k)- lStart.getTime());
										 }
									}
								}


								 longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));


							if(proceedingOccuped){
								proceedingYearOccupedDays1+=longProceedingNoDays;
							}else{
								proceedingYearOccupedDays2+=longProceedingNoDays;
								}
							proceedingYearOccupedDays=proceedingYearOccupedDays1+	proceedingYearOccupedDays2;
							proceedingOccuped=false;
							}else if(i==pastYear){
								if (lFinish != null){
										 var	j="JAN 01 "+pastYear;
										 var	k=lFinish.getTime();
								}else{
									var	j="JAN 01 "+pastYear;
									var l=Date.parese("DEC 31 "+pastYear);
									var	k=l;
									}
								var longPastTimeDiff = Math.abs(Date.parse(j)- k);
								var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24))+1;
								pastYearOccupedDays +=longPastNoDays;
						}else if(i == currentYear) {
								i="JAN 01 "+currentYear;
								var longCurrentTimeDiff = Math.abs(date - Date.parse(i));
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
								currentYearOccupedDays += longCurrentNoDays
							}
    			}

					}
				}
			});


			/*Under Repair/Restoration Column	*/
			var proceedingYearRepairDays = 0;
			var pastYearRepairDays = 0;
			var currentYearRepairDays = 0;
			var endOfRRDate= null;
			var phaseDate=null;
			var Operationalphase=false;


			angular.forEach($scope.rentalValueDtlTOs, function(value, key) {
				var eDate = new Date(value.effectiveDate);
				
				if(eDate!=null && currentYear == eDate.getFullYear()){
					if(value.assetCurrentLifeSataus=="Under Renovation/Repairs" ){
							 Operationalphase = true;
								endOfRRDate = new Date(value.effectiveDate);
							}
						if(value.assetCurrentLifeSataus=="Operational Phase"){
							phaseDate= new Date(value.effectiveDate);
						}
						if(Operationalphase){
						if(endOfRRDate!=null && phaseDate !=null){
							var rentalValueCurrentTimeDiff = Math.abs(endOfRRDate.getTime() - phaseDate.getTime());
							var rentalValueCurrentNoDays = Math.ceil(rentalValueCurrentTimeDiff / (1000 * 3600 * 24));
							currentYearRepairDays +=rentalValueCurrentNoDays;
							endOfRRDate=null;
							phaseDate=null;
							Operationalphase=false;
						}
						}
				}else if(eDate!=null && pastYear == eDate.getFullYear()){

					if(value.assetCurrentLifeSataus=="Under Renovation/Repairs" ){
						Operationalphase = true;
								endOfRRDate = new Date(value.effectiveDate);
							}
						if(value.assetCurrentLifeSataus=="Operational Phase"){
							phaseDate= new Date(value.effectiveDate);
						}
	
						if(Operationalphase){
						if(endOfRRDate!=null && phaseDate !=null){
							var rentalValuePastTimeDiff = Math.abs(endOfRRDate.getTime() - phaseDate.getTime());
							var rentalValuePastNoDays = Math.ceil(rentalValuePastTimeDiff / (1000 * 3600 * 24));
							pastYearRepairDays +=rentalValuePastNoDays;
							endOfRRDate=null;
							phaseDate=null;
							Operationalphase=false;
						}
						}
				}else if(eDate!=null && proceedingYear >= eDate.getFullYear()){
					
					//Operational Phase

				if(value.assetCurrentLifeSataus=="Under Renovation/Repairs" ){
					Operationalphase = true;
							endOfRRDate = new Date(value.effectiveDate);
						}
					if(value.assetCurrentLifeSataus=="Operational Phase"){
						phaseDate= new Date(value.effectiveDate);
					}
					if(Operationalphase){
					if(endOfRRDate!=null && phaseDate !=null){
						var rentalValueProceedingTimeDiff = Math.abs(endOfRRDate.getTime() - phaseDate.getTime());
						var rentalValueProceedingNoDays = Math.ceil(rentalValueProceedingTimeDiff / (1000 * 3600 * 24));
						proceedingYearRepairDays +=rentalValueProceedingNoDays;
						endOfRRDate=null;
						phaseDate=null;
						Operationalphase=false;
					}}
				}
			});

			/*Vacant Column*/
			var proceedingYearVacantDays = 0;
			var pastYearVacantDays = 0;
			var currentYearVacantDays = 0;

			var proceedingYearLogVacant;
			var proceedingYearLogVacantshort;
			var pastYearLogVacant = true;
			var leasefinishpervious;
			var leasefinishperviousshort;
			var longdate, shortdate;
			var longshortboolean = true;
			var commonelsebool;

			angular.forEach($scope.longTermLeasingDtlTOs, function (value, key) {
				longdate = value.leaseStart;

			});
			angular.forEach($scope.stcorrReturnsDtlTOs, function (value, key) {
				shortdate = value.startDate

			});
			var d = new Date(assetidservice.fixedAssetDateCommissioned);
			var d1 = new Date(d);
			var d2 = new Date(longdate);
			var d3 = new Date(shortdate);

			if (longshortboolean) {
				if (d1 <= d2 && d2 <= d3) {
					proceedingYearLogVacant = true;
					console.log(" long and short")
					//$scope.vacantlong(proceedingYearLogVacant);
					angular.forEach($scope.longTermLeasingDtlTOs, function (value, key) {
						longdate = value.leaseStart;
						console.log(" value long leasefinishpervious " + leasefinishpervious);

						var lStart = new Date(value.leaseStart);
						var lFinish = new Date(value.leaseFinish);


						if (proceedingYear >= lStart.getFullYear()) {
							var lStart = new Date(value.leaseStart);
							var d = new Date(assetidservice.fixedAssetDateCommissioned);
							console.log(' proceedingYearLogVacant' + proceedingYearLogVacant)
							if (proceedingYearLogVacant) {
								leasefinishpervious = lFinish;
								if (d.getTime() != lStart.getTime()) {
									//proceedingYearLogVacant=false;
									var longProceedingTimeDiff = Math.abs(d.getTime() - lStart.getTime());
									var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
									console.log(' longCurrentNoDays ' + longProceedingNoDays)
									proceedingYearVacantDays += (longProceedingNoDays);
								}
							} else {

								var longProceedingTimeDiff = Math.abs(lStart.getTime() - leasefinishpervious.getTime());
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								console.log(' longCurrentNoDays ' + longProceedingNoDays)
								proceedingYearVacantDays += (longProceedingNoDays);
								leasefinishpervious = lFinish;
								proceedingYearLogVacant = false;
							}
						} else if (pastYear == sDate.getFullYear()) {


							var sDate = new Date(value.startDate);

							var d = new Date(assetidservice.fixedAssetDateCommissioned);
							//		console.log(' assetidservice.fixedAssetDateCommissioned '+ d +' lStart '+ lStart)

							if (proceedingYearLogVacant) {

								leasefinishpervious = fDate;
								if (d.getTime() != sDate.getTime()) {
									//proceedingYearLogVacantshort=false;
									var longPastTimeDiff = Math.abs(d.getTime() - sDate.getTime());
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									//			console.log(' longCurrentNoDays '+ longProceedingNoDays)
									pastYearVacantDays += (longPastNoDays);
								}

							} else {

								if (leasefinishpervious.getFullYear() < sDate.getFullYear()) {

									if (pastYear == sDate.getFullYear()) {
										var proceeddate = (sDate.getFullYear() - 1)
										var j = "DEC 31 " + proceeddate;
										//	var leasefinishdate1 = new Date(j);
										//var	k=lFinish.getTime();
										var longProceedingTimeDiff = Math.abs(Date.parse(j) - leasefinishpervious.getTime());
										var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
										proceedingYearVacantDays += (longProceedingNoDays);

										var j = "JAN 01 " + pastYear;
										var longPastTimeDiff = Math.abs(sDate.getTime() - Date.parse(j));
										var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
										pastYearVacantDays += (longPastNoDays);
										leasefinishpervious = fDate;

									} else {
										var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
										var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
										pastYearVacantDays += (longPastNoDays);
										leasefinishpervious = fDate;

									}
								}


								if (fDate.getFullYear() == currentYear) {

									//		console.log(" lFinish.getFullYear() past " + fDate.getFullYear() +' currentYear '+ currentYear )
									var longCurrentTimeDiff = Math.abs(leasefinishpervious.getTime() - date.getTime());
									var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

									currentYearVacantDays += (longCurrentNoDays);
									proceedingYearLogVacant = false;
									leasefinishpervious = lFinish;
									//	console.log('past year finishdate '+leasefinishpervious);
								}

							}

						} else if (currentYear == sDate.getFullYear()) {
							//	var lStart = new Date(value.startDate);
							var d = new Date(assetidservice.fixedAssetDateCommissioned);
							if (proceedingYearLogVacant) {

								leasefinishpervious = fDate;
								if (d.getTime() != sDate.getTime()) {
									//proceedingYearLogVacantshort=false;
									var longCurrentTimeDiff = Math.abs(d.getTime() - sDate.getTime());
									var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

									currentYearVacantDays += (longCurrentNoDays);
								}

							} else {

								if (leasefinishpervious.getFullYear() == sDate.getFullYear()) {


									var longCurrentTimeDiff = leasefinishpervious.getTime() - date.getTime();
									var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24) - 1);
									currentYearVacantDays += (longCurrentNoDays);

								}

								var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
								var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays += (longPastNoDays);
								leasefinishpervious = fDate;
								proceedingYearLogVacant = false;

							}

						}
					});
					longshortboolean = false;
				} else if (d1 <= d3 && d3 <= d2) {
					proceedingYearLogVacantshort = true;
					console.log(" short and long")
					//$scope.vacantshort(proceedingYearLogVacant);


					angular.forEach($scope.stcorrReturnsDtlTOs, function (value, key) {
						shortdate = value.startDate
						console.log(" value short shortdate stc " + shortdate);

						var sDate = new Date(value.startDate);
						var fDate = new Date(value.finishDate);

						if (proceedingYear >= sDate.getFullYear()) {
							var d = new Date(assetidservice.fixedAssetDateCommissioned);
						if (proceedingYearLogVacantshort) {

								leasefinishpervious = fDate;
								if (d.getTime() != sDate.getTime()) {

									var longProceedingTimeDiff = Math.abs(d.getTime() - sDate.getTime());
									var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
									console.log(' longCurrentNoDays first proceed short true ' + longProceedingNoDays)
									proceedingYearVacantDays += (longProceedingNoDays);
								}
								//proceedingYearLogVacantshort=false;
								console.log("  leasefinishpervious if " + leasefinishpervious)
							} else {


								var longProceedingTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								console.log(' longCurrentNoDays proceeding short ' + longProceedingNoDays)
								proceedingYearVacantDays += (longProceedingNoDays);
								leasefinishperviousshort = fDate;
								proceedingYearLogVacantshort = false;


							}
						} else if (pastYear == sDate.getFullYear()) {


							var sDate = new Date(value.startDate);

							var d = new Date(assetidservice.fixedAssetDateCommissioned);
							//		console.log(' assetidservice.fixedAssetDateCommissioned '+ d +' lStart '+ lStart)

							if (proceedingYearLogVacantshort) {
								if (commonbooleanshort) {
									leasefinishpervious = fDate;
									if (d.getTime() != sDate.getTime()) {
										//proceedingYearLogVacantshort=false;
										var longPastTimeDiff = Math.abs(d.getTime() - sDate.getTime());
										var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
										//			console.log(' longCurrentNoDays '+ longProceedingNoDays)
										pastYearVacantDays += (longPastNoDays);
									}
								}
							} else {

								if (leasefinishpervious.getFullYear() < sDate.getFullYear()) {

									if (pastYear == sDate.getFullYear()) {
										var proceeddate = (sDate.getFullYear() - 1)
										var j = "DEC 31 " + proceeddate;
										//	var leasefinishdate1 = new Date(j);
										//var	k=lFinish.getTime();
										var longProceedingTimeDiff = Math.abs(Date.parse(j) - leasefinishpervious.getTime());
										var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
										proceedingYearVacantDays += (longProceedingNoDays);

										var j = "JAN 01 " + pastYear;
										var longPastTimeDiff = Math.abs(sDate.getTime() - Date.parse(j));
										var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
										pastYearVacantDays += (longPastNoDays);
										leasefinishpervious = fDate;

									} else {
										var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
										var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
										pastYearVacantDays += (longPastNoDays);
										leasefinishpervious = fDate;

									}
								}


								if (fDate.getFullYear() == currentYear) {

									//		console.log(" lFinish.getFullYear() past " + fDate.getFullYear() +' currentYear '+ currentYear )
									var longCurrentTimeDiff = Math.abs(leasefinishpervious.getTime() - date.getTime());
									var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

									currentYearVacantDays += (longCurrentNoDays);
									proceedingYearLogVacantshort = false;
									leasefinishpervious = lFinish;
									//	console.log('past year finishdate '+leasefinishpervious);
								}

							}

						} else if (currentYear == sDate.getFullYear()) {
							//	var lStart = new Date(value.startDate);
							var d = new Date(assetidservice.fixedAssetDateCommissioned);
							if (proceedingYearLogVacantshort) {

								leasefinishpervious = fDate;
								if (d.getTime() != sDate.getTime()) {
									//proceedingYearLogVacantshort=false;
									var longCurrentTimeDiff = Math.abs(d.getTime() - sDate.getTime());
									var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

									currentYearVacantDays += (longCurrentNoDays);
								}

							} else {

								if (leasefinishpervious.getFullYear() == sDate.getFullYear()) {

									var longCurrentTimeDiff = leasefinishpervious.getTime() - date.getTime();
									var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24) - 1);
									currentYearVacantDays += (longCurrentNoDays);
									//	leasefinishperviousshort=lFinish;
									//	proceedingYearLogVacantshort=false;
								}

								var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
								var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays += (longPastNoDays);
								leasefinishpervious = fDate;
								proceedingYearLogVacantshort = false;

							}

						}
					});
					longshortboolean = false;
				} else {

					proceedingYearLogVacant = true;
					proceedingYearLogVacantshort = true;
					commonelsebool = true;
					console.log(" long  " + JSON.stringify($scope.stcorrReturnsDtlTOs));
					longshortboolean = false;
				}
			}
			console.log(' Date commissioned ' + d1 + ' long date ' + d2 + ' short ' + d3)


			if (proceedingYearLogVacant) {
				proceedingYearLogVacant = false;
				angular.forEach($scope.stcorrReturnsDtlTOs, function (value, key) {
					shortdate = value.startDate
					console.log(" value short shortdate stc " + shortdate);

					var sDate = new Date(value.startDate);
					var fDate = new Date(value.finishDate);

					if (commonelsebool) {
						proceedingYearLogVacantshort = true
					}


					if (proceedingYear >= sDate.getFullYear()) {

						console.log(' leasefinishpervious ' + leasefinishpervious)
						//var lStart = new Date(value.startDate);

						var d = new Date(assetidservice.fixedAssetDateCommissioned);
						//	console.log(' proceedingYearLogVacant '+ proceedingYearLogVacantshort)

						if (proceedingYearLogVacantshort) {

							leasefinishpervious = fDate;
							if (d.getTime() != sDate.getTime()) {

								var longProceedingTimeDiff = Math.abs(d.getTime() - sDate.getTime());
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								console.log(' longCurrentNoDays first proceed short true ' + longProceedingNoDays)
								proceedingYearVacantDays += (longProceedingNoDays);
							}
							//proceedingYearLogVacantshort=false;
							commonelsebool = false;
							console.log("  leasefinishpervious if " + leasefinishpervious)
						} else {


							var longProceedingTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
							var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
							console.log(' longCurrentNoDays proceeding short ' + longProceedingNoDays)
							proceedingYearVacantDays += (longProceedingNoDays);
							leasefinishperviousshort = fDate;
							proceedingYearLogVacantshort = false;


						}
					} else if (pastYear == sDate.getFullYear()) {


						var sDate = new Date(value.startDate);

						var d = new Date(assetidservice.fixedAssetDateCommissioned);
						//		console.log(' assetidservice.fixedAssetDateCommissioned '+ d +' lStart '+ lStart)

						if (proceedingYearLogVacantshort) {

							leasefinishpervious = fDate;
							if (d.getTime() != sDate.getTime()) {
								//proceedingYearLogVacantshort=false;
								var longPastTimeDiff = Math.abs(d.getTime() - sDate.getTime());
								var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
								//			console.log(' longCurrentNoDays '+ longProceedingNoDays)
								pastYearVacantDays += (longPastNoDays);
							}
							commonelsebool = false;

						} else {

							if (leasefinishperviousshort.getFullYear() < sDate.getFullYear()) {

								if (pastYear == sDate.getFullYear()) {
									var proceeddate = (sDate.getFullYear() - 1)
									var j = "DEC 31 " + proceeddate;
									//	var leasefinishdate1 = new Date(j);
									//var	k=lFinish.getTime();
									var longProceedingTimeDiff = Math.abs(Date.parse(j) - leasefinishperviousshort.getTime());
									var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
									proceedingYearVacantDays += (longProceedingNoDays);

									var j = "JAN 01 " + pastYear;
									var longPastTimeDiff = Math.abs(sDate.getTime() - Date.parse(j));
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									pastYearVacantDays += (longPastNoDays);
									leasefinishpervious = fDate;

								} else {
									var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									pastYearVacantDays += (longPastNoDays);
									leasefinishpervious = fDate;

								}
							}


							if (fDate.getFullYear() == currentYear) {

								//		console.log(" lFinish.getFullYear() past " + fDate.getFullYear() +' currentYear '+ currentYear )
								var longCurrentTimeDiff = Math.abs(leasefinishperviousshort.getTime() - date.getTime());
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

								currentYearVacantDays += (longCurrentNoDays);
								proceedingYearLogVacantshort = false;
								leasefinishpervious = lFinish;
								//	console.log('past year finishdate '+leasefinishpervious);
							}

						}

					} else if (currentYear == sDate.getFullYear()) {
						//	var lStart = new Date(value.startDate);
						var d = new Date(assetidservice.fixedAssetDateCommissioned);
						if (proceedingYearLogVacantshort) {

							leasefinishpervious = fDate;
							if (d.getTime() != sDate.getTime()) {
								//proceedingYearLogVacantshort=false;
								var longCurrentTimeDiff = Math.abs(d.getTime() - sDate.getTime());
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

								currentYearVacantDays += (longCurrentNoDays);
							}
							commonelsebool = false;
						} else {

							if (leasefinishpervious.getFullYear() == sDate.getFullYear()) {

								var longCurrentTimeDiff = leasefinishpervious.getTime() - date.getTime();
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24) - 1);
								currentYearVacantDays += (longCurrentNoDays);
								//	leasefinishperviousshort=lFinish;
								//	proceedingYearLogVacantshort=false;
							}

							var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
							var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
							pastYearVacantDays += (longPastNoDays);
							leasefinishpervious = fDate;
							proceedingYearLogVacantshort = false;

						}

					}
				});
			}

			console.log("proceedingYearLogVacantshort " + proceedingYearLogVacantshort + ' leasefinishpervious ' + leasefinishpervious)

			if (proceedingYearLogVacantshort) {
				proceedingYearLogVacantshort = false;

				angular.forEach($scope.longTermLeasingDtlTOs, function (value, key) {
					longdate = value.leaseStart;
					console.log(" value long leasefinishpervious " + leasefinishpervious);

					if (commonelsebool) {
						proceedingYearLogVacant = true
					}

					var lStart = new Date(value.leaseStart);
					var lFinish = new Date(value.leaseFinish);


					if (proceedingYear >= lStart.getFullYear()) {
						var lStart = new Date(value.leaseStart);
						var d = new Date(assetidservice.fixedAssetDateCommissioned);
						console.log(' proceedingYearLogVacant' + proceedingYearLogVacant)
						if (proceedingYearLogVacant) {
							leasefinishpervious = lStart;
							if (d.getTime() != lStart.getTime()) {
								proceedingYearLogVacant = false;
								var longProceedingTimeDiff = Math.abs(d.getTime() - lStart.getTime());
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								console.log(' longCurrentNoDays ' + longProceedingNoDays)
								proceedingYearVacantDays += (longProceedingNoDays);
							}
							commonelsebool = false;
							proceedingYearLogVacant = false;
						} else {

							var longProceedingTimeDiff = Math.abs(lStart.getTime() - leasefinishpervious.getTime());
							var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
							proceedingYearVacantDays += (longProceedingNoDays);
							leasefinishpervious = lFinish;
							proceedingYearLogVacant = false;
						}
					} else if (pastYear == lStart.getFullYear()) {
						var sDate = new Date(value.startDate);

						var d = new Date(assetidservice.fixedAssetDateCommissioned);
						if (proceedingYearLogVacant) {

							leasefinishpervious = fDate;
							if (d.getTime() != sDate.getTime()) {
								
								var longPastTimeDiff = Math.abs(d.getTime() - sDate.getTime());
								var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
							
								pastYearVacantDays += (longPastNoDays);
							}
							commonelsebool = false;
							proceedingYearLogVacant = false;

						} else {

							if (leasefinishperviousshort.getFullYear() < sDate.getFullYear()) {

								if (pastYear == sDate.getFullYear()) {
									var proceeddate = (sDate.getFullYear() - 1)
									var j = "DEC 31 " + proceeddate;
									
									var longProceedingTimeDiff = Math.abs(Date.parse(j) - leasefinishperviousshort.getTime());
									var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
									proceedingYearVacantDays += (longProceedingNoDays);

									var j = "JAN 01 " + pastYear;
									var longPastTimeDiff = Math.abs(sDate.getTime() - Date.parse(j));
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									pastYearVacantDays += (longPastNoDays);
									leasefinishpervious = fDate;

								} else {
									var longPastTimeDiff = Math.abs(sDate.getTime() - leasefinishpervious.getTime());
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									pastYearVacantDays += (longPastNoDays);
									leasefinishpervious = fDate;

								}
							}


							if (fDate.getFullYear() == currentYear) {

								var longCurrentTimeDiff = Math.abs(leasefinishperviousshort.getTime() - date.getTime());
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

								currentYearVacantDays += (longCurrentNoDays);
								proceedingYearLogVacantshort = false;
								leasefinishpervious = lFinish;
								
							}

						}

					} else if (currentYear == lStart.getFullYear()) {
						
						var d = new Date(assetidservice.fixedAssetDateCommissioned);
						if (proceedingYearLogVacant) {
							leasefinishpervious = lFinish;
							if (d.getTime() != lStart.getTime()) {
								//proceedingYearLogVacantshort=false;
								var longCurrentTimeDiff = Math.abs(d.getTime() - lStart.getTime());
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));

								currentYearVacantDays += (longCurrentNoDays);
							}
							commonelsebool = false;
							proceedingYearLogVacant = false;
						} else {

							if (leasefinishpervious.getFullYear() == lStart.getFullYear()) {

								var longCurrentTimeDiff = leasefinishpervious.getTime() - date.getTime();
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24) - 1);
								currentYearVacantDays += (longCurrentNoDays);
								
							}

							var longPastTimeDiff = Math.abs(lStart.getTime() - leasefinishpervious.getTime());
							var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
							pastYearVacantDays += (longPastNoDays);
							leasefinishpervious = fDate;
							proceedingYearLogVacant = false;

						}

					}
				});

			}
			var proceedingYearVacant=0;
			var pastYearVacant=0;
			var currentYearVacant=0;
			proceedingYearVacant=proceedingYearVacantDays-proceedingYearRepairDays;
			pastYearVacant=pastYearVacantDays-pastYearRepairDays;
			currentYearVacant=currentYearVacantDays-currentYearRepairDays
				var addProceedingOccupancy = {
						"period":"Proceeding Years",
						"unit":"Days",
						"occupied":proceedingYearOccupedDays,
						"vacant":proceedingYearVacant,
						"underRepairRestoration":proceedingYearRepairDays,
						"total":proceedingYearOccupedDays+proceedingYearVacant+proceedingYearRepairDays
					};
				$scope.utilisationrecords.push(addProceedingOccupancy);
				var addPastOccupancy = {
						"period":"Past Year",
						"unit":"Days",
						"occupied":pastYearOccupedDays,
						"vacant":pastYearVacant,
						"underRepairRestoration":pastYearRepairDays,
						"total":pastYearOccupedDays+pastYearVacant+pastYearRepairDays
				};
				$scope.utilisationrecords.push(addPastOccupancy);
				var addCurrentOccupancy = {
						"period":"Current Years",
						"unit":"Days",
						"occupied":currentYearOccupedDays,
						"vacant":currentYearVacant,
						"underRepairRestoration":currentYearRepairDays,
						"total":currentYearOccupedDays+currentYearVacant+currentYearRepairDays
					};

				$scope.utilisationrecords.push(addCurrentOccupancy);
				angular.forEach($scope.utilisationrecords, function(value, key) {
					$scope.sumOccupied += value.occupied;
					$scope.sumVacant += value.vacant;
					$scope.sumRepair += value.underRepairRestoration;

				});
		},
		/* Get Periodical Records 11*/
		$scope.getPeriodicalRecords=function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var periodicalRecordsGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.getPeriodicalRecordsOnLoad(periodicalRecordsGetReq).then(function(data) {
				console.log(data);
				$scope.PeriodicalScheduleMaintenanceDtlTO = data.periodicalScheduleMaintenanceDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Periodical Details", "Error");
			});
		},
		/* Get Plant Repair Records 12*/
		$scope.getPlantRepairRecordsOnLoad=function() {
			console.log('	$scope.selectedRow '+ JSON.stringify($scope.selectedRow));
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var carParkingTollGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.getRepairRecordsOnLoad(carParkingTollGetReq).then(function(data) {
				console.log('data.fixedAssetRepairsRecordsDtlTOs;'+JSON.stringify(data.fixedAssetRepairsRecordsDtlTOs))
				$scope.fixedAssetRepairsRecordsDtlTO = data.fixedAssetRepairsRecordsDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Plant Repair Details", "Error");
			});
		},

		/* Get Asset Life Status 13*/
		$scope.getAssetLifeStatus = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var assetLifeStatusGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.getAssetLifeStatus(assetLifeStatusGetReq).then(function(data) {
				$scope.assetLifeStatusDtlTOs = data.assetLifeStatusDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Asset Life Status Details", "Error");
			});
		},
	/* Get Asset Cost Value 14*/
		$scope.getAssetCostValueStatus = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
				return;
			}
			var assetLifeStatusGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};
			AssetRegisterService.getAssetCostValueStatus(assetLifeStatusGetReq).then(function(data) {
				$scope.assetCostValueStatusDtlTOs = data.assetCostValueStatusDtlTOs;

			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching Asset Cost Value Details", "Error");
			});
		},
		$scope.getSearchAssetRegisters1 = function(countryName,provisionName,profitCentre){
						
			var getAssetRegisterReq = {
					"status" : "1",
					"countryName": countryName,
					"provisionName":provisionName,
					"profitCentre": profitCentre,
				};
		
			AssetRegisterService.getfixedAssetRegistersOnLoad(getAssetRegisterReq).then(function(data) {
				$scope.assetData = populateAssetsTreeData(data.fixedAssetDtlTOs);
				$scope.responsibleManagers = data.users;
				//$scope.countries = data.geonames;
				$scope.currencies = data.currency;
				$scope.timeZones = data.timeZoneTOs;
				//getCountries
			 });
			
		},
		
	$scope.getSearchAssetRegisters = function() {
			var getAssetRegisterReq = {
			"status" : "1",
		};
	AssetRegisterService.getfixedAssetRegistersOnLoad(getAssetRegisterReq).then(function(data) {
		$scope.assetData = populateAssetsTreeData(data.fixedAssetDtlTOs);
		$scope.responsibleManagers = data.users;
		$scope.currencies = data.currency;
		$scope.timeZones = data.timeZoneTOs;
		
	 });
	
	},
	$scope.getCountries = function() {
		CountryService.getCountries().then(function(data) {
			$scope.countries = data.countryInfoTOs;
			 console.log("$scope.countries", $scope.countries)

		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
		});
	},
	$scope.getTimeZone = function(province) {
		CountryService.getTimeZone(province.lat,province.lng).then(function(data) {
			console.log(data,"timezone");
			$scope.item.timezone = data.data.gmtOffset;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting TimeZone", "Error");
		});

	},

	$scope.getCountryDetailsByIdres = function(item) {
		
		$scope.geonameId;
		$scope.level=0;
	   
		console.log("item"+JSON.stringify(item))
		const countryName = item;
		const countryObj = $scope.countries.find(function(country){
			return country.countryName === countryName;
		});
		
		if(countryObj) {
		//	item.currency = countryObj.currencyCode;
			const req = { "geonameId": countryObj.geonameId };
			CountryService.getProvince(req).then(function(data) {
					$scope.provisionTOs = data.provisionTOs;
			}, function(error) {

			if(item.provisionTOs==undefined){
					GenericAlertService.alertMessage(" No Provision for selected country ", "Warning");
			}else{
					if(item.provisionTOs.clientId==undefined){
						GenericAlertService.alertMessage(" No Provision for selected country ", "Warning");
					}else{
					GenericAlertService.alertMessage("Error occured while getting Province details", "Error");
				}
		}
			});
		}
	},
	$scope.getprofit=function(){
		var onLoadReq = {
				"status" : 1
			};
			console.log("onLoadReq"+JSON.stringify(onLoadReq))
			var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
			profitCentrePopup.then(function(data) {
				console.log("data"+JSON.stringify(data))
				$scope.profitCentreTOId = data.selectedRecord.id;
				$scope.profitCentreTOCode = data.selectedRecord.code;
                $scope.profitCentre = data.selectedRecord.name;
                console.log("item.profitCentre"+profitCentre);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
			});
		
	},
	
	
	// $rootScope value setting for subtab
	$scope.assetEditDeleteSelect = function(assetDetails) {
    var count = 0;
    $scope.fixedAssetid = null;
	if (assetDetails.selected) {
		editAssetDetails.push(assetDetails);
		deleteTreeData.push(assetDetails.fixedAssetid);
		count++;
	} else {
		count--;
		editAssetDetails.splice(editAssetDetails.indexOf(editAssetDetails), 1)
		deleteTreeData.splice(editAssetDetails.indexOf(editAssetDetails), 1)

		}
	},

	$scope.assetRowSelect = function(event, assetDetails){
		let tr = event.target.parentNode;
		let table = tr.parentNode;

		let allTds = table.querySelectorAll('td');
		let selectedTds = tr.querySelectorAll('td');

		for( let index = 0; index < allTds.length; index++ ){
			allTds[index].classList.remove('selected');
		}

		for( let index = 0; index < selectedTds.length; index++ ){
			selectedTds[index].classList.add('selected');
		}

		console.log(' assetDetails '+ JSON.stringify( assetDetails));

		$scope.projectId=assetDetails.projectId;
		$scope.fixedAssetid = assetDetails.fixedAssetid;
		$scope.yearBuild = assetDetails.yearBuild;
		$scope.dateCommissioned = assetDetails.dateCommissioned;
		$scope.currency = assetDetails.currency;

		$rootScope.selected_project_id = $scope.projectId;
		assetidservice.projectId=$scope.projectId;
		assetidservice.fixedAssetId=$scope.fixedAssetid;
		assetidservice.fixedAssetYearBuild=$scope.yearBuild;
		assetidservice.fixedAssetDateCommissioned=$scope.dateCommissioned;
		assetidservice.currency=$scope.currency;

		$scope.onClickTab($scope.assetDetailsTabs[0]);
	},

	//create asset deatis
	$scope.addAssetList = function(actionType) {
		if (actionType == 'Edit' && (editAssetDetails.length <= 0 || editAssetDetails.length >1)) {
			GenericAlertService.alertMessage("Please select one row to modify ", 'Warning');
			return;
		}
			var assetdetailspopup = AssetDetailsFactory.assetDetailsPopUpOnLoad(actionType, editAssetDetails, $scope.countries,$scope.provisionMap);
			assetdetailspopup.then(function(data) {
				editAssetData=[];
				$scope.assetData =	data.fixedAssetDtlTOs;
				$scope.countryMap = data.countryMap;
				$scope.provisionMap = data.provisionMap;
			var success= GenericAlertService.alertMessagemodal("Asset saved successfully ");
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Asset details", 'Error');
			});
			$scope.getSearchAssetRegisters();
	},
	 /* $scope.$watch('[search.country,search.state,search.profitCenter]', function(val)
			    {
		  $scope.criteria = angular.copy($scope.assetData);
			    $scope.assetData = $filter('filter')($scope.criteria, val[0]);
			    if(val[0]== "" || val[0]== undefined && val[1]== "" || val[1]== undefined && val[2]== "" || val[2]== undefined)
			    	{
			    	$scope.getSearchAssetRegisters();
			    	}
		  		    });*/

	$scope.deleteAssetRegisters = function(actionType) {
		if (editAssetDetails.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}

		var deactivateIds =[];

		angular.forEach(editAssetDetails, function(asset, index) {
	        if (asset.fixedAssetid) {
	        	deactivateIds[index]=asset.fixedAssetid;
	        }
		});

		var saveAssetDetailsReq = {
				"fixedAssetRegIds" : deactivateIds
			}
		AssetRegisterService.deleteAssetRegisters(saveAssetDetailsReq).then(function(data) {
			var succMsg = GenericAlertService.alertMessageModal('Asset deactivation is'+ data.message, data.status);
			succMsg.then(function() {
				var returnPopObj = {
					"assetRegTO" : data.fixedAssetDtlTOs
				};

				deferred.resolve(returnPopObj);
			});
		}, function(error) {
			if (error.status != null && error.status != undefined) {
				GenericAlertService.alertMessage(error.message, error.status);
			} else {
				GenericAlertService.alertMessage('Asset deactivation is/are failed', "Error");
			}
		});



	},
	$scope.getCompanies = function(item) {
		var userCompanySelection = AssetCompanySelectFactory.getCompanies();
		userCompanySelection.then(function(data) {
			item.companyName = data.searchCompany.cmpName;
			item.companyId = data.searchCompany.cmpId;
		},
			function(error) {
		GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
	});
	}

	$scope.getCountryDetails = function(countryProvisionObj) {
		countryProvisionObj.id = null;
		countryProvisionObj.code = null;
		countryProvisionObj.provisionLabelKeyTO = {
			"id" : null,
			"code" : null,
			"name" : null
		};

		var popupDetails = TaxCountryFactory.getCountryDetails();
		popupDetails.then(function(data) {
			countryProvisionObj.countryName = data.selectedCountry.name;
			countryProvisionObj.countryId = data.selectedCountry.id;
			$scope.provisions = data.selectedCountry.provisionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting  details", 'Info');
		})
	};

	function populateAssetsTreeData(data){
		return TreeService.populateTreeData(data, 0, [], 'assetCategoryName', 'childProjs');
	}

	$scope.rowSelect = function(rowData) {
		if (rowData.select) {
			deleteTreeData.push(rowData.fixedAssetid);
		} else {
			deleteTreeData.pop(rowData.fixedAssetid);

		}
	}
	//changes eps tree
	var epspopup;
	var addEPSservice = {};
	//eps pencil icon usage
	$scope.editTreeDetails = function(actionType, itemData) {
		$scope.treeFlag = false;
		epspopup = addEPSservice.addTreeDetails(actionType, itemData, $scope.countries);
		epspopup.then(function(data) {
			$scope.assetData= populateAssetsTreeData(data.fixedAssetDtlTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS details  ", 'Error');
		});
	}

	addEPSservice.addTreeDetails = function(actionType, itemData, countries) {
		var deferred = $q.defer();
		epspopup = ngDialog.open({
			template : 'views/projresources/projassetreg/assetdetails/subassetdetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
			scope : $scope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.countries = countries;
				$scope.editTreeData = [];
				var treeData = [];
				if (actionType === 'Add') {
					$scope.editTreeData.push({
					'select':false,
					"deleteFlag" : false,
				     'fixedAssetid':'',
				     'assetId':'',
				     'assetDescription':'',
				      'assetCategoryName':'',
				      "level" : 0,
					  "collapse" : false,
					  "expand" : true,
				      'proj':'',
				      'settingStatus':'',
				      'address':'',
				      'currency':'',
				      'profitCentre':'',
				      'yearBuild':'',
				      'dateCommissioned':'',
				      'assetLifeStatus':'',
				      'ownershipStatus':'',
				      'geonameId':'',
				      'isoAlpha3':'',
				      'countryName':'',
				      'provisionName':'',
				      'assignedStatus':'',
				      'subAssetId':'',
				      'subAssetDescription':'',
				      'subAssetCategory':'',
				      'searchProject':'',
				      'assetCurrentLifeSataus':'',
				      'owenerShipStatus':'',
				       'projectId':'',
					   'projectCode':'',
				       'companyId':'',
				      'childProjs':[],
				       'parentId':''

					});
				} else {
					$scope.editTreeData = [angular.copy(itemData)];
				}
				$scope.editTreeData = TreeService.populateDynamicTreeData($scope.editTreeData, 0, [], 'assetCategoryName',
					'childProjs');

				$scope.addTreeSubGroup = function(tabData, index) {
					let obj = angular.copy({
						"select" : false,
						"parentId" : tabData.fixedAssetid,
						"status" : 1,
						"assignedStatus" : '',
						"deleteFlag" : false,
						"projCode" : '',
						"projName" : '',
						"assetCategoryName" : '',
						"assetDescription" : '',
						"address" :tabData.address,
						"currency" : tabData.currency,
						"profitCentre" :tabData.profitCentre,
						"yearBuild" :'',
						"dateCommissioned" :'',
						"geonameId" :tabData.geonameId,
						"isoAlpha3":tabData.isoAlpha3,
						"countryName":tabData.countryName,
						"provisionName":tabData.provisionName,
						"subAssetId":'',
						"subAssetDescription":'',
						'searchProject':'',
						"subAssetCategory":'',
						 'assetCurrentLifeSataus':'',
					     'owenerShipStatus':'',
						 "projectId":'',
						 'projectCode':'',
						 'companyId':'',
						"level" : (tabData.level + 1),
						"collapse" : false,
						"expand" : true,

						"childProjs" : []
					});
					$scope.editTreeData = TreeService.addItemToTree($scope.editTreeData, tabData, obj, index);
				}
				$scope.deleteTree = function (index) {
					TreeService.deleteDynamicTreeItem($scope.editTreeData, index);
				}

				$scope.poupTreeAssetItemClick = function(item, collapse){
					TreeService.dynamicTreeItemClick($scope.editTreeData, item, collapse);
				}
				$scope.addAssetData = [];
				$scope.currencies = [];
				$scope.reslut=[];
				$scope.getCountryDetailsById = function(item) {
					
					
					const countryName = item.countryName;
					const countryObj = $scope.countries.find(function(country){
						return country.countryName === countryName;
					});

					if(countryObj) {
						item.currency = countryObj.currencyCode;
						const req = { "geonameId": countryObj.geonameId };
						CountryService.getProvince(req).then(function(data) {
								item.provisionTOs = data.provisionTOs;
						}, function(error) {

						if(item.provisionTOs==undefined){
								GenericAlertService.alertMessage(" No Provision for selected country ", "Warning");
						}else{
								if(item.provisionTOs.clientId==undefined){
									GenericAlertService.alertMessage(" No Provision for selected country ", "Warning");
								}else{
								GenericAlertService.alertMessage("Error occured while getting Province details", "Error");
							}
					}
						});
					}
				},
				$scope.getProfitCenterList = function(profitCentreTO,item) {
					var onLoadReq = {
						"status" : 1
					};
					var profitCentrePopup = ProfitCentrePopUpFactory.getProfitCentres(onLoadReq);
					profitCentrePopup.then(function(data) {
						$scope.profitCentreTOId = data.selectedRecord.id;
						$scope.profitCentreTOCode = data.selectedRecord.code;
                        item.profitCentre = data.selectedRecord.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
				
				$scope.getUserProjects = function(item) {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection	.then(function(data) {
						$scope.tableData = [];
						item.searchProject = data.searchProject;
						item.projectId = item.searchProject.projId;
						item.projectCode = item.searchProject.projCode;
									},
									function(error) {
										GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
									});
				},

             //Assets Category Select Popup
				$scope.getAssetCategory = function(assetCategoryTO, item) {
					var assetCategoryService = {};
					var onLoadReq = {
						"status" : 1
					};
					var categoryPopup = AssetCategorySelectFactory.getAssetCategory(onLoadReq);
					categoryPopup.then(function(data) {
						$scope.assetCategoryTOId = data.selectedRecord.id;
						$scope.assetCategoryTOCode = data.selectedRecord.code;
						item.assetCategoryName = data.selectedRecord.name;

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},
				//Assets Sub Category Select Popup

				$scope.getAssetSubCategory = function(assetCategoryTO,item) {
					var assetCategoryService = {};
					var onLoadReq = {
						"status" : 1
					};
					var categoryPopup = AssetCategorySelectFactory.getAssetCategory(onLoadReq);
					categoryPopup.then(function(data) {
						$scope.assetCategoryTOId = data.selectedRecord.id;
						$scope.assetCategoryTOCode = data.selectedRecord.code;
						item.subAssetCategory = data.selectedRecord.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting Profit Center  details", 'Error');
					});
				},


				//save SubAsset
				$scope.saveSubAsset = function(item, epsForm) {
				var flag = false;
				if (actionType === 'Edit') {
						angular.forEach($scope.editTreeData, function(value, key) {

						});
					}
				const data = TreeService.extractTreeDataForSaving  ($scope.editTreeData, 'childProjs');
					var saveAssetDetailsReq = {
							"fixedAssetRegisterTOs" : data
						}
						if(saveAssetDetailsReq.fixedAssetRegisterTOs[0].assetDescription.length != 0){
						blockUI.start();
						AssetRegisterService.saveSubAsset(saveAssetDetailsReq).then(function(data) {
						blockUI.stop();
							if (data.status != null && data.status !== undefined && data.status === 'Info') {
								var results = data.fixedAssetDtlTOs;
								// var succMsg = GenericAlertService.alertMessageModal('Sub Asset detail(s) is/are ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal('Sub Asset detail(s) saved successfully',"Info");
								succMsg.then(function(data1) {
									$scope.closeThisDialog();
									var returnPopObj = {
										"fixedAssetDtlTOs" : results
									};
									deferred.resolve(returnPopObj);

								}, function(error) {
								blockUI.stop();
									GenericAlertService.alertMessage("Error occured while saving Sub Asset details", "Error");
								});
							}
							$scope.getSearchAssetRegisters();
						});
							}
							// else{
							// GenericAlertService.alertMessage('Please Enter the mandatory field',"Error");
							// }
				}
			} ]
		});

		return deferred.promise;
	}

   $scope.deactivateAssetDetails = function() {
		if (deleteTreeData == undefined || deleteTreeData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Asset to deactivate", 'Warning');
			return;
		}
		var assetDeactivateReq = {
			"fixedAssetRegIds" : deleteTreeData
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			AssetRegisterService.deleteAssetRegisters(assetDeactivateReq).then(function(data) {
				GenericAlertService.alertMessage("Asset(s) Deactivated successfully", "Info");
				deleteTreeData = [];
				$scope.getSearchAssetRegisters();
			}, function(error) {
				GenericAlertService.alertMessage(' Asset(s) is/are failed to deactivate', "Error");
			});
		}, function(data) {
			deleteTreeData = [];
			$scope.getSearchAssetRegisters();
		})
	}

	$scope.itemClick = function(item, collapse) {
		TreeService.treeItemClick (item, collapse, 'childProjs');
	};

	$scope.checkAll = function() {
		angular.forEach($scope.assetData, function(tab) {
			if ($scope.selectedAll) {
				tab.selected = false;
			} else {
				tab.selected = true;
			}
		});
	}
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/Enterprisehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}

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
	var grouppagepopup;
	var sortpagepopup;
	commonService.grouping = function () {
		var deferred = $q.defer();
		grouppagepopup = ngDialog.open({
			template: 'views/groupingsorting/resources/immovableassets/assetdetailsgroup.html',
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
			template: 'views/groupingsorting/resources/immovableassets/assetdetailssort.html',
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
