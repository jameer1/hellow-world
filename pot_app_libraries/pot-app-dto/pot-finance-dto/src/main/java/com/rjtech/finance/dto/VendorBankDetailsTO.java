package com.rjtech.finance.dto;

import java.util.List;

public class VendorBankDetailsTO {

		private List<VendorBankAccountDetailsTO> vendorBankDetailsTOs;
		private List<ApproverValidateDetailsTO> approverValidateDetailsTOs;
		
		
		public List<VendorBankAccountDetailsTO> getVendorBankDetailsTOs() {
			return vendorBankDetailsTOs;
		}
		public void setVendorBankDetailsTOs(List<VendorBankAccountDetailsTO> vendorBankDetailsTOs) {
			this.vendorBankDetailsTOs = vendorBankDetailsTOs;
		}
		public List<ApproverValidateDetailsTO> getApproverValidateDetailsTOs() {
			return approverValidateDetailsTOs;
		}
		public void setApproverValidateDetailsTOs(List<ApproverValidateDetailsTO> approverValidateDetailsTOs) {
			this.approverValidateDetailsTOs = approverValidateDetailsTOs;
		}
		
		
}
