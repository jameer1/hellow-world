'use strict';
app.factory('ProjStatusMileStonesFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "ProjectStatusService", "GenericAlertService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, ProjectStatusService, GenericAlertService) {

	var mileStonesPopup = [];
	var mileStonesService = {};
	mileStonesService.mileStoneDetails = function (actionType, editMileStone, projId) {
		var deferred = $q.defer();
		var addMileStonePopup = ngDialog.open({
			template: 'views/projectstatus/milestonespopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.action = actionType;
				$scope.mileStones = [];
				$scope.mileStones = [{
					'id': null,
					'projId': projId,
					'editMileStone': null,
					'mileStoneOriginal': null,
					'mileStoneForeCast': null,
					'mileStoneActual': null,
					'mileStoneVariance': null,
					'status': 1
				}];



				if (actionType === 'Edit') {

					$scope.mileStones[0].id = editMileStone[0].id;
					$scope.mileStones[0].mileStones = editMileStone[0].mileStones;
					$scope.mileStones[0].mileStoneOriginal = editMileStone[0].mileStoneOriginal;
					$scope.mileStones[0].mileStoneForeCast = editMileStone[0].mileStoneForeCast;
					$scope.mileStones[0].mileStoneActual = editMileStone[0].mileStoneActual;
					$scope.mileStones[0].mileStoneVariance = editMileStone[0].mileStoneVariance;

				}

				$scope.saveMileStoneDetail = function (mileStones) {

					if (mileStones[0].mileStoneOriginal && mileStones[0].mileStoneForeCast && new Date(mileStones[0].mileStoneForeCast) <= new Date(mileStones[0].mileStoneOriginal)) {
						GenericAlertService.alertMessage("ForeCast Date should be greater than Original Date", 'Warning');
						return;
					}

					$scope.mileStones[0].projId = projId,
						$scope.mileStones[0].status = 1,
						$scope.mileStones[0].mileStones = mileStones[0].mileStones;
					$scope.mileStones[0].mileStoneOriginal = mileStones[0].mileStoneOriginal;
					$scope.mileStones[0].mileStoneForeCast = mileStones[0].mileStoneForeCast;
					$scope.mileStones[0].mileStoneActual = mileStones[0].mileStoneActual;
					$scope.mileStones[0].mileStoneVariance = mileStones[0].mileStoneVariance;

					var saveReq = {
						"projMileStonesTOs": mileStones,
						"projId": projId,
						"status": "1"
					};
					// GenericAlertService.alertMessage("Milestone Added/Updated Successfully", "Info");
					GenericAlertService.alertMessage("Milestones saved successfully", "Info");
					ProjectStatusService.saveProjStatusMileStones(saveReq).then(function (data) {
						deferred.resolve(data);
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while saving project status MileStones", "Error");
					});

					ngDialog.close();
				},

					$scope.populateMileStoneVariance = function (mileStones) {
						var original = mileStones[0].mileStoneOriginal;
						// consider forecast only when actual is null
						var actual = mileStones[0].mileStoneActual ? mileStones[0].mileStoneActual : mileStones[0].mileStoneForeCast;
						var varianceMileDays = 0;
						if (original && actual) {
							var originalMileDate = new Date(original);
							var actualMileDate = new Date(actual);

							var compTimeDiff = Math.abs(actualMileDate.getTime() - originalMileDate.getTime());
							varianceMileDays = Math.ceil(compTimeDiff / (1000 * 3600 * 24));
						}
						mileStones[0].mileStoneVariance = varianceMileDays;
					}

			}]

		});
		return deferred.promise;
	}

	return mileStonesService;
}]);
