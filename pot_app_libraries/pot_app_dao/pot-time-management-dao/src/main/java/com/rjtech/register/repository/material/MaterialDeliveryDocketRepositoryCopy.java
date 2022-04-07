package com.rjtech.register.repository.material;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;

//import com.rjtech.register.material.model.MaterialPODeliveryDocketEntityCopy;

public interface MaterialDeliveryDocketRepositoryCopy extends JpaRepository<MaterialPODeliveryDocketEntity, Long> {

}
