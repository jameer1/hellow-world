'use strict';

app
    .factory(
        'RfqApproveMainPageFactory',
        ["ngDialog", "$q", "PreContractExternalService", "GenericAlertService", function (ngDialog, $q, PreContractExternalService,
            GenericAlertService) {
            var service = {};
            service.approveRfqBidItem = function (preContractObj, apprvStatus) {
                var deferred = $q.defer();
                var popupData = ngDialog
                    .open({
                        template: 'views/procurement/RFQ/rfqApproveMainPagePopup.html',
                        className: 'ngdialog-theme-plain ng-dialogueCustom6',
                        showClose: false,
                        closeByDocument: false,
                        controller: [
                            '$scope',
                            function ($scope) {
                                $scope.preContractObj = preContractObj;
                                $scope.approveRfq = function () {
                                    var approvedCompanyMap = {};
                                    if (validateBid(preContractObj, approvedCompanyMap)) {
                                        var savereq = {
                                            "preContractTO": $scope.preContractObj,
                                            "apprvStatus": apprvStatus,
                                            "approvedCompanyMap": approvedCompanyMap,
                                            "projId": preContractObj.projId
                                        };
                                        PreContractExternalService.saveExternalPreContracts(savereq).then(

                                            function (data) {
                                                  console.log('Viz saveExternalPreContracts savereq');
                                                  console.log(savereq);
                                                  console.log('data');
                                                  console.log(data);

                                                $scope.closeThisDialog();
                                                deferred.resolve();
                                            },
                                            function (error) {
                                                GenericAlertService.alertMessage("Approval(s) is/are Failed To save", "Error");
                                            });
                                    }

                                };

                                function validateBid(preContractData, approvedCompanyMap) {
                                    let atLeastOneSelected = false;

                                    // Manpower validations
                                    if (preContractData.preContractEmpDtlTOs) {
                                        for (const empDtlTO of preContractData.preContractEmpDtlTOs) {
                                            var quantity = 0;
                                            var apprvQuantity = 0;
                                            for (const empCmpTO of empDtlTO.preContractsEmpCmpTOs) {
                                                if (empCmpTO.approveFlag) {
                                                    if (empCmpTO.quantity <= 0) {
                                                        GenericAlertService.alertMessage("Please enter manpower items approval quantity for bid", "Error");
                                                        return false;
                                                    }
                                                    apprvQuantity = empDtlTO.quantity;
                                                    atLeastOneSelected = true;
                                                    quantity += parseInt(empCmpTO.quantity);
                                                    if (approvedCompanyMap == null || approvedCompanyMap[empCmpTO.preContractCmpId] == null) {
                                                        approvedCompanyMap[empCmpTO.preContractCmpId] = empCmpTO.preContractCmpId;
                                                    }
                                                }

                                            }
                                            if (empDtlTO.quantity < quantity) {
                                                GenericAlertService.alertMessage("Manpower Approval quantity cannot be greater than actual quantity", "Error");
                                                return false;
                                            }
                                        }

                                    }
                                    // Plant validations
                                    if (preContractData.preContractPlantDtlTOs) {
                                        for (const plantDtlTO of preContractData.preContractPlantDtlTOs) {
                                            var quantity = 0;
                                            var apprvQuantity = 0;
                                            for (const plantCmpTO of plantDtlTO.preContractPlantCmpTOs) {
                                                if (plantCmpTO.approveFlag) {
                                                    if (plantCmpTO.quantity <= 0) {
                                                        GenericAlertService.alertMessage(
                                                            "Please enter  plant items approval quantity for bid", "Error");
                                                        return false;
                                                    }
                                                    apprvQuantity = plantDtlTO.quantity;
                                                    atLeastOneSelected = true;
                                                    quantity += parseInt(plantCmpTO.quantity);
                                                    if (approvedCompanyMap == null || approvedCompanyMap[plantCmpTO.preContractCmpId] == null) {
                                                        approvedCompanyMap[plantCmpTO.preContractCmpId] = plantCmpTO.preContractCmpId;
                                                    }
                                                }

                                            }

                                            if (plantDtlTO.quantity < quantity) {
                                                GenericAlertService.alertMessage(
                                                    "Plant Approval quantity cannot be greater than actual quantity", "Error");
                                                return false;
                                            }
                                        }
                                    }

                                    // Material validations
                                    if (preContractData.preContractMaterialDtlTOs) {
                                        for (const materialDtlTO of preContractData.preContractMaterialDtlTOs) {
                                            var quantity = 0;
                                            var apprvQuantity = 0;
                                            for (const materialCmpTO of materialDtlTO.preContractMaterialCmpTOs) {
                                                if (materialCmpTO.approveFlag) {
                                                    if (materialCmpTO.quantity <= 0) {
                                                        GenericAlertService.alertMessage(
                                                            "Please enter material items approval quantity for bid", "Error");
                                                        return false;
                                                    }
                                                    apprvQuantity = materialDtlTO.quantity;
                                                    atLeastOneSelected = true;
                                                    quantity += parseInt(materialCmpTO.quantity);
                                                    if (approvedCompanyMap == null || approvedCompanyMap[materialCmpTO.preContractCmpId] == null) {
                                                        approvedCompanyMap[materialCmpTO.preContractCmpId] = materialCmpTO.preContractCmpId;
                                                    }
                                                }

                                            }

                                            if (materialDtlTO.quantity < quantity) {
                                                GenericAlertService.alertMessage(
                                                    "Material Approval quantity cannot be greater than actual quantity", "Error");
                                                return false;
                                            }
                                        }

                                    }

                                    // Service validations
                                    if (preContractData.preContractServiceDtlTOs) {
                                        for (const serviceDtlTO of preContractData.preContractServiceDtlTOs) {
                                            var quantity = 0;
                                            var apprvQuantity = 0;
                                            for (const serviceCmpTO of serviceDtlTO.preContractServiceCmpTOs) {
                                                if (serviceCmpTO.approveFlag) {
                                                    if (serviceCmpTO.quantity <= 0) {
                                                        GenericAlertService.alertMessage(
                                                            "Please enter  service items approval quantity for bid", "Error");
                                                        return false;
                                                    }
                                                    apprvQuantity = serviceDtlTO.quantity;
                                                    atLeastOneSelected = true;
                                                    quantity += parseInt(serviceCmpTO.quantity);
                                                    if (approvedCompanyMap == null || approvedCompanyMap[serviceCmpTO.preContractCmpId] == null) {
                                                        approvedCompanyMap[serviceCmpTO.preContractCmpId] = serviceCmpTO.preContractCmpId;
                                                    }
                                                }

                                            }

                                            if (serviceDtlTO.quantity < quantity) {
                                                GenericAlertService.alertMessage(
                                                    "Service Approval quantity cannot be greater than actual quantity", "Error");
                                                return false;
                                            }
                                        }

                                    }

                                    // SOW validations
                                    if (preContractData.precontractSowDtlTOs) {
                                        for (const sowDtlTO of preContractData.precontractSowDtlTOs) {
                                            var quantity = 0;
                                            var apprvQuantity = 0;
                                            for (const sowCmpTO of sowDtlTO.precontractSowCmpTOs) {
                                                if (sowCmpTO.approveFlag) {
                                                    if (sowCmpTO.quantity <= 0) {
                                                        GenericAlertService.alertMessage(
                                                            "Please enter  scope of work items approval quantity for bid", "Error");
                                                        return false;
                                                    }
                                                    apprvQuantity = sowCmpTO.quantity;
                                                    quantity += parseInt(sowCmpTO.quantity);
                                                    atLeastOneSelected = true;
                                                    if (approvedCompanyMap == null || approvedCompanyMap[sowCmpTO.preContractCmpId] == null) {
                                                        approvedCompanyMap[sowCmpTO.preContractCmpId] = sowCmpTO.preContractCmpId;
                                                    }
                                                }

                                            }

                                            if (sowDtlTO.quantity < quantity) {
                                                GenericAlertService.alertMessage(
                                                    "Scope of work Approval quantity cannot be greater than actual quantity", "Warning");
                                                return false;
                                            }
                                        }

                                    }
                                    if (!atLeastOneSelected) {
                                        GenericAlertService.alertMessage(
                                            "Please select atleast one schedule item", "Warning");
                                        return false;
                                    }
                                    return true;
                                }


                            }]

                    });
                return deferred.promise;
            }
            return service;
        }]);
