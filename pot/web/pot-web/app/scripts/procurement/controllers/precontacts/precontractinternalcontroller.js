'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("internalApproval", {
		url: '/internalApproval:request',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/procurement/pre-contracts/internalApproval/precontractinternal.html',
				controller: 'PreContractInternalController'
			}
		}
	})
}]).controller("PreContractInternalController", ["$rootScope", "$scope", "$q", "ngDialog","$filter", "PreContractInternalService", "PreContractInternalScheduleItemsFactory", "GenericAlertService", "EpsProjectMultiSelectFactory", "PrecontractReqApprovalFactory", "ReferenceDocumentsPopupFactory", "$stateParams", "PrecontractSubmitApprRequestFactory", "RequestForAdditionalTimeProcurementFactory","stylesService", "ngGridService", function ($rootScope, $scope, $q,ngDialog, $filter,
	PreContractInternalService, PreContractInternalScheduleItemsFactory, GenericAlertService,
	EpsProjectMultiSelectFactory, PrecontractReqApprovalFactory, ReferenceDocumentsPopupFactory,
	$stateParams, PrecontractSubmitApprRequestFactory, RequestForAdditionalTimeProcurementFactory, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.preContractTOs = [];
	var editInternalReq = [];
	$scope.deleteInternalRequest = [];
	$scope.searchCriteria = {};
	$scope.searchCriteria.searchProject = {};
	$scope.preContractReqApprList = [];
	$scope.approverUserList = [];
	$scope.viewInternalRequest = [];
	$scope.items = [];
	$scope.currentTab = null;
	$scope.preContractTabs = [];
	$scope.searchCriteria.userType = '1';
	$scope.refdocumentslist = [];
	$scope.resourceData = [];

	$scope.date = new Date();
	$scope.searchCriteria.toDate = new Date();
	var endDate = new Date($scope.searchCriteria.toDate.getFullYear(), $scope.searchCriteria.toDate.getMonth() - 1, 0);
	$scope.searchCriteria.fromDate = new Date($scope.searchCriteria.toDate);
	$scope.searchCriteria.fromDate.setDate($scope.searchCriteria.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.searchCriteria.fromDate);
	var defaultToDate = new Date($scope.searchCriteria.toDate);

	$scope.searchCriteria.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.searchCriteria.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	$scope.stateParamsRequestPage = ($stateParams.request === 'request');
	const approvPemissionKeys = {
		'manpower': ['PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL', 'APPROVE_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL'],
		'material': ['PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL', 'APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL'],
		'plant': ['PROCURMT_INTRNLSTAGE1APPROVALPLANT_APPROVAL', 'APPROVE_INTRNLSTAGE1APPROVALPLANT_APPROVAL'],
		'service': ['PROCURMT_INTRNLSTAGE1APPROVALSERVICES_APPROVAL', 'APPROVE_INTRNLSTAGE1APPROVALSERVICES_APPROVAL'],
		'sow': ['PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL', 'APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL']
	};

	$scope.getWorkFlowstatus = function () {
		PreContractInternalService.getWorkFlowstatus($scope.stateParamsRequestPage).then(function (data) {
			console.log(data);
			$scope.workflowStatusList = data.workFlowStatusTOs;
			setContractStatus();
			$scope.getInternalPreContracts();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting workflow status", 'Error');
		});
	}
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
			$scope.searchCriteria.searchProject = data.searchProject;
			$scope.invalidProjectSelection = data.searchProject.parentName ? false : true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getInternalPreContracts = function (click) {
		var projIds = null;
		if ($scope.searchCriteria.searchProject.projIds != null && $scope.searchCriteria.searchProject.projIds.length > 0) {
			projIds = [];
			projIds = $scope.searchCriteria.searchProject.projIds;
		}
		var loginUser = true;
		if ($scope.searchCriteria.userType == '2') {
			loginUser = false;
		}
		var fromDate1 = new Date($scope.searchCriteria.fromDate);
		var toDate1 = new Date($scope.searchCriteria.toDate);
		var totalDays = new Date(toDate1 - fromDate1);
		var days = totalDays / 1000 / 60 / 60 / 24;
		if (days < 0) {
			GenericAlertService.alertMessage('To-Date must be greater than From Date', 'Warning');
			return;
		}
		var getReq = {
			"projIds": projIds,
			"approveStatus": $scope.searchCriteria.contractStatus.value,
			"status": 1,
			"loginUser": loginUser,
			"fromDate": $filter('date')(($scope.searchCriteria.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.searchCriteria.toDate), "dd-MMM-yyyy")
		};
//		console.log(getReq);
		PreContractInternalService.getInternalPreContracts(getReq).then(function (data) {
//			console.log(data);
			$scope.userProjMap = data.userProjMap;
			$scope.preContractTOs = data.preContractTOs;
			for(var val of $scope.preContractTOs){
				val.userProjMap=$scope.userProjMap;
			}
			$scope.gridOptions.data = angular.copy($scope.preContractTOs);
//			console.log($scope.preContractTOs);
			$scope.usersMap = data.usersMap;
			if (click == 'click') {
				if ($scope.preContractTOs.length <= 0) {
					GenericAlertService.alertMessage("Pre-contracts not available for given search criteria", 'Warning');
				}
			}
			$scope.getProjSettingsInternalPreContracts();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});

	}, $scope.getProcurement1Notifications = function (precontract, userProjMap) {
		console.log(precontract);
		console.log(userProjMap[precontract.projId].name);
		var popup = RequestForAdditionalTimeProcurementFactory.getAdditionalTimeDetails(precontract, userProjMap);
		popup.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
		})
		
	},
	
		$scope.getInternalPreContracts1 = function () {
			var projIds = null;
			if ($scope.searchCriteria.searchProject.projIds != null && $scope.searchCriteria.searchProject.projIds.length > 0) {
				projIds = [];
				projIds = $scope.searchCriteria.searchProject.projIds;
			}
			var loginUser = true;
			if ($scope.searchCriteria.userType == '2') {
				loginUser = false;
			}
			var getReq = {
				"projIds": projIds,
				"approveStatus": $scope.searchCriteria.contractStatus.value,
				"status": 1,
				"loginUser": loginUser,
				"fromDate": $filter('date')(($scope.searchCriteria.fromDate), "dd-MMM-yyyy"),
				"toDate": $filter('date')(($scope.searchCriteria.toDate), "dd-MMM-yyyy")
			};
			PreContractInternalService.getInternalPreContracts(getReq).then(function (data) {
				console.log(data);
				$scope.userProjMap = data.userProjMap;
				$scope.preContractTOs = data.preContractTOs;
				$scope.usersMap = data.usersMap;
			});
		},

		$scope.resetInternalRequest = function () {

			$scope.searchCriteria.contractStatus = {};
			$scope.searchCriteria.searchProject = {};
			$scope.searchCriteria.userType = '1';
			$scope.preContractTOs = {};
			$scope.searchCriteria.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
			$scope.searchCriteria.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

			setContractStatus();
			$scope.getInternalPreContracts1();

		}, $scope.rowSelect = function (req) {
			if (req.select) {
				editInternalReq.push(req);
			} else {
				editInternalReq.pop(req);
			}

		}, $scope.getApprovelDetails = function (preContractObj) {
			var req = {
				"contractId": preContractObj.id,
				"status": 1,
				"userMap": $scope.usersMap
			};
			var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req, $scope.usersMap);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting request approvals", 'Info');
			})
		}, $scope.viewInternalRequestById = function (preContractObj) {
			var resultData = $scope.getInternalDetailsById(preContractObj);
//			console.log(preContractObj);
			resultData.then(function (data) {
				var popupDetails = PreContractInternalScheduleItemsFactory.procInternalApprovalPopUp(data.preContractObj, $scope.stateParamsRequestPage);
				popupDetails.then(function (data) {
					$scope.getInternalPreContracts1();
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
				})
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})

		}, $scope.getInternalDetailsById = function (preContractObj) {
			var internalDefer = $q.defer();
			var onLoadReq = {
				"projId": preContractObj.projId,
				"contractId": preContractObj.id,
				"status": 1
			};
			PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
//				console.log(data);
				var returnObj = {
					"preContractObj": angular.copy(data)
				};
				internalDefer.resolve(returnObj);
			});
			return internalDefer.promise;
		},
		
		$scope.getRefDocument = function (precontract) {			
			var refreq = ReferenceDocumentsPopupFactory.referenceDocumentDetails(precontract);
			refreq.then(function (data) {
				$scope.refdocumentslist = data.preContractDocsTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
			})

		}, $scope.deactivateInternalRequest = function () {
			if (editInternalReq == undefined || editInternalReq.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to Deactivate", "Warning");
				return;
			}
			var DeactivateReq = {
				"contractIds": editInternalReq,
				"status": 2
			};
			PreContractInternalService.deactivateInternalRequest(DeactivateReq).then(function (data) {
				console.log(data);
				GenericAlertService.alertMessage("Internal Detail(s) is/are Deactivated successfully", "Info");
				editInternalReq = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured  while Deactivating internal Details", "Error");
			});
		};

	$scope.approvePrecontract = function (preContractObj, apprvStatus) {
		var internalDetails = $scope.getInternalDetailsById(preContractObj);
		internalDetails.then(function (data) {
			var contract = data.preContractObj;
			let hasApprovePermission = true;
			let permissionVerified = false;
			if (contract.preContractTO.preContractEmpDtlTOs && contract.preContractTO.preContractEmpDtlTOs.length) {
				permissionVerified = true;
				hasApprovePermission = checkPermission(approvPemissionKeys.manpower);
			}
			if (contract.preContractTO.preContractMaterialDtlTOs && contract.preContractTO.preContractMaterialDtlTOs.length && hasApprovePermission) {
				hasApprovePermission = checkPermission(approvPemissionKeys.material);
				permissionVerified = true;
			}
			if (contract.preContractTO.preContractPlantDtlTOs && contract.preContractTO.preContractPlantDtlTOs.length && hasApprovePermission) {
				hasApprovePermission = checkPermission(approvPemissionKeys.plant);
				permissionVerified = true;
			}
			if (contract.preContractTO.preContractServiceDtlTOs && contract.preContractTO.preContractServiceDtlTOs.length && hasApprovePermission) {
				hasApprovePermission = checkPermission(approvPemissionKeys.service);
				permissionVerified = true;
			}
			if (contract.preContractTO.precontractSowDtlTOs && contract.preContractTO.precontractSowDtlTOs.length && hasApprovePermission) {
				hasApprovePermission = checkPermission(approvPemissionKeys.sow);
				permissionVerified = true;
			}

			if (hasApprovePermission && permissionVerified) {
				var submitpopupRes = PrecontractSubmitApprRequestFactory.submitOrApproveInternalRequest(contract.preContractTO, apprvStatus,
					contract.preContractTO.projId, contract.usersMap);
				submitpopupRes.then(function (data) {
					$scope.getInternalPreContracts();
				});
			} else if (!hasApprovePermission) {
				GenericAlertService.alertMessage("You don't have permissions to approve", 'Warning');
			} else {
				GenericAlertService.alertMessage("Scheule Items not assigned to precontract", 'Warning');
			}

		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while approving precontract", 'Error');
		})

	};

	$scope.checkApprovePermission = function () {
		for (const permissionKey in approvPemissionKeys) {
			if (checkPermission(approvPemissionKeys[permissionKey])) {
				console.log(checkPermission(approvPemissionKeys[permissionKey]));
				return true;
			}
		}
		return false;
	}

	function checkPermission(permissionArray) {
		console.log('permissionArray  ', permissionArray);
		for (const permission of permissionArray) {
			if ($rootScope.account.appCodeTemplate[permission]) {
				return true;
			}
		}
		return false;
	}

	function setContractStatus() {
		for (const status of $scope.workflowStatusList) {
			if ($scope.stateParamsRequestPage && status.value == 1) {
				$scope.searchCriteria.contractStatus = status;
				break;
			} else if (!$scope.stateParamsRequestPage && status.value == 2) {
				$scope.searchCriteria.contractStatus = status;
				break;
			}
		}
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
				
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
						
						{ field: 'code', displayName: "Pre-Contract ID", headerTooltip: "Pre-Contract ID", groupingShowAggregationMenu: false  },
						
						{ field: 'desc', displayName: "Pre-Contract Description", headerTooltip: "Pre-Contract Description", groupingShowAggregationMenu: false  },
						
						{ name: 'preContractReqApprTO.reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", 
						cellTemplate: '<div>{{row.entity.preContractReqApprTO.reqCode}}</div>', groupingShowAggregationMenu: false  },
						
						{ name: 'preContractReqApprTO.apprCode', displayName: "Approver Response ID", headerTooltip: "Approver Response ID",
						cellTemplate: '<div>{{row.entity.preContractReqApprTO.apprCode}}</div>', groupingShowAggregationMenu: false  },
						
						{ name: 'preContractReqApprTO.reqUserLabelkeyTO.code', displayName: "Requester", headerTooltip: "Requester", 
						cellTemplate: '<div>{{row.entity.preContractReqApprTO.reqUserLabelkeyTO.code}}</div>', groupingShowAggregationMenu: false  },
						
						{ name: 'preContractReqApprTO.apprUserLabelkeyTO.code', displayName: "Approver", headerTooltip: "Approver",
						cellTemplate: '<div>{{row.entity.preContractReqApprTO.apprUserLabelkeyTO.code}}</div>', groupingShowAggregationMenu: false  },
						
						{ field: 'preContractType', displayName: "Pre-Contract Type", headerTooltip: "Pre-Contract Type", groupingShowAggregationMenu: false  },
						
						{ name: 'Schedule', displayName: "Schedule Items", headerTooltip: "Schedule Items", cellClass:"justify-center", headerCellClass:"justify-center",
						cellTemplate:"<span class='ui-grid-cell-contents'><button data-test='{{row.entity.stateParamsRequestPage ? 'row.entity.Stage1Request_viewedit':'row.entityStage1Approve_viewedit'}}' ng-click='grid.appScope.viewInternalRequestById(row.entity, req)' class='link btn btn-primary'>View/Edit</button></span>",  groupingShowAggregationMenu: false  },
						
						{ field: 'workFlowStatusTO.desc', displayName: "Current Status", headerTooltip: "Current Status", groupingShowAggregationMenu: false},
						
						{ field: 'finsihDate', displayName: "Approve Details", headerTooltip: "Approve Details", cellClass:"justify-center", headerCellClass:"justify-center",
						cellTemplate: '<span ng-if="row.entity.workFlowStatusTO.desc!= Draft"><button ng-click="grid.appScope.getApprovelDetails(row.entity, 1, row.entity.req)" class="link" ng-if ="grid.appScope.row.entity.req.workFlowStatusTO.value != 1" data-test="row.entity.Requests&Approvals_Request_Stage1Requestapprovedetailsview">View</button></span>', groupingShowAggregationMenu: false  },
						
						{ field: 'paymentInDays', displayName: "Approve", headerTooltip: "Approve", cellTemplate:"template.html", cellClass:"justify-center", headerCellClass:"justify-center", groupingShowAggregationMenu: false  },
						
						{ name: 'Additional Time', displayName: "Request for Additional Time", headerTooltip: "Request for Additional Time",
						cellTemplate:"<div class='ui-grid-cell-contents'><button ng-disabled='{{!(row.entity.procurementNormalTimeTOs[0].normalTimeFlag)}}' ng-click='grid.appScope.getProcurement1Notifications(row.entity, row.entity.userProjMap)' class='link btn btn-primary btn-sm'>Additional Time</button></div>", groupingShowAggregationMenu: false  },
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement_PrecontractInternal_Stage1Request");
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
			template: 'views/help&tutorials/procurementhelp/stage1requesthelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	var HelpService1 = {};
	$scope.helpPage1 = function () {
		var help = HelpService1.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup1;
	HelpService1.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup1 = ngDialog.open({
			template: 'views/help&tutorials/procurementhelp/stage1approvalhelp.html',
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