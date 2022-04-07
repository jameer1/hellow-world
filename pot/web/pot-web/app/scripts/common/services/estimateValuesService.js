'use strict';

app.service('EstimateValuesService', ['ResourceCurveService', 'SchedulePlannedValueService', '$q', 'ProjectScheduleService', 'GenericAlertService',
    function (ResourceCurveService, SchedulePlannedValueService, $q, ProjectScheduleService, GenericAlertService) {

        const me = this;

        me.getCostEstimateValuesBetween = function (projIds, from, to, costEstimateValues) {
            var deferred = $q.defer();
            let costBudgetDetails;
            let resourceCurveTypeMap = new Array();

            ProjectScheduleService.getMultiProjMultiBudgetTypeDetails({
                'budgetTypes': ['COST_CODE'],
                'projIds': projIds
            }).then(function (data) {
                if (costBudgetDetails) {
                    getCostEstimateValuesBetween(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to, costEstimateValues);
                } else {
                    getCostPlannedValuesBetweenForProj(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to, costEstimateValues);
                }
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while getting Cost budget details", "Error");
            });

            // Load resource curves
            ResourceCurveService.getResourceCurves({ "status": 1 }).then(function (data) {
                for (const value of data.projResourceCurveTOs) {
                    resourceCurveTypeMap[value.id] = value;
                }
                getCostEstimateValuesBetween(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to, costEstimateValues);
            }, function (error) {
                GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
            });
            return deferred.promise;
        };
        function getCostEstimateValuesBetween(budgetDetails, resourceCurveTypeMap, projIds, deferred, from, to, costEstimateValues) {
            if (budgetDetails && budgetDetails && resourceCurveTypeMap.length) {
                let propName = "projScheduleCostCodeTOs";
                for (const projId of projIds) {

                    const budgetValue = budgetDetails[projId].costCode;
                    if (!budgetValue[propName] || budgetValue[propName].length <= 0) {
                        continue;
                    }
                    const regularNonWorkingDays = [];
                    if (budgetValue.regularHolidays) {
                        regularNonWorkingDays.push((budgetValue.regularHolidays['sunday']) ? 0 : "");
                        regularNonWorkingDays.push((budgetValue.regularHolidays['monday']) ? 1 : "");
                        regularNonWorkingDays.push((budgetValue.regularHolidays['tuesday']) ? 2 : "");
                        regularNonWorkingDays.push((budgetValue.regularHolidays['wednesday']) ? 3 : "");
                        regularNonWorkingDays.push((budgetValue.regularHolidays['thursday']) ? 4 : "");
                        regularNonWorkingDays.push((budgetValue.regularHolidays['friday']) ? 5 : "");
                        regularNonWorkingDays.push((budgetValue.regularHolidays['saturday']) ? 6 : "");
                    }
                    const fromDate = new Date(from);
                    const toDate = new Date(to);
                    for (const selectedRow of budgetValue[propName]) {
                        const costEstimate = costEstimateValues.find(function (data) {
                            return selectedRow.costCodeId === data.costItemId;
                        });
                        if (!costEstimate || (costEstimate.estimateToComplete && costEstimate.estimateToComplete <= 0))
                            continue;
                        selectedRow.estimateToComplete = costEstimate.estimateToComplete;
                        const startdate = selectedRow.startDate;
                        if (!startdate)
                            continue;
                        const req = {
                            "estimateToComplete": true,
                            "actualAndPlanned": false,
                            "forReport": true,
                            "reportPeriod": [startdate],
                            "selectedItemRow": selectedRow,
                            "selectedTimeScale": {
                                value: 7,
                                type: "Weekly"
                            },
                            "calNonWorkingDays": budgetValue.calNonWorkingDays,
                            "regularNonWorkingDays": regularNonWorkingDays,
                            "searchProject": {
                                "projId": projId
                            },
                            "resourceCurveTypeMap": resourceCurveTypeMap,
                            "reportProjectSetting": budgetValue.projReportsTo,
                            "calSplWorkingDays": budgetValue.calSplWorkingDays
                        };
                        const resp = SchedulePlannedValueService.createPlannedCurve(req);
                        if (resp && resp.labels) {
                            let estimateValue;
                            for (let index = 0; index < resp.labels.length; index++) {
                                const labelDate = new Date(resp.labels[index]);
                                if (labelDate >= fromDate && labelDate <= toDate) {
                                    estimateValue = resp.data[0][index];
                                    if (estimateValue) {
                                        const dateEstim = costEstimate.reportValues.find(function (data) {
                                            return resp.labels[index] === data.date;
                                        });
                                        if (dateEstim) {
                                            costEstimate.periodEstimate += parseFloat(estimateValue);
                                            dateEstim.estimateValue = parseFloat(estimateValue);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return deferred.resolve();
            }
        }
        return me;
    }]);
