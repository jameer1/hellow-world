'use strict';
app.factory('CompanyContactFactory',	["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "CompanyService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, $rootScope,CompanyService,GenericAlertService ) {
		var companyContactPopUp;
		var service = {};
		service.companyContactPopUp = function(actionType,editContacts,companyId,data) {
		//	console.log(data)
		var deferred = $q.defer();
		companyContactPopUp = 	ngDialog.open({
			template : 'views/centrallib/companylist/addcontactspopup.html',
			closeByDocument : false,
			showClose : false,
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : ['$scope',function($scope) {
						var selectedContacts = [];
						$scope.contactList = [];
						$scope.action = actionType;
						if (actionType === 'Add') {
							$scope.contactList.push({
										'companyId' :  companyId,
										'contactName' : '',
										'email' : '',
										'mobile' : '',
										'phone' : '',
										'designation' : '',
										'status' : '1',
										'selected' : false
									});
						} else {
							$scope.contactList = angular.copy(editContacts);
							editContacts = [];
						}
								$scope.addRows = function() {
									$scope.contactList.push({
												'companyId' :  companyId,
												'contactName' : '',
												'email' : '',
												'mobile' : '',
												'phone' : '',
												'designation' : '',
												'status' : '1',
												'selected' : false
											});
								},						
									$scope.saveContacts = function() {
										var flag = false;
										console.log(data)
									for (var i = 0; i < data.length; i++) {
										for(var j=0;j<$scope.contactList.length;j++){
											if (data[i].contactName == $scope.contactList[j].contactName && data[i].email == $scope.contactList[j].email && data[i].mobile == $scope.contactList[j].mobile && data[i].phone == $scope.contactList[j].phone && data[i].designation == $scope.contactList[j].designation) {
											//	console.log("hello")
											flag = true;

											//	break;
										}
										}
										
									}
										if (flag) {
											GenericAlertService.alertMessage('Duplicate Contacts are not allowed', 'Warning');
											return;
										}
										var req = {
											"contactsTOs": $scope.contactList,
											'companyId': companyId,
										};
										blockUI.start();
										CompanyService.saveContacts(req).then(function(data) {
											blockUI.stop();
											var results = data.contactsTOs;
											// var succMsg = GenericAlertService.alertMessageModal('Company Contact(s) is/are '+ data.message,data.status);
											var succMsg = GenericAlertService.alertMessageModal('Company Contact(s) saved successfully', "Info");
											succMsg.then(function(popData) {
												ngDialog.close(companyContactPopUp);
												var returnPopObj = {
													"contactsTOs": results
												};
												deferred.resolve(returnPopObj);
											});
										},
											function(error) {
												blockUI.stop();
												GenericAlertService.alertMessage('Contact(s) is/are failed to save', 'Error');
											});
									},
								$scope.contactsPopUpRowSelect = function(contact) {
									if (contact.selected) {
										selectedContacts.push(contact);
									} else {
										selectedContacts.splice(selectedContacts.indexOf(contact), 1);
									}
								},
								$scope.deleteContactRows = function() {
									if (selectedContacts.length == 0) {
										GenericAlertService.alertMessage('Please select atleast one row to delete',"Warning");
									}
									else if (selectedContacts.length < $scope.contactList.length) {
										angular.forEach(selectedContacts,function(value,key) {
															$scope.contactList.splice($scope.contactList.indexOf(value),1);
														});
										selectedContacts = [];
										GenericAlertService.alertMessage("Row(s) deleted successfully","Info");
									} else if(selectedContacts.length > 1){
										GenericAlertService.alertMessage('Please leave atleast one row',"Warning");
									}else if(selectedContacts.length == 1){
										$scope.contactList[0] ={
											'companyId' :  companyId,
											'contactName' : '',
											'email' : '',
											'mobile' : '',
											'phone' : '',
											'designation' : '',
											'status' : '1',
											'selected' : false
										};
										selectedContacts = [];
										GenericAlertService.alertMessage(	'Please leave atleast one row',"Warning");
									}
								}
					} ]
		});
						return deferred.promise;
					}
					return service;
				}]);
