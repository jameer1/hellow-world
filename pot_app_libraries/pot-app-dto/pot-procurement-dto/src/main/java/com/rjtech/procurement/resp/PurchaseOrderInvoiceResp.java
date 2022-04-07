package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.InvoiceAmountTO;
import com.rjtech.procurement.dto.InvoiceAssignCostCodesTO;
import com.rjtech.procurement.dto.InvoiceParticularsTO;
import com.rjtech.procurement.dto.InvoiceVendorBankTO;
import com.rjtech.procurement.dto.PurchaseOrderInvoiceDtlTO;

public class PurchaseOrderInvoiceResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3177617404253956817L;
    private List<PurchaseOrderInvoiceDtlTO> purchaseOrderInvoiceDtlTOs = null;
    private List<InvoiceParticularsTO> invoiceParticularsTOs = null;
    private List<InvoiceAmountTO> invoiceAmountTOs = null;
    private List<InvoiceAssignCostCodesTO> assignCostCodesTOs = null;
    private List<InvoiceVendorBankTO> vendorBankTOs = null;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, LabelKeyTO> companyMap = null;
    private Map<Long, LabelKeyTO> usersMap = null;
    private Map<Long, LabelKeyTO> projCostItemMap = null;

    public PurchaseOrderInvoiceResp() {
        purchaseOrderInvoiceDtlTOs = new ArrayList<PurchaseOrderInvoiceDtlTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        invoiceParticularsTOs = new ArrayList<InvoiceParticularsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        invoiceAmountTOs = new ArrayList<InvoiceAmountTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        assignCostCodesTOs = new ArrayList<InvoiceAssignCostCodesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        vendorBankTOs = new ArrayList<InvoiceVendorBankTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        companyMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        usersMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projCostItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PurchaseOrderInvoiceDtlTO> getPurchaseOrderInvoiceDtlTOs() {
        return purchaseOrderInvoiceDtlTOs;
    }

    public void setPurchaseOrderInvoiceDtlTOs(List<PurchaseOrderInvoiceDtlTO> purchaseOrderInvoiceDtlTOs) {
        this.purchaseOrderInvoiceDtlTOs = purchaseOrderInvoiceDtlTOs;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public List<InvoiceParticularsTO> getInvoiceParticularsTOs() {
        return invoiceParticularsTOs;
    }

    public void setInvoiceParticularsTOs(List<InvoiceParticularsTO> invoiceParticularsTOs) {
        this.invoiceParticularsTOs = invoiceParticularsTOs;
    }

    public List<InvoiceAmountTO> getInvoiceAmountTOs() {
        return invoiceAmountTOs;
    }

    public void setInvoiceAmountTOs(List<InvoiceAmountTO> invoiceAmountTOs) {
        this.invoiceAmountTOs = invoiceAmountTOs;
    }

    public List<InvoiceAssignCostCodesTO> getAssignCostCodesTOs() {
        return assignCostCodesTOs;
    }

    public void setAssignCostCodesTOs(List<InvoiceAssignCostCodesTO> assignCostCodesTOs) {
        this.assignCostCodesTOs = assignCostCodesTOs;
    }

    public List<InvoiceVendorBankTO> getVendorBankTOs() {
        return vendorBankTOs;
    }

    public void setVendorBankTOs(List<InvoiceVendorBankTO> vendorBankTOs) {
        this.vendorBankTOs = vendorBankTOs;
    }

    public Map<Long, LabelKeyTO> getProjCostItemMap() {
        return projCostItemMap;
    }

    public void setProjCostItemMap(Map<Long, LabelKeyTO> projCostItemMap) {
        this.projCostItemMap = projCostItemMap;
    }

}
