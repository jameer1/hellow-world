'use strict';

app
		.factory(
				'GeneratePayRollFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
						 GenericAlertService) {
					var generatePopUpDetails;
					var selectedUnitPayRate = [];
					var service = {};
					service.generatePopUpDetails = function() {
						var deferred = $q.defer();
						generatePopUpDetails = ngDialog
								.open({

									template : 'views/finance/financemain/employeewisecalc/generatepayrollpopup.html',
									scope : $rootScope,
									closeByDocument : false,
									showClose : false,
									controller : [
											'$scope',
											function($scope) {
												
											} ]

								});
						return deferred.promise;
					}
					return service;
				}]);