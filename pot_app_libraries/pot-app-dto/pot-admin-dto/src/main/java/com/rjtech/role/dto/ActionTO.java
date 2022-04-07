package com.rjtech.role.dto;

import com.rjtech.common.dto.AuditLog;

public class ActionTO extends AuditLog {

    /**
     * 
     */
    private static final long serialVersionUID = 639818595029985183L;
    private Long actionId;
    private String actionName;
    private Integer dispOrder;

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actionId == null) ? 0 : actionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActionTO other = (ActionTO) obj;
        if (actionId == null) {
            if (other.actionId != null)
                return false;
        } else if (!actionId.equals(other.actionId))
            return false;
        return true;
    }

}
