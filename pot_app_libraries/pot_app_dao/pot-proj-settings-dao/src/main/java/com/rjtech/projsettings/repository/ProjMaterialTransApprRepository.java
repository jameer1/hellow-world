package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;

import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntity;

public interface ProjMaterialTransApprRepository
        extends ProjSettingsBaseRepository<MaterialTransAddtionalTimeApprEntity, Long> {

    @Query("SELECT T FROM  MaterialTransAddtionalTimeApprEntity  T where T.status=1 and T.latest=true")
    public MaterialTransAddtionalTimeApprEntity findLatestApproval();
}
