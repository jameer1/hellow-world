package com.rjtech.calendar.service.handler;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class GlobalCalHandler {

    public static CalTO convertEntityToPOJO(GlobalCalEntity calenderEntity) {
        CalTO calenderTO = new CalTO();

        calenderTO.setId(calenderEntity.getId());
        calenderTO.setName(calenderEntity.getName());
        calenderTO.setLatest(calenderEntity.isLatest());
        calenderTO.setCalDefaultValue(calenderEntity.getCalDefaultValue());
        calenderTO.setProjectAssigned(calenderEntity.isProjectAssigned());
        if (CommonUtil.isNotBlankDate(calenderEntity.getFromDate())) {
            calenderTO.setFromDate(CommonUtil.convertDateToString(calenderEntity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(calenderEntity.getToDate())) {
            calenderTO.setToDate(CommonUtil.convertDateToString(calenderEntity.getToDate()));
        }
        ClientRegEntity clientRegEntity = calenderEntity.getClientId();
        if (null != clientRegEntity) {
            calenderTO.setClientId(clientRegEntity.getClientId());
        }
        if (CommonUtil.objectNotNull(calenderEntity.getProjMstrEntity())) {
            calenderTO.setProjId(calenderEntity.getProjMstrEntity().getProjectId());
            calenderTO.setProjectName(calenderEntity.getProjMstrEntity().getProjName());
        }
        calenderTO.setCalType(calenderEntity.getCalType());
        calenderTO.setStatus(calenderEntity.getStatus());
        return calenderTO;

    }

    public static GlobalCalEntity convertPOJOToEntity(CalTO calenderTO, EPSProjRepository epsProjRepository) {
        GlobalCalEntity calenderEntity = new GlobalCalEntity();

        if (CommonUtil.isNonBlankLong(calenderTO.getId())) {
            calenderEntity.setId(calenderTO.getId());
        }
        calenderEntity.setName(calenderTO.getName());
        calenderEntity.setLatest(calenderTO.isLatest());
        calenderEntity.setCalType(calenderTO.getCalType());
        calenderEntity.setCalDefaultValue(calenderTO.getCalDefaultValue());
        calenderEntity.setLatest(calenderTO.isLatest());
        if (CommonUtil.isNonBlankLong(calenderTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(calenderTO.getProjId());
            calenderEntity.setProjMstrEntity(projMstrEntity);
        }
        if (CommonUtil.isNotBlankStr(calenderTO.getFromDate())) {
            calenderEntity.setFromDate(CommonUtil.convertStringToDate(calenderTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(calenderTO.getToDate())) {
            calenderEntity.setToDate(CommonUtil.convertStringToDate(calenderTO.getToDate()));
        }
        calenderEntity.setStatus(calenderTO.getStatus());
        return calenderEntity;
    }

}
