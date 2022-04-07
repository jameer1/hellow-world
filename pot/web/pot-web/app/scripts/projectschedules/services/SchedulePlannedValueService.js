'use strict';

app.factory('SchedulePlannedValueService', ["GenericAlertService", "$filter", function (GenericAlertService, $filter) {

    const m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    function changeDayAsPerReportSetting(startDate, reportWeek) {
        var weeks = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        var week = weeks.indexOf(reportWeek);
        var startDateDay = new Date(startDate).getDay();
        if (week != startDateDay) {
            var iteratedWeek;
            var date = new Date(startDate);
            do {
                iteratedWeek = new Date(date.setDate(date.getDate() - 1)).getDay();
            } while (week != iteratedWeek);
            return date;
        } else {
            return startDate;
        }
    };

    function changeDateAsPerReportSetting(startDate, reportMonth) {
        var dateInMonth = reportMonth;
        var selectedSartDate = new Date(startDate).getDate();
        if (dateInMonth !== selectedSartDate) {
            var iteratedDate;
            var date = new Date(startDate);
            do {
                iteratedDate = new Date(date.setDate(date.getDate() - 1)).getDate();
            } while (dateInMonth != iteratedDate);
            return date;
        } else {
            return startDate;
        }
    };

    function changeMonthAsPerReportSetting(startDate, reportYear) {
        var months = new Array("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        var month = months.indexOf(reportYear);
        var selectedSartMonth = new Date(startDate).getMonth();
        if (month != selectedSartMonth) {
            var iteratedMonth;
            var date = new Date(startDate);
            do {
                iteratedMonth = new Date(date.setMonth(date.getMonth() - 1)).getMonth();
            } while (month != iteratedMonth);
            return new Date(date.setDate(1));
        } else {
            return startDate;
        }
    };

    function populateCurveData(totalBudgetHrs, nonWorkingDaysCount, startDate, finishDate, resourceCurveData, regularNonWorkingDays, calSplWorkingDays) {
        var totalScheduleDays = 0;
        var sheduleDays = 0;

        var dayWiseCurveDataList = [];

        var startDateCpy = angular.copy(startDate, startDateCpy);
        var finishDateCpy = angular.copy(finishDate, finishDateCpy);
        //console.log("Regular NOn WOkring Days ", regularNonWorkingDays);
        while (startDateCpy <= finishDateCpy) {
            var regularHoliday = (regularNonWorkingDays.indexOf(startDateCpy.getDay()) != -1) ? true : false;
            for (var i = 0; i < calSplWorkingDays.length; i++) {
                if (new Date(calSplWorkingDays[i]).getTime() == startDateCpy.getTime()) {
                    regularHoliday = false;
                    break;
                }
            }
            var nextDate = startDateCpy.setDate(startDateCpy.getDate() + 1);
            startDateCpy = new Date(nextDate);
            if (!regularHoliday) {
                sheduleDays++;
            }
        }
        totalScheduleDays = sheduleDays - nonWorkingDaysCount;
        //console.log("Non workng days  ", nonWorkingDaysCount);
        //console.log("Total Schedule Days ", totalScheduleDays);
        var perUnitValues = [];

        for (var i = 1; i <= 10; i++) {
            perUnitValues.push(((resourceCurveData["range" + i] / totalScheduleDays) / 100) * totalBudgetHrs);
        }

        //console.log("Per Unit Values ", perUnitValues);

        var breakdownEachDayToTenParts = totalScheduleDays * 10;
        var eachPartInADay = 0;
        var perUnitValueIndex = 0;
        for (var i = 1; i <= breakdownEachDayToTenParts; i++) {
            eachPartInADay += perUnitValues[perUnitValueIndex];
            if (i % 10 == 0) {
                dayWiseCurveDataList.push(eachPartInADay);
                eachPartInADay = 0;
            }

            if (i % totalScheduleDays == 0) {
                perUnitValueIndex += 1;
            }
        }
        //console.log("Day Wise Curve Value ", dayWiseCurveDataList);
        return dayWiseCurveDataList;
    };

    function calculateCumulativeData(data) {
        var count = 0;
        var cummulativeData = [];
        angular.forEach(data, function (value, key) {
            count = count + parseFloat(value);
            cummulativeData.push(count);
        });
        return cummulativeData;
    };

    function calculateDays(nonWorkingDaysCount, nonWorkingDayCountMap, startDate, finishDate, labelsFullDate, selectedTimeScale,
        regularNonWorkingDays, calNonWorkingDays, calSplWorkingDays) {
        var daysCountMap = [];
        var startDateCpy = angular.copy(startDate, startDateCpy);
        var finishDateCpy = angular.copy(finishDate, finishDateCpy);

        for (var i = 0; i < labelsFullDate.length; i++) {
            var labelDateCpy = angular.copy(labelsFullDate[i], labelDateCpy);
            if (selectedTimeScale.value === 7) {
                startDateCpy = addDaysCountMap(labelDateCpy, finishDate, startDateCpy, i, nonWorkingDaysCount, nonWorkingDayCountMap,
                    daysCountMap, 7, labelsFullDate, selectedTimeScale, regularNonWorkingDays, calNonWorkingDays, calSplWorkingDays);
            } else if (selectedTimeScale.value === 30) {
                startDateCpy = addDaysCountMap(labelDateCpy, finishDate, startDateCpy, i, nonWorkingDaysCount, nonWorkingDayCountMap,
                    daysCountMap, new Date(labelsFullDate[i].getFullYear(), labelsFullDate[i].getMonth() + 1, 0, 23, 59, 59).getDate(),
                    labelsFullDate, selectedTimeScale, regularNonWorkingDays, calNonWorkingDays, calSplWorkingDays);
            } else if (selectedTimeScale.value === 365) {
                startDateCpy = addDaysCountMap(labelDateCpy, finishDate, startDateCpy, i, nonWorkingDaysCount, nonWorkingDayCountMap,
                    daysCountMap, isLeapYear(labelsFullDate[i].getFullYear()) ? 366 : 365, labelsFullDate, selectedTimeScale,
                    regularNonWorkingDays, calNonWorkingDays, calSplWorkingDays);
            }
        }
        return daysCountMap;
    };

    function isLeapYear(year) {
        return year % 400 === 0 || (year % 100 !== 0 && year % 4 === 0);
    };

    function checkIfTheDayIsASplWorkingDay(startDateCpy, calSplWorkingDays) {
        var itsAWorkingDay = false;
        for (var j = 0; j < calSplWorkingDays.length; j++) {
            if (new Date(calSplWorkingDays[j]).getTime() == startDateCpy.getTime()) {
                itsAWorkingDay = true;
                break;
            }
        }
        return itsAWorkingDay;
    };

    function addDaysCountMap(labelDateCpy, finishDate, startDateCpy, index, nonWorkingDaysCount, nonWorkingDayCountMap,
        daysCountMap, daysToIncrement, labelsFullDate, selectedTimeScale, regularNonWorkingDays, calNonWorkingDays, calSplWorkingDays) {
        var labelDateCpy = new Date(labelDateCpy.setDate(labelDateCpy.getDate() + daysToIncrement));
        if (labelDateCpy > finishDate) {
            labelDateCpy = finishDate;
            labelDateCpy = new Date(labelDateCpy.setDate(labelDateCpy.getDate() + 1));
        }

        while (startDateCpy >= labelsFullDate[index] && startDateCpy < labelDateCpy) {
            var days = (Math.ceil((labelDateCpy.getTime() - startDateCpy.getTime()) / (1000 * 3600 * 24)));
            if (selectedTimeScale.value === 7) {
                reduceNonWorkingDaysForWeek(nonWorkingDaysCount, startDateCpy, labelDateCpy, daysCountMap, index, days, calNonWorkingDays);
            } else if (selectedTimeScale.value === 30) {
                reduceNonWorkingDaysForMonth(nonWorkingDayCountMap, index, daysCountMap, days, labelsFullDate);
            } else if (selectedTimeScale.value === 365) {
                reduceNOnWorkingDaysForYear(nonWorkingDayCountMap, index, daysCountMap, days, labelsFullDate);
            }
            daysCountMap[index + 1] = daysCountMap[index + 1] - getRegularHolidays(startDateCpy, days, regularNonWorkingDays, calSplWorkingDays);
            var labelDateForStartDate = angular.copy(labelsFullDate[index], labelDateForStartDate);
            startDateCpy = new Date(labelDateForStartDate.setDate(labelDateForStartDate.getDate() + daysToIncrement));
        }
        return startDateCpy;
    };

    function reduceNOnWorkingDaysForYear(nonWorkingDayCountMap, index, daysCountMap, days, labelsFullDate) {
        if (!isNaN(nonWorkingDayCountMap[labelsFullDate[index].getFullYear()])) {
            daysCountMap[index + 1] = (days - nonWorkingDayCountMap[labelsFullDate[index].getFullYear()]);
        } else {
            daysCountMap[index + 1] = days;
        }
    };

    function reduceNonWorkingDaysForMonth(nonWorkingDayCountMap, index, daysCountMap, days, labelsFullDate) {
        var monthNonWorkingDays = nonWorkingDayCountMap[m_names[labelsFullDate[index].getMonth()] + "-" + labelsFullDate[index].getFullYear()];
        if (!isNaN(monthNonWorkingDays)) {
            daysCountMap[index + 1] = days - monthNonWorkingDays;
        } else {
            daysCountMap[index + 1] = days;
        }
    };

    function reduceNonWorkingDaysForWeek(nonWorkingDaysCount, startDateCpy, labelDateCpy, daysCountMap, index, days, calNonWorkingDays) {
        var count = 0;
        if (nonWorkingDaysCount) {
            var dateCpy = angular.copy(startDateCpy, dateCpy);
            for (var i = 0; i <= (days - 1); i++) {
                var dateToCheckRegularHoliday = null;
                if (i == 0) {
                    dateToCheckRegularHoliday = angular.copy(startDateCpy, dateToCheckRegularHoliday);
                } else {
                    dateToCheckRegularHoliday = new Date(dateCpy.setDate(dateCpy.getDate() + 1));
                }
                if (checkIfTheDayIsAHoliday(dateToCheckRegularHoliday, calNonWorkingDays)) {
                    count += 1;
                }
            }
            daysCountMap[index + 1] = days - count;
        } else {
            daysCountMap[index + 1] = days;
        }
    };

    function getRegularHolidays(date, days, regularNonWorkingDays, calSplWorkingDays) {
        var dateCpy = angular.copy(date, dateCpy);
        var regularHolidaysCount = 0;
        for (var i = 0; i <= (days - 1); i++) {
            var dateToCheckRegularHoliday = null;
            if (i == 0) {
                dateToCheckRegularHoliday = angular.copy(date, dateToCheckRegularHoliday);
            } else {
                dateToCheckRegularHoliday = new Date(dateCpy.setDate(dateCpy.getDate() + 1));
            }
            if (regularNonWorkingDays.indexOf(dateToCheckRegularHoliday.getDay()) != -1
                && !checkIfTheDayIsASplWorkingDay(dateToCheckRegularHoliday, calSplWorkingDays)) {
                regularHolidaysCount += 1;
            }
        }
        return regularHolidaysCount;
    };

    function checkIfTheDayIsAHoliday(date, calNonWorkingDays) {
        var isHoliday = false;
        for (var i = 0; i < calNonWorkingDays.length; i++) {
            var endDate = new Date(calNonWorkingDays[i]);
            if (date.getTime() == endDate.getTime()) {
                isHoliday = true;
                break;
            }
        }
        return isHoliday;
    };

    function changeStartDateBasedOnReportsSetting(selectedItemRow, reportProjectSetting, selectedTimeScale) {
        if (reportProjectSetting) {
            var startDate = angular.copy((selectedItemRow.startDateCpy) ? selectedItemRow.startDateCpy : selectedItemRow.startDate, startDate);
            var finishDate = angular.copy(selectedItemRow.finishDate, finishDate);
            selectedItemRow.finishDateCpy = (selectedItemRow.finishDateCpy) ? selectedItemRow.finishDateCpy : finishDate;
            if (selectedTimeScale.value === 7) {
                selectedItemRow.startDateCpy = changeDayAsPerReportSetting(startDate, reportProjectSetting.week);
            } else if (selectedTimeScale.value === 30) {
                selectedItemRow.startDateCpy = changeDateAsPerReportSetting(startDate, reportProjectSetting.month);
            } else if (selectedTimeScale.value === 365) {
                selectedItemRow.startDateCpy = changeMonthAsPerReportSetting(startDate, reportProjectSetting.year);
            }
        }
    };

    return {
        changeStartDateBasedOnReportsSetting: function (selectedItemRow, reportProjectSetting, selectedTimeScale) {
            changeStartDateBasedOnReportsSetting(selectedItemRow, reportProjectSetting, selectedTimeScale);
        },
        createPlannedCurve: function (req) {
            if (!req.searchProject.projId == null) {
                GenericAlertService.alertMessage("Please select the project", "Warning");
                return;
            }
            if (!req.selectedItemRow.resourceCurveId) {
                // GenericAlertService.alertMessage("Please select the item or resource curve", "Warning");
                return;
            }

            if (!req.selectedItemRow.startDate || !req.selectedItemRow.finishDate) {
                GenericAlertService.alertMessage("Selected item doesn't have schedule dates ", "Warning");
                return;
            }

            if (req.forReport) {
                let inRange = false;
                // If period is not in range, no need to process further
                for (const period of req.reportPeriod) {
                    inRange = (moment(period, "DD/MMM/YYYY").isBetween(moment(req.selectedItemRow.startDate, "DD/MMM/YYYY"), moment(req.selectedItemRow.finishDate, "DD/MMM/YYYY"))
                        || moment(period, "DD/MMM/YYYY").isSame(moment(req.selectedItemRow.startDate, "DD/MMM/YYYY"))
                        || moment(period, "DD/MMM/YYYY").isSame(moment(req.selectedItemRow.finishDate, "DD/MMM/YYYY")));
                    if (inRange) {
                        break;
                    }
                }
                if (!inRange)
                    return;
            }

            if (!req.actualAndPlanned) {
                req.selectedItemRow.finishDateCpy = req.selectedItemRow.startDateCpy = null;
                changeStartDateBasedOnReportsSetting(req.selectedItemRow, req.reportProjectSetting, req.selectedTimeScale);
            }
            var totalBudgetHrs = 0;
            var totalPlannedScheduleDays = 0;
            var resourceCurveData = [];
            var labelsFullDate = [];
            var nonWorkingDayCountMap = [];
            if (req.selectedTimeScale.value == 30) {
                angular.forEach(req.calNonWorkingDays, function (value, key) {
                    if (nonWorkingDayCountMap[value.substring(3, 11)]) {
                        nonWorkingDayCountMap[value.substring(3, 11)] = ++count;
                    } else {
                        count = 1;
                        nonWorkingDayCountMap[value.substring(3, 11)] = count;
                    }
                });
            } else if (req.selectedTimeScale.value == 365) {
                angular.forEach(req.calNonWorkingDays, function (value, key) {
                    if (nonWorkingDayCountMap[value.substring(7, 11)]) {
                        nonWorkingDayCountMap[value.substring(7, 11)] = count++;
                    } else {
                        count = 1;
                        nonWorkingDayCountMap[value.substring(7, 11)] = count;
                    }
                });
            }
            var daysCountMap = [];
            let data = [];
            let labels = [];

            var baseStartDate = new Date(req.selectedItemRow.startDateCpy);
            var finishDate = new Date(req.selectedItemRow.finishDateCpy);
            var nonWorkingDaysCount = 0;
            var endDate = null;
            var startDate = new Date(req.selectedItemRow.startDateCpy);
            for (var i = 0; i < req.calNonWorkingDays.length; i++) {
                endDate = new Date(req.calNonWorkingDays[i]);
                if (new Date(req.selectedItemRow.startDate) <= endDate && new Date(req.selectedItemRow.finishDate) >= endDate
                    && (req.regularNonWorkingDays.indexOf(endDate.getDay()) < 0)
                    && !checkIfTheDayIsASplWorkingDay(endDate, req.calSplWorkingDays)) {
                    nonWorkingDaysCount++;
                }
            }

            if (!req.selectedItemRow.actualQty) {
                req.selectedItemRow.actualQty = null;
            }

            if (req.estimateToComplete) {
                totalBudgetHrs = req.selectedItemRow.estimateToComplete;
            } else {
                if (!req.actualAndPlanned && req.selectedItemRow.revisedQty != undefined && req.selectedItemRow.revisedQty != null && req.selectedItemRow.revisedQty > 0) {
                    totalBudgetHrs = req.selectedItemRow.revisedQty;
                } else if (!req.actualAndPlanned) {
                    totalBudgetHrs = req.selectedItemRow.originalQty;
                } else {
                    totalBudgetHrs = (req.selectedItemRow.revisedQty) ? (req.selectedItemRow.revisedQty - req.selectedItemRow.actualQty) : (req.selectedItemRow.originalQty - req.selectedItemRow.actualQty);
                }
            }
            resourceCurveData = req.resourceCurveTypeMap[req.selectedItemRow.resourceCurveId];
            var dayWiseCurveDataList = populateCurveData(totalBudgetHrs, nonWorkingDaysCount, new Date(req.selectedItemRow.startDate),
                new Date(req.selectedItemRow.finishDate), resourceCurveData, req.regularNonWorkingDays, req.calSplWorkingDays);

            var days = 0;
            var year = 0;
            var month = 0;
            var indexValue = 0;
            var count = 0;
            var reduceStartDays = 0;
            var nextDate = null;
            var weekDays = 7;
            startDate = new Date(req.selectedItemRow.startDateCpy);
            baseStartDate = new Date(req.selectedItemRow.startDateCpy);
            finishDate = new Date(req.selectedItemRow.finishDateCpy);
            while (startDate <= finishDate) {
                ++totalPlannedScheduleDays;
                year = startDate.getFullYear();
                month = startDate.getMonth();

                if (req.selectedTimeScale.value == 7) {
                    endDate = null;
                    var startDateCopy = angular.copy(startDate, startDateCopy);
                    nextDate = new Date(startDateCopy.setDate(startDateCopy.getDate() + weekDays));
                    if (nextDate > finishDate) {
                        weekDays = weekDays - ((Math.ceil((nextDate.getTime() - finishDate.getTime()) / (1000 * 3600 * 24))) - 1);
                    }
                    labels.push(startDate.getDate() + "-" + m_names[month] + "-" + year);
                    labelsFullDate.push(startDate);
                } else if (req.selectedTimeScale.value == 30) {
                    var startDateCopy = angular.copy(startDate, startDateCopy);
                    var nonworkingDaysCount = 0;
                    labels.push(m_names[month] + "-" + year);
                    nonworkingDaysCount = nonWorkingDayCountMap[m_names[month] + "-" + year];
                    nextDate = new Date(startDateCopy.setMonth(startDateCopy.getMonth() + 1));
                    labelsFullDate.push(startDate);
                } else if (req.selectedTimeScale.value == 365) {
                    var startDateCopy = angular.copy(startDate, startDateCopy);
                    labels.push(year);
                    labelsFullDate.push(startDate);
                    nextDate = new Date(startDateCopy.setFullYear(startDateCopy.getFullYear() + 1));
                }
                startDate = new Date(nextDate);
            }
            daysCountMap = calculateDays(nonWorkingDaysCount, nonWorkingDayCountMap, new Date(req.selectedItemRow.startDate),
                new Date(req.selectedItemRow.finishDate), labelsFullDate, req.selectedTimeScale, req.regularNonWorkingDays,
                req.calNonWorkingDays, req.calSplWorkingDays);
            var totalSum = 0;
            var count = 0;
            var previousCount = 0;
            var totalDays = 0;
            if (req.forReport) {
                let reportLabel = [];
                let reportData = [];
                let dayWiseCount = 0;
                for (let labelDate of labelsFullDate) {
                    const weekStartDate = angular.copy(labelDate);
                    for (let index = 0; index < 7; index++) {
                        const nextDay = new Date(angular.copy(weekStartDate));
                        nextDay.setDate(weekStartDate.getDate() + index);
                        if (!(moment(nextDay).isBetween(moment(req.selectedItemRow.startDate, "DD/MMM/YYYY"), moment(req.selectedItemRow.finishDate, "DD/MMM/YYYY"))
                            || moment(nextDay).isSame(moment(req.selectedItemRow.startDate, "DD/MMM/YYYY"))
                            || moment(nextDay).isSame(moment(req.selectedItemRow.finishDate, "DD/MMM/YYYY")))
                            || checkIfTheDayIsAHoliday(new Date(nextDay), req.calNonWorkingDays)
                            || (req.regularNonWorkingDays.indexOf(new Date(nextDay).getDay()) != -1))
                            continue;
                        reportLabel.push($filter('date')((nextDay), "dd-MMM-yyyy"));
                        reportData.push(parseFloat(dayWiseCurveDataList[dayWiseCount]).toFixed(2));
                        dayWiseCount++;
                    }
                }
                data = [reportData];
                labels = reportLabel;
            } else {
                for (var j = 0; j < labels.length; j++) {
                    totalDays = daysCountMap[j + 1];
                    for (var i = count; i <= dayWiseCurveDataList.length; i++) {
                        if (i < (totalDays + previousCount)) {
                            if (!isNaN(dayWiseCurveDataList[i])) {
                                totalSum = totalSum + dayWiseCurveDataList[i];
                            }
                            count++;
                        } else {
                            previousCount = count;
                            data.push(parseFloat(totalSum).toFixed(2));
                            totalSum = 0;
                            break;
                        }
                        if (dayWiseCurveDataList.length == count) {
                            previousCount = count;
                            data.push(parseFloat(totalSum).toFixed(2));
                            totalSum = 0;
                            break;
                        }
                    }
                }
                if (req.actualAndPlanned) {
                    data = [data];
                } else {
                    data = [data, calculateCumulativeData(data)];
                }
            }
            return {
                "data": data,
                "labels": labels,
                "labelsFullDate": labelsFullDate
            };
        }

    };
}]);
