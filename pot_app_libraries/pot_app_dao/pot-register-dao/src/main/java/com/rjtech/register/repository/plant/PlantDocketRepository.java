package com.rjtech.register.repository.plant;

import com.rjtech.register.plant.model.PlantPODocketDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlantDocketRepository extends RegisterBaseRepository<PlantPODocketDtlEntity, Long> {
		
}
