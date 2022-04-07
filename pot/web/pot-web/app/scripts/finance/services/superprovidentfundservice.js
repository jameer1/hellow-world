'use strict';

app.factory('SuperProvidentFundService', ["Restangular", "$http", "$q", "appUrl", function(Restangular,$http, $q,appUrl) {

	return {
		getUnitPayRate : function(req) {
			var getStatus = Restangular.one("finance/getSuperProvidentFund").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return getStatus;
		},
		saveUnitPayRate : function(req) {
			var saveStatus = Restangular.one("finance/saveSuperProvidentFund").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		deletePayRates : function(req) {
			var deleteStatus = Restangular.one("finance/deleteSuperProvidentFund").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return deleteStatus;
		},
		addFinancialCenter : function(financeCenterSaveReq) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/centlib/app/centrallib/savefinanceCenterRecords",
				method : "POST",
				data: JSON.stringify(financeCenterSaveReq),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},

	getEmployeeTypes :function(employeeTypesGetReq){
		var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/centlib/app/centrallib/getEmployeeTypes",
                method : "POST",
                data: JSON.stringify(employeeTypesGetReq),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
	},

    getFinanceCenters : function(financeCenterCodeGetReq) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/centlib/app/centrallib/getFinanceCenterRecords",
                method : "POST",
                data: JSON.stringify(financeCenterCodeGetReq),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },

         deactivateFinanceCenterRecords : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/centlib/app/centrallib/deactivateFinanceCenterRecords",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        }
	}

}]);
