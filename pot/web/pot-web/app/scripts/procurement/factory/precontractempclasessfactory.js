'use strict';

app.factory('PreContractProjEmpClassFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpService, GenericAlertService) {
	var projEmpClassServicepopup = [];
	var projEmpClassService = {};
	projEmpClassService.getProjEmpClasses = function() {
		var deferred = $q.defer();
		projEmpClassServicepopup = ngDialog.open({
			template : '/views/procurement/pre-contracts/internalApproval/precontractemppopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.projEmpClassData = [];
				$scope.getProjEmpClasses = function() {
					var empClassReq = {
						"status" : 1,
						//"projId" : projId
					};
					EmpService.getEmpClasses(empClassReq).then(function(data) {
						$scope.projEmpClassData = data.empClassTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Employee classes", 'Error');
					});
				}, $scope.projEmpClassPopup = function(projEmpTO) {

					var returnPopObj = {
						"projEmpclassTO" : projEmpTO
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};
	return projEmpClassService;

}]);
