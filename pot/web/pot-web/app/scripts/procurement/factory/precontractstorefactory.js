'use strict';

app.factory('PreContractStoreFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "StoreService", "ProjStoreStockService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, StoreService, ProjStoreStockService, GenericAlertService) {
	var storeYardPopup = [];
	var storeService = {};
	storeService.getStocks = function(projId, multiSelect) {
		var deferred = $q.defer();
		storeYardPopup = ngDialog.open({
			template : 'views/procurement/pre-contracts/internalApproval/precontractdeliverypopup.html',
			closeByDocument : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom4',
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.multiSelect = multiSelect;
				$scope.storeItems = [];
				$scope.projStoreItems = [];
				$scope.selectAllItems = false;
				$scope.selectAllProjItems = false;
				$scope.storeStockCurrentTab = null;
				$scope.storeStockTabs = [ {
					title : 'Store Stock ',
					url : 'views/procurement/pre-contracts/internalApproval/precontractstorestock.html',
					storeStockSeleniumLocator :'StoreStock_TAB'
				}, {
					title : 'Project Store And Stock',
					url : 'views/procurement/pre-contracts/internalApproval/precontractprojstorestock.html',
					storeStockSeleniumLocator :'ProjectStoreStock_TAB'
				} ];
				$scope.storeStockCurrentTab = 'views/procurement/pre-contracts/internalApproval/precontractstorestock.html';
				$scope.storeStockOnClickTab = function(tab) {
					$scope.storeStockCurrentTab = tab.url;
				}
				$scope.isStoreStockActiveTab = function(tabUrl) {
					return tabUrl == $scope.storeStockCurrentTab;
				}, $scope.storeYardData = [];
				$scope.projStoreYardData = [];
				$scope.getStocks = function() {
					var storeYardReq = {
						"status" : 1,
					};

					StoreService.getStocks(storeYardReq).then(function(data) {
						$scope.storeYardData = data.stockAndStoreTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Store Stock Details", 'Error');
					});
				}, $scope.getProjStoreStocks = function() {
					var storeYardReq = {
						"status" : 1,
						"projIds" : Array.isArray(projId) ? projId : [projId]
					}; 
					ProjStoreStockService.getMultipleProjsStoreList(storeYardReq).then(function(data) {
						$scope.projStoreYardData = data.projStoreStockTOs;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Project Store Stock Details", 'Error');
					});
				}, $scope.deliveryPlacePopup = function(storeData, type) {

					var projStoreReturnObj = {
						"storeStockTO" : storeData,
						"type" : type
					};
					deferred.resolve(projStoreReturnObj);
					$scope.closeThisDialog();
				}, $scope.addStoreStockItems = function() {
					let storeModelObj = "";
					let storeIds = [];
					$scope.storeItems.map(store => {
						storeModelObj += store.code + ",";
						storeIds.push(store.id);
					});
					storeModelObj += "";
					let projStoreIds = [];
					$scope.projStoreItems.map(store => {
						storeModelObj += store.code + "";
						projStoreIds.push(store.id);
					});
					storeModelObj += "";

					var projStoreReturnObj = {
						"storeItems" : storeIds,
						"projStoreItems" : projStoreIds,
						"storeModelObj" : storeModelObj
					};
					deferred.resolve(projStoreReturnObj);
					$scope.closeThisDialog();
				}, $scope.selectStoreItem = function(item) {
					if (item.select)
						$scope.storeItems.push(item);
					else 
						$scope.storeItems.splice($scope.storeItems.indexOf(item), 1);
				}, $scope.selectProjStoreItem = function(item) {
					if (item.select)
						$scope.projStoreItems.push(item);
					else 
						$scope.projStoreItems.splice($scope.projStoreItems.indexOf(item), 1);
				}, $scope.selectAllStoreItems = function(selectAll) {
					$scope.storeYardData.map(item => {
						item.select = selectAll;
						$scope.selectStoreItem(item);
					});
				}, $scope.selectAllProjStoreItems = function(selectAll) {
					$scope.projStoreYardData.map(item => {
						item.select = selectAll;
						$scope.selectProjStoreItem(item);
					});
				}
			} ]
		});
		return deferred.promise;
	};
	return storeService;

}]);
