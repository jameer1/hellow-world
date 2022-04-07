'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("document", {
		url: '/document',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/documents/document.html',
				controller: 'DocumentController'
			}
		}
	})
}]).controller(
	'DocumentController',
	["$scope", "uiGridGroupingConstants","uiGridConstants","blockUI", "EpsProjectSelectFactory", "DocumentService", "ProjFolderUserFactory", "$rootScope", "$state", "$q", "ngDialog", "EpsService", "ProjEmpClassService", 'stylesService', 'ngGridService',"GenericAlertService", "UserEPSProjectService", "Principal", "TreeService", "AssetRegisterService", function ($scope,uiGridGroupingConstants, uiGridConstants, blockUI, EpsProjectSelectFactory, DocumentService, ProjFolderUserFactory, $rootScope, $state, $q, ngDialog, EpsService,
		ProjEmpClassService,stylesService, ngGridService, GenericAlertService, UserEPSProjectService, Principal, TreeService, AssetRegisterService) {
		$scope.stylesSvc = stylesService;
		$scope.refdocuments = [];
		$scope.usersAccess = [];
		$scope.selectedUser = [];
		$scope.accessUsers = [];
		$scope.permissionusers = [];
		$scope.projDocFolderTO = $scope.editTreeData;
		$scope.itemId1 = 1;
		$scope.isExpand1 = true;
		$scope.itemClick1 = function (itemId, expand) {
			$scope.itemId1 = itemId;
			$scope.isExpand1 = !expand;
		};
		$scope.refdocuments1 = [];
		$scope.folderFlag1 = false;
		$scope.folderFlag = false;
		$scope.itemId = 1;
		$scope.isExpand = true;
		$scope.referenceval = [];
		$scope.selectedFileMap = [];
		$scope.itemId = 1;
		var selectedItem = [];
		var selectedFolders = [];
		var selectedDocumentsList = [];
		$scope.currentlyOpenedDocFolder = null;
		$scope.refdocumentscnt = 0;


		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				console.log('data.searchProject'+JSON.stringify(data.searchProject))
				$scope.searchProject = data.searchProject;
				$scope.searchProject.projId = data.searchProject.projId;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		},

			$scope.selectFolders = function (folderObj) {
				console.log("selectFolders function");				
				if (folderObj.select) {
					console.log("if condition");
					selectedFolders.push(folderObj);
				} else {
					selectedFolders.pop(folderObj);
				}
			},
			$scope.getFolderPermissions = function () {
				var req = {
					"projId": $scope.searchProject.projId,

				};
				var folderPermissions = DocumentService.getFolderPermissions(req);
				folderPermissions.then(function (data) {
					$scope.readValueMap = {};
					$scope.writeValueMap = {};
					angular.forEach(data.projDocFolderPermissionTOs, function (value, key) {
						$scope.readValueMap[value.folderId] = value.readAccess;
						$scope.writeValueMap[value.folderId] = value.writeAccess;
						var folder = findFolderById(value.folderId);
						folder && givePermissionsToChildRecursively(folder, value.readAccess, value.writeAccess);
					});
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			},

			$scope.getFolderDetails = function () {
				if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0) {
					GenericAlertService.alertMessage("Please select Project", 'Warning');
					return;
				}
				
				var req = {
					"status": 1,
					"projId": $scope.searchProject.projId
				};
				console.log("getFolderDetails from documentcontroller");
				DocumentService.getProjDocFolders(req).then(function (data) {
					/*
					angular.forEach($scope.projectList, function(value) {
						if(value.startDate=="" || value.startDate==null){
							GenericAlertService.alertMessage('Please enter start date', 'Warning');
							forEach.break();
							return;
						}
						var startDate = new Date(value.startDate);
						var finishDate = new Date(value.finishDate);
						if (startDate > finishDate) {
							GenericAlertService.alertMessage('Start date must be < Finish date', 'Warning');
							forEach.break();
							return;
						}
					})
					*/
					/*$scope.documentsList = [];
					angular.forEach( data.projDocFolderTOs, function(key, value){
						if( key.childProjDocFolderTOs.length != 0 )
						{
							console.log("if condition-"+key.name);
							$scope.documentsList.push(key);
						}
					});*/
					//console.log($scope.documentsList);	
					console.log("getProjDocFolders function");
					/*$scope.documentsData = TreeService.populateTreeData($scope.documentsList, 0, [], 'name', 'childProjDocFolderTOs');					
					$scope.documentsData.map(documents => {
						$scope.itemClick(documents, false);
					});*/
					$scope.documentsData = TreeService.populateTreeData(data.projDocFolderTOs, 0, [], 'name', 'childProjDocFolderTOs');
					console.log($scope.documentsData);
					$scope.documentsData.map(documents => {
						$scope.itemClick(documents, false);
					});
				})
			},
			$scope.Edit = function () {
				if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0) {
					GenericAlertService.alertMessage("Please select Project and folder", 'Warning');
					return;
				}
				if ($scope.documentsData.name == null || $scope.documentsData.name == '' || $scope.documentsData.name == undefined) {
					GenericAlertService.alertMessage("Please select Folder", 'Warning');
					return;
				}
			},
			$scope.downloadProjDocs = function(docId,fileName) {
				let req = {
					"recordId" : docId,
					"fileName" : fileName
				}
				console.log(req);
				DocumentService.downloadProjDocs(req);
			},
			$scope.itemClick = function (item, collapse) {
				//console.log(item);
				TreeService.treeItemClick(item, collapse, 'childProjDocFolderTOs');
			},

			$scope.addDocuments = function () {
				console.log("addDocuments function");
				console.log(selectedItem);
				let message = "";	
				if (selectedItem.length <= 0) {
					message += "Please select a folder";
				}
				if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0) {					
					message += "Please select Project and folder";
				}
				if( message != "" )
				{
					GenericAlertService.alertMessage("Please select Project and folder", 'Warning');
					return;	
				}				
				var refDocumentsPopup = ngDialog.open({
					template: 'views/documents/refdocumentpopup.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom4',
					closeByDocument: false,
					scope: $scope,
					showClose: false,
					controller: ['$scope','$rootScope', function ($scope,$rootScope) {
						$scope.refdocuments1.push({
							"select": false,
							"code": '',
							"name": '',
							"fileContentId": '',
							"fileType": '',
							"fileSize": '',
							"version": '',
							"fileStatus": '',
							"parentId": selectedItem[0].parentId,
							"parentName": selectedItem[0].parentName,
							"description": '',
							"displayVersionError" : true,
							"displayUploadError" : true,
							"displayDuplicateVersionError" : true
						});
						console.log($scope.refdocuments1);
						//$scope.refdocuments = [];	
						$scope.saveReferenceDocumentList = function () {
							var files = [];
							console.log($scope.refdocuments1);
							console.log($scope.selectedFileMap);
							console.log('$scope.searchProject '+JSON.stringify($scope.searchProject))
							
							let isError = false;
							angular.forEach($scope.refdocuments1, function (value, key) {
								console.log(value);
								console.log($scope.selectedFileMap);
								if( $scope.selectedFileMap[key] == undefined ||  $scope.selectedFileMap[key].name == null )
								{
									isError = true;
									value.displayUploadError = false;
								}
								else
								{
									isError = false;
									value.displayUploadError = true;
								}
								if( value.version == "" || value.version == undefined )
								{
									isError = true;
									value.displayVersionError = false;														
								}
								else
								{
									value.displayVersionError = true;	
									let isNotSameVersion = true;
									isError = false;
									if( key > 0 )
									{
										for( let k=key-1;k>=0;k-- )
										{
											if( $scope.refdocuments1[k].version == value.version )
											{
												isError = true;
												isNotSameVersion = false;
											}
										}
									}
									value.displayDuplicateVersionError = isNotSameVersion;	
									console.log("else condition")									
								}																
							});
							
							for (let index = 0; index < $scope.selectedFileMap.length; index++) {
								const value = $scope.selectedFileMap[index];
								if (value) {
									$scope.refdocuments1[index].fileObjectIndex = files.length;
									files.push(value);
								}
							}
							var req = {
								"projDocFileTOs": $scope.refdocuments1,
								"projId": $scope.searchProject.projId,
								"status": 1,
								"folderId": selectedItem[0].id,
								"createdBy": $rootScope.account.userId,
								"updatedBy": $rootScope.account.userId
							}
							console.log(isError);
							if( !isError )
							{
								console.log("if condition");
								DocumentService.saveProjDocFilesByFolder(files, req).then(function (data) {
										//blockUI.stop();
										var fileUploadSuccessMsgPopup = GenericAlertService.alertMessageModal(' Document Details are successfully saved', "Info");									
										fileUploadSuccessMsgPopup.then(function(){
											ngDialog.close(refDocumentsPopup);
											$scope.getDocumentDetails($scope.currentlyOpenedDocFolder);
										});									
									}, 
									function (error) {
										blockUI.stop();
										GenericAlertService.alertMessage('Document Details are failed to save', 'Error');
								});
								console.log(req);
								console.log(files);
							}
						},
						$scope.addRows = function () {
							$scope.refdocuments1.push(angular.copy({
								"select": false,
								"code": '',
								"name": '',
								"fileContentId": '',
								"fileType": '',
								"fileSize": '',
								"version": '',
								"fileStatus": '',
								"parentId": selectedItem[0].parentId,
								"parentName": selectedItem[0].parentName,
								"description":'',
								"displayVersionError" : true,
								"displayUploadError" : true,
								"displayDuplicateVersionError" : true
							}));							
						},
						$scope.deleteRows = function () {							
							const tempRefDocRequest = [];
							var flag = false;
							for (let index = 0; index < $scope.refdocuments1.length; index++) {
								if ($scope.refdocuments1[index].select) {
									$scope.selectedFileMap[index] = null;
									flag = true;
								} else {
									tempRefDocRequest.push($scope.refdocuments1[index]);
								}
							}
							for (let index = 0; index < $scope.refdocuments1.length; index++) {
								if ($scope.refdocuments1[index].select <= 0) {
									flag = false;
								}
							}
							if (flag == true) {
								GenericAlertService.alertMessage("Document(s) deleted successfully", "Info");
							} else {
								GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");
							}
							$scope.refdocuments1 = tempRefDocRequest;
						},
						$scope.fileUpload = function (fileObject, refdocument, index) {
							if (fileObject) {
								// Max file size should be 5 MB
								if (fileObject.size > 5 * 1024 * 1024) {
									GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
									return;
								}
								refdocument.name = fileObject.name;
								refdocument.fileType = fileObject.type;
								refdocument.fileSize = formatBytes(fileObject.size);
							} else {
								refdocument.name = null;
								refdocument.fileType = null;
								refdocument.fileSize = null;
							}
							console.log(fileObject);
							$scope.selectedFileMap[index] = fileObject;
						}
						function formatBytes(bytes) {
							if(bytes < 1024) return bytes + " Bytes";
							else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
							else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
							else return(bytes / 1073741824).toFixed(3) + " GB";
						}
						ngDialog.close(refDocumentsPopup);						
					}]
				});
			}

			/*$scope.getDocumentDetails = function(item) {
				selectedDocumentsList = [];
				var req = {
					"status" : 1,
					"projId" : $scope.searchProject.projId,
				};
				DocumentService.getProjDocFilesByFolder(req).then(function(data) {
					$scope.refdocuments = data.projDocFileTOs;
					$scope.currentlyOpenedDocFolder = item;
				})
			},*/
			$scope.searchDocuments = function() {
				console.log("search term:"+$scope.name);
			}
			$scope.getDocumentDetails = function (item) {
				selectedItem.push(item);
				selectedDocumentsList = [];
				var req = {
					"status": 1,
					"projId": $scope.searchProject.projId,
					"folderCategory" : item.name
				};
				console.log(req);
				console.log("getDocumentDetails");
				console.log(item);
				$scope.addDocumentsBtnDisabled = true;
				$scope.deleteDocumentsBtnDisabled = true;
				$scope.editDocumentsBtnDisabled = true;
				if( item.folderType == "CUSTOM" )
				{
					if( item.isWriteAccess == true && item.isWriteAccess != null )
					{
						$scope.addDocumentsBtnDisabled = false;
						$scope.deleteDocumentsBtnDisabled = false;
						$scope.editDocumentsBtnDisabled = false;
					}
				}
				$scope.selectedDocumentData = item; 
				console.log(req);
				// commented the below code as selected folder can be handled in one go by passing the folder name
				/*if (item.name == "Purchase/Acquisition") {
					DocumentService.getPurchaseDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Asset Sale Records") {
					DocumentService.getSaleRecordsDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Long Term Leasing/Rental History") {
					DocumentService.getRentalHistoryDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Short Term/Casual Occupancy Records & Rental Returns") {
					DocumentService.getShortTermDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Long Term Lease:Rental/Revenue-Actual Revenue") {
					DocumentService.getLongTermDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Car Parking and Toll Collections-Actual Revenue") {
					DocumentService.getCarParkingDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Periodical and Schedule maintenance Records") {
					DocumentService.getPeriodicalDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Repairs and Non Schedule maintenance Records") {
					DocumentService.getRepairsDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Asset Life Status") {
					DocumentService.getAssetLifeDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "Asset Cost and Value Status") {
					DocumentService.getAssetCostDocuments(req).then(function (data) {
						$scope.refdocuments = data.documentsDtlTOs;
						$scope.currentlyOpenedDocFolder = item;
					})
				}
				else if (item.name == "PreContract List-Reference Documents") {
					console.log("if block PreContract List-Reference Documents");
				}*/
				DocumentService.getProjDocumentsByProjectId(req).then(function(data){
					$scope.refdocuments = data.projDocFileTOs;
					$scope.refdocumentscnt = data.projDocFileTOs.length;
					var dummyDocument = angular.copy($scope.refdocuments).map((item) => {
					if(item.status == 1){
					item.status = 'Active'}
					else{
					item.status = 'Inactive'}
					return item;
					});
					$scope.gridOptions.data = dummyDocument;
					console.log($scope.refdocuments);
				})

			},

			$scope.rowselect = function (reference) {
				var itemIndex = selectedDocumentsList.indexOf(reference);
				if (reference.select && itemIndex == -1) {
					selectedDocumentsList.push(reference);
				} else {
					(itemIndex != -1) && selectedDocumentsList.splice(itemIndex, 1);
				}
			},

			$scope.deleteDocs = function () {
				if ($scope.searchProject == undefined || $scope.searchProject.projId <= 0) {
					GenericAlertService.alertMessage("Please select Project and folder", 'Warning');
					return;
				}
				if (selectedDocumentsList.length <= 0) {
					GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

				} else {
					var deleteIds = new Array();
					var value;
					for (var index = 0; index < selectedDocumentsList.length; index++) {
						value = selectedDocumentsList[index];
						if (value.id) {
							if ($scope.account.userId == value.createdBy) {
								deleteIds.push(value.id);
							} else {
								GenericAlertService.alertMessage("You can't delete file which is created by other person, Please deselect it", 'Warning');
								return;
							}
						} else {
							$scope.refdocuments.splice($scope.refdocuments.indexOf(value), 1);
							return;
						}
					}
					DocumentService.deleteProjDocs(deleteIds).then(function (data) {
						$scope.getDocumentDetails($scope.currentlyOpenedDocFolder);
						GenericAlertService.alertMessage('Files are deleted Successfully', "Info");
					}, function (error) {
						GenericAlertService.alertMessage('Files are failed to delete', "Error");
					});

				}
			},
			$scope.reset = function () {
				$scope.searchProject = null;
				$scope.refdocuments = [];
				$scope.editTreeData = [];
				$scope.documentsData = '';
			},
			$scope.downloadDocs = function (fileId) {
				//DocumentService.downloadDocs(fileId);
				console.log("downloadDocs function"+fileId);
			};

		$scope.expandAll = function (item) {
			$scope.isExpand1 = true;
			$scope.itemId1 = item.id;
			expandChildsRecursively(item, true);
		};

		$scope.collapseAll = function (item) {
			$scope.isExpand1 = false;
			$scope.itemId1 = item.id;
			expandChildsRecursively(item, false);
		};

		$scope.deleteFolder = function (folder) {
			if (folder.childProjDocFolderTOs.length > 0) {
				GenericAlertService.alertMessage('Folder contains child folders, please delete them first', "Warning");
				return;
			}
			blockUI.start();
			DocumentService.deleteProjFolders(folder.id).then(function (data) {
				blockUI.stop();
				findAndDeleteFolder($scope.editTreeData, folder.id);
				GenericAlertService.alertMessage('Folder is deleted Successfully', "Info");
			}, function (error) {
				blockUI.stop();
				if (error.status === 412) {
					GenericAlertService.alertMessage(error.data.message, "Warning");
				} else {
					GenericAlertService.alertMessage('Folder is failed to delete', "Error");
				}

			});
		};
		var linkCellTemplate = '<span class="fa fa-download" ng-click="grid.appScope.downloadProjDocs(id,name)" class="fa fa-download"></span>';
     $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
                {				
	                name: 'code',
					cellTemplate: '<div>{{row.entity.code}}-true</div>',
					displayName: "Document ID", headerTooltip: "Document ID" },
				{ field: 'code', displayName: "File Name", headerTooltip: "File Name", groupingShowAggregationMenu: false },
				{ field: 'name' , displayName: "Description", headerTooltip: "Description", groupingShowAggregationMenu: false },
				{ field: 'description', displayName: "Version", headerTooltip: "Version", groupingShowAggregationMenu: false },
				{ field: 'version' , displayName: "Upload / View File", headerTooltip: "Upload / View File", groupingShowAggregationMenu: false },
				{ field: 'fileType', displayName: "File Type", headerTooltip: "File Type", groupingShowAggregationMenu: false },
				{ field: 'fileSize' , displayName: "File Size", headerTooltip: "File Size", groupingShowAggregationMenu: false },
				{ name: 'createdOn',displayName:'Created Date',headerTooltip: "Created Date", cellTemplate: '<div class="ngCellText">&nbsp{{row.entity.createdOn|date:"MM-dd-yyyy"}}</div>'},
		        {
                   field: 'status', displayName:'status',headerTooltip: "status", },
				{  name: 'docUrl',
				   cellTemplate: linkCellTemplate, 
				   displayName: "Download", headerTooltip : "Download" }
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Documents_Project_Documents");
			$scope.gridOptions.showColumnFooter = false;
		}
	});
		/**
		 * Fetch logged-in user account
		 */
		Principal.identity().then(function (acc) {
			$scope.account = acc;
		});

		/**
		  * Assign parent permission to childs as well.
		  * @param {*} parentObject 
		  * @param {*} permissionValue 
		  * @param {*} resultMap 
		  */
		function givePermissionsToChildRecursively(parentObject, readPermission, writePermission) {
			var child = null;
			for (var index = 0; index < parentObject.childProjDocFolderTOs.length; index++) {
				child = parentObject.childProjDocFolderTOs[index];
				$scope.readValueMap[child.id] = readPermission;
				$scope.writeValueMap[child.id] = writePermission;
				if (child.childProjDocFolderTOs.length > 0) {
					givePermissionsToChildRecursively(child, readPermission, writePermission);
				}
			}
		}

		function findFolderById(folderId) {
			for (var index = 0; index < $scope.editTreeData.length; index++) {
				if ($scope.editTreeData[index].id == folderId) {
					return $scope.editTreeData[index];
				}
			}
		}

		/**
		  * Expan/collapse all childs recursively
		  * @param {*} parentObject 
		  * @param {*} isExpand
		  */
		function expandChildsRecursively(parentObject, isExpand) {
			var child = null;
			parentObject.expand = isExpand;
			for (var index = 0; index < parentObject.childProjDocFolderTOs.length; index++) {
				child = parentObject.childProjDocFolderTOs[index];
				child.expand = isExpand;
				if (child.childProjDocFolderTOs.length > 0) {
					expandChildsRecursively(child, isExpand);
				}
			}
		}

		/**
		  * Find and delete the folder recursively
		  */
		function findAndDeleteFolder(foldersArray, deleteFolderId) {
			for (var index = 0; index < foldersArray.length; index++) {
				if (deleteFolderId === foldersArray[index].id) {
					foldersArray.splice(index, 1);
					return;
				}
				if (foldersArray[index].childProjDocFolderTOs.length > 0) {
					findAndDeleteFolder(foldersArray[index].childProjDocFolderTOs, deleteFolderId);
				}
			}
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
				template: 'views/help&tutorials/documentshelp/projectdocumentshelp.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function ($scope) {
		
				}]
			});
			return deferred.promise;
		}

	}]).

	filter('documentFilterTrees', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o, key) {
				if (o.childProjDocFolderTOs != undefined && o.childProjDocFolderTOs.length > 0) {
					o.level = level;
					o.leaf = false;
					if (o.deleteFlag == undefined || !o.deleteFlag) {
						newObj.push(o);
						if (o.id == itemId) {
							o.expand = isExpand;
						}
						if (o.expand === true) {
							recursive(o.childProjDocFolderTOs, newObj, o.level + 1, itemId, isExpand);
						}
					} else {
						obj.splice(obj.indexOf(o), 1);
						return true;
					}
				} else {
					o.level = level;
					if (o.proj) {
						o.leaf = true;
					} else {
						o.leaf = false;
					}
					if (!o.proj && (o.deleteFlag == undefined || !o.deleteFlag)) {
						newObj.push(o);
					} else if (!o.proj) {
						obj.splice(obj.indexOf(o), 1);
						return true;
					}
					return false;
				}

			});
		}
		return function (obj, itemId, isExpand) {
			var newObj = [];

			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}

	})
	.directive('demoFileModel',  ['$parse', function ($parse) {
        return {
            restrict: 'A', //the directive can be used as an attribute only

            /*
             link is a function that defines functionality of directive
             scope: scope associated with the element
             element: element on which this directive used
             attrs: key value pair of element attributes
             */
            link: function (scope, element, attrs) {
                var model = $parse(attrs.demoFileModel),
                    modelSetter = model.assign; //define a setter for demoFileModel

                //Bind change event on the element
                element.bind('change', function () {
                    //Call apply on scope, it checks for value changes and reflect them on UI
                    scope.$apply(function () {
                        //set the model value
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);
