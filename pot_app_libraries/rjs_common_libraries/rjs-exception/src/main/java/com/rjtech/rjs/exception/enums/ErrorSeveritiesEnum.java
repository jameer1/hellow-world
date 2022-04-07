package com.rjtech.rjs.exception.enums;

public enum ErrorSeveritiesEnum {

	CRITICAL_ERROR("1"), ERROR("2"), WARNING("3"), INFORMATION("4"), INVALID_REQUEST(
			"5");

	private String value;

	private ErrorSeveritiesEnum(String value) {
		this.value = value;
	}

	public static String getErrorSeveritiesEnumKey(String appModule) {

		if (appModule.equalsIgnoreCase("1")) {
			return ErrorSeveritiesEnum.CRITICAL_ERROR.toString();
		}
		if (appModule.equalsIgnoreCase("2")) {
			return ErrorSeveritiesEnum.ERROR.toString();
		}
		if (appModule.equalsIgnoreCase("03")) {
			return ErrorSeveritiesEnum.WARNING.toString();
		}
		if (appModule.equalsIgnoreCase("4")) {
			return ErrorSeveritiesEnum.INFORMATION.toString();
		}
		if (appModule.equalsIgnoreCase("5")) {
			return ErrorSeveritiesEnum.INVALID_REQUEST.toString();
		}

		return ErrorSeveritiesEnum.CRITICAL_ERROR.toString();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
