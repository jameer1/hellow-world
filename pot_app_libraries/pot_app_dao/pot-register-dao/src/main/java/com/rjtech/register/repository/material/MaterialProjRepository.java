package com.rjtech.register.repository.material;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface MaterialProjRepository extends RegisterBaseRepository<MaterialProjDtlEntity, Long> {

    @Query("SELECT T FROM MaterialProjDtlEntity T WHERE T.projId.projectId=:projId AND T.status=:status")
    public List<MaterialProjDtlEntity> getProjMaterialSchItems(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialProjDtlEntity T LEFT JOIN FETCH  T.materialSchLocCountEntities LOC WHERE T.purchaseId.id = :purchaseId   AND LOC.baseLocation=true AND T.status=:status")
    public List<MaterialProjDtlEntity> findMaterialSchItemsByPurchaseOrder(@Param("purchaseId") Long purchaseId,
            @Param("status") Integer status);

    @Query("SELECT distinct T FROM MaterialProjDtlEntity T join fetch T.materialPODeliveryDocketEntities md  WHERE T.projId.projectId in (:projId) "
            + " AND T.supplyDeliveryDate between :inputFrom and :inputTO order by T.id desc")
    public List<MaterialProjDtlEntity> getProjMaterialSchItemsByFilter(@Param("projId") List<Long> projId,
            @Param("inputFrom") Date inputFrom, @Param("inputTO") Date inputTO);

    @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId  AND mpd.materialClassId.id not in (:materialClassList) "
            + " and (case when mscl.stockId.id > 0 then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForProjDocket(@Param("projId") Long projId,
            @Param("materialClassList") List<Long> materialClassList, @Param("locationId") Long locationId);

    @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId "
            + " and (case when mscl.stockId.id > 0 then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mpd.materialClassId NOT IN :restrictedMaterials "
            + "AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForProjDocket(@Param("projId") Long projId,
            @Param("locationId") Long locationId,
            @Param("restrictedMaterials") List<MaterialClassMstrEntity> restrictedMaterials);
    
    @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId "
            + " and (case when mscl.stockId.id > 0 then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForProjDocket(@Param("projId") Long projId,
            @Param("locationId") Long locationId);

    @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId  AND mpd.materialClassId.id in (:materialClassList) "
            + " and (case when mscl.stockId.id is not null then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForTransfer(@Param("projId") Long projId,
            @Param("materialClassList") List<Long> materialClassList, @Param("locationId") Long locationId);

    @Query("SELECT mpd FROM MaterialProjDtlEntity mpd " + "  WHERE mpd.projId = :projId "
            + "AND mpd.materialClassId IN :materialClassList")
    public List<MaterialProjDtlEntity> getMaterials(@Param("projId") ProjMstrEntity projId,
            @Param("materialClassList") List<MaterialClassMstrEntity> materialClassList);

    @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId "
            + " and (case when mscl.stockId.id is not null then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mpd.materialClassId NOT IN (:restrictedMaterials) AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForTransfer(@Param("projId") Long projId,
            @Param("locationId") Long locationId,
            @Param("restrictedMaterials") List<MaterialClassMstrEntity> restrictedMaterials);

   @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId "
            + " and (case when mscl.stockId.id is not null then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mpd.materialClassId.id NOT IN (:restrictedMaterials) AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForTransferNew(@Param("projId") Long projId,
            @Param("locationId") Long locationId,
            @Param("restrictedMaterials") List<Long> restrictedMaterials);
    
    @Query("SELECT distinct mpd FROM MaterialProjDtlEntity mpd join fetch mpd.materialSchLocCountEntities mscl  "
            + "  WHERE mpd.projId.projectId=:projId "
            + " and (case when mscl.stockId.id is not null then  mscl.stockId.id else mscl.projStockId.id end)=:locationId "
            + "AND mscl.totalQty IS NOT NULL AND mscl.avlQty > 0")
    public List<MaterialProjDtlEntity> getProjectLocSchItemsForTransfer(@Param("projId") Long projId,
            @Param("locationId") Long locationId);

    @Query("SELECT mpd.id, mscl.avlQty FROM MaterialProjDtlEntity mpd " + "JOIN MaterialSchLocCountEntity mscl "
            + "ON mscl.materialProjDtlEntity = mpd " + "  WHERE mpd.projId.projectId=:projId "
            + " and (case when mscl.stockId.id is not null then  mscl.stockId.id else mscl.projStockId.id end)=:locationId")
    public List<Object[]> getMaterialAvailableQty(@Param("projId") Long projId, @Param("locationId") Long locationId);

    @Query("SELECT T FROM MaterialProjDtlEntity T  WHERE T.projId.projectId=:projId AND "
            + "T.preContractsMaterialCmpEntity.id =:preContractItemCompId AND T.status=:status")
    public List<MaterialProjDtlEntity> getProjMaterialSchItem(@Param("projId") Long projId,
            @Param("preContractItemCompId") Long preContractItemCompId, @Param("status") Integer status);

}