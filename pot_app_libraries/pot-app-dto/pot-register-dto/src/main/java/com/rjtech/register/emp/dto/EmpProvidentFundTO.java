package com.rjtech.register.emp.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class EmpProvidentFundTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3961436478023138443L;
    private Long id;
    private Long empProjId;
    private Long empRegId;
    private Long projGenId;
    private String payCycle;
    private String fromDate;
    private String toDate;
    private boolean latest;
    private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();
    private List<EmpProvidentFundContributionTO> empProvidentFundContributionTOs = new ArrayList<EmpProvidentFundContributionTO>();
    private List<EmpProvidentFundTaxTO> empProvidentFundTaxTOs = new ArrayList<EmpProvidentFundTaxTO>();
    private List<EmpProvidentFundDetailTO> empProvidentFundDetailTOs = new ArrayList<EmpProvidentFundDetailTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjGenId() {
        return projGenId;
    }

    public void setProjGenId(Long projGenId) {
        this.projGenId = projGenId;
    }

    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
    }

    public List<EmpProvidentFundDetailTO> getEmpProvidentFundDetailTOs() {
        return empProvidentFundDetailTOs;
    }

    public void setEmpProvidentFundDetailTOs(List<EmpProvidentFundDetailTO> empProvidentFundDetailTOs) {
        this.empProvidentFundDetailTOs = empProvidentFundDetailTOs;
    }

    public List<EmpProvidentFundContributionTO> getEmpProvidentFundContributionTOs() {
        return empProvidentFundContributionTOs;
    }

    public void setEmpProvidentFundContributionTOs(
            List<EmpProvidentFundContributionTO> empProvidentFundContributionTOs) {
        this.empProvidentFundContributionTOs = empProvidentFundContributionTOs;
    }

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }

    public List<EmpProvidentFundTaxTO> getEmpProvidentFundTaxTOs() {
        return empProvidentFundTaxTOs;
    }

    public void setEmpProvidentFundTaxTOs(List<EmpProvidentFundTaxTO> empProvidentFundTaxTOs) {
        this.empProvidentFundTaxTOs = empProvidentFundTaxTOs;
    }

}
