package com.rjtech.notification.resp;

import com.rjtech.common.resp.AppResp;

public class NotificationCountResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private Integer Count;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

}
