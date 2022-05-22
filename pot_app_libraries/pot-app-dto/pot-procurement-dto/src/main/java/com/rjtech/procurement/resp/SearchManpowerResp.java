package com.rjtech.procurement.resp;

import java.util.List;

import com.rjtech.procurement.dto.SearchManpowerDocketTO;

public class SearchManpowerResp {
	
	private List<SearchManpowerDocketTO> searchManpowerTOs;

	public List<SearchManpowerDocketTO> getSearchManpowerTOs() {
		return searchManpowerTOs;
	}

	public void setSearchManpowerTOs(List<SearchManpowerDocketTO> searchManpowerTOs) {
		this.searchManpowerTOs = searchManpowerTOs;
	}
	
	

}
