angular.module('potApp').directive('customReadOnly', function () {
	const allowedKeyCodes = [116, 9];
	return {
		restrict: 'A',
		link: function (scope, element) {
			element.bind('keydown', function (e) {
				if (e && allowedKeyCodes.indexOf(e.keyCode) === -1)
					e.preventDefault();
			});
		}

	};
});
