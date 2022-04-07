'use strict';
app.factory('TaxCodeTypePopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "TaxTypeService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI,
	 TaxTypeService, GenericAlertService, generalservice) {
	var taxcodetypePopup;
	var selectedNonRegularPay = [];
	var service = {};

	service.taxcodetypeDetails = function(taxId, editTaxCodeType, existingCodeTypesMap) {
		var deferred = $q.defer();
		const codeTypes = new Array();
		for (let obj of generalservice.financeCodeTypes) {
			codeTypes.push({ 'name': obj });
		}
		var taxCodeSerivcePopup = service.openTaxCodePoupup(codeTypes, generalservice.taxTypes, taxId, existingCodeTypesMap);
		taxCodeSerivcePopup.then(function (data) {

			var resultData = GenericAlertService.alertMessageModal('Tax Code Country provision detail ' + data.message, "Info");
			resultData.then(function () {
				deferred.resolve(data);
			});

		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
		});

		return deferred.promise;

	}, service.openTaxCodePoupup = function(codeTypeMstrTOs, codeTypes, taxId, existingCodeTypesMap) {
		var deferred = $q.defer();
		ngDialog.open({
			template : 'views/finance/taxcodetypes/taxcodetypepopup.html',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.existingCodeTypesMap = existingCodeTypesMap;
				var selectedTaxTypes = [];
				$scope.taxcodetypeDetails = []
				for (let type of codeTypeMstrTOs) {
					if (existingCodeTypesMap.indexOf(type.name) === -1) {
						$scope.taxcodetypeDetails.push(type);
					}
				}
				$scope.codeTypes = codeTypes;
				$scope.selectedTaxTypes = function(taxcodetype) {
					if (taxcodetype.select) {
						selectedTaxTypes.push(taxcodetype);
					} else {
						selectedTaxTypes.pop(taxcodetype);
					}
				}, $scope.saveTaxCodeType = function() {
					var saveReqObj = [];
					angular.forEach(selectedTaxTypes, function(value, key) {
						if(value.type==null||value.type==undefined){
							GenericAlertService.alertMessage('Please select Tax Type' , "Warning");
							forEach.break();
							return;
						}
						
						saveReqObj.push({
							"taxCountryProvsionId": taxId,
							"financeCodeType": value.name,
							"taxType": value.type,
							"status": 1
						})
					});

					var req = {
						"codeTypesTOs" : saveReqObj,
						"taxId" : taxId
					};
					blockUI.start();
					TaxTypeService.saveCodeTypes(req).then(function(data) {
						blockUI.stop();
						var resultData = GenericAlertService.alertMessageModal('Tax Code Country provision detail ' + data.message, "Info");
						resultData.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					},function(error) {
						blockUI.stop();
					});

				}
			} ]
		});
		return deferred.promise;

	}
	return service;
}]);
