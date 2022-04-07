package com.rjtech.timemanagement.attendance.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class PlantAttendanceMstrTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private Long crewId;
    private Date attendanceMonth;
    private boolean copyTemplate;
    private String attendenceName;
    private String name;
    private String crewName;
    private String createdBy;
    
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

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Date getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(Date attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public boolean isCopyTemplate() {
        return copyTemplate;
    }

    public void setCopyTemplate(boolean copyTemplate) {
        this.copyTemplate = copyTemplate;
    }

	public String getAttendenceName() {
		return attendenceName;
	}

	public void setAttendenceName(String attendenceName) {
		this.attendenceName = attendenceName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCrewName() {
		return crewName;
	}

	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}