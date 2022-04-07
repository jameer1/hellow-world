'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifyattendancerecords", {
		url : '/notifyattendancerecords',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/attendance.html',
				controller : 'NotifyAttendanceController'
			}
		}
	})
}]).controller("NotifyAttendanceController", ["$rootScope", "$scope", "$q", "$state", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$filter", "ngDialog", "NotificationService", "GenericAlertService", "EpsProjectSelectFactory", function($rootScope, $scope, $q, $state, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $filter, ngDialog,NotificationService, GenericAlertService ,EpsProjectSelectFactory) {

	$scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Pending',
		notifyappCodeTemplateKey :'NOTIFICATION_ATTENDANCE_SUBMISSION'
		},
		{title: 'Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_ATTENDANCE_APPROVED'
		}
	];

	//$scope.notifyStatus = $scope.notificationStatus[0];
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

	NotificationService.notificationStatusOnLoad().then(function(data) {
		$scope.userProjMap = data.userProjMap;
		$scope.usersMap = data.usersMap;
	});
	
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		},
		function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
		});
	},
	$scope.getAttendanceNotificDetails = function(){
		var req = {
				"projId" : $scope.searchProject.projId,
				"status" : 1,
				"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
				"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
				"notifyStatus" : $scope.notifyStatus,
				"code" : $scope.code
			};
			console.log(req);
		
		NotificationService.getAttendanceNotifications(req).then(function(data) {
			$scope.attendanceNotification = data.attendenceNotificationsTOs;
			$scope.gridOptions.data = angular.copy($scope.attendanceNotification);
			console.log(data.attendenceNotificationsTOs);
			if ($scope.attendanceNotification == null || $scope.attendanceNotification.length <= 0) {
				GenericAlertService.alertMessage("There are no records avaialable for the Search Criteria", 'Warning');
			}
			
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
		
	},
	
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}
	
	$scope.getAttendanceNotificDetails();
	$scope.resetAttendanceNotificDetails = function(){
		$scope.attendanceNotification = [];
		$scope.code = null;
		$scope.searchProject = {};
		setDate();
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}
	$scope.show = function(reqComments) {
		ngDialog.open({
			template : 'views/notifications/common/notificationCommentsPopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose: false,
			controller : [ '$scope', function($scope) {
				$scope.reqComments = reqComments;
			} ]
		});
	}
	
	$scope.$watch(function () { return stylesService.finishedStyling; },
	function (newValue, oldValue) {
		
		if (newValue) {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
				{ field: 'parentName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
				{ field: 'name', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ field: 'crewName', displayName: "Week Commencing", headerTooltip: "Week Commencing", groupingShowAggregationMenu: false},
				{ field: 'attendenceMonth', displayName: "Time Sheet Number", headerTooltip: "Time Sheet Number", groupingShowAggregationMenu: false},
				{ field: 'toDate', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				{ field: 'code', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				{ field: 'fromUserName', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
				{ field: 'toUserName', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				{ field: 'type', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				{ field: 'notificationStatus', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				{ field: 'reqComments', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				
				{
					name: 'reqComments', headerCellClass: "justify-right", footerCellClass: "justify-right",
					displayName: "Comments", headerTooltip: "Comments", groupingShowAggregationMenu: false, 
					// cellTemplate: '<span ui-grid-cell-contents" name="comment" flip="horizontal" ng-click="show(attendance.reqComments)">{{attendance.reqComments}}</span>',
				},

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Notification_Attandance");
			$scope.gridOptions.showColumnFooter = false
		}
	});
}]);
