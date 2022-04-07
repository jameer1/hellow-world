'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("invoicewisestatus", {
		url : '/invoicewisestatus',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/invoice/invoicewisepaymentstatus.html',
				controller : 'InvoiceWiseStatusController'
			}
		}
	})
}]).controller("InvoiceWiseStatusController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", function($rootScope, $scope, $q, $state, ngDialog) {


}]);