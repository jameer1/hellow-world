'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("purchaseorder", {
		url: '/purchaseorder',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/procurement/purchaseorders/purchaseorder.html',
				controller: 'PurchaseOrderController'
			}
		}
	})
}]).controller("PurchaseOrderController", ["$rootScope", "$scope", "$state", "ngDialog", "$q", "RFQService", "PurchaseDeliveryDetailsFactory", "PurchaseGenerateFactory", "PurchaseBidanalysisFactory", "POExternalFactory", "POReferenceDocumentsFactory", "PrecontractReqApprovalFactory", "PurchaseOrderService", "PurchaseToVendorDetailsFactory", "PurchaseFromVendorDetailsFactory", "POInternalFactory", "PurchaseRfqScheduleItemsEditFactor", "RfqSchItemsViewFactory", "GenericAlertService", "UserEPSProjectService", "EpsProjectMultiSelectFactory", "PurchaseOrderFactory", "PreContractInternalService", "PurchaseInternalFactory", "InvoiceDeatilsFactory", "PreContractExternalService", "PurchaseExternalDetailsFactory", "PurchaseOrderDetailsFactory", function ($rootScope, $scope, $state, ngDialog, $q, RFQService, PurchaseDeliveryDetailsFactory, PurchaseGenerateFactory, PurchaseBidanalysisFactory, POExternalFactory, POReferenceDocumentsFactory, PrecontractReqApprovalFactory, PurchaseOrderService, PurchaseToVendorDetailsFactory, PurchaseFromVendorDetailsFactory, POInternalFactory, PurchaseRfqScheduleItemsEditFactor, RfqSchItemsViewFactory, GenericAlertService, UserEPSProjectService, EpsProjectMultiSelectFactory, PurchaseOrderFactory, PreContractInternalService, PurchaseInternalFactory, InvoiceDeatilsFactory, PreContractExternalService, PurchaseExternalDetailsFactory, PurchaseOrderDetailsFactory) {
	var editPurchaseReq = [];
	$scope.companyMap = [];
	$scope.usersMap = [];
	$scope.purchaseOrdersList = [];
	$scope.selectedProject = {};
	$scope.contractStatus = {};
	$scope.searchProject = {};
	$scope.purchaseFlag = 0;
	var selectedPurchaseOrders = [];

	$rootScope.enabledTab = {
		manpower: false,
		materials: false,
		plants: false,
		services: false,
		subcontract: false
	};


	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
			$scope.invalidProjectSelection = data.searchProject.parentName ? false : true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getPurchaseOrderSearch = function (projIds,status) {
		$scope.selectedproj = projIds;
		$scope.selectedstatus = status;
		$scope.purchaseOrdersList = {};
		$scope.selectedRow = {};
		var purchaseReq = {
			"projIds": projIds,
			"status": status
		};

		if (projIds == undefined || projIds <= 0) {
			GenericAlertService.alertMessage("Please select project ", 'Warning');
			return;
		}
		PurchaseOrderService.getPurchaseOrders(purchaseReq).then(function (data) {
			$scope.companyMap = data.companyMap;
			$scope.usersMap = data.usersMap;
			$scope.userProjMap = data.userProjMap
			$scope.purchaseOrdersList = data.purchaseOrderTOs;
			if ($scope.purchaseOrdersList.length <= 0) {
				GenericAlertService.alertMessage("Purchase orders are not available for given search criteria", 'Warning');
			}

		},

			function (error) {
				GenericAlertService.alertMessage("Error occured while getting Purchase Orders", 'Error');

			});

	}, $scope.rowSelect = function (req) {
		if (req.select) {
			editPurchaseReq.push(req);
		} else {
			editPurchaseReq.pop(req);
		}

	}, $scope.resetPurchaseOrderData = function () {
		$scope.searchProject = {};
		$scope.purchaseOrdersList = {};
		$rootScope.selectedPurchaseOrderData = {};
		$rootScope.$broadcast($scope.currentTotalTabs.resetMethod);
		$rootScope.$broadcast($scope.currenScheduletTotalTabs.resetMethod);
		$rootScope.$broadcast($scope.currenDeliveryTotalTabs.resetMethod);
	}, $scope.viewPurchaseOrderDetails = function (searchProject, purchaseOrder) {
		var purchaseOrderDetails = $scope.getPurchaseOrderDetailsByCmpId(searchProject, purchaseOrder);
		purchaseOrderDetails.then(function (data) {
			var popupDetails = PurchaseOrderDetailsFactory.getPurchaseOrderDetails(data.preContractObj, purchaseOrder);
			popupDetails.then(function (data) {
				$scope.companyMap = data.companyMap;
				$scope.usersMap = data.usersMap;
				$scope.purchaseOrdersList = data.purchaseOrderTOs;
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
		});

	}, $scope.viewGeneratePurchase = function (searchProject, purchaseOrder) {
		var popupDetails = PurchaseGenerateFactory.getPurchaseOrderDetails(searchProject, purchaseOrder);
		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
		});
	}, function (error) {
		GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
	}, $scope.getPurchaseOrderDetailsByCmpId = function (searchProject, purchaseOrder) {
		var purchaseOrderDefer = $q.defer();
		var onLoadReq = {
			"preContractId": purchaseOrder.preContractCmpTO.preContractTO.id,
			"contractCmpId": purchaseOrder.preContractCmpTO.id,
			"projId": purchaseOrder.projId,
			"status": 1
		};
		PurchaseOrderService.getPurchaseOrderDetails(onLoadReq).then(function (data) {
			var returnPreContractObj = {
				"preContractObj": angular.copy(data)
			};
			purchaseOrderDefer.resolve(returnPreContractObj);
		});
		return purchaseOrderDefer.promise;

	},
		$scope.$on('poschedule', function (event, args) {

			$rootScope.enabledTab = args.message;

			if ($scope.tabs[0].scheduleOfItemsTabs)
				$scope.tabs[0].scheduleOfItemsTabs[0].enabled = $rootScope.enabledTab.manpower;
			$scope.tabs[0].scheduleOfItemsTabs[1].enabled = $rootScope.enabledTab.materials;
			$scope.tabs[0].scheduleOfItemsTabs[2].enabled = $rootScope.enabledTab.plants;
			$scope.tabs[0].scheduleOfItemsTabs[3].enabled = $rootScope.enabledTab.services;
			$scope.tabs[0].scheduleOfItemsTabs[4].enabled = $rootScope.enabledTab.subcontract;

			if ($scope.click != 'click') {
				if ($rootScope.enabledTab.manpower) {
					$scope.currentScheduleTab = $scope.tabs[0].scheduleOfItemsTabs[0].url;
				} else if ($rootScope.enabledTab.materials) {
					$scope.currentScheduleTab = $scope.tabs[0].scheduleOfItemsTabs[1].url;
				} else if ($rootScope.enabledTab.plants) {
					$scope.currentScheduleTab = $scope.tabs[0].scheduleOfItemsTabs[2].url;
				} else if ($rootScope.enabledTab.services) {
					$scope.currentScheduleTab = $scope.tabs[0].scheduleOfItemsTabs[3].url;
				} else if ($rootScope.enabledTab.subcontract) {
					$scope.currentScheduleTab = $scope.tabs[0].scheduleOfItemsTabs[4].url;
				}
			}



		});
	$scope.$on('delivery', function (event, args) {

		$rootScope.enabledTab = args.message;

		if ($scope.tabs[1].deliveryTabs) {
			$scope.tabs[1].deliveryTabs[0].enabled = $rootScope.enabledTab.manpower;
			$scope.tabs[1].deliveryTabs[1].enabled = $rootScope.enabledTab.materials;
			$scope.tabs[1].deliveryTabs[2].enabled = $rootScope.enabledTab.plants;
			$scope.tabs[1].deliveryTabs[3].enabled = $rootScope.enabledTab.services;
			$scope.tabs[1].deliveryTabs[4].enabled = $rootScope.enabledTab.subcontract;
		}

		if ($scope.click != 'click') {
			if ($rootScope.enabledTab.manpower) {
				$scope.currentDeliveryTab = $scope.tabs[1].deliveryTabs[0].url;
			} else if ($rootScope.enabledTab.materials) {
				$scope.currentDeliveryTab = $scope.tabs[1].deliveryTabs[1].url;
			} else if ($rootScope.enabledTab.plants) {
				$scope.currentDeliveryTab = $scope.tabs[1].deliveryTabs[2].url;
			} else if ($rootScope.enabledTab.services) {
				$scope.currentDeliveryTab = $scope.tabs[1].deliveryTabs[3].url;
			} else if ($rootScope.enabledTab.subcontract) {
				$scope.currentDeliveryTab = $scope.tabs[1].deliveryTabs[4].url;
			}
		}

	});


	$scope.click = '';
	$rootScope.selectedPurchaseOrderData = null;
	$scope.tabs = [{
		title: 'PO-Schedule Of Items',
		url: 'views/procurement/purchaseorders/purchaseregeneratedetails.html',
		resetMethod: 'resetScheduleOfItems',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_POSCHEDULE_VIEW',
		purchaseorderSeleniumLocator: 'PurchaseOrders_scheduleofitems',
		scheduleOfItemsTabs: [{
			title: 'Manpower',
			url: 'views/procurement/purchaseorders/poschmanpower.html',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_POSCHEDULEMANPOWER_VIEW',
			purchaseorderSeleniumLocator: 'PurchaseOrders_ManPowerScheduleOfItems',
			resetMethod: 'resetScheduleItems',
			enabled: $rootScope.enabledTab.manpower
		}, {
			title: 'Materials',
			url: 'views/procurement/purchaseorders/poschmaterial.html',
			purchaseorderSeleniumLocator: 'PurchaseOrders_MaterialScheduleOfItems',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_POSCHEDULEMATERIL_VIEW',
			resetMethod: 'resetScheduleItems',
			enabled: $rootScope.enabledTab.materials
		}, {
			title: 'Plants',
			url: 'views/procurement/purchaseorders/poschplants.html',
			purchaseorderSeleniumLocator: 'PurchaseOrders_PlantsScheduleOfItems',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_POSCHEDULEPLANT_VIEW',
			resetMethod: 'resetScheduleItems',
			enabled: $rootScope.enabledTab.plants
		}, {
			title: 'Services',
			url: 'views/procurement/purchaseorders/poschservices.html',
			purchaseorderSeleniumLocator: 'PurchaseOrders_ServicesScheduleOfItems',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_POSCHEDULESERVICE_VIEW',
			resetMethod: 'resetScheduleItems',
			enabled: $rootScope.enabledTab.services
		}, {
			title: 'Sub Contract',
			url: 'views/procurement/purchaseorders/poschsow.html',
			purchaseorderSeleniumLocator: 'PurchaseOrders_SubContractScheduleOfItems',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_POSCHEDULESUBCONTRACT_VIEW',
			resetMethod: 'resetScheduleItems',
			enabled: $rootScope.enabledTab.subcontract
		}]
	}, {
		title: 'Resource Delivery/Progress',
		url: 'views/procurement/purchaseorders/purchasedelivery.html',
		purchaseorderSeleniumLocator: 'PurchaseOrders_resourcedelivery',
		purchaseorderappCodeTemplateKey: 'PROCURMT_RESOURCE_DELIVERYPROGRESS_VIEW',
		deliveryTabs: [{
			title: 'Manpower',
			purchaseorderSeleniumLocator: 'PurchaseOrders_ManPowerResourceDelivery',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_RESOURDELMANPOWER_VIEW',
			url: 'views/procurement/purchaseorders/podeliverymanpower.html',
			resetMethod: 'resetDeliveryDetails',
			enabled: $rootScope.enabledTab.manpower
		}, {
			title: 'Materials',
			purchaseorderSeleniumLocator: 'PurchaseOrders_MaterailsResourceDelivery',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_RESOURDELMATERIL_VIEW',
			url: 'views/procurement/purchaseorders/podeliverymaterial.html',
			resetMethod: 'resetDeliveryDetails',
			enabled: $rootScope.enabledTab.materials
		}, {
			title: 'Plants',
			purchaseorderSeleniumLocator: 'PurchaseOrders_PlantsResourceDelivery',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_RESOURDELPLANT_VIEW',
			url: 'views/procurement/purchaseorders/podeliveryplants.html',
			resetMethod: 'resetDeliveryDetails',
			enabled: $rootScope.enabledTab.plants
		}, {
			title: 'Services',
			purchaseorderSeleniumLocator: 'PurchaseOrders_ServicesResourceDelivery',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_RESOURDELSERVICE_VIEW',
			url: 'views/procurement/purchaseorders/podeliveryservices.html',
			resetMethod: 'resetDeliveryDetails',
			enabled: $rootScope.enabledTab.services
		}, {
			title: 'Sub Contract',
			purchaseorderSeleniumLocator: 'PurchaseOrders_SubContractResourceDelivery',
			purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_RESOURDELSUBCONTRACT_VIEW',
			url: 'views/procurement/purchaseorders/podeliverysow.html',
			resetMethod: 'resetDeliveryDetails',
			enabled: $rootScope.enabledTab.subcontract
		}]

	}, {
		title: 'Invoice Status',
		url: 'views/procurement/purchaseorders/purchaseinvoicedetails.html',
		purchaseorderSeleniumLocator: 'PurchaseOrders_invoicestatus',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_INOVICESTATUS_VIEW',
		resetMethod: 'resetInvoiceStatus'
	}, {
		title: 'PreContract List&Documents',
		url: 'views/procurement/purchaseorders/purchaseprecontractdocuments.html',
		purchaseorderSeleniumLocator: 'PurchaseOrders_precontractslistandDocuments',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_PRECONTLISTDOCM_VIEW',
		resetMethod: 'resetPreContactList'
	},
	// Existing Code
	// {
	// 	title: 'Stage1 Request & Approval',
	// 	purchaseorderSeleniumLocator: 'PurchaseOrders_Stage1RequestApproval',
	// 	purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_STAGE1REQAPPR_VIEW',
	// 	url: 'views/procurement/purchaseorders/pointernal.html',
	// 	resetMethod: 'resetStage1request&appoval'
	// }
	// Stage 1 Request & Stage 1 Approval tabs separtation
	{
		title: 'Stage1 Request',
		purchaseorderSeleniumLocator: 'PurchaseOrders_Stage1RequestApproval',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_STAGE1REQAPPR_VIEW',
		url: 'views/procurement/purchaseorders/pointernal.html',
		resetMethod: 'resetStage1request&appoval'
	},
	{
		title: 'Stage1 Approval',
		purchaseorderSeleniumLocator: 'PurchaseOrders_Stage1Approval',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_STAGE1APPR_VIEW',
		url: 'views/procurement/purchaseorders/pointernalapproval.html',
		resetMethod: 'resetStage1appoval'
	}
	// end
	, {
		title: 'RFQ Tendering',
		purchaseorderSeleniumLocator: 'PurchaseOrders_RFQ_Tendering',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_RFQ_VIEW',
		url: 'views/procurement/purchaseorders/purchaseorderrfq.html',
		resetMethod: 'resetRFQ'
	},
	// Existing code
	// {
	// 	title: 'Bid Analysis & Stage2 Approval',
	// 	purchaseorderSeleniumLocator: 'PurchaseOrders_BidAnalysisStage2RequestApproval',
	// 	purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_BIDANALYSIS_VIEW',
	// 	url: 'views/procurement/purchaseorders/poexternal.html',
	// 	resetMethod: 'resetBidAnalysis'
	// }
	// Stage 2 Request & Stage 2 Approval tabs separtation
	{
		title: 'Stage2 Request',
		purchaseorderSeleniumLocator: 'PurchaseOrders_stage2requesttab',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_BIDANALYSIS_VIEW',
		url: 'views/procurement/purchaseorders/poexternal.html',
		resetMethod: 'resetBidAnalysis'
	},
	{
		title: 'Stage2 Approval',
		purchaseorderSeleniumLocator: 'PurchaseOrders_stage2approvaltab',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_STAGE2APPROVAL_VIEW',
		url: 'views/procurement/purchaseorders/poexternalapproval.html',
		resetMethod: 'resetBidAnalysis'
	}
	// end
	// Repeat PO tab
	,
	{
		title: 'Repeat PO',
		purchaseorderSeleniumLocator: 'PurchaseOrders_RepeatPOtab',
		purchaseorderappCodeTemplateKey: 'PROCURMT_PURCHASEORDR_REPEAT_PO_VIEW',
		url: 'views/procurement/purchaseorders/repeatpolist.html',
	}
	// end
	];
	$scope.setSelected = function (row) {
		$scope.selectedRow = row;
	}

	//$scope.currentDeliveryTab = 'views/procurement/purchaseorders/podeliverymanpower.html';
	$scope.currentDeliveryTab = $scope.tabs[1].deliveryTabs[0].url;
	$scope.currentExternalTab = 'views/procurement/purchaseorders/purchaseexternalmanpower.html';
	$scope.currentRfqTab = 'views/procurement/purchaseorders/purchaserfqviewmanpower.html';

	//$scope.currentScheduleTab = 'views/procurement/purchaseorders/poschmanpower.html';
	$scope.currentScheduleTab = $scope.tabs[0].scheduleOfItemsTabs[0].url;
	$scope.onClickScheduleTab = function (scheduleOfItemsTab, click) {
		$scope.click = click;
		$scope.currenScheduletTotalTabs = scheduleOfItemsTab;
		$scope.currentScheduleTab = scheduleOfItemsTab.url;
	}, $scope.isActiveScheduleTab = function (scheduleOfItemsTab) {
		return scheduleOfItemsTab == $scope.currentScheduleTab;
	},
		$scope.getApprovelDetails = function () {
			var req = {
				"contractId": purchaseOrder.preContractCmpTO.preContractTO.id,
				"status": 1
			}
			var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
			})
		},
		$scope.onClickDeliveryTab = function (deliveryTabs, click) {
			$scope.click = click;
			$scope.currenDeliveryTotalTabs = deliveryTabs;
			$scope.currentDeliveryTab = deliveryTabs.url;
		}, $scope.isActiveDeliveryTab = function (deliveryTabs) {
			return deliveryTabs == $scope.currentDeliveryTab;
		}, $scope.onClickExternalTab = function (externalScheduleTabs) {
			$scope.currentExternalTab = externalScheduleTabs.url;
		}, $scope.isActiveExternalTab = function (externalScheduleTabs) {
			return externalScheduleTabs == $scope.currentExternalTab;
		}, $scope.onClickRfqTab = function (rfqSubTabs) {
			$scope.currentRfqTab = rfqSubTabs.url;
		}, $scope.isActiveRfqTab = function (rfqSubTabs) {
			return rfqSubTabs == $scope.currentExternalTab;
		}
	$scope.go = function (purchaseOrder, indexValue) {
		$rootScope.selectedPurchaseOrderData = purchaseOrder;
		$scope.setSelected(indexValue);
		$scope.onClickTab($scope.tabs[0]);
		$scope.onClickScheduleTab($scope.tabs[0].scheduleOfItemsTabs[0], '');
		$scope.onClickDeliveryTab($scope.tabs[1].deliveryTabs[0], '');
		$rootScope.$broadcast('defaultPurchaseOrderDetailsTab');
	};

	$scope.currentTab = $scope.tabs[0].url;
	$scope.onClickTab = function (tab) {
		$scope.currentTotalTabs = tab;
		$scope.currentTab = tab.url;
	}, $scope.isActiveTab = function (tabUrl) {
		return tabUrl == $scope.currentTab;
	},

		$scope.viewScheduleItemsDetails = function (preContractCompanyTO) {
			var resultData = $scope.getInternalDetailsById(preContractCompanyTO.preContractTO);
			resultData.then(function (data) {
				var popupDetails = RfqSchItemsViewFactory.viewScheduleItemsDetails(data.preContractObj);
				popupDetails.then(function (data) {
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while getting Schedule Item Details", 'Info');
				})
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting Schedule Item Details", 'Info');
			})

		}, $scope.getInternalDetailsById = function (preContractObj) {
			var internalDefer = $q.defer();
			var onLoadReq = {
				"projId": preContractObj.projId,
				"contractId": preContractObj.id,
				"status": 1
			};
			PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
				var returnObj = {
					"preContractObj": angular.copy(data)
				};
				internalDefer.resolve(returnObj);
			});
			return internalDefer.promise;

		}, $scope.editScheduleItemsDetails = function (preContractCmpTO) {
			var resultData = $scope.getPrecontractSchItems(preContractCmpTO);
			resultData.then(function (data) {
				var popupDetails = PurchaseRfqScheduleItemsEditFactor.editPrecontractSchItems(data.preContractObj, preContractCmpTO, $scope.rfq);
				popupDetails.then(function (data) {
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
				})
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, $scope.getPrecontractSchItems = function (preContractCmpTO) {
			var externalDefer = $q.defer();
			var onLoadReq = {
				"projId": preContractCmpTO.preContractTO.projId,
				"contractId": preContractCmpTO.preContractTO.id,
				"preContractCmpId": preContractCmpTO.id,
				"status": 1
			};
			RFQService.getPreContractCmpQuoteDetails(onLoadReq).then(function (data) {
				var returnObj = {
					"preContractObj": angular.copy(data)
				};
				externalDefer.resolve(returnObj);
			});
			return externalDefer.promise;
		}

	$scope.getAssetsResourceDetails = function (purchaseOrder) {
		var popupInvoice = AssetResourceDeatilsFactory.resourceDetails(purchaseOrder);
		popupInvoice.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting resource details", 'Info');
		})
	}, $scope.getApprovelDetails = function (preContractObj) {
		var req = {
			"contractId": preContractObj.id,
			"status": 1
		};
		var popupDetails = PrecontractReqApprovalFactory.getReqApprDetails(req);
		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting request approvals", 'Info');
		})
	}, $scope.getRefDocument = function (precontract) {
		var refreq = POReferenceDocumentsFactory.referenceDocumentDetails(precontract);
		refreq.then(function (data) {
			$scope.refdocumentslist = data.preContractDocsTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while selecting Details", 'Info');
		})

	}, $scope.viewInternalRequestById = function (preContractObj) {
		var resultData = $scope.getInternalDetailsById(preContractObj);
		resultData.then(function (data) {
			var popupDetails = POInternalFactory.procInternalApprovalPopUp(data.preContractObj);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
		})

	}, $scope.getInternalDetailsById = function (preContractObj) {
		var internalDefer = $q.defer();
		var onLoadReq = {
			"projId": preContractObj.projId,
			"contractId": preContractObj.id,
			"status": 1
		};
		PreContractInternalService.getInternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
			var returnObj = {
				"preContractObj": angular.copy(data)
			};
			internalDefer.resolve(returnObj);
		});
		return internalDefer.promise;

	}, $scope.addExternalRequest = function (preContractObj) {
		var resultData = $scope.getExternalPrecontractDetails(preContractObj);
		resultData.then(function (data) {
			var popupDetails = POExternalFactory.procExternalApprovalPopUp(data.preContractObj);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting   precontract details", 'Error');
		})
	},

		$scope.getExternalPrecontractDetails = function (preContractObj) {
			var externalDefer = $q.defer();
			var onLoadReq = {
				"projId": preContractObj.projId,
				"contractId": preContractObj.id,
				"status": 1
			};
			PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
				var returnObj = {
					"preContractObj": angular.copy(data)
				};
				externalDefer.resolve(returnObj);
			});
			return externalDefer.promise;
		}, $scope.getBidAnalysisDetails = function (preContractId) {
			var bidDetails = $scope.getCompanyBidDetails(preContractId);
			bidDetails.then(function (data) {
				var popupDetails = PurchaseBidanalysisFactory.getCompanyBidDetails(data.preContractObj);
				popupDetails.then(function (data) {
				}, function (error) {
					GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
				})
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting requeest approvals", 'Info');
			})

		}, $scope.getCompanyBidDetails = function (preContractObj) {
			var onLoadReq = {
				"projId": preContractObj.projId,
				"contractId": preContractObj.id,
				"status": 1

			};

			var bidAnalysisDefer = $q.defer();
			PreContractExternalService.getExternalPreContractPopupOnLoad(onLoadReq).then(function (data) {
				var returnPreContractObj = {
					"preContractObj": angular.copy(data)
				};
				bidAnalysisDefer.resolve(returnPreContractObj);
			});
			return bidAnalysisDefer.promise;

		}, $scope.getToVendorDetails = function (vendor) {
			var popupDetails = PurchaseToVendorDetailsFactory.getToVendorDocuments(vendor, $scope.companyMap, $scope.usersMap);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting Correspondence To Vendor Details", 'Info');
			})
		}, $scope.getFromVendorDetails = function (preContractCmpTO) {
			var popupDetails = PurchaseFromVendorDetailsFactory.getFromVendorDetails(preContractCmpTO, $scope.companyMap, $scope.usersMap);
			popupDetails.then(function (data) {
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting Correspondence From Vendor Details", 'Info');
			})
		}, $scope.selectPurchaseOrders = function (selectedPurchaseOrder) {
			selectedPurchaseOrders.push(selectedPurchaseOrder);
			selectedPurchaseOrder.editMode = true;
		},
		$scope.selectedorder = function(selectedPurchaseOrder){
		selectedPurchaseOrders.push(selectedPurchaseOrder);
			//selectedPurchaseOrder.editMode = true;
			$scope.selectedProjId = selectedPurchaseOrder.projId;
			$scope.selectedStatus = selectedPurchaseOrder.status;
			//selectedPurchaseOrder.editMode = true;
		}
		$scope.savePurchaseOrders = function (searchProject,selectedPurchaseOrder) {
		
			var req = {
				"purchaseOrderTOs": selectedPurchaseOrders,
				"projId": $scope.searchProject.projId,
				"status": $scope.selectedStatus
			};
			if ($scope.searchProject.projIds == undefined || $scope.searchProject.projIds <= 0) {
				GenericAlertService.alertMessage("Please select project ", 'Warning');
				return;
			}
			PurchaseOrderService.savePurchaseOrders(req).then(function (data) {
				//$scope.purchaseOrdersList = data.purchaseOrderTOs;
				//selectedPurchaseOrders = [];
				$scope.getPurchaseOrderSearch($scope.selectedproj,$scope.selectedstatus);
				GenericAlertService.alertMessage("Purchase order details saved successfully", "Info");
			},

				function (error) {
					GenericAlertService.alertMessage("Purchase Order(s) is/are Failed To save", "Error");
				});
	$scope.getPurchaseOrderSearch;
		}
		var HelpService = {};
	$scope.helpPage = function () {
		var help = HelpService.pageHelp();
		help.then(function(data) {

		}, function(error) {
			GenericAlertService.alertMessage("Error",'Info');
		})
	}
	var helppagepopup;
	HelpService.pageHelp = function () {
		var deferred = $q.defer();
		helppagepopup = ngDialog.open({
			template: 'views/help&tutorials/procurementhelp/purchaseorderhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {

			}]
		});
		return deferred.promise;
	}
}]);
