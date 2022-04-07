package com.rjtech.projectlib.service.handler;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjPMCMItemTO;
import com.rjtech.projectlib.model.ProjPMCMItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.req.ProjPMCMItemSaveReq;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProjPMCMItemHandler {

    public static ProjPMCMItemTO populateSORITems(ProjPMCMItemEntity projSORItemEntity, boolean addChild) {
        ProjPMCMItemTO projSORItemTO = null;
        if (CommonUtil.objectNotNull(projSORItemEntity)) {
            projSORItemTO = new ProjPMCMItemTO();
            projSORItemTO.setId(projSORItemEntity.getId());
            projSORItemTO.setCode(projSORItemEntity.getCode());
            projSORItemTO.setName(projSORItemEntity.getName());
            projSORItemTO.setProjId(projSORItemEntity.getProjMstrEntity().getProjectId());
            projSORItemTO.setItem(convertToInt2Boolean(projSORItemEntity.getItem()));
            projSORItemTO.setComments(projSORItemEntity.getComments());

            if (CommonUtil.objectNotNull(projSORItemEntity.getProjPMCMItemEntity())) {
                projSORItemTO.setParentId(projSORItemEntity.getProjPMCMItemEntity().getId());
                projSORItemTO.setParentName(projSORItemEntity.getProjPMCMItemEntity().getName());
            }

            projSORItemTO.setPmCostCodeId(projSORItemEntity.getPmCostCodeId());
            projSORItemTO.setPmCurrency(projSORItemEntity.getPmCurrency());
            projSORItemTO.setPmContractAmount(projSORItemEntity.getPmContractAmount());
            projSORItemTO.setPmProgressStatus(convertProgressStatusIntToString(projSORItemEntity.getPmProgressStatus()));
            if (CommonUtil.isNotBlankDate(projSORItemEntity.getPmSchedFinishDate())) {
                projSORItemTO.setPmSchedFinishDate(CommonUtil.convertDateToString(projSORItemEntity.getPmSchedFinishDate()));
            }
            if (CommonUtil.isNotBlankDate(projSORItemEntity.getPmActualFinishDate())) {
                projSORItemTO.setPmActualFinishDate(CommonUtil.convertDateToString(projSORItemEntity.getPmActualFinishDate()));
            }
            if (CommonUtil.isNotBlankDate(projSORItemEntity.getPmStatusDate())) {
                projSORItemTO.setPmStatusDate(CommonUtil.convertDateToString(projSORItemEntity.getPmStatusDate()));
            }
            projSORItemTO.setPmPrevProgClaim(projSORItemEntity.getPmPrevProgClaim());
            projSORItemTO.setPmPreviousProgClaim(convertPrevProgClaimLongToString(projSORItemEntity.getPmPrevProgClaim()));
            projSORItemTO.setPmClaimedAmount(projSORItemEntity.getPmClaimedAmount());
            // for Report ---
            ProjMstrEntity projMstr = projSORItemEntity.getProjMstrEntity();
                if (null != projMstr) {
                    projSORItemTO.setProjId(projMstr.getProjectId());
                    projSORItemTO.setProjCode(projMstr.getCode());
                    projSORItemTO.setProjName(projMstr.getProjName());
                    ProjMstrEntity epsMstr = projMstr.getParentProjectMstrEntity();
                    if (null != epsMstr) {
                        projSORItemTO.setEpsId(epsMstr.getProjectId());
                        projSORItemTO.setEpsCode(epsMstr.getCode());
                        projSORItemTO.setEpsName(epsMstr.getProjName());
                    }
                }
            if (addChild) {
                projSORItemTO.getChildSORItemTOs().addAll(addChilds(projSORItemEntity, projSORItemTO, addChild));
            }
        }
        return projSORItemTO;
    }

    public static List<ProjPMCMItemTO> addChilds(ProjPMCMItemEntity projSORItemEntity, ProjPMCMItemTO projSORItemTO,
                                                boolean addChild) {
        List<ProjPMCMItemTO> childSORTOs = new ArrayList<ProjPMCMItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.objectNotNull(projSORItemEntity.getChildEntities())) {
            for (ProjPMCMItemEntity childEntity : projSORItemEntity.getChildEntities()) {
                //if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
            	 if(childEntity.getStatus().equals(1)) {
            		 childSORTOs.add(populateSORITems(childEntity, addChild)); 
            	 }
                   
                //}
            }
        }
        return childSORTOs;
    }

    public static List<ProjPMCMItemEntity> populateEntitisFromPOJO(ProjPMCMItemSaveReq projSORItemSaveReq,
                                                                  EPSProjRepository projRepository) {
        List<ProjPMCMItemEntity> entities = new ArrayList<ProjPMCMItemEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjPMCMItemEntity entity = null;
        for (ProjPMCMItemTO projSOEItemTO : projSORItemSaveReq.getProjSORItemTOs()) {
            entity = new ProjPMCMItemEntity();
            converPOJOToEntity(entity, projSOEItemTO, projRepository);
            entities.add(entity);
        }
        return entities;
    }

    private static ProjPMCMItemEntity converPOJOToEntity(ProjPMCMItemEntity projSORItemEntity,
                                                        ProjPMCMItemTO projSORItemTO, EPSProjRepository projRepository) {
        if (CommonUtil.isNonBlankLong(projSORItemTO.getId())) {
            projSORItemEntity.setId(projSORItemTO.getId());
        }
        projSORItemEntity.setCode(projSORItemTO.getCode());
        projSORItemEntity.setName(projSORItemTO.getName());

        if (CommonUtil.isNonBlankLong(projSORItemTO.getParentId())) {
            ProjPMCMItemEntity parentEntity = new ProjPMCMItemEntity();
            parentEntity.setId(projSORItemTO.getParentId());
            projSORItemEntity.setProjPMCMItemEntity(parentEntity);
        }

        ProjMstrEntity proj = projRepository.findOne(projSORItemTO.getProjId());
        projSORItemEntity.setProjMstrEntity(proj);

        projSORItemEntity.setComments(projSORItemTO.getComments());
        projSORItemEntity.setItem(convertBooleanToInt(projSORItemTO.isItem()));

        projSORItemEntity.setPmCostCodeId(projSORItemTO.getPmCostCodeId());
        projSORItemEntity.setPmCurrency(projSORItemTO.getPmCurrency());
        projSORItemEntity.setPmContractAmount(projSORItemTO.getPmContractAmount());
        projSORItemEntity.setPmProgressStatus(convertProgressStatusStringToInt(projSORItemTO.getPmProgressStatus()));
        projSORItemEntity.setStatus(projSORItemTO.getStatus());
        projSORItemEntity.setPmPrevProgClaim(convertPrevProgClaimStringToLong(projSORItemTO.getPmPreviousProgClaim()));
      

        if (CommonUtil.isNotBlankStr(projSORItemTO.getPmSchedFinishDate())) {
            projSORItemEntity.setPmSchedFinishDate(CommonUtil.convertStringToDate(projSORItemTO.getPmSchedFinishDate()));
        }
        if (CommonUtil.isNotBlankStr(projSORItemTO.getPmActualFinishDate())) {
            projSORItemEntity.setPmActualFinishDate(CommonUtil.convertStringToDate(projSORItemTO.getPmActualFinishDate()));
        }
        if (CommonUtil.isNotBlankStr(projSORItemTO.getPmStatusDate())) {
            projSORItemEntity.setPmStatusDate(CommonUtil.convertStringToDate(projSORItemTO.getPmStatusDate()));
        }
        if (CommonUtil.isNonBlankLong(projSORItemTO.getPmPrevProgClaim())) {
            projSORItemEntity.setPmPrevProgClaim(projSORItemTO.getPmPrevProgClaim());
        }
        if (CommonUtil.isNonBlankLong(projSORItemTO.getPmClaimedAmount())) {
            projSORItemEntity.setPmClaimedAmount(projSORItemTO.getPmClaimedAmount());
        }

        ProjPMCMItemEntity childEntity = null;
        for (ProjPMCMItemTO childTO : projSORItemTO.getChildSORItemTOs()) {
            childEntity = new ProjPMCMItemEntity();
            childEntity.setProjPMCMItemEntity(projSORItemEntity);
            projSORItemEntity.getChildEntities().add(converPOJOToEntity(childEntity, childTO, projRepository));
        }
        return projSORItemEntity;
    }

    public static Integer convertBooleanToInt(boolean value)
    {
        if(value==true)
            return new Integer(1);
        else
            return new Integer(0);
    }

    public static boolean convertToInt2Boolean(Integer intValue)
    {
        int i = 0;
        if(intValue!=null)
        {
            i = intValue.intValue();
        }

        if(i==1)
            return true;
        else
            return false;
    }

    public static String getDateWithDDMMMYYYYFormat(Date inputDate) {
        if (inputDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM-yyyy");
            String inpuDateStr = sdf.format(inputDate);
            return inpuDateStr;
        }
        return null;
    }

    public static Date getStringDDMMMYYYYFormat(String inputDate) {
        if (inputDate != null || !"".equals(inputDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM-yyyy");
            Date inpuDateStr = null;
            try {
                inpuDateStr = sdf.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return inpuDateStr;
        }
        return null;
    }

    public static Integer convertProgressStatusStringToInt(String value)
    {
        if(value!=null && value.equalsIgnoreCase("complete"))
        {
            return new Integer(1);
        }else
        {
            return new Integer(0);
        }
    }

    public static String convertProgressStatusIntToString(Integer intValue)
    {
        int i = 0;
        if(intValue!=null)
        {
            i = intValue.intValue();
        }

        if(i==1)
            return "complete";
        else
            return "inProgress";
    }
    public static Integer convertPrevProgClaimStringToInt(String value)
    {
        if(value!=null && value.equalsIgnoreCase("Yes"))
        {
            return new Integer(1);
        }else
        {
            return new Integer(0);
        }
    }
    
    public static Long convertPrevProgClaimStringToLong(String value)
    {
        if(value!=null && value.equalsIgnoreCase("Yes"))
        {
            return new Long(1);
        }else
        {
            return new Long(0);
        }
    }

    public static String convertPrevProgClaimIntToString(Integer intValue)
    {
        int i = 0;
        if(intValue!=null)
        {
            i = intValue.intValue();
        }

        if(i==1)
            return "Yes";
        else
            return "No";
    }
    
    public static String convertPrevProgClaimLongToString(Long longValue)
    {
        long i = 0;
        if(longValue!=null)
        {
            i = longValue.longValue();
        }

        if(i==1)
            return "Yes";
        else
            return "No";
    }
}