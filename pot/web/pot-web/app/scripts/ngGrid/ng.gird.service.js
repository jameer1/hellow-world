(function () {
    "use strict";
    app.service('ngGridService', ["$q", "$interval", "uiGridConstants",
        function ($q, $interval, uiGridConstants) {

            var me = this;

            function fakeI18n(title) {
                return $q(function (resolve) {
                    $interval(function () {
                        resolve(' ' + title);
                    }, 1000, 1);
                });
            }
            var setGroupValues = function( columns, rows ) {
                columns.forEach( function( column ) {
                  if ( column.grouping && column.grouping.groupPriority > -1 ){
                    // Put the balance next to all group labels.
                    column.treeAggregationFn = function( aggregation, fieldValue, numValue, row ) {
                      if ( typeof(aggregation.value) === 'undefined') {
                        aggregation.value = 0;
                      }
                      aggregation.value = aggregation.value + row.entity.balance;
                    };
                    column.customTreeAggregationFinalizerFn = function( aggregation ) {
                      if ( typeof(aggregation.groupVal) !== 'undefined') {
                        aggregation.rendered = aggregation.groupVal + ' (' + $filter('currency')(aggregation.value) + ')';
                      } else {
                        aggregation.rendered = null;
                      }
                    };
                  }
                });
                return columns;
              };
            me.initGrid = function (scope, columns, data, filecode) {
                let gridOptions = {};
                gridOptions.exporterMenuCsv = true;
                gridOptions.enableGridMenu = true;
                gridOptions.gridMenuTitleFilter = fakeI18n;
                gridOptions.columnDefs = columns;
                gridOptions.data = data;
                gridOptions.enableSorting = true;
                gridOptions.showFooter = false,
                gridOptions.showGridFooter = true,
                gridOptions.showColumnFooter = true,
                gridOptions.enableColumnResizing = true;
                gridOptions.exporterMenuExcel = true;
                gridOptions.enableGroupHeaderSelection = true,
                gridOptions.exporterMenuPdf = true;
                gridOptions.exporterMenuVisibleData = true;
                gridOptions.exporterMenuAllData = true;
                gridOptions.enableFiltering = false;
                gridOptions.gridMenuCustomItems = [
                    {
                        title: 'Reset Columns',
                        action: function ($event) {
                            if (scope.initialColumnOrder) {
                                gridOptions.columnDefs = angular.copy(scope.initialColumnSet);
                                var columnDefsColMov = scope.gridApi.grid.moveColumns.orderCache;
                                columnDefsColMov.length = 0;
                                columnDefsColMov.push.apply(columnDefsColMov, scope.initialColumnOrder)
                                scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.COLUMN);
                            }
                        },
                        order: 210
                    }
                ];
                gridOptions.onRegisterApi = function (gridApi) {
                    scope.gridApi = gridApi;
                    gridApi.core.on.columnVisibilityChanged(scope, function (changedColumn) {
                        scope.columnChanged = { name: changedColumn.colDef.name, visible: changedColumn.colDef.visible };
                    });
                    gridApi.core.on.rowsRendered(scope, function () {
                        if (!scope.initialColumnOrder) {
                            scope.initialColumnOrder = scope.gridApi.grid.columns.slice();
                            scope.initialColumnSet = angular.copy(columns);
                        }
                    });
                    if (gridApi.selection) {
                     gridApi.selection.on.rowSelectionChanged(scope,function(row, event){
                           if (event)
                        	scope.gridItemSelected(row.entity);
                          });                    
                        gridApi.selection.on.rowSelectionChangedBatch(scope,function(rows, event){
                        	scope.gridItemsSelected(rows);
                        });
                        $scope.gridApi.selection.on.rowSelectionChanged( $scope, function ( rowChanged ) {
                            if ( typeof(rowChanged.treeLevel) !== 'undefined' && rowChanged.treeLevel > -1 ) {
                              // this is a group header
                              children = $scope.gridApi.treeBase.getRowChildren( rowChanged );
                              children.forEach( function ( child ) {
                                if ( rowChanged.isSelected ) {
                                  $scope.gridApi.selection.selectRow( child.entity );
                                } else {
                                  $scope.gridApi.selection.unSelectRow( child.entity );
                                }
                              });
                            }
                          });
                          $scope.gridApi.grouping.on.aggregationChanged($scope, function(col){
                            if ( col.treeAggregation.type ){
                              $scope.lastChange = col.displayName + ' aggregated using ' + col.treeAggregation.type;
                            } else {
                              $scope.lastChange = 'Aggregation removed from ' + col.displayName;
                            }
                          });
                    
                          $scope.gridApi.grouping.on.groupingChanged($scope, function(col){
                            if ( col.grouping.groupPriority ){
                              $scope.lastChange = col.displayName + ' grouped with priority ' + col.grouping.groupPriority;
                            } else {
                              $scope.lastChange = col.displayName + ' removed from grouped columns';
                            }
                          });
                         
                    }
                }
                setExportDataOptions(gridOptions,filecode);
                return gridOptions;
            }

            function setExportDataOptions(gridOptions,filecode) {
                var nowDate = new Date(); 
                var date = (nowDate.getDate()+'/'+(nowDate.getMonth()+1)+'/'+nowDate.getFullYear());
                gridOptions.exporterCsvFilename = filecode+ '/'+date+'.csv';
                gridOptions.exporterPdfDefaultStyle = { fontSize: 8 };
                gridOptions.exporterPdfTableStyle = {margin: [20, 20, 20, 20], alignment: 'left'};
                gridOptions.exporterPdfTableHeaderStyle = { fontSize: 10, bold: true, italics: true, color: 'black', alignment: 'center', fillColor: '#D3D3D3'};
                gridOptions.exporterPdfFooter = function (currentPage, pageCount) {
                    return {margin: [30], text:   ' Page ' + '  ' + currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
                };
                gridOptions.exporterPdfCustomFormatter = function (docDefinition) {
                    docDefinition.styles.headerStyle = { fontSize: 10, bold: true };
                    docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
                    return docDefinition;
                },
                gridOptions.exporterPdfOrientation = 'landscape';
                gridOptions.exporterPdfPageSize = 'A4';
                gridOptions.exporterPdfMaxGridWidth = 600;
                gridOptions.exporterCsvLinkElement = angular.element(document.querySelectorAll(".custom-csv-link-location"));
                gridOptions.exporterExcelFilename = filecode+ '/'+date+'.xlsx';
                gridOptions.exporterExcelSheetName = 'Pot_Sheet';
            }

    }]);
  }) ();