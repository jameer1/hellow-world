package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.NotificationTO;
import com.rjtech.common.dto.ProjectTO;

public class NotificationSaveReq extends ProjectTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<NotificationTO> notifictionTO = new ArrayList<NotificationTO>();

    public List<NotificationTO> getNotifictionTO() {
        return notifictionTO;
    }

    public void setNotifictionTO(List<NotificationTO> notifictionTO) {
        this.notifictionTO = notifictionTO;
    }

}