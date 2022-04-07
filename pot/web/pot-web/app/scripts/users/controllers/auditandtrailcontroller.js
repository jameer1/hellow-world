'use strict';
/**
 * @ngdoc function
 * @name potApp.controller:ModuleController
 * @description # Module Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("auditAndTrail", {

        url: '/auditAndTrail',
        parent : 'site',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/users/auditAndTrail.html',
                controller: 'AuditAndController'
            }
        }
    })
}]).controller("AuditAndController", ["$scope","uiGridGroupingConstants", "uiGridConstants", "$q", "ngDialog", "$filter", "GenericAlertService",
"EpsProjectMultiSelectFactory", "MaterialMultiSelectFactory", "MaterialRegisterService",
"ProjectSettingsService", "PreContractStoreFactory", "stylesService", "ngGridService",
function ($scope, uiGridGroupingConstants, uiGridConstants, $q, ngDialog, $filter, GenericAlertService, EpsProjectMultiSelectFactory,
    MaterialMultiSelectFactory, MaterialRegisterService,
    ProjectSettingsService, PreContractStoreFactory, stylesService, ngGridService) {

    $scope.stylesSvc = stylesService;
    $scope.selectedProjIds = [];
    $scope.storeIds = [];
    $scope.projStoreIds = [];
    $scope.selectedMaterialIds = [];

    let todayDate = new Date();
    let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
    $scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
    $scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
    $scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
    $scope.$watch('fromDate', function () {
        $scope.checkErr();
    });
    $scope.$watch('toDate', function () {
        $scope.checkErr();
    });
    $scope.checkErr = function () {
        if (new Date($scope.fromDate) > new Date($scope.toDate)) {
            GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
            return;
        }
    };
    let projReportsSettings = null;

    $scope.getUserProjects = function () {
        var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
        userProjectSelection.then(function (data) {
            $scope.searchProject = {};
            $scope.searchProject = data.searchProject;
            $scope.selectedProjIds = data.searchProject.projIds;
            $scope.selectedClientIds = data.searchProject.clientIds;
            ProjectSettingsService.projReportsOnLoad({ "projIds": $scope.selectedProjIds }).then(function (data) {
                projReportsSettings = data.projectReportsTOs;
            }, function (_error) {
                GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
            });
        }, function (_error) {
            GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
        });
    }

    $scope.getMaterialDetails = function () {

        var materialReq = {
            "status": 1
        };
        var materialPopUp = MaterialMultiSelectFactory.getMultiMaterials(materialReq);
        materialPopUp.then(function (data) {
            $scope.materialNameDisplay = data.selectedMaterial.materialName;
            $scope.selectedMaterialIds = data.selectedMaterial.materialIds;
        }, function (_error) {
            GenericAlertService.alertMessage("Error occured while getting Material Details", 'Error');
        })
    }

    $scope.getStoreOrStock = function () {
        if (!$scope.selectedProjIds || $scope.selectedProjIds.length == 0)
            GenericAlertService.alertMessage("Select projects, to fetch store stock details.", 'Warning');
        else {
            PreContractStoreFactory.getStocks($scope.selectedProjIds, true).then(function (data) {
                $scope.storeModelObj = data.storeModelObj;
                $scope.storeIds = data.storeItems;
                $scope.projStoreIds = data.projStoreItems;
            });
        }
    }

    $scope.reset = function () {
        $scope.selectedProjIds = [];
        $scope.storeModelObj = null;
        $scope.storeIds = [];
        $scope.projStoreIds = [];
        $scope.materialNameDisplay = null;
        $scope.selectedMaterialIds = [];
        projReportsSettings = null;
        $scope.searchProject = {};
        $scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
        $scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
        $scope.subReportCode = "";
        $scope.gridOptions.data = [];

    }

    $scope.getInventoryReportDetails = function () {
        if ($scope.selectedProjIds.length <= 0) {
            GenericAlertService.alertMessage("Select projects.", 'Warning');
            return;
        }
        if ($scope.storeIds.length <= 0 && $scope.projStoreIds.length <= 0) {
            GenericAlertService.alertMessage("Select store/stock.", 'Warning');
            return;
        }
        if ($scope.selectedMaterialIds.length <= 0) {
            GenericAlertService.alertMessage("Select materials.", 'Warning');
            return;
        }
        if ($scope.fromDate == null) {
            GenericAlertService.alertMessage("From date cannot be empty.", 'Warning');
            return;
        } if ($scope.toDate == null) {
            GenericAlertService.alertMessage("To date cannot be empty.", 'Warning');
            return;
        }
        if (new Date($scope.fromDate) > new Date($scope.toDate)) {
            GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
            return;
        }

        var req = {
            "status": 1,
            "projList": $scope.selectedProjIds,
            "fromDate": $scope.fromDate,
            "toDate": $scope.toDate,

            "storeIds": $scope.storeIds,
            "projStoreIds": $scope.storeIds,
            "materialIds": $scope.selectedMaterialIds
        };
        MaterialRegisterService.getInventoryReport(req).then(function (data) {
            let ledgerDetails = data.ledgerRes;
            ledgerDetails.map(ledger => {
                ledger.date = moment(ledger.date).format("DD-MMM-YYYY");
            });
            $scope.gridOptions.data = ledgerDetails;
            console.log("this is error", $scope.gridOptions)
            if ($scope.gridOptions.length <= 0) {
                GenericAlertService.alertMessage("Error occured while getting material inventory report", "Error");
            }

        }, function (_data) {
            GenericAlertService.alertMessage("Material-Inventory Report not available for the search criteria", 'Warning');
            
        });

    }

    $scope.$watch(function () { return stylesService.finishedStyling; },
        function (newValue, _oldValue) {
            if (newValue) {
                let columnDefs = [
                    { field: 'date', displayName: "Date", headerTooltip: "Date", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'eps', displayName: "Time", headerTooltip: "Time", visible: false, enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'projName', displayName: "Time Zone", headerTooltip: "Time Zone", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'storeLocation', displayName: "User ID", headerTooltip: "User ID", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'resourceMaterial', displayName: "User ( First Name + Last Name)", headerTooltip: "User ( First Name + Last Name)", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'unitOfMeasure', displayName: "Project ID", headerTooltip: "Project ID", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'openingStock', displayName: "Project Name", headerTooltip: "Project Name", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'supplied', displayName: "Module", headerTooltip: "Module", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'issued', displayName: "Sub Module", headerTooltip: "Sub Module", enableFiltering: false, groupingShowAggregationMenu: false},
                    { field: 'consumption', displayName: "Page", headerTooltip: "Page", enableFiltering: false, groupingShowAggregationMenu: false },
                    { field: 'stockOnTransit', displayName: "Sub Page", visible: false, headerTooltip: "Sub Page", enableFiltering: false},
                    { field: 'externalProjTransfer', displayName: "Operation", visible: false, headerTooltip: "Operation", enableFiltering: false},
                    { field: 'closingStock', displayName: "Field  Name", headerTooltip: "Field  Name", enableFiltering: false, groupingShowAggregationMenu: false},
                    { field: 'closingStock', displayName: "Old Value", headerTooltip: "Old Value", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
                    customTreeAggregationFinalizerFn: function (aggregation) {
                        aggregation.rendered = aggregation.value;
                    } },
                    { field: 'closingStock', displayName: "New Value", headerTooltip: "New Value", enableFiltering: false, groupingShowAggregationMenu: false, aggregationType: uiGridConstants.aggregationTypes.sum, treeAggregationType: uiGridGroupingConstants.aggregation.SUM,
                    customTreeAggregationFinalizerFn: function (aggregation) {
                        aggregation.rendered = aggregation.value;
                    } }
                ]
                let data = [];
                $scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Reports_Materials_MaterialInventoryReport");
            }
        });
        var HelpService = {};
        $scope.helpPage = function () {
            var help = HelpService.pageHelp();
            help.then(function(_data) {

            }, function(_error) {
                GenericAlertService.alertMessage("Error",'Info');
            })
        }
        var helppagepopup;
        HelpService.pageHelp = function () {
            var deferred = $q.defer();
            helppagepopup = ngDialog.open({
                template: 'views/help&tutorials/reportshelp/materialshelp/matinventoryhelp.html',
                className: 'ngdialog-theme-plain ng-dialogueCustom1',
                scope: $scope,
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function (_$scope) {
        
                }]
            });
            return deferred.promise;
        }
}]);