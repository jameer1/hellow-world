package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projectlib.dto.ProjEmpCatgTO;
import com.rjtech.projectlib.dto.ProjEmpClassTO;

public class ProjEmpClassOnLoadResp extends ProjectTO {

    private static final long serialVersionUID = 3783627591134014183L;

    private List<EmpClassTO> empClassTOs = null;

    private List<MeasureUnitTO> measureUnitTOs = null;

    private List<ProjEmpCatgTO> projEmpCatgTOs = null;

    private ProjEmpClassTO projEmpClassTO = null;

    public ProjEmpClassOnLoadResp() {
        projEmpClassTO = new ProjEmpClassTO();
        measureUnitTOs = new ArrayList<MeasureUnitTO>();
        empClassTOs = new ArrayList<EmpClassTO>();
        projEmpCatgTOs = new ArrayList<ProjEmpCatgTO>();
    }

    public List<EmpClassTO> getEmpClassTOs() {
        return empClassTOs;
    }

    public void setEmpClassTOs(List<EmpClassTO> empClassTOs) {
        this.empClassTOs = empClassTOs;
    }

    public List<ProjEmpCatgTO> getProjEmpCatgTOs() {
        return projEmpCatgTOs;
    }

    public void setProjEmpCatgTOs(List<ProjEmpCatgTO> projEmpCatgTOs) {
        this.projEmpCatgTOs = projEmpCatgTOs;
    }

    public ProjEmpClassTO getProjEmpClassTO() {
        return projEmpClassTO;
    }

    public void setProjEmpClassTO(ProjEmpClassTO projEmpClassTO) {
        this.projEmpClassTO = projEmpClassTO;
    }

    public List<MeasureUnitTO> getMeasureUnitTOs() {
        return measureUnitTOs;
    }

    public void setMeasureUnitTOs(List<MeasureUnitTO> measureUnitTOs) {
        this.measureUnitTOs = measureUnitTOs;
    }

}
