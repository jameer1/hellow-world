'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:EmailSettingController
 * @description # EmailSetting Controller of the potApp
 */
app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("emailSettings", {
		url : '/emailSettings',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/emailsettings/emailSettings.html',
				controller : 'EmailSettingController'
			}
		}
	})
}]).controller('EmailSettingController', ["$scope", "ngDialog", "$state", "$q", "blockUI", "EmailSettingService", "GenericAlertService", function($scope, ngDialog, $state, $q,blockUI, EmailSettingService, GenericAlertService) {
	$scope.resetUser = {
		"status" : 1
	};
	$scope.tableData = [];
	$scope.emailUniqueMap = [];
	var deleteIds = [];
	$scope.getEmailSettings = function() {
		var req = {
			"status" : 1
		};
		EmailSettingService.getEmailSettings(req).then(function(data) {
			$scope.tableData = data.emailSettingTOs;
		});
	}, $scope.rowselect = function(tab) {
		if (tab.select) {
			deleteIds.push(tab.id);
		} else {
			deleteIds.pop(tab.id);
		}
	}, $scope.deactivateEmailSettings = function() {
		if (deleteIds == undefined || deleteIds.length <= 0) {
			GenericAlertService.alertMessage("Please select atleast one Email Setting to deactivate", 'Warning');
			return;
		}
		var req = {
			"emailSettingIds" : deleteIds,
			"status" : 2
		};
		GenericAlertService.confirmMessageModal('Do you really want to delete the record', 'Warning', 'YES', 'NO').then(function() {
			EmailSettingService.deactivateEmailSettings(req).then(function(data) {
				$scope.tableData = data.emailSettingTOs;
				GenericAlertService.alertMessage('Email Settings is/are deactivated successfully', "Info");
				deleteIds = [];
			}, function(error) {
				GenericAlertService.alertMessage('Email Settings  is/are failed to deactivate', "Error");
			});
		});
	}

	var addEmailservice = {};
	var emailSettingPopUp;
	var deferred = $q.defer();

	var tableScope = $scope;

	$scope.editEmailDetails = function(actionType, editData) {
		emailSettingPopUp = addEmailservice.addEmailDetails(actionType, editData);
	}
	addEmailservice.addEmailDetails = function(actionType, editData) {
		emailSettingPopUp = ngDialog.open({
			template : 'views/emailsettings/emailsettingspopup.html',
			scope : $scope,
			className : 'ngdialog-theme-plain ng-dialogueCustom7',
			showClose : false,
			closeByDocument : false,
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				var copyEditArray = angular.copy(editData);
				$scope.emailSetting = {
					id : null,
					host : null,
					port : null,
					fromEmail : null,
					userName : null,
					password : null,
					status : 1
				};
				var req = {
					status : 1
				};

				$scope.hostDisabled = false;
				$scope.portDisabled = false;

				if ($scope.action === 'Edit') {
					$scope.emailSetting = angular.copy(editData);

					$scope.hostDisabled = true;
					$scope.portDisabled = true;
				}

				$scope.getEmailServiceMap = function() {
					var req = {};
					EmailSettingService.getEmailServiceMap(req).then(function(data) {
						$scope.emailUniqueMap = data.emailUniqueMap;
					});
				}, $scope.checkDuplicate = function(emailSetting) {
					emailSetting.duplicateFlag = false;
					emailSetting.fromEmail = emailSetting.fromEmail.toUpperCase();
					if ($scope.emailUniqueMap[emailSetting.fromEmail] != null) {
						emailSetting.duplicateFlag = true;
						return;
					}
					emailSetting.duplicateFlag = false;
				}
				$scope.saveEmailSettings = function() {

					 if ($scope.emailSetting.duplicateFlag) {
					 	GenericAlertService.alertMessage('Duplicate Email ID is not allowed', "Warning");
					 	return;
					}
					var saveReq = {
						"emailSettingTOs" : [ $scope.emailSetting ]
					};
					blockUI.start();
					EmailSettingService.saveEmailSettings(saveReq).then(function(data) {
						blockUI.stop();
						if (data.status != null && data.status !== undefined && data.status === 'Info') {
							var emailTOs = data.emailSettingTOs;
							// var succMsg = GenericAlertService.alertMessageModal('Email Setting(s) is/are ' + data.message, data.status);
							var succMsg = GenericAlertService.alertMessageModal('Email Setting(s) saved successfully','Info');
							succMsg.then(function(data) {
								tableScope.tableData = emailTOs;
								var returnPopObj = {
									"emailSettingTOs" : emailTOs
								};
								$scope.closeThisDialog();
								deferred.resolve(returnPopObj);
							}, function(error) {
								GenericAlertService.alertMessage("Error occurred while closing popup", 'Info');
							});
						}
					},

					function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('Email Setting(s)  is/are failed to save', "Error");
					});

				}
			} ]
		});
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
			template: 'views/help&tutorials/adminhelp/emailhostinghelp.html',
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
