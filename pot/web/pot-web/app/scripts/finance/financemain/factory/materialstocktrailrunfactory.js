'use strict';

app
		.factory(
				'MaterialStockTrailRunFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
						 GenericAlertService) {
					var generatePopUp;
					var service = {};
					service.generatePopUpDetails = function() {
						var deferred = $q.defer();
						generatePopUp = ngDialog
								.open({

									template : 'views/finance/financemain/materialstocktrailrun.html',
									scope : $rootScope,
									closeByDocument : false,
									showClose : false,
									controller : [
											'$scope',
											function($scope) {
												$scope.date = new Date();
												/*$scope.approvalList = ["Yes","No"];*/
											} ]

								});
						return deferred.promise;
					}
					return service;
				}]);