'use strict';
app.factory('EmployeeTransferReqDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "EmpRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, EmpRegisterService) {
	var service = {};
	var users = [];
	service.getEmpTransferReqDetails = function(req,empTransferExistingMap) {
		var deferred = $q.defer();
		var resultData = EmpRegisterService.getEmpTransferReqDetails(req);
		resultData.then(function(data) {
			let userData =  data.labelKeyTOs;
			console.log(userData)
			for(let i=0;i<userData.length;i++){
				if(userData[i].displayNamesMap.expectedTransDate != null){
					users.push(userData[i]);
				}
			}
			console.log("end of loop")
			var resultPopupData = service.openPopup(data.labelKeyTOs,empTransferExistingMap);
			resultPopupData.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting project employees", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting project employees", 'Error');
		});
		return deferred.promise;
	}, service.openPopup = function(labelKeyTOs,empTransferExistingMap) {
		var deferred = $q.defer();
		var popUp = ngDialog.open({
			template : 'views/projresources/projempreg/emptransfer/employeedetailspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			showClose : false,
			controller : [ '$scope', function($scope) {
			//	$scope.labelKeyTOs = labelKeyTOs;
				$scope.labelKeyTOs = users
				var selectedEmployees = [];
				$scope.empTransferExistingMap=empTransferExistingMap;
				var transferEmpMap = [];
				users = [];
				$scope.popUpRowSelect = function(tab) {
					if (tab.select) {
						selectedEmployees.push(tab);
					} else {
						selectedEmployees.pop(tab);
					}
				}
				$scope.addToTransferRequest = function() {
					var returnPopObj = {
						"empTransReqApprDetailTOs" : angular.copy(selectedEmployees)
					};
					selectedEmployees=[];
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
