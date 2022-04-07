'use strict';

app.service('EstimateToCompleteService',
    function () {
        const me = this;

        me.manpower = function (manpowerDetails) {
            for (const manpowerDetail of manpowerDetails) {
                let estimateToComplete = 0;
                if (manpowerDetail.estimateType === 'Remaining Units') {
                    estimateToComplete = manpowerDetail.budgetAtCompletion - manpowerDetail.actualQty;
                } else if (manpowerDetail.estimateType === 'BAC-EV') {
                    estimateToComplete = manpowerDetail.budgetAtCompletion - manpowerDetail.earnedValue;
                } else if (manpowerDetail.estimateType === '(BAC-EV)/PF') {
                    estimateToComplete = (manpowerDetail.budgetAtCompletion - manpowerDetail.earnedValue) / manpowerDetail.productivityFactor;
                } else if (manpowerDetail.estimateType === '(BAC-EV)/(PF*SPI)') {
                    // TODO planned value
                    const SPI = manpowerDetail.earnedValue / manpowerDetail.plannedValue;
                    estimateToComplete = (manpowerDetail.budgetAtCompletion - manpowerDetail.earnedValue) / (manpowerDetail.productivityFactor * SPI);
                } else if (manpowerDetail.estimateType === 'New Estimate') {
                    estimateToComplete = manpowerDetail.estimateComplete;
                }
                if(estimateToComplete != null) {
                	manpowerDetail.estimateComplete = fixedDecimal(estimateToComplete);
                	manpowerDetail.estimateCompletion = fixedDecimal(manpowerDetail.actualQty + manpowerDetail.estimateComplete);
                }
                
                
            }
        };

        function fixedDecimal(value) {
            return isNaN(value) || !isFinite(value) ? 0 : parseFloat(value.toFixed(2));
        }

        me.costStatement = function (projCostStmtDtls) {
            for (const projCostStmtDtl of projCostStmtDtls) {
                if (projCostStmtDtl.item) {
                    calculateValueByKey('labourCost', projCostStmtDtl);
                    calculateValueByKey('materialCost', projCostStmtDtl);
                    calculateValueByKey('plantCost', projCostStmtDtl);
                    calculateValueByKey('otherCost', projCostStmtDtl);
                }
            }
        };

        function calculateValueByKey(key, costStmtDtl) {
            const budgetAtCompletion = costStmtDtl.revisedCostBudget && costStmtDtl.revisedCostBudget[key] && costStmtDtl.revisedCostBudget[key] > 0 ?
                costStmtDtl.revisedCostBudget[key] : costStmtDtl.originalCostBudget[key];
                //console.log("costStmtDtl.estimateType", costStmtDtl.estimateType);
            if (costStmtDtl.estimateType === 'Remaining Units') {
                costStmtDtl.estimateCompleteBudget[key] = budgetAtCompletion - costStmtDtl.actualCostBudget[key];
            } else if (costStmtDtl.estimateType === 'BAC-EV') {
                costStmtDtl.estimateCompleteBudget[key] = budgetAtCompletion - costStmtDtl.earnedValue;
            } else if (costStmtDtl.estimateType === '(BAC-EV)/CPI') {
                const cpi = costStmtDtl.earnedValue / costStmtDtl.actualCostBudget[key];
                costStmtDtl.estimateCompleteBudget[key] = (budgetAtCompletion - costStmtDtl.earnedValue) / cpi;
            } else if (costStmtDtl.estimateType === '(BAC-EV)/(CPI*SPI)') {
                const cpi = costStmtDtl.earnedValue / costStmtDtl.actualCostBudget[key];
                const spi = costStmtDtl.earnedValue / costStmtDtl.plannedValue;
                costStmtDtl.estimateCompleteBudget[key] = (budgetAtCompletion - costStmtDtl.earnedValue) / (cpi * spi);
            } else if (costStmtDtl.estimateType === 'New Estimate') {
                costStmtDtl.estimateCompleteBudget[key] = costStmtDtl.estimateCompleteBudget[key];
            }
        };

        return me;
    });