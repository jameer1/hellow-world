'use strict';

app.factory('NonRegularPayAllowanceFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "NonRegularPayAllowancePastRecordsFactory", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, UnitPayRateService, GenericAlertService, NonRegularPayAllowancePastRecordsFactory) {
	var nonRegularPayAllowancePopup;
	var selectedNonRegularPayAllowance = [];
	var service = {};
	service.nonRegularPayAllowanceDetails = function(actionType,countryName,financeCenterRecords) {
		console.log("financeCenterRecords",financeCenterRecords);
		var deferred = $q.defer();
		nonRegularPayAllowancePopup = ngDialog.open({
			template : 'views/finance/nonregularpayallowance/noregularpayallowancepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				let editNonRegularPay = [];
				//editNonRegularPay = financeCenterRecords.nonRegularPayAndAllowances;
				editNonRegularPay = financeCenterRecords.nonRegularPayAllowances;
				$scope.action = actionType;
				//console.log($scope.action);
				if(actionType == 'View'){
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					//console.log($scope.provisionName);
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
					$scope.nonRegularPayCycleDisplayId = financeCenterRecords.nonRegularPayAllowances[0].nonRegularPayCycleDisplayId;
				}
				
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
				
				var selectedNonRegularPayRate = [];
				$scope.nonRegularPays = [];
				if (actionType === 'Add' && financeCenterRecords.nonRegularPayAllowances =="") {
					$scope.nonRegularPays.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'code' : null,
						'description' : null,
						'isTaxable' : null,
						'notes' : null,
						'status' : 1
					});
				}else if (actionType === 'Add' && financeCenterRecords.nonRegularPayAllowances!=""){
					$scope.nonRegularPays = angular.copy(financeCenterRecords.nonRegularPayAllowances);
					for ( var i=0; i< $scope.nonRegularPays.length;i++ ){
					$scope.nonRegularPays[i].actionType='View';
				     }
				     $scope.actionTypeSave='Edit';
// $scope.actionTypeSave='View';
				     editNonRegularPay = [];
					
				} else if(actionType === 'View') {
					//console.log(actionType);
					//console.log(financeCenterRecords);
					//console.log("lrngth",financeCenterRecords.nonRegularPayAllowances.length);
					$scope.nonRegularPays = angular.copy(financeCenterRecords.nonRegularPayAllowances);
					//console.log("====V1====",$scope.nonRegularPays);
					for ( var i=0; i< $scope.nonRegularPays.length;i++ ){
						//console.log("====V2====",$scope.nonRegularPays.length);
					$scope.nonRegularPays[i].actionType='View';
					//console.log("====V3====");
				     }
				     $scope.actionTypeSave='View';
					//console.log("====V4====");
				     editNonRegularPay = [];
					//console.log("====V5====");
				}
				else {
					$scope.nonRegularPays = angular.copy(editNonRegularPay);
					for ( var i=0; i< $scope.nonRegularPays.length;i++ ){
					$scope.nonRegularPays[i].actionType='Edit';
				     }
					$scope.actionTypeSave='Edit';
					editNonRegularPay = [];
				}
				$scope.taxable = [ {
					id : 1,
					name : "Yes"
				}, {
					id : 2,
					name : "No"
				}];
				$scope.addRows = function() {

					$scope.nonRegularPays.push({
						'selected' : false,
						'code' : null,
						'description' : null,
						'isTaxable' : null,
						'notes' : null,
						'status' : 1

					});
				},

				$scope.saveNonRegularPayPayRate = function() {
					$scope.closeThisDialog();
					//deferred.resolve($scope.editNonRegularPay);
					
					deferred.resolve($scope.nonRegularPays);
					//console.log("===A1====");
				}

				$scope.nonRegularPayPopupRowSelect = function(nonRegularPay) {
					//console.log("===A2====");
					if (nonRegularPay.selected) {
						//console.log("===A3====");
						selectedNonRegularPayRate.push(nonRegularPay);
					} else {
						//console.log("===A4====");
						selectedNonRegularPayRate.pop(nonRegularPay);
					}
				}, $scope.deleteRows = function() {
					if (selectedNonRegularPayRate.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedNonRegularPayRate.length < $scope.nonRegularPays.length) {
						//if (selectedNonRegularPayRate.length < $scope.addUnitPayrates.length) {
						angular.forEach(selectedNonRegularPayRate, function(value, key) {
							$scope.nonRegularPays.splice($scope.nonRegularPays.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedNonRegularPayRate = [];
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
						
						var popupUnitOfPayRate = NonRegularPayAllowancePastRecordsFactory.nonRegularPayAllowancePastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
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
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);