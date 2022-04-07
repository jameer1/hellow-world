app.factory('BudgetService', ["Restangular", "$http", "appUrl",
	function (Restangular, $http, appUrl) {

		function sendCostReports(requestPayload, requestEndPoint) {
			return $http({
				url: appUrl.originurl + "/app/costreports/" + requestEndPoint,
				method: "POST",
				data: JSON.stringify(requestPayload),
			}).then(data => { return data.data });
		};

		return {
			getCostCodeActualDetailsReport: function (req) {
				return sendCostReports(req, "getDatewiseActualCostDetails");
			},

			getCostCodeBudgetReport: function (req) {
				return sendCostReports(req, "getCostCodeBudgetReport");
			},
			getDateWisePlanActualEarned: function (req) {
				return sendCostReports(req, "getDateWisePlanActualEarned");
			},
			getPeriodicalWiseReport: function (req) {
				return sendCostReports(req, "getPeriodicalWiseReport");
			},
			getCostCodeWiseReport: function (req) {
				return sendCostReports(req, "getCostCodeWiseReport");
			},
			getDateProjWiseActualReport: function (req) {
				return sendCostReports(req, "getDateProjWiseActualReport");
			}
		}
	}]);
