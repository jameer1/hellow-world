'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("resourcecurves", {
		url : '/resourcecurves',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/tools/resourcecurves/resourcecurve.html',
				controller : 'ResourceCurveController'
			}
		}
	})
}]).controller("ResourceCurveController", ["$scope", "$q", "$state", "ngDialog", "$rootScope", "ResourceCurveService", "GenericAlertService", "ResourceCurveFactory", function($scope, $q, $state, ngDialog, $rootScope, ResourceCurveService, GenericAlertService, ResourceCurveFactory) {

	var resourceDetails = [];
	var curveDetails = [];
	var editResource = [];
	$scope.resourcecurves = [];
	$scope.getData = function() {
		var getResourceReq = {
			"status" : 1,
			"clientId" : null
		};
		ResourceCurveService.getResourceCurves(getResourceReq).then(function(data) {
			$scope.resourcecurves = data.projResourceCurveTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting resourcecurves Details", "Error");
		});
	}, $scope.resourcerowselect = function(resource) {
		if (resource.selected) {
			editResource.push(resource);
		} else {
			editResource.pop(resource);
		}
	}, $scope.addResourceCurve = function(actionType) {
		if (actionType == 'Edit' && editResource <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		} else {
			var resourceDetails = ResourceCurveFactory.resourcePopUpDetails(actionType, editResource);
			resourceDetails.then(function(data) {
				$scope.resourcecurves = data.projResourceCurveTOs;
				editResource = [];
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
			})
		}
	}, $scope.initGraph = function() {
		$scope.data = [ $scope.data, $scope.cummulativeData ];
		$scope.labels = [ "0%-10%", "10%-20%", "20%-30%", "30%-40%", "40%-50%", "50%-60%", "60%-70%", "70%-80%", "80%-90%", "90%-100%" ];
		$scope.datasetOverride = [ {
			label : 'bar chart',
			type : 'bar',
			id : 'y-axis-1',
		}, {
			type : 'line',
			label : 'line chart',
			fill : false,
			borderColor : '#0000ff',
			backgroundColor : '#0000ff',
			pointBorderColor : '#0000ff',
			pointBackgroundColor : '#0000ff',
			pointHoverBackgroundColor : '#0000ff',
			pointHoverBorderColor : '#0000ff',
			yAxisID : 'y-axis-2'
		} ];
		$scope.colors = [ '#00ff00', '#0000ff' ];
		$scope.options = {
			indexLabel : "low",
			scales : {
				xAxes : [ {
					stacked : true,
				} ],
				yAxes : [ {
					type : "linear",
					display : true,
					position : "left",
					id : "y-axis-1",
					labels : {
						show : true,
					}
				}, {
					type : "linear",
					display : true,
					position : "right",
					id : "y-axis-2",
					labels : {
						show : true,
					}
				} ]
			}
		};
	}, $scope.getResourceCurve = function(actionType, resource) {
		$scope.data = [];
		var valuesData = [];
		$scope.setHeader = resource.curveType;

		for (var i = 1; i <=10; i++) {
			$scope.data.push(resource["range" + i]);
		}

		var count = 0;
		$scope.cummulativeData = [];
		angular.forEach($scope.data, function(value, key) {
			count = count + value;
			$scope.cummulativeData.push(count);
		});
		Chart.scaleService.updateScaleDefaults('linear', {
			ticks : {
				beginAtZero : true
			}
		})
		$scope.initGraph();
	}
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/resourcecurvehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);