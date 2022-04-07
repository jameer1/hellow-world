'use strict';
app.factory('PurchaseRecordFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "TaxCountryFactory", "AssetRegisterService", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, TaxCountryFactory, AssetRegisterService,assetidservice) {
	var purchaseRecordPopUp;
	var service = {};
	service.purchaseRecordPopUp = function(actionType, editPurchaseRecordData) {
		var deferred = $q.defer();
		purchaseRecordPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/purchaserecords/purchaserecordspopup.html',
			closeByDocument : false,
			showClose : false,
			className  : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				var selectedPurchaseRecordData = [];
				var selectedFile=null;
				$scope.purchaseDoc={};
				$scope.purchaseRecord = [];
				var addPurchaseRecord={
					"status" : 1,
					"id" : null,
					"dateOfPurchase" : null,
					"purchaseType" : null,
					"vendorName" : null,
					"vendorAddress" : null,
					"landSurveyLotDetails" : null,
					"lotIdentificationDetails" : null,
					"landSizeAndDimesions" : null,
					"structureDetails" : null,
					"plantAndEquipmentDetails" : null,
					"landRegisterOfficeDetails" : null,
					"landValuation" : null,
					"structureValuation" : null,
					"plantAndEquipmentValuation" : null,
					"totalValuation" : null,
					"totalPurchaseAmount" : null,
					"initialMarginAmountPaid": null,
					"taxAmountWithHeld": null,
					"loanAmount": null,
					"loanAvailedFrom": null,
					"loanPeriod": null,
					"fixedRateOfInterest": null,
					"annualRateOfInterest": null,
					"buyerSolicitor":null,
					"vendorSolicitor":null,
					'fixedAssetid' : $scope.fixedAssetid,
					"plantCommissioningDate":$scope.plantCommissioningDate,
					'docKey':null
					};

				if (actionType === 'Add') {
					$scope.purchaseRecord.push(addPurchaseRecord);
				} else {
					$scope.purchaseRecord = angular.copy(editPurchaseRecordData)
				}


				$scope.purchaseTypes = [ {
					id : 1,
					name : "Lease Purchase"
				}, {
					id : 2,
					name : "Hire Purchase"
				},{
					id : 3,
					name : "Out Right Purchase"
				} ];

				$scope.interestTypes = [ {
					id : 1,
					name : "Fixed"
				}, {
					id : 2,
					name : "Variable"
				}];

				$scope.addRows = function() {
					$scope.purchaseRecord.push({
						"status" : 1,
						"id" : null,
						"dateOfPurchase" : null,
						"purchaseType" : null,
						"vendorName" : null,
						"vendorAddress" : null,
						"landSurveyLotDetails" : null,
						"lotIdentificationDetails" : null,
						"landSizeAndDimesions" : null,
						"structureDetails" : null,
						"plantAndEquipmentDetails" : null,
						"landRegisterOfficeDetails" : null,
						"landValuation" : null,
						"structureValuation" : null,
						"plantAndEquipmentValuation" : null,
						"totalValuation" : null,
						"totalPurchaseAmount" : null,
						"initialMarginAmountPaid": null,
						"taxAmountWithHeld": null,
						"loanAmount": null,
						"loanAvailedFrom": null,
						"loanPeriod": null,
						"fixedRateOfInterest": null,
						"annualRateOfInterest": null,
						"buyerSolicitor":null,
						"vendorSolicitor":null,
						'fixedAssetid' : $scope.fixedAssetid,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						'docKey':null
					});
				},
				$scope.calcTotalSaleAmount = function(asset) {
					asset.totalValuation = parseFloat(asset.landValuation) + parseFloat(asset.structureValuation) + parseFloat(asset.plantAndEquipmentValuation);

				},
				$scope.assetPurchaseRecordPopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedPurchaseRecordData.push(asset);
					} else {
						selectedPurchaseRecordData.pop(asset);
					}
				},
				$scope.deleteRows = function() {
					if (selectedPurchaseRecordData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedPurchaseRecordData.length < $scope.purchaseRecord.length) {
						angular.forEach(selectedPurchaseRecordData, function(value, key) {
							$scope.purchaseRecord.splice($scope.purchaseRecord.indexOf(value), 1);
						});
						selectedPurchaseRecordData = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedPurchaseRecordData.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedPurchaseRecordData.length == 1) {
						$scope.purchaseRecord[0]={
							"status" : 1,
							"id" : null,
							"dateOfPurchase" : null,
							"purchaseType" : null,
							"vendorName" : null,
							"vendorAddress" : null,
							"landSurveyLotDetails" : null,
							"lotIdentificationDetails" : null,
							"landSizeAndDimesions" : null,
							"structureDetails" : null,
							"plantAndEquipmentDetails" : null,
							"landRegisterOfficeDetails" : null,
							"landValuation" : null,
							"structureValuation" : null,
							"plantAndEquipmentValuation" : null,
							"totalValuation" : null,
							"totalPurchaseAmount" : null,
							"initialMarginAmountPaid": null,
							"taxAmountWithHeld": null,
							"loanAmount": null,
							"loanAvailedFrom": null,
							"loanPeriod": null,
							"fixedRateOfInterest": null,
							"annualRateOfInterest": null,
							"buyerSolicitor":null,
							"vendorSolicitor":null,
							'fixedAssetid' : $scope.fixedAssetid,
							"plantCommissioningDate":$scope.plantCommissioningDate,
							'docKey':null
						};
						selectedPurchaseRecordData = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},

				$scope.calcValuation = function(valuationObj) {
					valuationObj.totalValuation = parseFloat(valuationObj.landValuation) + parseFloat(valuationObj.structureValuation) + parseFloat(valuationObj.plantValuation);
						return valuationObj.totalValuation;
				}
				$scope.upload = function (fileObject, addPurchaseRecordData) {

					if (fileObject) {
						// Max file size should be 5 MB
						if(fileObject.size > 5 * 1024 * 1024 ){
							GenericAlertService.alertMessage('File size should be less than 5MB', 'Warning');
							return;
						}
						addPurchaseRecordData.prachaseRecordDetailsFileName = fileObject.name;
						addPurchaseRecordData.prachaseRecordDetailsFileType = fileObject.type;
						addPurchaseRecordData.prachaseRecordDetailsFileSize = fileObject.size;
					}else{
						addPurchaseRecordData.prachaseRecordDetailsFileName = null;
						addPurchaseRecordData.prachaseRecordDetailsFileType = null;
						addPurchaseRecordData.prachaseRecordDetailsFileSize = null;
					}
					selectedFile = fileObject;
				},

				 $scope.getDocumentDetails = function(item) {
					selectedDocumentsList = [];
					var req = {
						"status" : 1,
						"id" : item.id
					};
					/* DocumentService.getProjDocFilesByFolder(req).then(function(data) {
						$scope.refdocuments = data.fixedAssetAqulisitionRecordsDtlTO;
						$scope.currentlyOpenedDocFolder = item;

					}) */

					}, $scope.rowselect = function (reference) {
						var itemIndex = selectedDocumentsList.indexOf(reference);
						if (reference.select && itemIndex == -1) {
							selectedDocumentsList.push(reference);
						} else {
							(itemIndex != -1) && selectedDocumentsList.splice(itemIndex, 1);
						}
					},

				$scope.prachaseRecordDetails = function(prachaseRecordDetails) {
					if (prachaseRecordDetails.select) {
						prachaseRecordDetails.push(prachaseRecordDetails);
					} else {
						prachaseRecordDetails.pop(prachaseRecordDetails);
					}
				},

				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.purchaseDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";
						$scope.purchaseDoc.isValid=false;
					}else if( (file.size > 1000000)){
						$scope.purchaseDoc.errorMessage="Supported Max. File size 1MB";
						$scope.purchaseDoc.isValid=false;
					}else{
						$scope.purchaseDoc.isValid=true;
					}

				}


				$scope.save = function() {
					var fixedAssetPurchaseAcqulisitionSaveReqStr = {
						"fixedAssetAqulisitionRecordsDtlTO" : $scope.purchaseRecord,
						"folderCategory" : "Purchase/Acquisition",
						"userId" : $rootScope.account.userId
					};
					console.log(fixedAssetPurchaseAcqulisitionSaveReqStr);
					console.log($scope.fileInfo);
					if(fixedAssetPurchaseAcqulisitionSaveReqStr.fixedAssetAqulisitionRecordsDtlTO[0].dateOfPurchase != null  && fixedAssetPurchaseAcqulisitionSaveReqStr.fixedAssetAqulisitionRecordsDtlTO[0].vendorName != null && fixedAssetPurchaseAcqulisitionSaveReqStr.fixedAssetAqulisitionRecordsDtlTO[0].vendorAddress != null){
						AssetRegisterService.savePurchaseRecord($scope.fileInfo, fixedAssetPurchaseAcqulisitionSaveReqStr).then(function(data) {
								var succMsg = GenericAlertService.alertMessageModal('Asset Purchase Record Detail(s) Saved successfully ','Info');
								succMsg.then(function() {
									var returnPopObj = {
										"fixedAssetAqulisitionRecordsDtlTO" : data.fixedAssetAqulisitionRecordsDtlTO
									};
									ngDialog.close();
									deferred.resolve(returnPopObj);
								});
							}, function(error) {
								if (error.status != null && error.status != undefined) {
									GenericAlertService.alertMessageModal('Asset Purchase Record Detail(s) is/are failed to Save/Update ', "Error");
								}
							});
					}else{
						GenericAlertService.alertMessage("Please enter the mandatory fields", "Error");
					//	ngDialog.close();
					}
				}
			} ]
		});
		return deferred.promise;
	},
	service.purchaseRecordPopUpOnLoad = function(actionType, editPurchaseRecordData) {
		var deferred = $q.defer();
		var purchaseRecordPopUp = service.purchaseRecordPopUp(actionType, editPurchaseRecordData);
		purchaseRecordPopUp.then(function(data) {

			var returnPopObj = {
				"fixedAssetAqulisitionRecordsDtlTO" : data.fixedAssetAqulisitionRecordsDtlTO,
			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);
