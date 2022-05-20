package com.rjtech.projectlib.service;

import com.rjtech.projectlib.req.ChangeOrderReq;
import com.rjtech.projectlib.resp.ChangeOrderResp;

public interface ChangeOrderDtlService {

	public ChangeOrderResp saveChangeOrderDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp getChangeOrderDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp saveCoScopeOfWork(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp saveCoManpowerDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp getCoManpowerDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp saveCoPlantDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp getCoPlantDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp updateCoApproverDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp saveCoMaterialDetails(ChangeOrderReq changeOrderReq);

	public ChangeOrderResp saveCoCostDetails(ChangeOrderReq changeOrderReq);

}
