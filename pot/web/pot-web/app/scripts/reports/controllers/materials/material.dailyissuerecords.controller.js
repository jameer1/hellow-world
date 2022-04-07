'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("matdailyissuereport", {
		url: '/matdailyissuereport',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/materials/material.dailyissuerecords.html',
				controller: 'MaterialDailyIssueReport'
			}
		}
	})
}]).controller("MaterialDailyIssueReport", ["$scope", "uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog", "GenericAlertService",
	"EpsProjectMultiSelectFactory", "MaterialRegisterService", "stylesService", "ngGridService","$filter",
	function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog,  GenericAlertService, EpsProjectMultiSelectFactory,
		MaterialRegisterService, stylesService, ngGridService,$filter) {
		$scope.stylesSvc = stylesService;
		function setDate() {
			$scope.toDate = moment(new Date()).format('DD-MMM-YYYY');
			let fromDateObj = new Date();
			fromDateObj.setMonth(fromDateObj.getMonth() - 1);
			$scope.fromDate = moment(fromDateObj).format('DD-MMM-YYYY');
		}

		setDate();
		let todayDate = new Date();
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

		$scope.resetDeliverySupplyDetails = function () {
			$scope.searchProject = null;
			$scope.selectedProjIds = null;
			$scope.dailyIssueRecords = null;
			setDate();
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
		}

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				$scope.selectedClientIds = data.searchProject.clientIds
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		}

		$scope.getDailyIssueRecords = function () {
			if (!$scope.selectedProjIds) {
				GenericAlertService.alertMessage("Please select a project to generate report.", 'Warning');
			} else if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
			}
			else {
				let req = {
					"status": 1,
					projList: $scope.selectedProjIds,
					fromDate: $scope.fromDate,
					toDate: $scope.toDate
				}

				MaterialRegisterService.getMaterialDailyIssueSchItems(req).then(function (data) {
					$scope.dailyIssueRecords = data.labelKeyTOs;
					$scope.gridOptions.data = $scope.dailyIssueRecords;
					if ($scope.dailyIssueRecords.length <= 0) {
						GenericAlertService.alertMessage("Material Daily Issue Records not available for the search criteria", 'Warning');
					}
				}, function (data) {
					GenericAlertService.alertMessage("Error occured while fetching daily issues reports.", 'Error');
				});
			}
		}
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'displayNamesMap.consumptionDate', displayName: "Date", headerTooltip: 'Date Of Issue', width: 160, enableColumnResizing: false, groupingShowAggregationMenu: false},
						{ field: 'displayNamesMap.notificationCode', displayName: "Notify Id", headerTooltip: 'Pre Approval Notification ID', visible: false, groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.deliveryDockNo', displayName: "Proj Docket", headerTooltip: 'Project Docket', groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.className', displayName: "Resource", headerTooltip: 'Resource Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.classCode', displayName: "Resource SG", headerTooltip: 'Resource Sub Group Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.fromProjName', displayName: "Origin Proj", headerTooltip: 'Origin Project', groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.fromLocation', displayName: "Origin S.Y", headerTooltip: 'Origin Store Stock Yard', visible: false, groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.toProjName', displayName: "Dest Proj", headerTooltip: 'Destination Project', groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.toLocation', displayName: "Dest S.Y", headerTooltip: 'Destination Store Stock Yard', visible: false, groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.cmpName', displayName: "Supplier", headerTooltip: 'Supplier Name', visible: false, groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.issueEmpName', displayName: "Issuer", headerTooltip: 'Issuer Employee Name', groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.receiveEmpName', displayName: "Receiver", headerTooltip: 'Receiver Employee Name', groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.unitOfMeasure', displayName: "Unit", headerTooltip: 'Unit Of Measure', groupingShowAggregationMenu: false },
						{ field: 'displayNamesMap.issuedQty', displayName: "Qty", headerTooltip: 'Quantity', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'displayNamesMap.unitOfRate', displayName: "Rate", headerTooltip: 'Unit Of Rate', groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
						customTreeAggregationFinalizerFn: function (aggregation) {
							aggregation.rendered = aggregation.value;
						} },
						{ field: 'displayNamesMap.comments', displayName: "Comments", headerTooltip: 'Comments', visible: false, groupingShowAggregationMenu: false }


					]
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_DailyIssueRecords");
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
					template: 'views/help&tutorials/reportshelp/materialshelp/matdailyissuerecordshelp.html',
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