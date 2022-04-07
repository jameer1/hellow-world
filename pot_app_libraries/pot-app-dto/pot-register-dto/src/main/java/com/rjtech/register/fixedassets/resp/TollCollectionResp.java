package com.rjtech.register.fixedassets.resp;

//public class TollCollectionResp {

	import java.util.ArrayList;
	import java.util.List;

	import com.rjtech.common.resp.AppResp;
	import com.rjtech.constants.ApplicationConstants;
	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;

	public class TollCollectionResp extends AppResp {

	    private static final long serialVersionUID = 1L;
	    private List<TollCollectionDtlTO> tollCollectionDtlTOs = new ArrayList<TollCollectionDtlTO>(
	            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	    
		public List<TollCollectionDtlTO> getTollCollectionDtlTOs() {
			return tollCollectionDtlTOs;
		}
		public void setTollCollectionDtlTOs(List<TollCollectionDtlTO> tollCollectionDtlTOs) {
			this.tollCollectionDtlTOs = tollCollectionDtlTOs;
		}

}
