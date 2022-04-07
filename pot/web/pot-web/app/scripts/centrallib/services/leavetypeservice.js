'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Object Service in the potApp.
 */
app.factory('ProjLeaveTypeService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {

	return {
		getUserProjects: function (req) {
			var projectEps = Restangular.one("projectlib/getUserProjects").customPOST(
				req, '', {}, {
					ContentType: 'application/json'
				});
			return projectEps;
		},
		getCostDetails: function (req) {
			var costDetails = Restangular
				.one("projectlib/getProjCostItems").customPOST(req,
					'', {}, {
						ContentType: 'application/json'
					});
			return costDetails;
		},
		getProjLeaveTypes: function (req) {
			var leaveTypes = Restangular.one("projectlib/getProjLeaveTypes").customPOST(
				req, '', {}, {
					ContentType: 'application/json'
				});
			return leaveTypes;
		}, getProjEmpTypes: function (req) {
			var empClasses = Restangular.one("projectlib/getProjEmpTypes").customPOST(
				req, '', {}, {
					ContentType: 'application/json'
				});
			return empClasses;
		},
		saveProjLeaveTypes: function (req) {
			var saveStatus = Restangular.one("projectlib/saveProjLeaveTypes").customPOST(
				req, '', {}, {
					ContentType: 'application/json'
				});
			return saveStatus;
		},
		deleteProjLeaveTypes: function (req) {
			var deleteStatus = Restangular.one("projectlib/deleteProjLeaveTypes")
				.customPOST(req, '', {}, {
					ContentType: 'application/json'
				});
			return deleteStatus;
		},
		getLeaveTypesByCountry: function (req) {
			return $http({
				url: appUrl.originurl + "/projlib/app/projectlib/getLeaveTypesByCountry",
				method: "POST",
				data: JSON.stringify(req),
			}).then(data => { return data.data });
		},
		getGlobalLeaveTypes: function () {
			return $http({
				url: appUrl.originurl + "/projlib/app/projectlib/getGlobalLeaveTypes",
				method: "POST"
			}).then(data => { return data.data });
		},
		getEffectiveDatesForCountry: function (countryCode) {
			return $http({
				url: appUrl.originurl + "/projlib/app/projectlib/getEffectiveDatesForCountry?countryCode=" + countryCode,
				method: "GET"
			}).then(data => { return data.data });
		}
	}
}]);
