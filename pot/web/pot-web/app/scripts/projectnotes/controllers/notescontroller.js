'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notes", {
		url : '/notes',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projectnotes/notesview.html',
				controller : 'ProjectNotesController'
			}
		}
	})
}]).controller(
		'ProjectNotesController',
		["$rootScope", "$scope", "$state", "$q", "ProjectNotesService", "EmpRegisterService", "MaterialRegisterService", "PlantRegisterService", "ngDialog", "GenericAlertService", "CompanyListPopUpFactory", "ProjNoteBookFactory", "EpsService", "TreeService","stylesService", "ngGridService", function($rootScope, $scope, $state, $q, ProjectNotesService, EmpRegisterService, MaterialRegisterService, PlantRegisterService, ngDialog, GenericAlertService,CompanyListPopUpFactory, ProjNoteBookFactory, EpsService,TreeService, stylesService, ngGridService) {

			$rootScope.projId = null;
			$scope.treeData = [];
			var deferred = $q.defer();
			$scope.stylesSvc = stylesService;
			$scope.moreFlag = 0;
			$scope.currentTab = null;
			$scope.currentTab1 = null;

			var editNoteBook = [];
			$scope.editing = false;
			$scope.tableData = [];
			var getReq = {
				"status" : 1,
				"projId" : null
			};

			// leaf highlighted
			$scope.setClickedRow = function(row) {
				$scope.selectedRow = row;
			}
			// end
			/*---------Project Tabs----------*/

			$scope.preventCopyPaste = function() {
				$(document).ready(function() {
					$("body").on("contextmenu cut copy paste", function(e) {
						return false;
					});

				});

			},

			/* EPS Projects */
			$scope.openSettings = function(projId, row) {
				$scope.selectedRow = row;
				$rootScope.projId = projId;
				$scope.getProjNoteBooks();
				$scope.selectedRow = row;
			}, 
			$scope.getEPSDetails = function() {
				var epsReq = {
					"status" : 1
				};
				EpsService.getEPSUserProjects(epsReq).then(function(data) {
					$scope.epsData = populateData(data.epsProjs);
					
					$scope.epsData.map(eps => {
						$scope.itemClick(eps, false);
					});
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
				});
			};

			var populateData = function (data) {
				return TreeService.populateTreeData(data, 0, [], 'projCode', 'childProjs');
			}
			
			$scope.itemClick = function(item, collapse) {
				TreeService.treeItemClick(item, collapse, 'childProjs');
			};

			/* Note Book Tab */
			$scope.getProjNoteBooks = function() {
				var getNoteBookReq = {
					"status" : 1,
					"projId" : $rootScope.projId
				};
				ProjectNotesService.getProjNoteBook(getNoteBookReq).then(
				function(data) {
					$scope.projNoteBookDetails = data.projNoteBookTOs;
					$scope.gridOptions.data = angular.copy($scope.projNoteBookDetails);
				},
				function(error) {
					GenericAlertService.alertMessage(
				"Error occured while getting Note Book Details", "Error");
				});
			},
			$scope.rowSelectNoteBook = function(notebook) {
				if (notebook.select) {
					editNoteBook.push(notebook);
				} else {
					editNoteBook.splice(editNoteBook.indexOf(notebook), 1);
				}
			},
			$scope.editNoteBookDetails = function(actionType, projId) {
				angular.forEach(editNoteBook, function(value) {
					value.select = false;
				});
				if ($rootScope.projId == undefined && actionType == 'Add') {
					GenericAlertService.alertMessage("Please select atleast one project", 'Warning');
					return;
				}
				if (editNoteBook.length > 0 && actionType == 'Add') {
					editNoteBook=[];
					GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
					return;
				}
				if (actionType == 'Edit' && editNoteBook <= 0) {
					GenericAlertService.alertMessage("Please select atleast one row to modify",
							'Warning');
					return;
				} else if ($scope.projId !== undefined && $scope.projId != null) {
					var popupDetails = ProjNoteBookFactory.noteBookPopupDetails(actionType, projId,
							editNoteBook);
					editNoteBook = [];
					popupDetails.then(function(data) {
						$scope.projNoteBookDetails = data.projNoteBookTOs;
						editNoteBook = [];
						$scope.getProjNoteBooks();
					}, function(error) {
						GenericAlertService.alertMessage(
								"Error occurred while selecting Project NoteBook Details", 'Info');
					})
				}
			}
			
			var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.rowSelectNoteBook(row.entity)" >';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'topic', displayName: "Note Book Topic", headerTooltip: "Note Book Topic", },
						{ field: 'description', displayName: "Description", headerTooltip: "Description",},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Project_ProjectNotes");
				}
			});	
			var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/projectshelp/projnoteshelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}		
	}]);