package com.rjtech.register.service.impl.material;

import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialProjLedgerEntity;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;

public class MaterialProjSchItemEntityBO {

    private MaterialProjLedgerEntity exisingProjLedgerEntity;

    private MaterialSchLocCountEntity schLocCountEntity;

    private MaterialProjLedgerEntity materialProjLedgerEntity;

    private MaterialProjDtlEntity toProjmaterialProjDtlEntity;

    public MaterialProjSchItemEntityBO() {

    }

    public MaterialProjLedgerEntity getExisingProjLedgerEntity() {
        return exisingProjLedgerEntity;
    }

    public MaterialSchLocCountEntity getSchLocCountEntity() {
        return schLocCountEntity;
    }

    public MaterialProjLedgerEntity getMaterialProjLedgerEntity() {
        return materialProjLedgerEntity;
    }

    public void setExisingProjLedgerEntity(MaterialProjLedgerEntity exisingProjLedgerEntity) {
        this.exisingProjLedgerEntity = exisingProjLedgerEntity;
    }

    public void setSchLocCountEntity(MaterialSchLocCountEntity schLocCountEntity) {
        this.schLocCountEntity = schLocCountEntity;
    }

    public void setMaterialProjLedgerEntity(MaterialProjLedgerEntity materialProjLedgerEntity) {
        this.materialProjLedgerEntity = materialProjLedgerEntity;
    }

    public MaterialProjDtlEntity getToProjmaterialProjDtlEntity() {
        return toProjmaterialProjDtlEntity;
    }

    public void setToProjmaterialProjDtlEntity(MaterialProjDtlEntity toProjmaterialProjDtlEntity) {
        this.toProjmaterialProjDtlEntity = toProjmaterialProjDtlEntity;
    }

}
