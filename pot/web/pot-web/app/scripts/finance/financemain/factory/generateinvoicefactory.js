'use strict';

app
		.factory(
				'GenerateInvoiceFactory',
				["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope,
						 GenericAlertService) {
					var generatePopUp;
					var service = {};
					service.generatePopUpDetails = function() {
						var deferred = $q.defer();
						generatePopUp = ngDialog
								.open({

									template : 'views/finance/financemain/generateinvoicepopup.html',
									scope : $rootScope,
									className : 'ngdialog-theme-plain ng-dialogueCustom0',
									closeByDocument : false,
									showClose : false,
									controller : [
											'$scope',
											function($scope) {
												/*$scope.date = new Date();
												$scope.approvalList = ["Yes","No"];*/
											} ]

								});
						return deferred.promise;
					}
					return service;
				}]);