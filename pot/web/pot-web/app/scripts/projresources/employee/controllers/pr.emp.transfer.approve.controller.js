'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("emptransferapprove", {
		url : '/emptransferapprove',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projempreg/approvaltransfer/emptransferapproval.html',
				controller : 'EmpTransferApprovalController'
			}
		}
	}).state("emptransferapprove1", {
		url : '/emptransferapprove1',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/approvaltransfer/empapprovaltransfer.html',
				controller : 'EmpTransferApprovalController'
			}
		}
	})
}]).controller("EmpTransferApprovalController", ["$rootScope", "$scope", "stylesService", "ngGridService", 'uiGridGroupingConstants',"$q", "$state", "$filter", "ngDialog", "GenericAlertService", "EmpRegisterService", "EmpTransferApprFactory", function($rootScope, $scope, stylesService, ngGridService, uiGridGroupingConstants,$q, $state, $filter, ngDialog,GenericAlertService, EmpRegisterService, EmpTransferApprFactory) {
	$scope.stylesSvc = stylesService;
	var editPendingApproval = [];
	$scope.selectedValue = [];
	$scope.approvalTypeList = [ "Pending For Approval", "Approved", "Rejected", "All" ];
	$scope.empReqTransTOs = [];
	var editPendingApproval=[];
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.apprTransfer = {
		"fromDate" : $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
		"toDate" : $filter('date')((defaultToDate), "dd-MMM-yyyy"),
		"userType" : '1',
		"approvalType" : $scope.approvalTypeList[0]
	}
	$scope.getEmpTransfers = function(onload) {
		var startDate = new Date($scope.apprTransfer.fromDate);
		var finishDate = new Date($scope.apprTransfer.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('From Date should be Less than To Date', 'Warning');
			return;
		}
		var loginUser = true;
		if ($scope.apprTransfer.userType == '2') {
			loginUser = false;
		}
		var req = {
			"status" : 1,
			"fromDate" : $filter('date')(($scope.apprTransfer.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.apprTransfer.toDate), "dd-MMM-yyyy"),
			"onload" : onload,
			"transType" : false,
			"apprStatus" : $scope.apprTransfer.approvalType,
			"loginUser" : loginUser
		};
		EmpRegisterService.getEmpTransfers(req).then(function(data) {
			$scope.empReqTransTOs = data.empReqTransTOs;
			$scope.gridOptions.data = angular.copy($scope.empReqTransTOs);
			if (onload) {
				$scope.empReqTransMap = data.empReqTransOnLoadResp;
			}
			$scope.transferEmpMap=data.transferEmpMap;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  employee transfer requests", 'Error');

		});

	}, $scope.getEmpTransfers(true);

	$scope.newRequestRowSelect = function(approval) {
		if (approval.selected) {
			editPendingApproval.push(approval);
			$scope.selectedValue = editPendingApproval;
		} else {
			editPendingApproval.pop(approval);
		}
	}

	
	$scope.getEmpTransRequestDetails = function(requestTO) {
		var resultData = EmpTransferApprFactory.getEmpTransRequestDetails(requestTO, $scope.empReqTransMap);
		resultData.then(function(data) {
			for (let newEmpReq of data.empReqTransTOs) {
				const existing = $scope.empReqTransTOs.find(obj => { return obj.id === newEmpReq.id });
				if (!existing) {
					$scope.empReqTransTOs.unshift(newEmpReq);
				}
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching employee request details", 'Error');
		});
	},

	$scope.deleteApproval = function() {
		if (editPendingApproval.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.pendingApproval = [];
		} else {
			angular.forEach(editPendingApproval, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"plantIds" : deleteIds,
				"status" : 2
			};
			PlantRegisterService.deleteNewRequest(req).then(function(data) {
			});
			angular.forEach(editPendingApproval, function(value, key) {
				GenericAlertService.alertMessage('Plant Approval Detail(s) is/are  Deactivated successfully', 'Info');
				$scope.pendingApproval.splice($scope.pendingApproval.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Plant Approval Detail(s) is/are failed to Deactivate', "Error");
			});
			editPendingApproval = [];
			$scope.deleteIds = [];
		}
	},
	$scope.$on("resetApprovalForTransfer", function() {
		$scope.empReqTransTOs = [];
	});
	$scope.reset = function(){
		$scope.apprTransfer = {
			"fromDate" : $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')((defaultToDate), "dd-MMM-yyyy"),
			"userType" : '1',
			"approvalType" : $scope.approvalTypeList[0]
		}
		$scope.getEmpTransfers(true);
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'reqDate', displayName: "Date of Request", headerTooltip: "Date of Request", groupingShowAggregationMenu: false},
						{ name: 'reqUserTO.empCode', displayName: "Requester ID", headerTooltip: "Requester ID", groupingShowAggregationMenu: false},
						{ field: 'apprUserTO.empCode', displayName: "Approver ID", headerTooltip: "Approver ID", groupingShowAggregationMenu: false},
						{ field: 'fromProjName', displayName: "Origin Project", headerTooltip: "Origin Project", groupingShowAggregationMenu: false},
						{ field: 'toProjName', displayName: "Destination Project", headerTooltip: "Destination Project", groupingShowAggregationMenu: false},
						{ name: 'reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false},
						{ name: 'reqNotifyCode', displayName: "Requisition Notification", headerTooltip: "Requisition Notification", groupingShowAggregationMenu: false},
						{ name: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status", groupingShowAggregationMenu: false},
						{ name: 'preContractReqApprTO.reqDate', displayName: "More Details", headerTooltip: "More Details", cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: '<div><span class="btn-primary btn-sm btn" ng-click="grid.appScope.getEmpTransRequestDetails(row.entity, 1, $index);" data-test="EmpTransferApproval_View">View</label></div>', groupingShowAggregationMenu: false},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Request&Approvals_Approvals_EmployeeTransferApproval");
					$scope.gridOptions.gridMenuCustomItems = false;
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
			template: 'views/help&tutorials/requestapprovalhelp/approvalshelp/emptransferapprovalhelp.html',
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