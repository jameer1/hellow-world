'use strict';

app.factory('PreContractCostPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ProjectSettingsService", "GenericAlertService", "TreeService", function(ngDialog, $q, $filter, $timeout, $rootScope, 
	ProjectSettingsService, GenericAlertService, TreeService) {
	var service = {};
	service.getProjCostDetails = function(projId,currentTabTitle) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId
		};
        if(currentTabTitle=="Manpower"){
			ProjectSettingsService.getProjExitManpowerCostStatements(req).then(function(data) {
				var popupdata = service.openPopup(data.projCostStmtDtlTOs);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting project cost codes", 'Error');
			});
		}else if(currentTabTitle=="Materials"){
			ProjectSettingsService.getProjExitMaterialCostStatements(req).then(function(data) {
				var popupdata = service.openPopup(data.projCostStmtDtlTOs);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting project cost codes", 'Error');
			});
		}else if(currentTabTitle=="Services"){
			ProjectSettingsService.getProjExitServicesCostStatements(req).then(function(data) {
				var popupdata = service.openPopup(data.projCostStmtDtlTOs);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting project cost codes", 'Error');
			});
		}
		else if(currentTabTitle=="Plants"){
			ProjectSettingsService.getProjExitPlantCostStatements(req).then(function(data) {
				var popupdata = service.openPopup(data.projCostStmtDtlTOs);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting project cost codes", 'Error');
			});
		}
		else{
			ProjectSettingsService.getProjExitCostStatements(req).then(function(data) {
				var popupdata = service.openPopup(data.projCostStmtDtlTOs);
				popupdata.then(function(data) {
					deferred.resolve(data);
				});
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting project cost codes", 'Error');
			});
		}
		return deferred.promise;
	}, service.openPopup = function(projCostStmtDtlTOs) {
		var deferred = $q.defer();
		var popupData = ngDialog.open({
			template : '/views/procurement/pre-contracts/internalApproval/projcostcode.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.itemClick = function(item, expand) {
					TreeService.treeItemClick(item, expand, 'projCostStmtDtlTOs');
				}
				$scope.projCostStmtDtlTOs = TreeService.populateTreeData(projCostStmtDtlTOs, 0, [], 'code',
					'projCostStmtDtlTOs');
				console.log('$scope.projCostStmtDtlTOs ', $scope.projCostStmtDtlTOs);
				$scope.selectCostCode = function(projCostStmtDtlTO) {
					console.log('select cppst code ', projCostStmtDtlTO);
					deferred.resolve(projCostStmtDtlTO);
					$scope.closeThisDialog();
				}
				if (projCostStmtDtlTOs && projCostStmtDtlTOs.length){
					$scope.itemClick($scope.projCostStmtDtlTOs[0], false);
					console.log('select leave selected ', $scope.projCostStmtDtlTOs[0]);
				}
			} ]
		});
		return deferred.promise;
	};	
	return service;

}]);
