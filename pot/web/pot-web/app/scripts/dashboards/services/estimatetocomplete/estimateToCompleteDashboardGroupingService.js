
app.factory('EstimateToCompleteDashboardGroupingService', ["ProjectBudgetService", "ProjectStatusService", "$q",
    "EstimateToCompleteService", "GenericAlertService",
    function (ProjectBudgetService, ProjectStatusService, $q, EstimateToCompleteService, GenericAlertService) {

        function generateEstimateToCompleteDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred) {
        	console.log("costStatements",costStatements);
        	console.log("projManpowerTOs",projManpowerTOs);
        	console.log("projGenerals",projGenerals);
        	console.log("count",count);
        	console.log("dashboardType",dashboardType);
        	console.log("deferred",deferred);
            if (count === 2) {
                // Group by project
                const projEstimateDetails = new Object();
                for (const projGen of projGenerals) {
                    if (!projEstimateDetails[projGen.projId]) {
                        projEstimateDetails[projGen.projId] = {
                            'projGen': projGen,
                            'estimateToComplete': 0,
                            'estimateCompletion': 0
                        }
                    }
                    if (dashboardType === "projectManpower") {
                        const items = projManpowerTOs.filter(obj => obj.projEmpCategory === 'DIRECT' && projGen.projId === obj.projId);
                        EstimateToCompleteService.manpower(items);
                        items.map(o => {
                            projEstimateDetails[projGen.projId].estimateToComplete += o.estimateComplete;
                            projEstimateDetails[projGen.projId].estimateCompletion += o.estimateCompletion;
                        });
                    } else {
                        const items = new Array();
                        extractChildsRecursively(costStatements.filter(o => o.projId === projGen.projId), items);
                        EstimateToCompleteService.costStatement(items);
                        items.map(item => {
                            projEstimateDetails[projGen.projId].estimateToComplete +=
                                (item.estimateCompleteBudget.labourCost +
                                    item.estimateCompleteBudget.materialCost +
                                    item.estimateCompleteBudget.plantCost +
                                    item.estimateCompleteBudget.otherCost);

                            projEstimateDetails[projGen.projId].estimateCompletion +=
                                (item.actualCostBudget.labourCost +
                                    item.actualCostBudget.materialCost +
                                    item.actualCostBudget.plantCost +
                                    item.actualCostBudget.otherCost) + projEstimateDetails[projGen.projId].estimateToComplete;
                        });
                    }

                }
                let groupPropertyName;
                let codePropertyName;
                switch (dashboardType) {
                    case "country":
                        groupPropertyName = "geonameId";
                        codePropertyName = "countryName";
                        break;
                    case "province":
                        groupPropertyName = "provisionName";
                        codePropertyName = "provisionName";
                        break;
                    case "projectManpower":
                    case "projectCost":
                        groupPropertyName = "projId";
                        codePropertyName = "projId";
                        break;
                    case "projectManager":
                        groupPropertyName = "userLabelKeyTO.id";
                        codePropertyName = "userLabelKeyTO.name";
                        break;
                }
                const groupedObject = new Object();
                for (const key in projEstimateDetails) {
                    let objKey;
                    projEstimateDetails[key].projGen[groupPropertyName]
                    if (codePropertyName.includes(".")) {
                        const propArray = groupPropertyName.split(".");
                        objKey = projEstimateDetails[key].projGen[propArray[0]][propArray[1]];
                    } else {
                        objKey = projEstimateDetails[key].projGen[groupPropertyName];
                    }
                    if (!groupedObject[objKey]) {
                        if (codePropertyName.includes(".")) {
                            const propArray = codePropertyName.split(".");
                            groupedObject[objKey] = {
                                'code': projEstimateDetails[key].projGen[propArray[0]][propArray[1]],
                                'estimateToComplete': 0,
                                'estimateCompletion': 0
                            };
                        } else {
                            groupedObject[objKey] = {
                                'code': projEstimateDetails[key].projGen[codePropertyName],
                                'estimateToComplete': 0,
                                'estimateCompletion': 0
                            };
                        }
                    }
                    groupedObject[objKey].estimateToComplete += projEstimateDetails[key].estimateToComplete;
                    groupedObject[objKey].estimateCompletion += projEstimateDetails[key].estimateCompletion;
                }
                deferred.resolve(groupedObject);
            }
        }

        function extractChildsRecursively(costStmtArr, costItemsArray) {
            for (const costStmt of costStmtArr) {
                if (costStmt.item && !costStmt.projCostStmtDtlTOs.length) {
                    costItemsArray.push(costStmt);
                } else {
                    for (const child of costStmt.projCostStmtDtlTOs) {
                        extractChildsRecursively([child], costItemsArray);
                    }
                }
            }

        }

        return {
            getEstimateToCompleteDashboards: function (projIds, dashboardType) {
                var deferred = $q.defer();
                let count = 0;
                let costStatements;
                let projGenerals;
                let projManpowerTOs;
                var getCostStatReq = {
                    "status": 1,
                    "projIds": projIds
                };
                if(getCostStatReq.projIds == null || getCostStatReq.projIds == undefined) {
                    GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
                    return;
                }
                if (dashboardType === "projectManpower") {
                    ProjectBudgetService.getProjManpowers(getCostStatReq).then(function (data) {
                        projManpowerTOs = data.projManpowerTOs;
                        count++;
                        generateEstimateToCompleteDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred);
                    });
                } else {
                    ProjectBudgetService.getMultiProjCostStatements(getCostStatReq).then(function (data) {
                        costStatements = data.projCostStmtDtlTOs;
                        console.log("costStatements", costStatements);
                        count++;
                        generateEstimateToCompleteDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred);
                    });
                }

                ProjectStatusService.getMultiProjGenerals(getCostStatReq).then(function (data) {
                    projGenerals = data.projGeneralMstrTOs;
                    console.log("projGenerals", projGenerals);
                    count++;
                    generateEstimateToCompleteDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred);
                }, function (error) {
                    GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
                });

                return deferred.promise;

            },
        }
    }]);
