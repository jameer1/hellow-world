package com.rjtech.common.resp;

import java.io.Serializable;

public class AppResp implements Serializable {
    private static final long serialVersionUID = -6671175298681215590L;

    private String status;
    private String message;
    private String msgCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public void cloneAppResp(AppResp inputAppResp) {
        this.status = inputAppResp.getStatus();
        this.message = inputAppResp.getMessage();
        this.msgCode = inputAppResp.getMsgCode();

    }
}
