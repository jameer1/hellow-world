package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.CompanyMstrEntity;

public interface CompanyRepository extends CentralLibRepository<CompanyMstrEntity, Long> {
    @Query("SELECT CMP FROM CompanyMstrEntity CMP  WHERE (CMP.clientId IS NULL OR CMP.clientId.clientId=:clientId ) AND  CMP.status=:status ORDER BY CMP.code")
    List<CompanyMstrEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT CMP FROM CompanyMstrEntity CMP  WHERE (CMP.clientId IS NULL OR CMP.clientId.clientId=:clientId )  AND (:cmpCode IS NULL OR CMP.code like :cmpCode  )  AND  (:cmpName IS NULL OR CMP.name like :cmpName)  AND CMP.status=:status ORDER BY CMP.code")
    List<CompanyMstrEntity> findCompanies(@Param("clientId") Long clientId, @Param("cmpCode") String cmpCode,
            @Param("cmpName") String cmpName, @Param("status") Integer status);

    @Query("SELECT CMP FROM CompanyMstrEntity CMP  WHERE (CMP.clientId IS NULL OR CMP.clientId.clientId=:clientId ) AND   CMP.id=:cmpId   AND  CMP.status=:status ORDER BY CMP.code")
    List<CompanyMstrEntity> findCompanyDetails(@Param("clientId") Long clientId, @Param("cmpId") Long cmpId,
            @Param("status") Integer status);

    @Query("SELECT CMP FROM CompanyMstrEntity CMP  WHERE (CMP.clientId IS NULL OR CMP.clientId.clientId=:clientId ) AND   CMP.id in :cmpIds   AND  CMP.status=:status ORDER BY CMP.code")
    List<CompanyMstrEntity> findCompaniesDetailsByCmpIds(@Param("clientId") Long clientId,
            @Param("cmpIds") List<Long> cmpIds, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE CompanyMstrEntity CMP  SET   CMP.status=:status  WHERE   CMP.id in :companyIds")
    void deactivateCompanies(@Param("companyIds") List<Long> companyIds, @Param("status") Integer status);

    @Query("SELECT CMP FROM CompanyMstrEntity CMP WHERE CMP.clientId IS NULL OR CMP.clientId.clientId=:clientId ORDER BY CMP.code")
    List<CompanyMstrEntity> findAllCompanies(@Param("clientId") Long clientId);

    @Query("SELECT CMP.id FROM CompanyMstrEntity CMP WHERE CMP.clientId.clientId=:clientId AND CMP.status=1 ORDER BY CMP.code")
    List<Long> getCmpIds(@Param("clientId") Long clientId);

}
