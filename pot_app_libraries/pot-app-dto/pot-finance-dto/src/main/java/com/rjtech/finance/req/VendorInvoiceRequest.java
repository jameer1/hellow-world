package com.rjtech.finance.req;

import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.finance.dto.ApproverValidateDetailsTO;
import com.rjtech.finance.dto.AssignCostCodesTO;
import com.rjtech.finance.dto.InvoiceSectionTO;
import com.rjtech.finance.dto.InvoiceparticularsTO;
import com.rjtech.finance.dto.ManpowerDeliveryDocketTO;
import com.rjtech.finance.dto.MaterialDeliveryDocketTO;
import com.rjtech.finance.dto.PlantsDeliveryDocketTO;
import com.rjtech.finance.dto.ServiceDeliveryDocketTO;
import com.rjtech.finance.dto.SubDeliveryDocketTO;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;

public class VendorInvoiceRequest  extends ClientTO {
	
	private InvoiceSectionTO asPerSystemVerification;
	private InvoiceSectionTO asPerInvoice;
	private InvoiceSectionTO amountRetained;
	private InvoiceSectionTO netAmountPayable;
	
	private InvoiceparticularsTO invoiceparticularsTO;
	private List<AssignCostCodesTO> assignCostCodesTOList;
	private VendorBankAccountDetailsTO vendorBankAccountDetailsTO;
	private ApproverValidateDetailsTO approverValidateDetailsTO;
	
	private List<MaterialDeliveryDocketTO> materialDeliveryDocket;
	private List<ManpowerDeliveryDocketTO> manpowerDeliveryDocketTO;
	private List<PlantsDeliveryDocketTO> plantsDeliveryDocketTO;
	private List<ServiceDeliveryDocketTO> serviceDeliveryDocketTO;
	private List<SubDeliveryDocketTO> subDeliveryDocket;
	
	private int preContractId;
	
	public InvoiceSectionTO getAsPerSystemVerification() {
		return asPerSystemVerification;
	}
	public void setAsPerSystemVerification(InvoiceSectionTO asPerSystemVerification) {
		this.asPerSystemVerification = asPerSystemVerification;
	}
	public InvoiceSectionTO getAsPerInvoice() {
		return asPerInvoice;
	}
	public void setAsPerInvoice(InvoiceSectionTO asPerInvoice) {
		this.asPerInvoice = asPerInvoice;
	}
	public InvoiceSectionTO getAmountRetained() {
		return amountRetained;
	}
	public void setAmountRetained(InvoiceSectionTO amountRetained) {
		this.amountRetained = amountRetained;
	}
	public InvoiceSectionTO getNetAmountPayable() {
		return netAmountPayable;
	}
	public void setNetAmountPayable(InvoiceSectionTO netAmountPayable) {
		this.netAmountPayable = netAmountPayable;
	}
	public InvoiceparticularsTO getInvoiceparticularsTO() {
		return invoiceparticularsTO;
	}
	public void setInvoiceparticularsTO(InvoiceparticularsTO invoiceparticularsTO) {
		this.invoiceparticularsTO = invoiceparticularsTO;
	}
	public List<AssignCostCodesTO> getAssignCostCodesTOList() {
		return assignCostCodesTOList;
	}
	public void setAssignCostCodesTOList(List<AssignCostCodesTO> assignCostCodesTOList) {
		this.assignCostCodesTOList = assignCostCodesTOList;
	}
	public VendorBankAccountDetailsTO getVendorBankAccountDetailsTO() {
		return vendorBankAccountDetailsTO;
	}
	public void setVendorBankAccountDetailsTO(VendorBankAccountDetailsTO vendorBankAccountDetailsTO) {
		this.vendorBankAccountDetailsTO = vendorBankAccountDetailsTO;
	}
	public ApproverValidateDetailsTO getApproverValidateDetailsTO() {
		return approverValidateDetailsTO;
	}
	public void setApproverValidateDetailsTO(ApproverValidateDetailsTO approverValidateDetailsTO) {
		this.approverValidateDetailsTO = approverValidateDetailsTO;
	}
	public List<MaterialDeliveryDocketTO> getMaterialDeliveryDocket() {
		return materialDeliveryDocket;
	}
	public void setMaterialDeliveryDocket(List<MaterialDeliveryDocketTO> materialDeliveryDocket) {
		this.materialDeliveryDocket = materialDeliveryDocket;
	}
	public List<ManpowerDeliveryDocketTO> getManpowerDeliveryDocketTO() {
		return manpowerDeliveryDocketTO;
	}
	public void setManpowerDeliveryDocketTO(List<ManpowerDeliveryDocketTO> manpowerDeliveryDocketTO) {
		this.manpowerDeliveryDocketTO = manpowerDeliveryDocketTO;
	}
	public List<PlantsDeliveryDocketTO> getPlantsDeliveryDocketTO() {
		return plantsDeliveryDocketTO;
	}
	public void setPlantsDeliveryDocketTO(List<PlantsDeliveryDocketTO> plantsDeliveryDocketTO) {
		this.plantsDeliveryDocketTO = plantsDeliveryDocketTO;
	}
	public List<ServiceDeliveryDocketTO> getServiceDeliveryDocketTO() {
		return serviceDeliveryDocketTO;
	}
	public void setServiceDeliveryDocketTO(List<ServiceDeliveryDocketTO> serviceDeliveryDocketTO) {
		this.serviceDeliveryDocketTO = serviceDeliveryDocketTO;
	}
	public List<SubDeliveryDocketTO> getSubDeliveryDocket() {
		return subDeliveryDocket;
	}
	public void setSubDeliveryDocket(List<SubDeliveryDocketTO> subDeliveryDocket) {
		this.subDeliveryDocket = subDeliveryDocket;
	}
	public int getPreContractId() {
		return preContractId;
	}
	public void setPreContractId(int preContractId) {
		this.preContractId = preContractId;
	}
	
}
