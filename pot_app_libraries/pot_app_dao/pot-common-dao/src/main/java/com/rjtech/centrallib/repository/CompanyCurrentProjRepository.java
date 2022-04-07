package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.CmpCurrentProjsEntity;

public interface CompanyCurrentProjRepository extends CentralLibRepository<CmpCurrentProjsEntity, Long> {

    @Query("UPDATE CmpCurrentProjsEntity CCP  SET CCP.status=:status  where CCP.id=:cmpCurrentProjId")
    List<Long> deactivateCmpCurrentProjs(@Param("cmpCurrentProjId") Long cmpCurrentProjId,
            @Param("status") Integer status);

    @Query("SELECT CCM FROM CmpCurrentProjsEntity CCM  WHERE CCM.closed = false AND CCM.companyMstrEntity.id=:cmpId AND CCM.status=:status ")
    List<CmpCurrentProjsEntity> findByClientId(@Param("cmpId") Long cmpId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CmpCurrentProjsEntity CCM SET  CCM.status=:status   WHERE CCM.id  in :currentProjIds")
    void deactivateCurrentProjs(@Param("currentProjIds") List<Long> currentProjIds, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CmpCurrentProjsEntity CCM SET  CCM.closed = true  WHERE CCM.id in :projIds")
    void closeProjs(@Param("projIds") List<Long> projIds);

}
