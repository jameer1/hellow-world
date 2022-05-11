'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Module service which holds json data of all modules.
 */
app.service('moduleservice', function () {

  var me = this;
  me.modules = [];

  me.modules.push(getAdminModule());
  var jsonModules = getAdminModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  /*
  me.modules.push(getMyAccount());
  var jsonModules = getMyAccount();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }
  */

  me.modules.push(getFinanceModule());
  var jsonModules = getFinanceModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getDashboardModule());
  var jsonModules = getDashboardModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getReportModule());
  var jsonModules = getReportModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getEnterpriseModule());
  var jsonModules = getEnterpriseModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getProjectModule());
  var jsonModules = getProjectModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getDocuments());
  var jsonModules = getDocuments();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getSchedules());
  var jsonModules = getSchedules();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }
  me.modules.push(getProcurement());
  var jsonModules = getProcurement();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }
  

  me.modules.push(getResourcesModule());
  var jsonModules = getResourcesModule();
  for (var i = 0; i < jsonModules.length; i++) {
	 me.modules.push(jsonModules[i]);
  }
  me.modules.push(getAsbuiltRecordsModule());
  var jsonModules = getAsbuiltRecordsModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getRequestapprovalModule());
  var jsonModules = getRequestapprovalModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  me.modules.push(getNotficationModule());
  var jsonModules = getNotficationModule();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }

  /*
  me.modules.push(getHelpTutorials());
  var jsonModules = getJSONModules();
  for (var i = 0; i < jsonModules.length; i++) {
    me.modules.push(jsonModules[i]);
  }
  */

  function getEnterpriseModule() {
    var enterpriseModule = createModule("Enterprise", true, "", 'Y');
    enterpriseModule.permissionTOs.push(createPermission("ENTERPRISE_VIEW", "VIEW", false));

    var eps = createModule("EPS", true, "enterprise", "Y");
    eps.permissionTOs.push(createPermission("ENTRPRSE_EPS_VIEW", "VIEW", false));
    eps.permissionTOs.push(createPermission("ENTRPRSE_EPS_ADD", "ADD", false));
    eps.permissionTOs.push(createPermission("ENTRPRSE_EPS_DELETE", "DEACTIVATE", false));

    enterpriseModule.childModules.push(eps);

    var centralLibraryModule = createModule("Central Library", true, "", "Y");
    centralLibraryModule.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_VIEW", "VIEW", false));
    /* centralLibraryModule.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_ADD", "ADD", false));
    centralLibraryModule.permissionTOs.push(createPermission("ENTERPRSE_CENTLIB_DELETE", "DEACTIVATE", false)); */

    var companyList = createModule("Company List", true, "", "Y");
    companyList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_CMPLIST_VIEW", "VIEW", false));
    companyList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_CMPLIST_ADD", "ADD", false));
    companyList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_CMPLIST_DELETE", "DEACTIVATE", false));
    companyList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_CMPLIST_ACTIVATE", "ACTIVATE", false));

    var address = createModule("Address", true, "company", "Y");
    address.permissionTOs.push(createPermission("CMPLIST_ADDRESS_VIEW", "VIEW", false));
    address.permissionTOs.push(createPermission("CMPLIST_ADDRESS_ADD", "ADD", false));
    address.permissionTOs.push(createPermission("CMPLIST_ADDRESS_DELETE", "DEACTIVATE", false));
    companyList.childModules.push(address);

    var contacts = createModule("Contacts", true, "company", "Y");
    contacts.permissionTOs.push(createPermission("CMPLIST_CONTACTS_VIEW", "VIEW", false));
    contacts.permissionTOs.push(createPermission("CMPLIST_CONTACTS_ADD", "ADD", false));
    contacts.permissionTOs.push(createPermission("CMPLIST_CONTACTS_DELETE", "DEACTIVATE", false));
    companyList.childModules.push(contacts);

    var closedProjects = createModule("Closed Projects", true, "company", "Y");
    closedProjects.permissionTOs.push(createPermission("CMPLIST_CLOSESPROJECT_VIEW", "VIEW", false));
    companyList.childModules.push(closedProjects);

    var currentProject = createModule("Current Project", true, "company", "Y");
    currentProject.permissionTOs.push(createPermission("CMPLIST_CURRENTPROJ_VIEW", "VIEW", false));
    currentProject.permissionTOs.push(createPermission("CMPLIST_CURRENTPROJ_ADD", "ADD", false));
    currentProject.permissionTOs.push(createPermission("CMPLIST_CURRENTPROJ_DELETE", "DEACTIVATE", false));
    currentProject.permissionTOs.push(createPermission("CMPLIST_CURRENTPROJ_MOVETOCLOSE", "MOVE TO CLOSED", false));
    companyList.childModules.push(currentProject);

    var bankaccount = createModule("Bank Account Details", true, "company", "Y");
    bankaccount.permissionTOs.push(createPermission("CMPLIST_BANKPROJ_VIEW", "VIEW", false));
    bankaccount.permissionTOs.push(createPermission("CMPLIST_BANKACCOUNT_ADD", "ADD", false));
    bankaccount.permissionTOs.push(createPermission("CMPLIST_BANKACCOUNT_DELETE", "DEACTIVATE", false));
    companyList.childModules.push(bankaccount);

    centralLibraryModule.childModules.push(companyList);

    var measurementUnits = createModule("Measurement Units", true, "measure", "Y");
    measurementUnits.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MEASUREUNIT_VIEW", "VIEW", false));
    measurementUnits.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MEASUREUNIT_ADD", "ADD", false));
    measurementUnits.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MEASUREUNIT_DELETE", "DEACTIVATE", false));
    measurementUnits.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MEASUREUNIT_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(measurementUnits);

    var weatherClassification = createModule("Weather Classification", true, "weather", "Y");
    weatherClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_WEATHRCLSFY_VIEW", "VIEW", false));
    weatherClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_WEATHRCLSFY_ADD", "ADD", false));
    weatherClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_WEATHRCLSFY_DELETE", "DEACTIVATE", false));
    weatherClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_WEATHRCLSFY_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(weatherClassification);

    var employeeClassification = createModule("Employee Classification", true, "employee", "Y");
    employeeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPCLSFY_VIEW", "VIEW", false));
    employeeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPCLSFY_ADD", "ADD", false));
    employeeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPCLSFY_DELETE", "DEACTIVATE", false));
    employeeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPCLSFY_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(employeeClassification);

    var plantClassification = createModule("Plant Classification", true, "plant", "Y");
    plantClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTCLSFY_VIEW", "VIEW", false));
    plantClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTCLSFY_ADD", "ADD", false));
    plantClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTCLSFY_DELETE", "DEACTIVATE", false));
    plantClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTCLSFY_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(plantClassification);

    var materialClassification = createModule("Material Classifications", true, "materialclass", "Y");
    materialClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MATERIALCLS_VIEW", "VIEW", false));
    materialClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MATERIALCLS_ADD", "ADD", false));
    materialClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_MATERIALCLS_DELETE", "DEACTIVATE", false));

    centralLibraryModule.childModules.push(materialClassification);

    // Tangible items module

    var tangibleClassification = createModule("Tangible Item Classification", true, "tangibleclass", "Y");
    tangibleClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_TANGIBLECLS_VIEW", "VIEW", false));
    tangibleClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_TANGIBLECLS_ADD", "ADD", false));
    tangibleClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_TANGIBLECLS_DELETE", "DEACTIVATE", false));

    centralLibraryModule.childModules.push(tangibleClassification);

    // end

    var costCodeClassification = createModule("Cost Code Classification", true, "costcode", "Y");
    costCodeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_COSTCODCLSFY_VIEW", "VIEW", false));
    costCodeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_COSTCODCLSFY_ADD", "ADD", false));
    costCodeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_COSTCODCLSFY_DELETE", "DEACTIVATE", false));
    costCodeClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_COSTCODCLSFY_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(costCodeClassification);

    var employeeWageFactor = createModule("Employee Wage Factor", true, "empwage", "Y");
    employeeWageFactor.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPWGEFCTR_VIEW", "VIEW", false));
    employeeWageFactor.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPWGEFCTR_ADD", "ADD", false));
    employeeWageFactor.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPWGEFCTR_DELETE", "DEACTIVATE", false));
    employeeWageFactor.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_EMPWGEFCTR_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(employeeWageFactor);

    var procurementCategory = createModule("Procurement Category", true, "procurecatg", "Y");
    procurementCategory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PROCURCATGRY_VIEW", "VIEW", false));
    procurementCategory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PROCURCATGRY_ADD", "ADD", false));
    procurementCategory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PROCURCATGRY_DELETE", "DEACTIVATE", false));
    procurementCategory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PROCURCATGRY_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(procurementCategory);

    var serviceClassification = createModule("Service Classification", true, "serviceclass", "Y");
    serviceClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_SERVCECLSFY_VIEW", "VIEW", false));
    serviceClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_SERVCECLSFY_ADD", "ADD", false));
    serviceClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_SERVCECLSFY_DELETE", "DEACTIVATE", false));
    serviceClassification.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_SERVCECLSFY_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(serviceClassification);

    var wareHouseAndStockyardList = createModule("Ware House and Stock Yard List", true, "store", "Y");
    wareHouseAndStockyardList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_STORANDSTOK_VIEW", "VIEW", false));
    wareHouseAndStockyardList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_STORANDSTOK_ADD", "ADD", false));
    wareHouseAndStockyardList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_STORANDSTOK_DELETE", "DEACTIVATE", false));
    wareHouseAndStockyardList.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_STORANDSTOK_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(wareHouseAndStockyardList);

    var leaveType = createModule("Leave Types", true, "projleavetype", "Y");
    leaveType.permissionTOs.push(createPermission("PRJ_PRJLIB_LEAVETYPEATNDCE_VIEW", "VIEW", false));
    leaveType.permissionTOs.push(createPermission("PRJ_PRJLIB_LEAVETYPEATNDCE_ADD", "ADD", false));
    leaveType.permissionTOs.push(createPermission("PRJ_PRJLIB_LEAVETYPEATNDCE_DELETE", "DEACTIVATE", false));
    leaveType.permissionTOs.push(createPermission("PRJ_PRJLIB_LEAVETYPEATNDCE_ACTIVATE", "ACTIVATE", false));

    centralLibraryModule.childModules.push(leaveType);

    var plantServiceHistory = createModule("Plant Service History", true, "plantservicehistory", "Y");
    plantServiceHistory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTSERVICEHISTORY_VIEW", "VIEW", false));
    plantServiceHistory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTSERVICEHISTORY_ADD", "ADD", false));
    plantServiceHistory.permissionTOs.push(createPermission("ENTRPRSE_CENTLIB_PLANTSERVICEHISTORY_DELETE", "DEACTIVATE", false));

    centralLibraryModule.childModules.push(plantServiceHistory);

    var financeCodes = createModule("Finance Codes", true, "", "Y");
    financeCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_VIEW", "VIEW", false));

      let province_code = createModule("Country & Province Codes", false, "", "Y");
      province_code.permissionTOs.push(createPermission("FINANCE_PROVINCE_VIEW", "VIEW", false));
      financeCodes.childModules.push(province_code);

      let finance_center = createModule("Finance Center", false, "", "Y");
      finance_center.permissionTOs.push(createPermission("FINANCE_FINANCE_VIEW", "VIEW", false));
      financeCodes.childModules.push(finance_center);

      let profit_center = createModule("Profit Center", false, "", "Y");
      profit_center.permissionTOs.push(createPermission("FINANCE_PROFIT_VIEW", "VIEW", false));
      financeCodes.childModules.push(profit_center);

    /*
    var unitOfPayRates = createModule("Unit of Pay Rates", true, "unitofpayrates", "Y");
    unitOfPayRates.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_UNITOFPAYRATE_VIEW", "VIEW", false));
    unitOfPayRates.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_UNITOFPAYRATE_ADD", "ADD", false));
    unitOfPayRates.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_UNITOFPAYRATE_DELETE", "DEACTIVATE", false));
    financeCodes.childModules.push(unitOfPayRates);

    var taxCountryProvisions = createModule("Tax Country Provisions", true, "taxcountryprovison", "Y");
    taxCountryProvisions.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCOUNTRYPROVISION_VIEW", "VIEW", false));
    taxCountryProvisions.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCOUNTRYPROVISION_ADD", "ADD", false));
    taxCountryProvisions.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCOUNTRYPROVISION_DELETE", "DEACTIVATE", false));
    financeCodes.childModules.push(taxCountryProvisions);

    var taxCodeTypes = createModule("Tax Code Types", true, "taxcodes", "Y");
    taxCodeTypes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODETYPES_VIEW", "VIEW", false));
    taxCodeTypes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODETYPES_ADD", "ADD", false));
    taxCodeTypes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODETYPES_DELETE", "DEACTIVATE", false));
    financeCodes.childModules.push(taxCodeTypes);

    var taxTypes = createModule("Tax Types", true, "taxtypes", "Y");
    taxTypes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXTYPES_VIEW", "VIEW", false));
    taxTypes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXTYPES_ADD", "ADD", false));
    taxTypes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXTYPES_DELETE", "DEACTIVATE", false));
    financeCodes.childModules.push(taxTypes);

    var taxCodes = createModule("Tax Codes", true, "taxcodetypes", "Y");
    taxCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODES_VIEW", "VIEW", false));
    taxCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODES_ADD", "ADD", false));
    taxCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODES_DELETE", "DEACTIVATE", false));
    financeCodes.childModules.push(taxCodes);

    var taxCodes = createModule("Tax Codes", true, "taxcodetypes", "Y");
    taxCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODES_VIEW", "VIEW", false));
    taxCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODES_ADD", "ADD", false));
    taxCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_FINANCECODE_TAXCODES_DELETE", "DEACTIVATE", false));
    financeCodes.childModules.push(taxCodes);
    */

    centralLibraryModule.childModules.push(financeCodes);

    var countryCodes = createModule("Country Codes", true, "projleavetype", "Y");
    countryCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_COUNTRYCODE_VIEW", "VIEW", false));
    countryCodes.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_COUNTRYCODE_ADD", "ADD", false));
    centralLibraryModule.childModules.push(countryCodes);

    var immovableAssets = createModule("Immovable Assets Category", true, "assetscategory", "Y");
    immovableAssets.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_IMMOVASSETCATEG_VIEW", "VIEW", false));
    immovableAssets.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_IMMOVASSETCATEG_ADD", "ADD", false));
    immovableAssets.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_IMMOVASSETCATEG_DELETE", "DEACTIVATE", false));

    centralLibraryModule.childModules.push(immovableAssets);

    var assetsMaintenance = createModule("Assets Maintenance", true, "assetsmaintenancecategory", "Y");
    assetsMaintenance.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_ASSETMAINTEN_VIEW", "VIEW", false));
    assetsMaintenance.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_ASSETMAINTEN_ADD", "ADD", false));
    assetsMaintenance.permissionTOs.push(createPermission("ENTERPRISE_CENTLIB_ASSETMAINTEN_DELETE", "DEACTIVATE", false));

    centralLibraryModule.childModules.push(assetsMaintenance);

    enterpriseModule.childModules.push(centralLibraryModule);

    var tools = createModule("Tools", true, "", "Y");
    tools.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_VIEW", "VIEW", false));
    /* tools.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_ADD", "ADD", false));
    tools.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_DELETE", "DEACTIVATE", false)); */

    var calendar = createModule("Calanders", true, "calendar", "Y");
    calendar.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_CALANDERS_VIEW", "VIEW", false));
    calendar.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_CALANDERS_ADD", "ADD", false));
    calendar.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_CALANDERS_DELETE", "DEACTIVATE", false));
    calendar.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_CALANDERS_APPROVE", "APPROVE", false));

    tools.childModules.push(calendar);

    var resourceCurves = createModule("Resource Curves", true, "resourcecurves", "Y");
    resourceCurves.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_RESOURCECURVES_VIEW", "VIEW", false));
    resourceCurves.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_RESOURCECURVES_ADD", "ADD", false));
    resourceCurves.permissionTOs.push(createPermission("ENTRPRSE_TOOLS_RESOURCECURVES_DELETE", "DEACTIVATE", false));

    tools.childModules.push(resourceCurves);

    enterpriseModule.childModules.push(tools);

    return enterpriseModule;
  }

  function createModule(moduleName, expand, moduleURLValue, moduleType) {
    return {
      "moduleName": moduleName,
      "expand": !expand,
      "moduleURLValue": moduleURLValue,
      "moduleType": moduleType,
      "childModules": [],
      "permissionTOs": []
    };
  }

  function createPermission(permissionKey, actionName, permission) {
    return {
      "permissionKey": permissionKey,
      "actionName": actionName,
      "permission": permission
    }
  }

  function getAdminModule() {
    var adminModule = createModule("Admin", true, "", 'Y');
    adminModule.permissionTOs.push(createPermission("ADMIN_VIEW", "VIEW", false));


    var clientRegList = createModule("Client Registration", true, "clientreg", "Y");
    clientRegList.permissionTOs.push(createPermission("ADMIN_CLIENTREG_VIEW", "VIEW", false));
    clientRegList.permissionTOs.push(createPermission("ADMIN_CLIENTREG_ADD", "ADD", false));
    clientRegList.permissionTOs.push(createPermission("ADMIN_CLIENTREG_DELETE", "DEACTIVATE", false));
    adminModule.childModules.push(clientRegList);

    var userList = createModule("User List", true, "users", "Y");
    userList.permissionTOs.push(createPermission("ADMIN_USRLIST_VIEW", "VIEW", false));
    userList.permissionTOs.push(createPermission("ADMIN_USRLIST_ADD", "ADD", false));
    userList.permissionTOs.push(createPermission("ADMIN_USRLIST_DELETE", "DEACTIVATE", false));
    userList.permissionTOs.push(createPermission("ADMIN_USRLIST_ASSIGN", "ASSIGN", false));
    adminModule.childModules.push(userList);

    var userProfileandPrivilages = createModule("User Profile and Privilages", true, "roles", "Y");
    userProfileandPrivilages.permissionTOs.push(createPermission("ADMIN_USRPRFLANDPRVLG_VIEW", "VIEW", false));
    userProfileandPrivilages.permissionTOs.push(createPermission("ADMIN_USRPRFLANDPRVLG_ADD", "ADD", false));
    userProfileandPrivilages.permissionTOs.push(createPermission("ADMIN_USRPRFLANDPRVLG_DELETE", "DEACTIVATE", false));
    userProfileandPrivilages.permissionTOs.push(createPermission("ADMIN_USRPRFLANDPRVLG_ASSIGN", "ASSIGN", false));
    adminModule.childModules.push(userProfileandPrivilages);



    var emailSettings = createModule("Email Setting", true, "emailSettings", "Y");
    emailSettings.permissionTOs.push(createPermission("ADMIN_EMAILSTG_VIEW", "VIEW", false));
    emailSettings.permissionTOs.push(createPermission("ADMIN_EMAILSTG_ADD", "ADD", false));
    emailSettings.permissionTOs.push(createPermission("ADMIN_EMAILSTG_DELETE", "DEACTIVATE", false));
    adminModule.childModules.push(emailSettings);

    var auditAndTrail = createModule("Audit Trail Records", true, "auditAndTrail", "Y");
    auditAndTrail.permissionTOs.push(createPermission("ADMIN_AUDITANDTRAIL_VIEW", "VIEW", false));
    adminModule.childModules.push(auditAndTrail);

    return adminModule;
  }

