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
}]).controller('ClientController', ["$scope", "$state", "$q", "ngDialog", "ClientService", "blockUI", "GenericAlertService", "utilservice", "$filter","CountryService","stylesService", "ngGridService",  function ($scope, $state, $q, ngDialog, ClientService, blockUI, GenericAlertService, utilservice, $filter,CountryService, stylesService, ngGridService) {
	$scope.clients = [];


	var deferred = $q.defer();
	var countries = [];
	$scope.stylesSvc = stylesService;
	var currentRow;
	$scope.account1={
		'code':null,
		'name':null
	}
	$scope.getClients1=function(){
		console.log($scope.account1.code);
		console.log($scope.account1.name);
		if($scope.account1.code != "" && $scope.account1.code != null ){
		$scope.datas=[];
		for(var i=0; i<$scope.clients.length;i++){
			if($scope.clients[i].code == $scope.account1.code ){
				$scope.datas.push($scope.clients[i]);
			}
		}
		
		if($scope.datas !=""){
			$scope.gridOptions.data = angular.copy($scope.datas);
		}
			
		else{
			$scope.gridOptions.data=[];
			GenericAlertService.alertMessage('Client  details are not available for given search criteria', "Warning");
		}
		}
		
		//console.log($scope.account.name);
		else if($scope.account1.name != ""){
			$scope.datas1=[];
		for(var i=0; i<$scope.clients.length;i++){
			if($scope.clients[i].name == $scope.account1.name ){
				$scope.datas1.push($scope.clients[i]);
			}
		}
		if($scope.datas1 !=""){
			$scope.gridOptions.data = angular.copy($scope.datas1);
			}
		else{
			$scope.gridOptions.data=[];
			GenericAlertService.alertMessage('Client details are not available for given search criteria', "Warning");
		}
		}
	}
	$scope.reset1=function(){
		$scope.account1.name = '';
		$scope.account1.code = '';
		$scope.gridOptions.data = angular.copy($scope.clients);
	}
	var getCountries = function () {
		CountryService.getCountries().then(function (data) {			
			countries = data.countryInfoTOs;
			$scope.getClients();
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching countries", 'Error');
		});
	}
	getCountries();
	$scope.getClients = function () {
		select1 = false;
	//	console.log(select1)
		var req = {
			"status": "1"
		};
	
		ClientService.getClients(req).then(function (data) {
			$scope.clients = data.clientRegTOs;
	//		console.log($scope.clients)
			$scope.gridOptions.data = angular.copy($scope.clients);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching client details", 'Error');
		});
	}

	var clientpopup;
	var addClientservice = {};
	var isDisabled;
	var client1;
	var select1;
	$scope.clientRowSelect=function(client){
//		console.log(client)
       client1=client
		if(client.selected){
			select1=client.selected;
		}
		else{
			select1=null;
		}
	}
	$scope.clientDetails = function (actionType, client, index = -1) {
//		console.log(actionType);
		
		if (actionType == 'Edit') {
			if(select1 == true ){
				isDisabled = true;
				client=client1;
				}
				clientpopup = addClientservice.addClients(actionType, client, isDisabled, $scope.clients);
		clientpopup.then(function (data) {
			utilservice.addItemToArray($scope.clients, "id", data.clientRegTOs);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching client details", 'Error');
		});
		}
		 else if(actionType == 'Add') {
			isDisabled = false;
			clientpopup = addClientservice.addClients(actionType, client, isDisabled, $scope.clients);
			clientpopup.then(function (data) {
			utilservice.addItemToArray($scope.clients, "id", data.clientRegTOs);
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while fetching client details", 'Error');
		});
		}
//		console.log(select1)
		if(select1 == false && actionType != 'Add' ){
		GenericAlertService.alertMessage('Please select atleast one row to modify', "Warning");
		return;
		}
		$scope.getClients();
//		console.log(select1)
		currentRow = Number(index);
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

				$scope.checkFile = function (file,clientDoc) {
					var allowedFiles = [".csv", ".xls", ".pdf", ".xlsx",".doc", ".docx", ".png", ".jpg", ".jpeg"];
					var regex = new RegExp("([a-zA-Z0-9\s_\\.\(\)\-:])+(" + allowedFiles.join('|') + ")$");
					if (!regex.test(file.name.toLowerCase().replace(/\s/g, ""))) {
						$scope.clientDoc.errorMessage = "Supported formats csv,xls,pdf,xlsx,jpg,PNG,JPEG,DOC,DOCX ";
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
						getCountries();
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
	}
	  var linkCellTemplate = '<input type="checkbox" ng-model="row.entity.selected" ng-change="grid.appScope.clientRowSelect(row.entity)">';
      	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
					    {  name: 'select',width:'4%',cellClass:'justify-center',headerCellClass:'justify-center',cellTemplate: linkCellTemplate,displayName: "Select", headerTooltip : "Select", groupingShowAggregationMenu: false},
						{ name: 'code', displayName: "Client Code", headerTooltip : "Client Code"},
						{ field: 'name', displayName: "Client Name", headerTooltip: "Client Name", groupingShowAggregationMenu: false},
						{ field: 'businessType', displayName: "Business", headerTooltip: "Business", groupingShowAggregationMenu: false},
						{ field: 'email', displayName: "Email ID", headerTooltip: "Email ID", groupingShowAggregationMenu: false},
						{ field: 'fax', displayName: "Fax", headerTooltip: "Fax", groupingShowAggregationMenu: false},
						{ field: 'phone', displayName: "Phone", headerTooltip: "Phone", groupingShowAggregationMenu: false},
						{ field: 'mobile', displayName: "Mobile", headerTooltip: "Mobile", groupingShowAggregationMenu: false},
						{ field: 'country', displayName: "Country", headerTooltip: "Country", groupingShowAggregationMenu: false},
						{ field: 'webSiteURL', displayName: "Website", headerTooltip: "Website", groupingShowAggregationMenu: false},
						{ field: 'contactPerson', displayName: "Contact", headerTooltip: "Contact", groupingShowAggregationMenu: false},
						{ field: 'registeredUsers', displayName: "Max Users", headerTooltip: "Max Users", groupingShowAggregationMenu: false},
						{ field: 'licence', displayName: "Expiry Date", headerTooltip: "Expiry Date", groupingShowAggregationMenu: false},
						{ field: 'address',displayName: "Address", headerTooltip: "Address", groupingShowAggregationMenu: false},
						{ name: 'clientDetailsFileName',cellClass:"justify-center",headerCellClass:"justify-center",displayName: "Doc", headerTooltip: "Doc", groupingShowAggregationMenu: false,
						cellTemplate:'<a  class="fa fa-download" title="Click To Download"  href="/static/{{row.entity.clientDetailsFileName}}" download ng-if="row.entity.clientDetailsFileName">{{row.enity.clientDetailsFileName}}</a></div>'},
						{ field: 'remarks',cellClass:"justify-center",headerCellClass:"justify-center", displayName: "Comments", headerTooltip: "Comments", groupingShowAggregationMenu: false,
					    cellTemplate:'<div name="comment" class="fa fa-comment"  flip="horizontal" ng-click="grid.appScope.show(row.entity.remarks)"></div>'}
						];
					let data = [];
					$scope.getClients();
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Admin_Client Registration_Client List");
				}
				});


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
