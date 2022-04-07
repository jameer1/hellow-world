package com.rjtech.register.service.handler.emp;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.model.ProvidentFundEntity;
//import com.rjtech.finance.model.ProvidentFundEntityCopy;
import com.rjtech.finance.repository.ProvidentFundRepository;
//import com.rjtech.finance.repository.ProvidentFundRepositoryCopy;
import com.rjtech.register.emp.dto.EmpProvidentFundContributionTO;
import com.rjtech.register.emp.dto.EmpProvidentFundDetailTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTaxTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpProvidentFundContributionEntity;
import com.rjtech.register.emp.model.EmpProvidentFundDetailEntity;
import com.rjtech.register.emp.model.EmpProvidentFundTaxEntity;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;

public class EmpProvidentFundDetailHandler {

    public static EmpProvidentFundDetailTO convertEntityToPOJO(EmpProvidentFundDetailEntity entity) {

        EmpProvidentFundDetailTO empProvidentFundDetailTO = new EmpProvidentFundDetailTO();
        empProvidentFundDetailTO.setId(entity.getId());
        empProvidentFundDetailTO.setProvidentFundId(entity.getEmpProvidentFundEntity().getId());
        empProvidentFundDetailTO.setDesc(entity.getDesc());
        empProvidentFundDetailTO.setDetails(entity.getDetails());
        empProvidentFundDetailTO.setComments(entity.getComments());
        empProvidentFundDetailTO.setStatus(entity.getStatus());

        return empProvidentFundDetailTO;
    }

    public static EmpProvidentFundContributionTO convertContributionEntityToPOJO(
            EmpProvidentFundContributionEntity entity) {

        EmpProvidentFundContributionTO empProvidentFundContributionTO = new EmpProvidentFundContributionTO();
        empProvidentFundContributionTO.setId(entity.getId());
        empProvidentFundContributionTO.setProvidentFundId(entity.getEmpProvidentFundEntity().getId());
        empProvidentFundContributionTO.setFinanceFundId(entity.getProvidentFundEntity().getId());
        empProvidentFundContributionTO.setPercentage(entity.getPercentage());
        empProvidentFundContributionTO.setFixedAmount(entity.getFixedAmount());
        empProvidentFundContributionTO.setStatus(entity.getStatus());

        return empProvidentFundContributionTO;
    }

    public static EmpProvidentFundTaxTO convertTaxEntityToPOJO(EmpProvidentFundTaxEntity entity) {

        EmpProvidentFundTaxTO empProvidentFundTaxTO = new EmpProvidentFundTaxTO();
        empProvidentFundTaxTO.setId(entity.getId());
        empProvidentFundTaxTO.setProvidentFundId(entity.getEmpProvidentFundEntity().getId());
        empProvidentFundTaxTO.setPercentage(entity.getPercentage());
        empProvidentFundTaxTO.setFixedAmount(entity.getFixedAmount());
        empProvidentFundTaxTO.setComments(entity.getComments());
        empProvidentFundTaxTO.setStatus(entity.getStatus());

        return empProvidentFundTaxTO;
    }

    public static EmpProvidentFundDetailEntity convertPOJOToEntity(EmpProvidentFundDetailTO empProvidentFundDetailTO) {

        EmpProvidentFundDetailEntity empProvidentFundDetailEntity = new EmpProvidentFundDetailEntity();
        /*	if (CommonUtil.isNonBlankLong(empProvidentFundDetailTO.getId())) {
        empProvidentFundDetailEntity.setId(empProvidentFundDetailTO.getId());
        	}*/
        empProvidentFundDetailEntity.setDesc(empProvidentFundDetailTO.getDesc());
        empProvidentFundDetailEntity.setDetails(empProvidentFundDetailTO.getDetails());
        empProvidentFundDetailEntity.setComments(empProvidentFundDetailTO.getComments());
        empProvidentFundDetailEntity.setStatus(empProvidentFundDetailTO.getStatus());

        return empProvidentFundDetailEntity;
    }

    public static EmpProvidentFundContributionEntity convertContributionPOJOToEntity(
            EmpProvidentFundContributionTO empProvidentFundContributionTO,
            ProvidentFundRepository providentFundRepository) {

        EmpProvidentFundContributionEntity entity = new EmpProvidentFundContributionEntity();
        if (CommonUtil.isNonBlankLong(empProvidentFundContributionTO.getFinanceFundId())) {
            ProvidentFundEntity providentFundEntity = providentFundRepository
                    .findOne(empProvidentFundContributionTO.getFinanceFundId());
            entity.setProvidentFundEntity(providentFundEntity);
        }
        entity.setPercentage(empProvidentFundContributionTO.getPercentage());
        entity.setFixedAmount(empProvidentFundContributionTO.getFixedAmount());
        entity.setComments(empProvidentFundContributionTO.getComments());
        entity.setStatus(empProvidentFundContributionTO.getStatus());

        return entity;
    }

    public static EmpProvidentFundTaxEntity convertTaxPOJOToEntity(EmpProvidentFundTaxTO empProvidentFundTaxTO) {

        EmpProvidentFundTaxEntity empProvidentFundTaxEntity = new EmpProvidentFundTaxEntity();
        /*	empProvidentFundTaxEntity.setId(empProvidentFundTaxTO.getId());
        */ empProvidentFundTaxEntity.setPercentage(empProvidentFundTaxTO.getPercentage());
        empProvidentFundTaxEntity.setFixedAmount(empProvidentFundTaxTO.getFixedAmount());
        empProvidentFundTaxEntity.setComments(empProvidentFundTaxTO.getComments());
        empProvidentFundTaxEntity.setStatus(empProvidentFundTaxTO.getStatus());

        return empProvidentFundTaxEntity;
    }

    public static void populateEmpProvidentFundTOFromEmpProjRegTO(EmpProvidentFundResp empProvidentFundResp,
            ProjEmpRegisterTO projEmpRegisterTO) {
        EmpProvidentFundTO empProvidentFundTO = new EmpProvidentFundTO();
        empProvidentFundTO.setEmpProjId(projEmpRegisterTO.getId());
        empProvidentFundTO.setEmpRegId(projEmpRegisterTO.getEmpId());
        empProvidentFundTO.setFromDate(projEmpRegisterTO.getMobilaizationDate());
        empProvidentFundTO.setProjEmpRegisterTO(projEmpRegisterTO);
        empProvidentFundResp.getEmpProvidentFundTOs().add(empProvidentFundTO);
    }

}
