'use strict';

app.factory('UnitPayRateFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "UnitPayRateViewPastRecordsFactory", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, UnitPayRateService, GenericAlertService, UnitPayRateViewPastRecordsFactory) {
	var unitPayRatePopup;
	var selectedUnitPayRate = [];
	var service = {};
	service.unitPayPopupDetails = function(actionType,countryName,financeCenterRecords,editUnitPayRate) {
		var deferred = $q.defer();
		unitPayRatePopup = ngDialog.open({
			template : 'views/finance/unitpayratespopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.projCostStmtDtls = [];
				
			//	log.console("==financeCenterRecords==",financeCenterRecords);
				if(actionType == 'View'){
					$scope.countryName = financeCenterRecords.countryName;
					//console.log($scope.countryName)
					$scope.provisionName = financeCenterRecords.provisionName;
					//console.log($scope.provisionName)
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					//console.log($scope.effectiveDate)
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
					$scope.unitOfPayDisplayId=financeCenterRecords.unitOfPayRates[0].unitOfPayDisplayId
				}
				else {
					$scope.countryName = countryName;
					//console.log($scope.countryName)
					$scope.provisionName = financeCenterRecords.provisionName;
					//console.log($scope.provisionName)
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
				}
				var selectedUnitPayRate = [];
				$scope.unitOfPayRates = [];
				if (actionType === 'Add' && financeCenterRecords.unitOfPayRates =="") {
					$scope.unitOfPayRates.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'code' : null,
						'name' : null,
						'notes' : null,
						'status' : 1,
						'UnitOfPayDisplayId':null
					});
				} else if (actionType === 'Edit' && financeCenterRecords.unitOfPayRates!=""){
					$scope.unitOfPayRates = angular.copy(financeCenterRecords.unitOfPayRates);
					for ( var i=0; i< $scope.unitOfPayRates.length;i++ ){
						$scope.unitOfPayRates[i].actionType='View';
					}
					$scope.actionTypeSave='Edit';
					// $scope.actionTypeSave='View';
					editUnitPayRate = [];
					
				}
				else if(actionType === 'View') {
					//console.log(financeCenterRecords.unitOfPayRates)
					$scope.unitOfPayRates = angular.copy(financeCenterRecords.unitOfPayRates);
					for ( var i=0; i< $scope.unitOfPayRates.length;i++ ){
					$scope.unitOfPayRates[i].actionType='View';
				     }
				     $scope.actionTypeSave='View';
					editUnitPayRate = [];
				}
				else {
					$scope.unitOfPayRates = angular.copy(editUnitPayRate);
					for ( var i=0; i< $scope.unitOfPayRates.length;i++ ){
					$scope.unitOfPayRates[i].actionType='Edit';
				     }
					$scope.actionTypeSave='Edit';
					editUnitPayRate = [];
				}
				$scope.addRows = function() {

					$scope.unitOfPayRates.push({
						'selected' : false,
						'code' : null,
						'name' : null,
						'notes' : null,
						'status' : 1

					});
				},

				$scope.saveUnitPayRate = function() {
					//console.log("------")
					//console.log("--unitOfPayRates----",$scope.unitOfPayRates)
					$scope.closeThisDialog();
					deferred.resolve($scope.unitOfPayRates);
				}

				$scope.unitPayRatePopupRowSelect = function(unitPay) {
					//console.log("unitPay",unitPay)
					if (unitPay.selected) {
						selectedUnitPayRate.push(unitPay);
						//console.log("====if block====")
					} else {
						//console.log("====else block=====")
						selectedUnitPayRate.pop(unitPay);
					}
				}, $scope.deleteRows = function() {
					//console.log("selectedUnitPayRate.length",selectedUnitPayRate.length)
					if (selectedUnitPayRate.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					//if (selectedUnitPayRate.length < $scope.addUnitPayrates.length) {
						if (selectedUnitPayRate.length < $scope.unitOfPayRates.length) {
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						angular.forEach(selectedUnitPayRate, function(value, key) {
							$scope.unitOfPayRates.splice($scope.unitOfPayRates.indexOf(value), 1);
							//GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedUnitPayRate = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				
				
				// view past records 
				$scope.viewpastrecords = function () {
					
					var financeCenterFilterReq = {
						"status": 1,
		       			"countryCode": $scope.countryCode,
						"provisionCode": $scope.provisionCode,
						//$scope.countryId
					};
					UnitPayRateService.getUnitOfRate(financeCenterFilterReq).then(function (data) {
						$scope.projCostStmtDtls = data.financeCenterRecordTos;
						$scope.financeCenterRecordsData = data.financeCenterRecordTos;
						//console.log("$scope.financeCenterRecordsData",$scope.financeCenterRecordsData) 
						//console.log("$scope.projCostStmtDtls",$scope.projCostStmtDtls) 
						//$scope.financeCenterRecordsData
						var popupUnitOfPayRate = UnitPayRateViewPastRecordsFactory.unitPayRatePastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
						//console.log("popupUnitOfPayRate",popupUnitOfPayRate)
						popupUnitOfPayRate.then(function (data) {
							//console.log("popupUnitOfPayRate==",popupUnitOfPayRate)
							//console.log("data==",data)
	
						},
						function (error) {
							GenericAlertService.alertMessage("Error occurred while getting Unit of Pay Rate Details",'Info');
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting finance center details", "Error");
					});
					
					
				}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);