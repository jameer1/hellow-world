'use strict';

app.factory('UserMultipleEPSProjectService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjEmpClassService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,$rootScope,ProjEmpClassService,GenericAlertService) {
	var  epsProjPopup;
	var service = {};

	 service.epsProjectPopup = function (epsProjectData) {
         var deferred = $q.defer();
         epsProjPopup = ngDialog.open({
             template: 'views/procurement/common/epsmultipleprojectselect.html',
             className: 'ngdialog-theme-plain ng-dialogueCustom1',
             showClose : false,
             controller: ['$scope', function ($scope) {
            	 $scope.multiRoles = [];
                 $scope.userProjDetailsTOs = epsProjectData; 
                 var selectedProjects = [];
                 /*var obj = {};
                 obj.rootElements = [];
                 for (var i in epsProjectData) {
                     var _elem = epsProjectData[i];
                     if (_elem.parentId) {
                         var _parentId = _elem.parentId;
                         if (_parentId == _elem.id) {
                             // check children, if false - add
                             if (!_elem.children) {
                                 _elem.children = [];
                             }
                             _elem.children.push(_elem);
                         }
                         else {
                             addChildToParent(_elem, _parentId);
                         }
                     }
                     else // is root
                     {
                         obj.rootElements.push(_elem);
                     }
                 }
                 function addChildToParent(child, parentId, root) {
                     for (var j in epsProjectData) {
                         if (epsProjectData[j].id.toString() == parentId.toString()) {
                             if (!epsProjectData[j].children) {
                            	 epsProjectData[j].children = [];
                             }
                             epsProjectData[j].children.push(child);
                         }
                     }
                 }
                 */
                 $scope.selectproject = function(tab) {
 					if (tab.select) {
 						selectedProjects.push(tab);
 					} else {
 						selectedProjects.splice(selectedProjects.indexOf(tab), 1);
 					}
 				}
                 
                 $scope.save = function() {
               			var returnPopObj = {
 							"selectedProject" : angular.copy(selectedProjects)
 						};
 						deferred.resolve(returnPopObj);
 						$scope.closeThisDialog();
 				}
              }]
         });
         return deferred.promise;
     };


     service.epsProjectPopupOnLoad = function (req) {

 		var deferred = $q.defer();
 		var epsDetailsPromise = ProjEmpClassService.getUserProjects(req);
 		epsDetailsPromise.then(function(data) {
 			var epsProjectData = data.userProjDetailsTOs;
 			var epsProjectPopup = service.epsProjectPopup(epsProjectData);
 			epsProjectPopup.then(function(data) {
 				var returnPopObj = {
 					"selectedProject" : data.selectedProject
 				};
 				deferred.resolve(returnPopObj);
 			}, function(error) {
 				GenericAlertService.alertMessage("Error occured while selecting Purchase Order Item Details", 'Error');
 			})
 		}, function(error) {
 			GenericAlertService.alertMessage("Error occured while getting Purchase Order Item Details", "Error");
 		});
 		return deferred.promise;
 	
     };
	return service;

}]);