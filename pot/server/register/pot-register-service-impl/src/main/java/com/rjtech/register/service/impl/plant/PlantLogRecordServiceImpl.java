package com.rjtech.register.service.impl.plant;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.model.PlantLogRecordEntity;
import com.rjtech.register.plant.req.PlantDeactivateReq;
import com.rjtech.register.plant.req.PlantLogRecordsSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantLogRecordsResp;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.plant.PlantLogRecordRepository;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.service.handler.plant.PlantLogRecordHandler;
import com.rjtech.register.service.plant.PlantLogRecordService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "plantLogRecordsService")
@RJSService(modulecode = "plantLogRecordsService")
@Transactional
public class PlantLogRecordServiceImpl implements PlantLogRecordService {

    @Autowired
    private PlantLogRecordRepository plantLogRecordsRepository;

    @Autowired
    private PlantRegisterRepository plantRegisterRepository;

    @Autowired
    private PlantRegProjRepository plantRegProjRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    public PlantLogRecordsResp getPlantLogRecords(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantLogRecordsResp plantLogRecordsResp = new PlantLogRecordsResp();
        Date fromDate;
        Date toDate;
        if (CommonUtil.isNotBlankStr(plantProjectDtlGetReq.getFromDate())
                && CommonUtil.isNotBlankStr(plantProjectDtlGetReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(plantProjectDtlGetReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(plantProjectDtlGetReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
            plantLogRecordsResp.setFromDate(CommonUtil.convertDateToString(fromDate));
            plantLogRecordsResp.setToDate(CommonUtil.convertDateToString(toDate));
            fromDate = CommonUtil.convertStringToDate(plantLogRecordsResp.getFromDate());
            toDate = CommonUtil.convertStringToDate(plantLogRecordsResp.getToDate());
        }
        toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
        List<PlantLogRecordEntity> plantLogRecordsEntites = plantLogRecordsRepository.findPlantLogRecords(
                plantProjectDtlGetReq.getPlantId(), plantProjectDtlGetReq.getStatus(), fromDate, toDate);
        for (PlantLogRecordEntity plantLogRecordsEntity : plantLogRecordsEntites) {
            plantLogRecordsResp.getPlantLogRecordsTOs()
                    .add(PlantLogRecordHandler.convertEntityToPOJO(plantLogRecordsEntity));
        }
        return plantLogRecordsResp;

    }

    @Transactional
    public void savePlantLogRecords(PlantLogRecordsSaveReq plantLogRecordsSaveReq) {
        PlantLogRecordEntity plantLogRecordEntity = PlantLogRecordHandler.convertPOJOToEntity(
                plantLogRecordsSaveReq.getPlantLogRecordsTO(), plantRegisterRepository, plantRegProjRepository,
                empRegisterRepository);
        plantLogRecordEntity.setLatest(true);
        if (CommonUtil.isNonBlankLong(plantLogRecordsSaveReq.getPlantLogRecordsTO().getId())) {
            PlantLogRecordEntity existingLogRecordEntity = plantLogRecordsRepository
                    .findOne(plantLogRecordsSaveReq.getPlantLogRecordsTO().getId());
            existingLogRecordEntity.setLatest(false);
            Calendar cal = Calendar.getInstance();
            cal.setTime(plantLogRecordEntity.getFromDate());
            cal.add(Calendar.DATE, -1);
            existingLogRecordEntity.setToDate(cal.getTime());
        }
        plantLogRecordsRepository.save(plantLogRecordEntity);
    }

    public void plantLogRecordsDeactivate(PlantDeactivateReq plantDeactivateReq) {
        plantLogRecordsRepository.deactivatePlantLogrecords(plantDeactivateReq.getIds(),
                StatusCodes.DEACIVATE.getValue());
    }

    public PlantLogRecordsTO getMaxOdoMeterReadingByPlantProjId(Long plantProjId) {
        PlantLogRecordsTO plantLog = new PlantLogRecordsTO();
        Long latestPlantLogRecord = plantLogRecordsRepository.getMaxLogRecordIdByPlantProjId(plantProjId);

        if (CommonUtil.isNonBlankLong(latestPlantLogRecord)) {

            PlantLogRecordEntity plantLogRecordsEntity = plantLogRecordsRepository.findOne(latestPlantLogRecord);

            plantLog.setId(plantLogRecordsEntity.getId());
            plantLog.setEndMeter(plantLogRecordsEntity.getEndMeter());
            plantLog.setStartMeter(plantLogRecordsEntity.getStartMeter());

            if (CommonUtil.isBlankBigDecimal(plantLogRecordsEntity.getEndMeter())) {
                plantLog.setLatestMeter(plantLogRecordsEntity.getStartMeter());
            } else {
                plantLog.setLatestMeter(plantLogRecordsEntity.getEndMeter());
            }

        }
        return plantLog;
    }
}
