package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.rjs.core.annotations.RJSService;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.register.emp.dto.EmpQualificationRecordsTO;
import com.rjtech.register.emp.model.EmpQualificationRecordsEntity;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.register.repository.emp.EmpQualificationRecordsRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpQualificationRecordsService;
import com.rjtech.register.service.handler.emp.EmpQualificationRecordsHandler;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.repository.emp.EmpEnrollmentRepository;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.register.service.emp.RegisterDownloadService;
import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.repository.emp.EmpMedicalHistoryRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetPurchaseRecordsRepository;
import com.rjtech.register.repository.fixeassets.SalesRecordsRepository;
import com.rjtech.register.repository.fixeassets.LongTermLeasingRepository;
import com.rjtech.register.repository.fixeassets.STCORRReturnsRepository;
import com.rjtech.register.repository.fixeassets.LongTermLeaseActualRetrunsRepository;
import com.rjtech.register.repository.fixeassets.CarParkingTollCollectionRepository;
import com.rjtech.register.repository.fixeassets.TollCollectionRepository;
import com.rjtech.register.repository.fixeassets.AssetLifeStatusRepository;
import com.rjtech.register.repository.fixeassets.AssetCostValueStatusRepository;
import com.rjtech.register.repository.fixeassets.FixedAssetRepairsRepository;
import com.rjtech.register.repository.plant.PlantDocketRepository;

import com.rjtech.register.emp.model.EmpMedicalHistoryEntity;
import com.rjtech.register.fixedassets.model.FixedAssetPurchaseRecordsDtlEntity;
import com.rjtech.register.fixedassets.model.SalesRecordDtlEntity;
import com.rjtech.register.fixedassets.model.LongTermLeasingDtlEntity;
import com.rjtech.register.fixedassets.model.STCORRReturnsDtlEntity;
import com.rjtech.register.fixedassets.model.LongTermLeaseActualRetrunsDtlEntity;
import com.rjtech.register.fixedassets.model.CarParkingTollCollectionDtlEntity;
import com.rjtech.register.fixedassets.model.TollCollectionDtlEntity;
import com.rjtech.register.fixedassets.model.AssetLifeStatusDtlEntity;
import com.rjtech.register.fixedassets.model.AssetCostValueStatusDtlEntity;
import com.rjtech.register.fixedassets.model.FixedAssetRepairsDtlEntity;
import com.rjtech.register.plant.model.PlantPODocketDtlEntity;

import com.rjtech.register.repository.fixeassets.PeriodicalScheduleMaintenanceRepository;
import com.rjtech.register.fixedassets.model.PeriodicalScheduleMaintenanceDtlEntity;

@Service(value = "registerDownloadService")
@RJSService(modulecode = "registerDownloadService")
@Transactional
public class RegisterDownloadServiceImpl implements RegisterDownloadService {

    @Autowired
    private EmpQualificationRecordsRepository empQualificationRecordsRepository;
    
    @Autowired
    private EmpMedicalHistoryRepository empMedicalHistoryRepository;
    
    @Autowired
    private FixedAssetPurchaseRecordsRepository fixedAssetPurchaseRecordsRepository;
    
    @Autowired
    private SalesRecordsRepository salesRecordsRepository;
    
    @Autowired
    private LongTermLeasingRepository longTermLeasingRepository;
    
    @Autowired
    private STCORRReturnsRepository stcorrReturnsRepository;
    
    @Autowired
    private LongTermLeaseActualRetrunsRepository longTermLeaseActualRetrunsRepository;
    
    @Autowired
    private CarParkingTollCollectionRepository carParkingTollCollectionRepository;
    
    @Autowired
    private TollCollectionRepository tollCollectionRepository;
    
    @Autowired
    private AssetLifeStatusRepository assetLifeStatusRepository;
    
    @Autowired
    private AssetCostValueStatusRepository assetCostValueStatusRepository;
    
    @Autowired
    private FixedAssetRepairsRepository fixedAssetRepairsRepository;
        
    @Autowired
    private EmpEnrollmentRepository empEnrollmentRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;
    
    @Autowired
    private PlantDocketRepository plantDocketRepository;
    
    @Autowired
    private PeriodicalScheduleMaintenanceRepository periodicalScheduleMaintenanceRepository;
    
