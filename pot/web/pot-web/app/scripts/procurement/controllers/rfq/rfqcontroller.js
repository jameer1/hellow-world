'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("rfq", {
		url: '/rfq',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/procurement/RFQ/rfq.html',
				controller: 'RFQController'
			}
		}
	})
}]).controller("RFQController", ["$scope", "$q","ngDialog", "$filter", "RFQService", "PreContractInternalService", "PrecontractBidderDetailsFactory", "GenericAlertService", "EpsProjectMultiSelectFactory", "RfqSchItemsViewFactory", "RfqScheduleItemsEditFactor", "RFQDocumentStatusFactory", "ToVendorDetailsFactory", "FromVendorDetailsFactory","stylesService", "ngGridService", function ($scope, $q,ngDialog, $filter, RFQService, PreContractInternalService,
	PrecontractBidderDetailsFactory, GenericAlertService, EpsProjectMultiSelectFactory, RfqSchItemsViewFactory,
	RfqScheduleItemsEditFactor, RFQDocumentStatusFactory, ToVendorDetailsFactory, FromVendorDetailsFactory,stylesService, ngGridService) {
    $scope.stylesSvc = stylesService;
	var editBidder = [];
	$scope.companyTOs = [];
	$scope.searchProject = {};
	$scope.userType = '1';
	$scope.userProjMap = [];
	$scope.usersMap = [];
	$scope.companyMap = [];

	$scope.bidingStatusList = [{
		id: "1",
		code: "Open",
		name: "Open"
	}, {
		id: "2",
		code: "Closed",
		name: "Close"
	}];

	$scope.rfq = $scope.bidingStatusList[0];
	$scope.date = new Date();
	$scope.toDate = new Date();
	var endDate = new Date($scope.toDate.getFullYear(), $scope.toDate.getMonth() - 1, 0);
	$scope.fromDate = new Date($scope.toDate);
	$scope.fromDate.setDate($scope.toDate.getDate() - endDate.getDate());

	var defaultFromDate = new Date($scope.fromDate);
	var defaultToDate = new Date($scope.toDate);

	$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
	$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectMultiSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			$scope.searchProject = data.searchProject;
			$scope.invalidProjectSelection = data.searchProject.parentName ? false : true;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	};

	$scope.getRFQSearchDetails = function (click) {
		var projIds = null;
		if ($scope.searchProject.projIds != null && $scope.searchProject.projIds.length > 0) {
			projIds = [];
			projIds = $scope.searchProject.projIds;
		}
		var loginUser = true;
		if ($scope.userType == '2') {
			loginUser = false;
		}
		var fromDate1 = new Date($scope.fromDate);
		var toDate1 = new Date($scope.toDate);
		var totalDays = new Date(toDate1 - fromDate1);
		var days = totalDays / 1000 / 60 / 60 / 24;
		if (days < 0) {
			GenericAlertService.alertMessage('To-Date must be greater than From Date', 'Warning');
			return;
		}
		var req = {
			"projIds": projIds,
			"approveStatus": 1,
			"biddingStatus": $scope.rfq.code,
			"status": 1,
			"loginUser": loginUser,
			"fromDate": $filter('date')(($scope.fromDate), "dd-MMM-yyyy"),
			"toDate": $filter('date')(($scope.toDate), "dd-MMM-yyyy")

		};
		RFQService.getPrecontractRFQsByUser(req).then(function (data) {
			$scope.companyTOs = data.preContractCmpTOs;
			var dummyCompany = angular.copy($scope.companyTOs).map((item) =>{
				if(item.status == 1){
					item.status ='No'
				}else{
					item.status = 'Yes'
				}
				return item;
			});
			for (var item of dummyCompany){
			 item.closedate=item.preContractTO.revisedCloseDate != null ? item.preContractTO.revisedCloseDate : item.preContractTO.closeDate;
			}
			$scope.gridOptions.data = dummyCompany;
			$scope.companyMap = data.companyMap;
			$scope.usersMap = data.precontractReqApprMap;
			$scope.userProjMap = data.userProjMap;
			$scope.usersMap = data.precontractReqApprMap
			console.log($scope.companyTOs);
			if (click == "click") {
				if ($scope.companyTOs.length <= 0) {
					GenericAlertService.alertMessage("RFQ Details are not available for given search criteria", 'Warning');
				}
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting RFQ Details", 'Error');

		});

	};

	$scope.reset = function () {
		$scope.searchProject = {};
		$scope.companyTOs = [];
		$scope.bidStatus = {};
		$scope.loginUser = true;
		$scope.allUsers = null;
		$scope.userType = '1';
		$scope.rfq = $scope.bidingStatusList[0];

		$scope.fromDate = null;
		$scope.toDate = null;
		$scope.fromDate = $filter('date')((defaultFromDate), "dd-MMM-yyyy");
		$scope.toDate = $filter('date')((defaultToDate), "dd-MMM-yyyy");

		$scope.getRFQSearchDetails();

	};

	$scope.rowSelect = function (vendor) {
		if (vendor.select) {
			editBidder.push(vendor);
		} else {
			editBidder.pop(vendor);
		}

	};

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

	};

	$scope.getInternalDetailsById = function (preContractObj) {
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

	};

	$scope.getPreContractDocumentStatusList = function () {
		var popupDetails = RFQDocumentStatusFactory.openTenderDocumentStatusPopup($scope.companyTOs);
		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting Document Details", 'Info');
		})
	};

	$scope.getToVendorDetails = function (vendor) {
		var popupDetails = ToVendorDetailsFactory.getToVendorDocuments(vendor, $scope.companyMap, $scope.usersMap);
		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting Correspondence To Vendor Details", 'Info');
		})
	};

	$scope.getFromVendorDetails = function (preContractCmpTO) {
		var popupDetails = FromVendorDetailsFactory.getFromVendorDetails(preContractCmpTO, $scope.preContractCompanyMap, $scope.usersMap);
		popupDetails.then(function (data) {
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting Correspondence From Vendor Details", 'Info');
		})
	};

	$scope.addBiddersTOPrecontract = function () {
		var bidderdetailspopup = PrecontractBidderDetailsFactory.getPreContractBidders($scope.usersMap);
		bidderdetailspopup.then(function (data) {
			$scope.companyTOs = data.preContractCmpTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while fetching bidder details", 'Error');
		});
	};

	$scope.editScheduleItemsDetails = function (preContractCmpTO) {
		var resultData = $scope.getPrecontractSchItems(preContractCmpTO);
		resultData.then(function (data) {
			var popupDetails = RfqScheduleItemsEditFactor.editPrecontractSchItems(data.preContractObj, preContractCmpTO, $scope.rfq);
			popupDetails.then(function (data) {
				$scope.getRFQSearchDetails();
			}, function (error) {
				GenericAlertService.alertMessage("Error occurred while getting precontract details", 'Error');
			})
		}, function (error) {
			GenericAlertService.alertMessage("Error occurred while getting precontract details", 'Error');
		})
	};

	$scope.getPrecontractSchItems = function (preContractCmpTO) {
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
			template: 'views/help&tutorials/procurementhelp/rfqhelp.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			scope: $scope,
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
	
			}]
		});
		return deferred.promise;
	}
	
	var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.rowSelect(row,row.entity)" ng-disabled="row.entity.projectCompany">';
 $scope.$watch(function () { return stylesService.finishedStyling; }, function (newValue, oldValue) {
		if (newValue) {
			let columnDefs = [
				{ field: 'preContractTO.epsName',displayName:'EPS Name',headerTooltip: "EPS Name",},				
				{ field: 'preContractTO.projName',displayName:'Project Name',headerTooltip: "Project Name",},
				{ field: 'preContractTO.code',displayName:'Pre-Contract ID',headerTooltip: "Pre-Contract",},	
				{ field: 'preContractTO.desc',displayName:'Contract Description',headerTooltip: "Contract Description"},	
				{ field: 'preContractTO.preContractReqApprTO.reqUserLabelkeyTO.code',displayName:'Requester',headerTooltip: "Requester",},	
				{ field: 'preContractTO.preContractReqApprTO.apprUserLabelkeyTO.code',displayName:'Approver',headerTooltip: "Approver",},
				{ field: 'companyTO.cmpName',displayName:'Vendor',headerTooltip: "Vendor",},	
				{ name: 'closedate', cellFilter: 'date:"MM-dd-yyyy"' , displayName:'Closed Date',headerTooltip: "Closed Date"},
				//cellTemplate:'<div>{{row.entity.preContractTO.revisedCloseDate !=null ? row.entity.preContractTO.revisedCloseDate : row.entity.preContractTO.closeDate | date}}</div>'},
				{ name: 'soi',displayName:'S.O.I',headerTooltip: "S.O.I",cellClass:'justify-center',headerCellClass:'justify-center', 
				cellTemplate:'<button ng-click="grid.appScope.viewScheduleItemsDetails(row.entity)">View</button>'},	
				{ name: 'CToVendor',displayName:'Correspondence-To Vendor',headerTooltip: "Correspondence-To Vendor",cellClass:'justify-center',headerCellClass:'justify-center',
				cellTemplate:'<button  ng-click="grid.appScope.getToVendorDetails(row.entity)" class="btn btn-primary">View</button>', },	
                { name: 'CFromVendor',displayName:'Correspondence-From Vendor',headerTooltip: "Correspondence-From Vendor", cellClass:'justify-center',headerCellClass:'justify-center',
				cellTemplate:'<button ng-click="grid.appScope.getFromVendorDetails(row.entity)" class="btn btn-secondary">View</button>'},	
				{ name: 'status',displayName:'Received',headerTooltip: "Received", cellClass:'justify-center',
				cellTemplate:'<div>{{row.entity.biddingStatus == "Open" ? "No" : "Yes"}}</div>',headerCellClass:'justify-center'},
				{ name: 'Editqut',displayName:' Edit quotes from vendor',headerTooltip: " Edit quotes from vendor", cellClass:'justify-center',headerCellClass:'justify-center',
				cellTemplate:'<button ng-click="grid.appScope.editScheduleItemsDetails(row.entity)" class="btn btn-primary">View/Edit</button>'},
				{ field: 'biddingStatus',displayName:'Status',headerTooltip: "Status"},	
								
				]
			let data = [];
			$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Enterprise_CompanyList");
			$scope.getRFQSearchDetails();
			
		}
	});
}]);