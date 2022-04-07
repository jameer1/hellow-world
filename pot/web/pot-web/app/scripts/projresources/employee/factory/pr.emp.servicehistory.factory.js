'use strict';
app.factory('EmpServiceHistoryFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {
	var empServiceHistoryPopUp;
	var service = {};
	service.empServiceHistoryPopUp = function(itemData) {
		var deferred = $q.defer();
		empServiceHistoryPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/empservicehistory/empservicehistorypopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empDropDown = itemData.empDropDown;
				$scope.servicehistory = angular.copy(itemData.editEmpServiceHistory[0]);
				$scope.empClassMap = itemData.empDropDown.empClassMap;
				$scope.empServiceType = itemData.empServiceType;
				$scope.projClassificationMap=itemData.projClassificationMap;
				if($scope.servicehistory.deMobilaizationDate == null || $scope.servicehistory.empStatus == null){
					$scope.servicehistory.empStatus="On Transfer";
				}
				$scope.servicehistory.empStatus= $scope.servicehistory.empStatus; //"On Transfer";// itemData.servicehistory.empStatus;
				$scope.prerequisiteErrors = "";
             	$scope.minDate = $scope.servicehistory.enrollmentDate;// new Date($scope.servicehistory.mobilaizationDate)
				$scope.getEmployeeDeMobDate = function() {
					var getEmpServiceHistoryReq = {
						"status": 1,
						"empId": $rootScope.selectedEmployeeData.id
					}
					EmpRegisterService.getEmpServiceHistory(getEmpServiceHistoryReq).then(function(data) {
						$scope.projEmpRegister = data.projEmpRegisterTOs;
						let k = $scope.projEmpRegister.length-1;
						if($scope.minDate== null){
							if($scope.projEmpRegister[k].deMobilaizationDate != null){
							$scope.minDate = $scope.projEmpRegister[k].deMobilaizationDate
							}else if($scope.projEmpRegister[k-1].deMobilaizationDate != null){
								$scope.minDate = $scope.projEmpRegister[k-1].deMobilaizationDate
							}else if($scope.projEmpRegister[k-2].deMobilaizationDate != null){
								$scope.minDate = $scope.projEmpRegister[k-2].deMobilaizationDate
							}
							/*else if($scope.projEmpRegister[k-3].deMobilaizationDate != null){
								console.log("call reached here-loop4")
								$scope.minDate = $scope.projEmpRegister[k-3].deMobilaizationDate
							}*/
							
						}
					})
				},
				$scope.saveEmpServiceHistory = function() {
					$scope.prerequisiteErrors = "";
					
					/*T3*/
					var prerequisites = [
						['empContractName', "Designation as per Project Contract"],
						['empWorkUnionName', "Designation as per Trade Union"],
						['empCatgName', "Category"]
					];

					var req, prop, prerequisitesEmpty = [];
					var prerequisiteParent = document.getElementById('prerequisiteParent');

					for(let requisite in prerequisites){
						req = prerequisites[requisite];

						if( $scope.servicehistory.hasOwnProperty(req[0]) ){
							prop = ( String($scope.servicehistory[req[0]]) ).trim();
						}else{
							prop = "null";
						}

						if( prop == "null" || prop.length === 0 ){
							prerequisitesEmpty.push( req );
						}
					}

					for( let show = 0; show < prerequisitesEmpty.length; show++ ){
						prerequisiteParent.querySelector('.' + prerequisitesEmpty[show]).classList.remove('hide');

						if( show === 0 ){
							$scope.prerequisiteErrors += prerequisitesEmpty[show][1];
						}else{
							$scope.prerequisiteErrors += ", " + prerequisitesEmpty[show][1];
						}
					}

					if( prerequisitesEmpty.length > 0 ){
						document.getElementById('prerequisiteError').classList.remove('hide');
						return;
					}
					/*T3*/

					if ($scope.servicehistory.mobilaizationDate == null || $scope.servicehistory.mobilaizationDate == undefined) {
						GenericAlertService.alertMessage('Please enter Mobilization date ', "Warning");
						return;
					}
				
					if ($scope.servicehistory.id > 0 ) {
						var oldFromDate = new Date($scope.servicehistory.mobilaizationDate);
						var fromDate = new Date($scope.servicehistory.expecteddeMobilaizationDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
					}

					if ($scope.servicehistory.id > 0 ) {
						var oldFromDate = new Date($scope.servicehistory.mobilaizationDate);
						var fromDate = new Date($scope.servicehistory.deMobilaizationDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
					}

					$scope.servicehistory.empId = $rootScope.selectedEmployeeData.id;
					var saveReq = {

							"empId" : $rootScope.selectedEmployeeData.id,

						       "projEmpRegisterTO" : $scope.servicehistory
					}
					EmpRegisterService.saveEmpServiceHistory(saveReq).then(function(data) {
						// var succMsg = GenericAlertService.alertMessageModal('Employee Service History details ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Employee Service History details saved successfully',"Info");
						succMsg.then(function() {
							var returnPopObj = {
								"empServiceHistoryDtlTOs" : data.projEmpRegisterTOs
							};
							$rootScope.$broadcast('employeeRefresh',{tabIndex:1});
							$scope.closeThisDialog();
							deferred.resolve(returnPopObj);

						})
					}, function(error) {
						GenericAlertService.alertMessage('Error occured while saving the employee service details', "Error");
					});


				}



			} ]
		});
		return deferred.promise;
	}, service.empServiceHistoryOnLoad = function(itemData) {
		var deferred = $q.defer();
		service.empServiceHistoryPopUp(itemData).then(function(data) {
			var returnPopObj = {
				"empServiceHistoryDtlTOs" : data.empServiceHistoryDtlTOs
			};
			deferred.resolve(returnPopObj);

		});
		return deferred.promise;
	}
	return service;
}]);