    public ProjDocFileTO downloadRegisterDocs( Long recordId, String category ) throws IOException {
		ProjDocFileTO projDocFileTO = new ProjDocFileTO();
		int status = 1;
		ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        byte[] fileBytes = null;      
        System.out.println("downloadRegisterDocs from RegisterDownloadServiceImpl:"+recordId+" category:"+category);
        
    	if( category.equals("Enrollment/Promotions") )
        {
        	EmpEnrollmentDtlEntity empEnrollmentDtlEntity = empEnrollmentRepository.findOne( recordId );
        	projDocFileEntity = empEnrollmentDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Qualification Records") )
        {
        	EmpQualificationRecordsEntity empQualificationRecordsEntity = empQualificationRecordsRepository.findOne( recordId );
        	projDocFileEntity = empQualificationRecordsEntity.getProjDocFileEntity();
        }
        else if( category.equals("Medical History") )
        {
        	EmpMedicalHistoryEntity empMedicalHistoryEntity = empMedicalHistoryRepository.findOne( recordId );
        	projDocFileEntity = empMedicalHistoryEntity.getProjDocFileEntity();
        }
        else if( category.equals("Purchase/Acquisition") )
        {
        	FixedAssetPurchaseRecordsDtlEntity fixedAssetPurchaseRecordsDtlEntity = fixedAssetPurchaseRecordsRepository.findOne( recordId );
        	projDocFileEntity = fixedAssetPurchaseRecordsDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Asset Sale Records") )
        {
        	SalesRecordDtlEntity salesRecordDtlEntity = salesRecordsRepository.findOne( recordId );
        	projDocFileEntity = salesRecordDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Long Term Leasing/Rental History") )
        {
        	LongTermLeasingDtlEntity longTermLeasingDtlEntity = longTermLeasingRepository.findOne( recordId );
        	projDocFileEntity = longTermLeasingDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Short Term/Casual Occupancy Records and Rental Returns") )
        {
        	STCORRReturnsDtlEntity stcorrReturnsDtlEntity = stcorrReturnsRepository.findOne( recordId );
        	projDocFileEntity = stcorrReturnsDtlEntity.getProjDocFileEntity();
        	System.out.println(projDocFileEntity);
        }
        else if( category.equals("Long Term Lease:Rental/Revenue-Actual Revenue") )
        {
        	LongTermLeaseActualRetrunsDtlEntity longTermLeaseActualRetrunsDtlEntity = longTermLeaseActualRetrunsRepository.findOne( recordId );
        	projDocFileEntity = longTermLeaseActualRetrunsDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Car Parking Actual Revenue") )
        {
        	CarParkingTollCollectionDtlEntity carParkingTollCollectionDtlEntity = carParkingTollCollectionRepository.findOne( recordId );
        	projDocFileEntity = carParkingTollCollectionDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Toll Collections Actual Revenue") )
        {
        	TollCollectionDtlEntity tollCollectionDtlEntity = tollCollectionRepository.findOne( recordId );
        	projDocFileEntity = tollCollectionDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Asset Life Status") )
        {
        	AssetLifeStatusDtlEntity assetLifeStatusDtlEntity = assetLifeStatusRepository.findOne( recordId );
        	projDocFileEntity = assetLifeStatusDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Asset Cost and Value Status") )
        {
        	AssetCostValueStatusDtlEntity assetCostValueStatusDtlEntity = assetCostValueStatusRepository.findOne( recordId );
        	projDocFileEntity = assetCostValueStatusDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Repairs and Non Schedule maintenance Records") )
        {
        	FixedAssetRepairsDtlEntity fixedAssetRepairsDtlEntity = fixedAssetRepairsRepository.findOne( recordId );
        	projDocFileEntity = fixedAssetRepairsDtlEntity.getProjDocFileEntity();
        }
        else if( category.equals("Regular Payable Rates") || category.equals("Non Regular Payable Rates") || category.equals("Pay Deductions") || category.equals("Super Annuation/Provident Fund") )
        {
        	projDocFileEntity = projDocFileRepository.findOne( recordId );
        }
        /*else if( category.equals("PreContract List-Reference Documents") || category.equals("PreContract List-Reference Documents") )
        {
        	PreContractDocEntity preContractEntity = precontractDocRepository.findOne( recordId );
        	projDocFileEntity = preContractEntity.getProjDocFileEntity();
        }*/
        else if(category.equals("Procurement Delivery Details")) {
        	 PlantPODocketDtlEntity entity = plantDocketRepository.findOne(recordId);
        	 projDocFileEntity = entity.getProjDocFile();
        }
        else if(category.equals("PlanPeriodicalRecords"))
        {
        	PeriodicalScheduleMaintenanceDtlEntity entity = periodicalScheduleMaintenanceRepository.findOne(recordId);
            projDocFileEntity = entity.getProjDocFileEntity();
        	System.out.println("call here in the "+category);
        	String folder_path = ApplicationConstants.FILE_DIRECTORY + "/" + projDocFileEntity.getFolderId().getUploadFolder() + projDocFileEntity.getFolderPath() + "/" +"PLAN"+ "/" + entity.getPlanRecordsDetailsFileName();
        	 System.out.println("folder path:");
             System.out.println(folder_path);
             projDocFileTO.setFileType( projDocFileEntity.getFileType() );   
             projDocFileTO.setFolderPath( folder_path );
             projDocFileTO.setName( entity.getPlanRecordsDetailsFileName() );
             projDocFileTO.setFileSize( projDocFileEntity.getFileSize() );
             System.out.println(projDocFileTO);
            return projDocFileTO;
        }else if(category.equals("ActualPeriodicalRecords"))
        {
        	PeriodicalScheduleMaintenanceDtlEntity entity = periodicalScheduleMaintenanceRepository.findOne(recordId);
        	projDocFileEntity = entity.getProjDocFileEntity();
        	System.out.println("call ends here at "+category);
        	 String folder_path = ApplicationConstants.FILE_DIRECTORY + "/" + projDocFileEntity.getFolderId().getUploadFolder() + projDocFileEntity.getFolderPath() + "/" +"ACTUAL"+ "/" + projDocFileEntity.getName();
        	 System.out.println("folder path:");
             System.out.println(folder_path);
             projDocFileTO.setFileType( projDocFileEntity.getFileType() );   
             projDocFileTO.setFolderPath( folder_path );
             projDocFileTO.setName( projDocFileEntity.getName() );
             projDocFileTO.setFileSize( projDocFileEntity.getFileSize() );
             System.out.println(projDocFileTO);
             return projDocFileTO;
        }
        String folder_path = ApplicationConstants.FILE_DIRECTORY + "/" + projDocFileEntity.getFolderId().getUploadFolder() + projDocFileEntity.getFolderPath() + "/" + projDocFileEntity.getName();
        System.out.println("folder path:");
        System.out.println(folder_path);
        projDocFileTO.setFileType( projDocFileEntity.getFileType() );   
        projDocFileTO.setFolderPath( folder_path );
        projDocFileTO.setName( projDocFileEntity.getName() );
        projDocFileTO.setFileSize( projDocFileEntity.getFileSize() );
        System.out.println(projDocFileTO);
        return projDocFileTO;
    }
}
