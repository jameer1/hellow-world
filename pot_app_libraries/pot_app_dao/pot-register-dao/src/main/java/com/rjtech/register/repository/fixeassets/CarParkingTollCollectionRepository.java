package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.fixedassets.model.CarParkingTollCollectionDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository
public interface CarParkingTollCollectionRepository
        extends RegisterBaseRepository<CarParkingTollCollectionDtlEntity, Long> {

    @Query("SELECT FGV FROM CarParkingTollCollectionDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid  AND FGV.status=:status ")
    List<CarParkingTollCollectionDtlEntity> findAllCarParkingTollCollection(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);
    
    @Modifying
    @Query("DELETE from CarParkingTollCollectionDtlEntity rv  where rv.id in :carParkingTollCollectionIds")
    void carParkingTollCollectionDelete(@Param("carParkingTollCollectionIds") List<Long> carParkingTollCollectionIds);
    
    @Query("SELECT FGV FROM CarParkingTollCollectionDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<CarParkingTollCollectionDtlEntity> findAllProjectCarParking(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);
    
    @Query("SELECT FGV FROM CarParkingTollCollectionDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid  AND FGV.status=:status AND FGV.createdOn in (select max(FGV.createdOn) from CarParkingTollCollectionDtlEntity FGV WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid  AND FGV.status=:status)")
    List<CarParkingTollCollectionDtlEntity> findAllProjectCarParkingLastRecord(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

}
