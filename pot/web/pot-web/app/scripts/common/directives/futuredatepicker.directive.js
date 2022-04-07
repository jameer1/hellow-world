angular.module('potApp').directive('potFuturedatePicker', ["$filter", "$timeout", "$parse", function($filter, $timeout, $parse) {
	return {

		restrict : 'E',
		replace : true,
		scope : {
			dirIndex : '=',
			dateName : '@',
			minDate: '=',
			maxDate: '='
		},
		require: 'ngModel',
		template : "<input type='text' >",
		link: function (scope, element, attrs, ngModel) {
			attrs.id = scope.dirIndex + scope.dateName;
			var currDate = new Date();
			var currYear = currDate.getFullYear();
			var futureYear = currYear + 10;
			var yearRange = currYear - 10 + ':' + futureYear;
			$(element).datepicker({
				dateFormat : "dd-M-yy",
				yearRange : '' + yearRange + '',
				changeYear : true,
				minDate : new Date(),
				onSelect : function(dateText) {
					scope.$apply(function() {
						// The below code, setViewValue is to support ng-change
						ngModel.$setViewValue(dateText);
					});
				}
			});
			// set minDate and maxDate, if they are provied.
			scope.minDate && setMinDate(scope.minDate);
			scope.maxDate && setMaxDate(scope.maxDate);

			scope.$watch('minDate', function (value) {
				setMinDate(value);
			});

			scope.$watch('maxDate', function (value) {
				setMaxDate(value);
			});

			function setMinDate(value) {
				$(element).datepicker("option", "minDate", scope.minDate);
			}

			function setMaxDate(value) {
				$(element).datepicker("option", "maxDate", scope.maxDate);
			}
		}

	};
}]);
