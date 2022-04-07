'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("pobidanalysis", {
		url : '/pobidanalysis',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/purchaseorders/purchaseinternal.html',
				controller : 'PurchaseBidAnalysisController'
			}
		}
	})
}]).controller("PurchaseBidAnalysisController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService) {
	
}]);