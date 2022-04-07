'use strict';

app.factory(
	'EpsProjectMultiSelectFactory', ["ngDialog", "$q", "blockUI", "EpsService", "GenericAlertService",
		"TreeService", function (ngDialog, $q, blockUI, EpsService, GenericAlertService, TreeService) {
			var service = {};
			service.getEPSUserProjects = function (getEpsDetails) {
				var deferred = $q.defer();
				var req = {
					"status": 1
				};
				blockUI.start();
				EpsService.getEPSUserProjects(req).then(
					function (data) {
						blockUI.stop();
						var popupdata = service.openPopup(data.epsProjs, getEpsDetails);
						popupdata.then(function (resultData) {
							deferred.resolve(resultData);
						});
					},
					function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage(
							"Error occured while getting  EPS Project Details", "Error");
					});
				return deferred.promise;

			}, service.openPopup = function (epsProjs, getEpsDetails) {
				var deferred = $q.defer();
				var epsProjTreePopup = ngDialog.open({
					template: 'views/common/epsprojectmulptiselectpopup.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom1',
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function ($scope) {
						$scope.epsData = TreeService.populateTreeData(epsProjs, 0, [], 'projCode',
							'childProjs');
						var epsNameDisplay = '';
						var projectNameDisplay = '';
						var selectedProjIds = [];
						var parentProjIds = [];
						$scope.itemClick = function (item, expand) {
							TreeService.treeItemClick(item, expand, 'childProjs');
						};

						$scope.selectAllProjects = function () {
							$scope.epsData.map(o => {
								if (o.proj)
									o.select = $scope.selectAll;
							});
						};

						$scope.addEPSProjects = function () {
							var praentMap = [];
							const selectedEpsProjects = $scope.epsData.filter(o => o.proj && o.select);
							angular.forEach(selectedEpsProjects, function (value, key) {
								selectedProjIds.push(value.projId);
								parentProjIds.push(value.parentId);
								if (praentMap[value.parentId] == null) {
									praentMap[value.parentId] = true;
									epsNameDisplay = epsNameDisplay + value.parentName + ",";
								}
								projectNameDisplay = projectNameDisplay + value.projName + ",";
							});
							var returnPopObj = {
								"searchProject": {
									"projName": projectNameDisplay.slice(0, -1),
									"parentName": epsNameDisplay.slice(0, -1),
									"projIds": selectedProjIds,
								}
							};
							if (getEpsDetails) {
								returnPopObj.searchProject.epsDetails = {};
								angular.forEach(parentProjIds, function (parentId, index) {
									angular.forEach(epsProjs, function (epsProj, index) {
										if (epsProj.projId == parentId) {
											returnPopObj.searchProject.epsDetails[parentId] = {
												"projCode": epsProj.projCode,
												"projName": epsProj.projName
											};
										}

									});
								});
							}
							deferred.resolve(returnPopObj);
							$scope.closeThisDialog();
						};

						for (const obj of $scope.epsData)
							$scope.itemClick(obj, false);

					}]
				});
				return deferred.promise;
			};

			return service;

		}]);