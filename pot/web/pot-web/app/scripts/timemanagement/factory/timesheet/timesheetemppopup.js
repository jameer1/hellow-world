'use strict';
app.factory('TimesheetEmpPopupFactory', ["ngDialog", "$q", "blockUI", "$filter", "$timeout", "$rootScope", "TimeSheetCostCodeFactory", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, blockUI,$filter, $timeout, $rootScope, TimeSheetCostCodeFactory, TimeSheetService, GenericAlertService) {
	var dateWisePopUp;
	var service = {};

	service.getTimeSheetEmpDetails = function(empRegMap, timeSheetSearchReq, empObj, costcodeMap) {
		var timeSheetGetReq = {
			"status" : 1,
			"timeSheetEmpId" : empObj.id,
			"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
			"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
			"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
			"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
			"apprStatus" : null
		};
		if (timeSheetSearchReq.projectLabelKeyTO.projId == undefined || timeSheetSearchReq.projectLabelKeyTO.projId <= 0 || timeSheetSearchReq.crewLabelKeyTO == undefined || timeSheetSearchReq.crewLabelKeyTO == null) {
			GenericAlertService.alertMessage("Please select Project ID and Crew", 'Warning');
			return;
		}
		TimeSheetService.getTimeSheetEmpDetails(timeSheetGetReq).then(function(data) {
			service.openEmptimeSheetPopup(empRegMap, data.timeSheetEmpDtlTO, data.timeSheetDays, timeSheetSearchReq, empObj, costcodeMap);
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
		});
	},

	service.openEmptimeSheetPopup = function(empRegMap, timeSheetEmpDtlTO, timeSheetDays, timeSheetSearchReq, empObj, costcodeMap) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/timesheet/createtimesheet/emptimesheetpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.timeSheetEmpTaskMap = timeSheetEmpDtlTO.timeSheetEmpTaskMap;
				$scope.expensesList = timeSheetEmpDtlTO.timeSheetEmpExpenseTOs;
				//$scope.timeSheetDays = timeSheetDays;
				$scope.empRegMap = empRegMap;
				$scope.costcodeMap = costcodeMap;
				//$scope.timeSheetDays=timeSheetDays;
				$scope.timeSheetDays=[];
				angular.forEach(timeSheetDays, function(value, key) {
					
					var oldDate = new Date();
					$scope.sysDate = ($filter)('date')((oldDate),'EEE dd-MMM-yyyy');
					
					var value1 = new Date(value);
					var sysDate1 = new Date($scope.sysDate);
					if(value1<=sysDate1){
						$scope.timeSheetDays.push(value);
					}

				});
				var selectedExpensesList = [];
				$scope.selectedEmp = empObj;
		
				$scope.activeFlag= 0;
				$scope.timeSheetSearchReq=timeSheetSearchReq;
				$scope.timeSheetTab = [ {
					title : 'DateWise and Employee Wise Tasks Performed',
					url : 'views/timemanagement/timesheet/createtimesheet/timesheetemptasks.html',
					timesheetemptasksSeleniumLocator:'TimeSheets_CreateTimeSheets_AddTasks_DateWiseTasksPerformed'
				}, {
					title : 'DateWise Expenses Incurred',
					url : 'views/timemanagement/timesheet/createtimesheet/timesheetempexpenses.html',
					timesheetemptasksSeleniumLocator:'TimeSheets_CreateTimeSheets_AddTasks_DateWiseExpenseIncurred'
				} ];

				$scope.currentTab = $scope.timeSheetTab[0].url;
				$scope.onClickTimeSheetTab = function(masterTabs) {
					$scope.currentTab = masterTabs.url;
				}, $scope.isActiveTimeSheetTab = function(masterTabsUrl) {
					return masterTabsUrl == $scope.currentTab;
				}, $scope.selectCostCode = function(expenses) {

					var costCodepopup = TimeSheetCostCodeFactory.getCostCodeDetails(timeSheetSearchReq);
					costCodepopup.then(function(data) {
						expenses.costId = data.projCostItemTO.id;

					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while fetching costcode details", 'Error');
					});
				},

				$scope.addExpenses = function() {
					$scope.activeFlag= 1;
					$scope.expensesList.push({
						"date" : '',
						"empDtlId" : empObj.id,
						"costId" : '',
						"amount" : '',
						"comments" : ''
						
					});

				}, $scope.rowselect = function(expense) {
					if (expense.select) {
						selectedExpensesList.push(expense);

					} else

					{
						selectedExpensesList.pop(expense);
					}
				},

				$scope.deleteExpenses = function() {
					if (selectedExpensesList.length <= 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

					} else

					if (selectedExpensesList.length < $scope.expensesList.length) {
						angular.forEach(selectedExpensesList, function(value, key) {
							$scope.expensesList.splice($scope.expensesList.indexOf(value), 1);

						});
						selectedExpensesList = [];
						GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
					} else if (selectedExpensesList.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedExpensesList.length <= 1) {
						selectedExpensesList = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

				$scope.saveTimeSheetEmpExpenses = function() {
					$scope.activeFlag= 0;
					var req = {
						"timeSheetEmpExpenseTOs" : $scope.expensesList,
						"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
						"timeSheetEmpId" : empObj.id
					}
					blockUI.start();
					TimeSheetService.saveTimeSheetEmpExpenses(req).then(function(data) {
						blockUI.stop();
						$scope.expensesList = data.timeSheetEmpExpenseTOs;
						GenericAlertService.alertMessage('Expenses saved successfully', "Info");
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Expenses are Failed to Save ', "Error");
					});
				}, $scope.saveTimeSheeteEmpTaks = function() {
				
					var req = {
						"timeSheetEmpTaskMap" : $scope.timeSheetEmpTaskMap,
						"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
						"timeSheetEmpId" : empObj.id
					}
					blockUI.start();
					TimeSheetService.saveTimeSheeteEmpTasks(req).then(function(data) {
						blockUI.stop();
						$scope.timeSheetEmpTaskMap = data.timeSheetEmpTaskMap;
						GenericAlertService.alertMessage('Task Details saved successfully', "Info");
						$scope.closeThisDialog();
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Task Details are Failed to Save ', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
