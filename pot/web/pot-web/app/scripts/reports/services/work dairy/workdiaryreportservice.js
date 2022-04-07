app.factory('WorkDiaryReportService', ["Restangular", function(Restangular) {

	return {
		getWorkDairyDailyManpowerReport : function(req) {
			var workdiaryDetails = Restangular.one("reports/getWorkDairyDailyManpowerReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return workdiaryDetails;
		},
		getWorkDairyDailyPlantReport : function(req) {
			var workdiaryDetails = Restangular.one("reports/getWorkDairyDailyPlantReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return workdiaryDetails;
		},
		getWorkDairyDailyMaterialReport : function(req) {
			var workdiaryDetails = Restangular.one("reports/getWorkDairyDailyMaterialReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return workdiaryDetails;
		},
		getWorkDairyDailyProgressReport : function(req) {
			var workdiaryDetails = Restangular.one("reports/getWorkDairyDailyProgressReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return workdiaryDetails;
		},
		getWorkDiarySubmitApprDetails : function(req) {
			var workdiaryDetails = Restangular.one("reports/getWorkDairyApprStatusReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return workdiaryDetails;
		}
	}
}]);