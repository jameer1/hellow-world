package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.ServiceClassTO;
import com.rjtech.centrallib.model.ServiceMstrEntity;
import com.rjtech.centrallib.req.ServiceSaveReq;
import com.rjtech.centrallib.resp.ServiceResp;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class ServiceClassHandler {

    public static ServiceResp convertEntityToPOJO(List<ServiceMstrEntity> entities) {
        ServiceResp serviceResp = new ServiceResp();
        ServiceClassTO serviceClassTO = null;
        for (ServiceMstrEntity entity : entities) {
            serviceClassTO = new ServiceClassTO();
            serviceClassTO.setId(entity.getId());
            serviceClassTO.setCode(entity.getCode());
            serviceClassTO.setName(entity.getName());
            ClientRegEntity clientRegEntity = entity.getClientId();
            if (null != clientRegEntity) {
                serviceClassTO.setClientId(clientRegEntity.getClientId());
            }
            serviceClassTO.setStatus(entity.getStatus());
            serviceClassTO.setProcSubCatCode(entity.getProcSubcatCode());
            serviceClassTO.setProcSubCatName(entity.getProcSubcatName());
            serviceClassTO.setProCatgId(entity.getProcSubcatId());
            serviceResp.getServiceClassTOs().add(serviceClassTO);
        }
        return serviceResp;
    }

    public static List<ServiceMstrEntity> convertPOJOToEntity(ServiceSaveReq serviceSaveReq) {
        List<ServiceMstrEntity> serviceMstrEntities = new ArrayList<ServiceMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ServiceMstrEntity entity = null;
        for (ServiceClassTO ServiceClassTO : serviceSaveReq.getServiceClassTOs()) {
            entity = new ServiceMstrEntity();
            if (CommonUtil.isNonBlankLong(ServiceClassTO.getId())) {
                entity.setId(ServiceClassTO.getId());
            }
            entity.setCode(ServiceClassTO.getCode());
            entity.setName(ServiceClassTO.getName());

            entity.setStatus(ServiceClassTO.getStatus());
            entity.setProcSubcatCode(ServiceClassTO.getProcSubCatCode());
            entity.setProcSubcatName(ServiceClassTO.getProcSubCatName());
            entity.setProcSubcatId(ServiceClassTO.getProCatgId());
            serviceMstrEntities.add(entity);
        }
        return serviceMstrEntities;
    }

}
