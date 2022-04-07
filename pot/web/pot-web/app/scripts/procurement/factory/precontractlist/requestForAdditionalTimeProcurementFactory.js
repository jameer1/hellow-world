'use strict';
app.factory('RequestForAdditionalTimeProcurementFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "ApproveUserFactory", "PreContractInternalService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, ApproveUserFactory, PreContractInternalService, GenericAlertService) {
    var dateWisePopUp;
    var service = {};
    service.getAdditionalTimeDetails = function (preContractTOs, userProjMap) {
        var deferred = $q.defer();
        console.log("preContractTOs.projId");
        console.log(preContractTOs.projId);
        console.log("userProjMap[preContractTOs.projId].name)");
        console.log(userProjMap[preContractTOs.projId].name);
        console.log("preContractTOs.id");
        console.log(preContractTOs.id);
        dateWisePopUp = ngDialog.open({
            template: 'views/procurement/pre-contracts/internalApproval/requestforadditionaltimeprocurement.html',
            className: 'ngdialog-theme-plain ng-dialogueCustom0',
            closeByDocument : false,
            showClose : false,
            controller : [ '$scope', function($scope) {
            	$scope.id = preContractTOs.id;
            	$scope.epsName = userProjMap[preContractTOs.projId].code;
                //$scope.projId = preContractTOs[0].projId;
                $scope.projId = preContractTOs.projId;
                //$scope.projName = preContractTOs[0].name;
                $scope.projName = userProjMap[preContractTOs.projId].name;
                //$scope.preContractId = preContractTOs[0].id;
                $scope.preContractId = preContractTOs.code;
                $scope.requisitionId = preContractTOs.preContractReqApprTO.reqCode;
                $scope.addlTimeReqdFor = preContractTOs.contractStageStatus;
                $scope.notificationDate = $filter('date')(new Date(), "dd-MMM-yyyy");
                $scope.requestDate = preContractTOs.preContractReqApprTO.reqDate;
                $scope.approverTo = {
                    "id" : null,
                    "name" : null
                };

                $scope.submitNotifications = function(projId) {
                    
                    var saveReqObj = {
                        "projId": $scope.projId,
                        "toUserId": $scope.approverTo.id,
                        "preContractId" : $scope.id,
                        "preContractText" : $scope.preContractId,
                        "id": $scope.id,
                        "reqComments":$scope.comments,
                    };
                    console.log(saveReqObj);
                    //return;
                    var saveReq = {
                        "procurementNotificationsTOs" : [saveReqObj]
                    }
                    console.log(preContractTOs);
                    console.log(saveReq);
                    PreContractInternalService.saveProcurementNotifications(saveReq).then(function (data) {
                        $scope.closeThisDialog();
                        GenericAlertService.alertMessage('Additional Time Details are Submitted successfully', "Info");
                    }, function(error) {
                        GenericAlertService.alertMessage('Additional Time Details are failed to Submit', "Error");
                    });
                },
                $scope.getModuleUserDetails = function(approverTo) {
                    var getReq = {
                            "projId" :  $scope.projId,
                            "permission" : "PROCURMT_INTRNLSTAGE1APRVL_APPROVE"
                        };
                        var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
        
                        selectedUser.then(function(data) {
                            approverTo.id = data.approverTo.id;
                            approverTo.name = data.approverTo.name;
                        });
                    }
            }]
        });     
        return deferred.promise;
    }
    return service;
}]);