'use strict';
app.factory('EmpPayDeductionFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {
	var service = {};
	service.getEmpPayDeductionsDetails = function(itemData) {
		var deferred = $q.defer();
		var req = {
			"id" : itemData.payDeduction.id,
			"countryId" : itemData.projGeneralLabelTO.displayNamesMap['country'],
			"provinceId" : itemData.projGeneralLabelTO.displayNamesMap['province'],
			"effectiveDate":itemData.payDeduction.projEmpRegisterTO.enrollmentDate,
			"payTypeId" : 1
		};
	
		EmpRegisterService.getEmpPayDeductionsDetails(req).then(function(data) {
			var popupdata = service.openPopUp(itemData, data.payDeductionCodeMap, data.empPayDeductionDetailTOs, data.empPayDeductionQuestionTOs);
			popupdata.then(function(data) {
				
				deferred.resolve(data);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee pay deductions", 'Error');
		});
		return deferred.promise;
	}, service.openPopUp = function(itemData, payDeductionCodeMap, empPayDeductionDetailTOs, empPayDeductionQuestionTOs) {
		var deferred = $q.defer();
		var empRegularPay = ngDialog.open({
			template : 'views/projresources/projempreg/paydeduction/emppaydeductionpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,

			controller : [ '$scope', function($scope) {
				$scope.payDeduction = itemData.payDeduction;
				$scope.payDeduction.empPayDeductionDetailTOs = empPayDeductionDetailTOs;
				$scope.payDeduction.empPayDeductionQuestionTOs = empPayDeductionQuestionTOs;
				$scope.payDeductionCodeMap = payDeductionCodeMap;
				$scope.projGeneralLabelTO = itemData.projGeneralLabelTO;
				$scope.projEmployeeClassMap = itemData.projEmployeeClassMap;
				$scope.empDropDown = itemData.empDropDown;
				var oldEffectiveFromDate = $scope.payDeduction.fromDate;
				$scope.applicabilities = [ "Yes", "No" ];
				$scope.searchEffectiveDetails = function(payDeduction) {
					var req = {
							"id" : $scope.payDeduction.id,
							"countryId" : $scope.projGeneralLabelTO.displayNamesMap['country'],
							"provinceId" : $scope.projGeneralLabelTO.displayNamesMap['province'],
							"effectiveDate":$scope.payDeduction.projEmpRegisterTO.enrollmentDate,
							"payTypeId" : 1
						};
						EmpRegisterService.getEmpPayDeductionsDetails(req).then(function(data) {
							
							$scope.payDeduction.empPayDeductionDetailTOs=data.empPayDeductionDetailTOs;
							$scope.payDeduction.empPayDeductionQuestionTOs = data.empPayDeductionQuestionTOs;
							$scope.payDeductionCodeMap = data.payDeductionCodeMap;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting employee regular pay codes", 'Error');
						});
		        };
				$scope.validateAmount = function(payDeduction, type) {
					if (type == 1 && payDeduction.percentage >= 0) {
						payDeduction.fixedAmount = null;
					} else if (type == 2 && payDeduction.fixedAmount >= 0) {
						payDeduction.percentage = null;
					}
				}, $scope.saveEmpPayDeduction = function() {
					if ($scope.payDeduction.id  > 0 ) {
						var oldFromDate = new Date(oldEffectiveFromDate);
						var fromDate = new Date($scope.payDeduction.fromDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
						if (days <= 0 ) {
							GenericAlertService.alertMessage("Please select  the new effective from date", 'Warning');
							return;
						}
						}
					var saveReq = {
						"empPayDeductionTOs" : [ $scope.payDeduction ],
						"status" : 1,
						"projId" : $rootScope.selectedEmployeeData.projId,
						"empId" : $rootScope.selectedEmployeeData.id
					};
					EmpRegisterService.saveEmpPayDeductions(saveReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Employee pay deductions are ' + data.message, data.status);
						succMsg.then(function() {
							var returnPopObj = {
								"empPayDeductionTOs" : data.empPayDeductionTOs,
							};
							deferred.resolve(returnPopObj);
						});

					}, function(error) {
						GenericAlertService.alertMessage("Error occured while saving employee  pay deductions", 'Error');
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);