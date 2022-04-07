package com.rjtech.common.service;

import com.rjtech.common.dto.EmailTO;

public interface EmailService {

    public void sendEmail(final EmailTO emailTO);
}