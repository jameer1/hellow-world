'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("statusoninvoice", {

		url : '/statusoninvoice',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/statusoninvoicetoowner.html',
				controller : 'StatusInvoiceController'
			}
		}
	})
}]).controller("StatusInvoiceController", ["$scope", "$q", "$state", "ngDialog", "UserEPSProjectService", "GenerateInvoiceFactory", "GenericAlertService", function($scope, $q, $state, ngDialog, UserEPSProjectService,GenerateInvoiceFactory, GenericAlertService) {
	$scope.getUserProjects = function() {
		$scope.activeFlag = 1;
		var userProjectSelection = UserEPSProjectService.epsProjectPopup();
		userProjectSelection.then(function(userEPSProjData) {
			$scope.searchProject = userEPSProjData.selectedProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.generateInvoice = function(){
		var popupDetails = GenerateInvoiceFactory.generatePopUpDetails();
		popupDetails.then(function(data){
			
		},function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		
	})
	},
	$scope.claims = ["Pending Internal Approval","Pending Owner Approval","project owner approval","all"];
	$scope.deductions = ["yes","no"];

}]);