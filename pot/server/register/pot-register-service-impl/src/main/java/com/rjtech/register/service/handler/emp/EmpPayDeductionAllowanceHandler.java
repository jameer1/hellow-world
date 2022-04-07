package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;


import com.rjtech.centrallib.model.PayDeductionCodeEntity;
import com.rjtech.common.dto.PayDeductionCodes;


public class EmpPayDeductionAllowanceHandler {

    public static PayDeductionCodes convertEntityToPOJO(PayDeductionCodeEntity payDeductionCodeEntity) {
    	
    	 System.out.println("==EmpRegularPayAllowanceHandler====");
    	
       // if (!payDeductionCodeEntity.isEmpty()) {
        	 System.out.println("==inside if loop====");
			 PayDeductionCodes payDeductionCodes = new PayDeductionCodes();
			 System.out.println("==payDeductionCodeEntity.getCode()::"+payDeductionCodeEntity.getCode());
			 payDeductionCodes.setCode(payDeductionCodeEntity.getCode());
			 System.out.println("==payDeductionCodes.setCode:::"+payDeductionCodes.getCode());
			 System.out.println("==payDeductionCodeEntity.getDescription()::"+payDeductionCodeEntity.getDescription());
			 payDeductionCodes.setDescription(payDeductionCodeEntity.getDescription());
			 System.out.println("==payDeductionCodes.setCode:::"+payDeductionCodes.getDescription());
        	
           // }
        System.out.println("==after if loop====");
        return payDeductionCodes;
        }
       

      
}
