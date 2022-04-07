package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.InvoiceAmountTO;
import com.rjtech.procurement.dto.InvoiceAssignCostCodesTO;
import com.rjtech.procurement.dto.InvoiceParticularsTO;
import com.rjtech.procurement.dto.InvoiceVendorBankTO;
import com.rjtech.procurement.dto.ManpowerInvoiceDocketItemTO;
import com.rjtech.procurement.dto.MaterialInvoiceDocketItemTO;
import com.rjtech.procurement.dto.PlantInvoiceDocketItemTO;
import com.rjtech.procurement.dto.PurchaseOrderInvoiceDtlTO;
import com.rjtech.procurement.dto.ServiceInvoiceDocketItemTO;
import com.rjtech.procurement.dto.SowInvoiceDocketItemTO;

public class PurchaseOrderInvoiceSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3177617404253956817L;
    private PurchaseOrderInvoiceDtlTO purchaseOrderInvoiceDtlTO = null;
    private List<ManpowerInvoiceDocketItemTO> manpowerInvoiceDocketItemTOs = null;
    private List<PlantInvoiceDocketItemTO> plantInvoiceDocketItemTOs = null;
    private List<MaterialInvoiceDocketItemTO> materialInvoiceDocketItemTOs = null;
    private List<ServiceInvoiceDocketItemTO> serviceInvoiceDocketItemTOs = null;
    private List<SowInvoiceDocketItemTO> sowInvoiceDocketItemTOs = null;
    private List<InvoiceParticularsTO> invoiceParticularsTOs = null;
    private List<InvoiceAmountTO> invoiceAmountTOs = null;
    private List<InvoiceAssignCostCodesTO> assignCostCodesTOs = null;
    private List<InvoiceVendorBankTO> vendorBankTOs = null;
    private PurchaseOrderGetReq purchaseOrderGetReq = new PurchaseOrderGetReq();
    private Long purId;

    public PurchaseOrderInvoiceSaveReq() {
        purchaseOrderInvoiceDtlTO = new PurchaseOrderInvoiceDtlTO();
        manpowerInvoiceDocketItemTOs = new ArrayList<ManpowerInvoiceDocketItemTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        plantInvoiceDocketItemTOs = new ArrayList<PlantInvoiceDocketItemTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        materialInvoiceDocketItemTOs = new ArrayList<MaterialInvoiceDocketItemTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        serviceInvoiceDocketItemTOs = new ArrayList<ServiceInvoiceDocketItemTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        sowInvoiceDocketItemTOs = new ArrayList<SowInvoiceDocketItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        invoiceParticularsTOs = new ArrayList<InvoiceParticularsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        invoiceAmountTOs = new ArrayList<InvoiceAmountTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        assignCostCodesTOs = new ArrayList<InvoiceAssignCostCodesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        vendorBankTOs = new ArrayList<InvoiceVendorBankTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public PurchaseOrderInvoiceDtlTO getPurchaseOrderInvoiceDtlTO() {
        return purchaseOrderInvoiceDtlTO;
    }

    public void setPurchaseOrderInvoiceDtlTO(PurchaseOrderInvoiceDtlTO purchaseOrderInvoiceDtlTO) {
        this.purchaseOrderInvoiceDtlTO = purchaseOrderInvoiceDtlTO;
    }

    public List<ManpowerInvoiceDocketItemTO> getManpowerInvoiceDocketItemTOs() {
        return manpowerInvoiceDocketItemTOs;
    }

    public void setManpowerInvoiceDocketItemTOs(List<ManpowerInvoiceDocketItemTO> manpowerInvoiceDocketItemTOs) {
        this.manpowerInvoiceDocketItemTOs = manpowerInvoiceDocketItemTOs;
    }

    public List<PlantInvoiceDocketItemTO> getPlantInvoiceDocketItemTOs() {
        return plantInvoiceDocketItemTOs;
    }

    public void setPlantInvoiceDocketItemTOs(List<PlantInvoiceDocketItemTO> plantInvoiceDocketItemTOs) {
        this.plantInvoiceDocketItemTOs = plantInvoiceDocketItemTOs;
    }

    public List<MaterialInvoiceDocketItemTO> getMaterialInvoiceDocketItemTOs() {
        return materialInvoiceDocketItemTOs;
    }

    public void setMaterialInvoiceDocketItemTOs(List<MaterialInvoiceDocketItemTO> materialInvoiceDocketItemTOs) {
        this.materialInvoiceDocketItemTOs = materialInvoiceDocketItemTOs;
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

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public List<ServiceInvoiceDocketItemTO> getServiceInvoiceDocketItemTOs() {
        return serviceInvoiceDocketItemTOs;
    }

    public void setServiceInvoiceDocketItemTOs(List<ServiceInvoiceDocketItemTO> serviceInvoiceDocketItemTOs) {
        this.serviceInvoiceDocketItemTOs = serviceInvoiceDocketItemTOs;
    }

    public List<SowInvoiceDocketItemTO> getSowInvoiceDocketItemTOs() {
        return sowInvoiceDocketItemTOs;
    }

    public void setSowInvoiceDocketItemTOs(List<SowInvoiceDocketItemTO> sowInvoiceDocketItemTOs) {
        this.sowInvoiceDocketItemTOs = sowInvoiceDocketItemTOs;
    }

    public PurchaseOrderGetReq getPurchaseOrderGetReq() {
        return purchaseOrderGetReq;
    }

    public void setPurchaseOrderGetReq(PurchaseOrderGetReq purchaseOrderGetReq) {
        this.purchaseOrderGetReq = purchaseOrderGetReq;
    }

}
