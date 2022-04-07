'use strict';

app.factory('CreateWorkShiftPopupFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "ProjWorkShiftService", "GenericAlertService", function(ngDialog, $q, blockUI,$filter, $timeout, $rootScope, ProjWorkShiftService, GenericAlertService) {
	var workshiftPopup = [];
	var workService = {};
	workService.workShiftDetailsList = function(projId) {
		var deferred = $q.defer();
		workshiftPopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workingshifts.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom5',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.shifts = [];
				var shiftReq = {
					"status" : 1,
					"projId" : projId
				};
				blockUI.start();
				ProjWorkShiftService.getProjWorkShifts(shiftReq).then(function(data) {
					blockUI.stop();
					
					$scope.shifts = data.projWorkShiftTOs;
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while getting working shift Details", "Error");
				});
				$scope.workShiftpopup = function(projWorkShiftTO) {
					var returnPopObj = {
						"projWorkShiftTO" : projWorkShiftTO
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}
	return workService;

}]);