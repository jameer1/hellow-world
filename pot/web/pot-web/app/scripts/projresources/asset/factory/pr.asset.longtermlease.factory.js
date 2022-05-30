'use strict';
app.factory('LongTermLeaseFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", "LeaseSrNoFactory", function(ngDialog, $q, $filter, $timeout, $rootScope,  GenericAlertService,AssetRegisterService,generalservice,assetidservice,LeaseSrNoFactory) {
	var leasePopUp;
	var service = {};
	service.leasePopUp = function(actionType,editLongTermLeaseValueData,longTermRecords,rentalHistoryRecords,longTermLeaseLast,longtermleaselastrecord) {
		var deferred = $q.defer();
		leasePopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/longtermleaserental/longtermleasepopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				$scope.longTermLeaseDoc={};
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.addLongTermLeaseData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.longTermLease = [];
				var selectedLongTermLeaseData = [];
				$scope.longTermLeaseDoc={};
				$scope.longTermLeaseDoc.isValid=true;
				$scope.paymentTypes = generalservice.modeOfPayments;


		//		if(longTermLeaseLast[0].taxAmount == undefined){
				var addLongTermLease={
						"id" : null,
						"date" : null,
						"lease" : null,
						"leaseAgreement" : null,
						"tenantId" : null,
						"tenantName" : null,
						"cumulativeNetRent" : null,
						"cumulativeTaxAmount" : null,
						"cumulativeNetRentTenant" : null,
						"cumulativeTaxAmountTenant" : null,
						"shortFallRent" : null,
						"shortFallTax" : null,
						"modeOfPayment" : null,
						"receiverBankName" : null,
						"receiverBankCode" : null,
						"reciverBankAC" : null,
						"transferReceiptNo" : null,
						"rentalAmountReceived" : null,
						"taxAmountReceived" : null,
						"accountStatus" : null,
						"status":1,
						"fixedAssetid":$scope.fixedAssetid,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						'docKey':null
					};
/*}
else{
	var addlongterm1=0;
	var taxAmount1=0;
	var addlongterm=0;
	var addamountcumrent=0;
	var addamountcumtaxamount=0;
	 addlongterm =longTermLeaseLast[0].netRentAmountPerCycle+longTermLeaseLast[0].assetMaintenanceCharges;
	 taxAmount1= longTermLeaseLast[0].taxAmount;
	if(longtermleaselastrecord[0].rentalAmountReceived!=addlongterm){

	if(longtermleaselastrecord[0].rentalAmountReceived != undefined ){
	addlongterm1=0;
			addlongterm1 +=longtermleaselastrecord[0].rentalAmountReceived + addlongterm;
		}else{
			addlongterm1 =addlongterm;
		}
	}
	if(longtermleaselastrecord[0].taxAmountReceived!=longTermLeaseLast[0].taxAmount){
		taxAmount1=0;
		if(longtermleaselastrecord[0].taxAmountReceived!=undefined){
		taxAmount1+=longtermleaselastrecord[0].taxAmountReceived +longTermLeaseLast[0].taxAmount;
	}else{
		taxAmount1= longTermLeaseLast[0].taxAmount;
	}
	}
	var currentDate = new Date();
	var TimeDiff;
	var noDays;
	var leaseStart = new Date(longTermLeaseLast[0].leaseStart);

	TimeDiff = Math.abs(currentDate - leaseStart);
  noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24));

	if(longTermLeaseLast[0].paymentCycle!=undefined && longTermLeaseLast[0].netRentAmountPerCycle!=undefined){

		var content = longTermLeaseLast[0].paymentCycle.toString().replace(/[\n\t]/g, '').split('\r\n');

		if(content == "Daily"){
			 addamountcumrent= (noDays * longTermLeaseLast[0].netRentAmountPerCycle);
		}else if(content == "Weekly"){
					addamountcumrent= ((parseInt(noDays/7)+1)*longTermLeaseLast[0].netRentAmountPerCycle);
		}else if(content == "Fortnightly"){
			addamountcumrent = ((parseInt(noDays/15)+1) * longTermLeaseLast[0].netRentAmountPerCycle);
		}else if(content == "Monthly"){
			addamountcumrent = ((parseInt(noDays/30)+1) * longTermLeaseLast[0].netRentAmountPerCycle);
		}else if(content == "Quarterly"){
			addamountcumrent = ((parseInt(noDays/90)+1) * longTermLeaseLast[0].netRentAmountPerCycle);
		}else if(content == "Half Yearly"){
			addamountcumrent = ((parseInt(noDays/182)+1) * longTermLeaseLast[0].netRentAmountPerCycle);
		}else if(content == "Yearly"){
			addamountcumrent = ((parseInt(noDays/365)+1) * longTermLeaseLast[0].netRentAmountPerCycle);
		}

	}

	if(longTermLeaseLast[0].paymentCycle!=undefined && longTermLeaseLast[0].taxAmount!=undefined){
			var content = longTermLeaseLast[0].paymentCycle.toString().replace(/[\n\t]/g, '').split('\r\n');

	if(content == "Daily"){
			addamountcumtaxamount = (noDays * (longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}else if(content == "Weekly"){
			addamountcumtaxamount = ((parseInt(noDays/7)+1) * (longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}else if(content == "Fortnightly"){
			addamountcumtaxamount = ((parseInt(noDays/15)+1) *(longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}else if(content == "Monthly"){
			addamountcumtaxamount = ((parseInt(noDays/30)+1) * (longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}else if(content == "Quarterly"){
			addamountcumtaxamount = ((parseInt(noDays/90)+1) * (longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}else if(content == "Half Yearly"){
			addamountcumtaxamount = ((parseInt(noDays/182)+1) * (longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}else if(content == "Yearly"){
			addamountcumtaxamount = ((parseInt(noDays/365)+1) * (longTermLeaseLast[0].taxableAmount*longTermLeaseLast[0].tax/100));
		}
	}


	var addLongTermLease={
			"id" : null,
			"date" : null,
			"lease" : null,
			"leaseAgreement" : null,
			"tenantId" : null,
			"tenantName" : null,
			"cumulativeNetRent" : addlongterm1,
			"cumulativeTaxAmount" : taxAmount1,
			"cumulativeNetRentTenant" : addamountcumrent,
			"cumulativeTaxAmountTenant" : addamountcumtaxamount,
			"shortFallRent" : addamountcumrent-addlongterm1,
			"shortFallTax" : addamountcumtaxamount-taxAmount1,
			"modeOfPayment" : null,
			"receiverBankName" : null,
			"receiverBankCode" : null,
			"reciverBankAC" : null,
			"transferReceiptNo" : null,
			"rentalAmountReceived" : addlongterm,
			"taxAmountReceived" : longTermLeaseLast[0].taxAmount,
			"accountStatus" : null,
			"status":1,
			"fixedAssetid":$scope.fixedAssetid,
			'docKey':null
		};
}*/
				if (actionType === 'Add') {
					$scope.longTermLease.push(addLongTermLease);
				} else {
					$scope.longTermLease = angular.copy(editLongTermLeaseValueData)
				}


				$scope.addRows = function() {
					$scope.longTermLease.push({
						"id" : null,
						"date" : null,
						"lease" : null,
						"leaseAgreement" : null,
						"tenantId" : null,
						"tenantName" : null,
						"cumulativeNetRent" : null,
						"cumulativeTaxAmount" : null,
						"cumulativeNetRentTenant" : null,
						"cumulativeTaxAmountTenant" : null,
						"shortFallRent" : null,
						"shortFallTax" : null,
						"modeOfPayment" : null,
						"receiverBankName" : null,
						"receiverBankCode" : null,
						"reciverBankAC" : null,
						"transferReceiptNo" : null,
						"rentalAmountReceived" : null,
						"taxAmountReceived" : null,
						"accountStatus" : null,
						"status":1,
						"fixedAssetid":$scope.fixedAssetid,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						'docKey':null
					});
				},

				$scope.assetLongTermLeasePopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedLongTermLeaseData.push(asset);
					} else {
						selectedLongTermLeaseData.pop(asset);
					}
				},


				$scope.deleteRows = function() {
					if (selectedLongTermLeaseData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedLongTermLeaseData.length < $scope.longTermLease.length) {
						angular.forEach(selectedLongTermLeaseData, function(value, key) {
							$scope.longTermLease.splice($scope.longTermLease.indexOf(value), 1);
						});
						selectedLongTermLeaseData = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedLongTermLeaseData.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedLongTermLeaseData.length == 1) {
						$scope.longTermLease[0]={
								"id" : null,
								"date" : null,
								"lease" : null,
								"leaseAgreement" : null,
								"tenantId" : null,
								"tenantName" : null,
								"cumulativeNetRent" : null,
								"cumulativeTaxAmount" : null,
								"cumulativeNetRentTenant" : null,
								"cumulativeTaxAmountTenant" : null,
								"shortFallRent" : null,
								"shortFallTax" : null,
								"modeOfPayment" : null,
								"receiverBankName" : null,
								"receiverBankCode" : null,
								"receiverBankAC" : null,
								"transferReceiptNo" : null,
								"rentalAmountReceived" : null,
								"taxAmountReceived" : null,
								"accountStatus" : null,
								"status":1,
								"fixedAssetid":$scope.fixedAssetid,
								"plantCommissioningDate":$scope.plantCommissioningDate,
								'docKey':null
						};
						selectedLongTermLeaseData = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				},

				$scope.calCummNetRent = function(longterm){

					if(longTermRecords[0].rentalAmountReceived!=undefined){
						longterm.cumulativeNetRent = longterm.rentalAmountReceived + longTermRecords[0].rentalAmountReceived
					}else{
						longterm.cumulativeNetRent = longterm.rentalAmountReceived;
					}
				}

				$scope.calCummTaxAmount = function(longterm){
					if(longTermRecords[0].taxAmountReceived!=undefined){
						longterm.cumulativeNetRent = longterm.rentalAmountReceived + longTermRecords[0].taxAmountReceived
					}else{
						longterm.cumulativeNetRent = longterm.rentalAmountReceived;
					}
				}

				$scope.calcCummAmountandTax = function(){
					if(rentalHistoryRecords[0].leaseStart!=undefined && rentalHistoryRecords[0].leaseFinish){
						var currentDate = new Date();
						var TimeDiff;
						var noDays;
						var leaseStart = new Date(rentalHistoryRecords[0].leaseStart);
						var leaseFinish = new Date(rentalHistoryRecords[0].leaseFinish);
						if(leaseFinish <= currentDate){
							 TimeDiff = Math.abs(leaseFinish - leaseStart);
							 noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24));
						}else{
							 TimeDiff = Math.abs(currentDate - leaseStart);
							 noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24));
						}

						if(rentalHistoryRecords[0].paymentCycle!=undefined && rentalHistoryRecords[0].grossRent!=undefined){
							if(rentalHistoryRecords[0].paymentCycle === "Daily"){
								longterm.cumulativeNetRentTenant = (noDays * rentalHistoryRecords[0].grossRent);
							}else if(rentalHistoryRecords[0].paymentCycle === "Weekly"){
								longterm.cumulativeNetRentTenant = ((noDays/7) * rentalHistoryRecords[0].grossRent);
							}else if(rentalHistoryRecords[0].paymentCycle === "Fortnightly"){
								longterm.cumulativeNetRentTenant = ((noDays/15) * rentalHistoryRecords[0].grossRent);
							}else if(rentalHistoryRecords[0].paymentCycle === "Monthly"){
								longterm.cumulativeNetRentTenant = ((noDays/30) * rentalHistoryRecords[0].grossRent);
							}else if(rentalHistoryRecords[0].paymentCycle === "Quarterly"){
								longterm.cumulativeNetRentTenant = ((noDays/90) * rentalHistoryRecords[0].grossRent);
							}else if(rentalHistoryRecords[0].paymentCycle === "Half Yearly"){
								longterm.cumulativeNetRentTenant = ((noDays/182) * rentalHistoryRecords[0].grossRent);
							}else if(rentalHistoryRecords[0].paymentCycle === "Yearly"){
								longterm.cumulativeNetRentTenant = ((noDays/365) * rentalHistoryRecords[0].grossRent);
							}
						}

						if(rentalHistoryRecords[0].paymentCycle!=undefined && rentalHistoryRecords[0].taxAmount!=undefined){
							if(rentalHistoryRecords[0].paymentCycle === "Daily"){
								longterm.cumulativeTaxAmountTenant = (noDays * rentalHistoryRecords[0].taxAmount);
							}else if(rentalHistoryRecords[0].paymentCycle === "Weekly"){
								longterm.cumulativeTaxAmountTenant = ((noDays/7) * rentalHistoryRecords[0].taxAmount);
							}else if(rentalHistoryRecords[0].paymentCycle === "Fortnightly"){
								longterm.cumulativeTaxAmountTenant = ((noDays/15) * rentalHistoryRecords[0].taxAmount);
							}else if(rentalHistoryRecords[0].paymentCycle === "Monthly"){
								longterm.cumulativeTaxAmountTenant = ((noDays/30) * rentalHistoryRecords[0].taxAmount);
							}else if(rentalHistoryRecords[0].paymentCycle === "Quarterly"){
								longterm.cumulativeTaxAmountTenant = ((noDays/90) * rentalHistoryRecords[0].taxAmount);
							}else if(rentalHistoryRecords[0].paymentCycle === "Half Yearly"){
								longterm.cumulativeTaxAmountTenant = ((noDays/182) * rentalHistoryRecords[0].taxAmount);
							}else if(rentalHistoryRecords[0].paymentCycle === "Yearly"){
								longterm.cumulativeTaxAmountTenant = ((noDays/365) * rentalHistoryRecords[0].taxAmount);
							}
						}

					}
				}

				$scope.checkFile = function (file) {
					console.log("checkFile function")
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg",".xls",".xlsx"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.longTermLeaseDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX,XLS,XLSX";
						$scope.longTermLeaseDoc.isValid=false;
					}else if( (file.size > 1000000)){
						$scope.longTermLeaseDoc.errorMessage="Supported Max. File size 1MB";
						$scope.longTermLeaseDoc.isValid=false;
					}else{
						$scope.longTermLeaseDoc.isValid=true;
					}
				}

                $scope.saveAssetslongterm = function() {
					/*if (!$scope.longTermLeaseDoc.isValid) {
						GenericAlertService.alertMessage("Please upload valid file !!", 'Warning');
						return ;
					}*/


					// if($scope.longTermLease[0].tenantId!=null	){

					var longTermLeaseActualRetrunsSaveReq = {
						"longTermLeaseActualRetrunsDtlTOs" : $scope.longTermLease,
						"folderCategory" : "Long Term Lease:Rental/Revenue-Actual Revenue",
						"userId" : $rootScope.account.userId
					};
					AssetRegisterService.saveLongTermLeaseActualRetruns($scope.fileInfo,longTermLeaseActualRetrunsSaveReq).then(function(data) {
						// var succMsg = GenericAlertService.alertMessageModal('Lease:Rental-Actual Returns Record(s) is/are Saved/Updated successfully','Info');
						var succMsg = GenericAlertService.alertMessageModal('Lease:Rental-Actual Returns Record(s) saved successfully','Info');
						succMsg.then(function() {
						var returnPopObj = {
							"longTermLeaseActualRetrunsDtlTOs" : data.longTermLeaseActualRetrunsDtlTOs
						};
						deferred.resolve(returnPopObj);
					});
				}, function(error) {
					if (error.status != null && error.status != undefined) {
						GenericAlertService.alertMessageModal('Lease:Rental-Actual Returns Record(s) Failed to Save ', "Error");
					}
				});
				    ngDialog.close();

					// }
					// else {
					// 		GenericAlertService.alertMessage('Please Enter the mandatory field',"Error");
					// }
			}
			var longtermleaseallactive={
					"currentStatus":'Active',
					"status" : 1,
					"fixedAssetid" : $scope.fixedAssetid
				};


			$scope.leaseSrNo = function(longterm) {
				

				var leaseSrPopup = LeaseSrNoFactory.leaseSrNoPopupDetails(longtermleaseallactive);
				leaseSrPopup.then(function(data) {
					console.log("data" +JSON.stringify(data));
					console.log('$scope.longtermleaselastrecord'+JSON.stringify(longtermleaselastrecord));
					var addlongterm1=0;
					var taxAmount1=0;
					var boolean=false;
					var cumulativenetrent=0;
					var cumulativeTaxAmount=0;
					var addamountcumrent=0;
					var addamountcumtaxamount=0;
					longterm.lease = data.searchCompany.lease;
					longterm.leaseAgreement = data.searchCompany.leaseAgreement;
					longterm.tenantId= data.searchCompany.tenantId;
					longterm.tenantName = data.searchCompany.tenantName;
					longterm.rentalAmountReceived= data.searchCompany.addlongterm;
					longterm.taxAmountReceived = data.searchCompany.taxAmount1;
					//	console.log('longtermleaselastrecord'+longtermleaselastrecord[0].tenantId);


						angular.forEach(longtermleaselastrecord, function (value, key) {
					 	 //var total1 =0;
						
					 					value.tenantId;
											console.log('value.tenantId 123'+value.tenantId);
											if(value.tenantId==data.searchCompany.tenantId){
												
													
													cumulativenetrent=value.cumulativeNetRent;
													cumulativeTaxAmount=value.cumulativeTaxAmount;
													boolean=true;
											}

					 				 });
									 if(boolean==true){
									
										  
										 if(cumulativenetrent==data.searchCompany.addlongterm){
											 
											 addlongterm1=parseInt(cumulativenetrent) +parseInt(data.searchCompany.addlongterm);
											 taxAmount1=parseInt(cumulativeTaxAmount)+parseInt(data.searchCompany.taxAmount1);
											 longterm.cumulativeNetRent= addlongterm1;
											 longterm.cumulativeTaxAmount=taxAmount1;
										 }else{
											 
											 addlongterm1=parseInt(cumulativenetrent) +parseInt(data.searchCompany.addlongterm);
											taxAmount1=parseInt(cumulativeTaxAmount)+parseInt(data.searchCompany.taxAmount1);
											 longterm.cumulativeNetRent=addlongterm1;
											 longterm.cumulativeTaxAmount=taxAmount1;
										 }

									 }else{
										
										 addlongterm1=data.searchCompany.addlongterm;
										 taxAmount1=data.searchCompany.taxAmount1;
										 longterm.cumulativeNetRent= addlongterm1 ;
										 longterm.cumulativeTaxAmount = taxAmount1;
									 }
									 var currentDate = new Date();
								 	var TimeDiff;
								 	var noDays;
								 	var leaseStart = new Date(data.searchCompany.leaseStart);

								 	TimeDiff = Math.abs(currentDate - leaseStart);
								   noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24))-1;
								   									 	

								 	if(data.searchCompany.paymentCycle!=undefined && data.searchCompany.netRentAmountPerCycle!=undefined){

								 		var content = data.searchCompany.paymentCycle.toString().replace(/[\n\t]/g, '').split('\r\n');

								 		if(content == "Daily"){
								 			 addamountcumrent= (noDays * data.searchCompany.netRentAmountPerCycle);
								 		}else if(content == "Weekly"){
								 					addamountcumrent= ((parseInt(noDays/7)+1)*data.searchCompany.netRentAmountPerCycle);
								 		}else if(content == "Fortnightly"){
								 			addamountcumrent = ((parseInt(noDays/15)+1) * data.searchCompany.netRentAmountPerCycle);
								 		}else if(content == "Monthly"){
								 			addamountcumrent = ((parseInt(noDays/30)+1) * data.searchCompany.netRentAmountPerCycle);
								 		}else if(content == "Quarterly"){
								 			addamountcumrent = ((parseInt(noDays/90)+1) * data.searchCompany.netRentAmountPerCycle);
								 		}else if(content == "Half Yearly"){
								 			addamountcumrent = ((parseInt(noDays/182)+1) * data.searchCompany.netRentAmountPerCycle);
								 		}else if(content == "Yearly"){
								 			addamountcumrent = ((parseInt(noDays/365)+1) * data.searchCompany.netRentAmountPerCycle);
								 		}

								 	}

								 if(data.searchCompany.paymentCycle!=undefined && data.searchCompany.taxableAmount!=undefined){
								 			var content = data.searchCompany.paymentCycle.toString().replace(/[\n\t]/g, '').split('\r\n');

								 	if(content == "Daily"){
								 			addamountcumtaxamount = (noDays * (data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}else if(content == "Weekly"){
								 			addamountcumtaxamount = ((parseInt(noDays/7)+1) * (data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}else if(content == "Fortnightly"){
								 			addamountcumtaxamount = ((parseInt(noDays/15)+1) *(data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}else if(content == "Monthly"){
								 			addamountcumtaxamount = ((parseInt(noDays/30)+1) * (data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}else if(content == "Quarterly"){
								 			addamountcumtaxamount = ((parseInt(noDays/90)+1) * (data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}else if(content == "Half Yearly"){
								 			addamountcumtaxamount = ((parseInt(noDays/182)+1) * (data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}else if(content == "Yearly"){
								 			addamountcumtaxamount = ((parseInt(noDays/365)+1) * (data.searchCompany.taxableAmount*data.searchCompany.tax/100));
								 		}
								 	}
									longterm.cumulativeNetRentTenant= addamountcumrent;
									longterm.cumulativeTaxAmountTenant = addamountcumtaxamount;
									longterm.shortFallRent = addamountcumrent-addlongterm1;
									longterm.shortFallTax= addamountcumtaxamount-taxAmount1;

				}, function(error) {
					GenericAlertService.alertMessage("Error occured while fetching  details", 'Error');
				});
			}
		} ]
	});
	return deferred.promise;
}

			service.longTermLeasePopUpOnLoad = function(actionType, editLongTermLeaseValueData,longTermRecords,rentalHistoryRecords,longTermLeaseLast,longtermleaselastrecord) {
				var deferred = $q.defer();
				var leasePopUp = service.leasePopUp(actionType, editLongTermLeaseValueData,longTermRecords,rentalHistoryRecords,longTermLeaseLast,longtermleaselastrecord);
				leasePopUp.then(function(data) {

					var returnPopObj = {
						"longTermLeaseActualRetrunsDtlTOs" : data.longTermLeaseActualRetrunsDtlTOs
					}

					deferred.resolve(returnPopObj);
				});

				return deferred.promise;
			}
			return service;
			}]);
