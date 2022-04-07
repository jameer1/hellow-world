'use strict';

app.factory('CreateWeatherPopupFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "WeatherService", "GenericAlertService", function(ngDialog, $q, blockUI,$filter, $timeout, $rootScope, WeatherService, GenericAlertService) {
	var weatherPopup = [];
	var weatherService = {};
	weatherService.weatherDetailsList = function(projId) {
		var deferred = $q.defer();
		weatherPopup = ngDialog.open({
			template : 'views/timemanagement/workdairy/createworkdairy/weatherclass.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom5',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.weathers = [];
				var weatherReq = {
					"status" : 1,
					"clientId" : $rootScope.clientId
				};
				blockUI.start();
				WeatherService.getWeathers(weatherReq).then(function(data) {
					blockUI.stop();
					$scope.weathers = data.weatherTOs;
				}, function(error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while getting weather Details", "Error");
				});
				$scope.weathertpopup = function(weatherTO) {
					var returnPopObj = {
						"weatherTO" : weatherTO
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}
	return weatherService;

}]);