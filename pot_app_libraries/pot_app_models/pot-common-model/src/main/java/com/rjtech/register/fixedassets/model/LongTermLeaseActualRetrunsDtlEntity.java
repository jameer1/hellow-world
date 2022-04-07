package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "long_term_lease_actual_returns")
public class LongTermLeaseActualRetrunsDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703994L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLAR_ID")
    private Long id;
   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLAR_DATE")
    private Date date;
    
    @Column(name = "FLAR_LEASE")
    private Long lease;
    
    @Column(name = "FLAR_LEASEAGREEMENT")    
    private String leaseAgreement;
    
    @Column(name = "FLAR_TENANTID") 
    private String tenantId;
    
    @Column(name = "FLAR_TENANTNAME") 
    private String tenantName;
    
    @Column(name = "FLAR_CUMULATIVENETRENT") 
    private String cumulativeNetRent;
    
    @Column(name = "FLAR_CUMULATIVETAXAMOUNT") 
    private Long cumulativeTaxAmount;
    
    @Column(name = "FLAR_CUMULATIVENETRENTTENANT") 
    private Long cumulativeNetRentTenant;
    
    @Column(name = "FLAR_CUMULATIVETAXAMOUNTTENANT") 
    private Long cumulativeTaxAmountTenant;
    
    @Column(name = "FLAR_SHORTFALLRENT") 
    private String shortFallRent;
    
    @Column(name = "FLAR_SHORTFALLTAX") 
    private String shortFallTax;
    
    @Column(name = "FLAR_MODEOFPAYMENT") 
    private String modeOfPayment;
    
    @Column(name = "FLAR_RECEIVERBANKNAME") 
    private String receiverBankName;
    
    @Column(name = "FLAR_RECEIVERBANKCODE") 
    private String receiverBankCode;
    
    @Column(name = "FLAR_RECEIVERBANKAC") 
    private Long receiverBankAC;
    
    @Column(name = "FLAR_TRANSFERRECEIPTNO") 
    private String transferReceiptNo;
    
    @Column(name = "FLAR_RENTALAMOUNTRECEIVED")
    private Long rentalAmountReceived;
    
    @Column(name = "FLAR_TAXAMOUNTRECEIVED")
    private Long taxAmountReceived; 
    
    @ManyToOne
    @JoinColumn(name = "FLAR_FIXED_ASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;
    
    @ManyToOne
    @JoinColumn(name = "FLAR_CLIENT_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "FLAR_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLAR_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FLAR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLAR_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "FLAR_STATUS")
    private Integer status;

    @Column(name = "FLAR_DOC_KEY")
    private String docKey;
    
    @Column(name = "FLAR_UPLOADMONEYDOCUMENTFILENAME")
    private String uploadMoneyDocumentFileName;

    @Column(name = "FLAR_UPLOADMONEYDOCUMENTFILESIZE")
    private Long uploadMoneyDocumentFileSize;

    @Column(name = "FLAR_UPLOADMONEYDOCUMENTFILETYPE")
    private String uploadMoneyDocumentFileType;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getLease() {
        return lease;
    }

    public void setLease(Long lease) {
        this.lease = lease;
    }

    public String getLeaseAgreement() {
        return leaseAgreement;
    }

    public void setLeaseAgreement(String leaseAgreement) {
        this.leaseAgreement = leaseAgreement;
    }
    
    
   
    public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCumulativeNetRent() {
        return cumulativeNetRent;
    }

    public void setCumulativeNetRent(String cumulativeNetRent) {
        this.cumulativeNetRent = cumulativeNetRent;
    }

    public Long getCumulativeTaxAmount() {
        return cumulativeTaxAmount;
    }

    public void setCumulativeTaxAmount(Long cumulativeTaxAmount) {
        this.cumulativeTaxAmount = cumulativeTaxAmount;
    }

    public Long getCumulativeNetRentTenant() {
        return cumulativeNetRentTenant;
    }    

    public Long getCumulativeTaxAmountTenant() {
        return cumulativeTaxAmountTenant;
    }

    public void setCumulativeNetRentTenant(Long cumulativeNetRentTenant) {
        this.cumulativeNetRentTenant = cumulativeNetRentTenant;
    }

    public void setCumulativeTaxAmountTenant(Long cumulativeTaxAmountTenant) {
        this.cumulativeTaxAmountTenant = cumulativeTaxAmountTenant;
    }

    public String getShortFallRent() {
        return shortFallRent;
    }

    public void setShortFallRent(String shortFallRent) {
        this.shortFallRent = shortFallRent;
    }

    public String getShortFallTax() {
        return shortFallTax;
    }

    public void setShortFallTax(String shortFallTax) {
        this.shortFallTax = shortFallTax;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }    

    public Long getReceiverBankAC() {
        return receiverBankAC;
    }

    public void setReceiverBankAC(Long receiverBankAC) {
        this.receiverBankAC = receiverBankAC;
    }

    public String getTransferReceiptNo() {
        return transferReceiptNo;
    }

    public void setTransferReceiptNo(String transferReceiptNo) {
        this.transferReceiptNo = transferReceiptNo;
    }

    public Long getRentalAmountReceived() {
        return rentalAmountReceived;
    }

    public void setRentalAmountReceived(Long rentalAmountReceived) {
        this.rentalAmountReceived = rentalAmountReceived;
    }

    public Long getTaxAmountReceived() {
        return taxAmountReceived;
    }

    public void setTaxAmountReceived(Long taxAmountReceived) {
        this.taxAmountReceived = taxAmountReceived;
    }

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getUploadMoneyDocumentFileName() {
        return uploadMoneyDocumentFileName;
    }

    public void setUploadMoneyDocumentFileName(String uploadMoneyDocumentFileName) {
        this.uploadMoneyDocumentFileName = uploadMoneyDocumentFileName;
    }

    public Long getUploadMoneyDocumentFileSize() {
        return uploadMoneyDocumentFileSize;
    }

    public void setUploadMoneyDocumentFileSize(Long uploadMoneyDocumentFileSize) {
        this.uploadMoneyDocumentFileSize = uploadMoneyDocumentFileSize;
    }

    public String getUploadMoneyDocumentFileType() {
        return uploadMoneyDocumentFileType;
    }

    public void setUploadMoneyDocumentFileType(String uploadMoneyDocumentFileType) {
        this.uploadMoneyDocumentFileType = uploadMoneyDocumentFileType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
        this.projDocFileEntity = projDocFileEntity;
    }

}
