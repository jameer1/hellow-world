'use strict';
app
	.factory(
		'PreContractListFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "PreContractInternalService", "EpsProjectSelectFactory", "GenericAlertService", "blockUI", "generalservice", function (ngDialog, $q, $filter, $timeout, $rootScope, PreContractInternalService,
			EpsProjectSelectFactory, GenericAlertService, blockUI,generalservice) {
			var service = {};
			service.addPreContracts = function (searchReq, actionType, editPreContractList,
				userProjMap) {
				var deferred = $q.defer();
				var popup = ngDialog
					.open({
						template: 'views/procurement/pre-contractlist/precontractlistpopup.html',
						className: 'ngdialog-theme-plain ng-dialogueCustom1',
						scope: $rootScope,
						closeByDocument: false,
						showClose: false,
						controller: [
							'$scope',
							function ($scope) {
								$scope.action = actionType;
								var selectedPreContract = [];
								$scope.precontractData = [];
								$scope.userProjMap = userProjMap;
								var deleteIds = [];
								$scope.invalidProjectSelectionMap = {};
								$scope.contractTypes = generalservice.getPreContractTypes();
								

								$scope.precontractObj = {
									'selected': false,
									'status': 1,
									'projId': null,
									'preContractType': null,
									'desc': null,
									'currencyTO': {
										"id": null,
										"code": null,
										"name": null
									}

								};

								$scope.getInternalDetailsById = function () {
									var onLoadReq = {
										"status": 1,

									};
									PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq)
										.then(function (data) {
											$scope.precontractObj = data.preContractTO;
											$scope.currencyList = data.currencyList;
											$scope.preContractObj = data.preContractTO;
										});

								};
								$scope.getInternalDetailsById();
								if (actionType === 'Add') {
									$scope.precontractData.push(angular
										.copy($scope.precontractObj));
								} else {
									$scope.precontractData = angular
										.copy(editPreContractList);
									editPreContractList = [];
								}
								$scope.addPreContractDetails = function () {
									$scope.precontractData.push(angular
										.copy($scope.precontractObj));
								};

								$scope.savePreContractList = function () {
									var savereq = {
										"preContractTOs": $scope.precontractData,
										"status": 1,
										"procurementFilterReq": {
											"status": 1,
											"projIds": searchReq.projIds,
											"loginUser": searchReq.loginUser
										}
									};
									blockUI.start();
									PreContractInternalService.savePreContractsList(savereq).then(
										function (data) {
											blockUI.stop();
											var succMsg = GenericAlertService.alertMessageModal(
												"Pre-Contract(s) saved successfully", 'Info');
											succMsg.then(function (popData) {
												$scope.closeThisDialog(popup);
												var returnPopObj = {
													"preContractTOs": data.preContractTOs
												};
												deferred.resolve(returnPopObj);
											});
										}, function (error) {
											blockUI.stop();
											GenericAlertService.alertMessage(
												'Pre-Contract details are failed to save', 'Error');
										});
								};

								$scope.getUserProjects = function (precontract) {
									$scope.invalidProjectSelectionMap[precontract.projId] = true;
									var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
									userProjectSelection.then(function (data) {
										$scope.searchProject = data.searchProject;
										precontract.projId = data.searchProject.projId;
										precontract.projCode = data.searchProject.projCode;
										$scope.invalidProjectSelectionMap[precontract.projId] = data.searchProject.parentName ? false : true;
										$scope.getProjSettingsForProcurement(precontract);
									}, function (error) {
										GenericAlertService.alertMessage("Error occured while selecting EPS Project name",
											'Error');
									});
								};
								$scope.getProjSettingsForProcurement = function (
									precontract) {
									var req = {
										"projId": precontract.projId
									}
									PreContractInternalService
										.getProjSettingsForProcurement(req).then(function (data) {
											precontract.currencyCode =  data.name;
										}, function (error) {
											GenericAlertService.alertMessage("Error occured while gettting workflow status",
												'Error');
										});
								};
								$scope.precontractRowSelect = function (
									precontract) {
									if (precontract.select) {
										selectedPreContract
											.push(precontract);
									} else {
										selectedPreContract
											.pop(precontract);
									}
								};
								$scope.deletePreContractRows = function () {
									deleteIds = [];
									var tempDeleteRequest = [];
									var flag = false;
									angular.forEach($scope.precontractData, function (value, key) {
										if (!value.select) {
											tempDeleteRequest.push(value);
										} else {
											if (value.id > 0) {
												deleteIds.push(value.id);
											}
											flag = true;
											
										}
										// GenericAlertService.alertMessage("Pre-contract details are deleted successfully", "Info");
									});

									if (!flag) {
										GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");

									}
									$scope.precontractData = tempDeleteRequest;

								}
							}]

					});
				return deferred.promise;
			}
			return service;
		}]);
