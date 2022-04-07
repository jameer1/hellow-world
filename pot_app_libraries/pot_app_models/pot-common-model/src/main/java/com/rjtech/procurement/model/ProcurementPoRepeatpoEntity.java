package com.rjtech.procurement.model;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "procurement_po_repeatpo")
public class ProcurementPoRepeatpoEntity implements Serializable {

    private static final long serialVersionUID = 212L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPR_ID", updatable = false, nullable = false)
    private Long id;
    
   
    /*@Column(name = "PPR_PARENT_PO_ID")
    private Long purchaseorderentity;*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PPR_PARENT_PO_ID")
	private PurchaseOrderEntity purchaseorderentity=new PurchaseOrderEntity();

	@Column(name = "PPR_PARENT_SCHEDULE_ID")
    private Long parentscheduleid;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPR_START_DATE")
    private Date startDate;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PPR_FINISH_DATE")
    private Date finishDate;
    
    @Column(name = "PPR_DELIVERY_PLACE")
    private String deliveryplace;
    
    @Column(name = "PPR_QTY")
    private Integer quantity;
    
    @Column(name = "PPR_BID")
    private boolean bid;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_created_by", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ppr_created_on", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ppr_updated_on")
    private Date updatedOn;
    
   // @Column(name = "PPR_STATUS")
    //private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public Long getPurchaseorderentity() {
		return purchaseorderentity;
	}

	public void setPurchaseorderentity(Long purchaseorderentity) {
		this.purchaseorderentity = purchaseorderentity;
	}*/

	public Long getParentscheduleid() {
		return parentscheduleid;
	}

	public void setParentscheduleid(Long parentscheduleid) {
		this.parentscheduleid = parentscheduleid;
	}

	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getDeliveryplace() {
		return deliveryplace;
	}

	public void setDeliveryplace(String deliveryplace) {
		this.deliveryplace = deliveryplace;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean getBid() {
		return bid;
	}

	public void setBid(boolean bid) {
		this.bid = bid;
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




	public PurchaseOrderEntity getPurchaseorderentity() {
		return purchaseorderentity;
	}

	public void setPurchaseorderentity(PurchaseOrderEntity purchaseorderentity) {
		this.purchaseorderentity = purchaseorderentity;
	}

	public boolean isBid() {
		return bid;
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