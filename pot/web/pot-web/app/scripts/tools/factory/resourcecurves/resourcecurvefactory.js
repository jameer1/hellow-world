'use strict';
app.factory('ResourceCurveFactory', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "ResourceCurveService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout,blockUI, ResourceCurveService, GenericAlertService) {
	var resourcePopUp;
	var service = {};
	service.resourcePopUpDetails = function(actionType, editResource) {
		var deferred = $q.defer();
		resourcePopUp = ngDialog.open({
			template : 'views/tools/resourcecurves/resourcecurvepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var selectedResourceCurve = [];
				$scope.resourceList = [];

				if (actionType === 'Add') {
					$scope.resourceList.push({
						"selected" : false,
						"clientId" : null,
						"catg":"Customised",
						"status" : '1',
						"range1" : null,
						"range2" : null,
						"range3" : null,
						"range4" : null,
						"range5" : null,
						"range6" : null,
						"range7" : null,
						"range8" : null,
						"range9" : null,
						"range10" : null,
						"total":null
					});
				} else {
					$scope.resourceList = angular.copy(editResource);
					editResource = [];
				}

				$scope.addResourceRows = function() {
					$scope.resourceList.push({
						"selected" : false,
						"clientId" : null,
						"catg":"Customised",
						"status" : '1',
						"range1" : null,
						"range2" : null,
						"range3" : null,
						"range4" : null,
						"range5" : null,
						"range6" : null,
						"range7" : null,
						"range8" : null,
						"range9" : null,
						"range10" : null,
						"total":null
					});
				},

				$scope.resourcePopUpRowSelect = function(resource) {
					if (resource.selected) {
						selectedResourceCurve.push(resource);
					} else {
						selectedResourceCurve.pop(resource);
					}
				},

				$scope.deleteResourceRows = function() {
					if (selectedResourceCurve.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					}
					if (selectedResourceCurve.length < $scope.resourceList.length) {
						angular.forEach(selectedResourceCurve, function(value, key) {
							$scope.resourceList.splice($scope.resourceList.indexOf(value), 1);
							GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
						});
						selectedResourceCurve = [];
					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

				$scope.updateTotal = function(resource) {
					resource.total = 0;
					resource.total = parseFloat(resource.range1 || 0) + parseFloat(resource.range2 || 0)
					+ parseFloat(resource.range3 || 0)+ parseFloat(resource.range4 || 0) + parseFloat(resource.range5 || 0)
					+ parseFloat(resource.range6 || 0)+ parseFloat(resource.range7 || 0) + parseFloat(resource.range8 || 0)
					+ parseFloat(resource.range9 || 0)+ parseFloat(resource.range10 || 0);

				},
				$scope.saveResourceCurves = function() {
					var req = {
						"resourceCurveTOs" : $scope.resourceList
					}
					var results = [];
					blockUI.start();
					ResourceCurveService.saveResourceCurves(req).then(function(data) {
						blockUI.stop();
						results = data.projResourceCurveTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Resource Curves Details ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Resource Curves Details saved successfully',"Info");
						succMsg.then(function(data) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"projResourceCurveTOs" : results
							};
							deferred.resolve(returnPopObj);
						});

					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('ResourceCurves  Details are Failed to Save ', "Error");
					});
					$scope.closeThisDialog();

				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
