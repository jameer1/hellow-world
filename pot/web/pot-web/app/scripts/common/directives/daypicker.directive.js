angular.module('potApp').directive('dayPicker', ["$filter", "$timeout", function($filter, $timeout) {
	return {
		restrict : 'E',
		replace : true,
		scope : {
			weekCommenceDay : '=',
			commenceStartDay : '='
		},
		template : "<input type='text' autocomplete='off'> " ,
		link : function(scope, element, attrs, ngModelCtrl) {
			var currDate = new Date();
			var currYear = currDate.getFullYear();
			var futureYear=currYear+10;
			var yearRange=currYear-50+':'+futureYear;
			$(element).datepicker({
				dateFormat : "D dd-M-yy",
				yearRange: ''+yearRange+'', 
				changeYear : true,
				maxDate: new Date(),
				beforeShowDay : function(dt) {
					return [ dt.getDay() == scope.weekCommenceDay ? true : false ];
				},
				onSelect : function(dateText) {
					scope.$apply(function() {
						scope.commenceStartDay = dateText;
					});
				}
				
			});

		}

	};
}]);
