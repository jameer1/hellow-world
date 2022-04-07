'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Chart service which is used in all modules.
 */
app.service('chartService', function () {

    var me = this;

    let scopeObj = null;

    let colors = ['#0000ff','#ff4900','#800000','#ff3385','#800080','#ffa500','#80ff80'];

    me.getChartMenu = function (scope) {
        scopeObj = scope;
        scopeObj.menuOptions = menuOptions;
        scopeObj.colors = colors;
    };

    const menuOptions = [
        ['Chart Type',
            [
                ['Area',
                    function ($itemScope, scope) {
                        if (scopeObj.chart_type.toLowerCase() === 'line') {
                            lineFilled(true);
                        } else {
                            areaLine('line', true);
                        }
                        clearMenuOptions();
                        areaLineOptions();
                    }
                ],
                ['Bar',
                    function ($itemScope, scope) {
                        barInit(scopeObj.yAxislabels);
                    }
                ],
                ['HorizantalBar',
                    function ($itemScope, scope) {
                        scopeObj.datasetOverride = new Array();
                        scopeObj.chart_type = 'horizontalBar';
                        scopeObj.options = {
                            legend: {
                                display: getChartDataLength() != 1,
                            },
                            scales: {
                                xAxes: [{
                                    stacked: true
                                }],
                                yAxes: [{
                                    stacked: true,
                                }]
                            }
                        };
                        clearMenuOptions(scope);
                        scopeObj.menuOptions.push(stackMenu);
                        scopeObj.menuOptions.push(swapMenu);
                    }
                ],
                ['PolarArea',
                    function ($itemScope, scope) {
                        scopeObj.datasetOverride = new Array();
                        scopeObj.chart_type = 'polarArea';
                        noScales();
                        clearMenuOptions();
                    }
                ],
                ['Doughnut',
                    function ($itemScope, scope) {
                        scopeObj.datasetOverride = new Array();
                        scopeObj.chart_type = 'doughnut';
                        noScales();
                        clearMenuOptions();
                    }
                ],
                ['Line',
                    function ($itemScope, scope) {

                        if (scopeObj.chart_type.toLowerCase() === 'line') {
                            lineFilled(false);
                        } else {
                            areaLine('line', false);
                        }
                        clearMenuOptions();
                        areaLineOptions();
                    }
                ],
                ['Pie',
                    function ($itemScope, scope) {
                        scopeObj.datasetOverride = new Array();
                        scopeObj.chart_type = 'pie';
                        noScales();
                        clearMenuOptions();
                    }
                ],
                ['Radar',
                    function ($itemScope, scope) {
                        scopeObj.datasetOverride = new Array();
                        scopeObj.chart_type = 'radar';
                        scopeObj.options = {
                            legend: {
                                display: getChartDataLength() != 1,
                            }
                        };
                        clearMenuOptions();
                    }
                ]
            ]
        ]
    ]

    function clearMenuOptions() {
        const length = scopeObj.menuOptions.length;
        if (length > 1) {
            scopeObj.menuOptions.splice(1, length - 1);
        }

    }

    function areaLineOptions() {
        scopeObj.options = {
            legend: {
                display: getChartDataLength() != 1,
            },
            scales: {
                xAxes: [{
                    stacked: false
                }],
                yAxes: [{
                    stacked: false,
                }]
            }
        };
        scopeObj.menuOptions.push(chartFill);
    }

    me.defaultBarInit = function (yAxislabels) {
        barInit(yAxislabels);
    };

    me.defaultPieInit = function () {
        scopeObj.datasetOverride = new Array();
        scopeObj.chart_type = 'pie';
        noScales();

    }

    function barInit(yAxislabels) {
        scopeObj.datasetOverride = new Array();
        scopeObj.chart_type = 'bar';

        if (!yAxislabels) {
            scopeObj.options = {
                legend: {
                    display: getChartDataLength() != 1,
                },
                scales: {
                    xAxes: [{
                        stacked: true
                    }],
                    yAxes: [{
                        stacked: true,
                        scaleLabel: {
                            display: true,
                        }
                    }]
                }
            };
        } else {
            scopeObj.options = {
                legend: {
                    display: getChartDataLength() != 1,
                },
                scales: {
                    xAxes: [{
                        stacked: true
                    }],
                    yAxes: [{
                        stacked: true,
                        scaleLabel: {
                            display: true,
                            labelString: yAxislabels,
                            fontSize: "11",
                            fontColor: "#0000ff",
                        }
                    }]
                }
            };
        }

        clearMenuOptions();
        scopeObj.menuOptions.push(stackMenu);
        scopeObj.menuOptions.push(swapMenu);
    }

    const chartFill = ['Fill-Area/Line',
        [
            ['Filled',
                function ($itemScope, scope) {
                    lineFilled(true);
                }
            ],
            ['Unfilled',
                function ($itemScope, scope) {
                    lineFilled(false);
                }
            ],
            ['Auto',
                function ($itemScope, scope) {
                    lineFilled(true);
                }
            ]
        ]
    ];

    const stackMenu = ['Stack-Bars',
        [
            ['Stacked',
                function ($itemScope, scope) {
                    if (scopeObj.chart_type == 'bar' || scopeObj.chart_type == 'horizontalBar') {
                        scopeObj.options.scales.xAxes[0].stacked = true;
                        scopeObj.options.scales.yAxes[0].stacked = true;
                    }
                }
            ],
            ['Unstacked',
                function ($itemScope, scope) {
                    if (scopeObj.chart_type == 'bar' || scopeObj.chart_type == 'horizontalBar') {
                        scopeObj.options.scales.xAxes[0].stacked = false;
                        scopeObj.options.scales.yAxes[0].stacked = false;
                    }
                }
            ],
            ['Auto',
                function ($itemScope, scope) {
                    if (scopeObj.chart_type == 'bar' || scopeObj.chart_type == 'horizontalBar') {
                        scopeObj.options.scales.xAxes[0].stacked = true;
                        scopeObj.options.scales.yAxes[0].stacked = true;
                    }
                }
            ]
        ]
    ];

    const swapMenu = ['Swap Facets-Bars',
        function ($itemScope, scope) {
            if (scopeObj.chart_type == 'bar' ||
                scopeObj.chart_type == 'horizontalBar') {
                if (scopeObj.chart_type == 'horizontalBar') {
                    scopeObj.chart_type = 'bar';
                } else
                    scopeObj.chart_type = 'horizontalBar';
            }
        }
    ];

    function getChartDataLength() {
        if (scopeObj.data.length > 0) {
            if (Array.isArray(scopeObj.data[0])) {
                return scopeObj.data.length;
            } else {
                return 1;
            }
        }
    }

    function lineFilled(value) {
        const dataLength = getChartDataLength();
        if (dataLength == 1) {
            scopeObj.datasetOverride = {
                type: 'line',
                fill: value,
                borderColor: colors[0],
            };
        }
        else {
            for (let i = 0; i < dataLength; i++) {
                scopeObj.datasetOverride[i].fill = value;
            }
        }
    }

    function noScales() {
        scopeObj.options = {
            legend: {
                display: true,
            },
            scales: {
                xAxes: [{
                    display: false,
                }],
                yAxes: [{
                    display: false,
                }]
            }
        };
    }

    function areaLine(type, value) {
        scopeObj.chart_type = type;
        const dataLength = getChartDataLength();
        if (dataLength == 1 && value === false) {
            scopeObj.datasetOverride = {
                type: 'line',
                fill: false,
                borderColor: colors[0],
            };
        }
        else {
            for (let i = 0; i < dataLength; i++) {
                if (scopeObj.datasetOverride[i])
                    scopeObj.datasetOverride[i].fill = value;
                else {
                    scopeObj.datasetOverride.push({
                        type: 'line',
                        fill: value,
                    });
                }
            }
        }
        scopeObj.options.scales.xAxes[0].display = true;
        scopeObj.options.scales.yAxes[0].display = true;

    }


});