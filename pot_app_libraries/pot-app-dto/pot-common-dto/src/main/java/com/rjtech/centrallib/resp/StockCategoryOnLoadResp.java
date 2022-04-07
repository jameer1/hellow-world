package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;


public class StockCategoryOnLoadResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<String> categorys = new ArrayList<String>(5);

    public List<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<String> categorys) {
        this.categorys = categorys;
    }

}
