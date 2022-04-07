
package com.rjtech.register.service.impl.material;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.repository.material.MaterialConsumptionProcRepository;
import com.rjtech.register.repository.material.MaterialDockSchItemRepository;
import com.rjtech.register.service.material.MaterialDeliveryDocketSevice;
import com.rjtech.register.service.material.MaterialProjConsumptionSevice;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "materialProjConsumptionSevice")
@RJSService(modulecode = "materialProjConsumptionSevice")
@Transactional
public class MaterialProjConsumptionSeviceImpl implements MaterialProjConsumptionSevice {

    @Autowired
    private MaterialConsumptionProcRepository materialConsumptionProcRepository;

    @Autowired
    private MaterialDockSchItemRepository materialDockSchItemRepository;

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private MaterialDeliveryDocketSevice materialDeliveryDocketSevice;

    public List<MaterialLedgerResTo> getMaterialStoreTransitConsumption(MaterialFilterReq materialFilterReq) {
        return materialDeliveryDocketSevice.getInTransitDetails(materialFilterReq);
    }

    public List<MaterialLedgerResTo> getMaterialStockPiledConsumption(MaterialFilterReq materialFilterReq) {
        return materialDeliveryDocketSevice.getStockPiledDetails(materialFilterReq);
    }

    public LabelKeyTOResp getMaterialDailyIssueSchItems(MaterialFilterReq materialFilterReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(getMaterials(materialFilterReq));
        return labelKeyTOResp;
    }

