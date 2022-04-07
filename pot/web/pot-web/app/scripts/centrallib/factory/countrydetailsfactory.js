'use strict';
app.factory('CountryDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "CountryService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, CountryService, GenericAlertService) {
	var countryDetailsPopUp;
	var service = {};
	service.countryDetailsPopUp = function (actionType, editCountries, addedCountries, countries) {
		var deferred = $q.defer();
		var me = this;
		me.findObjectByKey = function (array, key1, key2, value) {
			for (var i = 0; i < array.length; i++) {
				if ((array[i][key1] + "-" + array[i][key2]).toUpperCase() === value) {
					return array[i];
				}
			}
			return null;
		};

		countryDetailsPopUp = ngDialog.open({
			template: 'views/centrallib/country/countrypopup.html',
			scope: $rootScope,
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.actionType = actionType;
				$scope.editCountries = editCountries;
				$scope.addedCountries = addedCountries;
				$scope.addCountryDetails = [];
				$scope.countries = countries;

				$scope.copyEditArray = angular.copy(editCountries);

				// Fetch countries list from ISO
				if (actionType != 'Add') {
					angular.forEach($scope.copyEditArray, function (editCountry, key) {
						angular.forEach($scope.countries, function (country, key1) {
							if (editCountry.code == country.isoAlpha3) {
								editCountry.code = country;
								$scope.addCountryDetails.push(editCountry);
							}
						});
					});
					editCountries = [];
				}

				if ($scope.actionType === 'Add') {
					angular.forEach(editCountries, function (value) {
						value.selected = false;
						editCountries.value = [];
						return editCountries.value = [];
					});

					$scope.popUpRowSelect = function (country) {
						if (country.selected) {
							editCountries.push(country);
						} else {
							editCountries.splice(editCountries.indexOf(country), 1);
						}
					};

					$scope.deleteRows = function () {
						if (editCountries.length == 0) {
							GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						} else if (editCountries.length < $scope.addCountryDetails.length) {
							angular.forEach(editCountries, function (value, key) {
								$scope.addCountryDetails.splice($scope.addCountryDetails.indexOf(value), 1);

							});
							editCountries = [];
							GenericAlertService.alertMessage("Row(s) deleted successfully", "Info");
						} else if (editCountries.length > 1) {
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
						else if (editCountries.length == 1) {
							$scope.addCountryDetails[0] = {
								'code': '',
								'name': '',
								'status': '1',
								'startDate': '',
								'finishDate': '',
								'selected': false
							};
							addCountryDetails = [];
							GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
						}
					};
					$scope.addCountryDetails.push({					
						'code': '',
						'name': '',
						'status': '1',
						'startDate': '',
						'finishDate': '',
						'selected': false
					});
				}
				$scope.addRows = function () {				
					$scope.addCountryDetails.push({
						'code': '',
						'name': '',
						'status': '1',
						'startDate': '',
						'finishDate': '',
						'selected': false
					});
				};	
			$scope.gotodate=function(country){					
			    var d1 = $filter('date')(new Date(country.startDate), "dd-MMM-yyyy");
			    
			    var day=new Date(country.startDate).getDate()-1;
			    var mon=new Date(country.startDate).getMonth();
			    var year=new Date(country.startDate).getFullYear()+1;
			    			
			    var d3=new Date(year, mon, day);
			     var d2 = $filter('date')((d3), "dd-MMM-yyyy");			
			
			$scope.startDate=d1;
			$scope.addCountryDetails[0].finishDate=d2;
			
			//console.log("previous",moment(d1).format('dd-MMM-yyyy'));
			
				}
				
							
				

				$scope.checkDuplicate = function (country) {
					country.name = country.code.countryName;
					country.duplicateFlag = false;
					var existing = me.findObjectByKey($scope.addedCountries, "code", "name", country.code.isoAlpha3 + "-" + country.name.toUpperCase());
					if (existing != null) {
						country.duplicateFlag = true;
						return;
					}
					country.duplicateFlag = false;
				};
				$scope.saveCountryProvisions = function () {
					var flag = false;
					//alert("hello");
					var countryMap = [];
					if (actionType === 'Add') {
						angular.forEach($scope.addCountryDetails, function (value, key) {
							var existing = me.findObjectByKey($scope.addedCountries, "code", "name", value.code.isoAlpha3.toUpperCase() + "-" + value.name.toUpperCase());
							if (existing != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Countries are not allowed', "Warning");
						return;
					}

					var countryList = angular.copy($scope.addCountryDetails);
					angular.forEach(countryList, function (country, key) {
						country.code = country.code.isoAlpha3;
					});
					var req = {
						"provisionTOs": countryList,
					};
					blockUI.start();
					CountryService.saveCountryProvisions(req).then(function (data) {
						blockUI.stop();
						var provisionTOs = data.provisionTOs;

						// var succMsg = GenericAlertService.alertMessageModal('Country(s) is/are saved successfully', 'Info');
						var succMsg = GenericAlertService.alertMessageModal('Country Codes saved successfully', 'Info');
						succMsg.then(function (data1) {
							$scope.closeThisDialog(countryDetailsPopUp);
							var returnPopObj = {
								"provisionTOs": provisionTOs
							};
							deferred.resolve(returnPopObj);
						})
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Country(s) is/are failed to save', 'Error');
					});
				};
			}]
		});
		return deferred.promise;
	};
	return service;
}]);
