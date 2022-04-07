angular.module('potApp').directive('potdatePicker', ["$filter", "$timeout", "$parse", function ($filter, $timeout, $parse) {
	return {
		restrict: 'E',
		replace: true,
		scope: {
			dirIndex: '=',
			dateName: '@',
			minDate: '=',
			maxDate: '=',
			preventEvents: '=',
			disableDates: '='
		},
		require: 'ngModel',
		template: "<input type='text' autocomplete='off' >",
		link: function (scope, element, attrs, ngModel) {
			attrs.id = scope.dirIndex + scope.dateName;
			var currDate = new Date();
			var currYear = currDate.getFullYear();
			var futureYear = currYear + 5;
			var yearRange = currYear - 80 + ':' + futureYear;

			if (scope.preventEvents) {
				element.bind('keydown', function (e) {
					e.preventDefault();
				});
			}

			$(element).datepicker({
				dateFormat: "dd-M-yy",
				yearRange: '' + yearRange + '',
				changeYear: true,
				onSelect: function (dateText) {
					scope.$apply(function () {
						// The below code, setViewValue is to support ng-change
						ngModel.$setViewValue(dateText);
					});
				}
			});
			// set minDate and maxDate, if they are provied.
			scope.minDate && setMinDate(scope.minDate);
			scope.maxDate && setMaxDate(scope.maxDate);
			scope.disableDates && setDisableDate();

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
			
			function setDisableDate() {
				$(element).datepicker("option", "beforeShowDay", disableSelectiveDates);
			}
			
			function disableSelectiveDates(date) {
		        for (var index = 0; index < scope.disableDates.length; index++) {
		        	if (date >= Date.parse(scope.disableDates[index][0]) && date <= Date.parse(scope.disableDates[index][1]))
		        		return [false, "", "Unavailable"];
		        }
		        return [true, ""];
		    }
		}

	};
}]);
