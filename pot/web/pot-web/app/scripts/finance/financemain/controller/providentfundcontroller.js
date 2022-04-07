'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("providentfund", {

		url : '/providentfund',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/superprovidentfund.html',
				controller : 'ProvidentFundController'
			}
		}
	})
}]).controller("ProvidentFundController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "ProvidentFundTrailRunFactory", "CompanyListPopUpFactory", "GenericAlertService", function($scope, $q, $state, ngDialog, UserEPSProjectService,ProvidentFundTrailRunFactory, CompanyListPopUpFactory, GenericAlertService) {
	
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
	$scope.generateProvidentFundDetails = function(){
		var popupDetails = ProvidentFundTrailRunFactory.generatePopUpDetails();
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
			$scope.name = data.selectedRecord.cmpName;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');

		})
	},
	
	
	$scope.claims = ["Pending Internal Approval","Pending Owner Approval","project owner approval","all"];
	$scope.deductions = ["yes","no"];

}]);