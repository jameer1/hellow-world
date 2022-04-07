'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("profitcentre", {
		url: '/profitcentre',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/finance/profitcentre/profitcentre.html',
				controller: 'ProfitCentreController'
			}
		}
	})
}]).controller("ProfitCentreController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "ProfitCentreService", "GenericAlertService", "TreeService", function ($rootScope, $scope, $q, $state, ngDialog, blockUI,
	ProfitCentreService, GenericAlertService, TreeService) {
	var deleteProfitCentreData = [];
	$scope.MaterialData = [];
	$scope.sortReverse = false;
	$scope.searchProject = {};
	var deferred = $q.defer();
	$scope.profitCentreTOs = [];
	$scope.getProfitCentres = function (click) {
		ProfitCentreService.getProfitCentres($scope.profitGetReq).then(function (data) {
			$scope.profitCentreTOs = populateProfitCentreData(data.profitCentreTOs, 0, []);
			if(click=='click'){
				if ($scope.profitCentreTOs.length <= 0) {
					GenericAlertService.alertMessage('Profit Center Details are not available for given search criteria', "Warning");
					return;
				}
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Profit Centers", 'Error');
		});
	},
		$scope.profitGetReq = {
			"code": null,
			"name": null,
			"status": 1
		}
	$scope.resetProfitCentres = function () {
		$scope.profitCentreTOs = [];
		$scope.profitGetReq = {}
		$scope.getProfitCentres();
	}
	$scope.profitSelect = function (tab) {
		if (tab.select) {
			deleteProfitCentreData.push(tab.id);
		} else {
			deleteProfitCentreData.splice(deleteProfitCentreData.indexOf(tab.id), 1);
		}
	}

	function populateProfitCentreData(data, level, profitCentreTOs) {
		return TreeService.populateTreeData(data, level, profitCentreTOs, 'code', 'childProfitCentreTOs');
	}



	var profitpopup;
	var addProfitservice = {};
	$scope.editProfitCentreData = function (actionType, itemData) {
		profitpopup = addProfitservice.addProfitCentres(actionType, itemData);
		profitpopup.then(function (data) {
			$scope.profitCentreTOs = populateProfitCentreData(data.profitCentreTOs, 0, []);
		}, function (error) {
			GenericAlertService.alertMessage("Error Occured While Getting  Profit Centers", "Error");
		});
	}

	addProfitservice.addProfitCentres = function (actionType, itemData) {
		var deferred = $q.defer();
		profitpopup = ngDialog.open({
			template: 'views/finance/profitcentre/profitcentrepopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.pcode = null;
				$scope.action = actionType;
				$scope.editProfitCentreData = [];
				$scope.proCategory = [];
				$scope.measurements = [];
				$scope.profitId = null;
				$scope.popupProfitCentreItemClick = function (item, collapse) {
					TreeService.dynamicTreeItemClick($scope.editProfitCentreData, item, collapse)
				};
				if (itemData) {
					$scope.pcode = itemData.name;
					$scope.profitId = itemData.id;
				}
				if (actionType === 'Add') {
					$scope.editProfitCentreData.push({
						"select": false,
						"clientId": $scope.clientId,
						"parentId": null,
						"item": false,
						"deleteFlag": false,
						"status": 1,
						"code": '',
						"name": '',
						"childProfitCentreTOs": [],
						"level": 0,
						"collapse": false,
						"expand": true
					});
				} else {
					$scope.editProfitCentreData = [angular.copy(itemData)];
					itemData = [];
				}
				$scope.editProfitCentreData = TreeService.populateDynamicTreeData($scope.editProfitCentreData, 0, [],
					'code', 'childProfitCentreTOs');
				console.log($scope.editProfitCentreData);
				$scope.addProfitCentreSubGroup = function (profitCentre, index) {

					const itemToAdd = angular.copy({
						"select": false,
						"clientId": $scope.clientId,
						"parentId": profitCentre.id,
						"item": false,
						"deleteFlag": false,
						"status": 1,
						"code": null,
						"name": null,
						"childProfitCentreTOs": [],
						"level": (profitCentre.level + 1),
						"collapse": false,
						"expand": true,
						"objIndex": null
					});
					$scope.editProfitCentreData = TreeService.addItemToTree($scope.editProfitCentreData, profitCentre, itemToAdd, index, 'childProfitCentreTOs');
				}

				$scope.addProfitItem = function (profitCentre, index) {
					
					const itemToAdd = angular.copy({
						"select": false,
						"clientId": $scope.clientId,
						"parentId": profitCentre.id,
						"item": true,
						"deleteFlag": false,
						"status": 1,
						"code": null,
						"name": null,
						"level": (profitCentre.level + 1),
						"collapse": false,
						"expand": true,
						"objIndex": null,
						"dueDate": '',
						"payCycle": '',
						"weeklist": '',
						"monthvalue": '',
						"month": ''

					});
					$scope.editProfitCentreData = TreeService.addItemToTree($scope.editProfitCentreData, profitCentre, itemToAdd, index, 'childProfitCentreTOs');
				}
				$scope.deleteProfitCentre = function (index) {
					TreeService.deleteDynamicTreeItem($scope.editProfitCentreData, index);
				}

				$scope.payCycle = ['Weekly', 'Fortnightly', 'Monthly'];
				
				$scope.monthTOs = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
				$scope.weekTOs = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
			
				$scope.saveProfitCentres = function () {
					const data = TreeService.extractTreeDataForSaving($scope.editProfitCentreData, 'childProfitCentreTOs');
					var saveReq = {
						"profitCentreTOs": data,
					};
                  console.log(data);
					angular.forEach($scope.editProfitCentreData, function (value) {
						if ((value.code == "" || value.code == undefined || value.name == "" || value.name == undefined || value.code == null || value.name == null)) {
						 	GenericAlertService.alertMessage("Profit Center Code and Profit Center should  be at least one alphabet is mandatory ", "Warning");
							angular.break();
							return;
						}
						angular.forEach(value.childProfitCentreTOs, function (value1) {
							if ((value1.code === "" || value1.code === undefined || value1.name === "" || value1.name === undefined || value1.code == null || value1.name == null)) {
								GenericAlertService.alertMessage("Profit Center Code and Profit Center should  be at least one alphabet is mandatory ", "Warning");
								angular.break();
								return;
							}
							angular.forEach(value1.childProfitCentreTOs, function (value2) {
								if ((value2.code === "" || value2.code === undefined || value2.name === "" || value2.name === undefined || value2.code == null || value2.name == null)) {
									GenericAlertService.alertMessage("Profit Center Code and Profit Center should  be at least one alphabet is mandatory ", "Warning");
									angular.break();
									return;
								}

								angular.forEach(value2.childProfitCentreTOs, function (value3) {
									if ((value3.code === "" || value3.code === undefined || value3.name === "" || value3.name === undefined || value3.code == null || value3.name == null)) {
										GenericAlertService.alertMessage("Profit Center Code and Profit Center should  be at least one alphabet is mandatory ", "Warning");
										angular.break();
										return;
									}
								})
							})
						})
					})
					blockUI.start();
					ProfitCentreService.saveProfitCentres(saveReq).then(function (data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var results = data.profitCentreTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Profit Center  Details   is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Profit Center details saved successfully',"Info");
							succMsg.then(function (data) {
								$scope.closeThisDialog();
								var returnPopObj = {
									"profitCentreTOs": results
								};
								deferred.resolve(returnPopObj);
							}, function (error) {
								blockUI.stop();
								GenericAlertService.alertMessage("Profit Center  Details is/are failed to Save", "Error");
							});
						}
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Profit Center  Details is/are failed to Save", "Error");
					});
				}
			}]

		});
		return deferred.promise;
	}

	$scope.deleteProfitCentres = function () {
		console.log("++++++++++++++++",deleteProfitCentreData);
		if (deleteProfitCentreData == undefined || deleteProfitCentreData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Profit Center   to deactivate", 'Warning');
			return;
		}
		for(var i=0; i<$scope.profitCentreTOs.length; i++){
			if(deleteProfitCentreData==$scope.profitCentreTOs[i].id){
				if($scope.profitCentreTOs[i].projAssigned){
					GenericAlertService.alertMessage("Profit Center(s) cannot be deactivated as it assigned to project ", "Warning");
					return;
				}
			}
		}
		for(var i=0; i<$scope.profitCentreTOs.length; i++){
		for(var j=0; j<$scope.profitCentreTOs[i].childProfitCentreTOs.length; j++){
		if(deleteProfitCentreData == $scope.profitCentreTOs[i].childProfitCentreTOs[j].id){
		alert($scope.profitCentreTOs[i].childProfitCentreTOs[j].projAssigned);
				if($scope.profitCentreTOs[i].childProfitCentreTOs[j].projAssigned){
					GenericAlertService.alertMessage("Profit Center(s) cannot be deactivated as it assigned to project ", "Warning");
					return;
				}
		}
		}
		for(var i=0; i<$scope.profitCentreTOs.length; i++){
		for(var j=0; j<$scope.profitCentreTOs[i].childProfitCentreTOs.length; j++){
		for(var k=0; k<$scope.profitCentreTOs[i].childProfitCentreTOs[j].childProfitCentreTOs.length; k++){
			
			if(deleteProfitCentreData == $scope.profitCentreTOs[i].childProfitCentreTOs[j].childProfitCentreTOs[k].id){
		alert($scope.profitCentreTOs[i].childProfitCentreTOs[j].projAssigned);
				if($scope.profitCentreTOs[i].childProfitCentreTOs[j].childProfitCentreTOs[k].projAssigned){
					GenericAlertService.alertMessage("Profit Center(s) cannot be deactivated as it assigned to project ", "Warning");
					return;
				}
		}
			}}}
			
		}
		var delReq = {
			"profitIds": deleteProfitCentreData,
			"status": 2
		};

		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
			ProfitCentreService.deleteProfitCentres(delReq).then(function (data) {
				GenericAlertService.alertMessage("Profit Center(s) Deactivated successfully", "Info");
				deleteProfitCentreData = [];
				$scope.getProfitCentres();
			}, function (error) {
				GenericAlertService.alertMessage('Profit Center  (s) is/are failed to deactivate', "Error");
			});
		}, function (data) {
			$scope.ProfitCentres();
		})
	}

	$scope.profitCentreitemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childProfitCentreTOs');
	};
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/profitcenterhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);
