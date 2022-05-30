'use strict';
app.factory('ShortTermCasualRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService,AssetRegisterService,generalservice,assetidservice) {
	var shorttermPopUp;
	var service = {};
	service.shorttermPopUp = function(actionType, editShortTermRecordData,leaseActualfinishdate) {
		var deferred = $q.defer();
		shorttermPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/shorttermcasualoccupancyrecords/shorttermcasualrecordspopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				
				$scope.action = actionType;
				var advancePaidPopup;
				var addAdvancePaidService = {};				
				var subSequentRentalPopup;				
				var addSubSequentRentalService = {};
				$scope.advancePaid =[];
				$scope.subSequentRentalRecepits =[];				
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.currency =assetidservice.currency;
				$scope.addShortTermRecordData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.shortTermRecord = [];				
				$scope.shortTermRecDoc={};
				$scope.shortTermRecDoc.isValid=true;
				$scope.currentStatusTypes = generalservice.shortTermStatus;
				$scope.subSequentRentalRecepitsData =[];
				$scope.selecttype;
				
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
				
				var addShorTermRecord = {
						"id" : null,
						"fromDate":null,
						"toDate":null,	
						"startDate":null,
						"finishDate":null,	
						"tenantName":null,
						"tenantRegisteredAddress":null,
						"emailAddrees":null,
						"tenantPhoneNumber":null,
						"checkIn":null,
						"checkOut":null,
						"noOfDays":null,
						"dailyNetRent":null,	
						"tax":null,
						"rentAmountReceivable":null,	
						"taxAmount":null,
						"grossAmount":null,	
						"refundofRemainingAdvanceamount":null,
						"netTaxAmountReceived":null,
						"netAmountOfRentRecived":null,
						"currentStatus"	:null,
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"plantCommissioningDate":plantcommissioningdate,
						'docKey':null,
						'advancePaid':null,
						'subsequentRentalRecepits': null,
						'advancePaidList':[],
						'subSequentRentalRecepitsList': []			
					};
				
				if (actionType === 'Add') {
					$scope.shortTermRecord.push(addShorTermRecord);
				} else {
					$scope.shortTermRecord = angular.copy(editShortTermRecordData)
				}
				
				$scope.calNoOfDays = function(shortTermlease){
				   var checkOutDate = new Date(shortTermlease.checkOut);
				   var checkInDate = new Date(shortTermlease.checkIn); 
				   var compTimeDiff = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
				   var noOfDays = Math.ceil(compTimeDiff / (1000 * 3600 * 24));
				   shortTermlease.noOfDays = noOfDays+1;
				}
				$scope.calTaxamount = function(shortterm){
					
					shortterm.taxAmount=(shortterm.dailyNetRent*shortterm.tax/100);
				}				
				$scope.calRentamount = function(shortterm){
					
					shortterm.rentAmountReceivable=shortterm.dailyNetRent-(shortterm.dailyNetRent*shortterm.tax/100);
				}
				
				$scope.calNetAmountRent = function(shortterm){
					   shortterm.netAmountOfRentRecived = parseInt(shortterm.advancePaid)+parseInt(shortterm.subsequentRentalRecepits)-parseInt(shortterm.netTaxAmountReceived)-parseInt(shortterm.refundofRemainingAdvanceamount);
					}
					
				$scope.showAdvancePaidPopup = function(shortTermlease){
					shortTermlease.advancePaid = 0;
					var advancePaidPopup = addAdvancePaidService.addAdvancePaid(actionType,editShortTermRecordData);
					advancePaidPopup.then(function(data) {			
						shortTermlease.advancePaidList = data;
						angular.forEach(shortTermlease.advancePaidList, function(value, key) {
							shortTermlease.advancePaid += parseInt(value.amountPaid);
						});				
				   });	
				
				}
				
				
				$scope.showSubSequentRentalPopup = function(shortTermlease){	
												
					angular.forEach($scope.subSequentRentalRecepitsData, function(value, key) {
						$scope.selecttype=value.selected;
						});
					
					
					if (actionType === 'Add') {			
						shortTermlease.subsequentRentalRecepits = 0;
						var subSequentRentalPopup = addSubSequentRentalService.addSubSequentRental(actionType,editShortTermRecordData);
						subSequentRentalPopup.then(function(data) {			
							shortTermlease.subSequentRentalRecepitsList = data;
							angular.forEach(shortTermlease.subSequentRentalRecepitsList, function(value, key) {
								if(value.selected==true){
								shortTermlease.subsequentRentalRecepits += parseInt(value.amountPaid);
								}
								
							});
						});	
				    }else{	    	
				    		var subSequentRentalReceiptsGetReq = {				    			
				    				"id" : editShortTermRecordData[0].id
				    		}				    		
				    		
				    		AssetRegisterService.getSubSequentRentalReceipts(subSequentRentalReceiptsGetReq).then(function(data) {				    				
				    				shortTermlease.subsequentRentalRecepits = 0;
									var subSequentRentalPopup = addSubSequentRentalService.addSubSequentRental(actionType,data.subSequentRentalRecepitsDtlTO);
									subSequentRentalPopup.then(function(data) {			
										shortTermlease.subSequentRentalRecepitsList = data;
										angular.forEach(shortTermlease.subSequentRentalRecepitsList, function(value, key) {
											if(value.selected==true){
											shortTermlease.subsequentRentalRecepits += parseInt(value.amountPaid);
											}
											$scope.selecttype==value.selected;
										});
									});			
				    		}, function(error) {
				    			GenericAlertService.alertMessage("Error occured while getting Sub Sequent Rental Receipts Details", "Error");
				    		});				    			
					}
				}
					
				
				addAdvancePaidService.addAdvancePaid = function(actionType,editShortTermRecordData) {
				var deferred = $q.defer();
				advancePaidPopup = ngDialog.open({
					template : 'views/projresources/projassetreg/shorttermcasualoccupancyrecords/advancePaidpopup.html',
					className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
					scope : $scope,
					closeByDocument : false,
					showClose : false,
					controller : [ '$scope', function($scope) {	
						
						$scope.action = actionType;	
						var editAdvancePaidRecord = [];
						$scope.advancePaidData =[];
						
						var addAdvancePaid = {
						   'id':null,
					       'givenDate':null,
					       'currency':$scope.currency,
					       'modeOfPayment':null,
					       'amountPaid':null,
						   'receiptNumber':null,
						   'note':null,
						   "status" : "1"
						}
						
						if (actionType === 'Add') {							
							$scope.advancePaidData.push(addAdvancePaid);
						} else {
							$scope.advancePaidData = [angular.copy(editShortTermRecordData)];
						}
						
					    $scope.saveAdvancePaidRecords = function(){						
							$scope.closeThisDialog();
							deferred.resolve($scope.advancePaidData);							
					    }						
					} ]
				});
				return deferred.promise;
			}
			
				
				addSubSequentRentalService.addSubSequentRental = function(actionType,editShortTermRecordData) {
					var deferred = $q.defer();
					subSequentRentalPopup = ngDialog.open({
						template : 'views/projresources/projassetreg/shorttermcasualoccupancyrecords/subSequentRentalRecepitspopup.html',
						className : 'ngdialog-theme-plain ng-dialogueCustom0-5',
						scope : $scope,
						closeByDocument : false,
						showClose : false,
						controller : [ '$scope', function($scope) {							
							$scope.action = actionType;				
							
							var selectedSubSequentRentalRecepitsData = [];
							$scope.addamounpaid=0;
							
							
							angular.forEach($scope.subSequentRentalRecepitsData, function(value, key) {
								if(value.selected==true){
								$scope.addamounpaid += parseInt(value.amountPaid);
								}
								
								//console.log()
								
							});
						
							
							if($scope.subSequentRentalRecepitsData.length==0){
								$scope.selecttype=true;
								var subSequentRentalRecepits = {
										   'id':null,
									       'givenDate':null,
									       'currency':$scope.currency,
									       'modeOfPayment':null,
									       'amountPaid':null,
									       'note':null,
										   "status" : "1"
										}
								
							}
							
									
							console.log('subSequentRentalRecepits'+JSON.stringify(subSequentRentalRecepits))
													console.log(' $scope.selecttype '+$scope.selecttype)
							console.log(' $scope.subSequentRentalRecepitsData '+JSON.stringify($scope.subSequentRentalRecepitsData))
							if (actionType === 'Add') {				
								if($scope.selecttype!=undefined){
									if(subSequentRentalRecepits!=undefined){
									
								$scope.subSequentRentalRecepitsData.push(subSequentRentalRecepits);
									}
								}else{
									$scope.subSequentRentalRecepitsData = angular.copy($scope.subSequentRentalRecepitsData);	
								}
								console.log("$scope.subSequentRentalRecepitsData"+JSON.stringify($scope.subSequentRentalRecepitsData))
							} else {
								$scope.subSequentRentalRecepitsData = angular.copy(editShortTermRecordData);								
							}
							
							
							
							$scope.addRows = function() {					
								$scope.subSequentRentalRecepitsData.push({
								'id':null,
						        'givenDate':null,
						        'currency':$scope.currency,
						        'modeOfPayment':null,
						        'amountPaid':null,
						        'note':null,
							    "status" : "1"								
								});
							},
							
							$scope.subSequentRentalRecepitsPopUpRowSelect = function(subSequentRentalRecepits) {
								$scope.addamounpaid=0;
								if (subSequentRentalRecepits.selected) {
									selectedSubSequentRentalRecepitsData.push(subSequentRentalRecepits);
								} else {
									selectedSubSequentRentalRecepitsData.pop(subSequentRentalRecepits);
								}
								console.log('$scope.subSequentRentalRecepitsData before '+JSON.stringify($scope.subSequentRentalRecepitsData));
								angular.forEach($scope.subSequentRentalRecepitsData, function(value, key) {
									if(value.selected==true){
									
									$scope.addamounpaid += parseInt(value.amountPaid);
									}
								});
								
							},
							
							$scope.deleteRows = function() {
								if (selectedSubSequentRentalRecepitsData.length == 0) {
									GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
									return;
								} else if (selectedSubSequentRentalRecepitsData.length < $scope.subSequentRentalRecepitsData.length) {
									angular.forEach(selectedSubSequentRentalRecepitsData, function(value, key) {
										$scope.subSequentRentalRecepitsData.splice($scope.subSequentRentalRecepitsData.indexOf(value), 1);
									});
									selectedSubSequentRentalRecepitsData = [];
									GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
								} else if (selectedSaleRecordData.length > 1) {
									GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
								} else if (selectedSubSequentRentalRecepitsData.length == 1) {
									$scope.subSequentRentalRecepitsData[0] = {
											'id':null,
									        'givenDate':null,
									        'currency':$scope.currency,
									        'modeOfPayment':null,
									        'amountPaid':null,
									        'note':null,
										    "status" : "1"	

									};
									selectedSubSequentRentalRecepitsData = [];
									GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
								}

							}
							
							$scope.saveSubSequentRentalRecepitsRecords = function(){	
								
								if (selectedSubSequentRentalRecepitsData.length == 0) {
									GenericAlertService.alertMessage('Please select atleast one row to Save', "Warning");
									return;
								} 								
								$scope.closeThisDialog();
								
								console.log('$scope.subSequentRentalRecepitsData before '+JSON.stringify($scope.subSequentRentalRecepitsData));
								
								deferred.resolve($scope.subSequentRentalRecepitsData);							
							}
							
							
						} ]
					});
					return deferred.promise;
				}	
			
				
				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.shortTermRecDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";	
						$scope.shortTermRecDoc.isValid=false;
					}else if( (file.size > 5000000)){
						$scope.shortTermRecDoc.errorMessage="Supported Max. File size 5MB";	
						$scope.shortTermRecDoc.isValid=false;
					}else{
						$scope.shortTermRecDoc.isValid=true;
					}
		
				},
				
				$scope.saveShortTermRecords = function() {
					
					/*if (!$scope.shortTermRecDoc.isValid) {
						GenericAlertService.alertMessage("Please upload valid file !!", 'Warning');
						return ;
					}	*/				
					var saveShortTermReq = {
							"stcorrReturnsDtlTOs" : $scope.shortTermRecord,
							"folderCategory" : "Short Term/Casual Occupancy Records and Rental Returns",
							"userId" : $rootScope.account.userId
					}

					AssetRegisterService.saveShortTerm($scope.fileInfo,saveShortTermReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Short Term  Detail(s) Saved Successfully','Info');
						succMsg.then(function() {
						var returnPopObj = {
							"stcorrReturnsDtlTOs" : data.stcorrReturnsDtlTOs
						};
						deferred.resolve(returnPopObj);
					});
					}, function(error) {
						if (error.status != null && error.status != undefined) {
							GenericAlertService.alertMessageModal('Asset Short Term Detail(s) is/are Failed to Save/Update ', "Error");
						} 
							});
							    ngDialog.close();
								}
							} ]
						});
						return deferred.promise;
					}
	
					service.shorttermPopUpOnLoad = function(actionType, editShortTermRecordData,leaseActualfinishdate) {
						var deferred = $q.defer();
						var saleRecordPopUp = service.shorttermPopUp(actionType, editShortTermRecordData,leaseActualfinishdate);
						saleRecordPopUp.then(function(data) {				
							var returnPopObj = {
								"stcorrReturnsDtlTOs" : data.stcorrReturnsDtlTOs				
							}
				
							deferred.resolve(returnPopObj);
						});
				
						return deferred.promise;
					}
					return service;
				}]);
								
