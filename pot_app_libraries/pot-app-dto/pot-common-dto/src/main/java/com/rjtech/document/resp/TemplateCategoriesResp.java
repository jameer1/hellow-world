package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.document.dto.TemplateCategoriesTO;

public class TemplateCategoriesResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<TemplateCategoriesTO> templateCategoriesTOs = new ArrayList<TemplateCategoriesTO>();

    public List<TemplateCategoriesTO> getTemplateCategoriesTOs() {
    	/*System.out.println("This is TemplateCategoriesResp class");
    	for(TemplateCategoriesTO item : templateCategoriesTOs)
    	{
    		System.out.println(item.getCategoryId());
        	System.out.println(item.getCategoryName());
        	System.out.println(item.getCategoryType());
    	} */   	
        return templateCategoriesTOs;
    }

    public void setTemplateCategoriesTOs(List<TemplateCategoriesTO> templateCategoriesTOs) {
        this.templateCategoriesTOs = templateCategoriesTOs;
    }

}