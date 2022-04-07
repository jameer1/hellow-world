'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifytimesheet", {
		url : '/notifytimesheet',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/timesheet.html',
				controller : 'NotifyTimeSheetController'
			}
		}
	})
}]).controller("NotifyTimeSheetController", ["$rootScope", "$scope", "$q", "$state", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$filter", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectSelectFactory", function($rootScope, $scope, $q, $state, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $filter, ngDialog,GenericAlertService,NotificationService, EpsProjectSelectFactory) {
    $scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Submitted',
		notifyappCodeTemplateKey :'NOTIFICATION_TIMESHEET_SUBMISSION'
		},
		{title: 'Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_TIMESHEET_APPROVED'
		},
		{title: 'Additional Time Request',
		notifyappCodeTemplateKey :'NOTIFICATION_TIMESHEET_ADDTIMEFORREQUEST'
		},
		{title: 'Additional Time Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_TIMESHEET_ADDTIMEFORAPPROVED'
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
	
	$scope.show = function(reqComments) {
		ngDialog.open({
			template : 'views/notifications/common/notificationCommnetsPopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom6',
			controller : [ '$scope', function($scope) {
				$scope.reqComments = reqComments;
			} ]
		});
	},
	
	$scope.getTimeSheetNotificationDetails = function(){
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};
		
		NotificationService.getTimeSheetNotifications(req).then(function(data) {
			$scope.timeSheetNotification = data.timeSheetNotificationsTOs;
			$scope.gridOptions.data = angular.copy($scope.timeSheetNotification);
				var fromDate = new Date($scope.fromDate);
				var fromDate1 = new Date($scope.fromDate);
				
				var fromDate2=fromDate1.setDate( fromDate1.getDate() + 31);
					
				var toDate = new Date($scope.toDate);
				if (fromDate > toDate) {
					GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
					return;
				}
				else if (fromDate2 < toDate) {
					GenericAlertService.alertMessage('dont exceed more than 31 days', 'Warning');
					return;
				}
			
				if ($scope.timeSheetNotification == null || $scope.timeSheetNotification.length <= 0) {
				GenericAlertService.alertMessage("There are no records avaialable for the Search Criteria", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	},
	
	$scope.getTimeSheetNotificationDetails();
	$scope.resetTimeSheetNotificationDetails = function(){
		$scope.timeSheetNotification = [];
		$scope.code = null;
		$scope.searchProject = {};
		$scope.notifyStatus = [];
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
				{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false},
				{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ field: 'weeakCommencingDay', displayName: "Week Commencing", headerTooltip: "Week Commencing", groupingShowAggregationMenu: false},
				{ field: 'timeSheetNumber', displayName: "Time Sheet Number", headerTooltip: "Time Sheet Number", groupingShowAggregationMenu: false},
				{ field: 'notificationNumber', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				{ field: 'fromUserName', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				{ field: 'toUserName', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
				{ field: 'notificationStatus', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				{
					name: 'reqComments', headerCellClass: "justify-right", footerCellClass: "justify-right",
					displayName: "Comments", headerTooltip: "Comments", groupingShowAggregationMenu: false, 
					// cellTemplate: '<span ui-grid-cell-contents" name="comment" flip="horizontal" ng-click="show(timeSheet.reqComments)"></span>',
				},

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Notification_TimeSheet");
			$scope.gridOptions.showColumnFooter = false
		}
	});

}]);
