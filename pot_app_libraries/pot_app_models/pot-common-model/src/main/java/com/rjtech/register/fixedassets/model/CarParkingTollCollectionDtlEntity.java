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
@Table(name = "car_parking_toll_collection")
public class CarParkingTollCollectionDtlEntity implements Serializable {
    private static final long serialVersionUID = -8795406421033703996L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FCP_ID")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "FCP_DATE")
    private Date date;

    @Column(name = "FCP_MODEOFPAYMENT")
    private String modeOfPayment;

    @Column(name = "FCP_BANKNAME")
    private String bankName;

    @Column(name = "FCP_BANKCODE")
    private String bankCode;

    @Column(name = "FCP_BANKAC")
    private String bankAc;

    @Column(name = "FCP_TRANSFERRECEIPTNO")
    private String transferReceiptNo;

    @Column(name = "FCP_NETAMOUNT")
    private Long netAmount;

    @Column(name = "FCP_TAXAMOUNT")
    private Long taxAmount;

    @Column(name = "FCP_CUMULATIVENETAMOUNT")
    private Long cumulativeNetAmount;

    @Column(name = "FCP_CUMULATIVETAXAMOUNT")
    private Long cumulativeTaxAmount;

    @Column(name = "FCP_FORECASTNETAMT")
    private Long forecastNetAmt;

    @Column(name = "FCP_FORECASTTAXAMT")
    private Long forecastTaxAmt;

    @Column(name = "FCP_CUMULATIVENETREVENUE")
    private String cumulativeNetRevenue;

    @Column(name = "FCP_cumulativeTax")
    private Long cumulativeTax;

    @Column(name = "FCP_CARPARKINGTOLLFILENAME")
    private String carParkingTollFileName;

    @Column(name = "FCP_CARPARKINGTOLLFILESIZE")
    private Long carParkingTollFileSize;

    @Column(name = "FCP_CARPARKINGTOLLFILETYPE")
    private String carParkingTollFileType;

    @Column(name = "FCP_DOCKEY")
    private String docKey;

    @ManyToOne
    @JoinColumn(name = "FCP_CREATED_BY")
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FCP_CREATED_ON")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "FCP__UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FCP_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "FCP_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "FCP_FIXEDASSETID")
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

    public String getCarParkingTollFileName() {
        return carParkingTollFileName;
    }

    public void setCarParkingTollFileName(String carParkingTollFileName) {
        this.carParkingTollFileName = carParkingTollFileName;
    }

    public Long getCarParkingTollFileSize() {
        return carParkingTollFileSize;
    }

    public void setCarParkingTollFileSize(Long carParkingTollFileSize) {
        this.carParkingTollFileSize = carParkingTollFileSize;
    }

    public String getCarParkingTollFileType() {
        return carParkingTollFileType;
    }

    public void setCarParkingTollFileType(String carParkingTollFileType) {
        this.carParkingTollFileType = carParkingTollFileType;
    }
    
    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
        this.projDocFileEntity = projDocFileEntity;
    }
}
