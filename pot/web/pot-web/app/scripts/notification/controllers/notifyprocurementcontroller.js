'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifyprocurement", {
		url : '/notifyprocurement',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/procurement.html',
				controller : 'NotifyProcurementController'
			}
		}
	})
}]).controller("NotifyProcurementController", ["$rootScope", "$scope", "uiGridGroupingConstants", 'ngGridService','stylesService',"$q", "$state", "$filter", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectSelectFactory", function($rootScope, $scope, uiGridGroupingConstants, ngGridService,stylesService, $q, $state, $filter, ngDialog,GenericAlertService,NotificationService, EpsProjectSelectFactory) {

    $scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Pending',
		notifyappCodeTemplateKey :'NOTIFICATION_PROCUREMENT_SUBMISSION'
		},
		{title: 'Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_PROCUREMENT_APPROVED'
		},
		{title: 'Rejected',
		notifyappCodeTemplateKey :'NOTIFICATION_PROCUREMENT_REJECT'
		}
	];

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
		userProjectSelection	.then(function(data) {
							$scope.searchProject = data.searchProject;
						},
						function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name",'Error');
						});
	},$scope.getProcureNotificationDetails = function(){
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};
		var fromDate = new Date($scope.fromDate);
		var fromDate1 = new Date($scope.fromDate);
		
		var fromDate2=fromDate1.setDate( fromDate1.getDate() + 31);
			
			
		var toDate = new Date($scope.toDate);
		if (fromDate > toDate) {
			GenericAlertService.alertMessage('To Date must be greater than from date', 'Warning');
			return;
		}
		else if (fromDate2 < toDate) {
			GenericAlertService.alertMessage('dont exceed more than 31 days', 'Warning');
			return;
		}
		NotificationService.getProcureNotifications(req).then(function(data) {
			console.log(data);
			//$scope.procurementNotificationTOs = data.reqApprNotificationsTOs;
			$scope.procurementNotificationTOs = data.procurementNotificationsTOs;
			$scope.gridOptions.data = angular.copy($scope.procurementNotificationTOs);
			console.log($scope.procurementNotificationTOs);
			/*$scope.usersMap = data.usersMap;
			console.log($scope.usersMap);
			$scope.userProjMap = data.userProjMap;*/
				if ($scope.procurementNotificationTOs == null || $scope.procurementNotificationTOs.length <= 0) {
				GenericAlertService.alertMessage("There are no records avaialable for the Search Criteria", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	}
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
				
				{ name: 'parentCode', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false,
					},
				
				{ name: 'parentName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false,
					},
				
				{ field: 'procureCatg', displayName: "Procurement Category", headerTooltip: "Procurement Category", groupingShowAggregationMenu: false},
				
				{ field: 'procureStage', displayName: "Procurement Stage", headerTooltip: "Procurement Stage", groupingShowAggregationMenu: false},
				
				{ field: 'moduleCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false},
				
				{ field: 'code', displayName: "Notification Number", headerTooltip: "Notification Number", groupingShowAggregationMenu: false},
				
				{ field: 'fromUserName', displayName: "From", headerTooltip: "From", groupingShowAggregationMenu: false},
				
				{ field: 'toUserName', displayName: "To", headerTooltip: "To", groupingShowAggregationMenu: false},
				
				{ field: 'notificationStatus', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				
				{ field: 'reqComments', displayName: "Comments", headerTooltip: "Comments", groupingShowAggregationMenu: false},
			    	
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement");
		}
	});
	
	$scope.getProcureNotificationDetails(); 
	$scope.resetProcureNotificationDetails = function(){
		$scope.notifyStatus = [];
	//	$scope.timeSheetSearchReq.crewLabelKeyTO.code= '';
		$scope.code = null;
		$scope.searchProject = {};
		$scope.fromDate= $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate= $filter('date')((defaultToDate), "dd-MMM-yyyy");
	}

}]);
