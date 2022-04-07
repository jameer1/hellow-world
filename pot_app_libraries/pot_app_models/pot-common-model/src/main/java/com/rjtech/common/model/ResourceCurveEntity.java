package com.rjtech.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "resource_curves")
public class ResourceCurveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RC_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "RC_TYPE")
    private String curveType;

    @Column(name = "RC_CATG")
    private String catg;

    @Column(name = "RC_DEFAULT", columnDefinition = "int default 0")
    private boolean defaultFlag;

    @Column(name = "RC_RANGE1")
    private BigDecimal range1;

    @Column(name = "RC_RANGE2")
    private BigDecimal range2;

    @Column(name = "RC_RANGE3")
    private BigDecimal range3;

    @Column(name = "RC_RANGE4")
    private BigDecimal range4;

    @Column(name = "RC_RANGE5")
    private BigDecimal range5;

    @Column(name = "RC_RANGE6")
    private BigDecimal range6;

    @Column(name = "RC_RANGE7")
    private BigDecimal range7;

    @Column(name = "RC_RANGE8")
    private BigDecimal range8;

    @Column(name = "RC_RANGE9")
    private BigDecimal range9;

    @Column(name = "RC_RANGE10")
    private BigDecimal range10;

    @Column(name = "RC_TOTAL")
    private BigDecimal total;

    @Column(name = "RC_STATUS")
    private int status;

    @ManyToOne
    @JoinColumn(name = "RC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "RC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RC_UPDATED_ON")
    private Date updatedOn;

    public ResourceCurveEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
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

    public void setCatg(String catg) {
        this.catg = catg;
    }

    public boolean isDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }
}
