package com.rjtech.projectlib.resp;

import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjPlantClassTO;

public class ProjPlantClassOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private MeasureUnitResp measureUnitResp = new MeasureUnitResp();
    private PlantClassResp plantClassResp = new PlantClassResp();
    private ProjPlantClassTO projPlantClassTO = null;
    /*private List<ProjPlantClassTO> projPlantClassTOs=null;*/

    public ProjPlantClassOnLoadResp() {
        projPlantClassTO = new ProjPlantClassTO();
        /*projPlantClassTOs=new ArrayList<ProjPlantClassTO>();*/
    }

    public MeasureUnitResp getMeasureUnitResp() {
        return measureUnitResp;
    }

    public void setMeasureUnitResp(MeasureUnitResp measureUnitResp) {
        this.measureUnitResp = measureUnitResp;
    }

    public PlantClassResp getPlantClassResp() {
        return plantClassResp;
    }

    public void setPlantClassResp(PlantClassResp plantClassResp) {
        this.plantClassResp = plantClassResp;
    }

    public ProjPlantClassTO getProjPlantClassTO() {
        return projPlantClassTO;
    }

    public void setProjPlantClassTO(ProjPlantClassTO projPlantClassTO) {
        this.projPlantClassTO = projPlantClassTO;
    }

    /*public List<ProjPlantClassTO> getProjPlantClassTOs() {
    	return projPlantClassTOs;
    }
    
    public void setProjPlantClassTOs(List<ProjPlantClassTO> projPlantClassTOs) {
    	this.projPlantClassTOs = projPlantClassTOs;
    }*/

}
