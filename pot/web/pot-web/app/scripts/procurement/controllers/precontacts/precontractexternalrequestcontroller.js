'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("externalRequest", {
		url : '/externalRequest',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/pre-contracts/externalApproval/precontractexternalrequest.html',
				controller : 'PreContractExternalRequestController'
			}
		}
	})
}]).controller(
		"PreContractExternalRequestController",
		["$rootScope", "$scope", "$state", "ngDialog", "$q", "$filter", "PreContractExternalService", "GenericAlertService", "UserEPSProjectService", "PreContractExternalFactory", "PrecontractReqApprovalFactory", "PreContractBidanalysisRequestFactory", "EpsProjectMultiSelectFactory", "ReferenceDocumentsPopupFactory", "PreContractInternalService", function($rootScope, $scope, $state, ngDialog, $q, $filter,PreContractExternalService, GenericAlertService, UserEPSProjectService, PreContractExternalFactory, PrecontractReqApprovalFactory, PreContractBidanalysisRequestFactory, EpsProjectMultiSelectFactory, ReferenceDocumentsPopupFactory,
				PreContractInternalService) {
			$scope.preContractTOs = [];
			var editExternalReq = [];
			$scope.deletepreContractTOs = [];
			$scope.searchProject = {};
			$scope.contractStatus = {
				"value" : 1,
				"desc" : 'Draft'
			};
			$scope.userType = '1';
			$scope.preContractReqApprList = [];
			$scope.viewExternalRequest = [];
			$scope.items = [];
			$scope.currentTab = null;
			$scope.userProjMap = [];
			$scope.preContractTabs = [];
			
			$scope.date = new Date();
			$scope.toDate = new Date();
			var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
			$scope.fromDate = new Date($scope.toDate);
			$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

			$scope.$watch('fromDate', function() {
				if ($scope.fromDate != null) {
					$scope.fromDate = $filter('date')(($scope.fromDate), "dd-MMM-yyyy")
				}
			});

			$scope.$watch('toDate', function() {
				if ($scope.toDate != null) {
					$scope.toDate = $filter('date')(($scope.toDate), "dd-MMM-yyyy")
				}
			});

			$scope.getWorkFlowstatus = function() {
				$scope.workflowStatusList = [];

				PreContractExternalService.getWorkFlowstatus().then(function(data) {
					$scope.workflowStatusList = data.workFlowStatusTOs;

				}, function(error) {
					GenericAlertService.alertMessage("Error occured while gettting workflow status", 'Error');
				});
			},

			$scope.getUserProjects = function() {
				var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
				userProjectSelection.then(function(data) {
					$scope.searchProject = data.searchProject;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
				});
			}, $scope.getExternalPreContracts = function() {
				var projIds = null;
				if ($scope.searchProject.projIds != null && $scope.searchProject.projIds.length > 0) {
					projIds = [];
					projIds = $scope.searchProject.projIds;
				}
				var loginUser = true;
				if ($scope.userType == '2') {
					loginUser = false;
				}
				var req = {
					"projIds" : projIds,
					"approveStatus" : $scope.contractStatus.value,
					"status" : 1,
					"loginUser" : loginUser,
					"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
					"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
				};
				PreContractExternalService.getExternalPreContracts(req).then(function(data) {
					$scope.userProjMap = data.userProjMap;
					$scope.preContractTOs = data.preContractTOs;
					$scope.usersMap= data.usersMap;
					if ($scope.preContractTOs.length <= 0) {
						GenericAlertService.alertMessage("PreContracts are not available for given search criteria", 'Warning');
					}
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');
				});
			}, $scope.getExternalPreContracts();
			$scope.resetExternalRequest = function() {
				$scope.preContractTOs = [];
				$scope.contractStatus = {};
				$scope.searchProject.parentName = null;
				$scope.searchProject.projName = null;

			}, $scope.rowSelect = function(req) {
				if (req.select) {
					editExternalReq.push(req);
				} else {
					editExternalReq.pop(req);
				}

			}, $scope.getApprovelDetails = function(req) {
				var req = {
					"contractId" : req.id,
					"status" : 1
				}

				var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req);
				popupDetails.then(function(data) {
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
				})
			}, $scope.getBidAnalysisDetails = function(preContractId) {
				var bidDetails = $scope.getCompanyBidDetails(preContractId);
				bidDetails.then(function(data) {
					var popupDetails = PreContractBidanalysisRequestFactory.getCompanyBidDetails(data.preContractObj);
					popupDetails.then(function(data) {
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
					})
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
				})

			}, $scope.getRefDocument = function(precontract) {
				var refreq = ReferenceDocumentsPopupFactory.referenceDocumentDetails(precontract);
				refreq.then(function(data) {
					$scope.refdocumentslist = data.preContractDocsTOs;
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
				})

			}, $scope.addExternalRequest = function(preContractObj) {

				var resultData = $scope.getExternalPrecontractDetails(preContractObj);
				resultData.then(function(data) {
					var popupDetails = PreContractExternalFactory.procExternalApprovalPopUp(data.preContractObj);
					popupDetails.then(function(data) {
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
					})
				}, function(error) {
					GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
				})
			},

			$scope.getExternalPrecontractDetails = function(preContractObj) {
				var externalDefer = $q.defer();
				var onLoadReq = {
					"projId" : preContractObj.projId,
					"contractId" : preContractObj.id,
					"status" : 1
				};
				PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
					var returnObj = {
						"preContractObj" : angular.copy(data)
					};
					externalDefer.resolve(returnObj);
				});
				return externalDefer.promise;
			}, $scope.getCompanyBidDetails = function(preContractObj) {
				var onLoadReq = {
					"projId" : preContractObj.projId,
					"contractId" : preContractObj.id,
					"status" : 1

				};

				var bidAnalysisDefer = $q.defer();
				PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function(data) {
					var returnPreContractObj = {
						"preContractObj" : angular.copy(data)
					};
					bidAnalysisDefer.resolve(returnPreContractObj);
				});
				return bidAnalysisDefer.promise;

			}

		}]);
