'use strict';
app.factory('RegularPayRatesFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {

	var service = {};
	//$scope.generalValues = {};
	service.getEmpRegularPaybleRateDetails = function(itemData, action) {
		//console.log("==RegularPayRatesFactory====")
		//console.log("==action====",action)
		//console.log("==itemData====",itemData)
		var deferred = $q.defer();
		var req = {
			"id" : itemData.empRegularPay.id,
			"countryId" : itemData.projGeneralLabelTO.displayNamesMap['country'],
			"provinceId" : itemData.projGeneralLabelTO.displayNamesMap['province'],
			"effectiveDate":itemData.empRegularPay.projEmpRegisterTO.enrollmentDate,
			"payTypeId" : 1
		};
		EmpRegisterService.getEmpRegularPaybleRateDetails(req).then(function(data) {
			//console.log("EmpRegisterService.getEmpRegularPaybleRateDetails")
			var popupdata = service.openPopUp(itemData, data.empPaybleRateDetailTOs, data.regularPayCodeMap, action);
			popupdata.then(function(data) {
				deferred.resolve(data);
				//console.log("data",data)
			});
			//console.log("====close== close==")
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee regular pay codes", 'Error');
		});
		return deferred.promise;
	},
	
	service.openPopUp = function(itemData, empPaybleRateDetailTOs, regularPayCodeMap, action) {
		console.log(itemData,'itemmmm');
		console.log(empPaybleRateDetailTOs,'empayable');
		console.log(regularPayCodeMap,'codemap');
		console.log(action,'actionn');
		//console.log("===openPopUp====")
		var deferred = $q.defer();
		var empRegularPay = ngDialog.open({
			template : 'views/projresources/projempreg/empregularpay/empregularpaypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,

			controller : [ '$scope', function($scope) {
				$scope.empRegularPay = angular.copy(itemData.empRegularPay);
				var mobilDate = new Date($rootScope.selectedEmployeeData.projEmpRegisterTO.mobilaizationDate);
			//	console.log(mobilDate,'date');
	            $scope.maxdate = $filter('date')(angular.copy(mobilDate), "dd-MMM-yyyy");
	         //   console.log($scope.maxdate,'maxx');	
			//	console.log($scope.empRegularPay,'empRegularPay')
				$scope.empRegularPay.empPaybleRateDetailTOs = empPaybleRateDetailTOs;
				//console.log("$scope.empRegularPay.empPaybleRateDetailTOs======43",$scope.empRegularPay.empPaybleRateDetailTOs)
				$scope.projGeneralLabelTO = itemData.projGeneralLabelTO;
				//console.log("$scope.projGeneralLabelTO",$scope.projGeneralLabelTO)
				$scope.projEmployeeClassMap = itemData.projEmployeeClassMap;
				//console.log("$scope.projEmployeeClassMap",$scope.projEmployeeClassMap)
				$scope.empDropDown = itemData.empDropDown;
				//console.log("$scope.empDropDown",$scope.empDropDown)
				$scope.regularPayCodeMap = regularPayCodeMap;
				//console.log("$scope.regularPayCodeMap",$scope.regularPayCodeMap)
				//console.log("$scope.regularPayCodeMap.code",$scope.regularPayCodeMap[61].code)
				$scope.action = action;
				//console.log("$scope.action",$scope.action)
				//console.log("$scope.empRegularPay",$scope.empRegularPay)
				var oldEffectiveFromDate = $scope.empRegularPay.fromDate;
				//console.log("oldEffectiveFromDate",oldEffectiveFromDate)
				$scope.validateBasiPayPercentage = function(type, regularpay) {
					if (type == 1) {
						regularpay.fixedAmount = null;
					} else {
						regularpay.percentage = null;
					}
				},
				//console.log("====values====",$scope.generalValues)
				//console.log("oldEffectiveFromDate")
				//console.log("khgjhgjhgjhgjhg")
				//console.log("------country------",itemData.projGeneralLabelTO.displayNamesMap['country'])
					//console.log("------province------",$scope.projGeneralLabelTO.displayNamesMap['province'])
					//empRegularPay
				$scope.searchEffectiveDetails = function(empRegularPay) {
					//console.log("searchEffectiveDetails")
					//console.log("------country------",$scope.projGeneralLabelTO.displayNamesMap['country'])
					//console.log("------province------",$scope.projGeneralLabelTO.displayNamesMap['province'])
					var req = {
							"id" : $scope.empRegularPay.id,
							"countryId" : $scope.projGeneralLabelTO.displayNamesMap['country'],
							"provinceId" : $scope.projGeneralLabelTO.displayNamesMap['province'],
							"effectiveDate":$scope.empRegularPay.projEmpRegisterTO.enrollmentDate,
							"payTypeId" : 1
						};
						console.log(req,'reqq');
					//	console.log("EmpRegisterService",$scope.empRegularPay.projEmpRegisterTO.enrollmentDate);
						EmpRegisterService.getEmpRegularPaybleRateDetails(req).then(function(data) {
							$scope.empRegularPay.empPaybleRateDetailTOs=data.empPaybleRateDetailTOs;
							//console.log("====$scope.empRegularPay.empPaybleRateDetailTOs====",$scope.empRegularPay.empPaybleRateDetailTOs)
							$scope.regularPayCodeMap = data.regularPayCodeMap;
							//console.log("====$scope.regularPayCodeMap====",$scope.regularPayCodeMap)
							//console.log("$scope.regularPayCodeMap.code",$scope.regularPayCodeMap[61].code)
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting employee regular pay codes", 'Error');
						});
						//console.log("====EmpRegisterService====")
		        };		        		       		       
				$scope.saveRegularPay = function() {
					if ($scope.empRegularPay.id > 0 ) {
					var oldFromDate = new Date(oldEffectiveFromDate);
					var fromDate = new Date($scope.empRegularPay.projEmpRegisterTO.enrollmentDate);
					var dateDiff = new Date(fromDate - oldFromDate);
					var days = dateDiff/1000/60/60/24;
					if (days <= 0) {
						GenericAlertService.alertMessage("Please select  the new effective from date", 'Warning');
						return;
					}
					}
					
					var saveReq = {
						"empPaybleRateTOs" : [ $scope.empRegularPay],
						"status" : 1,
						"projId" : $rootScope.selectedEmployeeData.projId,
						"empId" : $rootScope.selectedEmployeeData.id
					};
					
					EmpRegisterService.saveEmpRegularPaybleRates(saveReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Employee regular pay codes are ' + data.message, data.status);
						succMsg.then(function() {
						$scope.closeThisDialog();
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
}])
