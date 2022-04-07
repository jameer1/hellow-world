package com.rjtech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "potep")
public class POTEPConfiguration {

    private String centralLibUrl;
    private String projectLibUrl;
    private String adminUrl;
    private String projsettingsUrl;
    private String procurementUrl;
    private String timeManagementUrl;
    private String registerUrl;
    private String financeUrl;
    private String notificationUrl;
    private String reportsUrl;

    @Bean
    public String mwcentralLibUrl() {
        return getCentralLibUrl();
    }

    @Bean
    public String mwfinanceUrl() {
        return getFinanceUrl();
    }

    @Bean
    public String mwnotificationUrl() {
        return getNotificationUrl();
    }

    @Bean
    public String mwprojectLibUrl() {
        return getProjectLibUrl();
    }

    @Bean
    public String mwadminUrl() {
        return getAdminUrl();
    }

    @Bean
    public String mwprojsettingsUrl() {
        return getProjsettingsUrl();
    }

    @Bean
    public String mwprocurementUrl() {
        return getProcurementUrl();
    }

    @Bean
    public String timeManagementUrl() {
        return getTimeManagementUrl();
    }

    @Bean
    public String mwRegisterUrl() {
        return getRegisterUrl();
    }

    public String getReportsUrl() {
        return reportsUrl;
    }

    public void setReportsUrl(String reportsUrl) {
        this.reportsUrl = reportsUrl;
    }

    @Bean
    public String mwReportsUrl() {
        return getReportsUrl();
    }

    public String getCentralLibUrl() {
        return centralLibUrl;
    }

    public void setCentralLibUrl(String centralLibUrl) {
        this.centralLibUrl = centralLibUrl;
    }

    public String getProjectLibUrl() {
        return projectLibUrl;
    }

    public void setProjectLibUrl(String projectLibUrl) {
        this.projectLibUrl = projectLibUrl;
    }

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getProjsettingsUrl() {
        return projsettingsUrl;
    }

    public void setProjsettingsUrl(String projsettingsUrl) {
        this.projsettingsUrl = projsettingsUrl;
    }

    public String getProcurementUrl() {
        return procurementUrl;
    }

    public void setProcurementUrl(String procurementUrl) {
        this.procurementUrl = procurementUrl;
    }

    public String getTimeManagementUrl() {
        return timeManagementUrl;
    }

    public void setTimeManagementUrl(String timeManagementUrl) {
        this.timeManagementUrl = timeManagementUrl;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getFinanceUrl() {
        return financeUrl;
    }

    public void setFinanceUrl(String financeUrl) {
        this.financeUrl = financeUrl;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

}
