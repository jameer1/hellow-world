app.filter('potstatusfilter', function() {

	return function(status) {

		// Ensure that the passed in data is a number
		if (isNaN(status) || status < 1) {

			// If the data is not a number or is less than one (thus not having a cardinal value) return it unmodified.
			return status;

		} else {

			if (status === 1) {
				return 'Active';
			} else if (status === 2) {
				return 'Inactive';
			} else if (status === 3) {
				return 'Delete';
			}

		}
	}
});