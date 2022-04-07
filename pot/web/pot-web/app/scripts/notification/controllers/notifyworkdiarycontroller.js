'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifyworkdairy", {
		url : '/notifyworkdairy',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/workdiary.html',
				controller : 'NotifyWorkDiaryController'
			}
		}
	})
}]).controller("NotifyWorkDiaryController", ["$rootScope", "$scope", "$q", "$state", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$filter", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectSelectFactory", function($rootScope, $scope, $q, $state, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $filter, ngDialog, GenericAlertService, NotificationService, EpsProjectSelectFactory) {
	$scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Pending',
		notifyappCodeTemplateKey :'NOTIFICATION_WORKDAIRY_SUBMISSION'
		},
		{title: 'Approved Internally',
		notifyappCodeTemplateKey :'NOTIFICATION_WORKDAIRY_APPROVED'
		},
		{title: 'Approved by Client',
		notifyappCodeTemplateKey :'NOTIFICATION_WORKDAIRY_CLIENTAPPROVED'
		},
		{title: 'Request for Additional Time',
		notifyappCodeTemplateKey :'NOTIFICATION_WORKDAIRY_ADDTIMEFORREQUEST'
		},
		{title: 'Additional Time For Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_WORKDAIRY_ADDTIMEFORAPPROVED'
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
		console.log(data.userProjMap)
		$scope.gridOptions.data = angular.copy($scope.userProjMap);

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
	$scope.getWorkDiaryNotificationDetails = function(){
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};
		console.log(req);
		NotificationService.getWorkDiaryNotifications(req).then(function(data) {
			$scope.workDiary = data.workDairyNotificationsTOs;
			$scope.gridOptions.data = angular.copy($scope.workDiary);
			console.log("TEST");
			console.log(data.workDairyNotificationsTOs);
			var fromDate = new Date($scope.fromDate);
			var fromDate1 = new Date($scope.fromDate);
			
			var fromDate2=fromDate1.setDate( fromDate1.getDate() + 31);
					
			var toDate = new Date($scope.toDate);
			if (fromDate > toDate) {
				GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
				return;
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	}
	
	$scope.getWorkDiaryNotificationDetails();
	
	$scope.resetWorkDiaryNotificationDetails = function(){
		$scope.workDiary = [];
		$scope.code = null;
		$scope.searchProject = {};
		setDate();
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
	function (newValue, oldValue) {
		
		
		if (newValue) {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
				{
					name: 'plant', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].code}}</div>',
					displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
				{ field: 'parentName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ field: 'crewName', displayName: "Crew Name", headerTooltip: "Crew Name", groupingShowAggregationMenu: false},
				{ field: 'shift', displayName: "Shift", headerTooltip: "Shift", groupingShowAggregationMenu: false},
				{ field: 'id', displayName: "Work Diary Number", headerTooltip: "Work Diary Number", groupingShowAggregationMenu: false},
				{ field: 'code', displayName: "Notification Number", headerTooltip: "Notification Number", groupingShowAggregationMenu: false},
				{ field: 'Days', displayName: "Days Between", headerTooltip: "Days Between", groupingShowAggregationMenu: false},
				{ field: 'notificationMsg', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				{ field: 'fromUserName', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				{ field: 'toUserId', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
				{ field: 'notificationStatus', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				{
					name: 'reqComments', headerCellClass: "justify-right", footerCellClass: "justify-right",
					cellTemplate: '<span ui-grid-cell-contents" name="comment" flip="horizontal" ng-click="show(notify)"></span>',
					displayName: "Comments", headerTooltip: "Comments", groupingShowAggregationMenu: false, 
				},

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Notification_WorkDiary");
			$scope.gridOptions.showColumnFooter = false
		}
	});


}]);