    private List<LabelKeyTO> getMaterials(MaterialFilterReq materialFilterReq) {
        Date fromDate = null;
        Date toDate = null;
        if (CommonUtil.isNotBlankStr(materialFilterReq.getFromDate())
                && CommonUtil.isNotBlankStr(materialFilterReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(materialFilterReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(materialFilterReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        List<Long> projIds = null;
        if (CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            projIds = materialFilterReq.getProjList();
        } else {
            projIds = epsProjService.getUserProjIds();
        }
        List<Object[]> dailyIssueSchItems = materialDockSchItemRepository.getMaterialDailyIssueSchItems(projIds,
                fromDate, toDate);
        List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
        dailyIssueSchItems.forEach((dailyIssueSchItem) -> {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) dailyIssueSchItem[0]);
            Map<String, String> displayMap = new HashMap<String, String>();
            Long projId = (Long) dailyIssueSchItem[3];
            displayMap.put(RegisterConstants.PROJ_ID, projId.toString());
            displayMap.put(RegisterConstants.TO_PROJ_ID, ((Long) dailyIssueSchItem[4]).toString());
            displayMap.put(RegisterConstants.FROM_LOCATION, (String) dailyIssueSchItem[5]);
            displayMap.put(RegisterConstants.TO_LOCATION, (String) dailyIssueSchItem[6]);
            displayMap.put(RegisterConstants.ISSUE_EMP_CODE, (String) dailyIssueSchItem[7]);
            displayMap.put(RegisterConstants.ISSUE_EMP_NAME, (String) dailyIssueSchItem[8]);
            displayMap.put(RegisterConstants.RECIEVE_EMP_CODE, (String) dailyIssueSchItem[9]);
            displayMap.put(RegisterConstants.RECIEVE_EMP_NAME, (String) dailyIssueSchItem[10]);
            displayMap.put(RegisterConstants.CLASS_TYPE, ((Long) dailyIssueSchItem[11]).toString());
            displayMap.put(RegisterConstants.CMP_ID, ((Long) dailyIssueSchItem[12]).toString());
            displayMap.put(RegisterConstants.DELIVERY_DOCKET_NO,
                    ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + (String) dailyIssueSchItem[26] + "-"
                            + "SCH" + "-" + "PD" + "-" + (String) dailyIssueSchItem[27] + "-"
                            + (Long) dailyIssueSchItem[13]);
            displayMap.put(RegisterConstants.DELIVERY_DOCKET_DATE,
                    CommonUtil.convertDateToString(((Date) dailyIssueSchItem[14])));
            displayMap.put(RegisterConstants.UNIT_OF_RATE, ((BigDecimal) dailyIssueSchItem[15]).toString());
            displayMap.put(RegisterConstants.ISSUED_QTY, ((BigDecimal) dailyIssueSchItem[16]).toString());
            displayMap.put(RegisterConstants.CONSUMPTION_DATE,
                    CommonUtil.convertDateToString(((Date) dailyIssueSchItem[17])));
            displayMap.put(RegisterConstants.NOTIFICATION_CODE, (String) dailyIssueSchItem[18]);
            displayMap.put(RegisterConstants.COMMENTS, (String) dailyIssueSchItem[19]);
            displayMap.put("className", (String) dailyIssueSchItem[20]);
            displayMap.put("classCode", (String) dailyIssueSchItem[21]);
            displayMap.put("fromProjName", (String) dailyIssueSchItem[22]);
            displayMap.put("toProjName", (String) dailyIssueSchItem[23]);
            displayMap.put("cmpName", (String) dailyIssueSchItem[24]);
            displayMap.put("unitOfMeasure", (String) dailyIssueSchItem[28]);
            displayMap.put("parentProjName",
                    epsProjRepository.findOne(projId).getParentProjectMstrEntity().getProjName());
            BigDecimal openQty = ((BigDecimal) dailyIssueSchItem[29]);
            displayMap.put(RegisterConstants.OPEN_QTY, (openQty == null) ? "" : openQty.toString());
            labelKeyTO.setDisplayNamesMap(displayMap);
            labelKeyTOs.add(labelKeyTO);
        });
        return labelKeyTOs;
    }

    public LabelKeyTOResp getMaterialProjLedgers(MaterialFilterReq materialFilterReq) {
        Date fromDate = null;
        Date toDate = null;
        if (CommonUtil.isNotBlankStr(materialFilterReq.getFromDate())
                && CommonUtil.isNotBlankStr(materialFilterReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(materialFilterReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(materialFilterReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        List<Long> projIds = null;
        if (CommonUtil.isListHasData(materialFilterReq.getProjList())) {
            projIds = materialFilterReq.getProjList();
        } else {
            projIds = epsProjService.getUserProjIds();
        }

        List<LabelKeyTO> labelKeyTOs = materialConsumptionProcRepository.getMaterialProjLedgers(projIds, fromDate,
                toDate);
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    private LabelKeyTOResp populateCummulativeQty(List<LabelKeyTO> labelKeyTOs) {
        Date consumptionDate = null;
        Date consumptionNextDate = null;
        int count = 0;
        double totalQty = 0;
        double qty = 0;
        double docketTotal = 0;
        Long docketCountId = null;
        for (LabelKeyTO labelKeyTO : labelKeyTOs) {
            qty = 0;
            consumptionNextDate = null;
            if (count > 0) {

                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE))) {
                    consumptionNextDate = CommonUtil
                            .getYYYYMMDDFormat(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE));
                }
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                    qty = Double.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                }
                if (consumptionDate.before(consumptionNextDate)) {
                    totalQty = totalQty + qty;
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                            String.valueOf(totalQty));
                    consumptionDate = consumptionNextDate;
                } else {
                    totalQty = 0;
                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE))) {
                        consumptionDate = CommonUtil.getYYYYMMDDFormat(
                                labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE));
                    }
                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                        totalQty = Double
                                .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                    }
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY,
                            String.valueOf(totalQty));
                }
            } else {
                totalQty = 0;
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE))) {
                    consumptionDate = CommonUtil
                            .getYYYYMMDDFormat(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_DATE));
                }
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                    totalQty = Double.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                }
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.SCH_CUMMULATIVE_QTY, String.valueOf(totalQty));
            }
            if (count > 0) {
                if (docketCountId != null && docketCountId.equals(
                        Long.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID)))) {
                    docketTotal = docketTotal
                            + Double.valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.DOCKET_CUMMULATIVE_QTY,
                            String.valueOf(docketTotal));
                } else {
                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID))) {
                        docketCountId = Long
                                .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID));
                    }

                    if (CommonUtil
                            .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                        docketTotal = Double
                                .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                    }
                    labelKeyTO.getDisplayNamesMap().put(RegisterConstants.DOCKET_CUMMULATIVE_QTY,
                            String.valueOf(docketTotal));
                }
            } else {
                if (CommonUtil
                        .isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID))) {
                    docketCountId = Long
                            .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.DELIVERY_DOCKET_ID));
                }
                if (CommonUtil.isNotBlankStr(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY))) {
                    docketTotal = Double
                            .valueOf(labelKeyTO.getDisplayNamesMap().get(RegisterConstants.CONSUMPTION_QTY));
                }
                labelKeyTO.getDisplayNamesMap().put(RegisterConstants.DOCKET_CUMMULATIVE_QTY,
                        String.valueOf(docketTotal));
            }
            count++;

        }

        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

}
