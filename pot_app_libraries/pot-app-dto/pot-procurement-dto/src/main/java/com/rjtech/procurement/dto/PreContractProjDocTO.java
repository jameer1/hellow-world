package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class PreContractProjDocTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5559342478970854101L;

    private Long id;
    private Long preDocContentId;
    private String code;
    private String name;
    private String type;
    private Integer version;
    private String date;
    private List<PreContractDocContentTO> preContractDocContentTOs = new ArrayList<PreContractDocContentTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getPreDocContentId() {
        return preDocContentId;
    }

    public void setPreDocContentId(Long preDocContentId) {
        this.preDocContentId = preDocContentId;
    }

    public List<PreContractDocContentTO> getPreContractDocContentTOs() {
        return preContractDocContentTOs;
    }

    public void setPreContractDocContentTOs(List<PreContractDocContentTO> preContractDocContentTOs) {
        this.preContractDocContentTOs = preContractDocContentTOs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
