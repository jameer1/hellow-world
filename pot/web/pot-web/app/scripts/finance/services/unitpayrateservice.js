'use strict';

app.factory('UnitPayRateService', ["Restangular", "$http", "$q", "appUrl", function(Restangular,$http, $q,appUrl) {
	
	return {
		getUnitPayRate : function(req) {
			var getStatus = Restangular.one("finance/getUnitOfRates").customPOST(req,
					'', {}, {
						ContentType : 'application/json'
					});
			return getStatus;
		},
		saveUnitPayRate : function(req) {
			var saveStatus = Restangular.one("finance/saveUnitOfRates").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			return saveStatus;
		},
		deletePayRates : function(req) {
			var deleteStatus = Restangular.one("finance/deleteUnitOfRates").customPOST(req, '', {}, {
						ContentType : 'application/json'
					});
			return deleteStatus;
		},
		
		addFinancialCenter : function(financeCenterSaveReq) {
			//console.log("financeCenterSaveReq====>",financeCenterSaveReq)
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/centrallib/savefinanceCenterRecords",
				method : "POST",
				data: JSON.stringify(financeCenterSaveReq),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			//console.log("====addFinancialCenter=====")
			return deferred.promise;
		},

	//getEmployeeTypes :function(employeeTypesGetReq){
		getEmployeeTypes :function(financeCenterCodeGetReq){
		var deferred = $q.defer();
		//console.log("getEmployeeTypes :function(employeeTypesGetReq)")
            $http({
            	url : appUrl.originurl + "/app/centrallib/getEmployeeTypes",
                method : "POST",
                data: JSON.stringify(financeCenterCodeGetReq),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
		//console.log("close")
		
            return deferred.promise;
	},

    getFinanceCenters : function(financeCenterCodeGetReq) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/centrallib/getFinanceCenterRecords",
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
            	url : appUrl.originurl + "/app/centrallib/deactivateFinanceCenterRecords",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },

        getUnitOfRate : function(financeCenterFilterReq) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/centrallib/getUnitOfRate",
                method : "POST",
                data: JSON.stringify(financeCenterFilterReq),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },

		saveTaxAndRules : function(taxRatesRulesCodeDtlSaveReq) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/centrallib/saveTaxAndRules",
                method : "POST",
                data: JSON.stringify(taxRatesRulesCodeDtlSaveReq),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },

		/*saveTaxAndRules : function(req) {
			console.log('===saveTaxAndRules====')
			var saveStatus = Restangular.one("centrallib/saveTaxAndRules").customPOST(
					req, '', {}, {
						ContentType : 'application/json'
					});
			console.log('===close====')	
			return saveStatus;
		}*/
	}

}]);
