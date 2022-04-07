'use strict';
app.factory('EmpQualificationRecordsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, GenericAlertService) {
    var qualificationRecordsPopup;
    var service = {};
    service.qualificationRecordsPopup = function (actionType,editQualRecordData) {
		console.log(actionType);
        var deferred = $q.defer();
        qualificationRecordsPopup = ngDialog.open({
            template: 'views/projresources/projempreg/empqualification/empqualificationrecordspopup.html',
            className: 'ngdialog-theme-plain ng-dialogueCustom0',
            closeByDocument: false,
            showClose: false,
            controller: ['$scope', function ($scope) {
				$scope.selectedFileMap = [];
                $scope.renewalTypes = [{
                    id: 1,
                    name: 'Yes'
                }, {
                    id: 2,
                    name: 'No'
                }]
//---------------------------------------------------------------------------------------------------
 $scope.isRenewalRequireds = [{
                    id: 1,
                    name: 'Y'
                }, {
                    id: 2,
                    name: 'N'
                }]
		
//---------------------------------------------------------------------------------------------------
				$scope.qualificationRecords = [];
				
				var addQualRecord = {
					"recordType" : null,
					"instituteName" : null,
					"instituteAddress" : null,
					"instituteContactDetails" : null,
					"dateOfIssue" : null,
					"expiryDate" : null,
					"certificateNumber" : null,
					"newTrainingDueDate" : null,
					"empRegId" : $rootScope.selectedEmployeeData.id,
					"projId" : $rootScope.selectedEmployeeData.projId,
					"renewalType" : null,
					"isRenewalRequired": null
				};
				$scope.errorMessage = "";
				$scope.alertMessage = [];
				
				$scope.isValidDoc = true;
				$scope.actionType = actionType;
				$scope.disableExpiryDate = true;
								
				console.log($rootScope.selectedEmployeeData.projId,"call to projid");
				console.log($rootScope.selectedEmployeeData.id);
				
				if (actionType === 'Add') {
					$scope.qualificationRecords.push(addQualRecord);
				} else {
					$scope.qualificationRecords = angular.copy(editQualRecordData);					
				}
				console.log(actionType);
				console.log($scope.qualificationRecords);
				
				$scope.checkFile = function (file,index) {
					console.log("checkFile function");
					var allowedFiles = [".csv", ".xls", ".xlsx", ".doc", ".docx", ".png",".jpg", ".jpeg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!file) {
						$scope.isValidDoc=false;
					}if (!regex.test(file.name.toLowerCase().replace(/\s/g, "")) ) {
						$scope.errorMessage="Supported formats PNG,JPEG,DOC,DOCX,CSV,XLSX,XLS ";
						$scope.isValidDoc=false;
					}else if( (file.size > 1000000)){
						$scope.errorMessage="Supported Max. File size 1MB";
						$scope.isValidDoc=false;
					}else{
						$scope.isValidDoc=true;
						$scope.selectedFileMap[index] = file;
					}
				},
//-------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------
		$scope.getRenewalTypes = function(renewalType) {
			console.log(renewalType,"call to renewal record")
					if( renewalType == 'Y' )
					{
						$scope.disableExpiryDate = false;
					}
					else
					{
						$scope.disableExpiryDate = true;
					}
			
				},
//---------------------------------------------------------------------------------------------------------------------------------				
	console.log("disableExpiryDate",$scope.disableExpiryDate)
//------------------------------------------------------------------------------------------------------
				$scope.getRenewalType = function(renewalType) {
					if( renewalType.name == 'Yes' )
					{
						$scope.disableExpiryDate = false;
					}
					else
					{
						$scope.disableExpiryDate = true;
					}
					$scope.qualificationRecords[0].isRenewalRequired = (renewalType.name=='Yes') ? 'Yes':'No';			
				},
				$scope.saveEmpQualifyRecords = function() {				
					console.log(actionType);
					var files = [];
					if( $scope.selectedFileMap.length != 0 )
					{
						for (let index = 0; index < $scope.selectedFileMap.length; index++) {
							const value = $scope.selectedFileMap[index];
							if (value) {
								$scope.qualificationRecords[index].fileObjectIndex = files.length;
								$scope.qualificationRecords[index].fileName = $scope.selectedFileMap[index].name;
								files.push(value);
							}
						}
					}
					
					$scope.alertMessage = [];
					angular.forEach($scope.qualificationRecords,function(value,key){
						if( value.recordType == null )
						{
							$scope.alertMessage.push("Please enter Qualification/Licence/Certificate/Training");
						}
						if( value.certificateNumber == null )
						{
							$scope.alertMessage.push("Please enter Certificate/Licence Number");
						}
						if( value.instituteName == null )
						{
							$scope.alertMessage.push("Please enter Name of The University/Institution");
						}
						if( value.dateOfIssue == null )
						{
							$scope.alertMessage.push("Please select Date of issue/award");
						}
						if( value.isRenewalRequired == null )
						{
							$scope.alertMessage.push("Please select Required Renewal");
						}
						/*if( value.fileName == null || value.fileName == undefined || value.fileName == "" ){
							
							$scope.alertMessage.push("Please Upload Certificates");
						}*/
						else
						{
							//console.log("else condition:"+value.renewalType.name);
						if( value.isRenewalRequired.name == 'Yes' )
							{
							if( value.expiryDate )
								{
			    				$scope.alertMessage.push("Please choose Expiry Date");
								}
							}
						}
					});
					if( $scope.alertMessage.length > 0 )
					{
						var error_msg = "";
						for(var m=0;m<$scope.alertMessage.length;m++)
						{
							console.log($scope.alertMessage[m]);
							error_msg += $scope.alertMessage[m]+"<br>";
						}
						GenericAlertService.alertMessageModal(error_msg,"Error");
					}
					else
					{
						if( actionType === 'Edit' )
						{
							angular.forEach( $scope.qualificationRecords, function(value, key){
								console.log(key);
								console.log(value);							
								value.empRegId = $rootScope.selectedEmployeeData.id;
								value.projId = 	$rootScope.selectedEmployeeData.projId;				
							});
						}
						delete $scope.qualificationRecords[0].renewalType;
						var req = {
							"status" : 1,
							"empId" : $rootScope.selectedEmployeeData.id,
							"empQualificationRecordsTOs" : $scope.qualificationRecords,
							"folderCategory" : "Qualification Records",
							"userId" : $rootScope.account.userId
						}
						console.log(req);
						console.log(files);
											
						EmpRegisterService.saveEmpQualificationRecords(files,req).then(function(data) {
							var succMsg = GenericAlertService.alertMessageModal('Employee Qualification Records saved successfully',"Info");
							succMsg.then(function() {
								$scope.closeThisDialog();
								deferred.resolve(data);
							});
							
						}, function(error) {
							GenericAlertService.alertMessage('Employee Qualification Records are Failed to Save ', "Error");
						});
					}					
				}				
            }]
        });
        return deferred.promise;
    }
    return service;
}]);
