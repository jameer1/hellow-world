'use strict';

// this value will not be available during npm start
// uncomment below line for npm start
// var ORIGIN_URL = 'http://localhost:8080';

app.constant('POTURL', {
	// WildFly Server
	'API_PROD_STAGE_URL' : ORIGIN_URL + '/app/',

	// NodeJS Server
	'ORIGIN_URL' : ORIGIN_URL,

	'IS_PROD_STAGE_URL' : true
});