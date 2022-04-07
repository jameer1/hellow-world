'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("repeatpo", {
        url: '/repeatpo',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/procurement/purchaseorders/repeatpolist.html',
            }
        }
    })
}]).controller("RepeatPOController", ["ngDialog", "$scope", "$q", "$filter", "$timeout", "$rootScope", "PurchaseOrderService", "blockUI", "GenericAlertService", "GeneratePurchaseOrderFactory","PreContractExternalService",
"PurchaseOrderDetailsFactory","RepeatPurchaseOrderService","PurchaseSinglePOFactory","stylesService", "ngGridService", function (ngDialog, $scope, $q, $filter, $timeout, $rootScope, PurchaseOrderService, blockUI, GenericAlertService, GeneratePurchaseOrderFactory,PreContractExternalService, PurchaseOrderDetailsFactory,RepeatPurchaseOrderService,PurchaseSinglePOFactory, stylesService, ngGridService) {
          var editPurchaseReq = [];
          $scope.companyMap = [];
          $scope.usersMap = [];
          $scope.purchaseOrdersList = [];
          $scope.selectedProject = {};
          $scope.contractStatus = {};
          $scope.searchProject = {};
          $scope.purchaseFlag = 0;
          $scope.stylesSvc = stylesService;
          var selectedPurchaseOrders = [];
          $scope.repeatPOStatus = "Not Yet Initiated";
          //$scope.disableInitiateBtn = false;
          //$scope.disableGeneratePOBtn = false;
          $scope.userProjMap = [];
          $scope.userProjMap2 =[];
          //$scope.disableInitiateBtn = false;
          //$scope.disableGeneratePOBtn = false;
          $scope.isRepeatPOInitiated = false;
          $scope.isRepeatPOApproved = false;
          $scope.isRepeatPOCreated = false;

          //$rootScope.selectedData  = [];
//------------ Vijay Started --------------------------
      // for Generate PO
        $scope.generateRpeatPO = function () {

              //console.log('Repeat Controller : $scope.generateSinglePO')

              //console.log('$scope.selectedData');
              //console.log($scope.selectedData);

              //console.log('$scope.userProjMap');
              //console.log($scope.userProjMap);
              //alert('Selected generateSinglePO');

          if ($rootScope.selectedData == null) {
            GenericAlertService.alertMessage("Vzy3 Please select pre-contract id", 'Warning');
            return;
          }
          var popupDetails = PurchaseSinglePOFactory.getPurchaseOrderDetails($scope.userProjMap2, $rootScope.selectedData,
              "RepeatPO",$rootScope.clientId,$rootScope.selectedPurchaseOrderData.id,$rootScope.selectedPurchaseOrderData.projId,
              $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id);
          popupDetails.then(function (data) {
          }, function (error) {
            GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
          });
        };

  $scope.getRepeatPurchaseOrders = function() {
      //console.log('RepeatPOController Started : getRepeatPurchaseOrders');
      //alert('at UI Controller');
      /*
          "purchaseOrderId" :
          "projId" :
          "repeatPurchaseOrderId" :
          "contractItemType" :
          "contractId" :
          "contractCmpId" :
          "contractItemDetailId" :
          "contractItemId" :
        */
      var req = {
            "clientId" : $rootScope.clientId,
      			"purchaseOrderId" : $rootScope.selectedPurchaseOrderData.id,
      			"projId" : $rootScope.selectedPurchaseOrderData.projId, // 263
      			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id, // 543
      			"status" : 1
      		};

      RepeatPurchaseOrderService.getRepeatPurchaseOrders(req).then(function(data) {
      		  //console.log("RepeatPurchaseOrderService.getRepeatPurchaseOrders req:"+JSON.stringify(req));

      		  //console.log("Return data:"+JSON.stringify(data));
            //alert('You got result Vijay');

      		  //console.log("req:"+JSON.stringify(req));
            //console.log("data:"+JSON.stringify(data));
            //console.log("data.repeatPOPreContractTO:"+JSON.stringify(data.repeatPOPreContractTO));

            //console.log("req:"+JSON.stringify(req));
            //console.log("data:"+JSON.stringify(data));
            //console.log("data.repeatPOPreContractTO:"+JSON.stringify(data.repeatPOPreContractTO));
      			$scope.preContractTOs = [data.preContractTO];
      			$scope.gridOptions.data = angular.copy($scope.preContractTOs);

            //$scope.preContractTOs = [data.preContractTO];
            $scope.userProjMap2 = data.userProjMap;
 
            //console.log("*** userProjMap2 : "+JSON.stringify($scope.userProjMap2));
      			//$scope.isRepeatPOInitiated = [data.preContractTO.repeatPOInitiated];
      			//$scope.isRepeatPOApproved = [data.preContractTO.repeatPOApproved];


            $scope.isRepeatPOInitiated = [data.preContractTO.repeatPOInitiated];
            $scope.isRepeatPOApproved = [data.preContractTO.repeatPOApproved];
            $scope.isRepeatPOCreated = [data.preContractTO.repeatPOCreated];


            if($scope.isRepeatPOInitiated == 'true' && $scope.isRepeatPOApproved == 'true' && $scope.isRepeatPOCreated == 'true')
            {
              $scope.repeatPOStatus = "Not Yet Initiated"; // Not Yet Initiated
            } else if($scope.isRepeatPOInitiated == 'true' && $scope.isRepeatPOApproved == 'false' && $scope.isRepeatPOCreated == 'false')
            {
               $scope.repeatPOStatus = "In-Progress";
            } else if($scope.isRepeatPOInitiated == 'true' && $scope.isRepeatPOApproved == 'true' && $scope.isRepeatPOCreated == 'false')
            {
               $scope.repeatPOStatus = "Approved";
            } else if($scope.isRepeatPOInitiated != 'true')
            {
              $scope.repeatPOStatus = "Not Yet Initiated";
            }else if($scope.isRepeatPOInitiated == 'true' )
            {
              $scope.repeatPOStatus = "In-Progress";
            } else if($scope.isRepeatPOInitiated== 'true' && $scope.isRepeatPOApproved== 'true')
            {
              $scope.repeatPOStatus = "Approved";
            }else
            if($scope.isRepeatPOInitiated!= 'true' && $scope.isRepeatPOInitiated!= 'true' && $scope.isRepeatPOInitiated!= 'true')
            {
              $scope.repeatPOStatus = "Not Yet Initiated"; // Not Yet Initiated
            }

            //alert(" isRepeatPOInitiated :"+$scope.isRepeatPOInitiated);
            //alert(" isRepeatPOApproved :"+$scope.isRepeatPOApproved);
            //alert(" isRepeatPOCreated :"+$scope.isRepeatPOCreated);
            //alert(" repeatPOStatus :"+$scope.repeatPOStatus);

           /*

           if($scope.isRepeatPOInitiated && $scope.isRepeatPOApproved && $scope.isRepeatPOCreated)
                       {
                         $scope.repeatPOStatus = "PO Generated"; // Not Yet Initiated
                       } else

            var isRepeatPOInitiated = [data.preContractTO.repeatPOInitiated];
            var isRepeatPOApproved = [data.preContractTO.repeatPOApproved];
            $scope.isRepeatPOCreated = [data.preContractTO.repeatPOCreated];

            $scope.isRepeatPOInitiated = [data.preContractTO.repeatPOInitiated];
            $scope.isRepeatPOApproved = [data.preContractTO.repeatPOApproved];

           if(isRepeatPOInitiated=='false' && isRepeatPOApproved=='false')
            {
              //alert("false false");
              $scope.repeatPOStatus = "Not Yet Initiated";
              //$scope.disableInitiateBtn = false;
              //$scope.disableGeneratePOBtn = true;
            }else if (isRepeatPOInitiated=='true' && isRepeatPOApproved=='false')
            {
               //alert("true false");
               $scope.repeatPOStatus = "In-Progress";
               //$scope.disableInitiateBtn = true;
               //$scope.disableGeneratePOBtn = true;
            }else if (isRepeatPOInitiated == 'true' && isRepeatPOApproved == 'true')
            {
               //alert("true true");
               $scope.repeatPOStatus = "Approved";
               //$scope.disableInitiateBtn = true;
               //$scope.disableGeneratePOBtn = false;
            }else if (isRepeatPOCreated=='true')
            {
                $scope.repeatPOStatus = "PO Created";
            }else{
               //alert("final Else");
               $scope.repeatPOStatus = "Not Yet Initiated";
               //$scope.disableInitiateBtn = false;
              //$scope.disableGeneratePOBtn = true;
            }

            if($scope.repeatPOStatus != "Not Yet Initiated")
            {
              $scope.disableInitiateBtn = true;
            }

            if($scope.repeatPOStatus != "Approved")
            {
              $scope.disableGeneratePOBtn = true;
            }

           alert(" disableInitiateBtn :"+$scope.disableInitiateBtn);
           alert(" disableGeneratePOBtn :"+$scope.disableGeneratePOBtn);
           alert(" isRepeatPOCreated :"+$scope.isRepeatPOCreated);
            */

            /*
            if(Boolean([data.preContractTO.isRepeatPOInitiated]))
            {

            } else if(Boolean([data.preContractTO.repeatPOApproved]))
            {
              $scope.repeatPOStatus = "Approved";
            }else if(Boolean([data.preContractTO.isRepeatPOInitiated])==false && Boolean([data.preContractTO.repeatPOApproved])==false)
           {
             $scope.repeatPOStatus = "Not Yet Initiated";
             $scope.enableInitiateBtn = true;
           }else{
              $scope.repeatPOStatus = "Not Yet Initiated";
              $scope.enableInitiateBtn = true;
            }
            //alert("$scope.repeatPOStatus :"+$scope.repeatPOStatus);
            */
      			//----
      			//$scope.isRepeatPOInitiated2 = [data.repeatPOApproved];
            //$scope.isRepeatPOApproved2 = [data.repeatPOInitiated];

            //alert("$scope.isRepeatPOInitiated2 :"+$scope.isRepeatPOInitiated2);
            //alert("$scope.isRepeatPOApproved2 :"+$scope.isRepeatPOApproved2);


      			//alert("data isRepeatPOApproved :"+JSON.stringify(data.repeatPOApproved));
      			//alert("data isRepeatPOInitiated :"+JSON.stringify(data.repeatPOInitiated));

      			//alert("isRepeatPOInitiated:"+JSON.stringify($scope.preContractTOs.repeatPOInitiated));
      			//alert("isRepeatPOApproved:"+JSON.stringify($scope.preContractTOs.repeatPOApproved));
      			//$scope.repeatPOPreContractTOs = [data.repeatPOPreContractTO];
      			//console.log("repeatPOPreContractTOs:"+JSON.stringify($scope.repeatPOPreContractTOs));
            //alert($scope.repeatPOPreContractTOs);
      			$scope.projEmpClassMap = data.projEmpClassMap;
      			$scope.projPlantMap = data.projPlantMap;
      			$scope.projMaterialClassMap = data.projMaterialClassMap;
      			$scope.projServiceMap = data.projServiceMap;
      			$scope.projStoreMap = data.projStoreMap;
      			$scope.storeMap = data.storeMap;
      			$scope.projCostItemMap = data.projCostItemMap;
      			$scope.userMap = data.usersMap;

      			$scope.companyMap = data.companyMap;
      			$scope.projSOWMap = data.projSOWMap;
      			$scope.procureCategoryMap = data.procureCategoryMap;
      			$scope.expandCollapseManpower = function(manpowerFlag) {
      				$scope.manpowerFlag = !manpowerFlag;
      			}, $scope.expandCollapsePlant = function(plantFlag) {
      				$scope.plantFlag = !plantFlag;
      			}, $scope.expandCollapseMaterial = function(materialFlag) {
      				$scope.materialFlag = !materialFlag;
      			}, $scope.expandCollapseService = function(serviceFlag) {
      				$scope.serviceFlag = !serviceFlag;
      			}, $scope.expandCollapseSow = function(sowFlag) {
      				$scope.sowFlag = !sowFlag;
      			}

      		}, function(error) {
      			GenericAlertService.alertMessage("Error occurred while getting Repeat Purchase Orders", 'Error');
      		}),

      			$scope.generateRepeatPO = function () {
          		if ($scope.preContractTOs == null) {
          			GenericAlertService.alertMessage("Vzy4 Please select pre-contract id", 'Warning');
          			return;
          		}
          		var popupDetails = PurchaseSinglePOFactory.getPurchaseOrderDetails($scope.userProjMap2, $scope.preContractTOs,
          		  "RepeatPO",$rootScope.clientId,$rootScope.selectedPurchaseOrderData.id,$rootScope.selectedPurchaseOrderData.projId,
          		  $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id);
          		popupDetails.then(function (data) {
          		}, function (error) {
          			GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
          		});
          	};

  }
	$scope.getExternalPreContractDetails = function() {
	  //console.log('RepeatPOController Started : getExternalPreContractDetails');
	  //alert("selectedPurchaseOrderData:"+JSON.stringify($rootScope.selectedPurchaseOrderData));
	  //console.log("selectedPurchaseOrderData:"+JSON.stringify($rootScope.selectedPurchaseOrderData)); //304
		if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
			GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
			return;
		}
		$scope.preContractTOs = null;
		var req = {
			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id, // 543
			"projId" : $rootScope.selectedPurchaseOrderData.projId, // 263
			"status" : 1
		};
		PreContractExternalService.getExternalPreContractPopupOnLoad(req).then(function(data) {
		  //console.log("req:"+JSON.stringify(req));
      //console.log("data:"+JSON.stringify(data));
      //console.log("data.repeatPOPreContractTO:"+JSON.stringify(data.repeatPOPreContractTO));

      //console.log("req:"+JSON.stringify(req));
      //console.log("data:"+JSON.stringify(data));
      //console.log("data.repeatPOPreContractTO:"+JSON.stringify(data.repeatPOPreContractTO));
			$scope.preContractTOs = [data.preContractTO];
			$scope.repeatPOPreContractTOs = [data.repeatPOPreContractTO];
			//console.log("repeatPOPreContractTOs:"+JSON.stringify($scope.repeatPOPreContractTOs));
      //alert($scope.repeatPOPreContractTOs);
			$scope.projEmpClassMap = data.projEmpClassMap;
			$scope.projPlantMap = data.projPlantMap;
			$scope.projMaterialClassMap = data.projMaterialClassMap;
			$scope.projServiceMap = data.projServiceMap;
			$scope.projStoreMap = data.projStoreMap;
			$scope.storeMap = data.storeMap;
			$scope.projCostItemMap = data.projCostItemMap;
			$scope.userMap = data.usersMap;
			$scope.companyMap = data.companyMap;
			$scope.projSOWMap = data.projSOWMap;
			$scope.procureCategoryMap = data.procureCategoryMap;
			$scope.expandCollapseManpower = function(manpowerFlag) {
				$scope.manpowerFlag = !manpowerFlag;
			}, $scope.expandCollapsePlant = function(plantFlag) {
				$scope.plantFlag = !plantFlag;
			}, $scope.expandCollapseMaterial = function(materialFlag) {
				$scope.materialFlag = !materialFlag;
			}, $scope.expandCollapseService = function(serviceFlag) {
				$scope.serviceFlag = !serviceFlag;
			}, $scope.expandCollapseSow = function(sowFlag) {
				$scope.sowFlag = !sowFlag;
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting manpower details", 'Error');
		})

	},

//------------- Vijay Ended --------------------
		$scope.getGeneratePurchaseOrderDetails = function() {
				if ($rootScope.selectedPurchaseOrderData == null || $rootScope.selectedPurchaseOrderData == undefined) {
					GenericAlertService.alertMessage("Please select purchase order ID", 'Warning');
					return;
				}
				$scope.preContractTOs = null;
			//	alert(' after ' + $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id);
				var req = {
					"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id,
					"projId" : $rootScope.selectedPurchaseOrderData.projId,
					"purchaseId" :  $rootScope.selectedPurchaseOrderData.id,
					"status" : 1
				};
	  },
        // generate po button latest by vinod, suggested by lakshmi.
         $scope.generatepodetails = function () {
          //alert('I am in RepeatPOContoller');
          //alert(req);
           var purchasereq=$rootScope.selectedPurchaseOrderData;

           //console.log('purchasereq');
           //console.log(purchasereq);

           var purchaseOrderDetails = $scope.getPurchaseOrderDetailsByCmpId(purchasereq);
       	 		purchaseOrderDetails.then(function (data) {
       	 		//alert('I am in RepeatPOContoller received data');
     			var popupDetails = PurchaseOrderDetailsFactory.getPurchaseOrderDetails(data.preContractObj, purchasereq,$rootScope.selectedPurchaseOrderData);
       			popupDetails.then(function (data) {
       			//console.log('**Before populating data on the popup');
       			//console.log(data);
       				$scope.companyMap = data.companyMap;
       				$scope.usersMap = data.usersMap;
       				$scope.purchaseOrdersList = data.purchaseOrderTOs;
       			}, function (error) {
       				GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
       			});


       		}, function (error) {
       			GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
       		});

},

// This method is for final saving
    $scope.saveRepeatPurchaseOrder = function() {
      //console.log('RepeatPOController Started : saveRepeatPurchaseOrder');

      var req = {
            "clientId" : $rootScope.clientId,
      			"purchaseOrderId" : $rootScope.selectedPurchaseOrderData.id,
      			"projId" : $rootScope.selectedPurchaseOrderData.projId, // 263
      			"contractId" : $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id, // 543
      			"status" : 1
      		};
          //alert("Sent a call to saveRepeatPurchaseOrder :"+JSON.stringify(req));
      RepeatPurchaseOrderService.generateRepeatPurchaseOrder(req).then(function(data) {

      		  //console.log("RepeatPurchaseOrderService.saveRepeatPurchaseOrder req:"+JSON.stringify(req));
      		  //console.log("Return data:"+JSON.stringify(data));
            //alert("Got result saveRepeatPurchaseOrder :"+JSON.stringify(req));

      		}, function(error) {
      			GenericAlertService.alertMessage("Error occurred while on Saving saveRepeatPurchaseOrder", 'Error');
      		});
    },

//---------------------------------------------------


  $scope.generateRepeatPO = function () { //preContractTOs,userMap
	    //console.log('precontractexternalcontroller $scope.generateRepeatPO')
      //alert('Vzy Selected generateRepeatPO');
	    //$scope.repeatPOUserProjMap = userMap;
      //$scope.repeatPOSelectedData  = preContractTOs;

  		if ($rootScope.repeatPOSelectedData == null) {
  			GenericAlertService.alertMessage("Please select pre-contract id", 'Warning');
  			return;
  		}


  		//console.log('$scope.repeatPOSelectedData');
  		//console.log($scope.repeatPOSelectedData);

  		//alert('$scope.repeatPOUserProjMap');
  		//alert($scope.repeatPOUserProjMap);
  		//alert('Vzy Selected repeatPOSelectedData');

  		var popupDetails = PurchaseSinglePOFactory.getPurchaseOrderDetails($scope.repeatPOUserProjMap, $rootScope.repeatPOSelectedData,
                          "RepeatPO",$rootScope.clientId,$rootScope.selectedPurchaseOrderData.id,$rootScope.selectedPurchaseOrderData.projId,
                          $rootScope.selectedPurchaseOrderData.preContractCmpTO.preContractTO.id);

  		popupDetails.then(function (data) {
  		//alert('Got the result from PurchaseSinglePOFactory.getPurchaseOrderDetails');
  		}, function (error) {
  			GenericAlertService.alertMessage("Error occurred while adding purchase order", 'Info');
  		});
  	},
$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'epsName', displayName: "EPS Name", headerTooltip: "EPS Name"},
						{ field: 'projName', displayName: "Project Name", headerTooltip: "Project Name"},
						{ field: 'code', displayName: "Contract ID", headerTooltip: "Contract ID", },
						{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description",},
						{ field: 'preContractType', displayName: "Contract Type", headerTooltip: "Contract Type", },
						{ name: 'status', displayName: "RepeatPO Status", headerTooltip: "RepeatPO Status",
						cellTemplate:'<div>{{grid.appScope.repeatPOStatus}}</div>'},
						{ name: 'Repeat PO', displayName: "Repeat PO", headerTooltip: "Repeat PO",cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate:'<div ng-click="grid.appScope.generatepodetails(row.entity)">View/Edit</div>'},
						{ field: 'preContractReqApprTO.reqCode', displayName: "Requisition ID", headerTooltip: "Requisition ID"},
						{ field: 'preContractReqApprTO.reqUserLabelkeyTO.code', displayName: "Requestor", headerTooltip: "Requestor"},
						{ field: 'preContractReqApprTO.apprUserLabelkeyTO.code', displayName: "Approver", headerTooltip: "Approver"},
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Request&Approvals_Request_EmployeeTransferRequest");
					$scope.getEmpTransfers(true);
					$scope.getRepeatPurchaseOrders();
				}
			});

  $scope.getPurchaseOrderDetailsByCmpId = function (purchaseOrder) { //var purchasereq=$rootScope.selectedPurchaseOrderData;

  //console.log('** purchaseOrder '+ JSON.stringify(purchaseOrder));
  var purchaseOrderDefer = $q.defer();
  var onLoadReq = {
    "preContractId": purchaseOrder.preContractCmpTO.preContractTO.id,
    "contractCmpId": purchaseOrder.preContractCmpTO.id,
    "projId": purchaseOrder.projId,
    //"purchaseId":purchaseOrder.parentId,
    "purchaseId" :  $rootScope.selectedPurchaseOrderData.id,
    "isRepeatPO" : true,
    "status": 1
  };
  ///console.log(' onLoadReq '+ JSON.stringify(onLoadReq));
  PurchaseOrderService.getPurchaseOrderDetails(onLoadReq).then(function (data) {
    //console.log(" ** VZY data " +JSON.stringify(data));
    //alert('I am at RepeatPOController');
    var returnPreContractObj = {
      "preContractObj": angular.copy(data)
    };
    purchaseOrderDefer.resolve(returnPreContractObj);
  });
  return purchaseOrderDefer.promise;

},
	$scope.getGeneratePurchaseOrderDetails();
    }]);
