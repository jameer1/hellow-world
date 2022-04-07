package com.rjtech.centrallib.dto;

import com.rjtech.common.dto.ClientTO;

public class ServiceClassTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private String code;
    private String name;
    
    private String procSubCatName;
    private String procSubCatCode;
    private Integer proCatgId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceClassTO other = (ServiceClassTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

	public String getProcSubCatName() {
		return procSubCatName;
	}

	public void setProcSubCatName(String procSubCatName) {
		this.procSubCatName = procSubCatName;
	}

	public String getProcSubCatCode() {
		return procSubCatCode;
	}

	public void setProcSubCatCode(String procSubCatCode) {
		this.procSubCatCode = procSubCatCode;
	}

	public Integer getProCatgId() {
		return proCatgId;
	}

	public void setProCatgId(Integer proCatgId) {
		this.proCatgId = proCatgId;
	}

}