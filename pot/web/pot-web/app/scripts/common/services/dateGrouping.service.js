'use strict';

app.service('dateGroupingService', function () {

    const me = this;

    me.groupByDate = function (dataForGrouping) {
        return performGrouping(dataForGrouping, 'date');
    };

    me.groupByWeek = function (dataForGrouping, weekStartDay) {
        const weekGrouping = function (obj) {
            return moment(obj.date).startOf('isoWeek').isoWeekday(weekStartDay);
        };
        return performGrouping(dataForGrouping, weekGrouping);
    };

    me.groupByMonth = function (dataForGrouping, dateOfMonth) {
        const monthGrouping = function (obj) {
            if (dateOfMonth)
                return moment(obj.date).startOf('month').date(dateOfMonth);
            else
                return moment(obj.date).startOf('month');
        };
        return performGrouping(dataForGrouping, monthGrouping);
    };

    me.groupByYear = function (dataForGrouping, monthOfYear) {
        const yearGrouping = function (obj) {
            if (monthOfYear)
                return moment(obj.date).startOf('year').month(monthOfYear);
            else
                return moment(obj.date).startOf('year');
        };
        return performGrouping(dataForGrouping, yearGrouping);
    };

    me.getGroupedDate = function (group) {
        if (isNaN(group.date)) {
            return new Date(group.date);
        } else {
            return new Date(parseInt(group.date));
        }
    }

    function performGrouping(data, groupFunction) {
        return _.chain(data)
            .groupBy(groupFunction)
            .toPairs()
            .map(function (currentItem) {
                return _.zipObject(["date", "values"], currentItem);
            })
            .value();
    }

    return me;

});