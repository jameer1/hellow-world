	'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("occupancyutilisation", {
		url : '/occupancyutilisation',
		data : {
			roles : []
		},
		views : {
			'current@' : {
				templateUrl : 'views/projresources/projassetreg/occupancyutilisationrecords/occupancyutilisation.html',
				controller : 'OccupancyUtilisationController'
			}
		}
	})
}]).controller("OccupancyUtilisationController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "RepairsNonScheduleFactory", function($rootScope, $scope, $q, $state, ngDialog,GenericAlertService,RepairsNonScheduleFactory) {
	

	/* Get Long Term Lease 10 */
	$scope.getOccupancyUtilityRecords = function() {
		if ($scope.fixedAssetid == null || $scope.fixedAssetid == undefined) {
			GenericAlertService.alertMessage("Please select the Sub Asset", "Warning");
			return;
		}
		
		$scope.sumOccupied = 0;
		$scope.sumVacant = 0;
		$scope.sumRepair = 0;
		
		$scope.utilisationrecords = [];
		var date = new Date();
	    var currentYear = date.getFullYear();		  
	    var pastYear = date.getFullYear()-1;
	    var proceedingYear = date.getFullYear()-1;
		
		$scope.getShortTermRecords();
		$scope.getRentalHistory();
		$scope.getRentalValue();
		
    /*		Occupied Column		*/	
		var proceedingYearOccupedDays = 0;
		var pastYearOccupedDays = 0;
		var currentYearOccupedDays = 0;
		
		angular.forEach($scope.stcorrReturnsDtlTOs, function(value, key) {
			if(value.startDate!=null && value.finishDate!=null){
				var sDate = new Date(value.startDate); 
				var fDate = new Date(value.finishDate);
				if(currentYear == sDate.getFullYear() && currentYear == fDate.getFullYear()){
					var shortCurrentTimeDiff = Math.abs(fDate- sDate);
					var shotCurrentNoDays = Math.ceil(shortCurrentTimeDiff / (1000 * 3600 * 24));
					currentYearOccupedDays +=(shotCurrentNoDays)+2;
				}else if(pastYear == sDate.getFullYear() && pastYear == fDate.getFullYear()){
					var shortPastTimeDiff = Math.abs(fDate- sDate);
					var shotPastNoDays = Math.ceil(shortPastTimeDiff / (1000 * 3600 * 24));
					pastYearOccupedDays +=(shotPastNoDays)+2;
				}else if(sDate.getFullYear() <= proceedingYear && fDate.getFullYear() <= proceedingYear){
					var shortProceedingTimeDiff = Math.abs(fDate- sDate);
					var shotProceedingNoDays = Math.ceil(shortProceedingTimeDiff / (1000 * 3600 * 24));
					proceedingYearOccupedDays +=(shotProceedingNoDays)+2;
				}
			}
		});
		
		angular.forEach($scope.longTermLeasingDtlTOs, function(value, key) {
			if(value.leaseStart!=null && value.leaseFinish!=null){
				var lStart = new Date(value.leaseStart); 
				var lFinish = new Date(value.leaseFinish);
				if(currentYear == lStart.getFullYear() && currentYear == lFinish.getFullYear()){
					var longCurrentTimeDiff = Math.abs(lFinish.getTime() - lStart.getTime());
					var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));					
					currentYearOccupedDays += longCurrentNoDays;
				}else if(pastYear == lStart.getFullYear() && pastYear == lFinish.getFullYear()){
					var longPastTimeDiff = Math.abs(fDate- sDate);
					var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
					pastYearOccupedDays +=longPastNoDays;
				}
				else if(lStart.getFullYear() <= proceedingYear && lFinish.getFullYear() <= proceedingYear){
					var longProceedingTimeDiff = Math.abs(fDate- sDate);
					var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
					proceedingYearOccupedDays +=longProceedingNoDays;
				}
			}
		});
		
		/*Under Repair/Restoration Column	*/		
		var proceedingYearRepairDays = 0;
		var pastYearRepairDays = 0;
		var currentYearRepairDays = 0;
		
		angular.forEach($scope.rentalValueDtlTOs, function(value, key) {
			var eDate = new Date(value.effectiveDate);	
			if(eDate!=null && currentYear == eDate.getFullYear()){
				if(value.assetCurrentLifeSataus=="Under Renovation/Repairs"){	
					var currentYearKeepGoing = true;
					angular.forEach($scope.rentalValueDtlTOs, function(value, key) {							
						if(currentYearKeepGoing){
							if(value.assetCurrentLifeSataus!="Under Renovation/Repairs"){
								var endOfRRDate = new Date(value.effectiveDate); 
								if(endOfRRDate > eDate){
									var rentalValueCurrentTimeDiff = Math.abs(endOfRRDate.getTime() - eDate.getTime());
									var rentalValueCurrentNoDays = Math.ceil(rentalValueCurrentTimeDiff / (1000 * 3600 * 24));					
									currentYearRepairDays +=(rentalValueCurrentNoDays+1);
									if(currentYearRepairDays > 0){
										currentYearKeepGoing=false;
									}
								}
							}
						}
					});						
				}
				if(($scope.rentalValueDtlTOs.length-1) == key && value.assetCurrentLifeSataus=="Under Renovation/Repairs"){
					var rentalValueCurrentTimeDiff = Math.abs(date.getTime() - eDate.getTime());
					var rentalValueCurrentNoDays = Math.ceil(rentalValueCurrentTimeDiff / (1000 * 3600 * 24));					
					currentYearRepairDays +=rentalValueCurrentNoDays;
				}					
			}else if(eDate!=null && pastYear == eDate.getFullYear()){
				if(value.assetCurrentLifeSataus=="Under Renovation/Repairs"){	
					var pastYearKeepGoing = true;
					angular.forEach($scope.rentalValueDtlTOs, function(value, key) {							
						if(pastYearKeepGoing){
							if(value.assetCurrentLifeSataus!="Under Renovation/Repairs"){
								var endOfRRDate = new Date(value.effectiveDate); 
								if(endOfRRDate > eDate){
									var rentalValuePastTimeDiff = Math.abs(endOfRRDate.getTime() - eDate.getTime());
									var rentalValuePastNoDays = Math.ceil(rentalValuePastTimeDiff / (1000 * 3600 * 24));					
									pastYearRepairDays +=(rentalValuePastNoDays+1);
									if(pastYearRepairDays > 0){
										pastYearKeepGoing=false;
									}
								}
							}
						}
					});						
				}
				if(($scope.rentalValueDtlTOs.length-1) == key && value.assetCurrentLifeSataus=="Under Renovation/Repairs"){
					var rentalValuePastTimeDiff = Math.abs(date.getTime() - eDate.getTime());
					var rentalValuePastNoDays = Math.ceil(rentalValuePastTimeDiff / (1000 * 3600 * 24));					
					pastYearRepairDays +=rentalValuePastNoDays;
				}					
			}else if(eDate!=null && proceedingYear == eDate.getFullYear()){
				if(value.assetCurrentLifeSataus=="Under Renovation/Repairs"){	
					var proceedingYearKeepGoing = true;
					angular.forEach($scope.rentalValueDtlTOs, function(value, key) {							
						if(proceedingYearKeepGoing){
							if(value.assetCurrentLifeSataus!="Under Renovation/Repairs"){
								var endOfRRDate = new Date(value.effectiveDate); 
								if(endOfRRDate > eDate){
									var rentalValueProceedingTimeDiff = Math.abs(endOfRRDate.getTime() - eDate.getTime());
									var rentalValueProceedingNoDays = Math.ceil(rentalValueProceedingTimeDiff / (1000 * 3600 * 24));					
									proceedingYearRepairDays +=(rentalValueProceedingNoDays+1);
									if(proceedingYearRepairDays > 0){
										proceedingYearKeepGoing=false;
									}
								}
							}
						}
					});						
				}
				if(($scope.rentalValueDtlTOs.length-1) == key && value.assetCurrentLifeSataus=="Under Renovation/Repairs"){
					var rentalValueProceedingTimeDiff = Math.abs(date.getTime() - eDate.getTime());
					var rentalValueProceedingNoDays = Math.ceil(rentalValueProceedingTimeDiff / (1000 * 3600 * 24));					
					proceedingYearRepairDays +=rentalValueProceedingNoDays;
				}					
			}						
		});
		
		/*Vacant Column*/				
		var proceedingYearVacantDays = 0;
		var pastYearVacantDays = 0;
		var currentYearVacantDays = 0;
		
							
		angular.forEach($scope.longTermLeasingDtlTOs, function(value, key) {
			if(value.leaseFinish!=null){					
				var lFinish = new Date(value.leaseFinish);
				if(currentYear == lFinish.getFullYear()){
					var currentYearLogVacantKeepGoing = false;
					angular.forEach($scope.longTermLeasingDtlTOs, function(value, index) {
						if (index%2 != 0){
							currentYearLogVacantKeepGoing = true;
						}else{
							currentYearLogVacantKeepGoing = false;
						}
						if(currentYearLogVacantKeepGoing){
							var lStart = new Date(value.leaseStart); 
							if(currentYear == lStart.getFullYear()){
								var longCurrentTimeDiff = Math.abs(lStart- lFinish);
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
								currentYearVacantDays +=(longCurrentNoDays)+2;
							}else{
								var longCurrentTimeDiff = Math.abs(date- lFinish);
								var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
								currentYearVacantDays +=(longCurrentNoDays)+2;
							}
						}
					});
				}
				else if(pastYear == lFinish.getFullYear()){
						var pastYearLogVacantKeepGoing = false;
						angular.forEach($scope.longTermLeasingDtlTOs, function(value, index) {
							if (index%2 != 0){
								pastYearLogVacantKeepGoing = true;
							}else{
								pastYearLogVacantKeepGoing = false;
							}
							if(pastYearLogVacantKeepGoing){
								var lStart = new Date(value.leaseStart); 
								if(pastYear == lStart.getFullYear()){
									var longPastTimeDiff = Math.abs(lStart- lFinish);
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									pastYearVacantDays +=(longPastNoDays)+2;
								}else{
									var longPastTimeDiff = Math.abs(date- lFinish);
									var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
									pastYearVacantDays +=(longPastNoDays)+2;
								}
							}
						});						
				}
				else if(proceedingYear == lFinish.getFullYear()){
					var proceedingYearLogVacantKeepGoing = false;
					angular.forEach($scope.longTermLeasingDtlTOs, function(value, index) {
						if (index%2 != 0){
							proceedingYearLogVacantKeepGoing = true;
						}else{
							proceedingYearLogVacantKeepGoing = false;
						}
						if(proceedingYearLogVacantKeepGoing){
							var lStart = new Date(value.leaseStart); 
							if(pastYear == lStart.getFullYear()){
								var longProceedingTimeDiff = Math.abs(lStart- lFinish);
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays +=(longProceedingNoDays)+2;
							}else{
								var longProceedingTimeDiff = Math.abs(date- lFinish);
								var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
								proceedingYearVacantDays +=(longProceedingNoDays)+2;
							}
						}
					});						
			     }
				
				if((currentYear == lFinish.getFullYear() && $scope.longTermLeasingDtlTOs.length-1) == 0){
					var longCurrentTimeDiff = Math.abs(date- lFinish);
					var longCurrentNoDays = Math.ceil(longCurrentTimeDiff / (1000 * 3600 * 24));
					currentYearVacantDays +=(longCurrentNoDays)+2;
				}
				else if((pastYear == lFinish.getFullYear() && $scope.longTermLeasingDtlTOs.length-1) == 0){
					var longPastTimeDiff = Math.abs(date- lFinish);
					var longPastNoDays = Math.ceil(longPastTimeDiff / (1000 * 3600 * 24));
					pastYearVacantDays +=(longPastNoDays)+2;
				}else if((proceedingYear == lFinish.getFullYear() && $scope.longTermLeasingDtlTOs.length-1) == 0){
					var longProceedingTimeDiff = Math.abs(date- lFinish);
					var longProceedingNoDays = Math.ceil(longProceedingTimeDiff / (1000 * 3600 * 24));
					proceedingYearVacantDays +=(longProceedingNoDays)+2;
				}		
			}				 
		});
		
		
		
		angular.forEach($scope.stcorrReturnsDtlTOs, function(value, key) {
			if(value.finishDate!=null){					
				var fDate = new Date(value.finishDate);
				if(currentYear == fDate.getFullYear()){
					var currentYearShortVacantKeepGoing = false;
					angular.forEach($scope.stcorrReturnsDtlTOs, function(value, index) {
						if (index%2 != 0){
							currentYearShortVacantKeepGoing = true;
						}else{
							currentYearShortVacantKeepGoing = false;
						}
						if(currentYearShortVacantKeepGoing){
							var sDate = new Date(value.startDate);
							if(currentYear == sDate.getFullYear()){
								var shortCurrentTimeDiff = Math.abs(sDate- fDate);
								var shotCurrentNoDays = Math.ceil(shortCurrentTimeDiff / (1000 * 3600 * 24));
								currentYearVacantDays +=(shotCurrentNoDays)+2;
							}else{
								var shortCurrentTimeDiff = Math.abs(date- fDate);
								var shotCurrentNoDays = Math.ceil(shortCurrentTimeDiff / (1000 * 3600 * 24));
								currentYearVacantDays +=(shotCurrentNoDays)+2;
							}
						}
					});
				}
				else if(pastYear == fDate.getFullYear()){
					var pastYearShortVacantKeepGoing = false;
					angular.forEach($scope.stcorrReturnsDtlTOs, function(value, index) {
						if (index%2 != 0){
							pastYearShortVacantKeepGoing = true;
						}else{
							pastYearShortVacantKeepGoing = false;
						}
						if(pastYearShortVacantKeepGoing){
							var sDate = new Date(value.startDate);
							if(currentYear == sDate.getFullYear()){
								var shortPastTimeDiff = Math.abs(sDate- fDate);
								var shotPastNoDays = Math.ceil(shortPastTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays +=(shotPastNoDays)+2;
							}else{
								var shortPastTimeDiff = Math.abs(date- fDate);
								var shotPastNoDays = Math.ceil(shortPastTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays +=(shotPastNoDays)+2;
							}
						}
					});
				}else if(proceedingYear == fDate.getFullYear()){
					var proceedingYearShortVacantKeepGoing = false;
					angular.forEach($scope.stcorrReturnsDtlTOs, function(value, index) {
						if (index%2 != 0){
							proceedingYearShortVacantKeepGoing = true;
						}else{
							proceedingYearShortVacantKeepGoing = false;
						}
						if(proceedingYearShortVacantKeepGoing){
							var sDate = new Date(value.startDate);
							if(currentYear == sDate.getFullYear()){
								var shortProceedingTimeDiff = Math.abs(sDate- fDate);
								var shotProceedingNoDays = Math.ceil(shortProceedingTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays +=(shotPastNoDays)+2;
							}else{
								var shortProceedingTimeDiff = Math.abs(date- fDate);
								var shotProceedingNoDays = Math.ceil(shortProceedingTimeDiff / (1000 * 3600 * 24));
								pastYearVacantDays +=(shotProceedingNoDays)+2;
							}
						}
					});
				}
				if((currentYear == fDate.getFullYear() && $scope.stcorrReturnsDtlTOs.length-1) == 0){
					var shortCurrentTimeDiff = Math.abs(date- fDate);
					var shotCurrentNoDays = Math.ceil(shortCurrentTimeDiff / (1000 * 3600 * 24));
					currentYearVacantDays +=(shotCurrentNoDays)+2;
				}
				else if((pastYear == fDate.getFullYear() && $scope.stcorrReturnsDtlTOs.length-1) == 0){
					var shortPastTimeDiff = Math.abs(date- fDate);
					var shotPastNoDays = Math.ceil(shortPastTimeDiff / (1000 * 3600 * 24));
					pastYearVacantDays +=(shotPastNoDays)+2;
				}else if((proceedingYear == fDate.getFullYear() && $scope.stcorrReturnsDtlTOs.length-1) == 0){
					var shortProceedingTimeDiff = Math.abs(date- fDate);
					var shotProceedingNoDays = Math.ceil(shortProceedingTimeDiff / (1000 * 3600 * 24));
					pastYearVacantDays +=(shotProceedingNoDays)+2;
				}	
			}
		});
		
			var addProceedingOccupancy = {	
					"period":"Proceeding Years",
					"unit":"Days",
					"occupied":proceedingYearOccupedDays,	
					"vacant":proceedingYearVacantDays,
					"underRepairRestoration":proceedingYearRepairDays,	
					"total":null					
				};			
			$scope.utilisationrecords.push(addProceedingOccupancy);
			var addPastOccupancy = {	
					"period":"Past Year",
					"unit":"Days",
					"occupied":pastYearOccupedDays,	
					"vacant":pastYearVacantDays,
					"underRepairRestoration":pastYearRepairDays,	
					"total":null					
			};
			$scope.utilisationrecords.push(addPastOccupancy);
			var addCurrentOccupancy = {	
					"period":"Current Years",
					"unit":"Days",
					"occupied":currentYearOccupedDays,	
					"vacant":currentYearVacantDays,
					"underRepairRestoration":currentYearRepairDays,	
					"total":null					
				};
			
			$scope.utilisationrecords.push(addCurrentOccupancy);				
			angular.forEach($scope.utilisationrecords, function(value, key) {					
				$scope.sumOccupied += value.occupied;	
				$scope.sumVacant += value.vacant;
				$scope.sumRepair += value.underRepairRestoration;
				
			});	
	},
	
	$scope.$on("resetRevenueFromAssetSales", function() {
		$scope.revenueSalesDtlTOs = [];
	});
	$rootScope.$on('rentalfromassetsales', function (event) {
		
		if($scope.fixedAssetid){
			$scope.getOccupancyUtilityRecords();	
		}
	});
	
}]);