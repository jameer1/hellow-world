'use strict';
app.factory('SaleRecordFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "TaxCountryFactory", "AssetRegisterService", "generalservice", "assetidservice", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, TaxCountryFactory, AssetRegisterService,generalservice,assetidservice) {
	var saleRecordPopUp;
	var service = {};
	service.saleRecordPopUp = function(actionType, editSaleRecordData) {
		var deferred = $q.defer();
		saleRecordPopUp = ngDialog.open({
			template : 'views/projresources/projassetreg/salerecords/salerecordspopup.html',
			closeByDocument : false,
			showClose : false,
			className :'ngdialog ngdialog-theme-plain ng-dialogueCustom1-t',
			controller : [ '$scope', function($scope) {
				$scope.saleRecDoc={};
				$scope.fixedAssetid = assetidservice.fixedAssetId;
				$scope.plantCommissioningDate = assetidservice.fixedAssetDateCommissioned;
				$scope.addSaleRecordData = [];
				$scope.uiDeleteRows = [];
				$scope.action = actionType;
				$scope.saleRecord = [];
				var selectedSaleRecordData = [];
				$scope.saleRecordDoc={};
				$scope.saleRecordDoc.isValid=true;
				//$scope.saleRecDoc.isValid=true;
				$scope.saleTypesList = generalservice.saleTypes;
				var addSaleRecord = {
						"id" : null,
						"dateOfSale":null,
						"saleType":null,
						"buyerName":null,
						"buyerAddress":null,
						"landRegisterOfficeDetails":null,
						"landValudation":null,
						"structureValuation":null,
						"plantEquipmentValutaion":null,
						"totalValuation":null,
						"totalSalesAmount":null,
						"buyerSolicitor":null,
						"vendorSolicitor":null,
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						'docKey':null
					};

				if (actionType === 'Add') {
					$scope.saleRecord.push(addSaleRecord);
				} else {
					$scope.saleRecord = angular.copy(editSaleRecordData)
				}

				$scope.addRows = function() {
					$scope.saleRecord.push({
						"id" : null,
						"dateOfSale":null,
						"saleType":null,
						"buyerName":null,
						"buyerAddress":null,
						"landRegisterOfficeDetails":null,
						"landValudation":null,
						"structureValuation":null,
						"plantEquipmentValutaion":null,
						"totalValuation":null,
						"totalSalesAmount":null,
						"buyerSolicitor":null,
						"vendorSolicitor":null,
						"status" : "1",
						"fixedAssetid" : $scope.fixedAssetid,
						"plantCommissioningDate":$scope.plantCommissioningDate,
						'docKey':null

					});
				},

				$scope.assetSaleRecordPopUpRowSelect = function(asset) {
					if (asset.selected) {
						selectedSaleRecordData.push(asset);
					} else {
						selectedSaleRecordData.pop(asset);
					}
				},

				$scope.deleteRows = function() {
					if (selectedSaleRecordData.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						return;
					} else if (selectedSaleRecordData.length < $scope.saleRecord.length) {
						angular.forEach(selectedSaleRecordData, function(value, key) {
							$scope.saleRecord.splice($scope.saleRecord.indexOf(value), 1);
						});
						selectedSaleRecordData = [];
						GenericAlertService.alertMessage("Row(s) is/are deleted successfully", "Info");
					} else if (selectedSaleRecordData.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedSaleRecordData.length == 1) {
						$scope.saleRecord[0] = {
							"status" : "1",
							"selected" : false,
							"dateOfSale":null,
							"saleType":null,
							"buyerName":null,
							"buyerAddress":null,
							"landRegisterOfficeDetails":null,
							"landValudation":null,
							"structureValuation":null,
							"plantEquipmentValutaion":null,
							"totalValuation":null,
							"totalSalesAmount":null,
							"buyerSolicitor":null,
							"vendorSolicitor":null,
							'fixedAssetid' : $scope.fixedAssetid,
							"plantCommissioningDate":$scope.plantCommissioningDate,
							'docKey':null

						};
						selectedSaleRecordData = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}

				},

				$scope.calcTotalSaleAmount = function(asset) {
					asset.totalValuation = parseFloat(asset.landValudation) + parseFloat(asset.structureValuation) + parseFloat(asset.plantEquipmentValutaion);

				},

				$scope.checkFile = function (file) {
					$scope.fileInfo = file;
					var allowedFiles = [".doc", ".docx", ".png",".jpg",".xls", ".xlsx"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.saleRecDoc.errorMessage="Supported formats PNG,JPEG,DOC,DOCX ";
						$scope.saleRecDoc.isValid=false;
					}else if( (file.size > 5000000)){
						$scope.saleRecDoc.errorMessage="Supported Max. File size 5MB";
						$scope.saleRecDoc.isValid=false;
					}else{
						$scope.saleRecDoc.isValid=true;
					}

				}


				$scope.save = function() {

				/*	if (!$scope.saleRecDoc.isValid) {
						GenericAlertService.alertMessage("Please upload valid file !!", 'Warning');
						return ;
					}*/

					var salesRecordsSaveReq = {
						"salesRecordsDtlTOs" : $scope.saleRecord,
						"folderCategory" : "Asset Sale Records"
					};
					
					AssetRegisterService.saveSalesRecord($scope.fileInfo,salesRecordsSaveReq).then(function(data) {
						var succMsg = GenericAlertService.alertMessageModal('Asset Sale Records Value Detail(s) saved successfully','Info');
						succMsg.then(function() {
						var returnPopObj = {
							"salesRecordsDtlTOs" : data.salesRecordsDtlTOs
						};
						deferred.resolve(returnPopObj);
					});
				}, function(error) {
					if (error.status != null && error.status != undefined) {
						GenericAlertService.alertMessageModal('Asset Sale Records Detail(s) Failed to Save ', "Error");
					}
				});
				    ngDialog.close();
			}
		} ]
	});
	return deferred.promise;
}

	service.saleRecordPopUpOnLoad = function(actionType, editSaleRecordData) {
		var deferred = $q.defer();
		var saleRecordPopUp = service.saleRecordPopUp(actionType, editSaleRecordData);
		saleRecordPopUp.then(function(data) {

			var returnPopObj = {
				"salesRecordsDtlTOs" : data.salesRecordsDtlTOs,

			}

			deferred.resolve(returnPopObj);
		});

		return deferred.promise;
	}
	return service;
}]);
