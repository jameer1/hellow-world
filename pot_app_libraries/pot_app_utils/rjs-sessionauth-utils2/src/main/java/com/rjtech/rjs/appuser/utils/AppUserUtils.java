package com.rjtech.rjs.appuser.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUserUtils {

    private static Logger log = LoggerFactory.getLogger(AppUserUtils.class);

    public static Long getUserId() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");
            Integer defaultValue = new Integer(0);
            return new Long(defaultValue.intValue());
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return appUserDetails.getUserId();
    }

    public static String getUserName() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");

            return new String("C000000");
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return appUserDetails.getUsername();

    }
    
    public static String getName() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");

            return new String("C000000");
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return appUserDetails.getName();

    }

    public static String getEmail() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");

            return new String("C000000");
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return appUserDetails.getEmail();

    }

    public static String getToken() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");

            return new String("000000");
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return appUserDetails.getToken();
    }

    public static Long getClientId() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");
            Integer defaultValue = new Integer(0);
            return new Long(defaultValue.intValue());
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return appUserDetails.getClientId();
    }

    public static String getClientCode() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");

            return new String("C000000");
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return appUserDetails.getClientCode();
    }

    public static List<Long> getRoleIds() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");
            return new ArrayList<Long>();
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return appUserDetails.getRoleIds();
    }

    public static String getDisplayRole() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");

            return new String("C000000");
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return appUserDetails.getDisplayRole();

    }

    public static Timestamp getSysdate() {
        return new Timestamp(new Date().getTime());
    }

    public static Long getAdminClientId() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("  Authentication details are empty so returning   ");
            Integer defaultValue = new Integer(0);
            return new Long(defaultValue.intValue());
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return appUserDetails.getAdminClientId();
    }

}
