'use strict';

app.factory('EpsProjectSelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "EpsService", "GenericAlertService", "TreeService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, EpsService,
	GenericAlertService, TreeService) {
	var service = {};
	var contractTypeReq="";
	service.getEPSUserProjectsByContactType = function (contractType) {
	    // console.log('Got Call at Factory getEPSUserProjectsByContactType ');
      // console.log(contractType);
      // console.log(contractType);
  		var deferred = $q.defer();
  		var req = {
  			"status": 1,
  			"contractType": contractType
  		};
  		blockUI.start();
  		// console.log('contractType');
      // console.log(contractType);
      // console.log('req');
      // console.log(req);
  		EpsService.getEPSUserProjects(req).then(function (data) {
  		contractTypeReq=contractType;
  		// console.log('After Result contractType');
  		// console.log(contractTypeReq);
  			blockUI.stop();
  			// console.log('data');
        // console.log(data);
  			var popupdata = service.openPopup(data.epsProjs);
  			// console.log('eps factory popupdata');
        // console.log(popupdata);
  			popupdata.then(function (resultData) {
  			// console.log('eps factory resultData');
        // console.log(resultData);
        contractTypeReq="";
  				deferred.resolve(resultData);
  			});
  		}, function (error) {
  			blockUI.stop();
  			GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
  		});
  		return deferred.promise;

  	},
	service.getEPSUserProjects = function () {
	  contractTypeReq="";
		var deferred = $q.defer();
		var req = {
			"status": 1
		};
		blockUI.start();
		EpsService.getEPSUserProjects(req).then(function (data) {
			blockUI.stop();
			var popupdata = service.openPopup(data.epsProjs);
			popupdata.then(function (resultData) {
				deferred.resolve(resultData);
			});
		}, function (error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
		});
		return deferred.promise;

	}, service.openPopup = function (epsProjs) {
		var deferred = $q.defer();
		var epsProjTreePopup = ngDialog.open({
			template: 'views/common/epsprojectselectpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				var selectedEpsProjects = [];
				$scope.epsData = TreeService.populateTreeData(epsProjs, 0, [], 'projCode',
					'childProjs');
				$scope.itemClick = function (item, expand) {
					TreeService.treeItemClick(item, expand, 'childProjs');
				};

				$scope.selectEPSProject = function (item) {
				// console.log('On selectEPSProject ');
				// console.log(contractTypeReq);
				if(!item.enableContractType && (contractTypeReq=='LContractMile'
				                                 || contractTypeReq=='SORcontract'
				                                 || contractTypeReq=='CPPTypecontract'))
				{
				  GenericAlertService.alertMessage("Not a valid Contract Type", "Warning");
				  return;
				}
					var returnPopObj = {
						"searchProject": {
							"projId": item.projId,
							"projName": item.projName,
							"parentName": item.parentName,
							"projCode": item.projCode,
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
					contractTypeReq="";
				};
				for (const obj of $scope.epsData) {
					$scope.itemClick(obj, false);
				}


			}]
		});
		return deferred.promise;
	};

	return service;

}]);
