'use strict';
app.factory('PlantDeploymentFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "GenericAlertService", "EpsProjectSelectFactory", "PlantRegisterService", "ProjectPlantClassificationFactory", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope, GenericAlertService, EpsProjectSelectFactory, PlantRegisterService, ProjectPlantClassificationFactory) {
	var service = {};
	service.plantDeploymentOnLoad = function(actionType, editDeployment, userProjectMap, projPlantMap, plantClassMstrMap, deMobStatus,plantRegProjTOs) {
		var deferred = $q.defer();
		var addDeploymentData = null;
		
		if (actionType === 'Add') {
		$scope.isDisabled = true;
			addDeploymentData = {
				"status" : 1,
				"projId" : null,
				"id" : null,
				"plantId" : $rootScope.selectedPlantData.id,
				"commissionDate" : null,
				"mobDate" : null,
				"anticipatedDeMobDate" : null,
				"deMobDate" : null,
				"odoMeter" : null,
				"deMobOdoMeter" : null,
				"deMobStatus" : null,
			
				"comments" : null,
				"deploymentId" : null,
				"latest" : null,
				"projPlantClassTypeId" : null,
				"plantAssestCode" : $rootScope.selectedPlantData.assertId
			};
		}
		if (actionType === 'Edit') {
			addDeploymentData = angular.copy(editDeployment[0]);
		}
		var resultData = service.openPopUp(addDeploymentData, userProjectMap, projPlantMap, plantClassMstrMap, deMobStatus,plantRegProjTOs);
		resultData.then(function(data) {
			deferred.resolve(data);
		});
		return deferred.promise;
	}, service.openPopUp = function(addDeploymentData, userProjectMap, projPlantMap, plantClassMstrMap, deMobStatus,plantRegProjTOs) {
		var deferred = $q.defer();
		var plantDeploymentPopUp = ngDialog.open({
			template : 'views/projresources/projplantreg/plantdeployment/plantregdeploymentpopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.commissionDate = false;
				$scope.addDeploymentData = addDeploymentData;
				for(let k=0;k<=plantRegProjTOs.length-1;k++){
					if(plantRegProjTOs[k].deMobDate != null){
						$scope.minDate= plantRegProjTOs[k].deMobDate
					}
				}
				
				$scope.userProjectMap = userProjectMap;
				$scope.projPlantMap = projPlantMap;
				$scope.plantClassMstrMap = plantClassMstrMap;

				$scope.odoMeterLength = 0;

				$scope.postDemobilisations = [];
				var statusCode = {}, postDemobilisationsArray = [];

				for( let id = 0; id < deMobStatus.length; id++ ){
					statusCode.id = id + 1;
					statusCode.code = deMobStatus[id];

					postDemobilisationsArray.push( statusCode );
					statusCode = {};
				}

				$scope.postDemobilisations = postDemobilisationsArray;
				if( $scope.addDeploymentData.postDeMobStatus === null ){
					$scope.addDeploymentData.postDeMobStatus = postDemobilisationsArray[0].code;
				}
				
				$scope.searchProject = {
					"projId" : addDeploymentData.projId
				};
				$scope.projPlantData = null;
				$scope.getProcureDetails =function(){
				var req = {
						"status" : 1,
						"projId" : $rootScope.selectedPlantData.projId,
						"plantId" : $rootScope.selectedPlantData.id
					};
				
					PlantRegisterService.getRegPlantProcureDeliveryDetails(req).then(function(data) {
					        $scope.maxDate =data.plantProjPODtlTO.plantDocketDtlTOs[0].endDate;
							$scope.plantProjPODtlTOs = data.plantProjPODtlTO ;
							if($rootScope.selectedPlantData.assetType =='New Plant' &&	$scope.addDeploymentData.deploymentId==1 ){
								$scope.addDeploymentData.odoMeter=$scope.plantProjPODtlTOs.plantDocketDtlTOs[0].odoMeter;
								return;
							}

							if(	$scope.addDeploymentData.deploymentId==2 ){
								$scope.addDeploymentData.odoMeter=plantRegProjTOs[1].deMobOdoMeter;
								return;
							}

							let odoMeterString = String( $scope.addDeploymentData.odoMeter ).trim();

							if( odoMeterString == "null" ){
								$scope.odoMeterLength = 0;
							}else{
								$scope.odoMeterLength = odoMeterString.length;
							}
							
						});
				}
				
				$scope.enabledeMobStatus = function() {
						$scope.isDisabled = false;
				}
						
				$scope.savePlantDeploymentRecords = function() {
					var deMobilizationDate = new Date($scope.addDeploymentData.deMobDate)
					var MobilizationDate = new Date($scope.addDeploymentData.mobDate)
					if ($scope.addDeploymentData.mobDate == null || $scope.addDeploymentData.mobDate == undefined) {
						GenericAlertService.alertMessage('Please enter mobilization date ', "Info");
						return;
					}
					
					if ($scope.addDeploymentData.id > 0 ) {
						var oldFromDate = new Date($scope.addDeploymentData.mobDate);
						var fromDate = new Date($scope.addDeploymentData.anticipatedDeMobDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;

				console.log("call from here")
					if ( $scope.addDeploymentData.anticipatedDeMobDate != null && days <= 0 && MobilizationDate > fromDate) {
							GenericAlertService.alertMessage("Demobilaization date must be Future date of Actual Mobilaization Date", 'Warning');
							return;
						}
					}
					if ($scope.addDeploymentData.id > 0 ) {
						var oldFromDate = new Date($scope.addDeploymentData.mobDate);
						var fromDate = new Date($scope.addDeploymentData.deMobDate);
						var dateDiff = new Date(fromDate - oldFromDate);
						var days = dateDiff/1000/60/60/24;
				
						console.log($scope.addDeploymentData.mobDate)
						if ( $scope.addDeploymentData.deMobDate != null && days <= 0 && MobilizationDate > fromDate) {
							GenericAlertService.alertMessage("Demobilaization date must be Future date of Actual Mobilaization Date", 'Warning');
							return;
						}
					}
					
					
					var req = {
						"status" : 1,
						"projId" : $rootScope.selectedPlantData.projId,
						"plantRegProjTO" : $scope.addDeploymentData
					};
					blockUI.start();
					PlantRegisterService.savePlantDeployment(req).then(function(data) {
						blockUI.stop();
						var returnPopObj = {
							"plantRegProjTOs" : data.plantRegProjTOs
						};
						let message = 'Plant deployment details saved successfully' ;
						if ($scope.addDeploymentData.projectChanged) {
							message = $rootScope.selectedPlantData.assertId + " is registered to "+ $scope.addDeploymentData.name + " Successfully";
						}
						var succMsg = GenericAlertService.alertMessageModal(message, 'Info');
						succMsg.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(returnPopObj);
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Error occured while saving the plant deployement details', "Error");
					});

				}, $scope.getUserProjects = function() {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function(userEPSProjData) {
						$scope.searchProject = userEPSProjData.searchProject;
						if ($scope.addDeploymentData.projId != $scope.searchProject.projId) {
							$scope.addDeploymentData.projectChanged = true;
						}
						$scope.addDeploymentData.projId = $scope.searchProject.projId;
						$scope.addDeploymentData.name = $scope.searchProject.projName;
						$scope.addDeploymentData.parentName = $scope.searchProject.parentName;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				}, $scope.getPlantDetails = function() {

					if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
						GenericAlertService.alertMessage("Please select EPS Project ", 'Info');
						return;
					}

					if ($scope.projPlantMap == null || $scope.projPlantMap == undefined) {
						$scope.projPlantMap = [];
					}

					var getPlantClassificationReq = {
						"status" : 1,
						"projId" : $scope.searchProject.projId
					};
					ProjectPlantClassificationFactory.getProjectPlantDetails(getPlantClassificationReq).then(function(data) {
						$scope.projPlantData = data.projectPlantTO;
						$scope.addDeploymentData.projPlantClassTypeId = $scope.projPlantData.id;
						if (addDeploymentData.id == null) {
							$scope.projPlantMap[$scope.projPlantData.id] = $scope.projPlantData.plantClassTO;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Plant Deployment Details", "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
