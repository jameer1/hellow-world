'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("postinvoice", {
		url : '/postinvoice',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/finance/financemain/invoice/postinvoice.html',
				controller : 'PostInvoiceController'
			}
		}
	})
}]).controller("PostInvoiceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "ProjectInvoiceService", "PostInvoiceFactory", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, ProjectInvoiceService, PostInvoiceFactory) {

	var editInvoiceDetails = [];
	$scope.getInvoiceParticulars = function() {
		var req = {
			"purchaseId" : $rootScope.selectedInvoiceData.id,
			"projId":$rootScope.selectedInvoiceData.projId,
			"status" : 1
		}
		ProjectInvoiceService.getPurchaseOrderInvocies(req).then(function(data) {
			$scope.purchaseOrderInvoiceDtlTOs = data.purchaseOrderInvoiceDtlTOs;
			$scope.userProjMap = data.userProjMap;
			$scope.companyMap = data.companyMap;
			$scope.userMap = data.usersMap;
			$scope.paymentInDays = $rootScope.selectedInvoiceData.paymentInDays;
			$scope.procureCatg = $rootScope.selectedInvoiceData.procureType;
			$scope.projCostItemMap = data.projCostItemMap;
		});
	},
	$scope.getPayDueDate = function(invoice){
		$scope.paymentInDays = $rootScope.selectedInvoiceData.paymentInDays;
	}
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
		$scope.purchaseId = $rootScope.selectedInvoiceData.id
		var popup = PostInvoiceFactory.addPostInvoiceDetails(actionType,$scope.invoiceDetails,$rootScope.selectedInvoiceData,$scope.userProjMap,$scope.companyMap,$scope.userMap);
		popup.then(function(data) {
			$scope.getInvoiceParticulars();
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while creating post invoice details", 'Info');
		})
	},
	 $scope.$on("defaultPostInvoiceDetailsTab", function() {
		$scope.getInvoiceParticulars();
	});

}]);