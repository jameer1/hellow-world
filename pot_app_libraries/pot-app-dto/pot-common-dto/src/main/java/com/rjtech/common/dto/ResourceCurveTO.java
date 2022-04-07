package com.rjtech.common.dto;

import java.math.BigDecimal;

public class ResourceCurveTO extends ProjectTO {

    private static final long serialVersionUID = -4929619665133609726L;

    private Long id;
    private Long clientId;
    private String curveType;
    private String catg;
    private boolean defaultFlag;
    private BigDecimal range1;
    private BigDecimal range2;
    private BigDecimal range3;
    private BigDecimal range4;
    private BigDecimal range5;
    private BigDecimal range6;
    private BigDecimal range7;
    private BigDecimal range8;
    private BigDecimal range9;
    private BigDecimal range10;
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getCurveType() {
        return curveType;
    }

    public void setCurveType(String curveType) {
        this.curveType = curveType;
    }

    public String getCatg() {
        return catg;
    }

    public boolean isDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public void setCatg(String catg) {
        this.catg = catg;
    }

    public BigDecimal getRange1() {
        return range1;
    }

    public void setRange1(BigDecimal range1) {
        this.range1 = range1;
    }

    public BigDecimal getRange2() {
        return range2;
    }

    public void setRange2(BigDecimal range2) {
        this.range2 = range2;
    }

    public BigDecimal getRange3() {
        return range3;
    }

    public void setRange3(BigDecimal range3) {
        this.range3 = range3;
    }

    public BigDecimal getRange4() {
        return range4;
    }

    public void setRange4(BigDecimal range4) {
        this.range4 = range4;
    }

    public BigDecimal getRange5() {
        return range5;
    }

    public void setRange5(BigDecimal range5) {
        this.range5 = range5;
    }

    public BigDecimal getRange6() {
        return range6;
    }

    public void setRange6(BigDecimal range6) {
        this.range6 = range6;
    }

    public BigDecimal getRange7() {
        return range7;
    }

    public void setRange7(BigDecimal range7) {
        this.range7 = range7;
    }

    public BigDecimal getRange8() {
        return range8;
    }

    public void setRange8(BigDecimal range8) {
        this.range8 = range8;
    }

    public BigDecimal getRange9() {
        return range9;
    }

    public void setRange9(BigDecimal range9) {
        this.range9 = range9;
    }

    public BigDecimal getRange10() {
        return range10;
    }

    public void setRange10(BigDecimal range10) {
        this.range10 = range10;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
