package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.dto.ProcurementTO;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.req.ProcureCatgSaveReq;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class ProcureCatgHandler {

    public static ProcureCatgResp convertEntityToPOJO(List<ProcureCatgDtlEntity> entities) {
        ProcureCatgResp procureCatgResp = new ProcureCatgResp();
        ProcureMentCatgTO classificationTO = null;
        for (ProcureCatgDtlEntity entity : entities) {
            classificationTO = new ProcureMentCatgTO();
            classificationTO.setProCatgId(entity.getId());
            classificationTO.setCode(entity.getCode());
            classificationTO.setDesc(entity.getName());
            classificationTO.setStatus(entity.getStatus());
            classificationTO.setProcureId(entity.getProcureType());
            ProcurementTO procurementTO = new ProcurementTO();
            procurementTO.setName(entity.getProcureType());
            classificationTO.setProcurement(procurementTO);
            procureCatgResp.getProcureMentCatgTOs().add(classificationTO);
        }
        return procureCatgResp;
    }

    public static List<ProcureCatgDtlEntity> convertPOJOToEntity(ProcureCatgSaveReq procureCatgSaveReq) {
        List<ProcureCatgDtlEntity> procureCatgDtlEntitys = new ArrayList<ProcureCatgDtlEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProcureCatgDtlEntity procureCatgDtlEntity = null;
        for (ProcureMentCatgTO procureMentCatagoryTo : procureCatgSaveReq.getProcureMentCatgTOs()) {
            procureCatgDtlEntity = new ProcureCatgDtlEntity();
            if (CommonUtil.isNonBlankLong(procureMentCatagoryTo.getProCatgId())) {
                procureCatgDtlEntity.setId(procureMentCatagoryTo.getProCatgId());
            }
            procureCatgDtlEntity.setCode(procureMentCatagoryTo.getCode());
            procureCatgDtlEntity.setName(procureMentCatagoryTo.getDesc());
            procureCatgDtlEntity.setProcureType(procureMentCatagoryTo.getProcureId());
            procureCatgDtlEntity.setStatus(procureMentCatagoryTo.getStatus());
            procureCatgDtlEntitys.add(procureCatgDtlEntity);
        }
        return procureCatgDtlEntitys;
    }

    public static LabelKeyTO convertProcureCatgDtlEntityToLabelKeyTo(ProcureCatgDtlEntity procureCatgDtlEntity) {
    	 //System.out.println("convertProcureCatgDtlEntityToLabelKeyTo");
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(procureCatgDtlEntity.getId());
        labelKeyTO.setCode(procureCatgDtlEntity.getCode());
        labelKeyTO.setName(procureCatgDtlEntity.getName());
        //System.out.println("labelKeyTO.setName"+labelKeyTO.getName());
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE,
                String.valueOf(procureCatgDtlEntity.getProcureType()));
       // System.out.println("labelKeyTO.getDisplayNamesMap()"+labelKeyTO.getDisplayNamesMap());
       // System.out.println("labelKeyTO"+labelKeyTO);
        return labelKeyTO;
    }

}
