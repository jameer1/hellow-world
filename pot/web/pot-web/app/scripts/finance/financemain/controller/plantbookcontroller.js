'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("plantbook", {

		url : '/plantbook',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/plantbookvalue.html',
				controller : 'PlantBookController'
			}
		}
	})
}]).controller("PlantBookController", ["$scope", "$q", "$state", "ngDialog", "CompanyListPopUpFactory", "UserEPSProjectService", "PlantBookTrailRunFactory", "PlantClassPopUpFactory", "GenericAlertService", function($scope, $q, $state, ngDialog,CompanyListPopUpFactory,  UserEPSProjectService,PlantBookTrailRunFactory, PlantClassPopUpFactory, GenericAlertService) {
	$scope.date = new Date();
	$scope.getUserProjects = function() {
		$scope.activeFlag = 1;
		var userProjectSelection = UserEPSProjectService.epsProjectPopup();
		userProjectSelection.then(function(userEPSProjData) {
			$scope.searchProject = userEPSProjData.selectedProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.generatePlantDetails = function(){
		var popupDetails = PlantBookTrailRunFactory.generatePopUpDetails();
		popupDetails.then(function(data){
			
		},function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		
	})
	},
	
	$scope.getCompanies = function(company) {
		var companyListService = {};
		var onLoadReq = {
			"status" : 1
		};
		var companyListService = CompanyListPopUpFactory.getCompanies(onLoadReq);
		companyListService.then(function(data) {
			$scope.companyName = data.selectedRecord.cmpName;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');

		})

	},
	
	$scope.getPlantClass = function() {
		var plantClassService = {};
		var plantReq = {
			
			"status" : "1"
		};
		var plantClassService = PlantClassPopUpFactory.getPlants(plantReq);
		plantClassService.then(function(data) {
			$scope.plantName = data.selectedRecord.name;
			$scope.procurePlant = data.selectedRecord.measureUnitTO.procurementTO;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');

		})

	},
	
	$scope.claims = ["Pending Internal Approval","Pending Owner Approval","project owner approval","all"];
	$scope.deductions = ["yes","no"];

}]);