'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:RoleController
 * @description # Role Controller of the potApp
 */
app
		.config(["$stateProvider", function($stateProvider) {
			$stateProvider.state('invoicereports', {
				url : '/invoicereports',
				parent : 'site',
				data : {
					roles : []
				},
				views : {
					'content@' : {
						templateUrl : 'views/invoice/invoicereports.html',
						controller : 'InvoiceReportsController'
					}
				}
			})
		}])
		.controller(
				'InvoiceReportsController',
				["$scope", "$state", "InvoiceService", "UserEPSProjectService", "PurchaseOrderService", "GenericAlertService", function($scope, $state, InvoiceService, UserEPSProjectService,
						PurchaseOrderService, GenericAlertService) {

					$scope.searchProject = {};
					$scope.purchaseOrdersList = [];

							$scope.getUserProjects = function() {
								var userProjectSelection = UserEPSProjectService
										.epsProjectPopup();
								userProjectSelection
										.then(
												function(userEPSProjData) {
													$scope.searchProject = userEPSProjData.selectedProject;
												},
												function(error) {
													GenericAlertService
															.alertMessage(
																	"Error occured while selecting EPS Project name",
																	'Error');
												});
							}, $scope.resetInvoiceData = function() {
								$scope.searchProject = {};
								$scope.purchaseOrdersList = [];
							},

					$scope.getInvoiceDetails = function(projId) {
								if(projId == undefined || projId == null){
									GenericAlertService
									.alertMessage(
											"Please Select Project to get Invoice Reports",
											'Warning');
								}
						var getInvoiceDtlReq = {
							"status" : 1,
							"projId" : projId
						};
						PurchaseOrderService
								.getPurchaseOrders(getInvoiceDtlReq)
								.then(
										function(data) {
											$scope.purchaseOrdersList = data.purchaseOrderTOs;
										},
										function(error) {
											GenericAlertService
													.alertMessage(
															"Error occured while getting Purchase Orders",
															'Error');
										});
					}
				}]);
