package com.rjtech.user.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.dto.UserTO;

public class UserChangePwdReq extends ClientTO  {
	
	private static final long serialVersionUID = -6671175298681215599L;

    private long userId;
	
	private String CurrentPwd;
	
	private String OldPwd;
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCurrentPwd() {
		return CurrentPwd;
	}

	public void setCurrentPwd(String currentPwd) {
		CurrentPwd = currentPwd;
	}

	@Override
	public String toString() {
		return "UserChangePwdReq [userId=" + userId + ", CurrentPwd=" + CurrentPwd + ", OldPwd=" + OldPwd + "]";
	}

	public String getOldPwd() {
		return OldPwd;
	}

	public void setOldPwd(String oldPwd) {
		OldPwd = oldPwd;
	}

	

	public UserChangePwdReq(long userId, String currentPwd, String oldPwd) {
		super();
		this.userId = userId;
		CurrentPwd = currentPwd;
		OldPwd = oldPwd;
	}

	public UserChangePwdReq()
	{
		
	}
	
}
