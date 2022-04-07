'use strict';
app.factory('WorkDairyDocketFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "TimeSheetIndividualUsersFactory", "PotCommonService", "ModuleUserFactory", "ProjectCrewPopupService", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, TimeSheetIndividualUsersFactory, PotCommonService, ModuleUserFactory, ProjectCrewPopupService, TimeSheetService, GenericAlertService) {
	var service = {};
	service.docTypeDetails = function() {
		var deferred = $q.defer();
		var popUp = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/workdairyscheduleitems.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
			

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
