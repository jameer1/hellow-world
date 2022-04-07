package com.rjtech.mw.service.register;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusSaveReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetPurchaseDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetRepairsDeactiveReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionDeleteReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetRegDeactivateReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetsSaveReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsDeleteReq;
import com.rjtech.register.fixedassets.req.LongTermLeaseActualRetrunsGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingDeactiveReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsDeactiveReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsSaveReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsDeleteReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsGetReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsSaveReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceDeleteReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceGetReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceSaveReq;
import com.rjtech.register.fixedassets.req.RentalValueDeactiveReq;
import com.rjtech.register.fixedassets.req.RentalValueGetReq;
import com.rjtech.register.fixedassets.req.RentalValueSaveReq;
import com.rjtech.register.fixedassets.req.RevenueSalesDeactiveReq;
import com.rjtech.register.fixedassets.req.RevenueSalesGetReq;
import com.rjtech.register.fixedassets.req.RevenueSalesSaveReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsDeactiveReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsFilterReq;
import com.rjtech.register.fixedassets.req.STCORRReturnsGetReq;
import com.rjtech.register.fixedassets.req.SalesRecordsDeactiveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetDeleteReq;
import com.rjtech.register.fixedassets.req.SubAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;
import com.rjtech.register.fixedassets.req.TollCollectionDeleteReq;
import com.rjtech.register.fixedassets.req.TollCollectionGetReq;
import com.rjtech.register.fixedassets.resp.AssetCostValueStatusResp;
import com.rjtech.register.fixedassets.resp.AssetLifeStatusResp;
import com.rjtech.register.fixedassets.resp.CarParkingTollCollectionResp;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;
import com.rjtech.register.fixedassets.resp.FixedAssetRepairsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetsRegisterOnLoadResp;
import com.rjtech.register.fixedassets.resp.LongTermLeaseActualRetrunsResp;
import com.rjtech.register.fixedassets.resp.LongTermLeasingResp;
import com.rjtech.register.fixedassets.resp.MortgagePaymentResp;
import com.rjtech.register.fixedassets.resp.OccupancyUtilisationRecordsResp;
import com.rjtech.register.fixedassets.resp.PeriodicalScheduleMaintenanceResp;
import com.rjtech.register.fixedassets.resp.RentalValueResp;
import com.rjtech.register.fixedassets.resp.RevenueSalesResp;
import com.rjtech.register.fixedassets.resp.STCORRReturnsResp;
import com.rjtech.register.fixedassets.resp.SalesRecordsResp;
import com.rjtech.register.fixedassets.resp.SubAssetsResp;
import com.rjtech.register.fixedassets.resp.SubSequentRentalRecepitsResp;
import com.rjtech.register.fixedassets.resp.TollCollectionResp;

public interface MWFixedAssetsService {

    FixedAssetsRegisterOnLoadResp saveFixedAssetRegisters(FixedAssetsSaveReq fixedAssetsSaveReq);

    FixedAssetsRegisterOnLoadResp fixedAssetRegistersDeactivate(FixedAssetRegDeactivateReq fixedAssetRegDeactivateReq);

    FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoad(FixedAssetsGetReq fixedAssetsGetReq);

    FixedAssetPurachseAcqulistionResp getFixedAssetsPurachaseAcqulisition(
            FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq);

    FixedAssetPurachseAcqulistionResp saveFixedAssetPurachaseAcqulistion(MultipartFile file,
            String fixedAseetPurachaseAcqulisitionSaveReq);

    FixedAssetRepairsResp saveFixedAssetRepairs(MultipartFile file, String fixedAssetRepairsSaveReqStr);

    FixedAssetRepairsResp deactivateAssetsRepairs(AssetRepairsDeactiveReq assetRepairsDeactiveReq);

    FixedAssetRepairsResp getFixedAssetsRepairs(FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq);

    FixedAssetPurachseAcqulistionResp deactivateAssetsPurachase(AssetPurchaseDeactiveReq purchaseDeactiveReq);

    SubAssetsResp getSubAssets(SubAssetsGetReq subAssetsGetReq);

    SubAssetsResp saveSubAssets(SubAssetsSaveReq subAssetsSaveReq);

