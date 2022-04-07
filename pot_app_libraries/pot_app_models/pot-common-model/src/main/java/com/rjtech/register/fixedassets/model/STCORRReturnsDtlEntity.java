package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "stcorr_returns_dtl")
public class STCORRReturnsDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FSTR_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSTR_STARTDATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSTR_FINISHDATE")
    private Date finishDate;

    @Column(name = "FSTR_TENANTNAME")
    private String tenantName;

    @Column(name = "FSTR_TENANTREGISTEREDADDRESS")
    private String tenantRegisteredAddress;

    @Column(name = "FSTR_EMAILADDREES")
    private String emailAddrees;

    @Column(name = "FSTR_TENANTPHONENUMBER")
    private Long tenantPhoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSTR_CHECKIN")
    private Date checkIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSTR_CHECKOUT")
    private Date checkOut;

    @Column(name = "FSTR_NOOFDAYS")
    private Long noOfDays;

    @Column(name = "FSTR_dailyNetRent")
    private Long dailyNetRent;

    @Column(name = "FSTR_TAX")
    private Long tax;

    @Column(name = "FSTR_RENTAMOUNTRECEIVABLE")
    private Long rentAmountReceivable;

    @Column(name = "FSTR_TAXAMOUNT")
    private Long taxAmount;

    @Column(name = "FSTR_GROSSAMOUNT")
    private Long grossAmount;

    @Column(name = "FSTR_ADVANCEPAID")
    private Long advancePaid;

    @Column(name = "FSTR_SUBSEQUENTRENTALRECEIPTS")
    private Long subsequentRentalReceipts;

    @Column(name = "FSTR_REFUNDOFREMAININGADVANCEAMOUNT")
    private String refundofRemainingAdvanceamount;

    @Column(name = "FSTR_NETTAXAMOUNTRECEIVED")
    private Long netTaxAmountReceived;

    @Column(name = "FSTR_NETAMOUNTOFRENTRECIVED")
    private Long netAmountOfRentRecived;

    @Column(name = "FSTR_TENANTRECORDDETAILSFILENAME")
    private String tenantRecordDetailsFileName;

    @Column(name = "FSTR_TENANTRECORDDETAILSFILESIZE")
    private Long tenantRecordDetailsFileSize;

    @Column(name = "FSTR_TENANTRECORDDETAILSFILETYPE")
    private String tenantRecordDetailsFileType;

    @Column(name = "FSTR_CURRENTSTATUS")
    private String currentStatus;

    @Column(name = "FSTR_STATUS")
    private Integer status;

    @Column(name = "FSTR_CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSTR_CREATED_ON")
    private Date createdOn;

    @Column(name = "FSTR_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FSTR_UPDATED_ON")
    private Date updatedOn;   
    
    @ManyToOne
    @JoinColumn(name = "FSTR_FIXEDASSETID")
    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;    
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "FSTR_ADVANCED_RENTAL_RECEIPTS")
    private List<AdvanceRentalRecepitsDtlEntity> advanceRentalReceiptsDtlEntityList;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "FSTR_SUBSEQUENT_RENTAL_RECEIPTS")
    private List<SubSequentRentalRecepitsDtlEntity> SubSequentRentalReceiptsDtlEntityList;

    @ManyToOne
    @JoinColumn(name = "FSTR_CLIENT_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "FSTR_DOC_KEY")
    private String docKey;
    
    
    @Column(name = "FSTR_TENANTID")
    private String tenantId;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;

    public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantRegisteredAddress() {
        return tenantRegisteredAddress;
    }

    public void setTenantRegisteredAddress(String tenantRegisteredAddress) {
        this.tenantRegisteredAddress = tenantRegisteredAddress;
    }

    public String getEmailAddrees() {
        return emailAddrees;
    }

    public void setEmailAddrees(String emailAddrees) {
        this.emailAddrees = emailAddrees;
    }

    public Long getTenantPhoneNumber() {
        return tenantPhoneNumber;
    }

    public void setTenantPhoneNumber(Long tenantPhoneNumber) {
        this.tenantPhoneNumber = tenantPhoneNumber;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Long getDailyNetRent() {
        return dailyNetRent;
    }

    public void setDailyNetRent(Long dailyNetRent) {
        this.dailyNetRent = dailyNetRent;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public Long getRentAmountReceivable() {
        return rentAmountReceivable;
    }

    public void setRentAmountReceivable(Long rentAmountReceivable) {
        this.rentAmountReceivable = rentAmountReceivable;
    }

    public Long getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Long taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Long getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Long grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Long getAdvancePaid() {
        return advancePaid;
    }

    public void setAdvancePaid(Long advancePaid) {
        this.advancePaid = advancePaid;
    }

    public String getRefundofRemainingAdvanceamount() {
        return refundofRemainingAdvanceamount;
    }

    public void setRefundofRemainingAdvanceamount(String refundofRemainingAdvanceamount) {
        this.refundofRemainingAdvanceamount = refundofRemainingAdvanceamount;
    }

    public Long getNetTaxAmountReceived() {
        return netTaxAmountReceived;
    }

    public void setNetTaxAmountReceived(Long netTaxAmountReceived) {
        this.netTaxAmountReceived = netTaxAmountReceived;
    }

    public Long getNetAmountOfRentRecived() {
        return netAmountOfRentRecived;
    }

    public void setNetAmountOfRentRecived(Long netAmountOfRentRecived) {
        this.netAmountOfRentRecived = netAmountOfRentRecived;
    }

    public String getTenantRecordDetailsFileName() {
        return tenantRecordDetailsFileName;
    }

    public void setTenantRecordDetailsFileName(String tenantRecordDetailsFileName) {
        this.tenantRecordDetailsFileName = tenantRecordDetailsFileName;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getTenantRecordDetailsFileSize() {
        return tenantRecordDetailsFileSize;
    }

    public void setTenantRecordDetailsFileSize(Long tenantRecordDetailsFileSize) {
        this.tenantRecordDetailsFileSize = tenantRecordDetailsFileSize;
    }

    public String getTenantRecordDetailsFileType() {
        return tenantRecordDetailsFileType;
    }

    public void setTenantRecordDetailsFileType(String tenantRecordDetailsFileType) {
        this.tenantRecordDetailsFileType = tenantRecordDetailsFileType;
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

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public Long getSubsequentRentalReceipts() {
        return subsequentRentalReceipts;
    }

    public void setSubsequentRentalReceipts(Long subsequentRentalReceipts) {
        this.subsequentRentalReceipts = subsequentRentalReceipts;
    }

    public List<AdvanceRentalRecepitsDtlEntity> getAdvanceRentalReceiptsDtlEntityList() {
        return advanceRentalReceiptsDtlEntityList;
    }

    public void setAdvanceRentalReceiptsDtlEntityList(
            List<AdvanceRentalRecepitsDtlEntity> advanceRentalReceiptsDtlEntityList) {
        this.advanceRentalReceiptsDtlEntityList = advanceRentalReceiptsDtlEntityList;
    }

    public List<SubSequentRentalRecepitsDtlEntity> getSubSequentRentalReceiptsDtlEntityList() {
        return SubSequentRentalReceiptsDtlEntityList;
    }

    public void setSubSequentRentalReceiptsDtlEntityList(
            List<SubSequentRentalRecepitsDtlEntity> subSequentRentalReceiptsDtlEntityList) {
        SubSequentRentalReceiptsDtlEntityList = subSequentRentalReceiptsDtlEntityList;
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

       // this.leaseAgreement = result + "-" + "LS" + id;
    	
    	
        this.tenantId= String.valueOf(fixedAssetsRegisterDtlEntity.getFixedAssetid())+"-"+"T-"+id+"-" +result;
        		
        	//	"A-"+result+ "-" + String.valueOf(fixedAssetsRegisterDtlEntity.getFixedAssetid()) +"-" +"T-"+id;

    }


}
