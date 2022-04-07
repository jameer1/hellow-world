package com.rjtech.finance.req;

import java.util.Date;

import com.rjtech.common.dto.ClientTO;

public class TaxCountryProvisionGetReq extends ClientTO {

    private static final long serialVersionUID = 7545192002202588031L;

    private Long countryId;
    private Long id;
    private Date effectiveDate;
    private String countryName;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
