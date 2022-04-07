package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOWItemTO;

public class ProjSOWItemResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOWItemTO> projSOWItemTOs = null;
    private List<String> calNonWorkingDays = null;
    private Map<Long, LabelKeyTO> actualWorkingDayMap = null;
    private Map<Long, LabelKeyTO> sowItemMap = null;
    private Map<Long, LabelKeyTO> tangibleItemMap = null;
    private List<LabelKeyTO> dateWiseActualQuantity = null;

    public ProjSOWItemResp() {
        projSOWItemTOs = new ArrayList<ProjSOWItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calNonWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        actualWorkingDayMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        sowItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        tangibleItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        dateWiseActualQuantity = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSOWItemTO> getProjSOWItemTOs() {
        return projSOWItemTOs;
    }

    public void setProjSOWItemTOs(List<ProjSOWItemTO> projSOWItemTOs) {
        this.projSOWItemTOs = projSOWItemTOs;
    }

    public List<String> getCalNonWorkingDays() {
        return calNonWorkingDays;
    }

    public void setCalNonWorkingDays(List<String> calNonWorkingDays) {
        this.calNonWorkingDays = calNonWorkingDays;
    }

    public Map<Long, LabelKeyTO> getActualWorkingDayMap() {
        return actualWorkingDayMap;
    }

    public void setActualWorkingDayMap(Map<Long, LabelKeyTO> actualWorkingDayMap) {
        this.actualWorkingDayMap = actualWorkingDayMap;
    }

    public Map<Long, LabelKeyTO> getSowItemMap() {
        return sowItemMap;
    }

    public void setSowItemMap(Map<Long, LabelKeyTO> sowItemMap) {
        this.sowItemMap = sowItemMap;
    }
    
    public Map<Long, LabelKeyTO> getTangibleItemMap(){
    	return tangibleItemMap;
    }
    
    public void setTangibleItemMap(Map<Long, LabelKeyTO> tangibleItemMap) {
    	this.tangibleItemMap = tangibleItemMap;
    }

    public List<LabelKeyTO> getDateWiseActualQuantity() {
        return dateWiseActualQuantity;
    }

    public void setDateWiseActualQuantity(List<LabelKeyTO> dateWiseActualQuantity) {
        this.dateWiseActualQuantity = dateWiseActualQuantity;
    }

}
