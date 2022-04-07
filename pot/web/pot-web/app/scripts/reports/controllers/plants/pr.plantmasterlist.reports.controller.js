'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("plantmasterlist", {
		url: '/plantmasterlist',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/reports/plants/plantmasterlist.html',
				controller: 'PlantMasterListController'
			}
		}
	})
}]).controller("PlantMasterListController", ["$rootScope", "$scope", "$q", "$state","$filter", "$location", "ngDialog", "PlantDetailsFactory", "ProcureCategoryMultipleFactory", "CompanyMultiSelectFactory", "MultiProjPlantMasterDetailsFactory", "PlantRegisterService", "GenericAlertService", "generalservice", "EpsProjectMultiSelectFactory", "ManpowerReportService", "PlantReportService", "MultipleCrewSelectionFactory", "ProjCostCodeService", "stylesService", "ngGridService", "uiGridGroupingConstants","uiGridConstants", "chartService", function ($rootScope, $scope, $q, $state, $filter, $location, ngDialog, PlantDetailsFactory, ProcureCategoryMultipleFactory, CompanyMultiSelectFactory, MultiProjPlantMasterDetailsFactory, PlantRegisterService,
	GenericAlertService, generalservice, EpsProjectMultiSelectFactory, ManpowerReportService, PlantReportService, MultipleCrewSelectionFactory, ProjCostCodeService, stylesService, ngGridService, uiGridGroupingConstants, uiGridConstants, chartService) {

	$scope.plantRegisterDtlTOs = [];
	chartService.getChartMenu($scope);
	$scope.stylesSvc = stylesService;
	$scope.parseFloat = parseFloat;
	$scope.subReportCode = "";
	$scope.selectedProjIds = [];
	$scope.plantDetails = [];
	var editPlantData = [];
	$scope.selectedCompanyIds = [];
	$scope.selectedCrewIds = [];
	var projId = 0;
	$rootScope.selectedPlantData = null;
	$scope.isDisabled = false;
    $scope.ownerCmpName=[];
	$scope.searchProject = {};
	$scope.currentStatus = [];
	$scope.plantDatamoreFlag = 0;
	$scope.prPlantCurrentStatus = [];
	$scope.subReport = "None";
	$scope.data = [];
	$scope.labels = [];
	$scope.yAxislabels = 'Hours';
	$scope.contractStatus = "";
	$scope.userProjMap = [];
    $scope.plantCurrentStatus= generalservice.plantCurrentStatus;
    console.log($scope.plantCurrentStatus);

    /*let todayDate = new Date();
		let lastMonthDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
		$scope.purchaseCommissionefromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.purchaseCommissionetoDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.deMobilisationfromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.deMobilisationtoDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.actualMobilisationfromDate = $filter('date')(angular.copy(lastMonthDate), "dd-MMM-yyyy");
		$scope.actualMobilisationtoDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");
		$scope.todayDate = $filter('date')(angular.copy(todayDate), "dd-MMM-yyyy");	*/
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
		};
	$scope.subReports = [{
			name: 'Project Wise and Classification Wise Plant Count',
			code: "projectwise"
		},{
			name: 'Company Wise and Classification Wise Employees',
			code: "companywiseclassification"
		}, {
			name: 'Project Wise Mobilisation - Periodical Count',
			code: "pwmobilisation"
		}, {
			name: 'Company Wise Mobilisation - Periodical Count',
			code: "cwmobilisation"
		}, {
			name: 'Project Wise actual De-Mobilisation - Periodical Count',
			code: "pwactual"
		}, {
			name: 'Company Wise actual De-Mobilisation - Periodical Count',
			code: "cwactual"
		}, {
			name: 'Project Wise Excepted De-Mobilisation - Periodical Count',
			code: "pwexcepted"
		}, {
			name: 'Company Wise Excepted De-Mobilisation - Periodical Count',
			code: "cwexcepted"
		}];
		$scope.changeSubReport = function () {
			$scope.resetPlantDetails1();
			if ($scope.subReport.code == "projectwise") {
				if ($scope.subReport.code == "projectwise") {
						/*Project wise  and  Plant Classification  wise  Plants count*/
			   $scope.projectWisePlantClassification = [];
			   $scope.parentNames=[];
               $scope.projNames=[];
               $scope.sum1=0;
               $scope.sum2=0;
               $scope.num=[];
                        for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.projNames = $filter('unique')($scope.plantRegisterDtlTOs[i].plantRegProjTO,'name');
                            $scope.parentNames.push($scope.projNames);
                        }        
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }                        
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');

						for(var i=0;i<$scope.newduplicate.length;i++){
							
							$scope.filt = [];
							let projName = $scope.newduplicate[i];
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(projName == $scope.plantRegisterDtlTOs[j].plantRegProjTO.name){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'plantClassMstrName');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantClassMstrName;
								let value2 = $scope.filtereddata[k].cmpName;
								let value1 = $scope.filtereddata[k].plantClassMstrCode;
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantClassMstrName){
										count++;										
									}									
								}
								$scope.projectWisePlantClassification.push({"projName":projName,"value":value,"number":count,"value1":value1,"value2":value2});						
							}
						}
		        	}

	            $scope.projectWisePlant = $filter('unique')($scope.projectWisePlantClassification,'value');
                $scope.projNames = $filter('unique')($scope.parentNames,'name');
                generateSubReportData1(" "," ");
			}
			
			else if ($scope.subReport.code == "companywiseclassification") {
				if ($scope.subReport.code == "companywiseclassification") {
					 /*Compny  wise    Classificatioi Wise  Plant  count*/
                        $scope.company1 = [];
                        $scope.companyNames=[];
                        $scope.cmpNames =[];
						for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.companyNames = $filter('unique')($scope.plantRegisterDtlTOs,'cmpName');
                            $scope.cmpNames.push($scope.companyNames);
                        }
                        $scope.newduplicate = $filter('unique')($scope.cmpNames,'cmpName');
                        $scope.compaNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.companyNames.length;i++){
                            $scope.names= $scope.companyNames[i].cmpName;                        
                            $scope.compaNames.push($scope.names);                            
                        }                
                        $scope.newduplicate = $filter('unique')($scope.compaNames,'cmpName');
						for(var i=0;i<$scope.newduplicate.length;i++){						
							$scope.filt = [];
							let compNamesValues = $scope.newduplicate[i];
							console.log(compNamesValues);
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(compNamesValues == $scope.plantRegisterDtlTOs[j].cmpName){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}							
							$scope.filtereddata = $filter('unique')($scope.filt,'plantClassMstrName');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantClassMstrName;
								let value1 = $scope.filtereddata[k].plantClassMstrCode;
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantClassMstrName){
										count++;
									}
								}
								$scope.company1.push({"compNamesValues":compNamesValues,"value":value,"number":count,"value1":value1});
							}
						}
							$scope.filtereddata1 = $filter('unique')($scope.filt,'plantClassMstrName');
							$scope.company2=$scope.company1;
				}	
				     $scope.companyWisePlant = $filter('unique')($scope.company2,'value');
					 generateSubReportData2(" "," ");							
			}
			else if ($scope.subReport.code == "pwmobilisation") {
				if ($scope.subReport.code == "pwmobilisation") {
					$scope.company = [];
							$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.projNames = $filter('unique')($scope.plantRegisterDtlTOs[i].plantRegProjTO,'name');
                            $scope.parentNames.push($scope.projNames);
                        }
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
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(projName == $scope.plantRegisterDtlTOs[j].plantRegProjTO.name){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}
							
							$scope.filtereddata = $filter('unique')($scope.filt,'mobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantRegProjTO.mobDate;
								console.log(value);
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantRegProjTO.mobDate){
										count++;
									}									
								}
								$scope.company.push({"projName":projName,"value":value,"number":count});
							}
						}
				  }
		             $scope.commob=$scope.company;
		             generateSubReportData3(" "," ");
			}
			else if ($scope.subReport.code == "cwmobilisation") {
				if ($scope.subReport.code == "cwmobilisation") {
					$scope.company3 = [];
					$scope.companyNames=[];
                        $scope.cmpNames =[];
						for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.companyNames = $filter('unique')($scope.plantRegisterDtlTOs,'cmpName');
                            $scope.cmpNames.push($scope.companyNames);
                        }
                        $scope.newduplicate = $filter('unique')($scope.cmpNames,'cmpName');
                        $scope.compaNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.companyNames.length;i++){
                            $scope.names= $scope.companyNames[i].cmpName;
                            console.log($scope.names);
                            $scope.compaNames.push($scope.names);                            
                        }                  
                        $scope.newduplicate = $filter('unique')($scope.compaNames,'cmpName');
						for(var i=0;i<$scope.newduplicate.length;i++){						
							$scope.filt = [];
							let compNamesValues = $scope.newduplicate[i];
							console.log(compNamesValues);
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(compNamesValues == $scope.plantRegisterDtlTOs[j].cmpName){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}
							
							$scope.filtereddata = $filter('unique')($scope.filt,'mobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantRegProjTO.mobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantRegProjTO.mobDate){
										count++;
									}
									
								}
								$scope.company3.push({"compNamesValues":compNamesValues,"value":value,"number":count});
							}
						}
				}
				 $scope.company4=$scope.company3;
				 generateSubReportData4(" "," ");
			}
			else if ($scope.subReport.code == "pwactual") {
				if ($scope.subReport.code == "pwactual") {
					$scope.company4 = [];
					$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.projNames = $filter('unique')($scope.plantRegisterDtlTOs[i].plantRegProjTO,'name');
                            $scope.parentNames.push($scope.projNames);
                        }
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
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(projName == $scope.plantRegisterDtlTOs[j].plantRegProjTO.name){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}						
							$scope.filtereddata = $filter('unique')($scope.filt,'deMobDate');
							console.log($scope.filtereddata);
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantRegProjTO.deMobDate;
								console.log(value);
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantRegProjTO.deMobDate){
										count++;
									}									
								}
								$scope.company4.push({"projName":projName,"value":value,"number":count});
							}
						}
				}
				 $scope.comp5=$scope.company4;
				 generateSubReportData5(" "," ");	     
			}
			else if ($scope.subReport.code == "cwactual") {
				if ($scope.subReport.code == "cwactual") {
					$scope.company5 = [];
					$scope.companyNames=[];
                        $scope.cmpNames =[];
                        
						for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.companyNames = $filter('unique')($scope.plantRegisterDtlTOs,'cmpName');
                            $scope.cmpNames.push($scope.companyNames);
                        }
                        $scope.newduplicate = $filter('unique')($scope.cmpNames,'cmpName');
                        $scope.compaNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.companyNames.length;i++){
                            $scope.names= $scope.companyNames[i].cmpName;
                            console.log($scope.names);
                            $scope.compaNames.push($scope.names);                            
                        }                  
                        $scope.newduplicate = $filter('unique')($scope.compaNames,'cmpName');
						for(var i=0;i<$scope.newduplicate.length;i++){
							
							$scope.filt = [];
							let compNamesValues = $scope.newduplicate[i];
							console.log(compNamesValues);
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(compNamesValues == $scope.plantRegisterDtlTOs[j].cmpName){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}
							
							$scope.filtereddata = $filter('unique')($scope.filt,'deMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantRegProjTO.deMobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantRegProjTO.deMobDate){
										count++;
									}
									
								}
								$scope.company5.push({"compNamesValues":compNamesValues,"value":value,"number":count});
							}
						}					
				}			
			    $scope.comp6 = $filter('unique')($scope.company5,'value');
				 generateSubReportData6(" "," ");
			}
			else if ($scope.subReport.code == "pwexcepted") {
				if ($scope.subReport.code == "pwexcepted") {
					$scope.company6 = [];
					$scope.parentNames=[];
                        $scope.projNames=[];
                        for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.projNames = $filter('unique')($scope.plantRegisterDtlTOs[i].plantRegProjTO,'name');
                            $scope.parentNames.push($scope.projNames);
                        }
                        $scope.newduplicate = $filter('unique')($scope.parentNames,'name');
                        $scope.projectNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.parentNames.length;i++){
                            $scope.names= $scope.parentNames[i].name;
                            $scope.projectNames.push($scope.names);                            
                        }                                           
                        $scope.newduplicate = $filter('unique')($scope.projectNames,'name');                     
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let projName = $scope.newduplicate[i];
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(projName == $scope.plantRegisterDtlTOs[j].plantRegProjTO.name){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}
							
							$scope.filtereddata = $filter('unique')($scope.filt,'anticipatedDeMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantRegProjTO.anticipatedDeMobDate;							
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantRegProjTO.anticipatedDeMobDate){
										count++;
									}								
								}
								$scope.company6.push({"projName":projName,"value":value,"number":count});
							}						
						}					
				}
				  $scope.comp7 = $scope.company6;
				 generateSubReportData7(" "," ");
				
			}
			else if ($scope.subReport.code == "cwexcepted") {
				if ($scope.subReport.code == "cwexcepted") {
					$scope.company7 = [];
					$scope.companyNames=[];
                        $scope.cmpNames =[];
						for(var i=0;i<$scope.plantRegisterDtlTOs.length;i++){
                            $scope.companyNames = $filter('unique')($scope.plantRegisterDtlTOs,'cmpName');
                            $scope.cmpNames.push($scope.companyNames);
                        }
                        $scope.newduplicate = $filter('unique')($scope.cmpNames,'cmpName');
                        $scope.compaNames=[];
                        $scope.names =[];
                        for(var i=0;i<$scope.companyNames.length;i++){
                            $scope.names= $scope.companyNames[i].cmpName;
                            console.log($scope.names);
                            $scope.compaNames.push($scope.names);                            
                        }                  
                        $scope.newduplicate = $filter('unique')($scope.compaNames,'cmpName');
						for(var i=0;i<$scope.newduplicate.length;i++){							
							$scope.filt = [];
							let compNamesValues = $scope.newduplicate[i];
							console.log(compNamesValues);
							for(var j=0;j<$scope.plantRegisterDtlTOs.length;j++){
								if(compNamesValues == $scope.plantRegisterDtlTOs[j].cmpName){
									$scope.filt.push($scope.plantRegisterDtlTOs[j]);
								}
							}
							
							$scope.filtereddata = $filter('unique')($scope.filt,'anticipatedDeMobDate');
							for(var k=0;k<$scope.filtereddata.length;k++){
								var count = 0;
								let value = $scope.filtereddata[k].plantRegProjTO.anticipatedDeMobDate;
								for(var m=0;m<$scope.filt.length;m++){
									if(value == $scope.filt[m].plantRegProjTO.anticipatedDeMobDate){
										count++;
									}
									
								}
								$scope.company7.push({"compNamesValues":compNamesValues,"value":value,"number":count});
							}
						}
				  }
		          $scope.comp8 = $scope.new1 = $filter('unique')($scope.company7,'value');
		          generateSubReportData8(" "," ");
			}

			if ($scope.subReport && $scope.subReport != "None") {
				$scope.type = 'chartable';
				$scope.subReportName = $scope.subReport.name;
				$scope.subReportCode = $scope.subReport.code
/*				prepareSubReport();
*/			} else {
				$scope.plantDetails = [];
				$scope.type = '';
				$scope.subReportName = '';
				$scope.subReportCode = '';
				$scope.getPlantDetails();
			}
		};	
    $scope.getPlantList = function () {
			var plantReq = {
				"status": 1,
				"projIds": $scope.searchProject.projIds
			}
			var plantPopUp = MultiProjPlantMasterDetailsFactory.getPlantMasterDetails(plantReq);
			plantPopUp.then(function (data) {
				$scope.plantNameDisplay = data.selectedPlants;
				console.log($scope.plantNameDisplay);
				$scope.selectedPlantIds = data.selectedPlants.selectedPlantIds;
				
			})
		};
		
		$scope.plantCategoryDetails = function () {
			$scope.plantCategoryReq = {
				"status": 1,
				"procureClassName": "Plants"
			};
			var plantCategoryDetailsPopup = ProcureCategoryMultipleFactory.getProcureCategoryDetails($scope.plantCategoryReq);
			plantCategoryDetailsPopup.then(function (data) {
				console.log(data);
				//	$scope.procurecatgId.plantName =[];						
				$scope.procurecatgId = data.selectedRecord;
				//	console.log($scope.procurecatgId.plantName);
				$scope.procurecatgName = data.selectedRecord.desc;
				console.log($scope.procurecatgId, "gl--3");

				/*$scope.procurecatgCode = data.selectedRecord.code;
				$scope.procurecatgName = data.selectedRecord.desc;*/
			}, function (error) {
				GenericAlertService.alertMessage("Error occured while getting project plant classes", 'Error');
			});
		},	
	$scope.clickForwardPlantData = function (plantDatamoreFlag1) {
		if ($scope.plantDatamoreFlag < 1) {
			$scope.plantDatamoreFlag = plantDatamoreFlag1 + 1;
		}
	}, $scope.clickBackwardPlantData = function (plantDatamoreFlag1) {
		if ($scope.plantDatamoreFlag > 0) {
			$scope.plantDatamoreFlag = plantDatamoreFlag1 - 1;
		}
	}
	$scope.getCompanyList = function () {
		var companyReq = {
			"status": 1
		}
		var companyPopUp = CompanyMultiSelectFactory.getCompanies(companyReq);
		companyPopUp.then(function (data) {
		//	$scope.companyNameDisplay = [];
			$scope.companyNameDisplay = data.selectedCompanies;
			console.log($scope.companyNameDisplay);
			$scope.selectedCompanyIds = data.selectedCompanies.companyIds;			
		})
	};
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			// $scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.selectedProjIds = data.searchProject.projIds;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project", 'Error');
		});
	}, $scope.setSelected = function (row) {
		$scope.rowSelect = row;
		$scope.selectedRow = row;
	};
	$scope.getCrewList = function () {
		if ($scope.selectedProjIds == undefined || $scope.selectedProjIds == null) {
			GenericAlertService.alertMessage("Please select project to get crews", 'Warning');
			return;
		}
		var crewReq = {
			"status": 1,
			"projIds": $scope.selectedProjIds
		};
		var crewSerivcePopup = MultipleCrewSelectionFactory.crewPopup(crewReq);
		crewSerivcePopup.then(function (data) {
			$scope.crewNameDisplay = data.selectedCrews.crewName;
			$scope.selectedCrewIds = data.selectedCrews.crewIds;
			$scope.plantDetails = [];
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
		});
	};
	$scope.resetSearchData = function () {
	//	$scope.selectedRow = null;
		$scope.status ="On Transfer";
		$scope.searchProject = {};
		$scope.companyNameDisplay =[];
		$scope.procurecatgId =[];
		$scope.plantNameDisplay =[];
		$scope.purchaseCommissionefromDate = undefined;
		$scope.purchaseCommissionetoDate = undefined;
		$scope.deMobilisationfromDate = undefined;
		$scope.deMobilisationtoDate = undefined;
		$scope.actualMobilisationfromDate = undefined;
		$scope.actualMobilisationtoDate = undefined;
		$scope.plantRegisterDtlTOs = [];
	},
        
	$scope.currentPlantStatus = [{
		id: 1,
		name: "Active",
		code: "On Transfer",
	},{
		id: 2,
		name: "Return to Supplier/Plant Owner",
		code: "Return to Supplier",
	},{
		id: 3,
		name: "Salvaged",
		code: "Salvaged",
	}];
	
	 	$scope.getSearchPlantRegisters = function (click,status) {
			$scope.status = status;
			console.log($scope.status);
			$scope.currentTab = '';
			$scope.currentPlantDetailsTab = '';
			$scope.selectedRow = null;
			$rootScope.selectedPlantData = null;
	//		$scope.procurecatgId.selectedPlantIds = null;
			// editPlantData = [];
			$scope.plantRegisterDtlTOs = [];
		var purchaseCommissionefromDate = new Date($scope.purchaseCommissionefromDate);
	    var purchaseCommissionetoDate = new Date($scope.purchaseCommissionetoDate);
        var deMobilisationfromDate = new Date($scope.deMobilisationfromDate);
	    var deMobilisationtoDate = new Date($scope.deMobilisationtoDate);
        var actualMobilisationfromDate = new Date($scope.actualMobilisationfromDate);
	    var actualMobilisationtoDate = new Date($scope.actualMobilisationtoDate);
					var deferred = $q.defer();
					// editPlantData = [];
					$scope.selectedRow = null;
					var projIds = null;
					if ($scope.searchProject.projIds != null && $scope.searchProject.projIds.length > 0) {
			         projIds = [];
			         projIds = $scope.searchProject.projIds;
		            }
					var getPlantRegisterReq = {		
						"status": "1",
						"plantCurrentStatus" : $scope.status,
						"projIds": $scope.searchProject.projIds,
						"purchaseCommissionefromDate" : $filter('date')((purchaseCommissionefromDate), "dd-MMM-yyyy"),
						"purchaseCommissionetoDate" : $filter('date')((purchaseCommissionetoDate), "dd-MMM-yyyy"),
						"deMobilisationfromDate" : $filter('date')((deMobilisationfromDate), "dd-MMM-yyyy"),
						"deMobilisationtoDate" : $filter('date')((deMobilisationtoDate), "dd-MMM-yyyy"),
						"actualMobilisationfromDate" : $filter('date')((actualMobilisationfromDate), "dd-MMM-yyyy"),
						"actualMobilisationtoDate" : $filter('date')((actualMobilisationtoDate), "dd-MMM-yyyy"),
						"companyNameDisplay": $scope.companyNameDisplay.companyIds,
						"procurecatgId" : $scope.procurecatgId.selectedPlantIds,
						"plantNameDisplay" : $scope.plantNameDisplay.selectedPlantIds
					};
					console.log(getPlantRegisterReq);
					if(click=='click'){
						if ($scope.searchProject.projIds == null || $scope.searchProject.projIds == undefined) {
							GenericAlertService.alertMessage("Please select Project", "Warning");
							return;
						}
					}
					PlantRegisterService.getPlantRegistersOnLoad(getPlantRegisterReq).then(function (data) {
						$scope.plantRegisterDtlTOs = data.plantRegisterDtlTOs;
						console.log($scope.plantRegisterDtlTOs);
						$scope.assertTypes = data.assertTypes;	
				deferred.resolve();
				if (click == 'click') {
					if ($scope.plantRegisterDtlTOs.length <= 0) {
						GenericAlertService.alertMessage("Plant & Equipment details not available for the search criteria", "Warning");
						return;
					}

				}
			}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting plant details", "Error");
					});
					if ($scope.searchProject.projIds != null && $scope.searchProject.projIds != undefined) {
						$scope.disableFlag = 1;
					} else {
						$scope.disableFlag = 2;
					}
					return deferred.promise;
		}

	$scope.getPlantCurrentStatus = function (plant) {
		var req = {
			"status": 1,
			"projIds": $scope.searchProject.projIds,
			"plantId": plant.id
		};
		PlantRegisterService.getPlantCurrentStatus(req).then(function (data) {
			$scope.prPlantCurrentStatus = data.plantCurrentStatusList;
			$scope.userProjMap = data.userProjMap;

		}, function (error) {
			if (error.status != undefined && error.status != null) {
				GenericAlertService.alertMessage(error.message, error.status);
			} else {
				GenericAlertService.alertMessage("Error occured while selecting the plant details", 'Error');
			}
		});

	}
		$scope.go = function (plantRegisterDtlTOs, indexValue, plant) {
			console.log('plantRegisterDtlTOs  ', plantRegisterDtlTOs);
			console.log('indexValue  ', indexValue);
			console.log('plant  ', plant);
			angular.forEach(plantRegisterDtlTOs, function (plant, index) {
				if (indexValue != index) {
					plant.selected = false;
				}
				if (plant.selected) {
					plant.selected = false;
				}
			});
			$rootScope.selectedPlantData = plant;
			$scope.moreFlag = 0;
			$scope.setSelected(indexValue);
			if (plant.assetType === "Existing Plant")
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[1]);
			else
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[2]);
			    $rootScope.$broadcast('defaultPayableRatesTab');

			if (plant.assetType === "Existing Plant")
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[1]);
			else
				$scope.onClickPlantDetailsTab($scope.plantDetailsTabs[0]);
			$rootScope.$broadcast('defaultPlantProjDeliveryTab');
		};		
		$scope.plantRowSelect = function (position, plants) {
			console.log(plants, "gl--9")
			if (plants.selected) {
				console.log(plants.selected, "gl--10");
				if ($scope.selectedRow) {
					$scope.selectedRow = null;
					$rootScope.$broadcast($scope.currentTab.resetMethod);
				}
				$scope.selectedRow = null;
				editPlantData.push(plants);
				$rootScope.$broadcast($scope.currentTab.resetMethod);
			} else {
				editPlantData.splice(editPlantData.indexOf(plants), 1);
				$rootScope.$broadcast($scope.currentTab.resetMethod);
			}
		}
	$scope.addplantList = function (actionType) {
		if (actionType == 'Edit' && editPlantData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify ", 'Warning');
			return;
		}
		var plantdetailspopup = PlantDetailsFactory.plantDetailsPopUp($scope.assertTypes, actionType, editPlantData);
		plantdetailspopup.then(function (data) {
			editPlantData = [];
			$scope.tableData = data.plantRegisterDtlTOs;
			$scope.plantRegisterDtlTOs = data.plantRegisterDtlTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching Plant  details", 'Error');
		});

	}, $scope.deletePlantRegisters = function () {
		if (editPlantData.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		var deleteIds = [];
		$scope.nondeleteIds = [];
		if ($scope.selectedAll) {
			$scope.tableData = [];
		} else {
			angular.forEach(editPlantData, function (value, key) {
				if (value.id) {
					deleteIds.push(value.id);
				}
			});
			var req = {
				"plantRegIds": deleteIds,
				"status": 2
			};

			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function () {
				PlantRegisterService.deletePlantRegisters(req).then(function (data) {
					// $scope.resetData();
					GenericAlertService.alertMessage('Plant Registration Details are  Deactivated Successfully', 'Info');
					editPlantData = [];
					// editTableData = [];
					$scope.deleteIds = [];
                   $scope.getSearchPlantRegisters();
				});

				angular.forEach(editPlantData, function (value, key) {
					$scope.tableData.splice($scope.tableData.indexOf(value), 1);
				}, function (error) {
					GenericAlertService.alertMessage('Plant Registration Details are failed to Deactivate', "Error");
				});


			}, function (data) {
				angular.forEach(editPlantData, function (value) {
					value.selected = false;
				})
			})
		}
	}

	$scope.$on("LogBookTab", function () {
		projId = $scope.searchProject.projId;
		if (projId == null || projId == undefined) {
			return;
		} else {
			$scope.getSearchPlantRegisters(projId);
			$scope.setSelected($scope.rowSelect);
			$scope.currentPlantDetailsTab = 'views/projresources/projplantreg/planttabs/plantreglogbooktabs.html'
		}
	});

	$rootScope.$on('plantRefresh', function (event, value) {
		var selectedIndex = $scope.selectedRow;
		console.log(selectedIndex);
		$scope.getSearchPlantRegisters().then(function () {			
			$scope.go($scope.plantRegisterDtlTOs, selectedIndex, $scope.plantRegisterDtlTOs[selectedIndex], value);
		});
	});
	 /*Project Wise and Classification Wise Plant Count 1*/
	 $scope.number1 = [];
	 $scope.labels1 = [];
	 $scope.data1=[];
	 $scope.series1=[];		
	 $scope.series11=[];
 function generateSubReportData1(key, value) {
	 $scope.projectWisePlant1 = $filter('unique')($scope.projectWisePlantClassification,'projName');
	 $scope.projNames = $filter('unique')($scope.parentNames,'name');
	 let subReportMap1 = [];
 for (const catDtl of $scope.projectWisePlant1) {			
	 let mapKey;
	 let mapValue;
	 let number;
	 let number1;
	 let value;
		 mapKey = catDtl.projName;
		 mapValue = catDtl.projName;
		 value=catDtl.value;
		 number=catDtl.number;
		 number1=catDtl.number;
	 if (!subReportMap1[mapKey]) {		
		 subReportMap1[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "value": value,
			 "number": number,
			 "number1": number
		 };
	   }			
 }		
	 setGraphData1(subReportMap1);			
 };
 function setGraphData1(subReportMap1) {
	 for (const index in subReportMap1) {
	 $scope.number1.push(subReportMap1[index].number);
	 $scope.labels1.push(subReportMap1[index].mapKey);
	 $scope.series1.push(subReportMap1[index].value);
 }
for (var i=0;i<$scope.number1.length;i++)
   {
	  var data = [];
for (var j=0;j<$scope.number1.length;j++)
   {
		data.push($scope.number1[i]);		
   }
		$scope.data1.push(data);
}
	 initGraph1();
 };
 $scope.data1= [];
 $scope.labels1= [];
 function initGraph1() {
	 $scope.projectwiseSeries = $scope.series1;
	 $scope.projectwiseLabels = $scope.labels1;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };
 
 /*Company Wise and Classification Wise Employees2*/		
	 $scope.number2 = [];
	 $scope.labels2 = [];
	 $scope.data2=[];
	 $scope.series2=[];		
	 $scope.series22=[];
 function generateSubReportData2(key, value) {
let subReportMap2 = [];
 for (const catDtl of $scope.companyWisePlant) {			
	 let mapKey;
	 let mapValue;
	 let number;
	 let number1;
	 let value;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
		 number=catDtl.number;
		 number1=catDtl.number;
		 value=catDtl.compNamesValues;
	 if (!subReportMap2[mapKey]) {		
		 subReportMap2[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "number": number,
			 "number1": number,
			 "value":value
		 };
	   }			
 }		
	 setGraphData2(subReportMap2);			
 };
 function setGraphData2(subReportMap2) {		
	 for(let i=0;i<$scope.compaNames.length;i++)
	 {				
	 $scope.labels2.push($scope.compaNames[i]);
	 }	
	 for (const index in subReportMap2) {
	 $scope.number2.push(subReportMap2[index].number);
	 $scope.series2.push(subReportMap2[index].mapKey);			
 }
   for (var i=0;i<$scope.series2.length;i++)
   {
	  var data = [];
for (var j=0;j<$scope.compaNames.length;j++)
   {
		data.push($scope.number2[i]);		
   }
		 $scope.data2.push(data);
}
	 initGraph2();
 };
 $scope.data2= [];
 $scope.labels2= [];
 function initGraph2() {
	 $scope.compSeries = $scope.series2;
	 $scope.compLabels = $scope.labels2;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };

  /*Project Wise Mobilisation - Periodical Count 3*/
		 $scope.number3 =[];
		 $scope.labels3 =[];
		 $scope.data3 =[];
		 $scope.series3 =[];		
		 $scope.series33 =[];
 function generateSubReportData3(key, value) {
	 let subReportMap3 = [];
 for (const catDtl of $scope.commob) {			
	 let mapKey;
	 let mapValue;
	 let date;
	 let number;
	 let number1;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
		 date=catDtl.cmpName;
		 number=catDtl.number;
		 number1=catDtl.number;
	 if (!subReportMap3[mapKey]) {		
		 subReportMap3[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "date": date,
			 "number": number,
			 "number1": number
		 };
	   }			
 }		
	 setGraphData3(subReportMap3);			
 };
 function setGraphData3(subReportMap3) {
	 $scope.new1 = $filter('unique')($scope.commob,'projName');
	 for(let i=0;i<$scope.new1.length;i++)
	 {
	 $scope.series33=$scope.new1[i].projName;
	 $scope.series3.push($scope.series33);
	 }	
	 
	 for (const index in subReportMap3) {
	 $scope.number3.push(subReportMap3[index].number);
	 $scope.labels3.push($filter('date')(( subReportMap3[index].mapValue),"dd-MMM-yyyy"));
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
	 $scope.pwmobSeries = $scope.series3;
	 $scope.pwmobLabels = $scope.labels3;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };
 
 /*Company Wise Mobilisation - Periodical Count 4*/
		 $scope.number4 =[];
		 $scope.labels4 =[];
		 $scope.data4 =[];
		 $scope.series4 =[];		
		 $scope.series44 =[];
 function generateSubReportData4(key, value) {
	 let subReportMap4 = [];
 for (const catDtl of $scope.company4) {			
	 let mapKey;
	 let mapValue;
	 let date;
	 let number;
	 let number1;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
		 date=catDtl.cmpName;
		 number=catDtl.number;
		 number1=catDtl.number;
	 if (!subReportMap4[mapKey]) {		
		 subReportMap4[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "date": date,
			 "number": number,
			 "number1": number
		 };
	   }			
 }		
	 setGraphData4(subReportMap4);			
 };
 function setGraphData4(subReportMap4) {
	 $scope.new1 = $filter('unique')($scope.company4,'compNamesValues');
	 for(let i=0;i<$scope.new1.length;i++)
	 {
	 $scope.series44=$scope.new1[i].compNamesValues;
	 $scope.series4.push($scope.series44);
	 }	
	 
	 for (const index in subReportMap4) {
	 $scope.number4.push(subReportMap4[index].number);
	 $scope.labels4.push($filter('date')(( subReportMap4[index].mapValue),"dd-MMM-yyyy"));
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
	 $scope.cmmobSeries = $scope.series4;
	 $scope.cmmobLabels = $scope.labels4;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };
	 
	 /*Project Wise actual De-Mobilisation - Periodical Count 5*/
		 $scope.number5 =[];
		 $scope.labels5 =[];
		 $scope.data5 =[];
		 $scope.series5 =[];		
		 $scope.series55 =[];
 function generateSubReportData5(key, value) {
	 let subReportMap5 = [];
 for (const catDtl of $scope.comp5) {			
	 let mapKey;
	 let mapValue;
	 let date;
	 let number;
	 let number1;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
		 date=catDtl.cmpName;
		 number=catDtl.number;
		 number1=catDtl.number;
	 if (!subReportMap5[mapKey]) {		
		 subReportMap5[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "date": date,
			 "number": number,
			 "number1": number
		 };
	   }			
 }		
	 setGraphData5(subReportMap5);			
 };
 function setGraphData5(subReportMap5) {
	 $scope.new1 = $filter('unique')($scope.comp5,'projName');
	 for(let i=0;i<$scope.new1.length;i++)
	 {
	 $scope.series55=$scope.new1[i].projName;
	 $scope.series5.push($scope.series55);
	 }	
	 
	 for (const index in subReportMap5) {
	 $scope.number5.push(subReportMap5[index].number);
	 $scope.labels5.push($filter('date')(( subReportMap5[index].mapValue),"dd-MMM-yyyy"));
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
	 $scope.pwactualSeries = $scope.series5;
	 $scope.pwactualLabels = $scope.labels5;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };
	 
	 /*Company Wise actual De-Mobilisation - Periodical Count 6*/			
		 $scope.number6 =[];
		 $scope.labels6 =[];
		 $scope.data6 =[];
		 $scope.series6 =[];		
		 $scope.series66 =[];
 function generateSubReportData6(key, value) {
	 let subReportMap6 = [];
 for (const catDtl of $scope.comp6) {			
	 let mapKey;
	 let mapValue;
	 let date;
	 let number;
	 let number1;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
		 number=catDtl.number;
		 number1=catDtl.number;
	 if (!subReportMap6[mapKey]) {		
		 subReportMap6[mapKey] = {
			 "mapKey": mapKey,
			 "mapValue": mapValue,
			 "date": date,
			 "number": number,
			 "number1": number
		 };
	   }			
 }		
	 setGraphData6(subReportMap6);			
 };
 function setGraphData6(subReportMap6) {
	 $scope.new1 = $filter('unique')($scope.comp6,'compNamesValues');
	 for(let i=0;i<$scope.new1.length;i++)
	 {
	 $scope.series66=$scope.new1[i].compNamesValues;
	 $scope.series6.push($scope.series66);
	 }	
	 
	 for (const index in subReportMap6) {
	 $scope.number6.push(subReportMap6[index].number);
	 $scope.labels6.push($filter('date')((subReportMap6[index].mapValue),"dd-MMM-yyyy"));
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
	 $scope.cwactualSeries = $scope.series6;
	 $scope.cwactualLabels = $scope.labels6;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };
 
 /*Project Wise Excepted De-Mobilisation - Periodical Count 7*/
		 $scope.number7 =[];
		 $scope.labels7 =[];
		 $scope.data7 =[];
		 $scope.series7 =[];		
		 $scope.series77 =[];
 function generateSubReportData7(key, value) {			
	 let subReportMap7 = [];
 for (const catDtl of $scope.comp7) {			
	 let mapKey;
	 let mapValue;
	 let date;
	 let number;
	 let number1;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
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
 function setGraphData7(subReportMap7) {			
	 $scope.new1 = $filter('unique')($scope.comp7,'value');
	 for(let i=0;i<$scope.new1.length;i++)
	 {
	 $scope.series77=$scope.new1[i].projName;
	 $scope.series7.push($scope.series77);
	 }	
	 
	 for (const index in subReportMap7) {
	 $scope.number7.push(subReportMap7[index].number);
	 $scope.labels7.push($filter('date')((subReportMap7[index].mapValue),"dd-MMM-yyyy"));
 }
	 for(let i=0;i<$scope.series7.length;i++)
		 {
			 $scope.data7.push($scope.number7);
		 }
	 initGraph7();
 };
$scope.data7= [];
$scope.labels7= [];
 function initGraph7() {
	 $scope.pwexceptedSeries = $scope.series7;
	 $scope.pwexceptedLabels = $scope.labels7;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };
 
 
 /*Company Wise Excepted De-Mobilisation - Periodical Count 8*/
		 $scope.number8 =[];
		 $scope.labels8 =[];
		 $scope.data8 =[];
		 $scope.series8 =[];		
		 $scope.series88 =[];
 function generateSubReportData8(key, value) {
	 let subReportMap8 = [];
 for (const catDtl of $scope.comp8) {			
	 let mapKey;
	 let mapValue;
	 let date;
	 let number;
	 let number1;
		 mapKey = catDtl.value;
		 mapValue = catDtl.value;
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
	 $scope.new1 = $filter('unique')($scope.comp8,'compNamesValues');
	 for(let i=0;i<$scope.new1.length;i++)
	 {
	 $scope.series88=$scope.new1[i].compNamesValues;
	 $scope.series8.push($scope.series88);
	 }	
	 
	 for (const index in subReportMap8) {
	 $scope.number8.push(subReportMap8[index].number);
	 $scope.labels8.push($filter('date')((subReportMap8[index].mapValue),"dd-MMM-yyyy"));
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
	 $scope.cwexceptedSeries = $scope.series8;
	 $scope.cwexceptedLabels = $scope.labels8;
	 $scope.datasetOverride = new Array();
	 $scope.chart_type = 'bar';
	 chartService.defaultBarInit($scope.yAxislabels);
	 $scope.options.scales.xAxes[0].stacked = false;
	 $scope.options.scales.yAxes[0].stacked = false;
 };	
$scope.resetPlantDetails1 = function () {
	  $scope.datasetOverride = [];
	 $scope.chart_type = [];	
	 $scope.number1 = [];
	 $scope.labels1 = [];
	 $scope.data1=[];
	 $scope.series1=[];		
	 $scope.projectwiseSeries = [];
	 $scope.projectwiseLabels = [];
	 $scope.number2 = [];
	 $scope.number22 = [];
	 $scope.labels2 = [];
	 $scope.data2=[];
	 $scope.series2=[];		
	 $scope.series22=[];
	 $scope.compSeries =[];
	 $scope.compLabels =[];
	 $scope.number3 =[];
	 $scope.labels3 =[];
	 $scope.data3 =[];
	 $scope.series3 =[];		
	 $scope.series33 =[];
	 $scope.pwmobSeries =[];
	 $scope.pwmobLabels =[];
	 $scope.number4 =[];
	 $scope.labels4 =[];
	 $scope.data4 =[];
	 $scope.series4 =[];		
	 $scope.series44 =[];
	 $scope.cmmobSeries = [];
	 $scope.cmmobLabels = [];
	 $scope.number5 =[];
	 $scope.labels5 =[];
	 $scope.data5 =[];
	 $scope.series5 =[];		
	 $scope.series55 =[];
	 $scope.pwactualSeries =[];
	 $scope.pwactualLabels =[];
	 $scope.number6 =[];
	 $scope.labels6 =[];
	 $scope.data6 =[];
	 $scope.series6 =[];		
	 $scope.series66 =[];
	 $scope.cwactualSeries =[];
	 $scope.cwactualLabels =[];
	 $scope.number7 =[];
	 $scope.labels7 =[];
	 $scope.data7 =[];
	 $scope.series7 =[];		
	 $scope.series77 =[];
	 $scope.pwexceptedSeries =[];
	 $scope.pwexceptedLabels =[];
	 $scope.number8 =[];
	 $scope.labels8 =[];
	 $scope.data8 =[];
	 $scope.series8 =[];		
	 $scope.series88 =[];
}
$scope.getPlantDetails = function () {
	$scope.resetPlantDetails1();
	if ($scope.selectedProjIds.length <= 0) {
		GenericAlertService.alertMessage("Please select projects to fetch report", 'Warning');
		return;
	}
	if ($scope.selectedCompanyIds.length <= 0) {
		GenericAlertService.alertMessage("Please select companies to fetch report", 'Warning');
		return;
	}
	if ($scope.selectedCrewIds.length <= 0) {
		GenericAlertService.alertMessage("Please select crews to fetch report", 'Warning');
		return;
	}
	if (!$scope.fromDate) {
		GenericAlertService.alertMessage("Please select the from date to fetch report", 'Warning');
		return;
	}
	if (!$scope.toDate) {
		GenericAlertService.alertMessage("Please select the to date to fetch report", 'Warning');
		return;
	}
	var req = {
		"projIds": $scope.selectedProjIds,
		"crewIds": $scope.selectedCrewIds,
		"cmpIds": $scope.selectedCompanyIds,
		"fromDate": $scope.fromDate,
		"toDate": $scope.toDate
	};
	PlantReportService.getPlantDateWiseReport(req).then(function (data) {
		$scope.plantDetails = data;

		for (let plant of $scope.plantDetails) {
			plant.totalHrs = parseFloat(parseInt(plant.displayNamesMap.usedHrs) + parseInt(plant.displayNamesMap.idleHrs)).toFixed(2);

		}
		$scope.gridOptions.data = $scope.plantDetails;
		if ($scope.plantDetails.length <= 0) {
			GenericAlertService.alertMessage("Datewise-Actual Hours not available for the search criteria", 'Warning');
		}
	}, function (error) {
		GenericAlertService.alertMessage("Error occured while gettting  Plant details", 'Error');
	});
	$scope.initGraph();
};
	/*$scope.search = function () {
		if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
			GenericAlertService.alertMessage("Please select Project", "Warning");
			return;
		}
			
	}*/
	$scope.clearSubReportDetails = function () {
		$scope.plantDetails = [];
		$scope.type = "";
		$scope.subReportCode = "";
		$scope.subReportName = "";
	};
	var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/resourceshelp/planthelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
}]);
