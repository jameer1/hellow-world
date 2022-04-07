'use strict';

app.service('DashBoardThresholdCategoryService', ["ProjectSettingsService", "$q", function (ProjectSettingsService, $q) {

    const me = this;

    me.getThresholdCategories = function (valuesArray, thresholdTypes, projIds) {
        var deferred = $q.defer();

        ProjectSettingsService.getProjPerformenceThreshold({ "status": 1, "projIds": projIds }).then(function (data) {
            let thresholdProps;
            const thresholdResult = new Object();
            for (const prop of thresholdTypes) {
                switch (prop) {
                    case 'cpi':
                        thresholdProps = {
                            'keyProp': 'cpi',
                            'lowerLimitProp': 'cpiLowerLimit',
                            'upperLimitProp': 'cpiUpperLimit'
                        };
                        break;
                    case 'spi':
                        thresholdProps = {
                            'keyProp': 'spi',
                            'lowerLimitProp': 'spiLowerLimit',
                            'upperLimitProp': 'spiUpperLimit'
                        };
                        break;
                    case 'tcpi':
                        thresholdProps = {
                            'keyProp': 'tcpi',
                            'lowerLimitProp': 'tcpiUpperLimit',
                            'upperLimitProp': 'tcpiLowerLimit'
                        };
                    case 'cvp':
                        thresholdProps = {
                            'keyProp': 'cvp',
                            'lowerLimitProp': 'cvLowerLimit',
                            'upperLimitProp': 'cvUpperLimit'
                        };
                    case 'svp':
                        thresholdProps = {
                            'keyProp': 'svp',
                            'lowerLimitProp': 'svLowerLimit',
                            'upperLimitProp': 'svUpperLimit'
                        };
                        break;
                    default:
                        break;
                }
                for (const obj of valuesArray) {
                    if (!thresholdResult[obj.projId])
                        thresholdResult[obj.projId] = new Object();
                    for (const threshold of data.projPerformenceThresholdTOs) {
                        const lowerLimit = threshold[thresholdProps.lowerLimitProp].includes('<') ? Number.NEGATIVE_INFINITY
                            : parseFloat(threshold[thresholdProps.lowerLimitProp].replace(/[>%]/g, ''));
                        const upperLimit = (threshold[thresholdProps.upperLimitProp] == "Infinite") ? Number.POSITIVE_INFINITY
                            : parseFloat(threshold[thresholdProps.upperLimitProp].replace(/[<>%]/g, ''));
                        // Hint: _.inRange(number, [start=0], end)
                        if (_.inRange(obj[thresholdProps.keyProp], lowerLimit, upperLimit) || obj[thresholdProps.keyProp] == lowerLimit
                            || obj[thresholdProps.keyProp] == upperLimit) {
                            thresholdResult[obj.projId][prop] = { 'category': threshold.category, 'class': getCategoryClass(threshold.category) };
                        }
                    }
                }
                // TODO: Remove this below code, this is for mocking
                for (const key in thresholdResult) {
                    if (!thresholdResult[key].tcpi) {
                        console.log('set tcpi mck');
                        thresholdResult[key].tcpi = thresholdResult[key].cpi;
                    }
                }
                // TODO --

            }
            deferred.resolve(thresholdResult);

        }, function (error) {
            GenericAlertService.alertMessage("Error occured while getting Project Reports Details", "Error");
        });

        return deferred.promise;
    }

    function getCategoryClass(category) {
        switch (category) {
            case "Warning":
                return "fa fa-exclamation-triangle";
            case "Critical":
                return "images/critical.png";
            case "Exceptional":
                return "fa fa-star";
            case "Acceptable":
                return "fa fa-square";
        }
    }

    return me;

}]);