
app.factory('ActualCostDashboardGroupingService', ["ProjectBudgetService", "ProjectStatusService", "$q", "GenericAlertService",
    function (ProjectBudgetService, ProjectStatusService, $q, GenericAlertService) {

        function generateActualCostDashoard(costStatements, projGenerals, count, dashboardType, deferred) {
            if (count === 2) {
                // Group by project
                const projActualCostDetails = new Object();
                for (const projGen of projGenerals) {
                    if (!projActualCostDetails[projGen.projId]) {
                        projActualCostDetails[projGen.projId] = {
                            'projGen': projGen,
                            'actualCost': 0,
                        }
                    }

                    for (const costStmt of costStatements) {
                        if (costStmt.projId === projGen.projId) {
                            projActualCostDetails[projGen.projId].actualCost =
                                addCostRecursively(costStmt, projActualCostDetails[projGen.projId].actualCost);
                        }
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
                    case "project":
                        groupPropertyName = "projId";
                        codePropertyName = "projId";
                        break;
                    case "projectManager":
                        groupPropertyName = "userLabelKeyTO.id";
                        codePropertyName = "userLabelKeyTO.name";
                        break;
                }
                const groupedObject = new Object();
                for (const key in projActualCostDetails) {
                    let objKey;
                    projActualCostDetails[key].projGen[groupPropertyName]
                    if (codePropertyName.includes(".")) {
                        const propArray = groupPropertyName.split(".");
                        objKey = projActualCostDetails[key].projGen[propArray[0]][propArray[1]];
                    } else {
                        objKey = projActualCostDetails[key].projGen[groupPropertyName];
                    }
                    if (!groupedObject[objKey]) {
                        if (codePropertyName.includes(".")) {
                            const propArray = codePropertyName.split(".");
                            groupedObject[objKey] = {
                                'code': projActualCostDetails[key].projGen[propArray[0]][propArray[1]],
                                'actualCost': 0,
                            };
                        } else {
                            groupedObject[objKey] = {
                                'code': projActualCostDetails[key].projGen[codePropertyName],
                                'actualCost': 0,
                            };
                        }
                    }
                    groupedObject[objKey].actualCost += projActualCostDetails[key].actualCost;
                }
                deferred.resolve(groupedObject);
            }
        }

        function addCostRecursively(costStmt, actualCost) {
            if (costStmt.item && !costStmt.projCostStmtDtlTOs.length) {
                actualCost += (costStmt.actualCostBudget.labourCost +
                    costStmt.actualCostBudget.materialCost +
                    costStmt.actualCostBudget.plantCost +
                    costStmt.actualCostBudget.otherCost);
            } else {
                for (const child of costStmt.projCostStmtDtlTOs) {
                    actualCost = addCostRecursively(child, actualCost);
                }
            }
            return actualCost;
        }

        return {
            getActualCostDashboards: function (projIds, dashboardType) {
                var deferred = $q.defer();
                let count = 0;
                let costStatements;
                let projGenerals;
                var getCostStatReq = {
                    "status": 1,
                    "projIds": projIds
                };
                if(getCostStatReq.projIds == null || getCostStatReq.projIds == undefined) {
                    GenericAlertService.alertMessage("Please select EPS/Project", 'Warning');
                    return;
                }
                /*
                console.log("ProjectBudgetService.getActualCostDetails(getCostStatReq).then(function (data)");
                ProjectBudgetService.getActualCostDetails(getCostStatReq).then(function (data) {
                	console.log(data);
                	costStatements = data.projCostStmtDtlTOs;
                    count++;
                    generateActualCostDashoard(costStatements, projGenerals, count, dashboardType, deferred);
                });
                
                */
                ProjectBudgetService.getMultiProjCostStatements(getCostStatReq).then(function (data) {
                    costStatements = data.projCostStmtDtlTOs;
                    count++;
                    generateActualCostDashoard(costStatements, projGenerals, count, dashboardType, deferred);
                });

                ProjectStatusService.getMultiProjGenerals(getCostStatReq).then(function (data) {
                    projGenerals = data.projGeneralMstrTOs;
                    count++;
                    generateActualCostDashoard(costStatements, projGenerals, count, dashboardType, deferred);
                }, function (error) {
                    GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
                });
                
                return deferred.promise;

            },
        }
    }]);
