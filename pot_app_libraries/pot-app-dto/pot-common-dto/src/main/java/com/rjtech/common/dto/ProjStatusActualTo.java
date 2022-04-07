package com.rjtech.common.dto;

import java.io.Serializable;

public class ProjStatusActualTo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8608857571050634041L;

    private Long id;
    private Double beforeQty = Double.valueOf(0);
    private Double btwnQty = Double.valueOf(0);
    private Double origQty = Double.valueOf(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBeforeQty() {
        return beforeQty;
    }

    public void setBeforeQty(Double beforeQty) {
        this.beforeQty = beforeQty;
    }

    public Double getBtwnQty() {
        return btwnQty;
    }

    public void setBtwnQty(Double btwnQty) {
        this.btwnQty = btwnQty;
    }

    public Double getOrigQty() {
        return origQty;
    }

    public void setOrigQty(Double origQty) {
        this.origQty = origQty;
    }

}
