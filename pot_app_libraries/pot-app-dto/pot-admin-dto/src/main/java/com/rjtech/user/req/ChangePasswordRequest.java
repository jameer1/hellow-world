package com.rjtech.user.req;

public class ChangePasswordRequest {
	
    private static final long serialVersionUID = 2740448397989044791L;

    private String email;
    
    private Integer otp;
    
    private String encodeOtp;
    
    private String password;
    
    ChangePasswordRequest(){}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public String getEncodeOtp() {
		return encodeOtp;
	}

	public void setEncodeOtp(String encodeOtp) {
		this.encodeOtp = encodeOtp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ChangePasswordRequest(String email, Integer otp, String encodeOtp, String password) {
		super();
		this.email = email;
		this.otp = otp;
		this.encodeOtp = encodeOtp;
		this.password = password;
	}

	@Override
	public String toString() {
		return "ChangePasswordRequest [email=" + email + ", otp=" + otp + ", encodeOtp=" + encodeOtp + ", password="
				+ password + "]";
	}
    
    
    

}
