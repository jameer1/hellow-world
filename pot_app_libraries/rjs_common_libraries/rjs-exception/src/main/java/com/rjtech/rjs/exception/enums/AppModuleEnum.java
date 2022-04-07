package com.rjtech.rjs.exception.enums;

public enum AppModuleEnum {
	
	ADMIN("01"), COMMON("02"),;



	private String value;


	private AppModuleEnum(String value) {
		this.value = value;
	}
	
	
	public static String getAppModuleKey(String appModule) {
		
		if (appModule.equalsIgnoreCase("01")){
			return AppModuleEnum.ADMIN.toString();
		} else if (appModule.equalsIgnoreCase("02")){
			return AppModuleEnum.COMMON.toString();
		} 
		
		
		
	
		return AppModuleEnum.COMMON.toString();
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