    SubAssetsResp deactivateSubAsset(SubAssetDeleteReq subAssetsDeleteReq);

    SubAssetsResp subAssetDelete(SubAssetDeleteReq subAssetsDeleteReq);

    RentalValueResp getRentalValue(RentalValueGetReq rentalValueGetReq);

    RentalValueResp saveRentalValue(RentalValueSaveReq rentalValueSaveReq);

    RentalValueResp deactivateRentalValue(RentalValueDeactiveReq rentalValueDeactiveReq);

    SalesRecordsResp getSalesRecords(SalesRecordsGetReq salesRecordsGetReq);

    SalesRecordsResp saveSalesRecords(MultipartFile file, String fromJson);

    SalesRecordsResp deactivateSalesRecords(SalesRecordsDeactiveReq salesRecordsDeactiveReq);

    MortgagePaymentResp getMortgageePayments(MortgageePaymentsGetReq mortgageePaymentsGetReq);

    MortgagePaymentResp saveMortgageePayments(MortgageePaymentsSaveReq mortgageePaymentsSaveReq);

    MortgagePaymentResp deactivateMortgageePayments(MortgageePaymentsDeactiveReq mortgageePaymentsDeactiveReq);

    RevenueSalesResp getRevenueSales(RevenueSalesGetReq revenueSalesGetReq);

    RevenueSalesResp saveRevenueSales(RevenueSalesSaveReq revenueSalesSaveReq);

    RevenueSalesResp deactivateRevenueSales(RevenueSalesDeactiveReq revenueSalesDeactiveReq);

    LongTermLeasingResp getLongTermLeasing(LongTermLeasingGetReq longTermLeasingGetReq);
    
    LongTermLeasingResp getLongtermleaselastrecord(LongTermLeasingGetReq longTermLeasingGetReq);
    
    LongTermLeasingResp getLongtermleaseActiveAllRecord(LongTermLeasingGetReq longTermLeasingGetReq);

    LongTermLeasingResp saveLongTermLeasing(MultipartFile file, String fromJson);

    LongTermLeasingResp deactivateLongTermLeasing(LongTermLeasingDeactiveReq longTermLeasingDeactiveReq);

    LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetruns(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq);
    
    LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetrunsLastRecord(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq);

    LongTermLeaseActualRetrunsResp saveLongTermLeaseActualRetruns(MultipartFile file, String fromJson);

    LongTermLeaseActualRetrunsResp longTermLeaseActualRetrunsDelete(
            LongTermLeaseActualRetrunsDeleteReq longTermLeaseActualRetrunsDeleteReq);

    ResponseEntity<byte[]> getlongTermLeaseActualRetrunsDocDownloadFile(Long fileId);

    CarParkingTollCollectionResp getCarParkingTollCollection(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq);
    //last record in method
    CarParkingTollCollectionResp getCarParkingTollCollectionLastRecord(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq);

    CarParkingTollCollectionResp saveCarParkingTollCollection(MultipartFile file,
            String CarParkingTollCollectionSaveReq);
    
    CarParkingTollCollectionResp carParkingTollCollectionDelete(
            CarParkingTollCollectionDeleteReq carParkingTollCollectionDeleteReq);

    ResponseEntity<byte[]> carParkingTollCollectionDocDownloadFile(Long fileId);
    
    //Toll collection
    TollCollectionResp getTollCollection(
            TollCollectionGetReq TollCollectionGetReq);
    //last record in method
    TollCollectionResp getTollCollectionLastRecord(
            TollCollectionGetReq TollCollectionGetReq);

    TollCollectionResp saveTollCollection(MultipartFile file,
            String TollCollectionSaveReq);

    TollCollectionResp TollCollectionDelete(
            TollCollectionDeleteReq TollCollectionDeleteReq);

    ResponseEntity<byte[]> TollCollectionDocDownloadFile(Long fileId);
    

    OccupancyUtilisationRecordsResp getOccupancyUtilisationRecords(
            OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq);

    OccupancyUtilisationRecordsResp saveOccupancyUtilisationRecords(
            OccupancyUtilisationRecordsSaveReq occupancyUtilisationRecordsSaveReq);

