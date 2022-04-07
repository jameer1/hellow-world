package com.rjtech.register.repository.material;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.material.model.MaterialProjLedgerEntity;

//import com.rjtech.register.material.model.MaterialProjLedgerEntityCopy;

public interface MaterialProjLegderRepositoryCopy extends JpaRepository<MaterialProjLedgerEntity, Long> {

    @Modifying
    @Query("UPDATE MaterialProjLedgerEntity T SET T.latest='N' WHERE T.materialProjDtlEntity.id in :schItemIds  AND T.status=:status")
    public void updateMaterialSchLedgerStatus(@Param("schItemIds") List<Long> schItemIds,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialProjLedgerEntity T WHERE T.materialProjDtlEntity.id=:schItemId  AND T.status=:status AND T.latest='Y' ")
    public MaterialProjLedgerEntity findLatestMaterialLedger(@Param("schItemId") Long schItemId,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialProjLedgerEntity T WHERE T.materialProjDtlEntity.id=:schItemId  AND T.materialPODeliveryDocketEntity.id=:docketId AND T.status=:status AND T.docketLatest='Y' ")
    public MaterialProjLedgerEntity findLatestMaterialDeliveryDocketLedger(@Param("schItemId") Long schItemId,
            @Param("docketId") Long docketId, @Param("status") Integer status);

    @Query("SELECT T FROM MaterialProjLedgerEntity T WHERE T.materialProjDtlEntity.id=:schItemId AND T.materialProjDocketSchItemsEntity.id=:projDocketSchId AND T.status=:status   AND T.docketLatest='Y' ")
    public MaterialProjLedgerEntity findLatestMaterialProjDocketSchLedger(@Param("schItemId") Long schItemId,
            @Param("projDocketSchId") Long projDocketSchId, @Param("status") Integer status);
}
