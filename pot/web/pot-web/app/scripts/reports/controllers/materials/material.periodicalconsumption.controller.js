'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matperiodicalconsumptionreport", {
		url: '/matperiodicalconsumptionreport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.periodicalconsumption.html',
				controller: 'MaterialPeriodicalConsumptionReport'
			}
		}
	})
}]).controller("MaterialPeriodicalConsumptionReport", ["$scope", "uiGridGroupingConstants", "uiGridConstants","$q", "ngDialog", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "CostCodeMultiSelectFactory", "MaterialMultiSelectFactory",
	"WorkDiaryService", "dateGroupingService", "ProjectSettingsService", "stylesService", "ngGridService", "chartService", "$filter",
	function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog,GenericAlertService, EpsProjectMultiSelectFactory,
		CostCodeMultiSelectFactory, MaterialMultiSelectFactory, WorkDiaryService,
		dateGroupingService, ProjectSettingsService, stylesService, ngGridService, chartService, $filter) {
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Qty';
		$scope.subReportCode = 'None';
		$scope.periodicalDetails = [];
		$scope.type = 'chartTable';
		let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.labels = [];
		$scope.data = [];
		$scope.periodicReportType = "Daily";
		let projReportsSettings = null;
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
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.selectedClientIds = data.searchProject.clientIds;
				ProjectSettingsService.projReportsOnLoad({ "projIds": $scope.selectedProjIds }).then(function (data) {
					projReportsSettings = data.projectReportsTOs;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
				});
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		},

			$scope.getCostCodes = function () {
				if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
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
					$scope.periodicalDetails = [];
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting CostCode Details", 'Error');
				});
			},

			$scope.getMaterialDetails = function () {

				var materialReq = {
					"status": 1
				};
				var materialPopUp = MaterialMultiSelectFactory.getMultiMaterials(materialReq);
				materialPopUp.then(function (data) {
					$scope.materialNameDisplay = data.selectedMaterial.materialName;
					$scope.selectedMaterialIds = data.selectedMaterial.materialIds;
					$scope.periodicalDetails = [];
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting Material Details", 'Error');
				})
			},

			$scope.reset = function () {
				$scope.searchProject = null;
				$scope.selectedProjIds = null;
				$scope.selectedClientIds = null;
				$scope.costCodeNameDisplay = null;
				$scope.selectedCostCodeIds = null;
				$scope.periodicalDetails = [];
				$scope.materialNameDisplay = null;
				$scope.selectedMaterialIds = null;
				$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
				$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
				$scope.materialNameConsumption = [];
				$scope.costcodeWiseConsumption = [];
				$scope.epsWiseConsumption = [];
				$scope.projWiseConsumption = [];
				$scope.periodicalConsumption = [];
				$scope.overallConsumption = [];
				$scope.subReportCode = 'None';
				$scope.type = '';
				$scope.labels = [];
				$scope.data = [];
				if ($scope.gridOptions) {
					$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
					$scope.gridOptions.data = [];
				}
			}


		function isNotValid() {
			if (!$scope.selectedProjIds) {
				GenericAlertService.alertMessage("Select projects.", 'Warning');
				return true;
			} else if (!$scope.selectedCostCodeIds) {
				GenericAlertService.alertMessage("Select cost codes.", 'Warning');
				return true;
			} else if (!$scope.selectedMaterialIds) {
				GenericAlertService.alertMessage("Select materials.", 'Warning');
				return true;
			} else if (!$scope.fromDate) {
				GenericAlertService.alertMessage("From date cannot be empty.", 'Warning');
				return true;
			} else if (!$scope.toDate) {
				GenericAlertService.alertMessage("To date cannot be empty.", 'Warning');
				return true;
			} else {
				if (new Date($scope.fromDate) > new Date($scope.toDate)) {
					GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
					return true;
				}
			}
			return false;
		}

		function formatDates(data) {
			data.map(material => {
				$scope.formatDate(material);
			});
			return data;
		}

		$scope.getPeriodicalConsumption = function () {
			if (!isNotValid()) {
				let req = {
					"fromDate": $scope.fromDate,
					"toDate": $scope.toDate,
					"materialIds": $scope.selectedMaterialIds,
					"costCodeIds": $scope.selectedCostCodeIds,
					"projList": $scope.selectedProjIds
				}

				WorkDiaryService.getWorkdairyReports(req).then(function (data) {
				console.log(data);
				$scope.periodicalDetails  = data.ledgerRes.sort(function(a, b) {
    														return (a.cumulativeConsumption) - (b.cumulativeConsumption);
														});
					$scope.filtered = $filter('unique')(data.ledgerRes,'consumption');
					$scope.periodicalDetails = formatDates(data.ledgerRes);
					if ($scope.subReportCode && $scope.subReportCode === 'Overall - Consumption of Materials Summary')
						overallConsumption();
					else if ($scope.subReportCode && $scope.subReportCode === 'Material Name Wise - Consumption Report')
						materialNameConsumption();
					else if ($scope.subReportCode && $scope.subReportCode === 'Cost Code Wise - Material Consumption')
						costCodeWiseConsumption();
					else if ($scope.subReportCode && $scope.subReportCode === 'EPS ID Wise - Material Consumption')
						epsWiseConsumption();
					else if ($scope.subReportCode && $scope.subReportCode === 'Project ID Wise - Material Consumption')
						projWiseConsumption();
					else if ($scope.subReportCode && $scope.subReportCode === 'Periodical Report')
						periodicalReport();
					$scope.gridOptions.data = $scope.periodicalDetails;
					if ($scope.periodicalDetails.length <= 0) {
						GenericAlertService.alertMessage("Material-Periodical Consumption Report not available for the search criteria", 'Warning');
					}
				}, function (data) {
					GenericAlertService.alertMessage("Error occured while getting Material report details.", 'Error');
				});
			}
		},

			$scope.formatDate = function (material) {
				material.date = moment(material.date).format('DD-MMM-YYYY');
				return material.date;
			},

			$scope.periodicSubReports = [{
				name: 'Daily',
				code: "daily"
			}, {
				name: 'Weekly',
				code: "weekly"
			}, {
				name: 'Monthly',
				code: "monthly"
			}, {
				name: 'Yearly',
				code: "yearly"
			}];

		$scope.changePeriodType = function (periodicReportType) {
			$scope.periodicReportType = periodicReportType;
			periodicalReport();
		}

		$scope.subReports = [
			{
				name: 'Overall - Consumption of Materials Summary',
				code: "overall"
			},
			{
				name: 'Material Name Wise - Consumption Report',
				code: "material"
			},
			{
				name: 'Cost Code Wise - Material Consumption',
				code: "costcode"
			}, {
				name: 'Project ID Wise - Material Consumption',
				code: "proj"
			}, {
				name: 'EPS ID Wise - Material Consumption',
				code: "eps"
			}, {
				name: 'Periodical Report',
				code: "eps"
			}];

		function noSubReportData() {
			let columnDefs = [
				{ field: 'date', displayName: "Date", headerTooltip: 'Date', visible: false, groupingShowAggregationMenu: false },
				{ field: 'eps', displayName: "EPS", headerTooltip: 'EPS Name' , groupingShowAggregationMenu: false},
				{ field: 'projName', displayName: "Project", headerTooltip: 'Project Name' , groupingShowAggregationMenu: false},
				{ field: 'costCodeDesc', displayName: "CostCode Desc", headerTooltip: 'Costcode Description', visible: false, groupingShowAggregationMenu: false },
				{ field: 'costCodeGroup', displayName: "CostCode Group", headerTooltip: 'Costcode Group' , groupingShowAggregationMenu: false},
				{ field: 'costCodeName', displayName: "CostCode", headerTooltip: 'Costcode Name' , groupingShowAggregationMenu: false},
				{ field: 'resourceMaterial', displayName: "Material", headerTooltip: 'Material' , groupingShowAggregationMenu: false},
				{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: 'Unit Of Measure' , groupingShowAggregationMenu: false},
				{ field: 'previousPeriod', displayName: "Prev Period", headerTooltip: 'Previous Period' , groupingShowAggregationMenu: false},
				{ field: 'consumption', displayName: "Rep Period", headerTooltip: 'Reporting Period' , groupingShowAggregationMenu: false},
				{ field: 'upToDatePeriod', displayName: "U.T.D Period", headerTooltip: 'Up To Date Period' , groupingShowAggregationMenu: false},
				{ field: 'cumulativeConsumption', displayName: "Cumul Consum", headerTooltip: 'cumulativeConsumption', groupingShowAggregationMenu: false }

			]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_MaterialPeriodicalConsumptionReport");
		}
		$scope.changeSubReport = function () {
			if (!isNotValid()) {
				$scope.type = 'chartTable';
				if ($scope.subReportCode
					&& $scope.subReportCode === 'Overall - Consumption of Materials Summary')
					overallConsumption();
				else if ($scope.subReportCode
					&& $scope.subReportCode === 'Material Name Wise - Consumption Report')
					materialNameConsumption();
				else if ($scope.subReportCode
					&& $scope.subReportCode === 'Cost Code Wise - Material Consumption')
					costCodeWiseConsumption();
				else if ($scope.subReportCode
					&& $scope.subReportCode === 'EPS ID Wise - Material Consumption')
					epsWiseConsumption();
				else if ($scope.subReportCode
					&& $scope.subReportCode === 'Project ID Wise - Material Consumption')
					projWiseConsumption();
				else if ($scope.subReportCode
					&& $scope.subReportCode === 'Periodical Report')
					periodicalReport();
				else {
					$scope.type = null;
					$scope.subReportCode = 'None';
					noSubReportData();
					$scope.getPeriodicalConsumption();
				}
			} else
				$scope.subReportCode = null;
		}

		$scope.selectSubReportType = function () {
			$scope.changeSubReport($scope.subReportCode);
		}

		let overallConsumption = function () {
			if ($scope.periodicalDetails.length == 0)
				$scope.getPeriodicalConsumption();
			else {
				let overAll = [
					{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material" , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
					{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, overAll, data, "Reports_Materials_MaterialPeriodicalConsumptionReport");
				$scope.gridOptions.gridMenuCustomItems = false;

				$scope.overallConsumption = [];
				function addNewConsumptionMaterial(material) {
					let materialConsumptionObj = {
						"resourceMaterial": null,
						"unitOfMeasure": null,
						"consumption": null,
						"id": null
					}
					materialConsumptionObj.resourceMaterial = material.resourceMaterial;
					materialConsumptionObj.unitOfMeasure = material.unitOfMeasure;
					materialConsumptionObj.consumption = material.upToDatePeriod;
					materialConsumptionObj.id = material.materialId;
					return materialConsumptionObj;
				}
				sortData($scope.periodicalDetails, $scope.overallConsumption, addNewConsumptionMaterial, 'materialId');
				setChartData($scope.overallConsumption, 'resourceMaterial');
				$scope.gridOptions.data = angular.copy($scope.overallConsumption);
			}
		}

		let materialNameConsumption = function () {
			if ($scope.periodicalDetails.length == 0)
				$scope.getPeriodicalConsumption();
			else {
				let materialName = [
					{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material" , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
					{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, materialName, data);
				$scope.gridOptions.gridMenuCustomItems = false;
				$scope.materialNameConsumption = [];
				function addNewConsumptionMaterial(material) {
					let materialConsumptionObj = {
						"resourceMaterial": null,
						"unitOfMeasure": null,
						"consumption": null,
						"id": null
					}
					materialConsumptionObj.resourceMaterialChart = material.resourceMaterial + " in " + material.unitOfMeasure;;
					materialConsumptionObj.resourceMaterial = material.resourceMaterial;
					materialConsumptionObj.unitOfMeasure = material.unitOfMeasure;
					if(material.consumption == null)
						material.consumption = "0";
					materialConsumptionObj.consumption = material.consumption;
					materialConsumptionObj.id = material.materialId;
					return materialConsumptionObj;
				}
				sortData($scope.periodicalDetails, $scope.materialNameConsumption, addNewConsumptionMaterial, 'materialId');
				setChartData($scope.materialNameConsumption, 'resourceMaterialChart');
				$scope.gridOptions.data = angular.copy($scope.materialNameConsumption);
			}
		}

		let costCodeWiseConsumption = function () {
			if ($scope.periodicalDetails.length == 0)
				$scope.getPeriodicalConsumption();
			else {
				let costCodeConsum = [
					{ field: 'projName', displayName: "Project Name", headerTooltip: 'Project Name' , groupingShowAggregationMenu: false},
					{ field: 'costCodeDesc', displayName: "Cost Code Id", headerTooltip: "Cost Code Id" , groupingShowAggregationMenu: false},
					{ field: 'costCodeName', displayName: "Cost Code", headerTooltip: "Cost Code" , groupingShowAggregationMenu: false},
					{ field: 'costCodeGrp', displayName: "Cost Code Group", headerTooltip: "Cost Code Group" , groupingShowAggregationMenu: false},
					{ field: 'resourceMaterial', displayName: "Material Id", headerTooltip: "Material Id" , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
					{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, costCodeConsum, data, "Reports_Materials_MaterialPeriodicalConsumptionReport_Material");
				$scope.gridOptions.gridMenuCustomItems = false;
				$scope.costcodeWiseConsumption = [];
				function addNewConsumptionMaterial(material) {
					let materialConsumptionObj = {
						"costCodeName": null,
						"costCodeGrp": null,
						"costCodeDesc": null,
						"resourceMaterial": null,
						"unitOfMeasure": null,
						"consumption": null,
						"projName": null,
						"id": null
					}
					materialConsumptionObj.projName = material.projName;
					materialConsumptionObj.costCodeName = material.costCodeName;
					materialConsumptionObj.costCodeGrp = material.costCodeGroup;
					materialConsumptionObj.costCodeDesc = material.costCodeDesc;
					materialConsumptionObj.resourceMaterial = material.resourceMaterial;
					materialConsumptionObj.unitOfMeasure = material.unitOfMeasure;
					if(material.consumption == null)
						material.consumption = "0";
					materialConsumptionObj.consumption = material.consumption;
					materialConsumptionObj.id = material.costCodeId;
					return materialConsumptionObj;

				}

				sortData($scope.periodicalDetails, $scope.costcodeWiseConsumption, addNewConsumptionMaterial, 'costCodeId');
				setChartData($scope.costcodeWiseConsumption, 'costCodeDesc');
				$scope.gridOptions.data = angular.copy($scope.costcodeWiseConsumption);
			}
		}

		let epsWiseConsumption = function () {
			if ($scope.periodicalDetails.length == 0)
				$scope.getPeriodicalConsumption();
			else {
				let epsWiseConsum = [
					{ field: 'eps', displayName: "EPS", headerTooltip: "EPS" , groupingShowAggregationMenu: false},
					{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material" , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
					{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty" , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					}},
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, epsWiseConsum, data, "Reports_Materials_MaterialPeriodicalConsumptionReport_CostCode");
				$scope.gridOptions.gridMenuCustomItems = false;
				$scope.epsWiseConsumption = [];
				function addNewConsumptionMaterial(material) {
					let materialConsumptionObj = {
						"eps": null,
						"resourceMaterial": null,
						"unitOfMeasure": null,
						"consumption": null,
						"id": null
					}
					materialConsumptionObj.eps = material.eps;
					materialConsumptionObj.resourceMaterial = material.resourceMaterial;
					materialConsumptionObj.unitOfMeasure = material.unitOfMeasure;
					materialConsumptionObj.consumption = material.upToDatePeriod;
					materialConsumptionObj.id = material.epsId;
					return materialConsumptionObj;
				}
				sortData($scope.periodicalDetails, $scope.epsWiseConsumption, addNewConsumptionMaterial, 'epsId');
				setChartData($scope.epsWiseConsumption, 'eps');
				$scope.gridOptions.data = angular.copy($scope.epsWiseConsumption);
			}
		}

		let projWiseConsumption = function () {
			if ($scope.periodicalDetails.length == 0)
				$scope.getPeriodicalConsumption();
			else {
				let projWiseConsum = [
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name" , groupingShowAggregationMenu: false},
					{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material" , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
					{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty", groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, projWiseConsum, data, "Reports_Materials_MaterialPeriodicalConsumptionReport_EPSId");
				$scope.gridOptions.gridMenuCustomItems = false;

				$scope.projWiseConsumption = [];
				function addNewConsumptionMaterial(material) {
					let materialConsumptionObj = {
						"projName": null,
						"resourceMaterial": null,
						"unitOfMeasure": null,
						"consumption": null,
						"id": null
					}
					materialConsumptionObj.projName = material.projName;
					materialConsumptionObj.resourceMaterial = material.resourceMaterial;
					materialConsumptionObj.unitOfMeasure = material.unitOfMeasure;
					materialConsumptionObj.consumption = material.upToDatePeriod;
					materialConsumptionObj.id = material.projId;
					return materialConsumptionObj;
				}
				sortData($scope.periodicalDetails, $scope.projWiseConsumption, addNewConsumptionMaterial, 'projId');
				setChartData($scope.projWiseConsumption, 'projName');
				$scope.gridOptions.data = angular.copy($scope.projWiseConsumption);
			}
		}

		let periodicalReport = function () {
			if ($scope.periodicalDetails.length == 0)
				$scope.getPeriodicalConsumption();
			else {
				$scope.periodicalConsumption = [];

				let periodConsum = [
					{ field: 'date', displayName: "Periodical (" + $scope.periodicReportType + ")", headerTooltip: "Date" , groupingShowAggregationMenu: false},
					{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material" , groupingShowAggregationMenu: false},
					{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure" , groupingShowAggregationMenu: false},
					{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty",cellTemplate:'<div>{{row.entity.consumption>0?row.entity.consumption:0}}</div>', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
					customTreeAggregationFinalizerFn: function (aggregation) {
						aggregation.rendered = aggregation.value;
					} }
				]
				let data = [];
				$scope.gridOptions = ngGridService.initGrid($scope, periodConsum, data, "Reports_Materials_MaterialPeriodicalConsumptionReport_ProjId");
				$scope.gridOptions.gridMenuCustomItems = false;
				function addNewConsumptionMaterial(material) {
					let materialConsumptionObj = {
						"date": null,
						"resourceMaterial": null,
						"unitOfMeasure": null,
						"consumption": null,
						"id": null
					}
					materialConsumptionObj.date = $scope.formatDate(material);
					materialConsumptionObj.resourceMaterial = material.resourceMaterial;
					materialConsumptionObj.unitOfMeasure = material.unitOfMeasure;
					materialConsumptionObj.consumption = material.consumption;
					materialConsumptionObj.id = material.materialId;
					return materialConsumptionObj;
				}

				let groupData = null;
				if ($scope.periodicReportType === 'Daily') {
					groupData = dateGroupingService.groupByDate($scope.periodicalDetails);
					$scope.periodicalConsumption = [];
					groupData.map(data => {
						let sortedArray = new Array();
						sortData(data.values, sortedArray, addNewConsumptionMaterial, 'materialId');
						$scope.periodicalConsumption = $scope.periodicalConsumption.concat(sortedArray);
					});
				} else if ($scope.periodicReportType === 'Yearly') {
					if (projReportsSettings.length == 1)
						groupData = dateGroupingService.groupByYear($scope.periodicalDetails, projReportsSettings[0].year);
					else
						groupData = dateGroupingService.groupByYear($scope.periodicalDetails);
					$scope.periodicalConsumption = [];
					groupData.map(data => {
						let sortedArray = new Array();
						sortData(data.values, sortedArray, addNewConsumptionMaterial, 'materialId');
						$scope.periodicalConsumption = $scope.periodicalConsumption.concat(sortedArray);
					});
				} else if ($scope.periodicReportType === 'Weekly') {
					if (projReportsSettings.length == 1)
						groupData = dateGroupingService.groupByWeek($scope.periodicalDetails, projReportsSettings[0].week);
					else
						groupData = dateGroupingService.groupByWeek($scope.periodicalDetails, 'Monday');
					$scope.periodicalConsumption = [];
					groupData.map(data => {
						let sortedArray = new Array();
						sortData(data.values, sortedArray, addNewConsumptionMaterial, 'materialId');
						$scope.periodicalConsumption = $scope.periodicalConsumption.concat(sortedArray);
					});
				} else if ($scope.periodicReportType === 'Monthly') {
					if (projReportsSettings.length == 1)
						groupData = dateGroupingService.groupByMonth($scope.periodicalDetails,
							parseInt(projReportsSettings[0].month));
					else
						groupData = dateGroupingService.groupByMonth($scope.periodicalDetails);
					$scope.periodicalConsumption = [];
					groupData.map(data => {
						let sortedArray = new Array();
						sortData(data.values, sortedArray, addNewConsumptionMaterial, 'materialId');
						$scope.periodicalConsumption = $scope.periodicalConsumption.concat(sortedArray);
					});
				}
				setChartData($scope.periodicalConsumption, 'date');
				$scope.gridOptions.data = angular.copy($scope.periodicalConsumption);
			}
		}

		function sortData(mainArray, sortedArray, newMaterial, sortProp) {
			mainArray.map(material => {
				if (sortedArray.length == 0)
					sortedArray.push(newMaterial(material));
				else {
					let existingMaterial = sortedArray.find((item) => item.id === material[sortProp]);
					if (existingMaterial) {
						existingMaterial.consumption = parseInt(existingMaterial.consumption)
							+ parseInt(material.consumption);
					} else {
						sortedArray.push(newMaterial(material));
					}
				}
			});
		}

		function setChartData(materialArr, prop) {
			$scope.data = [];
			$scope.labels = [];
			materialArr.map(materialConsump => {
				$scope.labels.push(materialConsump[prop]);
				$scope.data.push(materialConsump.consumption);
			});
			initGraph();
		}

		function getProjReportSettings(projId) {
			for (const projSetting of projReportsSettings) {
				if (projSetting.projId == projId) {
					return projSetting;
				}
			}
			return null;
		}

		function initGraph() {
			$scope.series = ['Material'];
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);

		};


		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'date', displayName: "Date", headerTooltip: 'Date', visible: false, groupingShowAggregationMenu: false },
						{ field: 'eps', displayName: "EPS", headerTooltip: 'EPS Name' , groupingShowAggregationMenu: false},
						{ field: 'projName', displayName: "Project", headerTooltip: 'Project Name' , groupingShowAggregationMenu: false},
						{ field: 'costCodeDesc', displayName: "Cost Code Id", headerTooltip: 'Cost Code Id', visible: false, groupingShowAggregationMenu: false },
						{ field: 'costCodeName', displayName: "CostCode", headerTooltip: 'Costcode Name' , groupingShowAggregationMenu: false},
						{ field: 'costCodeGroup', displayName: "CostCode Group", headerTooltip: 'Costcode Group' , groupingShowAggregationMenu: false},					
						{ field: 'resourceMaterial', displayName: "Material", headerTooltip: 'Material' , groupingShowAggregationMenu: false},
						{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: 'Unit Of Measure' , groupingShowAggregationMenu: false},
						{ field: 'previousPeriod', displayName: "Prev Period", headerTooltip: 'Previous Period' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'consumption', displayName: "Rep Period", headerTooltip: 'Reporting Period' ,cellTemplate:'<div>{{row.entity.consumption > 0?row.entity.consumption:0}}</div>', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'upToDatePeriod', displayName: "U.T.D Period", headerTooltip: 'Up To Date Period' , groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						}},
						{ field: 'cumulativeConsumption', displayName: "Cumul Consum", headerTooltip: 'cumulativeConsumption', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} }

					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_MaterialPeriodicalConsumptionReport");
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
					template: 'views/help&tutorials/reportshelp/materialshelp/matperiodconsumphelp.html',
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