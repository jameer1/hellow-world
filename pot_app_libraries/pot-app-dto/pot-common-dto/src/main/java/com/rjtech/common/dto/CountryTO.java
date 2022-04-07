package com.rjtech.common.dto;

import java.util.ArrayList;
import java.util.List;

public class CountryTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long id;
    private Long geonameId;
    private String code;
    private String name;
    private String startDate;
    private String finishDate;
    private CurrencyTO currencyTO = new CurrencyTO();
    private ProvisionTO provisionTO = new ProvisionTO();
    private List<ProvisionTO> provisionTOs = new ArrayList<ProvisionTO>();

    public Long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Long geonameId) {
        this.geonameId = geonameId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public CurrencyTO getCurrencyTO() {
        return currencyTO;
    }

    public void setCurrencyTO(CurrencyTO currencyTO) {
        this.currencyTO = currencyTO;
    }

    public ProvisionTO getProvisionTO() {
        return provisionTO;
    }

    public void setProvisionTO(ProvisionTO provisionTO) {
        this.provisionTO = provisionTO;
    }

    public List<ProvisionTO> getProvisionTOs() {
        return provisionTOs;
    }

    public void setProvisionTOs(List<ProvisionTO> provisionTOs) {
        this.provisionTOs = provisionTOs;
    }

}