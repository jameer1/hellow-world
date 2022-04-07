'use strict';

app.factory('ProjNoteBookFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjectNotesService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, ProjectNotesService, GenericAlertService) {
	var projNoteBookPopup;
	var service = {};
	service.noteBookPopupDetails = function (actionType, projId, editNoteBook) {
		var deferred = $q.defer();
		projNoteBookPopup = ngDialog.open({
			template: 'views/projectnotes/notebookpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom5',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.action = actionType;
				var selectedProject = [];
				$scope.noteBookList = [];
				var selectedNotebooks = [];
				if (actionType === 'Add') {
					$scope.noteBookList.push({
						'projId': $scope.projId,
						'selected': false,
						'topic': null,
						'description': null,
						'status': 1
					});
				} else {
					$scope.noteBookList = angular.copy(editNoteBook);
					editNoteBook = [];
				}
				var noteBookService = {};
				$scope.projNoteBookPopUp = function (tab) {
					var projNotebookPopup = noteBookService.getProjNoteBook();
					projNotebookPopup.then(function (data) {
						$scope.projNoteBookDetails = data.projNoteBookTOs;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting project material class Details", 'Error');
					});
				}

				$scope.addRows = function () {

					$scope.noteBookList.push({
						'projId': $scope.projId,
						'selected': false,
						'topic': null,
						'description': null,
						'status': 1

					});
				}, $scope.saveNoteBook = function () {
					var req = {
						"projNoteBookTOs": $scope.noteBookList,
						"projId": $scope.projId
					}
					blockUI.start();
					ProjectNotesService.saveProjNoteBook(req).then(function (data) {
						blockUI.stop();
						var results = data.projNoteBookTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Notebook(s) is/are  ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Notebook(s) saved successfully',"Info");
						succMsg.then(function (data) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"projNoteBookTOs": results
							}
							deferred.resolve(returnPopObj);
						})
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Notebook(s)  is/are failed to save', "Error");
					});
				},

					$scope.notebooksPopUpRowSelect = function (notebooks) {
						if (notebooks.selected) {
							selectedNotebooks.push(notebooks);
						} else {
							selectedNotebooks.splice(selectedNotebooks.indexOf(notebooks), 1);
						}
					}, $scope.deleteProjRows = function () {
						if (selectedNotebooks.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						} else
							if (selectedNotebooks.length < $scope.noteBookList.length) {
								angular.forEach(selectedNotebooks, function (value, key) {
									$scope.noteBookList.splice($scope.noteBookList.indexOf(value), 1);
								});
								GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
								selectedNotebooks = [];
							} else if (selectedNotebooks.length > 1) {
								GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
							} else if (selectedNotebooks.length == 1) {
								$scope.noteBookList[0] = {
									'projId': $scope.projId,
									'selected': false,
									'topic': null,
									'description': null,
									'status': 1
								};
								selectedNotebooks = [];
								GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
							}
					}
			}]
		});
		return deferred.promise;
	}
	return service;
}]);