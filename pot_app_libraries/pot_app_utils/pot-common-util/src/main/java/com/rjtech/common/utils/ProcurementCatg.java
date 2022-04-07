package com.rjtech.common.utils;

public enum ProcurementCatg {
    MAN_POWER(1, "manpower", "Manpower"), PLANT(2, "plant", "Plants"), MATERIAL(3, "material", "Materials"),
    SERVICES(4, "services", "Services");

    private final Integer value;
    private final String desc;
    private final String dbConstValue;

    private ProcurementCatg(Integer value, String desc, String constValue) {
        this.value = value;
        this.desc = desc;
        this.dbConstValue = constValue;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getDbConstValue() {
        return dbConstValue;
    }

    public static Integer getDescValue(String desc) {
        for (ProcurementCatg procurementCatg : ProcurementCatg.values()) {
            if (procurementCatg.getDesc().equals(desc)) {
                return procurementCatg.getValue();
            }
        }
        return null;
    }
}
