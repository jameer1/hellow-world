'use strict';
app.factory('RentalHistoryFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,generalservice,assetidservice) {
	var rentalHistoryPopUp;
	var service = {};
	service.rentalHistoryPopUp = function(actionType, editRentalHistoryData,leaseActualfinishdate) {
		var deferred = $q.defer();
		rentalHistoryPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/rentalhsitory/rentalhistorypopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {


				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.addRentalHistoryData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.rentalHistory = [];
				var selectedRentalHistory = [];
				$scope.rentalHistoryDoc={};
				$scope.rentalHistoryDoc.isValid=true;
				$scope.paymentCycles = ["Daily","Weekly","Fortnightly","Monthly","Quarterly","Half Yearly","Yearly"]; //generalservice.pymentCycles;
				$scope.weekTOs = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
				$scope.monthsTOs = generalservice.monthly;
				$scope.monthTOs = generalservice.years;
				$scope.maintenances = generalservice.fixedVariableChanrges;
				$scope.status = generalservice.rentalHistoryStatus;
				var leasefinishdate=leaseActualfinishdate;
				const months = ["JAN", "FEB", "MAR","APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
					var leasefinishdate1 = new Date(leasefinishdate);
					 leasefinishdate1.setDate(leasefinishdate1.getDate() + 1 );
					var formatted_date = leasefinishdate1.getDate() + "-" + months[leasefinishdate1.getMonth()] + "-" + leasefinishdate1.getFullYear()
					if(leaseActualfinishdate !=undefined){
							var plantcommissioningdate=formatted_date;
					}else{
						var plantcommissioningdate=$scope.plantCommissioningDate;
					}
				var addRentalHistory = {
						"id" : null,
						"leaseAgreement":null,
						"leaseStart":null,
						"leaseFinish":null,
						"tenantName":null,
						"tenantAddress":null,
						"paymentCycle":null,
						"netRentAmountPerCycle":null,
						"maintenanceCharges":null,
						"assetMaintenanceCharges":null,
						"taxableAmount":null,
						"tax":null,
						"taxAmount":null,
						"grossRent":null,
						"paymentCycleDueDate":null,
						"leaseExtendedFinshDate":null,
						"leaseActualFinishFinshDate":null,
						"currentStatus":null,
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"plantCommissioningDate":plantcommissioningdate,
						'docKey':null

					};

				if (actionType === 'Add') {
					$scope.rentalHistory.push(addRentalHistory);
				} else {
					$scope.rentalHistory = angular.copy(editRentalHistoryData)
				}

				$scope.addRows = function() {
					$scope.rentalHistory.push({
						"id" : null,
						"leaseAgreement":null,
						"leaseStart":null,
						"leaseFinish":null,
						"tenantName":null,
						"tenantAddress":null,
						"paymentCycle":null,
						"netRentAmountPerCycle":null,
						"maintenanceCharges":null,
						"assetMaintenanceCharges":null,
						"taxableAmount":null,
						"tax":null,
						"taxAmount":null,
						"grossRent":null,
						"paymentCycleDueDate":null,
						"leaseExtendedFinshDate":null,
						"leaseActualFinishFinshDate":null,
						"currentStatus":null,
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"plantCommissioningDate":plantcommissioningdate,
						'docKey':null

					});
				},

				$scope.assetMaintenanceRentTaxAmount = function(addRentalHsitoryData){

					//addRentalHsitoryData
					addRentalHsitoryData.grossRent = parseInt(addRentalHsitoryData.netRentAmountPerCycle) + parseInt(addRentalHsitoryData.assetMaintenanceCharges) + parseInt(addRentalHsitoryData.taxAmount);
				},

				$scope.assetRentalHistoryPopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedRentalHistory.push(asset);
					} else {
						selectedRentalHistory.pop(asset);
					}
				},

				$scope.selectactualdate=function(addRentalHsitoryData){
							addRentalHsitoryData.leaseFinish=	addRentalHsitoryData.leaseActualFinishFinshDate;
				},

				$scope.deleteRows = function() {
					if (selectedRentalHistory.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedRentalHistory.length < $scope.rentalHistory.length) {
						angular.forEach(selectedRentalHistory, function(value, key) {
							$scope.rentalHistory.splice($scope.rentalHistory.indexOf(value), 1);
						});
						selectedRentalHistory = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedRentalHistory.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedRentalHistory.length == 1) {
						$scope.rentalHistory[0] = {
							"status" : "1",
							"selected" : false,
							"leaseAgreement":null,
							"leaseStart":null,
							"leaseFinish":null,
							"tenantName":null,
							"tenantAddress":null,
							"paymentCycle":null,
							"netRentAmountPerCycle":null,
							"maintenanceCharges":null,
							"assetMaintenanceCharges":null,
							"taxableAmount":null,
							"tax":null,
							"taxAmount":null,
							"grossRent":null,
							"paymentCycleDueDate":null,
							"leaseExtendedFinshDate":null,
							"leaseActualFinishFinshDate":null,
							"currentStatus":null,
							'fixedAssetid' : $scope.fixedAssetid,
							"plantCommissioningDate":plantcommissioningdate,
							'docKey':null

						};
						selectedRentalHistory = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},

				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.rentalHistoryDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";
						$scope.rentalHistoryDoc.isValid=false;
					}else if( (file.size > 5000000)){
						$scope.rentalHistoryDoc.errorMessage="Supported Max. File size 1MB";
						$scope.rentalHistoryDoc.isValid=false;
					}else{
						$scope.rentalHistoryDoc.isValid=true;
					}

				},
				$scope.startDate = $scope.rentalHistory[0].leaseStart;
				console.log($scope.startDate)
				$scope.saveRentalHistory = function() {
					let leaseStartDate = new Date($scope.rentalHistory[0].leaseStart);
				//	let paymentCycleDueDate = ($scope.rentalHistory[0].paymentCycleDueDate != null) ? new Date($scope.rentalHistory[0].paymentCycleDueDate) : null
					let leaseExtendedFDate =  ($scope.rentalHistory[0].leaseExtendedFinshDate != null) ? new Date($scope.rentalHistory[0].leaseExtendedFinshDate) : null
					let leaseExtendedAFininshDate = ($scope.rentalHistory[0].leaseActualFinishFinshDate != null) ? new Date($scope.rentalHistory[0].leaseActualFinishFinshDate) : null
						/*if (!$scope.rentalHistoryDoc.isValid) {
							GenericAlertService.alertMessage("Please upload valid file !!", 'Warning');
							return ;
						}*/
						console.log($scope.rentalHistory[0])
						/*if(paymentCycleDueDate !="" && paymentCycleDueDate != null && leaseStartDate >= paymentCycleDueDate){
							GenericAlertService.alertMessage("Payment Cycle Due Date should be greater than Lease Start Date !!", 'Info');
							return;
						}*/
						if(leaseExtendedFDate != "" && leaseExtendedFDate != null && leaseStartDate >= leaseExtendedFDate){
							GenericAlertService.alertMessage("Lease Extended finish Date should be greater than Lease Start Date !!", 'Info');
							return;
						}
						if(leaseExtendedAFininshDate !="" && leaseExtendedAFininshDate != null && leaseStartDate >= leaseExtendedAFininshDate){
							GenericAlertService.alertMessage("Lease Actual Finish Date should be greater than Lease Start Date !!",'Info');
							return;
						}
				
						var rentalHistorySaveReq = {
							"longTermLeasingDtlTOs" : $scope.rentalHistory,
							"folderCategory" : "Long Term Leasing/Rental History",
							"userId" : $rootScope.account.userId
						};
									AssetRegisterService.saveRentalHistory($scope.fileInfo,rentalHistorySaveReq).then(function(data) {
										var succMsg = GenericAlertService.alertMessageModal('Rental History Detail(s) Saved successfully','Info');
										succMsg.then(function() {
										var returnPopObj = {
											"longTermLeasingDtlTOs" : data.longTermLeasingDtlTOs
										};
										deferred.resolve(returnPopObj);
									});
								}, function(error) {
									if (error.status != null && error.status != undefined) {
										GenericAlertService.alertMessageModal('Rental History Detail(s) is/are Failed to Save/Update ', "Error");
									}
								});
								    ngDialog.close();
							}
						} ]
					});
					return deferred.promise;
				}


	service.rentalHistoryPopUpOnLoad = function(actionType, editRentalHistoryData,leaseActualfinishdate) {
		var deferred = $q.defer();
		var rentalHistoryPopUp = service.rentalHistoryPopUp(actionType, editRentalHistoryData,leaseActualfinishdate);
		rentalHistoryPopUp.then(function(data) {

			var returnPopObj = {
				"longTermLeasingDtlTOs" : data.longTermLeasingDtlTOs,

			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);
