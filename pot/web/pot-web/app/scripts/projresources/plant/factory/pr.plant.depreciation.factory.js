'use strict';
app.factory('PlantDepreciationFactory',	["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, blockUI,$timeout, $rootScope,GenericAlertService,PlantRegisterService  ) {
		var plantDepreciationPopUp;
		var service = {};
		service.plantDepreciationPopUp = function(addDepreciationData,itemData) {
		var deferred = $q.defer();
		plantDepreciationPopUp = 	ngDialog.open({
			template : 'views/projresources/projplantreg/plantdepreciation/plantregdepreciationpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : ['$scope',function($scope){
				$scope.depreciation = addDepreciationData;
				
				$scope.userProjMap = itemData.userProjMap;
				$scope.projGenCurrencyLabelKeyTO = itemData.projGenCurrencyLabelKeyTO;
				$scope.projPlantClassMap = itemData.projPlantClassMap;
				$scope.plantClassMstrMap = itemData.plantClassMstrMap
						
						$scope.calcRemainingLife = function(depriciationObj) {
								depriciationObj.remainingLife = depriciationObj.estimateLife - depriciationObj.plantLogRecordsTO.endMeterReading;
								return depriciationObj.remainingLife;
						}
						
						$scope.calcRemaining = function(depriciationObj) {
							depriciationObj.remaining = depriciationObj.orginalValue - depriciationObj.depriciationValue;
							return depriciationObj.remaining;
					}
						$scope.saveDepreciationDetails = function(){
							
							var depriciationValue = 	(($scope.depreciation.orginalValue-$scope.depreciation.salvageValue)/($scope.depreciation.estimateLife*$scope.depreciation.endMeterReading));
							var remaining = ($scope.depreciation.orginalValue - ((($scope.depreciation.orginalValue-$scope.depreciation.salvageValue)/($scope.depreciation.estimateLife*$scope.depreciation.endMeterReading))));
							
							var remaininglife = $scope.depreciation.estimateLife - $scope.depreciation.endMeterReading;
							$scope.depreciation.depriciationValue  = depriciationValue;
							$scope.depreciation.remaining = remaining;
							$scope.depreciation.projGenId = $scope.projGenCurrencyLabelKeyTO.id;
							$scope.depreciation.remainingLife = remaininglife;
							
							var req={
									"plantDepriciationSalvageTO" : $scope.depreciation
						}
							
						blockUI.start();
							PlantRegisterService.savePlantDeprisiationRecords(req).then(function(data) {
								blockUI.stop();
								// var succMsg = GenericAlertService.alertMessageModal('Plant Deprisiation and Salvage Detail(s) is/are  '+data.message,data.status);
								var succMsg = GenericAlertService.alertMessageModal('Plant Depreciation and Salvage Detail(s) saved successfully',"Info");
								   succMsg.then(function() {
									   $scope.closeThisDialog();	
									   var returnPopObj = {
											"plantDepriciationSalvageTOs" : data.plantDepriciationSalvageTOs,
										     "userProjMap" : data.projectLibTO.userProjMap,
										     "projPlantClassMap":data.projectLibTO.projClassMap,
										     "projGenCurrencyLabelKeyTO":data.projGenCurrencyLabelKeyTO
										};
										  deferred.resolve(returnPopObj); 
								
							})
							},function(error){
								 blockUI.stop();
								GenericAlertService.alertMessage('Plant Depreciation and Salvage Detail(s) is/are Failed to Save ',"Error");
							});
						}
							
						
						} ]
		});
						return deferred.promise;
					}
		service.plantDepreciationOnLoad = function(actionType, itemData){
			var deferred = $q.defer();
			var addDepreciationData = null;
			
			if (actionType === 'Edit') {
				addDepreciationData = angular.copy(itemData.editDepreciation[0]);
			}
			service.plantDepreciationPopUp(addDepreciationData,itemData).then(function(data){
				var returnPopObj = {
					"plantDepriciationSalvageTOs" : data.plantDepriciationSalvageTOs,
					"userProjMap" : data.userProjMap,
					"projPlantClassMap":data.projPlantClassMap,
					"projGenCurrencyLabelKeyTO": data.projGenCurrencyLabelKeyTO
				};
				deferred.resolve(returnPopObj);
				
			});
			return deferred.promise;
		}
					return service;
				}]);
