'use strict';
app.factory('ProfitCentrePopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "ProfitCentreService", "TreeService", function (ngDialog, $q, $filter, $timeout, $rootScope,
	 GenericAlertService, ProfitCentreService, TreeService) {
	var companyListPopUp;
	var service = {};
	service.profitCentreListPopUp = function (profitCentreTOs) {
		var deferred = $q.defer();
		companyListPopUp = ngDialog.open({
			template: 'views/projectsettings/profitcentretree.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			controller: ['$scope', function ($scope) {
				$scope.profitCentreTOs = TreeService.populateTreeData(profitCentreTOs, 0, [],
					'code', 'childProfitCentreTOs');
				$scope.itemClick = function (item, expand) {
					TreeService.treeItemClick(item, expand, 'childProfitCentreTOs');
				}
				$scope.selectRecord = function (record) {
					var returnRecord = {
						"selectedRecord": record
					};
					deferred.resolve(returnRecord);
					$scope.closeThisDialog();
				}
			}]
		});
		return deferred.promise;
	},

		service.getProfitCentres = function (companyReq) {
			var deferred = $q.defer();
			var profitCentreDetailsPromise = ProfitCentreService.getProfitCentres(companyReq)
			profitCentreDetailsPromise.then(function (data) {
				var profitListPopUp = service.profitCentreListPopUp(data.profitCentreTOs);
				profitListPopUp.then(function (data) {
					var returnPopObj = {
						"selectedRecord": data.selectedRecord
					};
					deferred.resolve(returnPopObj);
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while selecting ProfitCentre Details  ", 'Error');
				})
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting ProfitCentre Details", "Error");
			});
			return deferred.promise;
		}

	return service;


}]);