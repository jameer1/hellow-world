'use strict';
app.factory('GenerateInvoiceToProjOwnerFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService",
	function (ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService) {
		var generatePopUp;
		var service = {};
		service.generatePopUpDetails = function () {
			var deferred = $q.defer();
			generatePopUp = ngDialog.open({
				template: 'views/finance/financemain/receivables/projects/generateinvoicetoprojownerpopup.html',
				scope: $rootScope,
				className: 'ngdialog-theme-plain ng-dialogueCustom0',
				closeByDocument: false,
				showClose: false,
				controller: ['$scope', function ($scope) {

				}]
			});
			return deferred.promise;
		}
		return service;
	}]);