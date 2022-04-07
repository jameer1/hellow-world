'use strict';

app.factory('EmpDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "CompanyListPopUpFactory", "EmpClassificationPopupFactory", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, CompanyListPopUpFactory, EmpClassificationPopupFactory, EmpRegisterService, GenericAlertService) {
	var empDetailsPopUp;
	var service = {};
	service.empDetailsPopUp = function(addEmpDetails, itemData,actionType) {

		var deferred = $q.defer();
		empDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/empdetails/empdetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.actionType =actionType;
				$scope.addEmpDetails = addEmpDetails;
				$scope.empCompanyMap = itemData.empCompanyMap;
				$scope.emppocureMentCatgMap = itemData.emppocureMentCatgMap;
				$scope.empClassMap = itemData.empClassMap;
				$scope.genderList = itemData.genderList;
				$scope.empLocalityList = itemData.empLocalityList;
				$scope.employeeTypeList = itemData.employeeTypeList;
                $scope.todayDate = new Date();
				$scope.EmpIds = [];
                var dupclss = false;
                
				$scope.duplicateCode = function(employee) {
					employee.duplicateFlag = false;
					var req = {
						"code" : employee.empCode
					};
					EmpRegisterService.isEmpCodeUnique(req).then(function(data) {
						employee.duplicateFlag = (data && !data.unique);
						if(data && !data.unique){
							$scope.CallBacktoUser();
						}
					});
					//start, if projId is present
					let duplicateEmpCode = 0;
					$scope.isValid = false;
					var empReq = {
						"status" : 1
					}
					EmpRegisterService.empRegisterOnLoad(empReq).then(function(data) {
					angular.forEach(data.empRegisterDtlTOs, function(key, value){
						$scope.EmpIds.push(angular.copy(key));
						if(key.empCode == $scope.addEmpDetails[0].empCode){
								console.log("true")
								duplicateEmpCode = 1;
						}
					});
					if(duplicateEmpCode == 1){
						$scope.CallBacktoUser();
					}
					return;
				});
				//end,
				$scope.CallBacktoUser = function() {
					$scope.isValid = true;
					$scope.errorMessage = "Employee Id's already taken."; 
					return;
				}
		}
				var selectedEmployee = [];

				$scope.addRows = function() {
					$scope.addEmpDetails.push({
						"selected" : false,
						"code" : null,
						"firstName" : null,
						"lastName" : null,
						"dob" : '',
						"gender" : null,
						"location" : null,
						"empStatus" : null,
						"cmpId" : null,
						"empClassId" : null,
						"procureLabelKeyTO" : null
					})
				},
				$scope.companyList = function(employee) {
					var companyListService = {};
					var onLoadReq = {
						"status" : 1
					};
					var companyListPopup = CompanyListPopUpFactory.getCompanies(onLoadReq);
					companyListPopup.then(function(data) {
						employee.cmpId = data.selectedRecord.id;
						employee.cmpCode = data.selectedRecord.cmpCode;
						employee.cmpName = data.selectedRecord.cmpName;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting company list details", 'Error');
					});
				},
				$scope.empDesignation = function(employee) {
					var desgPopUpService = {};
					var empDesgReq = {
						"status" : 1,
					};
					var empDesignationListPopup = EmpClassificationPopupFactory.empDesignations(empDesgReq);
					empDesignationListPopup.then(function(data) {
						employee.empClassId = data.selectedRecord.id;
						employee.empClassName = data.selectedRecord.name;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting employee list details", 'Error');
					});
				},
				$scope.empDetailsPopUpRowSelect = function(employee) {
					if (employee.selected) {
						selectedEmployee.push(employee);
					} else {
						selectedEmployee.pop(employee);
					}
				},
				$scope.deleteRows = function() {
					if (selectedEmployee.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedEmployee.length < $scope.addEmpDetails.length) {
						angular.forEach(selectedEmployee, function(value, key) {
							$scope.addEmpDetails.splice($scope.addEmpDetails.indexOf(value), 1);
						});
						selectedEmployee = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				},
				
				$scope.validation = function(value){
	             var empDesignation = value;
                 console.log(empDesignation);
                 console.log(details[0].empClassName);
                if(empDesignation != details[0].empClassName){             
	               dupclss = true;
                   return;
                 }
                }
				$scope.saveEmpRegisters = function() {
					let dupFlag = false, dupMessage = "Duplicate Employee Ids are not allowed for ";

					for (let empDetail of $scope.addEmpDetails) {
						if (empDetail.duplicateFlag) {
							dupFlag = true;
							dupMessage += empDetail.empCode;
							break;
						}
					}

					if (actionType=='Edit' && dupFlag) {
						GenericAlertService.alertMessage(dupMessage, "Warning");
						return;
					}
                    if(dupclss){
	                  GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
                    }
					var saveEmpRegReq = {
						"empRegisterTOs" : $scope.addEmpDetails,
						"status" : 1
					}

					EmpRegisterService.saveEmpregisters(saveEmpRegReq).then(function(data) {
						// var succMsg = GenericAlertService.alertMessageModal('Employee Registration is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Employee Registration saved successfully ',"Info");
						succMsg.then(function() {
							$scope.closeThisDialog();
							var returnPopObj = {
								"empRegisterTOs" : data.empRegisterDtlTOs
							};
							deferred.resolve(returnPopObj);
						});
					}, function(error) {
						GenericAlertService.alertMessage('Employee Registration is/are Failed to Save ', "Warning");
					});
				}
			} ]
		});
		return deferred.promise;
	}
    var details;
	service.empDetailsOnLoad = function(empDropDown, editEmpDetails, actionType) {
		var deferred = $q.defer();
		var addEmpDetails = [];
        details = editEmpDetails;
		$rootScope.action = actionType;
		if (actionType === 'Add') {
			addEmpDetails.push({
				"selected" : false,
				"code" : null,
				"firstName" : null,
				"lastName" : null,
				"dob" : '',
				"gender" : null,
				"location" : null,
				"empStatus" : null,
				"cmpId" : null,
				"empClassId" : null,
				"procureLabelKeyTO" :null
			});

		} else if (actionType === 'Edit') {
			addEmpDetails = angular.copy(editEmpDetails);
		}

		service.empDetailsPopUp(addEmpDetails, empDropDown).then(function(data) {
			var returnPopObj = {
				"empRegisterTOs" : data.empRegisterTOs
			};
			deferred.resolve(returnPopObj);
		});
		return deferred.promise;
	}

	return service;
}]);
