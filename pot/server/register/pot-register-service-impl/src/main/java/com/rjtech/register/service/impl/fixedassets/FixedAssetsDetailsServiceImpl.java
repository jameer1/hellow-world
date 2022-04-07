package com.rjtech.register.service.impl.fixedassets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.req.FixedAssetRegDeactivateReq;
import com.rjtech.register.fixedassets.req.FixedAssetsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.FixedAssetsRegisterOnLoadResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.service.fixedassets.FixedAssetsDetailsService;
import com.rjtech.register.service.handler.fixedassets.FixedAssetsRegisterDtlHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "fixedAssetsDetailsService")
@RJSService(modulecode = "fixedAssetsDetailsService")
@Transactional
public class FixedAssetsDetailsServiceImpl implements FixedAssetsDetailsService {
	private static final Logger log = LoggerFactory.getLogger(FixedAssetsDetailsServiceImpl.class);

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;
    
    @Autowired
    private EPSProjRepository epsProjRepository;
    
    @Autowired
    private CompanyRepository companyRepository;


    public FixedAssetsRegisterOnLoadResp getFixedAssetRegister(FixedAssetsGetReq fixedAssetsGetReq) {
    	
        FixedAssetsRegisterOnLoadResp assetsDtlResp = new FixedAssetsRegisterOnLoadResp();
        
        List<FixedAssetsRegisterDtlEntity> assetsRegisterDtlEntities = new ArrayList<FixedAssetsRegisterDtlEntity>();
       
        if (CommonUtil.objectNotNull(fixedAssetsGetReq)) {
        	        	
        	if(fixedAssetsGetReq.getCountryName()!=null) {
        		
        		if((fixedAssetsGetReq.getProfitCentre()!=null) &&(fixedAssetsGetReq.getProvisionName()!=null)) {
        			assetsRegisterDtlEntities = fixedAssetsRegisterRepository.findFixedAssetByClientcountryProfit(AppUserUtils.getClientId(), fixedAssetsGetReq.getCountryName(),
        					fixedAssetsGetReq.getProvisionName(), fixedAssetsGetReq.getProfitCentre(), fixedAssetsGetReq.getStatus());
               		}
        		else if(fixedAssetsGetReq.getProvisionName()!=null) {
            assetsRegisterDtlEntities = fixedAssetsRegisterRepository.findFixedAssetByClientcountry(AppUserUtils.getClientId(), fixedAssetsGetReq.getCountryName(), fixedAssetsGetReq.getProvisionName(),
                    fixedAssetsGetReq.getStatus());
           
        		}else {
        			 assetsRegisterDtlEntities = fixedAssetsRegisterRepository.findFixedAssetByClientcountryonly(AppUserUtils.getClientId(), fixedAssetsGetReq.getCountryName(),
        	                    fixedAssetsGetReq.getStatus());
        		}
        }else if(fixedAssetsGetReq.getProfitCentre()!=null) {
        	assetsRegisterDtlEntities = fixedAssetsRegisterRepository.findFixedAssetByClientProfit(AppUserUtils.getClientId(), fixedAssetsGetReq.getProfitCentre(), fixedAssetsGetReq.getStatus());
        }else  {
        	assetsRegisterDtlEntities = fixedAssetsRegisterRepository.findFixedAssetByClient(AppUserUtils.getClientId(),
                   fixedAssetsGetReq.getStatus());
        	
            
        }
        }
        for (FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity : assetsRegisterDtlEntities) {
            assetsDtlResp.getFixedAssetDtlTOs()
                    .add(FixedAssetsRegisterDtlHandler.populateProjectTO(fixedAssetsRegisterDtlEntity, true));
        }
              return assetsDtlResp;
    }

    public void fixedAssetRegistersDeactivate(FixedAssetRegDeactivateReq fixedAssetRegDeactivateReq) {
        fixedAssetsRegisterRepository.deactivateFixedAssetRegisters(fixedAssetRegDeactivateReq.getFixedAssetRegIds(),
                StatusCodes.DEACIVATE.getValue());
    }


    public FixedAssetsRegisterOnLoadResp getAssetSubById(FixedAssetsGetReq fixedAssetsGetReq) {
        List<FixedAssetsRegisterDtlEntity> projMstrEntities = fixedAssetsRegisterRepository.findAssetSubById(
                AppUserUtils.getClientId(), fixedAssetsGetReq.getFixedAssetid(), fixedAssetsGetReq.getStatus());
        FixedAssetsRegisterOnLoadResp projectResp = new FixedAssetsRegisterOnLoadResp();
        for (FixedAssetsRegisterDtlEntity projectMstrEntity : projMstrEntities) {
            projectResp.getFixedAssetDtlTOs()
                    .add(FixedAssetsRegisterDtlHandler.populateProjectTO(projectMstrEntity, true));
        }
        return projectResp;
    }

    public void saveSubAsset(FixedAssetsSaveReq fixedAssetsSaveReq) {
        List<FixedAssetsRegisterDtlEntity> subAssetEntities = FixedAssetsRegisterDtlHandler
                .populateEntitisFromPOJO(fixedAssetsSaveReq, fixedAssetsRegisterRepository,epsProjRepository,companyRepository);
        fixedAssetsRegisterRepository.save(subAssetEntities);
    }

}
