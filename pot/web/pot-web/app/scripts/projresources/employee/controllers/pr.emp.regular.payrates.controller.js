'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empregularpayrates", {
		url : '/empregularpayrates',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/empregularpay/empregularpay.html',
				controller : 'EmpRegualrPayController'
			}
		}
	})
}]).controller("EmpRegualrPayController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PayRollService", "EmpRegisterDataShareFactory", "RegularPayRatesFactory", "EmpDocumentsPopupFactory", "GenericAlertService", "$location", "EmpRegisterService", "RegularPayservice", function($rootScope, $scope, $q, $state, ngDialog, PayRollService, EmpRegisterDataShareFactory, RegularPayRatesFactory, EmpDocumentsPopupFactory, GenericAlertService, $location, EmpRegisterService, RegularPayservice) {

	$scope.regularPayCodes = [];
	$scope.employeeRegularPayTOs = [];
	$scope.projGeneralLabelTO = [];
	$scope.projEmployeeClassMap = [];
	$scope.empDropDown = [];
	$scope.unitPayRates = [ "Hourly", "Daily", "Monthly", "Yearly" ];
	$scope.payCycles = [];
	$scope.selectedFileMap = [];
	$scope.employeeDocuments = [];
	
	$scope.getPayPeriodCycles = function() {
		var req = {
			"status" : "1"
		}
		//console.log("=====getPayPeriodCycles===")
		PayRollService.getPayRollCycle(req).then(function(data) {
			//console.log("==in method=====")
			angular.forEach(data.periodCycleTOs, function(value, key) {
				$scope.payCycles.push(value.value);
				//console.log("$scope.payCycles",$scope.payCycles)
				//console.log("$scope.payCycles.push(value.value)",$scope.payCycles.push(value.value))
				//console.log("===for each====")
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting pay period cycles", 'Error');
		});
	},

	$scope.getEmployeeRegularDetails = function() {
		$scope.getPayPeriodCycles();
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the Employee", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"projId" : $rootScope.selectedEmployeeData.projId,
			"empId" : $rootScope.selectedEmployeeData.id
		};
		var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
		resultMap.then(function(data) {
			//console.log("====data=====",data)
			$scope.empDropDown = data.empMapsData;
			EmpRegisterService.getEmpRegularPaybleRates(req).then(function(data) {
				console.log(data);
				$scope.employeeRegularPayTOs = data.empPaybleRateTOs;
				//console.log("====$scope.employeeRegularPayTOs====",$scope.employeeRegularPayTOs)
				$scope.projGeneralLabelTO = data.projGeneralLabelTO;
				//console.log("====$scope.projGeneralLabelTO====",$scope.projGeneralLabelTO)
				$scope.projEmployeeClassMap = data.projEmployeeClassMap;
				//console.log("====$scope.projEmployeeClassMap====",$scope.projEmployeeClassMap)
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting  employee regular payment details", 'Error');

			});
		});

	},

	$scope.getEmployeeRegularDetails();
	$scope.changeUnitPayRate = function(selectedPayRate) {
		//console.log("==selectedPayRate==",selectedPayRate)
		$scope.regularPayCodes = [];
		$scope.unitPayRateName = selectedPayRate.name;
		$scope.unitPayRateId = selectedPayRate.id;
	}
	$scope.changePayCycle = function(selectedPayCycle) {
		//console.log("==changePayCycle==")
		//console.log("==selectedPayCycle==",selectedPayCycle)
		$scope.regularPayCodes = [];
		//console.log("==selectedPayCycle==",selectedPayCycle)
		$scope.payCycleId = selectedPayCycle.id;
		$scope.payCycleName = selectedPayCycle.name;		
	}
	$scope.fileUpload = function( fileObject, record, index ) {
		$scope.employeeDocuments[0] = {
			"date": '',
			"name": fileObject.name,
			"code": '',
			"fileType": fileObject.type,
			"fileSize": formatBytes(fileObject.size),
			"description": '',
		};
        $scope.selectedFileMap[index] = fileObject;		
		const req = {
			"projDocFileTOs": $scope.employeeDocuments,
			"folderCategory": "Regular Payable Rates",
			"projId": $rootScope.selectedEmployeeData.projId,
			"empId" : $rootScope.selectedEmployeeData.id
		}
		var files = [];
		for (let idx = 0; idx < $scope.selectedFileMap.length; idx++) {
			const value = $scope.selectedFileMap[idx];
			if (value) {
				$scope.employeeDocuments[idx].fileObjectIndex = files.length;
				files.push(value);
			}
		}
		console.log(req);
		console.log(files);
		EmpRegisterService.saveEmployeeDocs(files,req).then(function (data) {
			var succMsg = GenericAlertService.alertMessageModal(
				'Employee Documents uploaded successfully ', 'Info');
			succMsg.then(function (popData) {
				const returnPopObj = {
					"projDocFileTO": data.projDocFileTO
				};
				$scope.closeThisDialog();
				deferred.resolve(returnPopObj);
			});
		}, function (error) {
			GenericAlertService.alertMessage('Employee Documents failed to upload', 'Error');
		});
	}
	function formatBytes(bytes) {
		if(bytes < 1024) return bytes + " Bytes";
		else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
		else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
		else return(bytes / 1073741824).toFixed(3) + " GB";
		//console.log("==$scope.payCycleId==",$scope.payCycleId)
		//console.log("==selectedPayCycle.id==",selectedPayCycle.id)
	}
	$scope.viewEmpRegularPayAllowances = function(regpay, action) {
		//console.log("action",action)
		if (regpay.unitPayRate == undefined || regpay.unitPayRate == null) {
			GenericAlertService.alertMessage("Please select the Unit Of Pay Rate", "Warning");
			return;
		}
		if (regpay.payCycle == undefined || regpay.payCycle == null) {
			GenericAlertService.alertMessage("Please select the Credit Cycle", "Warning");
			return;
		}

		var itemData = {
			"empRegularPay" : regpay,
			"projGeneralLabelTO" : $scope.projGeneralLabelTO,
			"projEmployeeClassMap" : $scope.projEmployeeClassMap,
			"empDropDown" : $scope.empDropDown
		}
		var regularpopup = RegularPayRatesFactory.getEmpRegularPaybleRateDetails(itemData,action);
		regularpopup.then(function(data) {
			$scope.employeeRegularPayTOs = data.empPaybleRateTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching employee regular pay and allowances", 'Error');
		});
	},
	$scope.$on("resetRegularPayable", function() {
		$scope.employeeRegularPayTOs = [];
	}),
	$scope.getRefDocument = function(regpay) {
		console.log("getRefDocument function");
		console.log(regpay);
		$rootScope.referenceDocumentMode = "Regular Payable Rates";		
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(regpay,"Add","Regular Payable Rates");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.regPayRatesDocsTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	},
	$scope.empDownloadDocuments = function(regpay) {
		console.log("empDownloadDocuments function");
		console.log(regpay);
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(regpay,"Download","Regular Payable Rates");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.regPayRatesDocsTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	}	
}]);
