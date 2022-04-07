angular.module('potApp').directive('potFullyearPicker', ["$filter", "$timeout", "$parse", function($filter, $timeout, $parse) {
	return {
		restrict : 'E',
		replace : true,
		scope : {
			selectedDate : '=',
			dirIndex : '=',
			dateName : '@'
		},
		template : "<input type='text' >",
		link : function(scope, element, attrs) {
			attrs.id = scope.dirIndex + scope.dateName;
			var currDate = new Date();
			$(element).datepicker({
				dateFormat : "dd-M-yy",
				changeYear : true,
				maxDate: new Date(),
				onSelect : function(dateText) {
					scope.$apply(function() {
						scope.selectedDate = dateText;
					});
				}
			});
			$(element).datepicker("setDate",scope.selectedDate);
		}

	};
}]);
