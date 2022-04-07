package com.rjtech.rjs.persistence.providers;

import java.util.ArrayList;

public final class ModuleCodeProvider {
    private static final ThreadLocal<ArrayList<String>> MODULE_TYPE_HOLDER = new ThreadLocal<ArrayList<String>>();

    private ModuleCodeProvider() {
    }

    public static void setModuleCode(String moduleCode) {
        if (MODULE_TYPE_HOLDER.get() == null) {
            MODULE_TYPE_HOLDER.set(new ArrayList<String>());
        }
        MODULE_TYPE_HOLDER.get().add(moduleCode);
    }

    public static String getCode() {
        if (MODULE_TYPE_HOLDER.get() == null) {
            return null;
        }
        return MODULE_TYPE_HOLDER.get().get(MODULE_TYPE_HOLDER.get().size() - 1);
    }

    public static void clearModuleCode() {
        MODULE_TYPE_HOLDER.get().remove(MODULE_TYPE_HOLDER.get().size() - 1);
    }

    public static void clearAllModuleCodes() {
        MODULE_TYPE_HOLDER.get().clear();
        MODULE_TYPE_HOLDER.set(null);
    }

    public static boolean isParent() {
        return (MODULE_TYPE_HOLDER.get() == null || MODULE_TYPE_HOLDER.get().isEmpty());
    }

}
