'use strict';

app.provider('appUrl', ["POTURL", function (POTURL) {
    	var urls={appurl:'', originurl:''};
   
    		this.getAppURL = function(){   
    			
				var appurl;
				
					
				if (POTURL.IS_PROD_STAGE_URL){
					appurl = POTURL.API_PROD_STAGE_URL;
				}
				
				urls.appurl = appurl;
				urls.originurl = POTURL.ORIGIN_URL;
				
				console.log('  from app url provider urls '+urls.appurl);
				
    		};
    		this.$get = function() {
    	        return urls;
    	    };
    		
    
    	

    }]);
