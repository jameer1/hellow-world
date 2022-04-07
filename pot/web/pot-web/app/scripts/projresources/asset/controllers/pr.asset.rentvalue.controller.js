'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetrent", {
		url : '/assetrent',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/rentalvalue/rentalvalue.html',
				controller : 'AssetRentValueController'
			}
		}
	})
}]).controller("AssetRentValueController", ["$rootScope", "$scope", "$q", "$state", "$location", "ngDialog", "RentValueFactory", "AssetRegisterService", "GenericAlertService", "assetidservice","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $location,ngDialog, RentValueFactory,AssetRegisterService, GenericAlertService,assetidservice, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	var selectedRentals = [];
	$scope.fixedAssetid = assetidservice.fixedAssetId;

	$scope.getRentalValue = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		var rentalValueGetReq = {
			"status" : 1,
			"fixedAssetid" : $scope.fixedAssetid
		};

		AssetRegisterService.getRentalValue(rentalValueGetReq).then(function(data) {
			$scope.rentalValueDtlTOs = data.rentalValueDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.rentalValueDtlTOs);
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
			GenericAlertService.alertMessage("Error occured while fetching Rental Value/Revenue(s) details", "Error");
		});
	},


	$scope.rentalRecordPopUpRowSelect = function(rental) {
		if (rental.selected) {
			selectedRentals.push(rental);
		} else {
			selectedRentals.splice(selectedRentals.indexOf(rental), 1);
		}
	},

	$scope.deleteRentValue = function() {
		if (selectedRentals.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
			return;
		}
		var deactivateIds = [];
		if ($scope.selectedAll) {
			$scope.selectedRentals = [];
		} else {
			angular.forEach(selectedRentals, function(value, key) {
				if (value.id) {
					deactivateIds.push(value.id);
				}
			});
			var deactivateRentalReq = {
					"rentalValueIds" : deactivateIds,
					"status" : 2,
					"fixedAssetid" : $scope.fixedAssetid
				}
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				AssetRegisterService.deleteRentalRegisters(deactivateRentalReq).then(function(data) {
					$scope.getRentalValue();
					selectedRentals = [];
				       });
				var succMsg = GenericAlertService.alertMessageModal('Rentals deactivation is'+ data.message, data.status);
				GenericAlertService.alertMessageModal('Rental Value/Revenue(s) is/are   deactivated successfully', 'Info');
				angular.forEach(selectedRentals, function(value, key) {
					$scope.selectedRentals.splice($scope.selectedRentals.indexOf(value), 1);
					selectedRentals = [];
					$scope.deactivateIds = [];

				}, function(error) {
					GenericAlertService.alertMessage('Rental Value/Revenue(s) is/are  failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(selectedRentals, function(value) {
					value.selected = false;
				})
					selectedRentals = [];
			})

		}
	},
	$scope.addRentValueList = function(actionType) {

		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}

		angular.forEach(selectedRentals, function(value, key) {
			value.selected=false;
		});
		if(selectedRentals.length >0 && actionType=="Add"){
			selectedRentals=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
		return;
		}
		if (actionType == 'Edit' && selectedRentals.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
			var rentvaluepopup = RentValueFactory.rentValuePopUpOnLoad(actionType, selectedRentals);
			rentvaluepopup.then(function(data) {
				$scope.rentalValueDtlTOs = data.rentalValueDtlTOs;
				$scope.getRentalValue();				
				selectedRentals=[];								
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching Rental Value/Revenue(s) details", 'Error');
			});



	}

	$scope.$on("resetRevenueFromAssetSales", function() {
		$scope.revenueSalesDtlTOs = [];
	});
	$rootScope.$on('rentalfromassetsales', function (event) {

		if($scope.fixedAssetid){
			$scope.getRentalValue();
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
	var grouppagepopup;
	var sortpagepopup;
	commonService.grouping = function () {
		var deferred = $q.defer();
		grouppagepopup = ngDialog.open({
			template: 'views/groupingsorting/resources/immovableassets/rentalvaluegroup.html',
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
			template: 'views/groupingsorting/resources/immovableassets/rentalvaluesort.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rentalRecordPopUpRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" , groupingShowAggregationMenu: false },
						{ field: 'effectiveDate', displayName: "Effective Date", headerTooltip: "Effective Date", groupingShowAggregationMenu: false },
						{ field: 'assetCurrentLifeSataus', displayName: "Asset Current Life Status", headerTooltip: "Asset Current Life Status",groupingShowAggregationMenu: false  },
						{ field: 'owenerShipStatus', displayName: "Revenue Status", headerTooltip: "Revenue Status", groupingShowAggregationMenu: false },
						{ field: 'revenueCollectionCycle', displayName: "Revenue Collection Cycle", headerTooltip: "Revenue Collection Cycle", groupingShowAggregationMenu: false },
						{ field: 'fixedOrRentIncome', displayName: "Fixed or Variable Rent/Income", headerTooltip: "Fixed or Variable Rent/Income", groupingShowAggregationMenu: false },
						{ field: 'estimatedRentPerCycle', displayName: "Estimated Rent per Cycle(Amount)", headerTooltip: "Estimated Rent per Cycle(Amount)", groupingShowAggregationMenu: false },
						{ field: 'applicableRevenue', displayName: "Applicable for Revenue Forecast", headerTooltip: "Applicable for Revenue Forecast", groupingShowAggregationMenu: false },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_RentalValue");
					$scope.getRentalValue();
				}
			});
}]);
