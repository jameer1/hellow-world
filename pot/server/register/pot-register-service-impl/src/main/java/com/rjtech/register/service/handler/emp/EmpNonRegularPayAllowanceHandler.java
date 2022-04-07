package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;


import com.rjtech.centrallib.model.NonRegularPayAllowanceEntity;
import com.rjtech.common.dto.NonRegularPayAllowance;


public class EmpNonRegularPayAllowanceHandler {

    public static NonRegularPayAllowance convertEntityToPOJO(NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity) {
    	
    	 System.out.println("==EmpRegularPayAllowanceHandler====");
    	
       // if (!regularPayAllowanceEntityList.isEmpty()) {
        	 System.out.println("==inside if loop====");
        	 NonRegularPayAllowance nonRegularPayAllowance = new NonRegularPayAllowance();
			 System.out.println("==regularPayAllowanceEntityList.getCode()::"+nonRegularPayAllowanceEntity.getCode());
			 nonRegularPayAllowance.setCode(nonRegularPayAllowanceEntity.getCode());
			 System.out.println("==regularPayAllowance.setCode:::"+nonRegularPayAllowance.getCode());
        	
           // }
        System.out.println("==after if loop====");
        return nonRegularPayAllowance;
        }
       

      
}
