'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialtransferapproval", {
		url : '/materialtransferapproval',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/approvalmaterialtransfer/materialregtransapproval.html',
				controller : 'MaterialTransferApprovalController'
			}
		}
	}).state("materialtransferapproval1", {
		url : '/materialtransferapproval1',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projmaterialreg/approvalmaterialtransfer/materialapprovaltransfer.html',
				controller : 'MaterialTransferApprovalController'
			}
		}
	})
}]).controller("MaterialTransferApprovalController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "MaterialTransferApprFactory", "MaterialRegisterService", "RequestForMaterialAdditionalTimeFactory", "GenericAlertService", "stylesService", "ngGridService", function($rootScope, $scope, $q, $state, $filter, ngDialog, MaterialTransferApprFactory, MaterialRegisterService,RequestForMaterialAdditionalTimeFactory, GenericAlertService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.materialTransferReqApprTOs = [];
	$scope.materialDataMap = [];
	var editPendingApproval = [];
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
	
	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.approvalMaterialTransfer = [];
	var editApprovalMaterialTransfer = [];

	$scope.approvalType = "Pending For Approval";
	$scope.approvalTypeList = [ "Pending For Approval", "Approved", "Rejected", "All" ];

	$scope.approvalTransfer = {
		"fromDate": $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
		"toDate": $filter('date')((defaultToDate), "dd-MMM-yyyy"),
		"userType" : '1'
	};

	$scope.newRequestRowSelect = function(approval) {
		if (approval.selected) {
			editPendingApproval.push(approval);
			$scope.selectedValue = editPendingApproval;
		} else {
			editPendingApproval.pop(approval);
		}
	}
	$scope.$watch('fromDate', function() {
		if ($scope.fromDate != null) {
			$scope.fromDate = $filter('date')(($scope.approvalTransfer.fromDate), "dd-MMM-yyyy")
		}
	});

	$scope.$watch('toDate', function() {
		if ($scope.toDate != null) {
			$scope.toDate = $filter('date')(($scope.approvalTransfer.toDate), "dd-MMM-yyyy")
		}
	});

	$scope.getMaterialTransfers = function(onload) {
		var loginUser = true;
		if ($scope.approvalTransfer.userType == '2') {
			loginUser = false;
		}
		var req = {
			"status" : 1,
			"fromDate" : $filter('date')(($scope.approvalTransfer.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.approvalTransfer.toDate), "dd-MMM-yyyy"),
			"onload" : onload,
			"transType" : false,
			"loginUser" : loginUser,
			"apprStatus":$scope.approvalType
		};
		MaterialRegisterService.getMaterialTransfers(req).then(function(data) {
			console.log(data)
			$scope.materialTransferReqApprTOs = data.materialTransferReqApprTOs;
			$scope.gridOptions.data = angular.copy($scope.materialTransferReqApprTOs);
			console.log($scope.materialTransferReqApprTOs)
			console.log("onload")
			console.log(onload)
			if (onload) {
				$scope.materialDataMap = {
					"storeYardMap" : data.storeYardMap,
					"userProjMap" : data.userProjMap
				};
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  material transfer requests", 'Error');

		});

	}, $scope.getMaterialTransfers(true);

	$scope.createOrEditTransRequest = function(actionType, requestTO) {
		var resultData = MaterialTransferApprFactory.getMaterialTransferDetails(actionType, requestTO, $scope.materialDataMap);
		resultData.then(function(data) {
			$scope.materialTransferReqApprTOs = data.materialTransferReqApprTOs;
			$scope.getMaterialTransfers();
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching material request transfer details", 'Error');
		});
	},
	/*$scope.deleteApproval = function() {
		if (editPendingApproval.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.pendingApproval = [];
		} else {
			angular.forEach(editPendingApproval, function(value, key) {
				deleteIds.push(value.id);
			});
			var req = {
				"plantIds" : deleteIds,
				"status" : 2
			};
			MaterialRegisterService.deleteNewRequest(req).then(function(data) {
			});
			angular.forEach(editPendingApproval, function(value, key) {
				GenericAlertService.alertMessage('Plant Approval Detail(s) is/are  Deactivated successfully', 'Info');
				$scope.pendingApproval.splice($scope.pendingApproval.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Plant Approval Detail(s) is/are failed to Deactivate', "Error");
			});
			editPendingApproval = [];
			$scope.deleteIds = [];
		}
	},*/
	$scope.requestForAdditionalTime = function(actionType) {
		
		 if ( $scope.approvalType!='Pending For Approval' ) {
			  GenericAlertService.alertMessage("Please Select Pending For Approval", 'Warning');
			  return;
			  }
		 if (editPendingApproval == undefined || editPendingApproval <= 0  ) {
		  GenericAlertService.alertMessage("Please select record to get Request Additional Time details", 'Warning');
		  return;
		  }
		 else if (editPendingApproval.length > 1) {
			  GenericAlertService.alertMessage("Can't select more than  one record", 'Warning');
			  return;
			  }
		 
		var reqForAdditionalTimePopUp = RequestForMaterialAdditionalTimeFactory.getAdditionalTimeDetails($scope.materialTransferReqApprTOs,$scope.materialDataMap,$scope.selectedValue);
		reqForAdditionalTimePopUp.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting additional transfer details", 'Error');
		});
		$scope.getMaterialTransfers();
	}

	$scope.$on("GenerateProjectDocket", function() {
		$scope.getProjectIssueDockets();
	});
	$scope.$on("resetApprovalForMaterialTransfer", function() {
		$scope.materialProjDtlTOs = [];
		$scope.approvalType = "Pending For Approval";
		$scope.fromDate=approvefromDate;
		$scope.toDate=approvetoDate;
		$scope.userType='1';
		$scope.getMaterialTransfers(true);
	});

	$scope.reset = function(){
		$scope.approvalTransfer = {
			"fromDate": $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')((defaultToDate), "dd-MMM-yyyy"),
			"userType" : '1'
		};
		$scope.getMaterialTransfers(true);
	}
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
						{ field: 'reqDate', displayName: "Date of Request", headerTooltip: "Date of Request", groupingShowAggregationMenu: false },
						{ field: 'reqUserTO.empCode', displayName: "Requester", headerTooltip: "Requester", groupingShowAggregationMenu: false },
						{ field: 'apprUserTO.empCode', displayName: "Approver", headerTooltip: "Approver", groupingShowAggregationMenu: false },
						{ field: 'fromProjName', displayName: "Origin Project", headerTooltip: "Origin Project", groupingShowAggregationMenu: false },
						{ field: 'toProjName', displayName: "Destination Project", headerTooltip: "Destination Project", groupingShowAggregationMenu: false },
						{ field: 'reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID", groupingShowAggregationMenu: false },
						{ field: 'notifyCode', displayName: "Notification ID", headerTooltip: "Notification ID", groupingShowAggregationMenu: false },
						{ field: 'apprStatus', displayName: "Approval Status", headerTooltip: "Approval Status", groupingShowAggregationMenu: false },
						{ name: 'Download', displayName: "More Details", headerTooltip: "More Details", cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: 'template.html', groupingShowAggregationMenu: false },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MaterialRegister_TransferApproval");
				}
			});
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/requestapprovalhelp/approvalshelp/materialtransferapprovalhelp.html',
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