'use strict';

app.service('PlannedValuesService', ['ResourceCurveService', 'SchedulePlannedValueService', '$q', 'ProjectScheduleService', 'GenericAlertService',
    function (ResourceCurveService, SchedulePlannedValueService, $q, ProjectScheduleService, GenericAlertService) {

        const me = this;

        me.getCostPlannedValuesBetween = function (projIds, from, to, isComplete) {
            var deferred = $q.defer();
            let costBudgetDetails;
            let resourceCurveTypeMap = new Array();
            ProjectScheduleService.getMultiProjMultiBudgetTypeDetails({
                'budgetTypes': ['COST_CODE'],
                'projIds': projIds
            }).then(function (data) {
                costBudgetDetails = data;
                if (isComplete) {
                    getCostPlannedValuesBetween(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to);
                } else {
                    getCostPlannedValuesBetweenForProj(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to);
                }
            }, function (error) {
               // GenericAlertService.alertMessage("Error occured while getting Cost budget details", "Error");
            });

            // Load resource curves
            ResourceCurveService.getResourceCurves({ "status": 1 }).then(function (data) {
                for (const value of data.projResourceCurveTOs) {
                    resourceCurveTypeMap[value.id] = value;
                }
                if (isComplete) {
                    getCostPlannedValuesBetween(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to);
                } else {
                    getCostPlannedValuesBetweenForProj(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, from, to);
                }
            }, function (error) {
                GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
            });

            return deferred.promise;
        }

        me.calculateManpowerPlannedValues = function (projIds) {
            var deferred = $q.defer();
            let manpowerBudgetDetails;
            let resourceCurveTypeMap = new Array();
            ProjectScheduleService.getMultiProjBudgetManPowerDetails(projIds).then(function (data) {
                manpowerBudgetDetails = data;
                getPlannedValues(manpowerBudgetDetails, resourceCurveTypeMap, projIds, deferred, "manPower");
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while getting Manpower budget details", "Error");
            });

            // Load resource curves
            ResourceCurveService.getResourceCurves({ "status": 1 }).then(function (data) {
                for (const value of data.projResourceCurveTOs) {
                    resourceCurveTypeMap[value.id] = value;
                }
                getPlannedValues(manpowerBudgetDetails, resourceCurveTypeMap, projIds, deferred, "manPower");
            }, function (error) {
                GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
            });

            return deferred.promise;
        }

        me.calculateCostPlannedValues = function (projIds) {
            var deferred = $q.defer();
            let costBudgetDetails = null;
            let resourceCurveTypeMap = new Array();
            ProjectScheduleService.getMultiProjMultiBudgetTypeDetails({'budgetTypes': ['COST_CODE'], 'projIds': projIds}).then(function (data) {
            	console.log(data);
                costBudgetDetails = new Object();
                for (const projId in data) {
                    costBudgetDetails[projId] = data[projId].costCode;
                }
                console.log(costBudgetDetails);
                getPlannedValues(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, 'costCode');
            }, function (error) {
               // GenericAlertService.alertMessage("Error occured while getting Cost budget details", "Error");
            });

            // Load resource curves
            ResourceCurveService.getResourceCurves({ "status": 1 }).then(function (data) {
                for (const value of data.projResourceCurveTOs) {
                    resourceCurveTypeMap[value.id] = value;
                }
                getPlannedValues(costBudgetDetails, resourceCurveTypeMap, projIds, deferred, "costCode");
            }, function (error) {
                GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
            });

            return deferred.promise;
        }

        me.calculateManPowerAndCostPlannedValues = function (projIds) {
            var deferred = $q.defer();
            let projBudgetDetails;
            let resourceCurveTypeMap = new Array();
            let count = 0;
            ProjectScheduleService.getMultiProjMultiBudgetTypeDetails({
                'budgetTypes': ['MAN_POWER', 'COST_CODE'],
                'projIds': projIds
            }).then(function (data) {
                projBudgetDetails = data;
                count++;
                getManPowerAndCostPlanned(projBudgetDetails, resourceCurveTypeMap, projIds, deferred, count);
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while getting Manpower budget details", "Error");
            });

            // Load resource curves
            ResourceCurveService.getResourceCurves({ "status": 1 }).then(function (data) {
                count++;
                for (const value of data.projResourceCurveTOs) {
                    resourceCurveTypeMap[value.id] = value;
                }
                getManPowerAndCostPlanned(projBudgetDetails, resourceCurveTypeMap, projIds, deferred, count);
            }, function (error) {
                GenericAlertService.alertMessage('An error occurred while fetching resource curves', "Error");
            });

            return deferred.promise;
        }

        function getManPowerAndCostPlanned(projBudgetDetails, resourceCurveTypeMap, projIds, deferred, count) {
            if (count == 2) {
                const plannedVlues = new Object();
                const manBudgetDetails = new Object();
                const costBudgetDetails = new Object();
                for (const projId of projIds) {
                    manBudgetDetails[projId] = projBudgetDetails[projId].manPower
                    costBudgetDetails[projId] = projBudgetDetails[projId].costCode;
                }
                asyncManPlanned(manBudgetDetails, resourceCurveTypeMap, projIds).then(function (manPlanHrs) {
                    const plannedValues = new Object();
                    for (const key in manPlanHrs) {
                        plannedValues[key] = { 'plannedHrs': manPlanHrs[key] };
                    }
                    asyncCostPlanned(costBudgetDetails, resourceCurveTypeMap, projIds).then(function (costPlanHrs) {
                        for (const key in costPlanHrs) {
                            plannedValues[key].plannedAmount = costPlanHrs[key];
                        };
                        deferred.resolve(plannedValues);
                    });
                });


            }
        }

        function asyncManPlanned(manBudgetDetails, resourceCurveTypeMap, projIds) {
            const deferredMan = $q.defer();
            getPlannedValues(manBudgetDetails, resourceCurveTypeMap, projIds, deferredMan, "manPower");
            return deferredMan.promise;
        }

        function asyncCostPlanned(costBudgetDetails, resourceCurveTypeMap, projIds) {
            const deferredCost = $q.defer();
            getPlannedValues(costBudgetDetails, resourceCurveTypeMap, projIds, deferredCost, "costCode");
            return deferredCost.promise;
        }

        function getCostPlannedValuesBetweenForProj(budgetDetails, resourceCurveTypeMap, projIds, deferred, from, to) {
            if (budgetDetails && budgetDetails && resourceCurveTypeMap.length) {
                let plannedValues = new Array();
                let propName = "projScheduleCostCodeTOs";
                for (const projId of projIds) {
                    let record = new Object();
                    record.projId = projId;
                    record.datePlanned = [];
                    plannedValues.push(record);
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
                        const startdate = selectedRow.startDate;
                        if (!startdate)
                            continue;
                        const req = {
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
                            let plannedValue;
                            for (let index = 0; index < resp.labels.length; index++) {
                                const labelDate = new Date(resp.labels[index]);
                                if (labelDate >= fromDate && labelDate <= toDate) {
                                    plannedValue = resp.data[0][index];
                                    if (plannedValue) {
                                        record.datePlanned.push({
                                            "date": resp.labels[index],
                                            "costId": selectedRow.costCodeId,
                                            "plannedValue": parseFloat(plannedValue)
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
                return deferred.resolve(plannedValues);
            }
        }

        function getCostPlannedValuesBetween(budgetDetails, resourceCurveTypeMap, projIds, deferred, from, to) {
            if (budgetDetails && budgetDetails && resourceCurveTypeMap.length) {
                let plannedValues = new Array();
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
                    let fromDate;
                    if (from)
                        fromDate = new Date(from);
                    const toDate = new Date(to);
                    for (const selectedRow of budgetValue[propName]) {
                        let record = new Object();
                        record.costId = selectedRow.costCodeId;
                        record.projId = projId;
                        record.datePlanned = [];
                        plannedValues.push(record);
                        const startdate = selectedRow.startDate;
                        if (!startdate)
                            continue;
                        const req = {
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
                            let plannedValue;
                            for (let index = 0; index < resp.labels.length; index++) {
                                const labelDate = new Date(resp.labels[index]);
                                if ((!fromDate || labelDate >= fromDate) && labelDate <= toDate) {
                                    plannedValue = resp.data[0][index];
                                    if (plannedValue) {
                                        record.datePlanned.push({
                                            "date": resp.labels[index],
                                            "plannedValue": parseFloat(plannedValue)
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
                return deferred.resolve(plannedValues);
            }
        }

        function getPlannedValues(budgetDetails, resourceCurveTypeMap, projIds, deferred, plannedValueType) {
            if (budgetDetails && budgetDetails && resourceCurveTypeMap.length) {
                const currentDate = new Date();
                const plannedValues = new Array();
                let propName = null;
                let startDateProp = null
                switch (plannedValueType) {
                    case "manPower":
                        propName = "projManpowerTOs";
                        break;
                    case "costCode":
                        propName = "projScheduleCostCodeTOs";
                        startDateProp = "projCostItemTO";
                        break;

                    default:
                        break;
                }
                for (const projId of projIds) {
                    const record = new Object();
                    record.projId = projId;
                    record.plannedValue = 0;
                    const budgetValue = budgetDetails[projId];
                    if (!budgetValue[propName] || budgetValue[propName].length <= 0) {
                        record.plannedValue = 0;
                        continue;
                    }
                    plannedValues.push(record);
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
                    let directEmpArray = null;
                    if (plannedValueType === "manPower") {
                        directEmpArray = budgetValue[propName].filter(o => o.projEmpCategory === 'DIRECT');
                    } else {
                        directEmpArray = budgetValue[propName];
                    }

                    for (const selectedRow of directEmpArray) {
                        const startdate = selectedRow.startDate;
                        if (!startdate)
                            continue;
                        const req = {
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
                            let plannedValue;
                            for (let index = 0; index < resp.labels.length; index++) {
                                if (new Date(resp.labels[index]) <= currentDate) {
                                    plannedValue = resp.data[0][index];
                                    if (plannedValue) {
                                        record.plannedValue += parseFloat(plannedValue);
                                    }
                                }
                            }

                        } else {
                            record.plannedValue = 0;
                        }
                    }


                }

                const plannedValuesMap = new Object();
                plannedValues.map(o => plannedValuesMap[o.projId] = o.plannedValue);
                return deferred.resolve(plannedValuesMap);

            }

        }

        return me;

    }]);