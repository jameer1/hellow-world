'use strict';
app.factory('AllClaimsSelectFactory', ["ngDialog", "$state", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "blockUI",
    function (ngDialog, $state, $q, $filter, $timeout, $rootScope, GenericAlertService, blockUI) {
        var generatePopUp;
        var service = {};
        service.getClaimDetails = function () {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/receivables/createnewclaimpopup.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom6',
                closeByDocument: false,
                showClose: false,
                controller: ['$scope', function ($scope) {
                    $scope.claimList = [{
                        id: 1,
                        name: "Progress Claim - Based on Schedule of Rates"
                    }, {
                        id: 2,
                        name: "Progress Claim - Based on Cost Plus %"
                    }, {
                        id: 3,
                        name: "Progress Claim - Based on Payment Milestones"
                    }, {
                        id: 4,
                        name: "Progress Claim - Materials / Store Items Sales"
                    }, {
                        id: 5,
                        name: "Progress Claim - Plant / Equipments Sales"
                    }];
                    $scope.submitClaim = function (value) {
                        if (value == 1) {
                            $state.go('generateprogressclaimratesbasis');
                        }
                        if (value == 2) {
                            $state.go('generateprogressclaimcostbasis');
                        }
                        if (value == 3) {
                            $state.go('generateprogressclaimmilestone');
                        }
                        if (value == 4) {
                            $state.go('revenuethroughgoodsprod');
                        }
                        if (value == 5) {
                            $state.go('revenuethroughplantequipsales');
                        }
                        $scope.closeThisDialog();
                    }

                }]
            });
            return deferred.promise;
        }
        return service;
    }]);