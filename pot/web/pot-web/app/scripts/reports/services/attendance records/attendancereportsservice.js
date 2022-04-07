app.factory('AttendanceReportsService', ["Restangular", function(Restangular) {

	return {
		getDailyResourceDetails : function(req) {
			var resource = Restangular.one("reports/getDailyResourceAttendanceReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return resource;
		},
		getDailyEmpAttendenceDetails : function(req) {
			var emp = Restangular.one("reports/getDailyEmpAttendanceReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return emp;
		},
		getMonthlyEmpAttendenceDetails : function(req) {
			var emp = Restangular.one("reports/getMonthlyEmpAttendanceReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return emp;
		},
		getCrewWiseAttendenceDetails : function(req) {
			var emp = Restangular.one("reports/getCrewWiseAttendanceReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return emp;
		},
		getMonthlyPlantAttendenceDetails : function(req) {
			var plant = Restangular.one("reports/getMonthlyPlantAttendanceReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return plant;
		},
		getDailyPlantAttendenceDetails : function(req) {
			var plant = Restangular.one("reports/getDailyPlantAttendanceReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return plant;
		}
	}
}]);