angular.module('potApp').directive('potCurrentyearPicker', ["$filter", "$timeout", "$parse", function($filter, $timeout, $parse) {
	 return {
	        restrict: 'E',
	        replace: true,
	        scope: {
	            dirIndex: '=',
	            dateName: '@',
	            minDate: '=',
	            maxDate: '='
	        },
	        require: 'ngModel',
	        template: "<input type='text' >",
	        link: function (scope, element, attrs, ngModel) {
	            attrs.id = scope.dirIndex + scope.dateName;
	            var currDate = new Date();
	            var currYear = currDate.getFullYear();
	            var futureYear = currYear + 5;
	            var yearRange = currYear - 80 + ':' + futureYear;
	            $(element).datepicker({
	            	dateFormat: 'yy',
	                changeYear: true,
	                showButtonPanel: true,
	                beforeShow: function () {
	                    $timeout(function () {
	                        hideUnwanted();
	                        $('.ui-datepicker-close').click(function () {
	                            var dateText = getValue();
	                            $(element).val(dateText);
	                            scope.$apply(function() {
	                                ngModel.$setViewValue(dateText);
	                            });
	                        });
	                    });

	                },onChangeMonthYear : function() {
	                    var dateText = getValue();
	                    $(element).val(dateText);
	                    scope.$apply(function() {
	                        ngModel.$setViewValue(dateText);
	                    });
	                    $timeout(function () {
	                        hideUnwanted();
	                    });
	                }, onClose: function () {
	                    $('.ui-datepicker-close').unbind();
	                }
	            });

	            $(element).focus(function () {
	                hideUnwanted();
	            });

	            function getValue() {
	                return $.datepicker.formatDate('yy', new Date($("#ui-datepicker-div .ui-datepicker-year :selected").val(), 1));
	            }

	            function hideUnwanted() {
	            	$(".ui-datepicker-month").hide();
	                $(".ui-datepicker-calendar").hide();
	                $(".ui-datepicker-prev").hide();
	                $(".ui-datepicker-next").hide();
	                $(".ui-datepicker-current").hide();
	                $("#ui-datepicker-div").position({
	                    my: "center top",
	                    at: "center bottom",
	                    of: $(this)
	                });
	            }

	            // set minDate and maxDate, if they are provied.
				/*
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
				*/
	        }

	    };
	}]);