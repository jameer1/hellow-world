'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # ProjCostCode Service in the potApp.
 */
app.factory('AssetRegisterService', ["Restangular", "Principal", "$http", "appUrl", function (Restangular, Principal, $http, appUrl) {
	var FileSaver = require('file-saver');
	return {


		getfixedAssetRegistersOnLoad: function (req) {
			var asset = Restangular.one("register/fixedAssetsRegisterOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;

		},
		getAssetSubById: function (req) {
			var asset = Restangular.one("register/getAssetSubById").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;

		},

		saveSubAsset: function (req) {
			var saveSubAsset = Restangular.one("register/saveSubAsset").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return saveSubAsset;
		},

		getAssetMap: function (req) {
			var asset = Restangular.one("register/fixedAssetsRegisterOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
		},
		getAssetListMap: function (req) {
			var epsMap = Restangular.one("register/fixedAssetsRegisterOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsMap;
		},
		getAssetById: function (req) {
			var epsIdDetails = Restangular.one("register/fixedAssetsRegisterOnLoad").customPOST(req, '',
				{}, {
					ContentType: 'application/json'
				});
			return epsIdDetails;
		},
		saveSubAssets: function (req) {
			var epsSaveStatus = Restangular.one("register/saveSubAssets").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return epsSaveStatus;
		},

		getAssetOnly: function (req) {
			var getAsset = Restangular.one("register/getAssetOnly").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getAsset;
		},



		getCountries: function () {
			return $http.get("http://api.geonames.org/countryInfoJSON?username=amit11patel6");
		},
		getProvince: function (id) {
			return $http.get("http://api.geonames.org/childrenJSON?username=amit11patel6&geonameId=" + id);
		},

		getCountryDetailsById: function (req) {
			var getProvisionsReq = Restangular.one('common/getCountryDetailsById').customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getProvisionsReq;
		},

		saveFixedAssetRegisters: function (req) {
			var plant = Restangular.one("register/saveFixedAssetsRegisters").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		deleteAssetRegisters: function (req) {
			var plant = Restangular.one("register/fixedAssetRegistersDeactivate").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPurchaseRecordOnLoad: function (req) {
			var plant = Restangular.one("register/getPurachaseAcqulisition").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		carParkingTollCollectionsRecords: function (req) {
			var plant = Restangular.one("register/getCarParkingTollCollection").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		// New service to call lastrecord
		carParkingTollCollectionslastrecord: function (req) {
			var plant = Restangular.one("register/getCarParkingTollCollectionlastrecord").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},



		//================================================
		getPlantChargeOutRates: function (req) {
			var plant = Restangular.one("register/getPlantChargeOutRates").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantChargeOutOnLoad: function (req) {
			var plant = Restangular.one("register/getPlantChargeOutRates").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantChargeOutRates: function (req) {
			var savePlantDetails = Restangular.one("register/savePlantChargeOutRates").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return savePlantDetails;
		},
		getPlantLogRecords: function (req) {
			var plant = Restangular.one("register/getPlantLogRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantLogRecords: function (req) {
			var savePlantDetails = Restangular.one("register/savePlantLogRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return savePlantDetails;
		},
		deletePlantLogRecords: function (req) {
			var savePlantDetails = Restangular.one("register/plantLogRecordsDeactivate").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return savePlantDetails;
		},
		getPlantProcureDeliveryRecords: function (req) {
			var plant = Restangular.one("register/getPlantProcureDeliveryDtls").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantDeploymentRecordsOnLoad: function (req) {
			var plant = Restangular.one("register/getPlantDeploymentOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		plantDeploymentPopUpOnLoad: function () {
			var plant = Restangular.one("register/plantDeploymentPopUpOnLoad").customPOST('', '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},

		savePlantDeploymentRecords: function (req) {
			var plant = Restangular.one("register/savePlantDeployment").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantServiceHistoryRecordsOnLoad: function (req) {
			var plant = Restangular.one("register/plantServiceOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantServiceHistoryRecords: function (req) {
			var plant = Restangular.one("register/savePlantServiceHistory").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		deletePlantServiceHistoryRecords: function (req) {
			var plant = Restangular.one("register/savePlantServiceHistory").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getRepairRecordsOnLoad: function (req) {
			var plant = Restangular.one("register/getRepairsRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},

		getPeriodicalRecordsOnLoad: function (req) {
			var plant = Restangular.one("register/getPeriodicalScheduleMaintenance").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantRepairRecords: function (req) {
			var plant = Restangular.one("register/savePlantRepairs").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		deletePlantRepairRecords: function (req) {
			var plant = Restangular.one("register/savePlantRepairs").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantDeprisiationRecords: function (req) {
			var plant = Restangular.one("register/getPlantDeprisiationSalvage").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantDeprisiationRecords: function (req) {
			var plant = Restangular.one("register/savePlantDeprisiationSalvage").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		}, prPlantCurrentStatus: function (req) {
			var currentStatus = Restangular.one("register/prPlantCurrentStatus").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return currentStatus;
		},
		getPlantProjectPODetails: function (req) {
			var plantProjectPODetails = Restangular.one("procurement/getPOByProcureTypes").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantProjectPODetails;
		},
		userProjectLabelKeyTO: function () {
			var plant = Restangular.one("register/userProjectLabelKeyTO").customPOST('', '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		saveRegPlantProcureDelivery: function (req) {
			var plant = Restangular.one("register/saveRegPlantProcureDelivery").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantPOItemDetails: function (req) {
			var plantProjectPODetails = Restangular.one("procurement/getPlantPOItemDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantProjectPODetails;
		},
		getRegPlantProcureDeliveryDetails: function (req) {
			var plantProjectPODetails = Restangular.one("register/getRegPlantProcureDeliveryDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantProjectPODetails;
		},

		saveSalesRecord: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveSalesRecord',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("salesRecordsSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},
		savePurchaseRecord: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/savePurachase',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("fixedAssetPurchaseAcqulisitionSaveReqStr", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		saveRepairsRecord: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveRepairs',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("fixedAssetRepairsSaveReqStr", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		savePeriodicalsRecord: function (planFile,actualFile,data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/savePeriodicalScheduleMaintenance',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("planFile", data.planFile);
					formData.append("actualFile", data.actualFile);
					formData.append("periodicalScheduleMaintenanceSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, planFile:planFile,actualFile:actualFile }
			});
		},

		saveCarParkingToll: function (file, data) {
			console.log("saveCarParkingToll function from service");
			console.log(data);
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveCarParkingTollCollection',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("carParkingTollCollectionSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},
// Toll service
		saveToll: function (file, data) {
			console.log(data);
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveTollCollection',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("TollCollectionSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		TollCollectionsRecords: function (req) {
				var plant = Restangular.one("register/getTollCollection").customPOST(req, '', {}, {
					ContentType: 'application/json'
				});
				return plant;
			},
			// New service to call lastrecord
			TollCollectionslastrecord: function (req) {
				var plant = Restangular.one("register/getTollCollectionlastrecord").customPOST(req, '', {}, {
					ContentType: 'application/json'
				});
				return plant;
			},

		getPurachaseAcqulisition: function (req) {
			var getPurachaseAcqulisition = Restangular.one("register/getPurachaseAcqulisition").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPurachaseAcqulisition;
		},
		purachaseAcqulistitonDeactive: function (req) {
			var purachaseAcqulistitonDeactive = Restangular.one("register/purachaseAcqulistitonDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return purachaseAcqulistitonDeactive;
		},
		purachaseAcqulistitonDelete: function (req) {
			var purachaseAcqulistitonDeactive = Restangular.one("register/purachaseAcqulistitonDelete").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return purachaseAcqulistitonDeactive;
		},
		purachaseAcqulistitonDownloadFile: function (fileId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();

			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "document/purachaseAcqulistitonDownloadFile?fileId=" + fileId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		saveRentalValue: function (req) {
			var asset = Restangular.one("register/saveRentalValue").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;

		},
		getRentalValue: function (req) {
			var asset = Restangular.one("register/getRentalValue").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;

		},
		getLongtermleaselastrecord: function (req) {
			var asset = Restangular.one("register/getLongtermleaselastrecord").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;

		},

		getLongtermleaseActiveAllRecord: function (req) {
		var asset = Restangular.one("register/getLongtermleaseActiveAllRecord").customPOST(req, '', {}, {
			ContentType: 'application/json'
		});
		return asset;

	},

		deleteRentalRegisters: function (req) {
			var asset = Restangular.one("register/rentalValueDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},


		saveSalesRecord: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveSalesRecord',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("salesRecordsSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		downloadRecordSaleFile: function (saleRecordId,element) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			//var app_url = appUrl.appurl + "register/salesRecordDocDownloadFile?saleRecordId=" + saleRecordId;
			var app_url = appUrl.appurl + "register/salesRecordDocDownloadFile?saleRecordId=" + saleRecordId + "&pottoken=" + token;
			/*var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}*/
			
			/*$http({method: 'GET', url: app_url,responseType:"arraybuffer"}).then(function(result){
				console.log(result);
				var file = new Blob([result.data], {
					type:  'image/png'
				});
				//var blob = new Blob(["Hello, world!"], {type: "text/plain;charset=utf-8"});
				FileSaver.saveAs(file, "ss.png");
				console.log("end");
			});*/			
		},	

		deleteSaleRecords: function (req) {
			var asset = Restangular.one("register/salesRecordDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},

		deletecarparkingToll: function (req) {
			var asset = Restangular.one("register/carParkingTollCollectionDelete").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},

		deleteToll: function (req) {
			var asset = Restangular.one("register/TollCollectionDelete").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},
		deletePeriodicalRecords: function (req) {
			var asset = Restangular.one("register/periodicalScheduleMaintenanceDelete").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},
		getSalesRecord: function (req) {
			var getPurachaseAcqulisition = Restangular.one("register/getSalesRecord").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPurachaseAcqulisition;
		},

		saveLongTermLeaseActualRetruns: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveLongTermLeaseActualRetruns',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("longTermLeaseActualRetrunsSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});

		},

		downloadLongTermLeaseActualRetrunsFile: function (longTermLeaseActualId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/longTermLeaseActualRetrunsDocDownloadFile?longTermLeaseActualId=" + longTermLeaseActualId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		deleteLongTermLeaseRevenue: function (req) {
			var asset = Restangular.one("register/longTermLeaseActualRetrunsDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},


		getLongTermLeaseActualRetruns: function (req) {
			var getPurachaseAcqulisition = Restangular.one("register/getLongTermLeaseActualRetruns").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPurachaseAcqulisition;
		},

		getLongTermLeaseActualRetrunsLastRecord: function (req) {
			var getPurachaseAcqulisition = Restangular.one("register/getLongTermLeaseActualRetrunsLastRecord").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPurachaseAcqulisition;
		},


		downloadRecordPurchaseFile: function (purchaseRecordId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/purchaseRecordDocDownloadFile?purchaseRecordId=" + purchaseRecordId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		downloadRecordPlanPeriodicalFile: function (purchaseRecordId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/actualDocDownloadFile?periodicalRecordId=" + purchaseRecordId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		downloadRecordActualPeriodicalFile: function (periodicalRecordId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/planDocDownloadFile?periodicalRecordId=" + periodicalRecordId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		downloadRecordRepairFile: function (repairRecordId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/repairsRecordDocDownloadFile?repairRecordId=" + repairRecordId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		downloadCarParkingFile: function (fileId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/carParkingTollCollectionDocDownloadFile?fileId=" + fileId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		downloadTollFile: function (fileId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/TollCollectionDocDownloadFile?fileId=" + fileId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},


		deletePurchaseRecords: function (req) {
			var asset = Restangular.one("register/purchaseRecordDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},

		deleteRepairRecords: function (req) {
			var asset = Restangular.one("register/repairsRecordDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},
		saveRentalHistory: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/savelongTermLeasing',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("longTermLeasingSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		downloadRentalHistoryFile: function (rentalHistoryId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/longTermLeasingDocDownloadFile?rentalHistoryId=" + rentalHistoryId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		deleteRentalHistory: function (req) {
			var asset = Restangular.one("register/longTermLeasingDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},

		getRentalHistory: function (req) {
			var longTermLeasing = Restangular.one("register/getlongTermLeasing").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return longTermLeasing;
		},


		saveMortgageePayments: function (req) {
			var plant = Restangular.one("register/saveMortgageePayments").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getMortgageePayments: function (req) {
			var mortgageePayments = Restangular.one("register/getMortgageePayments").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return mortgageePayments;
		},
		mortgageePaymentsDeactive: function (req) {
			var plant = Restangular.one("register/mortgageePaymentsDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getRevenueSales: function (req) {
			var getPurachaseAcqulisition = Restangular.one("register/getRevenueSales").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return getPurachaseAcqulisition;
		},

		saveRevenueSales: function (req) {
			var plant = Restangular.one("register/saveRevenueSales").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		revenueSalesDeactive: function (req) {
			var plant = Restangular.one("register/revenueSalesDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},

		saveAssetLifeStatus: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveAssetLifeStatus',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("assetsLifeStatusSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},
		getAssetLifeStatus: function (req) {
			var assetLifeStatus = Restangular.one("register/getAssetLifeStatus").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return assetLifeStatus;
		},

		downloadAssetLifeStatusFile: function (assetLifeStatusId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/assetLifeStatusDocDownloadFile?assetLifeStatusId=" + assetLifeStatusId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		deleteAssetLifeStatus: function (req) {
			var asset = Restangular.one("register/assetLifeStatusDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},

		getShortTermRecords: function (req) {
			var shortTermRecords = Restangular.one("register/getStcorrReturns").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return shortTermRecords;
		},
		deactivateShorTermRecords: function (req) {
			var asset = Restangular.one("register/stcorrReturnsDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},

		saveShortTerm: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveStcorrReturns',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("saveShortTermReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		downloadShortTermRecordFile: function (shortTermRecordId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/stcorrReturnsDocDownloadFile?shortTermRecordId=" + shortTermRecordId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

       getShortTermRecords : function(req) {
			var shortTermRecords = Restangular.one("register/getStcorrReturns").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return shortTermRecords;
		},

		getShortTermSearchRecords : function(req) {
			var shortTermRecords = Restangular.one("register/getStcorrReturnsSearch").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return shortTermRecords;
		},

		getSubSequentRentalReceipts : function(req) {
			var asset = Restangular.one("register/getStcorrSubSequentRentalReceipts").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return asset;

		},

		deactivateShorTermRecords: function(req) {
			var asset = Restangular.one("register/stcorrReturnsDeactive").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return asset;
	   },

	   saveShortTerm: function (file, data) {
			return $http({
				headers: {'Content-Type': undefined, 'pottoken' : Principal.potToken()},
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveStcorrReturns',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("saveShortTermReq", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,file : file}
			});
		},

	   downloadShortTermRecordFile : function (shortTermRecordId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/stcorrReturnsDocDownloadFile?shortTermRecordId=" + shortTermRecordId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		saveAssetCostValueStatus: function (file, data) {
			return $http({
				headers: { 'Content-Type': undefined, 'pottoken': Principal.potToken() },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveAssetCostValueStatus',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("assetCostValueStatusSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: { model: data, file: file }
			});
		},

		getAssetCostValueStatus: function (req) {
			var assetLifeStatus = Restangular.one("register/getAssetCostValueStatus").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return assetLifeStatus;
		},

		downloadAssetCostValueStatusFile: function (assetCostValueStatusId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}
			var url = appUrl.appurl + "register/assetCostValueStatusDocDownloadFile?assetCostValueStatusId=" + assetCostValueStatusId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},

		assetCostValueStatusDeactivate: function (req) {
			var asset = Restangular.one("register/assetCostValueStatusDeactive").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return asset;
		},
		getMaterialProjDocketsList: function (req) {
			var materialProjDocketsList = Restangular.one("register/getMaterialProjDocketsByProjId").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return materialProjDocketsList;
		}
	}
}]);
