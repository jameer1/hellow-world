'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("vendorinvoices", {
        url: '/vendorinvoices',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/finance/financemain/costpayables/vendorinvoices/invoicefromvendor.html',
                controller: 'VendorInvoicesController'
            }
        }
    })
}]).controller('VendorInvoicesController', ["$rootScope", "$scope", "$state", "$q", "EpsProjectSelectFactory",
    "GenericAlertService", "ProjectScheduleService",
    "SchedulePlannedValueService", "ScheduleResourceCurvesFactory",
    "ResourceCurveService", "CreateBaseLineFactory", "AssignBaseLineFactory",
    "stylesService", "ngGridService", "ngDialog", "PostAnInvoiceFactory", "ApproveAnInvoiceFactory", "ReleasePaymentDetailsFactory","EpsProjectMultiSelectFactory","PurchaseOrderService","PreContractExternalService", "PostInvoiceTrackingRecordsFactory","ProjectInvoiceService",
    function ($rootScope, $scope, $state, $q, EpsProjectSelectFactory,
        GenericAlertService, ProjectScheduleService, SchedulePlannedValueService,
        ScheduleResourceCurvesFactory, ResourceCurveService,
        CreateBaseLineFactory, AssignBaseLineFactory, stylesService, ngGridService, ngDialog, PostAnInvoiceFactory, ApproveAnInvoiceFactory, ReleasePaymentDetailsFactory,EpsProjectMultiSelectFactory,PurchaseOrderService,PreContractExternalService, PostInvoiceTrackingRecordsFactory, ProjectInvoiceService) {

          $scope.companyMap = [];
        	$scope.usersMap = [];
        	$scope.purchaseOrdersList = [];
        	$scope.selectedProject = {};
        	$scope.contractStatus = {};
        	$scope.searchProject = {};
          $scope.userProjMap = [];
          $scope.searchProject = {};
          $scope.invalidProjectSelection = true;
          
          $scope.invoiceDate;
          $scope.invoiceReceiveddate;
          $scope.paymentDuedate;
          $scope.purchaseOrderId;
          $scope.invoiceNumber;
          $scope.invoiceFrom;
          $scope.invoiceTo;
          $scope.proposedPaymentDate;
          $scope.paymentProcessingPeriod;
          $scope.purchaseOrderId;
          
          $scope.totalCostCodesAmount = 0;
          $scope.totalAmount = 0;
          
          $scope.invoiceCurStatus;
          
          $scope.selectedRow;
          
          $scope.invoiceparticularsTO;
          $scope.netAmountPayable;
          $scope.approverValidateDetailsTO;
          
          $scope.projMaterialClassMap = [];

        $scope.getUserProjects = function () {
          var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
          userProjectSelection.then(function (data) {
        	  console.log('******   userProjectSelection ********************** ',data);
            $scope.searchProject = data.searchProject;
            $scope.invalidProjectSelection = data.searchProject.parentName ? false : true;
          }, function (error) {
            GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
          });
        /*    $scope.selectedTimeScale = $scope.timeScales[0];
            $scope.selectedSchType = 'Planned';
            var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
            userProjectSelection.then(function (data) {
                $scope.searchProject = data.searchProject;
            }, function (error) {
                GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
            });*/
        };
        $scope.getPurchaseOrderSearch = function (projIds,status) {
          //alert(' projIds '+ projIds)
        	console.log('getPurchaseOrderSearch     ==>   ');
      		$scope.purchaseOrdersList = {};
      		$scope.selectedRow = {};
          var PoStatus=$scope.fromDate
      		var purchaseReq = {
      			"projIds": projIds,
      			"status": status
      		};

      		if (projIds == undefined || projIds <= 0) {
      			GenericAlertService.alertMessage("Please select project ", 'Warning');
      			return;
      		}
      		PurchaseOrderService.getPurchaseOrders(purchaseReq).then(function (data) {
      			console.log('getPurchaseOrderSearch  2323   ==>   ', data);
      			$scope.companyMap = data.companyMap;
      			$scope.usersMap = data.usersMap;
      			$scope.userProjMap = data.userProjMap
      			$scope.purchaseOrdersList = data.purchaseOrderTOs;
      			if ($scope.purchaseOrdersList.length <= 0) {
      				GenericAlertService.alertMessage("Purchase orders are not available for given search criteria", 'Warning');
      			}
            //console.log('try ===>>   '+ $scope.purchaseOrdersList);
            console.log('userProjMap  ', $scope.userProjMap)
      		},

      			function (error) {
      				GenericAlertService.alertMessage("Error occured while getting Purchase Orders", 'Error');

      			});

      	}


        $scope.go = function (purchaseOrdersList, indexValue, purchaseOrder) {
      		$rootScope.selectedPurchaseOrderData = purchaseOrder;
      		console.log('indexValue   ', indexValue);
      		console.log('purchaseOrder   ', purchaseOrder);
      		console.log('purchaseOrdersList   ', purchaseOrdersList);
      		$scope.setSelected(indexValue);
      		$scope.onClickTab($scope.tabs[0]);
      		console.log('$scope.tabs[0]   ', $scope.tabs[0]);
      		$rootScope.$broadcast('defaultPurchaseOrderDetailsTab');
      		var preContractId = purchaseOrder.preContractCmpTO.preContractTO.id;
      		console.log('preContractId   ', preContractId);
      		var projId = purchaseOrder.projId;
      		var req = {
          			"preContractId": preContractId,
          			"projectId": projId
          		};
      		PurchaseOrderService.getVendorPostInvoice(req).then(function (data) {
      			console.log('data ', data);
      			$scope.invoiceparticularsTO = data.invoiceparticularsTO;
      			$scope.netAmountPayable = data.netAmountPayable;
      			$scope.approverValidateDetailsTO = data.approverValidateDetailsTO;
      			/*$scope.postAnInvoice = {
      					'invoiceDate': $scope.invoiceparticularsTO.invocieDate
      			},*/
      		});
      	};
        $scope.setSelected = function (row) {
      		$scope.selectedRow = row;
      	}

        $scope.tabs = [{
            title: 'Post An Invoice',
            url: 'views/finance/financemain/costpayables/vendorinvoices/postinvoice.html'
        }, {
            title: 'Approve Invoice',
            url: 'views/finance/financemain/costpayables/vendorinvoices/approveinvoice.html'
        }, {
            title: 'Release Payment',
            url: 'views/finance/financemain/costpayables/vendorinvoices/releasepayment.html'
        }, {
            title: 'Invoice Wise Payment Status',
            url: 'views/finance/financemain/costpayables/vendorinvoices/invoicewisepaymentstatus.html'
        }, {
            title: 'PO Wise Payment Status',
            url: 'views/finance/financemain/costpayables/vendorinvoices/powisepaymentstatus.html'
        }];
               
       

        $scope.currentTab = $scope.tabs[0].url;

        $scope.onClickTab = function (tab) {
            $scope.currentTab = tab.url;
        }
        $scope.isActiveTab = function (tabUrl) {
            return tabUrl == $scope.currentTab;
        }
        
      

        $scope.addPostAnInvoiceDetails = function (actionType) {
            if (actionType == 'Edit') {
                GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
                return;
            }
           console.log('$rootScope.selectedData9990  ');

        
            var req = {
                    "contractId" : 0,
                    "projId" : 0,
                    "preContractCmpId" : 0,
                    "status" : 1
                  };
              
         if($rootScope.selectedPurchaseOrderData && $rootScope.selectedPurchaseOrderData.preContractCmpTO) {
        	 req.contractId = $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id;
        	 req.preContractCmpId = $rootScope.selectedPurchaseOrderData.preContractCmpTO.id;
         }
         
         if($rootScope.selectedPurchaseOrderData) {
        	 req.projId = $rootScope.selectedPurchaseOrderData.projId;
         }
         
         if($rootScope.selectedPurchaseOrderData) {
        	 req.projId = $rootScope.selectedPurchaseOrderData.projId;
         }
          
         console.log(req);
            PreContractExternalService.getExternalPreContractPopupOnLoad(req).then(function(data) {
              console.log('data   888 ');
             console.log(data);
              $scope.userProjMap = data.userProjMap;
			        $scope.preContractTOs = data.preContractTOs;
			        $scope.repeatPOPreContractTOs = data.repeatPOPreContractTO;
			        $scope.usersMap = data.usersMap;
			        $scope.projMaterialClassMap = data.projMaterialClassMap;
			        $scope.preContractTO = data.preContractTO;
			  
						       
			  console.log('$rootScope.preContractTO    ');
              var addPostAnInvoiceDetailsPopUp = PostAnInvoiceFactory.generateInvoiceDetails(actionType,data,$scope.userProjMap, $rootScope.selectedPurchaseOrderData);
              addPostAnInvoiceDetailsPopUp.then(function (data) {
            	  console.log('$rootScope.preContractTO  data  ==>  ',data);
             }, function (error) {
            	 console.log('error',error);
                  GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
              });
            }, function(error) {
            	console.log('error12222222222222',error);
        		GenericAlertService.alertMessage("Error occurred while getting RFQ details", 'Error');
        	})


        }

        $scope.editApproveInvoiceDetails = function (actionType) {
            var editApproveInvoiceDetailsPopUp = ApproveAnInvoiceFactory.generateApproveInvoiceDetails(actionType);
            editApproveInvoiceDetailsPopUp.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
            });
        }

        $scope.addReleasePaymentDetails = function (actionType) {
            var editReleasePaymentDetailsPopUp = ReleasePaymentDetailsFactory.generateReleasePaymentDetails(actionType);
            editReleasePaymentDetailsPopUp.then(function (data) {

            }, function (error) {
                GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
            });
        }
        
      $scope.invoiceRowSelect = function (position, purchaseOrderDTOs) {
    	    	  
    		angular.forEach(purchaseOrderDTOs, function (purchaseOrder, index) {
    			if (position != index) {
    				purchaseOrder.selected = false;
    				return;
    			}
    			/*editPlantData.splice(editPlantData.indexOf(plant), 1);
    			if (plant.selected) {
    				if ($scope.selectedRow) {
    					$scope.selectedRow = null;
    					$rootScope.$broadcast($scope.currentTab.resetMethod);
    				}
    				$scope.selectedRow = null;
    				editPlantData.push(plant);
    				$rootScope.$broadcast($scope.currentTab.resetMethod);
    			} else {
    				editPlantData.splice(editPlantData.indexOf(plant), 1);
    				$rootScope.$broadcast($scope.currentTab.resetMethod);
    			} */
    		}); 
    	  
      }
      
      $scope.selectPurchaseOrders = function (purchaseOrder, indexValue, plant) {
    	  
      }
      
      $scope.openResourceDeliveryDockets =  function(selectedData) {
    	  console.log('selectedData  ', selectedData);
    	  
      }
      
      $scope.invoiceTrackingRecords =  function() {
    	  console.log('  invoiceTrackingRecords  ');
    	  var postInvoiceTrackingRecords = PostInvoiceTrackingRecordsFactory.generatePostInvoiceTrackingRecords('open','','');
    	  postInvoiceTrackingRecords.then(function (data) {

          }, function (error) {
              GenericAlertService.alertMessage("Error occurred while fetching details", 'Error');
          });
      }
      
  }]);
