package com.rjtech.register.repository.fixeassets;



	
	import java.util.List;

	import org.springframework.data.jpa.repository.Modifying;
	import org.springframework.data.jpa.repository.Query;
	import org.springframework.data.repository.query.Param;
	import org.springframework.stereotype.Repository;

	import com.rjtech.register.fixedassets.model.TollCollectionDtlEntity;
	import com.rjtech.register.repository.RegisterBaseRepository;

	@Repository
	public interface TollCollectionRepository
	        extends RegisterBaseRepository<TollCollectionDtlEntity, Long> {

	    @Query("SELECT FGV FROM TollCollectionDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid  AND FGV.status=:status ")
	    List<TollCollectionDtlEntity> findAllTollCollection(@Param("fixedAssetid") Long fixedAssetid,
	            @Param("status") Integer status);
	    
	    @Modifying
	    @Query("DELETE from TollCollectionDtlEntity rv  where rv.id in :TollCollectionIds")
	    void TollCollectionDelete(@Param("TollCollectionIds") List<Long> TollCollectionIds);
	    
	    @Query("SELECT FGV FROM TollCollectionDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
	    public List<TollCollectionDtlEntity> findAllProjectToll(@Param("fixedassetids") List<Integer> fixedassetids,
	            @Param("status") Integer status);
	    
	    @Query("SELECT FGV FROM TollCollectionDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid  AND FGV.status=:status AND FGV.createdOn in (select max(FGV.createdOn) from TollCollectionDtlEntity FGV WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid  AND FGV.status=:status)")
	    List<TollCollectionDtlEntity> findAllProjectTollLastRecord(@Param("fixedAssetid") Long fixedAssetid,
	            @Param("status") Integer status);
}
