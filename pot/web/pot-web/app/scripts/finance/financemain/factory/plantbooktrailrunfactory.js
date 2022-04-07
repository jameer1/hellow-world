'use strict';

app
		.factory(
				'PlantBookTrailRunFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
						 GenericAlertService) {
					var generatePopUp;
					var service = {};
					service.generatePopUpDetails = function() {
						var deferred = $q.defer();
						generatePopUp = ngDialog
								.open({

									template : 'views/finance/financemain/plantbooktrailrun.html',
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