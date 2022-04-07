package com.rjtech.centrallib.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ClientTO;

public class EmployeeWageRateTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long wageRateId;
    private String code;
    private String name;
    private float wageFactor;

    public Long getWageRateId() {
        return wageRateId;
    }

    public void setWageRateId(Long wageRateId) {
        this.wageRateId = wageRateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWageFactor() {
        return wageFactor;
    }

    public void setWageFactor(float wageFactor) {
        this.wageFactor = wageFactor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wageRateId == null) ? 0 : wageRateId.hashCode());
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
        EmployeeWageRateTO other = (EmployeeWageRateTO) obj;
        if (wageRateId == null) {
            if (other.wageRateId != null)
                return false;
        } else if (!wageRateId.equals(other.wageRateId))
            return false;
        return true;
    }

}
