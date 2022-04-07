'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifyempleave", {
		url : '/notifyempleave',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/employeeleave.html',
				controller : 'NotifyEmployeeLeaveController'
			}
		}
	})
}]).controller("NotifyEmployeeLeaveController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectSelectFactory",'ngGridService','stylesService', function($rootScope, $scope, $q, $state, $filter, ngDialog,GenericAlertService,NotificationService, EpsProjectSelectFactory, ngGridService, stylesService) {

	$scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Pending',
		notifyappCodeTemplateKey :'NOTIFICATION_LEAVEREQAPPR_SUBMISSION'
		},
		{title: 'Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_LEAVEREQAPPR_APPROVED'
		},
		{title: 'Rejected',
		notifyappCodeTemplateKey :'NOTIFICATION_LEAVEREQAPPR_REJECT'
		},
		{title: 'Addtional Time For Submit',
		notifyappCodeTemplateKey :'NOTIFICATION_LEAVEREQAPPR_ADDTIMEFORREQUEST'
		},
		{title: 'Addtional Time For Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_LEAVEREQAPPR_ADDTIMEFORAPPROVED'
		}
	];

	$scope.notifyStatus = "All";
	$scope.searchProject = {};
	
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	
	NotificationService.notificationStatusOnLoad().then(function (data) {
		$scope.userProjMap = data.userProjMap;
		$scope.usersMap = data.usersMap;
	});
	NotificationService.getCountNotification().then(function(data) {
		$scope.NotificationCountResp = data.NotificationCountResp;

	});
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		},
		function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
		});
	},$scope.getEmpNotificationDetails = function(){
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};
		NotificationService.getEmpLeaveNotifications(req).then(function(data) {
			console.log(data);
			$scope.empNotification = data.employeeLeaveNotificationsTOs;

			var fromDate = new Date($scope.fromDate);
			var fromDate1 = new Date($scope.fromDate);
			
			var fromDate2=fromDate1.setDate( fromDate1.getDate() + 31);
			$scope.gridOptions.data = angular.copy($scope.empNotification);
			var toDate = new Date($scope.toDate);
			if (fromDate > toDate) {
				GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
				return;
			}
			
			if ($scope.empNotification == null || $scope.empNotification.length <= 0) {
				GenericAlertService.alertMessage("There are no records avaialable for the Search Criteria", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	}
	
	$scope.getEmpNotificationDetails();
	
	$scope.resetEmpNotificationDetails = function(){
		$scope.empNotification = [];
		$scope.gridOptions.data = [];
		$scope.code = null;
		$scope.searchProject = {};
		$scope.type="";
		$scope.notifyStatus = "All";
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	    $scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	}
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
				
				{
					name: 'plant',displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false,
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].code}}</div>',
					},
				
				{
					name: 'name', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false,
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].name}}</div>',
					}, 
				
				{ field: 'requistionCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false},
				
				{ field: 'code', displayName: "Notification Number", headerTooltip: "Notification Number", groupingShowAggregationMenu: false},
				
				{ field: 'fromUserName', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				
				{ field: 'toUserName', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
				
				{ field: 'type', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				{ field: 'notifyStatus', displayName: "Notification Status", headerTooltip: "Notification Status", groupingShowAggregationMenu: false},
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Notification_EmployeeTransfer");
		}
	});
	var openPopup;
	var service = {};
	$scope.show = function(notify) {
		openPopup = service.emailDetails(
			notify, $scope.userProjMap, 
			$scope.usersMap, 
			$scope.notifyStatus);
	}, service.emailDetails = function(notify, userProjMap, usersMap, notifyStatus) {
			var deferred = $q.defer();
			openPopup = ngDialog.open({
				template : 'views/notifications/common/notificationEmployeeLeavePopup.html',
				className : 'ngdialog-theme-plain ng-dialogueCustom4',
				showClose: false,
				controller : [ '$scope', function($scope) {
					$scope.userProjMap = userProjMap;
					$scope.usersMap = usersMap;
					$scope.notify = notify;
					$scope.notifyStatus = notifyStatus;
					$scope.type = notify.type;
				} ]
			});
		
	}

}]);
