'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Styles service which dyanmically calculates components heights inside content area.
 */
app.service('stylesService', ["styles_1366_1499", "styles_992_1023",
"styles_1024_1199", "styles_1200_1365","styles_1500_1799","styles_1800_1919",
"styles_1920_2055","styles_2056_2560",
function (styles_1366_1499, styles_992_1023, styles_1024_1199,
    styles_1200_1365, styles_1500_1799,styles_1800_1919, 
    styles_1920_2055,styles_2056_2560) {

    var me = this;
    me.finishedStyling = false;

    me.setPotStyles = function (contentHeight, windowWidth, windowHeight) {
        me.contentHeight = contentHeight;
        me.contentHeaderArea = contentHeight * 0.04;
        me.contentArea = contentHeight * 0.94;
        me.block = me.contentArea * 0.98;

        if (windowWidth >= 992 && windowWidth <= 1023) {
            styles_992_1023.setStyles(me);
        } else if (windowWidth >= 1024 && windowWidth <= 1199) {
           styles_1024_1199.setStyles(me);
        } else if (windowWidth >= 1200 && windowWidth <= 1365) {
            styles_1200_1365.setStyles(me);
        } else if (windowWidth >= 1366 && windowWidth <= 1499) {
            styles_1366_1499.setStyles(me);
        } else if (windowWidth >= 1500 && windowWidth <= 1799) {
            styles_1500_1799.setStyles(me);
        } else if (windowWidth >= 1800 && windowWidth <= 1919) {
            styles_1800_1919.setStyles(me);
        } else if (windowWidth >= 1920 && windowWidth <= 2055) {
            styles_1920_2055.setStyles(me);
        } else if (windowWidth >= 2056 && windowWidth <= 2560) {
            styles_2056_2560.setStyles(me);
        } else {
            styles_1366_1499.setStyles(me);
        }

        addStylesToDocument();

        if (!me.finishedStyling) {
            me.finishedStyling = true;
        }
    }

    function addStylesToDocument() {
        $("head").append("<style> "
            + ".block {height: " + me.block + "px } "
            + ".twinBlock {height: " + me.twinBlock + "px } "
            + ".topBlock {height: " + me.topBlock + "px } "
            + ".bottomBlock {height: " + me.bottomBlock + "px } "
            + ".topPanelBlock {height: " + me.topPanelBlock + "px } "
            + ".bottomPanelBlock {height: " + me.bottomPanelBlock + "px } "
            + ".tabContentHeight {height: " + me.tabContentHeight + "px } "
            + ".content {height:" + me.contentArea + "px} "
            + ".content-header {height: " + me.contentHeaderArea + "px} "
            + ".topTableResHeight { height:" + me.topTableResHeight + "px} "
            + ".tabR1Table_1_Res_height { height:" + me.tabR1Table_1_Res_height + "px} "
            + ".tabR1Table_2_Res_height { height:" + me.tabR1Table_2_Res_height + "px} "
            + ".topFooterTableResHeight { height:" + me.topFooterTableResHeight + "px} "
            + ".topHeaderFooterTableResHeight { height:" + me.topHeaderFooterTableResHeight + "px} "
            + ".topSrcFooterTableResHeight { height:" + me.topSrcFooterTableResHeight + "px} "
            + ".bottomTabTableResHeight { height:" + me.bottomTabTableResHeight + "px} "
            + ".bottomHeaderFooterTableResHeight { height:" + me.bottomHeaderFooterTableResHeight + "px} "
            + ".bottomFooterTableResHeight { height:" + me.bottomFooterTableResHeight + "px} "
            + ".bottomNestedTabTableResHeight { height:" + me.bottomNestedTabTableResHeight + "px} "
            + ".popUpTableResHeight { height:" + me.popUpTableResHeight + "px} "
            + ".popUpTableSmallResHeight { height:" + me.popUpTableSmallResHeight + "px} "
            + ".singleTableResHeight { height:" + me.singleTableResHeight + "px} "
            + ".nestedTabTable_0_Res_Height { height:" + me.nestedTabTable_0_Res_Height + "px} "
            + ".nestedTabTable_1_Res_Height { height:" + me.nestedTabTable_1_Res_Height + "px} "
            + ".nestedTabTable_2_Res_Height { height:" + me.nestedTabTable_2_Res_Height + "px} "
            + ".singleTableResHeight_2 { height:" + me.singleTableResHeight_2 + "px} "
            + ".firstTableResHeight { height:" + me.firstTableResHeight + "px} "
            + ".firstTableResHeight_1 { height:" + me.firstTableResHeight_1 + "px} "
            + ".tabTable_1_Res_height { height:" + me.tabTable_1_Res_height + "px} "
            + ".tabTable_1_Res_height_15 { height:" + me.tabTable_1_Res_height_15 + "px} "
            + ".tabTable_2_Res_height { height:" + me.tabTable_2_Res_height + "px} "
            + ".tabTable_2_10_Res_height { height:" + me.tabTable_2_10_Res_height + "px} "
            + ".tabTable_3_Res_height { height:" + me.tabTable_3_Res_height + "px} "
            + ".tabTable_4_Res_Height { height:" + me.tabTable_4_Res_Height + "px} "
            + ".tabTable_5_Res_Height { height:" + me.tabTable_5_Res_Height + "px} "
            + ".tabTable_2_heightTable { height:" + me.tabTable_2_heightTable + "px} "
            + ".singleTableResHeight_3 { height:" + me.singleTableResHeight_3 + "px}"
            + ".singleTableResHeight_4 { height:" + me.singleTableResHeight_4 + "px}"
            + ".TripleHeaderTableResHeight{ height:" + me.TripleHeaderTableResHeight + "px}"
            + ".singleTableResHeight_2{ height:" + me.singleTableResHeight_2 + "px}"
            + ".fullTwinBlockResHeight { height:" + me.fullTwinBlockResHeight + "px}"
            + ".doubleHeaderTableResHeight { height:" + me.doubleHeaderTableResHeight + "px}"
            + ".noTableInBottomBlock { height:" + me.noTableInBottomBlock + "px}"
            + ".noTableInBottomBlockWithTabs { height:" + me.noTableInBottomBlockWithTabs + "px}"
            + ".bottomTabDoubleHeaderResHeight { height:" + me.bottomTabDoubleHeaderResHeight + "px}"
            + ".bottomHeaderFooterTableResHeight { height:" + me.bottomHeaderFooterTableResHeight + "px}"
            + ".bottomDouleHeaderFooterTableResHeight { height:" + me.bottomDouleHeaderFooterTableResHeight + "px}"
            + ".singleTableResHeight_12  { height:" + me.singleTableResHeight_12 + "px}"
            + ".singleTableResHeight_13  { height:" + me.singleTableResHeight_13 + "px}"
            + ".bottomTabTableResHeight_14  { height:" + me.bottomTabTableResHeight_14 + "px}"
            + ".tabTable_2_Res_height_12  { height:" + me.tabTable_2_Res_height_12 + "px}"
            + ".singleTableResHeight_15  { height:" + me.singleTableResHeight_15 + "px}"
            + ".reportsTableResHeight  { height:" + me.reportsTableResHeight + "px}"
            + ".tableWithChartResHeight  { height:" + me.tableWithChartResHeight + "px}"
            + ".chartResHeight  { height:" + me.chartResHeight + "px}"
            //top 65 botto // 35
                + ".doubleHeaderTableResHeight_topB  { height:" + me.doubleHeaderTableResHeight_topB + "px}"
                + ".tabTable_1_Res_height_topB  { height:" + me.tabTable_1_Res_height_topB + "px}"
                + ".tabTable_3_Res_height_topB  { height:" + me.tabTable_3_Res_height_topB + "px}"
                + ".tabTable_2_Res_height_12_topB { height:" + me.tabTable_2_Res_height_12_topB + "px}"
                + ".tabTable_2_Res_height_topB { height:" + me.tabTable_2_Res_height_topB + "px}"
            + ".schedulesChart { height: " + me.schedulesChart + "px}"
            + "</style>");
        $("head").append("<style> "
            + ".tabTable_2_height_12 { max-height:" + me.tabTable_2_height_12 + "px} "
            + ".topTableHeight { max-height:" + me.topTableHeight + "px} "
            + ".topFooterTableHeight { max-height:" + me.topFooterTableHeight + "px} "
            + ".topHeaderFooterTableHeight { max-height:" + me.topHeaderFooterTableHeight + "px} "
            + ".topSrcFooterTableHeight { max-height:" + me.topSrcFooterTableHeight + "px} "
            + ".tabR1Table_1_height { max-height:" + me.tabR1Table_1_height + "px} "
            + ".tabR1Table_2_height { max-height:" + me.tabR1Table_2_height + "px} "
            + ".bottomTabTableHeight { max-height:" + me.bottomTabTableHeight + "px} "
            + ".bottomFooterTableHeight { max-height:" + me.bottomFooterTableHeight + "px} "
            + ".bottomHeaderFooterTableHeight { max-height:" + me.bottomHeaderFooterTableHeight + "px} "
            + ".bottomNestedTabTableHeight { max-height:" + me.bottomNestedTabTableHeight + "px} "
            + ".singleTableHeight { max-height:" + me.singleTableHeight + "px} "
            + ".singleTableHeight_2 { max-height:" + me.singleTableHeight_2 + "px} "
            + ".nestedTabTable_0_Height { max-height:" + me.nestedTabTable_0_Height + "px} "
            + ".nestedTabTable_1_Height { max-height:" + me.nestedTabTable_1_Height + "px} "
            + ".nestedTabTable_2_Height { max-height:" + me.nestedTabTable_2_Height + "px} "
            + ".firstTableHeight { max-height:" + me.firstTableHeight + "px} "
            + ".firstTableHeight_1 { max-height:" + me.firstTableHeight_1 + "px} "
            + ".tabTable_1_height { max-height:" + me.tabTable_1_height + "px} "
            + ".tabTable_1_height_15 { max-height:" + me.tabTable_1_height_15 + "px} "
            + ".tabTable_2_height { max-height:" + me.tabTable_2_height + "px} "
            + ".tabTable_2_10_height { max-height:" + me.tabTable_2_10_height + "px} "
            + ".tabTable_3_height { max-height:" + me.tabTable_3_height + "px} "
            + ".tabTable_4_Height { max-height:" + me.tabTable_4_Height + "px} "
            + ".tabTable_5_Height { max-height:" + me.tabTable_5_Height + "px} "
            + ".singleTableHeight_3 { max-height:" + me.singleTableHeight_3 + "px} "
            + ".singleTableHeight_4 { max-height:" + me.singleTableHeight_4 + "px} "
            + "#DocHeight { max-height:" + (me.singleTableHeight - 110) + "px}"
            + ".popUpTableHeight { max-height:" + me.popUpTableHeight + "px} "
            + ".popUpTableSmallHeight { max-height:" + me.popUpTableSmallHeight + "px} "
            + ".singleTableHeight_2 { max-height:" + me.singleTableHeight_2 + "px} "
            + ".fullTwinBlockHeight { max-height:" + me.fullTwinBlockHeight + "px}"
            + ".doubleHeaderTableHeight { max-height:" + me.doubleHeaderTableHeight + "px}"
            + ".bottomTabDoubleHeaderHeight { max-height:" + me.bottomTabDoubleHeaderHeight + "px}"
            + ".tabTable_2_height { max-height:" + me.tabTable_2_height + "px}"
            + ".bottomDouleHeaderFooterTableHeight { max-height:" + me.bottomDouleHeaderFooterTableHeight + "px}"
            + ".singleTableHeight_12 { max-height:" + me.singleTableHeight_12 + "px}"
            + ".singleTableHeight_13 { max-height:" + me.singleTableHeight_13 + "px}"
            + ".bottomTabTableHeight_14 { max-height:" + me.bottomTabTableHeight_14 + "px}"
            + ".singleTableHeight_15 { max-height:" + me.singleTableHeight_15 + "px}"
            + ".reportsTableHeight { max-height:" + me.reportsTableHeight + "px}"
            + ".tableWithChartHeight { max-height:" + me.tableWithChartHeight + "px}"
            //top 65 botto // 35
                + ".doubleHeaderTableHeight_topB  { max-height:" + me.doubleHeaderTableHeight_topB + "px}"
                + ".tabTable_1_height_topB  { max-height:" + me.tabTable_1_height_topB + "px}"
                + ".tabTable_3_height_topB  { max-height:" + me.tabTable_3_height_topB + "px}"
                + ".tabTable_2_height_12_topB  {max-height:" + me.tabTable_2_height_12_topB + "px}"
                + ".tabTable_2_height_topB  { max-height:" + me.tabTable_2_height_topB + "px}"
            + "</style>");
    }

}]);