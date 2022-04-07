package com.rjtech.projectlib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class ProjCrewTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private String code;
    private String desc;
    private ProjWorkShiftTO projWorkShiftTO = new ProjWorkShiftTO();
    private Long projId;
    private List<ProjCrewTO> projCrewTOs = new ArrayList<ProjCrewTO>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjWorkShiftTO getProjWorkShiftTO() {
        return projWorkShiftTO;
    }

    public void setProjWorkShiftTO(ProjWorkShiftTO projWorkShiftTO) {
        this.projWorkShiftTO = projWorkShiftTO;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public List<ProjCrewTO> getProjCrewTOs() {
        return projCrewTOs;
    }

    public void setProjCrewTOs(List<ProjCrewTO> projCrewTOs) {
        this.projCrewTOs = projCrewTOs;
    }

}