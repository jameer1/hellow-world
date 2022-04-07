'use strict';

app.factory('WorkDiaryService', ["$q", "$http", "Restangular", "appUrl","Principal", function ($q, $http, Restangular, appUrl,Principal) {

	function sendWorkDairyRequest(requestPayload, requestEndPoint) {
		return $http({
			url: appUrl.originurl + "/app/workdairy/" + requestEndPoint,
			method: "POST",
			data: JSON.stringify(requestPayload),
		}).then(data => { return data.data });
	}

	return {
		getProjSettingsForWorkDairy: function (req) {
			var workDairy = Restangular.one("workdairy/getProjSettingsForWorkDairy").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});

			return workDairy;
		},
		createWorkDairy: function (req) {
			return sendWorkDairyRequest(req, 'createWorkDairy');
		},
		getWorkdairyReports: function(req) {
			return sendWorkDairyRequest(req, 'getProjectDockets');
		},
		getEmpRegDetails: function (req) {
			var empworkDairy = Restangular.one("workdairy/getEmpRegDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});

			return empworkDairy;
		},
		getPlantRegDetails: function (req) {
			var workDairy = Restangular.one("workdairy/getPlantRegDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairy;
		},
		getMaterialRegDetails: function (req) {
			var workDairy = Restangular.one("workdairy/getMaterialRegDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairy;
		},
		saveMaterialRegDetails: function (req) {
			var workDairyDetails = Restangular.one("workdairy/saveMaterialRegDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyDetails;
		},
		getSowDetails: function (req) {
			var workDairy = Restangular.one("workdairy/getSowDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairy;
		},
		getWorkDairy: function (req) {
			return sendWorkDairyRequest(req, 'getWorkDairy');
		},
		copyWorkDairy: function (req) {
			return sendWorkDairyRequest(req, 'copyWorkDairy');
		},
		getWorkDairyDetails: function (req) {
			return sendWorkDairyRequest(req, 'getWorkDairyDetails');
		},
		saveWorkDairyDetails: function (req) {
			return sendWorkDairyRequest(req, 'saveWorkDairyDetails');
		},
		saveWorkDairyCopyDetails: function (req) {
			var workDairyDetails = Restangular.one("workdairy/saveWorkDairyCopyDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyDetails;
		},

		submitWorkDairy: function (req) {
			return sendWorkDairyRequest(req, 'submitWorkDairy');
		},
		approveWorkDairy: function (req) {
			var workDairy = Restangular.one("workdairy/approveWorkDairy").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});

			return workDairy;
		},
		clientApproveWorkDairy: function (req) {
			var workDairy = Restangular.one("workdairy/clientApproveWorkDairy").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});

			return workDairy;
		},
		getWorkDairyCostCodes: function (req) {

			var workDairyCostCodes = Restangular.one("workdairy/getWorkDairyCostCodes").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyCostCodes;
		},
		getWorkDairyCrewCostCodes: function (req) {

			var workDairyCostCodes = Restangular.one("workdairy/getWorkDairyCrewCostCodes").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyCostCodes;
		},
		saveWorkDairyCostCodes: function (req) {
			return sendWorkDairyRequest(req, 'saveWorkDairyCostCodes');
		},
		saveMoreSowCostCodes: function (req) {
			return sendWorkDairyRequest(req, 'saveMoreSowCostCodes');
		},
		saveWorkDairyCrewCostCodes: function (req) {

			var workDairyCostCodes = Restangular.one("workdairy/saveWorkDairyCrewCostCodes").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyCostCodes;
		},

		saveWorkDairyNotifications: function (req) {
			var workDairynotifications = Restangular.one("notification/saveWorkDiaryNotifications").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairynotifications;
		},

		getWorkDairyEmpDetails: function (req) {
			var workDairyEmpDetails = Restangular.one("workdairy/getWorkDairyEmpDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyEmpDetails;
		},
		saveWorkDairyEmpDetails: function (req) {
			var workDairyEmpDetails = Restangular.one("workdairy/saveWorkDairyEmpDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyEmpDetails;
		},
		getWorkDairyPlantDetails: function (req) {
			var plant = Restangular.one("workdairy/getWorkDairyPlantDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		saveWorkDairyPlantDetails: function (req) {
			var plant = Restangular.one("workdairy/saveWorkDairyPlantDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return plant;
		},
		getWorkDairyMaterialDetails: function (req) {
			var material = Restangular.one("workdairy/getWorkDairyMaterialDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return material;
		},
		saveWorkDairyMaterialDetails: function (req) {
			return sendWorkDairyRequest(req, 'saveWorkDairyMaterialDetails');
		},
		getWorkDairyProgressDetails: function (req) {
			var progrss = Restangular.one("workdairy/getWorkDairyProgressDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return progrss;
		},
		saveWorkDairyProgressDetails: function (data,filesArray) {
			//return sendWorkDairyRequest(req, 'saveWorkDairyProgressDetails');
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
			console.log(appUrl.originurl + "/app/workdairy/saveWorkDairyProgressDetails");
			return $http({
				headers: {'Content-Type': undefined,
				'pottoken': token },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.originurl + "/app/workdairy/saveWorkDairyProgressDetails",
				transformRequest: function (data) {
					console.log(data);
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("workDairyProgressStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		getProcureSowItems: function (req) {
			var sowItems = Restangular.one("procurment/getProcureSowItems").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowItems;
		},
		getMaterialDeliveryDocketDetails: function (req) {
			var sowItems = Restangular.one("register/getMaterialDeliveryDocketDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowItems;
		},
		getMaterialProjDocketsByDoctype: function (req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/register/getMaterialProjDocketsByDoctype",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getMaterialProjDocketSchItems: function (req) {
			var deferred = $q.defer();
			$http({
					url : appUrl.originurl + "/app/register/getMaterialProjDocketSchItems",
					method : "POST",
					data: JSON.stringify(req),
					headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getWorkDairyForInternalApproval: function (req) {
			var sowItems = Restangular.one("workdairy/getWorkDairyForInternalApproval").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowItems;
		}, getWorkDairyForClientApproval: function (req) {
			var sowItems = Restangular.one("workdairy/getWorkDairyForClientApproval").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowItems;
		}, getWorkDairyById: function (req) {
			var sowItems = Restangular.one("workdairy/getWorkDairyById").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowItems;
		},
		getProjSettingsWorkDairyDetails: function (req) {
			var sowItems = Restangular.one("workdairy/getProjSettingsWorkDairyDetails").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return sowItems;
		},
		getWorkDairyApprovalReport: function (req) {
			return sendWorkDairyRequest(req, 'getWorkDairyApprovalReport');
		},
		getCreatedWorkDiaries: function (req) {
			var createdWorkDairies = Restangular.one("workdairy/getCreatedWorkDiaries").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return createdWorkDairies;
		},
		getSubmittedWorkDiaries: function (req) {
			var createdWorkDairies = Restangular.one("workdairy/getSubmittedWorkDiaries").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return createdWorkDairies;
		},
		deleteWorkDairyProgressRecords: function(req) {
			var createdWorkDairies = Restangular.one("workdairy/deleteWorkDairy").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return createdWorkDairies;
		},
		deleteWorkDairyManpowerRecords : function(req) {
			var workDairyPlantRecords = Restangular.one("workdairy/deleteWorkDairy").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyPlantRecords;
		},
		deleteWorkDairyPlantRecords : function(req) {
			var workDairyPlantRecords = Restangular.one("workdairy/deleteWorkDairy").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return workDairyPlantRecords;
		}
	}
}]);
