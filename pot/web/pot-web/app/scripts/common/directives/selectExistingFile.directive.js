angular.module('potApp').directive('selectExistingFile', ["$filter", "$timeout", "$parse", "$q", "ngDialog", "DocumentService", "blockUI", "GenericAlertService", "Principal","TreeService","$rootScope", function ($filter, $timeout, $parse, $q, ngDialog, DocumentService, blockUI, GenericAlertService,
    Principal,TreeService,$rootScope) {

    function openPopup(projId, fileSelected) {
        var dialogVar = undefined;
        var deferred = $q.defer();
        dialogVar = ngDialog.open({
            template: 'views/common/selectExistingFilepopup.html',
            className : 'ngdialog-theme-plain ng-dialogueCustom1',
            closeByDocument: false,
            showClose: false,
            controller: ['$scope', function ($scope) {

                $scope.itemId1 = 1;
                $scope.isExpand1 = true;
                var selectedFolders = [];
                $scope.selectedFileMap = [];
                $scope.fileChoosen = null;
                $scope.refdocuments = [];

               /* $scope.getDocumentDetails = function (item) {
                    $scope.fileChoosen = null;
                    var req = {
                        "status": 1,
                        "id": item.id
                    };
                    DocumentService.getProjDocFilesByFolder(req).then(function (data) {
                        $scope.refdocuments = data.projDocFileTOs;
                        $scope.currentlyOpenedDocFolder = item;

                    });

                };*/
				
				$scope.getDocumentDetails = function (item) {
				console.log(item);
				$scope.currentlyOpenedDocFolder = item;
				selectedFolders.push(item);
				//selectedDocumentsList = [];
				var req = {
					"status": 1,
					"projId": projId,
					"folderCategory" : item.name
				};
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
				DocumentService.getProjDocumentsByProjectId(req).then(function(data){console.log(data);
					$scope.refdocuments = data.projDocFileTOs;
					for(let i=0;i<$scope.refdocuments.length;i++){
						$scope.refdocuments[i].category = item.parent;
					}
					$scope.refdocumentscnt = data.projDocFileTOs.length;
				})

			},
				
                $scope.itemClick1 = function (itemId, expand) {
                    $scope.itemId1 = itemId;
                    $scope.isExpand1 = !expand;
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

                $scope.selectFolders = function (folderObj) {
                    if (folderObj.select) {
                        selectedFolders.push(folderObj);
                    } else {
                        selectedFolders.pop(folderObj);
                    }
                };
                $scope.addDocuments = function () {
                    if (selectedFolders.length <= 0) {
                        GenericAlertService.alertMessage('Please select a folder', "Warning");
                        return;
                    }
                    $scope.refdocuments.push({
                        "select": false,
                        "code": '',
                        "name": '',
                        "fileContentId": '',
                        "fileType": '',
                        "fileSize": '',
                        "version": '',
                        "fileStatus": '',
                        "folderId": '',
                        "parentId": '',
                        "status": 1,
                        "projId": projId,
                    });

                };

                $scope.upload = function (fileObject, refdocument, index) {
                    if (fileObject) {
                        // Max file size should be 5 MB
                        if (fileObject.size > 5 * 1024 * 1024) {
                            GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
                            return;
                        }
                        refdocument.name = fileObject.name;
                        refdocument.fileType = fileObject.type;
                        refdocument.fileSize = fileObject.size;
                    } else {
                        refdocument.name = null;
                        refdocument.fileType = null;
                        refdocument.fileSize = null;
                    }
                    $scope.selectedFileMap[index] = fileObject;
                };

                $scope.saveReferenceDocumentList = function () {
                    if (selectedFolders <= 0) {
                        GenericAlertService.alertMessage('Please select atleast one folder', "Warning");
                        return;
                    }
                    var selectedFolderId = [];

                    angular.forEach(selectedFolders, function (folderObj, key) {
                        selectedFolderId.push(folderObj.id);
                    });
                    var req = {
                        "projDocFileTOs": $scope.refdocuments,
                        "folderId": selectedFolderId[0],
                        "status": 1,
                        "projId": projId,
                        "createdBy": $rootScope.account.userId,
						"updatedBy": $rootScope.account.userId

                    }
                    var filesArray = [];
                    angular.forEach($scope.selectedFileMap, function (folderObj, key) {
                        folderObj && filesArray.push(folderObj);
                    });
                    // remove select from object
                    angular.forEach($scope.refdocuments, function (refDoc, key) {
                        delete refDoc.select;
                    });
                    blockUI.start();
                    DocumentService.saveProjDocFilesByFolder(filesArray, req).then(function (data) {
                        blockUI.stop();
                        GenericAlertService.alertMessage(' Document Details are successfully saved', "Info");
                        $scope.getDocumentDetails($scope.currentlyOpenedDocFolder);
                    }, function (error) {
                        blockUI.stop();
                        GenericAlertService.alertMessage(' Document Details  are failed to Save', 'Error');
                    });
                };

                $scope.chooseFile = function (reference) {
                    if (reference.select) {
                        $scope.fileChoosen = reference;
                    } else {
                        $scope.fileChoosen = null;
                    }
                };

                $scope.selectFile = function () {
                    fileSelected($scope.fileChoosen);
                    console.log($scope.fileChoosen);
                    $scope.closeThisDialog(dialogVar);
                }

                function getFolderDetails() {

                    var req = {
                        "status": 1,
                        "projId": projId
                    };
                    DocumentService.getProjDocFolders(req).then(function (data) {
                        $scope.editTreeData = data.projDocFolderTOs;
                        console.log($scope.editTreeData);
                        $scope.editTreeData.map(documents => {
						$scope.itemClick(documents, false);
					});
					 
					$scope.editTreeData = TreeService.populateTreeData(data.projDocFolderTOs, 0, [], 'name', 'childProjDocFolderTOs');
					console.log($scope.editTreeData);
					$scope.editTreeData.map(documents => {
						$scope.itemClick(documents, false);
					});
                        $scope.getFolderPermissions();
                    })
                };
				
				$scope.itemClick = function (item, collapse) {
				//console.log(item);
				TreeService.treeItemClick(item, collapse, 'childProjDocFolderTOs');
			},
				
                 $scope.getFolderPermissions = function() {
                    var req = {
                        "projId": projId

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
                };


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
                };

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
                };

                function findFolderById(folderId) {
                    for (var index = 0; index < $scope.editTreeData.length; index++) {
                        if ($scope.editTreeData[index].id == folderId) {
                            return $scope.editTreeData[index];
                        }
                    }
                }

                /**
                 * Fetch logged-in user account
                 */
                Principal.identity().then(function (acc) {
                    $scope.account = acc;
                });

                // load folders on init
                getFolderDetails();
            }]
        });
        return deferred.promise;
    };

    return {
        restrict: 'E',
        replace: true,
        scope: {
            projId: '=',
            fileSelected: '=',
            buttonText: "="
        },
        template: '<input type="button" class="btn btn-brown btn-sm" value="{{buttonText}}"/>',
        link: function (scope, element, attrs) {
            element.bind('click', function () {
                openPopup(scope.projId, scope.fileSelected);
            });
        }
    };
}]);
