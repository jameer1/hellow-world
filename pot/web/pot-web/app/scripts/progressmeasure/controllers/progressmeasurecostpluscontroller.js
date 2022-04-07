'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("progressmeasurecostplus", {
        url: '/progressmeasurecostplus',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/progressmeasure/progressmeasurecostplusper.html',
                controller: 'ProgressMeasureCostPlusPerController'
            }
        }
    })

}]).controller('ProgressMeasureCostPlusPerController', ["$rootScope", "$scope", "ProjSOWService", "$q", "blockUI",
    "ProjectStatusService", "GenericAlertService", "ProjManPowerFactory", "ProjectCrewPopupService", "EpsService", "EpsProjectMultiSelectFactory",
    "ProjectSettingCostItemFactory", "ProjectSettingSOWItemFactory", "ProjStatusMileStonesFactory",
    "ProjStatusSubContractFactory", "$filter", "TreeService", "ProjectStatusResourceStatusValueService", "ngDialog","ProjPMCPService","ProjPMCMService",
    "EstimateToCompleteService","ProjectScheduleService","EpsProjectSelectFactory",
    function ($rootScope, $scope, ProjSOWService, $q, blockUI,
        ProjectStatusService, GenericAlertService, ProjManPowerFactory, ProjectCrewPopupService, EpsService, EpsProjectMultiSelectFactory,
        ProjectSettingCostItemFactory, ProjectSettingSOWItemFactory, ProjStatusMileStonesFactory,
        ProjStatusSubContractFactory, $filter, TreeService,ProjectStatusResourceStatusValueService,ngDialog, ProjPMCPService,ProjPMCMService,
        EstimateToCompleteService,ProjectScheduleService,EpsProjectSelectFactory) {

		$scope.contractType = "CPPTypecontract";
	    $scope.projCostStmtDtls = [];
        $scope.costDatamoreFlag = 0;

        let todayDate = new Date();
        let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
        $scope.pmFromStatusDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
              $scope.$watch('pmFromStatusDate', function () {
              //$scope.clearSubReportDetails();
        });
        $scope.pmToStatusDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
        $scope.$watch('pmToStatusDate', function () {
          //$scope.clearSubReportDetails();
        });
        $scope.clickForwardCostData = function (costDatamoreFlag1) {
        				if ($scope.costDatamoreFlag < 6) {
        					$scope.costDatamoreFlag = costDatamoreFlag1 + 1;
        				}
        			}, $scope.clickBackwardCostData = function (costDatamoreFlag1) {
        				if ($scope.costDatamoreFlag > 0) {
        					$scope.costDatamoreFlag = costDatamoreFlag1 - 1;
        				}
        			},

        $scope.costItemClick = function (item, expand) {
              TreeService.dynamicTreeItemClick($scope.projCostStmtDtls, item, expand);
        },

        $scope.getProjPMCPCostStatements = function () {
                //alert("getProjPMCPCostStatements");
            	  console.log("Controller getProjPMCPCostStatements");
            		/*if ($scope.selectedProjIds.length <= 0) {
            			GenericAlertService.alertMessage("Please select project to fetch report", 'Warning');
            			return;
            		}*/

            		var getCostStatReq = {
            		  "status": 1,
            			"projIds": $scope.selectedProjId,
            			//"pmFromStatusDate": $scope.pmFromStatusDate,
            			//"projStatusDate": $scope.pmToStatusDate
            			"fromDate": $scope.pmFromStatusDate,
                         "toDate": $scope.pmToStatusDate
            		}
            		console.log("getProjPMCPCostStatements req");
                console.log(getCostStatReq);
            		ProjPMCPService.getMultiProjCostStatements(getCostStatReq).then(function (data) {
                        console.log("getProjPMCPCostStatements Controller result data");
                        console.log(data); // projCostStmtDtlTOs projCostStmtDtlTOCopys
                        console.log('PMCP->projCostStmtDtlTOs'); // getting 5
                        console.log(data.projCostStmtDtlTOs);
                        // projCostStmtDtlTOCopys
                				let costData = populateCostData(data.projCostStmtDtlTOs, 0, []);
                				costData.map((treeItem) => {
                					$scope.costItemClick(treeItem, false);
                				});
                				$scope.projCostStmtDtls = costData;

                				console.log('$scope.projCostStmtDtls');
                				console.log($scope.projCostStmtDtls);

                				if ($scope.projCostStmtDtls.length > 0 && $scope.projCostStmtDtls.find(x => x.item == true).estimateType &&
                					$scope.projCostStmtDtls.find(x => x.item == true).estimateType.contains('SPI')) {
                					ProjectScheduleService.getProjBudgetCostCodeDetails(getCostStatReq).then(function (data) {

                					  console.log('Got Result for ProjectScheduleService.getProjBudgetCostCodeDetails data');
                            console.log(data);

                						calculatePlannedValues(data, $scope.projCostStmtDtls, "costId");
                						EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
                					}, function (error) {
                						GenericAlertService.alertMessage("Error occured while fetching manpower schedule values", "Error");
                					});

                				} else {
                					EstimateToCompleteService.costStatement($scope.projCostStmtDtls);
                				}
                			}, function (error) {
                				GenericAlertService.alertMessage("Error occured while getting Cost Details", "Error");
                			});
         };
        function populateCostData(data, level, costTOs, isChild, parent) {
              console.log('VXX data');
              console.log(data);

              console.log('level');
              console.log(level);

              console.log('costTOs');
              console.log(costTOs);

               console.log('isChild');
               console.log(isChild);

               console.log('parent');
               console.log(parent);
              return TreeService.populateDynamicTreeData(data, level, costTOs, 'id', 'projCostStmtDtlTOCopys',
                isChild, parent)
            }
        $scope.getUserProjects = function () {
            //var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
           // var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
           var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjectsByContactType($scope.contractType);
           
            userProjectSelection.then(function (data) {
            console.log('*** getUserProjects data');
            console.log(data);
            console.log(data.searchProject.projId);
            $scope.selectedProjId = [data.searchProject.projId,];
            //$scope.selectedProjId.fill(data.searchProject.projId);
            console.log('*** $scope.selectedProjId');
            console.log($scope.selectedProjId);
                //$scope.searchProject = {};
                $scope.searchProject = data.searchProject;
                //$scope.selectedProjIds = data.searchProject.projIds;
                //$scope.selectedClientIds = data.searchProject.clientIds;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });
        };
        
        	$scope.resetCostPlusData = function () {
					$scope.searchProject = {};
					$scope.pmToStatusDate = moment(new Date()).format('DD-MMM-YYYY');
					$scope.pmFromStatusDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
				}

        $scope.getBalanceBudget= function(tab){
        console.log('getBalanceBudget - tab.item : '+tab.item);
            $scope.balanceBudget = 0;

            //console.log('getBalanceBudget');
            var uptoDateManpowerCost =0;
            var uptoDateMaterialCost =0;
            var uptoDatePlantCost=0;
            //console.log('tab.actualCostUpToDatePeriod 1 : ' +Object.keys(tab).length);
            //console.log('tab.actualCostUpToDatePeriod 2 : ' +Object.keys(tab.actualCostUpToDatePeriod).length);
            //console.log('tab.actualCostUpToDatePeriod 3 : ' +Object.keys(tab.actualCostUpToDatePeriod.uptoDateManpowerCost).length);

            /*
            var inputHasValue = angular.isNumber(tab.actualCostUpToDatePeriod.uptoDateManpowerCost) || tab.actualCostUpToDatePeriod.uptoDateManpowerCost;
            if(inputHasValue)
            {
              uptoDateManpowerCost = tab.actualCostUpToDatePeriod.uptoDateManpowerCost;
            }
            */
            if(tab.actualCostUpToDatePeriod == null || tab.actualCostUpToDatePeriod === null || tab.actualCostUpToDatePeriod === undefined)
            {
              uptoDateManpowerCost = 0;
              uptoDateMaterialCost = 0;
              uptoDatePlantCost = 0
            }else{
              uptoDateManpowerCost = tab.actualCostUpToDatePeriod.uptoDateManpowerCost;
              uptoDateMaterialCost = tab.actualCostUpToDatePeriod.uptoDateMaterialCost;
              uptoDatePlantCost = tab.actualCostUpToDatePeriod.uptoDatePlantCost;
           }
            var actualCostPrevPeriod_prevMaterialCost = 0;
            var actualCostPrevPeriod_prevPlantCost = 0;
            var actualCostPrevPeriod_prevManpowerCost = 0;
            if(!(tab.actualCostPrevPeriod == null || tab.actualCostPrevPeriod === null || tab.actualCostPrevPeriod === undefined))
            {
              if(!(tab.actualCostPrevPeriod.prevMaterialCost == null || tab.actualCostPrevPeriod.prevMaterialCost === null || tab.actualCostPrevPeriod.prevMaterialCost === undefined))
                {
                  actualCostPrevPeriod_prevMaterialCost = tab.actualCostPrevPeriod.prevMaterialCost;
                }

              if(!(tab.actualCostPrevPeriod.prevPlantCost == null || tab.actualCostPrevPeriod.prevPlantCost === null || tab.actualCostPrevPeriod.prevPlantCost === undefined))
                {
                  actualCostPrevPeriod_prevPlantCost = tab.actualCostPrevPeriod.prevPlantCost;
                }

               if(!(tab.actualCostPrevPeriod.prevManpowerCost == null || tab.actualCostPrevPeriod.prevManpowerCost === null || tab.actualCostPrevPeriod.prevManpowerCost === undefined))
               {
                 actualCostPrevPeriod_prevManpowerCost = tab.actualCostPrevPeriod.prevManpowerCost;
               }
            }

            var actualCostReportPeriod_reportingMaterialCost = 0;
            var actualCostReportPeriod_reportingPlantCost = 0;
            var actualCostReportPeriod_reportingManpowerCost = 0;
            if(!(tab.actualCostReportPeriod == null || tab.actualCostReportPeriod === null || tab.actualCostReportPeriod === undefined))
            {
              if(!(tab.actualCostReportPeriod.reportingMaterialCost == null || tab.actualCostReportPeriod.reportingMaterialCost === null || tab.actualCostReportPeriod.reportingMaterialCost === undefined))
                {
                  actualCostReportPeriod_reportingMaterialCost = tab.actualCostReportPeriod.reportingMaterialCost;
                }

              if(!(tab.actualCostReportPeriod.reportingPlantCost == null || tab.actualCostReportPeriod.reportingPlantCost === null || tab.actualCostReportPeriod.reportingPlantCost === undefined))
                {
                  actualCostReportPeriod_reportingPlantCost = tab.actualCostReportPeriod.reportingPlantCost;
                }

               if(!(tab.actualCostReportPeriod.reportingManpowerCost == null || tab.actualCostReportPeriod.reportingManpowerCost === null || tab.actualCostReportPeriod.reportingManpowerCost === undefined))
               {
                 actualCostReportPeriod_reportingManpowerCost = tab.actualCostReportPeriod.reportingManpowerCost;
               }
            }

            var actualCostUpToDatePeriod_uptoDateMaterialCost = 0;
            var actualCostUpToDatePeriod_uptoDatePlantCost = 0;
            var actualCostUpToDatePeriod_uptoDateManpowerCost = 0;
            if(!(tab.actualCostUpToDatePeriod == null || tab.actualCostUpToDatePeriod === null || tab.actualCostUpToDatePeriod === undefined))
            {
              if(!(tab.actualCostUpToDatePeriod.uptoDateMaterialCost == null || tab.actualCostUpToDatePeriod.uptoDateMaterialCost === null || tab.actualCostUpToDatePeriod.uptoDateMaterialCost === undefined))
                {
                  actualCostUpToDatePeriod_uptoDateMaterialCost = tab.actualCostUpToDatePeriod.uptoDateMaterialCost;
                }

              if(!(tab.actualCostUpToDatePeriod.uptoDatePlantCost == null || tab.actualCostUpToDatePeriod.uptoDatePlantCost === null || tab.actualCostUpToDatePeriod.uptoDatePlantCost === undefined))
                {
                  actualCostUpToDatePeriod_uptoDatePlantCost = tab.actualCostUpToDatePeriod.uptoDatePlantCost;
                }

               if(!(tab.actualCostUpToDatePeriod.uptoDateManpowerCost == null || tab.actualCostUpToDatePeriod.uptoDateManpowerCost === null || tab.actualCostUpToDatePeriod.uptoDateManpowerCost === undefined))
               {
                 actualCostUpToDatePeriod_uptoDateManpowerCost = tab.actualCostUpToDatePeriod.uptoDateManpowerCost;
               }
            }

            //console.log('uptoDateManpowerCost'+uptoDateManpowerCost);
            var originalBudget = tab.originalCostBudget.labourCost+tab.originalCostBudget.materialCost+tab.originalCostBudget.plantCost+tab.originalCostBudget.otherCost;
            var revisedBudget = tab.revisedCostBudget.labourCost+tab.revisedCostBudget.materialCost+tab.revisedCostBudget.plantCost+tab.revisedCostBudget.otherCost;
            //var updateActualCostTotal = uptoDateManpowerCost+uptoDateMaterialCost+uptoDatePlantCost;
            /*
            var updateActualCostTotal = tab.actualCostPrevPeriod.prevMaterialCost+tab.actualCostPrevPeriod.prevPlantCost+ tab.actualCostReportPeriod.prevManpowerCost
                                        +tab.actualCostReportPeriod.reportingMaterialCost+tab.actualCostReportPeriod.reportingPlantCost+tab.actualCostReportPeriod.reportingManpowerCost
                                        +tab.actualCostUpToDatePeriod.uptoDateMaterialCost+tab.actualCostUpToDatePeriod.uptoDatePlantCost+tab.actualCostUpToDatePeriod.uptoDateManpowerCost;

            var updateActualCostTotal = actualCostPrevPeriod_prevMaterialCost+actualCostPrevPeriod_prevPlantCost+ actualCostPrevPeriod_prevManpowerCost
                                        +actualCostReportPeriod_reportingMaterialCost+actualCostReportPeriod_reportingPlantCost+actualCostReportPeriod_reportingManpowerCost
                                        +actualCostUpToDatePeriod_uptoDateMaterialCost+actualCostUpToDatePeriod_uptoDatePlantCost+actualCostUpToDatePeriod_uptoDateManpowerCost;
            */
             var updateActualCostTotal = actualCostUpToDatePeriod_uptoDateMaterialCost+actualCostUpToDatePeriod_uptoDatePlantCost+actualCostUpToDatePeriod_uptoDateManpowerCost;

            //console.log('originalBudget : '+originalBudget);
            //console.log('revisedBudget : '+revisedBudget);
            var finalBudget = 0;
            if(revisedBudget>0)
            {
                finalBudget = revisedBudget;
            }else{
                finalBudget = originalBudget;
            }
            //console.log('finalBudget : '+finalBudget);
            var resultBalanceBudget = finalBudget - updateActualCostTotal;
            if(tab.item)
            {
              $scope.balanceBudget = resultBalanceBudget;
            }else{
              $scope.balanceBudget = 0;
            }

          //console.log('resultBalanceBudget : '+resultBalanceBudget+'');
          console.log('resultBalanceBudget : '+ finalBudget + '-' +updateActualCostTotal+'='+resultBalanceBudget);
          return resultBalanceBudget;
        }
        $scope.reportTypes = [{
            code: "SOR",
            name: "Schedule Of Rates",
        }, {
            code: "Cost",
            name: "Actual Cost",
        }];
        $scope.costDatamoreFlag = 0;
        $scope.clickForwardCostData = function (costDatamoreFlag1) {
            if ($scope.costDatamoreFlag < 5) {
                $scope.costDatamoreFlag = costDatamoreFlag1 + 1;
            }
        }, $scope.clickBackwardCostData = function (costDatamoreFlag1) {
            if ($scope.costDatamoreFlag > 0) {
                $scope.costDatamoreFlag = costDatamoreFlag1 - 1;
            }
        }
        var HelpService = {};
        $scope.helpPage = function () {
            var help = HelpService.pageHelp();
            help.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error", 'Info');
            })
        }
        var helppagepopup;
        HelpService.pageHelp = function () {
            var deferred = $q.defer();
            helppagepopup = ngDialog.open({
                template: 'views/help&tutorials/Enterprisehelp.html',
                className: 'ngdialog-theme-plain ng-dialogueCustom1',
                scope: $scope,
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {

                }]
            });
            return deferred.promise;
        }
        var commonService = {};
        $scope.groupPage = function () {
            var group = commonService.grouping();
            group.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error", 'Info');
            })
        }
        $scope.sortPage = function () {
            var sort = commonService.sorting();
            sort.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error", 'Info');
            })
        }
        var grouppagepopup;
        var sortpagepopup;
        commonService.grouping = function () {
            var deferred = $q.defer();
            grouppagepopup = ngDialog.open({
                template: 'views/groupingsorting/projects/progmeasurecostplusgroup.html',
                className: 'ngdialog-theme-plain ng-dialogueCustom5',
                scope: $scope,
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {

                }]
            });
            return deferred.promise;
        }
        commonService.sorting = function () {
            var deferred = $q.defer();
            sortpagepopup = ngDialog.open({
                template: 'views/groupingsorting/projects/progmeasurecostplussort.html',
                className: 'ngdialog-theme-plain ng-dialogueCustom5',
                scope: $scope,
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {

                }]
            });
            return deferred.promise;
        }
    }]);
