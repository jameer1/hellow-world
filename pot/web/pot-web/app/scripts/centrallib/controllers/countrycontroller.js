'use strict';+
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("country", {
		url : '/country',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/centrallib/country/countrylist.html',
				controller : 'CountryController'
			}
		}
	})
}]).controller("CountryController", ["$rootScope", "$q", "$scope", "$filter", "$state", "$location", "$window", "blockUI", "ngDialog", "CountryDetailsFactory", "CountryService", "GenericAlertService","stylesService", "ngGridService", function($rootScope, $q, $scope, $filter, $state, $location, $window, blockUI, ngDialog,CountryDetailsFactory, CountryService, GenericAlertService, stylesService, ngGridService) {
	var me = this;
	var service = {}
	$scope.stylesSvc = stylesService;
	$scope.sortType="code"
	var deferred = $q.defer();
	$scope.deletecodes = [];
	me.editCountries = [];
	$scope.countries = [];
	
	$scope.countryProvisionDetails = []; 
	$scope.countryFilterReq={
			"countryCode":"",
			"countryName":null,
			"status":1
	}
		
	$scope.reset = function() {
		$scope.countryFilterReq={
			"countryCode":"",
			"countryName":null
		}
		$scope.getCountryProvisions();
	},
	
	$scope.getCountryProvisions = function(click) {
		$scope.countryFilterReq.countryCode = $scope.countryFilterReq.countryCode.toUpperCase();
		var provisionDetails = CountryService.getCountryProvisions($scope.countryFilterReq);
		provisionDetails.then(function(data) {
			$scope.countryProvisionDetails  = data.provisionTOs;
			for (let manpower of $scope.countryProvisionDetails ) {
				manpower.financeyear =(manpower.startDate !=null ?manpower.startDate:""  )+"-"+(manpower.finishDate !=null ? manpower.finishDate:"");
			}
			$scope.gridOptions.data = angular.copy($scope.countryProvisionDetails);
			if(click=='click'){
				if ($scope.countryProvisionDetails.length <= 0) {
					GenericAlertService.alertMessage('Country Codes details are not available for given search criteria', "Warning");
					return;
				}
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting country provisions", 'Warning');
		});
	}, 

	$scope.selectCountries = function(country) {
		if(country.selected){
			me.editCountries.push(country);
		} else {
			me.editCountries.pop(country);
		}
	},
	
	$scope.addCountryProvisions = function(actionType) {
		angular.forEach(me.editCountries,function(value,key) {
			value.selected=false;
		});

		if (me.editCountries.length > 0 && actionType=="Add") {
			me.editCountries=[];
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');	
			return;
		}
		
		if (me.editCountries.length > 0 || actionType=='Add') {
			if ($scope.countries.length == 0) {
				CountryService.getCountries().then(function (data) {
					$scope.countries = data.countryInfoTOs;
					addCountry(actionType);
				});
			} else {
				addCountry(actionType);
			}
			
		} else if (me.editCountries.length <= 0 ) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
		}
	};

	function addCountry(actionType) {
		var popupCountryDetails = CountryDetailsFactory.countryDetailsPopUp(actionType, me.editCountries, 
			$scope.countryProvisionDetails, $scope.countries);
		me.editCountries = [];
		popupCountryDetails.then(function (data) {
			$scope.countryProvisionDetails = data.provisionTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting country details", 'Info');
		});
	};
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
			template: 'views/help&tutorials/countrycodeshelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.selectCountries(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'code', displayName: "Country Code", headerTooltip: "Country Code"},
						{ field: 'name', displayName: "Country Name", headerTooltip: "Country Name", },
						{ name: 'financeyear', displayName: "Financial Year (Cycle Period)", headerTooltip: "Financial Year (Cycle Period)" },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_CountryCodes");
					$scope.getCountryProvisions();
					
				}
			});
}]);
