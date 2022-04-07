'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ProjPMCPService', ["Restangular", "$http", "appUrl", function (Restangular, $http, appUrl) {


return {
        getMultiProjCostStatements : function(requestPayload) {
        		 console.log('Budget Service sendProjectBudgetsRequest requestPayload');
             console.log(requestPayload);
             //console.log(requestEndPoint); //projsettings
             console.log(appUrl.originurl + "/app/projectlib/" + 'getPMCPItems');
              return $http({
                url: appUrl.originurl + "/app/projectlib/" + 'getPMCPItems',
                method: "POST",
                data: JSON.stringify(requestPayload),
              }).then(data => { return data.data });
        		}
      /*
      function getMultiProjCostStatements(requestPayload) {
      	console.log('Budget Service sendProjectBudgetsRequest requestPayload');
        console.log(requestPayload);
        //console.log(requestEndPoint); //projsettings
        console.log(appUrl.originurl + "/app/projectlib/" + 'getPMCPItems');
      		return $http({
      			url: appUrl.originurl + "/app/projectlib/" + 'getPMCPItems',
      			method: "POST",
      			data: JSON.stringify(requestPayload),
      		}).then(data => { return data.data });

      	}
      	*/
}
  /*
	return {

		getMultiProjCostStatements: function (req) {
		  console.log('ProjPMCPService getMultiProjCostStatements req');
		  console.log(req);
			return sendProjectBudgetsRequest(req, 'getPMCPItems');
		}
	}*/
}]);
