'use strict';

app.factory('EmpEnrollmentFactory', ["ngDialog", "$q", "$filter", "$timeout", "EpsProjectSelectFactory", "EmpRegisterService", "$rootScope", "EmpClassificationPopupFactory", "EmployeeMasterDetailsFactory", "RegisterPurchaseOrderFactory", "GenericAlertService", "RegisterPurchaseOrderItemsFactory", function(ngDialog, $q, $filter, $timeout, EpsProjectSelectFactory,EmpRegisterService,
											$rootScope, EmpClassificationPopupFactory,EmployeeMasterDetailsFactory,
											RegisterPurchaseOrderFactory,GenericAlertService,RegisterPurchaseOrderItemsFactory) {

		var poDetailsPopUp;
		var service = {};
		service.poDetailsPopUp = function(actionType, itemData ) {
			var deferred = $q.defer();
			poDetailsPopUp = ngDialog.open({
				template : 'views/projresources/projempreg/emppromotion/enrollmentpromotionpopup.html',
				closeByDocument : false,
				showClose : false,
				className : 'ngdialog-theme-plain ng-dialogueCustom0',
				controller : [ '$scope', function($scope) {

					$scope.activeEmpEnrollment = itemData.activeEmpEnrollment;
					$scope.userProjectMap = itemData.empDropDown.userProjMap;
					$scope.classificationMap = itemData.empDropDown.empClassMap;
					$scope.action = actionType;
					$scope.employee = itemData.empRegisterDtlTO;
					$scope.genderList = itemData.empRegisterDropDownTO.gender;
					$scope.companyMap  = itemData.empDropDown.empCompanyMap;
					$scope.procureCatgMap = itemData.procureCatgMap;
					$scope.empLocalityList = itemData.empRegisterDropDownTO.locality;
					$scope.employeeTypeList = itemData.empRegisterDropDownTO.employeeType;
					$scope.employeeType=itemData.empDropDown.employeeTypeList;
					$scope.actionType = actionType;
					$scope.inputDisable = false;

					let rowCount, row, parentName;
					for(rowCount = 0; rowCount < (itemData.employeeProjDtlTOs).length; rowCount++){
						row = itemData.employeeProjDtlTOs[rowCount];
						parentName = String(row.parentName);

						if( parentName == "null" ){
							itemData.employeeProjDtlTOs[rowCount].disableGup = false;
						}else{
							itemData.employeeProjDtlTOs[rowCount].disableGup = true;
						}
					}

					$scope.employeeProjDtlTOs = angular.copy(itemData.employeeProjDtlTOs);

					$scope.getUserProjects = function(promotion) {

						var userProjectSelection = EpsProjectSelectFactory
						.getEPSUserProjects();
						userProjectSelection.then(function(data) {
							if (promotion.projId != data.searchProject.projId) {
								promotion.projectChanged = true;
							}
							promotion.projId = data.searchProject.projId;
							promotion.projName = data.searchProject.projName;
							promotion.name = data.searchProject.projName;
							promotion.parentName=data.searchProject.parentName;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
						});
					},

					$scope.getProjectPoDetails = function(promotion) {

						if (promotion.projId == null || promotion.projId == undefined){
							GenericAlertService.alertMessage("Please select EPS Project name", 'Info');
							return;
						}
						var getPlantPoReq = {
							"status" : 1,
							"projId" : promotion.projId,
							"procureType" : '#E,#S,#E#S,#S#E'
						};

						RegisterPurchaseOrderFactory.getProjectPODetails(getPlantPoReq).then(function(data) {
							promotion.projectPOTO.purchaseLabelKeyTO.id = data.purchaseOrderTO.id;
							promotion.projectPOTO.purchaseLabelKeyTO.code = data.purchaseOrderTO.code;
							}, function(error) {
								if (error.message != undefined && error.status != null){
									GenericAlertService.alertMessage(error.message, error.status);
								}else{
										GenericAlertService.alertMessage("Error occured while getting Purchase Order Details", "Error");
								}
						});

					}


					$scope.getScheduleItems = function(promotion) {
						var onLoadReq = {
							"purId" : promotion.projectPOTO.purchaseLabelKeyTO.id,
							"procureType" : 'E',
							"status" : 1


						};

						var projEmployeeTypePopup = RegisterPurchaseOrderItemsFactory.getPOItemDetails(onLoadReq);
						projEmployeeTypePopup.then(function(data) {
							promotion.projectPOTO.purchaseSchLabelKeyTO = data.selectedRecord;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while selecting purchase order items details", 'Error');
						});
					},

					$scope.empDesignation = function(promotion) {
						if (promotion.projId == null || promotion.projId == undefined) {
							GenericAlertService.alertMessage("Please select the Project", "Warning");
							return;
						}
						var empDesgReq = {
							"status" : 1,
						};
						var empDesignationListPopup = EmpClassificationPopupFactory.empDesignations(empDesgReq);
						empDesignationListPopup.then(function(data) {
							promotion.empClassId = data.selectedRecord.id;
							promotion.empClassName = data.selectedRecord.name;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while selecting employee classification details", 'Error');
						});
					}
				//	console.log("employee details: "+JSON.stringify($scope.employeeProjDtlTOs))
					var mobilizationDate =  function (){
					if( $scope.employeeProjDtlTOs[0].projEmpRegisterTO != null && $scope.employeeProjDtlTOs[0].projEmpRegisterTO.mobilaizationDate != null) {
						return 	new Date($scope.employeeProjDtlTOs[0].projEmpRegisterTO.mobilaizationDate) 
						}else 
						return	false;
					}
					$scope.employeeDetails = function(promotion) {

						if (promotion.projId == null || promotion.projId == undefined) {
							GenericAlertService.alertMessage("Please select the Project", "Warning");
							return;
						}
						var getEmployeeRegisterReq = {
							"projId" : promotion.projId
						};
						EmployeeMasterDetailsFactory.getEmployeeMasterDetails(getEmployeeRegisterReq).then(function(data) {

							var name = data.employeeMasterTO.firstName+","+data.employeeMasterTO.lastName;

							var req = {
								"id" : data.employeeMasterTO.id,
								"code" : data.employeeMasterTO.code,
								"name" : name
							};

							promotion.reportingManagerLabelKeyTO = req;

						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting Project Employee Details", "Error");
						});
					}

					$scope.checkFile = function (file, promotion) {
						var allowedFiles = [".csv", ".xls", ".xlsx", ".doc", ".docx", ".png",".jpg", ".jpeg"];
						var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
						if (!file) {
							promotion.isValidDoc=false;
						}if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
							promotion.docErrorMessage="Supported formats PNG,JPEG,DOC,DOCX,CSV,XLSX,XLS ";
							promotion.isValidDoc=false;
						}else if( (file.size > 1000000)){
							promotion.docErrorMessage="Supported Max. File size 1MB";
							promotion.isValidDoc=false;
						}else{
							promotion.isValidDoc=true;
						}

					}

					$scope.saveEmpEnrollements = function() {

						let isInvalidFile;
						let file = null;
						let promotion = $scope.employeeProjDtlTOs.find(function(value){
							return value.latest === 'Y';
						});
						let projectId = promotion.projId;
			//			let mobilizationDate = ($scope.employeeProjDtlTOs[0].projEmpRegisterTO.mobilaizationDate != null) ? new Date($scope.employeeProjDtlTOs[0].projEmpRegisterTO.mobilaizationDate) : false;
						angular.forEach($scope.employeeProjDtlTOs, function(value, key) {
							if (value.latest ==='Y' && value.isValidDoc != undefined) {
								const isValidFile = (value.contractDocumentFileName && value.isValidDoc === undefined) || value.isValidDoc;
								isInvalidFile = !isValidFile;
								file = value.contractDocument;
								value.contractDocument = null;
							}
							
						});

						if (isInvalidFile || isInvalidFile == null) {
							GenericAlertService.alertMessage("Please upload required documents", 'Warning');
							return ;
						}
						
							//(dateofBirth != contractDate1)? console.log("true contract date") : console.log("false contract date")
							//(dateofBirth != effectiveDate1)? console.log("true effective date") : console.log("false effective date")
						
						let req = {
							"empRegisterDtlTO":$scope.employee,
							"empEnrollmentDtlTO":$scope.employeeProjDtlTOs,
							"category": "Enrollment/Promotions"
						};
						
						console.log(req);
						console.log(file);
						EmpRegisterService.saveEmpEnrollments(file, req).then(function(data) {
							let message = 'Employee Enrollment/Promotion Details ' + data.data.message ;
							if (promotion.projectChanged) {
								message = promotion.empLabelKeyTO.code + " is registered to "+ promotion.projName + " Successfully";
							}
							var succMsg = GenericAlertService.alertMessageModal(message, data.data.status);
							   succMsg.then(function() {
									 var returnPopObj = {
										 "empRegisterDtlTO" : data.data.empRegisterDtlTO,
										 "employeeProjDtlTOs" :data.data.empEnrollmentDtlTOs,
										    "userProjectMap" : data.data.projectLibTO.userProjMap,

										    "classificationMap":data.data.registerOnLoadTO.classificationMap,
											"empRegisterDropDownTO" : data.data.empRegisterDropDownTO,
											"companyMap" :data.data.registerOnLoadTO.companyMap,
											"procureCatgMap" :data.data.registerOnLoadTO.procureCatgMap
		                             }
									 $rootScope.$broadcast('employeeRefresh',{tabIndex:0});
									 $scope.closeThisDialog();
									 deferred.resolve(returnPopObj);

							   })
				

						}, function(error) {
							if (error.message != null && error.message != undefined){
								GenericAlertService.alertMessage(error.message, error.status);
							}else {
								// GenericAlertService.alertMessage('Employee Enrollment/Promotion Details is/are Failed to Save ', "Error");
								GenericAlertService.alertMessage('Employee Enrollment/Promotion Details Failed to Save ', "Error");
							}
						});
					}

				} ]
			});
			return deferred.promise;
		}
		service.downloadDocsPopUp = function() {
			var deferred = $q.defer();
				downloadDocsPopUp = ngDialog.open({
				template : 'views/projresources/projempreg/emppromotion/enrollmentpromotionpopup.html',
				closeByDocument : false,
				showClose : false,
				className : 'ngdialog-theme-plain ng-dialogueCustom0',
				controller : [ '$scope', function($scope) {
					$scope.activeEmpEnrollment = itemData.activeEmpEnrollment;
					$scope.userProjectMap = itemData.empDropDown.userProjMap;
					$scope.classificationMap = itemData.empDropDown.empClassMap;
					$scope.action = actionType;
					$scope.employee = itemData.empRegisterDtlTO;
					$scope.genderList = itemData.empRegisterDropDownTO.gender;
					$scope.companyMap  = itemData.empDropDown.empCompanyMap;
					$scope.procureCatgMap = itemData.procureCatgMap;
					$scope.empLocalityList = itemData.empRegisterDropDownTO.locality;
					$scope.employeeTypeList = itemData.empRegisterDropDownTO.employeeType;
					$scope.employeeType=itemData.empDropDown.employeeTypeList;
					$scope.actionType = actionType;
					$scope.inputDisable = false;

					let rowCount, row, parentName;
					for(rowCount = 0; rowCount < (itemData.employeeProjDtlTOs).length; rowCount++){
						row = itemData.employeeProjDtlTOs[rowCount];
						parentName = String(row.parentName);

						if( parentName == "null" ){
							itemData.employeeProjDtlTOs[rowCount].disableGup = false;
						}else{
							itemData.employeeProjDtlTOs[rowCount].disableGup = true;
						}
					}
				} ]
			});
			return deferred.promise; 
		}
		service.poDetailsPopUpOnLoad = function(actionType,itemData){
			var deferred = $q.defer();
				var poDetailsPopUp =service.poDetailsPopUp(actionType,itemData)
				poDetailsPopUp.then(function(data){
						 var returnPopObj = {
							   "empRegisterDtlTO" : data.empRegisterDtlTO,
							   "employeeProjDtlTOs" :data.employeeProjDtlTOs,
							    "userProjectMap" : data.userProjMap,
								"classificationMap":data.classificationMap,
								"empRegisterDropDownTO" : data.empRegisterDropDownTO,
								"companyMap" :data.companyMap,
								"procureCatgMap" :data.procureCatgMap
				         }
				  deferred.resolve(returnPopObj);
				});
			return deferred.promise;
		}

		return service;

}]);
