			'use strict';

		app.factory('AssetCompanySelectFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "blockUI", "EpsService", "GenericAlertService", "TreeService", "CompanyService", function (ngDialog, $q, $filter, $timeout, $rootScope, blockUI, EpsService,
			GenericAlertService, TreeService,CompanyService) {
			var service = {};
			service.getCompanies = function () {
				var deferred = $q.defer();
				var req = {
					"status": 1
				};
				blockUI.start();
				CompanyService.getCompanies(req).then(function (data) {
					blockUI.stop();
					var popupdata = service.openPopup(data.companyTOs);
					popupdata.then(function (resultData) {
						deferred.resolve(resultData);
					});
				}, function (error) {
					blockUI.stop();
					GenericAlertService.alertMessage("Error occured while getting  EPS Project Details", "Error");
				});
				return deferred.promise;

			}, service.openPopup = function (assetComps) {
				var deferred = $q.defer();
				var assetCompanyPopup = ngDialog.open({
					template: 'views/projresources/projassetreg/assetdetails/companydetails.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom4',
					closeByDocument: false,
					showClose: false,
					controller: ['$scope', function ($scope) {
						$scope.companyData = assetComps;
						$scope.selectAssetCompany = function (item) {
							var returnPopObj = {
								"searchCompany": {
									'cmpId':item.id,
									"cmpCode": item.cmpCode,
									"cmpName": item.cmpName
								}
							};
							deferred.resolve(returnPopObj);
							$scope.closeThisDialog();
						};

					}]
				});
				return deferred.promise;
			};

			return service;

		}]);