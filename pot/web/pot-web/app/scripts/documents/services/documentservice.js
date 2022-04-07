'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # PreContract Service in the potApp.
 */
app.factory('DocumentService', ["Restangular", "Principal", "$http", "appUrl", "$q", function (Restangular, Principal, $http, appUrl, $q) {
	var FileSaver = require('file-saver');
	return {
		saveProjDocFolders : function(req) {
			var folders = Restangular.one("document/saveProjDocFolders").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return folders;
		},
		getProjDocFolders : function(req) {
			console.log("getProjDocFolders from service");
			console.log(req);
			var result = Restangular.one("document/getProjDocFolders").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		getProjDoc : function(req) {
			var result = Restangular.one("document/getProjDoc").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			console.log(result);
			return result;
		},

		getProjDocFilesByFolder : function(req) {
			var result = Restangular.one("document/getProjDocFilesByFolder").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveProjDocFilesByFolder : function(filesArray, data) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();               

			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;                        
					}
				});
			}
			return $http({
				headers: {'Content-Type': undefined,
				'pottoken': token },
				cache: false,
				processData: false,
				method: 'POST',
				url: appUrl.appurl + 'document/saveProjDocFilesByFolder',
				transformRequest: function (data) {
					var formData = new FormData();
					angular.forEach(data.files, function(file, key) {
						formData.append("files",file);
					});
					formData.append("projDocFileSaveReqStr", angular.toJson(data.model));
					return formData;
				},
				data: {model:data,files : filesArray}
			});
		},
		getProjDocFolderPermissions : function(req) {
			var result = Restangular.one("document/getProjDocFolderPermissions").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		saveProjDocFolderPermissions : function(req) {
			var result = Restangular.one("document/saveProjDocFolderPermissions").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;

		},
		getFolderPermissions : function(req) {
			var result = Restangular.one("document/getFolderPermissions").customPOST(req, '', {}, {
				ContentType : 'application/json'
			});
			return result;
		},
		downloadDocs : function(fileId) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();               

			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;                        
					}
				});
			}
			var url = appUrl.appurl + "document/projDocDownloadFile?fileId=" + fileId + "&pottoken=" + token;
			var popUp = window.open(url);
			if (popUp == null || typeof (popUp) == 'undefined') {
				alert('Pop-up is blocked, please enable poup for this site and try again.');
			}
			else {
				popUp.focus();
			}
		},
		deleteProjDocs: function (fileIdsArray) {
			var req = { "fileIds": fileIdsArray };
			var result = Restangular.one("document/projDocDeleteFile").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		}, 
		deleteProjFolders: function (req) {
			var result = Restangular.one("document/projDocDeleteFolder").customPOST(req, '', {}, {
				ContentType: 'application/json'
			});
			return result;
		},		
		getPurchaseDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getPurachaseAcqulisitionDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getSaleRecordsDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getSaleRecordsDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getRentalHistoryDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getRentalHistoryDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getShortTermDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getShortTermDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getLongTermDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getLongTermDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getCarParkingDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getCarParkingDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getPeriodicalDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getPeriodicalDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },		
        getRepairsDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getRepairsDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },
        getAssetLifeDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getAssetLifeDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },
        getAssetCostDocuments : function(req) {
            var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/register/app/register/getAssetCostDocuments",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },
        getTemplateCategories : function(req) {
    		console.log("catgory mstr id from document service:"+req);
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getTemplateCategories",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		saveTempCategory : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/saveTemplateCategory",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		updateTemplCategory : function(req) {
			console.log("updateTemplCategory functon from document service");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/updateTemplateCategory",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		getTemplatesList : function(req) {
			console.log("getTemplatesList function");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getSampleTemplates",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		getCentralTemplatesList : function(req)
		{
			console.log("getCentralTemplatesList function");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getCentralTemplates",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
				console.log(response.data);
            });
            return deferred.promise;
		},
		getProjectTemplatesList : function(req)
		{
			console.log("getProjectTemplatesList function");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getProjectTemplates",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		saveNewTemplate : function(req) {
			console.log("saveNewTemplate function");
			console.log(req);
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/saveTemplate",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		updateTemplate : function(req) {
			console.log("updateTemplate function");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/updateTemplate",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		cloneTemplate : function(req) {
			console.log("cloneTemplate function");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/cloneTemplate",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		templateApproval : function(req) {
			console.log("templateApproval function");
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/templateApproval",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		searchProjectTemplates : function(req) {
			console.log(req);
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/searchProjTemplates",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		createProjectForm : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/createProjForm",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		getProjFormsList : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getProjectForms",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		transferTemplates : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/transferTemplates",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
				console.log(response);
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		getCategoryTemplates : function() {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getCategoryTemplates",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		getSampleTemplsMaxTemplId : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getSampleTemplsLastInsertedId",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		getCentralTemplsMaxTemplId : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getCentralTemplsLastInsertedId",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		getProjTemplsMaxTemplId : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getProjTemplsLastInsertedId",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		saveProjTemplatesProposal : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/saveProjectTemplatesProposal",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		getProposalList : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getProposalList",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		downloadProjDocs : function(req) {
			var token = null;
			if (Principal.isIdentityResolved()) {
				token = Principal.potToken();
			} else {
				Principal.identity().then(function (account) {
					if (account.token) {
						token = account.token;
					}
				});
			}			
			//console.log(req);
			var app_url = appUrl.originurl + "/app/document/downloadProjDocs?recordId=" + req.recordId + "&pottoken=" + token
			$http({method: 'GET', url: app_url,responseType:"arraybuffer"}).then(function(result){
				//console.log(result);
				var file = new Blob([result.data], {
					type:  'text/plain'
				});
				FileSaver.saveAs(file, req.fileName);
			});
		},
		getProjDocumentsByProjectId : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/getProjDocumentsByProjectId",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
		},
		updateProjectForm : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/updateProjForm",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		wfSubmitForApproval : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/workflowSubmitForApproval",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		},
		wfTemplateApproval : function(req) {
			var deferred = $q.defer();
            $http({
            	url : appUrl.originurl + "/app/document/workflowTemplateApproval",
                method : "POST",
                data: JSON.stringify(req),
                headers: {}
            }).then(function(response) {
                deferred.resolve(response.data);
                console.log(response.data);
            });
            return deferred.promise;
		}
	}
}]);
