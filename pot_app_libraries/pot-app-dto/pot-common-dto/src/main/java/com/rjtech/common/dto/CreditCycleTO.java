package com.rjtech.common.dto;

public class CreditCycleTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1421116171851165457L;

    private Long id;
    private String creditCycle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCycle() {
        return creditCycle;
    }

    public void setCreditCycle(String creditCycle) {
        this.creditCycle = creditCycle;
    }

}
