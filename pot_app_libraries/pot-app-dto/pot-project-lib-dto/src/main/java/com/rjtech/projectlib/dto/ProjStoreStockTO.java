package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ClientTO;

public class ProjStoreStockTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private String code;
    private String desc;
    private Long projId;
    private String category;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
