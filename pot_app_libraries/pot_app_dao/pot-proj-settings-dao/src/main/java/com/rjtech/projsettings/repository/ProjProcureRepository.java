package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProcurementNormalTimeEntity;

public interface ProjProcureRepository extends ProjSettingsBaseRepository<ProcurementNormalTimeEntity, Long> {

    @Query("SELECT PPR FROM com.rjtech.projsettings.model.ProcurementNormalTimeEntity PPR WHERE (( :projId IS NULL AND PPR.projId IS NULL) OR PPR.projId.projectId=:projId) AND PPR.status=:status ORDER BY  PPR.typeId")
    public List<ProcurementNormalTimeEntity> findProjProcure(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PPR FROM com.rjtech.projsettings.model.ProcurementNormalTimeEntity PPR WHERE PPR.isDefault='Y' AND PPR.projId IS NULL ")
    public List<ProcurementNormalTimeEntity> findDefaultProjProcure();
}
