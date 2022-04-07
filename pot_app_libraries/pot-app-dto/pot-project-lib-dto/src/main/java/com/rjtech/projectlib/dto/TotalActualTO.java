package com.rjtech.projectlib.dto;

import java.io.Serializable;

public class TotalActualTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2256251308145070484L;

    private Long id;
    private Long actualQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Long actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

}
