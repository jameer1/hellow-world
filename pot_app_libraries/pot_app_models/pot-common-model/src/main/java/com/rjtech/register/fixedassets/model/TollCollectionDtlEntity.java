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
	@Table(name = "TOLL_COLLECTION")
	public class TollCollectionDtlEntity implements Serializable {
	    private static final long serialVersionUID = -8795406421033703996L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "TC_ID")
	    private Long id;

	    @Temporal(TemporalType.DATE)
	    @Column(name = "TC_DATE")
	    private Date date;

	    @Column(name = "TC_MODEOFPAYMENT")
	    private String modeOfPayment;

	    @Column(name = "TC_BANKNAME")
	    private String bankName;

	    @Column(name = "TC_BANKCODE")
	    private String bankCode;

	    @Column(name = "TC_BANKAC")
	    private String bankAc;

	    @Column(name = "TC_TRANSFERRECEIPTNO")
	    private String transferReceiptNo;

	    @Column(name = "TC_NETAMOUNT")
	    private Long netAmount;

	    @Column(name = "TC_TAXAMOUNT")
	    private Long taxAmount;

	    @Column(name = "TC_CUMULATIVENETAMOUNT")
	    private Long cumulativeNetAmount;

	    @Column(name = "TC_CUMULATIVETAXAMOUNT")
	    private Long cumulativeTaxAmount;

	    @Column(name = "TC_FORECASTNETAMT")
	    private Long forecastNetAmt;

	    @Column(name = "TC_FORECASTTAXAMT")
	    private Long forecastTaxAmt;

	    @Column(name = "TC_CUMULATIVENETREVENUE")
	    private String cumulativeNetRevenue;

	    @Column(name = "TC_cumulativeTax")
	    private Long cumulativeTax;

	    @Column(name = "TC_TOLLFILENAME")
	    private String TollFileName;

	    @Column(name = "TC_TOLLFILESIZE")
	    private Long TollFileSize;

	    @Column(name = "TC_TOLLFILETYPE")
	    private String TollFileType;

	    @Column(name = "TC_DOCKEY")
	    private String docKey;

	    @ManyToOne
	    @JoinColumn(name = "TC_CREATED_BY")
	    private UserMstrEntity createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "TC_CREATED_ON")
	    private Date createdOn;

	    @ManyToOne
	    @JoinColumn(name = "TC_UPDATED_BY")
	    private UserMstrEntity updatedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "TC_UPDATED_ON")
	    private Date updatedOn;

	    @Column(name = "TC_STATUS")
	    private Integer status;

	    @ManyToOne
	    @JoinColumn(name = "client_id", updatable = false)
	    private ClientRegEntity clientId;

	    @ManyToOne
	    @JoinColumn(name = "TC_FIXEDASSETID")
	    private FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity;
	    
	    @OneToOne
	    @JoinColumn(name = "PDFL_ID_FK")
	    private ProjDocFileEntity projDocFileEntity;

	    public Long getId() {
	        return id;
	    }

	    public String getDocKey() {
	        return docKey;
	    }

	    public void setDocKey(String docKey) {
	        this.docKey = docKey;
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

	    public String getModeOfPayment() {
	        return modeOfPayment;
	    }

	    public void setModeOfPayment(String modeOfPayment) {
	        this.modeOfPayment = modeOfPayment;
	    }

	    public String getBankName() {
	        return bankName;
	    }

	    public void setBankName(String bankName) {
	        this.bankName = bankName;
	    }

	    public String getBankCode() {
	        return bankCode;
	    }

	    public void setBankCode(String bankCode) {
	        this.bankCode = bankCode;
	    }

	    public String getBankAc() {
	        return bankAc;
	    }

	    public void setBankAc(String bankAc) {
	        this.bankAc = bankAc;
	    }

	    public String getTransferReceiptNo() {
	        return transferReceiptNo;
	    }

	    public void setTransferReceiptNo(String transferReceiptNo) {
	        this.transferReceiptNo = transferReceiptNo;
	    }

	    public Long getNetAmount() {
	        return netAmount;
	    }

	    public void setNetAmount(Long netAmount) {
	        this.netAmount = netAmount;
	    }

	    public Long getTaxAmount() {
	        return taxAmount;
	    }

	    public void setTaxAmount(Long taxAmount) {
	        this.taxAmount = taxAmount;
	    }

	    public Long getCumulativeNetAmount() {
	        return cumulativeNetAmount;
	    }

	    public void setCumulativeNetAmount(Long cumulativeNetAmount) {
	        this.cumulativeNetAmount = cumulativeNetAmount;
	    }

	    public Long getCumulativeTaxAmount() {
	        return cumulativeTaxAmount;
	    }

	    public void setCumulativeTaxAmount(Long cumulativeTaxAmount) {
	        this.cumulativeTaxAmount = cumulativeTaxAmount;
	    }

	    public Long getForecastNetAmt() {
	        return forecastNetAmt;
	    }

	    public void setForecastNetAmt(Long forecastNetAmt) {
	        this.forecastNetAmt = forecastNetAmt;
	    }

	    public Long getForecastTaxAmt() {
	        return forecastTaxAmt;
	    }

	    public void setForecastTaxAmt(Long forecastTaxAmt) {
	        this.forecastTaxAmt = forecastTaxAmt;
	    }

	    public String getCumulativeNetRevenue() {
	        return cumulativeNetRevenue;
	    }

	    public void setCumulativeNetRevenue(String cumulativeNetRevenue) {
	        this.cumulativeNetRevenue = cumulativeNetRevenue;
	    }

	    public Long getCumulativeTax() {
	        return cumulativeTax;
	    }

	    public void setCumulativeTax(Long cumulativeTax) {
	        this.cumulativeTax = cumulativeTax;
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

	    public ClientRegEntity getClientId() {
	        return clientId;
	    }

	    public void setClientId(ClientRegEntity clientId) {
	        this.clientId = clientId;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public FixedAssetsRegisterDtlEntity getFixedAssetsRegisterDtlEntity() {
	        return fixedAssetsRegisterDtlEntity;
	    }

	    public void setFixedAssetsRegisterDtlEntity(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity) {
	        this.fixedAssetsRegisterDtlEntity = fixedAssetsRegisterDtlEntity;
	    }

		public String getTollFileName() {
			return TollFileName;
		}

		public void setTollFileName(String tollFileName) {
			TollFileName = tollFileName;
		}

		public Long getTollFileSize() {
			return TollFileSize;
		}

		public void setTollFileSize(Long tollFileSize) {
			TollFileSize = tollFileSize;
		}

		public String getTollFileType() {
			return TollFileType;
		}

		public void setTollFileType(String tollFileType) {
			TollFileType = tollFileType;
		}

		public ProjDocFileEntity getProjDocFileEntity() {
			return projDocFileEntity;
		}
		
		public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
			this.projDocFileEntity = projDocFileEntity;
		}

}
