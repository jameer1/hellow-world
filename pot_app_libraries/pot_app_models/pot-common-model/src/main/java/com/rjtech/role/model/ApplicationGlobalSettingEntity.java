package com.rjtech.role.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Application_Global_Settings")
public class ApplicationGlobalSettingEntity implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "AGS_ID")
	    private Long appGlobalId;
	  
	  @Column(name = "AGS_APPLICATION_NAME")
	    private String applicationName;
	  
	  @Column(name = "AGS_VERSION")
	    private String appVersion;
	  	  	
	  @Column(name = "AGS_DOMAIN_NAME")
	    private String applicationDomainName;
	  
	  @Column(name = "AGS_CLIENT_URL")
	    private String appClientUrl;

	public Long getAppGlobalId() {
		return appGlobalId;
	}

	public void setAppGlobalId(Long appGlobalId) {
		this.appGlobalId = appGlobalId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getApplicationDomainName() {
		return applicationDomainName;
	}

	public void setApplicationDomainName(String applicationDomainName) {
		this.applicationDomainName = applicationDomainName;
	}

	public String getAppClientUrl() {
		return appClientUrl;
	}

	public void setAppClientUrl(String appClientUrl) {
		this.appClientUrl = appClientUrl;
	}

}
