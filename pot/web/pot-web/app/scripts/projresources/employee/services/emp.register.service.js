'use strict';

app.factory('EmpRegisterService', ["Restangular", "$http", "appUrl", "Principal", "$q", function(Restangular, $http, appUrl, Principal, $q) {
	var FileSaver = require('file-saver');
	return {

		empRegisterOnLoad : function(req) {
			var employees = Restangular.one("register/empRegisterOnLoad").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return employees;
		},
		saveEmpregisters : function(req) {
			var saveEmpData = Restangular.one("register/saveEmpregisters").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpData;
		},
		saveEmpPayDeduction : function(req) {
			var saveEmpData = Restangular.one("register/saveEmpPayDeduction").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpData;
		},
		deactivateEmpRegisters : function(req) {
			var empDeactivateStatus = Restangular.one("register/EmpRegistersDeactivate").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empDeactivateStatus;
		},
		getEmpEnrollments : function(req) {
			var empEnrollmentMaps = Restangular.one("register/getEmpEnrollments").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empEnrollmentMaps;
		},
		saveEmpEnrollments : function(file, req) {

			return $http({
				headers: {'Content-Type': undefined, 'pottoken' : Principal.potToken()},
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveEmpEnrollments',
				transformRequest: function (data) {
					var formData = new FormData();
					formData.append("file", data.file);
					formData.append("empEnrollmentSaveReq", angular.toJson(data.model));
					return formData;
				},
				data: {model:req,file : file}
			});
		},

		getEmpServiceHistory : function(req) {
			var empServiceHistory = Restangular.one("register/getEmpServiceHistory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empServiceHistory;
		},
		getEmpLatestServiceHistory : function(req) {
			var empServiceHistory = Restangular.one("register/getEmpLatestServiceHistory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empServiceHistory;
		},
		saveEmpServiceHistory : function(req) {
			var saveEmpServiceData = Restangular.one("register/saveEmpServiceHistory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpServiceData;
		},
		saveEmployeeNotifications : function(req) {
			var saveEmpServiceData = Restangular.one("notification/saveEmpNotifications").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpServiceData;
		},
		deactivateEmpServiceHistory : function(req) {
			var deactivateEmpService = Restangular.one("register/deactivateEmpServiceHistory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpService;
		},

		empChargeOutRatesOnLoad : function(req) {
			var empChargeOutRates = Restangular.one("register/empChargeOutRatesOnLoad").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empChargeOutRates;
		},
		getEmpChargeOutRates : function(req) {
			var empChargeOutRates = Restangular.one("register/getEmpChargeOutRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empChargeOutRates;
		},
		saveEmpChargeOutRates : function(req) {
			var saveEmpChargeData = Restangular.one("register/saveEmpChargeOutRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpChargeData;
		},
		deactivateEmpChargeOutRates : function(req) {
			var deactivateEmpCharge = Restangular.one("register/deactivateEmpChargeOutRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpCharge;
		},
		getEmpRegularPaybleRates : function(req) {
			console.log("====getEmpRegularPaybleRates====")
			var empRegPay = Restangular.one("register/getEmpRegularPaybleRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			console.log("====close====")
			return empRegPay;
		},
		
		/*getEmpRegularPaybleRateDetails : function(empRegisterReq) {
            var deferred = $q.defer();
			console.log("getEmpRegularPaybleRateDetails")
            $http({
            	url : appUrl.originurl + "/app/register/getEmpRegularPaybleRateDetails",
                method : "POST",
                data: JSON.stringify(empRegisterReq),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
				console.log("close")
            return deferred.promise;
        },*/

		getEmpRegularPaybleRateDetails : function(req) {
			//console.log("true======getEmpRegularPaybleRateDetails")
			var empRegPay = Restangular.one("register/getEmpRegularPaybleRateDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			//console.log("true======close=====")
			return empRegPay;
		},
		saveEmpRegularPaybleRates : function(req) {
			var saveEmpRegPayData = Restangular.one("register/saveEmpRegularPaybleRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpRegPayData;
		},
		getEmpNonRegularPaybleRates : function(req) {
			var result = Restangular.one("register/getEmpNonRegularPaybleRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpNonRegularPaybleRateDetails : function(req) {
			var result = Restangular.one("register/getEmpNonRegularPaybleRateDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpNonRegularPaybleRates : function(req) {
			var result = Restangular.one("register/saveEmpNonRegularPaybleRates").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		deactivateEmpNonRegPayments : function(req) {
			var deactivateEmpNonRegPay = Restangular.one("register/deactivateEmpNonRegPayments").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpNonRegPay;
		},

		getEmpPayDeductions : function(req) {
			var empTax = Restangular.one("register/getEmpPayDeductions").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empTax;
		},
		getEmpPayDeductionsDetails : function(req) {
			var saveEmpTaxData = Restangular.one("register/getEmpPayDeductionsDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpTaxData;
		},
		saveEmpPayDeductions : function(req) {
			var result = Restangular.one("register/saveEmpPayDeductions").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		deactivateEmpTaxDetails : function(req) {
			var deactivateEmpTax = Restangular.one("register/deactivateEmpTaxDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpTax;
		},

		getEmpBankAccountDetails : function(req) {
			var empBankAccount = Restangular.one("register/getEmpBankAccountDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empBankAccount;
		},
		saveEmpBankAccountDetails : function(req) {
			var saveBankAccountDtls = Restangular.one("register/saveEmpBankAccountDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveBankAccountDtls;
		},
		deactivateBankAccDetails : function(req) {
			var deactivateBankAcc = Restangular.one("register/deactivateBankAccountDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateBankAcc;
		},
		getEmpProvidentFunds : function(req) {
			var result = Restangular.one("register/getEmpProvidentFunds").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},

		getEmpProvidentFundDetails : function(req) {
			var result = Restangular.one("register/getEmpProvidentFundDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpProvidentFunds : function(req) {
			var result = Restangular.one("register/saveEmpProvidentFunds").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		deactivateEmpPFDetails : function(req) {
			var deactivateEmpPF = Restangular.one("register/deactivateEmpPFDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpPF;
		},

		getEmpMedicalHistory : function(req) {
			var empMedicalHistory = Restangular.one("register/getEmpMedicalHistory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empMedicalHistory;
		},
		saveEmpMedicalHistory : function(filesArray,data) {
			/*var saveEmpMedicalDtls = Restangular.one("register/saveEmpMedicalHistory").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpMedicalDtls;*/
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
				url: appUrl.appurl + 'register/saveEmpMedicalHistory',
				transformRequest: function (data) {
				console.log(data);
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("empMedicalHistoryStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		deactivateEmpMedicalDetails : function(req) {
			var deactivateEmpMedical = Restangular.one("register/deactivateEmpMedicalDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpMedical;
		},

		getEmpAttendanceDtls : function(req) {
			var empAttendance = Restangular.one("register/getEmpAttendance").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empAttendance;
		},
		saveEmpAttendanceDtls : function(req) {
			var saveEmpAttendance = Restangular.one("register/saveEmpAttendanceDtls").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpAttendance;
		},
		deactivateEmpAttendanceDetails : function(req) {
			var deactivateEmpAttendance = Restangular.one("register/deactivateEmpAttendanceDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpAttendance;
		},

		getEmpContactDetails : function(req) {
			var result = Restangular.one("register/getEmpContactDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpContactDetails : function(req) {
			var result = Restangular.one("register/saveEmpContactDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpNok : function(req) {
			var result = Restangular.one("register/getEmpNok").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpNok : function(req) {
			var saveEmpNOKDetails = Restangular.one("register/saveEmpNok").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpNOKDetails;
		},
		updateEmpNotificationStatus : function(req) {
			var empNotify = Restangular.one("notification/updateEmpNotificationStatus").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return empNotify;
		},
		deactivateEmpNOKDtls : function(req) {
			var deactivateEmpNOKDetails = Restangular.one("register/deacctivateEmpNok").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return deactivateEmpNOKDetails;
		},
		getEmpTransfers : function(req) {
			var result = Restangular.one("register/getEmpTransfers").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpTransferDetails : function(req) {
			var result = Restangular.one("register/getEmpTransferDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpTransfers : function(req) {
			var result = Restangular.one("register/saveEmpTransfers").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpTransferReqDetails : function(req) {
			var result = Restangular.one("register/getEmpTransferReqDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpLeaveReqApprovals : function(req) {
			var result = Restangular.one("register/getEmpLeaveReqApprovals").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpLeaveReqApprovalDetails : function(req) {
			var result = Restangular.one("register/getEmpLeaveReqApprovalDetails").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpLeaveReqApprovals : function(req) {
			var result = Restangular.one("register/saveEmpLeaveReqApprovals").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpLeaveAttendenceDtls : function(req) {
			var result = Restangular.one("register/getEmpLeaveAttendenceDtls").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveEmpLeaveAttendenceDtls : function(req) {
			var result = Restangular.one("register/saveEmpLeaveAttendenceDtls").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getEmpNotifications : function(req) {
			var result = Restangular.one("register/getEmpNotifications").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getProjSettingsEmpLeaveCheck : function(req) {
			var req = Restangular.one("register/getProjSettingsEmpLeaveCheck").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return req;
		},
		saveEmployeeLeaveNotifications : function(req) {
			var saveEmpServiceData = Restangular.one("notification/saveEmployeeLeaveNotifications").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return saveEmpServiceData;
		},
		isEmpCodeUnique : function(req) {
			var isUnique = Restangular.one("register/isEmpCodeUnique").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return isUnique;
		},

		downloadContract : function (enrollId,fileName) {
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
			var url = appUrl.appurl + "register/downloadEnrollmentContract?enrollId=" + enrollId + "&pottoken=" + token;			
			console.log("file name:"+fileName);
			$http({method: 'GET', url: url,responseType:"arraybuffer"}).then(function(result){
				console.log(result);
				console.log(result.fileName);
				var file = new Blob([result.data], {
					type:  'text/plain'
				});
				//var blob = new Blob(["Hello, world!"], {type: "text/plain;charset=utf-8"});
				FileSaver.saveAs(file, fileName);
				console.log("end");
			});
		},

		getProjEmployees : function (req) {
			var deferred = $q.defer();
			$http({
				url : appUrl.originurl + "/app/register/getProjEmployees",
				method : "GET",
				params: req,
				headers: {}
			}).then(function(response) {
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		
		saveEmployeeDocs : function(filesArray,data) {
			console.log("saveEmployeeDocs function");			
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
				url: appUrl.appurl + 'register/saveEmployeeDocs',
				transformRequest: function (data) {
					console.log(data);
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("employeeDocsStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		getEmployeeDocs : function(req) {
			var empEmployeeDocs = Restangular.one("register/getEmployeeDocs").customPOST( req, '', {}, {
				ContentType : 'application/json'
			});
			return empEmployeeDocs;
		},
		saveEmpQualificationRecords : function(filesArray,data) {
			console.log("saveEmpQualificationRecords function");			
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
				headers: {'Content-Type': undefined,'pottoken': token },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'register/saveEmpQualificationRecords',
				transformRequest: function (data) {
					console.log(data);
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("empQualRecordsStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
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
		getEmpQualificationRecords : function(req) {
			var empQualificationRecords = Restangular.one("register/getEmpQualificationRecords").customPOST( req, '', {}, {
				ContentType : 'application/json'
			});
			return empQualificationRecords;
		}
	}
}]);