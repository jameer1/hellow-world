'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("weather", {
		url : '/weather',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/weatherclass/weatherclass.html',
				controller : 'WeatherController'
			}
		}
	})
}]).controller("WeatherController", ["$rootScope", "$q", "$scope","uiGridGroupingConstants", "uiGridConstants", "$state", "blockUI", "ngDialog", "WeatherService",'stylesService', 'ngGridService', "GenericAlertService", "utilservice", function($rootScope, $q, $scope,uiGridGroupingConstants, uiGridConstants, $state,blockUI, ngDialog, WeatherService,stylesService, ngGridService, GenericAlertService, utilservice) {
	$scope.stylesSvc = stylesService;
	$scope.weather = {};
	$scope.weatherClassification = [];
	$scope.deletecodes = [];
	$scope.sortType = "code"
	var editweatherClassification = [];
	$scope.uniqueCodeMap = [];
	var deferred = $q.defer();
	$scope.weatherReq = {
		"weatherCode" : "",
		"weatherName" : null,
		"status" : "1"
	}
	$scope.weatherSearch = function(click) {
		$scope.weatherReq.weatherCode = $scope.weatherReq.weatherCode.toUpperCase();
		WeatherService.getWeathers($scope.weatherReq).then(function(data) {
			editweatherClassification = [];
			// $scope.activeFlag = 0;
			$scope.weatherClassification = data.weatherTOs;
			// var copyWeather = angular.copy($scope.weatherClassification).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{
			// item.status = 'Inactive'}
			// return item;})
			$scope.gridOptions.data = $scope.weatherClassification;
			/*$scope.gridOptions.data = angular.copy($scope.weatherClassification);*/
			if(click=='click'){
				if ($scope.weatherClassification.length <= 0) {
					GenericAlertService.alertMessage('Weather Classifications are not available for given search criteria', "Warning");
					return;
				}
			}
			// removed code

			// if ($scope.weatherClassification != null && $scope.weatherClassification.length > 0) {
			// 	if ($scope.weatherClassification[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.weatherClassification[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.weatherReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.weatherReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.weatherReq = {
			// 		"weatherCode" : "",
			// 		"weatherName" : null,
			// 		"status" : "1"
			// 	}
			// }
		});
	},

	$scope.reset = function() {
		$scope.weatherReq = {
			"weatherCode" : "",
			"weatherName" : null,
			"status" : "1"
		}
		// $scope.activeFlag = 0;
		$scope.weatherSearch();
	}, $scope.rowSelect = function(weather) {
		if (weather.selected) {
			utilservice.addItemToArray(editweatherClassification, "id", weather);
		} else {
			editweatherClassification.splice(editweatherClassification.indexOf(weather), 1);
		}
	}
	var service = {};
	var weatherPopUp;
	$scope.weatherCls = function(actionType) {
		angular.forEach(editweatherClassification,function(value,key){
			value.selected=false;
				});
		weatherPopUp = service.addWeatherClass(actionType);
		weatherPopUp.then(function(data) {
			$scope.weatherClassification = data.weatherTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting Weather  details", 'Error');
		});
	}
	service.addWeatherClass = function(actionType) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editweatherClassification <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		weatherPopUp = ngDialog.open({
			template : 'views/centrallib/weatherclass/weatherpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom5',
			scope : $scope,
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var copyEditArray = angular.copy(editweatherClassification);
				var selectedWeather = [];
				$scope.weatherCls = [];
				
			
				
				if (actionType === 'Add') {
					
				
					if(editweatherClassification.length > 0 && actionType === 'Add'){
						editweatherClassification=[];
						GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
						angular.break();
					}
					$scope.weatherCls.push({
						"code" : '',
						"name" : '',
						"status" : 1,
						"selected" : false
					});
				} else {
					$scope.weatherCls = angular.copy(editweatherClassification)
					editweatherClassification = [];
				}
				$scope.addRows = function() {
					$scope.weatherCls.push({
						"code" : '',
						"name" : '',
						"status" : 1,
						"selected" : false
					});
				}, $scope.getWeatherMap = function() {
					var req = {

					}
					WeatherService.getWeathersMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
					})
				}, 
				
				
				
				
				$scope.checkDuplicate = function(weather) {
					weather.duplicateFlag = false;
					weather.code = weather.code.toUpperCase();
					if ($scope.uniqueCodeMap[weather.code] != null) {
						weather.duplicateFlag = true;
						return;
					}
					weather.duplicateFlag = false;
				}, $scope.saveWeathers = function(weatherForm) {
					var flag = false;
					var weatherClassMap = [];
					angular.forEach($scope.weatherCls, function(value, key) {
						if (weatherClassMap[value.code.toUpperCase()] != null) {
							value.duplicateFlag = true;
							flag = true;
						} else {
							value.duplicateFlag = false;
							weatherClassMap[value.code.toUpperCase()] = true;
						}
					});
					if (actionType === 'Add') {
						angular.forEach($scope.weatherCls, function(value, key) {
							if ($scope.uniqueCodeMap[value.code.toUpperCase()] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Weather codes are not allowed', "Warning");
						return;
					}
					var req = {
						"weatherTOs" : $scope.weatherCls
					}
					blockUI.start();
					WeatherService.saveWeathers(req).then(function(data) {
						blockUI.stop();
						var results = data.weatherTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Weather Classification(s) is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Weather Classification(s) saved successfully',"Info");
						succMsg.then(function(data1) {
							$scope.closeThisDialog(weatherPopUp);
							var returnPopObj = {
								"weatherTOs" : results
							}
							deferred.resolve(returnPopObj);
							$scope.weatherSearch();	
						})							
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Weather Classification code is already exists ', "Error");
					});
				},
				
		
				$scope.popUpRowSelect = function(weather) {
					if (weather.selected) {
						selectedWeather.push(weather);
					} else {
						selectedWeather.splice(selectedWeather.indexOf(weather), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedWeather.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedWeather.length < $scope.weatherCls.length) {
						angular.forEach(selectedWeather, function(value, key) {
							$scope.weatherCls.splice($scope.weatherCls.indexOf(value), 1);
						});
						selectedWeather = [];
						GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
					} else if (selectedWeather.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedWeather.length == 1) {
						$scope.weatherCls[0] = {
							"code" : '',
							"name" : '',
							"status" : 1,
							"selected" : false
						};
						selectedWeather = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
				
			
			} ]
		});
		return deferred.promise;
	}
	$scope.activeWeathers = function() {
		if (editweatherClassification.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.weatherClassification = [];
		} else {
			angular.forEach(editweatherClassification, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"weatherIds" : deleteIds,
				"status" : 1
			};
			GenericAlertService.confirmMessageModal('Do you really want to Activate the record', 'Warning', 'YES', 'NO').then(function() {
			WeatherService.deleteWeathers(req).then(function(data) {
			});
			angular.forEach(editweatherClassification, function(value, key) {
				GenericAlertService.alertMessage('Weather Classification(s) activated successfully', 'Info');
				$scope.weatherClassification.splice($scope.weatherClassification.indexOf(value), 1);
			}, function(error) {
				GenericAlertService.alertMessage('Weather Classification(s) is/are failed to activate', "Error");
			});
			editweatherClassification = [];
			$scope.deleteIds = [];

		})
		}
		
	}
	$scope.deleteWeathers = function() {
		if (editweatherClassification.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.weatherClassification = [];
		} else {
			angular.forEach(editweatherClassification, function(value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"weatherIds" : deleteIds,
				"status" : 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				WeatherService.deleteWeathers(req).then(function(data) {
					$scope.weatherSearch();		
				});			
				GenericAlertService.alertMessage('Weather Classification(s) deactivated successfully', 'Info');					
				angular.forEach(editweatherClassification, function(value, key) {
					$scope.weatherClassification.splice($scope.weatherClassification.indexOf(value), 1);
					editweatherClassification = [];
					$scope.deleteIds = [];

				}, function(error) {
					GenericAlertService.alertMessage(' Weather Classification(s) is/are failed to deactivate', "Error");
				});
			}, function(data) {
				angular.forEach(editweatherClassification, function(value) {
					value.selected = false;
				})
			})

		}
	}
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'5%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate, 
				   displayName: "Select", headerTooltip : "Select" },
				{ field: 'code', displayName: "Weather Classification Code", headerTooltip: "Weather Classification Code", groupingShowAggregationMenu: false },
				{ field: 'name', displayName: "Weather Classification Name", headerTooltip: "Weather Classification Name", groupingShowAggregationMenu: false },				
				{ name: 'status',displayName:'Status', cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status",}				
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_Weather_Classification");
			$scope.weatherSearch();	
            $scope.gridOptions.showColumnFooter = false;			
		}
	});
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/weatherclassificationhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
