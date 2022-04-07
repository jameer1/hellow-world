'use strict';

app.config(["$stateProvider", function ($stateProvider){
	$stateProvider.state("manpoweremployeemasterlist", {
		url: '/manpoweremployeemasterlist',
		data: {
			manpower: []
		},
		views: {
			'content@': {
			'templateUrl': 'views/reports/manpower/manpoweremployee.html',
			controller: 'ManpowerEmployeeController'
			}
		}
	})
}]).controller("ManpowerEmployeeController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "uiGridGroupingConstants", "ManpowerReportService", "GenericAlertService", "EpsProjectMultiSelectFactory", "CompanyMultiSelectFactory", "generalservice", "stylesService", "ngGridService", 'chartService', "uiGridConstants","$filter", "MultiProjPlantMasterDetailsFactory","MultipleCrewSelectionFactory", "dateGroupingService", "CostCodeMultiSelectFactory", "PlantReportService", function ($rootScope, $scope, $q, $state, ngDialog, uiGridGroupingConstants, ManpowerReportService, GenericAlertService, EpsProjectMultiSelectFactory, CompanyMultiSelectFactory,  generalservice, stylesService, ngGridService, chartService, uiGridConstants, $filter, MultiProjPlantMasterDetailsFactory, MultipleCrewSelectionFactory, dateGroupingService, CostCodeMultiSelectFactory, PlantReportService,) {
	chartService.getChartMenu($scope);
	$scope.yAxislabels = 'Employee Count';
	$scope.data = [];
	$scope.data1 = [];
	$scope.selectedProjIds = [];
	$scope.selectedCompanyIds = [];
	$scope.categoryName = [];
	$scope.employmentsType = [];
	$scope.genderType = [];
	$scope.employmentStatus = [];
	$scope.empDatamoreFlag = 0;
	$scope.date = new Date();
//	$scope.toDate = new Date();
	$scope.todayDate = new Date();
	$scope.employeeDetails = [];
	$scope.styleSvc = stylesService;
	$scope.labels = [];
	$scope.series2=[];	
	$scope.subReport = "None";
	$scope.subReportCode = "";
	$scope.plantDetails = [];
//	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() -1, 0);
//	$scope.fromDate = new Date($scope.toDate);
//	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());
//	$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
//	var defaultFromDate = new Date($scope.fromDate);
//	var defaultToDate = new Date($scope.toDate);
	//enrollement Date 
//	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
//	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	
	//expectedDemob-Date
//	$scope.expectedDemobFromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
//	$scope.expectedDemobToDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	
	//actualDemob- Date
//	$scope.actualDemobFromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
//	$scope.actualDemobToDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
	
//	let employeeDetails;
	/*let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.fromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.expectedDemobFromDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.expectedDemobToDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.actualDemobFromDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		  $scope.actualDemobToDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	
		$scope.$watch('fromDate', function () {
			$scope.checkErr();
			$scope.clearSubReportDetails();
		});
		$scope.$watch('toDate', function () {
			$scope.checkErr();
			$scope.clearSubReportDetails();
		});
		$scope.checkErr = function () {
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("From Date should be Less than To Date", 'Warning');
				return;
			}
		};*/
 	$scope.type = {
		name: null
	},
	$scope.types = [{
		id: 1,
		name: "Full Time", 
		code: generalservice.employmentTypes[0]
	},{
		id: 2,
		name: "Part Time",
		code: generalservice.employmentTypes[1]
	},{
		id: 3,
		name: "Casual",
		code: generalservice.employmentTypes[2]
	},{
		id: 4,
		name: "Contract",
		code: generalservice.employmentTypes[3]
	}];
	
	$scope.category = {
		name: null
	}
	$scope.genders = [{
		id: 1,
		name: "Male",
		code: "Male"
	},{
		id: 2,
		name: "Female",
		code: "Female",
	},{
		id: 3,
		name: "All",
		code: null,
	}];
	
	$scope.status = {
		name: null
	}
	
	$scope.cstatus = [{
		id: 1,
		name: "On Transfer",
		code: generalservice.currentStatus[0]
	},{
		id: 2,
		name: "Relived",
		code: generalservice.currentStatus[1]
	},{
		id: 3,
		name: "All",
		code: null, // generalservice.currentStatus[2]
	}];
	
	$scope.subReport = {
		name: null
	}
	
	$scope.subReports = [{
		name: 'Project wise and Category wise Employee count',
		code: "pcatEmployeeCount"
	},{
		name: 'Project wise and Trade wise Employees count',
		code: "pTradeEmployee"
	},{
		name: 'Project wise and Union classification wise Employees',
		code: "pUnionTradeEmployee"
	},{
		name: 'Company wise and Category wise Employees',
		code: "compCatgEmployees"
	},{
		name: 'Company wise and Trade wise Employees',
		code: "compTradeEmployees"
	},{
		name: 'Company wise and Union Classification wise Employees',
		code: "compUnionEmloyees"
	},{
		name: 'Project wise Mobilisation - Periodical Count',
		code: "projMobPeriodic"
	},{
		name: 'Company wise Mobilisation - Periodical Count',
		code: "compMobPeriodic"
	},{
		name: 'Project wise actual De-Mobilisation - Periodical Count',
		code: "projActualDeMobPeriodic"
	},{
		name: 'Company wise actual De-Mobilisation - Periodical Count',
		code: "compActualDeMobPeriodic"
	},{
		name: 'Project wise Expected De-Mobilisation - Periodical Count',
		code: "projExpectDeMobPeriodic"
	},{
		name: 'Company wise Expected De-Mobilisation - Periodical Count',
		code: "compExpectDeMobPeriodic"
	}];
	$scope.periodicSubReports = [{
		name: 'Daily',
		code: "daily"
	}, {
		name: 'Weekly',
		code: "weekly"
	}, {
		name: 'Monthly',
		code: "monthly"
	}, {
		name: 'Yearly',
		code: "yearly"
	}];
	$scope.periodicSubReport = $scope.periodicSubReports[0];
	let series;
	$scope.changeSubReport = function(){
		$scope.resetEmployeeDetails1();
			if ($scope.subReport.code =="pcatEmployeeCount") {
		if($scope.subReport.code == "pcatEmployeeCount"){
			$scope.catgoryNames=[];
                        $scope.catgNames=[];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.catgNames = $filter('unique')($scope.employeeDetails[i],'projEmpClassCatg');
                            $scope.catgoryNames.push($scope.catgNames);
                        }
                        console.log($scope.catgoryNames);
                        $scope.newduplicate = $filter('unique')($scope.catgoryNames,'projEmpClassCatg');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.catgoryNames.length;i++){
                            $scope.names= $scope.catgoryNames[i].projEmpClassCatg;
                            $scope.projectNames.push($scope.names);                            
                        }             
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'projEmpClassCatg');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){
							$scope.categoryWiseCount = [];
							$scope.filt = [];
							let categoryName = $scope.newduplicate[i];
							console.log(categoryName);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(categoryName == $scope.employeeDetails[j].projEmpClassCatg){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}
							
							$scope.filtereddata = $filter('unique')($scope.filt,'name');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let projName = $scope.filtereddata[k].name;
								let epsName= $scope.filtereddata[k].parentName;
								console.log(projName);
								console.log(epsName);
								for(var m=0;m<$scope.filt.length;m++){
									if(projName == $scope.filt[m].name){
										count++;
									}
									
								}
								$scope.categoryWiseCount.push({"categoryName":categoryName,"epsName":epsName,"projName":projName,"number":count});
							}
							/*generateSubReportData("epsName", "epsName");*/
							console.log($scope.categoryWiseCount);
						}			
				}
				generateSubReportData("epsName", "epsName");							
				}												
				else if($scope.subReport.code == "pTradeEmployee"){
					if($scope.subReport.code == "pTradeEmployee"){							
					    $scope.parentNames=[];
                        $scope.projNames=[];
                        $scope.projectWiseTradeWise = [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'name');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }  
            
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');
                        console.log($scope.newduplicate);
      					for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let projNames = $scope.newduplicate[i];
							console.log(projNames);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(projNames == $scope.employeeDetails[j].name){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'empClassName');
							console.log($scope.filtereddata);
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let empClassName = $scope.filtereddata[k].empClassName;								
									let empClassCode = $scope.filtereddata[k].empClassCode;
								for(var m=0;m<$scope.filt.length;m++){
									if(empClassName == $scope.filt[m].empClassName){
										count++;
									}
									
								}
								$scope.projectWiseTradeWise.push({"empClassCode":empClassCode,"projNames":projNames,"empClassName":empClassName,"number":count});
							}
							console.log($scope.projectWiseTradeWise);							
						}
					}
					generateSubReportData2("empClassName", "empClassName");
				}	
				
			else if($scope.subReport.code == "pUnionTradeEmployee"){
				if($scope.subReport.code == "pUnionTradeEmployee"){
					$scope.parentNames=[];
                        $scope.projNames=[];
                        $scope.projectWiseUnionWise = [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'name');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }              
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');
                        console.log($scope.newduplicate,"sk2");
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let projNames = $scope.newduplicate[i];
							console.log(projNames);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(projNames == $scope.employeeDetails[j].name){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'projEmpClassUnion');
							 console.log($scope.filtereddata,"sk1");
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								
								let projEmpClassUnion = $scope.filtereddata[k].projEmpClassUnion;
								let empClassName = $scope.filtereddata[k].empClassName;
								for(var m=0;m<$scope.filt.length;m++){
									if(projEmpClassUnion == $scope.filt[m].projEmpClassUnion){
										count++;
									}									
								}
								$scope.projectWiseUnionWise.push({"empClassName":empClassName,"projNames":projNames,"projEmpClassUnion":projEmpClassUnion,"number":count});
							}							
						}				
				}
				generateSubReportData3("empClassName", "empClassName");
				}				
				else if($scope.subReport.code == "compCatgEmployees"){
					if($scope.subReport.code == "compCatgEmployees"){
								$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'projEmpClassCatg');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'projEmpClassCatg');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].projEmpClassCatg;
                            $scope.projectNames.push($scope.names);                            
                        }  
                        
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'projEmpClassCatg');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){
							$scope.companyWiseCatgryWise = [];
							$scope.filt = [];
							let projEmpClassCatg = $scope.newduplicate[i];
							console.log(projEmpClassCatg);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(projEmpClassCatg == $scope.employeeDetails[j].projEmpClassCatg){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'cmpName');
							console.log($scope.filtereddata);
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;								
								let empClassName = $scope.filtereddata[k].empClassName;
								let cmpName = $scope.filtereddata[k].cmpName;
								for(var m=0;m<$scope.filt.length;m++){
									if(cmpName == $scope.filt[m].cmpName){
										count++;
									}									
								}
								$scope.companyWiseCatgryWise.push({"empClassName":empClassName,"projEmpClassCatg":projEmpClassCatg,"cmpName":cmpName,"number":count});
							}
							console.log($scope.companyWiseCatgryWise);
						}				
				}
				generateSubReportData4("empClassName", "empClassName");	
				}
				else if($scope.subReport.code == "compTradeEmployees"){
					if($scope.subReport.code == "compTradeEmployees"){
							
					$scope.parentNames=[];
                        $scope.projNames=[];
                        $scope.companyWiseTradeWise = [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'cmpName');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'cmpName');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].cmpName;
                            $scope.projectNames.push($scope.names);                            
                        } 
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'cmpName');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let cmpName = $scope.newduplicate[i];
							console.log(cmpName);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(cmpName == $scope.employeeDetails[j].cmpName){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'empClassName');
							console.log($scope.filtereddata);
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;								
								let empClassCode = $scope.filtereddata[k].empClassCode;
								let empClassName = $scope.filtereddata[k].empClassName;
								for(var m=0;m<$scope.filt.length;m++){
									if(empClassName == $scope.filt[m].empClassName){
										count++;
									}									
								}
								$scope.companyWiseTradeWise.push({"empClassCode":empClassCode,"cmpName":cmpName,"empClassName":empClassName,"number":count});
							}
							console.log($scope.companyWiseTradeWise);
						}				
				}
				generateSubReportData5("epsName", "epsName");	
				}
				else if($scope.subReport.code == "compUnionEmloyees"){
					if($scope.subReport.code == "compUnionEmloyees"){
						$scope.parentNames=[];
                        $scope.projNames=[];
                        $scope.companyWiseUnionWise = [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'cmpName');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'cmpName');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].cmpName;
                            $scope.projectNames.push($scope.names);                            
                        }         
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'cmpName');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let cmpName = $scope.newduplicate[i];
							console.log(cmpName);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(cmpName == $scope.employeeDetails[j].cmpName){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'projEmpClassUnion');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let projEmpClassUnion = $scope.filtereddata[k].projEmpClassUnion;
								for(var m=0;m<$scope.filt.length;m++){
									if(projEmpClassUnion == $scope.filt[m].projEmpClassUnion){
										count++;
									}									
								}
								$scope.companyWiseUnionWise.push({"cmpName":cmpName,"projEmpClassUnion":projEmpClassUnion,"number":count});
							}
							console.log($scope.companyWiseUnionWise);
						}										
				}
				generateSubReportData6("epsName", "epsName");	
				}
				else if($scope.subReport.code == "projMobPeriodic"){
					if($scope.subReport.code == "projMobPeriodic"){
						$scope.projectWiseActualMobiliz= [];
						$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'name');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }            
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let projName = $scope.newduplicate[i];
							console.log(name);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(projName == $scope.employeeDetails[j].name){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'mobilaizationDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let mobilaizationDate = $scope.filtereddata[k].mobilaizationDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(mobilaizationDate == $scope.filt[m].mobilaizationDate){
										count++;
									}									
								}
								$scope.projectWiseActualMobiliz.push({"projName":projName,"mobilaizationDate":mobilaizationDate,"number":count});
							}
							console.log($scope.projectWiseActualMobiliz);
						}		
				}
				generateSubReportData7("epsName", "epsName");
				}
				else if($scope.subReport.code == "compMobPeriodic"){
					if($scope.subReport.code == "compMobPeriodic"){
						$scope.companyWiseActualMobiliz= [];
						$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'cmpName');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'cmpName');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].cmpName;
                            $scope.projectNames.push($scope.names);                            
                        }          
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'cmpName');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let cmpName = $scope.newduplicate[i];
							console.log(cmpName);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(cmpName == $scope.employeeDetails[j].cmpName){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'mobilaizationDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let mobilaizationDate = $scope.filtereddata[k].mobilaizationDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(mobilaizationDate == $scope.filt[m].mobilaizationDate){
										count++;
									}									
								}
								$scope.companyWiseActualMobiliz.push({"cmpName":cmpName,"mobilaizationDate":mobilaizationDate ,"number":count});
							}
							console.log($scope.companyWiseActualMobiliz);
						}
					}
					generateSubReportData8("epsName", "epsName");
				}
				else if($scope.subReport.code == "projActualDeMobPeriodic"){
					if($scope.subReport.code == "projActualDeMobPeriodic"){
						$scope.parentNames=[];
                        $scope.projNames=[];
                        $scope.projectWiseActualDeMobiliz= [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'name');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }                     
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let projName = $scope.newduplicate[i];
							console.log(name);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(projName == $scope.employeeDetails[j].name){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'actualDeMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let actualDeMobDate = $scope.filtereddata[k].actualDeMobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(actualDeMobDate == $scope.filt[m].actualDeMobDate){
										count++;
									}									
								}
								$scope.projectWiseActualDeMobiliz.push({"projName":projName,"actualDeMobDate":actualDeMobDate,"number":count});
							}
							console.log($scope.projectWiseActualDeMobiliz);
						}		
				    }
                     generateSubReportData9("epsName", "epsName");
				}
				else if($scope.subReport.code == "compActualDeMobPeriodic"){
					if($scope.subReport.code == "compActualDeMobPeriodic"){
						$scope.companyWiseActualDeMobiliz= [];
						$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'cmpName');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'cmpName');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].cmpName;
                            $scope.projectNames.push($scope.names);                            
                        }                        
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'cmpName');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let cmpName = $scope.newduplicate[i];
							console.log(cmpName);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(cmpName == $scope.employeeDetails[j].cmpName){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'actualDeMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let actualDeMobDate = $scope.filtereddata[k].actualDeMobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(actualDeMobDate == $scope.filt[m].actualDeMobDate){
										count++;
									}									
								}
								$scope.companyWiseActualDeMobiliz.push({"cmpName":cmpName,"actualDeMobDate":actualDeMobDate ,"number":count});
							}
							console.log($scope.companyWiseActualDeMobiliz);
						}			
			     	}
                    generateSubReportData10("epsName", "epsName");
				}
				else if($scope.subReport.code == "projExpectDeMobPeriodic"){
					if($scope.subReport.code == "projExpectDeMobPeriodic"){
						$scope.parentNames=[];
                        $scope.projNames=[];
                        $scope.projectWiseExActualDeMobiliz= [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'name');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }                     
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){
							$scope.filt = [];
							let projName = $scope.newduplicate[i];
							console.log(name);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(projName == $scope.employeeDetails[j].name){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'expectedDeMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let expectedDeMobDate = $scope.filtereddata[k].expectedDeMobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(expectedDeMobDate == $scope.filt[m].expectedDeMobDate){
										count++;
									}									
								}
								$scope.projectWiseExActualDeMobiliz.push({"projName":projName,"expectedDeMobDate":expectedDeMobDate,"number":count});
							}
							console.log($scope.projectWiseExActualDeMobiliz);
						}					
			     	}
                    generateSubReportData11("epsName", "epsName");
				}
				else if($scope.subReport.code == "compExpectDeMobPeriodic"){
					if($scope.subReport.code == "compExpectDeMobPeriodic"){
						 $scope.parentNames=[];
                        $scope.projNames=[];
                       $scope.companyWiseExActualDeMobiliz= [];
                        for(var i=0;i<$scope.employeeDetails.length;i++){
                            $scope.projNames = $filter('unique')($scope.employeeDetails[i],'cmpName');
                            $scope.parentNames.push($scope.projNames);
                        }
                        console.log($scope.parentNames);
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'cmpName');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].cmpName;
                            $scope.projectNames.push($scope.names);                            
                        }             
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'cmpName');
                        console.log($scope.newduplicate);
						for(var i=0;i<$scope.newduplicate.length;i++){						
							$scope.filt = [];
							let cmpName = $scope.newduplicate[i];
							console.log(cmpName);
							for(var j=0;j<$scope.employeeDetails.length;j++){
								if(cmpName == $scope.employeeDetails[j].cmpName){
									$scope.filt.push($scope.employeeDetails[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'expectedDeMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let expectedDeMobDate = $scope.filtereddata[k].expectedDeMobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(expectedDeMobDate == $scope.filt[m].expectedDeMobDate){
										count++;
									}									
								}
								$scope.companyWiseExActualDeMobiliz.push({"cmpName":cmpName,"expectedDeMobDate":expectedDeMobDate ,"number":count});
							}
							console.log($scope.companyWiseExActualDeMobiliz);
						}				
				    }
                    generateSubReportData12("epsName", "epsName");
				}
					
			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
				/*prepareSubReport();*/
			} else {
				$scope.plantDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				$scope.getPlantDetails();
			}
		
	}; 
	/*Project wise and Category wise Employee count 1*/	
	/*Project wise and Category wise Employee count 1*/	
	$scope.numberone = [];
	$scope.numberone2 = [];
	$scope.labels2 = [];
	$scope.data2=[];
	$scope.seriesone=[];		
	$scope.seriesone2=[];
