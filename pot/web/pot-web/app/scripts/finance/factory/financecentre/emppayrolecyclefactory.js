'use strict';
app.factory('EmpRoleFactory', ["ngDialog", "$q", "$rootScope", "blockUI", "GenericAlertService", "payRoleCyclePreviousRecordsfactory", "EmpRegisterService", "generalservice", "UnitPayRateService", "EmpPayRoleCyclePastRecordsFactory", function(ngDialog, $q, $rootScope,blockUI, GenericAlertService,payRoleCyclePreviousRecordsfactory,EmpRegisterService,generalservice,UnitPayRateService,EmpPayRoleCyclePastRecordsFactory) {
	var empPayRole;
	var service = {};
	service.empPayRole = function(actionType,countryName,financeCenterRecords,editEmployeepayRoll) {
		var deferred = $q.defer();
		empPayRole = ngDialog.open({
			template :'views/finance/financecentre/emppayrolecyclepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3', 
			scope : $rootScope,
			closeByDocument : false,
			showClose : false,


			controller : [ '$scope', function($scope) {
				$scope.action = actionType;

              let editEmployeepayRoll = [];
              editEmployeepayRoll = financeCenterRecords.empPayRollCycles;

				if(actionType == 'View'){
					$scope.countryName = financeCenterRecords.countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					$scope.countryCode = financeCenterRecords.countryCode;
					//console.log($scope.countryCode)
					$scope.provisionCode = financeCenterRecords.provisionCode;
					//console.log($scope.provisionCode)
					$scope.empPayRoleCycleDisplayId=financeCenterRecords.empPayRollCycles[0].empPayRoleCycleDisplayId
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
				var selectedPayRollCycle = [];
				$scope.empPayRollCycles = [];
				if (actionType === 'Add' && financeCenterRecords.empPayRollCycles =="") {
					$scope.empPayRollCycles.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'employeeType' : null,
						'employeeCategory' : null,
						'payRoleCycle' : null,
						'cyclePeriodStartFrom' :null,
						'cycleDueDate':null
					});
				} else if (actionType === 'Add' && financeCenterRecords.empPayRollCycles!=""){
					$scope.empPayRollCycles = angular.copy(financeCenterRecords.empPayRollCycles);
					for ( var i=0; i< $scope.empPayRollCycles.length;i++ ){
						$scope.empPayRollCycles[i].actionType='View';
					}
					$scope.actionTypeSave='Edit';
					//$scope.actionTypeSave='View';
					editEmployeepayRoll = [];
					
				} else if(actionType === 'View') {
					$scope.empPayRollCycles = angular.copy(financeCenterRecords.empPayRollCycles);
					for ( var i=0; i< $scope.empPayRollCycles.length;i++ ){
						$scope.empPayRollCycles[i].actionType='View';
					}
					$scope.actionTypeSave='View';
					editEmployeepayRoll = [];
				}
				else {
					$scope.empPayRollCycles = angular.copy(editEmployeepayRoll);
					for ( var i=0; i< $scope.empPayRollCycles.length;i++ ){
						$scope.empPayRollCycles[i].actionType='Edit';
					}
					$scope.actionTypeSave='Edit';
					editEmployeepayRoll = [];
				}

				$scope.addRows = function() {
					$scope.empPayRollCycles.push({
						"actionType":'Add',
						'selected' : false,
						'clientId' : null,
						'employeeType' : null,
						'employeeCategory' : null,
						'payRoleCycle' : null,
						'cyclePeriodStartFrom' :null,
						'cycleDueDate':null
					});
				},
				$scope.employeeCategorys = [ {
					id : 1,
					name : "Direct"
				}, {
					id : 2,
					name : "Indirect"
				}];

				$scope.payRoleCycles = [ {
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

				$scope.displayCycleStart = function(emppayRoll) {

					
					if(emppayRoll.payRoleCycle == "Weekly")
					{
						emppayRoll.cyclePeriodStartFrom = "Monday";
						emppayRoll.cycleDueDates = generalservice.weeakDays
					}else if(emppayRoll.payRoleCycle == "Fortnightly")
					{
						emppayRoll.cyclePeriodStartFrom = "Monday";
						emppayRoll.cycleDueDates = generalservice.weeakDays
					}else if(emppayRoll.payRoleCycle == "Monthly")
					{
						emppayRoll.cyclePeriodStartFrom = "1";
						emppayRoll.cycleDueDates = generalservice.monthly
					}else if(emppayRoll.payRoleCycle == "Quarterly")
					{
						emppayRoll.cyclePeriodStartFrom = "January";
						emppayRoll.cycleDueDates = generalservice.years
					}
					else if(emppayRoll.payRoleCycle == "Half Yearly")
					{
						emppayRoll.cyclePeriodStartFrom = "January";
						emppayRoll.cycleDueDates = generalservice.years
					}
					else if(emppayRoll.payRoleCycle == "Yearly")
					{
						emppayRoll.cyclePeriodStartFrom = "June";
						emppayRoll.cycleDueDates = generalservice.years
					}
				}

				$scope.getEmployeeTypes = function()
				{       console.log("===getEmployeeTypes===")
						//let employeeTypes=[];
						//let employeeTypeList = [];
					//let financeCenterCodeGetReq = {
						var employeeTypes = [];
						//console.log("===employeeTypes===")
						var employeeTypeList = [];
						//console.log("===employeeTypeList===") 
						var financeCenterCodeGetReq = {
						"status":1
					} 
					    //console.log("===financeCenterCodeGetReq===")
						UnitPayRateService.getEmployeeTypes(financeCenterCodeGetReq).then(function(data) {	
						//console.log("==UnitPayRateService=getEmployeeTypes===")
						employeeTypeList = data.registerOnLoadTO.procureCatgMap;
						//console.log("==employeeTypeList===",employeeTypeList.length)
						angular.forEach(employeeTypeList, function (value, key) {
						 employeeTypes.push(value.name);
						 });

					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting Employee Type Details",'Info');
					});
					$scope.employeeTypes = employeeTypes;
					//console.log("==$scope.employeeTypes===",$scope.employeeTypes)

				},


				$scope.savePayRollCycle = function() {
					$scope.closeThisDialog();
					deferred.resolve($scope.empPayRollCycles);
				},
				$scope.emppayRollPopupRowSelect = function(payRoleCycle) {
					if (payRoleCycle.selected) {
						selectedPayRollCycle.push(payRoleCycle);
					} else {
						selectedPayRollCycle.pop(payRoleCycle);
					}
				},
				$scope.deleteRows = function() {
					if (selectedPayRollCycle.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPayRollCycle.length < $scope.empPayRollCycles.length) {
						GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						angular.forEach(selectedPayRollCycle, function(value, key) {
							$scope.empPayRollCycles.splice($scope.empPayRollCycles.indexOf(value), 1);
							//GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedPayRollCycle = [];
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
						var popupUnitOfPayRate = EmpPayRoleCyclePastRecordsFactory.empPayRoleCyclePastRecordsPopupDetails($scope.countryName,$scope.provisionName, $scope.financeCenterRecordsData);
						//console.log("popupUnitOfPayRate",popupUnitOfPayRate)
						popupUnitOfPayRate.then(function (data) {
							//console.log("popupUnitOfPayRate==",popupUnitOfPayRate)
							//console.log("data==",data)
	
						},
						function (error) {
							GenericAlertService.alertMessage("Error occurred while getting Employee Pay Role Cycle Details",'Info');
						});
					},
						function (error) {
							GenericAlertService.alertMessage("Error occurred while getting Employee Pay Role Cycle Details",'Info');
						});
				}
				
			} ]
		});
return deferred.promise;
};
return service;
}]);

app.factory("payRoleCyclePreviousRecordsfactory",["$rootScope", "ngDialog", "$q", "payRoleCycleMoreDetailsfactory", function($rootScope,ngDialog,$q,payRoleCycleMoreDetailsfactory){
	var services1={};
	var viewPreviousRecordsPopup;
	services1.viewPreviousRecords=function(){
		var deffered=$q.defer();
		viewPreviousRecordsPopup=ngDialog.open({
			templateUrl:'views/finance/financecentre/payrolecyclepastrecordspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			showClose : false,
			scope:$rootScope,
			controller : [ '$scope', function($scope) {
				$scope.moreDetails=function(){
					alert();
					var financeMoreDetails=payRoleCycleMoreDetailsfactory.moreDetails();
				}
			} ]
		});

		return deffered.promise;
	}

	return	services1;

}]);

app.factory("payRoleCycleMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
	var services1={};
	var moreDetailsPopup;
	services1.moreDetails=function(){
		var deffered=$q.defer();
		moreDetailsPopup=ngDialog.open({
			templateUrl:'views/finance/financecentre/payratecyclemoredetailspopup.html',
	//	className : 'ngdialog-theme-plain ng-dialogueCustom0',
	showClose : false,
	scope:$rootScope,
	controller : [ '$scope', function($scope) {

	} ]
});

		return deffered.promise;
	}

	return	services1;

}]);