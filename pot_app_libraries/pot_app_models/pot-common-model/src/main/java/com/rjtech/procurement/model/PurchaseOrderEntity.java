package com.rjtech.procurement.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the purchase_order_dtl database table.
 * 
 */
@Entity
@Table(name = "purchase_order_dtl")
public class PurchaseOrderEntity implements Serializable {

    private static final long serialVersionUID = -1446817822797471614L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUR_PARENT_ID")
    private PurchaseOrderEntity parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUR_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUR_CRA_ID")
    private UserMstrEntity apprUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PUR_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PUR_FINISH_DATE")
    private Date finsihDate;

    @Column(name = "PUR_PAYMENT_IN_DAYS")
    private BigDecimal paymentInDays;

    @Column(name = "PUR_AMOUNT")
    private BigDecimal amount;

    @Column(name = "PUR_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PUR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PUR_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PUR_PCC_ID")
    private PreContractsCmpEntity preContractsCmpEntity;

    @Column(name = "PUR_PROCURE_TYPE")
    private String procureType;

    @Column(name = "PUR_COMPLETE_PROCURE_TYPE")
    private String completeProcureType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PO_DETAILS_ID_FK")
    private PurchaseOrderDetailsEntity poDetailsEntity;

    @Column(name = "PUR_DELIVERED", columnDefinition = "int default 0")
    private boolean delivered;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaseOrderEntity getParentId() {
        return parentId;
    }

    public void setParentId(PurchaseOrderEntity parentId) {
        this.parentId = parentId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public UserMstrEntity getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(UserMstrEntity apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinsihDate() {
        return finsihDate;
    }

    public void setFinsihDate(Date finsihDate) {
        this.finsihDate = finsihDate;
    }

    public BigDecimal getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(BigDecimal paymentInDays) {
        this.paymentInDays = paymentInDays;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public PreContractsCmpEntity getPreContractsCmpEntity() {
        return preContractsCmpEntity;
    }

    public void setPreContractsCmpEntity(PreContractsCmpEntity preContractsCmpEntity) {
        this.preContractsCmpEntity = preContractsCmpEntity;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

    public String getCompleteProcureType() {
        return completeProcureType;
    }

    public void setCompleteProcureType(String completeProcureType) {
        this.completeProcureType = completeProcureType;
    }

    public PurchaseOrderDetailsEntity getPoDetailsEntity() {
        return poDetailsEntity;
    }

    public void setPoDetailsEntity(PurchaseOrderDetailsEntity poDetailsEntity) {
        this.poDetailsEntity = poDetailsEntity;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
