'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state('projleavetype', {
		url: '/projleavetype',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/leavetype/leavetype.html',
				controller: 'LeaveTypeController'
			}
		}
	})
}]).controller("LeaveTypeController", ["$rootScope", "$q", "$scope", "ngDialog", "CountryService", "utilservice", "ProjLeaveTypeService", "ProjectLeaveTypePopUpService", "GenericAlertService", function ($rootScope, $q, $scope,ngDialog, CountryService, utilservice, ProjLeaveTypeService,
	ProjectLeaveTypePopUpService, GenericAlertService) {
	$scope.toggleTable = true;
	let editLeaveClass = [];
	$scope.leaveReq = {
		"countryName": "",
		"effectiveFrom": ""
	};
	$scope.countries = [];
	$scope.effectiveDates = [];
	CountryService.getCountries().then(function (data) {
		$scope.countries = data.countryInfoTOs;
	}, function (error) {
		GenericAlertService.alertMessage("Error occured while getting Countries", "Error");
	});
	$scope.changeCountry = function () {
		if (!$scope.leaveReq.countryName) {
			GenericAlertService.alertMessage("Select the country to fetch effective dates", "Warning");
			return;
		}
		$scope.effectiveDates = [];
		$scope.leaveReq.effectiveFrom = "";
		ProjLeaveTypeService.getEffectiveDatesForCountry($scope.leaveReq.countryName).then(function (data) {
			$scope.toggleTable = false;
			$scope.effectiveDates = data;			
			$scope.tableData = [];
			$scope.toggleTable = true;
		}, function () {
			GenericAlertService.alertMessage("Error occured while fetching leave types", 'Error');
		});
	};
	$scope.getLeaveTypesByCountry = function () {
		if (!$scope.leaveReq.countryName) {
			GenericAlertService.alertMessage("Select the country to fetch leave types", "Warning");
			return;
		}
		if (!$scope.leaveReq.effectiveFrom) {
			GenericAlertService.alertMessage("Select the effective from to fetch leave types", "Warning");
			return;
		}
		const req = {
			"countryCode": $scope.leaveReq.countryName,
			"effectiveFrom": $scope.leaveReq.effectiveFrom
		};
		ProjLeaveTypeService.getLeaveTypesByCountry(req).then(function (data) {
			$scope.toggleTable = false;
			$scope.tableData = data.projLeaveTypeTOs;
			$scope.toggleTable = true;
		}, function () {
			GenericAlertService.alertMessage("Error occured while fetching leave types", 'Error');
		});
	};
	$scope.resetProjLeaveTypes = function () {
		$scope.leaveReq = {
			"countryName": "",
			"effectiveFrom": ""
		};
		$scope.effectiveDates = [];
		$scope.toggleTable = false;
		$scope.tableData = [];
		$scope.toggleTable = true;
	};
	$scope.rowSelect = function (tab) {
		if (tab.select) {
			utilservice.addItemToArray(editLeaveClass, "id", tab);
		} else {
			editLeaveClass.splice(editLeaveClass.indexOf(tab), 1);
		}
	};
	$scope.addpopup = function (actionType) {
		angular.forEach(editLeaveClass, function (value) {
			value.select = false;
		});
		if (editLeaveClass.length > 0 && actionType == 'Add') {
			editLeaveClass = [];
		}
		if (actionType == 'Edit' && editLeaveClass <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}

		if (actionType == 'Add') {
			ProjLeaveTypeService.getGlobalLeaveTypes().then(function (data) {
				editLeaveClass = data.projLeaveTypeTOs;
				openPopup(actionType);
			}, function (error) {
				GenericAlertService.alertMessage('Leave Type(s)  is/are failed to Save', 'Error');
			});
		} else {
			openPopup(actionType);
		}

	};

	function openPopup(actionType) {
		var popupDetails = ProjectLeaveTypePopUpService.projLeaveTypePopUp(actionType, editLeaveClass, $scope.countries);
		editLeaveClass = [];
		popupDetails.then(function (data) {
			$scope.tableData = data.projLeaveTypeTOs;
			editLeaveClass = [];
			if (actionType == 'Edit')
				$scope.getLeaveTypesByCountry();
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting Project Employee Classification details", 'Info');
		});
	};
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
			template: 'views/help&tutorials/leavetypehelp.html',
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
