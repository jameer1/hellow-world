'use strict';
app.factory('NonRegularPayFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {

	var service = {};
	service.getEmpNonRegularPaybleRateDetails = function(itemData,action) {
		var deferred = $q.defer();
		console.log("action=====",action)
		var req = {
			"id" : itemData.empNonRegularPay.id,
			"countryId" : itemData.projGeneralLabelTO.displayNamesMap['country'],
			"provinceId" : itemData.projGeneralLabelTO.displayNamesMap['province'],
			"effectiveDate":itemData.empNonRegularPay.projEmpRegisterTO.enrollmentDate,
			"payTypeId" : 1
		};
	
		EmpRegisterService.getEmpNonRegularPaybleRateDetails(req).then(function(data) {
			var popupdata = service.openPopUp(itemData, data.empPaybleRateDetailTOs, data.nonRegularPayCodeMap,action);
			popupdata.then(function(data) {
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee regular pay codes", 'Error');
		});
		return deferred.promise;
	}, service.openPopUp = function(itemData, empPaybleRateDetailTOs, nonRegularPayCodeMap,action) {
		var deferred = $q.defer();
		var empRegularPay = ngDialog.open({
			template : 'views/projresources/projempreg/empnonregularpay/empnonregularpaypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,

			controller : [ '$scope', function($scope) {
				$scope.empNonRegularPay = itemData.empNonRegularPay;
				$scope.empNonRegularPay.empPaybleRateDetailTOs = empPaybleRateDetailTOs;
				$scope.projGeneralLabelTO = itemData.projGeneralLabelTO;
				$scope.projEmployeeClassMap = itemData.projEmployeeClassMap;
				$scope.empDropDown = itemData.empDropDown;
				$scope.nonRegularPayCodeMap = nonRegularPayCodeMap;
				//console.log("$scope.nonRegularPayCodeMap",$scope.nonRegularPayCodeMap)
				//console.log("$scope.nonRegularPayCodeMap.code",$scope.nonRegularPayCodeMap[61].code)
				var oldEffectiveFromDate = $scope.empNonRegularPay.fromDate;
				$scope.searchEffectiveDetails = function(empNonRegularPay) {
					var req = {
							"id" : $scope.empNonRegularPay.id,
							"countryId" : $scope.projGeneralLabelTO.displayNamesMap['country'],
							"provinceId" : $scope.projGeneralLabelTO.displayNamesMap['province'],
							"effectiveDate":$scope.empNonRegularPay.projEmpRegisterTO.enrollmentDate,
							"payTypeId" : 1
						};
						EmpRegisterService.getEmpNonRegularPaybleRateDetails(req).then(function(data) {
							$scope.empNonRegularPay.empPaybleRateDetailTOs=data.empPaybleRateDetailTOs;
							$scope.nonRegularPayCodeMap = data.regularPayCodeMap;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting employee regular pay codes", 'Error');
						});
		        };
				
			
				$scope.validateBasiPayPercentage = function(type, nonRegularPay) {
					if (type == 1) {
						nonRegularPay.fixedAmount = null;
					} else {
						nonRegularPay.percentage = null;
					}
				},

				$scope.saveNonRegularPay = function() {
					
					/*angular.forEach($scope.empNonRegularPay.empPaybleRateDetailTOs ,function(value,key){
						
						$scope.applicableType=value.applicable;
						
						$scope.fixAmt=value.fixedAmount;
						$scope.basicAmt=value.percentage;
				
						
					});
					
					if($scope.applicableType=='Yes' ){
						GenericAlertService.alertMessage("Please select  FixedAmount Or Precentage", 'Warning');
						return;
					}*/
						
					
					
					if ($scope.empNonRegularPay.id > 0 ) {
						var oldFromDate = new Date(oldEffectiveFromDate);
						var fromDate = new Date($scope.empNonRegularPay.fromDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
						if (days <= 0) {
							GenericAlertService.alertMessage("Please select  the new effective from date", 'Warning');
							return;
						}
						}
					var saveReq = {
						"empPaybleRateTOs" : [ $scope.empNonRegularPay ],
						"status" : 1,
						"projId" : $rootScope.selectedEmployeeData.projId,
						"empId" : $rootScope.selectedEmployeeData.id
					};
					EmpRegisterService.saveEmpNonRegularPaybleRates(saveReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Employee regular pay codes are ' + data.message, data.status);
						succMsg.then(function() {
							deferred.resolve(data);
						});

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while saving employee regular pay codes", 'Error');
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
