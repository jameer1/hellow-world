package com.rjtech.register.repository.material;

import org.springframework.stereotype.Repository;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface CopyMaterialClassRepository extends RegisterBaseRepository<MaterialClassMstrEntity, Long> {

}
