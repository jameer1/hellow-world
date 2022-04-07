app.filter('openclosefilter', function() {
	return function(status) {
		if (isNaN(status) || status < 1) {
			return status;
		} else {
			if (status === 1) {
				return 'Open';
			} else if (status === 2) {
				return 'Closed';
			} else if (status === 3) {
				return status;
			}

		}
	}
});