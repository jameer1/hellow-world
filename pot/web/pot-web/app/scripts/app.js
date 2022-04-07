'use strict';

/**
 * @ngdoc overview
 * @name potApp
 * @description # potApp
 * 
 * Main module of the application.
 */

import angular from "angular";
import LocalStorageModule from "angular-local-storage";
import angularFilter from "angular-filter";
import blockUI from "angular-block-ui";
import UIRouter from "@uirouter/angularjs";
import ngFileUpload from "ng-file-upload";
import angularFileUpload from "angular-file-upload";
import angularTranslate from "angular-translate";
import chartJs from "angular-chart.js";
import angularBootstrap from "angular-bootstrap/ui-bootstrap";
import ngTableToCsv from "ng-table-to-csv/dist/ng-table-to-csv";
import angularBootstrapContextMenu from "angular-bootstrap-contextmenu";
import ngAria from "angular-aria";
import ngMessages from "angular-messages";
import restangular from "restangular/dist/restangular";
import ngDialog from "ng-dialog";
import ngSanitize from "angular-sanitize";
import jquery from "jquery";
import jqueryUI from "jquery-ui/ui/widgets/datepicker";
import jquerySlimScroll from "jquery-slimscroll";
import bootstrapSass from "bootstrap-sass";
import angularMaterial from "angular-material";
import ngPatternRestrict from "ng-pattern-restrict";
import uiGrid from "angular-ui-grid/ui-grid.min.js";
import gantt from "angular-gantt";
import ganttPlugins from "angular-gantt/assets/angular-gantt-plugins.js";
import angularMoment from "angular-moment";
import angularResizable from "angular-resizable";
import {saveAs} from "file-saver";

require('../styles/styles.less');

const _ = require("lodash");
window.moment = require("moment");

require("datatables.net-dt");

// Load CSS here

/* Third Party CSS styles */
import "angular-block-ui/dist/angular-block-ui.css";
import "ng-dialog/css/ngDialog.css";
import "ng-dialog/css/ngDialog-theme-default.css";
import "angularjs-datepicker/dist/angular-datepicker.css";
import "angular-tooltips/dist/angular-tooltips.min.css";
import "angular-moment-picker/dist/angular-moment-picker.min.css";
import "angularjs-datepicker/dist/angular-datepicker.css";
import "jquery-ui/themes/base/all.css";
import "jquery-ui/themes/base/button.css";
import "jquery-ui/themes/base/datepicker.css";
import "jquery-ui/ui/widgets/datepicker";
import "angular-gantt/assets/angular-gantt.css";
import "angular-gantt/assets/angular-gantt-plugins.css";
import "angular-resizable/angular-resizable.min.css";
import "angular-ui-tree/dist/angular-ui-tree.js";
import "angular-ui-tree/dist/angular-ui-tree.css";
require('angular-ui-grid/ui-grid.css');


window.app = angular.module(
    'potApp',
    ['LocalStorageModule', angularFilter, 'blockUI', UIRouter,
        'ngFileUpload', 'angularFileUpload', 'chart.js',
        'ui.bootstrap', 'ngTableToCsv', 'ui.bootstrap.contextMenu',
        'ngAria', 'ngMessages', 'restangular', 'ngDialog', 'ngSanitize', 
        angularMaterial, 'ngPatternRestrict', 'pascalprecht.translate', 
        'ui.grid', 'ui.grid.exporter', 'ui.grid.resizeColumns',
        'ui.grid.moveColumns', 'ui.grid.autoResize', 'ui.grid.selection', 'ui.grid.grouping', 
        'gantt', 'gantt.table', 'gantt.tree', 'gantt.dependencies', 'gantt.progress', 'gantt.groups', 'gantt.tooltips', 
        'angularMoment', 'angularResizable']);

// Import all local javascript files after 1 second 
setTimeout(() => {

    // Load all dependencies
    function importAll(r) {
        r.keys().forEach(r);
    }

    importAll(require.context('./', true, /\.js$/));

    // Bootstrap angular app manually
    angular.bootstrap(document, ["potApp"], {
        strictDi: true
    });

}, 1000);


