package com.rjtech.register.fixedassets.req;



	import java.util.ArrayList;
	import java.util.List;

	import com.rjtech.constants.ApplicationConstants;
	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;

	public class TollCollectionDeleteReq extends TollCollectionDtlTO {

	    private static final long serialVersionUID = -4543819922890869538L;
	    List<Long> TollCollectionIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	    
		public List<Long> getTollCollectionIds() {
			return TollCollectionIds;
		}
		public void setTollCollectionIds(List<Long> tollCollectionIds) {
			this.TollCollectionIds = tollCollectionIds;
		}

	   
}
