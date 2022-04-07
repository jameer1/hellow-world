package com.rjtech.register.repository.material;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.material.model.MaterialSchLocCountEntity;

@Repository
public interface MaterialSchLocCountRepository extends JpaRepository<MaterialSchLocCountEntity, Long> {

    @Query("SELECT T FROM  MaterialSchLocCountEntity T  WHERE  T.materialProjDtlEntity.id=:matSchId  AND T.stockId.id=:stockId ")
    public MaterialSchLocCountEntity findByMatSchIdAndStockId(@Param("matSchId") Long matschId,
            @Param("stockId") Long stockId);

    @Query("SELECT T FROM  MaterialSchLocCountEntity T  WHERE  T.materialProjDtlEntity.id=:matSchId  AND ( (T.stockId.id IS NOT NULL AND T.stockId.id = :stockId) OR (T.projStockId.id IS NOT NULL AND T.projStockId.id = :projStockId) )")
    public MaterialSchLocCountEntity findByMatSchIdAndStockIdOrProjstock(@Param("matSchId") Long matschId,
            @Param("stockId") Long stockId, @Param("projStockId") Long projStockId);

    @Query("SELECT T FROM MaterialSchLocCountEntity T  WHERE  T.materialProjDtlEntity.id=:matSchId  AND T.projStockId.id=:projStockId ")
    public MaterialSchLocCountEntity findByMatSchIdAndProjStockId(@Param("matSchId") Long matschId,
            @Param("projStockId") Long projStockId);

    @Modifying
    @Query("UPDATE MaterialSchLocCountEntity T  SET T.avlQty=:avlQty WHERE  T.materialProjDtlEntity.id=:matSchId  AND T.stockId.id=:stockId ")
    void updateProjSchLocationAvlQty(@Param("matSchId") Long matSchId, @Param("stockId") Long stockId,
            @Param("avlQty") BigDecimal avlQty);

}
