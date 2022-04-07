package com.rjtech.register.service.impl.material;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.StockRepository;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
//import com.rjtech.register.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
/*import com.rjtech.procurement.model.PreContractEntityCopy;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntityCopy;*/
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjMaterialClassRepository;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.MaterialTransNormalTimeEntity;
//import com.rjtech.projsettings.model.MaterialTransAddtionalTimeApprEntityCopy;
//import com.rjtech.projsettings.model.MaterialTransNormalTimeEntityCopy;
import com.rjtech.projsettings.repository.MaterialTransAddTimeEntityRepoCopy;
import com.rjtech.projsettings.repository.MaterialTransNormalTimeEntityRepositoryCopy;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.dto.MaterialProjSchItemTO;
import com.rjtech.register.material.dto.MaterialTransferReqApprDtlTO;
import com.rjtech.register.material.dto.MaterialTransferReqApprTO;
import com.rjtech.register.material.model.MaterialNotificationsEntity;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprDtlEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprEntity;
import com.rjtech.register.material.req.MaterialNotificationReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.req.MaterialTransferReqApprSaveReq;
import com.rjtech.register.material.resp.MaterialNotificationResp;
import com.rjtech.register.material.resp.MaterialTransferReqApprResp;
import com.rjtech.register.repository.material.MaterialNotificationsRepository;
import com.rjtech.register.repository.material.MaterialProjRepository;
import com.rjtech.register.repository.material.MaterialSchLocCountRepository;
import com.rjtech.register.repository.material.MaterialTranesferReqApprRepository;
import com.rjtech.register.repository.material.ProjSettingsMaterialTransProcRepository;
import com.rjtech.register.repository.material.UserMstrEntityRepository;
import com.rjtech.register.service.handler.material.MaterialNotificationsHandler;
import com.rjtech.register.service.handler.material.MaterialProjDtlHandler;
import com.rjtech.register.service.handler.material.MaterialTransferReqApprDtlHandler;
import com.rjtech.register.service.handler.material.MaterialTransferReqApprHandler;
import com.rjtech.register.service.material.MaterialTransferReqApprService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@Service(value = "materialTransferReqApprService")
@RJSService(modulecode = "materialTransferReqApprService")
@Transactional
public class MaterialTransferReqApprServiceImpl implements MaterialTransferReqApprService {
	
	private static final Logger log = LoggerFactory.getLogger(MaterialTransferReqApprServiceImpl.class);
	
	private static String pot = "\"Project on Track\"";

    @Autowired
    private CommonEmailServiceImpl commonEmail;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MaterialTranesferReqApprRepository materialTranesferReqApprRepository;

    @Autowired
    @Qualifier("registerMaterialNotificationsRepository")
    private MaterialNotificationsRepository materialNotificationsRepository;

    @Autowired
    private MaterialTransNormalTimeEntityRepositoryCopy materialTransNormalTimeEntityRepository;

    @Autowired
    private MaterialSchLocCountRepository materialSchLocCountRepository;

    @Autowired
    private MaterialProjRepository materialProjRepository;

    @Autowired
    private ProjSettingsMaterialTransProcRepository projSettingsMaterialTransProcRepository;

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private UserMstrEntityRepository userMstrEntityRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProjStoreStockRepositoryCopy projStoreStockRepository;

    @Autowired
    private ProjMaterialClassRepository projMaterialClassRepository;

    @Autowired
    private MaterialTransAddTimeEntityRepoCopy materialTransAdditionalTimeRepository;

