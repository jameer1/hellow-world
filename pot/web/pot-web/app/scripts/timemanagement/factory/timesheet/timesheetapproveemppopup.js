'use strict';
app.factory('TimesheetApproveEmpPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "TimeSheetCostCodeFactory", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI, TimeSheetCostCodeFactory, TimeSheetService, GenericAlertService) {
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
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.timeSheetEmpTaskMap = timeSheetEmpDtlTO.timeSheetEmpTaskMap;
				$scope.expensesList = timeSheetEmpDtlTO.timeSheetEmpExpenseTOs;
				$scope.timeSheetDays = timeSheetDays;
				$scope.empRegMap = empRegMap;
				$scope.costcodeMap = costcodeMap;
				var selectedExpensesList = [];
				$scope.selectedEmp = empObj;
				$scope.timeSheetSearchReq=timeSheetSearchReq;
				$scope.timeSheetTab = [ {
					title : 'DateWise and Employee Wise Tasks Performed',
					url : 'views/timemanagement/timesheet/approvetimesheet/timesheetapproveemptasks.html'
				}, {
					title : 'DateWise Expenses Incurred',
					url : 'views/timemanagement/timesheet/approvetimesheet/timesheetapproveempexpenses.html'
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
					$scope.expensesList.push({
						"date" : '',
						"empDtlId" : empObj.id,
						"costId" : '',
						"amount" : '',
						"comments" : '',
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
					var req = {
						"timeSheetEmpExpenseTOs" : $scope.expensesList,
						"timeSheetId" : timeSheetSearchReq.timeSheetLabelKeyTo.id,
						"timeSheetEmpId" : empObj.id
					}
					TimeSheetService.saveTimeSheetEmpExpenses(req).then(function(data) {
						$scope.expensesList = data.timeSheetEmpExpenseTOs;
						GenericAlertService.alertMessage('Expenses saved successfully', "Info");
					}, function(error) {
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
					}, function(error) {
						   blockUI.stop();
						GenericAlertService.alertMessage('Task Details are Failed to Save ', "Error");
					});
				},
				$scope.saveTimeSheeteApprove = function(){
				var saveReqObj={
					"projId":timeSheetSearchReq.projectLabelKeyTO.projId ,
					"notifyRefId":crewTypeId,
					"toUserId":$scope.userLabelKeyTO.id,
					"fromDate":$scope.tableData.weekStartDate,
					"toDate":$scope.tableData.weekEndDate,
					"weekCommenceDay":timeSheetSearchReq.weekCommenceDay,
					"type":'APPROVE',
					"timeSheetCode":timeSheetSearchReq.timeSheetLabelKeyTo.code,
					"status" : 1,
					"reqComments":$scope.comments
				};
				
				var saveReq = {
					"timeSheetNotificationsTOs":[saveReqObj],
					"status" : 1,
					"projId" : timeSheetSearchReq.projectLabelKeyTO.projId
				};
				TimeSheetService.saveTimeSheetNotifications(saveReq).then(function(data) {
					$scope.closeThisDialog();
					GenericAlertService.alertMessage('notification Details are Submitted successfully', "Info");
				}, function(error) {
					GenericAlertService.alertMessage('notification Details are  failed to Submit', "Error");
				});
			},$scope.getModuleUserDetails = function(userLabelKeyTO) {
				var getReq = {
					"moduleCode" : "APRVETIMESHEET",
					"actionCode" : "APPROVE",
					"projId" : timeSheetSearchReq.projectLabelKeyTO.projId
				};
				var selectedUser = ModuleUserFactory.getUsersByModulePermission(getReq);

				selectedUser.then(function(data) {
					userLabelKeyTO.id = data.userLabelKeyTO.id;
					userLabelKeyTO.name = data.userLabelKeyTO.name;
				});
			}
			
			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
