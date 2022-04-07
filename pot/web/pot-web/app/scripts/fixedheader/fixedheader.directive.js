'use strict';

/**
 * @ngdoc function
 * @name potApp.directie:FixedHeader
 * @description # Directive which makes tbody in a table scroll.
 * 
 */

app.directive("fixedHeader", ["$rootScope", "stylesService", function ($rootScope, stylesService) {

    function initDataTable(element, height) {
        let classList = $(element).attr('class').split(/\s+/);
        let isDataTable = false;
        $.each(classList, function (index, item) {
            if (item.toLowerCase().indexOf('dataTable'.toLowerCase()) > -1) {
                isDataTable = true;
                return false;
            }
        });

        if (!isDataTable) {
            $(element).DataTable({
                "scrollY": height,
                "scrollCollapse": true,
                "paging": false,
                "searching": false,
                "info": false,
                "ordering": false,
                fixedHeader: false
            });

        }
        showAllData(element);
    }

    function showAllData(tableElement) {
        let trElements = tableElement.find('tr');
        const displayedIds = new Array();
        $.each(trElements, function (index, item) {
            $(item).show();
        });
    }

    return {
        restrict: "A",
        link: function (scope, element, attributes) {
            if ($(element).is("tr")) {
                $(element).hide();
            }
            if ($(element).is("tr") && scope.$last) {
                element.ready(function () {
                    var classList = $(element).parent().parent().attr('class').split(/\s+/);
                    $.each(classList, function (index, item) {
                        if (item.toLowerCase().indexOf('height') > -1) {
                            if (stylesService.finishedStyling) {
                                initDataTable($(element).parent().parent(), stylesService[item]);
                            } else {
                                scope.$watch(function () { return stylesService.finishedStyling; },
                                    function (newValue, oldValue) {
                                        if (newValue)
                                            initDataTable($(element).parent().parent(), stylesService[item]);
                                    });
                            }
                        }
                    });
                });
            }
        }
    }

}]);