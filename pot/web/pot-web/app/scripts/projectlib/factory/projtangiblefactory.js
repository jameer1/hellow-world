'use strict';

app.factory('ProjTanFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjSORService","TangibleClassService", "MeasureService", "GenericAlertService", "TreeService", function (ngDialog, $q, $filter, $timeout, $rootScope, ProjSORService,
		TangibleClassService, MeasureService,GenericAlertService, TreeService) {
	var projSorPopup;
	var service = {};
	service.tanPopupDetails = function (projId) {
		var deferred = $q.defer();
		projSorPopup = ngDialog.open({
			template: 'views/projectlib/tangible/projtanviewpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.TangibleData = [];
				$scope.getTangibleDetails = function () {
					var sorReq = {
						"projId": projId,
						"status": 1
					}
					TangibleClassService.getTangibleGroups(sorReq).then(function (data) {
						$scope.TangibleData = TreeService.populateTreeData(data.tangibleClassTOs, 0, [], 'code', 
							'childTangibleItemTOs');
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting  Tangible Item Details", 'Error');
					});

				};

				$scope.selectTangibleDetails = function (tangibleItemData) {

					var returnPopObj = {
						"selectedTangibleItem": tangibleItemData
					};

					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				};

				$scope.tangibleFactoryItemClick = function (tab, collapse) {
					TreeService.treeItemClick(tab, collapse, 'childTangibleItemTOs');
				};
                
			}]
		});
		return deferred.promise;
	}
	return service;
}]);