package com.rjtech.common.dto;

public class TimeZoneTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long id;
    public String code;
    public String name;
    public String offset;
    private String timezoneId;
    private Float gmtOffset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public Float getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(Float gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

}