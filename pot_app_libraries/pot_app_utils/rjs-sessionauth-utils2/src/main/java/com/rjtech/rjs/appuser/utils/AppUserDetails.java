package com.rjtech.rjs.appuser.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class AppUserDetails extends User {

    private Map<String, Boolean> appCodeTemplate;

    private String username;
    private String password;
    private Long userId;
    private String email;

    private String name;
    private String displayName;
    private String displayRole;
    private String designation;
    private String token;

    private Long clientId;
    private String clientCode;

    private Set<GrantedAuthority> authorities;

    private List<Long> roleIds;
    private String registeredUsers;
    private Long adminClientId;

    public AppUserDetails() {
        super("Anonmous", "NA", AuthorityUtils.NO_AUTHORITIES);
    }

    public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.username = username;
        this.password = password;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -6068653137226915549L;

    public String getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(String registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map<String, Boolean> getAppCodeTemplate() {
        return appCodeTemplate;
    }

    public void setAppCodeTemplate(Map<String, Boolean> appCodeTemplate) {
        this.appCodeTemplate = appCodeTemplate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getDisplayRole() {
        return displayRole;
    }

    public void setDisplayRole(String displayRole) {
        this.displayRole = displayRole;
    }

    public Long getAdminClientId() {
        return adminClientId;
    }

    public void setAdminClientId(Long adminClientId) {
        this.adminClientId = adminClientId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}
