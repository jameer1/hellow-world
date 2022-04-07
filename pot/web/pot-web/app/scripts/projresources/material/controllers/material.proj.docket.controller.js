'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialgeneratedocket", {
		url : '/materialgeneratedocket',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/projdocket/materialprojdockets.html',
				controller : 'MaterialProjDocketController'
			}
		}
	})
}]).controller("MaterialProjDocketController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "ProjDocketGenerateFactory", "MaterialRegisterService","stylesService", "ngGridService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, ProjDocketGenerateFactory, MaterialRegisterService, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.materialProjDocketTOs = [];
	var editmaterialProjDocketTOs = [];
	$scope.companyMap = [];
	$scope.classificationMap = [];
	$scope.userProjectMap = [];

	$scope.getMaterialProjDockets = function() {
		if($rootScope.materialSearchProject.selectedProject!=null){
			var req = {
				"status" : 1,
				"projList" : [ $rootScope.materialSearchProject.selectedProject.projId ],
				"fromDate" : $rootScope.materialSearchProject.fromDate,
				"toDate" : $rootScope.materialSearchProject.toDate
			};
			}
			else{
				var req = {
						"status" : 1,
						"projId" : [ $rootScope.materialSearchProject.selectedProject ],
						"fromDate" : $rootScope.materialSearchProject.fromDate,
						"toDate" : $rootScope.materialSearchProject.toDate
					};
			}
		MaterialRegisterService.getProjectIssueDockets(req).then(function(data) {
			$scope.materialProjDocketTOs = data.materialProjDocketTOs;
			$scope.gridOptions.data = angular.copy($scope.materialProjDocketTOs);
		});

	}

	$scope.getMaterialProjDockets();

	$scope.$on("resetProjectDocket", function() {
		$scope.materialProjDocketTOs = [];
		$scope.getMaterialProjDockets();
	});

	$scope.$on("searchProjectDocket", function() {
		$scope.getMaterialProjDockets();
	});
	
	
	$scope.resetProjectDocket = function() {
		$scope.materialProjDocketTOs = [];
	}

	$scope.projDocketDetails = function(actionType, materialProjDocketTO) {
		var itemData = {
			"materialProjDocketTO" : materialProjDocketTO,
			"companyMap" : $scope.companyMap,
			"classificationMap" : $scope.classificationMap,
			"userProjectMap" : $scope.userProjectMap,
			"empClassificationMap" : $scope.empClassificationMap
		};
		var generatedocket = ProjDocketGenerateFactory.projDocketDetails(actionType, itemData);
		generatedocket.then(function(data) {
			$scope.materialProjDocketTOs = data.materialProjDocketTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching material issue docket details", 'Error');
		});
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'projdocketDate', displayName: "Docket Date", headerTooltip: "Docket Date"},
						{ field: 'projdocketNum', displayName: "Project Docket #", headerTooltip: "Project Docket #", },
						{ field: 'fromProjLabelkeyTO.name', displayName: "Origin Project", headerTooltip: "Origin Project"},
						{ field: 'toProjLabelkeyTO.name', displayName: "Destination Project", headerTooltip: "Destination Project",},
						{ field: 'issuedByLabelkeyTO.code', displayName: "Materials Issued By", headerTooltip: "Materials Issued By"},
						{ field: 'receivedByLabelkeyTO.code', displayName: "Materials Received By", headerTooltip: "Materials Received By"},
						{ field: 'apprStatus', displayName: "Docket Status", headerTooltip: "Docket Status"},
						{ name: 'Download', displayName: "View Docket", headerTooltip: "View Docket", cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: 'template.html'},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MaterialRegister_ProjDocket");
				}
			});
}]);