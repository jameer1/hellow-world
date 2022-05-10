'use strict';

/**
 * @ngdoc function
 * @name potApp.controller : CompanyController
 * @description # CompanyController of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("company", {
		url: '/company',
		parent: 'site',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/centrallib/companylist/companylist.html',
				controller: 'CompanyController'
			}
		}
	})
}]).controller('CompanyController', ["$rootScope", "$scope","uiGridGroupingConstants", "uiGridConstants", "$q", "blockUI", "$state", "ngDialog", "CompanyService", "CompanyAddressFactory", "CompanyContactFactory", "CompanyCurrentProjectsFactory",'stylesService', 'ngGridService', "GenericAlertService", "utilservice","CompanyBankFactory", function ($rootScope, $scope,uiGridGroupingConstants, uiGridConstants, $q, blockUI, $state, ngDialog, CompanyService, CompanyAddressFactory,
	CompanyContactFactory, CompanyCurrentProjectsFactory,stylesService, ngGridService, GenericAlertService, utilservice,CompanyBankFactory) {
	$scope.stylesSvc = stylesService;
	$rootScope.compId = null;
	var companyId = null;
	$scope.editCompanies = [];
	$scope.sortType = "cmpCode"
	$scope.companies = [];
	$scope.deleteIds = [];
	$scope.address = [];
	var deferred = $q.defer();
	$scope.contacts = [];
	var editAddress = [];
	var editBank =[];
	var editContacts = [];
	var editProjs = [];
	$scope.uniqueCodeMap = [];
	$scope.selectedAll = false;
	$scope.companyReq = {
		'cmpCode ': null,
		'cmpName ': null,
		'status': '1'
	};
	var companyid = new Array();
	var companyRegNo = new Array();
	var companyTaxFileNo = new Array();
	$scope.activeFlag = 0;
	$scope.companySearch = function (click) {
		$scope.activeFlag = 0;
		$scope.address = [];
		$scope.currentProjs = [];
		$scope.closedProjs = [];
		$scope.contacts = [];
		CompanyService.getCompanies($scope.companyReq).then(function (data) {
			editAddress = [];
			$scope.activeFlag = 0;
			$scope.companies = data.companyTOs;
			// var dummyCompany = angular.copy($scope.companies).map((item) => {
			// if(item.status == 1){
			// item.status = 'Active'}
			// else{item.status = 'Inactive'}
			// return item;
			// });
			$scope.gridOptions.data = $scope.companies;
			
			console.log($scope.companies);
			/*$scope.gridOptions.data = angular.copy($scope.companies);*/
			$scope.selectedRow = null;
			if(click=='click'){
				if ($scope.companies.length <= 0) {
					GenericAlertService.alertMessage('Company details are not available for given search criteria', "Warning");
					return;
				}
			}
			// if ($scope.companies != null && $scope.companies.length > 0) {
			// 	if ($scope.companies[0].status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.companies[0].status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// } else {
			// 	if ($scope.companyReq.status == 1) {
			// 		$scope.activeFlag = 1;
			// 	} else if ($scope.companyReq.status == 2) {
			// 		$scope.activeFlag = 2;
			// 	}
			// 	$scope.companyReq = {
			// 		'cmpCode ': null,
			// 		'cmpName ': null,
			// 		'status': '1'
			// 	};
			// }

		});
	}, $scope.contacts = [];
	$scope.reset = function () {
		$scope.companyReq = {
			'cmpCode ': null,
			'cmpName ': null,
			'status': '1'
		}

		$scope.activeFlag = 0;
		$scope.companySearch();
		$scope.company.length = 0;
		$scope.currentTab = 'views/centrallib/companylist/address.html';
	}, $scope.go = function (companies, indexValue) {
		angular.forEach(companies, function (company, index) {console.log(company);
			if (indexValue != index) {
				company.selected = false;
			} else {
				company.selected = true;
			}

		});
		$scope.setSelected(indexValue);
	};
	$scope.rowSelect = function (position, company) {console.log(company);
		if (company.selected) {
			if ($scope.selectedRow) {
				$scope.selectedRow = position;
				console.log($scope.selectedRow);
			}
			var req = {
			"cmpId": company.id,
			"status": 1
		}
		console.log(req);
		CompanyService.getCompanyDetails(req).then(function(data) {
			$scope.company = data.companyTOs;
			angular.forEach($scope.company, function(value) {
				$scope.address = value.addressList;
				$scope.contacts = value.contacts;
				$scope.closedProjs = value.closedProjs;
				$scope.currentProjs = value.currentProjs;
			});
		})
			$scope.selectedRow = null;
			$scope.contacts = [];
			$scope.address = [];
			$scope.currentProjs = [];
			$scope.closedProjs = [];
			$scope.editCompanies.push(company);
		} else {
			$scope.editCompanies.splice($scope.editCompanies.indexOf(company), 1);
		}
	},

		$scope.selectAll = function () {
			angular.forEach($scope.companies, function (company, index) {
				if ($scope.selectedAll) {
					if ($scope.selectedRow) {
						$scope.selectedRow = null;
					}
					$scope.selectedRow = null;
					$scope.contacts = [];
					$scope.address = [];
					$scope.currentProjs = [];
					$scope.closedProjs = [];
					company.selected = true;
					$scope.editCompanies.push(company);

				} else {
					company.selected = false;
					$scope.editCompanies.splice($scope.editCompanies.indexOf(company), 1);

				}
			});
		}

	$scope.selectCompany = function (companyId) {
		$scope.activeFlag = 0;
		if (companyId.selected) {
			$scope.getCmpAddress(companyId);
		} else {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
		}
	}
	$scope.setSelected = function (row) {
		$scope.selectedRow = row;
	}
	var companyservice = {};
	var companyPopUp;
	$scope.addCompanies = function (actionType) {
		if ($scope.editCompanies.length > 0 && actionType == 'Add') {
			$scope.editCompanies = [];
			GenericAlertService.alertMessage("System will not allow to create/edit company", 'Warning');
			return;
		}
		$scope.contacts = [];
		$scope.currentTab = 'views/centrallib/companylist/address.html';
		$scope.address = [];
		$scope.currentProjs = [];
		$scope.closedProjs = [];
		if ($scope.selectedRow >= 0) {
			$scope.selectedRow = -1;
		}
		companyPopUp = companyservice.addCompany(actionType, $scope.editCompanies);
		companyPopUp.then(function (data) {
			for (const com of $scope.editCompanies) {
				com.selected = false;
			}
			$scope.editCompanies = [];
			if (data && data.companyTOs) {
				$scope.companies = data.companyTOs;
				if (data.companyCatgResp)
					$scope.cmpCatagories = data.companyCatgResp.companyCatagoryTOs;
				$scope.businessData = data.businessCatgResp.businessCatagoryTOs;
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting company details", 'Error');
		});

	}
	companyservice.addCompany = function (actionType, editCompanies) {
		var deferred = $q.defer();
		if (actionType == 'Edit' && editCompanies.length === 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		}
		companyPopUp = ngDialog.open({
			template: 'views/centrallib/companylist/addcompanypopup.html',
			closeByDocument: false,
			showClose: false,
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			preCloseCallback: function (value) {
				if (value)
					deferred.resolve();
					$scope.companySearch('click');
			},
			controller: ['$scope', function($scope) {

				$scope.selectCompany = {};
				var selectedCompany = [];
				$scope.companyList = [];
				$scope.businessData = ['Manpower Supplier', 'Plant Supplier', 'Material Supplier', 'Service Provider'];
				$scope.cmpCatagories = ['Head Company', 'Sub Company'];
				var copyEditArray = angular.copy(editCompanies);
				$scope.action = actionType;
				if (actionType === 'Add') {
					angular.forEach(editCompanies, function(value) {
						value.selected = false;
						editCompanies.value = [];
						return editCompanies.value = [];
					});
					$scope.companyList.push({
						'cmpCode': '',
						'cmpName': '',
						"clientId": $scope.clientId,
						'status': '1',
						'cmpActivity': '',
						"businessCategoryTO": {
							"name": null
						},
						'regNo': '',
						'taxFileNo': '',
						'selected': false
					});
				} else {
					$scope.companyList = angular.copy(editCompanies);
					editCompanies = [];
				}
				var req = {
					"status": 1
				}
				$scope.addRows = function() {
					$scope.companyList.push({
						'cmpCode': '',
						'cmpName': '',
						"clientId": $scope.clientId,
						'status': '1',
						'cmpActivity': '',
						"businessCategoryTO": {
							"name": null
						},
						"name": '',
						'regNo': '',
						'taxFileNo': '',
						'selected': false
					});
				}, $scope.getCompanyMap = function() {
					var req = {

					}
					CompanyService.getCompanyMap(req).then(function(data) {
						$scope.uniqueCodeMap = data.uniqueCodeMap;
						
						for (var i = 0, keys = Object.keys($scope.uniqueCodeMap), ii = keys.length; i < ii; i++) {
						 var companyDetails = keys[i].split(" ");
						 	companyid.push(companyDetails[0].toUpperCase());
						  	companyRegNo.push(companyDetails[1]);
						  	companyTaxFileNo.push(companyDetails[2]);
						}
					})
					
					
				}, $scope.checkDuplicate = function (company) {
					company.duplicateFlag = false;
					if (company.cmpCode) {
						company.cmpCode = company.cmpCode.toUpperCase();
						if ($scope.uniqueCodeMap[company.cmpCode + " " + company.regNo + " " + company.taxFileNo] != null) {
							company.duplicateFlag = true;
							GenericAlertService.alertMessage('Company details already Exist', "Warning");
							return;
						}
						if (company.cmpCode != null && companyid.indexOf(company.cmpCode) > -1) {
							company.duplicateFlag = true;
							GenericAlertService.alertMessage('Company details already Exist', "Warning");
							return;   
						} 
						if (company.regNo != null && companyRegNo.indexOf(company.regNo) > -1) {
							company.duplicateFlag = true;
							GenericAlertService.alertMessage('company Reg no. already Exist', "Warning");
							return;
						}
						if (company.taxFileNo != null && companyTaxFileNo.indexOf(company.taxFileNo) > -1) {
							company.duplicateFlag = true;
							GenericAlertService.alertMessage('Company Tax File no. already Exist', "Warning");
							return;
						}
						company.duplicateFlag = false;
					}
				}, $scope.saveCompany = function(companyForm) {

					var flag = false;
					var companyMap = [];

            angular.forEach($scope.companyList, function (value, key) {	
              if (companyMap[value.cmpCode.toUpperCase()] != null) {
                value.duplicateFlag = true;
                flag = true;
              } 
			//below 2 else if statement are written check duplicates regNo and taxFileNo companyid
			else if (value.cmpCode != null && companyid.indexOf(value.cmpCode) > -1) {
				value.duplicateFlag = true;
                flag = true;
			}
			
			else if (value.regNo != null && companyRegNo.indexOf(value.regNo) > -1) {
				value.duplicateFlag = true;
                flag = true;
			}
			else if (value.taxFileNo != null && companyTaxFileNo.indexOf(value.taxFileNo) > -1) {
				value.duplicateFlag = true;
                flag = true;
			}
			
			
			 else {
                value.duplicateFlag = false;
                companyMap[value.cmpCode.toUpperCase()] = true;
              }
            });


					if (actionType === 'Add') {

						angular.forEach($scope.companyList, function (value, key) {
							if ($scope.uniqueCodeMap[value.cmpCode.toUpperCase() + " " + value.regNo + " " + value.taxFileNo] != null) {
								value.duplicateFlag = true;
								flag = true;
							}
						});
					} else {
						flag = false;
						angular.forEach($scope.companyList, function (value, key) {

							angular.forEach(copyEditArray, function (value1, key) {
							  console.log("copyEditArray" +copyEditArray);
							

               if ($scope.uniqueCodeMap[value.cmpCode.toUpperCase() + " " + value.regNo + " " + value.taxFileNo] != null) {

										value.duplicateFlag = true;
										//flag = true;
									} else {
										value.duplicateFlag = false;
										companyMap[value.cmpCode.toUpperCase()] = true;
									}
							
							});
						});
					}
					if (flag) {
						GenericAlertService.alertMessage('Duplicate Company codes,Reg.No and Tax File No are not allowed', "Warning");
						return;
					}
					var req = {
						"companyTOs": $scope.companyList,
						"clientId": $scope.clientId
					}
					blockUI.start();
					CompanyService.saveCompany(req).then(function(data) {
						blockUI.stop();
						var results = data.companyTOs;
						// var succMsg = GenericAlertService.alertMessageModal('Company Details is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Company Details saved successfully', "Info");
						succMsg.then(function(data) {
							$scope.closeThisDialog();
							var returnPopObj = {
								"companyTOs": results
							}
							deferred.resolve(returnPopObj);
						})
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Company details is/are failed to save', "Error");
					});
				}, $scope.popUpRowSelect = function(company) {
					if (company.selected) {
						selectedCompany.push(company);
					} else {
						selectedCompany.splice(selectedCompany.indexOf(company), 1);
					}
				}, $scope.deleteRows = function() {
					if (selectedCompany.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedCompany.length < $scope.companyList.length) {
						angular.forEach(selectedCompany, function(value, key) {
							$scope.companyList.splice($scope.companyList.indexOf(value), 1);
						});
						selectedCompany = [];
						GenericAlertService.alertMessage('Row(s) deleted successfully', "Info");
					} else if (selectedCompany.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedCompany.length == 1) {
						$scope.companyList[0] = {
							'cmpCode': '',
							'cmpName': '',
							"clientId": $scope.clientId,
							'status': '1',
							'cmpActivity': '',
							'regNo': '',
							'taxFileNo': '',
							'selected': false
						};
						selectedCompany = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}
			}]
		});
		return deferred.promise;
	},

		$scope.deleteCompanies = function() {console.log($scope.currentProjs);
			if ($scope.editCompanies.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
				return;
			}
			var delIds = [];
			var nondelIds = [];
			if($scope.currentProjs.length > 0){
			GenericAlertService.alertMessage("Delete not possible Company is assigned to project", 'Warning');
			return;
			}

			angular.forEach($scope.companies, function(value, key) {
				if (!value.selected) {
					nondelIds.push(value);
				} else {
					if (value.id != null && value.id > 0) {
						delIds.push(value.id);
					}
				}
			});
			var req = {
				"companyIds": delIds,
				"status": 2
			};
			GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
				CompanyService.deleteCompanies(req).then(function(data) {
				}, function(error) {
					$scope.companySearch();
				});
				GenericAlertService.alertMessage('Company(s) deactivated successfully', 'Info');
				angular.forEach($scope.editCompanies, function(value, key) {
					console.log($scope.editCompanies);
					$scope.companies.splice($scope.companies.indexOf(value), 1);					
					$scope.editCompanies = [];
					$scope.delIds = [];
				}, function(error) {
					GenericAlertService.alertMessage('Company detail(s) is/are failed to deactivate', "Error");
				});
			}, function(data) {

			})


		}, $scope.activateCompanies = function() {
			if ($scope.editCompanies.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to activate", 'Warning');
				return;
			}
			var delIds = [];
			var nondelIds = [];
			if ($scope.selectedAll) {
				$scope.companies = [];
			} else {
				angular.forEach($scope.companies, function(value, key) {
					if (!value.selected) {
						nondelIds.push(value);
					} else {
						if (value.id != null && value.id > 0) {
							delIds.push(value.id);
						}
					}
				});
				var req = {
					"companyIds": delIds,
					"status": 1
				};
				GenericAlertService.confirmMessageModal('Do you really want to activate the Company?', 'Warning', 'YES', 'NO').then(function() {
					CompanyService.deleteCompanies(req).then(function(data) {
					}, function(error) {
					});
					GenericAlertService.alertMessage('Company detail(s) activated successfully', 'Info');
					angular.forEach($scope.editCompanies, function(value, key) {
						$scope.companies.splice($scope.companies.indexOf(value), 1);
						$scope.editCompanies = [];
						$scope.delIds = [];
					}, function(error) {
						GenericAlertService.alertMessage('CompanyDetail(s) is/are failed to Activate', "Error");
					});
				}, function(data) {
					angular.forEach($scope.editCompanies, function(value) {
						value.selected = false;
					})
				})

			}
		},

		$scope.tabs = [{
			title: 'Address',
			SelenumLocator: 'Companylist_Address',
			appCodeTemplateKey: 'CMPLIST_ADDRESS_VIEW',
			url: 'views/centrallib/companylist/address.html'
		}, {
			title: 'Contacts',
			SelenumLocator: 'Companylist_Contact',
			appCodeTemplateKey: 'CMPLIST_CONTACTS_VIEW',
			url: 'views/centrallib/companylist/contacts.html'
		}, {
			title: 'Closed Projects',
			SelenumLocator: 'Companylist_ClosedProjects',
			appCodeTemplateKey: 'CMPLIST_CLOSESPROJECT_VIEW',
			url: 'views/centrallib/companylist/closedprojects.html'
		}, {
			title: 'Current Projects',
			SelenumLocator: 'Companylist_CurrentProjects',
			appCodeTemplateKey: 'CMPLIST_CURRENTPROJ_VIEW',
			url: 'views/centrallib/companylist/currentprojects.html'
		}, {
			title: 'Bank Account Details',
			SelenumLocator: 'Companylist_BankDetails',
			appCodeTemplateKey: 'CMPLIST_BANKPROJ_VIEW',
			url: 'views/centrallib/companylist/bankdetails.html'
		}];
	$scope.currentTab = 'views/centrallib/companylist/address.html';

	$scope.onClickTab = function(tab) {
		if ($scope.company.length <= 0) {
			GenericAlertService.alertMessage("Please select Company ID", 'Warning');
		}
		$scope.currentTab = tab.url;
	}
	$scope.isActiveTab = function(tabUrl) {
		return tabUrl == $scope.currentTab;
	},

		$scope.isActiveRow = function(tabUrl) {
			return tabUrl == $scope.currentRow;
		},

		$scope.editAddress = [];
	$scope.address = [];
	$scope.company = [];
	$scope.addressLists = [];
	$scope.contactsList = [];
	$scope.closedProjs = [];
	$scope.currentProjs = [];
	$scope.contacts = [];
    $scope.bankDetails=[];
	$scope.getCmpAddress = function(companyId) {
		$scope.currentTab = 'views/centrallib/companylist/address.html';
		$scope.isActiveTab = function(tabUrl) {
			return tabUrl == $scope.currentTab;
		}, $rootScope.compId = companyId;
		var req = {
			"cmpId": companyId,
			"status": $scope.companyReq.status
		}
		
		CompanyService.getCompanyDetails(req).then(function(data) {
			$scope.company = data.companyTOs;
			angular.forEach($scope.company, function(value) {
				$scope.address = value.addressList;
				$scope.contacts = value.contacts;
				$scope.closedProjs = value.closedProjs;
				$scope.currentProjs = value.currentProjs;
				$scope.bankList= value.bankList;
				$scope.bankDetails=[];
				for(var i=$scope.bankList.length-1; i>=0; i--){
					$scope.bankDetails.push($scope.bankList[i]);
				}
				console.log($scope.bankDetails,1111);
				console.log($scope.bankList)
			});
			console.log($scope.currentProjs, 'Hi...');
			$scope.gridOptions1.data = angular.copy($scope.address);
			$scope.gridOptions2.data = angular.copy($scope.contacts);
			$scope.gridOptions3.data = angular.copy($scope.closedProjs);
			$scope.gridOptions4.data = angular.copy($scope.currentProjs);
			$scope.gridOptions5.data = angular.copy($scope.bankDetails);
		});console.log($scope.address);
	}, $scope.addressRowSelect = function(addrVar) {
		if (addrVar.selected) {
			utilservice.addItemToArray(editAddress, "addressId", addrVar);
		} else {
			editAddress.pop(editAddress.indexOf(addrVar), 1);

		}
	}
	var linkCellTemplate3 ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.currentProjRowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'8%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate3, 
				   displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
				{ name: 'projCode',displayName:'Project ID',headerTooltip: "Project ID", groupingShowAggregationMenu: false},				
				{ name: 'projName',displayName:'Project Name',headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ name: 'contractValue',displayName:'Contract Value',headerTooltip: "Contract Value", groupingShowAggregationMenu: false},	
				{ name: 'startDate',displayName:'Contract Start Date',headerTooltip: "Contract Start Date", groupingShowAggregationMenu: false},	
				{ name: 'finishDate',displayName:'Expected Contract Finish Date',headerTooltip: "Expected Contract Finish Date", groupingShowAggregationMenu: false},
				{ name: 'performance',displayName:'Performance',headerTooltip: "Performance", groupingShowAggregationMenu: false},			
				]
			let data = [];
			$scope.gridOptions4 = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Contacts");
			$scope.companySearch();
			$scope.gridOptions4.showColumnFooter = false;
			$scope.gridOptions4.gridMenuCustomItems = false;
		}
	});
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
			
				{ name: 'projCode',displayName:'Project ID',headerTooltip: "Project ID", groupingShowAggregationMenu: false},				
				{ name: 'projName',displayName:'Project Name',headerTooltip: "Project Name", groupingShowAggregationMenu: false},
				{ name: 'contractValue',displayName:'Contract Value',headerTooltip: "Contract Value", groupingShowAggregationMenu: false},	
				{ name: 'startDate',displayName:'Contract Start Date',headerTooltip: "Contract Start Date", groupingShowAggregationMenu: false},	
				{ name: 'finishDate',displayName:'Contract Finish Date',headerTooltip: "Contract Finish Date", groupingShowAggregationMenu: false},
				{ name: 'performance',displayName:'Overall Performance',headerTooltip: "Overall Performance", groupingShowAggregationMenu: false},			
				]
			let data = [];
			$scope.gridOptions3 = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Contacts");
			$scope.companySearch();
			$scope.gridOptions3.showColumnFooter = false;
			$scope.gridOptions3.gridMenuCustomItems = false;
		}
	});
	var linkCellTemplate2 ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.contactsRowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'8%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate2, 
				   displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
				{ name: 'contactName',displayName:'Contact Name',headerTooltip: "Contact Name", groupingShowAggregationMenu: false},				
				{ name: 'email',displayName:'Email',headerTooltip: "Email", groupingShowAggregationMenu: false},
				{ name: 'mobile',displayName:'Mobile',headerTooltip: "Mobile", groupingShowAggregationMenu: false},	
				{ name: 'phone',displayName:'Phone',headerTooltip: "Phone", groupingShowAggregationMenu: false},	
				{ name: 'fax',displayName:'Fax',headerTooltip: "Fax", groupingShowAggregationMenu: false},
				{ name: 'designation',displayName:'Designation',headerTooltip: "Designation", groupingShowAggregationMenu: false},			
				]
			let data = [];
			$scope.gridOptions2 = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Contacts");
			$scope.companySearch();
			$scope.gridOptions2.showColumnFooter = false;
			$scope.gridOptions2.gridMenuCustomItems = false;
		}
	});
	var linkCellTemplate1 ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.addressRowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'8%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate1, 
				   displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
				{ name: 'address',displayName:'Address',headerTooltip: "Address", groupingShowAggregationMenu: false},				
				{ name: 'city',displayName:'City',headerTooltip: "City",groupingShowAggregationMenu: false},
				{ name: 'state',displayName:'State',headerTooltip: "State", groupingShowAggregationMenu: false},	
				{ name: 'pincode',displayName:'Pin Code',headerTooltip: "Pin Code", groupingShowAggregationMenu: false},				
				]
			let data = [];
			$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Address");
			$scope.companySearch();
			$scope.gridOptions1.showColumnFooter = false;
			$scope.gridOptions1.gridMenuCustomItems = false;
		}
	});
	
	
	var linkCellTemplate5 ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.bankRowSelect(row.entity)">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'8%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate5, 
				   displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
				{ name: 'accountName',displayName:'Account Name',headerTooltip: "Account Name", groupingShowAggregationMenu: false},				
				{ name: 'bankName',displayName:'Name Of Bank/Institution',headerTooltip: "Name Of Bank/Institution", groupingShowAggregationMenu: false},
				{ name: 'bankCode',displayName:'Bank Code',headerTooltip: "Bank Code", groupingShowAggregationMenu: false},	
				{ name: 'accountNumber',displayName:'Account Number',headerTooltip: "Account Number", groupingShowAggregationMenu: false},	
				{ name: 'bankAddress',displayName:'Bank Address',headerTooltip: "Bank Address", groupingShowAggregationMenu: false},
				{ name: 'bankStatus',displayName:'Status',headerTooltip: "Status", groupingShowAggregationMenu: false}	
				]
			let data = [];
			$scope.gridOptions5 = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList_Bank Account Details	");
			$scope.companySearch();
			$scope.gridOptions2.showColumnFooter = false;
			$scope.gridOptions2.gridMenuCustomItems = false;
		}
	});
	
	
	$scope.addAddress = function(actionType) {
		companyId = $rootScope.compId;
		if (actionType == 'Edit' && editAddress <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		} else if (companyId != undefined && companyId != null) {
			var companyadressdetails = CompanyAddressFactory.companyAddressDetails(actionType, editAddress, companyId);
			companyadressdetails.then(function(data) {
				$scope.address = data.addressTOs;
				console.log(data, 'Hi...')
				editAddress = [];
				$scope.getCmpAddress();
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching company address details", 'Error');
			});
		} else {
			GenericAlertService.alertMessage("Please select Company ID", 'Warning');
		}
	}, /*$scope.deleteAddress = function() {
		if (editAddress.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		GenericAlertService.confirmMessageModal('Do you really want to deactivate the record',
			'Warning', 'YES', 'NO').then(function() {
				var delAddrIds = [];
				var nondelAddrIds = [];
				if ($scope.selectedAll) {
					$scope.address = [];
				} else {
					angular.forEach($scope.address, function(value, key) {
						if (!value.selected) {
							nondelAddrIds.push(value);
						} else {
							if (value.addressId != null && value.addressId > 0) {
								delAddrIds.push(value.addressId);
							}
						}
					});
					var req = {
						"addressIds": delAddrIds,
						"status": 2
					};
					CompanyService.deleteAddress(req).then(function(data) {
					
						GenericAlertService.alertMessage('Address(s) deactivated successfully', 'Info');
						angular.forEach(editAddress, function(value, key) {
								$scope.getCmpAddress();
							$scope.address.splice($scope.address.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('Address(s) is/are failed to Deactivate', "Error");
						});
					});
					editAddress = [];
					$scope.delAddrIds = [];
				}
			});

	}, */
	$scope.deleteAddress = function() {
		if (editAddress.length <= 0) {
					GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
					return;
				}
		var delAddrIds = [];
		if ($scope.selectedAll) {
			editAddress = [];
		} else {
		angular.forEach(editAddress, function(value, key){
			if(value.addressId){
				delAddrIds.push(value.addressId);
			}
		});
		var req = {
			"addressIds" : delAddrIds,
			"status" : 2
		};
		console.log(req);
		GenericAlertService.confirmMessageModal('Do you really want to deactivate the record',	'Warning', 'YES', 'NO').then(function() {
			CompanyService.deleteAddress(req).then(function(data) {
				GenericAlertService.alertMessage('Address(s) deactivated successfully', 'Info');
				$scope.getCmpAddress();
				editAddress = [];
				console.log("record deleted successfully")
			});

		angular.forEach(editAddress, function(value, key){
			$scope.getCmpAddress();
		$scope.address.splice($scope.address.indexOf(value), 1);
		editAddress = [];
		$scope.delAddrIds = []; 
		}, function(error){
			GenericAlertService.alertMessage('Address(s) is/are failed to Deactivate', "Error");
			});
		},
		function(data){
		angular.forEach(address, function(value){
			value.selected = false;
		})
	})
}
},

	$scope.contactsRowSelect = function(contact) {
		if (contact.selected) {
			editContacts.push(contact);
		} else {
			editContacts.splice(editContacts.indexOf(contact), 1);
		}
	}
	$scope.addContacts = function(actionType) {
		if ($rootScope.compId == null || $rootScope.compId == undefined) {
			GenericAlertService.alertMessage("Please select the Company", "Warning");
			return;
		}
		companyId = $rootScope.compId;
		if (actionType == 'Edit' && editContacts <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		} else if (companyId != undefined && companyId != null) {
			var compContPopup = CompanyContactFactory.companyContactPopUp(actionType, editContacts, companyId, $scope.contacts);
			compContPopup.then(function(data) {
				$scope.contacts = data.contactsTOs;
				editContacts = [];
				$scope.getCmpAddress();
				$scope.currentTab = 'views/centrallib/companylist/contacts.html';
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching company contact details", 'Error');
			});
		} else {
			GenericAlertService.alertMessage("Please select Company ID", 'Warning');
		}
	},

		$scope.deleteContacts = function() {
			if (editContacts.length <= 0) {
				GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
				return;
			}
			GenericAlertService.confirmMessageModal('Do you really want to deactivate the record',
				'Warning', 'YES', 'NO').then(function() {
					var deleteIds = [];
					if ($scope.selectedAll) {
						$scope.contacts = [];
					} else {
						angular.forEach(editContacts, function(value, key) {
							deleteIds.push(value.contactId);
						});
						var req = {
							"contactIds": deleteIds,
							"status": 2
						};
						CompanyService.deleteContacts(req).then(function(data) {
						});
						GenericAlertService.alertMessage('Contact(s) Deactivated successfully', 'Info');
						angular.forEach(editContacts, function(value, key) {
							$scope.getCmpAddress();
							$scope.contacts.splice($scope.contacts.indexOf(value), 1);
						}, function(error) {
							GenericAlertService.alertMessage('Contact(s) is/are failed to Deactivate', "Error");
						});
						editContacts = [];
						deleteIds = [];
						$scope.currentTab = 'views/centrallib/companylist/contacts.html';
					}
				});
		}, $scope.bankRowSelect = function(bankVar) {
			if (bankVar.selected) {
				utilservice.addItemToArray(editBank, "bankId", bankVar);
			} else {
				editBank.pop(editBank.indexOf(bankVar), 1);

			}
		}
	$scope.addBank = function(actionType) {
		companyId = $rootScope.compId;
		if (actionType == 'Edit' && editBank <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		} else if (companyId != undefined && companyId != null) {
			var companybankdetails = CompanyBankFactory.companyBankDetails(actionType, editBank, companyId, $scope.bankList);
			companybankdetails.then(function(data) {
				$scope.bank = data.bankTOs;
				editBank = [];
				$scope.getCmpAddress();
				$scope.currentTab = 'views/centrallib/companylist/bankdetails.html';
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching company address details", 'Error');
			});
		} else {
			GenericAlertService.alertMessage("Please select Company ID", 'Warning');
		}
	}, $scope.deleteBank = function() {
		if (editBank.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		GenericAlertService.confirmMessageModal('Do you really want to deactivate the record',
			'Warning', 'YES', 'NO').then(function() {
				var delBankIds = [];
			//	var nondelBankIds = [];
				if ($scope.selectedAll) {
					$scope.bank = [];
				} else {
					angular.forEach(editBank, function(value, key) {
						if (!value.selected) {
							nondelbankIds.push(value);
						} else {
							if (value.bankAccountId != null && value.bankAccountId > 0) {
								delBankIds.push(value.bankAccountId);
							}
						}
					});
					var req = {
						"bankAccountIds": delBankIds,
						"status": 2
					};
					CompanyService.deleteBank(req).then(function(data) {
					});
					GenericAlertService.alertMessage('Bank Account deactivated successfully', 'Info');
					$scope.getCmpAddress();
					angular.forEach(editBank, function(value, key) {
						$scope.bank.splice($scope.bank.indexOf(value), 1);
					}, function(error) {
						GenericAlertService.alertMessage('Bank Account is/are failed to Deactivate', "Error");
					});
					editBank = [];
					$scope.delBankIds = [];
					$scope.currentTab = 'views/centrallib/companylist/bankdetails.html';
				}
			});

	}

	$scope.moveToClosedProj = function() {
		if ($scope.currentProjs.length == 0) {
			GenericAlertService.alertMessage("Please create project", 'Warning');
			return;
		}
		if (editProjs.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row", 'Warning');
			return;
		}
		var moveToClosedProjs = [];
		angular.forEach(editProjs, function(value, key) {
			moveToClosedProjs.push(value);
		});
		var req = {
			"companyProjectsTOs": moveToClosedProjs,
			"status": 2
		};

		CompanyService.moveToToCmpClosedProjs(req).then(function() {
			const currentProjArrayCopy = angular.copy($scope.currentProjs);
			for (const value of editProjs) {
				$scope.closedProjs.push(value);
				$scope.currentProjs = [];
				for (let index = 0; index < currentProjArrayCopy.length; index++) {
					if (value.projId === currentProjArrayCopy[index].projId) {
						currentProjArrayCopy.splice(index, 1);
						break;
					}
				}
			}
			$scope.currentProjs = currentProjArrayCopy;
			editProjs = [];
			$scope.getCmpAddress();
			$scope.currentTab = 'views/centrallib/companylist/currentprojects.html';
			GenericAlertService.alertMessage(' This project is moved to closed projects', 'Info');
		});

	}, $scope.currentProjRowSelect = function(proj) {
		editProjs = [];
		if (proj.selected) {
			editProjs.push(proj);
		} else {
			editProjs.splice(editProjs.indexOf(proj), 1);
		}
	}
	$scope.addProjs = function(actionType) {
		companyId = $rootScope.compId;
		if (actionType == 'Edit' && editProjs <= 0) {
			GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
			return;
		} else if (companyId != undefined && companyId != null) {
			var compProjectPopup = CompanyCurrentProjectsFactory.projectPopUp(actionType, editProjs, companyId, $scope.currentProjs);
			compProjectPopup.then(function(data) {
				$scope.currentProjs = data.companyProjectsTO;
				editProjs = [];
				$scope.getCmpAddress();
				$scope.currentTab = 'views/centrallib/companylist/currentprojects.html';
			}, function(error) {
				GenericAlertService.alertMessage("Error occurred while fetching company projects details", 'Error');
			});
		} else {
			GenericAlertService.alertMessage("Please select Company ID", 'Warning');
		}
	}, $scope.deleteCompanyCurrentProjs = function() {
		if (editProjs.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		GenericAlertService.confirmMessageModal('Do you really want to deactivate the record',
			'Warning', 'YES', 'NO').then(function() {
				$scope.getCmpAddress();
				var deleteIds = [];
				if ($scope.selectedAll) {
					$scope.currentProjs = [];
				} else {
					angular.forEach(editProjs, function(value, key) {
						deleteIds.push(value.id);
					});
					var req = {
						"cmpProjIds": deleteIds,
						"status": 2
					};
					CompanyService.deleteCompanyCurrentProjs(req).then(function(data) {
					});
					GenericAlertService.alertMessage('Project(s) Deactivated successfully', 'Info');
					$scope.companySearch();
					angular.forEach(editProjs, function(value, key) {
						$scope.currentProjs.splice($scope.currentProjs.indexOf(value), 1);
					}, function(error) {
						GenericAlertService.alertMessage('Project(s) is/are failed to Deactivate', "Error");
					});
					editProjs = [];
					deleteIds = [];
					$scope.currentTab = 'views/centrallib/companylist/currentprojects.html';
				}
			});
	}
	var HelpService = {};
	$scope.helpPage = function() {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error", 'Info');
		})
	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row,row.entity)" ng-disabled="row.entity.projectCompany">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{  name: 'select',width:'5%',cellClass:'justify-center',headerCellClass:'justify-center',
				   cellTemplate: linkCellTemplate, 
				   displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
				{ name: 'cmpCode',displayName:'Company ID',headerTooltip: "Company ID", cellTemplate: '<div ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.cmpCode}}</div>', groupingShowAggregationMenu: false},				
				{ name: 'cmpName',displayName:'Vendors / Business Partners',headerTooltip: "Vendors / Business Partners", cellTemplate: '<div  ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.cmpName}}</div>',groupingShowAggregationMenu: false},
				{ name: 'companyCatagory',displayName:'Company Category',headerTooltip: "Company Category", cellTemplate: '<div ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.companyCatagory}}</div>',  groupingShowAggregationMenu: false},	
				{ name: 'businessCategory',displayName:'Business Category',headerTooltip: "Business Category", cellTemplate: '<div ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.businessCategory}}</div>', groupingShowAggregationMenu: false},	
				{ name: 'cmpActivity',displayName:'Business Activity',headerTooltip: "Business Activity", cellTemplate: '<div ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.cmpActivity}}</div>', groupingShowAggregationMenu: false},	
				{ name: 'regNo',displayName:'Company Reg. No.',headerTooltip: "Company Reg. No.", cellTemplate: '<div  ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.regNo}}</div>', groupingShowAggregationMenu: false},
				{ name: 'taxFileNo',displayName:'Tax File No.',headerTooltip: "Tax File No.", cellTemplate: '<div  ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.taxFileNo}}</div>', groupingShowAggregationMenu: false},	
				{ name: 'status',displayName:'Status',cellFilter: 'potstatusfilter:tab.status', headerTooltip: "Status", cellTemplate: '<div ng-click="grid.appScope.getCmpAddress(row.entity.id)" class="ngCellText">&nbsp{{row.entity.status| potstatusfilter:row.entity.status}}</div>', groupingShowAggregationMenu: false},				
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList");
			$scope.companySearch();
			$scope.gridOptions.showColumnFooter = false;
		}
	});
	var helppagepopup;
	HelpService.pageHelp = function() {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/companylisthelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function($scope) {

			}]
		});
		return deferred.promise;
	}
	return companyservice;
}]);
