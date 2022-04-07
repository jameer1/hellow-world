app.factory('plannedVsActualDashboardService', ["Restangular", function(Restangular) {
	return {
	getPlanVsActualDashBoardDetails : function(req) {
			var PlanVsActualDashBoardDetails= Restangular.one("reports/getPlanVsActualDashBoardDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return PlanVsActualDashBoardDetails;
		},
	
	}
	}])