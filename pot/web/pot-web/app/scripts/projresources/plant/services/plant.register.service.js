'use strict';

app.factory('PlantRegisterService', ["Restangular", "$http", "$q", "appUrl", "Principal", function (Restangular, $http, $q, appUrl, Principal) {
	return {

		getPlantRegistersOnLoad: function (req) {
			var plant = Restangular.one("register/plantRegistersOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantRegisters: function (req) {
			var plant = Restangular.one("register/savePlantregisters").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		deletePlantRegisters: function (req) {
			var plant = Restangular.one("register/deactivatePlantRegisters").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantPOByProjId: function (req) {
			var plant = Restangular.one("register/getPlantPOByProjId").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
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
			var result = Restangular.one("register/savePlantChargeOutRates").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		
		savePlantPayableRates: function (req) {
			var result = Restangular.one("register/savePlantPayableRates").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		
		getPlantLogRecords: function (req) {
			var plant = Restangular.one("register/getPlantLogRecords").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantUtilaizationRecords: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/timemanagement/app/workdairy/getPlantUtilaizationRecords",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		savePlantLogRecords: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/app/register/savePlantLogRecords",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getPlantAttendence: function (req) {
			var result = Restangular.one("register/getPlantAttendence").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		deletePlantLogRecords: function (req) {
			var result = Restangular.one("register/plantLogRecordsDeactivate").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getPlantProcureDeliveryRecords: function (req) {
			var plant = Restangular.one("register/getPlantProcureDeliveryDtls").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantDeploymentOnLoad: function (req) {
			var plant = Restangular.one("register/getPlantDeploymentOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantDeployment: function (req) {
			var plant = Restangular.one("register/savePlantDeployment").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		plantServiceOnLoad: function (req) {
			var plant = Restangular.one("register/plantServiceOnLoad").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantServiceHistory: function (req) {
			var plant = Restangular.one("register/getPlantServiceHistory").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantServiceHistory: function (req) {
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
		plantRepairsOnLoad: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/register/app/register/getPlantRepairs",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getPlantRepairs: function (req) {
			var plant = Restangular.one("register/getPlantRepairs").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPlantMaterialProjDocketDetails: function (req) {
			var plant = Restangular.one("register/getPlantMaterialProjDocketDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		savePlantRepairRecords: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/register/app/register/savePlantRepairs",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
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
		},
		getPlantCurrentStatus: function (req) {
			var currentStatus = Restangular.one("register/getPlantCurrentStatus").customPOST(req, '', {}, {
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
		saveRegPlantProcureDelivery: function (data, filesArray) {
			console.log("saveRegPlantProcureDelivery function");
			/*var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/register/app/register/saveRegPlantProcureDelivery",
				method: "POST",
				headers: { 'Content-Type': undefined },
				cache: false,
				processData: false,
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("req", angular.toJson(data.model));
					return formData;
				},
				data: { model: req, file: file }
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;*/
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
			return $http({
				headers: {'Content-Type': undefined,
				'pottoken': token },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.originurl + "/app/register/saveRegPlantProcureDelivery",
				transformRequest: function (data) {
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("plantProcureDeliveryStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		updatePlantNotificationStatus: function (req) {
			var plant = Restangular.one("notification/updatePlantNotificationStatus").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getPOItemDetails: function (req) {
			var plantProjectPODetails = Restangular.one("procurement/getPOItemDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantProjectPODetails;
		},
		getPOItemsByProject: function (req) {
			var plantProjectPODetails = Restangular.one("procurement/getPOItemsByProject").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantProjectPODetails;
		},
		getRegPlantProcureDeliveryDetails: function (req) {
			console.log(req);
			var plantProjectPODetails = Restangular.one("register/getRegPlantProcureDeliveryDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plantProjectPODetails;
		},
		getPlantTransfers: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/app/register/getPlantTransfers",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		
		getPlantTransferDetails: function (req) {
			var result = Restangular.one("register/getPlantTransferDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		getPlantTransferReqDetails: function (req) {
			var result = Restangular.one("register/getPlantTransferReqDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},
		savePlantTransfers: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/app/register/savePlantTransfers",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getPlantNotifications: function (req) {
			var result = Restangular.one("register/getPlantNotifications").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		}, savePlantPostTransferReceiverDetails: function (req) {
			var result = Restangular.one("register/savePlantPostTransferReceiverDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		}, 
		savePlantNotification: function (req) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/app/notification/savePlantNotification",
				method: "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;			
		},
		getProjSettingsPlantDetailsCheck: function (req) {
			var req = Restangular.one("register/getProjSettingsPlantTransferCheck").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return req;
		},

		getProjectPurchaseOrders: function (req) {
			var purchaseOrders = Restangular.one("poorder/getSOWPurchaseOrders").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return purchaseOrders;
		},

		isPlantCodeUnique: function (assertId) {
			var deferred = $q.defer();
			$http({
				url: appUrl.originurl + "/register/app/register/isPlantCodeUnique?assertId=" + assertId,
				method: "GET",
				headers: {}
			}).then(function (response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		
		downloadRegisterDocs : function(req) {
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
			var fileName = req.fileName;
			var url = appUrl.appurl + "register/downloadRegisterDocs?recordId=" + req.recordId + "&category="+req.category;			
			console.log("file name:"+fileName);
			$http({method: 'GET', url: url,responseType:"arraybuffer"}).then(function(result){
				console.log(result);
				var file = new Blob([result.data], {
					type:  'text/plain'
				});
				FileSaver.saveAs(file, fileName);
				console.log("end");
			});
		},

		/*downloadDocketDoc: function (plantPOId) {
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

			var url = appUrl.appurl + "register/downloadRegPlantProcureDeliveryDoc?plantPOId=" + plantPOId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},*/
	}
}]);
