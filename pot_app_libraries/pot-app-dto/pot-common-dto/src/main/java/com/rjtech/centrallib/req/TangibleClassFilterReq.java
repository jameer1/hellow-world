package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class TangibleClassFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8054854110376498823L;
    private String tangibleName;
    private String tangibleCode;
    private Integer tangibleClassId;
    
	public String getTangibleName() {
		return tangibleName;
	}
	public void setTangibleName(String tangibleName) {
		this.tangibleName = tangibleName;
	}
	public String getTangibleCode() {
		return tangibleCode;
	}
	public void setTangibleCode(String tangibleCode) {
		this.tangibleCode = tangibleCode;
	}
	public Integer getTangibleClassId() {
		return tangibleClassId;
	}
	public void setTangibleClassId(Integer tangibleClassId) {
		this.tangibleClassId = tangibleClassId;
	}
    
    

    

}
