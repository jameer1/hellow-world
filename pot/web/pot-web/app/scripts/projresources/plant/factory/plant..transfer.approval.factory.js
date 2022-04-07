'use strict';
app.factory('PlantTransferApprovalFactory', ["ngDialog", "$q", "blockUI", "$filter", "$rootScope", "GenericAlertService", "ProjectSettingsService", "PlantRegisterService", "RequestForPlantAdditionalTimeFactory", function (ngDialog, $q, blockUI, $filter, $rootScope, GenericAlertService,
	ProjectSettingsService, PlantRegisterService, RequestForPlantAdditionalTimeFactory) {
	var service = {};
	service.getPlantTransReqDetails = function (editNewRequest) {
		var deferred = $q.defer();

		var popupdata = service.openPopup(editNewRequest);
		popupdata.then(function (data) {
			deferred.resolve(data);
		});
		return deferred.promise;
	}, service.openPopup = function (plantTransferReqApprTO) {
		var deferred = $q.defer();
		var popupdata = ngDialog.open({
			template: 'views/projresources/projplantreg/approvalForTransfer/plantregtrasnferapprovalpopup.html',
			closeByDocument: false,
			showClose: false,
			className: ' ngdialog ngdialog-theme-plain ng-dialogueCustom0-5',
			controller: ['$scope', function ($scope) {
				$scope.plantTransReqData = plantTransferReqApprTO;
				console.log($scope.plantTransReqData);
				$scope.apprStatus = $scope.plantTransReqData.apprStatus;
				if ($scope.plantTransReqData.apprDate == null) {
					$scope.date = new Date();
					$scope.plantTransReqData.apprDate = $filter('date')($scope.date, "dd-MMM-yyyy")
				}
				$scope.timeFlag = false;
				$scope.reqForAdtlTime = false;
				$scope.getProjSettingsPlantDetailsCheck = function () {
					// Disable button when approver is not logged in user
					/*
					if ($rootScope.account.userId != $scope.plantTransReqData.apprUserTO.userId) {
						$scope.timeFlag = true;
						return;
					}
					*/
					var req = {
						"projId": $scope.plantTransReqData.fromProjId,
						"id": $scope.plantTransReqData.id,
						"apprStatus": 'Approval Time'
					}
					ProjectSettingsService.projPlantTransOnLoad(req).then(function (data) {
						console.log(data)
						const plantTrans = data.projPlantTransTOs[0];
						console.log(plantTrans);
						const requestedDate = $scope.plantTransReqData.addlTimeFlag ? $scope.plantTransReqData.notifyDate : $scope.plantTransReqData.reqDate;
						//const requestedDate = $scope.plantTransReqData.reqDate;
						let reqDate = new Date(requestedDate);

						if (plantTrans.cutOffDays) {
							// Adding days
							// Adding Cut Off days + 1 (1 day is to set midnight from requested date)
							reqDate.setDate(reqDate.getDate() + 1 + plantTrans.cutOffDays);
						}
						if (plantTrans.cutOffHours) {
							// Adding hours
							reqDate.setHours(reqDate.getHours() + plantTrans.cutOffHours);
						}
						if (plantTrans.cutOffMinutes) {
							// Adding hours
							reqDate.setMinutes(reqDate.getMinutes() + plantTrans.cutOffMinutes);
						}
						//reqDate.setHours(0, 0, 0, 0);
						const today = new Date();
						$scope.timeFlag = (reqDate.getTime() <= today);
						if ($scope.timeFlag) {
							$scope.reqForAdtlTime = true;
						}
						if($scope.timeFlag == true && $scope.plantTransReqData.addlTimeFlag == true)
						  $scope.timeFlag = false;
					});
				};

				$scope.savePalntTrasnferRequest = function (apprStatus) {
					$scope.plantTransReqData.apprStatus = apprStatus;
					$scope.plantTransReqData.notificationStatus = 'APPROVED';
					var req = {
						"plantTransferReqApprTOs": [$scope.plantTransReqData],
						"status": 1,
						"transType": false,
						"loginUser": true,
						"projId": $scope.plantTransReqData.fromProjId,
						"apprStatus": apprStatus
					}
					blockUI.start();
					PlantRegisterService.savePlantTransfers(req).then(function (data) {
						blockUI.stop();
						var succMsg = GenericAlertService.alertMessageModal('Plant New Request Detail(s) is/are Successfully ' + req.apprStatus, "Info");
						succMsg.then(function () {
							$scope.closeThisDialog();
							$scope.apprStatus = apprStatus;
							deferred.resolve(data);
						});
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Plant New Request Detail(s) is/are Failed to ' + req.apprStatus, "Error");
					});
				};

				$scope.requestForAdditionalTime = function (apprStatus) {
					if (apprStatus != 'Pending For Approval') {
						GenericAlertService.alertMessage("Please Select Pending For Approval", 'Warning');
						return;
					}
					var reqForAdditionalTimePopUp = RequestForPlantAdditionalTimeFactory.getAdditionalTimeDetails($scope.plantTransReqData);
					reqForAdditionalTimePopUp.then(function (data) {
						$scope.closeThisDialog();
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while getting additional transfer details", 'Error');
					});
				};
			}]
		});
		return deferred.promise;
	}
	return service;
}]);
