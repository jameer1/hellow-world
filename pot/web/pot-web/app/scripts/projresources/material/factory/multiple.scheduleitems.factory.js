'use strict';
app.factory('MultipleScheduleItemsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PlantRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PlantRegisterService) {
	var scheduleItemPopUp;
	var service = {};
	service.scheduleItemPopUp = function(scheduleItemList) {
		var deferred = $q.defer();
		scheduleItemPopUp = ngDialog.open({
			template : 'views/projresources/projmaterialreg/deliverysuply/multiple.scheduleitems.popup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.scheduleItemList = scheduleItemList;
				var selectedscheduleItem = [];
				$scope.scheduleItemList = [];

				$scope.scheduleItemList = [ {
					code : '',
					name : '',
					qty : '',
					unitOfRate : ''
				}, {
					code : '',
					name : '',
					qty : '',
					unitOfRate : ''
				} ]

				$scope.savescheduleItem = function() {
					var req = {
						"slectedScheduleItemList" : slectedScheduleItemList,

					};
					PlantRegisterService.savescheduleItem(req).then(function(data) {
						var message = GenericAlertService.alertMessage('Schedule item  details ' + data.message, "Info");
						var returnPopObj = {
							"slectedScheduleItemList" : data.slectedScheduleItemList,
						}
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();
					});
				}

				$scope.slectedScheduleItemList = function(scheduleItem) {
					if (scheduleItem.select) {
						selectedscheduleItem.push(scheduleItem);
					} else {
						selectedscheduleItem.pop(scheduleItem);
					}
				}

			} ]

		});
		return deferred.promise;
	},

	service.getPOItemsByProject = function(req) {
		var deferred = $q.defer();
		var poItemDetailsPromise = PlantRegisterService.getPOItemsByProject(req);
		poItemDetailsPromise.then(function(data) {
			var scheduleItemPopUp = service.scheduleItemPopUp(data.labelKeyTOs);
			scheduleItemPopUp.then(function(data) {
				var returnPopObj = {
					"slectedScheduleItemList" : data.slectedScheduleItemList
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting purchase order schedule items", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting purchase order schedule items", 'Error');
		});
		return deferred.promise;
	}

	return service;
}]);
