'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("releasepaymentinvoice", {
		url : '/releasepaymentinvoice',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/invoice/releasepayment.html',
				controller : 'ReleasePaymentInvoiceController'
			}
		}
	})
}]).controller("ReleasePaymentInvoiceController", ["$rootScope", "$scope", "$q", "$state", "ReleasePaymentFactory", "ngDialog", "ProjectInvoiceService", "GenericAlertService", function($rootScope, $scope, $q, $state, ReleasePaymentFactory, ngDialog, ProjectInvoiceService, GenericAlertService) {

	var editInvoiceDetails = [];
	$scope.userType = '1';
	$scope.currentStatus = {
		id : "1",
		code : "Appr",
		name : "Approved"
	};

	$scope.currentStatusList = [ {
		id : "1",
		code : "PA",
		name : "Pending Approval"
	}, {
		id : "2",
		code : "Appr",
		name : "Approved"
	}, {
		id : "3",
		code : "AOH",
		name : "Approval On Hold"
	}, {
		id : "3",
		code : "paid",
		name : "Paid"
	}, {
		id : "3",
		code : "all",
		name : "All"
	} ];
	$scope.payStatus = {
		id : "1",
		code : "ytr",
		name : "Yet To Release"
	};
	$scope.payStatusList = [ {
		id : "1",
		code : "Release",
		name : "Released"
	}, {
		id : "2",
		code : "ytr",
		name : "Yet To Release"
	}, {
		id : "3",
		code : "paid",
		name : "Paid"
	} ];
	
	$scope.rowSelect = function(invoice) {
		if (invoice.selected) {
			editInvoiceDetails.push(invoice);
		} else {
			editInvoiceDetails.splice(editInvoiceDetails.indexOf(invoice), 1);
		}
	}, $scope.addPaymentDetails = function(actionType) {
		if ($rootScope.selectedInvoiceData == null || $rootScope.selectedInvoiceData == undefined) {
			GenericAlertService.alertMessage("Please select the invoice", "Warning");
			return;
		}
		angular.forEach(editInvoiceDetails, function(value) {
			$scope.invoiceDetails = value;
			value.selected = false;
		});
		if (actionType == 'Edit' && editInvoiceDetails <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to release the payment", 'Warning');
			return;
		}
		var popup = ReleasePaymentFactory.paymentDetails(actionType,$scope.invoiceDetails,$rootScope.selectedInvoiceData, $scope.userProjMap, $scope.companyMap, $scope.userMap);
		popup.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while creating release payment details", 'Info');
		})
	}
	$scope.getReleaseInvoiceDetails = function() {
		if ($rootScope.selectedInvoiceData == null || $rootScope.selectedInvoiceData == undefined) {
			GenericAlertService.alertMessage("Please select the invoice id", "Warning");
			return;
		}
		var loginUser = true;
		if ($scope.userType == '2') {
			loginUser = false;
		}
		var req = {
			"purchaseId" : $rootScope.selectedInvoiceData.id,
			"projId" : $rootScope.selectedInvoiceData.projId,
			"approveStatus" : $scope.currentStatus.name,
			"paymentStatus" : $scope.payStatus.name,
			"loginUser" : loginUser,
			"status" : 1
		}
		ProjectInvoiceService.getReleaseInvoiceDetails(req).then(function(data) {
			$scope.purchaseOrderInvoiceDtlTOs = data.purchaseOrderInvoiceDtlTOs;
			$scope.userProjMap = data.userProjMap;
			$scope.companyMap = data.companyMap;
			$scope.userMap = data.usersMap;
			$scope.paymentInDays = $rootScope.selectedInvoiceData.paymentInDays;
			$scope.procureCatg = $rootScope.selectedInvoiceData.procureType;
			$scope.projCostItemMap = data.projCostItemMap;
		});
	},

	$scope.getReleaseInvoiceDetails();

}]);