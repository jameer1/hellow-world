'use strict';
app.factory('PerformanceThresholdFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjectSettingsService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI,ProjectSettingsService,GenericAlertService ) {	
			var performancePopUp;
			var service = {};
			service.performancePopupDetails = function(editPerformanceRecords) {
				var deferred = $q.defer();
				performancePopUp = ngDialog.open({
					template : 'views/projectsettings/performancethresholdpopup.html',
					closeByDocument : false,
					showClose : false,
					className : 'ngdialog-theme-plain ng-dialogueCustom0',
					controller : ['$scope',function($scope){
						$scope.editPerformanceData =[];
							$scope.editPerformanceData = angular.copy(editPerformanceRecords);
							editPerformanceRecords=[];
				
						$scope.savePerformanceThreshold = function(){
							var savePerformanceThresholdReq={
									"projPerformenceThresholdTOs" : $scope.editPerformanceData,
									"status" : 1 ,
									"projId":$rootScope.projId
							};
							blockUI.start();
							ProjectSettingsService.savePerformanceThreshold(savePerformanceThresholdReq).then(function(data) {
								blockUI.stop();
								var results=data.projPerformenceThresholdTOs;
								// var succMsg = GenericAlertService.alertMessageModal('Project Performance Threshold(s) is/are  '+data.message,data.status);
								var succMsg = GenericAlertService.alertMessageModal('Project Performance Threshold(s) saved successfully',"Info");
								succMsg.then(function(data1) {
									$scope.closeThisDialog();
									var returnPopObj = {
										"projPerformenceThresholdTOs" : results
									}
									deferred.resolve(returnPopObj)
								})
							}, function(error) {
								blockUI.stop();
								GenericAlertService.alertMessage('Project Performance Threshold(s) ia/are Failed to Save ',"Info");
							});
						}
					}]
				});
				return deferred.promise;
			}
			return service;
	}]);
