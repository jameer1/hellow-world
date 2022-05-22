/**
 * 
 */
package com.rjtech.procurement.resp;

import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.procurement.dto.ProcurementSubCategory;

/**
 * @author sowmya
 *
 */
public class ProcurementSubCatResp extends AppResp{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProcurementSubCategory> procurementSubCatList;

	public List<ProcurementSubCategory> getProcurementSubCatList() {
		return procurementSubCatList;
	}

	public void setProcurementSubCatList(List<ProcurementSubCategory> procurementSubCatList) {
		this.procurementSubCatList = procurementSubCatList;
	}

	
}
