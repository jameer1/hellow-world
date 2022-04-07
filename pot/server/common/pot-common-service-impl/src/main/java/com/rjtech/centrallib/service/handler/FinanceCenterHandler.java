package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.EmployeePayRoleEntity;
import com.rjtech.centrallib.model.FinanceCenterEntity;
import com.rjtech.centrallib.model.NonRegularPayAllowanceEntity;
import com.rjtech.centrallib.model.PayDeductionCodeEntity;
import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.centrallib.model.SuperProvidentFundEntity;
import com.rjtech.centrallib.model.TaxPaymentReceiverCodeEntity;
import com.rjtech.centrallib.model.TaxRatesRulesCodeEntity;
import com.rjtech.centrallib.model.UnitOfPayRateEntity;
import com.rjtech.centrallib.req.FinanceCenterSaveReq;
import com.rjtech.common.dto.EmpPayRoleCycle;
import com.rjtech.common.dto.FinanceCenterRecordTo;
import com.rjtech.common.dto.NonRegularPayAllowance;
import com.rjtech.common.dto.PayDeductionCodes;
import com.rjtech.common.dto.RegularPayAllowance;
import com.rjtech.common.dto.SuperProvidentFund;
import com.rjtech.common.dto.TaxPaymentReceiverCodes;
import com.rjtech.common.dto.TaxRateRulesCodes;
import com.rjtech.common.dto.UnitOfPayRate;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.req.SystemDateReq;
import com.rjtech.common.utils.CommonUtil;

public class FinanceCenterHandler {

