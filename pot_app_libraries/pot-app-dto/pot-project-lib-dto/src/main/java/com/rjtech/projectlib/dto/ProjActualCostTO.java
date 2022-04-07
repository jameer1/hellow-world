package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjActualCostTO  extends ProjectTO {

    private static final long serialVersionUID = 2L;

    private Double prevManpowerCost = Double.valueOf(0);
    private Double prevPlantCost = Double.valueOf(0);
    private Double prevMaterialCost = Double.valueOf(0);

    private Double reportingManpowerCost = Double.valueOf(0);
    private Double reportingPlantCost = Double.valueOf(0);
    private Double reportingMaterialCost = Double.valueOf(0);

    private Double uptoDateManpowerCost = Double.valueOf(0);
    private Double uptoDatePlantCost = Double.valueOf(0);
    private Double uptoDateMaterialCost = Double.valueOf(0);


}
