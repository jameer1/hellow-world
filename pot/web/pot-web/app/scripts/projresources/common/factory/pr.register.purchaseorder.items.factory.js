'use strict';
app.factory('RegisterPurchaseOrderItemsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, 
														GenericAlertService, PlantRegisterService) {
	var plantPOItemPopUp;
	var service = {};
	service.plantPOItemPopUp = function(labelKeyTOList) {
		var deferred = $q.defer();
		plantPOItemPopUp = ngDialog.open({
			template : 'views/projresources/common/registerpurchaseorderitemslist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.labelKeyTOList = labelKeyTOList;
				$scope.convertToNumber = function(number) {
					if (number)
						return parseInt(number);
					return 0;
				}
				$scope.selectRecord = function(record, doNotSelect) {
					if (!doNotSelect) {
						var returnRecord = {
							"selectedRecord" : record
						};
						deferred.resolve(returnRecord);
						$scope.closeThisDialog();
					}
				}
			} ]

		});
		return deferred.promise;
	},

	service.getPOItemDetails = function(req) {
		var deferred = $q.defer();
		var poItemDetailsPromise = PlantRegisterService.getPOItemDetails(req);
		poItemDetailsPromise.then(function(data) {
			
			var plantPOItemPopUp = service.plantPOItemPopUp(data.labelKeyTOs);
			plantPOItemPopUp.then(function(data) {
				var returnPopObj = {
					"selectedRecord" : data.selectedRecord
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Purchase Order Item Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Purchase Order Item Details", "Error");
		});
		return deferred.promise;
	}

	return service;
}]);
