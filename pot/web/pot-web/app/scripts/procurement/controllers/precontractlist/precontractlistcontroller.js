'use strict';
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("precontractlist", {
		url : '/precontractlist',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/procurement/pre-contractlist/precontractlist.html',
				controller : 'PreContractListController'
			}
		}
	})
}]).controller("PreContractListController", ["$rootScope", "$scope", "$q", "ngDialog", "utilservice", "$filter", "PreContractInternalService", "ReferenceDocumentsPopupFactory", "blockUI", "EpsProjectMultiSelectFactory", "PreContractListFactory", "GenericAlertService","ProcurementDocumentsPopupFactory","stylesService", "ngGridService", function($rootScope, $scope, $q, ngDialog,utilservice, $filter, PreContractInternalService, ReferenceDocumentsPopupFactory, blockUI, EpsProjectMultiSelectFactory, PreContractListFactory, GenericAlertService,ProcurementDocumentsPopupFactory, stylesService, ngGridService) {
	var editPreContractList = [];
	$scope.userProjMap = [];
	$scope.stylesSvc = stylesService;
	$scope.searchProject = {};
	var newvalue1 = null;
	var oldvalue1 = null;
	$scope.contractlist = [];

	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

	$scope.searchReq = {};
	$scope.userType = '1';
	$scope.$watch(function() {
		return $scope.epsDisplay;
	}, function(newvalue, oldvalue) {
		newvalue1 = newvalue;
		oldvalue1 = oldvalue;
	}, true);

	$scope.getUserProjects = function() {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function(data) {
			$scope.searchProject = {};
			$scope.searchProject = data.searchProject;
			$scope.invalidProjectSelection = data.searchProject.parentName ?  false : true;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	}, $scope.getPreContractList = function() {
		var req = {
			"status" : 1,
			"loginUser" : true,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		}
		if ($scope.userType == '2') {
			req.loginUser = false;
		}
		$scope.searchReq = req;
		blockUI.start();
		PreContractInternalService.getPreContractList(req).then(function(data) {
			blockUI.stop();
			$scope.userProjMap = data.userProjMap;
			$scope.contractlist = data.preContractTOs;
			/* if ($scope.contractlist.length <= 0) {
				GenericAlertService.alertMessage("Precontracts are not available for logged in user", 'Warning');
			} */
		}, function(error) {
			blockUI.stop();
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});

	}, $scope.searchPreContractList = function(click) {
		/*if($scope.searchProject.projIds == null || $scope.searchProject.projIds == '' || $scope.searchProject.projIds == undefined) {
			GenericAlertService.alertMessage('Please Select Project', 'Warning');
			return;
		}*/
		var projIds = null;
		if ($scope.searchProject.projIds != null && $scope.searchProject.projIds.length > 0) {
			projIds = [];
			projIds = $scope.searchProject.projIds;
		}
		var fromDate1=new Date($scope.fromDate);
		var toDate1=new Date($scope.toDate);
		var totalDays = new Date(toDate1 - fromDate1);
		var days = totalDays/1000/60/60/24;
		if (days < 0) {
			GenericAlertService.alertMessage('To-Date must be greater than From Date', 'Warning');
			return;
		}
		var req = {
			"status" : 1,
			"projIds" : projIds,
			"loginUser" : true,
			"fromDate" : $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate" : $filter('date')(($scope.toDate), "dd-MMM-yyyy")
		}
		if ($scope.userType == '2') {
			req.loginUser = false;
		}
		$scope.searchReq = req;
		editPreContractList = [];
		PreContractInternalService.getPreContractList(req).then(function(data) {
			$scope.userProjMap = data.userProjMap;
			$scope.contractlist = data.preContractTOs;
		/*	var dummyContract = angular.copy($scope.contractlist).map((item) => {
			if(item.poStatus == 1){
			item.poStatus = 'Open'}
			else{
			item.poStatus = 'Close'}
			return item;
			});*/
			$scope.gridOptions.data = $scope.contractlist;
			console.log($scope.contractlist, 555555);
			if(click=='click'){
				if ($scope.contractlist.length <= 0) {
					GenericAlertService.alertMessage("Pre-contracts not available for given search criteria", 'Warning');
				}
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting PreContracts", 'Error');

		});

	},

	$scope.resetPreContractList = function() {
		$scope.searchProject = {};
		$scope.epsDisplay = [];
		$scope.projectDisplay = [];
		$scope.contractlist = [];
		$scope.fromDate = null;
		$scope.toDate = null;
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");
		$scope.userType = '1';
		$scope.searchPreContractList();
		editPreContractList = [];
	},

	$scope.rowSelect = function(precontract) {
		if (precontract.select) {
			utilservice.addItemToArray(editPreContractList, "id", precontract);
		} else {
			editPreContractList.splice(editPreContractList.indexOf(precontract),1);
		}

	}, $scope.addPreContract = function(actionType, userProjMap) {
		if (actionType == 'Edit' && editPreContractList <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}
		var popup = PreContractListFactory.addPreContracts($scope.searchReq, actionType, editPreContractList, $scope.userProjMap);

		popup.then(function(data) {
			$scope.contractlist = data.preContractTOs;
			editPreContractList = [];
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting precontract details", 'Info');
		})

	}, $scope.getRefDocument = function(precontract) {
		$rootScope.referenceDocumentMode = "Reference Documents";		
		var refreq = ReferenceDocumentsPopupFactory.referenceDocumentDetails(precontract);
		refreq.then(function(data) {
			$scope.refdocumentslist = data.preContractDocsTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})

	};
	$scope.downloadRefDocument = function(precontract) {
		console.log("downloadRefDocument function");
		console.log(precontract);
		$rootScope.referenceDocumentMode = "Download Documents";	
		var refreq = ProcurementDocumentsPopupFactory.getReferenceDocumentDetails(precontract);
		refreq.then(function(data) {
			$scope.refdocumentslist = data.preContractDocsTOs;
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while selecting details", 'Info');
		})

	}
	$scope.deletePreContract = function () {
		if (editPreContractList.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to Deactivate", 'Warning');
			return;
		}
		GenericAlertService.confirmMessageModal('Do you really want to deactivate the record?',
			'Warning', 'YES', 'NO').then(function () {
				var deleteIds = [];
				$scope.nondeleteIds = [];
				if ($scope.selectedAll) {
					$scope.contractlist = [];
				} else {
					angular.forEach(editPreContractList, function (value, key) {
						if (value.id && value.workFlowStatusTO.value == 1) {
							deleteIds.push(value.id);
						}
					});
         if(deleteIds.length<=0){
           GenericAlertService.alertMessage('The record is used in stage 1 request', "Error");
           return;
         }
					var req = {
						"contractIds": deleteIds,
						"status": 2
					};
					PreContractInternalService.deactivatePreContracts(req).then(function (data) {
						GenericAlertService.alertMessage('Pre-Contract Detail(s) Deactivated successfully', 'Info');
					});
					angular.forEach(editPreContractList, function (value, key) {
						if(value.id && deleteIds.indexOf(value.id) != -1){
							$scope.contractlist.splice($scope.contractlist.indexOf(value), 1);
						}
					}, function (error) {
						GenericAlertService.alertMessage('Pre-Contracts are failed to Deactivate', "Error");
					});
					editPreContractList = [];

				}

			});

	}
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.select" ng-change="grid.appScope.rowSelect(row.entity)">';
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'epsName', displayName: "EPS", headerTooltip: "EPS",},
						{ field: 'projName', displayName: "Project", headerTooltip: "Project",},
						{ field: 'code', displayName: "Pre-Contract ID", headerTooltip: "Pre-Contract ID",},
						{ field: 'preContractType', displayName: "Contract Type", headerTooltip: "Contract Type",},
						{ field: 'desc', displayName: "Contract Description", headerTooltip: "Contract Description",},
						{ field: 'purchaseOrderStatus', displayName: "Pre-Contract Stage", headerTooltip: "Pre-Contract Stage",},
						{ field: 'poStatus', displayName: "Status",headerTooltip: "status",cellTemplate:'<div>{{row.entity.poStatus == 1 ? "Open" : "Close"}}</div>'},
						{ name: 'Document', displayName: "Reference Documents", headerTooltip: "Reference Documents",cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: '<div ng-click="grid.appScope.getRefDocument(row.entity,precontract)" data-test="PreContractList_Refdocument" class="btn btn-primary btn-sm">Ref Document</div>'},
						{ name: 'Download', displayName: "Download Documents", headerTooltip: "Download Documents",cellClass: 'justify-center', headerCellClass:"justify-center",
						cellTemplate: '<div ng-click="grid.appScope.downloadRefDocument(row.entity, precontract)" data-test="PreContractList_Refdocument_Download" class="fa fa-download"></div>'}
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Procurement_PreContractList");
					$scope.getPreContractList();
				}
			});
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
			template: 'views/help&tutorials/procurementhelp/precontractlisthelp.html',
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
