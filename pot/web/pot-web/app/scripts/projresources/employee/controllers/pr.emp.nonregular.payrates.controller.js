'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empnonregularpay", {
		url : '/empnonregularpay',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/empnonregularpay/empnonregularpay.html',
				controller : 'EmpNonRegularPayController'
			}
		}
	})
}]).controller("EmpNonRegularPayController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PayRollService", "EmpRegisterDataShareFactory", "NonRegularPayFactory", "GenericAlertService", "$location", "EmpRegisterService","EmpDocumentsPopupFactory", function($rootScope, $scope, $q, $state, ngDialog, PayRollService, EmpRegisterDataShareFactory, NonRegularPayFactory, GenericAlertService, $location, EmpRegisterService, EmpDocumentsPopupFactory) {
	$scope.nonRegularPayCodes = [];
	$scope.empNonRegularPayTOs = [];
	$scope.empDropDown = [];
	$scope.unitPayRates = [ "Hourly", "Daily", "Monthly", "Yearly" ];
	$scope.selectedFileMap = [];
	$scope.employeeDocuments = [];

	$scope.payCycles = [];
	$scope.getPayPeriodCycles = function() {
		var req = {
			"status" : "1"
		}
		PayRollService.getPayRollCycle(req).then(function(data) {
			angular.forEach(data.periodCycleTOs, function(value, key) {
				$scope.payCycles.push(value.value);
			});
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting pay period cycles", 'Error');
		});
	},

	$scope.getEmpNonRegularPayDetails = function() {
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
			$scope.empDropDown = data.empMapsData;
			EmpRegisterService.getEmpNonRegularPaybleRates(req).then(function(data) {
	
				$scope.empNonRegularPayTOs = data.empPaybleRateTOs;
				$scope.projGeneralLabelTO = data.projGeneralLabelTO;
				$scope.projEmployeeClassMap = data.projEmployeeClassMap;
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting  employee non regular payment details", 'Error');

			});
		});

	}, $scope.getEmpNonRegularPayDetails();
	
	$scope.changePayCycle = function(selectedPayCycle) {
		$scope.nonRegularPayCodes = [];
		$scope.payCycleId = selectedPayCycle.id;
		$scope.payCycleName = selectedPayCycle.name;
	}
	$scope.changePayRate = function(selectedPayRate) {
		$scope.nonRegularPayCodes = [];
		$scope.payRateId = selectedPayRate.id;
		$scope.payRateName = selectedPayRate.name;
		
	}

	$scope.fileUpload = function( fileObject, record, index ) {	
		$scope.employeeDocuments[0] = {
			"date": '',
			"name": fileObject.name,
			"code": '',
			"fileType": fileObject.type,
			"fileSize": formatBytes(fileObject.size),
			"description": ''
		};
        $scope.selectedFileMap[index] = fileObject;		
		const req = {
			"projDocFileTOs": $scope.employeeDocuments,
			"folderCategory": "Non Regular Payable Rates",
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
	}
	$scope.getNonEmpRegularPayDetails = function(nonRegularpay) {
  // commented to resolve the conflict on 15-12-2020 as it is not required
	//$scope.getNonEmpRegularPayDetails = function(nonRegularpay,action) { 

		if (nonRegularpay.unitPayRate == undefined || nonRegularpay.unitPayRate == null) {
			GenericAlertService.alertMessage("Please select the Unit Of Pay Rate", "Warning");
			return;
		}

		if (nonRegularpay.payCycle == undefined || nonRegularpay.payCycle == null) {
			GenericAlertService.alertMessage("Please select the Credit Cycle", "Warning");
			return;
		}

		var itemData = {
			"empNonRegularPay" : nonRegularpay,
			"projGeneralLabelTO" : $scope.projGeneralLabelTO,
			"projEmployeeClassMap" : $scope.projEmployeeClassMap,
			"empDropDown" : $scope.empDropDown
		}
        console.log(JSON.stringify(itemData));
    // commented to resolve the conflict on 15-12-2020 as it is not required
		//var regularpopup = NonRegularPayFactory.getEmpNonRegularPaybleRateDetails(itemData,action);
    var regularpopup = NonRegularPayFactory.getEmpNonRegularPaybleRateDetails(itemData);
		regularpopup.then(function(data) {
			$scope.empNonRegularPayTOs = data.empPaybleRateTOs;
		
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching employee non regular pay and allowances", 'Error');
		});
	},
	$scope.getRefDocument = function(nonRegularPay) {
		console.log("getRefDocument function");
		console.log(nonRegularPay);
		$rootScope.referenceDocumentMode = "Non Regular Payable Rates";
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(nonRegularPay,"Add","Non Regular Payable Rates");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.empPaybleRateTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	},
	$scope.empDownloadDocuments = function(nonRegularPay) {
		console.log("empDownloadDocuments function");
		console.log(nonRegularPay);
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(nonRegularPay,"Download","Non Regular Payable Rates");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.empPaybleRateTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	}
}]);