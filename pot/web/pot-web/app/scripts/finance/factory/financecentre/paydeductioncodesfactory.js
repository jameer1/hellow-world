app.factory("PayDeductionCodeFactory",["ngDialog", "$q", "$rootScope", "payDeductioncodesPreviousRecordsfactory", function(ngDialog,$q,$rootScope,payDeductioncodesPreviousRecordsfactory){
	
	var service={};
	var PayDeductionCodePopup;
	
	service.PayDeductionCode=function(){
	var deffered=	$q.defer();
		PayDeductionCodePopup=	ngDialog.open({
			templateUrl:'views/finance/financecentre/paydeductioncodespopup.html',
			$scope:$rootScope,
			
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				if(actionType == 'View'){
					$scope.countryName = financeCenterRecords.countryName;
					//console.log($scope.countryName)
					$scope.provisionName = financeCenterRecords.provisionName;
					//console.log($scope.provisionName)
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
					//console.log($scope.effectiveDate)
				}
				else {
					$scope.countryName = countryName;
					$scope.provisionName = financeCenterRecords.provisionName;
					$scope.effectiveDate = financeCenterRecords.effectiveFrom;
				}
				var selectedPayDeductionCodes = [];
				$scope.payDeductionCodes = [];
				if (actionType === 'Add' && financeCenterRecords.payDeductionCodes =="") {
					$scope.payDeductionCodes.push({
						"actionType":'Add',
						'selected' : false,
						'code' : null,
						'description' : null,
						'UnitOfPayDisplayId':null
					});
				} else if (actionType === 'Add' && financeCenterRecords.payDeductionCodes!=""){
					$scope.payDeductionCodes = angular.copy(financeCenterRecords.payDeductionCodes);
					for ( var i=0; i< $scope.payDeductionCodes.length;i++ ){
						$scope.payDeductionCodes[i].actionType='View';
					}
					$scope.actionTypeSave='View';
					editPayDeductionCodes = [];
					
				}
				else if(actionType === 'View') {
					$scope.payDeductionCodes = angular.copy(financeCenterRecords.payDeductionCodes);
					for ( var i=0; i< $scope.payDeductionCodes.length;i++ ){
					$scope.payDeductionCodes[i].actionType='View';
				     }
				     $scope.actionTypeSave='View';
					editPayDeductionCodes = [];
				}
				else {
					$scope.payDeductionCodes = angular.copy(editPayDeductionCodes);
					for ( var i=0; i< $scope.payDeductionCodes.length;i++ ){
					$scope.payDeductionCodes[i].actionType='Edit';
				     }
					$scope.actionTypeSave='Edit';
					editPayDeductionCodes = [];
				}
				$scope.addRows = function() {

					$scope.payDeductionCodes.push({
						'selected' : false,
						'code' : null,
						'name' : null,
						'notes' : null,
						'status' : 1

					});
				},

				$scope.savePayDeductionCodes = function() {
					$scope.closeThisDialog();
					deferred.resolve($scope.payDeductionCodes);
				}

				$scope.payDeductionCodesPopupRowSelect = function(payDeduction) {
					if (payDeduction.selected) {
						selectedPayDeductionCodes.push(payDeduction);
					} else {
						selectedPayDeductionCodes.pop(payDeduction);
					}
				}, $scope.deleteRows = function() {
					if (selectedPayDeductionCodes.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedPayDeductionCodes.length < $scope.addPayDeductionCodes.length) {
						angular.forEach(selectedPayDeductionCodes, function(value, key) {
							$scope.addPayDeductionCodes.splice($scope.addPayDeductionCodes.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedPayDeductionCodes = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

				
				$scope.viewPastrecords=function(){
					var previousrecords=payDeductioncodesPreviousRecordsfactory.viewPreviousRecords();
				}
			} ]
		});
	return deffered.promise;
	}
	return service;
}]);


app.factory("payDeductioncodesPreviousRecordsfactory",["$rootScope", "ngDialog", "$q", "payDeductionMoreDetailsfactory", function($rootScope,ngDialog,$q,payDeductionMoreDetailsfactory){
    var services1={};
	var viewPreviousRecordsPopup;
    services1.viewPreviousRecords=function(){
	var deffered=$q.defer();
	viewPreviousRecordsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/paydeductionpastrecordspopup.html',
		showClose : false,
		scope:$rootScope,
		controller : [ '$scope', function($scope) {
			$scope.moreDetails=function(){
				var finanaceMoreDetails=payDeductionMoreDetailsfactory.moreDetails();
			}
		} ]
	});
	return deffered.promise;
}
return	services1;
}]);


app.factory("payDeductionMoreDetailsfactory",["$rootScope", "ngDialog", "$q", function($rootScope,ngDialog,$q){
    var services1={};
	var moreDetailsPopup;
    services1.moreDetails=function(){
	var deffered=$q.defer();
	moreDetailsPopup=ngDialog.open({
		templateUrl:'views/finance/financecentre/paydeductionmoredetailspopup.html',
		scope:$rootScope,
		showClose : false,
		controller : [ '$scope', function($scope) {
			
		} ]
	});
	
	return deffered.promise;
}

return	services1;

}]);