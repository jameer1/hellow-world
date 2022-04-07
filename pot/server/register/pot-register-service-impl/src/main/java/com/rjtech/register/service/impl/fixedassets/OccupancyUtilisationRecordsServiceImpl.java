package com.rjtech.register.service.impl.fixedassets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.register.fixedassets.dto.OccupancyUtilisationRecordsDtlTO;
import com.rjtech.register.fixedassets.model.OccupancyUtilisationRecordsDtlEntity;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsDeleteReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsGetReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.OccupancyUtilisationRecordsResp;
import com.rjtech.register.repository.fixeassets.OccupancyUtilisationRecordsRepository;
import com.rjtech.register.service.fixedassets.OccupancyUtilisationRecordsService;
import com.rjtech.register.service.handler.fixedassets.OccupancyUtilisationRecordsDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "occupancyUtilisationRecordsService")
@RJSService(modulecode = "occupancyUtilisationRecordsService")
@Transactional
public class OccupancyUtilisationRecordsServiceImpl implements OccupancyUtilisationRecordsService {

    @Autowired
    private OccupancyUtilisationRecordsRepository occupancyUtilisationRecordsRepository;

    public OccupancyUtilisationRecordsResp getOccupancyUtilisationRecords(
            OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq) {

        OccupancyUtilisationRecordsResp resp = new OccupancyUtilisationRecordsResp();
        List<OccupancyUtilisationRecordsDtlEntity> occupancyUtilisationRecordsDtlEntities = occupancyUtilisationRecordsRepository
                .findOccupancyUtilisationRecords(occupancyUtilisationRecordsGetReq.getSubid());
        for (OccupancyUtilisationRecordsDtlEntity occupancyUtilisationRecordsDtlEntity : occupancyUtilisationRecordsDtlEntities) {
            resp.getOccupancyUtilisationRecordsDtlTOs().add(
                    OccupancyUtilisationRecordsDtlHandler.convertEntityToPOJO(occupancyUtilisationRecordsDtlEntity));
        }

        return resp;

    }

    public void saveOccupancyUtilisationRecords(OccupancyUtilisationRecordsSaveReq occupancyUtilisationRecordsSaveReq) {
        List<OccupancyUtilisationRecordsDtlTO> occupancyUtilisationRecordsDtlTO = occupancyUtilisationRecordsSaveReq
                .getOccupancyUtilisationRecordsDtlTOs();
        for (OccupancyUtilisationRecordsDtlTO occupancyUtilisationRecordsDtlTOs : occupancyUtilisationRecordsDtlTO) {
            OccupancyUtilisationRecordsDtlEntity dtlEntity = null;
            if (occupancyUtilisationRecordsDtlTOs.getId() != null) {
                dtlEntity = occupancyUtilisationRecordsRepository.findOne(occupancyUtilisationRecordsDtlTOs.getId());
            } else {
                dtlEntity = new OccupancyUtilisationRecordsDtlEntity();
            }
            occupancyUtilisationRecordsRepository.save(OccupancyUtilisationRecordsDtlHandler
                    .convertPOJOToEntity(dtlEntity, occupancyUtilisationRecordsDtlTOs));
        }

    }

    public void occupancyUtilisationRecordsDelete(
            OccupancyUtilisationRecordsDeleteReq occupancyUtilisationRecordsDeleteReq) {
        occupancyUtilisationRecordsRepository
                .occupancyUtilisationRecordsDelete(occupancyUtilisationRecordsDeleteReq.getOccupancyIds());

    }

}
