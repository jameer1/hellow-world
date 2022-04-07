'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("folder", {
		url : '/folders',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/documents/folder.html',
				controller : 'FolderController'
			}
		}
	})
}]).controller('FolderController',
		["$scope", "blockUI", "EpsProjectSelectFactory", "DocumentService", "ProjFolderUserFactory", "$rootScope", "$state", "$q", "ngDialog", "EpsService", "ProjEmpClassService", "GenericAlertService", "UserEPSProjectService", "TreeService", function($scope, blockUI, EpsProjectSelectFactory, DocumentService, ProjFolderUserFactory, $rootScope, $state, $q, ngDialog, EpsService,
				ProjEmpClassService, GenericAlertService, UserEPSProjectService,TreeService) {

			var selectedFolders = [];
			var selectedFolderType = [];
			var removeFromDelete = [];
			var folderId = [];
			var parentScope = $scope;
			$scope.editTreeData;
			$scope.itemId1 = 1;
			$scope.isExpand1 = true;
			$scope.writeValue=null;
			$scope.readValue=null;
			$scope.itemClick1 = function(itemId, expand) {
				$scope.itemId1 = itemId;
				$scope.isExpand1 = !expand;
			};
			$scope.documentsData = [];
			$scope.getUserProjects = function() {
				var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
				userProjectSelection.then(function(data) {
					$scope.searchProject = data.searchProject;
					$scope.searchProject.projId = data.searchProject.projId;
					//$scope.getFolderDetails();
					//$scope.getFolderPermissions();
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			},

			$scope.getFolderDetails = function() {
				if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0) {
					GenericAlertService.alertMessage("Please select Project", 'Warning');
					return;
				}

				var req = {
					"status" : 1,
					"projId" : $scope.searchProject.projId
				};
				
				DocumentService.getProjDoc(req).then(function(data) {
					console.log(data);
					$scope.documentsData = TreeService.populateTreeData(data.projDocFolderTOs, 0, [], 'name', 'childProjDocFolderTOs');
					$scope.documentsData.map(documents => {
						$scope.itemClick(documents, false);
					});

				})
			},

			$scope.itemClick = function(item, collapse) {
				TreeService.treeItemClick(item, collapse, 'childProjDocFolderTOs');
			}

			//changes eps tree
			var epspopup;
			var addEPSservice = {};
			//eps pencil icon usage
			$scope.editTreeDetails = function(actionType, itemData, folderType) {
				$scope.treeFlag = false;
				console.log(itemData);
				epspopup = addEPSservice.addTreeDetails(actionType, itemData,folderType, $scope.searchProject.projId);
				epspopup.then(function(data) {
					$scope.folderData= populateFolderTreeData(data.projDocFolderTOs);
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Details  ", 'Error');
				});
			}

			function populateFolderTreeData(data){
				return TreeService.populateTreeData(data, 0, [], 'name', 'childProjDocFolderTOs');
			}

			addEPSservice.addTreeDetails = function(actionType, itemData, folderType, projId) {
				var deferred = $q.defer();
				epspopup = ngDialog.open({
					template : 'views/documents/folderpopup.html',
					className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
					scope : $scope,
					closeByDocument : false,
					showClose : false,
					controller : [ '$scope', function($scope) {
						$scope.action = actionType;
						$scope.editTreeData = {'projId':projId};
						$scope.editTreeData = TreeService.populateDynamicTreeData([itemData], 0, [], 'name','childProjDocFolderTOs');
						console.log(folderType);
						$scope.addTreeSubGroup = function(tabData, index) {
							console.log("addTreeSubGroup function");
							console.log(index);
							console.log(tabData);
							console.log(selectedFolderType+"->"+actionType);
							//console.log(itemData);
							/*let obj = angular.copy({
							   "select" : false,
								"id" : null,
								"name":null,
								"projId":projId,
								"parentId" : tabData.id,
								"level" : (tabData.level + 1),
								"collapse" : false,
								"expand" : true,
								"childProjDocFolderTOs" : []
							});
							if( $scope.action == "Edit" )
							{
								obj.folderType = folderType;
							}*/
							//console.log(obj);
							/*if( tabData.name == null )
							{
								blockUI.stop();
								GenericAlertService.alertMessage("Please enter the folder name", "Error");								
							}
							else
							{
								$scope.editTreeData = TreeService.addItemToTree($scope.editTreeData, tabData, obj, index, 'childProjDocFolderTOs');
							}*/
							/*console.log($scope.checkDataValidationRecursively(obj,index));
							if( $scope.checkDataValidationRecursively(obj,index) )
							{
								console.log("if condition");
								$scope.editTreeData = TreeService.addItemToTree($scope.editTreeData, tabData, obj, index, 'childProjDocFolderTOs');
							}*/
							//$scope.editTreeData = TreeService.addItemToTree($scope.editTreeData, tabData, obj, index, 'childProjDocFolderTOs');
							if($scope.checkDataValidationRecursively(tabData,index))
							{
								let obj = angular.copy({
								   "select" : false,
									"id" : null,
									"name":null,
									"projId":projId,
									"parentId" : tabData.id,
									"level" : (tabData.level + 1),
									"collapse" : false,
									"expand" : true,
									"childProjDocFolderTOs" : []
								});
								if( $scope.action == "Edit" )
								{
									obj.folderType = folderType;
								}
								$scope.editTreeData = TreeService.addItemToTree($scope.editTreeData, tabData, obj, index, 'childProjDocFolderTOs');	
							}	
						}
						$scope.deleteTree = function (index) {
							TreeService.deleteDynamicTreeItem($scope.editTreeData, index);
						}

						$scope.poupTreeAssetItemClick = function(item, collapse){
							TreeService.dynamicTreeItemClick($scope.editTreeData, item, collapse);
						}
						$scope.checkDataValidationRecursively = function(tabData,index) {
							console.log(tabData);
							if(!tabData.name) {
								tabData.invalidField = true;
								return false;
							}
							else
							{
								tabData.invalidField = false;
							}
		
							for (++index; index < $scope.editTreeData.length; index++) {
								return $scope.checkDataValidationRecursively($scope.editTreeData[index], index);
							}
							return true;
						}
						//save Folders
						$scope.saveDocFolder = function() {
							const data = TreeService.extractTreeDataForSaving($scope.editTreeData, 'childProjDocFolderTOs');
							var error_cnt = 0;
							var folderReq = {
									"projDocFolderTOs" : data
								};
								console.log(data[0]);								
								blockUI.start();
								if( error_cnt > 0 )
								{
									GenericAlertService.alertMessage("Please enter the folder name", "Error");
									blockUI.stop();
								}
								else
								{
									console.log(folderReq);
									DocumentService.saveProjDocFolders(folderReq).then(function(data) {
										blockUI.stop();
										if (data.status != null && data.status !== undefined && data.status === 'Info') {
											var succMsg = GenericAlertService.alertMessageModal('Folders(s) popup are saved successfully ',"Info");
											succMsg.then(function() {
												$scope.closeThisDialog();
	
												var req = {
													"status" : 1,
													"projId" : parentScope.searchProject.projId
												};
	
												DocumentService.getProjDocFolders(req).then(function(data) {
													parentScope.documentsData = TreeService.populateTreeData(data.projDocFolderTOs, 0, [], 'name', 'childProjDocFolderTOs');
	
													parentScope.documentsData.map(documents => {
														parentScope.itemClick(documents, false);
													});
												});
	
											}, function(error) {
												blockUI.stop();
												GenericAlertService.alertMessage("Error occured while saving Folder Details", "Error");
											});
										}
										$scope.getSearchAssetRegisters();
									});
								}
						}
					} ]
				});
				return deferred.promise;
			},

			$scope.selectFolders = function(item ,index, folderType) {
				console.log(folderType);
				/*let parentid =item.parentId;
				 if(index === 0){
			            angular.forEach(function(slave, ind){
			                $scope.item[ind].select = $scope.item[0].select;
			            });
			        }
			        else {
			            var anyChild = true;
			            for(var i = 1; i < item.childProjDocFolderTOs.length; i++){
			            	
			                anyChild = anyChild || item[i].select; 
			            }
			                $scope.item.select = anyChild;
			        }*/
				let projId = item.projId;

				if (item.select) {
					
					/*if(item.childProjDocFolderTOs.length==0){
						selectedFolders.push(item.id);
				
					}else{
					if(item.childProjDocFolderTOs.length>0){
			
						angular.forEach(item.childProjDocFolderTOs,function(value,key)
						{
							console.log(JSON.stringify(value.id))
						selectedFolders.push(item.childProjDocFolderTOs[0].id);

					  });
					}else{
						selectedFolders.push(item.id);
					}
					}*/
					selectedFolders.push(item.id);
					selectedFolderType.push(item.folderType);
					console.log('selectedFolders'+JSON.stringify(selectedFolders))
					if( projId === null ){
						removeFromDelete.push(item.id);
					}
				} else {
					//alert('else')
					selectedFolders.splice(selectedFolders.indexOf(item.id), 1);
					selectedFolderType.splice(selectedFolderType.indexOf(item.folderType), 1);

					if( projId === null && removeFromDelete.indexOf(item.id) > -1 ){
						removeFromDelete.splice(removeFromDelete.indexOf(item.id), 1);
					}
				}
			},

			$scope.deleteFolders = function() {
				if (selectedFolders.length < 0) {
					GenericAlertService.alertMessage("Please select a folder", 'Warning');
					return;
				}

				if (removeFromDelete.length > 0) {
					let documentsDataSelect = document.querySelectorAll('.documentsData');
					let selectBox, selectionId;

					for(let index = 0; index < documentsDataSelect.length; index++ ){
						selectBox = documentsDataSelect[index];
						selectionId = Number(selectBox.getAttribute('data-selection'));

						if( selectBox.checked && removeFromDelete.indexOf(selectionId) > -1 ){
							selectBox.checked = false;
							removeFromDelete.splice(removeFromDelete.indexOf(selectionId), 1);
							selectedFolders.splice(selectedFolders.indexOf(selectionId), 1);
							selectedFolderType.splice(selectedFolderType.indexOf(selectionId), 1);
						}
					}

					GenericAlertService.alertMessage("Default Folders can not be deleted. Removing Default Folders from Selection", 'Warning');
					return;
				}

				var deleteProjDocFolderReq = {
						"pojDocFolderIds" : selectedFolders,
						"projId" : $scope.searchProject.projId
					}
				DocumentService.deleteProjFolders(deleteProjDocFolderReq).then(function(data) {
					// var succMsg = GenericAlertService.alertMessageModal('Project Doc Folder deactivation is'+ data.message, data.status);
					var succMsg = GenericAlertService.alertMessageModal('Project document folder deleted successfully',"Info");
					succMsg.then(function() {
						$scope.documentsData = TreeService.populateTreeData(data.projDocFolderTOs, 0, [], 'name', 'childProjDocFolderTOs');
						$scope.documentsData.map(documents => {
							$scope.itemClick(documents, false);
						});
					});
				}, function(error) {
					if (error.status != null && error.status != undefined) {
						GenericAlertService.alertMessage(error.message, error.status);
					} else {
						GenericAlertService.alertMessage('Project Doc Folder deactivation is/are Failed', "Error");
					}
				});
			},

			$scope.getFolderPermissions = function() {
				var req = {
						"projId" : $scope.searchProject.projId,
					};
				var folderPermissions = DocumentService.getFolderPermissions(req);
				folderPermissions.then(function(data) {

					angular.forEach(data.labelKeyTOsmap,function(value,key)
							{

						$scope.writeValue=value.displayNamesMap.writevalue;
						$scope.readValue=value.displayNamesMap.readvalue;

						  });


				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			},
			$scope.getProjDocFolderPermissions = function() {
				if ($scope.searchProject.projId == undefined) {
					GenericAlertService.alertMessage("Please select Project", 'Warning');
					return;
				}
				if (selectedFolders.length <= 0) {
					GenericAlertService.alertMessage('Please select a folder', "Warning");
					return;
				}
				if (selectedFolders.length > 1) {
					GenericAlertService.alertMessage('Please select only one folder', "Warning");
					return;
				}
				var selectedUser = ProjFolderUserFactory.getProjDocFolderPermissions(selectedFolders, selectedFolderType, $scope.searchProject.projId);
			},
			/*$scope.checkDataValidationRecursively = function (tabData, index) {
				console.log("checkDataValidationRecursively function");
				console.log(tabData);
				if (!tabData.code || !tabData.name) {
					tabData.invalidField = true;
					return false;
				}


				for (++index; index < $scope.editTreeData.length; index++) {
					return $scope.checkDataValidationRecursively($scope.editTreeData[index], index);
				}
				return true;
			}*/
			$scope.reset = function() {
				$scope.searchProject = null;
				$scope.refdocuments = [];
				$scope.editTreeData = [];
				$scope.documentsData = '';
			}
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
			template: 'views/help&tutorials/documentshelp/projectfolderhelp.html',
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
