'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialtransferrequest", {
		url : '/materialtransferrequest',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/reqmaterialtransfer/materialregtrasnrequest.html',
				controller : 'MaterialTransferReqApprController'
			}
		}
	}).state("materialtransferrequest1", {
		url : '/materialtransferrequest1',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projmaterialreg/reqmaterialtransfer/materialreqtransfer.html',
				controller : 'MaterialTransferReqApprController'
			}
		}
	})
}]).controller("MaterialTransferReqApprController", ["$rootScope", "$scope", "$q", "$state", "$filter", "ngDialog", "MaterialTransferReqFactory", "MaterialRegisterService", "GenericAlertService", function($rootScope, $scope, $q, $state, $filter, ngDialog, MaterialTransferReqFactory, MaterialRegisterService, GenericAlertService) {

	$scope.materialTransferReqApprTOs = [];
	$scope.materialDataMap = [];

	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
	
	$scope.requestTransfer = {
		"userType" : '1',
		"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
		"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
	}

var reqfromDate = $scope.fromDate;
var reqtoDate = $scope.toDate;

	$scope.$watch('fromDate', function() {
		if ($scope.fromDate != null) {
			$scope.fromDate = $filter('date')(($scope.fromDate), "dd-MMM-yyyy")
		}
	});

	$scope.$watch('toDate', function() {
		if ($scope.toDate != null) {
			$scope.toDate = $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		}
	});
	

	
	$scope.getMaterialTransfers = function(onload) {
		var loginUser = true;
		if ($scope.requestTransfer.userType == '2') {
			loginUser = false;
		}
		var req = {
			"status" : 1,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
			"onload" : onload,
			"transType" : true,
			"loginUser" : loginUser
		};
		MaterialRegisterService.getMaterialTransfers(req).then(function(data) {
			
			$scope.materialTransferReqApprTOs = data.materialTransferReqApprTOs;
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
		var resultData = MaterialTransferReqFactory.getMaterialTransferDetails(actionType, requestTO, $scope.materialDataMap);
		resultData.then(function(data) {
			$scope.materialTransferReqApprTOs = data.materialTransferReqApprTOs;
			$scope.getMaterialTransfers();
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching material request transfer details", 'Error');
		});
	},

	
	$scope.$on("resetRequestForMaterialTransfer", function() {
		$scope.materialProjDtlTOs = [];
		$scope.fromDate = reqfromDate;
		$scope.toDate = reqtoDate;
		$scope.requestTransfer.userType="1";
		$scope.getMaterialTransfers(true);
		
		
	});

	/*$scope.reset = function(){
		$scope.requestTransfer = {
			"userType" : '1',
			"fromDate" : null,
			"toDate" :null
		}
		$scope.getMaterialTransfers(true);
	}
	*/
	$scope.reset = function(){
		$scope.requestTransfer = {
      		"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy"),
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
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/requestapprovalhelp/requesthelp/materialtransferrequesthelp.html',
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