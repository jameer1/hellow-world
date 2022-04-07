'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empreqtransfer", {
		url : '/empreqtransfer',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projempreg/emptransfer/emptransfer.html',
				controller : 'EmpReqTransferController'
			}
		}
	}).state("empreqtransfer1", {
		url : '/empreqtransfer1',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/emptransfer/empreqtransfer.html',
				controller : 'EmpReqTransferController'
			}
		}
	})
}]).controller("EmpReqTransferController", ["$rootScope", "$scope", "stylesService", "ngGridService", "$q", "$state", "$filter", "ngDialog", "EmpRegisterDataShareFactory", "EmpTransferReqFactory", "EmpRegisterService", "GenericAlertService", function($rootScope, $scope, stylesService, ngGridService, $q, $state, $filter, ngDialog, EmpRegisterDataShareFactory, EmpTransferReqFactory, EmpRegisterService, GenericAlertService) {
	$scope.stylesSvc = stylesService;
	$scope.newRequestData = [];
	var editNewRequest = [];
	$scope.empReqTransTOs = [];
	$scope.empReqTransMap = [];
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.transferReq = {
		"fromDate" : $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
		"toDate" : $filter('date')((defaultToDate), "dd-MMM-yyyy"),
		"userType" : '1'
	};

	$scope.getEmpTransfers = function(onload) {
		var startDate = new Date($scope.transferReq.fromDate);
		var finishDate = new Date($scope.transferReq.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('From Date should be Less than To Date', 'Warning');
			return;
		}
		var loginUser = true;
		var TransTypes = true;
		if ($scope.transferReq.userType == '2') {
			loginUser = false; // loginUser;
			TransTypes = false;
		}
		var req = {
			"status" : 1,
			"fromDate" : $filter('date')(($scope.transferReq.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.transferReq.toDate), "dd-MMM-yyyy"),
			"onload" : onload,
			"transType" :TransTypes, // true,
			"loginUser" : loginUser,
			"apprStatus" : "Pending For Approval"
		};
		EmpRegisterService.getEmpTransfers(req).then(function(data) {
			$scope.empReqTransTOs = data.empReqTransTOs;
			$scope.empTransReqApprDetailTOs = data.empReqTransTOs;
			console.log($scope.empTransReqApprDetailTOs,'glsingh')
			$scope.gridOptions.data = angular.copy($scope.empTransReqApprDetailTOs);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  employee transfer requests", 'Error');

		});

	},
	 $scope.getEmpTransfers(true);
	$scope.newRequestRowSelect = function(request) {
		if (request.select) {
			editNewRequest.push(request);
		} else {
			editNewRequest.pop(request);
		}
	},

	$scope.createOrEditTransRequest = function(actionType='Edit', requestTO) {
		if (actionType == 'Edit' && requestTO <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
		var resultData = EmpTransferReqFactory.getEmpTransRequestDetails(actionType, requestTO, $scope.empReqTransMap);
		resultData.then(function(data) {
			console.log($scope.data, 'Hi.....')
			$scope.empReqTransTOs = data.empReqTransTOs;
			$scope.gridOptions.data = angular.copy($scope.empReqTransTOs);
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

	$scope.deleteNewRequest = function() {
		if (editNewRequest.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.empReqTransTOs = [];
		} else {
			angular.forEach(editNewRequest, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"empIds" : deleteIds,
				"status" : 2
			};
			EmpRegisterService.deleteNewRequest(req).then(function(data) {
			});
			angular.forEach(editNewRequest, function(value, key) {
				GenericAlertService.alertMessage('Employee request detail(s) is/are  deactivated successfully', 'Info');
				$scope.empReqTransTOs.splice($scope.empReqTransTOs.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Employee request detail(s) is/are failed to deactivate', "Error");
			});
			editNewRequest = [];
			$scope.deleteIds = [];
		}
	},
	$scope.$on("resetRequestForTransfer", function() {
		$scope.empReqTransTOs = [];
	});
	
	$scope.$on("value", function() {
		$scope.value=1;
	});
	$scope.reset = function() {
		$scope.transferReq = {
			"fromDate" : $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')((defaultToDate), "dd-MMM-yyyy"),
			"userType" : '1'
		};
		$scope.getEmpTransfers(true);
	}
	
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'reqDate', displayName: "Date of Request", headerTooltip: "Date of Request", groupingShowAggregationMenu: false },
						{ field: 'reqUserTO.empCode', displayName: "Requester", headerTooltip: "Requester", groupingShowAggregationMenu: false },
						{ field: 'apprUserTO.empCode', displayName: "Approver", headerTooltip: "Approver", groupingShowAggregationMenu: false },
						{ field: 'fromProjName', displayName: "Origin Project", headerTooltip: "Origin Project", groupingShowAggregationMenu: false },
						{ field: 'toProjName', displayName: "Destination Project", headerTooltip: "Destination Project", groupingShowAggregationMenu: false },
						{ field: 'reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false },
						{ field: 'reqNotifyCode', displayName: "Requisition Notification", headerTooltip: "Requisition Notification", groupingShowAggregationMenu: false },
						{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status", groupingShowAggregationMenu: false },
						{ name: 'name',displayName: "More Details", headerTooltip : "More Details", groupingShowAggregationMenu: false,cellClass: 'justify-center', headerCellClass:"justify-center", 
						cellTemplate: '<div><span class="btn btn-primary btn-sm" ng-click="grid.appScope.createOrEditTransRequest(Edit, row.entity, 1, $index);" data-test="Resources_Employee_RequestForTransfer_MoreDetailsView">View</span></div>',},
						
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Request&Approvals_Request_EmployeeTransferRequest");
					$scope.gridOptions.gridMenuCustomItems = false;
					$scope.getEmpTransfers(true);
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
			template: 'views/help&tutorials/requestapprovalhelp/requesthelp/emptransferrequesthelp.html',
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