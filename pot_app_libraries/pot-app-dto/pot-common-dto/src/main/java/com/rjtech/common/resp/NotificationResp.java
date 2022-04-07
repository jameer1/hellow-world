package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.NotificationTO;

public class NotificationResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<NotificationTO> notificationTOs = null;

    public NotificationResp() {
        notificationTOs = new ArrayList<NotificationTO>();
    }

    public List<NotificationTO> getNotificationTOs() {
        return notificationTOs;
    }

}