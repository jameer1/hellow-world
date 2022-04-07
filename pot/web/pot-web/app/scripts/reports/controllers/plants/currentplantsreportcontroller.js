'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantlist", {
		url: '/plantlist',
		data: {
			plant: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/plants/currentplantsreport.html',
				controller: 'CurrentPlantsReportController'
			}
		}
	})
}]).controller("CurrentPlantsReportController", ["$rootScope", "$scope", "$q", "$state", "ProjCostCodeService", "ManpowerReportService", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "PlantReportService", "CompanyMultiSelectFactory", "stylesService", "ngGridService", function ($rootScope, $scope, $q, $state, ProjCostCodeService, ManpowerReportService,
	ngDialog, GenericAlertService, EpsProjectMultiSelectFactory, PlantReportService, CompanyMultiSelectFactory, stylesService, ngGridService) {
	$scope.stylesSvc = stylesService;
	$scope.plantDetails = [];
	$scope.selectedProjIds = [];
	$scope.selectedCompanyIds = [];
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
			$scope.plantDetails = [];
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};
	$scope.getCompanyList = function () {
		var companyReq = {
			"status": 1
		}
		var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
		companyPopUp.then(function (data) {
			$scope.companyNameDisplay = data.selectedCompanies.companyName;
			$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
			$scope.plantDetails = [];
		})
	};
	$scope.getPlantDetails = function () {
		if ($scope.selectedProjIds.length <= 0) {
			GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
			return;
		}
		if ($scope.selectedCompanyIds.length <= 0) {
			GenericAlertService.alertMessage("Please select companies to fetch report", 'Warning');
			return;
		}
		var req = {
			"projIds": $scope.selectedProjIds,
			"cmpIds": $scope.selectedCompanyIds,
		}
		PlantReportService.getCurrentActivePlants(req).then(function (data) {
			$scope.plantDetails = data;
			$scope.gridOptions.data = $scope.plantDetails;
			if ($scope.plantDetails.length <= 0) {
				GenericAlertService.alertMessage("Current Plant List not available for the search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Plant Details", 'Error');
		});
	};
	$scope.resetReportDetails = function () {
		$scope.plantDetails = [];
		$scope.searchProject = {};
		$scope.selectedProjIds = [];
		$scope.selectedCompanyIds = [];
		$scope.companyNameDisplay = null;
		if ($scope.gridOptions) {
			$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
			$scope.gridOptions.data = [];
		}
	};
	$scope.$watch(function () { return stylesService.finishedStyling; },
		function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'displayNamesMap.epsName', displayName: "EPS", headerTooltip: 'EPS Name' },
					{ field: 'displayNamesMap.projName', displayName: "Project", headerTooltip: 'Project Name' },
					{ field: 'displayNamesMap.plantAssertId', displayName: "Plant ID", headerTooltip: 'Plant ID' },
					{ field: 'displayNamesMap.plantRegNum', displayName: "Plant Reg Num", headerTooltip: 'Plant Reg Number' },
					{ field: 'displayNamesMap.plantName', displayName: "Plant Name", headerTooltip: 'Plant Name' },
					{ field: 'displayNamesMap.plantClassName', displayName: "Classification", headerTooltip: 'Plant Classification' },
					{ field: 'displayNamesMap.plantModel', displayName: "Model", headerTooltip: 'Plant Model' },
					{ field: 'displayNamesMap.plantManfacture', displayName: "Make", headerTooltip: 'Plant Manfacture' },
					{ field: 'displayNamesMap.cmpName', displayName: "Supplier Name", headerTooltip: 'Supplier Name' },
					{ field: 'displayNamesMap.plantMobDate', displayName: "Mob Date", headerTooltip: 'Mobilisation Date' },
					{ field: 'displayNamesMap.plantDeMobDate', displayName: "DeMob Date", headerTooltip: 'Expected DeMobilisation Date' },
					{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit of Measure", headerTooltip: 'Unit of Measure' },
					{ field: 'displayNamesMap.currency', displayName: "Currency", headerTooltip: 'Currency' },
					{ field: 'displayNamesMap.unitOfRate', displayName: "Unit of Rate", headerTooltip: 'Unit of Rate' }
				];
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [],"Reports_Plant_Current_Plant_List");
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
				template: 'views/help&tutorials/reportshelp/planthelp/currentplantshelp.html',
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
