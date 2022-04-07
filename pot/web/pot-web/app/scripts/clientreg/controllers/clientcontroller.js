'use strict';

/**
 * @ngdoc function
 * @name potApp.controller:ClientController
 * @description # Client Controller of the potApp
 */
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("clientreg", {
		url: '/clientReg',
		parent: 'site',
		data: {
			roles: []
		},
		views: {
			'content@': {
				templateUrl: 'views/clientreg/clientview.html',
				controller: 'ClientController'
			}
		}
	})
}]).controller('ClientController', ["$scope", "$state", "$q", "ngDialog", "ClientService", "blockUI", "GenericAlertService", "utilservice", "$filter","CountryService", function ($scope, $state, $q, ngDialog, ClientService, blockUI, GenericAlertService, utilservice, $filter,CountryService) {
	$scope.clients = [];


	var deferred = $q.defer();
	var countries = [];
	var currentRow;
	
	var getCountries = function () {
		CountryService.getCountries().then(function (data) {			
			countries = data.countryInfoTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching countries", 'Error');
		});
	}
	getCountries();
	$scope.getClients = function () {
		var req = {
			"status": "1"
		};
		ClientService.getClients(req).then(function (data) {
			$scope.clients = data.clientRegTOs;
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching client details", 'Error');
		});
	}

	var clientpopup;
	var addClientservice = {};
	var isDisabled;
	$scope.clientDetails = function (actionType, client, index = -1) {

		if (actionType == 'Edit') {
			isDisabled = true;
		} else {
			isDisabled = false;
		}
		
		currentRow = Number(index);
		
		clientpopup = addClientservice.addClients(actionType, client, isDisabled, $scope.clients);
		clientpopup.then(function (data) {
			utilservice.addItemToArray($scope.clients, "id", data.clientRegTOs);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching client details", 'Error');
		});
	}

	$scope.downloadMailTemplate = function (clientId) {
		ClientService.getClientMailTemplate(clientId);
	}

	addClientservice.addClients = function (actionType, client, isDisabled, clients) {
		clientpopup = ngDialog.open({
			template: 'views/clientreg/clientregpopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom0',
			showClose: false,
			closeByDocument: false,
			controller: ['$scope', function ($scope) {
				$scope.countries = countries;
				$scope.countryObj = null;
				$scope.isDisabled = isDisabled;
				$scope.actionType = actionType;
				$scope.captcha = '';
				$scope.userTO = {};
				$scope.clientDoc = {};
				$scope.clientDoc.isValid = true;
				
				$scope.minLicenseExpiryDate = $filter('date')((new Date()), "dd-MMM-yyyy");
				
				if (actionType === 'Add') {
					$scope.addClient = {
						"userTO": {
							"firstName": null,
							"lastName": null,
							"dispName": null,
							"userName": null,
							"password": null,
							"status": 1
						},
						"code": null,
						"name": null,
						"businessType": null,
						"email": null,
						"fax": null,
						"mobile": null,
						"phone": null,
						"saveFlag": true,
						"remarks": null,
						"registeredUsers": null,
						"status": 1,
						"country": null
					}
				} else {
					$scope.addClient = angular.copy(client);
					client = {};
					$scope.countryObj = $scope.addClient.country;
				}

				$scope.updateCountryName = function (data, addClient) {
					addClient.country = data;
				}

				$scope.checkEmail = function (form) {
					var req = {
						"status": "1",
						"email": form.email.$viewValue
					};
					ClientService.getClientByEmail(req).then(function (data) {
						if (data && data > 0) {
							form.email.$setValidity("duplicateEmail", false);
							form.email.errorMessage = "Duplicate client email";
						} else {
							form.email.$setValidity("duplicateEmail", true);
						}
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while duplicate  client email check", 'Error');
					});
				}

				$scope.checkFile = function (file) {
					var allowedFiles = [".doc", ".docx", ".png", ".jpg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, ""))) {
						$scope.clientDoc.errorMessage = "Supported formats PNG,JPEG,DOC,DOCX ";
						$scope.clientDoc.isValid = false;
					} else if ((file.size > 1000000)) {
						$scope.clientDoc.errorMessage = "Supported Max. File size 1MB";
						$scope.clientDoc.isValid = false;
					} else {
						$scope.clientDoc.isValid = true;
						console.log("else condition");					
						$scope.fileInfo = file;
					}

				}
				let formatBytes = function (bytes) {
					if(bytes < 1024) return bytes + " Bytes";
					else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
					else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
					else return(bytes / 1073741824).toFixed(3) + " GB";
				}
								
				let checkForDuplicateClientCodeAndUserName = function () {
					let duplicateFound = false;
					let error_msg = "";
					clients.map((client) => {
						if (client.code.toLowerCase() === $scope.addClient.code.toLowerCase()) {
							duplicateFound = true;
							$scope.addClient.code = $scope.addClient.code;
							error_msg += "Please choose a unique client code.";
						}
						if( client.userTO.userName.toLowerCase() === $scope.addClient.userTO.userName.toLowerCase() )
						{
							duplicateFound = true;
							error_msg += "Please choose a unique User Name.";
							$scope.addClient.userTO.userName = $scope.addClient.userTO.userName;							
						}
					});
					if( duplicateFound )
					{
						GenericAlertService.alertMessage( error_msg, 'Error');
					}
					return duplicateFound;
				}
				
				$scope.saveClient = function () {
					if( parseInt( $scope.addClient.registeredUsers ) <= 0 )
					{
						GenericAlertService.alertMessage("Max Users cannot be less than zero", 'Error');
						return;
					}
					if( actionType == 'Add' && checkForDuplicateClientCodeAndUserName() ) {
						return;
					}
					
					if( $scope.fileInfo != null )
					{
						$scope.addClient.clientFileSize = formatBytes($scope.fileInfo.size);
					}					
					var req = {
						"clientRegTO": $scope.addClient,
						"folderCategory" : "Client Registration"
					};
					console.log(req);
					blockUI.start();
					ClientService.saveClient($scope.fileInfo, req).then(function (data) {
						blockUI.stop();
						data = data.data;
						var results = data.clientRegTOs[0];
						saveDefaultClientData(results);
						// var succMsg = GenericAlertService.alertMessageModal('Client Details is/are ' + data.message, data.status);
						var succMsg = GenericAlertService.alertMessageModal('Client Details saved successfully',"Info");
						succMsg.then(function (data) {
							if( currentRow == -1 ){
								clients[clients.length] = angular.copy(results);
							}else{
								clients[currentRow] = angular.copy(results);
							}
							
							$scope.closeThisDialog();
						}, function (error) {
							blockUI.stop();
							GenericAlertService.alertMessage("Error occured while saving Client Details", "Error");
						});
					});
				};

				function saveDefaultClientData(client) {
					if (actionType == 'Add' && client.id)
						ClientService.saveDefaultClientData(client.id);
				}
			}]
		});
		return deferred.promise;
	},


		$scope.show = function (remarks) {
			ngDialog.open({
				template: 'views/clientreg/viewpopup.html',
				className: 'ngdialog-theme-plain ng-dialogueCustom6',
				showClose: false,
				controller: ['$scope', function ($scope) {
					$scope.remarks = remarks;
				}]
			});
		}
	return addClientservice;
}]);
