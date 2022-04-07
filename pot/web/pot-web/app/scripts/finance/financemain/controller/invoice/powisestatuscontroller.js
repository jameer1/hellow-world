'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("powisestatus", {
		url : '/powisestatus',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/invoice/postinvoice.html',
				controller : 'POWiseStatusController'
			}
		}
	})
}]).controller("POWiseStatusController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", function($rootScope, $scope, $q, $state, ngDialog) {


}]);