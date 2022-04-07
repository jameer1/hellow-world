package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "long_term_leasing_dtl")
public class LongTermLeasingDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLT_ID")
    private Long id;

    @Column(name = "FLT_LEASEAGREEMENT")
    private String leaseAgreement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLT_LEASESTART")
    private Date leaseStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLT_LEASEFINISH")
    private Date leaseFinish;

    @Column(name = "FLT_TENANTNAME")
    private String tenantName;

    @Column(name = "FLT_TENANTADDRESS")
    private String tenantAddress;

    @Column(name = "FLT_PAYMENTCYCLE")
    private String paymentCycle;

    @Column(name = "FLT_NETRENTAMOUNTPERCYCLE")
    private Float netRentAmountPerCycle;

    @Column(name = "FLT_MAINTENANCECHARGES")
    private String maintenanceCharges;

    @Column(name = "FLT_ASSETMAINTENANCECHARGES")
    private Float assetMaintenanceCharges;

    @Column(name = "FLT_TAXABLEAMOUNT")
    private Float taxableAmount;

    @Column(name = "FLT_TAX")
    private Float tax;

    @Column(name = "FLT_TAXAMOUNT")
    private Float taxAmount;

    @Column(name = "FLT_GROSSRENT")
    private Float grossRent;

  //  @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLT_PAYMENTCYCLEDUEDATE")
    private String paymentCycleDueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLT_LEASEEXTENDEDFINSHDATE")
    private Date leaseExtendedFinshDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FLT_LEASEACTUALFINISHFINSHDATE")
    private Date leaseActualFinishFinshDate;

    @Column(name = "FLT_LEASEDOCUMENTDETAILSFILENAME")
    private String leaseDocumentDetailsFileName;

    @Column(name = "FLT_LEASEDOCUMENTDETAILSFILESIZE")
    private Long leaseDocumentDetailsFileSize;

    @Column(name = "FLT_LEASEDOCUMENTDETAILSFILETYPE")
    private String leaseDocumentDetailsFileType;

    @Column(name = "FLT_CURRENTSTATUS")
    private String currentStatus;

    @Column(name = "FLT_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "FLT_DOC_KEY")
    private String docKey;

    @ManyToOne
    @JoinColumn(name = "FLT_FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;
    
    @Column(name = "FLT_TENANT_ID")
    private String tenantid;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaseDocumentDetailsFileSize() {
        return leaseDocumentDetailsFileSize;
    }

    public void setLeaseDocumentDetailsFileSize(Long leaseDocumentDetailsFileSize) {
        this.leaseDocumentDetailsFileSize = leaseDocumentDetailsFileSize;
    }

    public String getLeaseDocumentDetailsFileType() {
        return leaseDocumentDetailsFileType;
    }

    public void setLeaseDocumentDetailsFileType(String leaseDocumentDetailsFileType) {
        this.leaseDocumentDetailsFileType = leaseDocumentDetailsFileType;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public String getLeaseAgreement() {
        return leaseAgreement;
    }

    public void setLeaseAgreement(String leaseAgreement) {
        this.leaseAgreement = leaseAgreement;
    }

    public Date getLeaseStart() {
        return leaseStart;
    }

    public void setLeaseStart(Date leaseStart) {
        this.leaseStart = leaseStart;
    }

    public Date getLeaseFinish() {
        return leaseFinish;
    }

    public void setLeaseFinish(Date leaseFinish) {
        this.leaseFinish = leaseFinish;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Float getNetRentAmountPerCycle() {
        return netRentAmountPerCycle;
    }

    public void setNetRentAmountPerCycle(Float netRentAmountPerCycle) {
        this.netRentAmountPerCycle = netRentAmountPerCycle;
    }

    public String getMaintenanceCharges() {
        return maintenanceCharges;
    }

    public void setMaintenanceCharges(String maintenanceCharges) {
        this.maintenanceCharges = maintenanceCharges;
    }

    public Float getAssetMaintenanceCharges() {
        return assetMaintenanceCharges;
    }

    public void setAssetMaintenanceCharges(Float assetMaintenanceCharges) {
        this.assetMaintenanceCharges = assetMaintenanceCharges;
    }

    public Float getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(Float taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getGrossRent() {
        return grossRent;
    }

    public void setGrossRent(Float grossRent) {
        this.grossRent = grossRent;
    }

    public String getPaymentCycleDueDate() {
        return paymentCycleDueDate;
    }

    public void setPaymentCycleDueDate(String paymentCycleDueDate) {
        this.paymentCycleDueDate = paymentCycleDueDate;
    }

    public Date getLeaseExtendedFinshDate() {
        return leaseExtendedFinshDate;
    }

    public void setLeaseExtendedFinshDate(Date leaseExtendedFinshDate) {
        this.leaseExtendedFinshDate = leaseExtendedFinshDate;
    }

    public Date getLeaseActualFinishFinshDate() {
        return leaseActualFinishFinshDate;
    }

    public void setLeaseActualFinishFinshDate(Date leaseActualFinishFinshDate) {
        this.leaseActualFinishFinshDate = leaseActualFinishFinshDate;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getLeaseDocumentDetailsFileName() {
        return leaseDocumentDetailsFileName;
    }

    public void setLeaseDocumentDetailsFileName(String leaseDocumentDetailsFileName) {
        this.leaseDocumentDetailsFileName = leaseDocumentDetailsFileName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
        return fixedAssetsRegisterDtlEntity;
    }

    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
    }

    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
    
    @PostLoad
    @PostPersist
    private void setFormattedId() {
        StringBuilder charBuffer = new StringBuilder();
        String str = AppUserUtils.getClientCode();
        String result = "";
        String s[] = str.split(" ");
        for (String values : s) {
            charBuffer.append(values.charAt(0));
        }
        result = charBuffer.toString();

   //     this.leaseAgreement = result + "-" + "LS" + id;
    	
    	
        this.tenantid= String.valueOf(fixedAssetsRegisterDtlEntity.getFixedAssetid())+"-"+"T-"+id+"-" +result;
        		
        		//"A-"+result+ "-" + String.valueOf(fixedAssetsRegisterDtlEntity.getFixedAssetid()) +"-" +"T-"+id;

    }

}
