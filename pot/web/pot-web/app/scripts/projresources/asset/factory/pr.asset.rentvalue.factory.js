'use strict';
app.factory('RentValueFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,generalservice,assetidservice) {
	var rentValuePopUp;
	var service = {};
	service.rentValuePopUp = function(actionType, editRentalValueData) {
		var deferred = $q.defer();
		rentValuePopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/rentalvalue/rentalvaluepopup.html',
			closeByDocument : false,
			showClose : false,
			className  : 'ngdialog-theme-plain ng-dialogueCustom2',
			controller : [ '$scope', function($scope) {

				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.addRentValueData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.rentalValue = [];
				var selectedRentValueData = [];
				$scope.assetCurrentLifeSatausList = generalservice.lifeStatuses;
				$scope.owenerShipStatusList = generalservice.ownershipStatuses;
				$scope.revenueCollectionCycleList = generalservice.revenueCycles;
				$scope.fixedOrRentIncomeList = generalservice.rents;
				$scope.applicableRevenueList = generalservice.revenueForecasts;	                         
                if(actionType == 'Edit'){         
                	$scope.owenerShipStatusList = getOwnershipStatusList(editRentalValueData[0].assetCurrentLifeSataus);
                	$scope.applicableRevenueList = getRevenueCollectionCycleList(editRentalValueData[0].owenerShipStatus);
                }                                                      
                $scope.changeSelection=function(asset){
                     $scope.owenerShipStatusList = getOwnershipStatusList(asset);            
                }             
                function getOwnershipStatusList(lifestatus){
                  if(lifestatus=='Operational Phase'){             
                  	return['On Lease','Vacant'];
                  }
                  if(lifestatus=='Under Renovation/Repairs'){             
                  	return ['Vacant'];
                  }
                  if(lifestatus=='Construction Phase'){
                  	return['On Lease','Vacant','Sold Out','Not Applicable'];
                  }
                  if(lifestatus=='Commissioning Phase'){
                    return['On Lease','Vacant','Sold Out','Not Applicable'];
                  }
                  if(lifestatus=='Design Phase'){
                  	return['On Lease','Vacant','Sold Out','Not Applicable'];
                  }
                  if(lifestatus=='Acquisition Phase'){
                  	return['On Lease','Vacant','Not Applicable'];
                  }
                }                            
               $scope.changeValues = function(asset){
                 $scope.applicableRevenueList = getRevenueCollectionCycleList(asset);
               }
              function getRevenueCollectionCycleList(statusList){
               if(statusList=='Sold Out'){
                   return['No'];
                 }
                 if(statusList=='On Lease'){
                   return['Yes'];
                 }
                 if(statusList=='Vacant'){
                   return['Yes'];
                 }
                 if(statusList=='Not Applicable'){
                   return['Yes'];
                 }
               }
				var addRental = {
						"id" : null,
						'effectiveDate' : null,
						'assetCurrentLifeSataus' : null,
						'owenerShipStatus' : null,
						'revenueCollectionCycle' : null,
						'fixedOrRentIncome' : null,
						'estimatedRentPerCycle' : null,
						'applicableRevenue' : null,
						"status" : "1",
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"fixedAssetid" : $scope.fixedAssetid
					};

				if (actionType === 'Add') {
					$scope.rentalValue.push(addRental);
				} else {
					$scope.rentalValue = angular.copy(editRentalValueData)
				}

				$scope.addRows = function() {
					$scope.rentalValue.push({
						"id" : null,
						'effectiveDate' : null,
						'assetCurrentLifeSataus' : null,
						'owenerShipStatus' : null,
						'revenueCollectionCycle' : null,
						'fixedOrRentIncome' : null,
						'estimatedRentPerCycle' : null,
						'applicableRevenue' : null,
						"status" : "1",
						"plantCommissioningDate":$scope.plantCommissioningDate,
						"fixedAssetid" : $scope.fixedAssetid

					});
				},

				$scope.assetRentValuePopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedRentValueData.push(asset);
					} else {
						selectedRentValueData.pop(asset);
					}
				},

				$scope.deleteRows = function() {
					if (selectedRentValueData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedRentValueData.length < $scope.rentalValue.length) {
						angular.forEach(selectedRentValueData, function(value, key) {
							$scope.rentalValue.splice($scope.rentalValue.indexOf(value), 1);
						});
						selectedRentValueData = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedRentValueData.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedRentValueData.length == 1) {
						$scope.rentalValue[0] = {
							"status" : "1",
							"selected" : false,
							'effectiveDate' : null,
							'assetCurrentLifeSataus' : null,
							'owenerShipStatus' : null,
							'revenueCollectionCycle' : null,
							'fixedOrRentIncome' : null,
							'estimatedRentPerCycle' : null,
							'applicableRevenue' : null,
							"plantCommissioningDate":$scope.plantCommissioningDate,
							"fixedAssetid" : $scope.fixedAssetid

						};
						selectedRentValueData = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},
				$scope.selectowenershipstatus=function(asset){

				
				}
				$scope.selectassettcurrent=function(owenerShipStatusList,owenerShipStatus,asset){
				//	alert(owenerShipStatusList)
					var owenerShipStatusList1=[];
					$scope.contact = {};

	// $scope.owenerShipStatus = ""
					console.log(JSON.stringify(owenerShipStatusList.length))
					console.log(JSON.stringify(owenerShipStatus))
					console.log(JSON.stringify(asset))
				//	for (var i = 0; i < owenerShipStatusList.length; i++) {
        //	if (i <= index) {
          //  owenerShipStatusList[i].removeAttribute('disabled');
        //	} else {
				//owenerShipStatusList[i].isDisabled = true;

				//owenerShipStatusList1.push(owenerShipStatusList[i])

				//alert(owenerShipStatusList1)

        //	}
    		//}
				console.log(JSON.stringify(owenerShipStatusList1))
				}
				$scope.save = function() {
					var RentalValueSaveReq = {
						"rentalValueDtlTOs" : $scope.rentalValue
						};
					    AssetRegisterService.saveRentalValue(RentalValueSaveReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Rent Value Detail(s) Saved Successfully','Info');
							succMsg.then(function() {
							var returnPopObj = {
								"rentalValueDtlTOs" : data.rentalValueDtlTOs
							};
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						if (error.status != null && error.status != undefined) {
							GenericAlertService.alertMessageModal('Asset Rent Value Detail(s) is/are Failed to Save/Update ', "Error");
						}
					});
					    ngDialog.close();
				}
			} ]
		});
		return deferred.promise;
	}

	service.rentValuePopUpOnLoad = function(actionType, editRentalValueData) {
		var deferred = $q.defer();
		var rentValuePopUp = service.rentValuePopUp(actionType, editRentalValueData);
		rentValuePopUp.then(function(data) {

			var returnPopObj = {
				"rentalValueDtlTOs" : data.rentalValueDtlTOs
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}

	return service;
}]);
