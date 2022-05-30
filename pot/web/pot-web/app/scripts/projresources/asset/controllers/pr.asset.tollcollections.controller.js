'use strict';
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("assetcarparkingtollcollection", {
        url: '/assetcarparkingtollcollection',
        data: {
            roles: []
        },
        views: {
            'current@': {
                templateUrl: 'views/projresources/projassetreg/tollcollections/tollcollections.html',
                controller: 'TollCollectionController'
            }
        }
    })
}]).controller("TollCollectionController", ["$scope", "$q", "$state", "ngDialog", "GenericAlertService", "AssetRegisterService", "ImmovableTollCollectionsFactory", "$rootScope","EmpRegisterService","stylesService", "ngGridService", function ($scope, $q, $state, ngDialog, GenericAlertService, AssetRegisterService, ImmovableTollCollectionsFactory, $rootScope,EmpRegisterService, stylesService, ngGridService) {
    var editCarParkingToll = [];
    $scope.tollcollections = [];
	$scope.stylesSvc = stylesService;
    //$scope.carparkinglastrecord = [] ;
    $scope.carparkingtollmoreFlag = 0;
    var SelectedCarParkingToll = [];

    $scope.clickForwardCarParkingToll = function (moreFlag) {
        if ($scope.carparkingtollmoreFlag < 1) {
            $scope.carparkingtollmoreFlag = moreFlag + 1;
        }
    }, $scope.clickBackwardCarParkingToll = function (moreFlag) {
        if ($scope.carparkingtollmoreFlag > 0) {
            $scope.carparkingtollmoreFlag = moreFlag - 1;
        }
    }

    var req = {
        "status": 1,
        "fixedAssetid": $scope.fixedAssetid,
    };
    AssetRegisterService.TollCollectionslastrecord(req).then(function (data) {
       if (data.tollCollectionDtlTOs.length == 0) {
            $scope.carparkinglastrecord = JSON.stringify(data.tollCollectionDtlTOs);
            
        } else {
            $scope.carparkinglastrecord = data.tollCollectionDtlTOs;
			$scope.gridOptions.data = angular.copy($scope.carparkinglastrecord);
        }
    }, function (error) {
        GenericAlertService.alertMessage("Error occured while fetching toll details", "Error");
    });

    var rentalValueGetReq = {
        "status": 1,
        "fixedAssetid": $scope.fixedAssetid
    };

    AssetRegisterService.getRentalValue(rentalValueGetReq).then(function (data) {
        console.log(' data ' + data.rentalValueDtlTOs);
        if (data.rentalValueDtlTOs.length == 0) {
            $scope.rentalValueDtlTOs = JSON.stringify(data.rentalValueDtlTOs);
            //console.log('rentalValueDtlTOs :'+ $scope.rentalValueDtlTOs);
        } else {
            $scope.rentalValueDtlTOs = data.rentalValueDtlTOs;
        }
    }, function (error) {
        GenericAlertService.alertMessage("Error occured while fetching Rental Value/Revenue(s) details", "Error");
    });
    //	console.log('rentalValueDtlTOs : after'+ JSON.stringify($scope.rentalValueDtlTOs));

    $scope.getCarParkingToll = function () {

        var getCarParkingTollReq = {
            "status": 1,

        };
        AssetRegisterService.getCarParkingToll(getCarParkingTollReq).then(function (data) {
            $scope.projGenCurrencyLabelKeyTO = data.projGenCurrencyLabelKeyTO;
            $scope.tollcollections = data.assetCostValueStatusTOs;
            if ($scope.tollcollections != null &&
                $scope.tollcollections != undefined &&
                $scope.tollcollections[0].id != null &&
                $scope.tollcollections[0].id != undefined) {
                $scope.editCarParkingToll = false;
            } else {
                $scope.editCarParkingToll = true;
            }
        }, function (error) {
            GenericAlertService.alertMessage("Error occured while fetching toll details", "Error");
        });

    },
        $scope.carParkingTollCollectionsRecords = function () {
            if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
                GenericAlertService.alertMessage("Please select the sub asset", "Warning");
                return;
            }
            var carParkingTollGetReq = {
                "status": 1,
                "fixedAssetid": $scope.fixedAssetid,
            };

            AssetRegisterService.TollCollectionsRecords(carParkingTollGetReq).then(function (data) {
                $scope.tollCollectionDtlTOs = data.tollCollectionDtlTOs;
               
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while fetching toll details", "Error");
            });
        },
        $scope.carParkingTollSelect = function (carparking) {
            if (carparking.selected) {
                SelectedCarParkingToll.push(carparking);
            } else {
                SelectedCarParkingToll.pop(carparking);
            }
        }

    $scope.createTollCollection = function (actionType) {

        if (actionType == 'Edit' && SelectedCarParkingToll <= 0) {
            GenericAlertService.alertMessage('Please select active record to modify', "Warning");
            return;
        }

        //console.log('rentalValueDtlTOs : after'+ JSON.stringify($scope.rentalValueDtlTOs));

        angular.forEach(editCarParkingToll, function (value) {
            value.selected = false;
        });


        var popup = ImmovableTollCollectionsFactory.tollCollectionsDetailsPopUpOnLoad(actionType, SelectedCarParkingToll, $scope.carparkinglastrecord, $scope.rentalValueDtlTOs);
        popup.then(function (data) {
            $scope.projGenCurrencyLabelKeyTO = data.projGenCurrencyLabelKeyTO;
            $scope.tollcollections = data.carparkingtollTO;
            $scope.carParkingTollCollectionsRecords();
            SelectedCarParkingToll = [];

        }, function (error) {
            GenericAlertService.alertMessage("Error occurred while fetching toll details", 'Error');
        });
    }

    $scope.deleteCarParkingToll = function () {
        if (SelectedCarParkingToll.length <= 0) {
            GenericAlertService.alertMessage("Please select atleast one row to deactivate ", 'Warning');
            return;
        }
        var deactivateIds = [];
        if ($scope.selectedAll) {
            $scope.SelectedCarParkingToll = [];
        } else {
            angular.forEach(SelectedCarParkingToll, function (value, key) {
                if (value.id) {
                    deactivateIds.push(value.id);
                }
            });
            var carParkingTollDeactiveReq = {
                "carParkingTollCollectionIds": deactivateIds,
                "status": 2,
                "fixedAssetid": $scope.fixedAssetid
            }
            GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
                AssetRegisterService.deleteToll(carParkingTollDeactiveReq).then(function (data) {
                    GenericAlertService.alertMessageModal('Toll record(s) deactivated successfully', 'Info');
                    $scope.carParkingTollCollectionsRecords();
                    SelectedCarParkingToll = [];
                });
                angular.forEach(SelectedCarParkingToll, function (value, key) {
                    $scope.SelectedCarParkingToll.splice($scope.SelectedCarParkingToll.indexOf(value), 1);
                    SelectedCarParkingToll = [];
                    $scope.deactivateIds = [];
                }, function (error) {
                    GenericAlertService.alertMessage(' toll  record(s) is/are  failed to deactivate', "Error");
                });
            }, function (data) {
                angular.forEach(SelectedCarParkingToll, function (value) {
                    value.selected = false;
                })
            })

        }
    }
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.carParkingTollSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'date', displayName: "Date", headerTooltip: "Date"},
						{ field: 'modeOfPayment', displayName: "Mode of Payment", headerTooltip: "Mode of Payment", },
						{ field: 'bankName', displayName: "Bank Name", headerTooltip: "Bank Name"},
						{ field: 'bankCode', displayName: "Bank Code", headerTooltip: "Bank Code",},
						{ field: 'bankAc', displayName: "Bank AC", headerTooltip: "Bank AC",},
						{ field: 'transferReceiptNo', displayName: "Transfer Receipt No", headerTooltip: "Transfer Receipt No",},
						{ field: 'netAmount', displayName: "Net amount", headerTooltip: "Net amount"},
						{ field: 'taxAmount', displayName: "Tax amount", headerTooltip: "Tax amount", },
						{ field: 'cumulativeNetAmount', displayName: "Cumulative Net Amount", headerTooltip: "Cumulative Net Amount"},
						{ field: 'cumulativeTaxAmount', displayName: "Cumulative Tax Amount", headerTooltip: "Cumulative Tax Amount",},
						{ field: 'forecastNetAmt', displayName: "Forecast - Cumulative Net amount", headerTooltip: "Forecast - Cumulative Net amount",},
						{ field: 'forecastTaxAmt', displayName: "Forecast - Cumulative Tax amount", headerTooltip: "Forecast - Cumulative Tax amount",},
						{ field: 'cumulativeNetRevenue', displayName: "Cumulative Net Revenue", headerTooltip: "Cumulative Net Revenue",},
						{ field: 'cumulativeTax', displayName: "Cumulative tax amount", headerTooltip: "Cumulative tax amount",},
				    	{ name: 'Car Parking', displayName: "Car Parking Toll Records(Upload & View)", headerTooltip: "Car Parking Toll Records(Upload & View)",cellClass: 'justify-center', headerCellClass:'justify-center',
						cellTemplate: '<div  class="fa fa-download" ng-if="row.entity.tollFileName"  ng-click="grid.appScope.downloadTollParkingFile(row.entity.id, row.entity.tollFileName)"  ></div>'},
						  
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Immovableassets_TollCollection");
					$scope.gridOptions.exporterPdfOrientation = 'landscape';
					$scope.gridOptions.exporterPdfPageSize = 'A3';
					$scope.gridOptions.exporterPdfMaxGridWidth = 910;
					$scope.carParkingTollCollectionsRecords();
				}
			});
			$scope.downloadTollParkingFile = function(TollId,file_name) {
		console.log(TollId)
		console.log(file_name)
		//AssetRegisterService.downloadShortTermRecordFile(TollId);
		let req = {
			"recordId" : TollId,
			"category" : "Toll Collections Actual Revenue",
			"fileName" : file_name
		}
		EmpRegisterService.downloadRegisterDocs(req);
		console.log("downloadTollParkingFile");
	}
   

}]);
