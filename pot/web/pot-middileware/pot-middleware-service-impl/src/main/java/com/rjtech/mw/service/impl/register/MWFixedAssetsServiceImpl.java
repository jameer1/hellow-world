package com.rjtech.mw.service.impl.register;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusGetReq;
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
import com.rjtech.register.fixedassets.req.STCORRReturnsSaveReq;
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
import com.rjtech.rjs.core.annotations.RJSService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;

@Service(value = "mwFixedAssetsService")
@RJSService(modulecode = "mwFixedAssetsService")
@Transactional
public class MWFixedAssetsServiceImpl extends RestConfigServiceImpl implements MWFixedAssetsService {

    public FixedAssetsRegisterOnLoadResp saveFixedAssetRegisters(FixedAssetsSaveReq fixedAssetsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_FIXED_ASSETS_REGISTERS);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetsRegisterOnLoadResp.class);
    }

    public FixedAssetsRegisterOnLoadResp fixedAssetRegistersDeactivate(
            FixedAssetRegDeactivateReq fixedAssetRegDeactivateReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetRegDeactivateReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.FIXED_ASSETS_REGISTERS_DEACTIVATE);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetsRegisterOnLoadResp.class);
    }

    public FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoad(FixedAssetsGetReq fixedAssetsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.FIXED_ASSETS_REGISTERS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetsRegisterOnLoadResp.class);
    }

    public FixedAssetPurachseAcqulistionResp getFixedAssetsPurachaseAcqulisition(
            FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetPurachaseRecordsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_FIXED_ASSETS_PURACHASE_ACQULISITION);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetPurachseAcqulistionResp.class);
    }

    public FixedAssetRepairsResp getFixedAssetsRepairs(FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetRepairsRecordsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_REPAIRS_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetRepairsResp.class);
    }

    public FixedAssetPurachseAcqulistionResp saveFixedAssetPurachaseAcqulistion(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PURACHASE_ACQULISITION);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetPurachseAcqulistionResp.class);
    }

    public FixedAssetRepairsResp saveFixedAssetRepairs(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_REPAIRS_RECORDS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetRepairsResp.class);
    }

    public FixedAssetPurachseAcqulistionResp deactivateAssetsPurachase(AssetPurchaseDeactiveReq purchaseDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(purchaseDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.FIXED_ASSETS_PURCHASE_ACQULISITION_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetPurachseAcqulistionResp.class);
    }
    
    public FixedAssetRepairsResp deactivateAssetsRepairs(AssetRepairsDeactiveReq assetRepairsDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetRepairsDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.FIXED_ASSETS_REPAIRS_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetRepairsResp.class);
    }

    public SubAssetsResp getSubAssets(SubAssetsGetReq subAssetsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(subAssetsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_SUB_ASSETS);
        return AppUtils.fromJson(strResponse.getBody(), SubAssetsResp.class);

    }

    public SubAssetsResp saveSubAssets(SubAssetsSaveReq subAssetsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(subAssetsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_SUBASSETS);
        return AppUtils.fromJson(strResponse.getBody(), SubAssetsResp.class);

    }

    public SubAssetsResp deactivateSubAsset(SubAssetDeleteReq subAssetsDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(subAssetsDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SUB_ASSETS_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), SubAssetsResp.class);

    }

    public SubAssetsResp subAssetDelete(SubAssetDeleteReq subAssetsDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(subAssetsDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SUB_ASSETS_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), SubAssetsResp.class);
    }

    public RentalValueResp getRentalValue(RentalValueGetReq rentalValueGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(rentalValueGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_RENTALVALUE);
        return AppUtils.fromJson(strResponse.getBody(), RentalValueResp.class);

    }

    public RentalValueResp saveRentalValue(RentalValueSaveReq rentalValueSaveReq) {
        ResponseEntity<String> strResponse = null;        
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(rentalValueSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_RENTALVALUE);
        return AppUtils.fromJson(strResponse.getBody(), RentalValueResp.class);
    }

    public RentalValueResp deactivateRentalValue(RentalValueDeactiveReq rentalValueDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(rentalValueDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.RENATAL_VALUE_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), RentalValueResp.class);
    }

    public SalesRecordsResp getSalesRecords(SalesRecordsGetReq salesRecordsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(salesRecordsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_SALESRECORD);
        return AppUtils.fromJson(strResponse.getBody(), SalesRecordsResp.class);
    }

    public SalesRecordsResp saveSalesRecords(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_SALESRECORD);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), SalesRecordsResp.class);
    }

    public SalesRecordsResp deactivateSalesRecords(SalesRecordsDeactiveReq salesRecordsDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(salesRecordsDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SALES_RECORD_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), SalesRecordsResp.class);
    }

    public ResponseEntity<ByteArrayResource> getAssetRecordFileInfo(Long saleRecordId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("saleRecordId", saleRecordId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(
                getRegisterExchangeUrl(
                        RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SALES_RECORD_DOC_DOWNLOAD_FILE),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        // code to download the file
        /*String fileBasePath = "E://pavani/Downloads/Resources/Immovable Assets/Asset Life Status/121/418/ss.png";
        String type = "";
        Path fileName = null;
	    
        byte[] fileBytes = null;
        try
        {
        	Path path = Paths.get(fileBasePath);
    	    fileBytes = Files.readAllBytes(path);
    	    //System.out.println("File size in bytes");
    	    //System.out.println(fileBytes);
    	    type = Files.probeContentType(path);
            System.out.println("response body from getAssetRecordFileInfo function of MWFixedAssetsServiceImpl file:");
            fileName = path.getFileName();             
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName.toString() + "\"")
                .body(new ByteArrayResource(fileBytes));*/
        return response;  
    }
    
    public ResponseEntity<ByteArrayResource> getAssetRepairRecordFileInfo(Long repairRecordId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("repairRecordId", repairRecordId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(
                getRegisterExchangeUrl(
                        RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.FIXED_ASSETS_REPAIRS_DOWNLOAD_FILE),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public ResponseEntity<ByteArrayResource> getAssetPurchaseRecordFileInfo(Long purchaseRecordId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("purchaseRecordId", purchaseRecordId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.FIXED_ASSETS_PURACHASE_ACQULISITION_DOWNLOAD_FILE), paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public ResponseEntity<ByteArrayResource> assetLifeStatusDocDownloadFile(Long assetLifeStatusId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("assetLifeStatusId", assetLifeStatusId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.ASSET_LIFE_STATUS_DOC_DOWNLOAD_FILE),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public MortgagePaymentResp getMortgageePayments(MortgageePaymentsGetReq mortgageePaymentsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(mortgageePaymentsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_MORTGAGEE_PAYMENTS);
        return AppUtils.fromJson(strResponse.getBody(), MortgagePaymentResp.class);
    }

    public MortgagePaymentResp saveMortgageePayments(MortgageePaymentsSaveReq mortgageePaymentsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(mortgageePaymentsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_MORTGAGEE_PAYMENTS);
        return AppUtils.fromJson(strResponse.getBody(), MortgagePaymentResp.class);
    }

    public MortgagePaymentResp deactivateMortgageePayments(MortgageePaymentsDeactiveReq mortgageePaymentsDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(mortgageePaymentsDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.MORTGAGEE_PAYMENTS_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), MortgagePaymentResp.class);
    }

    public RevenueSalesResp getRevenueSales(RevenueSalesGetReq revenueSalesGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(revenueSalesGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_REVENUE_SALES);
        return AppUtils.fromJson(strResponse.getBody(), RevenueSalesResp.class);
    }

    public RevenueSalesResp saveRevenueSales(RevenueSalesSaveReq revenueSalesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(revenueSalesSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_REVENUE_SALES);
        return AppUtils.fromJson(strResponse.getBody(), RevenueSalesResp.class);
    }

    public RevenueSalesResp deactivateRevenueSales(RevenueSalesDeactiveReq revenueSalesDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(revenueSalesDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.REVENUE_SALES_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), RevenueSalesResp.class);
    }

    public LongTermLeasingResp getLongTermLeasing(LongTermLeasingGetReq longTermLeasingGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeasingGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_LONG_TERM_LEASING);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeasingResp.class);
    }

    
    public LongTermLeasingResp getLongtermleaselastrecord(LongTermLeasingGetReq longTermLeasingGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeasingGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_LONG_TERM_LEASING_LAST_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeasingResp.class);
    }
    
    public LongTermLeasingResp getLongtermleaseActiveAllRecord(LongTermLeasingGetReq longTermLeasingGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeasingGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_LONG_TERM_LEASING_ACTIVEALL_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeasingResp.class);
    }
    
    public LongTermLeasingResp saveLongTermLeasing(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_LONG_TERM_LEASING);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeasingResp.class);
    }

    public LongTermLeasingResp deactivateLongTermLeasing(LongTermLeasingDeactiveReq longTermLeasingDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeasingDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.LONG_TERM_LEASING_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeasingResp.class);
    }

    public STCORRReturnsResp saveStcorrReturns(MultipartFile file, STCORRReturnsSaveReq fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_STCORR_RETURNS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, AppUtils.toJson(fromJson));
        return AppUtils.fromJson(strResponse.getBody(), STCORRReturnsResp.class);

    }

    public LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetruns(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeaseActualRetrunsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_LONG_TERM_LEASE_ACTUAL_RETRUNS);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeaseActualRetrunsResp.class);
    }
    
    public LongTermLeaseActualRetrunsResp getLongTermLeaseActualRetrunsLastRecord(
            LongTermLeaseActualRetrunsGetReq longTermLeaseActualRetrunsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeaseActualRetrunsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_LONG_TERM_LEASE_ACTUAL_RETRUNS_LAST_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeaseActualRetrunsResp.class);
    }
    

    public LongTermLeaseActualRetrunsResp longTermLeaseActualRetrunsDelete(
            LongTermLeaseActualRetrunsDeleteReq longTermLeaseActualRetrunsDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeaseActualRetrunsDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeaseActualRetrunsResp.class);

    }

    public ResponseEntity<byte[]> getlongTermLeaseActualRetrunsDocDownloadFile(Long fileId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fileId", fileId);
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DOC_DOWNLOAD_FILE), paramsMap);
        return constructGETRestTemplate(url, byte[].class);

    }

    public CarParkingTollCollectionResp getCarParkingTollCollection(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(carParkingTollCollectionGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_CAR_PARKING_TOLL_COLLECTION);
        return AppUtils.fromJson(strResponse.getBody(), CarParkingTollCollectionResp.class);
    }

    //last record in method
    public CarParkingTollCollectionResp getCarParkingTollCollectionLastRecord(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(carParkingTollCollectionGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_CAR_PARKING_TOLL_COLLECTION_LAST_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), CarParkingTollCollectionResp.class);
    }
    
    
    //==========================
    public CarParkingTollCollectionResp saveCarParkingTollCollection(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_CAR_PARKING_TOLL_COLLECTION);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), CarParkingTollCollectionResp.class);
    }

    public CarParkingTollCollectionResp carParkingTollCollectionDelete(
            CarParkingTollCollectionDeleteReq carParkingTollCollectionDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(carParkingTollCollectionDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.CAR_PARKING_TOLL_COLLECTION_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), CarParkingTollCollectionResp.class);

    }

    public ResponseEntity<byte[]> carParkingTollCollectionDocDownloadFile(Long fileId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fileId", fileId);
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.CAR_PARKING_TOLL_COLLECTION_DOC_DOWNLOAD_FILE), paramsMap);
        return constructGETRestTemplate(url, byte[].class);

    }
    
    public TollCollectionResp getTollCollection(
            TollCollectionGetReq TollCollectionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(TollCollectionGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_TOLL_COLLECTION);
        return AppUtils.fromJson(strResponse.getBody(), TollCollectionResp.class);
    }

    //last record in method
    public TollCollectionResp getTollCollectionLastRecord(
            TollCollectionGetReq TollCollectionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(TollCollectionGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_TOLL_COLLECTION_LAST_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), TollCollectionResp.class);
    }
    
    
    //==========================
    public TollCollectionResp saveTollCollection(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_TOLL_COLLECTION);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), TollCollectionResp.class);
    }

    public TollCollectionResp TollCollectionDelete(
            TollCollectionDeleteReq TollCollectionDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(TollCollectionDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.TOLL_COLLECTION_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), TollCollectionResp.class);

    }

    public ResponseEntity<byte[]> TollCollectionDocDownloadFile(Long fileId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fileId", fileId);
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.TOLL_COLLECTION_DOC_DOWNLOAD_FILE), paramsMap);
        return constructGETRestTemplate(url, byte[].class);

    }

    public OccupancyUtilisationRecordsResp getOccupancyUtilisationRecords(
            OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(occupancyUtilisationRecordsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_OCCUPANCY_UTILISATION_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), OccupancyUtilisationRecordsResp.class);
    }

    public OccupancyUtilisationRecordsResp saveOccupancyUtilisationRecords(
            OccupancyUtilisationRecordsSaveReq occupancyUtilisationRecordsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(occupancyUtilisationRecordsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_OCCUPANCY_UTILISATION_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), OccupancyUtilisationRecordsResp.class);
    }

    public OccupancyUtilisationRecordsResp occupancyUtilisationRecordsDelete(
            OccupancyUtilisationRecordsDeleteReq occupancyUtilisationRecordsDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(occupancyUtilisationRecordsDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.OCCUPANCY_UTILISATION_RECORDS_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), OccupancyUtilisationRecordsResp.class);
    }

    public PeriodicalScheduleMaintenanceResp getPeriodicalScheduleMaintenance(
            PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(periodicalScheduleMaintenanceGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_PERIODICAL_SCHEDULE_MAINTENANCE);
        return AppUtils.fromJson(strResponse.getBody(), PeriodicalScheduleMaintenanceResp.class);
    }

    public PeriodicalScheduleMaintenanceResp savePeriodicalScheduleMaintenance(MultipartFile planFile,
            MultipartFile actualFile, PeriodicalScheduleMaintenanceSaveReq fromJson) {
        ResponseEntity<String> strResponse = null;
        
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_PERIODICAL_SCHEDULE_MAINTENANCE);
        strResponse = constructPOSTRestTemplateWithMultipleMultiPartFiles(url, planFile, actualFile,
                AppUtils.toJson(fromJson));
        return AppUtils.fromJson(strResponse.getBody(), PeriodicalScheduleMaintenanceResp.class);
    }

    public PeriodicalScheduleMaintenanceResp periodicalScheduleMaintenanceDelete(
            PeriodicalScheduleMaintenanceDeleteReq periodicalScheduleMaintenanceDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(periodicalScheduleMaintenanceDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.PERIODICAL_SCHEDULE_MAINTENANCE_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), PeriodicalScheduleMaintenanceResp.class);
    }

    public ResponseEntity<byte[]> periodicalScheduleMaintenanceDocDownloadFile(Long fileId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fileId", fileId);
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.PERIODICAL_SCHEDULE_MAINTENANCE_DOC_DOWNLOAD_FILE), paramsMap);
        return constructGETRestTemplate(url, byte[].class);
    }

    public ResponseEntity<byte[]> periodicalScheduleCompletionDocDownloadFile(Long fileId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fileId", fileId);
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.PERIODICAL_SCHEDULE_COMPLETION_DOC_DOWNLOAD_FILE), paramsMap);
        return constructGETRestTemplate(url, byte[].class);
    }

    public AssetLifeStatusResp getAssetLifeStatus(AssetLifeStatusGetReq assetLifeStatusGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetLifeStatusGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ASSET_LIFE_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), AssetLifeStatusResp.class);
    }

    public AssetLifeStatusResp saveAssetLifeStatus(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_ASSET_LIFE_STATUS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), AssetLifeStatusResp.class);
    }

    public AssetLifeStatusResp deactivateAssetLifeStatus(AssetLifeStatusDeactiveReq assetLifeStatusDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetLifeStatusDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.ASSET_LIFE_STATUS_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), AssetLifeStatusResp.class);
    }

    public AssetLifeStatusResp assetLifeStatusDelete(AssetLifeStatusDeleteReq assetLifeStatusDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetLifeStatusDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.ASSET_LIFE_STATUS_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), AssetLifeStatusResp.class);
    }

    public AssetCostValueStatusResp getAssetCostValueStatus(AssetCostValueStatusGetReq assetCostValueStatusGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetCostValueStatusGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ASSET_COST_VALUE_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), AssetCostValueStatusResp.class);   
    }
    
    public AssetCostValueStatusResp saveAssetCostValueStatus(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_ASSET_COST_VALUE_STATUS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), AssetCostValueStatusResp.class);      
       
    }

    public AssetCostValueStatusResp assetCostValueStatusDelete(
            AssetCostValueStatusDeleteReq assetCostValueStatusDeleteReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetCostValueStatusDeleteReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.ASSET_COST_VALUE_STATUS_DELETE);
        return AppUtils.fromJson(strResponse.getBody(), AssetCostValueStatusResp.class);
    }
    
    public AssetCostValueStatusResp deactivateAssetCostValueStatus(AssetCostValueStatusDeactiveReq assetCostValueStatusDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(assetCostValueStatusDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.ASSET_COST_VALUE_STATUS_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), AssetCostValueStatusResp.class);
    }

    public ResponseEntity<ByteArrayResource> assetCostValueStatusDocDownloadFile(Long assetCostValueStatusId) {
        
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("assetCostValueStatusId", assetCostValueStatusId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(
                getRegisterExchangeUrl(
                        RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.ASSET_COST_VALUE_STATUS_DOC_DOWNLOAD_FILE),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;       
    }

    public SubAssetsResp getAssetById(SubAssetsGetReq subAssetsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(subAssetsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ASSET_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), SubAssetsResp.class);
    }

    public FixedAssetsRegisterOnLoadResp getAssetOnly(FixedAssetsGetReq fixedAssetsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ASSET_ONLY);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetsRegisterOnLoadResp.class);
    }

    public FixedAssetsRegisterOnLoadResp getAssetSubsById(FixedAssetsGetReq fixedAssetsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_ASSET_SUB_BY_ID);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetsRegisterOnLoadResp.class);
    }

    public FixedAssetsRegisterOnLoadResp saveSubAsset(FixedAssetsSaveReq fixedAssetsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(fixedAssetsSaveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_SUB_ASSET);
        return AppUtils.fromJson(strResponse.getBody(), FixedAssetsRegisterOnLoadResp.class);
    }

    public LongTermLeaseActualRetrunsResp saveLongTermLeaseActualRetruns(MultipartFile file, String fromJson) {
        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_LONG_TERM_LEASE_ACTUAL_RETRUNS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeaseActualRetrunsResp.class);
    }

    public ResponseEntity<ByteArrayResource> getLongTermLeaseActualRetrunsFileInfo(Long longTermLeaseActualId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("longTermLeaseActualId", longTermLeaseActualId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(getRegisterExchangeUrl(RegisterURLConstants.REGISTER_PARH_URL
                + RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DOC_DOWNLOAD_FILE), paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public LongTermLeaseActualRetrunsResp deactivateLongTermLeaseActualRetruns(
            LongTermLeaseActualRetrunsDeactiveReq longTermLeaseActualRetrunsDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(longTermLeaseActualRetrunsDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL
                        + RegisterURLConstants.LONG_TERM_LEASE_ACTUAL_RETRUNS_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), LongTermLeaseActualRetrunsResp.class);
    }

    public ResponseEntity<ByteArrayResource> getSTCORRReturnsFileInfo(Long shortTermRecordId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("shortTermRecordId", shortTermRecordId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(
                getRegisterExchangeUrl(
                        RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.STCORR_RETURNS_DOC_DOWNLOAD_FILE),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public STCORRReturnsResp deactivateSTCORRReturns(STCORRReturnsDeactiveReq stcorrReturnsDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(stcorrReturnsDeactiveReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.STCORR_RETURNS_DEACTIVATES);
        return AppUtils.fromJson(strResponse.getBody(), STCORRReturnsResp.class);
    }

    public STCORRReturnsResp getStcorrReturns(STCORRReturnsGetReq stcorrReturnsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(stcorrReturnsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_STCORR_RETURNS);
        return AppUtils.fromJson(strResponse.getBody(), STCORRReturnsResp.class);
    }

    public STCORRReturnsResp saveStcorrReturns(MultipartFile file, String fromJson) {

        ResponseEntity<String> strResponse = null;
        String url = getRegisterExchangeUrl(
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.SAVE_STCORR_RETURNS);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, fromJson);
        return AppUtils.fromJson(strResponse.getBody(), STCORRReturnsResp.class);
    }
    
    public SubSequentRentalRecepitsResp getStcorrSubSequentRentalReceipts(STCORRReturnsGetReq stcorrReturnsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(stcorrReturnsGetReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.GET_STCORR_SUBSEQUENT_RENTAL_RECEIPTS);
        return AppUtils.fromJson(strResponse.getBody(), SubSequentRentalRecepitsResp.class);
    }

    public STCORRReturnsResp getStcorrReturnsSearch(STCORRReturnsFilterReq stcorrReturnsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getRegistersPOSTRestTemplate(AppUtils.toJson(stcorrReturnsFilterReq),
                RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.STCORR_RETURNS_SEARCH);
        return AppUtils.fromJson(strResponse.getBody(), STCORRReturnsResp.class);
    }
}
