angular.module('potApp').directive('dashboardsFooter', function () {
	return {
        restrict: 'E',
        replace: true,
        template: 
            '<div class="text-center" style="padding-top: 10px;">'+
                '<label class="warning">'+
                    '<i class="fa fa-exclamation-triangle"></i>'+
                    '<span>Warning</span>'+
                '</label>'+
                '<label class="critical">'+
                    '<img src="images/critical.png" style="padding-left:5px !important;"><span'+
                    'style="padding-left:5px !important;">Critical</span>'+
                '</label>'+
                '<label class="exceptional">'+
                    '<i class="fa fa-star"></i>'+
                    '<span>Exceptional</span>'+
                '</label>'+
                '<label class="acceptable">'+
                    '<i class="fa fa-square"></i>'+
                    '<span>Acceptable</span>'+
                    '</label>'+
            '</div>',
	};
});
