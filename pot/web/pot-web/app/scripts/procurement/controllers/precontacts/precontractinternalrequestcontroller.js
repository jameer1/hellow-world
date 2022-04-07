'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("internalRequest", {
		url : '/internalRequest',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/pre-contracts/internalApproval/precontractinternalrequest.html',
				controller : 'PreContractInternalRequestController'
			}
		}
	})
}]).controller("PreContractInternalRequestController", ["$rootScope", "$scope", "$state", "ngDialog", "$q", "$filter", "PreContractInternalService", "PreContractInternalReqFactory", "GenericAlertService", "UserEPSProjectService", "EpsProjectMultiSelectFactory", "PrecontractReqApprovalFactory", "ReferenceDocumentsPopupFactory", function($rootScope, $scope, $state, ngDialog, $q, $filter,PreContractInternalService, PreContractInternalReqFactory, GenericAlertService, UserEPSProjectService, EpsProjectMultiSelectFactory, PrecontractReqApprovalFactory, ReferenceDocumentsPopupFactory) {

	$scope.preContractTOs = [];
	var editInternalReq = [];
	$scope.deleteInternalRequest = [];
	$scope.searchProject = {};
	$scope.contractStatus = {
		"value" : 1,
		"desc" : 'Draft'
	};
	$scope.preContractReqApprList = [];
	$scope.approverUserList = [];
	$scope.viewInternalRequest = [];
	$scope.items = [];
	$scope.currentTab = null;
	$scope.preContractTabs = [];
	$scope.userType = '1';
	$scope.refdocumentslist = [];
	$scope.resourceData = [];

	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	
	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	
	
	$scope.getWorkFlowstatus = function() {
		PreContractInternalService.getWorkFlowstatus().then(function(data) {
			$scope.workflowStatusList = data.workFlowStatusTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting workflow status", 'Error');
		});
	}
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getInternalPreContracts = function() {
		var projIds = null;
		if ($scope.searchProject.projIds != null && $scope.searchProject.projIds.length > 0) {
			projIds = [];
			projIds = $scope.searchProject.projIds;
		}
		var loginUser = true;
		if ($scope.userType == '2') {
			loginUser = false;
		}
		var getReq = {
			"projIds" : projIds,
			"approveStatus" : $scope.contractStatus.value,
			"status" : 1,
			"loginUser" : loginUser,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		};
		PreContractInternalService.getInternalPreContracts(getReq).then(function(data) {
			$scope.userProjMap = data.userProjMap;
			$scope.preContractTOs = data.preContractTOs;
			$scope.usersMap = data.usersMap;
			if ($scope.preContractTOs.length <= 0) {
				GenericAlertService.alertMessage("Precontracts are not available for given search criteria", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});

	}, $scope.getInternalPreContracts();
	
	
	$scope.resetInternalRequest = function() {
		
		$scope.contractStatus = {};
		$scope.searchProject = {};
		$scope.userType = '1';
		$scope.fromDate =null;
		$scope.toDate=null;
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.contractStatus = {
			"value" : 1,
			"desc" : 'Draft'
		};
		
		
	}, $scope.rowSelect = function(req) {
		if (req.select) {
			editInternalReq.push(req);
		} else {
			editInternalReq.pop(req);
		}

	}, $scope.getApprovelDetails = function(preContractObj) {
		var req = {
			"contractId" : preContractObj.id,
			"status" : 1,
			"userMap" :$scope.usersMap 
		};
		var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req);
		popupDetails.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting request approvals", 'Info');
		})
	}, $scope.viewInternalRequestById = function(preContractObj) {
		var resultData = $scope.getInternalDetailsById(preContractObj);
		resultData.then(function(data) {
			var popupDetails = PreContractInternalReqFactory.procInternalApprovalPopUp(data.preContractObj);
			popupDetails.then(function(data) {
				$scope.preContractTOs = data.preContractTOs;
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
		})

	}, $scope.getInternalDetailsById = function(preContractObj) {
		var internalDefer = $q.defer();
		var onLoadReq = {
			"projId" : preContractObj.projId,
			"contractId" : preContractObj.id,
			"status" : 1
		};
		PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
			var returnObj = {
				"preContractObj" : angular.copy(data)
			};
			internalDefer.resolve(returnObj);
		});
		return internalDefer.promise;

	}, $scope.getRefDocument = function(precontract) {
		var refreq = ReferenceDocumentsPopupFactory.referenceDocumentDetails(precontract);
		refreq.then(function(data) {
			$scope.refdocumentslist = data.preContractDocsTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		})

	}, $scope.deactivateInternalRequest = function() {

		if (editInternalReq == undefined || editInternalReq.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", "Warning");
			return;
		}
		var DeactivateReq = {
			"contractIds" : editInternalReq,
			"status" : 2
		};
		PreContractInternalService.deactivateInternalRequest(DeactivateReq).then(function(data) {
			GenericAlertService.alertMessage("Internal Detail(s) is/are Deactivated successfully", "Info");
			editInternalReq = [];
		}, function(error) {
			GenericAlertService.alertMessage("Error occured  while Deactivating internal Details", "Error");
		});
	};

}]);