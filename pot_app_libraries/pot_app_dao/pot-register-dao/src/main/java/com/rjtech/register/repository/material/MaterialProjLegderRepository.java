package com.rjtech.register.repository.material;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.material.model.MaterialProjLedgerEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface MaterialProjLegderRepository extends RegisterBaseRepository<MaterialProjLedgerEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT T FROM MaterialProjLedgerEntity T WHERE T.materialProjDtlEntity.id=:schItemId  AND T.status=:status AND T.latest='Y' ")
    public MaterialProjLedgerEntity findLatestMaterialProjLedger(@Param("schItemId") Long schItemId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE  MaterialProjLedgerEntity T SET T.latest='N' WHERE T.materialProjDtlEntity.id in :schItemIds  AND T.status=:status")
    public void updateMaterialSchLedgerStatus(@Param("schItemIds") List<Long> schItemIds,
            @Param("status") Integer status);
}
