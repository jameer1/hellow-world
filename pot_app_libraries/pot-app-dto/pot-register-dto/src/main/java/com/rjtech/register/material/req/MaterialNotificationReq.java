package com.rjtech.register.material.req;

import java.io.Serializable;

public class MaterialNotificationReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1416570220234910078L;
    
    private long materialNotificationId;

    public long getMaterialNotificationId() {
        return materialNotificationId;
    }

    public void setMaterialNotificationId(long materialNotificationId) {
        this.materialNotificationId = materialNotificationId;
    }
}
