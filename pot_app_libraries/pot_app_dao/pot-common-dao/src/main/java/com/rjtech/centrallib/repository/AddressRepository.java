package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.CmpAddressEntity;

public interface AddressRepository extends CentralLibRepository<CmpAddressEntity, Long> {
    @Query("SELECT CAM FROM CmpAddressEntity CAM  WHERE CAM.companyMstrEntity.id=:cmpId   AND CAM.status=:status ")
    List<CmpAddressEntity> findByClientId(@Param("cmpId") Long cmpId, @Param("status") Integer status);

    @Query("SELECT CAM FROM CmpAddressEntity CAM  WHERE CAM.companyMstrEntity.clientId.clientId =:clientId AND CAM.defaultAddress=true  AND CAM.status=:status ")
    List<CmpAddressEntity> findDefaultAddresByClientId(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CmpAddressEntity CAM SET  CAM.status=:status   WHERE CAM.id  in :addressIds ")
    void deactivateAddress(@Param("addressIds") List<Long> addressIds, @Param("status") Integer status);

}
