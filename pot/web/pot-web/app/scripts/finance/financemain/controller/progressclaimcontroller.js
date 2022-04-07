'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("generateprogress", {

		url : '/generateprogress',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/generateprogressclaim.html',
				controller : 'ProgressClaimController'
			}
		}
	})
}]).controller("ProgressClaimController", ["$scope", "$q", "$state", "ngDialog", "EmpRegisterService", "UserEPSProjectService", "GenerateInvoiceFactory", "ProjectCrewPopupService", "GenericAlertService", function($scope, $q, $state, ngDialog, EmpRegisterService, UserEPSProjectService, GenerateInvoiceFactory, ProjectCrewPopupService, GenericAlertService) {
	$scope.progressClaim = [];
	$scope.date = new Date();
	/*$scope.approvalList = [{
		id : 1,
		name : "Pending Internal Approval"
	}, {
		id : 2,
		name : "Pending Owner Approval"
	}, {
		id : 3,
		name : "project owner approval"
	}, {
		id : 4,
		name : "all"
	}];*/
	$scope.approvalList = ["Yes","No"];
	var service = {};
	$scope.getUserProjects = function() {
		$scope.activeFlag = 1;
		var userProjectSelection = UserEPSProjectService.epsProjectPopup();
		userProjectSelection.then(function(userEPSProjData) {
			$scope.searchProject = userEPSProjData.selectedProject;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.generateInvoice = function() {
		var popupDetails = GenerateInvoiceFactory.generatePopUpDetails();
		popupDetails.then(function(data) {
			$scope.progressClaim = data.invoiceTOs;
			// editData = [];
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		})
	}, $scope.getProjectOwner = function() {
		var ApproverSerivcePopup = [];
		ApproverSerivcePopup = ProjectCrewPopupService.approverDetailsList();
		ApproverSerivcePopup.then(function(data) {
			userLabelKeyTO.id = data.projApproverTO.userId;
			userLabelKeyTO.name = data.projApproverTO.firstName;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting Employee List Details", 'Error');
		});

	}, service.getProjectEmployeeDetails = function(req) {
		var deferred = $q.defer();
		var projectEmployeeDetailsPromise = EmpRegisterService.empRegisterOnLoad(req);
		projectEmployeeDetailsPromise.then(function(data) {
			var plantEmployeeDetails = [];
			plantEmployeeDetails = data.empRegisterDtlTOs;
			var plantEmployeePopUp = service.plantEmployeePopUp(plantEmployeeDetails);
			plantEmployeePopUp.then(function(data) {
				var returnPopObj = {
					"plantEmployeeTO" : data.plantEmployeeTO
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting ProgressClime Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting ProgressClime Details", "Error");
		});
		return deferred.promise;
	}


}]);