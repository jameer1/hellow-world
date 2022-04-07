'use strict';
app.factory('PlantUtilizationFactory',	["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, blockUI,$filter, $timeout, $rootScope,GenericAlertService,PlantRegisterService ) {
		var plantUtilizationPopUp;
		var service = {};
		service.plantUtilizationPopUp = function(actionType,editUtilization,projId) {
		var deferred = $q.defer();
		plantUtilizationPopUp = 	ngDialog.open({
			template : 'views/projresources/projplantreg/plantutilization/plantutilizationpopup.html',
			closeByDocument : false,
			showClose : false,
			controller : ['$scope',function($scope){
				$scope.utilizationData =[];
				
				
						$scope.action = actionType;
						
						if (actionType === 'Edit') {
							$scope.addUtilizationData =[];
							$scope.addUtilizationData = angular.copy(editUtilization);
							editUtilization=[];
						}
						
						$scope.save = function(){
							var req={
						}
							blockUI.start();
							PlantRegisterService.savePlantRegisters(req).then(function(data) {
								blockUI.stop();
								GenericAlertService.alertMessage('Plant Utilization Detail(s) is/are  '+data.message,data.status);
							},function(error){
								blockUI.stop();
								GenericAlertService.alertMessage('Plant Utilization Detail(s)  is/are Failed to Save ',"Error");
							});
							ngDialog.close();
						}
							
						
						
						} ]
		});
						return deferred.promise;
					}
					return service;
				}]);