    OccupancyUtilisationRecordsResp occupancyUtilisationRecordsDelete(
            OccupancyUtilisationRecordsDeleteReq occupancyUtilisationRecordsDeleteReq);

    PeriodicalScheduleMaintenanceResp getPeriodicalScheduleMaintenance(
            PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq);

    PeriodicalScheduleMaintenanceResp savePeriodicalScheduleMaintenance(MultipartFile maintainceList,
            MultipartFile completionRecord, PeriodicalScheduleMaintenanceSaveReq fromJson);

    PeriodicalScheduleMaintenanceResp periodicalScheduleMaintenanceDelete(
            PeriodicalScheduleMaintenanceDeleteReq periodicalScheduleMaintenanceDeleteReq);

    ResponseEntity<byte[]> periodicalScheduleMaintenanceDocDownloadFile(Long fileId);

    ResponseEntity<byte[]> periodicalScheduleCompletionDocDownloadFile(Long fileId);

    AssetLifeStatusResp getAssetLifeStatus(AssetLifeStatusGetReq assetLifeStatusGetReq);

    AssetLifeStatusResp saveAssetLifeStatus(MultipartFile file, String fromJson);

    AssetLifeStatusResp assetLifeStatusDelete(AssetLifeStatusDeleteReq assetLifeStatusDeleteReq);

    AssetLifeStatusResp deactivateAssetLifeStatus(AssetLifeStatusDeactiveReq assetLifeStatusDeactiveReq);

    AssetCostValueStatusResp getAssetCostValueStatus(AssetCostValueStatusGetReq assetCostValueStatusGetReq);

    AssetCostValueStatusResp saveAssetCostValueStatus(MultipartFile file, String fromJson);

    AssetCostValueStatusResp assetCostValueStatusDelete(AssetCostValueStatusDeleteReq assetCostValueStatusDeleteReq);
    
    AssetCostValueStatusResp deactivateAssetCostValueStatus(AssetCostValueStatusDeactiveReq assetCostValueStatusDeactiveReq);

    ResponseEntity<ByteArrayResource> assetCostValueStatusDocDownloadFile(Long fileId);

    SubAssetsResp getAssetById(SubAssetsGetReq subAssetsGetReq);

    FixedAssetsRegisterOnLoadResp getAssetOnly(FixedAssetsGetReq fixedAssetsGetReq);

    FixedAssetsRegisterOnLoadResp getAssetSubsById(FixedAssetsGetReq fixedAssetsGetReq);

    FixedAssetsRegisterOnLoadResp saveSubAsset(FixedAssetsSaveReq fixedAssetsSaveReq);

    ResponseEntity<ByteArrayResource> getAssetRecordFileInfo(Long saleRecordId);

    ResponseEntity<ByteArrayResource> getAssetRepairRecordFileInfo(Long repairRecordId);

    ResponseEntity<ByteArrayResource> getAssetPurchaseRecordFileInfo(Long purchaseRecordId);

    ResponseEntity<ByteArrayResource> assetLifeStatusDocDownloadFile(Long assetLifeStatusId);

    ResponseEntity<ByteArrayResource> getLongTermLeaseActualRetrunsFileInfo(Long longTermLeaseActualId);

    LongTermLeaseActualRetrunsResp deactivateLongTermLeaseActualRetruns(
            LongTermLeaseActualRetrunsDeactiveReq longTermLeaseActualRetrunsDeactiveReq);

    ResponseEntity<ByteArrayResource> getSTCORRReturnsFileInfo(Long shortTermRecordId);

    STCORRReturnsResp getStcorrReturns(STCORRReturnsGetReq stcorrReturnsGetReq);

    STCORRReturnsResp saveStcorrReturns(MultipartFile file, String fromJson);

    STCORRReturnsResp deactivateSTCORRReturns(STCORRReturnsDeactiveReq stcorrReturnsDeactiveReq);

    SubSequentRentalRecepitsResp getStcorrSubSequentRentalReceipts(STCORRReturnsGetReq stcorrReturnsGetReq);

    STCORRReturnsResp getStcorrReturnsSearch(STCORRReturnsFilterReq stcorrReturnsFilterReq);
}
