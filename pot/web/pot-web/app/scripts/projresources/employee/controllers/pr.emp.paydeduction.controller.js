'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("emppaydeduction", {
		url : '/emppaydeduction',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/paydeduction/emppaydeduction.html',
				controller : 'EmpPayDeductionController'
			}
		}
	})
}]).controller("EmpPayDeductionController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PayRollService", "EmpRegisterDataShareFactory", "EmpPayDeductionFactory", "EmpRegisterService", "GenericAlertService", "$location","EmpDocumentsPopupFactory", function($rootScope, $scope, $q, $state, ngDialog, PayRollService, EmpRegisterDataShareFactory, EmpPayDeductionFactory, EmpRegisterService, GenericAlertService, $location,EmpDocumentsPopupFactory) {

	$scope.empPayDeductionTOs = [];
	$scope.empDropDown = [];
	$scope.projGeneralLabelTO = {};
	$scope.payCycles = [];
	$scope.projEmpClassMap = [];
	$scope.unitPayRates = [ "Hourly", "Daily", "Monthly", "Yearly" ];
	$scope.selectedFileMap = [];
	$scope.employeeDocuments = [];

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
	}, $scope.getEmpPayDeductions = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the employee", "Warning");
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
			EmpRegisterService.getEmpPayDeductions(req).then(function(data) {
				$scope.empPayDeductionTOs = data.empPayDeductionTOs;
				$scope.projGeneralLabelTO = data.projGeneralLabelTO;
				$scope.projEmpClassMap = data.projEmployeeClassMap;
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting employee pay deductions", 'Error');
			});
		});

	}, $scope.getEmpPayDeductions();

	$scope.getEmpPayDeductionsDetails = function(payDeduction) {
		if (payDeduction.unitPayRate == undefined || payDeduction.unitPayRate == null) {
			GenericAlertService.alertMessage("Please select the Unit Of Pay Rate", "Warning");
			return;
		}

		if (payDeduction.payCycle == undefined || payDeduction.payCycle == null) {
			GenericAlertService.alertMessage("Please select the Credit Cycle", "Warning");
			return;
		}
		var itemData = {
			"payDeduction" : payDeduction,
			"projGeneralLabelTO" : $scope.projGeneralLabelTO,
			"projEmpClassMap" : $scope.projEmpClassMap,
			"empDropDown" : $scope.empDropDown
		}

		var regularpopup = EmpPayDeductionFactory.getEmpPayDeductionsDetails(itemData);
		regularpopup.then(function(data) {
			$scope.empPayDeductionTOs = data.empPayDeductionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching employee non regular pay and allowances", 'Error');
		});
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
			"folderCategory": "Pay Deductions",
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
	$scope.getRefDocument = function(payDeduction) {
		console.log("getRefDocument function");
		console.log(payDeduction);
		$rootScope.referenceDocumentMode = "Non Regular Payable Rates";
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(payDeduction,"Add","Pay Deductions");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.empPayDeductionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	},
	$scope.empDownloadDocuments = function(payDeduction) {
		console.log("empDownloadDocuments function");
		console.log(payDeduction);
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(payDeduction,"Download","Pay Deductions");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.empPayDeductionTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	}
}]);