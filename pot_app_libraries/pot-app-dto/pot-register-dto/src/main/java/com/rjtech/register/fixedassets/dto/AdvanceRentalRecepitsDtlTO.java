package com.rjtech.register.fixedassets.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.rjtech.common.dto.ClientTO;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvanceRentalRecepitsDtlTO extends ClientTO {

    private static final long serialVersionUID = 6048003796093964935L;

    private Long id;
    private String givenDate;
    private String currency;
    private String modeOfPayment;
    private Long amountPaid;
    private String note;
    private String receiptNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(String givenDate) {
        this.givenDate = givenDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
}
