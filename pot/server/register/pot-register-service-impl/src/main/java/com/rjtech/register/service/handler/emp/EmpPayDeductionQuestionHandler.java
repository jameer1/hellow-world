package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpPayDeductionQuestionTO;
import com.rjtech.register.emp.dto.EmpPayQuestionMstrTO;
import com.rjtech.register.emp.model.EmpPayDeductionEntity;
import com.rjtech.register.emp.model.EmpPayDeductionQuestEntity;
import com.rjtech.register.emp.model.EmpPayQuestionaryMstrEntity;

public class EmpPayDeductionQuestionHandler {

    public static EmpPayDeductionQuestionTO convertEntityToPOJO(EmpPayDeductionQuestEntity entity) {

        EmpPayDeductionQuestionTO empPayDeductionQuestionTO = new EmpPayDeductionQuestionTO();
        empPayDeductionQuestionTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getEmpPayQuestionaryMstrEntity())) {
            empPayDeductionQuestionTO
                    .setEmpPayQuestionMstrTO(convertMstrEntityToPOJO(entity.getEmpPayQuestionaryMstrEntity()));
        }
        if (CommonUtil.objectNotNull(entity.getEmpPayDeductionEntity())) {
            empPayDeductionQuestionTO.setPayDeductionId(entity.getEmpPayDeductionEntity().getId());
        }
        empPayDeductionQuestionTO.setComments(entity.getAnswer());
        empPayDeductionQuestionTO.setStatus(entity.getStatus());
        return empPayDeductionQuestionTO;
    }

    public static EmpPayQuestionMstrTO convertMstrEntityToPOJO(EmpPayQuestionaryMstrEntity entity) {
        EmpPayQuestionMstrTO empPayQuestionMstrTO = new EmpPayQuestionMstrTO();
        empPayQuestionMstrTO.setId(entity.getId());
        empPayQuestionMstrTO.setDesc(entity.getDesc());
        empPayQuestionMstrTO.setType(entity.isType());
        return empPayQuestionMstrTO;
    }

    public static EmpPayDeductionQuestEntity convertPOJOToEntity(EmpPayDeductionQuestionTO empPayDeductionQuestionTO) {
        EmpPayDeductionQuestEntity entity = new EmpPayDeductionQuestEntity();
        /* entity.setId(empPayDeductionQuestionTO.getId()); */
        if (CommonUtil.isNonBlankLong(empPayDeductionQuestionTO.getPayDeductionId())) {
            EmpPayDeductionEntity empPayDeductionEntity = new EmpPayDeductionEntity();
            empPayDeductionEntity.setId(empPayDeductionQuestionTO.getPayDeductionId());
            entity.setEmpPayDeductionEntity(empPayDeductionEntity);
        }
        if (CommonUtil.objectNotNull(empPayDeductionQuestionTO.getEmpPayQuestionMstrTO())) {
            EmpPayQuestionaryMstrEntity empPayQuestionaryMstrEntity = new EmpPayQuestionaryMstrEntity();
            empPayQuestionaryMstrEntity.setId(empPayDeductionQuestionTO.getEmpPayQuestionMstrTO().getId());
            entity.setEmpPayQuestionaryMstrEntity(empPayQuestionaryMstrEntity);
        }
        entity.setAnswer(empPayDeductionQuestionTO.getComments());
        entity.setStatus(empPayDeductionQuestionTO.getStatus());
        return entity;
    }
}
