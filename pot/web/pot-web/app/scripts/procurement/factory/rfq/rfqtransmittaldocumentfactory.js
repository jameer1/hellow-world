'use strict';

app.factory('RfqTransmittalDocumentFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "RFQService","PreContractExternalService", "GenericAlertService","Principal","ClientService", function(ngDialog, $q, $filter, $timeout, $rootScope,blockUI,
		RFQService,PreContractExternalService, GenericAlertService,Principal,ClientService) {
	var service = {};
	service.getPreContractCmpDistributionDocs = function(preContractCompanyMap, preContractCmpTOs,
			documentStatusTO, precontractObj,searchProject) {
		var deferred = $q.defer();
		var docReq = {
			"status" : 1,
			"distributionDocId" : documentStatusTO.id
		};
		RFQService.getPreContractCmpDistributionDocs(docReq).then(
				function(data) {
					var popupdata = service
							.openPopup(preContractCompanyMap, preContractCmpTOs,
									data.preContractCmpDistributionDocTOs, documentStatusTO,
									precontractObj,searchProject);
					popupdata.then(function(data) {
						deferred.resolve(data);
					});
				},
				function(error) {
					GenericAlertService.alertMessage(
							"Error occured while getting PreContract approval details", 'Error');
				});
		return deferred.promise;

	}, service.openPopup = function(companyMap, preContractCmpTOs,
			preContractCmpDistributionDocTOs, documentStatusTO, precontractObj,searchProject) {console.log(precontractObj);
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template : 'views/procurement/RFQ/rfqtransmittaldocument.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.preContractCmpMap = companyMap;
				$scope.preContractCmpTOs = preContractCmpTOs;
				$scope.documentStatusTO = documentStatusTO;
				$scope.precontractObj = precontractObj;
				console.log($scope.precontractObj);
				$scope.searchProject = searchProject;
				$scope.distibutedCmpDocMap = [];
				$scope.vendors = [];
				$scope.preContractData = { 'vendorDetails': undefined, 'clientDetails': undefined };
				angular.forEach(preContractCmpDistributionDocTOs, function(value, key) {
					$scope.distibutedCmpDocMap[value.preContractCmpId] = true;
				});
				
				$scope.transmittalMsgData ={
					"id":null,
					"contractId":$scope.precontractObj.id,
					"projId":$scope.precontractObj.projId,
					"desc":null,
					"issuedBy" : null,
					"acceptedBy" : null,
					"sign" : null,
					"name" : null,
					"designation" : null,
					"companyRep" : null,
					"vendorRep" : null,
					"vendorName" : null,
					"vendorSign" : null,
					"vendorDesignation" : null,
					"status" : 1
				};
			
				$scope.saveTransmittalMsg = function() {
					var savereq = {
						"documentTransmittalTO" : $scope.transmittalMsgData,
						"status" : 1
						
					};
					blockUI.start();
					RFQService
							.saveTransmittalMsg(savereq)
							.then(
									function(data) {
										blockUI.stop();
										var succMsg = GenericAlertService
												.alertMessageModal(
														"Transmittal message details are saved successfully",
														'Info');
										succMsg
												.then(function(
														popData) {
													var returnPopObj = {
														"documentTransmittalTO" : data
													};
													deferred
															.resolve(returnPopObj);
												});
									},
									function(error) {
										blockUI.stop();
										GenericAlertService
												.alertMessage(
														'Transmittal message details are failed to Save',
														'Error');
									});
				},
				$scope.getPreContractCompanies=function() {
					console.log("abslfnlkjfnalsfnsakjlfng")
					$scope.vendorName = null;
					$scope.vendorDesignation = null;
					var cmpGetReq = {
						"status": 1,
						"preContractId": $scope.precontractObj.id

					};console.log($scope.precontractObj.id);
					PreContractExternalService.getPreContratCompanies(cmpGetReq).then(
						function (data) {console.log(data);
						
						var companyIds = [];
						//	preContractCmpTOs = data.preContractCmpTOs;
							angular.forEach(data.preContractCmpTOs, function (company, key) {
								companyIds.push(company.id);
								console.log(companyIds);
								var vendorDetails = data.preContractCompanyMap[company.companyId];
								console.log(vendorDetails);
								vendorDetails.preContractCmpId = company.id;
								console.log(company.addressId);
								var vendorAddress = vendorDetails.addressMap[company.addressId];
								console.log(vendorAddress);
								var groupedDetails = vendorDetails.cmpName + " (" + vendorDetails.cmpCode + ")";
								console.log(groupedDetails);
								vendorDetails.vendorName = groupedDetails;
								if (vendorAddress) {
									groupedDetails += "\n" + vendorAddress.address + ",";
									groupedDetails += "\n" + vendorAddress.city + ",";
									groupedDetails += "\n" + vendorAddress.pincode + ",";
									groupedDetails += "\n" + vendorAddress.state + ".";
								}
								vendorDetails.addressDetails = groupedDetails;
								vendorDetails.contactDetails = vendorDetails.contactsMap[company.contactId];
								$scope.vendors.push(vendorDetails);
								console.log($scope.vendors);
							});
						
						
						
						
						
						
						
						
						
							/*preContractCmpTOs = data.preContractCmpTOs;
							console.log(preContractCmpTOs);
							var vendorDetails = data.preContractCompanyMap[data.preContractCmpTOs[0].companyId];
							console.log(vendorDetails);
							var vendorAddress = vendorDetails.addressMap[data.preContractCmpTOs[0].addressId];
							console.log('Vendor adddddddd',vendorAddress)
							var groupedDetails = vendorDetails.cmpName + " (" + vendorDetails.cmpCode + ")";
							console.log('groupedDetails111111',groupedDetails,'kkkkk')
								$scope.preContractData.vendor=vendorDetails.cmpName + " (" + vendorDetails.cmpCode + ")";
								console.log($scope.preContractData.vendor);
							//$scope.selectedData.vendorName = groupedDetails;
							
								groupedDetails += "\n" + vendorAddress.address + ",";
								groupedDetails += "\n" + vendorAddress.city + ",";
								groupedDetails += "\n" + vendorAddress.pincode + ",";
								groupedDetails += "\n" + vendorAddress.state + ".";
								console.log('groupedDetails',groupedDetails)
							
							var contactDetails = vendorDetails.contactsMap[data.preContractCmpTOs[0].contactId];console.log(contactDetails);
							
								// $scope.singlePOData.acceptedBy = contactDetails.contactName;
								$scope.transmittalMsgData.acceptedBy = contactDetails.email;
								$scope.transmittalMsgData.vendorName = contactDetails.contactName;
								$scope.transmittalMsgData.vendorDesignation = contactDetails.designation;
								$scope.preContractData.vendor=vendorDetails.cmpName + " (" + vendorDetails.cmpCode + ")";
								console.log($scope.preContractData.vendor);
							

							$scope.preContractData.vendor = groupedDetails;*/

						},
						function (error) {
							GenericAlertService.alertMessage("Error occured while getting Project Material classes", 'Error');
						});

				};
				$scope.getClientDetails=function() {
					$scope.issuerName = null;
					$scope.issuerDesignation = null;
					Principal.identity().then(function (acc) {console.log(acc);
						var clientReq = {
							"clientId": acc.clientId
						};
						$scope.transmittalMsgData.designation = acc.designation;
						$scope.transmittalMsgData.issuedBy = acc.username;
						ClientService.getClientById(clientReq).then(
							function (data) {console.log(data);
								$scope.transmittalMsgData.name = data.name;
								$scope.preContractData.company = data.name + ",\n" + data.address + ",\n" + data.country;
							},
							function (error) {
								GenericAlertService.alertMessage("Error occured while getting Client details", 'Error');
							});
					});


				};
				
				
				
				$scope.vendorChanged = function () {
					$scope.preContractData.vendor = $scope.selectedVendor.addressDetails;
					if($scope.selectedVendor.contactDetails){
					$scope.transmittalMsgData.acceptedBy = $scope.selectedVendor.contactDetails.email;
					$scope.transmittalMsgData.vendorName = $scope.selectedVendor.contactDetails.contactName;
					$scope.transmittalMsgData.vendorDesignation = $scope.selectedVendor.contactDetails.designation;
					}
					$scope.transmittalMsgData.desc = "This is to inform  you that  we had   transmitted  the following documents  to your nominated E mail address  in  relation to  RFQ / Bidding process of their above  mentioned   project  and Pre- contract. Please acknowledge receipt of those  documents.";
					$scope.precontractObj.date = $filter('date')(new Date(), "dd-MMM-yyyy");
				
					/*$scope.selectedData.vendorName = $scope.selectedVendor.vendorName;
					initMultiPOData();
					$scope.multiPOData.vendorName = $scope.selectedVendor.contactDetails.contactName;
					$scope.multiPOData.vendorDesignation = $scope.selectedVendor.contactDetails.designation;
					$scope.multiPOData.issuerName = $scope.preContractData.issuerName;
					$scope.multiPOData.issuerDesignation = $scope.preContractData.issuerDesignation;

					if (purchaseOrders.length) {
						var poFound = false
						angular.forEach(purchaseOrders, function (po, key) {
							if (po.preContractCmpTO.id === $scope.selectedVendor.preContractCmpId) {
								poFound = true;
								$scope.po.code = po.code;
								$scope.po.id = po.id;
								$scope.multiPOData = po.poDetailsTO;
								$scope.po.createdOn = $filter('date')(new Date(po.createdOn), "dd-MMM-yyyy");
								$scope.selectedData.date = $scope.po.createdOn;
								$scope.selectedData.poId = po.id;
							}
						});
						if (!poFound) {
							$scope.po.createdOn = $filter('date')(new Date(), "dd-MMM-yyyy");
							$scope.po.code = undefined;
							$scope.po.id = null;
							$scope.selectedData.poId = null;
						}

					}*/
				}
				
				
				
				$scope.getPreContractDocuments = function() {
					var deferred = $q.defer();
					var req = {
						"projId" :$scope.precontractObj.projId,
						"contractId" : $scope.precontractObj.id,
						"status" : 1
					};
					var serviceData = RFQService.getPreContratDocs(req);
					serviceData.then(function(data) {
						$scope.preContractDocsTOs = data.preContractDocsTOs
						var popupData = service.openPopUp(data.preContractDocsTOs);
						popupData.then(function(data) {
							deferred.resolve(data);
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while getting pre-contract documents",
									'Error');
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting pre-contract documents",
								'Error');
					});
					return deferred.promise;
				}
				
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);
