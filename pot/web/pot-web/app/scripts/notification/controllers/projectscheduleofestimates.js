'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifyprojectscheduleofestimates", {
		url : '/notifyprojectscheduleofestimates',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/projectscheduleofestimates.html',
				controller : 'NotifyProjectScheduleofEstimates'
			}
		}
	})
}]).controller("NotifyProjectScheduleofEstimates", ["$rootScope", "$scope", "$q", "$state", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$filter", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectMultiSelectFactory", function($rootScope, $scope, $q, $state, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $filter, ngDialog, GenericAlertService, NotificationService, EpsProjectMultiSelectFactory) {
	$scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Submit Internal Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_SUBMISSION'
		},
		{title: 'Submit External Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_APPROVED'
		},
		{title: 'Return',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_CLIENTAPPROVED'
		},
		{title: 'Internally Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_CLIENTAPPROVED'
		},
		{title: 'External Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_CLIENTAPPROVED'
		},
		{title: 'Request for Additional Time',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_ADDTIMEFORREQUEST'
		},
		{title: 'Additional Time For Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_SOE_ADDTIMEFORAPPROVED'
		}
	];

	//$scope.notifyStatus = $scope.notificationStatus[0];
	$scope.notifyStatus = "All";
	$scope.searchProject = {};
	$scope.dataCount= [];
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
	NotificationService.getCountNotification().then(function(data){
		   var count = data.count;
           $scope.dataCount.push(count);
		});
		
		console.log($scope.dataCount);
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		},
		function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
		});
	},
	$scope.getSoeNotificationDetails = function(){
		var req = {
			"projIds" : $scope.searchProject.projIds,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};
		console.log(req);
		
		NotificationService.getSOENotifications(req).then(function(data) {
			$scope.soeDetails=data.soeNotificationsTOs;
			console.log($scope.soeDetails);
			$scope.gridOptions.data = angular.copy($scope.soeDetails);
			console.log("TEST");
		//	console.log(data.workDairyNotificationsTOs);
			var fromDate = new Date($scope.fromDate);
			var fromDate1 = new Date($scope.fromDate);
			
			var fromDate2 = fromDate1.setDate(fromDate1.getDate() + 31);
					
			var toDate = new Date($scope.toDate);
			if (fromDate > toDate) {
				GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
				return;
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	}
	
	$scope.getSoeNotificationDetails();
	
	$scope.resetWorkDiaryNotificationDetails = function(){
		$scope.workDiary = [];
		$scope.code = null;
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	    $scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
        $scope.notifyStatus="";
		$scope.searchProject = {};
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
					field: 'epsName', headerCellClass: "justify-left", footerCellClass: "justify-left",
					displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ field: 'notificationNumber', displayName: "Notification Number", headerTooltip: "Notification Number", groupingShowAggregationMenu: false},
				{ field: 'notificationMsg', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				{ field: 'fromUserName', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				{ field: 'toUserName', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
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
