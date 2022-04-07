'use strict';

app.factory('UserEPSProjectService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjEmpClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,$rootScope,ProjEmpClassService,GenericAlertService) {
	var  epsProjPopup;
	var service = {};

	 service.epsProjectPopup = function () {
         var deferred = $q.defer();
         var req = {						
 				"status" : 1
 			  };
    	 ProjEmpClassService.getUserProjects(req).then(function(data) {
				var  userProjDetailsTOs = data.userProjDetailsTOs;
				service.getUserProjects(userProjDetailsTOs).then(function(data){
					var returnPopObj = {
                         "selectedProject": data.selectedProject                                 
                     };
                     deferred.resolve(returnPopObj);  
				},function(error){
						GenericAlertService.alertMessage('An error occurred while fetching EPS Project details',"Error");
				});
		 },function(error){
				GenericAlertService.alertMessage('An error occurred while fetching EPS Project details',"Error");
		 });
        
         return deferred.promise;
     },
	 service.epsAllUserProjectPopup = function () {
         var deferred = $q.defer();
    	 ProjEmpClassService.getAllUserProjects().then(function(data) {
				service.getUserProjects(data.userProjDetailsTOs).then(function(resultData){
					var returnPopObj = {
                         "selectedProject": resultData.selectedProject                                 
                     };
                     deferred.resolve(returnPopObj);  
				},function(error){
						GenericAlertService.alertMessage('An error occurred while fetching EPS Project details',"Error");
				});
		 },function(error){
				GenericAlertService.alertMessage('An error occurred while fetching EPS Project details',"Error");
		 });
        
         return deferred.promise;
     },

	 service.getUserProjects = function (userProjDetailsTOs) {
         var deferred = $q.defer();
         epsProjPopup = ngDialog.open({
             template: 'views/projectlib/common/epsprojectselect.html',
             className: 'ngdialog-theme-plain ng-dialogueCustom1',
             controller: ['$scope', function ($scope) {
                 $scope.userProjDetailsTOs = userProjDetailsTOs; 
                 $scope.selectproject = function (projDetail) {                    
                     var returnPopObj = {
                         "selectedProject": projDetail                                 
                     };
                     deferred.resolve(returnPopObj);                        
                     $scope.closeThisDialog();
                 }         
              }]
         });
         return deferred.promise;
     }

	return service;

}]);
