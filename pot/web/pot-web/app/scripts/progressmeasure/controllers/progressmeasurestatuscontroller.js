'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("progressmeasureprojstatus", {
		url: '/progressmeasureprojstatus',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/projectstatus/progressmeasurestatusview.html',
				controller: 'ProgressMeasureProjectStatusController'
			}
		}
	})
}]).controller('ProgressMeasureProjectStatusController', ["$rootScope", "$scope", "ProjSOWService", "$q", "blockUI",
	"ProjectStatusService", "GenericAlertService", "ProjManPowerFactory", "ProjectCrewPopupService", "EpsService",
	"ProjectSettingCostItemFactory", "ProjectSettingSOWItemFactory", "ProjStatusMileStonesFactory",
	"ProjStatusSubContractFactory", "$filter", "TreeService", "ProjectStatusResourceStatusValueService","ngDialog",
	function ($rootScope, $scope, ProjSOWService, $q, blockUI,
		ProjectStatusService, GenericAlertService, ProjManPowerFactory, ProjectCrewPopupService, EpsService,
		ProjectSettingCostItemFactory, ProjectSettingSOWItemFactory, ProjStatusMileStonesFactory,
		ProjStatusSubContractFactory, $filter, TreeService, ProjectStatusResourceStatusValueService,ngDialog) {

		$rootScope.projId = null;
		$scope.treeData = [];
		var deferred = $q.defer();

		$scope.projProgressStatus = [];
		$scope.projDurationStatus = [];



		$scope.moreFlag = 0;
		$scope.currentTab = null;

		$scope.projSummaryTab = null;

		$scope.projStatusTab = null;



		var editNoteBook = [];
		var selectedPlants = [];
		var selectedManpower = [];
		var selectedMeterials = [];



		$scope.editing = false;
		$scope.supervisors = [];
		$scope.reqNumbers = [];

		$scope.resourceTypes = ['Direct Labour Hours', 'Indirect Labour Hours', 'Labour Cost', 'Plant Cost', 'Materials Cost', 'Others Cost',
			'Total Cost'];

		$scope.resourceStatus = {
			originalBudget: '',
			revisedBudget: '',
			actual: '',
			remaining: ''
		};

		$scope.projTabs = [];
		$scope.projMeasureDetails = [];

		$scope.date = new Date();
		$scope.measureToDate = new Date();
		var endDate = new Date($scope.measureToDate.getFullYear(), $scope.measureToDate.getMonth() - 1, 0);
		$scope.measureFromDate = new Date($scope.measureToDate);
		$scope.measureFromDate.setDate($scope.measureToDate.getDate() - endDate.getDate());

		var defaultFromDate = new Date($scope.measureFromDate);
		var defaultToDate = new Date($scope.measureToDate);

		$scope.measureFromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.measureToDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

		$scope.projProgressStatus = [{
			"id": null,
			"projId": null,
			"status": 1,
			"resumeDate": null,
			"suspendedDate": null,
			"startDate": null,
			"finishDate": null
		}];

		$scope.mileStones = [];

		var mileStoneSelected = [];

		$scope.projDurationStatus = [{
			"id": null,
			"projId": null,
			"status": 1,
			"originalDuration": null,
			"foreCastDuration": null,
			"actualDuration": null,
			"remainingDuration": null,
			"completionDuration": null
		}];

		$scope.measureunits = [];
		$scope.plantunits = [];
		$scope.costunits = [];
		$scope.dateunits = [];
		$scope.tableData = [];
		$scope.plantUnitsMap = [];

		var getReq = {
			"status": 1,
			"projId": null
		};

		$scope.projStatusReq =
			{
				"contractType": 'Select',
				"measureFromDate": $filter('date')((defaultFromDate), "dd-MMM-yyyy"),
				"measureToDate": $filter('date')((defaultToDate), "dd-MMM-yyyy"),
				"resourceType": 'Total Cost'
			}
		// leaf highlighted
		$scope.setClickedRow = function (row) {
			$scope.selectedRow = row;
		}
		// end
		/*---------Project Tabs----------*/

		$scope.preventCopyPaste = function () {
			$(document).ready(function () {
				$("body").on("contextmenu cut copy paste", function (e) {
					return false;
				});

			});

		};


		$scope.projTabs = [{
			title: '	Progress Measure',
			url: 'views/projectstatus/progressmeasure.html',
			appCodeTemplateKey: 'PRJ_PROGRESS_MEASURE_VIEW',
			SelenumLocator: 'Projects_ProjectStatus_ProgressMeasureTab'

		}]

		$scope.currentTab = $scope.projTabs[0].url;

		$scope.isActiveTab = function (tabUrl) {
			return tabUrl == $scope.currentTab;
		};

		$scope.onClickTab = function (tab) {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select project", "Warning");
				return;
			}
			if ($scope.projTabs[0].url == tab.url) {
				$scope.getSOWItems();
				//reset to default data on tab switch
				$scope.projStatusReq.contractType = 'Head-Company';
				$scope.selectContractType();
				$scope.projStatusReq.measureFromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
				$scope.projStatusReq.measureToDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
				$scope.getProjStatusActualQty();
            }
            // removing code

			// if ($scope.projTabs[1].url == tab.url) {
			// 	$scope.clickProjSummaryTab(tab.childTabs[0]);
			// }
			// if ($scope.projTabs[2].url == tab.url) {
			// 	$scope.clickProjStatusTab(tab.childTabs[0]);
			// }
            
            $scope.currentTab = tab.url;
			$scope.projSummaryTab = 'views/projectstatus/manpowerunits.html';
		};

		$scope.selectContractType = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select project", "Info");
				$scope.projStatusReq.contractType = 'Select';
			}
			if ($scope.projStatusReq.contractType == 'Head-Company' && $scope.generalValues) {
				$scope.projStatusReq.contractNumber = $scope.generalValues.contractNumber;
			} else {
				$scope.projStatusReq.contractNumber = null;
			}

		};

		$scope.selectSubContract = function () {
			if ($rootScope.projId == undefined || $rootScope.projId == null) {
				GenericAlertService.alertMessage("Please select project  to get sub-contracts", 'Warning');
				return;
			}
			var req = {
				"status": 1,
				"projId": $rootScope.projId,
				"procureType": 'SOW'
			};
			ProjStatusSubContractFactory.getProjectPurchaseOrders(req).then(function (data) {
				$scope.projStatusReq.purId = data.purchaseOrderTO.id;
				$scope.projStatusReq.contractNumber = data.purchaseOrderTO.code;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting sub-contract", 'Error');
			});
		};

		$scope.getProjGenerals = function () {
			var getProjGeneralsReq = {
				"status": "1",
				"projId": $rootScope.projId
			};
			ProjectStatusService.getProjGenerals(getProjGeneralsReq).then(function (data) {
				// $scope.getSOWItems();
				$scope.generalValues = data.projGeneralMstrTO;
				$scope.projStatusReq.contractType = 'Head-Company';
				$scope.selectContractType();
				//	$scope.getProjStatusActualQty();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
			});
		};

        // removing code

		/*---------Project Summary Tabs----------*/

		// $scope.projSummaryTab = $scope.projTabs[1].childTabs[0].url;
		// $scope.clickProjSummaryTab = function (tab) {
		// 	if ($scope.projTabs[1].childTabs[0].url == tab.url) {
		// 		$scope.getMeasureUnitsRecords();
		// 	} else if ($scope.projTabs[1].childTabs[1].url == tab.url) {
		// 		$scope.getPlantUnitsRecords();
		// 	} else if ($scope.projTabs[1].childTabs[2].url == tab.url) {
		// 		$scope.getCostUnitsRecords();
		// 	} else if ($scope.projTabs[1].childTabs[3].url == tab.url) {
		// 		$scope.getProjMileStonesRecords();
		// 	}
		// 	$scope.projSummaryTab = tab.url;
		// };

		// $scope.projSummaryActiveTab = function (summaryurlValue) {
		// 	return summaryurlValue == $scope.projSummaryTab;
		// }


		// /*---------Project Status Tabs----------*/

		// $scope.projStatusTab = $scope.projTabs[2].childTabs[0].url;

		// $scope.clickProjStatusTab = function (tab) {
		// 	if ($scope.projTabs[2].childTabs[0].url == tab.url) {
		// 		$scope.getProgressStatusRecords();
		// 	} else if ($scope.projTabs[2].childTabs[1].url == tab.url) {
		// 		$scope.getDurationStatusRecords();
		// 	} else if ($scope.projTabs[2].childTabs[2].url == tab.url) {
		// 		$scope.getResourceStatusRecords();
		// 	}
		// 	$scope.projStatusTab = tab.url;
		// };

		$scope.projStatusActiveTab = function (summaryurlValue) {
			return summaryurlValue == $scope.projStatusTab;
		}


		$scope.getResourceStatusRecords = function () {
			$scope.changeResourceStatus();
		};

		$scope.saveProjProgressStatusDates = function (projProgressStatus) {

			var projProgressStatusLocal = projProgressStatus[0];
			const actualStartDate = new Date(projProgressStatusLocal.startDate);
			const actualFinishDate = new Date(projProgressStatusLocal.finishDate);

			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please Select the Project", "Warning");
				return;
			} else if (projProgressStatusLocal.scheduleStartDate && projProgressStatusLocal.scheduleFinishDate
				&& new Date(projProgressStatusLocal.scheduleStartDate) >= new Date(projProgressStatusLocal.scheduleFinishDate)) {
				GenericAlertService.alertMessage("Schedule Finish Date should be greater than Schedule Start Date", 'Warning');
				return;
			} else if (projProgressStatusLocal.forecastStartDate && projProgressStatusLocal.forecastFinishDate
				&& new Date(projProgressStatusLocal.forecastStartDate) >= new Date(projProgressStatusLocal.forecastFinishDate)) {
				GenericAlertService.alertMessage("Forecast Finish Date should be greater than Forecast Start Date", 'Warning');
				return;
			} else if (actualStartDate && actualFinishDate && actualStartDate >= actualFinishDate) {
				GenericAlertService.alertMessage("Actual Finish Date should be greater than Actual Start Date", 'Warning');
				return;
			} else if (projProgressStatusLocal.suspendedDate && actualStartDate
				&& new Date(projProgressStatusLocal.suspendedDate) <= actualStartDate) {
				GenericAlertService.alertMessage("Suspension Date should be greater than Actual Start Date", 'Warning');
				return;
			} else if (projProgressStatusLocal.resumeDate && projProgressStatusLocal.suspendedDate
				&& new Date(projProgressStatusLocal.resumeDate) <= new Date(projProgressStatusLocal.suspendedDate)) {
				GenericAlertService.alertMessage("Resume Date should be greater than Suspension  Date", 'Warning');
				return;
			} else if (projProgressStatusLocal.resumeDate && actualFinishDate
				&& new Date(projProgressStatusLocal.resumeDate) >= actualFinishDate) {
				GenericAlertService.alertMessage("Resume Date should be less than Actual Finish Date", 'Warning');
				return;
			} else if (projProgressStatusLocal.suspendedDate && actualFinishDate
				&& new Date(projProgressStatusLocal.suspendedDate) >= actualFinishDate) {
				GenericAlertService.alertMessage("Actual Finish Date should be greater than Suspension Date", 'Warning');
				return;
			} else {
				projProgressStatus[0].projId = $rootScope.projId

				var saveReq = {
					"projStatusDatesTOs": projProgressStatus
				};

				ProjectStatusService.saveProjStatusDates(saveReq).then(function (data) {
					GenericAlertService.alertMessage('Project Status Dates  is/are ' + data.message, data.status);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while saving project status dates", "Error");
				});
			}

		};

		$scope.saveProjDurationStatus = function (origDuration, fcastDuration, actDuration, remDuration, completion) {
			$scope.originalDuration = origDuration;
			$scope.foreCastDuration = fcastDuration;
			$scope.actualDuration = actDuration;
			$scope.remainingDuration = remDuration;
			$scope.completionDuration = completion;

			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please Select the Project", "Warning");
				return;
			}
			else {
				$scope.projDurationStatus[0].id = $scope.projProgressStatus[0].id;
				$scope.projDurationStatus[0].projId = $rootScope.projId;
				$scope.projDurationStatus[0].originalDuration = $scope.originalDuration;
				$scope.projDurationStatus[0].foreCastDuration = $scope.foreCastDuration;
				$scope.projDurationStatus[0].actualDuration = $scope.actualDuration;
				$scope.projDurationStatus[0].remainingDuration = $scope.remainingDuration;
				$scope.projDurationStatus[0].completionDuration = $scope.completionDuration;

				var saveReq = {
					"projStatusDatesTOs": $scope.projDurationStatus
				};

				ProjectStatusService.saveProjDurationStatus(saveReq).then(function (data) {
					GenericAlertService.alertMessage('Project Duration Status  is/are ' + data.message, data.status);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while saving Project Duration Status", "Error");
				});

			}

		};

		$scope.populateDuration = function (origDuration) {

			//$scope.originalDuration = origDuration;
			var date = new Date();

			//completion logic
			/* var actstart=$rootScope.actStartDate;
			 var actfinish=$rootScope.actFinishDate;*/

			var completionDate2 = new Date($scope.projProgressStatus[0].finishDate);
			var completionDate1 = new Date($scope.projProgressStatus[0].startDate);

			var compTimeDiff = Math.abs(completionDate2.getTime() - completionDate1.getTime());
			var daysCompilation = Math.ceil(compTimeDiff / (1000 * 3600 * 24));
			$scope.completionDuration = daysCompilation;


			//Actual Duration logic	   
			var currentDate = new Date(date);

			var actDurationTimeDiff = Math.abs(currentDate.getTime() - completionDate1.getTime());
			var daysActDuration = Math.ceil(actDurationTimeDiff / (1000 * 3600 * 24));
			$scope.actualDuration = daysActDuration;

			//Remaining Duration logic
			$scope.remainingDuration = origDuration - $scope.actualDuration;


		};


		/*------------Progress tabs-----------*/
		$scope.onClickProgressTab = function (progressTabs) {
			$scope.currentProgressTab = progressTabs.urlValue;
		};

		$scope.isActiveProgressTab = function (progressTaburlValue) {
			return progressTaburlValue == $scope.currentProgressTab;
		};

        $scope.getSOWItems = function (projId) {
			var sowReq = {
				"projId": $rootScope.projId,
				"status": "1",
				"loggedInUser" : $rootScope.account.userId
			};
			ProjSOWService.getProjSOWDetails(sowReq).then(function (data) {
				$scope.activeFlag = 1;
				$scope.SOWData = TreeService.populateTreeData(data.projSOWItemTOs, 0, [], 'code', 'childSOWItemTOs');
				if ($scope.SOWData <= 0) {
					GenericAlertService.alertMessage("SOW Detail(s) is/are not available for given search criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
			});
		};

		/* EPS Projects */
		$scope.openSettings = function (projId, row) {
			$scope.selectedRow = row;
			$rootScope.projId = projId;
			$scope.onClickTab($scope.projTabs[0]);
			//		$scope.clickProjSummaryTab($scope.projTabs[1].childTabs[0]);
			$scope.getProjGenerals();
			$scope.selectedRow = row;
		};

		$scope.getEPSDetails = function () {
			var epsReq = {
				"status": 1
			};
			EpsService.getEPSUserProjects(epsReq).then(function (data) {
				$scope.epsData = populateData(data.epsProjs);
				for (const eps of $scope.epsData) {
					$scope.projectStatusItemClick(eps, false);
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
			});
		};

		function populateData(data) {
			return TreeService.populateTreeData(data, 0, [], 'projCode', 'childProjs');
		}

		// ------------------------------------------------------------------------------------------------------------------//
		$scope.calcEstimateToComplete = function (budgetObj, formulaType) {
			var BAC = 0;
			if (budgetObj.revisedQty != undefined && budgetObj.revisedQty > 0) {
				BAC = budgetObj.revisedQty - budgetObj.actualQty;
			} else {
				BAC = budgetObj.originalQty - budgetObj.actualQty
			}
			if (formulaType == 1) {
				budgetObj.estimateComplete = BAC;
				return budgetObj.estimateComplete;
			} else if (formulaType == 2) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue);
				return budgetObj.estimateComplete;
			} else if (formulaType == 3) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.costPermIndex;
				return budgetObj.estimateComplete;
			} else if (formulaType == 4) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.costPermIndex * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			} else if (formulaType == 5) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / budgetObj.progressFactor;
				return budgetObj.estimateComplete;
			} else if (formulaType == 6) {
				budgetObj.estimateComplete = (BAC - budgetObj.earnedValue) / (budgetObj.progressFactor * budgetObj.schedulePermIndex);
				return budgetObj.estimateComplete;
			}

		}

		$scope.saveSOWDetails = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select project", "Info");
			}
			var sowSaveReq = {
				"projSOWItemTOs": $scope.SOWData,
				"projId": $scope.projId
			};
			blockUI.start();
			ProjSOWService.saveSOWItems(sowSaveReq).then(function (data) {
				blockUI.stop();
				if (data.status != null && data.status !== undefined && data.status === 'Info') {
					var projSOWTOs = data.projSOWItemTOs;
					// var succMsg = GenericAlertService.alertMessageModal('Progress Measure(s) is/are ' + data.message, data.status);
					var succMsg = GenericAlertService.alertMessageModal('Progress Measure(s) saved successfully ',"Info" );
					succMsg.then(function (data) {
						var returnPopObj = {
							"projSOWItemTOs": projSOWTOs
						};
						deferred.resolve(returnPopObj);
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage("SOW(s) is/are Failed to Save", "Error");
					});
				}
			});
		};

		$scope.calcEstimateAtCompletion = function (budgetObj) {
			budgetObj.estimateCompletion = budgetObj.actualQty + budgetObj.estimateComplete;
			return budgetObj.estimateCompletion;
		};

		/* Man Power Tab */	/* Man Power Tab */
		$scope.getManpowerRecords = function () {
			var getManpowerReq = {
				"status": "1",
				"projId": $rootScope.projId
			};
			ProjectStatusService.getProjManpowers(getManpowerReq).then(function (data) {
				$scope.projManpowerDetails = data.projManpowerTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
			});
		};

		$scope.manPowerRowSelect = function (manpower) {
			if (manpower.select) {
				editManpower.push(manpower);
			} else {
				editManpower.splice(editManpower.indexOf(manpower), 1);
			}
		};

		$scope.editManPowerDetails = function (actionType, projId) {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select project", "Info");

				return;
			}
			angular.forEach(editManpower, function (value) {
				value.select = false;
			});
			if (actionType == 'Edit' && editManpower <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
				return;
			} else if ($scope.projId !== undefined && $scope.projId != null) {
				var popupDetails = ProjManPowerFactory.manPowerPopupDetails(actionType, projId, editManpower);
				popupDetails.then(function (data) {
					$scope.projManpowerDetails = data.projManpowerTOs;
					editManpower = [];
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while selecting Project Man Power Details", 'Info');
				})
			}
		};

		$scope.calcRemainingUnits = function (manpower) {
			ProjManPowerFactory.manPowerPopupDetails().calcRemainingUnits(manpower);
		};

		/* Progress Measure Tab */

		$scope.getProjMeasureRecords = function () {
			var getProgMeasureReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectStatusService.getProjProgMeasure(getProgMeasureReq).then(function (data) {
				$scope.projMeasureDetails = data.projProgressTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Progress Measure Details", "Error");
			});
		};

		$scope.saveProgMeasures = function () {
			angular.forEach($scope.projMeasureDetails, function (value) {
				angular.forEach(value.childProjProgressTOs, function (value1) {
					angular.forEach(value1.childProjProgressTOs, function (value2) {
						var fromDate = new Date(value2.fromDate);
						var toDate = new Date(value2.toDate);
						if (fromDate > toDate) {
							GenericAlertService.alertMessage('Start Date must be less than  Finish Date', 'Warning');
							forEach.break();
							return;
						}
					})
				})
			})
			var saveProgMeasureReq = {
				"projProgressTOs": $scope.projMeasureDetails
			};
			blockUI.start();
			ProjectStatusService.saveProjProgMeasure(saveProgMeasureReq).then(function (data) {
				blockUI.stop();
				$scope.getProjMeasureRecords();
				GenericAlertService.alertMessage('Project Progress Measure(s) is/are ' + data.message, data.status);
			}, function (error) {
				blockUI.stop();
				GenericAlertService.alertMessage('Project Progress Measure(s) is/are failed to save', "Error");
			});
		};

        // removing code

		/* Man Power Tab */
		// $scope.getMeasureUnitsRecords = function (fromTitle) {
		// 	var getMeasureUnitsReq = {
		// 		"status": 1,
		// 		"projId": $rootScope.projId
		// 	};
		// 	ProjectStatusService.getMeasureUnits(getMeasureUnitsReq).then(function (data) {
		// 		$scope.measureunits = data.projManPowerStatusTOs;
		// 		if (fromTitle && $scope.projTabs[2].childTabs[2].title == fromTitle) {
		// 			angular.forEach($scope.measureunits, function (unit) {
		// 				if ((unit.empCatgName == 'DIRECT' && $scope.projStatusReq.resourceType == $scope.resourceTypes[0])
		// 					|| (unit.empCatgName == 'IN-DIRECT' && $scope.projStatusReq.resourceType == $scope.resourceTypes[1])) {
		// 					$scope.resourceStatus = {
		// 						originalBudget: unit.originalQty,
		// 						revisedBudget: unit.revisedQty,
		// 						actual: unit.actualQty,
		// 						remaining: (unit.revisedQty ? unit.revisedQty : unit.originalQty) - unit.actualQty
		// 					};
		// 				}
		// 			});
		// 		}
		// 	}, function (error) {
		// 		GenericAlertService.alertMessage("Error occured while getting Man Power Units", "Error");
		// 	});
		// };

		$scope.getTotal = function (type) {
			var total = 0;
			angular.forEach($scope.measureunits, function (el) {
				if (type == 'ageSpent') {
					total += (el['actualQty'] / (el['revisedQty'] ? el['revisedQty'] : el['originalQty'])) * 100;
				} else if (type == 'estimateCompletion') {
					total += (el['actualQty'] + el['estimateComplete']);
				} else if (type == 'compVariance') {
					total += ((el['actualQty'] + el['estimateComplete']) - (el['revisedQty'] ? el['revisedQty'] : el['originalQty']));
				} else {
					total += el[type];
				}
			});
			return total;
		};

		$scope.getTotalFrom = function (fieldName, fromArray) {
			var total = 0;
			angular.forEach(fromArray, function (el) {
				total += el[fieldName];
			});
			return total;
		};

		/* Plant Tab */
		$scope.getPlantUnitsRecords = function () {
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please Select the Project", "Warning");
				return;
			}

			var getPlantUnitsReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectStatusService.getPlantUnits(getPlantUnitsReq).then(function (data) {
				$scope.plantunits = data.projectPlantsStatusTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Plant Units", "Error");
			});
		};

        // removing code
        
		/* Cost Tab */
		// $scope.getCostUnitsRecords = function (fromTitle) {
		// 	if ($rootScope.projId == null || $rootScope.projId == undefined) {
		// 		GenericAlertService.alertMessage("Please Select the Project", "Warning");
		// 		return;
		// 	}

		// 	var getCostUnitsReq = {
		// 		"status": 1,
		// 		"projId": $rootScope.projId
		// 	};
		// 	ProjectStatusService.getCostUnits(getCostUnitsReq).then(function (data) {
		// 		$scope.projCostStatementsSummaryTOs = data.projCostStatementsSummaryTOs;
		// 		if (fromTitle && $scope.projTabs[2].childTabs[2].title == fromTitle)
		// 			$scope.resourceStatus = ProjectStatusResourceStatusValueService.getResourceStatusValues(
		// 				$scope.projCostStatementsSummaryTOs, $scope.projStatusReq, $scope.resourceTypes);
		// 	}, function (error) {
		// 		GenericAlertService.alertMessage("Error occured while getting Cost Units", "Error");
		// 	});
		// };

		/* MileStones Tab */

		// $scope.getProjMileStonesRecords = function () {

		// 	mileStoneSelected = [];

		// 	if ($rootScope.projId == null || $rootScope.projId == undefined) {
		// 		GenericAlertService.alertMessage("Please Select the Project", "Warning");
		// 		return;
		// 	}

		// 	var getMileStonesReq = {
		// 		"status": 1,
		// 		"projId": $rootScope.projId
		// 	};
		// 	ProjectStatusService.getProjStatusMileStones(getMileStonesReq).then(function (data) {
		// 		$scope.mileStones = data.projMileStonesTOs;
		// 	}, function (error) {
		// 		GenericAlertService.alertMessage("Error occured while getting Project Status MileStones", "Error");
		// 	});
		// };

		// $scope.changeResourceStatus = function () {
		// 	$scope.resourceStatus = {
		// 		originalBudget: '',
		// 		revisedBudget: '',
		// 		actual: '',
		// 		remaining: ''
		// 	};
		// 	if ($scope.projStatusReq.resourceType == $scope.resourceTypes[0] || $scope.projStatusReq.resourceType == $scope.resourceTypes[1]) {
		// 		$scope.getMeasureUnitsRecords($scope.projTabs[2].childTabs[2].title);
		// 	} else {
		// 		$scope.getCostUnitsRecords($scope.projTabs[2].childTabs[2].title);
		// 	}
		// };

		/* MileStones Tab */
		$scope.populateMileStonePopup = function (actionType) {

			var editMileStone = [];
			if ($rootScope.projId == null || $rootScope.projId == undefined) {
				GenericAlertService.alertMessage("Please select the Project", "Warning");
				return;
			}

			if (actionType == 'Add') {
				editMileStone = [];
			} else if (actionType == 'Edit') {
				if (mileStoneSelected.length <= 0) {
					GenericAlertService.alertMessage("Please select a milestone", "Warning");
					return;
				}
				if (mileStoneSelected.length > 1) {
					GenericAlertService.alertMessage("Please select single milestone", "Warning");
					return;
				}

				for (var i = 0; i < $scope.mileStones.length; i++) {
					if ($scope.mileStones[i].id === mileStoneSelected[0].id) {
						editMileStone.push($scope.mileStones[i]);
					}
				}
			}

			ProjStatusMileStonesFactory.mileStoneDetails(actionType, editMileStone, $rootScope.projId).then(function (data) {
				mileStoneSelected = [];
				$scope.mileStones = data.projMileStonesTOs;
			});

		};

		$scope.mileStoneRowSelect = function (mile) {
			if (mile.selected) {
				mileStoneSelected.push(mile);
			} else {
				mileStoneSelected.splice(mileStoneSelected.indexOf(mileStoneSelected), 1)
			}
		};

		$scope.mileStoneDelete = function () {

			if (mileStoneSelected.length <= 0) {
				GenericAlertService.alertMessage("Please select milestones", "Warning");
				return;
			}

			var deleteMileStonesReq = {
				"projMileStonesTOs": mileStoneSelected
			};

			ProjectStatusService.deleteProjStatusMileStones(deleteMileStonesReq).then(function (data) {
				GenericAlertService.alertMessage("Project Status MileStones deleted successfully", data.status);
				$scope.getProjMileStonesRecords();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while deleting milestones", "Error");
			});


		}


		/* Project Status Tab */
		/*Progress Status */

		$scope.getProgressStatusRecords = function () {

			var getProgressStatusReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectStatusService.getProjStatusDates(getProgressStatusReq).then(function (data) {
				$scope.projProgressStatus = data.projStatusDatesTOs;
				$scope.changeResourceStatus();
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Progress Status", "Error");
			});
		};

		$scope.getDurationStatusRecords = function () {

			var getDurationStatusReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			ProjectStatusService.getProjStatusDates(getDurationStatusReq).then(function (data) {
				$scope.projProgressStatus = data.projStatusDatesTOs;
				$scope.projDurationStatus = data.projStatusDatesTOs;
				$scope.originalDuration = $scope.projDurationStatus[0].originalDuration;
				$scope.foreCastDuration = $scope.projDurationStatus[0].foreCastDuration;
				$scope.actualDuration = $scope.projDurationStatus[0].actualDuration;
				$scope.remainingDuration = $scope.projDurationStatus[0].remainingDuration;
				$scope.completionDuration = $scope.projDurationStatus[0].completionDuration;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Progress Status", "Error");
			});
		};

		$scope.spent = function (projManPowerStatus) {
			if (projManPowerStatus.revisedQty != undefined) {
				return projManPowerStatus.actualQty / projManPowerStatus.originalQty;
			} else {
				return projManPowerStatus.actualQty / projManPowerStatus.revisedQty;
			}

		};

		$scope.spentCost = function (budgetCosts) {
			var cost = 0;
			if (budgetCosts.length > 2) {
				if (budgetCosts[1].cost != undefined && budgetCosts[1].cost != null && budgetCosts[2].cost != undefined && budgetCosts[2].cost != null) {
					cost = parseFloat(parseFloat(budgetCosts[2].cost) / parseFloat(budgetCosts[1].cost));
				}
			} else {
				cost = parseFloat(parseFloat(budgetCosts[1].cost) / parseFloat(budgetCosts[0].cost));
			}
			return cost;
		};

		/* Note Book Tab */

		$scope.crewList = function (projectCrew) {
			var crewReq = {
				"status": 1,
				"projId": $rootScope.projId
			};
			var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
			crewSerivcePopup.then(function (data) {
				projectCrew.projCrewId = data.projCrewTO.id;
				projectCrew.projCrewCode = data.projCrewTO.code;
				projectCrew.crewName = data.projCrewTO.desc;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Crew List Details", 'Error');
			});
		};
		var ApproverSerivcePopup = [];
		$scope.approversList = function (projectApprover) {
			ApproverSerivcePopup = ProjectCrewPopupService.approverDetailsList($rootScope.projId);
			ApproverSerivcePopup.then(function (data) {
				projectApprover.apprUserId = data.projApproverTO.userId;
				projectApprover.apprUserCode = data.projApproverTO.firstName;
				projectApprover.apprUserName = data.projApproverTO.lastName;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Approver List Details", 'Error');
			});
		};

		$scope.getProcurementNotifications = function (procurementNotification) {
			$scope.getNotificationsByModuleCode('PROCURMT', procurementNotification);
		};

		$scope.getEmpNotifications = function (empNotification) {
			$scope.getEmpNotifications('EMPTRSFR', empNotification);
		};

		$scope.getPlantNotifications = function (plantNotification) {
			$scope.getPlantNotifications('PLANTTRSFR', plantNotification);
		};

		$scope.getMaterialNotifications = function (materialNotification) {
			$scope.getMaterialNotifications('MATRLTRSFR', materialNotification);
		};

		$scope.getMaterialNotifications = function (notification) {
			var notificationPopup = [];
			notificationPopup = ProjectCrewPopupService.getMaterialNotificationDetails($scope.projId);
			notificationPopup.then(function (data) {
				notification.notificationId = data.projNotificationTO.id;
				notification.code = data.projNotificationTO.code;
				notification.date = data.projNotificationTO.date;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			});

		};
		$scope.getPlantNotifications = function (notification) {
			var notificationPopup = [];
			notificationPopup = ProjectCrewPopupService.getPlantNotificationDetails($scope.projId);
			notificationPopup.then(function (data) {
				notification.notificationId = data.projNotificationTO.id;
				notification.code = data.projNotificationTO.code;
				notification.date = data.projNotificationTO.date;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			});

		};
		$scope.getEmpNotifications = function (notification) {
			var notificationPopup = [];
			notificationPopup = ProjectCrewPopupService.getEmployeeNotificationDetails($scope.projId);
			notificationPopup.then(function (data) {
				notification.notificationId = data.projNotificationTO.id;
				notification.code = data.projNotificationTO.code;
				notification.date = data.projNotificationTO.date;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Notification Details", 'Error');
			});

		};

		var EPSPopUp = [];
		$scope.project = function (projClaim) {
			EPSPopUp = ProjectCrewPopupService.projctDetailList($rootScope.projId);
			EPSPopUp.then(function (data) {
				projClaim.projId = data.projEPSTO.projId;
				projClaim.code = data.projEPSTO.projCode;
				projClaim.name = data.projEPSTO.projName;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting Projects List Details", 'Error');
			});
		};

		$scope.addCostItemDetails = function () {
			var costCodePopup = [];
			costCodePopup = ProjectSettingCostItemFactory.getCostItemDetails($rootScope.projId);
			costCodePopup.then(function (data) {
				$scope.projCostStmtDtls = data.projCostStmtDtlTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error osccured while gettting  cost codes", 'Error');
			});
		};

		$scope.calculateBudgetAmount = function (tab) {
			var budgetAmount = tab.projCostBudgetTOs[0];
			tab.projCostBudgetTOs[0].total = budgetAmount.labourCost + budgetAmount.materialCost +
				budgetAmount.plantCost + budgetAmount.otherCost;
		};

		var originalBudgetTotal = 0;
		$scope.calculateRevisedBudget = function (tab) {
			if (tab.projCostBudgetTOs[1].total == undefined || tab.projCostBudgetTOs[1].total == NaN) {
				tab.projCostBudgetTOs[1].total = 0;
			}
			var revisedBudget = tab.projCostBudgetTOs[1];
			tab.projCostBudgetTOs[1].total = revisedBudget.labourCost +
				revisedBudget.materialCost + revisedBudget.plantCost +
				revisedBudget.otherCost;
		}

		var originalBudgetTotal = 0;
		var revisedBudgetTotal = 0;
		var actualCost = 100000;
		$scope.costPercentage = function (tab) {

			// var actualCost = tab.actualCost;
			var originalBudget = tab.projCostBudgetTOs[0];
			originalBudgetTotal = originalBudget.labourCost + originalBudget.materialCost + originalBudget.plantCost + originalBudget.otherCost;

			var revisedBudget = tab.projCostBudgetTOs[1];
			revisedBudgetTotal = revisedBudget.labourCost + revisedBudget.materialCost + revisedBudget.plantCost + revisedBudget.otherCost;

			if (revisedBudgetTotal != undefined && revisedBudgetTotal > 0) {
				tab.costPercentage = (actualCost / revisedBudgetTotal) * 100;
			} else if (originalBudgetTotal != undefined) {
				tab.costPercentage = (actualCost / originalBudgetTotal) * 100;
			}
		};

		$scope.percentageOfWork = function (tab) {
			var earnedValueAmount = tab.earnedValue;
			if (revisedBudgetTotal != undefined && revisedBudgetTotal > 0) {
				tab.percentageOfWork = (earnedValueAmount / revisedBudgetTotal) * 100;
			} else if (originalBudgetTotal != undefined) {
				tab.percentageOfWork = (earnedValueAmount / revisedBudgetTotal) * 100;
				tab.percentageOfWork = (earnedValueAmount / originalBudgetTotal) * 100;
			}
		};

		$scope.productivityFactor = function (tab) {
			var earnedAmount = tab.earnedValue;
			// var actualCost = tab.actualCost;
			if (earnedAmount != undefined && earnedAmount > 0) {
				tab.productivityFactor = (earnedAmount / actualCost) * 100;
			}
		};

		$scope.actualAmount = function (tab) {
			var workDairy = 0;
			var registers = 0;
			var empWages = 0;
			var timsSheet = 0;
			var projLib = 0;
			var attendance = 0;
			var purchaseOrder = 0;
			tab.projCostBudgetTOs[2].labourCost = ((workDairy.usedValue * registers.ratePerUnit * empWages.wageRate) * (workDairy.idleValue * registers.idleTimeRate * empWages.wageRate) + (timsSheet.timsSheetHrs * timsSheet.wageFactor) + (timsSheet.timeSheetExpenses) + (attendance.attenValue * projLib.paidLeaves) + (registers.mobilisationRate * registers.deMobilisationRate));
			tab.projCostBudgetTOs[2].materialCost = ((workDairy.value * purchaseOrder.purcahseOrderListRate) + purchaseOrder.paidAmount);
			tab.projCostBudgetTOs[2].plantCost = ((workDairy.usedValue * registers.rateWithFuel) + (workDairy.idleValue * registers.plantIdleTime));
			tab.projCostBudgetTOs[2].otherCost = purchaseOrder.paidAmount;
		};

		$scope.remainingAmount = function (tab) {
			var actualAmount = tab.projCostBudgetTOs[0];
			var revisedBudget = tab.projCostBudgetTOs[1];
			tab.projCostBudgetTOs[2].labourCost = actualAmount.labourCost - revisedBudget.labourCost;
			tab.projCostBudgetTOs[2].materialCost = actualAmount.materialCost - revisedBudget.materialCost;
			tab.projCostBudgetTOs[2].plantCost = actualAmount.plantCost - revisedBudget.plantCost;
			tab.projCostBudgetTOs[2].otherCost = actualAmount.otherCost - revisedBudget.otherCost;
			tab.projCostBudgetTOs[2].total = tab.projCostBudgetTOs[1].total - tab.projCostBudgetTOs[0].total;
		};
		$scope.applyProgsMeasure = function (fromDate, toDate) {

		};
		$scope.addSOWItems = function () {
			var sowPopup = [];
			sowPopup = ProjectSettingSOWItemFactory.getSOWItemDetails($rootScope.projId);
			sowPopup.then(function (data) {
				$scope.projMeasureDetails = data.projSOWDtlTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while gettting  cost codes", 'Error');
			});
		};

		$scope.checkErr = function (startDate, endDate) {
			$scope.errMessage = '';
			var curDate = new Date();
			if (!(startDate && endDate)) {
				$scope.errMessage = 'Please Select Date';
				return false;
			}
			if (new Date(startDate) >= new Date(endDate)) {
				$scope.errMessage = 'From Date less than To Date';
				return false;
			}
		};
		$scope.editItem = function () {
			$scope.editing = true;
		};

		$scope.projectStatusItemClick = function (item, collapse) {
			TreeService.treeItemClick(item, collapse, 'childProjs');
		};

		$scope.projectStatusSowItemClick = function (item, collapse) {
			TreeService.treeItemClick(item, collapse, 'childSOWItemTOs');
		};

		$scope.expandAll = function (item) {
			$scope.isExpand = true;
			$scope.itemId = item.id;
			expandChildsRecursively(item, true);
		};

		$scope.collapseAll = function (item) {
			$scope.isExpand = false;
			$scope.itemId = item.id;
			expandChildsRecursively(item, false);
		};

		/**
		  * Expan/collapse all childs recursively
		  * @param {*} parentObject 
		  * @param {*} isExpand
		  */
		function expandChildsRecursively(parentObject, isExpand) {
			var child = null;
			parentObject.expand = isExpand;
			for (var index = 0; index < parentObject.childSOWItemTOs.length; index++) {
				child = parentObject.childSOWItemTOs[index];
				child.expand = isExpand;
				if (child.childSOWItemTOs.length > 0) {
					expandChildsRecursively(child, isExpand);
				}
			}
		}

		$scope.getProjStatusActualQty = function () {
			var req = {
				"projId": $rootScope.projId,
				"fromDate": $scope.projStatusReq.measureFromDate,
				"toDate": $scope.projStatusReq.measureToDate,
				"contractCode": $scope.projStatusReq.contractType != 'Head-Company' ? $scope.projStatusReq.contractNumber : null,
				"purId": $scope.projStatusReq.purId
			};
			ProjectStatusService.getProjStatusActualQty(req).then(function (data) {
				$scope.projStatusActualTos = data.projStatusActualTos;
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting  project status dates", "Error");
			});
		};

		function itemexpandCollpase(item, expand) {
			angular.forEach(item.children, function (o) {
				o.expand = !expand;
				if (o.children != null && o.children.length > 0) {
					o.expand = false;
					itemexpandCollpase(o, expand);
				}
			});
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
			template: 'views/help&tutorials/projectshelp/progressmeasurehelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	}]).filter('slice', function () {
		return function (arr, start, end) {
			return (arr || []).slice(start, end);
		}
	}).filter('projCostFilterTree', function () {
		function recursive(obj, newObj, level, itemId, isExpand) {
			angular.forEach(obj, function (o) {
				if (o != undefined && o.projCostCodeItemTOs && o.projCostCodeItemTOs.length != 0) {
					o.level = level;
					o.leaf = false;
					newObj.push(o);
					if (o.id == itemId) {
						o.expand = isExpand;
					}
					if (o.expand == true) {
						recursive(o.projCostCodeItemTOs, newObj, o.level + 1, itemId, isExpand);
					}
				} else {
					o.level = level;
					if (o.item) {

						o.leaf = true;
					} else {
						o.leaf = false;
					}
					newObj.push(o);
					return false;
				}
			});
		}

		return function (obj, itemId, isExpand) {
			var newObj = [];
			recursive(obj, newObj, 0, itemId, isExpand);
			return newObj;
		}
	});