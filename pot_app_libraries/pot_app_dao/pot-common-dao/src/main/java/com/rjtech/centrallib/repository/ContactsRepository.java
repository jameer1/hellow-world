package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.CmpContactsEntity;

public interface ContactsRepository extends CentralLibRepository<CmpContactsEntity, Long> {

    @Query("SELECT CCM FROM CmpContactsEntity CCM  WHERE CCM.companyMstrEntity.id=:cmpId   AND  CCM.status=:status ")
    List<CmpContactsEntity> findByClientId(@Param("cmpId") Long cmpId, @Param("status") Integer status);

    @Query("SELECT CCM FROM CmpContactsEntity CCM  WHERE CCM.companyMstrEntity.clientId.clientId=:clientId AND CCM.defaultContact=true  AND CCM.status=:status")
    List<CmpContactsEntity> findDefaultContactByClientId(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CmpContactsEntity CCM SET  CCM.status=:status   WHERE CCM.id  in :contactIds ")
    void deactivateContacts(@Param("contactIds") List<Long> contactIds, @Param("status") Integer status);

}
