'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("notifymaterialtransfer", {
		url : '/notifymaterialtransfer',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/notifications/materialtransfer.html',
				controller : 'NotifyMaterialTransferController'
			}
		}
	})
}]).controller("NotifyMaterialTransferController", ["$rootScope", "$scope", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$filter", "$q", "$state", "ngDialog", "GenericAlertService", "NotificationService", "EpsProjectSelectFactory", function($rootScope, $scope, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $filter, $q, $state, ngDialog,GenericAlertService,NotificationService, EpsProjectSelectFactory) {
	$scope.stylesSvc = stylesService;
	$scope.notificationStatus=[
		{title: 'Pending',
		notifyappCodeTemplateKey :'NOTIFICATION_MATERIALTRANS_SUBMISSION'
		},
		{title: 'Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_MATERIALTRANS_APPROVED'
		},
		{title: 'Rejected',
		notifyappCodeTemplateKey :'NOTIFICATION_MATERIALTRANS_REJECT'
		},
		{title: 'Addtional Time For Submit',
		notifyappCodeTemplateKey :'NOTIFICATION_MATERIALTRANS_ADDTIMEFORREQUEST'
		},
		{title: 'Addtional Time For Approved',
		notifyappCodeTemplateKey :'NOTIFICATION_MATERIALTRANS_ADDTIMEFORAPPROVED'
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

	NotificationService.notificationStatusOnLoad().then(function(data) {
		$scope.userProjMap = data.userProjMap;
		$scope.usersMap = data.usersMap;
	});
	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = data.searchProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getMaterialNotificationDetails = function() {
		var req = {
			"projId" : $scope.searchProject.projId,
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"notifyStatus" : $scope.notifyStatus,
			"code" : $scope.code
		};

		NotificationService.getMaterialNotifications(req).then(function(data) {
			$scope.materialNotification = data.materialNotificationsTOs;
			$scope.gridOptions.data = angular.copy($scope.materialNotification);
			console.log($scope.materialNotification);
			var fromDate = new Date($scope.fromDate);
			var fromDate1 = new Date($scope.fromDate);

			var fromDate2 = fromDate1.setDate(fromDate1.getDate() + 31);

			var toDate = new Date($scope.toDate);
			if (fromDate > toDate) {
				GenericAlertService.alertMessage('StartDate must be < FinishDate', 'Warning');
				return;
			} else if (fromDate2 < toDate) {
				GenericAlertService.alertMessage('dont exceed more than 31 days', 'Warning');
				return;
			}

			if ($scope.materialNotification == null || $scope.materialNotification.length <= 0) {
				GenericAlertService.alertMessage("There are no records available for the selected Status", 'Warning');
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects ", 'Error');
		});
	}
	
	$scope.getMaterialNotificationDetails();
	
	$scope.resetMaterialNotifications = function(){
		$scope.materialNotification = [];
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
				template : 'views/notifications/common/notificationMaterialTransferPopup.html',
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
	$scope.$watch(function () { return stylesService.finishedStyling; },
	function (newValue, oldValue) {
		
		if (newValue) {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false},
				{ name: 'code', displayName: "EPS Name", headerTooltip: "EPS Name", groupingShowAggregationMenu: false,
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].code}}</div>'},
				{ name: 'projId', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false,
					cellTemplate: '<div>{{grid.appScope.userProjMap[row.entity.projId].name}}</div>'},
				{ field: 'requistionCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false},
				{ field: 'code', displayName: "Notification Number", headerTooltip: "Notification Number", groupingShowAggregationMenu: false},
				{ field: 'type', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false},
				{ field: 'fromUserName', displayName: "From User", headerTooltip: "From User", groupingShowAggregationMenu: false},
				{ field: 'toUserName', displayName: "To User", headerTooltip: "To User", groupingShowAggregationMenu: false},
				{ field: 'notifyStatus', displayName: "Status", headerTooltip: "Status", groupingShowAggregationMenu: false},
				

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Notification_MaterialTransfer");
			$scope.gridOptions.showColumnFooter = false
		}
	});
}]);
