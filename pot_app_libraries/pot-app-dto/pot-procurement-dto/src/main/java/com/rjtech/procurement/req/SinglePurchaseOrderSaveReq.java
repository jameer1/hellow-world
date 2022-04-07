package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PurchaseOrderDetailsTO;
import com.rjtech.procurement.dto.SinglePurchaseOrderTO;

public class SinglePurchaseOrderSaveReq extends ProjectTO {

    private static final long serialVersionUID = 842143099691436540L;

    private List<SinglePurchaseOrderTO> singlePurchaseOrderTOs = new ArrayList<SinglePurchaseOrderTO>();
    private PreContractTO preContractTO = new PreContractTO();
    private Map<Long, Long> approvedCompanyMap;
    private PurchaseOrderDetailsTO poDetailsTO;
    private Date poStartDate;
    private Date poFinishtDate;
    private String poProcureType;
    private String typeOfPO;
    private Long purchaseOrderId;
    private Long contractId;

    public List<SinglePurchaseOrderTO> getSinglePurchaseOrderTOs() {
        return singlePurchaseOrderTOs;
    }

    public void setSinglePurchaseOrderTOs(List<SinglePurchaseOrderTO> singlePurchaseOrderTOs) {
        this.singlePurchaseOrderTOs = singlePurchaseOrderTOs;
    }

    public PreContractTO getPreContractTO() {
        return preContractTO;
    }

    public void setPreContractTO(PreContractTO preContractTO) {
        this.preContractTO = preContractTO;
    }

    public Map<Long, Long> getApprovedCompanyMap() {
        return approvedCompanyMap;
    }

    public void setApprovedCompanyMap(Map<Long, Long> approvedCompanyMap) {
        this.approvedCompanyMap = approvedCompanyMap;
    }

    public PurchaseOrderDetailsTO getPoDetailsTO() {
        return poDetailsTO;
    }

    public void setPoDetailsTO(PurchaseOrderDetailsTO poDetailsTO) {
        this.poDetailsTO = poDetailsTO;
    }

    public Date getPoStartDate() {
        return poStartDate;
    }

    public void setPoStartDate(Date poStartDate) {
        this.poStartDate = poStartDate;
    }

    public Date getPoFinishtDate() {
        return poFinishtDate;
    }

    public void setPoFinishtDate(Date poFinishtDate) {
        this.poFinishtDate = poFinishtDate;
    }

    public String getPoProcureType() {
        return poProcureType;
    }

    public void setPoProcureType(String poProcureType) {
        this.poProcureType = poProcureType;
    }

    public String getTypeOfPO() {
        return typeOfPO;
    }

    public void setTypeOfPO(String typeOfPO) {
        this.typeOfPO = typeOfPO;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
}
