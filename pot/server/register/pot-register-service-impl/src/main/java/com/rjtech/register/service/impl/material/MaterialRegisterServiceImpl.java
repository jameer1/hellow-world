package com.rjtech.register.service.impl.material;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.register.service.material.MaterialRegisterService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "materialRegisterService")
@RJSService(modulecode = "materialRegisterService")
@Transactional
public class MaterialRegisterServiceImpl implements MaterialRegisterService {

}
