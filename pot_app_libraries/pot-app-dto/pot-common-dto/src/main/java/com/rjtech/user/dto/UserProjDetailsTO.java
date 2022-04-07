package com.rjtech.user.dto;

import java.util.Date;

import com.rjtech.common.dto.ClientTO;

public class UserProjDetailsTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private Long projId;
    private Long userId;
    private String projCode;
    private String projName;
    private String projType;
    private boolean proj;
    private Long parentId;
    private String parentName;
    private String parentEPSCode;
    private Date startDate;
    private Date finishDate;
    private boolean usrProj;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public boolean isProj() {
        return proj;
    }

    public void setProj(boolean proj) {
        this.proj = proj;
    }

    public boolean isUsrProj() {
        return usrProj;
    }

    public void setUsrProj(boolean usrProj) {
        this.usrProj = usrProj;

    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserProjDetailsTO other = (UserProjDetailsTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public String getParentEPSCode() {
        return parentEPSCode;
    }

    public void setParentEPSCode(String parentEPSCode) {
        this.parentEPSCode = parentEPSCode;
    }

}
