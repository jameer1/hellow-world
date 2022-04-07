'use strict';
app.factory(
		'MaterialClassificationFactory',
		["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "MaterialClassService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService,
				MaterialClassService) {
			var plantServiceMaterialPopUp;
			var service = {};
			service.plantServiceMaterialPopUp = function(plantMaterialDetails) {
				var deferred = $q.defer();
				plantServiceMaterialPopUp = ngDialog.open({
					template : 'views/common/materialclassification.html',
					className : 'ngdialog-theme-plain ng-dialogueCustom1',
					showClose : false,
					controller : [
							'$scope',
							function($scope) {

								$scope.plantServiceMaterialDetails = plantMaterialDetails;
								$scope.plantServiceMaterialPopUp = function(projMaterialTO) {
									if (projMaterialTO.item) {
										var returnServiceMaterialTO = {
											"projMaterialTO" : projMaterialTO
										};
										deferred.resolve(returnServiceMaterialTO);
										$scope.closeThisDialog();

									} else {
										GenericAlertService.alertMessage(
												"Please Select Only Material Items", 'Warning');
										return;
									}
								}
								$scope.itemId = 1;
								$scope.isExpand = true;
								$scope.itemClick = function(itemId, expand) {
									$scope.itemId = itemId;
									$scope.isExpand = !expand;
								};
							} ]

				});
				return deferred.promise;
			},

			service.getPlantMaterialDetails = function(req) {
				var deferred = $q.defer();
				var plantMaterialDetailsPromise = MaterialClassService.getMaterialGroups(req);
				plantMaterialDetailsPromise.then(function(data) {
					var plantServiceMaterialDetails = [];
					plantServiceMaterialDetails = data.materialClassTOs;
					;
					var plantMaterialDetailsPopup = service
							.plantServiceMaterialPopUp(plantServiceMaterialDetails);
					plantMaterialDetailsPopup.then(function(data) {
						var returnPopObj = {
							"projMaterialTO" : data.projMaterialTO
						};
						deferred.resolve(returnPopObj);
					}, function(error) {
						GenericAlertService.alertMessage(
								"Error occured while selecting Material Details", 'Error');
					})
				}, function(error) {
					GenericAlertService.alertMessage(
							"Error occured while getting Material Details", "Error");
				});
				return deferred.promise;
			}

			return service;
		}]).filter('materialCommonSelectFilterTree', function() {
	function recursive(obj, newObj, level, itemId, isExpand) {
		angular.forEach(obj, function(o, key) {
			if (o.childMaterialItemTOs && o.childMaterialItemTOs.length != 0) {
				o.level = level;
				o.leaf = false;
				newObj.push(o);
				o.expand = isExpand;
				if (o.expand == true) {
					recursive(o.childMaterialItemTOs, newObj, o.level + 1, itemId, isExpand);
				}
			} else {
				o.level = level;
				if (o.item) {
					o.leaf = true;
					newObj.push(o);
				} else {
					obj.splice(obj.indexOf(o), 1);
				}
				return false;
			}
		});
	}

	return function(obj, itemId, isExpand) {
		var newObj = [];
		recursive(obj, newObj, 0, itemId, isExpand);
		return newObj;
	}
});
