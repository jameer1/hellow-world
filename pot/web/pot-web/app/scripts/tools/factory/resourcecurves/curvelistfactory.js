/*'use strict';
app.factory('ResourceCurvePopupFactory', function(ngDialog, $q, GenericAlertService) {
	var curvePopUp;
	var service = {};
	service.curveListDetails = function(actionType, resource) {
		var deferred = $q.defer();
		curvePopUp = ngDialog.open({
			template : 'views/tools/resourcecurves/curvelist.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.labels = [ "0%-10%", "10%-20%", "20%-30%", "30%-40%", "40%-50%", "50%-60%", "60%-70%", "70%-80%", "80%-90%", "90%-100%" ];
				$scope.colors = ['#45b7cd', '#ff6384'];
				$scope.data = [];
				var valuesData = [];

				$scope.setHeader = resource.curveType;
				angular.forEach(resource, function(value) {
					valuesData.push(value);

				});

				angular.forEach(valuesData, function(value, index) {
					if (index > 11 && index < 22) {
						$scope.data.push(value);
					}
				});
				var count = 0;
				$scope.cummulativeData = [];
				angular.forEach($scope.data, function(value, key) {

					count = count + value;
					$scope.cummulativeData.push(count);
				});

				$scope.data = [ $scope.data, $scope.cummulativeData ];
				$scope.datasetOverride = [ {

					type : 'bar'
				}, {
					type : 'line',
				    
				} ];

				Chart.scaleService.updateScaleDefaults('linear', {
					ticks : {
						min : 0,
						max : 100
					}
				})
			} ]
		});
		return deferred.promise;
	}
	return service;
});*/