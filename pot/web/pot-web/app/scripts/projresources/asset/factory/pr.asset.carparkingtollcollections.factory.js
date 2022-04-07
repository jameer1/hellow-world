'use strict';
app.factory('CarParkingTollCollectionsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, AssetRegisterService,assetidservice) {
	var assetDetailsPopUp;
	var service = {};
	service.carParkingDetailsPopUpOnLoad = function(actionType,editCarParkingTollRecordData,carparkinglastrecord,rentalValue) {

		var deferred = $q.defer();
		assetDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/carparkingandtollcollections/carparkingtollpopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
			    $scope.carParkingTollRecord = [];
				$scope.carParkingTollDoc={};
				$scope.carparkinglastrecord =null;

if(carparkinglastrecord[0].cumulativeNetAmount == undefined){

	var currentDate = new Date();
	 var TimeDiff;
	 var noDays;
	 var leaseStart = new Date(rentalValue[0].effectiveDate);

	 TimeDiff = Math.abs(currentDate - leaseStart);
		noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24));



	if(rentalValue[0].revenueCollectionCycle!=undefined && rentalValue[0].estimatedRentPerCycle!=undefined){
var addamountcumrent=0;
		 var content = rentalValue[0].revenueCollectionCycle;
	//	 alert(noDays); Note: In 17 server changes were not reflected, so again resubmiting the file. ignore commnet...!!!
		 if(content == "Daily"){
				addamountcumrent= (noDays * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Weekly"){
					 addamountcumrent= ((parseInt(noDays/7)+1)*rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Fortnightly"){
			 addamountcumrent = ((parseInt(noDays/15)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Monthly"){
			 addamountcumrent = ((parseInt(noDays/30)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Quarterly"){
			 addamountcumrent = ((parseInt(noDays/90)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Half Yearly"){
			 addamountcumrent = ((parseInt(noDays/182)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Yearly"){
			 addamountcumrent = ((parseInt(noDays/365)+1) * rentalValue[0].estimatedRentPerCycle);
		 }

	 }
//	 var total = 0;
	 //angular.forEach(rentalValue, function (value, key) {
	//	var total1 =0;
			//			total +=total1 + addamountcumrent;
			//		});
//	 var num = addamountcumrent*0.1;
//	 var n = num.toFixed(2);
//		 var round =Math.round(n);

 	if(rentalValue[0].revenueCollectionCycle!=undefined ){
			 var content = rentalValue[0].revenueCollectionCycle;
var addamountcumtaxamount=0;
	 if(content == "Daily"){
			 addamountcumtaxamount = (noDays * addamountcumrent*0.1);
		 }else if(content == "Weekly"){
			 addamountcumtaxamount = ((parseInt(noDays/7)+1) * addamountcumrent*0.1);
		 }else if(content == "Fortnightly"){
			 addamountcumtaxamount = ((parseInt(noDays/15)+1) *addamountcumrent*0.1);
		 }else if(content == "Monthly"){
			 addamountcumtaxamount = ((parseInt(noDays/30)+1) * addamountcumrent*0.1);
		 }else if(content == "Quarterly"){
			 addamountcumtaxamount = ((parseInt(noDays/90)+1) * addamountcumrent*0.1);
		 }else if(content == "Half Yearly"){
			 addamountcumtaxamount = ((parseInt(noDays/182)+1) * addamountcumrent*0.1);
		 }else if(content == "Yearly"){
			 addamountcumtaxamount = ((parseInt(noDays/365)+1) * addamountcumrent*0.1);
		 }
	 }
				var addCarParkingTollRecord={
						"status" : 1,
						"id" : null,
						"date" : null,
						"modeOfPayment" : null,
						"bankName" : null,
						"bankCode" : null,
						"bankAc" : null,
						"transferReceiptNo" : null,
						"netAmount" : null,
						"taxAmount" : null,
						"cumulativeNetAmount" : null,
						"cumulativeTaxAmount" : null,
						"forecastNetAmt" : addamountcumrent,
						"forecastTaxAmt" : addamountcumtaxamount,
						"cumulativeNetRevenue" : null,
						"cumulativeTax" : null,
						'fixedAssetid' : $scope.fixedAssetid,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						'docKey':null
						};
}else{
	var total = 0;



	var currentDate = new Date();
	 var TimeDiff;
	 var noDays;
	 var leaseStart = new Date(rentalValue[0].effectiveDate);

	 TimeDiff = Math.abs(currentDate - leaseStart);
		noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24));



	if(rentalValue[0].revenueCollectionCycle!=undefined && rentalValue[0].estimatedRentPerCycle!=undefined){
var addamountcumrent=0;
		 var content = rentalValue[0].revenueCollectionCycle;
	//	 alert(noDays);
		 if(content == "Daily"){
				addamountcumrent= (noDays * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Weekly"){
					 addamountcumrent= ((parseInt(noDays/7)+1)*rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Fortnightly"){
			 addamountcumrent = ((parseInt(noDays/15)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Monthly"){
			 addamountcumrent = ((parseInt(noDays/30)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Quarterly"){
			 addamountcumrent = ((parseInt(noDays/90)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Half Yearly"){
			 addamountcumrent = ((parseInt(noDays/182)+1) * rentalValue[0].estimatedRentPerCycle);
		 }else if(content == "Yearly"){
			 addamountcumrent = ((parseInt(noDays/365)+1) * rentalValue[0].estimatedRentPerCycle);
		 }

	 }
//	 var total = 0;
	 //angular.forEach(rentalValue, function (value, key) {
	//	var total1 =0;
			//			total +=total1 + addamountcumrent;
			//		});
//	 var num = addamountcumrent*0.1;
//	 var n = num.toFixed(2);
//		 var round =Math.round(n);

 	if(rentalValue[0].revenueCollectionCycle!=undefined ){
			 var content = rentalValue[0].revenueCollectionCycle;
var addamountcumtaxamount=0;
	 if(content == "Daily"){
			 addamountcumtaxamount = (noDays * addamountcumrent*0.1);
		 }else if(content == "Weekly"){
			 addamountcumtaxamount = ((parseInt(noDays/7)+1) * addamountcumrent*0.1);
		 }else if(content == "Fortnightly"){
			 addamountcumtaxamount = ((parseInt(noDays/15)+1) *addamountcumrent*0.1);
		 }else if(content == "Monthly"){
			 addamountcumtaxamount = ((parseInt(noDays/30)+1) * addamountcumrent*0.1);
		 }else if(content == "Quarterly"){
			 addamountcumtaxamount = ((parseInt(noDays/90)+1) * addamountcumrent*0.1);
		 }else if(content == "Half Yearly"){
			 addamountcumtaxamount = ((parseInt(noDays/182)+1) * addamountcumrent*0.1);
		 }else if(content == "Yearly"){
			 addamountcumtaxamount = ((parseInt(noDays/365)+1) * addamountcumrent*0.1);
		 }
	 }
	  var round =Math.round(addamountcumtaxamount);
	 var cumulativetax=	round-carparkinglastrecord[0].cumulativeTaxAmount;
	var addCarParkingTollRecord={
			"status" : 1,
			"id" : null,
			"date" : null,
			"modeOfPayment" : null,
			"bankName" : null,
			"bankCode" : null,
			"bankAc" : null,
			"transferReceiptNo" : null,
			"netAmount" : null,
			"taxAmount" : null,
			"cumulativeNetAmount" :carparkinglastrecord[0].cumulativeNetAmount,
			"cumulativeTaxAmount" :carparkinglastrecord[0].cumulativeTaxAmount,
			"forecastNetAmt" : addamountcumrent,
			"forecastTaxAmt" : round,
			"cumulativeNetRevenue" :addamountcumrent-carparkinglastrecord[0].cumulativeNetAmount,
			"cumulativeTax" :cumulativetax,
			'fixedAssetid' : $scope.fixedAssetid,
			"plantCommissioningDate":$scope.plantCommissioningDate,
			'docKey':null
			};
}

				if (actionType === 'Add') {
					$scope.carParkingTollRecord.push(addCarParkingTollRecord);

				} else {
					$scope.carParkingTollRecord = angular.copy(editCarParkingTollRecordData)
				}

				$scope.modeOfPayments = [ {
					id : 1,
					name : "Bank Cheque"
				}, {
					id : 2,
					name : "Cash Deposit"
				}, {
					id : 3,
					name : "Credit Card"
				},{
					id : 4,
					name : "Debit Card"
				},{
					id : 5,
					name : "Direct Debit from Bank Account"
				} ];

				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.carParkingTollDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";
						$scope.carParkingTollDoc.isValid=false;
					}else if( (file.size > 1000000)){
						$scope.carParkingTollDoc.errorMessage="Supported Max. File size 1MB";
						$scope.carParkingTollDoc.isValid=false;
					}else{
						$scope.carParkingTollDoc.isValid=true;
					}

				}

				//	if (actionType === 'Edit') {
				if(carparkinglastrecord[0].cumulativeNetAmount != undefined){

						$scope.netAmount=function(carparkingtollTO){
							//carparkingtollTO.netAmount.target.select();
									carparkingtollTO.cumulativeNetAmount= parseInt(carparkingtollTO.cumulativeNetAmount) + parseInt(carparkingtollTO.netAmount);
							}

							$scope.TaxAmount=function(carparkingtollTO){
								//carparkingtollTO.target.select();
								carparkingtollTO.cumulativeTaxAmount= parseInt(carparkingtollTO.cumulativeTaxAmount) + parseInt(carparkingtollTO.taxAmount);

							}
					}else{

							var total = 0;
							var currentDate = new Date();
							 var TimeDiff;
							 var noDays;
							 var leaseStart = new Date(rentalValue[0].effectiveDate);

							 TimeDiff = Math.abs(currentDate - leaseStart);
								noDays = Math.ceil(TimeDiff / (1000 * 3600 * 24));



							if(rentalValue[0].revenueCollectionCycle!=undefined && rentalValue[0].estimatedRentPerCycle!=undefined){
						var addamountcumrent=0;
								 var content = rentalValue[0].revenueCollectionCycle;
							//	 alert(noDays);
								 if(content == "Daily"){
										addamountcumrent= (noDays * rentalValue[0].estimatedRentPerCycle);
								 }else if(content == "Weekly"){
											 addamountcumrent= ((parseInt(noDays/7)+1)*rentalValue[0].estimatedRentPerCycle);
								 }else if(content == "Fortnightly"){
									 addamountcumrent = ((parseInt(noDays/15)+1) * rentalValue[0].estimatedRentPerCycle);
								 }else if(content == "Monthly"){
									 addamountcumrent = ((parseInt(noDays/30)+1) * rentalValue[0].estimatedRentPerCycle);
								 }else if(content == "Quarterly"){
									 addamountcumrent = ((parseInt(noDays/90)+1) * rentalValue[0].estimatedRentPerCycle);
								 }else if(content == "Half Yearly"){
									 addamountcumrent = ((parseInt(noDays/182)+1) * rentalValue[0].estimatedRentPerCycle);
								 }else if(content == "Yearly"){
									 addamountcumrent = ((parseInt(noDays/365)+1) * rentalValue[0].estimatedRentPerCycle);
								 }

							 }
						//	 var total = 0;
							 //angular.forEach(rentalValue, function (value, key) {
							//	var total1 =0;
									//			total +=total1 + addamountcumrent;
									//		});
						//	 var num = addamountcumrent*0.1;
						//	 var n = num.toFixed(2);
						//		 var round =Math.round(n);

						 	if(rentalValue[0].revenueCollectionCycle!=undefined ){
									 var content = rentalValue[0].revenueCollectionCycle;
						var addamountcumtaxamount=0;
							 if(content == "Daily"){
									 addamountcumtaxamount = (noDays * addamountcumrent*0.1);
								 }else if(content == "Weekly"){
									 addamountcumtaxamount = ((parseInt(noDays/7)+1) * addamountcumrent*0.1);
								 }else if(content == "Fortnightly"){
									 addamountcumtaxamount = ((parseInt(noDays/15)+1) *addamountcumrent*0.1);
								 }else if(content == "Monthly"){
									 addamountcumtaxamount = ((parseInt(noDays/30)+1) * addamountcumrent*0.1);
								 }else if(content == "Quarterly"){
									 addamountcumtaxamount = ((parseInt(noDays/90)+1) * addamountcumrent*0.1);
								 }else if(content == "Half Yearly"){
									 addamountcumtaxamount = ((parseInt(noDays/182)+1) * addamountcumrent*0.1);
								 }else if(content == "Yearly"){
									 addamountcumtaxamount = ((parseInt(noDays/365)+1) * addamountcumrent*0.1);
								 }
							 }
							 var round =Math.round(addamountcumtaxamount);
							// var cumulativetax=	round-carparkinglastrecord[0].cumulativeTaxAmount;

						//if (actionType === 'Add') {
							$scope.netAmount=function(carparkingtollTO){
								//carparkingtollTO.target.select();
									carparkingtollTO.cumulativeNetAmount = parseInt(carparkingtollTO.netAmount);
									carparkingtollTO.cumulativeNetRevenue=addamountcumrent-carparkingtollTO.cumulativeNetAmount
							}

							$scope.TaxAmount=function(carparkingtollTO){
								//carparkingtollTO.target.select();

									carparkingtollTO.cumulativeTaxAmount = parseInt(carparkingtollTO.taxAmount);
									carparkingtollTO.cumulativeTax=round-carparkingtollTO.cumulativeTaxAmount
										}

						}

						$scope.Netamount = function (carparkingtollTO) {

							if(carparkinglastrecord[0].netAmount!=undefined){
								carparkingtollTO.cumulativeNetAmount = parseInt(carparkingtollTO.netAmount) + parseInt(carparkinglastrecord[0].netAmount);
								carparkingtollTO.cumulativeNetRevenue =parseInt(carparkingtollTO.forecastNetAmt)-parseInt(carparkingtollTO.cumulativeNetAmount)
							}else{
								carparkingtollTO.cumulativeNetAmount = parseInt(carparkingtollTO.netAmount);
								carparkingtollTO.cumulativeNetRevenue =parseInt(carparkingtollTO.forecastNetAmt)-parseInt(carparkingtollTO.cumulativeNetAmount);

							}
						},

						$scope.Taxamount= function (carparkingtollTO) {
							if(carparkinglastrecord[0].taxAmount!=undefined){
								//	alert('netamout if')
									carparkingtollTO.cumulativeTaxAmount = parseInt(carparkingtollTO.taxAmount) + parseInt(carparkinglastrecord[0].taxAmount);
									carparkingtollTO.cumulativeTax=carparkingtollTO.forecastTaxAmt-carparkingtollTO.cumulativeTaxAmount;
							}else{
								carparkingtollTO.cumulativeTaxAmount = carparkingtollTO.taxAmount;
								carparkingtollTO.cumulativeTax=carparkingtollTO.forecastTaxAmt-carparkingtollTO.cumulativeTaxAmount;
							}
						}

				$scope.saveCarParkingToll = function() {
					//console.log("saveCarParkingToll function type:"+collection_type);
					
					var saveCarParkingTollReq = {
							"carParkingTollCollectionDtlTO" : $scope.carParkingTollRecord,
							"folderCategory" : "Car Parking Actual Revenue",
							"fixedAssetId" : $scope.fixedAssetid,
							"clientId" : $rootScope.account.clientId,
							"userId" : $rootScope.account.userId
						};
					//	console.log('saveCarParkingTollReq '+ saveCarParkingTollReq.carParkingTollCollectionDtlTO[0].date+"DF "+saveCarParkingTollReq.carParkingTollCollectionDtlTO[0].bankName +" DSF"+saveCarParkingTollReq.carParkingTollCollectionDtlTO[0].bankCode );
						// if(saveCarParkingTollReq.carParkingTollCollectionDtlTO[0].date!= null && saveCarParkingTollReq.carParkingTollCollectionDtlTO[0].bankName !=  null && saveCarParkingTollReq.carParkingTollCollectionDtlTO[0].bankCode !=null ){

							AssetRegisterService.saveCarParkingToll($scope.fileInfo, saveCarParkingTollReq).then(function(data) {
									var succMsg = GenericAlertService.alertMessageModal('Car Parking Toll Record Detail(s) Saved Successfully ','Info');
									succMsg.then(function() {
										var returnPopObj = {
											"carParkingTollCollectionDtlTO" : data.carparkingtollTOTOs,
										};
										ngDialog.close();
										deferred.resolve(returnPopObj);
									});
								}, function(error) {
									if (error.status != null && error.status != undefined) {
										GenericAlertService.alertMessageModal('Car Parking Toll Record Detail(s) failed to save ', "Error");
									}
								});
						// }
						// else{
						// 		GenericAlertService.alertMessage("Please enter the mandatory fields", "Error");
						// }
					 //ngDialog.close();

				}
			} ]
		});
		return deferred.promise;
	}

	service.assetDetailsPopUpOnLoad = function(itemData) {
		var deferred = $q.defer();
		service.assetDetailsPopUp(itemData).then(function(data) {

			var returnPopObj = {
				"projGenCurrencyLabelKeyTO" : data.projGenCurrencyLabelKeyTO,
				"carparkingtollTOTOs" : data.carparkingtollTOTOs,
			};
			deferred.resolve(returnPopObj);

		});
		return deferred.promise;
	}
	return service;
}]);
