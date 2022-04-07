'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matdatewisereport", {
		url: '/matdatewisereport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.dateconsumption.html',
				controller: 'MaterialDateWiseReport'
			}
		}
	})
}]).controller("MaterialDateWiseReport", ["$scope","$q", "ngDialog", "$filter", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "CostCodeMultiSelectFactory", "MaterialMultiSelectFactory",
	"WorkDiaryService", "dateGroupingService", "ProjectSettingsService", "stylesService", "ngGridService", "chartService",
	function ($scope,$q, ngDialog, $filter, GenericAlertService, EpsProjectMultiSelectFactory,
		CostCodeMultiSelectFactory, MaterialMultiSelectFactory, WorkDiaryService,
		dateGroupingService, ProjectSettingsService, stylesService, ngGridService, chartService) {
		$scope.stylesSvc = stylesService;
		chartService.getChartMenu($scope);
		$scope.yAxislabels = 'Qty';
		$scope.type = 'table';
		$scope.series = ["Materials"];

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

		$scope.labels = [];
		$scope.data = [];
		let projReportsSettings = null;

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
				$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy"); $scope.subReportCode = "";
				$scope.materialNameConsumption = [];
				$scope.costcodeWiseConsumption = [];
				$scope.epsWiseConsumption = [];
				$scope.projWiseConsumption = [];
				$scope.periodicalConsumption = [];
				$scope.overallConsumption = [];
				$scope.subReportCode = null;
				$scope.type = 'table';
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
			} else if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
			return false;
		}

		function formatDates(data) {
			data.map(material => {
				$scope.formatDate(material);
			});
			return data;
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

		function getProjReportSettings(projId) {
			for (const projSetting of projReportsSettings) {
				if (projSetting.projId == projId) {
					return projSetting;
				}
			}
			return null;
		}

		$scope.formatDate = function (material) {
			material.date = moment(material.date).format('DD-MMM-YYYY');
			return material.date;
		}

		$scope.setChartData = function (materialArr, prop) {
			$scope.data = [];
			$scope.labels = [];
			materialArr.map(materialConsump => {
				$scope.labels.push(materialConsump[prop]);
				$scope.data.push(materialConsump.consumption);
			});
			initGraph();
		}

		function initGraph() {
			$scope.series = ["Materials"];
			$scope.datasetOverride = new Array();
			$scope.chart_type = 'bar';
			chartService.defaultBarInit($scope.yAxislabels);
		};


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
					$scope.periodicalDetails = formatDates(data.ledgerRes);
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

					let groupData = dateGroupingService.groupByDate($scope.periodicalDetails);
					$scope.periodicalConsumption = [];
					groupData.map(data => {
						let sortedArray = new Array();
						sortData(data.values, sortedArray, addNewConsumptionMaterial, 'materialId');
						$scope.periodicalConsumption = $scope.periodicalConsumption.concat(sortedArray);
					});

					$scope.setChartData($scope.periodicalConsumption, 'date');
					$scope.gridOptions.data = angular.copy($scope.periodicalConsumption);
					if ($scope.periodicalConsumption.length <= 0) {
						GenericAlertService.alertMessage("Material-Datewise Consumption not available for the search criteria", 'Warning');
					}
				}, function (data) {
					GenericAlertService.alertMessage("Error occured while getting Material report details.", 'Error');
				});
			}
		},

			$scope.$watch(function () { return stylesService.finishedStyling; },
				function (newValue, oldValue) {
					if (newValue) {
						let columnDefs = [
							{ field: 'date', displayName: "Date", headerTooltip: "Date",groupingShowAggregationMenu: false },
							{ field: 'resourceMaterial', displayName: "Material", headerTooltip: "Material",groupingShowAggregationMenu: false },
							{ field: 'unitOfMeasure', displayName: "Units", headerTooltip: "Unit Of Measure",groupingShowAggregationMenu: false },
							{ field: 'consumption', displayName: "Reporting Period Qty", headerTooltip: "Reporting Period Qty",cellTemplate:'<div>{{row.entity.consumption >0?row.entity.consumption:0}}</div>',groupingShowAggregationMenu: false }
						]
						let data = [];
						$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data,"Reports_Materials_DateWise_Consumption");
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
						template: 'views/help&tutorials/reportshelp/materialshelp/matdateconsumphelp.html',
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