'use strict';
app.factory('MaterialRegisterService', ["Restangular", "$q", "$http", "appUrl", "Principal",
		function(Restangular, $q, $http, appUrl, Principal) {
	return {
		materialHomedefaultSearch : function(req) {
			var result = Restangular.one("register/materialHomedefaultSearch").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},

		getProjMaterialSchItems : function(req) {
			var result = Restangular.one("register/getProjMaterialSchItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialSchItemsByPurchaseOrder : function(req) {
			var result = Restangular.one("register/getMaterialSchItemsByPurchaseOrder").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getProjMaterialSch : function(req) {
			var result = Restangular.one("register/getProjMaterialSch").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialDeliveryDockets : function(req) {
			var result = Restangular.one("register/getMaterialDeliveryDockets").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialSchItemDeliveryDockets : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getMaterialSchItemDeliveryDockets",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialDeliveryDocketDetails : function(req) {
			var result = Restangular.one("register/getMaterialDeliveryDocketDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveProjMaterialSchItems : function(req) {
			var result = Restangular.one("register/saveProjMaterialSchItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveProjMaterialSchDocketDetails : function(filesArray, data) {
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
				url: appUrl.originurl + "/app/register/saveProjMaterialSchDocketDetails",
				transformRequest: function (data) {
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("materialReq", angular.toJson(data.model));
					console.log(formData);
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		downloadMaterialFile : function(id) {
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
			var url = appUrl.originurl + "/app/register/downloadMaterialFile?mapId=" + id + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable pop-up for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		saveAdditionalMaterialNotifications : function(req) {
			var result = Restangular.one("notification/saveMaterialNotification").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		updateMaterialNotificationStatus : function(req) {
			var result = Restangular.one("notification/updateMaterialNotificationStatus").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialSchItemsByProject : function(req) {
			var result = Restangular.one("register/getMaterialSchItemsByProject").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialSchItemsByProjectAndLoc : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getMaterialSchItemsByProjectAndLoc",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		saveMaterialProjDockets : function(req) {
			var result = Restangular.one("register/saveMaterialProjDockets").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getProjectIssueDockets : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getMaterialProjDockets",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialProjConsumption : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/timemanagement/app/workdairy/getMaterialProjConsumption",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialStoreTransitConsumption : function(req) {
			var result = Restangular.one("workdairy/getInTransitMaterials").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialStockPiledConsumption : function(req) {
			var result = Restangular.one("workdairy/getStockPiledMaterials").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialDailyIssueSchItems : function(req) {
			var result = Restangular.one("register/getMaterialDailyIssueSchItems").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialProjLedgers : function(req) {
			var result = Restangular.one("workdairy/getMaterialLedger").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getInventoryReport : function(req) {
			var result = Restangular.one("workdairy/getInventoryReport").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getMaterialTransfers : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getMaterialTransfers",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialTranferDetails : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getMaterialTranferDetails",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialDetailsForTransfer : function(req) {
			var result = Restangular.one("register/getMaterialDetailsForTransfer").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveMaterialTransfers : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/saveMaterialTransfers",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialsForProjDocket : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/register/app/register/getMaterialsForProjDocket",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialNotification : function(req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getMaterialNotification",
				method : "POST",
				data: JSON.stringify(req),
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getProjSettingsMaterialDetailsCheck : function(req) {
			var req = Restangular.one("register/getProjSettingsMaterialTransferCheck").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return req;
		},
		getMaterialSchDetailsForTransfer : function(req) {
			var result = Restangular.one("register/getMaterialSchDetailsForTransfer").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
	}
}]);