    public static FinanceCenterRecordTo convertEntityToPOJO(FinanceCenterEntity financeCenterEntity) {
    	System.out.println("FinanceCenterHandler=====convertEntityToPOJO");
        FinanceCenterRecordTo financeCenterRecordTo = new FinanceCenterRecordTo();
        financeCenterRecordTo.setCountryName(financeCenterEntity.getCountryName());
        financeCenterRecordTo.setCountryCode(financeCenterEntity.getCountryCode());
        financeCenterRecordTo.setEffectiveFrom(CommonUtil.convertDateToString(financeCenterEntity.getEffectiveFrom()));
        financeCenterRecordTo.setProvisionName(financeCenterEntity.getProvisionName());
        financeCenterRecordTo.setProvisionCode(financeCenterEntity.getProvisionCode());
        financeCenterRecordTo.setStatus(financeCenterEntity.getStatus());
        System.out.println("financeCenterRecordTo.getStatus=====convertEntityToPOJO"+financeCenterRecordTo.getStatus());
        System.out.println("financeCenterEntity.getStatus()=====convertEntityToPOJO"+financeCenterEntity.getStatus());
        financeCenterRecordTo.setId(financeCenterEntity.getId());
        financeCenterRecordTo.setDisplayFinanceCenterId(financeCenterEntity.getCountryCode() + "-"
                + financeCenterEntity.getProvisionCode() + "- FC" + "00" + financeCenterEntity.getId());
       // System.out.println("financeCenterRecordTo.setDisplayFinanceCenterId=====convertEntityToPOJO"+financeCenterRecordTo.getDisplayFinanceCenterId());
        System.out.println();
        List<UnitOfPayRateEntity> unitOfPayRateEntityList = financeCenterEntity.getUnitOfPayRateEntity();
        List<EmployeePayRoleEntity> employeePayRoleEntityList = financeCenterEntity.getEmployeePayRoleEntity();
        List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntityList = financeCenterEntity.getNonRegularPayAllowanceEntity();
        List<RegularPayAllowanceEntity> regularPayAllowanceEntityList = financeCenterEntity.getRegularPayAllowanceEntity();
        List<SuperProvidentFundEntity> superProvidentFundEntityList= financeCenterEntity.getSuperProvidentFundEntity();
        List<TaxRatesRulesCodeEntity> taxRatesRulesCodeEntityList= financeCenterEntity.getTaxRatesRulesCodeEntity();
        List<PayDeductionCodeEntity> payDeductionCodeEntityList= financeCenterEntity.getPayDeductionCodeEntity();
        List<TaxPaymentReceiverCodeEntity> taxPaymentReceiverCodeEntityList= financeCenterEntity.getTaxPaymentReceiverCodeEntity();
        
        if (!unitOfPayRateEntityList.isEmpty()) {
        	//System.out.println("unitOfPayRateEntityList=====unitOfPayRateEntityList");
            List<UnitOfPayRate> unitOfPayRatesList = new ArrayList<>();
            for (UnitOfPayRateEntity unitOfPayRateEntity : unitOfPayRateEntityList) {
            	//System.out.println("unitOfPayRateEntityList=====for");
                UnitOfPayRate unitOfPayRate = new UnitOfPayRate();
                unitOfPayRate.setCode(unitOfPayRateEntity.getCode());
                unitOfPayRate.setName(unitOfPayRateEntity.getName());
                unitOfPayRate.setNotes(unitOfPayRateEntity.getNotes());
                unitOfPayRate.setUnitOfPayDisplayId(financeCenterEntity.getCountryCode() + "-"+ financeCenterEntity.getProvisionCode() + "- EUP -" + "00" + unitOfPayRateEntity.getId());
            	//System.out.println("unitOfPayRate.setUnitOfPayDisplayId=====unitOfPayRateEntityList"+unitOfPayRate.getUnitOfPayDisplayId());
                unitOfPayRatesList.add(unitOfPayRate);
            }
           // System.out.println("unitOfPayRateEntityList=====size"+unitOfPayRateEntityList.size());
            financeCenterRecordTo.setUnitOfPayRates(unitOfPayRatesList);
        }
        if (!employeePayRoleEntityList.isEmpty()) {
        	//System.out.println("employeePayRoleEntityList=====employeePayRoleEntityList");
            List<EmpPayRoleCycle> empPayRoleCycleList = new ArrayList<>();
            for (EmployeePayRoleEntity employeePayRoleEntity : employeePayRoleEntityList) {
            	//System.out.println("employeePayRoleEntityList=====for");
                EmpPayRoleCycle empPayRoleCycle = new EmpPayRoleCycle();
                empPayRoleCycle.setId(employeePayRoleEntity.getId());
                empPayRoleCycle.setPayRoleCycle(employeePayRoleEntity.getPayRoleCycle());
                empPayRoleCycle.setCycleDueDate(employeePayRoleEntity.getCycleDueDate());
                empPayRoleCycle.setCyclePeriodStartFrom(employeePayRoleEntity.getCyclePeriodStartFrom());
                empPayRoleCycle.setEmployeeType(employeePayRoleEntity.getEmployeeType());
                empPayRoleCycle.setEmployeeCategory(employeePayRoleEntity.getEmployeeCategory());
                empPayRoleCycle.setEmpPayRoleCycleDisplayId(financeCenterEntity.getCountryCode() + "-"+ financeCenterEntity.getProvisionCode() + "- PRC -" + "00" + employeePayRoleEntity.getId());
               // System.out.println(" empPayRoleCycle.setEmpPayRoleCycleDisplayId=====employeePayRoleEntityList"+ empPayRoleCycle.getEmpPayRoleCycleDisplayId());
                empPayRoleCycleList.add(empPayRoleCycle);
            }
           // System.out.println("employeePayRoleEntityList=====size"+employeePayRoleEntityList.size());
            financeCenterRecordTo.setEmpPayRollCycles(empPayRoleCycleList);
        }
        //System.out.println("=====before regularPayAllowanceEntityList==========");
        if (!regularPayAllowanceEntityList.isEmpty()) {
        	//System.out.println("regularPayAllowanceEntityList=====regularPayAllowanceEntityList");
            List<RegularPayAllowance> regularPayAllowanceList = new ArrayList<>();
            for (RegularPayAllowanceEntity regularPayAllowanceEntity : regularPayAllowanceEntityList) {
            	//System.out.println("regularPayAllowanceEntityList=====for");
                RegularPayAllowance regularPayAllowance = new RegularPayAllowance();
                regularPayAllowance.setId(regularPayAllowanceEntity.getId());
                regularPayAllowance.setCode(regularPayAllowanceEntity.getCode());
                regularPayAllowance.setDescription(regularPayAllowanceEntity.getDescription());
                regularPayAllowance.setNotes(regularPayAllowanceEntity.getName());
                regularPayAllowance.setIsTaxable(regularPayAllowanceEntity.getIsTaxable());
                regularPayAllowance.setRegularPayCycleDisplayId(
                        financeCenterEntity.getCountryCode() + "-" + financeCenterEntity.getProvisionCode() + "- RPA -"
                                + "00" + regularPayAllowanceEntity.getId());
            	//System.out.println("regularPayAllowance.setRegularPayCycleDisplayId=====regularPayAllowanceEntityList"+regularPayAllowance.getRegularPayCycleDisplayId());
                regularPayAllowanceList.add(regularPayAllowance);
            }
            //System.out.println("regularPayAllowanceEntityList=====size"+regularPayAllowanceEntityList.size());
            financeCenterRecordTo.setRegularPayAllowances(regularPayAllowanceList);
        }
      /* System.out.println("=====before superProvidentFundEntityList==========");
       System.out.println("regularPayAllowanceEntityList=====size2222"+regularPayAllowanceEntityList.size());*/

      if(!superProvidentFundEntityList.isEmpty() || superProvidentFundEntityList.isEmpty()) {
    	  //System.out.println("superProvidentFundEntityList=====superProvidentFundEntityList");
            List<SuperProvidentFund> superProvidentFundList = new ArrayList<>();
           // System.out.println("superProvidentFundEntityList=====list");
            for (SuperProvidentFundEntity superProvidentFundEntity : superProvidentFundEntityList) {
            	//System.out.println("superProvidentFundEntityList=====for");
                SuperProvidentFund superProvidentFund = new SuperProvidentFund();
                superProvidentFund.setId(superProvidentFundEntity.getId());
                superProvidentFund.setCode(superProvidentFundEntity.getCode());
                superProvidentFund.setDescription(superProvidentFundEntity.getDescription());
                superProvidentFund.setIsTaxable(superProvidentFundEntity.getIsTaxable());
                superProvidentFund.setCreditRunCycle(superProvidentFundEntity.getCreditRunCycle());
                superProvidentFund.setCreditRunDueDate(superProvidentFundEntity.getCreditRunDueDate());
                superProvidentFund.setSuperProvidentFundDisplayId(
                        financeCenterEntity.getCountryCode() + "-" + financeCenterEntity.getProvisionCode() + "- SP -"
                               + "00" + superProvidentFundEntity.getId());
               // System.out.println(" superProvidentFund.setSuperProvidentFundDisplayId=====superProvidentFundEntityList"+ superProvidentFund.getSuperProvidentFundDisplayId());
                superProvidentFundList.add(superProvidentFund);
            }
           // System.out.println("superProvidentFundEntityList=====size"+superProvidentFundEntityList.size());
            financeCenterRecordTo.setSuperFundCodes(superProvidentFundList);
        }
     // System.out.println("=====before taxRatesRulesCodeEntityList==========");
	        if(!taxRatesRulesCodeEntityList.isEmpty() || taxRatesRulesCodeEntityList.isEmpty()) {
	        	 //System.out.println("taxRatesRulesCodeEntityList=====taxRatesRulesCodeEntityList");
	            List<TaxRateRulesCodes> taxRateRulesCodesList = new ArrayList<>();
	            for (TaxRatesRulesCodeEntity taxRatesRulesCodeEntity : taxRatesRulesCodeEntityList) {
	            	//System.out.println("taxRatesRulesCodeEntityList=====for");
	                TaxRateRulesCodes taxRateRulesCodes = new TaxRateRulesCodes();
	                taxRateRulesCodes.setCode(taxRatesRulesCodeEntity.getCode());
	                taxRateRulesCodes.setDescription(taxRatesRulesCodeEntity.getDescription());
	               // taxRateRulesCodes.setApplicableFor(taxRatesRulesCodeEntity.getApplicableFor());
	                taxRateRulesCodes.setCreditRunCycle(taxRatesRulesCodeEntity.getCreditRunCycle());
	                taxRateRulesCodes.setCreditRunDueDate(taxRatesRulesCodeEntity.getCreditRunDueDate());
	                taxRateRulesCodes.setTaxRateRules(taxRatesRulesCodeEntity.getTaxRateRules());
	                taxRateRulesCodes.setTaxRateRulesDisplayId(
	                        financeCenterEntity.getCountryCode() + "-" + financeCenterEntity.getProvisionCode() + "- TCAR -"
	                                + taxRatesRulesCodeEntity.getId());
	              //  System.out.println("taxRateRulesCodes.setTaxRateRulesDisplayId=====taxRatesRulesCodeEntityList"+taxRateRulesCodes.getTaxRateRulesDisplayId());
	                taxRateRulesCodesList.add(taxRateRulesCodes);
	            }
	            //System.out.println("taxRatesRulesCodeEntityList=====size"+taxRatesRulesCodeEntityList.size());
	           // financeCenterRecordTo.setTaxCodesTaxRules(taxRateRulesCodesList);
	            financeCenterRecordTo.setTaxRateRulesCodes(taxRateRulesCodesList);
	        }
	        
	       // System.out.println("=====before payDeductionCodeEntityList==========");
	        if(!payDeductionCodeEntityList.isEmpty() || payDeductionCodeEntityList.isEmpty()) {
	        	// System.out.println("payDeductionCodeEntityList=====payDeductionCodeEntityList");
	            List<PayDeductionCodes> payDeductionCodesList = new ArrayList<>();
	            for (PayDeductionCodeEntity payDeductionCodeEntity : payDeductionCodeEntityList) {
	            	//System.out.println("payDeductionCodeEntityList=====for");
	                PayDeductionCodes payDeductionCodes = new PayDeductionCodes();
	                payDeductionCodes.setCode(payDeductionCodeEntity.getCode());
	                payDeductionCodes.setDescription(payDeductionCodeEntity.getDescription());
	                payDeductionCodes.setPayDeductionsDisplayId(
	                        financeCenterEntity.getCountryCode() + "-" + financeCenterEntity.getProvisionCode() + "- PD -"
	                                + payDeductionCodeEntity.getId());
	               // System.out.println(" payDeductionCodes.setPayDeductionsDisplayId=====payDeductionCodeEntityList"+ payDeductionCodes.getPayDeductionsDisplayId());
	                payDeductionCodesList.add(payDeductionCodes);
	            }
	           // System.out.println("payDeductionCodeEntityList=====size"+payDeductionCodeEntityList.size());
	            financeCenterRecordTo.setPayDeductionCodes(payDeductionCodesList);
	        }
	       // System.out.println("=====before taxPaymentReceiverCodeEntityList==========");

        if(!taxPaymentReceiverCodeEntityList.isEmpty() || taxPaymentReceiverCodeEntityList.isEmpty()) {
        	 //System.out.println("taxPaymentReceiverCodeEntityList=====taxPaymentReceiverCodeEntityList");
            List<TaxPaymentReceiverCodes> taxPaymentReceiverCodesList = new ArrayList<>();
            for (TaxPaymentReceiverCodeEntity taxPaymentReceiverCodeEntity : taxPaymentReceiverCodeEntityList) {
            	//System.out.println("taxPaymentReceiverCodeEntityList=====for");
                TaxPaymentReceiverCodes taxPaymentReceiverCodes = new TaxPaymentReceiverCodes();

                taxPaymentReceiverCodes.setCode(taxPaymentReceiverCodeEntity.getCode());
                taxPaymentReceiverCodes.setDescription(taxPaymentReceiverCodeEntity.getDescription());
                taxPaymentReceiverCodes.setNameofDepartment(taxPaymentReceiverCodeEntity.getNameofDepartment());
                //System.out.println("taxPaymentReceiverCodeEntityList=====taxPaymentReceiverCodes.setNameofDepartment"+taxPaymentReceiverCodes.getNameofDepartment());
                taxPaymentReceiverCodes.setRegisterdAddress(taxPaymentReceiverCodeEntity.getRegisterdAddress());
                //System.out.println("taxPaymentReceiverCodeEntityList=====taxPaymentReceiverCodes.setRegisterdAddress"+taxPaymentReceiverCodes.getRegisterdAddress());
                taxPaymentReceiverCodes.setAccountNumber(taxPaymentReceiverCodeEntity.getAccountNumber());
                
                //System.out.println("taxPaymentReceiverCodeEntityList=====taxPaymentReceiverCodes.setAccountNumber"+taxPaymentReceiverCodes.getAccountNumber());
                taxPaymentReceiverCodes.setBankInstituteName(taxPaymentReceiverCodeEntity.getBankInstituteName());
                //System.out.println("taxPaymentReceiverCodeEntityList=====taxPaymentReceiverCodes.getBankInstituteName"+taxPaymentReceiverCodes.getBankInstituteName());
                taxPaymentReceiverCodes.setBankCode(taxPaymentReceiverCodeEntity.getBankCode());

                taxPaymentReceiverCodes.setTaxPaymentReceiverDisplayId(
                        financeCenterEntity.getCountryCode() + "-" + financeCenterEntity.getProvisionCode() + "- TPR -"
                                + taxPaymentReceiverCodeEntity.getId());
               // System.out.println("taxPaymentReceiverCodes.setTaxPaymentReceiverDisplayId=====taxPaymentReceiverCodeEntityList"+taxPaymentReceiverCodes.getTaxPaymentReceiverDisplayId());
                taxPaymentReceiverCodesList.add(taxPaymentReceiverCodes);
            }
            //System.out.println("taxPaymentReceiverCodeEntityList=====size"+taxPaymentReceiverCodeEntityList.size());
            financeCenterRecordTo.setTaxPaymentsReceivers(taxPaymentReceiverCodesList);
            //financeCenterRecordTo.setTaxPaymentReceiverCodes(taxPaymentReceiverCodesList);
        }
        
        //System.out.println("=====before nonRegularPayAllowanceEntityList==========");
        
        if (!nonRegularPayAllowanceEntityList.isEmpty()) {
        	//System.out.println("nonRegularPayAllowanceEntityList=====nonRegularPayAllowanceEntityList");
            List<NonRegularPayAllowance> nonRegularPayAllowanceList = new ArrayList<>();
            for (NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity : nonRegularPayAllowanceEntityList) {
            	//System.out.println("nonRegularPayAllowanceEntityList=====for");
                NonRegularPayAllowance nonRegularPayAllowance = new NonRegularPayAllowance();
                nonRegularPayAllowance.setId(nonRegularPayAllowanceEntity.getId());
                nonRegularPayAllowance.setCode(nonRegularPayAllowanceEntity.getCode());
                nonRegularPayAllowance.setDescription(nonRegularPayAllowanceEntity.getDescription());
                nonRegularPayAllowance.setNotes(nonRegularPayAllowanceEntity.getName());
                nonRegularPayAllowance.setIsTaxable(nonRegularPayAllowanceEntity.getIsTaxable());
                nonRegularPayAllowance.setNonRegularPayCycleDisplayId(
                        financeCenterEntity.getCountryCode() + "-" + financeCenterEntity.getProvisionCode() + "- NRPA -"
                                + "00" + nonRegularPayAllowanceEntity.getId());
               // System.out.println("nonRegularPayAllowance.setNonRegularPayCycleDisplayId=====nonRegularPayAllowanceEntityList"+nonRegularPayAllowance.getNonRegularPayCycleDisplayId());
                nonRegularPayAllowanceList.add(nonRegularPayAllowance);
            }
            //System.out.println("nonRegularPayAllowanceEntityList=====size"+nonRegularPayAllowanceEntityList.size());
            financeCenterRecordTo.setNonRegularPayAllowances(nonRegularPayAllowanceList);
        }


       
        ClientRegEntity clientRegEntity = financeCenterEntity.getClientId();
        if (null != clientRegEntity) {
            financeCenterRecordTo.setClientId(clientRegEntity.getClientId());
        }

        return financeCenterRecordTo;
    }

