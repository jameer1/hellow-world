package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class PayRollCycleTO extends ClientTO {

    private static final long serialVersionUID = -2090802597735566435L;
    private Long id;
    private String payRollCycle;
    private String selectYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayRollCycle() {
        return payRollCycle;
    }

    public void setPayRollCycle(String payRollCycle) {
        this.payRollCycle = payRollCycle;
    }

    public String getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(String selectYear) {
        this.selectYear = selectYear;
    }

}
