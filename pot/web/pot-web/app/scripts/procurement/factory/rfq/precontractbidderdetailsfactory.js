'use strict';

app.factory('PrecontractBidderDetailsFactory', ["ngDialog", "$q", "RFQService", "EpsProjectSelectFactory", "ApproveUserFactory", "PrecontractStage1ApprovedFactory", "CompanyContactDetailsFactory", "CompanyAddressDetailsFactory", "PreContractApproverFactory", "CompanyDetailsFactory", "GenericAlertService", function (ngDialog, $q,
	RFQService, EpsProjectSelectFactory, ApproveUserFactory, PrecontractStage1ApprovedFactory,
	CompanyContactDetailsFactory, CompanyAddressDetailsFactory, PreContractApproverFactory,
	CompanyDetailsFactory, GenericAlertService) {

	var service = {};
	service.getPreContractBidders = function (usersMap) {
		var deferred = $q.defer();
		var bidderDetailsPopUp = ngDialog.open({
			template: 'views/procurement/RFQ/rfqdetailspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.companyTOs = [];
				$scope.preContractCompanyMap = [];
				$scope.usersMap = usersMap;
				$scope.searchProject = {};
				$scope.precontractObj = [];
				$scope.searchProject = {};
				$scope.companyMap = [];
        $scope.today= new Date();
				var selectedCompanyDetails = [];
				var deleteCmpIds = [];
				$scope.rowSelect = function (details) {
					if (details.selected) {
						selectedCompanyDetails.push(details);
					} else {
						selectedCompanyDetails.splice(selectedCompanyDetails.indexOf(details), 1);
					}
				};

				$scope.getPreContractDetails = function () {
					if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
						GenericAlertService.alertMessage("Please select the Project", "Warning");
						return;
					}
					var getContractReq = {
						"projId": $scope.searchProject.projId,
						"approveStatus": 5,
						"status": 1,
					};
					PrecontractStage1ApprovedFactory.getPreContractDetails(getContractReq).then(function (data) {
						$scope.precontractObj = data.selectedRecord;
						$scope.getPreContarcttCompanyDetails($scope.precontractObj);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while getting Pre-Contract Stage1 Approved Details", "Error");
					});

				};
				$scope.getContactDetails = function (companyTO, companyMap) {
					var req = {
						"cmpId": companyTO.companyId
					}
					var contactData = CompanyContactDetailsFactory.getCmpContacts(req);
					contactData.then(function (data) {
						companyTO.contactId = data.contactId;
						companyMap.contactsMap = [];
						companyMap.contactsMap[companyTO.contactId] = data;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting company contact details", 'Error');
					})
				};
				$scope.getAddressDetails = function (companyTO, companyMap) {
					var req = {
						"cmpId": companyTO.companyId
					}
					var addressData = CompanyAddressDetailsFactory.getCmpAddress(req);
					addressData.then(function (data) {
						companyTO.addressId = data.addressId;
						companyMap.addressMap = [];
						companyMap.addressMap[companyTO.addressId] = data;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting company address details", 'Error');
					})
				};
				$scope.getUserProjects = function () {
					var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
					userProjectSelection.then(function (data) {
						$scope.searchProject = data.searchProject;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
					});
				};
				$scope.getUsersByModulePermission = function (userLabelkeyTO) {
					var userFactoryPopup = PreContractApproverFactory.getUsersByModulePermission();
					userFactoryPopup.then(function (data) {
						userLabelkeyTO = data.approverUserTO;
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while gettting approver users", 'Error');
					});
				};
				$scope.getApprovelreq = function (approverTo) {
					var getReq = {
						"moduleCode": "INTERNALREQAPPOVAL",
						"actionCode": "APPROVE",
						"permission": "PROCURMT_INTRNLSTAGE1APRVL_APPROVE",
						"projId": $scope.searchProject.projId
					};
					var selectedUser = ApproveUserFactory.getProjUsersOnly(getReq);
					selectedUser.then(function (data) {
						approverTo.id = data.approverTo.id;
						approverTo.name = data.approverTo.name;
					});
				}
				$scope.addCompaniesToPrecontract = function () {
					if ($scope.precontractObj.id == undefined || $scope.precontractObj.id <= 0) {
						GenericAlertService.alertMessage("Please select precontract ID to add companies", 'Warning');
						return;
					}
					var existingPrecontactCompanyMap = [];
					angular.forEach($scope.companyTOs, function (value, key) {
						existingPrecontactCompanyMap[value.companyId] = value.companyId;

					});

					var addCompanyPopup = CompanyDetailsFactory.getCompanyDetails(existingPrecontactCompanyMap);
					addCompanyPopup.then(function (data) {
						var cmpContactMap = data.cmpContactMap;
						var cmpAddressMap = data.cmpAddressMap;
						var addressMap = [];
						var contactsMap = [];
						var addressId = null;
						var contactId = null;
						angular.forEach(data.companyTOs, function (value, key) {
							if (cmpContactMap[value.id] != null) {
								contactId = cmpContactMap[value.id].contactId;
								contactsMap[contactId] = cmpContactMap[value.id];
							}
							if (cmpAddressMap[value.id] != null) {
								addressId = cmpAddressMap[value.id].contactId;
								addressMap[addressId] = cmpAddressMap[value.id];
							}
							$scope.companyTOs.push(angular.copy({
								"companyId": value.id,
								"addressId": addressId,
								"contactId": contactId,
								"preContractId": $scope.precontractObj.id,
								"rfqCode": $scope.precontractObj.code,
								"status": 1
							}));
							$scope.companyMap[value.id] = value;
							$scope.companyMap[value.id].contactsMap = contactsMap;
							$scope.companyMap[value.id].addressMap = addressMap;
						});

					}, function (error) {
						GenericAlertService.alertMessage("Error occured while add companies to precontract", 'Error');
					});
				};

				$scope.getPreContarcttCompanyDetails = function (precontractObj) {
					var req = {
						"preContractId": precontractObj.id,
						"status": 1
					};
					RFQService.getPreContratCompanies(req).then(function (data) {

						$scope.companyTOs = data.preContractCmpTOs;
						$scope.companyMap = data.preContractCompanyMap;

					}, function (error) {
						if (error.status != undefined && error.status != null) {
							GenericAlertService.alertMessage("Company Detail(s) is/are Failed to Save", 'Error');
						} else {
							GenericAlertService.alertMessage("Company Detail(s) is/are Failed to Save", 'Error');
						}

					});

				};

				$scope.saveCompanyDetails = function () {
					if ($scope.searchProject.projId == null || $scope.searchProject.projId == undefined) {
						GenericAlertService.alertMessage("Please select the eps/project", "Warning");
						return;
					}
					else if ($scope.precontractObj.id == null || $scope.precontractObj.id == undefined) {
						GenericAlertService.alertMessage("Please select Pre-Contract ID", "Warning");
						return;
					}
					else if ($scope.precontractObj.closeDate == undefined || $scope.precontractObj.closeDate == null) {
						GenericAlertService.alertMessage("Please enter  the tender close date", "Warning");
						return;
					}

					else if ($scope.companyTOs && !$scope.companyTOs.length) {
						GenericAlertService.alertMessage("Please select atleast one company", "Warning");
						return;
					}
					const req = {
						"preContractCmpTOs": $scope.companyTOs,
						"preContractId": $scope.precontractObj.id,
						"closeDate": $scope.precontractObj.closeDate,
						"revisedCloseDate": $scope.precontractObj.revisedCloseDate,
						"reqUsrId": $scope.precontractObj.preContractReqApprTO.reqUserLabelkeyTO.id,
						"apprUsrId": $scope.precontractObj.preContractReqApprTO.apprUserLabelkeyTO.id,
					};
					RFQService.savePreContratCompanies(req).then(function (data) {
						var succMsg = GenericAlertService.alertMessageModal("Company Detail(s) saved successfully", 'Info');
						succMsg.then(function () {
							$scope.closeThisDialog(bidderDetailsPopUp);
							deferred.resolve(data);
						})
					}, function (error) {
						if (error.status != undefined && error.status != null) {
							GenericAlertService.alertMessage("Company Detail(s) is/are Failed to Save", 'Error');
						} else {
							GenericAlertService.alertMessage("Company Detail(s) is/are Failed to Save", 'Error');
						}

					});

				};

				$scope.deactiveCompanies = function () {
					deleteCmpIds = [];
					var tempCmpRequest = [];
					var flag = false;
					angular.forEach($scope.companyTOs, function (value, key) {
						if (!value.select) {
							tempCmpRequest.push(value);
						} else if (value.select) {
							if (value.companyId > 0) {
								deleteCmpIds.push(value.companyId);
							}
							flag = true;
						}
					});
					if (!flag) {
						GenericAlertService.alertMessage("Please select atleast one row to delete", "Warning");
					} else
						GenericAlertService.alertMessage("Company(s) deleted successfully", "Info");
					$scope.companyTOs = tempCmpRequest;
				}

			}]
		});
		return deferred.promise;
	}
	return service;
}]);
