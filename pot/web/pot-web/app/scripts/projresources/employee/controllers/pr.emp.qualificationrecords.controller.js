'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state("empqualificationrecords", {
        url: '/empqualificationrecords',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'views/projresources/projempreg/empqualification/empqualificationrecords.html',
                controller: 'EmpQualificationRecordsController'
            }
        }
    })
}]).controller("EmpQualificationRecordsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "$location", "EmpRegisterService", "EmpQualificationRecordsFactory", "utilservice","stylesService", "ngGridService",
    function ($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, $location, EmpRegisterService, EmpQualificationRecordsFactory, utilservice, stylesService, ngGridService) {

        /*$scope.projEmpQualRecord = [{
			"code" : "QUAL-1",
			"recordType" : "MCA certificate",
			"instituteName" : "JNTU",
			"instituteAddress" : "KPHB",
			"instituteContactDetails" : "9899794979",
			"renewalType" : "N",
			"fileName" : "ss.png"
		}];*/
		$scope.stylesSvc = stylesService;
		$scope.projEmpQualRecord = [];
		var editEmpQualificationRecords = [];
        $scope.getEmpQualifyRecords = function () {

            if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
                GenericAlertService.alertMessage("Please select the Employee", "Warning");
                return;
            }
			var req = {
				"status" : 1,
				"empId" : $rootScope.selectedEmployeeData.id,
			};
			console.log("getEmpQualifyRecords function");
			console.log(req);
			EmpRegisterService.getEmpQualificationRecords(req).then(function(data) {
				console.log(data);	
				$scope.projEmpQualRecord = data.empQualificationRecordsTOs;
				$scope.gridOptions.data = angular.copy($scope.projEmpQualRecord);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while getting qualification records details", 'Error');
			});
			/*var resultMap = EmpRegisterDataShareFactory.getShareObjData(null);
			resultMap.then(function(data) {
				console.log("then function");
				editEmpQualificationRecords = [];
				$scope.empDropDown = data.empMapsData;
				EmpRegisterService.getEmpQualificationRecords(req).then(function(data) {
					console.log(data);	
					$scope.projEmpQualRecord = data.empQualificationRecordsTOs;
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while getting qualification records details", 'Error');
				});
			});*/
        }
		$scope.getEmpQualifyRecords();
        $scope.addEmpQualifyRecord = function (actionType) {
			console.log("addEmpQualifyRecord function");
            if (actionType == 'Edit' && editEmpQualificationRecords.length<=0) {
                GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
                return;
            }
			else if( actionType == 'Edit' && editEmpQualificationRecords.length>1 )
			{
				GenericAlertService.alertMessage("Please select only one row", 'Warning');
                return;
			}
			else
			{
				if( actionType == 'Edit' )
				{
					angular.forEach(editEmpQualificationRecords,function(value,key) {
						console.log(key);
						delete value.selected;
					});
				}
				console.log(editEmpQualificationRecords);
				var qualificationRecordsPopup = EmpQualificationRecordsFactory.qualificationRecordsPopup(actionType,editEmpQualificationRecords);
			    qualificationRecordsPopup.then(function (data) {
				 $scope.getEmpQualifyRecords();
			
			    }, function (error) {
			        GenericAlertService.alertMessage("Error occurred while fetching emp service history  details", 'Error');
			    });
			}			
        }
		$scope.empQualRowSelect = function(qualrecord) {
			if (qualrecord.selected) {
				utilservice.addItemToArray(editEmpQualificationRecords, "id", qualrecord);					
			} else {
				editEmpQualificationRecords.pop(qualrecord);
			}
		}
		$scope.downloadQualDoc = function(recordId,file_name) {
			let req = {
				"recordId" : recordId,
				"category" : "Qualification Records",
				"fileName" : file_name
			}
			EmpRegisterService.downloadRegisterDocs(req);
		}
		var linkCellTemplate ='	<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.empQualRowSelect(row.entity)">';
		$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    { name: 'select', cellTemplate: linkCellTemplate,  displayName: "Select", headerTooltip : "Select" },
						{ field: 'code', displayName: "Record ID", headerTooltip: "Record ID"},
						{ field: 'recordType', displayName: "Qualification / Licence / Certificate / Training", headerTooltip: "Qualification / Licence / Certificate / Training", },
						{ field: 'instituteName', displayName: "Name of The University / Institution", headerTooltip: "Name of The University / Institution"},
						{ field: 'instituteAddress', displayName: "University / Institution address", headerTooltip: "University / Institution address",},
						{ field: 'instituteContactDetails', displayName: "University / Institution Contact details", headerTooltip: "University / Institution Contact details",},
						{ field: 'certificateNumber', displayName: "Certificate / Licence Number", headerTooltip: "Certificate / Licence Number"},
						{ field: 'dateOfIssue', displayName: "Date of issue / award", headerTooltip: "Date of issue / award"},
						{ field: 'isRenewalRequired', displayName: "Required Renewal", headerTooltip: "Required Renewal"},
						{ field: 'expiryDate', displayName: "Expiry Date", headerTooltip: "Expiry Date"},
						{ field: 'newTrainingDueDate', displayName: "New Training Due Date for start", headerTooltip: "New Training Due Date for start"},
						{ name: 'Download Documents', cellClass: 'justify-center', headerCellClass:"justify-center", cellTemplate: '<div ng-click="grid.appScope.downloadQualDoc(row.entity.id, row.entity.fileName)" class="fa fa-download" class="btn btn-primary btn-sm" ></div>', 
			      		displayName: "More Details", headerTooltip : "More Details", },
						];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Resources_MasterEmployeeList_Qualification");
					$scope.gridOptions.gridMenuCustomItems = false;
				}
			});
        $scope.$on("resetEmpQualification", function () {
            $scope.projEmpQualRecord = [];
        });
        $rootScope.$on('empService', function (event) {
            if ($rootScope.selectedEmployeeData) {
                $scope.getEmpQualifyRecords();
            }
        });
    }]);
