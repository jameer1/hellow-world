package com.rjtech.admin.constans;

import java.io.Serializable;

public class AdminURLConstants implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -923474679747855169L;

    public final static String USER_PARH_URL = "/app/user/";
    public final static String ROLE_PARH_URL = "/app/auth/";
    public final static String GET_MODULES_FOR_CLIENTS = "getModulesForClients";
    public final static String GET_MODULES_BY_PARENT = "getModulesByParent";
    public final static String GET_TAB_PERMISSION_BY_MODULE = "getTabPermissionsByModule";
    public final static String GET_OBJECTS_BY_USER = "getModulesByUser";
    public final static String GET_MODULES_BY_ROLE = "getModulesByRole";

    public final static String GET_MODULES_BY_ROLE_FOR_CLIENTS = "getRolePermissionsForClients";
    public final static String GET_ACTIONS = "getActions";
    public final static String SAVE_MODULE = "saveModule";
    public final static String SAVE_ROLE = "saveRole";
    public final static String DELETE_ROLES = "deleteRoles";
    public final static String SAVE_ROLE_PERMISSION = "saveRolePermissions";
    public final static String GET_ROLE_PERMISSION = "getRolePermissions";
    public final static String GET_ROLE_BY_USER = "getRoleByUser";
    public final static String GET_PERMISSION_KEYS = "getPermissionKeys";
    public final static String DELETE_MODULE = "deleteModule";
    public static final String DEACTIVATE_MODULE = "deactivateModules";
    public final static String GET_USERS = "getUsers";
    public final static String GET_VENDOR_USERS = "getVendorUsers";
    public final static String GET_USER_PROJECTS = "getUserProjects";
    public final static String SAVE_USER_PROJECTS = "saveUserProjects";
    public final static String GET_CLIENTS = "getClients";
    public final static String SAVE_CLIENT = "saveClient";
    public final static String GET_ROLES = "getRoles";
    public final static String FID_BY_USER_ID = "findByUserId";
    public final static String SAVE_USER = "saveUser";
    public final static String SAVE_VENDOR_USER = "saveVendorUser";
    public final static String UPDATE_USER = "updateUser";
    public final static String DELETE_USER = "deleteUser";
    public final static String ACTIVATE_USER = "activateUser";
    public final static String DELETE_CLIENT = "deleteClient";
    public final static String GET_EMAIL_SETTINGS = "getEmailSettings";
    public final static String SAVE_EMAIL_SETTINGS = "saveEmailSettings";
    public final static String DEACTIVATE_EMAIL_SETTINGS = "deactivateEmailSettings";
    public final static String USER_POPUP_ONLOAD = "userOnLoad";
    public final static String GET_USERS_BY_ROLEID = "getUsersByRoleId";
    public final static String GET_USER_PERMISSSIONS = "getUserPermissions";
    public final static String GET_USERS_BY_MODULE_PERMISSION = "getUsersByModulePermission";

    public final static String GET_USERS_BY_CLIENT_ID = "getUsersByClientId";
    public final static String GET_CLIENT_BY_EMAIL = "getClientByEmail";
    public final static String GET_CLIENT_BY_ID = "getClientById";
    public final static String GET_CLIENT_MAIL_TEMPLATE = "getClientMailTemplate";

    public final static String SUCCESS = "Success";

    public final static String FAILURE = "Failure";

    // ================================AdminMapUrlConstants==================================================

    public final static String GET_ROLESERVICE_MAP = "getRoleServiceMap";
    public final static String GET_USERSERVICE_MAP = "getUserServiceMap";
    public final static String GET_CLIENTSERVICE_MAP = "getClientServiceMap";
    public final static String GET_EMAILSERVICE_MAP = "getEmailServiceMap";

    public final static String SAVE_DEFAULT_CLIENT_DATA = "saveDefaultClientData";

}
