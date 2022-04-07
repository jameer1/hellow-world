package com.rjtech.common.constants;

public enum EmpLeaveType {

    P("Present"), AB("Absent"), PH("Public Holiday"), AL("Annual Leave"), LSL("Long Service Leave"), SL("Sick Leave"),
    ML("Maternity / Parental Leave"), CBL("Compassionate & Bereavement Leave"), CSL("Community Service Leave"),
    UPL("Unpaid Leave");

    private final String desc;

    private EmpLeaveType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
