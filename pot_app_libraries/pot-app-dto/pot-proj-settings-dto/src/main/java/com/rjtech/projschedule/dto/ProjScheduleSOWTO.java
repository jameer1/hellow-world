package com.rjtech.projschedule.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.centrallib.dto.TangibleClassTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projectlib.dto.ProjSORItemTO;

public class ProjScheduleSOWTO extends ProjectTO {

    private static final long serialVersionUID = -4929619665133609726L;
    private Long id;
    private Long sowId;
    private Long baseLineId;
    private BigDecimal originalQty;
    private BigDecimal revisedQty;
    private BigDecimal actualQty;
    private BigDecimal remainingQty;
    private BigDecimal estimateComplete;
    private BigDecimal estimateCompletion;
    private String startDate;
    private String finishDate;
    private Long resourceCurveId;
    private String scheduleType;
    private String timeScale;

    private List<String> weeklyData = new ArrayList<String>();
    private List<String> monthlyData = new ArrayList<String>();
    private List<String> yearlyData = new ArrayList<String>();
    private TangibleClassTO tangibleClassTO = new TangibleClassTO();
    private ProjSORItemTO scheduleOfRateTO = new ProjSORItemTO();
    private Date startDt;
    private Date finishDt;
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public Long getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(Long baseLineId) {
        this.baseLineId = baseLineId;
    }

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
        this.originalQty = originalQty;
    }

    public BigDecimal getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(BigDecimal revisedQty) {
        this.revisedQty = revisedQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(BigDecimal remainingQty) {
        this.remainingQty = remainingQty;
    }

    public BigDecimal getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(BigDecimal estimateComplete) {
        this.estimateComplete = estimateComplete;
    }

    public BigDecimal getEstimateCompletion() {
        return estimateCompletion;
    }

    public void setEstimateCompletion(BigDecimal estimateCompletion) {
        this.estimateCompletion = estimateCompletion;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Long getResourceCurveId() {
        return resourceCurveId;
    }

    public void setResourceCurveId(Long resourceCurveId) {
        this.resourceCurveId = resourceCurveId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(String timeScale) {
        this.timeScale = timeScale;
    }

    public List<String> getWeeklyData() {
        return weeklyData;
    }

    public void setWeeklyData(List<String> weeklyData) {
        this.weeklyData = weeklyData;
    }

    public List<String> getMonthlyData() {
        return monthlyData;
    }

    public void setMonthlyData(List<String> monthlyData) {
        this.monthlyData = monthlyData;
    }

    public List<String> getYearlyData() {
        return yearlyData;
    }

    public void setYearlyData(List<String> yearlyData) {
        this.yearlyData = yearlyData;
    }

	public TangibleClassTO getTangibleClassTO() {
		return tangibleClassTO;
	}

	public void setTangibleClassTO(TangibleClassTO tangibleClassTO) {
		this.tangibleClassTO = tangibleClassTO;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getFinishDt() {
		return finishDt;
	}

	public void setFinishDt(Date finishDt) {
		this.finishDt = finishDt;
	}

	public Date getMinStartDateOfBaseline() {
		return minStartDateOfBaseline;
	}

	public void setMinStartDateOfBaseline(Date minStartDateOfBaseline) {
		this.minStartDateOfBaseline = minStartDateOfBaseline;
	}

	public Date getMaxFinishDateOfBaseline() {
		return maxFinishDateOfBaseline;
	}

	public void setMaxFinishDateOfBaseline(Date maxFinishDateOfBaseline) {
		this.maxFinishDateOfBaseline = maxFinishDateOfBaseline;
	}

	public ProjSORItemTO getScheduleOfRateTO() {
		return scheduleOfRateTO;
	}

	public void setScheduleOfRateTO(ProjSORItemTO scheduleOfRateTO) {
		this.scheduleOfRateTO = scheduleOfRateTO;
	}

}
