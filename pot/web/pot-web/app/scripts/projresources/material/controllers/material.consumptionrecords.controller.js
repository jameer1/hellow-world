'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("materialconsumptionrecords", {
		url : '/materialconsumptionrecords',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projmaterialreg/datewiseconsumption/datewiseconsumptionquantity.html',
				controller : 'MaterialConsumptionRecordsController'
			}
		}
	})
}]).controller("MaterialConsumptionRecordsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "MaterialRegisterService", "GenericAlertService", function($rootScope, $scope, $q, $state, ngDialog, MaterialRegisterService, GenericAlertService) {

	$scope.dateWiseConsumptionDetails = [];
	$scope.companyMap = [];
	$scope.classificationMap = [];
	$scope.userProjectMap = [];
	
	$scope.getConsumptionRecords = function() {
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
		MaterialRegisterService.getMaterialProjConsumption(req).then(function(data) {
			$scope.dateWiseConsumptionDetails = data.labelKeyTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Material Consumption Details", "Error");
		});
	}
	
	$scope.$on("resetConsumptionRecords", function() {
		$scope.dateWiseConsumptionDetails = [];
		$scope.getConsumptionRecords();
	});

	$scope.$on("searchConsumptionRecords", function() {
		$scope.getConsumptionRecords();
	});

}]);