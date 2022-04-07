'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifyplanttransfer", {
		url : '/notifyplanttransfer',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/planttransfer.html',
				controller : 'NotifyPlantTransferController'
			}
		}
	})
}]).controller("NotifyPlantTransferController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectSelectFactory","uiGridGroupingConstants", 'ngGridService','stylesService', function($rootScope, $scope, $q, $state, $filter, ngDialog,GenericAlertService,NotificationService, EpsProjectSelectFactory,uiGridGroupingConstants, ngGridService, stylesService) {
	$scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Pending',
		notifyappCodeTemplateKey :'NOTIFICATION_PLANTTRANS_SUBMISSION'
		},
		{title: 'APPROVED',
		notifyappCodeTemplateKey :'NOTIFICATION_PLANTTRANS_APPROVED'
		},
		{title: 'REJECTED',
		notifyappCodeTemplateKey :'NOTIFICATION_PLANTTRANS_REJECT'
		},
		{title: 'Additional Time for Submit',
		notifyappCodeTemplateKey :'NOTIFICATION_PLANTTRANS_ADDTIMEFORREQUEST'
		},
		{title: 'Additional Time for Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_PLANTTRANS_ADDTIMEFORAPPROVED'
		}
	];

	//$scope.notifyStatus = $scope.notificationStatus[0].title;
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
	
	$scope.getPlantNotificationDetails = function(){
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};
		NotificationService.getPlantNotifications(req).then(function(data) {
			console.log(data);
			$scope.plantNotification = data.plantNotificationsTOs;
			
				var fromDate = new Date($scope.fromDate);
				var fromDate1 = new Date($scope.fromDate);
				
				var fromDate2=fromDate1.setDate( fromDate1.getDate() + 31);
				$scope.gridOptions.data = angular.copy($scope.plantNotification);	
				var toDate = new Date($scope.toDate);
				if (fromDate > toDate) {
					GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
					forEach.break();
					return;
				}
			
			if ($scope.plantNotification == null || $scope.plantNotification.length <= 0) {
			GenericAlertService.alertMessage("There are no records avaialable for the Search Criteria",'Warning');
		}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	}
	 
	 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
				
				{ name: 'plant', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false,
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].code}}</div>'},
				
				{ name: 'name', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false,
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].name}}</div>'},
				
				{ field: 'requistionCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false},
				
				{ field: 'code', displayName: "Notification Number", headerTooltip: "Notification Number", groupingShowAggregationMenu: false},
				
				{ field: 'fromUserName', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				
				{ field: 'toUserName', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
				
				{ field: 'type', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				
				{ field: 'notificationStatus', displayName: "Notification Status", headerTooltip: "Notification Status", groupingShowAggregationMenu: false},
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Notification_plantTransfer");
		}
	});
	$scope.getPlantNotificationDetails();
	
	$scope.resetPlantNotificationDetails = function(){
		$scope.plantNotification = [];
		$scope.code = null;
		$scope.searchProject = {};
		$scope.fromDate="";
		$scope.toDate="";
		$scope.notifyStatus="";
	}
	
	var openPopup;
	var service = {};
	$scope.show = function(notify) {
		openPopup = service.emailDetails(notify, $scope.userProjMap, $scope.usersMap, $scope.notifyStatus);
	},


	service.emailDetails = function(notify, userProjMap, usersMap, notifyStatus) {
			var deferred = $q.defer();
			openPopup = ngDialog.open({
				template : 'views/notifications/common/notificationPlantTransferPopup.html',
				className : 'ngdialog-theme-plain ng-dialogueCustom4',
				showClose : false,
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
