app.filter('yesnofilter', function() {
	return function(status) {
		if (typeof status === "boolean"){
			if (status === true)
				return 'Yes';
			else
				return 'No';
		} else {
			// Ensure that the passed in data is a number
			if (isNaN(status) || status < 0 || status > 1) {
				// If the data is not a number or is less than one (thus not having a cardinal value) return it unmodified.
				return status;
			} else {
				if (status === 0) {
					return 'No';
				} else if (status === 1) {
					return 'Yes';
				}
			}
		}
	}
});