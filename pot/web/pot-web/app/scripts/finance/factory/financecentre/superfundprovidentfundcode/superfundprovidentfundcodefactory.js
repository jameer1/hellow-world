'use strict';

app.factory('SuperFundProvidentFundFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "$rootScope", "UnitPayRateService", "GenericAlertService","generalservice", "SuperFundProvidentFundPastRecordsFactory", function(ngDialog, $q, $filter, $timeout,blockUI, $rootScope, UnitPayRateService, GenericAlertService,generalservice, SuperFundProvidentFundPastRecordsFactory) {
	var superProvidentFundPopup;
	var selectedNonRegularPayAllowance = [];
	var service = {};
	service.superProvidentFundDetails = function(actionType,countryName,financeCenterRecords) {
		var deferred = $q.defer();
		superProvidentFundPopup = ngDialog.open({
			template : 'views/finance/superfundprovidentfund/superfundprovidentcodecodes.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				let editSuperProvidentFund = [];
        		editSuperProvidentFund = financeCenterRecords.superFundCodes;
				//console.log("editSuperProvidentFund",editSuperProvidentFund)		
				$scope.action = actionType;
				$scope.projCostStmtDtls = [];
				//console.log($scope.action);
				
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
					if(actionType == 'View'){
					$scope.superProvidentFundDisplayId=financeCenterRecords.superFundCodes[0].superProvidentFundDisplayId
					//console.log($scope.superProvidentFundDisplayId)
				}
					
				
				var selectedUnitPayRate = [];
				var selectedSuperProvidentFund = [];
				$scope.superFunds = [];
				if (actionType === 'Add' && financeCenterRecords.superFundCodes =="") {
					//console.log("actionType==add",actionType)
					$scope.superFunds.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'code' : null,
						'description' : null,
						'isTaxable' : null,
						'creditRunCycle' : null,
                        'creditRunDueDate' : null,
						'status' : 1
					});
					//console.log("$scope.superFunds",$scope.superFunds)
				}else if (actionType === 'Add' && financeCenterRecords.superFundCodes!=""){
					//console.log("actionType==loop",actionType)
					$scope.superFunds = angular.copy(financeCenterRecords.superFundCodes);
					//console.log("$scope.superFunds",$scope.superFunds)
					//console.log("actionType==length",$scope.superFunds.length)
					for ( var i=0; i< $scope.superFunds.length;i++ ){
					$scope.superFunds[i].actionType='Edit';
				     }
				     $scope.actionTypeSave='Edit';
         			 editSuperProvidentFund = [];

				} else if(actionType === 'View') {
					$scope.superFunds = angular.copy(financeCenterRecords.superFundCodes);
					for ( var i=0; i< $scope.superFunds.length;i++ ){
					$scope.superFunds[i].actionType='View';
				     }
				     $scope.actionTypeSave='View';
          			 editSuperProvidentFund = [];
				}
				else {
					$scope.superFunds = angular.copy(editSuperProvidentFund);
					for ( var i=0; i< $scope.superFunds.length;i++ ){
					 $scope.superFunds[i].actionType='Edit';
					 if($scope.superFunds.length > 0){
					
					 if($scope.superFunds[i].creditRunCycle == "Weekly")
					{
						$scope.superFunds[i].creditRunDueDates = generalservice.weeakDays
					}else if($scope.superFunds[i].creditRunCycle == "Fortnightly")
					{
						$scope.superFunds[i].creditRunDueDates = generalservice.weeakDays
					}else if($scope.superFunds[i].creditRunCycle == "Monthly")
					{
						$scope.superFunds[i].creditRunDueDates = generalservice.monthly
					}else if($scope.superFunds[i].creditRunCycle == "Quarterly")
					{
						$scope.superFunds[i].creditRunDueDates = generalservice.years
					}
					else if($scope.superFunds[i].creditRunCycle == "Half Yearly")
					{
						$scope.superFunds[i].creditRunDueDates = generalservice.years
					}
					else if($scope.superFunds[i].creditRunCycle == "Yearly")
					{
						$scope.superFunds[i].creditRunDueDates = generalservice.years
					}
						}
				     }
				     
					$scope.actionTypeSave='Edit';
            		editSuperProvidentFund = [];
				}
				$scope.taxable = [ {
					id : 1,
					name : "Yes"
				}, {
					id : 2,
					name : "No"
				}];
				
				$scope.creditRunCycles = [ {
					id : 1,
					name : "Weekly"
				}, {
					id : 2,
					name : "Fortnightly"
				},
				{
					id : 3,
					name : "Monthly"
				},
				{
					id : 4,
					name : "Quarterly"
				},
				{
					id : 5,
					name : "Half Yearly"
				},
				{
					id : 6,
					name : "Yearly"
				}
				];
                    $scope.displayFundCreditCycle = function(superFund) {
					
					if(superFund.creditRunCycle == "Weekly")
					{
						superFund.creditRunDueDates = generalservice.weeakDays
					}else if(superFund.creditRunCycle == "Fortnightly")
					{
						superFund.creditRunDueDates = generalservice.weeakDays
					}else if(superFund.creditRunCycle == "Monthly")
					{
						superFund.creditRunDueDates = generalservice.monthly
					}else if(superFund.creditRunCycle == "Quarterly")
					{
						superFund.creditRunDueDates = generalservice.years
					}
					else if(superFund.creditRunCycle == "Half Yearly")
					{
						superFund.creditRunDueDates = generalservice.years
					}
					else if(superFund.creditRunCycle == "Yearly")
					{
						superFund.creditRunDueDates = generalservice.years
					}
				}

				$scope.addRows = function() {

		 $scope.superFunds.push({
             "actionType":'Add',
             'selected' : false,
             'clientId' : null,
             'code' : null,
             'description' : null,
             'isTaxable' : null,
             'creditRunCycle' : null,
             'creditRunDueDate' : null,
             'status' : 1
					 });
				},

				$scope.saveSuperProvidentFund = function() {
					$scope.closeThisDialog();
					deferred.resolve($scope.superFunds);
					//console.log("$scope.superFunds",$scope.superFunds)
				}

				$scope.superProvidentFundPopupRowSelect = function(superProvidentFund) {
					if (superProvidentFund.selected) {
						selectedSuperProvidentFund.push(superProvidentFund);
					} else {
            			selectedSuperProvidentFund.pop(superProvidentFund);
					}
				}, $scope.deleteRows = function() {
					if (selectedSuperProvidentFund.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedSuperProvidentFund.length < $scope.superFunds.length) {
						angular.forEach(selectedSuperProvidentFund, function(value, key) {
							$scope.superFunds.splice($scope.superFunds.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
            selectedSuperProvidentFund = [];
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
						var popupUnitOfPayRate = SuperFundProvidentFundPastRecordsFactory.superFundProvidentFundPastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
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
