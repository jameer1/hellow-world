app.factory('BudgetDashboardGroupingService', ["ProjectBudgetService", "ProjectStatusService", "$q", "GenericAlertService",
    function (ProjectBudgetService, ProjectStatusService, $q, GenericAlertService) {

        function generateBudgetByCountryDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred) {
            if (count === 2) {
                // Group by project
                const projBudgetDetails = new Object();
                for (const projGen of projGenerals) {
                    if (!projBudgetDetails[projGen.projId]) {
                        projBudgetDetails[projGen.projId] = { 'projGen': projGen, 'budget': 0 }
                    }
                    if (dashboardType === "projectManpower") {
                        for (const obj of projManpowerTOs) {
                        	console.log("projGen.projId", projGen.projId);
                            //if (obj.projEmpCategory === 'DIRECT' && projGen.projId === obj.projId) {
                            if (projGen.projId === obj.projId) {
                                projBudgetDetails[projGen.projId].budget += obj.revisedQty ? obj.revisedQty : obj.originalQty;
                            }
                        }
                    } else {
                        for (const costStmt of costStatements) {
                            if (costStmt.projId === projGen.projId) {
                                projBudgetDetails[projGen.projId].budget =
                                    addBudgetRecursively(costStmt, projBudgetDetails[projGen.projId].budget);
                            }
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
                for (const key in projBudgetDetails) {
                    let objKey;
                    projBudgetDetails[key].projGen[groupPropertyName]
                    if (codePropertyName.includes(".")) {
                        const propArray = groupPropertyName.split(".");
                        objKey = projBudgetDetails[key].projGen[propArray[0]][propArray[1]];
                    } else {
                        objKey = projBudgetDetails[key].projGen[groupPropertyName];
                    }
                    if (!groupedObject[objKey]) {
                        if (codePropertyName.includes(".")) {
                            const propArray = codePropertyName.split(".");
                            groupedObject[objKey] = {
                                'code': projBudgetDetails[key].projGen[propArray[0]][propArray[1]],
                                'budget': 0
                            };
                        } else {
                            groupedObject[objKey] = {
                                'code': projBudgetDetails[key].projGen[codePropertyName],
                                'budget': 0
                            };
                        }
                    }
                    groupedObject[objKey].budget += projBudgetDetails[key].budget;
                }
                deferred.resolve(groupedObject);
            }
        }

        function addBudgetRecursively(costStmt, budget) {
            if (costStmt.item && !costStmt.projCostStmtDtlTOs.length) {
                budget += costStmt.revisedCostBudget.total ? costStmt.revisedCostBudget.total :
                    costStmt.originalCostBudget.total;
            } else {
                for (const child of costStmt.projCostStmtDtlTOs) {
                    budget = addBudgetRecursively(child, budget);
                }
            }
            return budget;
        }

        return {
            getBudgetDashboards: function (projIds, dashboardType) {
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
                    	console.log("data.projManpowerTOs", data.projManpowerTOs);
                        projManpowerTOs = data.projManpowerTOs;
                        count++;
                        generateBudgetByCountryDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred);
                    });
                } else {
                    ProjectBudgetService.getMultiProjCostStatements(getCostStatReq).then(function (data) {
                        costStatements = data.projCostStmtDtlTOs;
                        count++;
                        generateBudgetByCountryDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred);
                    });
                }

                ProjectStatusService.getMultiProjGenerals(getCostStatReq).then(function (data) {
                    projGenerals = data.projGeneralMstrTOs;
                    count++;
                    generateBudgetByCountryDashoard(costStatements, projManpowerTOs, projGenerals, count, dashboardType, deferred);
                }, function (error) {
                    GenericAlertService.alertMessage("Error occured while getting Man Power Details", "Error");
                });

                return deferred.promise;

            },
        }
    }]);
