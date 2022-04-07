package com.rjtech.rjs.model.token;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "opr_token_cache")
public class TokenCacheEntity implements Serializable {

    private static final long serialVersionUID = 2495056650922325850L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPR_TOKEN_CACHE_ID")
    private Long tokenCacheId;

    @Column(name = "AUTH_TOKEN")
    private String token;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserMstrEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientRegEntity clientEntity;

    @Lob
    @Column(name = "APP_USER_DETAILS")
    private String appUserDetails;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "LAST_CREATE_DATE")
    private Timestamp lastCreateDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    public Long getTokenCacheId() {
        return tokenCacheId;
    }

    public void setTokenCacheId(Long tokenCacheId) {
        this.tokenCacheId = tokenCacheId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserMstrEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserMstrEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getLastCreateDate() {
        return lastCreateDate;
    }

    public void setLastCreateDate(Timestamp lastCreateDate) {
        this.lastCreateDate = lastCreateDate;
    }

    public ClientRegEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientRegEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    public String getAppUserDetails() {
        return appUserDetails;
    }

    public void setAppUserDetails(String appUserDetails) {
        this.appUserDetails = appUserDetails;
    }

}
