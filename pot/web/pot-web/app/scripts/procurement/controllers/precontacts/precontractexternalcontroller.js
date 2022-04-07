'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("externalApproval", {
		url: '/externalApproval:request',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/procurement/pre-contracts/externalApproval/precontractexternal.html',
				controller: 'PreContractExternalController'
			}
		}
	})
}]).controller("PreContractExternalController", ["$rootScope", "$scope", "$q","ngDialog", "$filter", "PreContractExternalService", "PurchaseSinglePOFactory", "PurchaseMultiPOFactory", "GenericAlertService", "PreContractExternalFactory", "PrecontractReqApprovalFactory", "PreContractBidanalysisFactory", "EpsProjectMultiSelectFactory", "ReferenceDocumentsPopupFactory", "$stateParams", "RfqApproveMainPageFactory", "RequestForAdditionalTimeProcurementFactory", "stylesService", "ngGridService",'uiGridGroupingConstants','uiGridConstants', function ($rootScope, $scope, $q,ngDialog, $filter, PreContractExternalService,
	PurchaseSinglePOFactory, PurchaseMultiPOFactory, GenericAlertService, PreContractExternalFactory,
	PrecontractReqApprovalFactory, PreContractBidanalysisFactory, EpsProjectMultiSelectFactory, ReferenceDocumentsPopupFactory,
	$stateParams, RfqApproveMainPageFactory, RequestForAdditionalTimeProcurementFactory, stylesService, ngGridService, uiGridGroupingConstants, uiGridConstants) {

	$rootScope.selectedData = null;
	$scope.setSelected = function (row) {
		$scope.selectedRow = row;
	}
	$scope.stylesSvc = stylesService;
	$scope.searchCriteria = {};
	$scope.preContractTOs = [];
	var editExternalReq = [];
	let selectedItemRows = [];
	$scope.deletepreContractTOs = [];
	$scope.searchCriteria.searchProject = {};
	$scope.searchCriteria.userType = '1';
	$scope.preContractReqApprList = [];
	$scope.viewExternalRequest = [];
	$scope.items = [];
	$scope.currentTab = null;
	$scope.userProjMap = [];
	$scope.preContractTabs = [];

	$scope.todayDate = new Date();
	$scope.searchCriteria.toDate = $scope.todayDate;
	var endDate = new Date($scope.searchCriteria.toDate.getFullYear(), $scope.searchCriteria.toDate.getMonth() - 1, 0);
	$scope.searchCriteria.fromDate = new Date($scope.searchCriteria.toDate);
	$scope.searchCriteria.fromDate.setDate($scope.searchCriteria.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.searchCriteria.fromDate);
	var defaultToDate = new Date($scope.searchCriteria.toDate);

	$scope.searchCriteria.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.searchCriteria.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	$scope.stateParamsRequestPage = ($stateParams.request === 'request');

  // for RepeatPO
  $scope.repeatPOUserProjMap = [];
  $scope.repeatPOSelectedData  = [];
  // End of RepeatPO
	$scope.getWorkFlowstatus = function () {
		$scope.workflowStatusList = [];

		PreContractExternalService.getWorkFlowstatus($scope.stateParamsRequestPage).then(function (data) {
			$scope.workflowStatusList = data.workFlowStatusTOs;
			setContractStatus();
			$scope.getExternalPreContracts();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while gettting workflow status", 'Error');
		});
	};

	$scope.generateSinglePO = function () {
           if ($rootScope.selectedData == null) {
                GenericAlertService.alertMessage("Please select pre-contract id", 'Warning');
                return;
           }
           console.log("generateSinglePO function from precontractexternalcontroller js file");
           var popupDetails = PurchaseSinglePOFactory.getPurchaseOrderDetails($scope.userProjMap, $rootScope.selectedData,"singlepo");
           popupDetails.then(function (data) {
           }, function (error) {
                GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
           });
     };

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchCriteria.searchProject = data.searchProject;
			$scope.invalidProjectSelection = data.searchProject.parentName ? false : true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getExternalPreContracts = function (click) {
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
		var req = {
			"projIds": projIds,
			"approveStatus": $scope.searchCriteria.contractStatus.value,
			"status": 1,
			"loginUser": loginUser,
			"fromDate": $filter('date')(($scope.searchCriteria.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.searchCriteria.toDate), "dd-MMM-yyyy")
		};
		console.log(req);
		PreContractExternalService.getExternalPreContracts(req).then(function (data) {
			console.log(data);
			$scope.userProjMap = data.userProjMap;
			$scope.gridOptions.data = angular.copy($scope.userProjMap);
			console.log($scope.userProjMap, 'Hi....');
			$scope.preContractTOs = data.preContractTOs;
			$scope.gridOptions.data = angular.copy($scope.preContractTOs);
			console.log(data.preContractTOs);
			$scope.usersMap = data.usersMap;
			$scope.selectedRow = undefined;

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

	}, $scope.getExternalPreContracts1 = function () {
		var projIds = null;
		if ($scope.searchCriteria.searchProject.projIds != null && $scope.searchCriteria.searchProject.projIds.length > 0) {
			projIds = [];
			projIds = $scope.searchCriteria.searchProject.projIds;
		}
		var loginUser = true;
		if ($scope.searchCriteria.userType == '2') {
			loginUser = false;
		}
		var req = {
			"projIds": projIds,
			"approveStatus": $scope.searchCriteria.contractStatus.value,
			"status": 1,
			"loginUser": loginUser,
			"fromDate": $filter('date')(($scope.searchCriteria.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.searchCriteria.toDate), "dd-MMM-yyyy")
		};
		PreContractExternalService.getExternalPreContracts(req).then(function (data) {
			$scope.userProjMap = data.userProjMap;
			$scope.preContractTOs = data.preContractTOs;
			$scope.usersMap = data.usersMap;

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');
		});
	};
	$scope.resetExternalRequest = function () {
		$scope.searchCriteria.contractStatus = {};
		$scope.searchCriteria.searchProject = {};
		$scope.searchCriteria.userType = '1';
		$scope.searchCriteria.fromDate = null;
		$scope.searchCriteria.toDate = null;
		$scope.searchCriteria.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.searchCriteria.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		setContractStatus();
		$scope.getExternalPreContracts();
	};

	$scope.rowSelect = function (req) {
		if (req.select) {
			editExternalReq.push(req);
		} else {
			editExternalReq.pop(req);
		}

	};

	$scope.getApprovelDetails = function (req) {
		var req = {
			"contractId": req.id,
			"status": 1
		}
		var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req, $scope.usersMap);

		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
		})
	};

	$scope.getBidAnalysisDetails = function (preContractId) {
		var bidDetails = $scope.getCompanyBidDetails(preContractId);
		bidDetails.then(function (data) {
			var popupDetails = PreContractBidanalysisFactory.getCompanyBidDetails(data.preContractObj, $scope.stateParamsRequestPage);
			popupDetails.then(function (data) {
				$scope.getExternalPreContracts1();
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
		})

	};

	$scope.getRefDocument = function (precontract) {
		var refreq = ReferenceDocumentsPopupFactory.referenceDocumentDetails(precontract);
		refreq.then(function (data) {
			$scope.refdocumentslist = data.preContractDocsTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		})

	};

	$scope.addExternalRequest = function (preContractObj) {
		var resultData = $scope.getExternalPrecontractDetails(preContractObj);
		resultData.then(function (data) {
			var popupDetails = PreContractExternalFactory.procExternalApprovalPopUp(data.preContractObj);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
		})
	};

	$scope.getExternalPrecontractDetails = function (preContractObj) {
		var externalDefer = $q.defer();
		var onLoadReq = {
			"projId": preContractObj.projId,
			"contractId": preContractObj.id,
			"status": 1
		};
		PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
			var returnObj = {
				"preContractObj": angular.copy(data)
			};
			externalDefer.resolve(returnObj);
		});
		return externalDefer.promise;
	};

	$scope.getCompanyBidDetails = function (preContractObj) {
		var onLoadReq = {
			"projId": preContractObj.projId,
			"contractId": preContractObj.id,
			"status": 1

		};

		var bidAnalysisDefer = $q.defer();
		PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
			var returnPreContractObj = {
				"preContractObj": angular.copy(data)
			};
			bidAnalysisDefer.resolve(returnPreContractObj);
		});
		return bidAnalysisDefer.promise;

	};

	$scope.generateMultiplePO = function () {
		if ($rootScope.selectedData == null) {
			GenericAlertService.alertMessage("Please select pre-contract id", 'Warning');
			return;
		}
		var popupDetails = PurchaseMultiPOFactory.getPurchaseOrderDetails($scope.userProjMap, $rootScope.selectedData);
		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
		});
	};

	$scope.go = function (req, indexValue) {
	  console.log('precontractexternalcontroller $scope.go req')
		$rootScope.selectedData = req;
		console.log($rootScope.selectedData);
		$scope.setSelected(indexValue);
	};

	$scope.approveProcBidderItems = function (preContract, apprvStatus) {
		var bidDetails = $scope.getCompanyBidDetails(preContract);
		bidDetails.then(function (data) {
			RfqApproveMainPageFactory.approveRfqBidItem(data.preContractObj.preContractTO, apprvStatus).then(
				function (data) {
					GenericAlertService.alertMessage("Bid has been Approved", "Info");
					$scope.getExternalPreContracts();
				});
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting BID details", 'Info');
		})
	};

	$scope.checkExternalPrecontractStatus = function (workFlowMap, status) {
		for (const key in workFlowMap) {
			if (workFlowMap[key] === status) {
				return true;
			}
		}
		return false;
	};

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
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name",enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)">{{row.entity.epsName}}</div>', groupingShowAggregationMenu: false},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name",enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)">{{row.entity.projName}}</div>', groupingShowAggregationMenu: false},
						{ field: 'code', displayName:"Pre-Contract ID", headerTooltip: "Contract ID", enableFiltering: false,
						cellTemplate:'<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)">{{row.entity.code}}</div>', groupingShowAggregationMenu: false},
						{ field: 'desc', displayName:"Contract Description", headerTooltip: "Description", enableFiltering: false,
						cellTemplate:'<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.desc}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractType', displayName: "Service Type", headerTooltip: "Type", enableFiltering: false,
						cellTemplate:'<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractType}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractReqApprTO.reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID",enableFiltering: false, 
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.reqCode}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractReqApprTO.apprDate', displayName: "Requisition Date", headerTooltip: "Requisition Date",enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.apprDate}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractReqApprTO.apprCode', displayName: "Approver Response ID", headerTooltip: "Appr. Response ID",enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.apprCode}}</div>', groupingShowAggregationMenu: false},

						{ field: 'preContractReqApprTO.reqUserLabelkeyTO.code', displayName: "Requester", headerTooltip: "Requester",enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.reqUserLabelkeyTO.code}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractReqApprTO.reqDate', displayName: "Date Of Request", headerTooltip: "Date Of Request",enableFiltering: false, 
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.reqDate}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractReqApprTO.apprUserLabelkeyTO.code', displayName: "Approver", headerTooltip: "Approver", enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.apprUserLabelkeyTO.code}}</div>', groupingShowAggregationMenu: false},
						{ field: 'schedule', displayName: "Schedule Items", headerTooltip: "Schedule Items",enableFiltering: false, cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: '<span><a ng-click="grid.appScope.addExternalRequest(row.entity, row.entity.req)" class="btn btn-primary btn-sm"  data-test="Requests&Approvals_Request_stage2request_scheduleitemview">View</a> <span>', groupingShowAggregationMenu: false},
						{ field: 'workFlowStatusTO.desc', displayName: "Stage 2 Approval Status", headerTooltip: "Stage 2 Approval Status",enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.workFlowStatusTO.desc}}</div>', groupingShowAggregationMenu: false},
						{ field: 'preContractReqApprTO.reqComments', displayName: "Requester Comments", headerTooltip: "Requester Comments", enableFiltering: false,
						cellTemplate: '<div ng-click="grid.appScope.go(row.entity, rowRenderIndex)" ng-class="{row.entity, selected: $index ==selectedRow}">{{row.entity.preContractReqApprTO.reqComments}}</div>', groupingShowAggregationMenu: false},
						{ field: 'Approver Details', displayName: "Approver Details", headerTooltip: "Approver Details", enableFiltering: false,
						cellTemplate:"<div class='ui-grid-cell-contents'><a data-test='Requests&Approvals_Request_stage2request_approverdetailsview' ng-click='grid.appScope.getApprovelDetails(row.entity, row.entityreq)' class='btn btn-primary btn-sm' >View</a></div>", groupingShowAggregationMenu: false},
						{ field: 'Bid Analysis', displayName: "Bid Analysis", headerTooltip: "Bid Analysis",enableFiltering: false, cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate:"<div class='ui-grid-cell-contents'><a data-test='{{stateParamsRequestPage ? 'Stage2Request_Bidanalys_View':'Stage2Approval_Bidanalys_View'}}' ng-click='grid.appScope.getBidAnalysisDetails(row.entity, req)' class='btn btn-primary btn-sm' >View</a></div>", groupingShowAggregationMenu: false},
						{ field: 'paymentInDays', displayName: "Approve", width:'5%', headerTooltip: "Approve", enableFiltering: false, cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: 'template.html', groupingShowAggregationMenu: false},						
						{ field: 'Additional Time', width:'10%', displayName: "Request for Additional Time", cellClass: 'justify-center', headerCellClass:"justify-center", headerTooltip: "Request for Additional Time", enableFiltering: false,
						cellTemplate:"template1.html", groupingShowAggregationMenu: false},
						];
						
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement_PrecontractInternal_Stage2Request");
					$scope.gridOptions.showColumnFooter = false;
					$scope.gridOptions.enableRowHeaderSelection = false;
					$scope.gridOptions.enableFullRowSelection = true;
					$scope.gridOptions.enableSelectAll = false;
					$scope.gridOptions.multiSelect = false;
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
			template: 'views/help&tutorials/procurementhelp/stage2requesthelp.html',
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
			template: 'views/help&tutorials/procurementhelp/stage2approvalhelp.html',
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
