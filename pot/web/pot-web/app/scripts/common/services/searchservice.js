'use strict';

app.service('potSearchService', function () {
	this.searchPages = [
        {
            searchkeyWord: "EPS",
            url: "enterprise",
            class:"fa fa-building",
            path:"EnterPrise"
        },
        {
            searchkeyWord: "Company List",
            url: "company",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Measuring Units",
            url: "measure",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Weather Classification",
            url: "weather",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Central Employee Classification",
            url: "employee",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Central Plant Classification",
            url: "plant",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Central Material Classification",
            url: "materialclass",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Tangible Item Classification",
            url: "tangibleclass",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Cost Code Classification",
            url: "costcode",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Employee Wages",
            url: "empwage",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Procurement Category",
            url: "procurecatg",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Project Services Classification",
            url: "serviceclass",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Warehouse and Stockyard List",
            url: "store",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Leave Types",
            url: "projleavetype",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Plant Service History",
            url: "plantservicehistory",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Finance Center",
            url: "financecentre",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Unit of Pay Rates",
            url: "unitofpayrates",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Tax Codes",
            url: "taxcodes",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Tax Country Provisions",
            url: "taxcountryprovison",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Tax Types",
            url: "taxtypes",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Tax Code Types",
            url: "taxcodetypes",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Profit Center",
            url: "profitcentre",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Country Codes",
            url: "country",
            class:"fa fa-building",
            path:"EnterPrise/Central Library/Finance Codes"
        },
        {
            searchkeyWord: "Immovable Assets",
            url: "assetscategory",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Assets Maintenance",
            url: "assetsmaintenancecategory",
            class:"fa fa-building",
            path:"EnterPrise/Central Library"
        },
        {
            searchkeyWord: "Calendars",
            url: "calendar",
            class:"fa fa-building",
            path:"EnterPrise/Tools"
        },
        {
            searchkeyWord: "Resource Curves",
            url: "resourcecurves",
            class:"fa fa-building",
            path:"EnterPrise/Tools"
        },
        {
            searchkeyWord: "Project List",
            url: "epsproject",
            class:"fa fa-folder",
            path:"Projects"
        },
        {
            searchkeyWord: "Project Settings",
            url: "projsettings",
            class:"fa fa-folder",
            path:"Projects"
        },
        {
            searchkeyWord: "Project Employee Classification",
            url: "projempclass",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Project Material Transfer Restriction List",
            url: "projmaterial",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Project Ware House and Stock Yard List",
            url: "projstore",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Project Plant Classification",
            url: "projplantclass",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Project Working Shifts",
            url: "projworkshift",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Project Crew List",
            url: "projcrewlist",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Schedule of Estimates",
            url: "projsoe",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Schedule of Rates",
            url: "projsor",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Cost Code Schedule",
            url: "projcostcode",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Scope Of Works",
            url: "projsow",
            class:"fa fa-folder",
            path:"Projects/Project Library"
        },
        {
            searchkeyWord: "Project Budgets",
            url: "projbudgets",
            class:"fa fa-folder",
            path:"Projects"
        },
        {
            searchkeyWord: "Progress Based on Schedule of Rates",
            url: "progressmeasureprojstatus",
            class:"fa fa-folder",
            path:"Projects/Progress Measure"
        },
        {
            searchkeyWord: "Progress Measure Based on Contract Milestones",
            url: "progressmeasurecontractmilestones",
            class:"fa fa-folder",
            path:"Projects/Progress Measure"
        },
        {
            searchkeyWord: "Progress Measure Based on Cost Plus %",
            url: "progressmeasurecostplus",
            class:"fa fa-folder",
            path:"Projects/Progress Measure"
        },
        {
            searchkeyWord: "Project Status",
            url: "projstatus",
            class:"fa fa-folder",
            path:"Projects"
        },
        {
            searchkeyWord: "Notes",
            url: "notes",
            class:"fa fa-folder",
            path:"Projects"
        },
        {
            searchkeyWord: "Project Folders",
            url: "folder",
            class:"glyphicon glyphicon-folder-open glyphstyle",
            path:"Documents"
        },
        {
            searchkeyWord: "Project Documents",
            url: "document",
            class:"glyphicon glyphicon-folder-open glyphstyle",
            path:"Documents"
        },
        {
            searchkeyWord: "Activity Schedule Data Table",
            url: "activityscheduledatatable",
            class: "glyphicon glyphicon-calendar glyphstyle",
            path:"Schedules"
        },
        {
            searchkeyWord: "Resource Schedules",
            url: "projschedules",
            class: "glyphicon glyphicon-calendar glyphstyle",
            path:"Schedules"
        },
        {
            searchkeyWord: "SOW Schedule",
            url: "wbsschedules",
            class: "glyphicon glyphicon-calendar glyphstyle",
            path:"Schedules"
        },
        {
            searchkeyWord: "Tangible Items Schedules",
            url: "tanschedules",
            class: "glyphicon glyphicon-calendar glyphstyle",
            path:"Schedules"
        },
        {
            searchkeyWord: "Activity Schedules",
            url: "actschedules",
            class: "glyphicon glyphicon-calendar glyphstyle",
            path:"Schedules"
        },
        {
            searchkeyWord: "Resource Assignment Data Table",
            url: "resourceassignment",
            class: "glyphicon glyphicon-calendar glyphstyle",
            path:"Schedules"
        },
        {
            searchkeyWord: "Pre-Contract List",
            url: "precontractlist",
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement/Pre-Contracts"
        },
        {
            searchkeyWord: "Stage 1 Request",
            url: "internalApproval",
            params: {'request':'request'},
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement/Pre-Contracts"
        },
        {
            searchkeyWord: "Stage 1 Approval",
            url: "internalApproval",
            params: {'request':'approval'},
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement/Pre-Contracts"
        },
        {
            searchkeyWord: "RFQ",
            url: "rfq",
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement/Pre-Contracts"
        },
        {
            searchkeyWord: "Stage 2 Request",
            url: "externalApproval",
            params: {'request':'request'},
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement/Pre-Contracts"
        },
        {
            searchkeyWord: "Stage 2 Approval",
            url: "externalApproval",
            params: {'request':'approval'},
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement/Pre-Contracts"
        },
        {
            searchkeyWord: "Purchase Orders",
            url: "purchaseorder",
            class: "glyphicon glyphicon-shopping-cart glyphstyle",
            path:"Procurement"
        },
        {
            searchkeyWord: "Employee",
            url: "empregister",
            class: "fa fa-users",
            path:"Resources"
        },
        {
            searchkeyWord: "Plant & Equipments",
            url: "plantregistor",
            class: "fa fa-users",
            path:"Resources"
        },
        {
            searchkeyWord: "Materials",
            url: "materialregistor",
            class: "fa fa-users",
            path:"Resources"
        },
        {
            searchkeyWord: "Employee Attendance",
            url: "empattendence",
            class: "fa fa-database",
            path:"Asbuilt Records/Attendance Records"
        },
        {
            searchkeyWord: "Plants Attendance",
            url: "plantattendence",
            class: "fa fa-database",
            path:"Asbuilt Records/Attendance Records"
        },
        {
            searchkeyWord: "Create Work Diary",
            url: "listofcreatedworkdairy",
            class: "fa fa-database",
            path:"Asbuilt Records/Work Diary"
        },
        {
            searchkeyWord: "Approve Work Diary",
            url: "listofapproveworkdiary",
            class: "fa fa-database",
            path:"Asbuilt Records/Work Diary"
        },
        {
            searchkeyWord: "Client Approval",
            url: "workdairyclientapproval",
            class: "fa fa-database",
            path:"Asbuilt Records/Work Diary"
        },
        {
            searchkeyWord: "Create Time Sheet",
            url: "listofcreatetimesheet",
            class: "fa fa-database",
            path:"Asbuilt Records/Time sheets"
        },
        {
            searchkeyWord: "Approve Time Sheet",
            url: "listofapprovetimesheet",
            class: "fa fa-database",
            path:"Asbuilt Records/Time sheets"
        },
        {
            searchkeyWord: "Generate Progress Claim",
            url: "generateprogress",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Receivables"
        },
        {
            searchkeyWord: "Progress Claims",
            url: "statusonprogress",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Receivables"
        },
        {
            searchkeyWord: "Project Owner Invoices",
            url: "statusoninvoice",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Receivables"
        },
        {
            searchkeyWord: "Vendor Invoices",
            url: "invoicefromvendor",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Employee Payables",
            url: "empcalculation",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Employee Payroll",
            url: "emppayrollrun",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Employee Payments",
            url: "empcalrecord",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Employee Payrolls",
            url: "emppayrollrecord",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Superfund credit",
            url: "providentfund",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Tax Payments",
            url: "taxpayments",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance/Payables"
        },
        {
            searchkeyWord: "Plants Book Value",
            url: "plantbook",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance"
        },
        {
            searchkeyWord: "Materials Stock value",
            url: "materialstock",
            class: "glyphicon glyphicon-piggy-bank glyphstyle",
            path:"Finance"
        },
        {
            searchkeyWord: "Master Dashboard",
            url: "masterdashboards",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard"
        },
        {
            searchkeyWord: "Progress & Performance",
            url: "performanceindices",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Performance"
        },
        {
            searchkeyWord: "Cost Schedule & Performance",
            url: "costandscheduleperformance",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Performance"
        },
        {
            searchkeyWord: "Cost & Schedule(Units)",
            url: "costandschedulevarianceunits",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Performance"
        },
        {
            searchkeyWord: "Cost and Schedule(%)",
            url: "costschedulevariancepercentage",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Performance"
        },
        {
            searchkeyWord: "Cost Schedule & Performance Indices",
            url: "costscheduleperformanceindices",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Performance"
        },
        {
            searchkeyWord: "Performance Index",
            url: "tocompleteperformanceindex",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Performance"
        },
        {
            searchkeyWord: "Plans Vs Actual",
            url: "planvsactualprogress",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Progress"
        },
        {
            searchkeyWord: "Cost & Schedule(Bubble Chart)",
            url: "costandschedulevariancebubblechart",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Progress"
        },
        {
            searchkeyWord: "Current Progress(%)",
            url: "currentdatesandprogresspercentage",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Progress"
        },
        {
            searchkeyWord: "Cost & Schedule(Units)",
            url: "costschedulevarianceunits",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Progress"
        },
        {
            searchkeyWord: "Projects Gantt Chart",
            url: "projectsganttchart",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Progress"
        },
        {
            searchkeyWord: "Country",
            url: "budgetbycountry",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Budget"
        },
        {
            searchkeyWord: "Province",
            url: "budgetbyprovince",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Budget"
        },
        {
            searchkeyWord: "Dashboard Budget - Project",
            url: "budgetbyproject",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Budget"
        },
        {
            searchkeyWord: "Project Manager",
            url: "budgetbyprojectmanager",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Budget"
        },
        {
            searchkeyWord: "Cost Health Check",
            url: "costhealthcheck",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Cost"
        },
        {
            searchkeyWord: "CV and SV for Cost(Bubble Chart)",
            url: "cvandsvforcostbubblechart",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Cost"
        },
        {
            searchkeyWord: "Original Vs Estimate(Cost)",
            url: "originalvsestimateatcompletioncost",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Cost"
        },
        {
            searchkeyWord: "Plan Vs Actual Vs Earned(Cost)",
            url: "planvsactualvsearnedvalue",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Cost"
        },
        {
            searchkeyWord: "Cost Work Sheet",
            url: "costworksheet",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Cost"
        },
        {
            searchkeyWord: "Labour Health Check",
            url: "labourhourhealthcheck",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Labour"
        },
        {
            searchkeyWord: "CV and SV for Labour (Bubble Chart)",
            url: "cvandsvforlaboursbubblechart",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Labour"
        },
        {
            searchkeyWord: "Original Vs Estimate at Completion - Manhours",
            url: "originalvsestimateatcompletionmanhours",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Labour"
        },
        {
            searchkeyWord: "Plan Vs Actual Vs Earned - Direct Mnahours",
            url: "planvsactualvsearnedvaluedirectmanhours",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Labour"
        },
        {
            searchkeyWord: "Productivity Rates - Major Items of Work",
            url: "productivityratesmajoritemsofwork",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Labour"
        },
        {
            searchkeyWord: "Periodical Productivity Rates - Major Items of Work",
            url: "periodicalproductivityratesmajoritemsofwork",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Labour"
        },
        {
            searchkeyWord: "Actual Cost - By Country",
            url: "actualcostbycountry",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Actual Cost"
        },
        {
            searchkeyWord: "Actual Cost - By Province",
            url: "actualcostbyprovince",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Actual Cost"
        },
        {
            searchkeyWord: "Actual Cost - By Project",
            url: "actualcostbyproject",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Actual Cost"
        },
        {
            searchkeyWord: "Actual Cost - By Project Manager",
            url: "actualcostbyprojectmanager",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Actual Cost"
        },
        {
            searchkeyWord: "Earned Value - By Country",
            url: "earnedvaluebycountry",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Earned Value"
        },
        {
            searchkeyWord: "Earned Value - By Province",
            url: "earnedvaluebyprovince",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Earned Value"
        },
        {
            searchkeyWord: "Earned Value - By Project",
            url: "earnedvaluebyproject",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Earned Value"
        },
        {
            searchkeyWord: "Earned Value - By Project Manager",
            url: "earnedvaluebyprojectmananger",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Earned Value"
        },
        {
            searchkeyWord: "Remaining Budget - By Country",
            url: "remainingbudgetbycountry",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Remaining Budget"
        },
        {
            searchkeyWord: "Remaining Budget - By Province",
            url: "remainingbudgetbyprovince",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Remaining Budget"
        },
        {
            searchkeyWord: "Remaining Budget - By Project",
            url: "remainingbudgetbyproject",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Remaining Budget"
        },
        {
            searchkeyWord: "Remaining Budget - By Project Manager",
            url: "remainingbudgetbyprojectmanager",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Remaining Budget"
        },
        {
            searchkeyWord: "Estimate To Complete - By Country",
            url: "estimatetocompletebycountry",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate to Complete"
        },
        {
            searchkeyWord: "Estimate To Complete - By Province",
            url: "estimatetocompletebyprovince",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate to Complete"
        },
        {
            searchkeyWord: "Estimate To Complete - By Project",
            url: "estimatetocompletebyproject",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate to Complete"
        },
        {
            searchkeyWord: "Estimate To Complete - By Project Manager",
            url: "estimatetocompleteprojectmanager",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate to Complete"
        },
        {
            searchkeyWord: "Estimate At Completion - By Country",
            url: "estimateatcompletionbycountry",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate At Completion"
        },
        {
            searchkeyWord: "Estimate At Complete - By Province",
            url: "estimateatcompletionbyprovince",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate At Completion"
        },
        {
            searchkeyWord: "Estimate At Complete - By Project",
            url: "estimateatcompletionbyproject",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate At Completion"
        },
        {
            searchkeyWord: "Estimate At Complete - By Project Manager",
            url: "estimateatcompletionbyprojectmanager",
            class: "glyphicon glyphicon-dashboard glyphstyle",
            path: "Dashboard/Estimate At Completion"
        },
        {
            searchkeyWord: "Daily Employee Records",
            url: "dailyemprecords",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Crew Wise Daily Deployment List",
            url: "crewwiseemp",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Daily Plant Records",
            url: "dailyplant",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Monthly Employee Records",
            url: "monthlyemp",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Monthly Plant Records",
            url: "monthlyplant",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Emp Daily Resources Status",
            url: "empdailyresourcestatus",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Plant Daily Resources Status",
            url: "plantdailyresourcestatus",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Attendance Records"
        },
        {
            searchkeyWord: "Work Diary Search & Its Records",
            url: "workdairydailysearch",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Diary"
        },
        {
            searchkeyWord: "Work Diary Submission & Approval Status",
            url: "workdiarysubmitappr",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Diary"
        },
        {
            searchkeyWord: "Time Sheet Search & Its Records",
            url: "timesheetsearch",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Time Sheets"
        },
        {
            searchkeyWord: "Time Sheet Submission & Approval Status",
            url: "timesheetsubmitappr",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Time Sheets"
        },
        {
            searchkeyWord: "Man Power Periodical- Actual Hours",
            url: "manpowerperiodical",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Man Power Date wise - Actual Hours",
            url: "manpowerdatewise",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Man Power Cost Code Wise - Daily Deployment List",
            url: "manpowercostwise",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Man Power Actual Vs Standard Hours",
            url: "manpoweractualvsstandhrs",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Idle Hours Records",
            url: "manpoweridlehrs",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Gender Statistics",
            url: "genderstats",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Periodical - Mobilisation Statistics",
            url: "manpowermobistats",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Current Employee List",
            url: "currentemplist",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Manpower - Productivity Analysis",
            url: "manpowerproductivityanalysis",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Plan Vs Actual Vs Earned Hours",
            url: "manpowerplanedactualearned",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Man power"
        },
        {
            searchkeyWord: "Plant Periodical- Actual Hours",
            url: "plantperiodical",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Plant"
        },
        {
            searchkeyWord: "Plant Date wise - Actual Hours",
            url: "plantdatewise",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Plant"
        },
        {
            searchkeyWord: "Plant Cost Code Wise - Daily Deployment List",
            url: "plantcostcodewise",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Plant"
        },
        {
            searchkeyWord: "Plant Idle Hours",
            url: "plantidlehrs",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Plant"
        },
        {
            searchkeyWord: "Plant Actual Vs Standard Hours",
            url: "plantactualvsstandardhrs",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Plant"
        },
        {
            searchkeyWord: "Current Plant List",
            url: "plantlist",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Plant"
        },
        {
            searchkeyWord: "Delivery and Supply Details",
            url: "matdeliverysupplyreport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Daily Issue Records",
            url: "matdailyissuereport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Store Stock Balance in Transit",
            url: "matstorestockreport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Stock Piles - Stock Balance",
            url: "matstockpilesreport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Periodical Consumption",
            url: "matperiodicalconsumptionreport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Date wise Consumption",
            url: "matdatewisereport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Inventory Records",
            url: "matinventoryreport",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Materials"
        },
        {
            searchkeyWord: "Periodical Progress",
            url: "periodicalprogress",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Progress"
        },
        {
            searchkeyWord: "Date Wise Progress",
            url: "datewiseprogress",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Progress"
        },
        {
            searchkeyWord: "Progress - Plan Vs Actual",
            url: "progressplanvsactual",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Progress"
        },
        {
            searchkeyWord: "Periodical - Progress Claim",
            url: "periodicalprogressclaim",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Progress"
        },
        {
            searchkeyWord: "Date wise - Actual Cost Details",
            url: "costperformancedatewise",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Periodical - Plan Vs Actual Vs Earned Value",
            url: "costperformanceperiodical",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Date Wise - Plan Vs Actual Vs Earned Value",
            url: "costperformancedatewiseplantandactual",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Cost & Schedule Variance",
            url: "costperformancecostschedulevariance",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Cost & Schedule Performance Index",
            url: "costperformancecostscheduleperformanceindex",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Item Wise Budget Provision",
            url: "costperformanceitemwisebudgetprovision",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Item Wise - Plan Vs Actual Cost",
            url: "costperformanceitemwiseplantactualcost",
            class: "glyphicon glyphicon-list glyphstyle",
            path: "Reports/Cost and Performance"
        },
        {
            searchkeyWord: "Employee Transfer Request",
            url: "empreqtransfer1",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Requests"
        },
        {
            searchkeyWord: "Plant Transfer Request",
            url: "planttransferrequest1",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Requests"
        },
        {
            searchkeyWord: "Materials Transfer Request",
            url: "materialtransferrequest1",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Requests"
        },
        {
            searchkeyWord: "Procurement Stage 1 Request",
            url: "internalApprova",
            params: {'request':'request'},
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Requests"
        },
        {
            searchkeyWord: "Procurement Stage 2 Request",
            url: "externalApproval",
            params: {'request':'request'},
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Requests"
        },
        {
            searchkeyWord: "Leave Request",
            url: "empleaverequest",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Requests"
        },
        {
            searchkeyWord: "Approve Employee Transfer",
            url: "emptransferapprove1",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Approvals"
        },
        {
            searchkeyWord: "Approve Plant Transfer",
            url: "planttransferapprove1",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Approvals"
        },
        {
            searchkeyWord: "Approve Materials Transfer",
            url: "materialtransferapproval1",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Approvals"
        },
        {
            searchkeyWord: "Approve Procurement Stage 1",
            url: "internalApproval",
            params: {'request':'approval'},
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Approvals"
        },
        {
            searchkeyWord: "Approve Procurement Stage 2",
            url: "externalApproval",
            params: {'request':'approval'},
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Approvals"
        },
        {
            searchkeyWord: "Leave Approvals",
            url: "empleaveapproval",
            class: "glyphicon glyphicon-check glyphstyle",
            path: "Requests & Approvals/Approvals"
        },
        /*{
            searchkeyWord: "Client Registration",
            url: "clientreg",
            class: "fa fa-user",
            path: "Admin"
        },*/
        {
            searchkeyWord: "User List",
            url: "clientusers",
            class: "fa fa-user",
            path: "Admin"
        },
        {
            searchkeyWord: "User Profile and Privileges",
            url: "clientroles",
            class: "fa fa-user",
            path: "Admin"
        },
        {
            searchkeyWord: "Email Hosting Details",
            url: "emailSettings",
            class: "fa fa-user",
            path: "Admin"
        },
        {
            searchkeyWord: "Work Diary Notifications",
            url: "notifyworkdairy",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Time Sheet Notifications",
            url: "notifytimesheet",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Attendance Notifications",
            url: "notifyattendancerecords",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Procurement Notifications",
            url: "notifyprocurement",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Employee Transfer Notifications",
            url: "notifyemptransfer",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Plants Transfer Notifications",
            url: "notifyplanttransfer",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Materials  Transfer Notifications",
            url: "notifymaterialtransfer",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Leave Request & Approval Notifications",
            url: "notifyempleave",
            class: "fa fa-bell",
            path: "Notifications"
        },
        {
            searchkeyWord: "Help & Tutorials",
            url: "help",
            class: "fa fa-info-circle",
            path: "Help Tutorials"
        },
        {
            searchkeyWord: "About Project on Track",
            url: "//rajutech.com",
            class: "fa fa-info-circle",
            path: "Help Tutorials"
        },
        {
            searchkeyWord: "Change Password",
            url: "changepawd",
            class: "fa fa-caret-down",
            path: "My Account"
        }
    ];
});