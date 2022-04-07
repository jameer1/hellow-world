'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("planttransferrequest", {
		url: '/planttransferrequest',
		data: {
			roles: []
		},
		views: {
			'current@': {
				templateUrl: 'views/projresources/projplantreg/requestForTransfer/plantregtrasnferreq.html',
				controller: 'PlantTransferRequestController'
			}
		}
	}).state("planttransferrequest1", {
		url: '/planttransferrequest1',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projresources/projplantreg/requestForTransfer/plantreqtransfer.html',
				controller: 'PlantTransferRequestController'
			}
		}
	})
}]).controller("PlantTransferRequestController", ["$rootScope", "$scope", "uiGridGroupingConstants", "uiGridConstants", "stylesService", "ngGridService", "$q", "$state", "$filter", "ngDialog", "GenericAlertService", "PlantPostApprovalReceiverFactory", "PlantTransferRequestFactory", "PlantRegisterService", function ($rootScope, $scope, uiGridGroupingConstants, uiGridConstants, stylesService, ngGridService, $q, $state, $filter, ngDialog, GenericAlertService, PlantPostApprovalReceiverFactory, PlantTransferRequestFactory, PlantRegisterService) {
	$scope.stylesSvc = stylesService;
	var editNewRequest = [];
	$scope.plantTransferReqApprTOs = [];
	$scope.plantTransferReqTable = true;
	let todayDate = new Date();
	let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));

	$scope.plantTransfer = {
		"fromDate" : $filter('date')((lastMonthDate), "dd-MMM-yyyy"),
		"toDate" : $filter('date')((todayDate), "dd-MMM-yyyy"),
		"userType" : '1'
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
			"transType": true,
			"loginUser": loginUser
		};
		PlantRegisterService.getPlantTransfers(req).then(function (data) {
			$scope.plantTransReqApprTO =data.plantTransferReqApprTOs;
			console.log($scope.plantTransReqApprTO, 'gls')
			$scope.gridOptions.data = angular.copy($scope.plantTransReqApprTO);
			/*for(var i=0;i<=data.plantTransferReqApprTOs.length; i++){
				console.log(data.plantTransferReqApprTOs[i].apprStatus);
				if(data.plantTransferReqApprTOs[i].apprStatus!=null && data.plantTransferReqApprTOs[i].apprStatus!=""){
					$scope.plantTransferReqApprTOs.push(data.plantTransferReqApprTOs[i]);
				}
			}*/
			$scope.plantTransferReqApprTOs = data.plantTransferReqApprTOs;
			$scope.plantTransferReqTable = true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  plant transfer requests", 'Error');
		});
	};
	$scope.getPlantTransfers(true);

	$scope.newRequestRowSelect = function (request) {
		if (request.selected) {
			editNewRequest.push(request);
		} else {
			editNewRequest.pop(request);
		}
	};

	$scope.createOrEditTransRequest = function (actionType, requestTO) {
		var resultData = PlantTransferRequestFactory.getPlantTransRequestDetails(actionType, requestTO);
		resultData.then(function (data) {
			console.log(resultData, 'HI..........')
			$scope.plantTransReqApprTO = data.plantTransferReqApprTOs
			
            $scope.gridOptions.data = angular.copy($scope.plantTransReqApprTO);
			// $scope.gridOptions.data = data;
			$scope.plantTransferReqTable = false;
			$scope.getPlantTransfers(true);			
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching plant request details", 'Error');
		});
	};

	$scope.$on("resetRequest", function () {
		$scope.plantTransferReqApprTOs = [];
	});

	$scope.reset = function(){
		$scope.plantTransfer = {
			"fromDate" : $filter('date')((lastMonthDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')((todayDate), "dd-MMM-yyyy"),
			"userType" : '1'
		}
		$scope.getPlantTransfers(true);
	};
	
	$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ name: 'reqDate', cellFilter: 'date', displayName:'Date of Request', headerTooltip: "Date of Request", groupingShowAggregationMenu: false, },
				{ field: 'reqUserTO.empCode', displayName: "Requester", headerTooltip: "Requester", groupingShowAggregationMenu: false,},
				{ field: 'apprUserTO.empCode', displayName: "Approver", headerTooltip: "Approver", groupingShowAggregationMenu: false,},
				{ field: 'fromProjName', displayName: "Origin Project", headerTooltip: "Origin Project", groupingShowAggregationMenu: false,},
				{ field: 'toProjName', displayName: "Destination Project", headerTooltip: "Destination Project", groupingShowAggregationMenu: false,},
				{ field: 'reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false,},
				{ field: 'notifyCode', displayName: "Notification", headerTooltip: "Notification", groupingShowAggregationMenu: false,},
				{ field: 'apprStatus',displayName:'Approval Status', cellFilter:"openclosefilter : row.entity.status", headerTooltip: "Approval Status",},
				{ name: 'View', displayName: "More Details", headerTooltip : "Resource Curve", cellClass: 'justify-center', headerCellClass:"justify-center", enableFiltering: false,groupingShowAggregationMenu: false, cellTemplate: 'template.html' },
				  ]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_Plant_RequestForApproval");
			$scope.gridOptions.gridMenuCustomItems = false;
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
			template: 'views/help&tutorials/requestapprovalhelp/requesthelp/planttransferrequesthelp.html',
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
