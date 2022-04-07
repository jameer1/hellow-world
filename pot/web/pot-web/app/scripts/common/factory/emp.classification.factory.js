'use strict';

app.factory('EmpClassificationPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "EmpService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, EmpService, GenericAlertService) {
	
		var empDesignationPopUp;
		var service = {};
		service.employeeDetailListPopUp = function(empClassTOs) {
			var deferred = $q.defer();
			empDesignationPopUp = ngDialog.open({
				template : 'views/common/empclassificationlookup.html',
				className : 'ngdialog-theme-plain ng-dialogueCustom4',
				showClose : false,
				controller : [ '$scope', function($scope) {
					$scope.empClassTOs = empClassTOs;
					$scope.selectRecord = function(record) {
						var returnRecord = {
							"selectedRecord" : record
						};
						deferred.resolve(returnRecord);
						$scope.closeThisDialog();

					}
				} ]

			});
			return deferred.promise;
		},

		service.empDesignations = function(empDesgReq) {
			var deferred = $q.defer();
			var empDetailsPromise =  EmpService.getEmpClasses(empDesgReq)
			empDetailsPromise.then(function(data) {
				var empListPopUp = service.employeeDetailListPopUp(data.empClassTOs);
				empListPopUp.then(function(data) {
					var returnPopObj = {
						"selectedRecord" : data.selectedRecord
					};
					deferred.resolve(returnPopObj);
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting Employee List Details", 'Error');
				})
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting Employee List Details", "Error");
			});
			return deferred.promise;
		}

		return service;
	}]);