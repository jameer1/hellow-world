'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("approveinvoice", {
		url : '/approveinvoice',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/invoice/approveinvoice.html',
				controller : 'ApproveInvoiceController'
			}
		}
	})
}]).controller("ApproveInvoiceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "PostInvoiceFactory", "ApproveInvoiceFactory", "ProjectInvoiceService", "GenericAlertService", function($rootScope, $scope, $q, $state, ngDialog, PostInvoiceFactory, ApproveInvoiceFactory,ProjectInvoiceService, GenericAlertService) {

	$scope.userType = '1';
	$scope.currentStatus = {
		id : "1",
		code : "PA",
		name : "Pending Approval"
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

	var editInvoiceDetails = [];
	$scope.rowSelect = function(invoice) {
		if (invoice.selected) {
			editInvoiceDetails.push(invoice);
		} else {
			editInvoiceDetails.splice(editInvoiceDetails.indexOf(invoice), 1);
		}
	},
	$scope.addPostAnInvoiceDetails = function(actionType) {
		if ($rootScope.selectedInvoiceData == null || $rootScope.selectedInvoiceData == undefined) {
			GenericAlertService.alertMessage("Please select the invoice", "Warning");
			return;
		}
		angular.forEach(editInvoiceDetails, function(value) {
			$scope.invoiceDetails = value;
			value.selected = false;
		});
		if (actionType == 'Edit' && editInvoiceDetails <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var popup = ApproveInvoiceFactory.addPostInvoiceDetails(actionType,$scope.invoiceDetails,$rootScope.selectedInvoiceData,$scope.userProjMap,$scope.companyMap,$scope.userMap);
		popup.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while creating post invoice details", 'Info');
		})
	}
	$scope.getApproveInvocie = function() {
		if ($rootScope.selectedInvoiceData == null || $rootScope.selectedInvoiceData == undefined) {
			GenericAlertService.alertMessage("Please select the invoice", "Warning");
			return;
		}
		var loginUser = true;
		if ($scope.userType == '2') {
			loginUser = false;
		}
		var req = {
			"purchaseId" : $rootScope.selectedInvoiceData.id,
			"projId" : $rootScope.selectedInvoiceData.projId,
			"approveStatus":$scope.currentStatus.name,
			"loginUser" : loginUser,
			"status" : 1
		}
		ProjectInvoiceService.getApproveInvocie(req).then(function(data) {
			$scope.purchaseOrderInvoiceDtlTOs = data.purchaseOrderInvoiceDtlTOs;
			$scope.userProjMap = data.userProjMap;
			$scope.companyMap = data.companyMap;
			$scope.userMap = data.usersMap;
			$scope.paymentInDays = $rootScope.selectedInvoiceData.paymentInDays;
			$scope.procureCatg = $rootScope.selectedInvoiceData.procureType;
			$scope.projCostItemMap = data.projCostItemMap;
		});
	}, $scope.getApproveInvocie();

}]);