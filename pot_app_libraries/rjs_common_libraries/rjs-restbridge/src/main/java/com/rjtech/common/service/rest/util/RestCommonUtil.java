package com.rjtech.common.service.rest.util;

import java.util.List;

public class RestCommonUtil {

    public static boolean isListHasData(List inputData) {

        boolean listisNotNull = false;

        if (inputData != null && inputData.size() > 0) {
            listisNotNull = true;
        }
        return listisNotNull;
    }

    /**
     * Checks if either null or empty or blank String
     * 
     * @param str
     * @return flag
     */
    public static boolean isBlankStr(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * Checks if not either null or empty or blank String
     * 
     * @param str
     * @return flag
     */
    public static boolean isNotBlankStr(String str) {
        return !isBlankStr(str);
    }

}
