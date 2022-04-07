'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("costperformancedatewise", {
		url: '/costperformancedatewise',
		data: {
			cost: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/cost performance/datewiseactualcost.html',
				controller: 'CostDateWiseActualCostController'
			}
		}
	})
}]).controller("CostDateWiseActualCostController", ["$rootScope", "uiGridGroupingConstants", "uiGridConstants", "$filter", "CostCodeMultiSelectFactory", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory", "MultipleCrewSelectionFactory", "CompanyMultiSelectFactory", "BudgetService", "ManpowerReportService", "ProjCostCodeService", "PlantReportService", "stylesService", "ngGridService", "chartService",
	function ($rootScope, uiGridGroupingConstants, uiGridConstants, $filter, CostCodeMultiSelectFactory, $scope, $q, $state, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory, MultipleCrewSelectionFactory, CompanyMultiSelectFactory, BudgetService, ManpowerReportService, ProjCostCodeService, PlantReportService, stylesService, ngGridService, chartService) {
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Amount';
		var series = ['Manpower Cost', 'Plant Cost', 'Material Cost', 'Service Cost'];
		$scope.data = [];
		$scope.labels = [];
		$scope.type = 'chartTable';
		$scope.subReports = [{
			name: 'CostCodeWise Actual Cost Report',
			code: "costcode"
		}, {
			name: 'ProjectWise Actual Cost Report',
			code: "proj"
		}, {
			name: 'EPSWise Actual Cost Report',
			code: "eps"
		}];
		$scope.subReport = "None";
		$scope.categories = [{
			name: 'All',
			code: ["direct", "in-direct"]
		}, {
			name: 'Direct',
			code: ["direct"]
		}, {
			name: 'In Direct',
			code: ["in-direct"]
		}];
		$scope.category = $scope.categories[0];
		$scope.subReportCode = "";
		$scope.dateActualDetails = [];

		let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.$watch('fromDate', function () {
			$scope.checkErr();
		});
		$scope.$watch('toDate', function () {
			$scope.checkErr();
		});
		$scope.checkErr = function () {
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
		};

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.dateActualDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		$scope.getCostCodes = function () {
			if (!$scope.selectedProjIds) {
				GenericAlertService.alertMessage("Please select project to get CostCodes", 'Warning');
				return;
			}
			var costCodeReq = {
				"status": 1,
				"projIds": $scope.selectedProjIds
			};
			var costCodePopUp = CostCodeMultiSelectFactory.getMultiProjCostCodes(costCodeReq);
			costCodePopUp.then(function (data) {
				$scope.costCodeNameDisplay = data.selectedCostCodes.costCodesName;
				$scope.selectedCostCodeIds = data.selectedCostCodes.costCodeIds;
				$scope.dateActualDetails = [];
				$scope.clearSubReportDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
			})
		};
		$scope.getCompanyList = function () {
			var companyReq = {
				"status": 1
			}
			var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
			companyPopUp.then(function (data) {
				$scope.companyNameDisplay = data.selectedCompanies.companyName;
				$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
				$scope.dateActualDetails = [];
				$scope.clearSubReportDetails();
			})
		};
		function noSubReportData() {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false  },
				{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
				{ field: 'cmpName', displayName: "Company", headerTooltip: "Parent Company Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Desc", headerTooltip: "Cost Code Sub Group Description", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Item Id", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", groupingShowAggregationMenu: false  },
				{ field: 'resourceId', displayName: "Resource", headerTooltip: "Resource Id", groupingShowAggregationMenu: false  },
				{ field: 'empFirstName', displayName: "First Name", headerTooltip: "Employee First Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Employee Last Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'category', displayName: "Category", headerTooltip: "Category", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'type', displayName: "Type", headerTooltip: "Actual Hours Type", groupingShowAggregationMenu: false  },
				{ field: 'plantDesc', displayName: "Plant Desc", headerTooltip: "Plant Description AsPerCompany", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'plantRegNumber', displayName: "Reg.Num", headerTooltip: "Plant Registration Number", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'plantMake', displayName: "Make", headerTooltip: "Plant Make", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'plantModel', displayName: "Model", headerTooltip: "Plant Model", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'plantRateType', displayName: "Rate Type", headerTooltip: "Plant Rate Type", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'materialSubGroupCode', displayName: "Material Sub Group Id", headerTooltip: "Material Sub Group Id", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'materialSubGroupName', displayName: "Material Sub Group Name", headerTooltip: "Material Sub Group Name", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'invoiceNumber', displayName: "Invoice Num", headerTooltip: "Invoice Number", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'invoiceDate', displayName: "Invoice Date", headerTooltip: "Invoice Date", visible: false, groupingShowAggregationMenu: false   },
				{ field: 'ratePerUnit', displayName: "Rate/Unit", headerTooltip: "Rate PerUnit", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'unitOfMesure', displayName: "Units", headerTooltip: "Unit OfMeasure", visible: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}   },
				{ field: 'quantity', displayName: "Qty", headerTooltip: "Quantity", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'currencyCode', displayName: "Base Currency", headerTooltip: "Base Currency", groupingShowAggregationMenu: false  },
				{ field: 'costAmount', displayName: "Base Currency Amt", headerTooltip: "Base Curreny Amount", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'currencyCode', displayName: "Common Currency", headerTooltip: "Common Currency", groupingShowAggregationMenu: false  },
				{ field: 'costAmount', displayName: "Common Currency Amt", headerTooltip: "Common Curreny Amount", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				
			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data);
		}
		$scope.changeSubReport = function () {
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartTable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				prepareSubReport();
			} else {
				$scope.plantPeriodicalDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				noSubReportData();
				$scope.getDatewiseactualDetails();
			}
		};
		function prepareSubReport() {
			$scope.labels = [];
			$scope.subReportData = [];
			$scope.data = [];
			if ($scope.subReport.code == "costcode") {
				generateSubReportData("costItemId", "costItemCode");
			} else if ($scope.subReport.code == "proj") {
				generateSubReportData("projId", "projName");
			} else if ($scope.subReport.code == "eps") {
				generateSubReportData("epsId", "epsName");
			}
		};
		function generateSubReportData(key, value) {
			let manpowerArr = [], plantArr = [], materialArr = [], serviceArr = [];
			let subReportMap = [];
			if ($scope.subReport.code == "costcode") {
				let costCodeData = [
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
					{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
					{ field: 'mapValue', displayName: "Cost Code ID", headerTooltip: "Cost Code ID", groupingShowAggregationMenu: false  },
					{ field: 'costName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", groupingShowAggregationMenu: false  },
					{ field: 'currency', displayName: "Base Currency", headerTooltip: "Base Currency", groupingShowAggregationMenu: false  },
					{ field: 'manpowerCost', displayName: "Base Currency in Manpower Cost", headerTooltip: "Base Currency in Manpower Cost", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'plantCost', displayName: "Base Currency in Plant Cost", headerTooltip: "Base Currency in Plant Cost", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'materialCost', displayName: "Base Currency in Material Cost", headerTooltip: "Base Currency in Material Cost", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'serviceCost', displayName: "Base Currency in Service Cost", headerTooltip: "Base Currency in Service Cost", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'totalCost', displayName: "Base Currency in Total Cost", headerTooltip: "Base Currency in Total Cost", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },

					{ field: 'currency', displayName: "Common Currency", headerTooltip: "Common Currency", groupingShowAggregationMenu: false  },
					{ field: 'manpowerCost', displayName: "Manpower Cost in Common Currency", headerTooltip: "Manpower Cost in Common Currency", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
					{ field: 'plantCost', displayName: "Plant Cost in Common Currency", headerTooltip: "Plant Cost in Common Currency", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'materialCost', displayName: "Material Cost in Common Currency", headerTooltip: "Material Cost in Common Currency", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'serviceCost', displayName: "Service Cost in Common Currency", headerTooltip: "Service Cost in Common Currency", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'totalCost', displayName: "Total Cost in Common Currency", headerTooltip: "Total Cost in Common Currency", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeData, data, "Reports_Cost&Performance_DateWiseActualCostDetails_CostCodeWise");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "proj") {
				let projData = [
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
					{ field: 'mapValue', displayName: "Project Name", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
					{ field: 'currency', displayName: "Base Currency", headerTooltip: "Base Currency", groupingShowAggregationMenu: false  },
					{
						name: 'manpowerCost',
						cellTemplate: '<div>{{row.entity.manpowerCost | number : 2}}</div>',
						displayName: "Base Currency in Manpower Cost", headerTooltip: "Base Currency in Manpower Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'plantCost',
						cellTemplate: '<div>{{row.entity.plantCost | number : 2}}</div>',
						displayName: "Base Currency in Plant Cost", headerTooltip: "Base Currency in Plant Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'materialCost',
						cellTemplate: '<div>{{row.entity.materialCost | number : 2}}</div>',
						displayName: "Base Currency in Material Cost", headerTooltip: "Base Currency in Material Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'serviceCost',
						cellTemplate: '<div>{{row.entity.serviceCost | number : 2}}</div>',
						displayName: "Base Currency in Service Cost", headerTooltip: "Base Currency in Service Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'totalCost',
						cellTemplate: '<div>{{row.entity.totalCost | number : 2}}</div>',
						displayName: "Base Currency in Total Cost", headerTooltip: "Base Currency in Total Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},

					{ field: 'currency', displayName: "Common Currency", headerTooltip: "Common Currency", groupingShowAggregationMenu: false  },
					{
						field: 'manpowerCost', displayName: "Manpower Cost in Common Currency", headerTooltip: "Manpower Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						field: 'plantCost', displayName: "Plant Cost in Common Currency", headerTooltip: "Plant Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						field: 'materialCost', displayName: "Material Cost in Common Currency", headerTooltip: "Material Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						field: 'serviceCost', displayName: "Service Cost in Common Currency", headerTooltip: "Service Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						field: 'totalCost', displayName: "Total Cost in Common Currency", headerTooltip: "Total Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, projData, data, "Reports_Cost&Performance_DateWiseActualCostDetails_ProjWise");
				$scope.gridOptions.gridMenuCustomItems = false;
			}

			if ($scope.subReport.code == "eps") {
				let epsData = [
					{ field: 'mapValue', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false  },
					{ field: 'currency', displayName: "Base Currency", headerTooltip: "Base Currency", groupingShowAggregationMenu: false  },
					{
						name: 'manpowerCost',
						cellTemplate: '<div>{{row.entity.manpowerCost | number : 2}}</div>',
						displayName: "Base Currency in Manpower Cost", headerTooltip: "Base Currency in Manpower Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'plantCost',
						cellTemplate: '<div>{{row.entity.plantCost | number : 2}}</div>',
						displayName: "Base Currency in Plant Cost", headerTooltip: "Base Currency in Plant Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'materialCost',
						cellTemplate: '<div>{{row.entity.materialCost | number : 2}}</div>',
						displayName: "Base Currency in Material Cost", headerTooltip: "Base Currency in Material Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'serviceCost',
						cellTemplate: '<div>{{row.entity.serviceCost | number : 2}}</div>',
						displayName: "Base Currency in Service Cost", headerTooltip: "Base Currency in Service Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}
					},
					{
						name: 'totalCost',
						cellTemplate: '<div>{{row.entity.totalCost | number : 2}}</div>',
						displayName: "Base Currency in Total Cost", headerTooltip: "Base Currency in Total Cost", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						},
					},
					{ field: 'currency', displayName: "Common Currency", headerTooltip: "Common Currency", groupingShowAggregationMenu: false  },
					{ field: 'manpowerCost', displayName: "Manpower Cost in Common Currency", headerTooltip: "Manpower Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}, },
					{ field: 'plantCost', displayName: "Plant Cost in Common Currency", headerTooltip: "Plant Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					},},
					{ field: 'materialCost', displayName: "Material Cost in Common Currency", headerTooltip: "Material Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					},},
					{ field: 'serviceCost', displayName: "Service Cost in Common Currency", headerTooltip: "Service Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					},},
					{ field: 'totalCost', displayName: "Total Cost in Common Currency", headerTooltip: "Manpower Cost in Common Currency", groupingShowAggregationMenu: false  , aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					},},
					
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsData, data, "Reports_Cost&Performance_DateWiseActualCostDetails_EPSWise");
				$scope.gridOptions.gridMenuCustomItems = false;
			}


			for (let plantDtl of $scope.dateActualDetails) {
				let mapKey = plantDtl[key];
				let mapValue = plantDtl[value];
				if (!subReportMap[mapKey]) {
					subReportMap[mapKey] = {
						"mapKey": mapKey,
						"mapValue": mapValue,
						"currency": plantDtl["currencyCode"],
						"manpowerCost": 0,
						"plantCost": 0,
						"materialCost": 0,
						"serviceCost": 0,
						"totalCost": 0
					};
					if (key == "costItemId") {
						subReportMap[mapKey].epsName = plantDtl["epsName"];
						subReportMap[mapKey].projName = plantDtl["projName"];
						subReportMap[mapKey].costName = plantDtl["costItemName"];
					}
					if (key == "projId") {
						subReportMap[mapKey].epsName = plantDtl["epsName"];
					}
				}
				if (plantDtl.empId)
					subReportMap[mapKey].manpowerCost += plantDtl.costAmount;
				else if (plantDtl.plantId)
					subReportMap[mapKey].plantCost += plantDtl.costAmount;
				else if (plantDtl.matId)
					subReportMap[mapKey].materialCost += plantDtl.costAmount;
				subReportMap[mapKey].totalCost += plantDtl.costAmount;
			}
			for (const index in subReportMap) {
				if (subReportMap[index].manpowerCost > 0 || subReportMap[index].plantCost > 0 || subReportMap[index].materialCost > 0 || subReportMap[index].serviceCost > 0) {
					manpowerArr.push(fixedDecimal(subReportMap[index].manpowerCost));
					plantArr.push(fixedDecimal(subReportMap[index].plantCost));
					materialArr.push(fixedDecimal(subReportMap[index].materialCost));
					serviceArr.push(fixedDecimal(subReportMap[index].serviceCost));
					$scope.labels.push(subReportMap[index].mapValue);
					$scope.subReportData.push(subReportMap[index]);
				}
			}
			$scope.gridOptions.data = angular.copy($scope.subReportData);
			$scope.data.push(manpowerArr);
			$scope.data.push(plantArr);
			$scope.data.push(materialArr);
			$scope.data.push(serviceArr);
			initGraph();
		};
		function fixedDecimal(value) {
			return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
		}
		function initGraph() {
			$scope.series = series;
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};
		$scope.getDatewiseactualDetails = function () {
			// $scope.clearSubReportDetails();
			if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select Project", 'Warning');
				return;
			}
			if ($scope.selectedCostCodeIds == undefined || $scope.selectedCostCodeIds == null) {
				GenericAlertService.alertMessage("Please select cost codes", 'Warning');
				return;
			}
			if ($scope.category.code == undefined || $scope.category.code == null) {
				GenericAlertService.alertMessage("Please select Category", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds == undefined || $scope.selectedCompanyIds == null) {
				GenericAlertService.alertMessage("Please select Company", 'Warning');
				return;
			}

			var req = {
				"projIds": $scope.selectedProjIds,
				"costcodeIds": $scope.selectedCostCodeIds,
				"categories": $scope.category.code,
				"cmpIds": $scope.selectedCompanyIds,
				"fromDate": $scope.fromDate,
				"toDate": $scope.toDate
			};
			BudgetService.getCostCodeActualDetailsReport(req).then(function (data) {
				$scope.dateActualDetails = data.costReportResps;
				$scope.gridOptions1.data = angular.copy(data.costReportResps);
				if ($scope.subReport && $scope.subReport != "None") {
					prepareSubReport();
				}
				if ($scope.dateActualDetails.length <= 0) {
					GenericAlertService.alertMessage("Details are not available for the search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting Date Wise Actual details", 'Error');
			});
			initGraph();
		};
		$scope.clearSubReportDetails = function () {
			$scope.dateActualDetails = [];
			$scope.subReportName = "";
			$scope.subReport = "None";
			$scope.type = "";
			$scope.subReportCode = "";
		};
		$scope.resetDateWiseActualDetails = function () {
			$scope.dateActualDetails = [];
			$scope.data = [];
			$scope.labels = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.companyNameDisplay = null;
			$scope.costCodeNameDisplay = null;
			$scope.selectedCostCodeIds = [];
			$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
			$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
			$scope.subReportName = null;
			$scope.subReport = "None";
			$scope.type = '';
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		};
		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'date', displayName: "Date", headerTooltip: "Date", groupingShowAggregationMenu: false  },
					{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS Id", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false  },
					{ field: 'cmpName', displayName: "Company", headerTooltip: "Parent Company Name", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costSubGroupCode', displayName: "Cost Code Sub Group Id", headerTooltip: "Cost Code Sub Group Id", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costSubGroupName', displayName: "Cost Code Sub Group Desc", headerTooltip: "Cost Code Sub Group Description", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costItemCode', displayName: "Cost Code Id", headerTooltip: "Cost Code Item Id", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'costItemName', displayName: "Cost Code Desc", headerTooltip: "Cost Code Description", groupingShowAggregationMenu: false  },
					{ field: 'resourceId', displayName: "Resource", headerTooltip: "Resource Id", groupingShowAggregationMenu: false  },
					{ field: 'empFirstName', displayName: "First Name", headerTooltip: "Employee First Name", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'empLastName', displayName: "Last Name", headerTooltip: "Employee Last Name", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'category', displayName: "Category", headerTooltip: "Category", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'type', displayName: "Type", headerTooltip: "Actual Hours Type", groupingShowAggregationMenu: false  },
					{ field: 'plantDesc', width:"50",displayName: "Plant Desc", headerTooltip: "Plant Description AsPerCompany", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'plantRegNumber', width:"50",displayName: "Reg.Num", headerTooltip: "Plant Registration Number", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'plantMake', width:"50",displayName: "Make", headerTooltip: "Plant Make", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'plantModel', width:"50",displayName: "Model", headerTooltip: "Plant Model", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'plantRateType', width:"50",displayName: "Rate Type", headerTooltip: "Plant Rate Type", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'materialSubGroupCode', width:"50",displayName: "Material Sub Group Id", headerTooltip: "Material Sub Group Id", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'materialSubGroupName', width:"50",displayName: "Material Sub Group Name", headerTooltip: "Material Sub Group Name", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'invoiceNumber', width:"50",displayName: "Invoice Num", headerTooltip: "Invoice Number", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'invoiceDate', width:"50",displayName: "Invoice Date", headerTooltip: "Invoice Date", visible: false, groupingShowAggregationMenu: false   },
					{ field: 'ratePerUnit', displayName: "Rate/Unit", headerTooltip: "Rate PerUnit", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'unitOfMesure', displayName: "Units", headerTooltip: "Unit OfMeasure", visible: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}   },
					{ field: 'quantity', displayName: "Qty", headerTooltip: "Quantity", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}  },
					{ field: 'currencyCode', displayName: "Base Currency", headerTooltip: "Base Currency", groupingShowAggregationMenu: false  },
				{ field: 'costAmount', displayName: "Base Currency Amt", headerTooltip: "Base Curreny Amount", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				{ field: 'currencyCode', displayName: "Common Currency", headerTooltip: "Common Currency", groupingShowAggregationMenu: false  },
				{ field: 'costAmount', displayName: "Common Currency Amt", headerTooltip: "Common Curreny Amount", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
				customTreeAggregationFinalizerFn: function (aggregation) {
					aggregation.rendered = aggregation.value;
				}  },
				]
				let data = [];
				$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data, "Reports_Cost&Performance_DateWiseActualCostDetails");
				$scope.gridOptions1.exporterPdfOrientation = 'landscape';
				$scope.gridOptions1.exporterPdfPageSize = 'A3';
				$scope.gridOptions1.exporterPdfMaxGridWidth = 950;
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
				template: 'views/help&tutorials/reportshelp/costperformancehelp/costdatewiseactualhelp.html',
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
