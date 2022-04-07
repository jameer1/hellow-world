package com.rjtech.proj.settings.common.service;

import java.util.Map;

import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;

public interface ActualHrsService {

    public Map<Long, LabelKeyTO> getManpowerActualHrs(Long projId);

    public Map<Long, LabelKeyTO> getPlantActualHrs(Long projId);

    public Map<Long, LabelKeyTO> getMaterialActualHrs(Long projId);

    public Map<Long, CostActualHoursTO> getCostCodeActualHrs(Long projId);

}
