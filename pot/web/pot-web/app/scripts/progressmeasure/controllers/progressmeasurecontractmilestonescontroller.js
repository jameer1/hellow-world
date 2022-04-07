'use strict';
app.config(["$stateProvider", function ($stateProvider) {

	$stateProvider.state("progressmeasurecontractmilestones", {
		url: '/progressmeasurecontractmilestones',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/progressmeasure/progressmeasurecontractmilestones.html',
        controller: 'ProgressMeasureContractMileStonesController'
			}
		}
	})
}]).controller("ProgressMeasureContractMileStonesController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "blockUI", "ProjPMCMService",
"ProjEmpClassService", "GenericAlertService", "EpsProjectSelectFactory", "TreeService","EmpCostCodeFactory",
"EmpChargeOutRatesFactory","ProjectStatusService",
function ($rootScope, $scope, $q, $state, ngDialog, blockUI, ProjPMCMService,
	ProjEmpClassService, GenericAlertService, EpsProjectSelectFactory, TreeService,EmpCostCodeFactory,
	EmpChargeOutRatesFactory,ProjectStatusService) {
  $scope.contractType = "LContractMile";
	$scope.SORData = [];
	var deleteSORData = [];
	$scope.searchProject = {};
	$scope.activeFlag = 0
	$scope.statusDate = "";
	$scope.isFinishDateAllowed = true;
	$scope.isStatusInprogress  = false;
  $scope.projCostItemEntitiesMap = {};
  $scope.projPMCMItemTOs = {};

  $scope.editOnLoadDefaultValues = function (tab) {
              console.log('Vz Before editOnLoadDefaultValues tab');
              console.log(tab);

              $scope.isFinishDateAllowed = !angular.equals(tab.pmProgressStatus,"complete");
              tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
              if(!angular.equals(tab.pmProgressStatus,"complete"))
               {
                      tab.pmActualFinishDate = "";
                      tab.pmClaimedAmount = 0;
                      tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
               }

               if(angular.equals(tab.pmPrevProgClaim,"Yes"))
               {
                 tab.pmClaimedAmount = parseInt(tab.pmContractAmount);
               }else{
                 tab.pmClaimedAmount = "";
               }
  },
	$scope.updateProgressStatus = function (tab, data) {
              $scope.isFinishDateAllowed = !angular.equals(data,"complete");
              $scope.isStatusInprogress = false;
              if(angular.equals(data,"inProgress")){
              		$scope.isStatusInprogress = true;
              }
              tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
              if(!angular.equals(data,"complete"))
               {
               		  tab.pmPrevProgClaim="";
                      tab.pmActualFinishDate = "";
                      tab.pmClaimedAmount = 0;
                      tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
               }
               if(angular.equals(data,"complete"))
               {
                      tab.pmBalanceAmount = 0;
               }
               
  },
  $scope.updatebalanceamount = function (tab, data) {
             
              tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
              if(!angular.equals(data,"complete"))
               {
                      tab.pmActualFinishDate = "";
                      tab.pmClaimedAmount = 0;
                      tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
               }
  },
  $scope.updatePrevProgClaim = function (tab, data) {
        if(angular.equals(data,"Yes"))
        {
          tab.pmClaimedAmount = parseInt(tab.pmContractAmount);
        }else{
          tab.pmClaimedAmount = "";
        }

               // console.log('(pmContractAmount pmClaimedAmount');
               // console.log(parseInt(tab.pmContractAmount));
               // console.log(parseInt(tab.pmClaimedAmount));
              // Logic for pmBalanceAmount parseInt(tab.pmClaimedAmount) ==0 &&
              tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);
              if(!angular.equals(tab.pmProgressStatus,"complete"))
               {
                      tab.pmBalanceAmount = 0;
               }
               // console.log('pmBalanceAmount');
               // console.log(tab.pmBalanceAmount);
    },
    $scope.updatePmClaimedAmount = function (tab, data) {
            tab.pmBalanceAmount = parseInt(tab.pmContractAmount) - parseInt(tab.pmClaimedAmount);

             // console.log('pmBalanceAmount');
             // console.log(tab.pmBalanceAmount);
        },
	$scope.getUserProjects = function () {
		//var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		// console.log('Before Hitting getUserProjects');
    // console.log($scope.contractType);
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjectsByContactType($scope.contractType);
		// console.log('userProjectSelection');
    // console.log(userProjectSelection);
		userProjectSelection.then(function (data) {
		// console.log('data');
    // console.log(data);
			$scope.SORData = [];
			$scope.searchProject = data.searchProject;
		},
			function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
	}, $scope.getPMCMDetails = function () {

	        // console.log('scope getPMCMDetails');
    			// console.log($scope.searchProject);
     //$scope.statusDate = statusDate;
     // console.log('scope.statusDate');
     // console.log($scope.statusDate);
		var sorReq = {
			"status": 1,
			"projId": $scope.searchProject.projId,
			"projStatusDate": $scope.statusDate
		};
		/*if (sorReq.projId == null || sorReq.status == undefined) {
			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
			return;
		}*/
		if (sorReq.projId == null) {
    			GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
    			return;
    		}
		ProjPMCMService.getPMCMDetails(sorReq).then(function (data) {

      console.log(data);
      console.log('projCostItemEntitiesMap');
      console.log(data.projCostItemEntitiesMap);
      console.log('projPMCMItemTOs');
      		$scope.projPMCMItemTOs = data.projPMCMItemTOs;
      console.log(data.projPMCMItemTOs);
      $scope.projCostItemEntitiesMap = data.projCostItemEntitiesMap;
      console.log('CC-G-EF');
      console.log($scope.projCostItemEntitiesMap["CC-G-EF"]);
			$scope.activeFlag = 1
			$scope.SORData = populateSorData(data.projPMCMItemTOs);
			// console.log('getPMCMDetails SORData');
			// console.log($scope.SORData);
			if ($scope.SORData <= 0) {
				GenericAlertService.alertMessage("PMCM Details  are not available for given search criteria", 'Warning');
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting EPS Projects", 'Error');
		});

		//----


	};

	function populateSorData(data) {
		return TreeService.populateTreeData(data, 0, [], 'code', 'childSORItemTOs');
	}

	$scope.resetSORData = function () {
		$scope.activeFlag = 0;
		$scope.SORData = [];
		$scope.searchProject = {};
		$scope.statusDate = moment(new Date()).format('DD-MMM-YYYY');
	}
	$scope.rowSelect = function (rowData) {
		if (rowData.select) {
			deleteSORData.push(rowData.id);
		} else {
			deleteSORData.splice(deleteSORData.indexOf(rowData.id), 1);
		}

	}

	var sorpopup;
	var addSORservice = {};
	$scope.addSORDetails = function (actionType, itemData, projId) {
      console.log('$scope.addSORDetails');
        console.log(actionType);
        console.log(itemData);
        console.log(projId);
		if (deleteSORData.length > 0 && actionType == 'Add') {
			GenericAlertService.alertMessage("System will allow only one operation at a time", 'Warning');
			return;
		}
		sorpopup = addSORservice.addProjSORDetails(actionType, itemData, projId);
		console.log('sorpopup');
    console.log(sorpopup);
		sorpopup.then(function (data) {
		console.log('data');
    console.log(data);
			$scope.SORData = populateSorData(data.projSORItemTOs);
		}, function (error) {
			GenericAlertService.alertMessage("PMCM(s) is/are failed to save", "Error");
		});
	}
	addSORservice.addProjSORDetails = function (actionType, itemData, projId) {
	   console.log('addSORservice.addProjSORDetails');
     console.log(actionType);
     console.log(itemData);
     console.log(projId);
		var deferred = $q.defer();
		if ($scope.searchProject.projId !== undefined && $scope.searchProject.projId != null) {
        console.log('$scope.searchProject.projId');
        console.log($scope.searchProject.projId);
			  sorpopup = ngDialog.open({
				template: 'views/progressmeasure/progressmeasurecontractmilestonespopup.html',
				scope: $scope,
				closeByDocument: false,
				showClose: false,
				className: 'ngdialog ngdialog-theme-plain ng-dialogueCustom0',
				controller: ['$scope', function ($scope) {
					$scope.action = actionType;
					$scope.addSORData = [];
					//$scope.measurements = [];
					$scope.projSorId = null;
					var sorData = [];
					$scope.projSorPopupItemClick = function (item, expand) {
						TreeService.dynamicTreeItemClick($scope.addSORData, item, expand);
					};
					if (itemData) {
						$scope.pcode = itemData.name;
						$scope.projSorId = itemData.id;
					}
					$scope.projSorifOnLoad = function () {
					console.log('Viz $scope.projSorId');
          console.log($scope.projSorId);
					console.log('Viz $scope.searchProject.projId');
          console.log($scope.searchProject.projId);

            var projPMCMEditReq = {
              "status": "1",
              "pmId": $scope.projSorId,
              "projId": $scope.searchProject.projId
            };
            ProjPMCMService.projSorifOnLoad(projPMCMEditReq).then(function (data) {
            console.log('Viz projSorifOnLoad');
            console.log(data);
            console.log(data.projSORItemTOs);
              //$scope.measurements = data.measureUnitResp.measureUnitTOs;
              if ($scope.projSorId != null) {
                $scope.addSORData = data.projSORItemTOs;
              }
              sorData = data.projSORItemTOs;
              if (sorData && sorData.length)
                $scope.addSORData = TreeService.populateDynamicTreeData(sorData, 0, [], 'code', 'childSORItemTOs');
            });

          }
          /*, $scope.updateMeasureId = function (tab, data) {
            tab.measureId = data.id;
          }*/

					if (actionType === 'Add') {
					  //pmStatusDate=$scope.statusDate;
						$scope.addSORData.push(angular.copy({
							"select": false,
							"projId": projId,
							"parentId": null,
							"item": false,
							"deleteFlag": false,
							"status": 1,
							"code": '',
							"name": '',
							"pmStatusDate":$scope.statusDate,
							"comments": '',
							"childSORItemTOs": []
						}));
					}

					$scope.addSORData = TreeService.populateDynamicTreeData($scope.addSORData, 0, [], 'code', 'childSORItemTOs');
          console.log('$scope.addSORData');
          console.log($scope.addSORData);
					$scope.addSORSUBGroup = function (tabData, projId, itemIndex) {
						// check for input fileds validations
						if ($scope.checkDataValidationRecursively(tabData, itemIndex)) {
						//pmStatusDate=$scope.statusDate;
							const obj = angular.copy({
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"item": false,
								"deleteFlag": false,
								"status": 1,
								"code": '',
								"name": '',
								"pmStatusDate":$scope.statusDate,
								"comments": '',
								"childSORItemTOs": [],
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							});
							$scope.addSORData = TreeService.addItemToTree($scope.addSORData, tabData,
								obj, itemIndex, 'childSORItemTOs');
						}
					}, $scope.addSORItem = function (tabData, projId, index) {
						// check for input fileds validations
						if ($scope.checkDataValidationRecursively(tabData, index)) {

							const obj = angular.copy({
								"select": false,
								"projId": projId,
								"parentId": tabData.id,
								"status": 1,
								"deleteFlag": false,
								"code": '',
								"name": '',
								"item": true,
								"pmCostCodeId":'',
								"pmCostCodeName":'',
								"pmCurrency":'',
								"pmStatusDate":'',
								"pmContractAmount":'',
								"pmSchedFinishDate":'',
								"pmProgressStatus":'',
								"pmActualFinishDate":'',
								"pmPrevProgClaim":'',
								"pmClaimedAmount":'',
								"pmBalanceAmount":'',
								"comments":'',
              /*"measureUnitTO": '',
								"measureId": null,
								"manPowerHrs": '',
								"labourRate": '',
								"plantRate": '',
								"materialRate": '',
								"othersRate": '',
								"quantity": '',
								"total": '',
								"amount": '',
								"comments": '',*/
								"level": (tabData.level + 1),
								"collapse": false,
								"expand": true,
							});
							$scope.addSORData = TreeService.addItemToTree($scope.addSORData, tabData,
								obj, index, 'childSORItemTOs');

						}



					};

					$scope.deleteSOR = function (index) {
						TreeService.deleteDynamicTreeItem($scope.addSORData, index);
					};

					/**
					 * Checks all childs for data validation recursively.
					 * @param {*} tabData
					 */
					$scope.checkDataValidationRecursively = function (tabData, index) {
						if (!tabData.code || !tabData.name) {
							tabData.invalidField = true;
							console.log('checkDataValidationRecursively:false');
							return false;
						}

						for (++index; index < $scope.addSORData.length; index++) {
						  console.log('checkDataValidationRecursively:false : '+$scope.checkDataValidationRecursively($scope.addSORData[index], index));
							return $scope.checkDataValidationRecursively($scope.addSORData[index], index);
						}
						console.log('checkDataValidationRecursively:TRUE : ');
						return true;

					};

					$scope.convertToNumber = function (val) {
						return Number(val);
					};
					$scope.saveSORDetails = function () {console.log($scope.projPMCMItemTOs);
						const data = TreeService.extractTreeDataForSaving($scope.addSORData, 'childSORItemTOs');console.log(data);
						if(actionType == 'Add'){
						for (const value of data) {
						for(let i=0;i<$scope.projPMCMItemTOs.length;i++){
							if ($scope.projPMCMItemTOs[i].code.toLowerCase()==value.code.toLowerCase()) {
								GenericAlertService.alertMessage('Group ID already exists', "Warning");
								return;
							}
							/*if ($scope.projPMCMItemTOs[i].name==value.name) {
								GenericAlertService.alertMessage('Already SOR group name', "Warning");
								return;
							}*/
							}
							

							for (const value1 of value.childSORItemTOs) {
							for(let i=0;i<$scope.projPMCMItemTOs.length;i++){
							for(let j=0;j<$scope.projPMCMItemTOs[i].childSORItemTOs.length;j++){
								if ($scope.projPMCMItemTOs[i].childSORItemTOs[j].code.toLowerCase()==value1.code.toLowerCase()) {
									GenericAlertService.alertMessage('Sub Group ID already exists', "Warning");
									return;
								}
								/*if (value1.name == null || value1.name == undefined || value1.name == "") {
									GenericAlertService.alertMessage('Please enter SOR sub group name', "Warning");
									return;
								}*/}}
								if (value1.childSORItemTOs)
									for (const value2 of value1.childSORItemTOs) {
									for(let i=0;i<$scope.projPMCMItemTOs.length;i++){
									for(let j=0;j<$scope.projPMCMItemTOs[i].childSORItemTOs.length;j++){
									for(let k=0;k<$scope.projPMCMItemTOs[i].childSORItemTOs[j].childSORItemTOs.length;k++){
										if ($scope.projPMCMItemTOs[i].childSORItemTOs[j].childSORItemTOs[k].code.toLowerCase()==value2.code.toLowerCase()) {
											GenericAlertService.alertMessage('Item id already exists', "Warning");
											return;
										}}}}
										/*if (value2.name == null || value2.name == undefined || value2.name == "") {
											GenericAlertService.alertMessage('Please enter item name', "Warning");
											return;
										}
										if (value2.measureUnitTO == undefined || value2.measureUnitTO == null || value2.measureUnitTO == '') {
											GenericAlertService.alertMessage('select unit of measure', "Warning");
											return;
										}
										if (value2.manPowerHrs == undefined || value2.manPowerHrs == null || value2.manPowerHrs == '') {
											GenericAlertService.alertMessage('Please enter Man Power hrs', "Warning");
											return;
										}
										if (value2.labourRate == undefined || value2.labourRate == null || value2.labourRate == '') {
											GenericAlertService.alertMessage('Please enter labour Rate', "Warning");
											return;
										}
										if (value2.plantRate == undefined || value2.plantRate == null || value2.plantRate == '') {
											GenericAlertService.alertMessage('Please enter plant Rate', "Warning");
											return;
										}
										if (value2.materialRate == undefined || value2.materialRate == null || value2.materialRate == '') {
											GenericAlertService.alertMessage('Please enter material Rate', "Warning");
											return;
										}
										if (value2.othersRate == undefined || value2.othersRate == null || value2.othersRate == '') {
											GenericAlertService.alertMessage('Please enter others Rate', "Warning");
											return;
										}
										if (value2.quantity == undefined || value2.quantity == null || value2.quantity == '') {
											GenericAlertService.alertMessage('Please enter quantity', "Warning");
											return;
										}
										if (value2.comments == undefined || value2.comments == null || value2.comments == '') {
											GenericAlertService.alertMessage('Please enter comments', "Warning");
											return;
										}
									}*/
							}}
						}}
						var sorSaveReq = {
							"projSORItemTOs": data,
							"projId": $scope.searchProject.projId
						};
						blockUI.start();
						ProjPMCMService.saveSORDetails(sorSaveReq).then(function (data) {
							blockUI.stop();
							if (data.status != null && data.status !== undefined && data.status === 'Info') {
								var projSORTOs = data.projSORItemTOs;
								// var succMsg = GenericAlertService.alertMessageModal('Project SOR(s) is/are ' + data.message, data.status);
								var succMsg = GenericAlertService.alertMessageModal('Project PMCM(s) saved successfully ',"Info");
								succMsg.then(function (data) {
									$scope.closeThisDialog();
									var returnPopObj = {
										"projSORItemTOs": projSORTOs
									};
									deferred.resolve(returnPopObj);
									$scope.getPMCMDetails();
								}, function (error) {
									blockUI.stop();
									GenericAlertService.alertMessage("PMCM(s) is/are failed to save", "Error");
								});
							}
						});
					}
				}]
			});
			return deferred.promise;
		} else {
			GenericAlertService.alertMessage("Please select Project Id", 'Warning');
		}
	}, $scope.deactivateSORDetails = function () {

		if (deleteSORData == undefined || deleteSORData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one PMCM to Deactivate", "Warning");
			return;
		}
		var sorDeactivateReq = {
			"projSORItemIds": deleteSORData,
			"status": 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
			ProjPMCMService.deactivateSORDetails(sorDeactivateReq).then(function (data) {
				GenericAlertService.alertMessage("PMCM(s) Deactivated successfully", "Info");
				deleteSORData = [];
				$scope.getPMCMDetails();
			}, function (error) {
				GenericAlertService.alertMessage("PMCM(s) is/are failed to deactivate", "Error");
			});
		}, function (data) {
			deleteSORData = [];
			$scope.getPMCMDetails();
		})
	};

	$scope.sorItemClick = function (item, collapse) {
		TreeService.treeItemClick(item, collapse, 'childSORItemTOs');
	};

	$scope.show = function (comments) {
		ngDialog.open({
			template: 'views/projectlib/sow/viewpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom6',
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.comments = comments;
			}]
		});
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
			template: 'views/help&tutorials/projectshelp/projsorhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
	//Vij
    $scope.getCostCode = function(tab) {
    					var onLoadReq = {
    						"status" : 1,
    						"projId": $scope.searchProject.projId
    					};
    					// console.log('Vij Local onLoadReq');
              // console.log(onLoadReq);
    					var costCodetPopUp = EmpCostCodeFactory.getCostCodeDetails(onLoadReq);
    					// console.log('Vij Local costCodetPopUp');
              // console.log(costCodetPopUp);
    					costCodetPopUp.then(function(data) {
    					// console.log('Vij Local data');
              // console.log(data);
    					//tab.leaveCostItemId = data.selectedRecord.id;
    					tab.pmCostCodeId = data.selectedRecord.code;
    					tab.pmCostCodeName = data.selectedRecord.name;
              tab.pmStatusDate = $scope.statusDate;

    					}, function(error) {
    						GenericAlertService.alertMessage("Error occured while selecting cost code details", 'Error');
    					});

    					ProjectStatusService.getProjGenerals(onLoadReq).then(function (data) {
                  			 // console.log('Vij ProjectStatusService getProjGenerals');
                         // console.log(data);
                        $scope.generalValues = data.projGeneralMstrTO;
                        tab.pmCurrency = data.projGeneralMstrTO.currency;
                        // console.log(tab.pmCurrency);

                  			}, function (error) {
                  				GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
                  			});
    				}
    				
    				var date=new Date();
    				$scope.statusDate=moment(date).format('DD-MMM-YYYY');
}]);
