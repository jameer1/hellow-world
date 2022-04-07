package com.rjtech.projectlib.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ChangeOrderMapTO extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private Long coMstrId;
    private Long coManpowerId;
    private Long coPlantId;
    private Long coMaterialId;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }
    
    public Long getCoMstrId() {
        return coMstrId;
    }

    public void setCoMstrId( Long coMstrId ) {
        this.coMstrId = coMstrId;
    }
    
    public Long getCoManpowerId() {
        return coManpowerId;
    }

    public void setCoManpowerId( Long coManpowerId ) {
        this.coManpowerId = coManpowerId;
    }
    
    public Long getcoPlantId() {
        return coPlantId;
    }

    public void setcoPlantId( Long id ) {
        this.coPlantId = coPlantId;
    }
    
    public Long getCoMaterialId() {
        return coMaterialId;
    }

    public void setCoMaterialId( Long coMaterialId ) {
        this.coMaterialId = coMaterialId;
    }
    
    public String toString() {
    	return " id:"+id+" coMstrId:"+coMstrId+" coManpowerId:"+coManpowerId+" coPlantId:"+coPlantId;
    }
}