function generateSubReportData(key, value) {
	let subReportMap1 = [];
for (const catDtl of $scope.categoryWiseCount) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.projName;
		mapValue = catDtl.projName;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap1[mapKey]) {		
		subReportMap1[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData1(subReportMap1);			
};
function setGraphData1(subReportMap1) {
	 $scope.newduplicate = $filter('unique')($scope.projectNames,'projEmpClassCatg');
	for(let i=0;i<$scope.newduplicate.length;i++)
	{
	$scope.seriesone2=$scope.newduplicate[i];
	$scope.seriesone.push($scope.seriesone2);
	}	
	for (const index in subReportMap1) {
	$scope.numberone.push(subReportMap1[index].number);
	$scope.numberone2.push(subReportMap1[index].number);
	$scope.labelsone.push(subReportMap1[index].mapValue);
}
	for(let i=0;i<$scope.seriesone.length;i++)
		{
			$scope.dataone.push($scope.numberone);
		}
	initGraph1();
};
$scope.dataone= [];
$scope.labelsone= [];
function initGraph1() {
	$scope.OneSeries = $scope.seriesone;
	$scope.OneLabels = $scope.labelsone;			
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;	
};
	
/*Project wise and Trade wise Employees count 2*/		
	$scope.number2 = [];
	$scope.number22 = [];
	$scope.labels2 = [];
	$scope.data2=[];
	$scope.series2=[];		
	$scope.series22=[];
function generateSubReportData2(key, value) {
	let subReportMap2 = [];
for (const catDtl of $scope.projectWiseTradeWise) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.projNames;
		mapValue = catDtl.projNames;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap2[mapKey]) {		
		subReportMap2[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData2(subReportMap2);			
};
function setGraphData2(subReportMap2) {
	for(let i=0;i<$scope.projectWiseTradeWise.length;i++)
	{
	$scope.series22=$scope.projectWiseTradeWise[i].empClassName;
	$scope.series2.push($scope.series22);
	}	
	for (const index in subReportMap2) {
	$scope.number2.push(subReportMap2[index].number);
	$scope.number22.push(subReportMap2[index].number);
	$scope.labels2.push(subReportMap2[index].mapValue);
}
	for(let i=0;i<$scope.series2.length;i++)
		{
			$scope.data1.push($scope.number2);
		}
	initGraph2();
};
$scope.data1= [];
$scope.labels2= [];
function initGraph2() {
	$scope.pTradeSeries = $scope.series2;
	$scope.pTradeLabels = $scope.labels2;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};
/*Project wise and Union classification wise Employees 3*/
	$scope.number3 = [];
	$scope.number33 = [];
	$scope.labels3 = [];
	$scope.data3=[];
	$scope.series3=[];		
	$scope.series33=[];
function generateSubReportData3(key, value) {
	let subReportMap3 = [];
for (const catDtl of $scope.projectWiseUnionWise) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.projNames;
		mapValue = catDtl.projNames;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap3[mapKey]) {		
		subReportMap3[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData3(subReportMap3);			
};
function setGraphData3(subReportMap3) {
	for(let i=0;i<$scope.projectWiseUnionWise.length;i++)
	{
	$scope.series33=$scope.projectWiseUnionWise[i].empClassName;
	$scope.series3.push($scope.series33);
	}	
	for (const index in subReportMap3) {
	$scope.number3.push(subReportMap3[index].number);
	$scope.number33.push(subReportMap3[index].number);
	$scope.labels3.push(subReportMap3[index].mapValue);
}
	for(let i=0;i<$scope.series3.length;i++)
		{
			$scope.data3.push($scope.number3);
		}
	initGraph3();
};
$scope.data3= [];
$scope.labels3= [];
function initGraph3() {
	$scope.pUnionSeries = $scope.series3;
	$scope.pUnionLabels = $scope.labels3;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

/*Company wise and Category wise Employees 4*/
	$scope.number4 = [];
	$scope.number44 = [];
	$scope.labels4 = [];
	$scope.data4=[];
	$scope.series4=[];		
	$scope.series44=[];
function generateSubReportData4(key, value) {
	let subReportMap4 = [];
for (const catDtl of $scope.companyWiseCatgryWise) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.cmpName;
		mapValue = catDtl.cmpName;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap4[mapKey]) {		
		subReportMap4[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData4(subReportMap4);			
};
function setGraphData4(subReportMap4) {
	   $scope.newduplicate = $filter('unique')($scope.projectNames,'projEmpClassCatg');
	for(let i=0;i<$scope.newduplicate.length;i++)
	{
	$scope.series44=$scope.newduplicate[i];
	$scope.series4.push($scope.series44);
	}	
	for (const index in subReportMap4) {
	$scope.number4.push(subReportMap4[index].number);
	$scope.number44.push(subReportMap4[index].number);
	$scope.labels4.push(subReportMap4[index].mapValue);
}
	for(let i=0;i<$scope.series4.length;i++)
		{
			$scope.data4.push($scope.number4);
		}
	initGraph4();
};

$scope.data4= [];
$scope.labels4= [];
function initGraph4() {
	$scope.cCAtSeries = $scope.series4;
	$scope.cCAtLabels = $scope.labels4;	
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};	
	$scope.number5 = [];
	$scope.number55 = [];
	$scope.labels5 = [];
	$scope.data5=[];
	$scope.series5=[];		
	$scope.series55=[];
function generateSubReportData5(key, value) {
	let subReportMap5 = [];
for (const catDtl of $scope.companyWiseTradeWise) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.cmpName;
		mapValue = catDtl.cmpName;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap5[mapKey]) {		
		subReportMap5[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData5(subReportMap5);			
};
function setGraphData5(subReportMap5) {
	for(let i=0;i<$scope.companyWiseTradeWise.length;i++)
	{
	$scope.series55=$scope.companyWiseTradeWise[i].empClassName;
	$scope.series5.push($scope.series55);
	}	
	for (const index in subReportMap5) {
	$scope.number5.push(subReportMap5[index].number);
	$scope.number55.push(subReportMap5[index].number);
	$scope.labels5.push(subReportMap5[index].mapValue);
}
	for(let i=0;i<$scope.series5.length;i++)
		{
			$scope.data5.push($scope.number5);
		}
	initGraph5();
};
$scope.data5= [];
$scope.labels5= [];
function initGraph5() {
	$scope.compTradeSeries = $scope.series5;
	$scope.compTradeLabels = $scope.labels5;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

 /*Company wise and Union Classification wise Employees 6*/
	$scope.number6 = [];
	$scope.number66 = [];
	$scope.labels6 = [];
	$scope.data6=[];
	$scope.series6=[];		
	$scope.series66=[];
function generateSubReportData6(key, value) {
	let subReportMap6 = [];
for (const catDtl of $scope.companyWiseUnionWise) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.cmpName;
		mapValue = catDtl.cmpName;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap6[mapKey]) {		
		subReportMap6[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData6(subReportMap6);			
};
function setGraphData6(subReportMap6) {
	for(let i=0;i<$scope.companyWiseUnionWise.length;i++)
	{
	$scope.series66=$scope.companyWiseUnionWise[i].projEmpClassUnion;
	$scope.series6.push($scope.series66);
	}	
	for (const index in subReportMap6) {
	$scope.number6.push(subReportMap6[index].number);
	$scope.number66.push(subReportMap6[index].number);
	$scope.labels6.push(subReportMap6[index].mapValue);
}
	for(let i=0;i<$scope.series6.length;i++)
		{
			$scope.data6.push($scope.number6);
		}
	initGraph6();
};
$scope.data6= [];
$scope.labels6= [];
function initGraph6() {
	$scope.compUnionSeries = $scope.series6;
	$scope.compUnionLabels = $scope.labels6;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};
	
/*Project wise Mobilisation - Periodical Count 7*/
	$scope.number7 = [];
	$scope.number77 = [];
	$scope.series7=[];		
	$scope.series77=[];
	$scope.data7=[];
	$scope.labels7=[];
function generateSubReportData7(key, value) {
	$scope.new1 = $filter('unique')($scope.projectWiseActualMobiliz,'projName');
	let subReportMap7 = [];
	for(let i=0;i<$scope.new1.length;i++)
	{
	$scope.series77=$scope.new1[i].projName;
	$scope.series7.push($scope.series77);
	}
for (const catDtl of $scope.projectWiseActualMobiliz) {			
	let mapKey;
	let mapValue;
	let date;
	let number;
	let number1;
		mapKey = catDtl.mobilaizationDate;
		mapValue = catDtl.mobilaizationDate;
		date=catDtl.projName;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap7[mapKey]) {		
		subReportMap7[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"date": date,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData7(subReportMap7);			
};
$scope.labels7 = [];
$scope.data7=[];
function setGraphData7(subReportMap7) {
	for (const index in subReportMap7) {
	$scope.number7.push(subReportMap7[index].number);
	$scope.number77.push(subReportMap7[index].number);
	$scope.labels7.push($filter('date')(( subReportMap7[index].mapValue),"dd-MMM-yyyy"));
}	
	for(let i=0;i<$scope.series7.length;i++)
		{
			$scope.data7.push($scope.number7);
		}
	initGraph7();
};
$scope.data7= [];
var labels7= [];
function initGraph7() {	
	$scope.projMobSeries = $scope.series7;
	$scope.projMobLabels = $scope.labels7;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

/*Company wise Mobilisation - Periodical Count 8*/
		$scope.number8 =[];
		$scope.number88 =[];
		$scope.labels8 =[];
		$scope.data8 =[];
		$scope.series8 =[];		
		$scope.series88 =[];
function generateSubReportData8(key, value) {
	let subReportMap8 = [];
for (const catDtl of $scope.companyWiseActualMobiliz) {			
	let mapKey;
	let mapValue;
	let date;
	let number;
	let number1;
		mapKey = catDtl.mobilaizationDate;
		mapValue = catDtl.mobilaizationDate;
		date=catDtl.cmpName;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap8[mapKey]) {		
		subReportMap8[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"date": date,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData8(subReportMap8);			
};
function setGraphData8(subReportMap8) {
	$scope.new1 = $filter('unique')($scope.companyWiseActualMobiliz,'cmpName');
	for(let i=0;i<$scope.new1.length;i++)
	{
	$scope.series88=$scope.new1[i].cmpName;
	$scope.series8.push($scope.series88);
	}	
	
	for (const index in subReportMap8) {
	$scope.number8.push(subReportMap8[index].number);
	$scope.number88.push(subReportMap8[index].number);
	$scope.labels8.push($filter('date')(( subReportMap8[index].mapValue),"dd-MMM-yyyy"));
}
	for(let i=0;i<$scope.series8.length;i++)
		{
			$scope.data8.push($scope.number8);
		}
	initGraph8();
};
$scope.data8= [];
$scope.labels8= [];
function initGraph8() {
	$scope.compMobSeries = $scope.series8;
	$scope.compMobLabels = $scope.labels8;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

 /*Project wise actual De-Mobilisation - Periodical Count 9 */
	$scope.number9 = [];
	$scope.number99 = [];
	$scope.labels9 = [];
	$scope.data9=[];
	$scope.series9=[];		
	$scope.series99=[];
function generateSubReportData9(key, value) {
	let subReportMap9 = [];
for (const catDtl of $scope.projectWiseActualDeMobiliz) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.actualDeMobDate;
		mapValue = catDtl.actualDeMobDate;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap9[mapKey]) {		
		subReportMap9[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData9(subReportMap9);			
};
function setGraphData9(subReportMap9) {
	for(let i=0;i<$scope.projectWiseActualDeMobiliz.length;i++)
	{
	$scope.series99=$scope.projectWiseActualDeMobiliz[i].projName;
	$scope.series9.push($scope.series99);
	}	
	for (const index in subReportMap9) {
	$scope.number9.push(subReportMap9[index].number);
	$scope.number99.push(subReportMap9[index].number);
	$scope.labels9.push($filter('date')(( subReportMap9[index].mapValue),"dd-MMM-yyyy"));
}
	for(let i=0;i<$scope.series9.length;i++)
		{
			$scope.data9.push($scope.number9);
		}
	initGraph9();
};
$scope.data9= [];
$scope.labels9= [];
function initGraph9() {
	$scope.projActSeries = $scope.series9;
	$scope.projActLabels = $scope.labels9;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

/*Company wise actual De-Mobilisation - Periodical Count 10 */
	$scope.number10 = [];
	$scope.number100 = [];
	$scope.labels10 = [];
	$scope.data10=[];
	$scope.series10=[];		
	$scope.series100=[];
function generateSubReportData10(key, value) {
	let subReportMap10 = [];
for (const catDtl of $scope.companyWiseActualDeMobiliz) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.actualDeMobDate;
		mapValue = catDtl.actualDeMobDate;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap10[mapKey]) {		
		subReportMap10[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData10(subReportMap10);			
};
function setGraphData10(subReportMap10) {
	 $scope.newduplicate = $filter('unique')($scope.projectNames,'name');
	for(let i=0;i<$scope.newduplicate.length;i++)
	{
	$scope.series100=$scope.newduplicate[i];
	$scope.series10.push($scope.series100);
	}	
	for (const index in subReportMap10) {
	$scope.number10.push(subReportMap10[index].number);
	$scope.number100.push(subReportMap10[index].number);
	$scope.labels10.push($filter('date')(( subReportMap10[index].mapValue),"dd-MMM-yyyy"));
}
	for(let i=0;i<$scope.series10.length;i++)
		{
			$scope.data10.push($scope.number10);
		}
	initGraph10();
};
$scope.data10= [];
$scope.labels10= [];
function initGraph10() {
	$scope.compActSeries = $scope.series10;
	$scope.compActLabels = $scope.labels10;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

/*	Project wise Expected De-Mobilisation - Periodical Count 11*/
	$scope.number11 = [];
	$scope.number111 = [];
	$scope.labels11 = [];
	$scope.data11=[];
	 $scope.series11=[];		
	$scope.series111=[];
function generateSubReportData11(key, value) {
	let subReportMap11 = [];
for (const catDtl of $scope.projectWiseExActualDeMobiliz) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.expectedDeMobDate;
		mapValue = catDtl.expectedDeMobDate;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap11[mapKey]) {		
		subReportMap11[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData11(subReportMap11);			
};
function setGraphData11(subReportMap11) {
	$scope.new1 = $filter('unique')($scope.projectWiseExActualDeMobiliz,'projName');
 for(let i=0;i<$scope.new1.length;i++)
	{
	$scope.series111=$scope.new1[i].projName;
	$scope.series11.push($scope.series111);
	}	
	
	for (const index in subReportMap11) {
	$scope.number11.push(subReportMap11[index].number);
	$scope.number111.push(subReportMap11[index].number);
	$scope.labels11.push($filter('date')(( subReportMap11[index].mapValue),"dd-MMM-yyyy"));
}
	for(let i=0;i<$scope.series11.length;i++)
		{
			$scope.data11.push($scope.number11);
		}
	initGraph11();
};
$scope.data11= [];
$scope.labels11= [];
function initGraph11() {
	$scope.projExpSeries = $scope.series11;
	$scope.projExpLabels = $scope.labels11;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};

/*Company wise Expected De-Mobilisation - Periodical Count 12*/
	$scope.number12 = [];
	$scope.number112 = [];
	$scope.labels12 = [];
	$scope.data12=[];
	$scope.series12=[];		
	$scope.series112=[];
function generateSubReportData12(key, value) {
	let subReportMap12 = [];
for (const catDtl of $scope.companyWiseExActualDeMobiliz) {			
	let mapKey;
	let mapValue;
	let number;
	let number1;
		mapKey = catDtl.expectedDeMobDate;
		mapValue = catDtl.expectedDeMobDate;
		number=catDtl.number;
		number1=catDtl.number;
	if (!subReportMap12[mapKey]) {		
		subReportMap12[mapKey] = {
			"mapKey": mapKey,
			"mapValue": mapValue,
			"number": number,
			"number1": number
		};
	  }			
}		
	setGraphData12(subReportMap12);			
};

function setGraphData12(subReportMap12) {
	$scope.new1 = $filter('unique')($scope.companyWiseExActualDeMobiliz,'cmpName');
	for(let i=0;i<$scope.new1.length;i++)
	{
	$scope.series112=$scope.new1[i].cmpName;
	$scope.series12.push($scope.series112);
	}	
	for (const index in subReportMap12) {
	$scope.number12.push(subReportMap12[index].number);
	$scope.number112.push(subReportMap12[index].number);
	$scope.labels12.push($filter('date')(( subReportMap12[index].mapValue),"dd-MMM-yyyy"));
}
	for(let i=0;i<$scope.series12.length;i++)
		{
			$scope.data12.push($scope.number12);
		}
	initGraph12();
};

$scope.data12= [];
$scope.labels12= [];
function initGraph12() {
	$scope.compExpSeries = $scope.series12;
	$scope.compExpLabels = $scope.labels12;
	$scope.datasetOverride = new Array();
	$scope.chart_type = 'bar';
	chartService.defaultBarInit($scope.yAxislabels);
	$scope.options.scales.xAxes[0].stacked = false;
	$scope.options.scales.yAxes[0].stacked = false;
};
	// function setGraphData(subReportsMap) {
	// 	$scope.labels = new Array();
	// 	$scope.data = new Array();
	// 	let obj = null;
	// 	const direct = new Array();
	// 	const inDirect = new Array();
	// 	$scope.subReportData = new Array();
	// 	const total = { 'Direct': 0, 'Indirect': 0 };
	// 	for(const key in subReportsMap){
	// 		obj = subReportsMap[key];
	// 		direct.push(obj.direct);
	// 		total.Direct += obj.direct;
			//inDirect.push(obj[])
			//###################################################
	// 	}
	// }
	$scope.getUserProjects = function () {
			var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
			userProjectSelection.then(function (data) {
				$scope.searchProject = {};
				$scope.searchProject = data.searchProject;
				$scope.selectedProjIds = data.searchProject.projIds;
				employeeDetails = [];
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
			});
		};
		
		$scope.getCompanyList = function () {
			var companyReq = {
				"status": 1
			}
			var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
			companyPopUp.then(function (data) {
				$scope.companyNameDisplay = data.selectedCompanies.companyName;
				$scope.selectedCompanyIds = data.selectedCompanies.companyIds;
				employeeDetails = [];
			})
		};
		
		$scope.clickForwardempData = function(empDatamoreFlag){
			if($scope.empDatamoreFlag < 1){
				$scope.empDatamoreFlag = empDatamoreFlag + 1;
			}
		}, $scope.clickBackwardempData = function(empDatamoreFlag){
			if($scope.empDatamoreFlag > 0){
				$scope.empDatamoreFlag = empDatamoreFlag - 1;
			}
		},
		
		$scope.go = function(employeeDetails, indexValue, emp, value){
			angular.forEach(employeeDetails, function(employeeDetail, index){
				if(indexValue != index){
					employeeDetail.selected = false;
				}
				if(employeeDetail.selected){
					employee.selected = false;
				}
			});
		}
		
		$scope.getEmployeeDetails = function () {
			$scope.resetEmployeeDetails1();
			if($scope.selectedProjIds.length <= 0){
				GenericAlertService.alertMessage("Please select the Project", 'Warning');
				return;
			}
			/*if($scope.selectedCompanyIds.length <= 0){
				GenericAlertService.alertMessage("Please select the Company", 'Warning');
				return;
			}
			if($scope.employmentsType == ''){
				GenericAlertService.alertMessage('Please select the Employee Type', "Warning");
				return;
			}
			if($scope.genderType == ''){
				GenericAlertService.alertMessage('Please select the Gender Type', "Warning");
				return;
			}
			if($scope.employmentStatus == ''){
				GenericAlertService.alertMessage('Please select Status Type', "Warning");
				return;
			}*/
			if (new Date($scope.fromDate) > new Date($scope.toDate)) {
				GenericAlertService.alertMessage("Enrollement From Date should be Enrollement Less than To Date", 'Warning');
				return;
			}
			if(new Date($scope.expectedDemobFromDate) > new Date($scope.expectedDemobToDate)){
				GenericAlertService.alertMessage("Expected Mobilisation FromDate should be Expected Mobilisation Less than To Date",'Warning');
				return;
			}
			if(new Date($scope.actualDemobFromDate) > new Date($scope.actualDemobToDate)){
				GenericAlertService.alertMessage("Actual De-Mobilisation FromDate should be Actual De-Mobilisation Less than To Date",'Warning');
				return;
			}
			var req = {
				"projIds": $scope.selectedProjIds,
				"cmpIds": $scope.selectedCompanyIds,
				"enrollFromDate": $scope.fromDate,
				"enrollToDate": $scope.toDate,
				"employmentType": $scope.employmentsType,
				"gender": $scope.genderType,
				"currentStatus": $scope.employmentStatus,
				"expectedDeMobFromDate": $scope.expectedDemobFromDate,
				"expectedDeMobToDate": $scope.expectedDemobToDate,
				"actualDeMobFromDate": $scope.actualDemobFromDate,//actualDeMobToDate
				"actualDeMobToDate": $scope.actualDemobToDate
			//	"subReportType": $scope.subReport.code 
			}
			console.log(req);
			ManpowerReportService.getManpowerEmployeeDetail(req).then(function (data){
				console.log(data);
				$scope.employeeDetails = data;
				
				console.log($scope.employeeDetails)
				console.log($scope.employeeDetails.length <= 0)
				$scope.gridOptions.data = angular.copy($scope.employeeDetails)
				if($scope.subReport && $scope.subReport.code){
					$scope.changeSubReport();
				}
				if(employeeDetails.length <= 0){
					GenericAlertService.alertMessage("Manpower Employee Details not available for the Search Criteria", 'Warning');
					return;
				}
			},function (error){
				GenericAlertService.alertMessage("Error occured while getting Employee Details", 'Error');
				return;
			});
		};
		
		
		$scope.resetEmployeeDetails1 = function () {
			$scope.number2 = [];
            $scope.labels2 = [];
		    $scope.data2=[];
		    $scope.series2=[];		
			$scope.data=[];
			$scope.labels=[];
			$scope.series2=[];
			$scope.number1 = [];
	        $scope.number2 = [];
            $scope.series22=[];
	        $scope.series2=[];
			$scope.dataone=[];
		    $scope.numberone = [];
		    $scope.labelsone = [];
	        $scope.numberone1 = [];
			$scope.data1 = [];	
			$scope.seriesone2=[];
			$scope.seriesone=[];
			$scope.OneSeries = [];
			$scope.OneLabels =[];
			$scope.number1 = [];
			$scope.series3=[];				
			$scope.number3 = [];	       
            $scope.labels3 = [];
		    $scope.data3=[];
			$scope.pTradeSeries = [];
			$scope.pTradeLabels = [];
			$scope.data4=[];
			$scope.series44=[];
			$scope.series4=[];
		    $scope.number4 = [];
		    $scope.labels4 = [];
			$scope.cCAtSeries =[];
			$scope.cCAtLabels =[];
            $scope.data5 = [];
		    $scope.number5 = [];
            $scope.labels5 = [];
            $scope.series5=[];		
	        $scope.compTradeSeries =[];
			$scope.compTradeLabels =[];
		    $scope.number6= [];
            $scope.labels6 = [];
		    $scope.data6=[];
            $scope.series6=[];		
            $scope.compUnionSeries=[];
			$scope.compUnionLabels=[];
			$scope.number7 = [];
            $scope.series7=[];	
            $scope.data7=[];
            $scope.labels7=[];
            $scope.projMobSeries =[];
			$scope.projMobLabels =[];
			$scope.number8 =[];
            $scope.labels8 =[];
		    $scope.data8 =[];
		    $scope.series8 =[];		
	        $scope.compMobSeries =[];
			$scope.compMobLabels =[];
			$scope.number9 = [];
            $scope.labels9 = [];
	    	$scope.data9=[];
		    $scope.series9=[];		
            $scope.projActSeries = [];
			$scope.projActLabels = [];
			$scope.number10 = [];
            $scope.labels10 = [];
		    $scope.data10=[];
		    $scope.series10=[];		
            $scope.compActSeries =[];
			$scope.compActLabels =[];
			$scope.number11 = [];
            $scope.labels11 = [];
		    $scope.data11=[];
	     	$scope.series11=[];		
            $scope.projExpSeries = [];
			$scope.projExpLabels = [];
			$scope.number12 = [];
            $scope.labels12 = [];
		    $scope.data12=[];
		    $scope.series12=[];		
            $scope.compExpSeries =[];
			$scope.compExpLabels = [];
		};
		$scope.clearSubReportDetails = function () {
			$scope.plantIdleHrsDetails = [];
			$scope.type = "";
			$scope.subReportCode = "";
			$scope.subReportName = "";
			$scope.periodicSubReportCode = "";
			$scope.subReport = 'None';
		};
		
		$scope.resetEmployeeDetails = function () {
			$scope.employeeDetails = [];
			$scope.searchProject = {};
			$scope.selectedProjIds = [];
			$scope.selectedCompanyIds = [];
			$scope.companyNameDisplay = null;
			$scope.employmentsType = [];
			$scope.type = null;
			$scope.genderType = [];
			$scope.category = null;
			$scope.employmentStatus = [];
			$scope.status = null;
			$scope.subReport = null;
			
			$scope.fromDate = null;
			$scope.toDate = null;
			$scope.expectedDemobFromDate = null;
			$scope.expectedDemobToDate = null;
			$scope.actualDemobFromDate = null;
			$scope.actualDemobToDate = null;
		};
}]);