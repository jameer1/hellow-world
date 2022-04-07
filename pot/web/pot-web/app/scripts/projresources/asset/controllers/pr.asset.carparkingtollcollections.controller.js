'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("assetcarparkingtoll", {
		url : '/assetcarparkingtoll',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/carparkingandtollcollections/carparkingtollcollections.html',
				controller : 'CarParkingCollectionController'
			}
		}
	})
}]).controller("CarParkingCollectionController", ["$scope", "$q", "$state", "ngDialog", "GenericAlertService", "AssetRegisterService", "CarParkingTollCollectionsFactory","$rootScope","EmpRegisterService","stylesService", "ngGridService", function($scope, $q, $state, ngDialog,GenericAlertService,AssetRegisterService,CarParkingTollCollectionsFactory,$rootScope,EmpRegisterService, stylesService, ngGridService) {
	var editCarParkingToll = [];
	$scope.tollcollections = [];
	$scope.stylesSvc = stylesService;
//$scope.carparkinglastrecord = [] ;
	$scope.carparkingtollmoreFlag = 0;
   var SelectedCarParkingToll=[];

	$scope.clickForwardCarParkingToll = function(moreFlag) {
		if ($scope.carparkingtollmoreFlag < 1) {
			$scope.carparkingtollmoreFlag = moreFlag + 1;
		}
	}, $scope.clickBackwardCarParkingToll = function(moreFlag) {
		if ($scope.carparkingtollmoreFlag > 0) {
			$scope.carparkingtollmoreFlag = moreFlag - 1;
		}
	}

	var req = {
		"status" : 1,
		"fixedAssetid" : $scope.fixedAssetid,
	};
	AssetRegisterService.carParkingTollCollectionslastrecord(req).then(function(data) {
console.log(' data '+ data.carParkingTollCollectionDtlTOs);
		if (data.carParkingTollCollectionDtlTOs.length == 0 ) {
				$scope.carparkinglastrecord=JSON.stringify(data.carParkingTollCollectionDtlTOs);
		}else{
			$scope.carparkinglastrecord = data.carParkingTollCollectionDtlTOs;

		}
	}, function(error) {
		GenericAlertService.alertMessage("Error occured while fetching car parking toll details", "Error");
	});

	var rentalValueGetReq = {
		"status" : 1,
		"fixedAssetid" : $scope.fixedAssetid
	};

	AssetRegisterService.getRentalValue(rentalValueGetReq).then(function(data) {
		console.log(' data '+ data.rentalValueDtlTOs);
		if (data.rentalValueDtlTOs.length == 0 ) {
		$scope.rentalValueDtlTOs = JSON.stringify(data.rentalValueDtlTOs);
		//console.log('rentalValueDtlTOs :'+ $scope.rentalValueDtlTOs);
	}else{
		$scope.rentalValueDtlTOs = data.rentalValueDtlTOs;
	}
	}, function(error) {
		GenericAlertService.alertMessage("Error occured while fetching Rental Value/Revenue(s) details", "Error");
	});
	//	console.log('rentalValueDtlTOs : after'+ JSON.stringify($scope.rentalValueDtlTOs));

	$scope.getCarParkingToll = function() {

	      var getCarParkingTollReq = {
				"status" : 1,

			};
	      AssetRegisterService.getCarParkingToll(getCarParkingTollReq).then(function(data) {
				$scope.projGenCurrencyLabelKeyTO = data.projGenCurrencyLabelKeyTO;
				$scope.tollcollections = data.assetCostValueStatusTOs;
				if ($scope.tollcollections != null &&
					$scope.tollcollections != undefined  &&
					$scope.tollcollections[0].id != null &&
					$scope.tollcollections[0].id != undefined ){
					$scope.editCarParkingToll  = false;
				}else {
					$scope.editCarParkingToll  = true;
				}
			 }, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching car parking toll details", "Error");
			});

		},
		$scope.carParkingTollCollectionsRecords = function() {
			if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
				GenericAlertService.alertMessage("Please select the sub asset", "Warning");
				return;
			}
			var carParkingTollGetReq = {
				"status" : 1,
				"fixedAssetid" : $scope.fixedAssetid,
			};

			AssetRegisterService.carParkingTollCollectionsRecords(carParkingTollGetReq).then(function(data) {
				$scope.CarParkingTollCollectionDtlTO = data.carParkingTollCollectionDtlTOs;
				$scope.gridOptions.data = angular.copy($scope.CarParkingTollCollectionDtlTO);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while fetching car parking toll details", "Error");
			});
		},
	$scope.carParkingTollSelect = function(carparking) {
		if (carparking.selected) {
			SelectedCarParkingToll.push(carparking);
		} else {
			SelectedCarParkingToll.pop(carparking);
		}
	}

			$scope.createCarParkingToll = function(actionType) {

		if (actionType == 'Edit' && SelectedCarParkingToll <= 0) {
			GenericAlertService.alertMessage('Please select active record to modify', "Warning");
			return;
		}

	//console.log('rentalValueDtlTOs : after'+ JSON.stringify($scope.rentalValueDtlTOs));

		angular.forEach(editCarParkingToll, function(value) {
			value.selected = false;
		});


		var popup = CarParkingTollCollectionsFactory.carParkingDetailsPopUpOnLoad(actionType,SelectedCarParkingToll,$scope.carparkinglastrecord,$scope.rentalValueDtlTOs);
		popup.then(function(data) {
			    $scope.projGenCurrencyLabelKeyTO = data.projGenCurrencyLabelKeyTO;
			    $scope.tollcollections = data.carparkingtollTO;
			    $scope.carParkingTollCollectionsRecords();
			    SelectedCarParkingToll=[];

		},function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching car parking toll details", 'Error');
		});
	}

			$scope.deleteCarParkingToll = function() {
				if (SelectedCarParkingToll.length <= 0) {
					GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
					return;
				}
				var deactivateIds = [];
				if ($scope.selectedAll) {
					$scope.SelectedCarParkingToll = [];
				} else {
					angular.forEach(SelectedCarParkingToll, function(value, key) {
						if (value.id) {
							deactivateIds.push(value.id);
						}
					});
					var carParkingTollDeactiveReq = {
							"carParkingTollCollectionIds" : deactivateIds,
							"status" : 2,
							"fixedAssetid" : $scope.fixedAssetid
						}
					GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
						AssetRegisterService.deletecarparkingToll(carParkingTollDeactiveReq).then(function(data) {
							GenericAlertService.alertMessageModal('Car parking toll record(s) deactivated successfully', 'Info');
							   $scope.carParkingTollCollectionsRecords();
							   SelectedCarParkingToll = [];
						       });
						angular.forEach(SelectedCarParkingToll, function(value, key) {
							$scope.SelectedCarParkingToll.splice($scope.SelectedCarParkingToll.indexOf(value), 1);
							SelectedCarParkingToll = [];
							$scope.deactivateIds = [];
						}, function(error) {
							GenericAlertService.alertMessage('Car parking toll  record(s) is/are  failed to deactivate', "Error");
						});
					}, function(data) {
						angular.forEach(SelectedCarParkingToll, function(value) {
							value.selected = false;
						})
					})

				}
			}
		var linkCellTemplate ='	<input type="checkbox" value="id" ng-model="row.entity.selected" ng-if="row.entity.carParkingTollFileName" ng-change="grid.appScope.carParkingTollSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false },
						{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
						{ field: 'modeOfPayment', displayName: "Mode of Payment", headerTooltip: "Mode of Payment", groupingShowAggregationMenu: false},
						{ field: 'bankName', displayName: "Bank Name", headerTooltip: "Bank Name", groupingShowAggregationMenu: false},
						{ field: 'bankCode', displayName: "Bank Code", headerTooltip: "Bank Code", groupingShowAggregationMenu: false},
						{ field: 'bankAc', displayName: "Bank AC", headerTooltip: "Bank AC", groupingShowAggregationMenu: false},
						{ field: 'transferReceiptNo', displayName: "Transfer Receipt No", headerTooltip: "Transfer Receipt No", groupingShowAggregationMenu: false},
						{ field: 'netAmount', displayName: "Net amount", headerTooltip: "Net amount", groupingShowAggregationMenu: false},
						{ field: 'taxAmount', displayName: "Tax amount", headerTooltip: "Tax amount", groupingShowAggregationMenu: false},
						{ field: 'cumulativeNetAmount', displayName: "Cumulative Net Amount", headerTooltip: "Cumulative Net Amount", groupingShowAggregationMenu: false},
						{ field: 'cumulativeTaxAmount', displayName: "Cumulative Tax Amount", headerTooltip: "Cumulative Tax Amount", groupingShowAggregationMenu: false},
						{ field: 'forecastNetAmt', displayName: "Forecast - Cumulative Net amount", headerTooltip: "Forecast - Cumulative Net amount", groupingShowAggregationMenu: false},
						{ field: 'forecastTaxAmt', displayName: "Forecast - Cumulative Tax amount", headerTooltip: "Forecast - Cumulative Tax amount", groupingShowAggregationMenu: false},
						{ field: 'cumulativeNetRevenue', displayName: "Cumulative Net Revenue", headerTooltip: "Cumulative Net Revenue", groupingShowAggregationMenu: false},
						{ field: 'cumulativeTax', displayName: "Cumulative tax amount", headerTooltip: "Cumulative tax amount", groupingShowAggregationMenu: false},
						{ field: 'carParkingTollFileName', displayName: "Car Parking Toll Records(Upload & View)", headerTooltip: "Car Parking Toll Records(Upload & View)",cellClass: 'justify-center', headerCellClass:"justify-center", groupingShowAggregationMenu: false,
						cellTemplate: '<div ng-click="grid.appScope.downloadCarParkingFile(row.entity.id, row.entity.carParkingTollFileName)" class="fa fa-download">{{carparking.carParkingTollFileName}}</div>'},
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_CarParking");
					$scope.carParkingTollCollectionsRecords();
				}
			});
			$scope.downloadCarParkingFile = function(purchaseRecordId,file_name) {
				//AssetRegisterService.downloadCarParkingFile(purchaseRecordId);
				let req = {
					"recordId" : purchaseRecordId,
					"category" : "Car Parking Actual Revenue",
					"fileName" : file_name
				}
				EmpRegisterService.downloadRegisterDocs(req);
				console.log("downloadCarParkingFile");
			}

}]);
