package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;



public class CliamePeriodOnLoadResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<String> claimPeriod = new ArrayList<String>(5);

    public List<String> getClaimPeriod() {
        return claimPeriod;
    }

    public void setClaimPeriod(List<String> claimPeriod) {
        this.claimPeriod = claimPeriod;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
