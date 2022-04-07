'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empprovidentfund", {
		url : '/empprovidentfund',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/empprovident/empprovident.html',
				controller : 'EmpProvidentFundController'
			}
		}
	})
}]).controller("EmpProvidentFundController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PayRollService", "EmpRegisterDataShareFactory", "EmpProvidentFundFactory", "GenericAlertService", "$location", "EmpRegisterService","EmpDocumentsPopupFactory", function($rootScope, $scope, $q, $state, ngDialog,PayRollService, EmpRegisterDataShareFactory, EmpProvidentFundFactory, GenericAlertService, $location, EmpRegisterService,EmpDocumentsPopupFactory) {

	$scope.empProvidentFundTOs = [];
	$scope.empDropDown = [];
	$scope.payCycles = [];
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
		},
	$scope.getEmpProvidentFunds = function() {
		$scope.getPayPeriodCycles();
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
			EmpRegisterService.getEmpProvidentFunds(req).then(function(data) {
				$scope.empProvidentFundTOs = data.empProvidentFundTOs;
				$scope.projGeneralLabelTO = data.projGeneralLabelTO;
				$scope.projEmployeeClassMap = data.projEmployeeClassMap;
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting employee provident fund details", 'Error');
			});
		});

	}, $scope.getEmpProvidentFunds();
	$scope.getProvidentFundDetails = function(empProvidentFundTO) {
		if (empProvidentFundTO.payCycle == undefined || empProvidentFundTO.payCycle == null) {
			GenericAlertService.alertMessage("Please select the Credit Cycle", "Warning");
			return;
		}
		var itemData = {
			"empProvidentFundTO" : empProvidentFundTO,
			"projGeneralLabelTO" : $scope.projGeneralLabelTO,
			"empDropDown" : $scope.empDropDown
		}
		var providentfundpopupdetails = EmpProvidentFundFactory.getEmpProvidentfundDetails(itemData);
		providentfundpopupdetails.then(function(data) {
			$scope.empProvidentFundTOs = data.empProvidentFundTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching employee provident fund  details", 'Error');
		});
	},$scope.changePayCycle = function(selectedPayCycle) {
			
			$scope.payCycleId = selectedPayCycle.id;
			$scope.payCycleName = selectedPayCycle.name;
			
		},
		$scope.$on("resetProvidentFund", function() {
			$scope.empProvidentFundTOs = [];
		});
		
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
			"folderCategory": "Super Annuation/Provident Fund",
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
	$scope.getRefDocument = function(providentfund) {
		console.log("getRefDocument function");
		console.log(providentfund);
		$rootScope.referenceDocumentMode = "Non Regular Payable Rates";
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(providentfund,"Add","Super Annuation/Provident Fund");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.empPaybleRateTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	},
	$scope.empDownloadDocuments = function(providentfund) {
		console.log("empDownloadDocuments function");
		console.log(providentfund);
		var refreq = EmpDocumentsPopupFactory.referenceDocumentDetails(providentfund,"Download","Super Annuation/Provident Fund");
		refreq.then(function(data) {
			$scope.refdocumentslist = data.empPaybleRateTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})
	}
}]);