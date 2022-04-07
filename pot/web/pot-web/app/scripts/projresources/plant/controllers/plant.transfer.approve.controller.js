'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("planttransferapprove", {
		url: '/planttransferapprove',
		data: {
			roles: []
		},
		views: {
			'current@': {
				templateUrl: 'views/projresources/projplantreg/approvalForTransfer/plantregtransferapproval.html',
				controller: 'PlantTransferApprovalController'
			}
		}
	}).state("planttransferapprove1", {
		url: '/planttransferapprove1',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projresources/projplantreg/approvalForTransfer/plantapprovaltransfer.html',
				controller: 'PlantTransferApprovalController'
			}
		}
	})
}]).controller("PlantTransferApprovalController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "GenericAlertService", "PlantTransferApprovalFactory", "PlantRegisterService","stylesService", "ngGridService", function ($rootScope, $scope, $q, $state, $filter, ngDialog, GenericAlertService, PlantTransferApprovalFactory, PlantRegisterService, stylesService, ngGridService) {
	$scope.plantTransferAprTable = true;
	$scope.stylesSvc = stylesService;
	$scope.plantTransferReqApprTOs = [];
	let todayDate = new Date();
	let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));

	$scope.pendingApproval = [];
	var editPendingApproval = [];

	$scope.selectedValue = [];
	
	 $scope.approvalTypeList = ["Pending For Approval", "Approved", "Rejected", "All"];
	 $scope.approvalType = $scope.approvalTypeList[0];

	$scope.plantTransfer = {
		"fromDate": $filter('date')((lastMonthDate), "dd-MMM-yyyy"),
		"toDate": $filter('date')((todayDate), "dd-MMM-yyyy"),
		"userType" : '1'
	};

	$scope.newRequestRowSelect = function (approval) {
		if (approval.selected) {
			editPendingApproval.push(approval);
			$scope.selectedValue = editPendingApproval;
		} else {
			editPendingApproval.pop(approval);
		}
	}
	$scope.getPlantTransfers = function (onload) {
		var startDate = new Date($scope.plantTransfer.fromDate);
		var finishDate = new Date($scope.plantTransfer.toDate);
		if (startDate > finishDate) {
			GenericAlertService.alertMessage('StartDate cant not GreaterThan FinishDate', 'Warning');
			return;
		}
		var loginUser = true;
		if ($scope.plantTransfer.userType == '2') {
			loginUser = false;
		}
		var req = {
			"status": 1,
			"fromDate": $filter('date')(($scope.plantTransfer.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.plantTransfer.toDate), "dd-MMM-yyyy"),
			"onload": onload,
			"transType": false,
			"loginUser": loginUser,
			"apprStatus": $scope.approvalType

		};
		PlantRegisterService.getPlantTransfers(req).then(function (data) {
			console.log(data);
			/*for(var i=0;i<=data.plantTransferReqApprTOs.length; i++){
				console.log(data.plantTransferReqApprTOs[i].apprStatus);
				if(data.plantTransferReqApprTOs[i].apprStatus!=null && data.plantTransferReqApprTOs[i].apprStatus!=""){
					$scope.plantTransferReqApprTOs.push(data.plantTransferReqApprTOs[i]);
				}
			}*/
			console.log("length",data.plantTransferReqApprTOs.length)
			$scope.plantTransReqApprTO =data.plantTransferReqApprTOs;
			console.log($scope.plantTransReqApprTO, 'gls')
			$scope.gridOptions.data = angular.copy($scope.plantTransReqApprTO);
			$scope.plantTransferAprTable = true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  plant transfer requests", 'Error');
		});
	};
	$scope.getPlantTransfers(true);
	$scope.getPlantTransReqDetails = function (editTransReqTO) {
		var popupData = PlantTransferApprovalFactory.getPlantTransReqDetails(editTransReqTO);
		popupData.then(function (data) {
			$scope.plantTransferAprTable = false;
			$scope.getPlantTransfers(true);
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant transfer request details", 'Error');
		});
	};

	$scope.$on("resetApproval", function () {
		$scope.plantTransferReqApprTOs = [];
	});

	$scope.reset = function(){
		$scope.plantTransfer = {
			"fromDate": $filter('date')((lastMonthDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')((todayDate), "dd-MMM-yyyy"),
			"userType" : '1'
		};	
		$scope.getPlantTransfers(true);
	};
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'reqDate', displayName: "Date of Request", headerTooltip: "Date of Request", groupingShowAggregationMenu: false},
						{ field: 'reqUserTO.empCode', displayName: "Requester", headerTooltip: "Requester", groupingShowAggregationMenu: false},
						{ field: 'apprUserTO.empCode', displayName: "Approver", headerTooltip: "Approver", groupingShowAggregationMenu: false},
						{ field: 'fromProjName', displayName: "Origin Project", headerTooltip: "Origin Project", groupingShowAggregationMenu: false},
						{ field: 'toProjName', displayName: "Destination Project", headerTooltip: "Destination Project", groupingShowAggregationMenu: false},
						{ field: 'reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false},
						{ field: 'apprCode', displayName: "Approval ID", headerTooltip: "Approval ID", groupingShowAggregationMenu: false},
						{ field: 'notifyCode', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
						{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status", groupingShowAggregationMenu: false},
						{ name: 'Download Documents', cellClass: 'justify-center', headerCellClass:"justify-center",displayName: "More Details", headerTooltip : "More Details",
						cellTemplate: '<button  ng-click="grid.appScope.getPlantTransReqDetails(row.entity, plantTransReqApprTO)" class="btn btn-primary btn-sm" >View</button>', groupingShowAggregationMenu: false},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Plant_ApprovalForTransfer");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/requestapprovalhelp/approvalshelp/planttransferapprovalhelp.html',
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