function getFinanceModule() {
  var financeModule = createModule("Finance", true, "", 'Y');
  financeModule.permissionTOs.push(createPermission("FINANCE_VIEW", "VIEW", false));

    let revenue = createModule("Revenue (Receivables)", true, "", "Y");
    revenue.permissionTOs.push(createPermission("FINANCE_REVENUE_VIEW", "VIEW", false));
    financeModule.childModules.push(revenue);

      let revenue_projects = createModule("Projects", true, "", "Y");
      revenue_projects.permissionTOs.push(createPermission("FINANCE_REVENUE_PROJECTS_VIEW", "VIEW", false));
      revenue.childModules.push(revenue_projects);

        let projects_claim_rate = createModule("Generate Progress Claim(Schedule of Rates Basis)", false, "", "Y");
        projects_claim_rate.permissionTOs.push(createPermission("FINANCE_REVENUE_PROJECTS_SCHEDULE_VIEW", "VIEW", false));
        revenue_projects.childModules.push(projects_claim_rate);

        let projects_claim_cost = createModule("Generate Progress Claim(Cost plus % Basis)", false, "", "Y");
        projects_claim_cost.permissionTOs.push(createPermission("FINANCE_REVENUE_PROJECTS_COST_VIEW", "VIEW", false));
        revenue_projects.childModules.push(projects_claim_cost);

        let projects_claim_milestone = createModule("Generate Progress Claim(Payment Milestones basis)", false, "", "Y");
        projects_claim_milestone.permissionTOs.push(createPermission("FINANCE_REVENUE_PROJECTS_MILESTONE_VIEW", "VIEW", false));
        revenue_projects.childModules.push(projects_claim_milestone);

        let projects_claim_status = createModule("Progress Claims", false, "", "Y");
        projects_claim_status.permissionTOs.push(createPermission("FINANCE_REVENUE_PROJECTS_STATUS_VIEW", "VIEW", false));
        revenue_projects.childModules.push(projects_claim_status);

        let projects_invoice = createModule("Project Owner Invoices", false, "", "Y");
        projects_invoice.permissionTOs.push(createPermission("FINANCE_REVENUE_PROJECTS_INVOICE_VIEW", "VIEW", false));
        revenue_projects.childModules.push(projects_invoice);

      let revenue_immovable_assets = createModule("Immovable Assets", true, "", "Y");
      revenue_immovable_assets.permissionTOs.push(createPermission("FINANCE_REVENUE_IMMOVABLE_VIEW", "VIEW", false));
      revenue.childModules.push(revenue_immovable_assets);

        let ia_rent = createModule("Revenue receipts from Rents", false, "", "Y");
        ia_rent.permissionTOs.push(createPermission("FINANCE_REVENUE_IMMOVABLE_RENT_VIEW", "VIEW", false));
        revenue_immovable_assets.childModules.push(ia_rent);

        let ia_parking = createModule("Revenue through Toll or Parking", false, "", "Y");
        ia_parking.permissionTOs.push(createPermission("FINANCE_REVENUE_IMMOVABLE_PARKING_VIEW", "VIEW", false));
        revenue_immovable_assets.childModules.push(ia_parking);

        let ia_sales = createModule("Revenue  through  asset Sales", false, "", "Y");
        ia_sales.permissionTOs.push(createPermission("FINANCE_REVENUE_IMMOVABLE_SALES_VIEW", "VIEW", false));
        revenue_immovable_assets.childModules.push(ia_sales);

        let ia_service = createModule("Revenue through Services Sales", false, "", "Y");
        ia_service.permissionTOs.push(createPermission("FINANCE_REVENUE_IMMOVABLE_SERVICE_SALES_VIEW", "VIEW", false));
        revenue_immovable_assets.childModules.push(ia_service);

      let revenue_goods = createModule("Goods and Services", false, "", "Y");
      revenue_goods.permissionTOs.push(createPermission("FINANCE_REVENUE_GOODS_VIEW", "VIEW", false));
      revenue.childModules.push(revenue_goods);

      let revenue_plants = createModule("Plant and Equipment", false, "", "Y");
      revenue_plants.permissionTOs.push(createPermission("FINANCE_REVENUE_PLANTS_VIEW", "VIEW", false));
      revenue.childModules.push(revenue_plants);

      let revenue_receipts = createModule("Actual Revenue Receipts", false, "", "Y");
      revenue_receipts.permissionTOs.push(createPermission("FINANCE_REVENUE_RECEIPTS_VIEW", "VIEW", false));
      revenue.childModules.push(revenue_receipts);

    let cost = createModule("Cost (Payables)", true, "", "Y");
    cost.permissionTOs.push(createPermission("FINANCE_COST_VIEW", "VIEW", false));
    financeModule.childModules.push(cost);

      let cost_employee = createModule("Employee", true, "", "Y");
      cost_employee.permissionTOs.push(createPermission("FINANCE_COST_EMPLOYEE_VIEW", "VIEW", false));
      cost.childModules.push(cost_employee);

        let cost_employee_calculationsSheet = createModule("Pay Calculations sheet", false, "", "Y");
        cost_employee_calculationsSheet.permissionTOs.push(createPermission("FINANCE_COST_EMPLOYEE_CALCULATIONS5SHEET_VIEW", "VIEW", false));
        cost_employee.childModules.push(cost_employee_calculationsSheet);

        let cost_employee_payrollRun = createModule("Pay Roll Run", false, "", "Y");
        cost_employee_payrollRun.permissionTOs.push(createPermission("FINANCE_COST_EMPLOYEE_PAYROLL5RUN_VIEW", "VIEW", false));
        cost_employee.childModules.push(cost_employee_payrollRun);

        let cost_employee_calculationsSheetRecords = createModule("Pay Calculations sheet Records", false, "", "Y");
        cost_employee_calculationsSheetRecords.permissionTOs.push(createPermission("FINANCE_COST_EMPLOYEE_CALCULATIONS5SHEET5RECORDS_VIEW", "VIEW", false));
        cost_employee.childModules.push(cost_employee_calculationsSheetRecords);

        let cost_employee_payrollRunRecords = createModule("Pay Roll Run Records", false, "", "Y");
        cost_employee_payrollRunRecords.permissionTOs.push(createPermission("FINANCE_COST_EMPLOYEE_PAYROLL5RUN5RECORDS_VIEW", "VIEW", false));
        cost_employee.childModules.push(cost_employee_payrollRunRecords);

        let cost_employee_providentFund = createModule("Super Fund/Provident Fund - Credit run", false, "", "Y");
        cost_employee_providentFund.permissionTOs.push(createPermission("FINANCE_COST_EMPLOYEE_PROVIDENT5FUND_VIEW", "VIEW", false));
        cost_employee.childModules.push(cost_employee_providentFund);

      let cost_plants = createModule("Plants & Equipments", true, "", "Y");
      cost_plants.permissionTOs.push(createPermission("FINANCE_COST_PLANTS_VIEW", "VIEW", false));
      cost.childModules.push(cost_plants);

      let cost_materials = createModule("Materials and Store Items", true, "", "Y");
      cost_materials.permissionTOs.push(createPermission("FINANCE_COST_MATERIALS_VIEW", "VIEW", false));
      cost.childModules.push(cost_materials);

      let cost_vendor = createModule("Sub Contractor or Vendors", true, "", "Y");
      cost_vendor.permissionTOs.push(createPermission("FINANCE_COST_VENDOR_VIEW", "VIEW", false));
      cost.childModules.push(cost_vendor);

        let cost_vendor_invoice = createModule("Invoices from Vendors/Subcontractors", false, "", "Y");
        cost_vendor_invoice.permissionTOs.push(createPermission("FINANCE_COST_VENDOR_INVOICE_VIEW", "VIEW", false));
        cost_vendor.childModules.push(cost_vendor_invoice);

        let cost_vendor_payments = createModule("Vendors/Sub Contractor Payment - Credit Run", false, "", "Y");
        cost_vendor_payments.permissionTOs.push(createPermission("FINANCE_COST_VENDOR_PAYMENTS_VIEW", "VIEW", false));
        cost_vendor.childModules.push(cost_vendor_payments);

      let cost_fixedAssets = createModule("Fixed Assets", true, "", "Y");
      cost_fixedAssets.permissionTOs.push(createPermission("FINANCE_COST_FIXED5ASSET_VIEW", "VIEW", false));
      cost.childModules.push(cost_fixedAssets);

        let cost_fixedAssets_acquisition = createModule("Acquisition costs", false, "", "Y");
        cost_fixedAssets_acquisition.permissionTOs.push(createPermission("FINANCE_COST_FIXED5ASSET_ACQUISITION_VIEW", "VIEW", false));
        cost_fixedAssets.childModules.push(cost_fixedAssets_acquisition);

        let cost_fixedAssets_mortage = createModule("Mortage Payments", false, "", "Y");
        cost_fixedAssets_mortage.permissionTOs.push(createPermission("FINANCE_COST_FIXED5ASSET_MORTAGE_VIEW", "VIEW", false));
        cost_fixedAssets.childModules.push(cost_fixedAssets_mortage);

      let cost_bank = createModule("Bank Finance", true, "", "Y");
      cost_bank.permissionTOs.push(createPermission("FINANCE_COST_BANK_VIEW", "VIEW", false));
      cost.childModules.push(cost_bank);

        let cost_bank_odList = createModule("Over Draft List", false, "", "Y");
        cost_bank_odList.permissionTOs.push(createPermission("FINANCE_COST_BANK_OD5LIST_VIEW", "VIEW", false));
        cost_bank.childModules.push(cost_bank_odList);

        let cost_bank_odPayment = createModule("Over Draft - Payment Schedule", false, "", "Y");
        cost_bank_odPayment.permissionTOs.push(createPermission("FINANCE_COST_BANK_OD5PAYMENT_VIEW", "VIEW", false));
        cost_bank.childModules.push(cost_bank_odPayment);

      let cost_taxPayments = createModule("Tax Payments", true, "", "Y");
      cost_taxPayments.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_VIEW", "VIEW", false));
      cost.childModules.push(cost_taxPayments);

        let cost_taxPayments_allowances = createModule("Employee Pay & Allowances Tax - Credit Run", false, "", "Y");
        cost_taxPayments_allowances.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_ALLOWANCES_VIEW", "VIEW", false));
        cost_taxPayments.childModules.push(cost_taxPayments_allowances);

        let cost_taxPayments_superfund = createModule("Employee superfund Tax - Credit Run", false, "", "Y");
        cost_taxPayments_superfund.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_SUPERFUND_VIEW", "VIEW", false));
        cost_taxPayments.childModules.push(cost_taxPayments_superfund);

        let cost_taxPayments_payroll = createModule("Employee Payroll Tax - Credit Run", false, "", "Y");
        cost_taxPayments_payroll.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_PAYROLL_VIEW", "VIEW", false));
        cost_taxPayments.childModules.push(cost_taxPayments_payroll);

        let cost_taxPayments_supplier = createModule("Supplier & Sub Contractor Tax - Credit Run", false, "", "Y");
        cost_taxPayments_supplier.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_SUPPLIER_VIEW", "VIEW", false));
        cost_taxPayments.childModules.push(cost_taxPayments_supplier);

        let cost_taxPayments_gst = createModule("GST Payments Credit Run", false, "", "Y");
        cost_taxPayments_gst.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_GST_VIEW", "VIEW", false));
        cost_taxPayments.childModules.push(cost_taxPayments_gst);

        let cost_taxPayments_companyTax = createModule("Company Tax", false, "", "Y");
        cost_taxPayments_companyTax.permissionTOs.push(createPermission("FINANCE_COST_TAX5PAYMENTS_COMPANY5TAX_VIEW", "VIEW", false));
        cost_taxPayments.childModules.push(cost_taxPayments_companyTax);

      let cost_insurance = createModule("Insurance", true, "", "Y");
      cost_insurance.permissionTOs.push(createPermission("FINANCE_COST_INSURANCE_VIEW", "VIEW", false));
      cost.childModules.push(cost_insurance);

        let cost_insurance_policy = createModule("Insurance Policy List", false, "", "Y");
        cost_insurance_policy.permissionTOs.push(createPermission("FINANCE_COST_INSURANCE_POLICY_VIEW", "VIEW", false));
        cost_insurance.childModules.push(cost_insurance_policy);

        let cost_insurance_payment = createModule("Insurance Payments - Credit Run", false, "", "Y");
        cost_insurance_payment.permissionTOs.push(createPermission("FINANCE_COST_INSURANCE_PAYMENT_VIEW", "VIEW", false));
        cost_insurance.childModules.push(cost_insurance_payment);

    let assets = createModule("Assets Value", true, "", "Y");
    assets.permissionTOs.push(createPermission("FINANCE_ASSET_VIEW", "VIEW", false));
    financeModule.childModules.push(assets);

      let assets_plants = createModule("Plants - Book Value", false, "", "Y");
      assets_plants.permissionTOs.push(createPermission("FINANCE_ASSET_PLANTS_VIEW", "VIEW", false));
      assets.childModules.push(assets_plants);

      let assets_materials = createModule("Materials - Stock  Value", false, "", "Y");
      assets_materials.permissionTOs.push(createPermission("FINANCE_ASSET_MATERIALS_VIEW", "VIEW", false));
      assets.childModules.push(assets_materials);

      let assets_fa = createModule("Fixed Assets market value", false, "", "Y");
      assets_fa.permissionTOs.push(createPermission("FINANCE_ASSET_FA_VIEW", "VIEW", false));
      assets.childModules.push(assets_fa);

    let profit = createModule("Profit", true, "", "Y");
    profit.permissionTOs.push(createPermission("FINANCE_PROFIT_VIEW", "VIEW", false));
    financeModule.childModules.push(profit);

      let profit_profits = createModule("Profit Center wise Profits (Revenue-Expenses)", false, "", "Y");
      profit_profits.permissionTOs.push(createPermission("FINANCE_PROFIT_PROFITS_VIEW", "VIEW", false));
      profit.childModules.push(profit_profits);

      let profit_asbuilt = createModule("Profit Center Wise Costs - Based on Asbuilt Records", false, "", "Y");
      profit_asbuilt.permissionTOs.push(createPermission("FINANCE_PROFIT_ASBUILT_VIEW", "VIEW", false));
      profit.childModules.push(profit_asbuilt);

      let profit_finance = createModule("Profit Center Wise Costs - Based on Finance Records", false, "", "Y");
      profit_finance.permissionTOs.push(createPermission("FINANCE_PROFIT_FINANCE_VIEW", "VIEW", false));
      profit.childModules.push(profit_finance);

      let profit_revenue = createModule("Profit Center wise Revenue Status", false, "", "Y");
      profit_revenue.permissionTOs.push(createPermission("FINANCE_PROFIT_REVENUE_VIEW", "VIEW", false));
      profit.childModules.push(profit_revenue);

  return financeModule;
}

function getDashboardModule() {
  var dashboardModule = createModule("Dashboard", true, "", 'Y');
  dashboardModule.permissionTOs.push(createPermission("DASHBOARD_VIEW", "VIEW", false));

    let master = createModule("Master Dashboard", false, "", "Y");
    master.permissionTOs.push(createPermission("DASHBOARD_MASTER_VIEW", "VIEW", false));
    dashboardModule.childModules.push(master);

    let performance = createModule("Performance", true, "", "Y");
    performance.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_VIEW", "VIEW", false));
    dashboardModule.childModules.push(performance);

      let performance_progress = createModule("Progress & Performance", false, "", "Y");
      performance_progress.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_PROGRESS_VIEW", "VIEW", false));
      performance.childModules.push(performance_progress);

      let performance_costPerformance = createModule("Cost Schedule & Performance", false, "", "Y");
      performance_costPerformance.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_COST5PERFORMANCE_VIEW", "VIEW", false));
      performance.childModules.push(performance_costPerformance);

      let performance_costUnits = createModule("Cost & Schedule(Units)", false, "", "Y");
      performance_costUnits.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_COST5UNITS_VIEW", "VIEW", false));
      performance.childModules.push(performance_costUnits);

      let performance_costPercent = createModule("Cost and Schedule(%)", false, "", "Y");
      performance_costPercent.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_COST5PERCENT_VIEW", "VIEW", false));
      performance.childModules.push(performance_costPercent);

      let performance_costIndices = createModule("Cost Schedule & Performance Indices", false, "", "Y");
      performance_costIndices.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_COST5INDICES_VIEW", "VIEW", false));
      performance.childModules.push(performance_costIndices);

      let performance_index = createModule("Performance Index", false, "", "Y");
      performance_index.permissionTOs.push(createPermission("DASHBOARD_PERFORMANCE_INDEX_VIEW", "VIEW", false));
      performance.childModules.push(performance_index);

    let progress = createModule("Progress", true, "", "Y");
    progress.permissionTOs.push(createPermission("DASHBOARD_PROGRESS_VIEW", "VIEW", false));
    dashboardModule.childModules.push(progress);

      let progress_plansActual = createModule("Plans Vs Actual", false, "", "Y");
      progress_plansActual.permissionTOs.push(createPermission("DASHBOARD_PROGRESS_PLANS5ACTUAL_VIEW", "VIEW", false));
      progress.childModules.push(progress_plansActual);

      let progress_sCurveCost = createModule("S Curve - Cost", false, "", "Y");
      progress_sCurveCost.permissionTOs.push(createPermission("DASHBOARD_PROGRESS_S5CURVE5COST_VIEW", "VIEW", false));
      progress.childModules.push(progress_sCurveCost);

      let progress_sCurveLabour = createModule("S Curve - Labour", false, "", "Y");
      progress_sCurveLabour.permissionTOs.push(createPermission("DASHBOARD_PROGRESS_S5CURVE5LABOUR_VIEW", "VIEW", false));
      progress.childModules.push(progress_sCurveLabour);

    let time = createModule("Time", true, "", "Y");
    time.permissionTOs.push(createPermission("DASHBOARD_TIME_VIEW", "VIEW", false));
    dashboardModule.childModules.push(time);

      let time_bubble = createModule("Cost & Schedule(Bubble Chart)", false, "", "Y");
      time_bubble.permissionTOs.push(createPermission("DASHBOARD_TIME_BUBBLE_VIEW", "VIEW", false));
      time.childModules.push(time_bubble);

      let time_percentage = createModule("Current Progress(%)", false, "", "Y");
      time_percentage.permissionTOs.push(createPermission("DASHBOARD_TIME_PERCENTAGE_VIEW", "VIEW", false));
      time.childModules.push(time_percentage);

      let time_units = createModule("Cost & Schedule(Units)", false, "", "Y");
      time_units.permissionTOs.push(createPermission("DASHBOARD_TIME_UNITS_VIEW", "VIEW", false));
      time.childModules.push(time_units);

      let time_gantt = createModule("Projects Gantt Chart", false, "", "Y");
      time_gantt.permissionTOs.push(createPermission("DASHBOARD_TIME_GANTT_VIEW", "VIEW", false));
      time.childModules.push(time_gantt);

    let budget = createModule("Budget", true, "", "Y");
    budget.permissionTOs.push(createPermission("DASHBOARD_BUDGET_VIEW", "VIEW", false));
    dashboardModule.childModules.push(budget);

      let budget_country = createModule("Budget by Country", false, "", "Y");
      budget_country.permissionTOs.push(createPermission("DASHBOARD_BUDGET_COUNTRY_VIEW", "VIEW", false));
      budget.childModules.push(budget_country);

      let budget_province = createModule("Budget by Province", false, "", "Y");
      budget_province.permissionTOs.push(createPermission("DASHBOARD_BUDGET_PROVINCE_VIEW", "VIEW", false));
      budget.childModules.push(budget_province);

      let budget_project = createModule("Budget by Project", false, "", "Y");
      budget_project.permissionTOs.push(createPermission("DASHBOARD_BUDGET_PROJECT_VIEW", "VIEW", false));
      budget.childModules.push(budget_project);

      let budget_pm = createModule("Budget by Project Manager", false, "", "Y");
      budget_pm.permissionTOs.push(createPermission("DASHBOARD_BUDGET_PM_VIEW", "VIEW", false));
      budget.childModules.push(budget_pm);

    let cost = createModule("Cost", true, "", "Y");
    cost.permissionTOs.push(createPermission("DASHBOARD_COST_VIEW", "VIEW", false));
    dashboardModule.childModules.push(cost);

      let cost_health = createModule("Cost Health Check", false, "", "Y");
      cost_health.permissionTOs.push(createPermission("DASHBOARD_COST_HEALTH_VIEW", "VIEW", false));
      cost.childModules.push(cost_health);

      let cost_bubble = createModule("CV and SV for Cost(Bubble Chart)", false, "", "Y");
      cost_bubble.permissionTOs.push(createPermission("DASHBOARD_COST_BUBBLE_VIEW", "VIEW", false));
      cost.childModules.push(cost_bubble);

      let cost_estimate = createModule("Original Vs Estimate(Cost)", false, "", "Y");
      cost_estimate.permissionTOs.push(createPermission("DASHBOARD_COST_ESTIMATE_VIEW", "VIEW", false));
      cost.childModules.push(cost_estimate);

      let cost_earned = createModule("Plan Vs Actual Vs Earned(Cost)", false, "", "Y");
      cost_earned.permissionTOs.push(createPermission("DASHBOARD_COST_EARNED_VIEW", "VIEW", false));
      cost.childModules.push(cost_earned);

      let cost_worksheet = createModule("Cost Work Sheet", false, "", "Y");
      cost_worksheet.permissionTOs.push(createPermission("DASHBOARD_COST_WORKSHEET_VIEW", "VIEW", false));
      cost.childModules.push(cost_worksheet);

    let labour = createModule("Labour", true, "", "Y");
    labour.permissionTOs.push(createPermission("DASHBOARD_LABOUR_VIEW", "VIEW", false));
    dashboardModule.childModules.push(labour);

      let labour_health = createModule("Labour Health Check", false, "", "Y");
      labour_health.permissionTOs.push(createPermission("DASHBOARD_LABOUR_HEALTH_VIEW", "VIEW", false));
      labour.childModules.push(labour_health);

      let labour_bubble = createModule("CV and SV for Labour (Bubble Chart)", false, "", "Y");
      labour_bubble.permissionTOs.push(createPermission("DASHBOARD_LABOUR_BUBBLE_VIEW", "VIEW", false));
      labour.childModules.push(labour_bubble);

      let labour_estimate = createModule("Original Vs Estimate at Completion - Manhours", false, "", "Y");
      labour_estimate.permissionTOs.push(createPermission("DASHBOARD_LABOUR_ESTIMATE_VIEW", "VIEW", false));
      labour.childModules.push(labour_estimate);

      let labour_manhours = createModule("Plan Vs Actual Vs Earned - Direct Manhours", false, "", "Y");
      labour_manhours.permissionTOs.push(createPermission("DASHBOARD_LABOUR_MANHOURS_VIEW", "VIEW", false));
      labour.childModules.push(labour_manhours);
      
      let labour_productivity_rates_major_items_of_work = createModule("Productivity Rates - Major  Items of Work", false, "", "Y");
      labour_productivity_rates_major_items_of_work.permissionTOs.push(createPermission("PRODUCTIVITY_RATES_MAJOR_ITEMS_OF_WORK", "VIEW", false));
      labour.childModules.push(labour_productivity_rates_major_items_of_work);
      
      let labour_periodical_productivity_rates_major__items_of_work = createModule("Periodical Productivity Rates - Major Items of Work", false, "", "Y");
      labour_periodical_productivity_rates_major__items_of_work.permissionTOs.push(createPermission("PERIODICAL_PRODUCTIVITY_RATES_MAJOR__ITEMS_OF_WORK", "VIEW", false));
      labour.childModules.push(labour_periodical_productivity_rates_major__items_of_work);

    let actualCost = createModule("Actual Cost", true, "", "Y");
    actualCost.permissionTOs.push(createPermission("DASHBOARD_ACTUAL5COST_VIEW", "VIEW", false));
    dashboardModule.childModules.push(actualCost);

      let actualCost_country = createModule("Actual Cost by Country", false, "", "Y");
      actualCost_country.permissionTOs.push(createPermission("DASHBOARD_ACTUAL5COST_COUNTRY_VIEW", "VIEW", false));
      actualCost.childModules.push(actualCost_country);

      let actualCost_province = createModule("Actual Cost by Province", false, "", "Y");
      actualCost_province.permissionTOs.push(createPermission("DASHBOARD_ACTUAL5COST_PROVINCE_VIEW", "VIEW", false));
      actualCost.childModules.push(actualCost_province);

      let actualCost_project = createModule("Actual Cost by Project", false, "", "Y");
      actualCost_project.permissionTOs.push(createPermission("DASHBOARD_ACTUAL5COST_PROJECT_VIEW", "VIEW", false));
      actualCost.childModules.push(actualCost_project);

      let actualCost_pm = createModule("Actual Cost by Project Manager", false, "", "Y");
      actualCost_pm.permissionTOs.push(createPermission("DASHBOARD_ACTUAL5COST_PM_VIEW", "VIEW", false));
      actualCost.childModules.push(actualCost_pm);

    let earnedValue = createModule("Earned Value", true, "", "Y");
    earnedValue.permissionTOs.push(createPermission("DASHBOARD_EARNED5VALUE_VIEW", "VIEW", false));
    dashboardModule.childModules.push(earnedValue);

      let earnedValue_country = createModule("Earned Value by Country", false, "", "Y");
      earnedValue_country.permissionTOs.push(createPermission("DASHBOARD_EARNED5VALUE_COUNTRY_VIEW", "VIEW", false));
      earnedValue.childModules.push(earnedValue_country);

      let earnedValue_province = createModule("Earned Value by Province", false, "", "Y");
      earnedValue_province.permissionTOs.push(createPermission("DASHBOARD_EARNED5VALUE_PROVINCE_VIEW", "VIEW", false));
      earnedValue.childModules.push(earnedValue_province);

      let earnedValue_project = createModule("Earned Value by Project", false, "", "Y");
      earnedValue_project.permissionTOs.push(createPermission("DASHBOARD_EARNED5VALUE_PROJECT_VIEW", "VIEW", false));
      earnedValue.childModules.push(earnedValue_project);

      let earnedValue_pm = createModule("Earned Value by Project Manager", false, "", "Y");
      earnedValue_pm.permissionTOs.push(createPermission("DASHBOARD_EARNED5VALUE_PM_VIEW", "VIEW", false));
      earnedValue.childModules.push(earnedValue_pm);

    let remainingBudget = createModule("Remaining Budget", true, "", "Y");
    remainingBudget.permissionTOs.push(createPermission("DASHBOARD_REMAINING5BUDGET_VIEW", "VIEW", false));
    dashboardModule.childModules.push(remainingBudget);

      let remainingBudget_country = createModule("Remaining Budget by Country", false, "", "Y");
      remainingBudget_country.permissionTOs.push(createPermission("DASHBOARD_REMAINING5BUDGET_COUNTRY_VIEW", "VIEW", false));
      remainingBudget.childModules.push(remainingBudget_country);

      let remainingBudget_province = createModule("Remaining Budget by Province", false, "", "Y");
      remainingBudget_province.permissionTOs.push(createPermission("DASHBOARD_REMAINING5BUDGET_PROVINCE_VIEW", "VIEW", false));
      remainingBudget.childModules.push(remainingBudget_province);

      let remainingBudget_project = createModule("Remaining Budget by Project", false, "", "Y");
      remainingBudget_project.permissionTOs.push(createPermission("DASHBOARD_REMAINING5BUDGET_PROJECT_VIEW", "VIEW", false));
      remainingBudget.childModules.push(remainingBudget_project);

      let remainingBudget_pm = createModule("Remaining Budget by Project Manager", false, "", "Y");
      remainingBudget_pm.permissionTOs.push(createPermission("DASHBOARD_REMAINING5BUDGET_PM_VIEW", "VIEW", false));
      remainingBudget.childModules.push(remainingBudget_pm);

    let toComplete = createModule("Estimate to Complete", true, "", "Y");
    toComplete.permissionTOs.push(createPermission("DASHBOARD_TO5COMPLETE_VIEW", "VIEW", false));
    dashboardModule.childModules.push(toComplete);

      let toComplete_country = createModule("Estimate to Complete by Country", false, "", "Y");
      toComplete_country.permissionTOs.push(createPermission("DASHBOARD_TO5COMPLETE_COUNTRY_VIEW", "VIEW", false));
      toComplete.childModules.push(toComplete_country);

      let toComplete_province = createModule("Estimate to Complete by Province", false, "", "Y");
      toComplete_province.permissionTOs.push(createPermission("DASHBOARD_TO5COMPLETE_PROVINCE_VIEW", "VIEW", false));
      toComplete.childModules.push(toComplete_province);

      let toComplete_project = createModule("Estimate to Complete by Project", false, "", "Y");
      toComplete_project.permissionTOs.push(createPermission("DASHBOARD_TO5COMPLETE_PROJECT_VIEW", "VIEW", false));
      toComplete.childModules.push(toComplete_project);

      let toComplete_pm = createModule("Estimate to Complete by Project Manager", false, "", "Y");
      toComplete_pm.permissionTOs.push(createPermission("DASHBOARD_TO5COMPLETE_PM_VIEW", "VIEW", false));
      toComplete.childModules.push(toComplete_pm);

    let atComplete = createModule("Estimate at Completion", true, "", "Y");
    atComplete.permissionTOs.push(createPermission("DASHBOARD_AT5COMPLETE_VIEW", "VIEW", false));
    dashboardModule.childModules.push(atComplete);

      let atComplete_country = createModule("Estimate at Completion by Country", false, "", "Y");
      atComplete_country.permissionTOs.push(createPermission("DASHBOARD_AT5COMPLETE_COUNTRY_VIEW", "VIEW", false));
      atComplete.childModules.push(atComplete_country);

      let atComplete_province = createModule("Estimate at Completion by Province", false, "", "Y");
      atComplete_province.permissionTOs.push(createPermission("DASHBOARD_AT5COMPLETE_PROVINCE_VIEW", "VIEW", false));
      atComplete.childModules.push(atComplete_province);

      let atComplete_project = createModule("Estimate at Completion by Project", false, "", "Y");
      atComplete_project.permissionTOs.push(createPermission("DASHBOARD_AT5COMPLETE_PROJECT_VIEW", "VIEW", false));
      atComplete.childModules.push(atComplete_project);

      let atComplete_pm = createModule("Estimate at Completion by Project Manager", false, "", "Y");
      atComplete_pm.permissionTOs.push(createPermission("DASHBOARD_AT5COMPLETE_PM_VIEW", "VIEW", false));
      atComplete.childModules.push(atComplete_pm);

  return dashboardModule;
}

