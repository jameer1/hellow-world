package com.rjtech.centrallib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.repository.POTSequenceGeneratorProcRepository;
import com.rjtech.common.service.POTSequenceGeneratorService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "potSequenceGeneratorService")
@RJSService(modulecode = "potSequenceGeneratorService")
@Transactional
public class POTSequenceGeneratorServiceImpl implements POTSequenceGeneratorService {

    @Autowired
    private POTSequenceGeneratorProcRepository potSequenceGeneratorProcRepository;

    public String getSequenceGenerator(Long projId, String modulePrefix, String moduleCode) {
        return potSequenceGeneratorProcRepository.generatePOTSeqCode(AppUserUtils.getClientId(), projId, modulePrefix,
                moduleCode);
    }

    public String getClientSequenceGenerator(Long clientId, String modulePrefix, String moduleCode) {
        return potSequenceGeneratorProcRepository.generateClientPOTSeqCode(AppUserUtils.getClientId(), modulePrefix,
                moduleCode);
    }

}
