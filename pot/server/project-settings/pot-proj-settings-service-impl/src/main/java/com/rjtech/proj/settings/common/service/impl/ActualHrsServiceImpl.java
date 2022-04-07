package com.rjtech.proj.settings.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.proj.settings.common.service.ActualHrsService;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projschedule.repository.ProjectPlantsRepository;
import com.rjtech.projsettings.repository.ProjManpowerRepository;
import com.rjtech.projsettings.repository.ProjectMaterialRepository;
//import com.rjtech.projsettings.repository.ProjectPlantsRepository;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "actualHrsServiceImpl")
@RJSService(modulecode = "actualHrsServiceImpl")
@Transactional
public class ActualHrsServiceImpl implements ActualHrsService {

    @Autowired
    private ProjManpowerRepository projManpowerRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ProjectPlantsRepository projectPlantsRepository;

    @Autowired
    private ProjectMaterialRepository projectMaterialRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Override
    public Map<Long, LabelKeyTO> getManpowerActualHrs(Long projId) {
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
        List<Object[]> actualHrsList = projManpowerRepository.getManpowerWorkDiaryActualHrs(projMstrEntity);
        actualHrsList.addAll(projManpowerRepository.getManpowerTimesheetActualHrs(projMstrEntity));
        return getMappedResult(actualHrsList);
    }

    @Override
    public Map<Long, LabelKeyTO> getPlantActualHrs(Long projId) {
        return getMappedResult(projectPlantsRepository.getPlantActualHrs(epsProjRepository.findOne(projId)));
    }
    
    @Override
    public Map<Long, LabelKeyTO> getMaterialActualHrs(Long projId) {
        return getMappedResult(
                projectMaterialRepository.getProjectMaterialActualHrs(epsProjRepository.findOne(projId)));
    }

    @Override
    public Map<Long, CostActualHoursTO> getCostCodeActualHrs(Long projId) {
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
        List<Object[]> actualHrsList = projCostItemRepository.getEmpWorkdairyActualHrs(projMstrEntity);
        Map<Long, CostActualHoursTO> actualHrsMap = new HashMap<>();
        actualHrsMap = getEmpMappedResult(actualHrsMap, actualHrsList);
        actualHrsMap = getEmpMappedResult(actualHrsMap,
                projCostItemRepository.getEmpTimesheetActualHrs(projMstrEntity));
        actualHrsMap = getMaterialMappedResult(actualHrsMap,
                projCostItemRepository.getMaterialWorkDairyActualHrs(projMstrEntity));
        actualHrsMap = getPlantMappedResult(actualHrsMap,
                projCostItemRepository.getMaterialPlantActualHrs(projMstrEntity));
        return actualHrsMap;
    }

    private Map<Long, CostActualHoursTO> getEmpMappedResult(Map<Long, CostActualHoursTO> actualHrs,
            List<Object[]> actualHrsList) {
        actualHrsList.forEach((actualHr) -> {
            CostActualHoursTO costActualHoursTo = new CostActualHoursTO();
            costActualHoursTo.setId((Long) actualHr[0]);
            costActualHoursTo.setLabourCost((Double) actualHr[1]);
            if (actualHrs.get(costActualHoursTo.getId()) != null) {
                CostActualHoursTO totalHours = actualHrs.get(costActualHoursTo.getId());
                Double newTotal = Double.valueOf(totalHours.getLabourCost())
                        + Double.valueOf(costActualHoursTo.getLabourCost());
                totalHours.setLabourCost(newTotal);
            } else {
                actualHrs.put(costActualHoursTo.getId(), costActualHoursTo);
            }
        });
        return actualHrs;
    }

    private Map<Long, CostActualHoursTO> getMaterialMappedResult(Map<Long, CostActualHoursTO> actualHrs,
            List<Object[]> actualHrsList) {
        actualHrsList.forEach((actualHr) -> {
            CostActualHoursTO costActualHoursTo = new CostActualHoursTO();
            costActualHoursTo.setId((Long) actualHr[0]);
            costActualHoursTo.setMaterialCost((Double) actualHr[1]);
            if (actualHrs.get(costActualHoursTo.getId()) != null) {
                CostActualHoursTO totalHours = actualHrs.get(costActualHoursTo.getId());
                Double newTotal = Double.valueOf(totalHours.getMaterialCost())
                        + Double.valueOf(costActualHoursTo.getMaterialCost());
                totalHours.setMaterialCost(newTotal);
            } else {
                actualHrs.put(costActualHoursTo.getId(), costActualHoursTo);
            }
        });
        return actualHrs;
    }

    private Map<Long, CostActualHoursTO> getPlantMappedResult(Map<Long, CostActualHoursTO> actualHrs,
            List<Object[]> actualHrsList) {
        actualHrsList.forEach((actualHr) -> {
            CostActualHoursTO costActualHoursTo = new CostActualHoursTO();
            costActualHoursTo.setId((Long) actualHr[0]);
            costActualHoursTo.setPlantCost((Double) actualHr[1]);
            if (actualHrs.get(costActualHoursTo.getId()) != null) {
                CostActualHoursTO totalHours = actualHrs.get(costActualHoursTo.getId());
                double plantCost = 0;
                if (totalHours.getPlantCost() != null) {
                    plantCost = totalHours.getPlantCost().doubleValue();
                }
                Double newTotal = plantCost + costActualHoursTo.getPlantCost();
                totalHours.setPlantCost(newTotal);
            } else {
                actualHrs.put(costActualHoursTo.getId(), costActualHoursTo);
            }
        });
        return actualHrs;
    }

    private Map<Long, LabelKeyTO> getMappedResult(List<Object[]> actualHrsList) {
        Map<Long, LabelKeyTO> actualHrs = new HashMap<Long, LabelKeyTO>();
        actualHrsList.forEach((actualHr) -> {
            LabelKeyTO labelKeyTo = new LabelKeyTO();
            labelKeyTo.setId((Long) actualHr[0]);
            labelKeyTo.setCode(actualHr[1] != null ? String.valueOf((Double) actualHr[1]) : null);
            if (actualHrs.get(labelKeyTo.getId()) != null) {
                LabelKeyTO totalHours = actualHrs.get(labelKeyTo.getId());
                String newTotal = String
                        .valueOf(Double.valueOf(totalHours.getCode()) + Double.valueOf(labelKeyTo.getCode()));
                totalHours.setCode(newTotal);
            } else {
                actualHrs.put(labelKeyTo.getId(), labelKeyTo);
            }
        });
        return actualHrs;
    }

}
