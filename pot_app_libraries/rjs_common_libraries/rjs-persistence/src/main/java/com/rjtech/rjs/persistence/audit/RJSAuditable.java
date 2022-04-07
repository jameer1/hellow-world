package com.rjtech.rjs.persistence.audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.rjtech.rjs.persistence.entity.RJSAbstractEntity;
import com.rjtech.rjs.transaction.TransactionIDHolder;

@MappedSuperclass
public abstract class RJSAuditable extends RJSAbstractEntity {

    @Column(name = "DTL_ID")
    private Long dtlId;

    public Long getDtlId() {
        return dtlId;
    }

    public void setDtlId(Long dtlId) {
        this.dtlId = dtlId;
    }

    @Override
    @PrePersist
    public void onPrePersist() {
        super.onPrePersist();
        this.setCreatedDate();
        this.setCreatedBy();
        this.setDtlId(TransactionIDHolder.getTransactionId());

    }

    @Override
    @PreUpdate
    public void onPreUpdate() {
        super.onPreUpdate();
        this.setModifiedDate();
        this.setModifiedBy();
        this.setDtlId(TransactionIDHolder.getTransactionId());

    }

    public void setCreatedDate() {

    }

    public void setCreatedBy() {

    }

    public void setModifiedDate() {

    }

    public void setModifiedBy() {

    }
}
