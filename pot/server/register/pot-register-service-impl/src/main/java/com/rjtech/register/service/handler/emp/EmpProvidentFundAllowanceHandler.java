package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;


import com.rjtech.centrallib.model.SuperProvidentFundEntity;
import com.rjtech.common.dto.SuperProvidentFund;


public class EmpProvidentFundAllowanceHandler {

    public static SuperProvidentFund convertEntityToPOJO(SuperProvidentFundEntity superProvidentFundEntity) {
    	
    	 System.out.println("==EmpRegularPayAllowanceHandler====");
    	
       // if (!payDeductionCodeEntity.isEmpty()) {
        	 System.out.println("==inside if loop====");
			 SuperProvidentFund providentFundCodes = new SuperProvidentFund();
			 System.out.println("==superProvidentFundEntity.getCode()::"+superProvidentFundEntity.getCode());
			 providentFundCodes.setCode(superProvidentFundEntity.getCode());
			 System.out.println("==providentFundCodes.setCode:::"+providentFundCodes.getCode());
			 System.out.println("==superProvidentFundEntity.getDescription()::"+superProvidentFundEntity.getDescription());
			 providentFundCodes.setDescription(superProvidentFundEntity.getDescription());
			 System.out.println("==providentFundCodes.setCode:::"+providentFundCodes.getDescription());
        	
           // }
        System.out.println("==after if loop====");
        return providentFundCodes;
        }
       

      
}
