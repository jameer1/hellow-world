package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.fixedassets.model.RentalValueDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface RentalValueRepository extends RegisterBaseRepository<RentalValueDtlEntity, Long> {

    @Query("SELECT rv FROM RentalValueDtlEntity rv WHERE  rv.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND rv.status=:status ")
    List<RentalValueDtlEntity> findAllRentalValue(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE RentalValueDtlEntity rv  SET rv.status=:status  where rv.id in :rentalValueIds")
    void deactivateRentalValue(@Param("rentalValueIds") List<Long> rentalValueIds, @Param("status") Integer status);

    @Modifying
    @Query("DELETE from RentalValueDtlEntity rv  where rv.id in :rentalValueIds")
    void rentalValueDelete(@Param("rentalValueIds") List<Long> rentalValueIds);

}
