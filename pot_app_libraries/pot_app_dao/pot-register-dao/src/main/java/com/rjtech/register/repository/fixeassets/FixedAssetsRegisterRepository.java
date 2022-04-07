
package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface FixedAssetsRegisterRepository extends RegisterBaseRepository<FixedAssetsRegisterDtlEntity, Long> {

    @Modifying
    @Query("UPDATE FixedAssetsRegisterDtlEntity FDA SET FDA.status=:status  where FDA.id in :fixedAssetRegIds ")
    void deactivateFixedAssetRegisters(@Param("fixedAssetRegIds") List<Long> fixedAssetRegIds,
            @Param("status") Integer status);

    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.clientId.clientId=:clientId and fda.fixedAssetsRegisterDtlEntity.fixedAssetid is null and fda.status=:status ")
    public List<FixedAssetsRegisterDtlEntity> findFixedAssetByClient(@Param("clientId") Long clientId,
            @Param("status") Integer status);
    
    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.clientId.clientId=:clientId and fda.countryName=:countryName and fda.provisionName=:provisionName "
    		+ "and fda.fixedAssetsRegisterDtlEntity.fixedAssetid is null and fda.status=:status ")
    public List<FixedAssetsRegisterDtlEntity> findFixedAssetByClientcountry(@Param("clientId") Long clientId,@Param("countryName") String countryName,@Param("provisionName") String provisionName,
            @Param("status") Integer status);
    
    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.clientId.clientId=:clientId and fda.countryName=:countryName "
    		+ "and fda.fixedAssetsRegisterDtlEntity.fixedAssetid is null and fda.status=:status ")
    public List<FixedAssetsRegisterDtlEntity> findFixedAssetByClientcountryonly(@Param("clientId") Long clientId,@Param("countryName") String countryName,
            @Param("status") Integer status);
    
    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.clientId.clientId=:clientId and fda.countryName=:countryName and fda.provisionName=:provisionName "
    		+ "and fda.profitCentre =:profitCentre "
    		+ "and fda.fixedAssetsRegisterDtlEntity.fixedAssetid is null and fda.status=:status ")
    public List<FixedAssetsRegisterDtlEntity> findFixedAssetByClientcountryProfit(@Param("clientId") Long clientId,@Param("countryName") String countryName,@Param("provisionName") String provisionName,
    		@Param("profitCentre") String profitCentre,@Param("status") Integer status);

    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.clientId.clientId=:clientId "
    		+ "and fda.profitCentre =:profitCentre "
    		+ "and fda.fixedAssetsRegisterDtlEntity.fixedAssetid is null and fda.status=:status ")
    public List<FixedAssetsRegisterDtlEntity> findFixedAssetByClientProfit(@Param("clientId") Long clientId,
    		@Param("profitCentre") String profitCentre,@Param("status") Integer status);
    
    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.fixedAssetid=:fixedAssetid and fda.status=:status ")
    List<FixedAssetsRegisterDtlEntity> getAssetOnly(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Query("SELECT fda FROM FixedAssetsRegisterDtlEntity fda WHERE fda.fixedAssetid=:fixedAssetid and fda.clientId.clientId=:clientId and fda.status=:status ")
    List<FixedAssetsRegisterDtlEntity> findAssetSubById(@Param("clientId") Long clientId,
            @Param("fixedAssetid") Long fixedAssetid, @Param("status") Integer status);
    
    @Query("SELECT fda.fixedAssetid FROM FixedAssetsRegisterDtlEntity fda WHERE fda.projMstrEntity.projectId=:projectId and fda.status=:status")
    List<Integer> findAllAssetByProjectId(@Param("projectId") Long projectId, @Param("status") Integer status);
    
    @Query("SELECT assetId from FixedAssetsRegisterDtlEntity where fixedAssetid=:id")
    public String getAssetNameById(@Param("id") Long id);

}
