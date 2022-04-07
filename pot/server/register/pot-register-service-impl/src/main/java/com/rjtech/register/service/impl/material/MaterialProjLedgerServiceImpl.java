package com.rjtech.register.service.impl.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.register.repository.material.MaterialProjLegderRepository;
import com.rjtech.register.service.material.MaterialProjLedgerService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "materialProjLedgerService")
@RJSService(modulecode = "materialProjLedgerService")
@Transactional
public class MaterialProjLedgerServiceImpl implements MaterialProjLedgerService {

    @Autowired
    private MaterialProjLegderRepository materialProjLegderRepository;

}
