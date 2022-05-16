'use strict'

app
		.config(
				["$stateProvider", function($stateProvider) {

					$stateProvider
							.state(
									"countryprovincecodes",
									{
										url : "/countryprovincecodes",
										data : {
											roles : []
										},
										views : {
											'content@' : {
												templateUrl : 'views/finance/countryprovince/countryprovince.html',
												controller : 'countryProvinceController'
											}
										}
									})
				}])
		.controller(
				'countryProvinceController',
				["$q","$scope", "ngDialog", "GenericAlertService", "CountryProvinceCodeFactory", "CountryProvinceCodeService", "FinancialYearFactory", "FinancialHalfYearFactory", "FinancialQuarterYearFactory","stylesService", "ngGridService", function($q,$scope, ngDialog, GenericAlertService,CountryProvinceCodeFactory,CountryProvinceCodeService,FinancialYearFactory,FinancialHalfYearFactory,FinancialQuarterYearFactory, stylesService, ngGridService) {
					var editCountryProvinceDetails =[];
					$scope.stylesSvc = stylesService;
					var deleteCountryProvince = [];
					$scope.countryProvinceRowSelect = function(countryProvinceDetails) {
					    var count = 0;
					    $scope.fixedAssetid = null;
						if (countryProvinceDetails.selected) {
							editCountryProvinceDetails.push(countryProvinceDetails);
							deleteCountryProvince.push(countryProvinceDetails.id);
							count++;
						} else {
							count--;
							deleteCountryProvince.splice(editCountryProvinceDetails.indexOf(editCountryProvinceDetails), 1)
							editCountryProvinceDetails.splice(editCountryProvinceDetails.indexOf(editCountryProvinceDetails), 1)
							
							}
						},
					$scope.addCountryProvinceCodes = function(actionType) {
							if(actionType == 'Edit' && editCountryProvinceDetails.length == 0)
								{
								GenericAlertService.alertMessage("Please select Country & Province Code to Edit", 'Warning');
			        			return;
								}
							else if(actionType == 'Edit' && editCountryProvinceDetails.length > 1)
							{
							GenericAlertService.alertMessage("Please select only one country province to Edit", 'Warning');
		        			return;
							}
							var popupDetails = CountryProvinceCodeFactory.countryprovincecodePopUpDetails(actionType,editCountryProvinceDetails);
							popupDetails.then(function(data) {
								$scope.getCountryProvinceCodes();
						}, function(error) {
							GenericAlertService.alertMessage("Error occurred while adding country provision details", 'Error');
						});
							$scope.getCountryProvinceCodes();
					},
					$scope.getCountryProvinceCodes= function () {
						var countryProvinceCodeGetReq = {
							"status": 1,
						};
						editCountryProvinceDetails=[];
						CountryProvinceCodeService.getCountryProvinceCodes(countryProvinceCodeGetReq).then(function (data) {
							//console.log('data===>'+data)
							$scope.countryProvinceCodeToTOs = data.countryProvinceCodeToTOs;
							console.log($scope.countryProvinceCodeToTOs)
							for(var val of $scope.countryProvinceCodeToTOs){
								val.Countrycode = val.countryName.substring(0, 3).toUpperCase();
							}
							$scope.gridOptions.data = angular.copy($scope.countryProvinceCodeToTOs);
							//console.log('8888'+data.countryProvinceCodeToTOs)

						}, function (error) {
							GenericAlertService.alertMessage("Error occured while getting Material Details", "Error");
						});
					},
					 $scope.addFinancialYear = function(item,index)            {	
					console.log("====8888888888888888888======")
						var financialYearData = [];
						var actionType=null;
		            	if(item.financialYearData=="")
		            		{
		            		var actionType='Add';
		            		}
		            	else
		            		{
		            		 actionType='View';
		            		}
		            	
						financialYearData.push(item.financialYearData);
						console.log("===actionType====",actionType)
						console.log("===financialYearData====",financialYearData)
						var popupDetails = FinancialYearFactory.financialYearPopUpDetails(actionType,financialYearData);
						popupDetails.then(function(data) {
							$scope.CountryProvinceDetails[0].financialYearData = data[0];
							
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while adding financial year details", 'Error');
					});
						
	            },

	         
	            $scope.addFinancialHalfYear = function(item)
	            {
	            	var actionType=null;
	            	if(item.financialHalfYearData=="")
	            		{
	            		var actionType='Add';
	            		}
	            	else
	            		{
	            		 actionType='View';
	            		}
	            	
	            	var financialHalfYearData= [];
	            	financialHalfYearData.push(item.financialHalfYearData);
						var popupDetails = FinancialHalfYearFactory.financialHalfYearPopUpDetails(actionType,financialHalfYearData);
						popupDetails.then(function(data) {
							$scope.CountryProvinceDetails[0].financialHalfYearData = data[0];
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while  adding financial half year details", 'Error');
					});
						
	            },
		

	            $scope.addFinancialQuarterYear = function(item)
	            {
	            	var actionType=null;
	            	if(item.financialQuarterYearData=="")
	            		{
	            		var actionType='Add';
	            		}
	            	else
	            		{
	            		 actionType='View';
	            		}
	            	
	            	var financialQuarterYearData = [];
	            	financialQuarterYearData.push(item.financialQuarterYearData)
						var popupDetails = FinancialQuarterYearFactory.financialQuarterYearPopUpDetails(actionType,financialQuarterYearData);
						popupDetails.then(function(data) {
							$scope.CountryProvinceDetails[0].financialQuarterYearData = data;
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while  adding financial quarter year details", 'Error');
					});
						
	            },
	            
	            $scope.deactivateCountryProvinceCodes = function() {
	        		if (deleteCountryProvince == undefined || deleteCountryProvince.length <= 0) {
	        			GenericAlertService.alertMessage("Please select atleast one Country Province Code to deactivate", 'Warning');
	        			return;
	        		}
	        		var countryProvinceCodeDeactiveReq = {
	        			"status" : 2,
	        			"countryProvinceIds" : deleteCountryProvince 
	        		};
	        		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
	        			CountryProvinceCodeService.deactivateCountryProvinceCodes(countryProvinceCodeDeactiveReq).then(function(data) {
	        				GenericAlertService.alertMessage("Country & Province code(s) Deleted successfully", "Info");
	        				deleteCountryProvince = [];
	        				$scope.getCountryProvinceCodes();
	        			}, function(error) {
	        				GenericAlertService.alertMessage(' Asset(s) is/are failed to deactivate', "Error");
	        			});
	        		}, function(data) {
	        			deleteCountryProvince = [];
	        			$scope.getCountryProvinceCodes();
	        		})
	        	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.countryProvinceRowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'Countrycode', displayName: "Country Code", headerTooltip: "Country Code"},
						{ field: 'countryName', displayName: "Country Name", headerTooltip: "Country Name", },
						{ field: 'provisionName', displayName: "Province Name", headerTooltip: "Province Name", },
						
						{ name: 'Yearly', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Financial Year", headerTooltip : "Financial Year",
						cellTemplate: 'template.html'},
			      		 
			      		 { name: 'Half Yearly', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Financial Half Year", headerTooltip : "Financial Half Year",
						cellTemplate: '<button ng-click="grid.appScope.addFinancialHalfYear(row.entity, item)" data-test="CountryProvinceCodes_FinancialHalfYear_View" class="btn btn-primary btn-sm">View</button>'},
			      		 
			      		 { name: 'quarterly', cellClass: 'justify-center', headerCellClass:"justify-center", displayName: "Financial Quarter Year", headerTooltip : "Financial Quarter Year",
						cellTemplate: '<button ng-click="grid.appScope.addFinancialQuarterYear(row.entity, item)" data-test="CountryProvinceCodes_FinancialQuarterYear_View" class="btn btn-primary btn-sm">View</button>'}
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprises_CentralLibrary_FinanceCoded_ProvinceCodes");
					$scope.getCountryProvinceCodes();
					
				}
			});
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
						template: 'views/help&tutorials/countryprovincehelp.html',
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