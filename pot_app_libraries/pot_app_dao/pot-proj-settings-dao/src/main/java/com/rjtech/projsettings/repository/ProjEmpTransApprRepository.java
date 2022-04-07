package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;

import com.rjtech.projsettings.model.EmpTransAddtionalTimeApprEntity;

public interface ProjEmpTransApprRepository extends ProjSettingsBaseRepository<EmpTransAddtionalTimeApprEntity, Long> {

    @Query("SELECT T from EmpTransAddtionalTimeApprEntity T where T.status=1 and T.latest=true")
    public EmpTransAddtionalTimeApprEntity findLatestApproval();

}
