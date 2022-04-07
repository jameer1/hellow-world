'use strict';

app.factory('TimeSheetReportFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "TimeSheetService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, blockUI, TimeSheetService, GenericAlertService) {
	var service = {};
	service.getTimeSheetData = function(crewTypeId,timeSheetSearchReq) {
		var deferred = $q.defer();
		if(crewTypeId==1){
			var timeSheetGetReq = {
				"status" : 1,
				"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
				"crewId" : timeSheetSearchReq.crewLabelKeyTO.id,
				"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
				"weekStartDate" : timeSheetSearchReq.weekStartDate,
				"weekEndDate" : timeSheetSearchReq.weekEndDate
			};
			blockUI.start();
			TimeSheetService.getCrewTimeSheets(timeSheetGetReq).then(function(data) {
				blockUI.stop();
				var popupdata = service.openPopup(data.timeSheetTOs);
				popupdata.then(function(resultData) {
					deferred.resolve(resultData);
				});
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		}else{
			var timeSheetGetReq = {
				"status" : 1,
				"projId" : timeSheetSearchReq.projectLabelKeyTO.projId,
				"empId" : timeSheetSearchReq.timesheetUserLabelKeyTO.id,
				"weekCommenceDay" : timeSheetSearchReq.weekCommenceDay,
				"weekStartDate" : timeSheetSearchReq.weekStartDate,
				"weekEndDate" : timeSheetSearchReq.weekEndDate
			};
			blockUI.start();
			TimeSheetService.getIndividualTimeSheets(timeSheetGetReq).then(function(data) {
				blockUI.stop();
				var popupdata = service.openPopup(data.timeSheetTOs);
				popupdata.then(function(resultData) {
					deferred.resolve(resultData);
				});
			}, function(error) {
				blockUI.stop();
				GenericAlertService.alertMessage("Error occured while getting TimeSheet Details", 'Error');
			});
		
		}
		return deferred.promise;
	}, service.openPopup = function(timeSheetData) {
		var deferred = $q.defer();
		var epsProjTreePopup = ngDialog.open({
			template : 'views/reports/time sheet/timesheetselectpopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedTimeSheets = [];
				$scope.timeSheetData = timeSheetData;
				var reqNameDisplay = '';
				var apprNameDisplay = '';
				var selectedTimeSheetIds = [];
				var selectedReqUserIds = [];
				var selectedApprUserIds = [];
				$scope.selectedItems = function(timeSheet) {
						selectedTimeSheets.push(timeSheet);
				}
				$scope.checkAll = function() {
					angular.forEach($scope.timeSheetData, function(tab) {
						if ($scope.selectedAll) {
							tab.selected = false;
						} else {
							tab.selected = true;
						}
					});
				}
				$scope.addTimeSheets = function() {
					var praentMap = [];
					angular.forEach(selectedTimeSheets, function(value, key) {
						selectedTimeSheetIds.push(value.code);
						selectedReqUserIds.push(value.reqUserId);
						selectedApprUserIds.push(value.apprUserId);
					/*	if (praentMap[value.parentId] == null) {
							praentMap[value.parentId] = true;
							reqNameDisplay = reqNameDisplay + value.parentName + ",";
						}
						apprNameDisplay = apprNameDisplay + value.projName + ",";*/
					});
					var returnPopObj = {
						"timeSheetDetails" : {
							"apprNames" : apprNameDisplay.slice(0, -1),
							"reqNames" : reqNameDisplay.slice(0, -1),
							"timesheetIds" : selectedTimeSheetIds,
							"reqUserIds" : selectedReqUserIds,
							"apprUserIds" : selectedApprUserIds
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};

	return service;

}]);