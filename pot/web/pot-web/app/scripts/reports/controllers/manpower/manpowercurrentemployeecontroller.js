'use strict';

app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("currentemplist", {
		url: '/currentemplist',
		data: {
			manpower: []
		},
		views: {
			'content@': {
				'templateUrl': 'views/reports/manpower/manpowercurrentemployee.html',
				controller: 'ManpowerCurrentEmployeeController'
			}
		}
	})
}]).controller("ManpowerCurrentEmployeeController", ["$scope","uiGridGroupingConstants","$q", "ngDialog", "GenericAlertService", "EpsProjectMultiSelectFactory",
	"CompanyMultiSelectFactory", "generalservice", "ManpowerReportService", "ngGridService", 'stylesService',
	function ($scope,uiGridGroupingConstants,$q, ngDialog, GenericAlertService, EpsProjectMultiSelectFactory,
		CompanyMultiSelectFactory, generalservice, ManpowerReportService, ngGridService, stylesService) {
		$scope.selectedProjIds = [];
		$scope.selectedCompanyIds = [];
		$scope.categoryName = [];
		let manpowerDetails;
		$scope.category = {
			name: null
		}
		$scope.categories = [{
			id: 1,
			name: "Direct",
			code: generalservice.employeeCategory[0]
		}, {
			id: 2,
			name: "In-Direct",
			code: generalservice.employeeCategory[1]
		}, {
			id: 3,
			name: "All",
			code: null
		}];

		$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				manpowerDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};

		$scope.getCompanyList = function () {
			var companyReq = {
				"status": 1
			}
			var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
			companyPopUp.then(function (data) {
				$scope.companyNameDisplay = data.selectedCompanies.companyName;
				$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
				manpowerDetails = [];
			})
		};

		$scope.getManpowerDetails = function () {
			if ($scope.selectedProjIds.length <= 0 || $scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
				GenericAlertService.alertMessage("Please select the Project", 'Warning');
				return;
			}
			if ($scope.selectedCompanyIds.length <= 0 || $scope.selectedCompanyIds == undefined || $scope.selectedCompanyIds == null) {
				GenericAlertService.alertMessage("Please select the Company", 'Warning');
				return;
			}
			if ($scope.categoryName == '') {
				GenericAlertService.alertMessage("Please select the Category", 'Warning');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"cmpIds": $scope.selectedCompanyIds,
				"categories": $scope.categoryName,
			}
			ManpowerReportService.getManpowerCurrentEmployeeReport(req).then(function (data) {
				manpowerDetails = data;
				manpowerDetails.map(o => {
					o.mobilizationDate = moment(o.mobilizationDate).format('DD-MMM-YYYY');
					o.deMobilizationDate = moment(o.deMobilizationDate).format('DD-MMM-YYYY');
				});
				$scope.gridOptions.data = angular.copy(manpowerDetails);
				if (manpowerDetails.length <= 0) {
					GenericAlertService.alertMessage("Manpower Current Employee List not available for the Search Criteria", 'Warning');
				}
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting Employee Details", 'Error');
			});
		};


		$scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
			if (newValue) {
				let columnDefs = [
					{ field: 'parentProjName', displayName: "EPS", headerTooltip: "EPS Name", groupingShowAggregationMenu: false },
					{ field: 'projName', displayName: "Project", headerTooltip: "Project Name", groupingShowAggregationMenu: false },
					{ field: 'crewName', displayName: "Crew", headerTooltip: "Crew Name", visible: false, groupingShowAggregationMenu: false },
					{ field: 'empCategoryName', displayName: "Category", headerTooltip: "Employee Category", groupingShowAggregationMenu: false },
					{ field: 'companyName', displayName: "Company", headerTooltip: "Company Name", groupingShowAggregationMenu: false },
					{ field: 'empClassName', displayName: "Trade", headerTooltip: "Trade Name", groupingShowAggregationMenu: false },
					{ field: 'empCode', displayName: "Emp ID", headerTooltip: "Employee ID", groupingShowAggregationMenu: false },
					{ field: 'empFirstname', displayName: "First Name", headerTooltip: "Employee first name", groupingShowAggregationMenu: false },
					{ field: 'empLastname', displayName: "Last Name", headerTooltip: "Employee Last name", visible: false, groupingShowAggregationMenu: false },
					{ field: 'classificationPerUnion', displayName: "Union", headerTooltip: "Classification as per trade union", groupingShowAggregationMenu: false },
					{ field: 'unitOfMeasure', displayName: "UOM", headerTooltip: "Unit Of Measure", visible: false, groupingShowAggregationMenu: false },
					{ field: 'mobilizationDate', displayName: "Mobil Date", headerTooltip: "Mobilization Date", groupingShowAggregationMenu: false },
					{ field: 'deMobilizationDate', displayName: "De-Mobil Date", headerTooltip: "Expected De-Mobilization Date", groupingShowAggregationMenu: false },
					{ field: 'normalRate', displayName: "Rate", headerTooltip: "Chargeable Rate Per Hour", groupingShowAggregationMenu: false },
					{ field: 'phoneNum', displayName: "Ph.No", headerTooltip: "Phone Number", groupingShowAggregationMenu: false },
					{ field: 'emailId', displayName: "Email", headerTooltip: "Email Address", visible: false, groupingShowAggregationMenu: false },
				]
				$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, [],"Reports_Manpower_Current_Employee_List");
			}
		});

		$scope.resetManpowerDetails = function () {
			manpowerDetails = [];
			$scope.selectedProjIds = [];
			$scope.selectedCompanyIds = [];
			$scope.searchProject = {};
			$scope.categoryName = [];
			$scope.companyNameDisplay = null;
			$scope.category.name = null;
			if ($scope.gridOptions) {
				$scope.gridOptions.columnDefs = angular.copy($scope.initialColumnSet);
				$scope.gridOptions.data = [];
			}
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
				template: 'views/help&tutorials/reportshelp/manpowerhelp/manpowercurrentemplisthelp.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom1',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function ($scope) {
		
				}]
			});
			return deferred.promise;
		}
	}])
