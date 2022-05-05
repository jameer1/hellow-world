'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("help", {
		url : '/help',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/help&tutorials/menu.html',
				controller : 'HelpAndTutorialController'
			}
		}
	})
}]).controller("HelpAndTutorialController", ["$scope", "$q", "$state", "ngDialog", "$rootScope", function($scope, $q, $state, ngDialog, $rootScope) {
	$scope.tasks = [ {
		title : 'Enterprise',
		url : 'views/help&tutorials/Enterprise.html'
	}, {
		title : 'Projects',
		url : 'views/help&tutorials/Projects.html'
	}, {
		title : 'Project Documents',
		url : 'views/help&tutorials/ProjectDocuments.html'
	}, {
		title : 'Central Library',
		url : 'null',
		subTasks : [ {
			title : 'Company List',
			url : 'views/help&tutorials/centrallibrary/ClCompanyList.html'
		}, {
			title : 'Measuring Units',
			url : 'views/help&tutorials/centrallibrary/CLMeasuringUnits.html'
		}, {
			title : 'Weather Classification',
			url : 'views/help&tutorials/centrallibrary/CLWeatherClassification.html'
		}, {
			title : 'Employee Classification',
			url : 'views/help&tutorials/centrallibrary/CLEmployeeClassification.html'
		}, {
			title : 'Plant Classification',
			url : 'views/help&tutorials/centrallibrary/ClPlantClassification.html'
		}, {
			title : 'Materials Classification',
			url : 'views/help&tutorials/centrallibrary/CLMaterialClassification.html'
		}, {
			title : 'Cost Code Classification',
			url : 'views/help&tutorials/centrallibrary/CLCostCodeClassification.html'
		}, {
			title : 'Employee Wage Factors',
			url : 'views/help&tutorials/centrallibrary/CLEmployeeWageRateFactors.html'
		}, {
			title : 'Service Classification',
			url : 'views/help&tutorials/centrallibrary/CLServiceClassification.html'
		} ]
	}, {
		title : 'Schedules',
		url : 'null',
		subTasks : [ {
			title : 'Resource Schedule',
			url : 'views/help&tutorials/scheduleshelp/resourceschedulehelp.html'
		}, {
			title : 'SOW Schedule',
			url : 'views/help&tutorials/scheduleshelp/sowschedulehelp.html'
		} ]
	},{
		title : 'Project Library',
		url : 'null',
		subTasks : [ {
			title : 'Employee Classification',
			url : 'views/help&tutorials/projectlibray/PLEmployeeClassification.html'
		}, {
			title : 'Plant Classification',
			url : 'views/help&tutorials/projectlibray/PlPlantClassification.html'
		}, {
			title : 'Leave Type and Attendance Records Code',
			url : 'views/help&tutorials/projectlibray/PlLeaveTypeCodes.html'
		}, {
			title : 'Working Shifts',
			url : 'views/help&tutorials/projectlibray/PLWorkingShift.html'
		}, {
			title : 'Crew List',
			url : 'views/help&tutorials/projectlibray/PLCrewList.html'
		}, {
			title : 'Schedule Of Estimated Quantities(SOE)',
			url : 'views/help&tutorials/projectlibray/PLSOE.html'
		}, {
			title : 'Schedule Of Rates(SOR)',
			url : 'views/help&tutorials/projectlibray/PLSOE.html'
		}, {
			title : 'Cost Code Schedule',
			url : 'views/help&tutorials/projectlibray/PLCostCodeSchedule.html'
		}, {
			title : 'Scope Of Works(SOW)',
			url : 'views/help&tutorials/projectlibray/PLScopeOfWork.html'
		} ]
	}, {
		title : 'Procurement',
		url : 'null',
		subTasks : [ {
			title : 'Pre-Contracts',
			url : 'views/help&tutorials/procurement/PreContracts.html'
		}, {
			title : 'Purchase Orders',
			url : 'views/help&tutorials/procurement/PurchaseOrders.html'
		} ]
	}, {
		title : 'Central Resource',
		url : 'null',
		subTasks : [ {
			title : 'Employee Registers',
			url : 'views/help&tutorials/centralresources/CREmployeeRegister.html'
		}, {
			title : 'Plant Registers',
			url : 'views/help&tutorials/centralresources/CRPlantRegister.html'
		}, {
			title : 'Material Registers',
			url : 'views/help&tutorials/centralresources/CRMaterialRegister.html'
		} ]
	}, {
		title : 'Work Dairy',
		url : 'null',
		subTasks : [ {
			title : 'Create Work Dairy',
			url : 'views/help&tutorials/workdairy/CreateWorkDairy.html'
		}, {
			title : 'Approve Work Dairy',
			url : 'views/help&tutorials/workdairy/ApproveWorkDairy.html'
		} ]
	}, {
		title : 'Staff Time Sheet',
		url : 'null',
		subTasks : [ {
			title : 'Create Time Sheet',
			url : 'views/help&tutorials/timesheet/CreateTimeSheet.html'
		}, {
			title : 'Approve Time Sheet',
			url : 'views/help&tutorials/timesheet/ApproveStaffTimeSheet.html'
		} ]
	}, {
		title : 'Attendance Records',
		url : 'null',
		subTasks : [ {
			title : 'Employee Attendance',
			url : 'views/help&tutorials/attendencerecords/EmployeeAttendance.html'
		}, {
			title : 'Plant Attendance',
			url : 'views/help&tutorials/attendencerecords/PlantAttendance.html'
		} ]
	}, {
		title : 'Project Invoices',
		url : 'null',
		subTasks : [ {
			title : 'Project Invoices',
			url : 'views/help&tutorials/projectinvoices/ProjectInvoices.html'
		}, {
			title : 'Invoices Reports',
			url : 'views/help&tutorials/projectinvoices/InvoiceReport.html'
		} ]
	}, {
		title : 'Dash Boards',
		url : 'null',
		subTasks : [ {
			title : 'Performance',
			url : 'views/help&tutorials/DBPerformance.html'
		}, {
			title : 'Progress',
			url : 'views/help&tutorials/DBProgress.html'
		}, {
			title : 'Time',
			url : 'views/help&tutorials/DBTime.html'
		}, {
			title : 'Budget',
			url : 'views/help&tutorials/DBBudget.html'
		}, {
			title : 'Cost',
			url : 'views/help&tutorials/DBCost.html'
		}, {
			title : 'Labour',
			url : 'views/help&tutorials/DBLabour.html'
		}, {
			title : 'Actual Cost',
			url : 'views/help&tutorials/DBActualCost.html'
		}, {
			title : 'Earned Value',
			url : 'views/help&tutorials/DBEarnedValue.html'
		}, {
			title : 'Remaining Budget',
			url : 'views/help&tutorials/DBRemainingBudget.html'
		}, {
			title : 'Estimate To Complete',
			url : 'views/help&tutorials/DBEstimateToComplete.html'
		}, {
			title : 'Estimate At Completion',
			url : 'views/help&tutorials/DBEstimateAtCompletion.html'
		}, {
			title : 'Common Reports',
			url : 'null',
			subTasks : [ {
				title : 'Daily Resources Summary',
				url : 'views/help&tutorials/DailyResourcesSummary.html'
			}, {
				title : 'Attendance Records',
				url : 'views/help&tutorials/AttendanceRecords.html'
			}, {
				title : 'Plant Attendance Records',
				url : 'views/help&tutorials/PlantAttendanceRecords.html'
			} ]
		} ]
	}, {
		title : 'Work Diary Reports',
		url : 'null',
		subTasks : [ {
			title : 'Daily Report',
			url : 'views/workdairyreports/help&tutorials/WDDailyReport.html'
		}, {
			title : 'Approval Status',
			url : 'views/workdairyreports/help&tutorials/WDApprovalStatus.html'
		}, ]
	}, {
		title : 'Time Sheet Reports',
		url : 'null',
		subTasks : [ {
			title : 'Daily Report',
			url : 'views/help&tutorials/timesheetreports/TSDailyReport.html'
		}, {
			title : 'Approval Status',
			url : 'views/help&tutorials/timesheetreports/TSApprovalStatus.html'
		}, ]
	}, {
		title : 'Man Power Reports',
		url : 'null',
		subTasks : [ {
			title : 'Periodical Reports',
			url : 'views/help&tutorials/manpowerreports/MPPeriodicalReports.html'
		}, {
			title : 'Daily Employee List',
			url : 'views/help&tutorials/manpowerreports/MPDailyEmployeesList.html'
		}, {
			title : 'Utilization Analysis',
			url : 'views/help&tutorials/manpowerreports/MPUtilisationAnalysis.html'
		}, {
			title : 'Idle Time Records',
			url : 'views/help&tutorials/manpowerreports/MPIdleTimeRecords.html'
		}, {
			title : 'Statistics',
			url : 'views/help&tutorials/manpowerreports/MPStatistics.html'
		}, {
			title : 'Mobilization Status',
			url : 'views/help&tutorials/manpowerreports/MPMobilizationStatus.html'
		}, {
			title : 'Employee Search',
			url : 'views/help&tutorials/manpowerreports/MPEmployeeSearch.html'
		}, {
			title : 'Periodical Man Hour Reports',
			url : 'views/help&tutorials/manpowerreports/MPPeriodicalManHourReports.html'
		} ]
	}, {
		title : 'Plant Reports',
		url : 'null',
		subTasks : [ {
			title : 'Periodical',
			url : 'views/help&tutorials/plantreports/Periodical.html'
		}, {
			title : 'Plant List',
			url : 'views/help&tutorials/plantreports/PlantList.html'
		}, {
			title : 'Utilization Analysis',
			url : 'views/help&tutorials/plantreports/UtilisationAnalysis.html'
		}, {
			title : 'Idle Time Records',
			url : 'views/help&tutorials/plantreports/IdleTimeRecords.html'
		}, {
			title : 'Plant Search',
			url : 'views/help&tutorials/plantreports/PlantSearch.html'
		} ]
	}, {
		title : 'Material Reports',
		url : 'null',
		subTasks : [ {
			title : 'Periodical Consumption',
			url : 'views/help&tutorials/materialreports/PeriodicalConsumption.html'
		}, {
			title : 'Inventory Reports',
			url : 'views/help&tutorials/materialreports/InventoryReports.html'
		}, ]
	}, {
		title : 'Progress Reports',
		url : 'null',
		subTasks : [ {
			title : 'Tangible Quantities',
			url : 'views/help&tutorials/pregressreports/TangibleQuantities.html'
		}, {
			title : 'Progress Planned Vs Actual',
			url : 'views/help&tutorials/pregressreports/ProgressPlannedActual.html'
		}, ]
	}, {
		title : 'Cost & Performance & Reports',
		url : 'null',
		subTasks : [ {
			title : 'Actual Cost Details',
			url : 'views/help&tutorials/costperformenceandreports/ActualCostDetails.html'
		}, {
			title : 'Earned Value & Performance',
			url : 'views/help&tutorials/costperformenceandreports/EarnedValueAndPerformance.html'
		}, {
			title : 'Progress Claim',
			url : 'views/help&tutorials/costperformenceandreports/ProgressClaim.html'
		}, {
			title : 'Cost Schedule Variance',
			url : 'views/help&tutorials/costperformenceanreports/CostScheduleVariance.html'
		}, {
			title : 'Cost Schedule Performance',
			url : 'views/help&tutorials/costperformenceandreports/CostSchedulePerformance.html'
		}, {
			title : 'Item Wise Budget',
			url : 'views/help&tutorials/costperformenceandreports/ItemWiseBudget.html'
		}, {
			title : 'Item Wise Periodical Cost',
			url : 'views/help&tutorials/costperformenceandreports/ItemWisePeriodicalCost.html'
		} ]
	}, {
		title : 'Admin',
		url : 'null',
		subTasks : [ {
			title : 'User List',
			url : 'views/help&tutorials/admin/AdminUserList.html'
		}, {
			title : 'User Priveleges',
			url : 'views/help&tutorials/admin/UsersPrivileges.html'
		}, {
			title : 'Settings',
			url : 'views/help&tutorials/admin/AdminSettings.html'
		} ]
	}, {
		title : 'Tools',
		url : 'null',
		subTasks : [ {
			title : 'Calendars',
			url : 'views/help&tutorials/tools/Calendars.html'
		}, {
			title : 'Resource Curves',
			url : 'views/help&tutorials/tools/ResourceCurves.html'
		}, ]
	}, {
		title : 'Notification',
		url : 'views/help&tutorials/tools/Notifications.html'
	}, {
		title : 'My Account',
		url : 'views/help&tutorials/tools/MyAccount.html'
	}
	];

	$scope.subTaskUrl = function(subTask) {
		$scope.view = subTask.concat("");
	}
}]).animation('.slide', function() {
	var NgHideClassName = 'ng-hide';
	return {
		beforeAddClass : function(element, className, done) {
			if (className === NgHideClassName) {
				jQuery(element).slideUp(done);
			}
		},
		removeClass : function(element, className, done) {
			if (className === NgHideClassName) {
				jQuery(element).hide().slideDown(done);
			}
		}
	};
});
