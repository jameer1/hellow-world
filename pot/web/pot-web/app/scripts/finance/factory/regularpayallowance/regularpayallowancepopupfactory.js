'use strict';

app.factory('RegularPayAllowanceFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService", "RegularPayAllowancePastRecordsFactory", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, UnitPayRateService, GenericAlertService, RegularPayAllowancePastRecordsFactory) {
	var regularPayAllowancePopup;
	var selectedNonRegularPayAllowance = [];
	var service = {};
	service.regularPayAllowanceDetails = function(actionType,countryName,financeCenterRecords) {
		var deferred = $q.defer();
		regularPayAllowancePopup = ngDialog.open({
			template : 'views/finance/regularpayallowance/regularpayallowancepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				let editRegularPay = [];
				editRegularPay = financeCenterRecords.regularPayAllowances; 
				$scope.action = actionType;
				if(actionType == 'View'){
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
					$scope.regularPayCycleDisplayId=financeCenterRecords.regularPayAllowances[0].regularPayCycleDisplayId
				}
				else {
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
				}
				var selectedUnitPayRate = [];
				var selectedRegularPayRate = [];
				$scope.regularPays = [];
				if (actionType === 'Add' && financeCenterRecords.regularPayAllowances =="") {
					$scope.regularPays.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'code' : null,
						'description' : null,
						'isTaxable' : null,
						'notes' : null,
						'status' : 1
					});
				}else if (actionType === 'Edit' && financeCenterRecords.regularPayAllowances!=""){
					$scope.regularPays = angular.copy(financeCenterRecords.regularPayAllowances);
					for ( var i=0; i< $scope.regularPays.length;i++ ){
					$scope.regularPays[i].actionType='View';
				     }
				     $scope.actionTypeSave='Edit';
 					 //$scope.actionTypeSave='View';
				     editRegularPay = [];
					
				} else if(actionType === 'View') {
					//console.log("====actionType====",actionType);
					//console.log(financeCenterRecords);
					$scope.regularPays = angular.copy(financeCenterRecords.regularPayAllowances);
					//console.log("====V1====",$scope.regularPays.length);
					for ( var i=0; i< $scope.regularPays.length;i++ ){
						//console.log("====V2====");
					$scope.regularPays[i].actionType='View';
					//console.log("====V3====");
				     }
				     $scope.actionTypeSave='View';
//console.log("====V4====");
				     editRegularPay = [];
//console.log("====V5====");
				}
				else {
					$scope.regularPays = angular.copy(editUnitPayRate);
					for ( var i=0; i< $scope.regularPays.length;i++ ){
					$scope.regularPays[i].actionType='Edit';
				     }
					$scope.actionTypeSave='Edit';
					editRegularPay = [];
				}
				$scope.taxable = [ {
					id : 1,
					name : "Yes"
				}, {
					id : 2,
					name : "No"
				}];
				$scope.addRows = function() {

					$scope.regularPays.push({
					//	$scope.editRegularPay.push({
						'selected' : false,
						'code' : null,
						'description' : null,
						'isTaxable' : null,
						'notes' : null,
						'status' : 1

					});
				},

				$scope.saveRegularPayRate = function() {
					$scope.closeThisDialog();
					deferred.resolve($scope.regularPays);
				}

				$scope.regularPayPopupRowSelect = function(regularPay) {
					if (regularPay.selected) {
						selectedRegularPayRate.push(regularPay);
					} else {
						selectedRegularPayRate.pop(regularPay);
					}
				}, $scope.deleteRows = function() {
					if (selectedRegularPayRate.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedRegularPayRate.length < $scope.regularPays.length) {
						//if (selectedRegularPayRate.length < $scope.addRegularPayrates.length) {
						angular.forEach(selectedRegularPayRate, function(value, key) {
							$scope.regularPays.splice($scope.regularPays.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedRegularPayRate = [];
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
						var popupUnitOfPayRate = RegularPayAllowancePastRecordsFactory.regularPayAllowancePastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
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