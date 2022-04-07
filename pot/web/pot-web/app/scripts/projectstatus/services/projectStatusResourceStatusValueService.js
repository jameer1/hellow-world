'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module Service in the potApp.
 */
app.factory('ProjectStatusResourceStatusValueService', [function () {
    return {
        getResourceStatusValues: function (projCostStatementsSummaryTOs, projStatusReq, resourceTypes) {
            var originalTotal = 0, revisedTotal = 0, actualTotal = 0, remTotal = 0;;

            // Calculate original and revised values
            for (const projCostStatementsSummaryTO of projCostStatementsSummaryTOs) {
                //if total cost, add all values
                if (projStatusReq.resourceType == resourceTypes[6]) {
                    if (projCostStatementsSummaryTO.originalCostBudget && projCostStatementsSummaryTO.originalCostBudget.cost) {
                        originalTotal += projCostStatementsSummaryTO.originalCostBudget.cost;
                    }
                    if (projCostStatementsSummaryTO.revisedCostBudget && projCostStatementsSummaryTO.revisedCostBudget.cost) {
                        revisedTotal += projCostStatementsSummaryTO.revisedCostBudget.cost;
                    } else {
                        revisedTotal += projCostStatementsSummaryTO.originalCostBudget.cost;
                    }
                    if (projCostStatementsSummaryTO.actualCostBudget && projCostStatementsSummaryTO.actualCostBudget.cost) {
                        actualTotal += projCostStatementsSummaryTO.actualCostBudget.cost;
                    }
                }
                else if ((projCostStatementsSummaryTO.catgType == "Labour" && projStatusReq.resourceType == resourceTypes[2]) ||
                    (projCostStatementsSummaryTO.catgType == "Plant" && projStatusReq.resourceType == resourceTypes[3]) ||
                    (projCostStatementsSummaryTO.catgType == "Material" && projStatusReq.resourceType == resourceTypes[4]) ||
                    (projCostStatementsSummaryTO.catgType == "Other" && projStatusReq.resourceType == resourceTypes[5])) {
                    if (projCostStatementsSummaryTO.originalCostBudget && projCostStatementsSummaryTO.originalCostBudget.cost) {
                        originalTotal += projCostStatementsSummaryTO.originalCostBudget.cost;
                    }
                    if (projCostStatementsSummaryTO.revisedCostBudget && projCostStatementsSummaryTO.revisedCostBudget.cost) {
                        revisedTotal += projCostStatementsSummaryTO.revisedCostBudget.cost;
                    } else {
                        revisedTotal += projCostStatementsSummaryTO.originalCostBudget.cost;
                    }
                    if (projCostStatementsSummaryTO.actualCostBudget && projCostStatementsSummaryTO.actualCostBudget.cost) {
                        actualTotal += projCostStatementsSummaryTO.actualCostBudget.cost;
                    }
                }
            }

            remTotal = (revisedTotal ? revisedTotal : originalTotal) - actualTotal;

            const resourceStatus = {
                originalBudget: originalTotal,
                revisedBudget: revisedTotal,
                actual: actualTotal,
                remaining: remTotal
            };

            return resourceStatus;
        }

    }


}]);