    public MaterialTransferReqApprResp getMaterialTransfers(MaterialTransReq materialTransReq) {
    	log.info("public MaterialTransferReqApprResp getMaterialTransfers");
        MaterialTransferReqApprResp materialTransferReqApprResp = new MaterialTransferReqApprResp();
        Date fromDate = null;
        Date toDate = null;
        if (CommonUtil.isNotBlankStr(materialTransReq.getFromDate())
                && CommonUtil.isNotBlankStr(materialTransReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(materialTransReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(materialTransReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
        List<Long> projIds = epsProjService.getUserProjIds();
        Long userId = null;
        Long strUserId = null;
        if (materialTransReq.isLoginUser()) {
            userId = AppUserUtils.getUserId();
            strUserId = Long.valueOf(userId.toString());
        }
        List<MaterialTransferReqApprEntity> materialTransferReqApprEntities = null;
        if (materialTransReq.isTransType()) {
            materialTransferReqApprEntities = materialTranesferReqApprRepository.findMaterialReqTranfers(projIds,
                    strUserId, materialTransReq.getStatus(), fromDate, toDate);
        } else {
            if (RegisterConstants.ALL.equalsIgnoreCase(materialTransReq.getApprStatus())) {
                materialTransferReqApprEntities = materialTranesferReqApprRepository.findMaterialAllTranfers(projIds,
                        strUserId, materialTransReq.getStatus(), fromDate, toDate);
            } else {
                materialTransferReqApprEntities = materialTranesferReqApprRepository.findMaterialApprTranfers(projIds,
                        strUserId, materialTransReq.getApprStatus(), materialTransReq.getStatus(), fromDate, toDate);
            }
        }
        if (CommonUtil.isListHasData(materialTransferReqApprEntities)) {
            for (MaterialTransferReqApprEntity materialTransferReqApprEntity : materialTransferReqApprEntities) {
                materialTransferReqApprResp.getMaterialTransferReqApprTOs()
                        .add(MaterialTransferReqApprHandler.convertEntityToPOJO(materialTransferReqApprEntity));
            }
        }

        return materialTransferReqApprResp;
    }

    public MaterialTransferReqApprResp getMaterialTransferDetails(MaterialTransReq materialTransReq) {
    	log.info("public MaterialTransferReqApprResp getMaterialTransferDetails");
        MaterialTransferReqApprResp materialTransferReqApprResp = new MaterialTransferReqApprResp();
        MaterialTransferReqApprEntity materialTransferReqApprEntity = materialTranesferReqApprRepository
                .findOne(materialTransReq.getId());
        MaterialTransferReqApprTO materialTransferReqApprTO = MaterialTransferReqApprHandler
                .convertEntityToPOJO(materialTransferReqApprEntity);
        for (MaterialTransferReqApprDtlEntity materialTransferReqApprDtlEntity : materialTransferReqApprEntity
                .getMaterialTransferReqApprDtlEntities()) {
            materialTransferReqApprTO.getMaterialTransferReqApprDtlTOs()
                    .add(MaterialTransferReqApprDtlHandler.convertEntityToPOJO(materialTransferReqApprDtlEntity));
        }

        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
        List<Object[]> transferDetails = materialTranesferReqApprRepository
                .getTransferReqDetails(materialTransReq.getId());
        transferDetails.forEach((materialTransfer) -> {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            long mapId = (Long) materialTransfer[0];
            MaterialProjDtlEntity materialProjDtl = materialProjRepository.findOne(mapId);
            labelKeyTO.setId(mapId);
            PreContractsMaterialDtlEntity preContractMaterialDtlEntity = materialProjDtl
                    .getPreContractsMaterialCmpEntity().getPreContractsMaterialDtlEntity();
            PreContractEntity preContractEntity = preContractMaterialDtlEntity.getPreContractEntity();
            PurchaseOrderEntity purchaseOrderEntity = materialProjDtl.getPurchaseId();
            preContractEntity = purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity();
            labelKeyTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                    + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                    + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                    + AppUtils.formatNumberToString(preContractMaterialDtlEntity.getId()));
            labelKeyTO.setName((String) materialTransfer[2]);
            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.PUR_CODE,
                    ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + materialProjDtl.getProjId().getCode()
                            + "-" + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-"
                            + purchaseOrderEntity.getId());
            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_TYPE, (String) materialTransfer[4]);
            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_NAME, (String) materialTransfer[5]);
            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_PARENT_NAME, (String) materialTransfer[6]);
            labelKeyTO.setUnitOfMeasure((String) materialTransfer[7]);
            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.QTY, ((BigDecimal) materialTransfer[8]).toString());
            labelKeyTOs.add(labelKeyTO);
        });
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        labelKeyTOs.forEach((labelKeyTo) -> {
            materialTransferReqApprResp.getTransferMaterialMap().put(labelKeyTo.getId(), labelKeyTo);
        });
        String matTrasType = null;
        if(materialTransferReqApprEntity.getFromProjId().getProjectId().equals(materialTransferReqApprEntity.getToProjId().getProjectId())) {
        	matTrasType = "Internal Transfer";
        } else {
        	matTrasType = "External Transfer";
        }
        materialTransferReqApprResp.setTimeFlag(
                getMaterialTransferProjSettings(materialTransferReqApprEntity.getFromProjId().getProjectId(),
                        materialTransferReqApprEntity.getReqDate(), matTrasType));
        log.info("The Time Flag is " + materialTransferReqApprResp.isTimeFlag());
        if (materialTransferReqApprResp.isTimeFlag() == false) {
        	 MaterialNotificationsEntity matNotificationsEntity = null;
             matNotificationsEntity = materialNotificationsRepository.findOne(materialTransferReqApprEntity.getMaterialNotificationsEntity().getId());
             if(matNotificationsEntity != null) {
             	if ((matNotificationsEntity.getNotificationMsg() != null && matNotificationsEntity.getNotificationMsg().equalsIgnoreCase("Additional Time for Request")) 
             			&& matNotificationsEntity.getNotificationStatus().equalsIgnoreCase("APPROVED")) {
             		materialTransferReqApprResp.setTimeFlag(
                            getMaterialTransferProjSettings(materialTransferReqApprEntity.getFromProjId().getProjectId(),
                            		matNotificationsEntity.getDate(), matTrasType));
             	}
             }
        }
       
        materialTransferReqApprResp.getMaterialTransferReqApprTOs().add(materialTransferReqApprTO);
        return materialTransferReqApprResp;
    }

    private boolean getMaterialTransferProjSettings(Long projId, Date materialTransferReqDate, String matTrasType) {
    	log.info("private boolean getMaterialTransferProjSettings");
        MaterialTransNormalTimeEntity materialTransProjSettings = materialTransNormalTimeEntityRepository
                .findNormalTimeByProject(projId, matTrasType);
        
        LocalDate localDate = Instant.ofEpochMilli(materialTransferReqDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        //LocalDate localDate = ((java.sql.Date) materialTransferReqDate).toLocalDate();
        log.info("localDate " + localDate);
        LocalDateTime localdateTime = localDate.atStartOfDay();
        localdateTime = localdateTime.plusDays(1);
        localdateTime = localdateTime.plusDays(materialTransProjSettings.getCutOffDays());
        localdateTime = localdateTime.plusHours(materialTransProjSettings.getCutOffHours());
        localdateTime = localdateTime.plusMinutes(materialTransProjSettings.getCutOffMinutes());
        log.info("localdateTime " + localdateTime);
        if (localdateTime.isAfter(Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
        	return true;
        } else {
        	MaterialTransAddtionalTimeApprEntity materialTransAddTimeApprEntity = materialTransAdditionalTimeRepository
                    .getMaterialAdditionalTime(projId);
            if (materialTransAddTimeApprEntity != null
                    && (materialTransferReqDate.compareTo(materialTransAddTimeApprEntity.getRequisitionDate()) >= 0)) {
                return true;
            }
        }
        /*
        //int hours = 24; //Should be calculated from next day morning...
        //hours += materialTransProjSettings.getCutOffHours();
        int hours = materialTransProjSettings.getCutOffHours();
        log.info("getCutOffHours " + hours);
        hours += materialTransProjSettings.getCutOffDays() * 24;
        log.info("getCutOffDays " + hours);
        hours += materialTransProjSettings.getCutOffMinutes() / 60;
        log.info("getCutOffMinutes " + hours);
        Calendar calendar = Calendar.getInstance();
        log.info("calendar " + calendar);
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        Date dateToCompare = calendar.getTime();
        log.info("dateToCompare " + dateToCompare);
        log.info("materialTransferReqDate " + materialTransferReqDate);
        
        if (materialTransferReqDate.compareTo(dateToCompare) >= 0) {
            return true;
        } else {
            MaterialTransAddtionalTimeApprEntityCopy materialTransAddTimeApprEntity = materialTransAdditionalTimeRepository
                    .getMaterialAdditionalTime(projId);
            if (materialTransAddTimeApprEntity != null
                    && (materialTransferReqDate.compareTo(materialTransAddTimeApprEntity.getRequisitionDate()) >= 0)) {
                return true;
            }
        }
        */
        return false;
    }

    public AppResp saveMaterialTransfers(MaterialTransferReqApprSaveReq materialTransferReqApprSaveReq) {
        List<MaterialTransferReqApprEntity> materialTransferReqApprEntities = new ArrayList<MaterialTransferReqApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        MaterialNotificationsEntity materialNotificationsEntity = null;
        MaterialNotificationsEntity matNotificationsEntity = null;
        MaterialTransferReqApprEntity materialTransferReqApprEntity = null;
        MaterialTransferReqApprDtlEntity materialTransferReqApprDtlEntity = null;

        if (CommonUtil.isListHasData(materialTransferReqApprSaveReq.getMaterialTransferReqApprTOs())) {
            for (MaterialTransferReqApprTO materialTransferReqApprTO : materialTransferReqApprSaveReq
                    .getMaterialTransferReqApprTOs()) {
                materialTransferReqApprEntity = MaterialTransferReqApprHandler
                        .convertPOJOToEntity(materialTransferReqApprTO, epsProjRepository, stockRepository, projStoreStockRepository);
                for (MaterialTransferReqApprDtlTO materialTransferReqApprDtlTO : materialTransferReqApprTO
                        .getMaterialTransferReqApprDtlTOs()) {
                    if (CommonUtil.isBlankBigDecimal(materialTransferReqApprDtlTO.getApprQty())) {
                        materialTransferReqApprDtlTO.setApprQty(materialTransferReqApprDtlTO.getReqQty());
                    }
                    materialTransferReqApprDtlEntity = MaterialTransferReqApprDtlHandler
                            .convertPOJOToEntity(materialTransferReqApprDtlTO, materialProjRepository);
                    materialTransferReqApprDtlEntity.setMaterialTransferReqApprEntity(materialTransferReqApprEntity);
                    if (CommonUtil.isBlankLong(materialTransferReqApprEntity.getId())) {
                        materialTransferReqApprEntity.setApprStatus(RegisterConstants.PENDING_FOR_APPROVAL);
                        materialTransferReqApprDtlEntity.setApprStatus(RegisterConstants.PENDING_FOR_APPROVAL);
                    }
                    if (RegisterConstants.APPROVED.equalsIgnoreCase(materialTransferReqApprTO.getApprStatus())) {
                        materialTransferReqApprEntity.setApprStatus(RegisterConstants.APPROVED);
                        materialTransferReqApprDtlEntity.setApprStatus(RegisterConstants.APPROVED);
                        materialTransferReqApprDtlEntity.setAvailableQty(materialTransferReqApprDtlEntity.getReqQty());
                        MaterialProjDtlEntity materialProjDtlEntity = materialTransferReqApprDtlEntity.getMaterialId();
                        if (null != materialProjDtlEntity) {
                            Long saveMaterialStockId = null;
                            Long saveMaterialStoreProjectId = null;

                            if( materialTransferReqApprEntity.getFromStoreId() != null ){
                                saveMaterialStockId = materialTransferReqApprEntity.getFromStoreId().getId();
                            }else if( materialTransferReqApprEntity.getFromStoreProjectId() != null ){
                                saveMaterialStoreProjectId = materialTransferReqApprEntity.getFromStoreProjectId().getId();
                            }

                            MaterialSchLocCountEntity materialSchLocCountEntity = materialSchLocCountRepository.findByMatSchIdAndStockIdOrProjstock(materialProjDtlEntity.getId(), saveMaterialStockId, saveMaterialStoreProjectId);
                            materialSchLocCountEntity.setAvlQty(materialSchLocCountEntity.getAvlQty()
                                    .subtract(materialTransferReqApprDtlEntity.getApprQty()));
                            materialTransferReqApprDtlEntity.setMaterialId(saveNewMaterialProjDtl(materialProjDtlEntity,
                                    materialTransferReqApprTO, materialTransferReqApprDtlEntity.getApprQty()));
                        }
                    }
                    materialTransferReqApprEntity.getMaterialTransferReqApprDtlEntities()
                            .add(materialTransferReqApprDtlEntity);
                }

                

                materialNotificationsEntity = populateNotificationEntity(materialTransferReqApprEntity,
                        epsProjRepository, userMstrEntityRepository);
                materialNotificationsEntity.setToProjId(materialTransferReqApprEntity.getToProjId());
                materialNotificationsEntity.setFromStore(materialTransferReqApprEntity.getFromStoreId());
                materialNotificationsEntity.setToStore(materialTransferReqApprEntity.getToStoreId());
                materialNotificationsEntity.setFromStoreProject(materialTransferReqApprEntity.getFromStoreProjectId());
                materialNotificationsEntity.setToStoreProject(materialTransferReqApprEntity.getToStoreProjectId());
                matNotificationsEntity = materialNotificationsRepository.save(materialNotificationsEntity);
                log.info("materialNotificationsEntity.getId() " + materialNotificationsEntity.getId());
                if (CommonUtil.isBlankLong(materialTransferReqApprEntity.getId())) {
                	
                	sendEmailNotification(matNotificationsEntity);
                	log.info("sending Request Email for the Notfication id " + matNotificationsEntity.getId());
                }
                materialTransferReqApprEntity.setMaterialNotificationsEntity(materialNotificationsEntity);
                materialTransferReqApprEntities.add(materialTransferReqApprEntity);

            }
        }
        materialTranesferReqApprRepository.save(materialTransferReqApprEntities);
        return null;
    }
    
    private void sendEmailNotification(MaterialNotificationsEntity materialNotificationsEntity) {
    	log.info("sending email method and the id is " + materialNotificationsEntity.getId());
    	String epsName = null;
        String projName = null;
        String ccEmail;
        String toEmail;
        String toSubject;
        String text;
        String toepsName = null;
        String toprojName = null;
        String reqDate = null;
        
        UserProjGetReq userProjGetReq = new UserProjGetReq();
        UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
            if (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.getKey())) {
                epsName = entry.getValue().getCode();
                projName = entry.getValue().getName();
            }
            if (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
                toepsName = entry.getValue().getCode();
                toprojName = entry.getValue().getName();
            }

        }
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
    	reqDate = dateFormat1.format(materialNotificationsEntity.getDate());
        
        String requestCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                + materialNotificationsEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
        
        String notifyCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                + materialNotificationsEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(materialNotificationsEntity.getDate());
        if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
	        UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
	        UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
	        toEmail = userMstr1.getEmail();
	        ccEmail = userMstr.getEmail();
	        //"\"Project on Track\""
	        toSubject = "Request for Material Transfer within the Internal Project - " + projName;
	        text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>"
	                + "<p>I have submitted my request for \"Internal Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
	                + "<table border='1'>"
	                + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
	                + "<tr><td>Project </td><td>" + projName + "</td></tr>"
					// + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" +
					// + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
	                + "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
	                + "<tr><td>Notification  ID</td><td>" + notifyCode + "</td></tr>"
	                + "</table>"
	                + "<p>This is for your approval please.</p>" 
	                + "<p>Regards,</p>" 
	                + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>"
	                + "</body></html>";
	
	        commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
        } else {
            UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
            UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
            toEmail = userMstr1.getEmail();
            ccEmail = userMstr.getEmail();
            toSubject = "Request for External Project Material Transfer to the project - " + toprojName;
            text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>"
                    + "<p>I have submitted my request \"External Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
                    + "<table border='1'>"
                    + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                    + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                    + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                    + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                    + "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
                    + "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
                    + "</table>"
                    + "<p>This is for your approval please.</p>" 
                    + "<p>Regards,</p>" 
                    + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>" 
                    + "</body></html>";

            commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
        }
    }

    private MaterialProjDtlEntity saveNewMaterialProjDtl(MaterialProjDtlEntity entity,
            MaterialTransferReqApprTO materialTransferReqApprTO, BigDecimal qty) {
        MaterialProjDtlEntity materialProjDtlEntity = new MaterialProjDtlEntity();
        materialProjDtlEntity.setMaterialClassId(entity.getMaterialClassId());
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(materialTransferReqApprTO.getToProjId());
        if (null != projMstrEntity) {
            materialProjDtlEntity.setProjId(projMstrEntity);
        }
        if (CommonUtil.objectNotNull(entity.getCompanyMstrEntity()))
            materialProjDtlEntity.setCompanyMstrEntity(entity.getCompanyMstrEntity());
        if (CommonUtil.objectNotNull(entity.getPreContractsMaterialCmpEntity()))
            materialProjDtlEntity.setPreContractsMaterialCmpEntity(entity.getPreContractsMaterialCmpEntity());
        materialProjDtlEntity.setPreContractMterialId(entity.getPreContractMterialId());
        materialProjDtlEntity.setRate(entity.getRate());
        materialProjDtlEntity.setPreContractMaterialName(entity.getPreContractMaterialName());
        materialProjDtlEntity.setPurchaseId(entity.getPurchaseId());

        MaterialSchLocCountEntity materialSchLocCountEntity = new MaterialSchLocCountEntity();
        materialSchLocCountEntity.setTotalQty(qty.setScale(4, RoundingMode.UP));
        materialSchLocCountEntity.setAvlQty(qty.setScale(4, RoundingMode.UP));

        if( materialTransferReqApprTO.getToStoreId() != null ){
            StockMstrEntity stockMstrEntity = stockRepository.findOne( materialTransferReqApprTO.getToStoreId() );
            if( stockMstrEntity != null ){
                materialSchLocCountEntity.setStockId( stockMstrEntity );
            }
        }else{
            ProjStoreStockMstrEntity projStoreStockMstrEntity = projStoreStockRepository.findOne( materialTransferReqApprTO.getToStoreProjectId() );
            if( projStoreStockMstrEntity != null ){
                materialSchLocCountEntity.setProjStockId( projStoreStockMstrEntity );
            }
        }

        materialSchLocCountEntity.setStatus(StatusCodes.ACTIVE.getValue());
        materialSchLocCountEntity.setStockCode("");
        materialSchLocCountEntity.setBaseLocation(false);
        materialSchLocCountEntity.setMaterialProjDtlEntity(materialProjDtlEntity);
        materialProjDtlEntity.getMaterialSchLocCountEntities().add(materialSchLocCountEntity);
        return materialProjRepository.save(materialProjDtlEntity);
    }

    public MaterialNotificationsEntity populateNotificationEntity(
            MaterialTransferReqApprEntity materialTransferReqEntity, EPSProjRepository epsProjRepository,
            UserMstrEntityRepository userMstrEntityRepository) {
        MaterialNotificationsEntity materialNotificationsEntity = null;
        if (CommonUtil.isBlankLong(materialTransferReqEntity.getId())) {
            materialNotificationsEntity = new MaterialNotificationsEntity();

            //updated
            if (RegisterConstants.PENDING_FOR_APPROVAL.equalsIgnoreCase(materialTransferReqEntity.getApprStatus())) {
                materialNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
            }
            materialNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
            ProjMstrEntity projFromMstrEntity = epsProjRepository
                    .findOne(materialTransferReqEntity.getFromProjId().getProjectId());
            if (null != projFromMstrEntity) {
                materialNotificationsEntity.setFromProjId(projFromMstrEntity);
            }
            ProjMstrEntity projToMstrEntity = epsProjRepository
                    .findOne(materialTransferReqEntity.getToProjId().getProjectId());
            if (null != projToMstrEntity) {
                materialNotificationsEntity.setProjId(projToMstrEntity);
            }
            materialNotificationsEntity.setDate(new Date());
            UserMstrEntity UserReqMstrEntity = userMstrEntityRepository
                    .findOne(materialTransferReqEntity.getReqUserEntity().getUserId());
            UserMstrEntity UserApprMstrEntity = userMstrEntityRepository
                    .findOne(materialTransferReqEntity.getApprUserEntity().getUserId());
            if (null != UserApprMstrEntity) {
                materialNotificationsEntity.setReqUserId(UserReqMstrEntity);
            }
            if (null != UserApprMstrEntity) {
                materialNotificationsEntity.setApprUserId(UserApprMstrEntity);
            }
            materialNotificationsEntity.setIsLatest("Y");
            materialNotificationsEntity.setStatus(StatusCodes.ACTIVE.getValue());
            materialNotificationsEntity.setReqCode(ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                    + materialTransferReqEntity.getToProjId().getCode() + "-" + materialTransferReqEntity.getId());
            materialNotificationsEntity.setReqComments(materialTransferReqEntity.getReqComments());
            materialNotificationsEntity.setApprComments(materialTransferReqEntity.getApprComments());
            if (materialTransferReqEntity.getFromProjId().equals(materialTransferReqEntity.getToProjId())) {
                materialNotificationsEntity.setType("Request for internal project material transfer");

            } else {
                materialNotificationsEntity.setType("Request for External project material transfer");
            }
            /*
            //new request for material transfer email notification
            String epsName = null;
            String projName = null;
            String ccEmail;
            String toEmail;
            String toSubject;
            String text;
            String toepsName = null;
            String toprojName = null;

            UserProjGetReq userProjGetReq = new UserProjGetReq();
            UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
            Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
            LabelKeyTO userProjLabelKeyTO = null;
            for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
                userProjLabelKeyTO = new LabelKeyTO();
                userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
                userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
                userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
                userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
            }
            for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
                if (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.getKey())) {
                    epsName = entry.getValue().getCode();
                    projName = entry.getValue().getName();
                }
                if (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
                    toepsName = entry.getValue().getCode();
                    toprojName = entry.getValue().getName();
                }

            }

            String code = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                    + materialTransferReqEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String apprDate = dateFormat.format(materialNotificationsEntity.getDate());
            
            if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
                UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
                UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
                toEmail = userMstr1.getEmail();
                ccEmail = userMstr.getEmail();
                //"\"Project on Track\""
                toSubject = "Request for Material Transfer within the Internal Project - " + projName;
                text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                        + "<p>I have submitted my request for “Internal Project Material Transfer” through " + pot + ", as per details mentioned here below.</p>"
                        + "<table border='1'>"
                        + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                        + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
						// + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>" +
						// + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                        + "<tr><td>Requisition ID</td><td>" + materialNotificationsEntity.getReqCode() + "</td></tr>"
                        + "<tr><td>Notification  ID</td><td>" + code+ "</td></tr>"
                        + "</table>"
                        + "<p>This is for your Approval Please.</p>" 
                        + "<p>Regards,</p>" 
                        + "<p>" + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>"
                        + "</body></html>";

                commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
           
            } else {
                UserEntity userMstr = userRepository.findOne(materialNotificationsEntity.getReqUserId().getUserId());
                UserEntity userMstr1 = userRepository.findOne(materialNotificationsEntity.getApprUserId().getUserId());
                toEmail = userMstr1.getEmail();
                ccEmail = userMstr.getEmail();
                toSubject = "Request for External Project Material Transfer to the project - " + toprojName;
                text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                        + "<p>I have submitted my request “Eternal Project Material Transfer” through " + pot + ", as per details mentioned here below.</p>"
                        + "<table border='1'>"
                        + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                        + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                        + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                        + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                        + "<tr><td>Requisition ID</td><td>" + materialNotificationsEntity.getReqCode() + "</td></tr>"
                        + "<tr><td>Notification  ID</td><td>" + code + "</td></tr>"
                        + "</table>"
                        + "<p>This is for your approval please.</p>" 
                        + "<p>Regards,</p>" 
                        + "<p>" + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>" 
                        + "</body></html>";

                commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);

            }
			 */
        } else {
            materialNotificationsEntity = materialNotificationsRepository
                    .findOne(materialTransferReqEntity.getMaterialNotificationsEntity().getId());

            String epsName = null;
            String projName = null;
            String ccEmail;
            String toEmail;
            String toSubject;
            String text;
            String toepsName = null;
            String toprojName = null;
            String reqDate = null;

            UserProjGetReq userProjGetReq = new UserProjGetReq();
            UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
            Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
            LabelKeyTO userProjLabelKeyTO = null;
            for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
                userProjLabelKeyTO = new LabelKeyTO();
                userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
                userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
                userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
                userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
            }
            for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
                if (materialNotificationsEntity.getFromProjId().getProjectId().equals(entry.getKey())) {
                    epsName = entry.getValue().getCode();
                    projName = entry.getValue().getName();
                }
                if (materialNotificationsEntity.getProjId().getProjectId().equals(entry.getKey())) {
                    toepsName = entry.getValue().getCode();
                    toprojName = entry.getValue().getName();
                }

            }

            if (RegisterConstants.APPROVED.equalsIgnoreCase(materialTransferReqEntity.getApprStatus())) {
                materialNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_APPROVED);
                if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
                    materialNotificationsEntity.setType("Internal project material transfer approved");
                } else {
                    materialNotificationsEntity.setType("External project material transfer approved");
                }
            } else if (RegisterConstants.REJECTED.equalsIgnoreCase(materialTransferReqEntity.getApprStatus())) {
                materialNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_REJECTED);
                if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
                    materialNotificationsEntity.setType("Internal project material transfer rejected");
                } else {
                    materialNotificationsEntity.setType("External project material transfer rejected");
                }
            }
                
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String apprDate = dateFormat.format(materialNotificationsEntity.getDate());
            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yy");
        	reqDate = dateFormat1.format(materialNotificationsEntity.getDate());
        	
        	String apprDec = materialNotificationsEntity.getNotificationStatus();
            String apprDecision =apprDec.substring(0, 1).toUpperCase() + apprDec.substring(1).toLowerCase();
            
            String requestCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                    + materialNotificationsEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
            
            String notifyCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                    + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                    + materialNotificationsEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId() + " dated " + reqDate;
            
            //Approved / Rejected material trasnfer notification email

            if (materialNotificationsEntity.getFromProjId().equals(materialNotificationsEntity.getProjId())) {
                UserEntity userMstr1 = userRepository
                        .findOne(materialNotificationsEntity.getReqUserId().getUserId());
                UserEntity userMstr = userRepository
                        .findOne(materialNotificationsEntity.getApprUserId().getUserId());
                toEmail = userMstr1.getEmail();
                ccEmail = userMstr.getEmail();
                toSubject = "Approver decision for Material Transfer within the Internal Project - " + toprojName;
                text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                        + "<p>Reference Notification ID - " + notifyCode + "</p>"
                        + "<p>I have transmitted my decision for \"Internal Project Material Transfer\" through " + pot + ", as per details mentioned here below.</p>"
                        + "<table border='1'>"
                        + "<tr><td>EPS </td><td>" + epsName + "</td></tr>"
                        + "<tr><td>Project </td><td>" + projName + "</td></tr>"
                        //+ "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                        //+ "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                        + "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
                        + "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
                        + "<tr><td>Approver Decision</td><td>" + apprDecision + "</td></tr>"
                        + "</table>"
                        + "<p>This is for your information please.</p>" 
                        + "<p>Regards,</p>"
                        + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + " <br/>" + apprDate + "</p>"
                        + "</body></html>";

                commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
            } else {
                UserEntity userMstr1 = userRepository
                        .findOne(materialNotificationsEntity.getReqUserId().getUserId());
                UserEntity userMstr = userRepository
                        .findOne(materialNotificationsEntity.getApprUserId().getUserId());
                toEmail = userMstr1.getEmail();
                ccEmail = userMstr.getEmail();
                toSubject = "Approver decision for External Project Material Transfer to the project - " + toprojName;
                text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                        + "<p>Reference Notification ID - " + notifyCode +" </p>"
                        + "<p>I have transmitted my decision for \"Exernal Project Material Transfer\" through  " + pot + ", as per details mentioned here below.</p>"
                        + "<table border='1'>"
                        + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                        + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                        + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                        + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                        + "<tr><td>Requisition ID</td><td>" + requestCode + "</td></tr>"
                        + "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
                        + "<tr><td>Approver Decision</td><td>" + apprDecision + "</td></tr>"
                        + "</table>"
                        + "<p>This is for your information please.</p>" 
                        + "<p>Regards,</p>"
                        + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>"
                        + "</body></html>";

                commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
            }
        }
        return materialNotificationsEntity;
    }

    public LabelKeyTOResp getMaterialDetailsForTransfer(MaterialTransReq materialTransReq) {
    	log.info("public LabelKeyTOResp getMaterialDetailsForTransfer");
        List<MaterialClassMstrEntity> projMaterialClassMstr = new ArrayList<>();
        log.info("Getting Material Information................");
        if (materialTransReq.getProjId() == materialTransReq.getToProjId()){
            projMaterialClassMstr = projMaterialClassRepository.getInternalProjMaterialMstrEntity(materialTransReq.getProjId(), 1);
        } else {
            projMaterialClassMstr = projMaterialClassRepository.getExternalProjMaterialMstrEntity(materialTransReq.getProjId(), 1);
		}

        List<Long> restrictedMaterialids = new ArrayList<Long>();
        List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
        List<MaterialProjDtlEntity> materials = new ArrayList<MaterialProjDtlEntity>();
        Long locationId = null;

        if( materialTransReq.getStoreId() == null ){
            locationId = materialTransReq.getStoreProjectId();
        }else{
            locationId = materialTransReq.getStoreId();
        }

        if ( projMaterialClassMstr.isEmpty() == false ) {
            projMaterialClassMstr.forEach((restrictedMaterial) -> {
                restrictedMaterialids.add(restrictedMaterial.getId());
            });

            materials = materialProjRepository.getProjectLocSchItemsForTransferNew( materialTransReq.getProjId(), locationId, restrictedMaterialids );
        }else{
            materials = materialProjRepository.getProjectLocSchItemsForTransfer( materialTransReq.getProjId(), locationId );
        }
        
        materials.forEach((material) -> {
            if (!isSupplierDocketMaterial(material)) {
                LabelKeyTO labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(material.getId());
                PreContractsMaterialDtlEntity preContractMaterialDtl = material.getPreContractMterialId();
                PreContractEntity preContract = preContractMaterialDtl.getPreContractEntity();
                labelKeyTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                        + preContract.getProjId().getCode() + "-" + preContract.getId() + "-"
                        + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                        + AppUtils.formatNumberToString(preContractMaterialDtl.getId()));
                labelKeyTO.setName(preContractMaterialDtl.getDesc());
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.PUR_CODE,
                        ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + material.getProjId().getCode()
                                + "-" + preContract.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc()
                                + "-" + material.getPurchaseId().getId());
                MaterialClassMstrEntity materialClass = material.getMaterialClassId();
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_TYPE, materialClass.getCode());
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_NAME, materialClass.getName());
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_PARENT_NAME,
                        materialClass.getMaterialClassMstrEntity().getName());
                labelKeyTO.setUnitOfMeasure(materialClass.getMeasurmentMstrEntity().getCode());
                List<MaterialSchLocCountEntity> materialLocCount = material.getMaterialSchLocCountEntities();
                double availableQty = 0;
                for (MaterialSchLocCountEntity materialLoc : materialLocCount)
                    availableQty += (materialLoc.getAvlQty() == null) ? 0 : materialLoc.getAvlQty().doubleValue();
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.QTY, String.valueOf(availableQty));
                labelKeyTOs.add(labelKeyTO);
            }
        });

        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    private boolean isSupplierDocketMaterial(MaterialProjDtlEntity material) {
        boolean isSupplierDocket = false;
        List<MaterialPODeliveryDocketEntity> materialPoDeliveryDocket = material.getMaterialPODeliveryDocketEntities();
        for (MaterialPODeliveryDocketEntity mDocket : materialPoDeliveryDocket) {
            isSupplierDocket = mDocket.getSupplierDocket();
        }
        return isSupplierDocket;
    }

    public MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq) {
    	log.info("public MaterialNotificationResp getMaterialNotifications");
        MaterialNotificationResp materialNotificationResp = new MaterialNotificationResp();
        // Switching from and to locations as after material approval, material belongs to destination location.
        List<MaterialNotificationsEntity> materialNotificationsEntities = materialNotificationsRepository
                .findMaterialNotificationsBy4(notificationsGetReq.getNotificationStatus(), notificationsGetReq.getProjId(), notificationsGetReq.getToProjId(), notificationsGetReq.getFromStoreId(), notificationsGetReq.getFromStoreProjectId(), notificationsGetReq.getToStoreId(), notificationsGetReq.getToStoreProjectId(), notificationsGetReq.getStatus());
        for (MaterialNotificationsEntity materialNotificationsEntity : materialNotificationsEntities) {
            if (checkIfMaterialQtyIsAvailable(materialNotificationsEntity)) {
                materialNotificationResp.getMaterialNotificationsTOs()
                        .add(MaterialNotificationsHandler.convertEntityToPOJO(materialNotificationsEntity));
            }
        }
        return materialNotificationResp;
    }

    private boolean checkIfMaterialQtyIsAvailable(MaterialNotificationsEntity materialNotificationsEntity) {
        List<MaterialTransferReqApprDtlEntity> materialSchItems = materialTranesferReqApprRepository
                .getMaterialTransferReqApprEntity(materialNotificationsEntity).getMaterialTransferReqApprDtlEntities();
        boolean isAvailable = false;
        for (MaterialTransferReqApprDtlEntity materialTransferReqApprDtlEntity : materialSchItems) {
            if (materialTransferReqApprDtlEntity.getAvailableQty() == null
                    || materialTransferReqApprDtlEntity.getAvailableQty().doubleValue() > 0) {
                isAvailable = true;
            }
        }
        return isAvailable;
    }

    public LabelKeyTOResp getProjSettingsMaterialTransferCheck(MaterialTransReq materialTransReq) {
    	log.info("public LabelKeyTOResp getProjSettingsMaterialTransferCheck");
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
        if (CommonUtil.isNonBlankLong(materialTransReq.getProjId())
                && CommonUtil.isNotBlankStr(materialTransReq.getApprStatus())) {
            labelKeyTOs = projSettingsMaterialTransProcRepository.getProSettingsForMaterialTransCheck(
                    materialTransReq.getProjId(), materialTransReq.getId(), materialTransReq.getApprStatus());
            labelKeyTOResp.getLabelKeyTOs().addAll(labelKeyTOs);

        }
        return labelKeyTOResp;
    }

    @Override
    public MaterialProjDocketTO getMaterialsForProjDocket(MaterialNotificationReq materialNotificationReq) {
    	log.info("public MaterialProjDocketTO getMaterialsForProjDocket");
        MaterialNotificationsEntity materialNotificationEntity = materialNotificationsRepository
                .findOne(materialNotificationReq.getMaterialNotificationId());
        MaterialTransferReqApprEntity materialTransferReqApprEntity = materialTranesferReqApprRepository
                .getMaterialTransferReqApprEntity(materialNotificationEntity);
        MaterialProjDocketTO materialProjDocketTO = new MaterialProjDocketTO();

        ProjMstrEntity fromProjId = materialTransferReqApprEntity.getFromProjId();
        ProjMstrEntity toProjId = materialTransferReqApprEntity.getToProjId();

        materialProjDocketTO.getFromProjLabelkeyTO().setId(fromProjId.getProjectId());
        materialProjDocketTO.getFromProjLabelkeyTO().setName(fromProjId.getProjName());
        if ( materialTransferReqApprEntity.getToStoreId() != null ) {
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID,
                    String.valueOf(materialTransferReqApprEntity.getToStoreId().getId()));
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_CODE,
                    materialTransferReqApprEntity.getToStoreId().getCode());
        } else if( materialTransferReqApprEntity.getToStoreProjectId() != null ){
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID,
                    String.valueOf(materialTransferReqApprEntity.getToStoreProjectId().getId()));
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_CODE,
                    materialTransferReqApprEntity.getToStoreProjectId().getCode());
        } else {
            materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID, "0");
        }

        materialProjDocketTO.getToProjLabelkeyTO().setId(toProjId.getProjectId());
        materialProjDocketTO.getToProjLabelkeyTO().setName(toProjId.getProjName());
        if ( materialTransferReqApprEntity.getFromStoreId() != null ) {
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID,
                    String.valueOf(materialTransferReqApprEntity.getFromStoreId().getId()));
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_CODE,
                    materialTransferReqApprEntity.getFromStoreId().getCode());
        } else if( materialTransferReqApprEntity.getFromStoreProjectId() != null ){
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID,
                    String.valueOf(materialTransferReqApprEntity.getFromStoreProjectId().getId()));
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_CODE,
                    materialTransferReqApprEntity.getFromStoreProjectId().getCode());
        }else {
            materialProjDocketTO.getToProjLabelkeyTO().getDisplayNamesMap().put(CommonConstants.STOCK_SM_ID, "0");

        }

        for (MaterialTransferReqApprDtlEntity materialTransferReqApprDtlEntity : materialTransferReqApprEntity
                .getMaterialTransferReqApprDtlEntities()) {
            MaterialProjSchItemTO schItemTO = new MaterialProjSchItemTO();
            schItemTO.setMaterialClassId(materialTransferReqApprDtlEntity.getId());
            MaterialProjDtlHandler.getSchItem(materialTransferReqApprDtlEntity.getMaterialId(), schItemTO);
            if (materialTransferReqApprDtlEntity.getAvailableQty() == null)
                schItemTO.setCurrentAvaiableQty(materialTransferReqApprDtlEntity.getReqQty());
            else
                schItemTO.setCurrentAvaiableQty(materialTransferReqApprDtlEntity.getAvailableQty());
            schItemTO.setMaterialTransReqId(materialTransferReqApprDtlEntity.getId());
            schItemTO.setTotalQty(materialTransferReqApprDtlEntity.getReqQty());
            materialProjDocketTO.getMaterialProjSchItemTOs().add(schItemTO);
        }

        materialProjDocketTO.getIssuedByLabelkeyTO()
                .setId(materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getId());
        materialProjDocketTO.getIssuedByLabelkeyTO()
                .setCode(materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getCode());
        materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.FIRST_NAME,
                materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getFirstName());
        materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.LAST_NAME,
                materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getLastName());
        if (CommonUtil.objectNotNull(
                materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getEmpClassMstrEntity())) {
            materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CLASS_TYPE,
                    materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getEmpClassMstrEntity().getId()
                            .toString());
        }
        if (CommonUtil.objectNotNull(
                materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getCompanyMstrEntity())) {
            materialProjDocketTO.getIssuedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CMP_ID,
                    materialTransferReqApprEntity.getApprUserEntity().getEmpRegId().getCompanyMstrEntity().getId()
                            .toString());
        }

        materialProjDocketTO.getReceivedByLabelkeyTO()
                .setId(materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getId());
        materialProjDocketTO.getReceivedByLabelkeyTO()
                .setCode(materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getCode());
        materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.FIRST_NAME,
                materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getFirstName());
        materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.LAST_NAME,
                materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getLastName());
        if (CommonUtil.objectNotNull(
                materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getEmpClassMstrEntity())) {
            materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CLASS_TYPE,
                    materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getEmpClassMstrEntity().getId()
                            .toString());
        }

        if (CommonUtil.isNonBlankLong(
                materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getCompanyMstrEntity().getId())) {
            materialProjDocketTO.getReceivedByLabelkeyTO().getDisplayNamesMap().put(CommonConstants.CMP_ID,
                    materialTransferReqApprEntity.getReqUserEntity().getEmpRegId().getCompanyMstrEntity().getId()
                            .toString());
        }

        LabelKeyTO notifyLabelKeyTO = new LabelKeyTO();
        notifyLabelKeyTO.setId(materialNotificationEntity.getId());
        notifyLabelKeyTO.setCode(ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                + materialNotificationEntity.getProjId().getCode() + "-" + materialNotificationEntity.getId());
        materialProjDocketTO.setNotifyLabelKeyTO(notifyLabelKeyTO);
        materialProjDocketTO.setApprStatus(materialNotificationEntity.getNotificationStatus());
        materialProjDocketTO.setStatus(materialNotificationEntity.getStatus());
        return materialProjDocketTO;
    }
}
