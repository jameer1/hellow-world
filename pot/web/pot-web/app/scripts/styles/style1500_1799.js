
/**
 * Resolution settings for 1366 * 1499
 */
app.service('styles_1500_1799', function () {

    var currentObj = this;

    function styles50_50(me) {
        me.twinBlock = me.contentArea * 0.49;
        // One table with footer
        me.singleTableResHeight = me.block * 0.91;
        me.singleTableHeight = me.block * 0.82;

        // One table with fields on top and footer
        me.singleTableResHeight_2 = me.block * 0.79;
        me.singleTableHeight_2 = me.block * 0.71;

        // One table with 2 lines of fields and footer
        me.singleTableResHeight_3 = me.block * 0.70;
        me.singleTableHeight_3 = me.block * 0.63;

        // One table with 2 lines of fields and footer
        me.singleTableResHeight_12 = me.block * 0.80;
        me.singleTableHeight_12 = me.block * 0.70;


        // One table with 2 lines of fields and footer
        me.singleTableResHeight_15 = me.block * 0.78;
        me.singleTableHeight_15 = me.block * 0.73;

        // One table with 2 lines of fields and footer
        me.singleTableResHeight_4 = me.block * 0.26;
        me.singleTableHeight_4 = me.block * 0.22;

        // Table which occupies whole twin block height
        me.fullTwinBlockResHeight = me.twinBlock;
        me.fullTwinBlockHeight = me.twinBlock * 0.85;

        // Two tables with 1 line fields on top and -
        // - footers for each table and tabs as parent for second table.
        me.firstTableResHeight = me.twinBlock * 0.65;
        me.firstTableHeight = me.twinBlock * 0.54;

        //Single table in first panel
        me.firstTableResHeight_1 = me.twinBlock * 0.80;
        me.firstTableHeight_1 = me.twinBlock * 0.75;

        me.tabTable_1_Res_height = me.twinBlock * 0.75;
        me.tabTable_1_height = me.twinBlock * 0.66;


        me.tabTable_1_Res_height_15 = me.twinBlock * 0.70;
        me.tabTable_1_height_15 = me.twinBlock * 0.56;

        //Used in Resources second panel -tab tables when textrows
        me.tabTable_2_Res_height = me.twinBlock * 0.70;
        me.tabTable_2_height = me.twinBlock * 0.60;

        //Used in Resources second panel -tab tables when textrows
        me.tabTable_2_10_Res_height = me.twinBlock * 0.60;
        me.tabTable_2_10_height = me.twinBlock * 0.40;

        // One table with 2 lines of fields and footer
        me.tabTable_2_Res_height_12 = me.twinBlock * 0.58;
        me.tabTable_2_height_12 = me.twinBlock * 0.48;

        // Used in second table with nested tabs like tabMain -> tabSub -> Table
        me.tabTable_3_Res_height = me.twinBlock * 0.78;
        me.tabTable_3_height = me.twinBlock * 0.60;

        // No table in the bottom block
        me.noTableInBottomBlock = me.twinBlock * 0.70;

        // No table in the bottom block with tabs
        me.noTableInBottomBlockWithTabs = me.twinBlock * 0.50;

        me.tabContentHeight = me.twinBlock - 50;
        me.tabTable_2_heightTable = me.twinBlock * 0.53;

        me.popUpTableResHeight = me.block * 0.60;
        me.popUpTableHeight = me.block * 0.55;

        me.popUpTableSmallResHeight = me.block * 0.50;
        me.popUpTableSmallHeight = me.block * 0.40;

        //double table headers in single table at first panel
        me.doubleHeaderTableResHeight = me.twinBlock * 0.65;
        me.doubleHeaderTableHeight = me.twinBlock * 0.48;

        me.tabTable_4_Res_Height = me.twinBlock * 0.50;
        me.tabTable_4_Height = me.twinBlock * 0.40;

        //nested tab with textrow
        me.nestedTabTable_1_Res_Height = me.twinBlock * 0.35;
        me.nestedTabTable_1_Height = me.twinBlock * 0.30;

        //nested tab with textrow
        me.nestedTabTable_0_Res_Height = me.twinBlock * 0.45;
        me.nestedTabTable_0_Height = me.twinBlock * 0.30;

        //nested tab without textrow
        me.nestedTabTable_2_Res_Height = me.twinBlock * 0.60;
        me.nestedTabTable_2_Height = me.twinBlock * 0.52;

        //table in Tabs at two rows textboxes and tabs in third row
        me.tabTable_5_Res_Height = me.block * 0.65;
        me.tabTable_5_Height = me.block * 0.55;

        me.TripleHeaderTableResHeight = me.twinBlock * 0.80;

        // Reports Styles
        me.chartResHeight = me.twinBlock * -0.50;
        me.tableWithChartResHeight = me.twinBlock * 0.45;
        me.tableWithChartHeight = me.twinBlock * 0.55;

        me.schedulesChart = me.twinBlock * 0.73;
    }

    function styles35_65(me) {

        me.topBlock = me.contentArea * 0.34;
        me.bottomBlock = me.contentArea * 0.63;

        //top panel table(35:65)- full table
        me.topTableResHeight = me.topBlock * 0.90;
        me.topTableHeight = me.topBlock * 0.75;
        //top panel table(35:65)-table with footer
        me.topFooterTableResHeight = me.topBlock * 0.75;
        me.topFooterTableHeight = me.topBlock * 0.55;
        //top panel table(35:65)-table with footer along with panel header
        me.topHeaderFooterTableResHeight = me.topBlock * 0.58;
        me.topHeaderFooterTableHeight = me.topBlock * 0.50;

        //top Seacrh criteria and panel table(35:65)-table with footer
        me.topSrcFooterTableResHeight = me.topBlock * 0.40;
        me.topSrcFooterTableHeight = me.topBlock * 0.35;

        // One table with 2 lines of fields and footer
        me.singleTableResHeight_13 = me.block * 0.76;
        me.singleTableHeight_13 = me.block * 0.68;

        //bottom panel table with footer in (35:65)
        me.tabR1Table_1_Res_height = me.bottomBlock * 0.80;
        me.tabR1Table_1_height = me.bottomBlock * 0.70;

        // One table with 2 lines of fields and footer
        me.singleTableResHeight_12 = me.block * 0.80;
        me.singleTableHeight_12 = me.block * 0.70;

        // One table with 2 lines of fields and footer
        me.singleTableResHeight_15 = me.block * 0.78;
        me.singleTableHeight_15 = me.block * 0.73;

        //bottom panel table with footer in (35:65) with search criteria
        me.tabR1Table_2_Res_height = me.bottomBlock * 0.63;
        me.tabR1Table_2_height = me.bottomBlock * 0.57;

        //bottom panel table(35:65)-full table
        me.bottomTabTableResHeight = me.bottomBlock * 0.75;
        me.bottomTabTableHeight = me.bottomBlock * 0.65;

        //bottom panel table(35:65)-full table
        me.bottomTabTableResHeight_14 = me.bottomBlock * 0.80;
        me.bottomTabTableHeight_14 = me.bottomBlock * 0.60;

        //bottom panel table(35:65)-full table
        me.bottomTabDoubleHeaderResHeight = me.bottomBlock * 0.64;
        me.bottomTabDoubleHeaderHeight = me.bottomBlock * 0.56;

        //bottom panel table(35:65)- table and footer buttons along with panel header 
        me.bottomHeaderFooterTableResHeight = me.bottomBlock * 0.80;
        me.bottomHeaderFooterTableHeight = me.bottomBlock * 0.72;

        //bottom panel table(35:65)- table and footer buttons along with panel header 
        me.bottomDouleHeaderFooterTableResHeight = me.bottomBlock * 0.76;
        me.bottomDouleHeaderFooterTableHeight = me.bottomBlock * 0.50;

        //botoom panel table(35:65) in nested tabs
        me.bottomNestedTabTableResHeight = me.bottomBlock * 0.65;
        me.bottomNestedTabTableHeight = me.bottomBlock * 0.60;
    }

    function styles65_35(me) {
        me.topPanelBlock = me.contentArea * 0.63;
        me.bottomPanelBlock = me.contentArea * 0.34;

        //double table headers in single table at first panel
        me.doubleHeaderTableResHeight_topB = me.twinBlock * 0.90;
        me.doubleHeaderTableHeight_topB = me.twinBlock * 0.74;
        
        me.tabTable_1_Res_height_topB = me.twinBlock * 0.45;
        me.tabTable_1_height_topB = me.twinBlock * 0.35;

        me.tabTable_3_Res_height_topB = me.twinBlock * 0.45;
        me.tabTable_3_height_topB = me.twinBlock * 0.35;

        me.tabTable_2_Res_height_12_topB = me.twinBlock * 0.28;
        me.tabTable_2_height_12_topB = me.twinBlock * 0.20;

        me.tabTable_2_Res_height_topB = me.twinBlock * 0.30;
        me.tabTable_2_height_topB = me.twinBlock * 0.22;

    }

    currentObj.setStyles = function (me) {
        styles50_50(me);
        styles35_65(me);
        styles65_35(me);
    }

});