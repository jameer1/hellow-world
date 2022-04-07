'use strict';

app.factory('CompanyMultiSelectFactory', ["ngDialog", "$q", "CompanyService", "GenericAlertService", function (ngDialog, $q, CompanyService, GenericAlertService) {
	var service = {};
	service.getCompanies = function (req) {
		var deferred = $q.defer();

		CompanyService.getCompanies(req).then(function (data) {
			var popupdata = service.openPopup(data.companyTOs);
			popupdata.then(function (resultData) {
				deferred.resolve(resultData);
			});
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while getting  Crew Details", "Error");
		});
		return deferred.promise;

	}, service.openPopup = function (companyTOs) {
		var deferred = $q.defer();
		var crewPopup = ngDialog.open({
			template: 'views/reports/common/companymultiselectpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			showClose: false,
			controller: ['$scope', function ($scope) {
				var selectedCompanyIds = [];
				$scope.companies = companyTOs;
				var companyName = '';

				$scope.selectAllCompanies = function () {
					$scope.companies.map(o => o.select = $scope.selectAll);
				};

				$scope.addCompanies = function () {
					const selectedCompanies = $scope.companies.filter(o => o.select);
					for (const comp of selectedCompanies) {
						selectedCompanyIds.push(comp.id);
						companyName = companyName + comp.cmpCode + ",";
					}

					var returnPopObj = {
						"selectedCompanies": {
							"companyIds": selectedCompanyIds,
							"companyName": companyName.slice(0, -1),
						}
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}

			}]
		});
		return deferred.promise;
	};

	return service;
}]);
