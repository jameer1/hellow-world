/**
 * 
 */
package com.rjtech.finance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rjtech.common.model.UserMstrEntity;

/**
 * @author sowmya
 *
 */
@Entity
@Table(name = "INVOICE_RECORDS_HISTORY")
public class InvoiceRecordsHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IRH_ID")
	private Long id;
	
	@Column(name ="Invoice_num")
	private String invoiceNum;
	
	@ManyToOne
	@JoinColumn(name ="Vendor_post_invoice_id")
	private VendorPostInvoiceEntity vendorPostInvoiceId;
	
	@Column(name="Status")
	private String status;
	
	@Column(name ="Approved_Date")
	private Date approvedDate;
	
	@ManyToOne
	@JoinColumn(name = "Approved_User")
	private UserMstrEntity approvedBy;
	
	@Column(name ="comments")
	private String comments;
	
	@ManyToOne
	@JoinColumn(name ="Responsible_person_for pending_step")
	private UserMstrEntity responsiblePersonForPending;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public VendorPostInvoiceEntity getVendorPostInvoiceId() {
		return vendorPostInvoiceId;
	}

	public void setVendorPostInvoiceId(VendorPostInvoiceEntity vendorPostInvoiceId) {
		this.vendorPostInvoiceId = vendorPostInvoiceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	
	public UserMstrEntity getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(UserMstrEntity approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public UserMstrEntity getResponsiblePersonForPending() {
		return responsiblePersonForPending;
	}

	public void setResponsiblePersonForPending(UserMstrEntity responsiblePersonForPending) {
		this.responsiblePersonForPending = responsiblePersonForPending;
	}
	
	
	

}