    public static FinanceCenterEntity convertPOJOToEntity(FinanceCenterSaveReq financeCenterSaveReq,
            FinanceCenterEntity entity, List<UnitOfPayRateEntity> unitOfPayRateEntityList,
            List<EmployeePayRoleEntity> employeePayRoleEntityList,
			List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntityList,  
			List<RegularPayAllowanceEntity>  regularPayAllowanceEntity,
			          List<SuperProvidentFundEntity>  superProvidentFundEntity,
             List<TaxRatesRulesCodeEntity>  taxRatesRulesCodeEntity,
            List<PayDeductionCodeEntity>  payDeductionCodeEntity,
			          List<TaxPaymentReceiverCodeEntity> taxPaymentReceiverCodeEntity, 
			          Boolean isUpdate) {

    	System.out.println("====convertPOJOToEntity==========");
    	System.out.println("====isUpdate=========="+isUpdate);
    	if(isUpdate==false) {
    		System.out.println("====isUpdate====false======"+isUpdate);	
    		entity.setStatus(financeCenterSaveReq.getStatus());
    	} else {
    		System.out.println("====isUpdate=====true====="+isUpdate);	
    		entity.setStatus(2);
    	}
    	System.out.println("====financeCenterSaveReq.getId()=====before if loop====="+financeCenterSaveReq.getId());
        if (CommonUtil.objectNotNull(financeCenterSaveReq) && CommonUtil.isNonBlankLong(financeCenterSaveReq.getId())) {
            entity.setId(financeCenterSaveReq.getId());
            System.out.println("====financeCenterSaveReq.getId()=========="+financeCenterSaveReq.getId());
            System.out.println("====financeCenterSaveReq.getStatus()=========="+financeCenterSaveReq.getStatus());
        }
        entity.setCountryName(financeCenterSaveReq.getCountryName());
        entity.setCountryCode(financeCenterSaveReq.getCountryCode());
        entity.setProvisionName(financeCenterSaveReq.getProvisionName());
        entity.setProvisionCode(financeCenterSaveReq.getProvisionCode());
       // entity.setStatus(financeCenterSaveReq.getStatus());
        System.out.println("====entity.getStatus()=========="+entity.getStatus());
        entity.setEffectiveFrom(CommonUtil.convertStringToDate(financeCenterSaveReq.getEffectiveFrom()));

        List<UnitOfPayRate> unitOfPayRateList = financeCenterSaveReq.getUnitOfPayRates();
        List<EmpPayRoleCycle> empPayRoleCycleList = financeCenterSaveReq.getEmpPayRollCycles();
        List<NonRegularPayAllowance> nonRegularPayAllowanceList = financeCenterSaveReq.getNonRegularPayAllowances();
       
        List<RegularPayAllowance> regularPayAllowanceList = financeCenterSaveReq.getRegularPayAllowances();
        List<SuperProvidentFund> superProvidentFundList = financeCenterSaveReq.getSuperFundCodes();
         // List<TaxRateRulesCodes> taxRateRulesCodesList = financeCenterSaveReq.getTaxCodesTaxRules();
          List<TaxRateRulesCodes> taxRateRulesCodesList = financeCenterSaveReq.getTaxRateRulesCodes();
        List<PayDeductionCodes>  payDeductionCodesList= financeCenterSaveReq.getPayDeductionCodes();
        List<TaxPaymentReceiverCodes> taxPaymentReceiverCodesList = financeCenterSaveReq.getTaxPaymentsReceivers();
        //List<TaxPaymentReceiverCodes> taxPaymentReceiverCodesList = financeCenterSaveReq.getTaxPaymentReceiverCodes();

       
        if (CommonUtil.isListHasData(unitOfPayRateList) && !unitOfPayRateList.isEmpty()) {
        	//System.out.println("====convertPOJOToEntity====unitOfPayRateList======");
            int index = 0;
            for (UnitOfPayRate unitOfPayRate : unitOfPayRateList) {
            //	System.out.println("====unitOfPayRateList====unitOfPayRateList======"+unitOfPayRateList.size());
                if (!isUpdate) {
                    UnitOfPayRateEntity unitOfPayRateEntity = new UnitOfPayRateEntity();
                    unitOfPayRateEntity.setCode(unitOfPayRate.getCode());
                    unitOfPayRateEntity.setName(unitOfPayRate.getName());
                    unitOfPayRateEntity.setNotes(unitOfPayRate.getNotes());
                    unitOfPayRateEntity.setFinanceCenterEntity(entity);
                    unitOfPayRateEntityList.add(unitOfPayRateEntity);
                } else {
                    unitOfPayRateEntityList.get(index).setCode(unitOfPayRate.getCode());
                    unitOfPayRateEntityList.get(index).setName(unitOfPayRate.getName());
                    unitOfPayRateEntityList.get(index).setNotes(unitOfPayRate.getNotes());
                    unitOfPayRateEntityList.get(index).setFinanceCenterEntity(entity);
                }
                index++;
            }
            entity.setUnitOfPayRateEntity(unitOfPayRateEntityList);
        }
        if (CommonUtil.isListHasData(nonRegularPayAllowanceList) && !nonRegularPayAllowanceList.isEmpty()) {
            int index = 0;
           // System.out.println("====convertPOJOToEntity====nonRegularPayAllowanceList======");
            for (NonRegularPayAllowance nonRegularPayAllowance : nonRegularPayAllowanceList) {
                if (!isUpdate) {
                    NonRegularPayAllowanceEntity nonRegularPayAllowanceEnt = new NonRegularPayAllowanceEntity();
                    nonRegularPayAllowanceEnt.setCode(nonRegularPayAllowance.getCode());
                    nonRegularPayAllowanceEnt.setDescription(nonRegularPayAllowance.getDescription());
                    nonRegularPayAllowanceEnt.setName(nonRegularPayAllowance.getNotes());
                    nonRegularPayAllowanceEnt.setIsTaxable(nonRegularPayAllowance.getIsTaxable());
                    nonRegularPayAllowanceEnt.setFinanceCenterEntity(entity);
                    nonRegularPayAllowanceEntityList.add(nonRegularPayAllowanceEnt);
                } else {
                    nonRegularPayAllowanceEntityList.get(index).setCode(nonRegularPayAllowance.getCode());
                    nonRegularPayAllowanceEntityList.get(index).setDescription(nonRegularPayAllowance.getDescription());
                    nonRegularPayAllowanceEntityList.get(index).setName(nonRegularPayAllowance.getNotes());
                    nonRegularPayAllowanceEntityList.get(index).setIsTaxable(nonRegularPayAllowance.getIsTaxable());
                    nonRegularPayAllowanceEntityList.get(index).setFinanceCenterEntity(entity);
                }
                index++;
            }
            entity.setNonRegularPayAllowanceEntity(nonRegularPayAllowanceEntityList);
        }

        if (CommonUtil.isListHasData(empPayRoleCycleList) && !empPayRoleCycleList.isEmpty()) {
        	//System.out.println("====convertPOJOToEntity====empPayRoleCycleList======");
            int index = 0;
            for (EmpPayRoleCycle empPayRoleCycle : empPayRoleCycleList) {
            	//System.out.println("====convertPOJOToEntity====empPayRoleCycleList======0000");
                if (!isUpdate) {
                    EmployeePayRoleEntity employeePayRoleEntity = new EmployeePayRoleEntity();
                    employeePayRoleEntity.setCycleDueDate(empPayRoleCycle.getCycleDueDate());
                    employeePayRoleEntity.setCyclePeriodStartFrom(empPayRoleCycle.getCyclePeriodStartFrom());
                    employeePayRoleEntity.setEmployeeCategory(empPayRoleCycle.getEmployeeCategory());
                    employeePayRoleEntity.setEmployeeType(empPayRoleCycle.getEmployeeType());
                    employeePayRoleEntity.setPayRoleCycle(empPayRoleCycle.getPayRoleCycle());
                    //System.out.println("====convertPOJOToEntity====empPayRoleCycle.getPayRollCycle()======"+empPayRoleCycle.getPayRoleCycle());
                    //System.out.println("====convertPOJOToEntity====employeePayRoleEntity.getPayRollCycle()======"+employeePayRoleEntity.getPayRoleCycle());
                    employeePayRoleEntity.setFinanceCenterEntity(entity);
                    employeePayRoleEntityList.add(employeePayRoleEntity);

                } else {
                    employeePayRoleEntityList.get(index)
                            .setCyclePeriodStartFrom(empPayRoleCycle.getCyclePeriodStartFrom());
                    employeePayRoleEntityList.get(index).setCycleDueDate(empPayRoleCycle.getCycleDueDate());
                    employeePayRoleEntityList.get(0).setEmployeeCategory(empPayRoleCycle.getEmployeeCategory());
                    employeePayRoleEntityList.get(index)
                            .setCyclePeriodStartFrom(empPayRoleCycle.getCyclePeriodStartFrom());
                    employeePayRoleEntityList.get(index).setEmployeeType(empPayRoleCycle.getEmployeeType());
                    employeePayRoleEntityList.get(index).setPayRoleCycle(empPayRoleCycle.getPayRoleCycle());
                    employeePayRoleEntityList.get(index).setFinanceCenterEntity(entity);

                }
                index++;
            }
            entity.setEmployeePayRoleEntity(employeePayRoleEntityList);
        }


       

        if(CommonUtil.isListHasData(regularPayAllowanceList) && !regularPayAllowanceList.isEmpty()) {
        	//System.out.println("====convertPOJOToEntity====regularPayAllowanceList======");
            int index = 0;
            List <RegularPayAllowanceEntity> regularPayAllowanceEntityList = entity.getRegularPayAllowanceEntity();
            for (RegularPayAllowance regularPayAllowance : regularPayAllowanceList) {
            	//System.out.println("====convertPOJOToEntity====regularPayAllowanceList=====0000=");
                if (!isUpdate) {
                    RegularPayAllowanceEntity regularPayAllowanceEnt = new RegularPayAllowanceEntity();
                    regularPayAllowanceEnt.setCode(regularPayAllowance.getCode());
                    regularPayAllowanceEnt.setDescription(regularPayAllowance.getDescription());
                    regularPayAllowanceEnt.setName(regularPayAllowance.getNotes());
                    regularPayAllowanceEnt.setIsTaxable(regularPayAllowance.getIsTaxable());
                    regularPayAllowanceEnt.setFinanceCenterEntity(entity);
                    regularPayAllowanceEntityList.add(regularPayAllowanceEnt);

                } else {
                    regularPayAllowanceEntityList.get(index).setCode(regularPayAllowance.getCode());
                    regularPayAllowanceEntityList.get(index).setDescription(regularPayAllowance.getDescription());
                    regularPayAllowanceEntityList.get(index).setName(regularPayAllowance.getNotes());
                    regularPayAllowanceEntityList.get(index).setIsTaxable(regularPayAllowance.getIsTaxable());
                    regularPayAllowanceEntityList.get(index).setFinanceCenterEntity(entity);
                }
                index++;
            }
            entity.setRegularPayAllowanceEntity(regularPayAllowanceEntityList);
        }


        if(CommonUtil.isListHasData(superProvidentFundList) && !superProvidentFundList.isEmpty()) {
       //System.out.println("====convertPOJOToEntity====superProvidentFundList======");
            int index = 0;
            List <SuperProvidentFundEntity> superProvidentFundEntityList = entity.getSuperProvidentFundEntity();
            for (SuperProvidentFund superProvidentFund : superProvidentFundList) {
            	//System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=");
                if (!isUpdate) {
                	//System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=if block");
                    SuperProvidentFundEntity superProvidentFundEnt = new SuperProvidentFundEntity();
                    superProvidentFundEnt.setCode(superProvidentFund.getCode());
                    superProvidentFundEnt.setDescription(superProvidentFund.getDescription());
                    superProvidentFundEnt.setIsTaxable(superProvidentFund.getIsTaxable());
                    superProvidentFundEnt.setCreditRunCycle(superProvidentFund.getCreditRunCycle());
                    superProvidentFundEnt.setCreditRunDueDate(superProvidentFund.getCreditRunDueDate());
                    superProvidentFundEnt.setFinanceCenterEntity(entity);
                    superProvidentFundEntityList.add(superProvidentFundEnt);

                } else {
                	//System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=else block");
                    superProvidentFundEntityList.get(index).setCode(superProvidentFund.getCode());
                    superProvidentFundEntityList.get(index).setDescription(superProvidentFund.getDescription());
                    superProvidentFundEntityList.get(index).setIsTaxable(superProvidentFund.getIsTaxable());
                    superProvidentFundEntityList.get(index).setCreditRunCycle(superProvidentFund.getCreditRunCycle());
                    superProvidentFundEntityList.get(index).setCreditRunDueDate(superProvidentFund.getCreditRunDueDate());
                    superProvidentFundEntityList.get(index).setFinanceCenterEntity(entity);
                }
                index++;
                //System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=index"+index);
            }
            entity.setSuperProvidentFundEntity(superProvidentFundEntityList);

        }
	        if(CommonUtil.isListHasData(taxRateRulesCodesList) && !taxRateRulesCodesList.isEmpty()) {
	        	//System.out.println("====convertPOJOToEntity====taxRateRulesCodesList======");
	            int index = 0;
	            List <TaxRatesRulesCodeEntity> taxRatesRulesCodeEntityList = entity.getTaxRatesRulesCodeEntity();
	            for (TaxRateRulesCodes taxRateRulesCodes : taxRateRulesCodesList) {
	            	//System.out.println("====convertPOJOToEntity====taxRateRulesCodesList===0000===");
	                if (!isUpdate) {
	                    TaxRatesRulesCodeEntity taxRatesRulesCodeEnt = new TaxRatesRulesCodeEntity();
	                    taxRatesRulesCodeEnt.setCode(taxRateRulesCodes.getCode());
	                    taxRatesRulesCodeEnt.setDescription(taxRateRulesCodes.getDescription());
	                 //   taxRatesRulesCodeEnt.setApplicableFor(taxRateRulesCodes.getApplicableFor());
	                    taxRatesRulesCodeEnt.setCreditRunCycle(taxRateRulesCodes.getCreditRunCycle());
	                    taxRatesRulesCodeEnt.setCreditRunDueDate(taxRateRulesCodes.getCreditRunDueDate());
	                    //taxRatesRulesCodeEnt.setTaxRateRules(taxRateRulesCodes.getTaxRateRules());
	                    taxRatesRulesCodeEnt.setTaxRateRules(taxRateRulesCodes.getTaxRateRules());
	                    taxRatesRulesCodeEnt.setFinanceCenterEntity(entity);
	                    taxRatesRulesCodeEntityList.add(taxRatesRulesCodeEnt);
	
	                } else {
	                    taxRatesRulesCodeEntityList.get(index).setCode(taxRateRulesCodes.getCode());
	                    taxRatesRulesCodeEntityList.get(index).setDescription(taxRateRulesCodes.getDescription());
	                  //  taxRatesRulesCodeEntityList.get(index).setApplicableFor(taxRateRulesCodes.getApplicableFor());
	                    taxRatesRulesCodeEntityList.get(index).setCreditRunCycle(taxRateRulesCodes.getCreditRunCycle());
	                    taxRatesRulesCodeEntityList.get(index).setCreditRunDueDate(taxRateRulesCodes.getCreditRunDueDate());
	                    taxRatesRulesCodeEntityList.get(index).setTaxRateRules(taxRateRulesCodes.getTaxRateRules());
	                    taxRatesRulesCodeEntityList.get(index).setFinanceCenterEntity(entity);
	                }
	                index++;
	            }
	            entity.setTaxRatesRulesCodeEntity(taxRatesRulesCodeEntityList);
	
	        }
	        if(CommonUtil.isListHasData(payDeductionCodesList) &&!payDeductionCodesList.isEmpty()) {
	        	//System.out.println("====convertPOJOToEntity====payDeductionCodesList======");
	            int index = 0;
	            List <PayDeductionCodeEntity> payDeductionCodeEntityList = entity.getPayDeductionCodeEntity();
	            //System.out.println("====convertPOJOToEntity====payDeductionCodesList====0000==");
	            for (PayDeductionCodes  payDeductionCodes : payDeductionCodesList) {
	                if (!isUpdate) {
	                    PayDeductionCodeEntity payDeductionCodeEnt= new PayDeductionCodeEntity();
	                    payDeductionCodeEnt.setCode(payDeductionCodes.getCode());
	                    payDeductionCodeEnt.setDescription(payDeductionCodes.getDescription());
	                    payDeductionCodeEnt.setFinanceCenterEntity(entity);
	                    payDeductionCodeEntityList.add(payDeductionCodeEnt);
	
	                } else {
	                    payDeductionCodeEntityList.get(index).setCode(payDeductionCodes.getCode());
	                    payDeductionCodeEntityList.get(index).setDescription(payDeductionCodes.getDescription());
	                    payDeductionCodeEntityList.get(index).setFinanceCenterEntity(entity);
	                }
	                index++;
	            }
	            entity.setPayDeductionCodeEntity(payDeductionCodeEntityList);
	        }
        if(CommonUtil.isListHasData(taxPaymentReceiverCodesList) && !taxPaymentReceiverCodesList.isEmpty()) {
        	//System.out.println("====convertPOJOToEntity====taxPaymentReceiverCodesList======");
            int index = 0;
            List <TaxPaymentReceiverCodeEntity> taxPaymentReceiverCodeEntityList = entity.getTaxPaymentReceiverCodeEntity();
        	//System.out.println("====convertPOJOToEntity====taxPaymentReceiverCodesList====00000==");
            for (TaxPaymentReceiverCodes  taxPaymentReceiverCodes : taxPaymentReceiverCodesList) {
                if (!isUpdate) {
                    TaxPaymentReceiverCodeEntity taxPaymentReceiverCodeEnt= new TaxPaymentReceiverCodeEntity();
                    taxPaymentReceiverCodeEnt.setCode(taxPaymentReceiverCodes.getCode());
                    taxPaymentReceiverCodeEnt.setDescription(taxPaymentReceiverCodes.getDescription());
                    taxPaymentReceiverCodeEnt.setNameofDepartment(taxPaymentReceiverCodes.getNameofDepartment());
                    //System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=index"+taxPaymentReceiverCodes.getNameofDepartment());
                    taxPaymentReceiverCodeEnt.setRegisterdAddress(taxPaymentReceiverCodes.getRegisterdAddress());
                    //System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=index"+taxPaymentReceiverCodes.getRegisterdAddress());
                    taxPaymentReceiverCodeEnt.setAccountNumber(taxPaymentReceiverCodes.getAccountNumber());
                    //System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=index"+taxPaymentReceiverCodes.getAccountNumber());
                    taxPaymentReceiverCodeEnt.setBankInstituteName(taxPaymentReceiverCodes.getBankInstituteName());
                    //System.out.println("====convertPOJOToEntity====superProvidentFundList=====0000=index"+taxPaymentReceiverCodes.getBankInstituteName());
                    taxPaymentReceiverCodeEnt.setBankCode(taxPaymentReceiverCodes.getBankCode());
                    taxPaymentReceiverCodeEnt.setFinanceCenterEntity(entity);
                    taxPaymentReceiverCodeEntityList.add(taxPaymentReceiverCodeEnt);

                } else {
                    taxPaymentReceiverCodeEntityList.get(index).setCode(taxPaymentReceiverCodes.getCode());
                    taxPaymentReceiverCodeEntityList.get(index).setDescription(taxPaymentReceiverCodes.getDescription());
                    taxPaymentReceiverCodeEntityList.get(index).setNameofDepartment(taxPaymentReceiverCodes.getNameofDepartment());
                    taxPaymentReceiverCodeEntityList.get(index).setRegisterdAddress(taxPaymentReceiverCodes.getRegisterdAddress());
                    taxPaymentReceiverCodeEntityList.get(index).setAccountNumber(taxPaymentReceiverCodes.getAccountNumber());
                    taxPaymentReceiverCodeEntityList.get(index).setBankInstituteName(taxPaymentReceiverCodes.getBankInstituteName());
                    taxPaymentReceiverCodeEntityList.get(index).setBankCode(taxPaymentReceiverCodes.getBankCode());
                    taxPaymentReceiverCodeEntityList.get(index).setFinanceCenterEntity(entity);
                }
                index++;
            }
            entity.setTaxPaymentReceiverCodeEntity(taxPaymentReceiverCodeEntityList);
        }
        

        return entity;
    }

}
