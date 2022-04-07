package com.rjtech.register.fixedassets.req;



	import java.util.ArrayList;
	import java.util.List;

	import com.rjtech.constants.ApplicationConstants;
	import com.rjtech.register.fixedassets.dto.TollCollectionDtlTO;

	public class TollCollectionSaveReq extends TollCollectionDtlTO {
	    private static final long serialVersionUID = 2740448397989044793L;
	    private String folderCategory;
	    private Long userId;
	    
	    private List<TollCollectionDtlTO> tollCollectionDtlTO = new ArrayList<TollCollectionDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

		public List<TollCollectionDtlTO> getTollCollectionDtlTO() {
			return tollCollectionDtlTO;
		}

		public void setTollCollectionDtlTO(List<TollCollectionDtlTO> tollCollectionDtlTO) {
			this.tollCollectionDtlTO = tollCollectionDtlTO;
		}

		public void setFolderCategory( String folderCategory ) {
	    	this.folderCategory = folderCategory;
	    }

	    public String getFolderCategory() {
	    	return folderCategory;
	    }
	    
	    public void setUserId( Long userId ) {
	    	this.userId = userId;
	    }

	    public Long getUserId() {
	    	return userId;
	    }
					    
}