function getReportModule() {
  var reportModule = createModule("Reports", true, "", 'Y');
  reportModule.permissionTOs.push(createPermission("REPORTS_VIEW", "VIEW", false));
  
  let procurement = createModule("Procurement", true, "", "Y");
  procurement.permissionTOs.push(createPermission("REPORTS_PROCUREMENT_VIEW", "VIEW", false));
  reportModule.childModules.push(procurement);

    let precontracts_phase = createModule("Precontracts Phase", true, "", "Y");
    precontracts_phase.permissionTOs.push(createPermission("REPORTS_PRECONTRACTS_PHASE_VIEW", "VIEW", false));
    procurement.childModules.push(precontracts_phase);

      let precontracts_list_status_count = createModule("Precontracts list - Status Count", false, "", "Y");
      precontracts_list_status_count.permissionTOs.push(createPermission("REPORTS_PRECONTRACTS_LIST_STATUS_COUNT_VIEW", "VIEW", false));
      precontracts_phase.childModules.push(precontracts_list_status_count);

      let precontracts_estimated_budget = createModule("Precontracts - Estimated Budget", false, "", "Y");
      precontracts_estimated_budget.permissionTOs.push(createPermission("REPORTS_PRECONTRACTS_ESTIMATED_BUDGET_VIEW", "VIEW", false));
      precontracts_phase.childModules.push(precontracts_estimated_budget);

      let precontracts_employee_task_wise_count = createModule("Precontracts - Employee Task wise Count", false, "", "Y");
      precontracts_employee_task_wise_count.permissionTOs.push(createPermission("REPORTS_PRECONTRACTS_EMPLOYEE_TASK_WISE_COUNT_VIEW", "VIEW", false));
      precontracts_phase.childModules.push(precontracts_employee_task_wise_count);

      let precontracts_cost_code_wise_status = createModule("Precontracts - Cost Code Wise Status", false, "", "Y");
      precontracts_cost_code_wise_status.permissionTOs.push(createPermission("REPORTS_PRECONTRACTS_COST_CODE_WISE_STATUS_VIEW", "VIEW", false));
      precontracts_phase.childModules.push(precontracts_cost_code_wise_status);

    let post_contracts_phase = createModule("Post Contracts Phase", true, "", "Y");
    post_contracts_phase.permissionTOs.push(createPermission("REPORTS_POST_CONTRACTS_PHASE_VIEW", "VIEW", false));
    procurement.childModules.push(post_contracts_phase);

      let po_subcontracts_list = createModule("PO / Subcontracts - List", false, "", "Y");
      po_subcontracts_list.permissionTOs.push(createPermission("REPORTS_PO_SUBCONTRACTS_LIST_VIEW", "VIEW", false));
      post_contracts_phase.childModules.push(po_subcontracts_list);

      let po_contract_budget_cost = createModule("PO / Sub Contract Budget Cost", false, "", "Y");
      po_contract_budget_cost.permissionTOs.push(createPermission("REPORTS_PO_CONTRACT_BUDGET_COST_VIEW", "VIEW", false));
      post_contracts_phase.childModules.push(po_contract_budget_cost);

      let vendor_invoices_status = createModule("Vendor Invoices Status", false, "", "Y");
      vendor_invoices_status.permissionTOs.push(createPermission("REPORTS_VENDOR_INVOICES_STATUS_VIEW", "VIEW", false));
      post_contracts_phase.childModules.push(vendor_invoices_status);

    let attendance = createModule("Attendance Records", true, "", "Y");
    attendance.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_VIEW", "VIEW", false));
    reportModule.childModules.push(attendance);

      let attendance_dailyEmployee = createModule("Daily Employee Records", false, "", "Y");
      attendance_dailyEmployee.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_DAILY5EMPLOYEE_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_dailyEmployee);

      let attendance_dailyCrew = createModule("Crew Wise Daily Deployment List", false, "", "Y");
      attendance_dailyCrew.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_DAILY5CREW_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_dailyCrew);

      let attendance_dailyPlant = createModule("Daily Plant Records", false, "", "Y");
      attendance_dailyPlant.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_DAILY5PLANT_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_dailyPlant);

      let attendance_monthlyEmployee = createModule("Monthly Employee Records", false, "", "Y");
      attendance_monthlyEmployee.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_MONTHLY5EMPLOYEE_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_monthlyEmployee);

      let attendance_monthlyPlant = createModule("Monthly Plant Records", false, "", "Y");
      attendance_monthlyPlant.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_MONTHLY5PLANT_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_monthlyPlant);

      let attendance_status = createModule("Employee Daily Resources Status", false, "", "Y");
      attendance_status.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_STATUS_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_status);

      let attendance_plantStatus = createModule("Plant Daily Resources Status", false, "", "Y");
      attendance_plantStatus.permissionTOs.push(createPermission("REPORTS_ATTENDANCE_PLANT5STATUS_VIEW", "VIEW", false));
      attendance.childModules.push(attendance_plantStatus);

    let workDiary = createModule("Work Diary", true, "", "Y");
    workDiary.permissionTOs.push(createPermission("REPORTS_WORKDIARY_VIEW", "VIEW", false));
    reportModule.childModules.push(workDiary);

      let workDiary_search = createModule("Work Diary Search & Its Records", false, "", "Y");
      workDiary_search.permissionTOs.push(createPermission("REPORTS_WORKDIARY_SEARCH_VIEW", "VIEW", false));
      workDiary.childModules.push(workDiary_search);

      let workDiary_submit = createModule("Submission & Approval Status", false, "", "Y");
      workDiary_submit.permissionTOs.push(createPermission("REPORTS_WORKDIARY_SUBMIT_VIEW", "VIEW", false));
      workDiary.childModules.push(workDiary_submit);

    let timesheets = createModule("Timesheets", true, "", "Y");
    timesheets.permissionTOs.push(createPermission("REPORTS_TIMESHEETS_VIEW", "VIEW", false));
    reportModule.childModules.push(timesheets);

      let timesheets_search = createModule("Time Sheet Search & Its Records", false, "", "Y");
      timesheets_search.permissionTOs.push(createPermission("REPORTS_TIMESHEETS_SEARCH_VIEW", "VIEW", false));
      timesheets.childModules.push(timesheets_search);

      let timesheets_submit = createModule("Submission & Approval Status", false, "", "Y");
      timesheets_submit.permissionTOs.push(createPermission("REPORTS_TIMESHEETS_SUBMIT_VIEW", "VIEW", false));
      timesheets.childModules.push(timesheets_submit);

    let manpower = createModule("Manpower", true, "", "Y");
    manpower.permissionTOs.push(createPermission("REPORTS_MANPOWER_VIEW", "VIEW", false));
    reportModule.childModules.push(manpower);

      let manpower_periodical = createModule("Periodical- Actual Hours", false, "", "Y");
      manpower_periodical.permissionTOs.push(createPermission("REPORTS_MANPOWER_PERIODICAL_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_periodical);

      let manpower_datewise = createModule("Date wise - Actual Hours", false, "", "Y");
      manpower_datewise.permissionTOs.push(createPermission("REPORTS_MANPOWER_DATEWISE_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_datewise);

      let manpower_costcode = createModule("Cost Code Wise - Daily Deployment List", false, "", "Y");
      manpower_costcode.permissionTOs.push(createPermission("REPORTS_MANPOWER_COSTCODE_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_costcode);

      let manpower_actualStandard = createModule("Actual Vs Standard Hours", false, "", "Y");
      manpower_actualStandard.permissionTOs.push(createPermission("REPORTS_MANPOWER_ACTUAL5STANDARD_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_actualStandard);

      let manpower_idle = createModule("Idle Hours Records", false, "", "Y");
      manpower_idle.permissionTOs.push(createPermission("REPORTS_MANPOWER_IDLE_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_idle);

      let manpower_gender = createModule("Gender Statistics", false, "", "Y");
      manpower_gender.permissionTOs.push(createPermission("REPORTS_MANPOWER_GENDER_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_gender);

      let manpower_mobilization = createModule("Periodical - Mobilisation Statistics", false, "", "Y");
      manpower_mobilization.permissionTOs.push(createPermission("REPORTS_MANPOWER_MOBILIZATION_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_mobilization);

      let manpower_currentEmployee = createModule("Current Employee List", false, "", "Y");
      manpower_currentEmployee.permissionTOs.push(createPermission("REPORTS_MANPOWER_CURRENT5EMPLOYEE_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_currentEmployee);

      let manpower_earned = createModule("Plan Vs Actual Vs Earned Hours", false, "", "Y");
      manpower_earned.permissionTOs.push(createPermission("REPORTS_MANPOWER_EARNED_VIEW", "VIEW", false));
      manpower.childModules.push(manpower_earned);

      let manpower_s_curve_manpower = createModule("Progress S Curve - Manpower", false, "", "Y");
      manpower_s_curve_manpower.permissionTOs.push(createPermission("REPORTS_MANPOWER_S_CURVE_MANPOWER", "VIEW", false));
      manpower.childModules.push(manpower_s_curve_manpower);

      let manpower_productivity_analysis = createModule("Manpower - Productivity Analysis", false, "", "Y");
      manpower_productivity_analysis.permissionTOs.push(createPermission("REPORTS_MANPOWER_PRODUCTIVITY_ANALYSIS", "VIEW", false));
      manpower.childModules.push(manpower_productivity_analysis);
      
    let plant = createModule("Plant", true, "", "Y");
    plant.permissionTOs.push(createPermission("REPORTS_PLANT_VIEW", "VIEW", false));
    reportModule.childModules.push(plant);
    
      /*let plant_masterlist = createModule("Plant Master List", false, "", "Y");
      plant_periodical.permissionTOs.push(createPermission("REPORTS_PLANT_MASTERLIST_VIEW", "VIEW", false));
      plant.childModules.push(plant_masterlist);*/
      
      let plant_periodical = createModule("Periodical- Actual Hours", false, "", "Y");
      plant_periodical.permissionTOs.push(createPermission("REPORTS_PLANT_PERIODICAL_VIEW", "VIEW", false));
      plant.childModules.push(plant_periodical);

      let plant_datewise = createModule("Date wise - Actual Hours", false, "", "Y");
      plant_datewise.permissionTOs.push(createPermission("REPORTS_PLANT_DATEWISE_VIEW", "VIEW", false));
      plant.childModules.push(plant_datewise);

      let plant_costcode = createModule("Cost Code Wise - Daily Deployment List", false, "", "Y");
      plant_costcode.permissionTOs.push(createPermission("REPORTS_PLANT_COSTCODE_VIEW", "VIEW", false));
      plant.childModules.push(plant_costcode);

      let plant_idle = createModule("Plant Idle Hours", false, "", "Y");
      plant_idle.permissionTOs.push(createPermission("REPORTS_PLANT_IDLE_VIEW", "VIEW", false));
      plant.childModules.push(plant_idle);

      let plant_actual = createModule("Actual Vs Standard Hours", false, "", "Y");
      plant_actual.permissionTOs.push(createPermission("REPORTS_PLANT_ACTUAL_VIEW", "VIEW", false));
      plant.childModules.push(plant_actual);

      let plant_current = createModule("Current Plant List", false, "", "Y");
      plant_current.permissionTOs.push(createPermission("REPORTS_PLANT_CURRENT_VIEW", "VIEW", false));
      plant.childModules.push(plant_current);

    let materials = createModule("Materials", true, "", "Y");
    materials.permissionTOs.push(createPermission("REPORTS_MATERIALS_VIEW", "VIEW", false));
    reportModule.childModules.push(materials);

      let materials_delivery = createModule("Delivery and Supply Details", false, "", "Y");
      materials_delivery.permissionTOs.push(createPermission("REPORTS_MATERIALS_DELIVERY_VIEW", "VIEW", false));
      materials.childModules.push(materials_delivery);

      let materials_daily = createModule("Daily Issue Records", false, "", "Y");
      materials_daily.permissionTOs.push(createPermission("REPORTS_MATERIALS_DAILY_VIEW", "VIEW", false));
      materials.childModules.push(materials_daily);

      let materials_store = createModule("Store Stock Balance in Transit", false, "", "Y");
      materials_store.permissionTOs.push(createPermission("REPORTS_MATERIALS_STORE_VIEW", "VIEW", false));
      materials.childModules.push(materials_store);

      let materials_stock = createModule("Stock Piles - Stock Balance", false, "", "Y");
      materials_stock.permissionTOs.push(createPermission("REPORTS_MATERIALS_STOCK_VIEW", "VIEW", false));
      materials.childModules.push(materials_stock);

      let materials_periodical = createModule("Periodical Consumption", false, "", "Y");
      materials_periodical.permissionTOs.push(createPermission("REPORTS_MATERIALS_PERIODICAL_VIEW", "VIEW", false));
      materials.childModules.push(materials_periodical);

      let materials_datewise = createModule("Date wise Consumption", false, "", "Y");
      materials_datewise.permissionTOs.push(createPermission("REPORTS_MATERIALS_DATEWISE_VIEW", "VIEW", false));
      materials.childModules.push(materials_datewise);

      let materials_inventory = createModule("Inventory Records", false, "", "Y");
      materials_inventory.permissionTOs.push(createPermission("REPORTS_MATERIALS_INVENTORY_VIEW", "VIEW", false));
      materials.childModules.push(materials_inventory);

    let progress = createModule("Progress", true, "", "Y");
    progress.permissionTOs.push(createPermission("REPORTS_PROGRESS_VIEW", "VIEW", false));
    reportModule.childModules.push(progress);

      let progress_periodic = createModule("Periodical Progress", false, "", "Y");
      progress_periodic.permissionTOs.push(createPermission("REPORTS_PROGRESS_PERIODICAL_VIEW", "VIEW", false));
      progress.childModules.push(progress_periodic);

      let progress_datewise = createModule("Date Wise Progress", false, "", "Y");
      progress_datewise.permissionTOs.push(createPermission("REPORTS_PROGRESS_DATEWISE_VIEW", "VIEW", false));
      progress.childModules.push(progress_datewise);

      let progress_progress = createModule("Progress - Plan Vs Actual", false, "", "Y");
      progress_progress.permissionTOs.push(createPermission("REPORTS_PROGRESS_PROGRESS_VIEW", "VIEW", false));
      progress.childModules.push(progress_progress);

      let progress_claim = createModule("Periodical Progress Claim", true, "", "Y");
      progress_claim.permissionTOs.push(createPermission("REPORTS_PROGRESS_CLAIM_VIEW", "VIEW", false));
      progress.childModules.push(progress_claim);

        let progress_claim_actcost = createModule("Progress Claim Based on Actual Cost Plus % Basis", false, "", "Y");
        progress_claim_actcost.permissionTOs.push(createPermission("REPORTS_PROGRESS_CLAIM_VIEW_ACTUAL_COST", "VIEW", false));
        progress_claim.childModules.push(progress_claim_actcost);

        let progress_claim_sor = createModule("Progress Claim Based on Schedule of Rates", false, "", "Y");
        progress_claim_sor.permissionTOs.push(createPermission("REPORTS_PROGRESS_CLAIM_VIEW_SOR", "VIEW", false));
        progress_claim.childModules.push(progress_claim_sor);

        let progress_claim_cont_mile = createModule("Progress Measure Based on Contract Milestones", false, "", "Y");
        progress_claim_cont_mile.permissionTOs.push(createPermission("REPORTS_PROGRESS_CLAIM_VIEW_CONT_MILE", "VIEW", false));
        progress_claim.childModules.push(progress_claim_cont_mile);

    let cost = createModule("Cost and Performance", true, "", "Y");
    cost.permissionTOs.push(createPermission("REPORTS_COST_VIEW", "VIEW", false));
    reportModule.childModules.push(cost);

      let cost_datewise = createModule("Date wise - Actual Cost Details", false, "", "Y");
      cost_datewise.permissionTOs.push(createPermission("REPORTS_COST_DATEWISE_VIEW", "VIEW", false));
      cost.childModules.push(cost_datewise);

      let cost_periodical = createModule("Periodical - Plan Vs Actual Vs Earned Value", false, "", "Y");
      cost_periodical.permissionTOs.push(createPermission("REPORTS_COST_PERIODICAL_VIEW", "VIEW", false));
      cost.childModules.push(cost_periodical);

      let cost_actual = createModule("Date Wise - Plan Vs Actual Vs Earned Value", false, "", "Y");
      cost_actual.permissionTOs.push(createPermission("REPORTS_COST_ACTUAL_VIEW", "VIEW", false));
      cost.childModules.push(cost_actual);

      let cost_cost = createModule("Cost & Schedule Variance", false, "", "Y");
      cost_cost.permissionTOs.push(createPermission("REPORTS_COST_COST_VIEW", "VIEW", false));
      cost.childModules.push(cost_cost);

      let cost_index = createModule("Cost & Schedule Performance Index", false, "", "Y");
      cost_index.permissionTOs.push(createPermission("REPORTS_COST_INDEX_VIEW", "VIEW", false));
      cost.childModules.push(cost_index);

      let cost_budget = createModule("Item Wise Budget Provision", false, "", "Y");
      cost_budget.permissionTOs.push(createPermission("REPORTS_COST_BUDGET_VIEW", "VIEW", false));
      cost.childModules.push(cost_budget);

      let cost_item = createModule("Item Wise - Plan Vs Actual Cost", false, "", "Y");
      cost_item.permissionTOs.push(createPermission("REPORTS_COST_ITEM_VIEW", "VIEW", false));
      cost.childModules.push(cost_item);

      let cost_scurve = createModule("Progress S Curve - Cost", false, "", "Y");
      cost_scurve.permissionTOs.push(createPermission("REPORTS_COST_SCURVE_VIEW", "VIEW", false));
      cost.childModules.push(cost_scurve);

    let finance = createModule("Finance", true, "", "Y");
    finance.permissionTOs.push(createPermission("REPORTS_FINANCE_VIEW", "VIEW", false));
    reportModule.childModules.push(finance);

      let finance_budget = createModule("Budget", true, "", "Y");
      finance_budget.permissionTOs.push(createPermission("REPORTS_FINANCE_BUDGET_VIEW", "VIEW", false));
      finance.childModules.push(finance_budget);

        let finance_budget_orig = createModule("Original or Revised", false, "", "Y");
        finance_budget_orig.permissionTOs.push(createPermission("REPORTS_FINANCE_BUDGET_ORIG_VIEW", "VIEW", false));
        finance_budget.childModules.push(finance_budget_orig);

        let finance_budget_eoc = createModule("Estimate to Complete", false, "", "Y");
        finance_budget_eoc.permissionTOs.push(createPermission("REPORTS_FINANCE_BUDGET_EOC_VIEW", "VIEW", false));
        finance_budget.childModules.push(finance_budget_eoc);

        let finance_budget_eac = createModule("Estimate At Completion", false, "", "Y");
        finance_budget_eac.permissionTOs.push(createPermission("REPORTS_FINANCE_BUDGET_EAC_VIEW", "VIEW", false));
        finance_budget.childModules.push(finance_budget_eac);

        let finance_budget_forecast = createModule("Forecast", false, "", "Y");
        finance_budget_forecast.permissionTOs.push(createPermission("REPORTS_FINANCE_BUDGET_FORECAST_VIEW", "VIEW", false));
        finance_budget.childModules.push(finance_budget_forecast);

      let finance_cost = createModule("Costs", true, "", "Y");
      finance_cost.permissionTOs.push(createPermission("REPORTS_FINANCE_COST_VIEW", "VIEW", false));
      finance.childModules.push(finance_cost);

        let finance_cost_vendor = createModule("Vendor Payment Status", false, "", "Y");
        finance_cost_vendor.permissionTOs.push(createPermission("REPORTS_FINANCE_COST_VENDOR_VIEW", "VIEW", false));
        finance_cost.childModules.push(finance_cost_vendor);

        let finance_cost_asbuilt = createModule("Actual Cost - Based on Asbuilt Records", false, "", "Y");
        finance_cost_asbuilt.permissionTOs.push(createPermission("REPORTS_FINANCE_COST_ASBUILT_VIEW", "VIEW", false));
        finance_cost.childModules.push(finance_cost_asbuilt);

        let finance_cost_finance = createModule("Actual Cost - Based on Finance Records", false, "", "Y");
        finance_cost_finance.permissionTOs.push(createPermission("REPORTS_FINANCE_COST_FINANCE_VIEW", "VIEW", false));
        finance_cost.childModules.push(finance_cost_finance);

      let finance_revenue = createModule("Revenue", true, "", "Y");
      finance_revenue.permissionTOs.push(createPermission("REPORTS_FINANCE_REVENUE_VIEW", "VIEW", false));
      finance.childModules.push(finance_revenue);

        let finance_revenue_pmeasure = createModule("Progress Measure Status", false, "", "Y");
        finance_revenue_pmeasure.permissionTOs.push(createPermission("REPORTS_FINANCE_REVENUE_PMEASURE_VIEW", "VIEW", false));
        finance_revenue.childModules.push(finance_revenue_pmeasure);

        let finance_revenue_forecast = createModule("Revenue Forecast", false, "", "Y");
        finance_revenue_forecast.permissionTOs.push(createPermission("REPORTS_FINANCE_REVENUE_FORECAST_VIEW", "VIEW", false));
        finance_revenue.childModules.push(finance_revenue_forecast);

        let finance_revenue_actual = createModule("Revenue - Actual", false, "", "Y");
        finance_revenue_actual.permissionTOs.push(createPermission("REPORTS_FINANCE_REVENUE_ACTUAL_VIEW", "VIEW", false));
        finance_revenue.childModules.push(finance_revenue_actual);

      let finance_pstatus = createModule("Profit Status", true, "", "Y");
      finance_pstatus.permissionTOs.push(createPermission("REPORTS_FINANCE_PSTATUS_VIEW", "VIEW", false));
      finance.childModules.push(finance_pstatus);

        let finance_pstatus_asbuilt = createModule("Profit Status Based on Asbuilt Records", false, "", "Y");
        finance_pstatus_asbuilt.permissionTOs.push(createPermission("REPORTS_FINANCE_PSTATUS_ASBUILT_VIEW", "VIEW", false));
        finance_pstatus.childModules.push(finance_pstatus_asbuilt);

        let finance_pstatus_finance = createModule("Profit Status Based on Finance Records", false, "", "Y");
        finance_pstatus_finance.permissionTOs.push(createPermission("REPORTS_FINANCE_PSTATUS_FINANCE_VIEW", "VIEW", false));
        finance_pstatus.childModules.push(finance_pstatus_finance);

  return reportModule;
}

  function getMyAccount() {
    var myAccount = createModule("My Account", true, "", 'Y');
    myAccount.permissionTOs.push(createPermission("MY_ACCOUNT_VIEW", "VIEW", false));

    var changePassword = createModule("Change Password", true, "changepawd", "Y");
    changePassword.permissionTOs.push(createPermission("CHANGE_PASSWORD_VIEW", "VIEW", false));
    myAccount.childModules.push(changePassword);

    var logOut = createModule("Logout", true, "logout", "Y");
    logOut.permissionTOs.push(createPermission("LOGOUT_VIEW", "VIEW", false));
    myAccount.childModules.push(logOut);
    return myAccount;

  }

  function getProjectModule() {
    var projectModule = createModule("Projects", true, "", 'Y');
    projectModule.permissionTOs.push(createPermission("PROJECT_VIEW", "VIEW", false));


    var projectList = createModule("Project List", true, "epsproject", "Y");
    projectList.permissionTOs.push(createPermission("ENTRPRSE_EPSPRJCT_VIEW", "VIEW", false));
    projectList.permissionTOs.push(createPermission("ENTRPRSE_EPSPRJCT_ADD", "ADD", false));
    projectList.permissionTOs.push(createPermission("ENTRPRSE_EPSPRJCT_DELETE", "DEACTIVATE", false));

    projectModule.childModules.push(projectList);

    var projectSettings = createModule("Project Settings", true, "", "Y");
    projectSettings.permissionTOs.push(createPermission("PRJ_PRJSTG_VIEW", "VIEW", false));

    var generalValues = createModule("General Values", true, "projsettings", "Y");
    generalValues.permissionTOs.push(createPermission("PRJ_PRJSTG_GNRLVLU_VIEW", "VIEW", false));
    generalValues.permissionTOs.push(createPermission("PRJ_PRJSTG_GNRLVLU_ADD", "ADD", false));
    projectSettings.childModules.push(generalValues);
	
	//new requirement tab - 1
	var scheduleofEstimates = createModule("Schedule of Estimates", true, "", "Y");
	scheduleofEstimates.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFEstimatesTab", "VIEW", false));
	
	var schofEstiNormalTime = createModule("Normal Time", true, "schofEstimates", "Y");
	schofEstiNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFEstimatesNORMALTIME", "VIEW", false));
	schofEstiNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFEstimates_ADD", "ADD", false));
	scheduleofEstimates.childModules.push(schofEstiNormalTime);
	
	var schofEstiAdditionalTime = createModule("Additional Time", true, "schofEstimates", "Y");
	schofEstiAdditionalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFEstimatesADDITIONALTIME", "VIEW", false));
	schofEstiAdditionalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFEstimates_APPROVE", "APPROVE", false));
	scheduleofEstimates.childModules.push(schofEstiAdditionalTime);
	
	projectSettings.childModules.push(scheduleofEstimates);
	
	//new requirement tab - 2
	var schofRates = createModule("Schedule of Rates", true, "", "Y");
	schofRates.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFRatesTab", "VIEW", false));
	
	var schofRatesNormalTime = createModule("Normal Time", true, "schofRate", "Y");
	schofRatesNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFRatesNORMALTIME", "VIEW", false));
	schofRatesNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFRates_ADD", "ADD", false));
	schofRates.childModules.push(schofRatesNormalTime);
	
	var schofRatesAdditionalTime = createModule("Additional Time", true, "schofRate", "Y");
	schofRatesAdditionalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFRatesADDITIONALTIME", "VIEW", false));
	schofRatesAdditionalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_SchOFRates_APPROVE", "APPROVE", false));
	schofRates.childModules.push(schofRatesAdditionalTime);
	
	projectSettings.childModules.push(schofRates);
	
	//new requirement tab - 3
	var resourceBudget = createModule("Resources Budget", true, "", "Y");
	resourceBudget.permissionTOs.push(createPermission("PRJ_PRJSTG_ResBudgetTab", "VIEW", false));
	
	var resourceBudgetNormalTime = createModule("Normal Time", true, "resourceBudgets", "Y");
	resourceBudgetNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_ResBudgetTabNORMALTIME", "VIEW", false));
	resourceBudgetNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_ResBudgetTab_ADD", "ADD", false));
	resourceBudget.childModules.push(resourceBudgetNormalTime);
	
	var resourceBudgetAdditionalTime = createModule("Additional Time", true, "resourceBudgets", "Y");
	resourceBudgetAdditionalTime.permissionTOs.push(createPermission("PRJ_PTRSTG_ResBudgetTabADDITIONALTIME", "VIEW", false));
	resourceBudgetAdditionalTime.permissionTOs.push(createPermission("PRJ_PTRSTG_ResBudgetTab_APPROVE", "APPROVE", false));
	resourceBudget.childModules.push(resourceBudgetAdditionalTime);
	
	projectSettings.childModules.push(resourceBudget);
	
    var employeeAttendance = createModule("Employee Attendance", true, "", "Y");
    employeeAttendance.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPATNDCERCDSTG_VIEW", "VIEW", false));

    var normalTimemodule = createModule("Normal Time", true, "empattendence", "Y");
    normalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPNORMALTIME_VIEW", "VIEW", false));
    normalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_ATNDCERCDSTG_ADD", "ADD", false));
    employeeAttendance.childModules.push(normalTimemodule);

    var additionalTimemodule = createModule("Additional Time", true, "empattendence", "Y");
    additionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPADDITIONALTIME_VIEW", "VIEW", false));
    additionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_ATNDCERCDSTG_APPROVE", "APPROVE", false));
    employeeAttendance.childModules.push(additionalTimemodule);

    projectSettings.childModules.push(employeeAttendance);
    //
    var plantAttendance = createModule("Plant Attendance", true, "", "Y");
    plantAttendance.permissionTOs.push(createPermission("PRJ_PRJSTG_PLTATNDCERCDSTG_VIEW", "VIEW", false));

    var plantNormalTimemodule = createModule("Normal Time", true, "plantattend", "Y");
    plantNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTNORMALTIME_VIEW", "VIEW", false));
    plantNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTATNDCERCDSTG_ADD", "ADD", false));
    plantAttendance.childModules.push(plantNormalTimemodule);

    var plantAdditionalTimemodule = createModule("Additional Time", true, "plantattend", "Y");
    plantAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTADDITIONALTIME_VIEW", "VIEW", false));
    plantAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTATNDCERCDSTG_APPROVE", "APPROVE", false));
    plantAttendance.childModules.push(plantAdditionalTimemodule);

    projectSettings.childModules.push(plantAttendance);
    //

    var workDiaryAttendance = createModule("Work Diary", true, "", "Y");
    workDiaryAttendance.permissionTOs.push(createPermission("PRJ_PRJSTG_WRKDIRYSTG_VIEW", "VIEW", false));

    var workDiaryNormalTimemodule = createModule("Normal Time", true, "workdairyattend", "Y");
    workDiaryNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_WRKDIRYSTGNORMALTIME_VIEW", "VIEW", false));
    workDiaryNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_WRKDIRYSTG_ADD", "ADD", false));
    workDiaryAttendance.childModules.push(workDiaryNormalTimemodule);

    var workDiaryAdditionalTimemodule = createModule("Additional Time", true, "workdairyattend", "Y");
    workDiaryAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_WRKDIRYSTGADDITIONALTIME_VIEW", "VIEW", false));
    workDiaryAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_WRKDIRYSTG_APPROVE", "APPROVE", false));
    workDiaryAttendance.childModules.push(workDiaryAdditionalTimemodule);

    projectSettings.childModules.push(workDiaryAttendance);

    var timeSheetAttendance = createModule("Time Sheet", true, "", "Y");
    timeSheetAttendance.permissionTOs.push(createPermission("PRJ_PRJSTG_TIMSHETSTG_VIEW", "VIEW", false));

    var timesheetNormalTimemodule = createModule("Normal Time", true, "timesheetattend", "Y");
    timesheetNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_TIMESHEETSTGNORMALTIME_VIEW", "VIEW", false));
    timesheetNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_TIMSHETSTG_ADD", "ADD", false));
    timeSheetAttendance.childModules.push(timesheetNormalTimemodule);

    var timesheetAdditionalTimemodule = createModule("Additional Time", true, "timesheetattend", "Y");
    timesheetAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_TIMESHEETSTGADDITIONALTIME_VIEW", "VIEW", false));
    timesheetAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_TIMSHETSTG_APPROVE", "APPROVE", false));
    timeSheetAttendance.childModules.push(timesheetAdditionalTimemodule);

    projectSettings.childModules.push(timeSheetAttendance);

    var procurementAttendance = createModule("Procurement", true, "", "Y");
    procurementAttendance.permissionTOs.push(createPermission("PRJ_PRJSTG_PROCURSTG_VIEW", "VIEW", false));

    var procurementNormalTimemodule = createModule("Normal Time", true, "procurement", "Y");
    procurementNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROCURSTGNORMALTIME_VIEW", "VIEW", false));
    procurementNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROCURSTG_ADD", "ADD", false));
    procurementAttendance.childModules.push(procurementNormalTimemodule);

    var procurementAdditionalTimemodule = createModule("Additional Time", true, "procurement", "Y");
    procurementAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROCURSTGADDITIONALTIME_VIEW", "VIEW", false));
    procurementAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROCURSTG_APPROVE", "APPROVE", false));
    procurementAttendance.childModules.push(procurementAdditionalTimemodule);

    projectSettings.childModules.push(procurementAttendance);

    var employeeTransfer = createModule("Employee Transfer", true, "", "Y");
    employeeTransfer.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPTRSFR_VIEW", "VIEW", false));

    var employeeTransferNormalTimemodule = createModule("Normal Time", true, "procurement", "Y");
    employeeTransferNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPTRANSTGNORMALTIME_VIEW", "VIEW", false));
    employeeTransferNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPTRSFR_ADD", "ADD", false));
    employeeTransfer.childModules.push(employeeTransferNormalTimemodule);

    var employeeTransferAdditionalTimemodule = createModule("Additional Time", true, "procurement", "Y");
    employeeTransferAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPTRANSTGADDITIONALTIME_VIEW", "VIEW", false));
    employeeTransferAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_EMPTRSFR_APPROVE", "APPROVE", false));
    employeeTransfer.childModules.push(employeeTransferAdditionalTimemodule);

    projectSettings.childModules.push(employeeTransfer);


    var plantTransfer = createModule("Plant Transfer", true, "", "Y");
    plantTransfer.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTTRSFR_VIEW", "VIEW", false));

    var plantTransferNormalTimemodule = createModule("Normal Time", true, "planttransfer", "Y");
    plantTransferNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTTRANSTGNORMALTIME_VIEW", "VIEW", false));
    plantTransferNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTTRSFR_ADD", "ADD", false));
    plantTransfer.childModules.push(plantTransferNormalTimemodule);

    var plantTransferAdditionalTimemodule = createModule("Additional Time", true, "planttransfer", "Y");
    plantTransferAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTTRANSTGADDITIONALTIME_VIEW", "VIEW", false));
    plantTransferAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PLANTTRSFR_APPROVE", "APPROVE", false));
    plantTransfer.childModules.push(plantTransferAdditionalTimemodule);

    projectSettings.childModules.push(plantTransfer);

    var materialTransfer = createModule("Material Transfer", true, "", "Y");
    materialTransfer.permissionTOs.push(createPermission("PRJ_PRJSTG_MATRLTRSFR_VIEW", "VIEW", false));

    var materialTransferNormalTimemodule = createModule("Normal Time", true, "planttransfer", "Y");
    materialTransferNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_MATERIALTRANSTGNORMALTIME_VIEW", "VIEW", false));
    materialTransferNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_MATRLTRSFR_ADD", "ADD", false));
    materialTransfer.childModules.push(materialTransferNormalTimemodule);

    var materialTransferAdditionalTimemodule = createModule("Additional Time", true, "planttransfer", "Y");
    materialTransferAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_MATERIALTRANSTGADDITIONALTIME_VIEW", "VIEW", false));
    materialTransferAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_MATRLTRSFR_APPROVE", "APPROVE", false));
    materialTransfer.childModules.push(materialTransferAdditionalTimemodule);

    projectSettings.childModules.push(materialTransfer);


    var estimatetoComplete = createModule("Estimate to Complete", true, "projsettings", "Y");
    estimatetoComplete.permissionTOs.push(createPermission("PRJ_PRJSTG_ESTTOCMPLT_VIEW", "VIEW", false));
    estimatetoComplete.permissionTOs.push(createPermission("PRJ_PRJSTG_ESTTOCMPLT_ADD", "ADD", false));
    projectSettings.childModules.push(estimatetoComplete);


    var performanceThreshold = createModule("Performance Threshold", true, "projsettings", "Y");
    performanceThreshold.permissionTOs.push(createPermission("PRJ_PRJSTG_PRFMTHRSHLD_VIEW", "VIEW", false));
    performanceThreshold.permissionTOs.push(createPermission("PRJ_PRJSTG_PRFMTHRSHLD_ADD", "ADD", false));
    projectSettings.childModules.push(performanceThreshold);

	var changeOrder = createModule("Change Orders", true, "", "Y");
	changeOrder.permissionTOs.push(createPermission("PRJ_PRJSTG_CHANGEORDER_TAB", "VIEW", false));
	
	var changeOrderNormalTime = createModule("Normal Time", true, "changeOrders", "Y");
	changeOrderNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_CHANGEORDERNORMALTIME_VIEW", "VIEW", false));
	changeOrderNormalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_CHANGEORDERNORMALTIME_ADD", "ADD", false));
	changeOrder.childModules.push(changeOrderNormalTime);
	
	var changeOrderAdditionalTime = createModule("Additional Time", true, "changeOrders", "Y");
	changeOrderAdditionalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_CHANGEORDERADDITIONALTIME_VIEW", "VIEW", false));
	changeOrderAdditionalTime.permissionTOs.push(createPermission("PRJ_PRJSTG_CHANGEORDERADDITIONALTIME_ADD", "ADD", false));
	changeOrder.childModules.push(changeOrderAdditionalTime);
	
	projectSettings.childModules.push(changeOrder);
	
    var reports = createModule("Reports", true, "prjstgreports", "Y");
    reports.permissionTOs.push(createPermission("PRJ_PRJSTG_REPORT_VIEW", "VIEW", false));
    reports.permissionTOs.push(createPermission("PRJ_PRJSTG_REPORT_ADD", "ADD", false));
    projectSettings.childModules.push(reports);


    var progressClaim = createModule("Progress Claims", true, "", "Y");
    progressClaim.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAM_VIEW", "VIEW", false));

    var progressClaimNormalTimemodule = createModule("Normal Time", true, "progresclaim", "Y");
    progressClaimNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAMNORMALTIME_VIEW", "VIEW", false));
    progressClaimNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAM_ADD", "ADD", false));
    progressClaim.childModules.push(progressClaimNormalTimemodule);

    var progressClaimPeriodCyclemodule = createModule("Period Cycle", true, "progresclaim", "Y");
    progressClaimPeriodCyclemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAMPERIODCYCLE_VIEW", "VIEW", false));
    progressClaimPeriodCyclemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAMPERIODCYCLE_ADD", "ADD", false));
    progressClaim.childModules.push(progressClaimPeriodCyclemodule);


    var progressClaimAdditionalTimemodule = createModule("Additional Time", true, "progresclaim", "Y");
    progressClaimAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAMADDITIONALTIME_VIEW", "VIEW", false));
    progressClaimAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_PROGRESCLIAMADDTIONAL_ADD", "ADD", false));
    progressClaim.childModules.push(progressClaimAdditionalTimemodule);

    projectSettings.childModules.push(progressClaim);


    var payrollCycle = createModule("Payroll Cycle", true, "payrollcycle", "Y");
    payrollCycle.permissionTOs.push(createPermission("PRJ_PRJSTG_PAYROLLCYCLE_VIEW", "VIEW", false));
    payrollCycle.permissionTOs.push(createPermission("PRJ_PRJSTG_PAYROLLCYCLE_ADD", "ADD", false));
    projectSettings.childModules.push(payrollCycle);

    var leaveApproval = createModule("Leave Approval", true, "", "Y");
    leaveApproval.permissionTOs.push(createPermission("PRJ_PRJSTG_LEAVEAPPROVAL_VIEW", "VIEW", false));

    var leaveApprovalNormalTimemodule = createModule("Normal Time", true, "leaveapproval", "Y");
    leaveApprovalNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_LEAVEAPPROVAL_NORMALTIME_VIEW", "VIEW", false));
    leaveApprovalNormalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_LEAVEAPPROVAL_NORMALTIME_ADD", "ADD", false));
    leaveApproval.childModules.push(leaveApprovalNormalTimemodule);

    var leaveApprovalAdditionalTimemodule = createModule("Additional Time", true, "leaveapproval", "Y");
    leaveApprovalAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_LEAVEAPPROVAL_ADDITIONALTIME_VIEW", "VIEW", false));
    leaveApprovalAdditionalTimemodule.permissionTOs.push(createPermission("PRJ_PRJSTG_LEAVEAPPROVAL_ADDITIONALTIME_APPROVE", "APPROVE", false));
    leaveApproval.childModules.push(leaveApprovalAdditionalTimemodule);

    projectSettings.childModules.push(leaveApproval);
    projectModule.childModules.push(projectSettings);

    var projectLibrary = createModule("Project Library", true, "", "Y");
    projectLibrary.permissionTOs.push(createPermission("PRJ_PRJLIB_VIEW", "VIEW", false));

    var employeeClassification = createModule("Employee Classification", true, "projempclass", "Y");
    employeeClassification.permissionTOs.push(createPermission("PRJ_PRJLIB_PRJEMPCLSFY_VIEW", "VIEW", false));
    employeeClassification.permissionTOs.push(createPermission("PRJ_PRJLIB_PRJEMPCLSFY_ADD", "ADD", false));
    projectLibrary.childModules.push(employeeClassification);

    var materialTransferRestriction = createModule("Material Transfer Restriction List", true, "projmaterial", "Y");
    materialTransferRestriction.permissionTOs.push(createPermission("PRJ_PRJLIB_PRJMATRLCLSFY_VIEW", "VIEW", false));
    materialTransferRestriction.permissionTOs.push(createPermission("PRJ_PRJLIB_PRJMATRLCLSFY_ADD", "ADD", false));
    projectLibrary.childModules.push(materialTransferRestriction);

    var wareHouseandStockyardList = createModule("Ware House and Stockyard List", true, "projstore", "Y");
    wareHouseandStockyardList.permissionTOs.push(createPermission("PRJ_PRJLIB_STOREANDSTOKITM_VIEW", "VIEW", false));
    wareHouseandStockyardList.permissionTOs.push(createPermission("PRJ_PRJLIB_STOREANDSTOKITM_ADD", "ADD", false));
    wareHouseandStockyardList.permissionTOs.push(createPermission("PRJ_PRJLIB_STOREANDSTOKITM_DELETE", "DEACTIVATE", false));
    projectLibrary.childModules.push(wareHouseandStockyardList);

    var plantClassification = createModule("Plant Classification", true, "projplantclass", "Y");
    plantClassification.permissionTOs.push(createPermission("PRJ_PRJLIB_PRJPLANTCLSFY_VIEW", "VIEW", false));
    plantClassification.permissionTOs.push(createPermission("PRJ_PRJLIB_PRJPLANTCLSFY_ADD", "ADD", false));
    projectLibrary.childModules.push(plantClassification);

    var workingShifts = createModule("Working Shifts", true, "projworkshift", "Y");
    workingShifts.permissionTOs.push(createPermission("PRJ_PRJLIB_WRKGSHFT_VIEW", "VIEW", false));
    workingShifts.permissionTOs.push(createPermission("PRJ_PRJLIB_WRKGSHFT_ADD", "ADD", false));
    workingShifts.permissionTOs.push(createPermission("PRJ_PRJLIB_WRKGSHFT_DELETE", "DEACTIVATE", false));
    projectLibrary.childModules.push(workingShifts);

    var crewList = createModule("Crew List", true, "projworkshift", "Y");
    crewList.permissionTOs.push(createPermission("PRJ_PRJLIB_CRWLIST_VIEW", "VIEW", false));
    crewList.permissionTOs.push(createPermission("PRJ_PRJLIB_CRWLIST_ADD", "ADD", false));
    crewList.permissionTOs.push(createPermission("PRJ_PRJLIB_CRWLIST_DELETE", "DEACTIVATE", false));
    projectLibrary.childModules.push(crewList);

    var scheduleofEstimatedQuantities = createModule("Schedule of Estimated Quantities ( SOE)", true, "projsoe", "Y");
    scheduleofEstimatedQuantities.permissionTOs.push(createPermission("PRJ_PRJLIB_SCHEDOFESTQTY_VIEW", "VIEW", false));
    scheduleofEstimatedQuantities.permissionTOs.push(createPermission("PRJ_PRJLIB_SCHEDOFESTQTY_ADD", "ADD", false));
    scheduleofEstimatedQuantities.permissionTOs.push(createPermission("PRJ_PRJLIB_SCHEDOFESTQTY_DELETE", "DEACTIVATE", false));
    projectLibrary.childModules.push(scheduleofEstimatedQuantities);

    var scheduleofRates = createModule("Schedule of Rates ( SOR)", true, "projsor", "Y");
    scheduleofRates.permissionTOs.push(createPermission("PRJ_PRJLIB_SCHEDOFRATE_VIEW", "VIEW", false));
    scheduleofRates.permissionTOs.push(createPermission("PRJ_PRJLIB_SCHEDOFRATE_ADD", "ADD", false));
    scheduleofRates.permissionTOs.push(createPermission("PRJ_PRJLIB_SCHEDOFRATE_DELETE", "DEACTIVATE", false));
    projectLibrary.childModules.push(scheduleofRates);

    var costCodeSchedule = createModule("Cost Code Schedule", true, "projcostcode", "Y");
    costCodeSchedule.permissionTOs.push(createPermission("PRJ_PRJLIB_COSTCODESCHED_VIEW", "VIEW", false));
    costCodeSchedule.permissionTOs.push(createPermission("PRJ_PRJLIB_COSTCODESCHED_ADD", "ADD", false));
    costCodeSchedule.permissionTOs.push(createPermission("PRJ_PRJLIB_COSTCODESCHED_DELETE", "DEACTIVATE", false));
    projectLibrary.childModules.push(costCodeSchedule);

    var scopeOfWorks = createModule("Scope Of Works ( SOW)", true, "projsow", "Y");
    scopeOfWorks.permissionTOs.push(createPermission("PRJ_PRJLIB_SCOPEOFWRK_VIEW", "VIEW", false));
    scopeOfWorks.permissionTOs.push(createPermission("PRJ_PRJLIB_SCOPEOFWRK_ADD", "ADD", false));
    projectLibrary.childModules.push(scopeOfWorks);
    projectModule.childModules.push(projectLibrary);

    //Project Budgets
    var projectBudgets = createModule("Project Budgets", true, "", "Y");
    projectBudgets.permissionTOs.push(createPermission("PRJ_PROJBUDGET_VIEW", "VIEW", false));

    var projectBudgetManpower = createModule("Manpower", true, "projbudgets", "Y");
    projectBudgetManpower.permissionTOs.push(createPermission("PRJ_PROJBUDGET_MANPOWER_VIEW", "VIEW", false));
    projectBudgetManpower.permissionTOs.push(createPermission("PRJ_PROJBUDGET_MANPOWER_ADD", "ADD", false));
    projectBudgets.childModules.push(projectBudgetManpower);

    var projectBudgetPlant = createModule("Plant", true, "projbudgets", "Y");
    projectBudgetPlant.permissionTOs.push(createPermission("PRJ_PROJBUDGET_PLANT_VIEW", "VIEW", false));
    projectBudgetPlant.permissionTOs.push(createPermission("PRJ_PROJBUDGET_PLANT_ADD", "ADD", false));
    projectBudgets.childModules.push(projectBudgetPlant);

    var projectBudgetMaterials = createModule("Materials", true, "projbudgets", "Y");
    projectBudgetMaterials.permissionTOs.push(createPermission("PRJ_PROJBUDGET_MATERIAL_VIEW", "VIEW", false));
    projectBudgetMaterials.permissionTOs.push(createPermission("PRJ_PROJBUDGET_MATERIAL_ADD", "ADD", false));
    projectBudgets.childModules.push(projectBudgetMaterials);

    var projectBudgetCostStatement = createModule("Cost (Cost Statement)", true, "projbudgets", "Y");
    projectBudgetCostStatement.permissionTOs.push(createPermission("PRJ_PROJBUDGET_COSTSTATEMENT_VIEW", "VIEW", false));
    projectBudgetCostStatement.permissionTOs.push(createPermission("PRJ_PROJBUDGET_COSTSTATEMENT_ADD", "ADD", false));
    projectBudgets.childModules.push(projectBudgetCostStatement);
    projectModule.childModules.push(projectBudgets);

    // Project Measure
    var progressMeasure = createModule("Progress Measure", true, "projstatus", "Y");
    progressMeasure.permissionTOs.push(createPermission("PRJ_PROGRESS_MEASURE_VIEW", "VIEW", false));
    progressMeasure.permissionTOs.push(createPermission("PRJ_PROGRESS_MEASURE_ADD", "ADD", false));

      let progressMeasure_SOR = createModule("Progress Based on Schedule of Rates", false, "", "Y");
      progressMeasure_SOR.permissionTOs.push(createPermission("PRJ_PROGRESS_MEASURE_SOR_VIEW", "VIEW", false));
      progressMeasure.childModules.push(progressMeasure_SOR);

      let progressMeasure_contract = createModule("Progress Measure Based on Contract Milestones", false, "", "Y");
      progressMeasure_contract.permissionTOs.push(createPermission("PRJ_PROGRESS_MEASURE_CONTRACT_VIEW", "VIEW", false));
      progressMeasure_contract.permissionTOs.push(createPermission("PRJ_PROGRESS_MEASURE_CONTRACT_ADD", "ADD", false));
      progressMeasure.childModules.push(progressMeasure_contract);

      let progressMeasure_cost = createModule("Progress Measure Based on Cost Plus %", false, "", "Y");
      progressMeasure_cost.permissionTOs.push(createPermission("PRJ_PROGRESS_MEASURE_COST_VIEW", "VIEW", false));
      progressMeasure.childModules.push(progressMeasure_cost);

    projectModule.childModules.push(progressMeasure);

    //Project Progress Status
    var projectProgressStatus = createModule("Project Status", true, "", "Y");
    projectProgressStatus.permissionTOs.push(createPermission("PRJ_PROJSTATUS_VIEW", "VIEW", false));

    //
    var progressrProjectSummary = createModule("Project Summary", true, "", "Y");
    progressrProjectSummary.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_VIEW", "VIEW", false));

    var manPowermodule = createModule("Man Power", true, "projstatus", "Y");
    manPowermodule.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_MANPOWER_VIEW", "VIEW", false));
    progressrProjectSummary.childModules.push(manPowermodule);

    var plantmodule = createModule("Plant", true, "projstatus", "Y");
    plantmodule.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_PLANT_VIEW", "VIEW", false));
    progressrProjectSummary.childModules.push(plantmodule);

    var costsmodule = createModule("Costs", true, "projstatus", "Y");
    costsmodule.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_COSTS_VIEW", "VIEW", false));
    progressrProjectSummary.childModules.push(costsmodule);

    var milestones = createModule("Milestones", true, "projstatus", "Y");
    milestones.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_MILESTONES_VIEW", "VIEW", false));
    milestones.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_MILESTONES_ADD", "ADD", false));
    milestones.permissionTOs.push(createPermission("PRJ_PROGRESS_SUMMARY_MILESTONES_DEACTIVE", "DEACTIVE", false));
    progressrProjectSummary.childModules.push(milestones);
    projectProgressStatus.childModules.push(progressrProjectSummary);


    var progresserProjectStatus = createModule("Project Status", true, "", "Y");
    progresserProjectStatus.permissionTOs.push(createPermission("PRJ_PROGRESS_PROJECT_STATUS_VIEW", "VIEW", false));

    var projectStatusProgressStatusmodule = createModule("Progress Status", true, "projstatus", "Y");
    projectStatusProgressStatusmodule.permissionTOs.push(createPermission("PRJ_PROJECT_STATUS_PROGRESS_STATUS_VIEW", "VIEW", false));
    projectStatusProgressStatusmodule.permissionTOs.push(createPermission("PRJ_PROJECT_STATUS_PROGRESS_STATUS_ADD", "ADD", false));
    progresserProjectStatus.childModules.push(projectStatusProgressStatusmodule);

    var projectStatusDurationStatusmodule = createModule("Duration Status", true, "projstatus", "Y");
    projectStatusDurationStatusmodule.permissionTOs.push(createPermission("PRJ_PROJECT_STATUS_DURATION_STATUS_VIEW", "VIEW", false));
    projectStatusDurationStatusmodule.permissionTOs.push(createPermission("PRJ_PROJECT_STATUS_DURATION_STATUS_ADD", "ADD", false));
    progresserProjectStatus.childModules.push(projectStatusDurationStatusmodule);

    var projectStatuResourceStatusmodule = createModule("Resource Status", true, "projstatus", "Y");
    projectStatuResourceStatusmodule.permissionTOs.push(createPermission("PRJ_PROJECT_STATUS_RESOURCE_STATUS_VIEW", "VIEW", false));
    progresserProjectStatus.childModules.push(projectStatuResourceStatusmodule);
    projectProgressStatus.childModules.push(progresserProjectStatus);
    projectModule.childModules.push(projectProgressStatus);


    var projectNotes = createModule("Project Notes", true, "", "Y");
    projectNotes.permissionTOs.push(createPermission("PRJ_PRJSTG_NOTEBK_VIEW", "VIEW", false));

    var projnotes = createModule("Notes", true, "notes", "Y");
    projnotes.permissionTOs.push(createPermission("PRJ_PRJSTG_NOTEBK_VIEW", "VIEW", false));
    projnotes.permissionTOs.push(createPermission("PRJ_PRJSTG_NOTEBK_ADD", "ADD", false));
    projectNotes.childModules.push(projnotes);

    projectModule.childModules.push(projectNotes);


    return projectModule;

  }


  function getDocuments() {
    var documents = createModule("Documents", true, "", 'Y');
    documents.permissionTOs.push(createPermission("DOCMTT_VIEW", "VIEW", false));

    var projectFolder = createModule("Project Folder", true, "document", "Y");
    projectFolder.permissionTOs.push(createPermission("PROJECT_FOLDER_DOCMTT_VIEW", "VIEW", false));
    projectFolder.permissionTOs.push(createPermission("PROJECT_FOLDER_DOCMTT_ADD", "ADD", false));
    projectFolder.permissionTOs.push(createPermission("PROJECT_FOLDER_DOCMTT_ALLOW", "ALLOW USERS", false));
    documents.childModules.push(projectFolder);

    var projectDocuments = createModule("Project Documents", true, "document", "Y");
    projectDocuments.permissionTOs.push(createPermission("PROJECT_DOCUMENTS_DOCMTT_VIEW", "VIEW", false));
    projectDocuments.permissionTOs.push(createPermission("PROJECT_DOCUMENTS_DOCMTT_ADD", "ADD", false));
    projectDocuments.permissionTOs.push(createPermission("PROJECT_DOCUMENTS_DOCMTT_DEACTIVATE", "DEACTIVATE", false));

    documents.childModules.push(projectDocuments);
    return documents;
   }

    function getSchedules() {
    let schedules = createModule("Schedules", true, "", 'Y');
    schedules.permissionTOs.push(createPermission("SCHED_VIEW", "VIEW", false));

      let schedule_resourceAssignment = createModule("Resource Assignment Data Table", false, "", "Y");
      schedule_resourceAssignment.permissionTOs.push(createPermission("SCHED_RESOURCE5ASSIGNMENT_VIEW", "VIEW", false));
      schedules.childModules.push(schedule_resourceAssignment);

      let schedule_resourceSchedules = createModule("Resource Schedules", false, "", "Y");
      schedule_resourceSchedules.permissionTOs.push(createPermission("SCHED_RESOURCE5SCHEDULES_VIEW", "VIEW", false));
      schedules.childModules.push(schedule_resourceSchedules);

      let schedule_sow = createModule("SOW Schedules", false, "", "Y");
      schedule_sow.permissionTOs.push(createPermission("SCHED_SOW_VIEW", "VIEW", false));
      schedules.childModules.push(schedule_sow);

      let schedule_tangible = createModule("Tangible Items Schedules", false, "", "Y");
      schedule_tangible.permissionTOs.push(createPermission("SCHED_TANGIBLE_VIEW", "VIEW", false));
      schedules.childModules.push(schedule_tangible);

      let schedule_dataTable = createModule("Activity Schedule Data Table", false, "", "Y");
      schedule_dataTable.permissionTOs.push(createPermission("SCHED_DATA5TABLE_VIEW", "VIEW", false));
      schedules.childModules.push(schedule_dataTable);

      let schedule_schedules = createModule("Activity Schedules", false, "", "Y");
      schedule_schedules.permissionTOs.push(createPermission("SCHED_SCHEDULES", "VIEW", false));
      schedules.childModules.push(schedule_schedules);

      let schedule_sorschedules = createModule("SOR Items Schedule", false, "", "Y");
      schedule_sorschedules.permissionTOs.push(createPermission("SCHED_SOR_SCHEDULES", "VIEW", false));
      schedules.childModules.push(schedule_sorschedules);

    return schedules;
    }

    function getProcurement() {
      var procurementModule = createModule("Procurement", true, "", 'Y');
      procurementModule.permissionTOs.push(createPermission("PROCURMT_VIEW", "VIEW", false));

      var preContracts = createModule("Pre-Contracts", true, "", "Y");
      preContracts.permissionTOs.push(createPermission("PRE_CONTRACTS_VIEW", "VIEW", false));

      var preContractList = createModule("Pre-Contract List", true, "precontractlist", "Y");
      preContractList.permissionTOs.push(createPermission("PROCURMT_PRECONTRCT_VIEW", "VIEW", false));
      preContractList.permissionTOs.push(createPermission("PROCURMT_PRECONTRCT_ADD", "ADD", false));
      preContractList.permissionTOs.push(createPermission("PROCURMT_PRECONTRCT_DEACTIVE", "DEACTIVE", false));
      preContracts.childModules.push(preContractList);

      procurementModule.childModules.push(preContracts);

      var Stage1Request = createModule("Stage 1 Request", true, "internalApproval/request", "Y");
      Stage1Request.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1REQUEST_VIEW", "VIEW", false));
      Stage1Request.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1REQUEST_ADD", "ADD", false));
      Stage1Request.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1REQUESTREFDOCUMENT_ADD", "REF DOCUMENT", false));

      var preContractListManPower = createModule("Man Power", true, "internalApproval/request", "Y");
      preContractListManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MANPOWER_VIEW", "VIEW", false));
      preContractListManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MANPOWER_ADD", "ADD", false));
      preContractListManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MANPOWER_DEACTIVE", "DEACTIVE", false));
      preContractListManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MANPOWER_SUBMIT", "SUBMIT", false));
      Stage1Request.childModules.push(preContractListManPower);

      var preContractListMaterials = createModule("Materials", true, "internalApproval/request", "Y");
      preContractListMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MATERIALS_VIEW", "VIEW", false));
      preContractListMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MATERIALS_ADD", "ADD", false));
      preContractListMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MATERIALS_DEACTIVE", "DEACTIVE", false));
      preContractListMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1MATERIALS_SUBMIT", "SUBMIT", false));
      Stage1Request.childModules.push(preContractListMaterials);

      var preContractListPlants = createModule("Plants", true, "internalApproval/request", "Y");
      preContractListPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1PLANTS_VIEW", "VIEW", false));
      preContractListPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1PLANTS_ADD", "ADD", false));
      preContractListPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1PLANTS_DEACTIVE", "DEACTIVE", false));
      preContractListPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1PLANTS_SUBMIT", "SUBMIT", false));
      Stage1Request.childModules.push(preContractListPlants);

      var preContractListServices = createModule("Services", true, "internalApproval/request", "Y");
      preContractListServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SERVICES_VIEW", "VIEW", false));
      preContractListServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SERVICES_ADD", "ADD", false));
      preContractListServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SERVICES_DEACTIVE", "DEACTIVE", false));
      preContractListServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SERVICES_SUBMIT", "SUBMIT", false));
      Stage1Request.childModules.push(preContractListServices);

      var preContractListProjectSubContract = createModule("Project Sub Contract", true, "internalApproval/request", "Y");
      preContractListProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SUBCONTRACT_VIEW", "VIEW", false));
      preContractListProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SUBCONTRACT_ADD", "ADD", false));
      preContractListProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SUBCONTRACT_DEACTIVE", "DEACTIVE", false));
      preContractListProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1SUBCONTRACT_SUBMIT", "SUBMIT", false));
      Stage1Request.childModules.push(preContractListProjectSubContract);
      preContracts.childModules.push(Stage1Request);

      var stage1Approval = createModule("Stage 1 Approval", true, "internalApproval/approval", "Y");
      stage1Approval.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APRVL_VIEW", "VIEW", false));
      stage1Approval.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APRVL_ADD", "ADD", false));
      stage1Approval.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APRVLREFDOCUMENT_ADD", "REF DOCUMENT", false));

      var preContractListStag1ApprovalManPower = createModule("Man Power", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_VIEW", "VIEW", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_ADD", "ADD", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANPOWER_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalManPower);

      var preContractListStag1ApprovalMaterials = createModule("Materials", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_VIEW", "VIEW", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_ADD", "ADD", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALMANTERIAL_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalMaterials);

      var preContractListStag1ApprovalPlants = createModule("Plants", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_VIEW", "VIEW", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_ADD", "ADD", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPLANT_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalPlants);

      var preContractListStag1ApprovalServices = createModule("Services", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_VIEW", "VIEW", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_ADD", "ADD", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALSERVICES_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalServices);

      var preContractListStag1ApprovalProjectSubContract = createModule("Project Sub Contract", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_VIEW", "VIEW", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ADD", "ADD", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("PROCURMT_INTRNLSTAGE1APPROVALPRESUBCONTRACT_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalProjectSubContract);
      preContracts.childModules.push(stage1Approval);

      var rfqModule = createModule("RFQ", true, "", "Y");
      rfqModule.permissionTOs.push(createPermission("PROCURMT_PROCURMTRFQ_VIEW", "VIEW", false));

      var rfqlist = createModule("RFQ Tendering", true, "rfq", "Y");
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_VIEW", "VIEW", false));
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_ADDBIDDERS", "ADD BIDDERS", false));
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_TENDERDOCUMENTS_ADD", "TENDER DOCUMENTS", false));
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_SCHEDULEOFITEMS", "SCHEDULE OF ITEMS", false));
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_FROMVENDER", "FROM VENDER", false));
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_TOVENDER", "TO VENDER", false));
      rfqlist.permissionTOs.push(createPermission("PROCURMT_RFQTENDERING_QUOTESFROMVENDOR", "QUOTES FROM VENDOR", false));
      rfqModule.childModules.push(rfqlist);
      preContracts.childModules.push(rfqModule);

      var stage2Request = createModule("Stage 2 Request", true, "", "Y");
      stage2Request.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2REQUEST_VIEW", "VIEW", false));
      stage2Request.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2REQUEST_SCHEDULEOFITEMS", "SCHEDULE OF ITEMS", false));
      stage2Request.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2REQUEST_APPRVDETAILS", "APPROVER DETAILS", false));
      stage2Request.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2REQUEST_REFDOCUMENT_ADD", "REF DOCUMENT", false));

      var bidAnalysismodule = createModule("BID ANALYSIS", true, "externalApproval/request", "Y");
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2REQUEST_ADDBIDDERS_VIEW", "VIEW", false));
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2REQUEST_SENDFORAPPROVAL", "SEND FOR APPROVAL", false));
      stage2Request.childModules.push(bidAnalysismodule);
      preContracts.childModules.push(stage2Request);

      var stage2Approval = createModule("Stage 2 Approval", true, "", "Y");
      stage2Approval.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APRVL_VIEW", "VIEW", false));
      stage2Approval.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_SCHEDULEOFITEMS", "SCHEDULE OF ITEMS", false));
      stage2Approval.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_APPRVDETAILS", "APPROVER DETAILS", false));
      stage2Approval.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_REFDOCUMENT_ADD", "REF DOCUMENT", false));
      stage2Approval.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_APPROVE", "APPROVE", false));

      var stage2approvebidAnalysismodule = createModule("BID ANALYSIS", true, "externalApproval/approval", "Y");
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_ADDBIDDERS_VIEW", "VIEW", false));
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_REJECT", "REJECT", false));
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_EXTRNLSTAGE2APPROVAL_ONHOLD", "ON HOLD", false));

      stage2Approval.childModules.push(stage2approvebidAnalysismodule);
      preContracts.childModules.push(stage2Approval);


      var purchaseOrdersmodule = createModule("Purchase Orders", true, "", "Y");
      purchaseOrdersmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_VIEW", "VIEW", false));
      purchaseOrdersmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_ADD", "ADD", false));

      var scheduleOfItemsmodule = createModule("PO-Schedule Of Items", true, "purchaseorder", "Y");
      scheduleOfItemsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_POSCHEDULE_VIEW", "VIEW", false));

      var manpowerscheduleOfItemsmodule = createModule("Man Power", true, "purchaseorder", "Y");
      manpowerscheduleOfItemsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_POSCHEDULEMANPOWER_VIEW", "VIEW", false));
      scheduleOfItemsmodule.childModules.push(manpowerscheduleOfItemsmodule);

      var materialscheduleOfItemsmodule = createModule("Material", true, "purchaseorder", "Y");
      materialscheduleOfItemsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_POSCHEDULEMATERIL_VIEW", "VIEW", false));
      scheduleOfItemsmodule.childModules.push(materialscheduleOfItemsmodule);

      var plantcheduleOfItemsmodule = createModule("Plant", true, "purchaseorder", "Y");
      plantcheduleOfItemsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_POSCHEDULEPLANT_VIEW", "VIEW", false));
      scheduleOfItemsmodule.childModules.push(plantcheduleOfItemsmodule);

      var servicescheduleOfItemsmodule = createModule("Services", true, "purchaseorder", "Y");
      servicescheduleOfItemsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_POSCHEDULESERVICE_VIEW", "VIEW", false));
      scheduleOfItemsmodule.childModules.push(servicescheduleOfItemsmodule);

      var subContractcheduleOfItemsmodule = createModule("Sub Contract", true, "purchaseorder", "Y");
      subContractcheduleOfItemsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_POSCHEDULESUBCONTRACT_VIEW", "VIEW", false));
      scheduleOfItemsmodule.childModules.push(subContractcheduleOfItemsmodule);
      purchaseOrdersmodule.childModules.push(scheduleOfItemsmodule);

      var resourceDeliverymodule = createModule("Resource Delivery/Progress", true, "purchaseorder", "Y");
      resourceDeliverymodule.permissionTOs.push(createPermission("PROCURMT_RESOURCE_DELIVERYPROGRESS_VIEW", "VIEW", false));

      var manpowerResourceDeliverymodule = createModule("Man Power", true, "purchaseorder", "Y");
      manpowerResourceDeliverymodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RESOURDELMANPOWER_VIEW", "VIEW", false));
      resourceDeliverymodule.childModules.push(manpowerResourceDeliverymodule);

      var materialResourceDeliverymodule = createModule("Material", true, "purchaseorder", "Y");
      materialResourceDeliverymodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RESOURDELMATERIL_VIEW", "VIEW", false));
      resourceDeliverymodule.childModules.push(materialResourceDeliverymodule);

      var plantcheduleResourceDeliverymodule = createModule("Plant", true, "purchaseorder", "Y");
      plantcheduleResourceDeliverymodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RESOURDELPLANT_VIEW", "VIEW", false));
      resourceDeliverymodule.childModules.push(plantcheduleResourceDeliverymodule);

      var serviceResourceDeliverymodule = createModule("Services", true, "purchaseorder", "Y");
      serviceResourceDeliverymodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RESOURDELSERVICE_VIEW", "VIEW", false));
      resourceDeliverymodule.childModules.push(serviceResourceDeliverymodule);

      var subContractResourceDeliverymodule = createModule("Sub Contract", true, "purchaseorder", "Y");
      subContractResourceDeliverymodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RESOURDELSUBCONTRACT_VIEW", "VIEW", false));
      resourceDeliverymodule.childModules.push(subContractResourceDeliverymodule);
      purchaseOrdersmodule.childModules.push(resourceDeliverymodule);

      var invoiceStatusmodule = createModule("Invoice Status", true, "purchaseorder", "Y");
      invoiceStatusmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_INOVICESTATUS_VIEW", "VIEW", false));
      purchaseOrdersmodule.childModules.push(invoiceStatusmodule);

      var preContractListDocumentsmodule = createModule("PreContract List & Documents", true, "purchaseorder", "Y");
      preContractListDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_PRECONTLISTDOCM_VIEW", "VIEW", false));
      preContractListDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_PRECONTLISTDOCMREFDOCM_VIEW", "REF DOCUMENTS", false));
      purchaseOrdersmodule.childModules.push(preContractListDocumentsmodule);

      // Existing code

      // var stage1RequestDocumentsmodule = createModule("Stage1 Request & Approval", true, "purchaseorder", "Y");
      // stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPR_VIEW", "VIEW", false));
      // stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPRSCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      // stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPRAPPROVEDETAIILS_VIEW", "APPROVE DETAIILS", false));
      // stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPRREFDOCUMENTS_VIEW", "REF DOCUMENTS", false));
      // purchaseOrdersmodule.childModules.push(stage1RequestDocumentsmodule);

      // Stage 1 Request & Approval separation module

      var stage1RequestDocumentsmodule = createModule("Stage1 Request", true, "purchaseorder", "Y");
      stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPR_VIEW", "VIEW", false));
      stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPRSCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPRAPPROVEDETAIILS_VIEW", "APPROVER DETAILS", false));
      stage1RequestDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1REQAPPRREFDOCUMENTS_VIEW", "REF DOCUMENTS", false));
      purchaseOrdersmodule.childModules.push(stage1RequestDocumentsmodule);


      var stage1ApprovalDocumentsmodule = createModule("Stage1 Approval", true, "purchaseorder", "Y");
      stage1ApprovalDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1APPR_VIEW", "VIEW", false));
      stage1ApprovalDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1APPRSCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      stage1ApprovalDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1APPRAPPROVEDETAIILS_VIEW", "APPROVER DETAILS", false));
      stage1ApprovalDocumentsmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE1APPRREFDOCUMENTS_VIEW", "REF DOCUMENTS", false));
      purchaseOrdersmodule.childModules.push(stage1ApprovalDocumentsmodule);

      // end

      var rfqmodule = createModule("RFQ", true, "purchaseorder", "Y");
      rfqmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RFQ_VIEW", "VIEW", false));
      rfqmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RFQSCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      rfqmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RFQ_TOVENDOR_VIEW", "TO VENDOR", false));
      rfqmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RFQFROMVENDOR_VIEW", "FROM VENDOR", false));
      rfqmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RFQQUOTESFROMVENDOR_VIEW", "QUOTES FROM VENDOR", false));
      purchaseOrdersmodule.childModules.push(rfqmodule);

      // Existing Code

      // var bidAnalysismodule = createModule("Bid Analysis & Stage2 Approval", true, "purchaseorder", "Y");
      // bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSIS_VIEW", "VIEW", false));
      // bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSISSCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      // bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSIS_APPROVERDETAILS_VIEW", "APPROVER DETAILS", false));
      // bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSISBIDANLAYSIS_VIEW", "BID ANLAYSIS", false));
      // bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RBIDANALYSISQUOTESREFDOCUMENT_VIEW", "REF DOCUMENT", false));
      // purchaseOrdersmodule.childModules.push(bidAnalysismodule);
      // procurementModule.childModules.push(purchaseOrdersmodule);

      // Stage 2 Request & Stage 2 Approval separation module

      var bidAnalysismodule = createModule("Stage2 Request", true, "purchaseorder", "Y");
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSIS_VIEW", "VIEW", false));
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSISSCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSIS_APPROVERDETAILS_VIEW", "APPROVER DETAILS", false));
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_BIDANALYSISBIDANLAYSIS_VIEW", "BID ANLAYSIS", false));
      bidAnalysismodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_RBIDANALYSISQUOTESREFDOCUMENT_VIEW", "REF DOCUMENT", false));
      purchaseOrdersmodule.childModules.push(bidAnalysismodule);

      var stage2approvalmodule = createModule("Stage2 Approval", true, "purchaseorder", "Y");
      stage2approvalmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE2APPROVAL_VIEW", "VIEW", false));
      stage2approvalmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE2APPROVAL_SCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      stage2approvalmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE2APPROVAL_APPROVERDETAILS_VIEW", "APPROVER DETAILS", false));
      stage2approvalmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE2APPROVALSTAGE2APPROVAL_VIEW", "BID ANLAYSIS", false));
      stage2approvalmodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_STAGE2APPROVAL_QUOTESREFDOCUMENT_VIEW", "REF DOCUMENT", false));
      purchaseOrdersmodule.childModules.push(stage2approvalmodule);
      // procurementModule.childModules.push(purchaseOrdersmodule);

      // end
      // Aug 9th Repeat PO module
      var repeatpomodule = createModule("Repeat PO", true, "purchaseorder", "Y");
      repeatpomodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_REPEAT_PO_VIEW", "VIEW", false));
      repeatpomodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_REPEAT_PO_SCHEDULEOFITEMS_VIEW", "SCHEDULE OF ITEMS", false));
      repeatpomodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_REPEAT_PO_APPROVERDETAILS_VIEW", "APPROVER DETAILS", false));
      repeatpomodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_REPEAT_POREPEAT_PO_VIEW", "BID ANLAYSIS", false));
      repeatpomodule.permissionTOs.push(createPermission("PROCURMT_PURCHASEORDR_REPEAT_PO_QUOTESREFDOCUMENT_VIEW", "REF DOCUMENT", false));
      purchaseOrdersmodule.childModules.push(repeatpomodule);
      procurementModule.childModules.push(purchaseOrdersmodule);
      // end

      return procurementModule;
    }

    function getAsbuiltRecordsModule() {
      var asbuiltRecordsModule = createModule("Asbuilt Records", true, "", 'Y');
      asbuiltRecordsModule.permissionTOs.push(createPermission("ASBUILTRCRD_VIEW", "VIEW", false));

      var attendanceRecords = createModule("Attendance Records", true, "", "Y");
      attendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_VIEW", "VIEW", false));

      var listofempattendanceRecords = createModule("List of Employee Attendance Records", true, "listofempattendence", "Y");
      listofempattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_LIST_EMPATNDNCE_VIEW", "VIEW", false));
      attendanceRecords.childModules.push(listofempattendanceRecords);

      var empattendanceRecords = createModule("Employee Attendance", true, "empattendence", "Y");
      empattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_EMPATNDNCE_VIEW", "VIEW", false));
      empattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_EMPATNDNCE_ADD", "ADD", false));
      // empattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_EMPATNDNCE_SUBMIT", "SUBMIT", false));
      attendanceRecords.childModules.push(empattendanceRecords);

      var listofplantattendanceRecords = createModule("List of Plant Attendance Records", true, "listofplantattendence", "Y");
      listofplantattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_LIST_PLANTATNDNCE_VIEW", "VIEW", false));
      attendanceRecords.childModules.push(listofplantattendanceRecords);

      var plantattendanceRecords = createModule("Plant Attendance", true, "plantattendence", "Y");
      plantattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_PLANTATNDNCE_VIEW", "VIEW", false));
      plantattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_PLANTATNDNCE_ADD", "ADD", false));
      // plantattendanceRecords.permissionTOs.push(createPermission("ASBUILTRCRD_ATNDNCERCRDS_PLANTATNDNCE_SUBMIT", "SUBMIT", false));
      attendanceRecords.childModules.push(plantattendanceRecords);
      asbuiltRecordsModule.childModules.push(attendanceRecords);

      var workDairyModule = createModule("Work Diary", true, "", "Y");
      workDairyModule.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_VIEW", "VIEW", false));

      var createWorkDairy = createModule("Create Work Diary", true, "createworkdairy", "Y");
      createWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CREATWRKDIRY_VIEW", "VIEW", false));
      createWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CREATWRKDIRY_ADD", "ADD", false));
      createWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CREATWRKDIRY_SUBMIT", "SUBMIT", false));
      createWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CREATWRKDIRY_REQUESTFORADDTIONALTIME", "REQUEST FOR ADDTIONAL TIME", false));
      workDairyModule.childModules.push(createWorkDairy);

      var approveWorkDairy = createModule("Approve Work Diary", true, "approveworkdiary", "Y");
      approveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_VIEW", "VIEW", false));
      approveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_ADD", "ADD", false));
      approveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_APPROVE", "APPROVE", false));
      approveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_REQUESTFORADDTIONALTIME", "REQUEST FOR ADDTIONAL TIME", false));
      workDairyModule.childModules.push(approveWorkDairy);


      var clientApproveWorkDairy = createModule("Client Approval", true, "workdairyclientapproval", "Y");
      clientApproveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CLIENTAPPROVAL_VIEW", "VIEW", false));
      clientApproveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CLIENTAPPROVAL_ADD", "ADD", false));
      clientApproveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CLIENTAPPROVAL_APPROVE", "APPROVE", false));
      clientApproveWorkDairy.permissionTOs.push(createPermission("ASBUILTRCRD_WRKDIRY_CLIENTAPPROVAL_REQUESTFORADDTIONALTIME", "REQUEST FOR ADDTIONAL TIME", false));
      workDairyModule.childModules.push(clientApproveWorkDairy);
      asbuiltRecordsModule.childModules.push(workDairyModule);

      var timeSheetModule = createModule("Time Sheets", true, "", "Y");
      timeSheetModule.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_VIEW", "VIEW", false));

      var createTimeSheet = createModule("Create Time Sheet", true, "createworkdairy", "Y");
      createTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_CREATTIMESHET_VIEW", "VIEW", false));
      createTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_CREATTIMESHET_ADD", "ADD", false));
      createTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_CREATTIMESHET_SUBMIT", "SUBMIT", false));
      createTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_CREATTIMESHET_NOTFICATIONFORSUBMIT", "NOTFICATION FOR SUBMIT", false));
      timeSheetModule.childModules.push(createTimeSheet);

      var approveTimeSheet = createModule("Approve Time Sheet", true, "approvetimesheet", "Y");
      approveTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_APRVETIMESHET_VIEW", "VIEW", false));
      approveTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_APRVETIMESHET_ADD", "ADD", false));
      approveTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_APRVETIMESHET_SUBMIT", "SUBMIT", false));
      approveTimeSheet.permissionTOs.push(createPermission("ASBUILTRCRD_TIMSHET_APRVETIMESHET_NOTFICATIONFORAPPROVAL", "NOTFICATION FOR APPROVAL", false));
      timeSheetModule.childModules.push(approveTimeSheet);


      asbuiltRecordsModule.childModules.push(timeSheetModule);


      return asbuiltRecordsModule;
    }

    function getResourcesModule() {
      var resourcesModule = createModule("Resources", true, "", 'Y');
      resourcesModule.permissionTOs.push(createPermission("RESOURCE_VIEW", "VIEW", false));


      var employeeModule = createModule("Employee", true, "", "Y");
      employeeModule.permissionTOs.push(createPermission("RESOURCE_EMPREGLIST_VIEW", "VIEW", false));

      var masterEmployeeList = createModule("Master Employee List", true, "empregister", "Y");
      masterEmployeeList.permissionTOs.push(createPermission("RESOURCE_EMPREG_VIEW", "VIEW", false));
      masterEmployeeList.permissionTOs.push(createPermission("RESOURCE_EMPREG_ADD", "ADD", false));
      masterEmployeeList.permissionTOs.push(createPermission("RESOURCE_EMPREG_DELETE", "DEACTIVATE", false));


      var enrollmentPromotionModule = createModule("Enrollment/Promotion", true, "empregister", "Y");
      enrollmentPromotionModule.permissionTOs.push(createPermission("RESOURCE_EMPENROLLMENTPROMOTION_VIEW", "VIEW", false));
      enrollmentPromotionModule.permissionTOs.push(createPermission("RESOURCE_EMPENROLLMENTPROMOTION_ADD", "ADD", false));
      masterEmployeeList.childModules.push(enrollmentPromotionModule);

      var employeeServiceHistory = createModule("Employee Service History", true, "empregister", "Y");
      employeeServiceHistory.permissionTOs.push(createPermission("RESOURCE_EMPSERVICEHHISTORY_VIEW", "VIEW", false));
      employeeServiceHistory.permissionTOs.push(createPermission("RESOURCE_EMPSERVICEHHISTORY_ADD", "ADD", false));
      masterEmployeeList.childModules.push(employeeServiceHistory);

      var employeeQualificationRecords = createModule("Employee Qualification Records", true, "empregister", "Y");
      employeeQualificationRecords.permissionTOs.push(createPermission("RESOURCE_QUALIFICATIONRECORDS_VIEW", "VIEW", false));
      employeeQualificationRecords.permissionTOs.push(createPermission("RESOURCE_QUALIFICATIONRECORDS_ADD", "ADD", false));
      masterEmployeeList.childModules.push(employeeQualificationRecords);

      var chargeOutRates = createModule("Charge Out Rates", true, "empregister", "Y");
      chargeOutRates.permissionTOs.push(createPermission("RESOURCE_EMPCHARGEOUTRATES_VIEW", "VIEW", false));
      chargeOutRates.permissionTOs.push(createPermission("RESOURCE_EMPCHARGEOUTRATES_ADD", "ADD", false));
      masterEmployeeList.childModules.push(chargeOutRates);

      var regularPayableRates = createModule("Regular Payable Rates", true, "empregister", "Y");
      regularPayableRates.permissionTOs.push(createPermission("RESOURCE_EMPREGULARPAYBLERATES_VIEW", "VIEW", false));
      regularPayableRates.permissionTOs.push(createPermission("RESOURCE_EMPREGULARPAYBLERATES_ADD", "ADD", false));
      masterEmployeeList.childModules.push(regularPayableRates);

      var nonRegularPayableRates = createModule("Non Regular Payable Rates", true, "empregister", "Y");
      nonRegularPayableRates.permissionTOs.push(createPermission("RESOURCE_EMPNONREGULARPAYBLERATES_VIEW", "VIEW", false));
      nonRegularPayableRates.permissionTOs.push(createPermission("RESOURCE_EMPNONREGULARPAYBLERATES_ADD", "ADD", false));
      masterEmployeeList.childModules.push(nonRegularPayableRates);

      var payDeductions = createModule("Pay Deductions", true, "empregister", "Y");
      payDeductions.permissionTOs.push(createPermission("RESOURCE_EMPPAYDEDUCTION_VIEW", "VIEW", false));
      payDeductions.permissionTOs.push(createPermission("RESOURCE_EMPEMPPAYDEDUCTION_ADD", "ADD", false));
      masterEmployeeList.childModules.push(payDeductions);


      var bankAccountDetails = createModule("Bank Account Details", true, "empregister", "Y");
      bankAccountDetails.permissionTOs.push(createPermission("RESOURCE_EMPBANKACCTDETAILS_VIEW", "VIEW", false));
      bankAccountDetails.permissionTOs.push(createPermission("RESOURCE_EMPBANKACCTDETAILS_ADD", "ADD", false));
      bankAccountDetails.permissionTOs.push(createPermission("RESOURCE_EMPBANKACCTDETAILS_DELETE", "DEACTIVATE", false));
      masterEmployeeList.childModules.push(bankAccountDetails);

      var providentfund = createModule("Super annuation / Provident fund", true, "empregister", "Y");
      providentfund.permissionTOs.push(createPermission("RESOURCE_EMPSUPERANNPROVIDENTFUND_VIEW", "VIEW", false));
      providentfund.permissionTOs.push(createPermission("RESOURCE_EMPSUPERANNPROVIDENTFUND_ADD", "ADD", false));
      masterEmployeeList.childModules.push(providentfund);

      var medicalHistory = createModule("Medical History", true, "empregister", "Y");
      medicalHistory.permissionTOs.push(createPermission("RESOURCE_EMPMEDICALHISTROEY_VIEW", "VIEW", false));
      medicalHistory.permissionTOs.push(createPermission("RESOURCE_EMPMEDICALHISTROEY_ADD", "ADD", false));
      masterEmployeeList.childModules.push(medicalHistory);

      var leaveandAttendance = createModule("Leave and Attendance", true, "empregister", "Y");
      leaveandAttendance.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEATTENDANCE_VIEW", "VIEW", false));
      leaveandAttendance.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEATTENDANCE_ADD", "ADD", false));
      masterEmployeeList.childModules.push(leaveandAttendance);

      var employeeContactDetails = createModule("Employee Contact Details", true, "empregister", "Y");
      employeeContactDetails.permissionTOs.push(createPermission("RESOURCE_EMPCONTRACTDETAILS_VIEW", "VIEW", false));
      employeeContactDetails.permissionTOs.push(createPermission("RESOURCE_EMPCONTRACTDETAILS_ADD", "ADD", false));
      masterEmployeeList.childModules.push(employeeContactDetails);

      var nokNextofKinDetails = createModule("NOK (Next of Kin) Details", true, "empregister", "Y");
      nokNextofKinDetails.permissionTOs.push(createPermission("RESOURCE_EMPNOKDETAILS_VIEW", "VIEW", false));
      nokNextofKinDetails.permissionTOs.push(createPermission("RESOURCE_EMPNOKDETAILS_ADD", "ADD", false));
      masterEmployeeList.childModules.push(nokNextofKinDetails);


      var requestforTransfer = createModule("Request for Transfer", true, "empregister", "Y");
      requestforTransfer.permissionTOs.push(createPermission("RESOURCE_EMPREQFORTRANSFER_VIEW", "VIEW", false));
      requestforTransfer.permissionTOs.push(createPermission("RESOURCE_EMPREQFORTRANSFER_SUBMIT", "SUBMIT", false));
      masterEmployeeList.childModules.push(requestforTransfer);

      var approvalforTransfer = createModule("Approval for Transfer", true, "empregister", "Y");
      approvalforTransfer.permissionTOs.push(createPermission("RESOURCE_EMPAPPROVALTRANSFER_VIEW", "VIEW", false));
      approvalforTransfer.permissionTOs.push(createPermission("RESOURCE_EMPAPPROVALTRANSFER_REQFORADDTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalforTransfer.permissionTOs.push(createPermission("RESOURCE_EMPAPPROVALTRANSFER_APPROVE", "APPROVE", false));
      approvalforTransfer.permissionTOs.push(createPermission("RESOURCE_EMPAPPROVALTRANSFER_REJECT", "REJECT", false));
      masterEmployeeList.childModules.push(approvalforTransfer);

      var leaveRequest = createModule("Leave Request", true, "empregister", "Y");
      leaveRequest.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEREQUEST_VIEW", "VIEW", false));
      leaveRequest.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEREQUEST_SUBMIT", "SUBMIT", false));
      masterEmployeeList.childModules.push(leaveRequest);

      var leaveApproval = createModule("Leave Approval", true, "empregister", "Y");
      leaveApproval.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEAPPROVAL_VIEW", "VIEW", false));
      leaveApproval.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEAPPROVAL_APPROVE", "APPROVE", false));
      leaveApproval.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEAPPROVAL_REQFORADDTIME", "REQUEST FOR ADDITIONAL TIME", false));
      leaveApproval.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEAPPROVAL_APPROVE", "APPROVE", false));
      leaveApproval.permissionTOs.push(createPermission("RESOURCE_EMPLEAVEAPPROVAL_NOTAPPROVE", "NOT APPROVE", false));
      masterEmployeeList.childModules.push(leaveApproval);

      employeeModule.childModules.push(masterEmployeeList);
      resourcesModule.childModules.push(employeeModule);

      var plantEquipments = createModule("Plant & Equipments", true, "", "Y");
      plantEquipments.permissionTOs.push(createPermission("RESOURCE_PLANTEQUIPMENT_VIEW", "VIEW", false));

      var masterPlantandEquipments = createModule("Master Plant and Equipments", true, "plantregistor", "Y");
      masterPlantandEquipments.permissionTOs.push(createPermission("RESOURCE_PLANTREG_VIEW", "VIEW", false));
      masterPlantandEquipments.permissionTOs.push(createPermission("RESOURCE_PLANTREG_ADD", "ADD", false));
      masterPlantandEquipments.permissionTOs.push(createPermission("RESOURCE_PLANTREG_DELETE", "DEACTIVATE", false));


      var procurementDeliverDetails = createModule("Procurement & Delivery Details", true, "plantregistor", "Y");
      procurementDeliverDetails.permissionTOs.push(createPermission("RESOURCE_PLANTPROCDELDETLS_VIEW", "VIEW", false));
      procurementDeliverDetails.permissionTOs.push(createPermission("RESOURCE_PLANTPROCDELDETLS_ADD", "ADD", false));
      procurementDeliverDetails.permissionTOs.push(createPermission("RESOURCE_PLANTPROCDELDETLSDOCKETDETAILS_VIEW", "DOCKET DETAILS VIEW", false));
      procurementDeliverDetails.permissionTOs.push(createPermission("RESOURCE_PLANTPROCDELDETLSDOCKETDETAILS_ADD", "DOCKET DETAILS ADD", false));
      procurementDeliverDetails.permissionTOs.push(createPermission("RESOURCE_PLANTPROCDELDETLSDOCKETDETAILS_DELETE", "DOCKET DETAILS DEACTIVATE", false));
      masterPlantandEquipments.childModules.push(procurementDeliverDetails);

      var deploymentHistory = createModule("Deployment History", true, "plantregistor", "Y");
      deploymentHistory.permissionTOs.push(createPermission("RESOURCE_PLANTDEPHISTORY_VIEW", "VIEW", false));
      deploymentHistory.permissionTOs.push(createPermission("RESOURCE_PLANTDEPHISTORY_ADD", "ADD", false));
      masterPlantandEquipments.childModules.push(deploymentHistory);
      
      var plantPayableRates = createModule("Payable Rates", true, "plantregistor", "Y");
      plantPayableRates.permissionTOs.push(createPermission("RESOURCE_PLANTPAYABLERATES_VIEW", "VIEW", false));
      plantPayableRates.permissionTOs.push(createPermission("RESOURCE_PLANTPAYABLERATES_ADD", "ADD", false));
      masterPlantandEquipments.childModules.push(plantPayableRates);

      var plantchargeOutRates = createModule("Charge out Rates", true, "plantregistor", "Y");
      plantchargeOutRates.permissionTOs.push(createPermission("RESOURCE_PLANTCHARGEOUTRATE_VIEW", "VIEW", false));
      plantchargeOutRates.permissionTOs.push(createPermission("RESOURCE_PLANTCHARGEOUTRATE_ADD", "ADD", false));
      masterPlantandEquipments.childModules.push(plantchargeOutRates);

      var utilizationRecords = createModule("Utilization Records", true, "plantregistor", "Y");
      utilizationRecords.permissionTOs.push(createPermission("RESOURCE_PLANTUTILIZATIONRCD_VIEW", "VIEW", false));
      masterPlantandEquipments.childModules.push(utilizationRecords);


      var logBookRecords = createModule("Log Book Records", true, "plantregistor", "Y");
      logBookRecords.permissionTOs.push(createPermission("RESOURCE_PLANTLONGBOOKRECORDS_VIEW", "VIEW", false));

      var logBook = createModule("Log Book", true, "plantregistor", "Y");
      logBook.permissionTOs.push(createPermission("RESOURCE_PLANTLONGBOOK_VIEW", "VIEW", false));
      logBook.permissionTOs.push(createPermission("RESOURCE_PLANTLONGBOOK_ADD", "ADD", false));
      logBookRecords.childModules.push(logBook);

      var plantWorkingDays = createModule("Plant Working Days", true, "plantregistor", "Y");
      plantWorkingDays.permissionTOs.push(createPermission("RESOURCE_PLANTWORKINGDAYS_VIEW", "VIEW", false));
      logBookRecords.childModules.push(plantWorkingDays);
      masterPlantandEquipments.childModules.push(logBookRecords);

      var serviceHistoryRepairs = createModule("Service History & Repairs", true, "plantregistor", "Y");
      serviceHistoryRepairs.permissionTOs.push(createPermission("RESOURCE_PLANTSERVICEHISTORYREPAIR_VIEW", "VIEW", false));

      var serviceHistory = createModule("Service History", true, "plantregistor", "Y");
      serviceHistory.permissionTOs.push(createPermission("RESOURCE_PLANTSERVICEHISTORY_VIEW", "VIEW", false));
      serviceHistory.permissionTOs.push(createPermission("RESOURCE_PLANTSERVICEHISTORY_ADD", "ADD", false));
      serviceHistoryRepairs.childModules.push(serviceHistory);

      var recordsofRepair = createModule("Records of Repair", true, "plantregistor", "Y");
      recordsofRepair.permissionTOs.push(createPermission("RESOURCE_PLANTRECDOFREPAIR_VIEW", "VIEW", false));
      recordsofRepair.permissionTOs.push(createPermission("RESOURCE_PLANTRECDOFREPAIR_ADD", "ADD", false));
      serviceHistoryRepairs.childModules.push(recordsofRepair);
      masterPlantandEquipments.childModules.push(serviceHistoryRepairs);

      var depreciationandSalvageValue = createModule("Depreciation and Salvage Value", true, "plantregistor", "Y");
      depreciationandSalvageValue.permissionTOs.push(createPermission("RESOURCE_PLANTDEPRANDSALVEVALUE_VIEW", "VIEW", false));
      depreciationandSalvageValue.permissionTOs.push(createPermission("RESOURCE_PLANTDEPRANDSALVEVALUE_ADD", "ADD", false));
      masterPlantandEquipments.childModules.push(depreciationandSalvageValue);

      var requestForTransfer = createModule("Request For Transfer", true, "plantregistor", "Y");
      requestForTransfer.permissionTOs.push(createPermission("RESOURCE_PLANTREQFORTRANSFER_VIEW", "VIEW", false));
      requestForTransfer.permissionTOs.push(createPermission("RESOURCE_PLANTREQFORTRANSFER_SUBMIT", "SUBMIT", false));
      masterPlantandEquipments.childModules.push(requestForTransfer);

      var approvalForTransfer = createModule("Approval For Transfer", true, "plantregistor", "Y");
      approvalForTransfer.permissionTOs.push(createPermission("RESOURCE_PLANTAPPRFORTRANSFER_VIEW", "VIEW", false));
      approvalForTransfer.permissionTOs.push(createPermission("RESOURCE_PLANTAPPRFORTRANSFER_REQFORADDTTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalForTransfer.permissionTOs.push(createPermission("RESOURCE_PLANTAPPRFORTRANSFER_APPROVE", "APPROVE", false));
      approvalForTransfer.permissionTOs.push(createPermission("RESOURCE_PLANTAPPRFORTRANSFER_REJECT", "REJECT", false));
      masterPlantandEquipments.childModules.push(approvalForTransfer);


      plantEquipments.childModules.push(masterPlantandEquipments);
      resourcesModule.childModules.push(plantEquipments);

      var materialsModule = createModule("Materials", true, "", "Y");
      materialsModule.permissionTOs.push(createPermission("RESOURCE_MATRLLIST_VIEW", "VIEW", false));

      var materialRegister = createModule("Material Register", true, "materialregistor", "Y");
      materialRegister.permissionTOs.push(createPermission("RESOURCE_MATRLREG_VIEW", "VIEW", false));

      var materialLedger = createModule("Ledger", true, "materialregistor", "Y");
      materialLedger.permissionTOs.push(createPermission("RESOURCE_MATRLLEDGERREGVIEW", "VIEW", false));
      materialRegister.childModules.push(materialLedger);

      var deliverySupplyDetails = createModule("Delivery/Supply Details", true, "materialregistor", "Y");
      deliverySupplyDetails.permissionTOs.push(createPermission("RESOURCE_MATRLDELSUPPDETAILS_VIEW", "VIEW", false));
      deliverySupplyDetails.permissionTOs.push(createPermission("RESOURCE_MATRLDELSUPPDETAILS_ADD", "ADD", false));
      materialRegister.childModules.push(deliverySupplyDetails);

      var projectDockets = createModule("Project Dockets", true, "materialregistor", "Y");
      projectDockets.permissionTOs.push(createPermission("RESOURCE_MATRLPROJDOCK_VIEW", "VIEW", false));
      projectDockets.permissionTOs.push(createPermission("RESOURCE_MATRLPROJDOCK_ADD", "ADD", false));
      materialRegister.childModules.push(projectDockets);

      var storeMaterialsDailyIssueRecords = createModule("Store/Materials Daily Issue Records", true, "materialregistor", "Y");
      storeMaterialsDailyIssueRecords.permissionTOs.push(createPermission("RESOURCE_MATRLSTOREDAILYISSUERCD_VIEW", "VIEW", false));
      materialRegister.childModules.push(storeMaterialsDailyIssueRecords);


      var consumptionRecords = createModule("Consumption Records", true, "materialregistor", "Y");
      consumptionRecords.permissionTOs.push(createPermission("RESOURCE_MATRLCONSUMRECD_VIEW", "VIEW", false));
      materialRegister.childModules.push(consumptionRecords);


      var storeItemsStockOnSiteIntransit = createModule("Store Items Stock OnSite-Intransit", true, "materialregistor", "Y");
      storeItemsStockOnSiteIntransit.permissionTOs.push(createPermission("RESOURCE_MATRLSTORITMSTCKONSITINTRANS_VIEW", "VIEW", false));
      materialRegister.childModules.push(storeItemsStockOnSiteIntransit);

      var materialsStockonSiteStockPiledItems = createModule("Materials Stock on Site-Stock Piled Items", true, "materialregistor", "Y");
      materialsStockonSiteStockPiledItems.permissionTOs.push(createPermission("RESOURCE_MATRLSTCKSITEPILEDITM_VIEW", "VIEW", false));
      materialRegister.childModules.push(materialsStockonSiteStockPiledItems);

      var requestForMaterialTransfer = createModule("Request For Material Transfer", true, "materialregistor", "Y");
      requestForMaterialTransfer.permissionTOs.push(createPermission("RESOURCE_MATRLREQFORMATTRANSFER_VIEW", "VIEW", false));
      requestForMaterialTransfer.permissionTOs.push(createPermission("RESOURCE_MATRLREQFORMATTRANSFER_SUBMIT", "SUBMIT", false));
      materialRegister.childModules.push(requestForMaterialTransfer);

      var approvalForMaterialTransfer = createModule("Approval For Material Transfer", true, "materialregistor", "Y");
      approvalForMaterialTransfer.permissionTOs.push(createPermission("RESOURCE_MATRLAPPRFORTRANSFER_VIEW", "VIEW", false));
      approvalForMaterialTransfer.permissionTOs.push(createPermission("RESOURCE_MATRLAPPRFORTRANSFER_REQFORADDTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalForMaterialTransfer.permissionTOs.push(createPermission("RESOURCE_MATRLAPPRFORTRANSFER_APPROVE", "APPROVE", false));
      approvalForMaterialTransfer.permissionTOs.push(createPermission("RESOURCE_MATRLAPPRFORTRANSFER_REJECT", "REJECT", false));
      materialRegister.childModules.push(approvalForMaterialTransfer);

      materialsModule.childModules.push(materialRegister);
      resourcesModule.childModules.push(materialsModule);

      var fixedAssetsModule = createModule("Immovable Assets", true, "", "Y");
      fixedAssetsModule.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_VIEW", "VIEW", false));

      var immovableAssetsList = createModule("Immovable Assets List", true, "assetregister", "Y");
      immovableAssetsList.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_VIEW", "VIEW", false));
      immovableAssetsList.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ADD", "ADD", false));
      immovableAssetsList.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_DEACTIVATE", "DEACTIVATE", false));

      var purchaseAcquisitionRecords = createModule("Purchase/Acquisition Records", true, "assetregister", "Y");
      purchaseAcquisitionRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ PURCHASE_VIEW", "VIEW", false));
      purchaseAcquisitionRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ PURCHASE_ADD", "ADD", false));
      purchaseAcquisitionRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ PURCHASE_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(purchaseAcquisitionRecords);

      var rentalValueRevenue = createModule("Rental Value/Revenue", true, "assetregister", "Y");
      rentalValueRevenue.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ RENTAL_VIEW", "VIEW", false));
      rentalValueRevenue.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ RENTAL_ADD", "ADD", false));
      rentalValueRevenue.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ RENTAL_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(rentalValueRevenue);


      var assetSaleRecords = createModule("Asset Sale Records", true, "assetregister", "Y");
      assetSaleRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ SALERCDS_VIEW", "VIEW", false));
      assetSaleRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ SALERCDS_ADD", "ADD", false));
      assetSaleRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ SALERCDS_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(assetSaleRecords);

      var mortgaggePayments = createModule("Mortgagge Payments", true, "assetregister", "Y");
      mortgaggePayments.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ MORTGAGE_VIEW", "VIEW", false));
      mortgaggePayments.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ MORTGAGE_ADD", "ADD", false));
      mortgaggePayments.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ MORTGAGE_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(mortgaggePayments);


      var revenuefromAssetSales = createModule("Revenue from Asset Sales", true, "assetregister", "Y");
      revenuefromAssetSales.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ REVENUE_VIEW", "VIEW", false));
      revenuefromAssetSales.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ REVENUE_ADD", "ADD", false));
      revenuefromAssetSales.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ REVENUE_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(revenuefromAssetSales);

      var longTermLeasingRentalHistory = createModule("Long Term Leasing/Rental History", true, "assetregister", "Y");
      longTermLeasingRentalHistory.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ LONGTERM_VIEW", "VIEW", false));
      longTermLeasingRentalHistory.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ LONGTERM_ADD", "ADD", false));
      longTermLeasingRentalHistory.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ LONGTERM_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(longTermLeasingRentalHistory);

      var casualOccupancyRecords = createModule("Short Term/Casual Occupancy Records & Rental Returns", true, "assetregister", "Y");
      casualOccupancyRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ SHORTTERM_VIEW", "VIEW", false));
      casualOccupancyRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ SHORTTERM_ADD", "ADD", false));
      casualOccupancyRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ SHORTTERM_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(casualOccupancyRecords);


      var revenueActualReturns = createModule("Long Term Lease:Rental/Revenue-Actual Returns", true, "assetregister", "Y");
      revenueActualReturns.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ LTERMACTL_VIEW", "VIEW", false));
      revenueActualReturns.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ LTERMACTL_ADD", "ADD", false));
      revenueActualReturns.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ LTERMACTL_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(revenueActualReturns);

      var carParkingAndTollCollections = createModule("Car Parking And Toll Collections - Actual Revenue", true, "assetregister", "Y");
      carParkingAndTollCollections.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ CARPARKING_VIEW", "VIEW", false));
      carParkingAndTollCollections.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ CARPARKING_ADD", "ADD", false));
      carParkingAndTollCollections.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ CARPARKING_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(carParkingAndTollCollections);


      var occupancyUtilisationRecords = createModule("Occupancy/Utilisation Records-Statistics", true, "assetregister", "Y");
      occupancyUtilisationRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ OCCUPANCY_VIEW", "VIEW", false));
      occupancyUtilisationRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ OCCUPANCY_ADD", "ADD", false));
      occupancyUtilisationRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ OCCUPANCY_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(occupancyUtilisationRecords);

      var periodicalScheduleMaintenanceRecords = createModule("Periodical and Schedule MaintenanceRecords", true, "assetregister", "Y");
      periodicalScheduleMaintenanceRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ PERIODICAL_VIEW", "VIEW", false));
      periodicalScheduleMaintenanceRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ PERIODICAL_ADD", "ADD", false));
      periodicalScheduleMaintenanceRecords.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ PERIODICAL_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(periodicalScheduleMaintenanceRecords);

      var repairsandNonSchedule = createModule("Repairs and NonSchedule", true, "assetregister", "Y");
      repairsandNonSchedule.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ REPAIRS_VIEW", "VIEW", false));
      repairsandNonSchedule.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ REPAIRS_ADD", "ADD", false));
      repairsandNonSchedule.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ REPAIRS_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(repairsandNonSchedule);

      var assetLifeStatus= createModule("Asset Life Status", true, "assetregister", "Y");
      assetLifeStatus.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ ASETLIFE_VIEW", "VIEW", false));
      assetLifeStatus.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ ASETLIFE_ADD", "ADD", false));
      assetLifeStatus.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ ASETLIFE_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(assetLifeStatus);

      var assetCostandValueStatus= createModule("Asset Cost and Value Status", true, "assetregister", "Y");
      assetCostandValueStatus.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ ASETCOST_VIEW", "VIEW", false));
      assetCostandValueStatus.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ ASETCOST_ADD", "ADD", false));
      assetCostandValueStatus.permissionTOs.push(createPermission("RESOURCES_IMMVBLEASSETS_LIST_ ASETCOST_DEACTIVATE", "DEACTIVATE", false));
      immovableAssetsList.childModules.push(assetCostandValueStatus);

      fixedAssetsModule.childModules.push(immovableAssetsList);
      resourcesModule.childModules.push(fixedAssetsModule);

      return resourcesModule;
    }

    function getRequestapprovalModule() {
      var requestsApprovals = createModule("Requests & Approvals", true, "", 'Y');
      requestsApprovals.permissionTOs.push(createPermission("REQANDAPRVL_VIEW", "VIEW", false));


      var requestModule = createModule("Request", true, "", "Y");
      requestModule.permissionTOs.push(createPermission("REQUEST_FORALLMODULE_VIEW", "VIEW", false));

      var employeeTransferRequest = createModule("Employee Transfer", true, "empreqtransfer1", "Y");
      employeeTransferRequest.permissionTOs.push(createPermission("REQUEST_EMPTRNASFER_VIEW", "VIEW", false));
      employeeTransferRequest.permissionTOs.push(createPermission("REQUEST_EMPTRNASFER_SUBMIT", "SUBMIT", false));
      requestModule.childModules.push(employeeTransferRequest);

      var plantTransferRequest = createModule("Plant Transfer", true, "planttransferrequest1", "Y");
      plantTransferRequest.permissionTOs.push(createPermission("REQUEST_PLANTTRANSFER_VIEW", "VIEW", false));
      plantTransferRequest.permissionTOs.push(createPermission("REQUEST_PLANTTRANSFER_SUBMIT", "SUBMIT", false));
      requestModule.childModules.push(plantTransferRequest);

      var materialsTransferRequest = createModule("Materials Transfer", true, "materialtransferrequest1", "Y");
      materialsTransferRequest.permissionTOs.push(createPermission("REQUEST_MATERIALTRANSFER_VIEW", "VIEW", false));
      materialsTransferRequest.permissionTOs.push(createPermission("REQUEST_MATERIALTRANSFER_SUBMIT", "SUBMIT", false));
      requestModule.childModules.push(materialsTransferRequest);


      var stage1Request = createModule("Procurement Stage 1", true, "internalApproval/request", "Y");
      stage1Request.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1REQUEST_VIEW", "VIEW", false));
      stage1Request.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1REQUEST_ADD", "ADD", false));
      stage1Request.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1REQUESTREFDOCUMENT_ADD", "REF DOCUMENT", false));

      var preContractListManPower = createModule("Man Power", true, "internalApproval/request", "Y");
      preContractListManPower.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_VIEW", "VIEW", false));
      preContractListManPower.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_ADD", "ADD", false));
      preContractListManPower.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_DEACTIVE", "DEACTIVE", false));
      preContractListManPower.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MANPOWER_SUBMIT", "SUBMIT", false));
      stage1Request.childModules.push(preContractListManPower);

      var preContractListMaterials = createModule("Materials", true, "internalApproval/request", "Y");
      preContractListMaterials.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_VIEW", "VIEW", false));
      preContractListMaterials.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_ADD", "ADD", false));
      preContractListMaterials.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_DEACTIVE", "DEACTIVE", false));
      preContractListMaterials.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1MATERIALS_SUBMIT", "SUBMIT", false));
      stage1Request.childModules.push(preContractListMaterials);

      var preContractListPlants = createModule("Plants", true, "internalApproval/request", "Y");
      preContractListPlants.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1PLANTS_VIEW", "VIEW", false));
      preContractListPlants.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1PLANTS_ADD", "ADD", false));
      preContractListPlants.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1PLANTS_DEACTIVE", "DEACTIVE", false));
      preContractListPlants.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1PLANTS_SUBMIT", "SUBMIT", false));
      stage1Request.childModules.push(preContractListPlants);

      var preContractListServices = createModule("Services", true, "internalApproval/request", "Y");
      preContractListServices.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SERVICES_VIEW", "VIEW", false));
      preContractListServices.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SERVICES_ADD", "ADD", false));
      preContractListServices.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SERVICES_DEACTIVE", "DEACTIVE", false));
      preContractListServices.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SERVICES_SUBMIT", "SUBMIT", false));
      stage1Request.childModules.push(preContractListServices);

      var preContractListProjectSubContract = createModule("Project Sub Contract", true, "internalApproval/request", "Y");
      preContractListProjectSubContract.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_VIEW", "VIEW", false));
      preContractListProjectSubContract.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_ADD", "ADD", false));
      preContractListProjectSubContract.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_DEACTIVE", "DEACTIVE", false));
      preContractListProjectSubContract.permissionTOs.push(createPermission("REQUEST_REQPROC_INTRNLSTAGE1SUBCONTRACT_SUBMIT", "SUBMIT", false));
      stage1Request.childModules.push(preContractListProjectSubContract);
      requestModule.childModules.push(stage1Request);

      var stage2Request = createModule("Procurement Stage 2", true, "", "Y");
      stage2Request.permissionTOs.push(createPermission("REQUEST_REQPROC_EXTRNLSTAGE2REQUEST_VIEW", "VIEW", false));
      stage2Request.permissionTOs.push(createPermission("REQUEST_REQPROC_EXTRNLSTAGE2REQUEST_SCHEDULEOFITEMS", "SCHEDULE OF ITEMS", false));
      stage2Request.permissionTOs.push(createPermission("REQUEST_REQPROC_EXTRNLSTAGE2REQUEST_APPRVDETAILS", "APPROVER DETAILS", false));
      stage2Request.permissionTOs.push(createPermission("REQUEST_REQPROC_EXTRNLSTAGE2REQUEST_REFDOCUMENT_ADD", "REF DOCUMENT", false));

      var bidAnalysismodule = createModule("BID ANALYSIS", true, "externalApproval/request", "Y");
      bidAnalysismodule.permissionTOs.push(createPermission("REQUEST_REQPROC_EXTRNLSTAGE2REQUEST_ADDBIDDERS_VIEW", "VIEW", false));
      bidAnalysismodule.permissionTOs.push(createPermission("REQUEST_REQPROC_EXTRNLSTAGE2REQUEST_SENDFORAPPROVAL", "SEND FOR APPROVAL", false));
      stage2Request.childModules.push(bidAnalysismodule);
      requestModule.childModules.push(stage2Request);

      var leaveRequest = createModule("Leave Request", true, "empregister", "Y");
      leaveRequest.permissionTOs.push(createPermission("REQUEST_EMPLEAVEREQUEST_VIEW", "VIEW", false));
      leaveRequest.permissionTOs.push(createPermission("REQUEST_EMPLEAVEREQUEST_SUBMIT", "SUBMIT", false));
      requestModule.childModules.push(leaveRequest);
      requestsApprovals.childModules.push(requestModule);


      var approvalsModule = createModule("Approvals", true, "", "Y");
      approvalsModule.permissionTOs.push(createPermission("APPROVE_FORALLMODULE_VIEW", "VIEW", false));

      var approveWorkDairy = createModule("Work Dairy", true, "approveworkdiary", "Y");
      approveWorkDairy.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_VIEW", "VIEW", false));
      approveWorkDairy.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_ADD", "ADD", false));
      approveWorkDairy.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_APPROVE", "APPROVE", false));
      approveWorkDairy.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_WRKDIRY_APRVEWRKDIRY_REQUESTFORADDTIONALTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalsModule.childModules.push(approveWorkDairy);

      var approveTimeSheet = createModule("Approve Time Sheet", true, "approvetimesheet", "Y");
      approveTimeSheet.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_TIMSHET_APRVETIMESHET_VIEW", "VIEW", false));
      approveTimeSheet.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_TIMSHET_APRVETIMESHET_ADD", "ADD", false));
      approveTimeSheet.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_TIMSHET_APRVETIMESHET_SUBMIT", "SUBMIT", false));
      approveTimeSheet.permissionTOs.push(createPermission("APPROVE_ASBUILTRCRD_TIMSHET_APRVETIMESHET_NOTFICATIONFORAPPROVAL", "NOTFICATION FOR APPROVAL", false));
      approvalsModule.childModules.push(approveTimeSheet);

      var approvalforTransfer = createModule("Employee Transfer", true, "emptransferapprove1", "Y");
      approvalforTransfer.permissionTOs.push(createPermission("APPROVE_EMPAPPROVALTRANSFER_VIEW", "VIEW", false));
      approvalforTransfer.permissionTOs.push(createPermission("APPROVE_EMPAPPROVALTRANSFER_REQFORADDTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalforTransfer.permissionTOs.push(createPermission("APPROVE_EMPAPPROVALTRANSFER_APPROVE", "APPROVE", false));
      approvalforTransfer.permissionTOs.push(createPermission("APPROVE_EMPAPPROVALTRANSFER_REJECT", "REJECT", false));
      approvalsModule.childModules.push(approvalforTransfer);

      var approvalForTransfer = createModule("Plant Transfer", true, "planttransferapprove1", "Y");
      approvalForTransfer.permissionTOs.push(createPermission("APPROVE_PLANTAPPRFORTRANSFER_VIEW", "VIEW", false));
      approvalForTransfer.permissionTOs.push(createPermission("APPROVE_PLANTAPPRFORTRANSFER_REQFORADDTTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalForTransfer.permissionTOs.push(createPermission("APPROVE_PLANTAPPRFORTRANSFER_APPROVE", "APPROVE", false));
      approvalForTransfer.permissionTOs.push(createPermission("APPROVE_PLANTAPPRFORTRANSFER_REJECT", "REJECT", false));
      approvalsModule.childModules.push(approvalForTransfer);

      var approvalForMaterialTransfer = createModule("Materials Transfer", true, "materialtransferapproval1", "Y");
      approvalForMaterialTransfer.permissionTOs.push(createPermission("APPROVE_MATRLAPPRFORTRANSFER_VIEW", "VIEW", false));
      approvalForMaterialTransfer.permissionTOs.push(createPermission("APPROVE_MATRLAPPRFORTRANSFER_REQFORADDTIME", "REQUEST FOR ADDTIONAL TIME", false));
      approvalForMaterialTransfer.permissionTOs.push(createPermission("APPROVE_MATRLAPPRFORTRANSFER_APPROVE", "APPROVE", false));
      approvalForMaterialTransfer.permissionTOs.push(createPermission("APPROVE_MATRLAPPRFORTRANSFER_REJECT", "REJECT", false));
      approvalsModule.childModules.push(approvalForMaterialTransfer);

      var stage1Approval = createModule("Procurement Stage 1", true, "internalApproval/approval", "Y");
      stage1Approval.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APRVL_VIEW", "VIEW", false));
      stage1Approval.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APRVL_ADD", "ADD", false));
      stage1Approval.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APRVLREFDOCUMENT_ADD", "REF DOCUMENT", false));

      var preContractListStag1ApprovalManPower = createModule("Man Power", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_VIEW", "VIEW", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_ADD", "ADD", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalManPower.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANPOWER_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalManPower);

      var preContractListStag1ApprovalMaterials = createModule("Materials", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_VIEW", "VIEW", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_ADD", "ADD", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalMaterials.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALMANTERIAL_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalMaterials);

      var preContractListStag1ApprovalPlants = createModule("Plants", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_VIEW", "VIEW", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_ADD", "ADD", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalPlants.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPLANT_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalPlants);

      var preContractListStag1ApprovalServices = createModule("Services", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_VIEW", "VIEW", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_ADD", "ADD", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalServices.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALSERVICES_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalServices);

      var preContractListStag1ApprovalProjectSubContract = createModule("Project Sub Contract", true, "internalApproval/approval", "Y");
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_VIEW", "VIEW", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ADD", "ADD", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_DEACTIVE", "DEACTIVE", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_ONHOLD", "ON HOLD", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_APPROVAL", "APPROVAL", false));
      preContractListStag1ApprovalProjectSubContract.permissionTOs.push(createPermission("APPROVE_INTRNLSTAGE1APPROVALPRESUBCONTRACT_REJECT", "REJECT", false));
      stage1Approval.childModules.push(preContractListStag1ApprovalProjectSubContract);
      approvalsModule.childModules.push(stage1Approval);

      var stage2Approval = createModule("Procurement Stage 2", true, "externalApproval/approval", "Y");
      stage2Approval.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APRVL_VIEW", "VIEW", false));
      stage2Approval.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_SCHEDULEOFITEMS", "SCHEDULE OF ITEMS", false));
      stage2Approval.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_APPRVDETAILS", "APPROVER DETAILS", false));
      stage2Approval.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_REFDOCUMENT_ADD", "REF DOCUMENT", false));
      stage2Approval.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_APPROVE", "APPROVE", false));

      var stage2approvebidAnalysismodule = createModule("BID ANALYSIS", true, "externalApproval/approval", "Y");
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_ADDBIDDERS_VIEW", "VIEW", false));
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_REJECT", "REJECT", false));
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_SENDBACKTOREQUESTOR", "SEND BACK TO REQUESTOR", false));
      stage2approvebidAnalysismodule.permissionTOs.push(createPermission("APPROVE_EXTRNLSTAGE2APPROVAL_ONHOLD", "ON HOLD", false));
      stage2Approval.childModules.push(stage2approvebidAnalysismodule);
      approvalsModule.childModules.push(stage2Approval);

      var leaveApproval = createModule("Leave Approval", true, "empregister", "Y");
      leaveApproval.permissionTOs.push(createPermission("APPROVE_EMPLEAVEAPPROVAL_VIEW", "VIEW", false));
      leaveApproval.permissionTOs.push(createPermission("APPROVE_EMPLEAVEAPPROVAL_REQFORADDTIME", "REQUEST FOR ADDITIONAL TIME", false));
      leaveApproval.permissionTOs.push(createPermission("APPROVE_EMPLEAVEAPPROVAL_APPROVE", "APPROVE", false));
      leaveApproval.permissionTOs.push(createPermission("APPROVE_EMPLEAVEAPPROVAL_NOTAPPROVE", "NOT APPROVE", false));
      approvalsModule.childModules.push(leaveApproval);


      requestsApprovals.childModules.push(approvalsModule);

      return requestsApprovals;
    }


    function getNotficationModule() {
      var notificationModule = createModule("Notifications", true, "", 'Y');
      notificationModule.permissionTOs.push(createPermission("NOTIFICATION_VIEW", "VIEW", false));


      var workDiary = createModule("Work Diary", true, "notifyworkdairy", "Y");
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_VIEW", "VIEW", false));
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_SUBMISSION", "SUBMISSION", false));
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_APPROVED", "APPROVED", false));
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_REJECT", "REJECT", false));
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_ADDTIMEFORREQUEST", "ADDTIONAL TIME FOR REQUEST", false));
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_ADDTIMEFORAPPROVED", "ADDTIONAL TIME FOR APPROVED", false));
      workDiary.permissionTOs.push(createPermission("NOTIFICATION_WORKDAIRY_CLIENTAPPROVED", "CLIENT APPROVED", false));
      notificationModule.childModules.push(workDiary);

      var timeSheet = createModule("Time Sheet", true, "notifytimesheet", "Y");
      timeSheet.permissionTOs.push(createPermission("NOTIFICATION_TIMESHEET_VIEW", "VIEW", false));
      timeSheet.permissionTOs.push(createPermission("NOTIFICATION_TIMESHEET_SUBMISSION", "SUBMISSION", false));
      timeSheet.permissionTOs.push(createPermission("NOTIFICATION_TIMESHEET_APPROVED", "APPROVED", false));
      timeSheet.permissionTOs.push(createPermission("NOTIFICATION_TIMESHEET_ADDTIMEFORREQUEST", "ADDTIONAL TIME FOR REQUEST", false));
      timeSheet.permissionTOs.push(createPermission("NOTIFICATION_TIMESHEET_ADDTIMEFORAPPROVED", "ADDTIONAL TIME FOR APPROVED", false));
      notificationModule.childModules.push(timeSheet);

      var attendance = createModule("Attendance", true, "notifyattendancerecords", "Y");
      attendance.permissionTOs.push(createPermission("NOTIFICATION_ATTENDANCE_VIEW", "VIEW", false));
      attendance.permissionTOs.push(createPermission("NOTIFICATION_ATTENDANCE_SUBMISSION", "SUBMISSION", false));
      attendance.permissionTOs.push(createPermission("NOTIFICATION_ATTENDANCE_APPROVED", "APPROVED", false));
      notificationModule.childModules.push(attendance);

      var procurement = createModule("Procurement", true, "notifyprocurement", "Y");
      procurement.permissionTOs.push(createPermission("NOTIFICATION_PROCUREMENT_VIEW", "VIEW", false));
      procurement.permissionTOs.push(createPermission("NOTIFICATION_PROCUREMENT_SUBMISSION", "SUBMISSION", false));
      procurement.permissionTOs.push(createPermission("NOTIFICATION_PROCUREMENT_APPROVED", "APPROVED", false));
      procurement.permissionTOs.push(createPermission("NOTIFICATION_PROCUREMENT_REJECT", "REJECT", false));
      notificationModule.childModules.push(procurement);

      var employeeTransfer = createModule("Employee Transfer", true, "notifyemptransfer", "Y");
      employeeTransfer.permissionTOs.push(createPermission("NOTIFICATION_EMPLOYEETRANS_VIEW", "VIEW", false));
      employeeTransfer.permissionTOs.push(createPermission("NOTIFICATION_EMPLOYEETRANS_SUBMISSION", "SUBMISSION", false));
      employeeTransfer.permissionTOs.push(createPermission("NOTIFICATION_EMPLOYEETRANS_APPROVED", "APPROVED", false));
      employeeTransfer.permissionTOs.push(createPermission("NOTIFICATION_EMPLOYEETRANS_REJECT", "REJECT", false));
      employeeTransfer.permissionTOs.push(createPermission("NOTIFICATION_EMPLOYEETRANS_ADDTIMEFORREQUEST", "ADDTIONAL TIME FOR REQUEST", false));
      employeeTransfer.permissionTOs.push(createPermission("NOTIFICATION_EMPLOYEETRANS_ADDTIMEFORAPPROVED", "ADDTIONAL TIME FOR APPROVED", false));
      notificationModule.childModules.push(employeeTransfer);

      var plantTransfer = createModule("Plant Transfer", true, "notifyplanttransfer", "Y");
      plantTransfer.permissionTOs.push(createPermission("NOTIFICATION_PLANTTRANS_VIEW", "VIEW", false));
      plantTransfer.permissionTOs.push(createPermission("NOTIFICATION_PLANTTRANS_SUBMISSION", "SUBMISSION", false));
      plantTransfer.permissionTOs.push(createPermission("NOTIFICATION_PLANTTRANS_APPROVED", "APPROVED", false));
      plantTransfer.permissionTOs.push(createPermission("NOTIFICATION_PLANTTRANS_REJECT", "REJECT", false));
      plantTransfer.permissionTOs.push(createPermission("NOTIFICATION_PLANTTRANS_ADDTIMEFORREQUEST", "ADDTIONAL TIME FOR REQUEST", false));
      plantTransfer.permissionTOs.push(createPermission("NOTIFICATION_PLANTTRANS_ADDTIMEFORAPPROVED", "ADDTIONAL TIME FOR APPROVED", false));
      notificationModule.childModules.push(plantTransfer);

      var materialsTransfer = createModule("Materials Transfer", true, "notifymaterialtransfer", "Y");
      materialsTransfer.permissionTOs.push(createPermission("NOTIFICATION_MATERIALTRANS_VIEW", "VIEW", false));
      materialsTransfer.permissionTOs.push(createPermission("NOTIFICATION_MATERIALTRANS_SUBMISSION", "SUBMISSION", false));
      materialsTransfer.permissionTOs.push(createPermission("NOTIFICATION_MATERIALTRANS_APPROVED", "APPROVED", false));
      materialsTransfer.permissionTOs.push(createPermission("NOTIFICATION_MATERIALTRANS_REJECT", "REJECT", false));
      materialsTransfer.permissionTOs.push(createPermission("NOTIFICATION_MATERIALTRANS_ADDTIMEFORREQUEST", "ADDTIONAL TIME FOR REQUEST", false));
      materialsTransfer.permissionTOs.push(createPermission("NOTIFICATION_MATERIALTRANS_ADDTIMEFORAPPROVED", "ADDTIONAL TIME FOR APPROVED", false));
      notificationModule.childModules.push(materialsTransfer);


      var leavereqapp = createModule("Leave Request & Approval", true, "notifyempleave", "Y");
      leavereqapp.permissionTOs.push(createPermission("NOTIFICATION_LEAVEREQAPPR_VIEW", "VIEW", false));
      leavereqapp.permissionTOs.push(createPermission("NOTIFICATION_LEAVEREQAPPR_SUBMISSION", "SUBMISSION", false));
      leavereqapp.permissionTOs.push(createPermission("NOTIFICATION_LEAVEREQAPPR_APPROVED", "APPROVED", false));
      leavereqapp.permissionTOs.push(createPermission("NOTIFICATION_LEAVEREQAPPR_REJECT", "REJECT", false));
      leavereqapp.permissionTOs.push(createPermission("NOTIFICATION_LEAVEREQAPPR_ADDTIMEFORREQUEST", "ADDTIONAL TIME FOR REQUEST", false));
      leavereqapp.permissionTOs.push(createPermission("NOTIFICATION_LEAVEREQAPPR_ADDTIMEFORAPPROVED", "ADDTIONAL TIME FOR APPROVED", false));
      notificationModule.childModules.push(leavereqapp);

      return notificationModule;
    }

    function getHelpTutorials() {
      var helpTutorialsModule = createModule("Help & Tutorials", true, "", 'Y');
      helpTutorialsModule.permissionTOs.push(createPermission("HELPANDTUTORIALS_VIEW", "VIEW", false));

      var help = createModule("Help & Tutorials", true, "help", "Y");
      help.permissionTOs.push(createPermission("HELPANDTUTRL_VIEW", "VIEW", false));

      helpTutorialsModule.childModules.push(help);

      var aboutProjectonTrack = createModule("About Project on Track", true, "rajutech.com", "Y");
      aboutProjectonTrack.permissionTOs.push(createPermission("ABOUTPROJONTRACK_VIEW", "VIEW", false));

      helpTutorialsModule.childModules.push(aboutProjectonTrack);


      return helpTutorialsModule;
    }
  function getJSONModules() {

    return [
      /* removed
      {
        "moduleId": 408,
        "moduleName": "Finance",
        "moduleDispOrder": 8,
        "moduleParentId": null,
        "rootFlag": false,
        "tabValue": false,
        "expand": false,
        "moduleURLValue": "",
        "moduleType": "Y",
        "childModules": [
        ],
        "permissionTOs": [
          {
            "perId": 8,
            "moduleId": 408,
            "rolePerId": null,
            "roleId": 366,
            "displayOder": 1,
            "actionId": 1,
            "actionName": "VIEW",
            "permission": false,
            "permissionKey": "FINANCE_VIEW"
          }
        ]
      },
      {
        "moduleId": 409,
        "moduleName": "Dash Boards",
        "moduleDispOrder": 9,
        "moduleParentId": null,
        "rootFlag": false,
        "tabValue": false,
        "expand": false,
        "moduleURLValue": "",
        "moduleType": "Y",
        "childModules": [
        ],
        "permissionTOs": [
          {
            "perId": 9,
            "moduleId": 409,
            "rolePerId": null,
            "roleId": 366,
            "displayOder": 1,
            "actionId": 1,
            "actionName": "VIEW",
            "permission": false,
            "permissionKey": "DASHBOARD_VIEW"
          }
        ]
      },
      {
        "moduleId": 410,
        "moduleName": "Reports",
        "moduleDispOrder": 10,
        "moduleParentId": null,
        "rootFlag": false,
        "tabValue": false,
        "expand": false,
        "moduleURLValue": "",
        "moduleType": "Y",
        "childModules": [
          {
            "moduleId": 1020,
            "moduleName": "Time Sheet",
            "moduleDispOrder": 3,
            "moduleParentId": 410,
            "rootFlag": true,
            "tabValue": false,
            "expand": false,
            "moduleURLValue": null,
            "moduleType": "Y",
            "childModules": [
              {
                "moduleId": 1021,
                "moduleName": "Submission and Approval Status",
                "moduleDispOrder": 2,
                "moduleParentId": 1020,
                "rootFlag": true,
                "tabValue": false,
                "expand": false,
                "moduleURLValue": "timesheetsubmitappr",
                "moduleType": "Y",
                "childModules": [
                ],
                "permissionTOs": [
                  {
                    "perId": 2234,
                    "moduleId": 1021,
                    "rolePerId": null,
                    "roleId": 366,
                    "displayOder": 1,
                    "actionId": 1,
                    "actionName": "VIEW",
                    "permission": false,
                    "permissionKey": "REPTS_TIMESHEET_SUBMIT_APPR_VIEW"
                  },
                  {
                    "perId": 2235,
                    "moduleId": 1021,
                    "rolePerId": null,
                    "roleId": 366,
                    "displayOder": 2,
                    "actionId": 2,
                    "actionName": "ADD",
                    "permission": false,
                    "permissionKey": "REPTS_TIMESHEET_SUBMIT_APPR_ADD"
                  },
                  {
                    "perId": 2236,
                    "moduleId": 1021,
                    "rolePerId": null,
                    "roleId": 366,
                    "displayOder": 3,
                    "actionId": 3,
                    "actionName": "DEACTIVATE",
                    "permission": false,
                    "permissionKey": "REPTS_TIMESHEET_SUBMIT_APPR_DELETE"
                  },
                  {
                    "perId": 2237,
                    "moduleId": 1021,
                    "rolePerId": null,
                    "roleId": 366,
                    "displayOder": 4,
                    "actionId": 4,
                    "actionName": "APPROVE",
                    "permission": false,
                    "permissionKey": "REPTS_TIMESHEET_SUBMIT_APPR_APPROVE"
                  }
                ]
              }
            ],
            "permissionTOs": [
            ]
          }
        ],
        "permissionTOs": [
          {
            "perId": 10,
            "moduleId": 410,
            "rolePerId": null,
            "roleId": 366,
            "displayOder": 1,
            "actionId": 1,
            "actionName": "VIEW",
            "permission": false,
            "permissionKey": "REPORT_VIEW"
          }
        ]
      },
      */
    ];
  }
});
