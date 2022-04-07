package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;

import com.rjtech.projsettings.model.PlantTransAddtionalTimeApprEntity;

public interface ProjPlantTransApprRepository
        extends ProjSettingsBaseRepository<PlantTransAddtionalTimeApprEntity, Long> {

    @Query("SELECT T FROM PlantTransAddtionalTimeApprEntity T where T.status=1 AND T.latest=true")
    public PlantTransAddtionalTimeApprEntity findLatestApproval();
}
