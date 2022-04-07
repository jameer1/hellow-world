
app.factory('EarnedValueDashboardGroupingService', ["ManpowerReportService", "ProjectStatusService", "$q",
    "EstimateToCompleteService", "GenericAlertService",
    function (ManpowerReportService, ProjectStatusService, $q, EstimateToCompleteService, GenericAlertService) {

        function generateEarnedValueDashoard(earnedHrsAndAmount, projGenerals, count, dashboardType, deferred) {
            if (count === 2) {
                // Group by project
                const projEarnedValueDetails = new Object();
                let earnedProp = null;
                if (dashboardType.includes('Manpower')) {
                    earnedProp = 'earnedHrs';
                } else {
                    earnedProp = 'earnedAmount';
                }
                for (const projGen of projGenerals) {
                    if (!projEarnedValueDetails[projGen.projId]) {
                        projEarnedValueDetails[projGen.projId] = {
                            'projGen': projGen,
                            'earnedValue': 0,
                        }
                    }
                    const obj = earnedHrsAndAmount.filter(o => o.projId === projGen.projId)[0];
                    if (obj)
                        projEarnedValueDetails[projGen.projId].earnedValue += obj[earnedProp];
                }
                let groupPropertyName;
                let codePropertyName;
                switch (dashboardType) {
                    case "countryManpower":
                    case "countryCost":
                        groupPropertyName = "geonameId";
                        codePropertyName = "countryName";
                        break;
                    case "provinceManpower":
                    case "provinceCost":
                        groupPropertyName = "provisionName";
                        codePropertyName = "provisionName";
                        break;
                    case "projectManpower":
                    case "projectCost":
                        groupPropertyName = "projId";
                        codePropertyName = "projId";
                        break;
                    case "projectManagerManpower":
                    case "projectManagerCost":
                        groupPropertyName = "userLabelKeyTO.id";
                        codePropertyName = "userLabelKeyTO.name";
                        break;
                }
                const groupedObject = new Object();
                for (const key in projEarnedValueDetails) {
                    let objKey;
                    projEarnedValueDetails[key].projGen[groupPropertyName]
                    if (codePropertyName.includes(".")) {
                        const propArray = groupPropertyName.split(".");
                        objKey = projEarnedValueDetails[key].projGen[propArray[0]][propArray[1]];
                    } else {
                        objKey = projEarnedValueDetails[key].projGen[groupPropertyName];
                    }
                    if (!groupedObject[objKey]) {
                        if (codePropertyName.includes(".")) {
                            const propArray = codePropertyName.split(".");
                            groupedObject[objKey] = {
                                'code': projEarnedValueDetails[key].projGen[propArray[0]][propArray[1]],
                                'earnedValue': 0,
                            };
                        } else {
                            groupedObject[objKey] = {
                                'code': projEarnedValueDetails[key].projGen[codePropertyName],
                                'earnedValue': 0,
                            };
                        }
                    }
                    groupedObject[objKey].earnedValue += projEarnedValueDetails[key].earnedValue;
                }
                deferred.resolve(groupedObject);
            }
        }

        return {
            getEarnedValueDashboards: function (projIds, dashboardType) {
                var deferred = $q.defer();
                let count = 0;
                let earnedHrsAndAmount;
                let projGenerals;
                var getCostStatReq = {
                    "status": 1,
                    "projIds": projIds
                };
                if(getCostStatReq.projIds == null || getCostStatReq.projIds == undefined) {
                    GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
                    return;
                }
                ManpowerReportService.getProjEarnedValues(getCostStatReq).then(function (data) {
                    earnedHrsAndAmount = data;
                    count++;
                    generateEarnedValueDashoard(earnedHrsAndAmount, projGenerals, count, dashboardType, deferred);
                }, function (error) {
                    GenericAlertService.alertMessage("Error occured while gettting labour actual hours", 'Error');
                });

                ProjectStatusService.getMultiProjGenerals(getCostStatReq).then(function (data) {
                    projGenerals = data.projGeneralMstrTOs;
                    count++;
                    generateEarnedValueDashoard(earnedHrsAndAmount, projGenerals, count, dashboardType, deferred);
                }, function (error) {
                    GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
                });

                return deferred.promise;

            },
        }
    }]);
