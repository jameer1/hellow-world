package com.rjtech.mw.service.projlib;

import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjLibMapResp;
import com.rjtech.projectlib.resp.ProjLibUniqueMapResp;

public interface MWProjLibMapService {

    /* public ProjLibMapResp ProjEmpClassMap(ProjGetReq projGetReq); */

    public ProjLibMapResp ProjStockPileMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp ProjWorkShiftMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp ProjCrewListMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp ProjSOEMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp ProjSORMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp ProjCostCodeMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp getEpsListMap(ProjGetReq projGetReq);

    public ProjLibMapResp ProjEmpClassMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibUniqueMapResp ProjPlantClassMap(ProjCrewGetReq projCrewGetReq);

    public ProjLibMapResp getEpsProjectMap(ProjGetReq projGetReq);

    public LabelKeyTOMapResp getMultiEPSProjCodeMap(ProjGetReq projGetReq);

    public LabelKeyTOMapResp getMultiProjCodeMap(ProjGetReq projGetReq);

    public LabelKeyTOMapResp getMultiProjSOWItemMap(ProjGetReq projGetReq);

    public LabelKeyTOMapResp getMultiProjSORItemMap(ProjGetReq projGetReq);

    public LabelKeyTOMapResp getMultiProjCostCodeMap(ProjGetReq projGetReq);

}
