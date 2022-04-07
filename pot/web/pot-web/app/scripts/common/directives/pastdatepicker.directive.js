angular.module('potApp').directive('pastdatePicker', ["$filter", "$timeout", "$parse", function($filter, $timeout, $parse) {
	return {
		restrict : 'E',
		replace : true,
		scope : {
			dirIndex : '=',
			dateName : '@'
		},
		require: 'ngModel',
		template : "<input type='text' >",
		link : function(scope, element, attrs, ngModel) {
			
			attrs.id = scope.dirIndex + scope.dateName;
			var currDate = new Date();
			var currYear = currDate.getFullYear();
			var futureYear = currYear ;
			var yearRange = currYear - 37 + ':' + futureYear;
			
			$(element).datepicker({
				dateFormat : "dd-M-yy",
				yearRange : '' + yearRange + '',
				changeYear : true,
				onSelect : function(dateText) {
					scope.$apply(function () {
						// The below code, setViewValue is to support ng-change
						ngModel.$setViewValue(dateText);
					});
				}
			});
		}

	};
}]);
