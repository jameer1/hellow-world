package com.rjtech.notification.dto;

import com.rjtech.common.dto.ProjectTO;

public class CountNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
