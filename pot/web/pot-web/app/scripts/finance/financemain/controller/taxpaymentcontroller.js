'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("taxpayments", {

		url : '/taxpayments',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/taxpaymentcreditrun.html',
				controller : 'TaxPaymentController'
			}
		}
	})
}]).controller("TaxPaymentController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "TaxPaymentTrailRunFactory", "GenericAlertService", function($scope, $q, $state, ngDialog, UserEPSProjectService,TaxPaymentTrailRunFactory, GenericAlertService) {
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
	$scope.generateTaxPaymentDetails = function(){
		var popupDetails = TaxPaymentTrailRunFactory.generatePopUpDetails();
		popupDetails.then(function(data){
			
		},function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		
	})
	},
	$scope.claims = ["Pending Internal Approval","Pending Owner Approval","project owner approval","all"];
	$scope.deductions = ["yes","no"];

}]);