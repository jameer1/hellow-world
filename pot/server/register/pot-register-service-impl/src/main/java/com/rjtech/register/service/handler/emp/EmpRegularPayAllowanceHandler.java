package com.rjtech.register.service.handler.emp;

import java.util.ArrayList;
import java.util.List;


import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.common.dto.RegularPayAllowance;


public class EmpRegularPayAllowanceHandler {

    public static RegularPayAllowance convertEntityToPOJO(RegularPayAllowanceEntity regularPayAllowanceEntityList) {
    	
    	 System.out.println("==EmpRegularPayAllowanceHandler====");
    	
       // if (!regularPayAllowanceEntityList.isEmpty()) {
        	 System.out.println("==inside if loop====");
			 RegularPayAllowance regularPayAllowance = new RegularPayAllowance();
			 System.out.println("==regularPayAllowanceEntityList.getCode()::"+regularPayAllowanceEntityList.getCode());
			 regularPayAllowance.setCode(regularPayAllowanceEntityList.getCode());
			 System.out.println("==regularPayAllowance.setCode:::"+regularPayAllowance.getCode());
			 System.out.println("==regularPayAllowanceEntityList.getDescription()::"+regularPayAllowanceEntityList.getDescription());
			 regularPayAllowance.setDescription(regularPayAllowanceEntityList.getDescription());
			 System.out.println("==regularPayAllowance.setCode:::"+regularPayAllowance.getDescription());
        	
           // }
        System.out.println("==after if loop====");
        return regularPayAllowance;
        }
       

      
}
