package com.rjtech.register.service.impl.material;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.register.material.model.MaterialProjLedgerEntity;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;

public class MaterialProjSchItemValidateBO {

    private Boolean anyIssueExistItem = false;

    private Map<Long, MaterialProjLedgerEntity> ledgerMap = new HashMap<Long, MaterialProjLedgerEntity>();

    private Map<Long, MaterialSchLocCountEntity> schLocCountMap = new HashMap<Long, MaterialSchLocCountEntity>();

    public Boolean getAnyIssueExistItem() {
        return anyIssueExistItem;
    }

    public Map<Long, MaterialProjLedgerEntity> getLedgerMap() {
        return ledgerMap;
    }

    public Map<Long, MaterialSchLocCountEntity> getSchLocCountMap() {
        return schLocCountMap;
    }

    public void setAnyIssueExistItem(Boolean anyIssueExistItem) {
        this.anyIssueExistItem = anyIssueExistItem;
    }

    public void setLedgerMap(Map<Long, MaterialProjLedgerEntity> ledgerMap) {
        this.ledgerMap = ledgerMap;
    }

    public void setSchLocCountMap(Map<Long, MaterialSchLocCountEntity> schLocCountMap) {
        this.schLocCountMap = schLocCountMap;
    }

}
