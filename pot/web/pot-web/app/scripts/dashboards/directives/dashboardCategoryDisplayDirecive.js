angular.module('potApp').directive('dashboardCategoryDisplay', function () {
	return {
		restrict: 'E',
		replace: true,
		scope: {
			categoryObj: '=',
		},
		template: '<div> <label ng-if="categoryObj.category !== \'Critical\'"' +
			'class="{{categoryObj.category.toLowerCase()}}">' +
			'<i class="{{categoryObj.class}}"></i>' +
			//'<span>{{ categoryObj.category }}</span>' +
			'</label>' +
			'<label ng-if="categoryObj.category == \'Critical\'" class="critical">' +
			'<img src="images/critical.png" style="padding-left:5px !important;" >' +
			//'<span style="padding-left:5px !important;">Critical</span>' +
			'</label> </div>',
	};
});
