package com.rjtech.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.CountryProvisionEntity;

@Repository
public interface CountryProvisionRepository extends JpaRepository<CountryProvisionEntity, Long> {

    @Query("SELECT cp FROM CountryProvisionEntity cp WHERE cp.clientId.clientId = :clientId AND "
            + "(cp.code like :code OR cp.name like :name)")
    List<CountryProvisionEntity> findProvisions(@Param("code") String code, @Param("name") String name,
            @Param("clientId") Long clientId);

    @Query("SELECT cp FROM CountryProvisionEntity cp WHERE cp.clientId.clientId = :clientId")
    List<CountryProvisionEntity> findByClientId(@Param("clientId") Long clientId);

}
