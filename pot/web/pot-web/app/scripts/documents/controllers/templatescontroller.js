'use strict';
app.config(["$stateProvider", function ($stateProvider) {
	$stateProvider.state("templates", {
		url: '/templates',
		data: {
			roles: []
		},
		params: {
			templateMode : null,
			selected_category_id : null
		},
		views: {
			'content@': {
				templateUrl: 'views/documents/templates.html',
				controller: 'SampleTemplatesCtrl'
			}
		}
	})
	.state("centraltemplates",{
		url: '/centraltemplates',
		params: {
			mode: null,
			selectedTemplate : null,
			selectedCategory : null
		},
		views: {
			'content@': {
				templateUrl: 'views/documents/centraltemplates.html',
				controller: 'CentralTemplatesCtrl'
			}
		}
	})
	.state("projecttemplates",{
		url: '/projecttemplates',
		params: {
			mode: null,
			selectedTemplate : null,
			selectedCategory : null,
			selectedProject : null
		},
		views: {
			'content@': {
				templateUrl: 'views/documents/projecttemplates.html',
				controller: 'ProjectTemplatesCtrl'
			}
		}
	})
	.state("projectforms",{
		url: '/projectforms',
		views: {
			'content@': {
				templateUrl: 'views/documents/projectforms.html',
				controller: 'ProjectFormsCtrl'
			}
		}
	})
	.state("fromcentraltempls",{
		url: '/fromcentraltempls',
		views: {
			'content@': {
				templateUrl: 'views/documents/centraltemplatespopup.html',
				controller: 'FromCentralTemplatesCtrl'
			}
		}
	})
	.state("createtemplate",{
		url: '/createtemplate',
		params: {
	        templateCategoryId : null,
	        categoryMstrId : null,
	        templateJson : null,
	        mode : null,
	        templateId : null,
	        templateName : null,
	        createdBy : null,
	        formName : null,
	        status : null,
	        templateType : null,
	        crmId : null,
	        projectId : null,
	        versionStatus : null,
	        formsCount : null,
			templateType : null,
			templateCode : null,
			workflowId : null,
			templateStatus : null,
			fromSource : null,
			internalApprovalUserId : null,
			externalApprovalUserId : null
	    },
		views: {
			'content@': {
				templateUrl: 'views/documents/index.html',
				controller: 'CreateTemplateCtrl'
			}
		}
	})
	.state("createform",{
		url: '/createform',
		params: {
	        projectId : null,
			templateCategoryId : null,
			projectTemplateId : null,
			templateJson : null,
			formName : null,
			mode : null,
			formType : null,
			formJson : null,
			formVersion : null,
			formId : null,
			fromSource : null
	    },
		views: {
			'content@': {
				templateUrl: 'views/common/createform.html',
				controller: 'CreateFormCtrl'
			}
		}
	})
	.state("plugin",{
		url: '/plugin',
		params: {
	        projectId : null,
			templateCategoryId : null,
			projectTemplateId : null,
			templateJson : null
	    },
		views: {
			'content@': {
				templateUrl: 'views/documents/sample.html',
				controller: 'PluginCtrl'
			}
		}
	})
	.state("webform",{
		url: '/webform',		
		views: {
			'content@': {
				templateUrl: 'views/documents/sampleweb.html',
				controller: 'WebFormCtrl'
			}
		}
	})
}])
.controller('PluginCtrl',["$scope","$rootScope","Principal", function($scope,$rootScope,Principal){
	let test_template_json = {
			"templateJson":[{"label":"First Name","tableView":true,"key":"firstName","type":"textfield","input":true},{"label":"Reason for leave","autoExpand":false,"tableView":true,"key":"reasonForLeave","type":"textarea","input":true},{"label":"No. of days leave","mask":false,"spellcheck":true,"tableView":false,"delimiter":false,"requireDecimal":false,"inputFormat":"plain","key":"noOfDaysLeave","type":"number","input":true},{"type":"button","label":"Submit","key":"submit","disableOnInvalid":true,"input":true,"tableView":false}]
		}
	let isUpdated = "N";
	var plugin_json;
	var json_data;
	//console.log($rootScope);
	$scope.loggedin_user_displayname = "";
	var loggedin_user = "Pavani Kota";
	Principal.identity().then(function (account) {
		if (account.token) {
			console.log(account.displayName);  
			$scope.loggedin_user_displayname = account.displayName;                 
		}
	});
	console.log($scope.loggedin_user_displayname);
	$scope.doInit = function()
	{
		var builder = new Formio.FormBuilder(document.getElementById('builder'), {
		    "display": "form"
		});
		builder.instance.ready.then(function() {				    
		    // Set schema 
		    builder.setForm({components: test_template_json.templateJson});
		  
		    builder.instance.on('saveComponent', function() {				
		    	json_data = JSON.stringify(builder.instance.schema.components);
		    	console.log(json_data);
				test_template_json.templateJson = json_data;
				isUpdated = "Y";
		    });
		});
	},
	$scope.save = function()
	{
		console.log("save function");
		if( isUpdated.indexOf('Y') != -1 )
		{
			console.log("Y condition");
			plugin_json = json_data;			
		}
		else
		{
			console.log("N condition");
			plugin_json = JSON.stringify(test_template_json.templateJson);
		}
		console.log(plugin_json);
	}
}])
.controller('SampleTemplatesCtrl',["$scope","$rootScope","GenericAlertService","DocumentService","ngDialog","$window","$state","$stateParams","TemplateFactory", "stylesService", "ngGridService",function($scope,$rootScope,GenericAlertService,DocumentService,ngDialog,$window,$state,$stateParams,TemplateFactory, stylesService, ngGridService){
	let colorcodes = ["000099","8CFF66","FF9900","558000","FB3CD5","FF471A","3333CC","CCCCFF","CCCC00","00B3B3","990099","B35900","999966","993366","FFFF00","00FFFF","808080","66CDAA","FFEBCD","000000","808080","FB2810"];	
	$scope.stylesSvc = stylesService;
	
	$scope.categoryname = "";
	$scope.mstrCategoryId = 1;
	$scope.selected_category_id = $stateParams.selected_category_id ? $stateParams.selected_category_id : 0;
	
	$scope.showCreateOwnDiv = false;
	$scope.disableButton = true;
	$scope.showCreateTmplDiv = false;
	$scope.showMsgDiv = false;
	$scope.showTmplMessage = "";
	$scope.templateType = "TIMELINE";
	$scope.createNewTmplButton = true;
	
	$scope.selected_template = "";
	$scope.templateMode = $stateParams.templateMode ? $stateParams.templateMode : "";
	$scope.templateCategoriesList = [];
	$scope.sampleTemplatesList = {};
	$scope.stemplates_categories_count = 0;
	$scope.stemplates_count = 0;
	$scope.displayCategoryNameError = true;
	$scope.lastTemplateId = 0;
	console.log($rootScope.account);
	console.log("stateparams:",$stateParams);
	
	$scope.getSampleCategories = function(category) {		
		let req = {
			categoryMstrId: $scope.mstrCategoryId
		};
		console.log($stateParams.selected_category_id);
		console.log($stateParams.templateMode);
		DocumentService.getTemplateCategories(req).then(function (data) {			
			$scope.templateCategoriesList = data.templateCategoriesTOs;
			angular.forEach($scope.templateCategoriesList,function(value,key){
				value.editCategoryName = value.categoryName;
			});
			//$scope.templateCategoriesList[i].editCategoryName = $scope.templateCategoriesList[i].categoryName;
			console.log($scope.templateCategoriesList);
			$scope.stemplates_categories_count = data.templateCategoriesTOs.length;
		})
		
		/*let template_req = {
			categoryMstrId : $scope.mstrCategoryId
		}
		DocumentService.getSampleTemplsMaxTemplId(template_req).then(function(data){
			console.log(data);
			$scope.lastTemplateId = data;
		})*/
		
		if( $stateParams.templateMode != "" && ( $stateParams.templateMode == "NEW_TEMPL" || $stateParams.templateMode == "From Sample Templates" || $stateParams.templateMode == "createTemplateCopy" ) )
		{
			$scope.templatesList = [];
			console.log("selected category id from getSampleCategories:"+$scope.selected_category_id);
			let req = {
				templateCategoryId : $scope.selected_category_id,
				categoryMstrId : $scope.mstrCategoryId
			};
			DocumentService.getTemplatesList(req).then(function(data) {
				console.log(data);
				$scope.mode = $stateParams.templateMode;
				$scope.templatesList = data.templatesTOs;
				$scope.stemplates_count = data.templatesTOs.length;
				console.log("templates count:");
				console.log($scope.stemplates_count);				
				if( $stateParams.templateMode !== "" )
				{
					$scope.templatesList = [];
					for( let j=0; j<data.templatesTOs.length; j++)
					{
						data.templatesTOs[i].sampleTemplCode = data.templatesTOs[i].templateCode+"-"+data.templatesTOs[i].templateId;
						if( $stateParams.templateMode == "From Sample Templates" )
						{
							if( data.templatesTOs[j].status == "READY_TO_USE" || data.templatesTOs[j].status == "SUPERSEDED" )
							{
								$scope.templatesList.push(data.templatesTOs[j]);
							}
						}
						else
						{
							$scope.templatesList.push(data.templatesTOs[j]);
						}
					}
				}
			})
		}
	},
	$scope.addTemplCategory = function() {
		console.log($scope.templateCategoriesList);
	},
	$scope.saveTemplateCategory = function()
	{
		let newColorCode = '';
		let colorCodeStr = '';
		console.log($scope.templateCategoriesList);
		for( let cat = 0 ; cat<$scope.templateCategoriesList.length ; cat++ ) {
			let current_element = $scope.templateCategoriesList[cat];
			if( colorCodeStr == '' ) {
				colorCodeStr += current_element.colorCode;
			}
			else
			{
				if( colorCodeStr.indexOf(current_element.colorCode) == -1 ) {
					colorCodeStr += ","+current_element.colorCode;
				}
			}		
		}
		
		for(let j=0;j<colorcodes.length;j++)
		{
			if( colorCodeStr.indexOf( colorcodes[j] ) == -1 && newColorCode == '' ) {
				newColorCode = colorcodes[j];
			}
		}
		//console.log("saveTemplateCategory function");
		if( $scope.categoryname == '' )
		{
			GenericAlertService.alertMessage("Please enter the category name", "Warning");
			return;
		}
		else
		{
			let req = {
				categoryName : $scope.categoryname,
				createdBy : $rootScope.account.userId,
				categoryMstrId : $scope.mstrCategoryId,
				colorCode : newColorCode
			}	
			console.log(req);		
			let isDuplicate = false;
			let templ_error = false;
			let templateCategoryListCnt = $scope.templateCategoriesList.length+1;
			if( templateCategoryListCnt > colorcodes.length )
			{
				GenericAlertService.alertMessage("Could not create category more than 20", "Warning");
				templ_error = true;
			}
			
			if( templ_error == false )
			{
				angular.forEach($scope.templateCategoriesList,function(value,key){
					console.log(value);
					if( value.categoryName.indexOf( $scope.categoryname ) > -1 )
					{
						isDuplicate = true;
					}
				});
				if( isDuplicate )
				{
					GenericAlertService.alertMessage("Please choose the different name.", "Warning");
				}
				else
				{
					DocumentService.saveTempCategory(req).then(function (data) {
						$scope.templateCategoriesList.push(data.templateCategoriesTOs[0]);
						GenericAlertService.alertMessage("Template category created successfully.....", "Info");
						//console.log($scope.templateCategoriesList);
					})
				}
			}			
		}
	},
	$scope.editCategoryName = function(categoryData){
		console.log("editCategoryName");
		console.log(categoryData);
		document.getElementById("editcategory"+categoryData.categoryId).value = categoryData.editCategoryName;		
	},
	$scope.updateTemplateCategory = function(event) {
		let category_id = event.target.id;
		let modCategoryId = parseInt( category_id.replace( "update" , "" ) );
		let textelementid = category_id.replace("update","editcategory");
		let category_name = document.getElementById(textelementid).value;
		let req={
			categoryId: modCategoryId,
			categoryName: category_name,
			categoryMstrId : $scope.mstrCategoryId
		};
		console.log("updatetemplatecategory function");
		if( category_name.trim().length == 0 )
		{
			GenericAlertService.alertMessage("Please enter the category name", "Warning");
			return;
		}
		else
		{
			let isDuplicate = false;
			angular.forEach($scope.templateCategoriesList,function(value,key){
				console.log(value);
				if( value.categoryName.indexOf( category_name ) > -1 )
				{
					isDuplicate = true;
				}
			});
			if( isDuplicate )
			{
				GenericAlertService.alertMessage("Choose different category name.", "Warning");
			}
			else
			{
				DocumentService.updateTemplCategory(req).then(function(data){
					GenericAlertService.alertMessage("Template category updated successfully.....", "Info");	
					var templateList =  $scope.templateCategoriesList;
					for(let i=0;i<templateList.length;i++)
					{
						let current_category = templateList[i];
						if( current_category.categoryId == modCategoryId )
						{
							$scope.templateCategoriesList[i].categoryName = category_name;
						}
					}
				})	
			}			
		}		
	},
	$scope.getTemplates = function(event)
	{
		$scope.mode = 'VIEW';
		let category_element_id = event.target.id;
		let categoryId = parseInt( category_element_id.replace( "span" , "" ) );
		$scope.selected_category_id = categoryId;
		$scope.selected_category_color_code = "";
		$scope.templatesList = [];
		if( $scope.selected_category_id != 0 ) {
			for(var i=0;i<$scope.templateCategoriesList.length;i++) {
				if( categoryId == $scope.templateCategoriesList[i].categoryId ) {
					$rootScope.selectedTemplCategory = $scope.templateCategoriesList[i];
					$scope.selected_category_color_code = $scope.templateCategoriesList[i].colorCode;
				}
			}
		}
		let req = {
			templateCategoryId : categoryId,
			categoryMstrId : $scope.mstrCategoryId
	  	};
		DocumentService.getTemplatesList(req).then(function(data){
			console.log(data.templatesTOs.length);
			for(var i=0; i<data.templatesTOs.length; i++)
			{				
				if( data.templatesTOs[i].templateType == "WORKFLOW" )
				{
					//console.log(data.templatesTOs[i]);
					data.templatesTOs[i].currentWorkflowStatus = $scope.getCurrentWorkflowStatus(data.templatesTOs[i].workflowStageId,data.workflowTOs);
				}
				data.templatesTOs[i].sampleTemplCode = data.templatesTOs[i].templateCode+"-"+data.templatesTOs[i].templateId;			
				$scope.templatesList.push(data.templatesTOs[i]);				
			}
			console.log($scope.templatesList);
			 for(var sample of $scope.templatesList){
				sample.versionstatus="V"+sample.versionStatus+".0";
			}
			
			$scope.gridOptions.data= angular.copy($scope.templatesList);
			
			//$scope.templatesList = data.templatesTOs;
			$rootScope.sampleTemplatesList = data;	
			$scope.selected_category_id = categoryId;
			$scope.stemplates_count = data.templatesTOs.length;			
		})
		$scope.createNewTmplButton = false;
	},	
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ field: 'sampleTemplCode', displayName: "Template Id", headerTooltip: "Template Id", },
						{ field: 'templateName', displayName: "Name of Template", headerTooltip: "Name of Template",},
						{ name: 'versionstatus', displayName: "Template Version", headerTooltip: "Template Version",},
						{ field: 'formName', displayName: "Form Name", headerTooltip: "Form Name", },
						{ field: 'createdBy', displayName: "Created By", headerTooltip: "Created By",},
						{ name: 'createdOn',cellFilter:'date',displayName: " Creation / Updated (Date)", headerTooltip: " Creation / Updated (Date)",},
						{ field: 'templateType',displayName: "Process Type", headerTooltip: "Process Type",},
						{ field: 'formsCount', displayName: "Number of Forms", headerTooltip: "Number of Forms",},
						{ name: 'status',displayName: "Status", headerTooltip: "Status",},
					];
					let data = [];
					$scope.gridOptions = ngGridService.initGrid($scope, columnDefs, data, "Documents_Templates&Forms_Sample Templates ");
				}
			});
	$scope.getCurrentWorkflowStatus = function(workflowId,workflowData) {
		let currentWorkflowStatus = null;
		for(var k=0;k<workflowData.length;k++)
		{
			if( workflowId == workflowData[k].workflowId )
			{
				currentWorkflowStatus = workflowData[k].workflowStatus;
			}			
		}
		return currentWorkflowStatus;
	},	
	$scope.loadSampleTemplates = function(templateCategoryId){
		console.log("loadSampleTemplates function");
		$scope.selected_category_id = templateCategoryId;
		$scope.mode = 'VIEW';
		/*DocumentService.getTemplatesList(req).then(function(data){		
			$scope.templatesList = data.templatesTOs;			
			$rootScope.sampleTemplatesList = data;			
			$scope.stemplates_count = data.templatesTOs.length;
		})
		$scope.createNewTmplButton = false;*/
	},
	$scope.createTemplate = function() {
		var createTemplatePopup = ngDialog.open({
			template: 'views/common/createtemplate.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.error = false;
				console.log("rootscope sampletemplateslist:",$rootScope.sampleTemplatesList);
				$scope.nextStageBtn = false;
				$scope.nextBtn = true;		
				var templshortcut = $rootScope.selectedTemplCategory.categoryName.split(' ').map(i=>i.charAt(0).toUpperCase()).join('');
				if(templshortcut.length == 1){
					templshortcut = $rootScope.selectedTemplCategory.categoryName.substring(0,3).toUpperCase();
				}
				//console.log($scope.lastTemplateId);
				//$scope.templateCode = "SMPL-"+templshortcut+"-"+($scope.lastTemplateId+1);
				$scope.templateCode = "SMPL-"+templshortcut;
				$scope.next = function(templateList) {
					$scope.displayTemplNameError = false;
					$scope.displayFormNameError = false;
					$scope.errormsgs = [];
					console.log("selected template from next function:"+$scope.selected_category_id);
					if( $scope.selected_template == 'From Sample Templates' )
					{
						$scope.nextBtn = false;
						$scope.nextStageBtn = true;
						$scope.showListDiv = false;
						$scope.showCreateTmplDiv = true;
						$scope.showTemplateTypeDiv = false;
						//$state.go('templates',{selected_category_id:$scope.selected_category_id,templateMode:'From Sample Templates'});
						angular.forEach(templateList, function (value,key) {
							console.log(templateList[key]);
			                if(templateList[key].selected) {
			                    templateList[key].selected = true;
			                }
							else
							{
								templateList[key].selected = false;
							}
		            	});
					}
					else
					{
						var form_name = document.getElementById("form_name").value;
						var template_name = document.getElementById("template_name").value;
						var template_category = document.getElementById("template_category").value;
						console.log("This is from createNewTemplate function");
						let newTemplId = null;
						if( form_name != "" && template_name != "" )
						{
							var template_request = {
								formName : form_name,
								templateName : template_name,
								templateCategoryId : $scope.selected_category_id,
								status : 'DRAFT',
								categoryMstrId : $scope.mstrCategoryId,
								createdBy : $rootScope.account.userId,
								templateType : $scope.templateType,
								versionStatus : 1,
								templateCode : $scope.templateCode,
								formsCount : 0,
								mode : 'NEW_TEMPL'
							};
							console.log(template_request);							
							$state.go('createtemplate',template_request);
						}
						else
						{
							if( template_name.trim().length == 0 )
							{
								$scope.errormsgs.push('Please enter the template name');
								$scope.displayTemplNameError = true;
							}
							if( form_name.trim().length == 0 )
							{
								$scope.errormsgs.push('Please enter the form name');
								$scope.displayFormNameError = true;
							}
							$scope.error = true;
						}
						if( $scope.error == false )
						{
							ngDialog.close(createTemplatePopup);
						}
					}					
				},
				$scope.templateSelection = function(category) {
					$scope.selected_template = category;
					if( category == "Create Own" )
					{
						$scope.showCreateTmplDiv = true;
						$scope.templateMode = "NEW_TEMPL";
						$scope.showListDiv = false;
						$scope.showTemplateTypeDiv = true;
					}
					else
					{
						$scope.showCreateTmplDiv = false;
						$scope.templateMode = "COPY_TEMPLATE";
						$scope.showTemplateTypeDiv = false;
						$scope.showListDiv = true;
						
						console.log($rootScope.sampleTemplatesList);
						$scope.templateList = [];
						for(var k=0;k<$rootScope.sampleTemplatesList.templatesTOs.length;k++)
						{
							if( $rootScope.sampleTemplatesList.templatesTOs[k].status == 'READY_TO_USE' )
							{
								$scope.templateList.push($rootScope.sampleTemplatesList.templatesTOs[k]);
							}								
						}
					}
					$scope.disableButton = false;
				},
				$scope.nextStage = function() {
					let templatesArry = $rootScope.sampleTemplatesList.templatesTOs;
					let current_template_json = {};
					var current_template_data;
					for(let count=0;count<templatesArry.length;count++)
					{
						if( templatesArry[count].selected )
						{
							console.log("if condition of for loop");
							current_template_json = templatesArry[count].templateJson;
							current_template_data = templatesArry[count];
						}
					}
					console.log(current_template_data);
					let form_name = document.getElementById("form_name").value;
					let template_name = document.getElementById("template_name").value;
					let template_category = document.getElementById("template_category").value;
					if( form_name.trim().length !=0 && template_name.trim().length !=0 )
					{
						let template_request = {
							templateId : current_template_data.templateId,
							formName : form_name,
							templateName : template_name,
							templateCategoryId : current_template_data.templateCategoryId,
							status : 'DRAFT',
							categoryMstrId : $scope.mstrCategoryId,
							createdBy : $rootScope.account.userId,
							templateType : current_template_data.templateType,
							templateJson : current_template_data.templateJson,
							mode: "createTemplateCopy",
							templateCode: $scope.templateCode,
							versionStatus : 1,
							formsCount : 0
						};
						console.log("tempalte request:");
						console.log(template_request);
						ngDialog.close(createTemplatePopup);
						$state.go('createtemplate',template_request);
					}
					else
					{
						$scope.error = true;
						if( form_name.trim().length ==0 )
						{
							$scope.errormsgs.push("Please enter Form Name.");
						}
						if( template_name.trim().length ==0 )
						{
							$scope.errormsgs.push("Please enter Template Name.");
						}
					}
				}
			}]
		});
	},
	$scope.editTemplate = function() {
		//console.log("changeMode:"+mode);
		console.log($rootScope.sampleTemplatesList);
		$scope.templatesList = $rootScope.sampleTemplatesList.templatesTOs;
		$scope.mode = "VIEW";
	},
	$scope.editExistingTemplate = function(template_id) {
		console.log("editExistingTemplate function");
		console.log($rootScope.sampleTemplatesList);
		let current_stempl = {};
		for( var cnt=0;cnt<$rootScope.sampleTemplatesList.templatesTOs.length;cnt++)
		{
			if( $rootScope.sampleTemplatesList.templatesTOs[cnt].templateId == template_id )
			{
				current_stempl = $rootScope.sampleTemplatesList.templatesTOs[cnt];
			}			
		}
		console.log("current sample template:");
		console.log(current_stempl);
		let template_req = {
			templateId : template_id,
			mode : 'edit',
			categoryMstrId : $scope.mstrCategoryId,
			templateCategoryId : current_stempl.templateCategoryId,
			templateJson : current_stempl.templateJson,
			formsCount : 0,
			versionStatus : 1,
			status : 'READY_TO_USE',
			templateType : current_stempl.templateType,
			workflowId : current_stempl.workflowStageId,
			templateStatus : current_stempl.status
		};
		$state.go('createtemplate',template_req);
	},
	$scope.changeMode = function(mode) {
		console.log("changeMode:"+mode);
		console.log($rootScope.sampleTemplatesList);
		$scope.templatesList = $rootScope.sampleTemplatesList.templatesTOs;
		if(mode == 'editView') {
			$scope.mode = 'edit';
				
			$scope.templatesList = [];
			for(var i=0;i<$rootScope.sampleTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.sampleTemplatesList.templatesTOs[i].status == 'DRAFT' )
				{
					$scope.templatesList.push($rootScope.sampleTemplatesList.templatesTOs[i]);
				}
				else if( $rootScope.sampleTemplatesList.templatesTOs[i].status == 'SUBMITTED_FOR_INTERNAL_APPROVAL' && $rootScope.sampleTemplatesList.templatesTOs[i].internalApprovedBy == $rootScope.account.userId )
				{
					$scope.templatesList.push($rootScope.sampleTemplatesList.templatesTOs[i]);
				}
				else if( $rootScope.sampleTemplatesList.templatesTOs[i].status == "SUBMITTED_FOR_EXTERNAL_APPROVAL" && $rootScope.sampleTemplatesList.templatesTOs[i].externalApprovedBy == $rootScope.account.userId && $rootScope.sampleTemplatesList.templatesTOs[i].isExternalApprovedRequired == "Y" )
				{
					$scope.templatesList.push($rootScope.sampleTemplatesList.templatesTOs[i]);
				}
			}	
		} 
		else if(mode == 'internalApproval') {
			$scope.templatesList = [];
			for(var i=0;i<$rootScope.sampleTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.sampleTemplatesList.templatesTOs[i].status == 'DRAFT' || $rootScope.sampleTemplatesList.templatesTOs[i].status == 'WF_STAGES_APPROVED' )
				{
					$scope.templatesList.push($rootScope.sampleTemplatesList.templatesTOs[i]);
				}
			}
			//$rootScope.sampleTemplatesList.templatesTOs;		
			$scope.mode = 'SUBMIT_FOR_INTERNAL_APPROVAL';
		}
		else if(mode == 'externalApproval') {
			console.log("exernalApproval block");
			$scope.templatesList = [];
			for(var i=0;i<$rootScope.sampleTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.sampleTemplatesList.templatesTOs[i].status == 'INTERNAL_APPROVED' && $rootScope.sampleTemplatesList.templatesTOs[i].isInternalApproved == 'Y' )
				{
					$scope.templatesList.push($rootScope.sampleTemplatesList.templatesTOs[i]);
				}
			}
			$scope.mode = 'SUBMIT_FOR_EXTERNAL_APPROVAL';
		}
		else
		$scope.mode = "VIEW";
		console.log($scope.templatesList);
	},
	$scope.submitForApproval = function(template,mode) {
		console.log("submitForApproval function");
		console.log(mode);
		let isDisplayExternalApprovalRequired = false;
		let displayApproverComments = false;
		let approvalModeArry = mode.split("_");
		let approvalMode = approvalModeArry[2];
		TemplateFactory.approvalUserPopup(template,approvalMode,"SUBMITTED",isDisplayExternalApprovalRequired,displayApproverComments).then(function(data){
			console.log(data);
			//$scope.loadSampleTemplates(data.templatesTOs[0].templateCategoryId);
		});
	},
	$scope.approveTemplate = function(template,mode) {
		console.log(template);
		console.log(mode);
		let approvalMode = mode+"_APPROVAL";
		var template_request = {
			formName : template.formName,
			templateName : template.templateName,
			templateCategoryId : template.templateCategoryId,
			status : 'DRAFT',
			categoryMstrId : template.categoryMstrId,
			createdBy : $rootScope.account.userId,
			templateType : template.templateType,
			versionStatus : template.versionStatus,
			templateCode : template.templateCode,
			formsCount : 0,
			mode : approvalMode,
			workflowId : template.workflowStageId,
			templateJson : template.templateJson,
			templateId : template.templateId,
			internalApproverUserId : template.internalApprovedBy,
			externalApproverUserId : template.externalApprovedBy			
		};
		console.log(template_request);	
		$state.go('createtemplate',template_request);	
	}
}])
.controller('CentralTemplatesCtrl',["$scope","$rootScope","$stateParams","ngDialog","$state","DocumentService","UserService","GenericAlertService","EpsService","TemplateFactory","stylesService", "ngGridService",function($scope,$rootScope,$stateParams,ngDialog,$state,DocumentService,UserService,GenericAlertService,EpsService,TemplateFactory,stylesService, ngGridService){
	let colorcodes = ["000099","8CFF66","FF9900","558000","FB3CD5","FF471A","3333CC","CCCCFF","CCCC00","00B3B3","990099","B35900","999966","993366","FFFF00","00FFFF","808080","66CDAA","FFEBCD","000000"];
	$scope.mstrCategoryId = 2;
	$scope.categoryname = "";
	$scope.selected_ccategory_id = 0;
	$scope.error = false;
	$scope.stylesSvc = stylesService;
	$scope.showCreateTmplDiv = false;
	$scope.showMsgDiv = false;
	$scope.showTmplMessage = "";
	$scope.templateType = "TIMELINE";
	$scope.createNewTmplButton = true;
	$scope.mode = "ALL";
	$scope.selectedTemplate = "";
	$scope.ctemplates_categories_count = 0;
	$scope.ctemplates_count = 0;
	$scope.templateCategoriesList = [];
	$scope.lastTemplateId = 0;
	$scope.crmName = '';
	$scope.selected_category_color_code = "";
	$scope.resetGrid= function () {
						$scope.gridOptions1.data= [];
						$scope.centralTemplatesList=[];
		
	}
	
	$scope.getCentralCategories = function(category) {
		console.log("stateParams:");
		console.log($stateParams);
		console.log("mode from getCentralCategories:"+$stateParams.mode+"=>"+$stateParams.selectedTemplate+"=>"+$scope.selected_ccategory_id);
		
		let selectedTemplate = $stateParams.selectedTemplate;
		
		/*let template_req = {
			crmId : $rootScope.account.clientId
		}
		DocumentService.getCentralTemplsMaxTemplId(template_req).then(function(data){
			$scope.lastTemplateId = data;
		})*/
		console.log(selectedTemplate);		
		console.log("stateparams mode:"+$stateParams.mode);		
		let req = {
				categoryMstrId: $scope.mstrCategoryId,
				crmId : $rootScope.account.clientId
		};
		DocumentService.getTemplateCategories(req).then(function (data) {
			console.log(data);
			$scope.templateCategoriesList = data.templateCategoriesTOs;
			$scope.ctemplates_categories_count = data.templateCategoriesTOs.length;
		})
	},
	$scope.saveTemplateCategory = function()
	{
		let newColorCode = '';
		let colorCodeStr = '';
		for( let cat = 0 ; cat<$scope.templateCategoriesList.length ; cat++ ) {
			let current_element = $scope.templateCategoriesList[cat];
			if( colorCodeStr == '' ) {
				colorCodeStr += current_element.colorCode;
			}
			else
			{
				if( colorCodeStr.indexOf(current_element.colorCode) == -1 ) {
					colorCodeStr += ","+current_element.colorCode;
				}
			}		
		}
		
		for(let j=0;j<colorcodes.length;j++)
		{
			if( colorCodeStr.indexOf( colorcodes[j] ) == -1 && newColorCode == '' ) {
				newColorCode = colorcodes[j];
			}
		}
		console.log("selected template id:"+$scope.selected_ccategory_id);
		if( $scope.categoryname == "" )
		{
			GenericAlertService.alertMessage("Please enter the category name", "Warning");
			return;
		}
		else
		{
			let req = {
				categoryName : $scope.categoryname,
				createdBy : $rootScope.account.userId,
				categoryMstrId : $scope.mstrCategoryId,
				colorCode : newColorCode,
				crmId : $rootScope.account.clientId
			}
			DocumentService.saveTempCategory(req).then(function (data) {
				GenericAlertService.alertMessage("Template Category created successfully..........", "Info");
				$scope.templateCategoriesList.push(data.templateCategoriesTOs[0]);
			})
		}
	},
	$scope.updateTemplateCategory = function(event) {
		let category_id = event.target.id;
		let modCategoryId = parseInt( category_id.replace( "update" , "" ) );
		let textelementid = category_id.replace("update","editcategory");
		let category_name = document.getElementById(textelementid).value;
		console.log("updateTemplateCategory function:"+category_name+"->"+modCategoryId);
		let req={
				categoryId: modCategoryId,
				categoryName: category_name,
				categoryMstrId : $scope.mstrCategoryId
		};
		if( category_name.trim().length == 0 )
		{
			GenericAlertService.alertMessage("Please enter the category name", "Warning");
			return;
		}
		else
		{
			DocumentService.updateTemplCategory(req).then(function(data){
				var templateList =  $scope.templateCategoriesList;
				GenericAlertService.alertMessage("Template Category updated successfully..........", "Info");
				for(let i=0;i<templateList.length;i++)
				{
					let current_category = templateList[i];
					if( current_category.categoryId == modCategoryId )
					{
						//$scope.templateCategoriesList[i] = category_name;
						console.log("if condition of for loop");
						$scope.templateCategoriesList[i].categoryName = category_name;
					}
				}
			})
		}
	},
	$scope.getCentralTemplates = function(event) {
		$scope.resetGrid();
		console.log(" This is from getCentralTemplates function");
		console.log(event.target.id);
		let category_element_id = event.target.id;
		let categoryId = parseInt( category_element_id.replace( "span" , "" ) );
		$scope.selected_ccategory_id = categoryId;
		//$rootScope.centralTemplatesList = [];
		$scope.mode = "ALL";
		let req = {
			templateCategoryId : $scope.selected_ccategory_id,
			crmId : $rootScope.account.clientId,
			categoryMstrId: $scope.mstrCategoryId
	  	};
		DocumentService.getCentralTemplatesList(req).then(function(data){
			console.log(data);
			for( var i=0;i<data.templatesTOs.length; i++ )
			{
				data.templatesTOs[i].centralTemplCode = data.templatesTOs[i].templateCode+"-"+data.templatesTOs[i].templateId;				
			}
			$scope.centralTemplatesList = data.templatesTOs;
			$scope.crmName = data.templatesTOs[0].crmName;
			$rootScope.centralTemplatesList = data;
			$scope.ctemplates_count = data.templatesTOs.length;
			console.log($rootScope.centralTemplatesList);
			$scope.centralTemplatesList = data.templatesTOs;                                
			for (var central of $scope.centralTemplatesList ){
				central.version="V"+central.versionStatus+".0";
				central.formsCountt= (central.formsCount!=0 && central.formsCount!=null) ?  central.formsCount : (central.formsCount == 0) ? '0' : null;
				}
			$scope.gridOptions1.data= angular.copy($scope.centralTemplatesList);
			if( $scope.selected_ccategory_id != 0 ) {
				for(var i=0;i<$scope.templateCategoriesList.length;i++) {
					if( categoryId == $scope.templateCategoriesList[i].categoryId ) {
						$scope.selected_category_color_code = $scope.templateCategoriesList[i].colorCode;
					}
				}
			}
		})
		$scope.createNewTmplButton = false;
	},
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'centralTemplCode', displayName: 'Template Id', headerTooltip : "Template Id" },
						{ field: 'templateName', displayName: 'Name of Template', headerTooltip: "Name of Template"},
						{ field: 'version', displayName: "Template Version", headerTooltip: "Template Version", },
						{ name: 'formName', displayName: "Form Name", headerTooltip: "Form Name" },
						{ name: 'createdBy', displayName: "Created By", headerTooltip : "Created By" },
						{ field: 'createdOn',cellFilter:'date', displayName: "Creation / Updated (Date)", headerTooltip: "Creation / Updated (Date)"},
						{ field: 'templateType', displayName: "Process Type", headerTooltip: "Process Type", },
						{ name: 'formsCountt', displayName: "Number of Forms", headerTooltip : "Number of Forms" },
						{ field: 'status',cellFilter:'replaceStatus', displayName: "Status", headerTooltip: "Status"},
					
						];
					let data = [];
					$scope.gridOptions1 = ngGridService.initGrid($scope, columnDefs, data, "Documents_Templates & Forms_Central Templates");
					$scope.getCountryProvisions();
					
				}
			});
	$scope.createTemplate = function() {
		var createTemplatePopup = ngDialog.open({
			template: 'views/common/createtemplate.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.error = false;
				$scope.displayFormNameError = false;
				$scope.displayTemplNameError = false;
				
				//console.log($scope.templateCategoriesList);
				let selected_ctempl_category = '';
				$scope.disableButton = true;
				$scope.nextStageBtn = false;
				$scope.nextBtn = true;
				let crm_name = $scope.crmName.substring(0,4).toUpperCase();
				for(let k=0;k<$scope.templateCategoriesList.length;k++)
				{
					if($scope.templateCategoriesList[k].categoryId==$scope.selected_ccategory_id)
					{
						selected_ctempl_category = $scope.templateCategoriesList[k].categoryName;
					}
				}
				var templcode = selected_ctempl_category.split(' ').map(i=>i.charAt(0)).join('').toUpperCase();
				if(templcode.length == 1){
					templcode = selected_ctempl_category.substring(0,3).toUpperCase();
				}
				//$scope.templateCode = crm_name+"-"+templcode+"-"+($scope.lastTemplateId+1);
				$scope.templateCode = "ORG-"+templcode;
				$scope.selectedTemplCategory = {categoryName:selected_ctempl_category};
				console.log($rootScope.centralTemplatesList);
				$scope.templatesList = [];
				$scope.templateId = 0;
				
				console.log($scope.selected_template);
				$scope.next = function(templateList) {
					console.log("selected template from next function:"+$scope.selectedTemplate);
					let form_name = document.getElementById("form_name").value;
					let template_name = document.getElementById("template_name").value;
					if( $scope.selected_template == 'From Sample Templates' || $scope.selected_template == 'From Central Templates' )
					{
						$scope.showListDiv = false;
						$scope.showCreateTmplDiv = true;
						$scope.showTemplateTypeDiv = false;
						//$scope.selectedTemplateId = 122;
						$scope.error = true;
						$scope.nextBtn = false;
						$scope.nextStageBtn = true;
						angular.forEach(templateList, function (value,key) {
							console.log(templateList[key]);
			                if(templateList[key].selected) {
			                    templateList[key].selected = true;
								//forms_cnt++;
			                }
							else
							{
								templateList[key].selected = false;
							}
		            	});
					}
					else if( $scope.selected_template == 'Create Own' )
					{
						$scope.errormsgs = [];
						console.log("inside the else condition");						
						let template_category = document.getElementById("template_category").value;
						console.log("This is from createNewTemplate function");						
						if( form_name != "" && template_name != "" )
						{
							var template_request = {
								formName : form_name,
								templateName : template_name,
								templateCategoryId : $scope.selected_ccategory_id,
								status : 'DRAFT',
								categoryMstrId : $scope.mstrCategoryId,
								createdBy : $rootScope.account.userId,
								templateType : $scope.templateType,
								crmId : $rootScope.account.clientId,
								versionStatus : 1,
								templateCode : $scope.templateCode,
								formsCount : 0,
								mode : 'NEW_TEMPL'
							};
							console.log(template_request);
							ngDialog.close(createTemplatePopup);
							$state.go('createtemplate',template_request);
						}
						else
						{
							$scope.error = true;
							if( template_name.trim().length == 0 )
							{
								$scope.errormsgs.push('Please enter the template name');
								$scope.displayTemplNameError = true;							
							}
							if( form_name.trim().length == 0 )
							{
								$scope.errormsgs.push('Please enter the form name');
								$scope.displayFormNameError = true;
							}
						}
					}
					else
					{
						GenericAlertService.alertMessage("Please select the option to create template", "Warning");
						return;
					}
					if( !$scope.error )
					{
						ngDialog.close(createTemplatePopup);
					}								
				},
				$scope.checkTemplateId = function(changedVal){
					$scope.selectedTemplateId = changedVal;
					console.log(changedVal);
				},
				$scope.templateSelection = function(category) {
					$scope.selected_template = category;
					$scope.selectedTemplate = category;
					if( category == "Create Own" )
					{
						$scope.showCreateTmplDiv = true;
						$scope.showMsgDiv = false;
						$scope.showListDiv = false;
						$scope.showTemplateTypeDiv = true;
					}
					else
					{
						$scope.showCreateTmplDiv = false;
						$scope.showMsgDiv = true;
						$scope.showListDiv = true;
						$scope.showTemplateTypeDiv = false;
						$scope.showTmplMessage = ( category == "From Sample Templates" ) ? "Sample Templates" : "Central Templates";
						
						if( category == "From Sample Templates" )
						{
							console.log("if condition of From Sample Templates");
							$scope.templateList = [];
							let req = {				
								categoryMstrId : 1,
								templateStatus : "READY_TO_USE"
							};
							DocumentService.getTemplatesList(req).then(function(data){
								console.log(data);	
								console.log(data.templatesTOs.length);			
								for(var i=0;i<data.templatesTOs.length;i++)
								{
									$scope.templateList.push(data.templatesTOs[i]);
								}
							})
						}
						else
						{
							console.log("if condition of From Central Templates");
							console.log($rootScope.centralTemplatesList);
							$scope.templateList = [];
							for(var k=0;k<$rootScope.centralTemplatesList.templatesTOs.length;k++)
							{
								if( $rootScope.centralTemplatesList.templatesTOs[k].status == 'READY_TO_USE' )
								{
									$scope.templateList.push($rootScope.centralTemplatesList.templatesTOs[k]);
								}								
							}
						}
						console.log($scope.templateList);
					}
					console.log($scope.selected_template);
					$scope.disableButton = false;
				},
				$scope.nextStage = function() {
					console.log("nextStage function");
					console.log($scope.selected_template);
					console.log($scope.templateList);
					
					var currentSelectedTemplate;
					for(var j=0;j<$scope.templateList.length;j++)
					{
						if( $scope.templateList[j].selected )
						{
							currentSelectedTemplate = $scope.templateList[j];
						}
					}
					console.log("current selected template");
					console.log(currentSelectedTemplate);
					let template_request = {
						templateId : currentSelectedTemplate.templateId,
						formName : $scope.form_name,
						templateName : $scope.template_name,
						templateCategoryId : $scope.selected_ccategory_id,
						status : 'DRAFT',
						categoryMstrId : $scope.mstrCategoryId,
						createdBy : $rootScope.account.userId,
						crmId : $rootScope.account.clientId,
						mode : 'createTemplateCopy',
						versionStatus : 1,
						formsCount : 0,
						templateCode : $scope.templateCode,
						templateType : currentSelectedTemplate.templateType,
						templateJson : currentSelectedTemplate.templateJson
					};
					console.log(template_request);
					$state.go('createtemplate',template_request);
					ngDialog.close(createTemplatePopup);
				}
			}]
		});
	},
	$scope.createTemplateCopy = function(event,current_id) {
		console.log("selected template id:"+current_id);
		let templatesArry = $rootScope.centralTemplatesList.templatesTOs;
		let current_template_json = {};
		var current_template_data;
		for(let count=0;count<templatesArry.length;count++)
		{
			if( current_id == templatesArry[count].templateId )
			{
				console.log("if condition of for loop");
				current_template_json = templatesArry[count].templateJson;
				current_template_data = templatesArry[count];
			}
		}
		$scope.templateCategoryName = current_template_data.templCategoryname;
		$scope.lastTemplateId = 0;
		/*
		let last_template_req = {
			crmId : $rootScope.account.clientId
		}
		DocumentService.getCentralTemplsMaxTemplId(last_template_req).then(function(data){
			$scope.lastTemplateId = data;
		})
		*/
		let crm_name = current_template_data.crmName.substring(0,4).toUpperCase();		
		var templshortcut = current_template_data.templCategoryname.split(' ').map(i=>i.charAt(0).toUpperCase()).join('');
		if(templshortcut.length == 1){
			templshortcut = current_template_data.templCategoryname.substring(0,4).toUpperCase();
		}
		//$scope.templateCode = crm_name+"-"+templshortcut+"-"+($scope.lastTemplateId+1);
		$scope.templateCode = crm_name+"-"+templshortcut;		
		
		$scope.template_json = '[{"label":"First Name","labelPosition":"left-left","placeholder":"Enter your first name.........","tableView":true,"key":"firstName","type":"textfield","input":true},{"label":"Last Name","labelPosition":"left-left","placeholder":"Enter your last name...........","tableView":true,"key":"lastName","type":"textfield","input":true},{"type":"button","label":"Submit","key":"submit","disableOnInvalid":true,"input":true,"tableView":false}]';
		var editTemplatePopup = ngDialog.open({
			template: 'views/common/edittemplate.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				$scope.error = false;
				$scope.errormsgs = [];
				$scope.next = function() {
					console.log("from createtemplatecopy of next function:"+current_template_data);
					let form_name = document.getElementById("form_name").value;
					let template_name = document.getElementById("template_name").value;
					let template_category = document.getElementById("template_category").value;					
					if( template_name != "" && form_name != "" )
					{
						let template_request = {
							templateId : current_template_data.templateId,
							formName : form_name,
							templateName : template_name,
							templateCategoryId : current_template_data.templateCategoryId,
							status : 'DRAFT',
							categoryMstrId : $scope.mstrCategoryId,
							createdBy : $rootScope.account.userId,
							templateType : 'TIMELINE',
							templateJson : current_template_data.templateJson,
							mode: "createTemplateCopy",
							crmId : $rootScope.account.clientId,
							formsCount : 0,
							versionStatus : 1
						};
						console.log("template request:"+template_request);						
						$state.go('createtemplate',template_request);
						ngDialog.close(editTemplatePopup);
					}
					else
					{
						console.log("else condition");
						$scope.error = true;
						if( template_name.trim().length == 0 )
						{
							$scope.errormsgs.push("Template Name cannot be empty");
						}
						if( form_name.trim().length == 0 ){
							$scope.errormsgs.push("Form Name cannot be empty");
						}
					}
				}			
			}]
		});
	},	
	$scope.editExistingTemplate = function(template_id) {	
		let current_ctempl = {};		
		console.log("editExistingTemplate function");
		console.log($rootScope.centralTemplatesList);
		
		for(let j=0;j<$rootScope.centralTemplatesList.templatesTOs.length;j++)
		{
			if( $rootScope.centralTemplatesList.templatesTOs[j].templateId == template_id )
			{
				current_ctempl = $rootScope.centralTemplatesList.templatesTOs[j];
			}
		}
		let template_req = {
			templateId : template_id,
			mode : 'edit',
			categoryMstrId : $scope.mstrCategoryId,
			templateCategoryId : current_ctempl.templateCategoryId,
			templateJson : current_ctempl.templateJson,
			formsCount : 0,
			versionStatus : 1,
			status : 'READY_TO_USE',
			templateType : current_ctempl.templateType,
			workflowId : current_ctempl.workflowStageId,
			templateStatus : current_ctempl.status
		};
		console.log(template_req);
		$state.go('createtemplate',template_req);
	},
	$scope.editTemplate = function() {
		let req = {
			templateCategoryId : $scope.selected_ccategory_id,
			categoryMstrId : $scope.mstrCategoryId,
			crmId : $rootScope.account.clientId
		};
		console.log(req);
		$scope.centralTemplatesList = $rootScope.centralTemplatesList.templatesTOs;
		/*$scope.templatesList = $rootScope.sampleTemplatesList.templatesTOs;
		$scope.mode = "VIEW";
		
		let req = {
			templateCategoryId : $scope.selected_category_id,
			categoryMstrId : $scope.mstrCategoryId
		};
		DocumentService.getTemplatesList(req).then(function(data){	
			$scope.mode = 'edit';
			//$scope.templatesList = [];
			let templatesList = [];
			for(let i=0;i<data.templatesTOs.length;i++){
				console.log( data.templatesTOs[i]);
				if( data.templatesTOs[i].status == 'DRAFT' || data.templatesTOs[i].status == 'READY_TO_USE' )
				{
					//$scope.templatesList.push(data.templatesTOs[i]);
					templatesList.push(data.templatesTOs[i]);
				}				
			}
			$scope.templatesList = templatesList;
			console.log('mode:'+data);
		})*/
		/*DocumentService.getCentralTemplatesList(req).then(function(data){
			$scope.mode = 'edit';			
			$scope.centralTemplatesList = [];
			console.log($rootScope.centralTemplatesList);
			for(let k=0;k<$rootScope.centralTemplatesList.templatesTOs.length;k++)
			{
				let current_element = $rootScope.centralTemplatesList.templatesTOs[k];
				if( $rootScope.centralTemplatesList.templatesTOs[k].status == 'INPROGRESS' )
				{
					if( current_element.internalApprovalStatus == "SUBMITTED" || current_element.externalApprovalStatus == "SUBMITTED" )
					{
						console.log("if condition of if block");
						console.log(current_element);
						if( current_element.internalApprovalStatus == "SUBMITTED" && current_element.internalApprovedBy == $rootScope.account.userId )
						{
							console.log("if condition of internalapprovalstatus submitted");
							current_element.internal_approval_button = true;
							current_element.external_approval_button = false;
						}
						if( current_element.internalApprovalStatus == "APPROVED" && current_element.externalApprovalStatus == "SUBMITTED" && current_element.externalApprovedBy == $rootScope.account.userId )
						{
							console.log("if condition of internalapprovalstatus approved and externalapprovalstatus submitted");
							current_element.internal_approval_button = false;
							current_element.external_approval_button = true;
						}
						$scope.centralTemplatesList.push(current_element);
					}
				}
				else
				{
					console.log("else condition");
					current_element.internal_approval_button = false;
					current_element.external_approval_button = false;
					if( current_element.status == 'DRAFT' || current_element.status == 'READY_TO_USE' )
					{
						$scope.centralTemplatesList.push(current_element);
					}					
				}
			}
			console.log($scope.centralTemplatesList);
		})*/
	},
	$scope.forInternalApproval = function() {
		$scope.mode = 'SUBMIT_FOR_INTERNAL_APPROVAL';
		$scope.centralTemplatesList = [];
		for(let k=0;k<$rootScope.centralTemplatesList.templatesTOs.length;k++)
		{
			if( $rootScope.centralTemplatesList.templatesTOs[k].status == 'DRAFT' )
			{
				$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[k]);
			}
		}
	},
	$scope.forExternalApproval = function(){		
		$scope.mode = 'SUBMIT_FOR_EXTERNAL_APPROVAL';
		$scope.centralTemplatesList = [];
		for(let k=0;k<$rootScope.centralTemplatesList.templatesTOs.length;k++)
		{
			if( $rootScope.centralTemplatesList.templatesTOs[k].isExternalApprovedRequired == 'Y' && $rootScope.centralTemplatesList.templatesTOs[k].internalApprovalStatus === 'APPROVED' )
			{
				$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[k]);
			}
		}
	},
	$scope.internalApprovalTemplate = function(template_id,template_category) {
		console.log("internalApprovalTemplate function");
		let template_request = {
			templateId : template_id,
			templateCategoryId : template_category,
			status : 'INTERNAL_APPROVAL',
			createdBy : $rootScope.account.userId,
			categoryMstrId : $scope.mstrCategoryId
		};
		console.log(template_request);
		DocumentService.templateApproval(template_request).then(function(data){	
			//$scope.centralTemplatesList = data.templatesTOs;
			console.log(data);
			GenericAlertService.alertMessage("You approved the template", "Info");
		})
	},
	$scope.externalApprovalTemplate = function(template_id,template_category) {
		console.log("externalApprovalTemplate function");
		let template_request = {
			templateId : template_id,
			templateCategoryId : template_category,
			status : 'EXTERNAL_APPROVAL',
			createdBy : $rootScope.account.userId,
			categoryMstrId : $scope.mstrCategoryId
		};
		console.log(template_request);
		DocumentService.templateApproval(template_request).then(function(data){				
			//$scope.mode = 'edit';
			//$scope.centralTemplatesList = data.templatesTOs;
			console.log(data);
			GenericAlertService.alertMessage("You approved the template", "Info");
		})
	},
	$scope.submitForExtApproval = function(template_id,template_category) {
		var externalApprovalCtemplPopup = ngDialog.open({
			template: 'views/documents/approveruserspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {	
				$scope.title = "External Approver User List";
				$scope.approverUserId = "";
				$scope.approvalMode = "EXTERNAL";
				$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1"
				}
				$scope.users = [];
				UserService.getUsers($scope.userReq).then(function(data) {
					//$scope.users = data.users;
					let usersList = data.users;
					for(let j=0;j<usersList.length;j++)
					{
						if( usersList[j].userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[j]);
						}
					}
					console.log(data.users);
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				$scope.submit = function() {
					let template_request = {
						templateId : template_id,
						templateCategoryId : template_category,
						status : 'SUBMIT_FOR_EXTERNAL_APPROVAL',
						createdBy : $scope.approverUserId,
						categoryMstrId : $scope.mstrCategoryId
					};
					console.log(template_request);					
					console.log($scope.approverUserId);	
					if( $scope.approverUserId != "" )
					{
						ngDialog.close(externalApprovalCtemplPopup);
						DocumentService.templateApproval(template_request).then(function(data){				
							//$scope.mode = 'edit';
							//$scope.centralTemplatesList = data.templatesTOs;
							console.log(data);
							GenericAlertService.alertMessage('Your request for the approval sent successfully', "Info");
						})
					}
					else
					{
						GenericAlertService.alertMessage('Please select the Approver for approval', "Error");
					}
				}
			}]
		});
	},
	$scope.submitForIntApproval = function(template_id,template_category) {
		var internalApprovalPopup = ngDialog.open({
			template: 'views/documents/approveruserspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {	
				$scope.title = "Internal Approver User List";
				$scope.approverUserId = "";
				$scope.approvalMode = "INTERNAL";
				$scope.isexternalapprovalrequired = null;
				$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1"
				}
				$scope.users = [];
				UserService.getUsers($scope.userReq).then(function(data) {
					let usersList = data.users;
					for(let j=0;j<usersList.length;j++)
					{
						if( usersList[j].userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[j]);
						}
					}
					console.log($scope.users);
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				$scope.toggleExternalApprovalRequired = function(event) {
					$scope.isexternalapprovalrequired = event.target.checked;
				}
				$scope.submit = function() {
					console.log($scope.isexternalapprovalrequired);
					let template_request = {
						templateId : template_id,
						templateCategoryId : template_category,
						status : 'SUBMIT_FOR_INTERNAL_APPROVAL',
						createdBy : $scope.approverUserId,
						categoryMstrId : $scope.mstrCategoryId
					};
					if( $scope.isexternalapprovalrequired != null )
					{
						template_request.isExternalApprovedRequired = ( $scope.isexternalapprovalrequired == true ) ? "Y" : "N";
					}
					else
					{
						template_request.isExternalApprovedRequired = "N";
					}

					if( $scope.approverUserId != "" )
					{
						DocumentService.templateApproval(template_request).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage("Your request sent to the Approver", 'Info');
						})
						ngDialog.close(internalApprovalPopup);
					}
					else
					{
						GenericAlertService.alertMessage("Please select the Approver for approval", 'Warning');
					}
				}
			}]
		});
	},
	$scope.submitForApproval = function(template,mode) {
		console.log("submitForApproval function central templates");
		console.log(mode);
		let isDisplayExternalApprovalRequired = false;
		let displayApproverComments = false;
		let approvalModeArry = mode.split("_");
		let approvalMode = approvalModeArry[2];
		TemplateFactory.approvalUserPopup(template,approvalMode,"SUBMITTED",isDisplayExternalApprovalRequired,displayApproverComments);
	},
	$scope.approveTemplate = function(template,mode) {
		console.log("approveTemplate function from central templates");
		console.log(template);
		console.log(mode);
		let approvalMode = mode+"_APPROVAL";
		var template_request = {
			formName : template.formName,
			templateName : template.templateName,
			templateCategoryId : template.templateCategoryId,
			status : 'DRAFT',
			categoryMstrId : template.categoryMstrId,
			createdBy : $rootScope.account.userId,
			templateType : template.templateType,
			versionStatus : template.versionStatus,
			templateCode : template.templateCode,
			formsCount : 0,
			mode : approvalMode,
			workflowId : template.workflowStageId,
			templateJson : template.templateJson,
			templateId : template.templateId
		};
		console.log(template_request);		
		$state.go('createtemplate',template_request);	
	},
	$scope.fromProjects = function() {
		$scope.ptemplslist = [];
		var fromProjectsPopup = ngDialog.open({
			template: 'views/documents/fromprojectspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				$scope.allow_edit_view = null;
				$scope.reviewer_decision = null;
				$scope.proposal_template_list = [];
				$scope.proposal_count = 0;
				let proposal_request = {
					crmId : $rootScope.account.clientId
				};
				
				DocumentService.getProposalList(proposal_request).then(function(data){
					$scope.proposalList = data.projectTemplatesProposalTOs;
					console.log($scope.proposalList);
					for( let k=0;k<$scope.proposalList.length;k++)
					{
						$scope.proposal_template_list.push({templateId:$scope.proposalList[k].projectTemplateId,projectId:$scope.proposalList[k].fromProjectId});
						$scope.proposal_count++;
					}
					console.log($scope.proposal_template_list);
				})
				let request = {};
				
				$scope.reviewerDecision = function(option,index) {
					$scope.proposal_template_list[index].reviewer_decision = (option=="Yes") ? "Y" : "N";
				}				
				$scope.apply = function() {
					let templates_list = [];
					
					let request = {
						categoryMstrId : 2,
						userId : $rootScope.account.userId,
						transferFrom : "From Projects"
					}
					let reviewers_count = 0;
					for(let j=0;j<$scope.proposal_template_list.length;j++)
					{
						if( $scope.proposal_template_list[j].reviewer_decision == "Y" )
						{
							templates_list.push({templateId:$scope.proposal_template_list[j].templateId,projectId:$scope.proposal_template_list[j].projectId});
						}
					}
					request.templatesTOs = templates_list;
					if( templates_list.length !=0 )
					{
						DocumentService.transferTemplates(request).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage('The selected templates transferred successfully', "Info");
						});
						ngDialog.close(fromProjectsPopup);	
					}
					else
					{
						GenericAlertService.alertMessage('Please select atleast one template to transfer into central templatees', "Error");
					}
					console.log(request);									
				}				
			}]
		});
	},
	$scope.changeMode = function(mode) {
		console.log("changeMode:"+mode);
		console.log($rootScope.centralTemplatesList);
		$scope.centralTemplatesList = $rootScope.centralTemplatesList.templatesTOs;
		if(mode == 'editView') {
			$scope.mode = 'edit';	
			$scope.centralTemplatesList = [];
			for(var i=0;i<$rootScope.centralTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.centralTemplatesList.templatesTOs[i].status == 'DRAFT' )
				{
					$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[i]);
				}
				else if( $rootScope.centralTemplatesList.templatesTOs[i].status == 'SUBMITTED_FOR_INTERNAL_APPROVAL' && $rootScope.centralTemplatesList.templatesTOs[i].internalApprovedBy == $rootScope.account.userId )
				{
					$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[i]);
				}
				else if( $rootScope.centralTemplatesList.templatesTOs[i].status == "SUBMITTED_FOR_EXTERNAL_APPROVAL" && $rootScope.centralTemplatesList.templatesTOs[i].externalApprovedBy == $rootScope.account.userId && $rootScope.centralTemplatesList.templatesTOs[i].isExternalApprovedRequired == "Y" )
				{
					$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[i]);
				}
			}		
		} 
		else if(mode == 'internalApproval') {
			$scope.centralTemplatesList = [];
			for(var i=0;i<$rootScope.centralTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.centralTemplatesList.templatesTOs[i].status == 'DRAFT' || $rootScope.centralTemplatesList.templatesTOs[i].status == 'WF_STAGES_APPROVED' )
				{
					$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[i]);
				}
			}
			//$rootScope.sampleTemplatesList.templatesTOs;		
			$scope.mode = 'SUBMIT_FOR_INTERNAL_APPROVAL';
		}
		else if(mode == 'externalApproval') {
			console.log("externalApproval block");			
			$scope.centralTemplatesList = [];
			for(var i=0;i<$rootScope.centralTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.centralTemplatesList.templatesTOs[i].status == 'INTERNAL_APPROVED' && $rootScope.centralTemplatesList.templatesTOs[i].isInternalApproved == 'Y' )
				{
					$scope.centralTemplatesList.push($rootScope.centralTemplatesList.templatesTOs[i]);
				}
			}
			$scope.mode = 'SUBMIT_FOR_EXTERNAL_APPROVAL';
		}
		else
		$scope.mode = "VIEW";
	}	
}])
.controller('ProjectTemplatesCtrl',["$scope","$rootScope","$state","$stateParams","$window","ngDialog","EpsProjectSelectFactory","DocumentService","UserService","GenericAlertService","TemplateFactory","stylesService", "ngGridService",function($scope,$rootScope,$state,$stateParams,$window,ngDialog,EpsProjectSelectFactory,DocumentService,UserService,GenericAlertService,TemplateFactory, stylesService, ngGridService) {
	$scope.mstrCategoryId = 3;
	$scope.selected_pcategory_id = ( $stateParams.selectedCategory != null ) ? $stateParams.selectedCategory : 0;
	$scope.stylesSvc = stylesService;
	$scope.showCreateTmplDiv = false;
	$scope.showMsgDiv = false;
	$scope.showTmplMessage = "";
	$scope.templateType = "TIMELINE";
	$scope.createNewTmplButton = true;
	$scope.projectId = 0;
	$scope.displayMessage = "";
	$scope.crmName = "";
	
	$scope.mode = "ALL";
	$scope.selectedTemplate = "";
	$scope.searchProject = {};
	$scope.templateCategoriesList = [];
	$scope.lastTemplateId = 0;
	$scope.selectedProject = ( $stateParams.selectedProject != null ) ? $stateParams.selectedProject : 0;
	$scope.selected_category_color_code = "";
	$scope.ptemplates_cnt = 0;
	console.log($scope.selected_pcategory_id);
	
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			console.log('data.searchProject'+JSON.stringify(data.searchProject))
			$scope.searchProject = data.searchProject;
			$scope.projectId = data.searchProject.projId;			
			if( $scope.selected_pcategory_id !== 0 )
			{
				$scope.projectTemplatesList = [];
				$scope.displayMessage = "";
				let req = {
					templateCategoryId : $scope.selected_pcategory_id,
					categoryMstrId : $scope.mstrCategoryId,
					crmId : $rootScope.account.clientId,
					projId : $scope.projectId
				};
				console.log(req);				
				DocumentService.getProjectTemplatesList(req).then(function(data){
					//$scope.projectTemplatesList = data.templatesTOs;
					$rootScope.projectTemplatesList = data;
					if( data.templatesTOs.length == 0 )
					{
						$scope.displayMessage = "No templates to display";
					}
					else
					{
						console.log("else condition of data templates");
						$scope.crmName = data.templatesTOs[0].crmName;
						for(let j=0;j<data.templatesTOs.length;j++)
						{
							if( $scope.projectId == data.templatesTOs[j].projectId )
							{
								console.log("project id:"+data.templatesTOs[j].projectId);
								data.templatesTOs[j].projTemplCode = data.templatesTOs[j].templateCode+"-"+data.templatesTOs[j].templateId;								
								$scope.projectTemplatesList.push(data.templatesTOs[j]);
							}
						}
					}
					console.log($scope.projectTemplatesList);
				})
				$scope.createNewTmplButton = false;
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.getProjectCategories = function(category) {
		$scope.displayMessage = "Please select the project and template category to get the list of templates";
		let selectedTemplate = $stateParams.selectedTemplate;
		let req = {
				categoryMstrId: $scope.mstrCategoryId,
				crmId : $rootScope.account.clientId
			  };
		//console.log($rootScope);
		DocumentService.getTemplateCategories(req).then(function (data) {			
			console.log("document service getProjectCategories function:");
			$scope.templateCategoriesList = data.templateCategoriesTOs;
		})
		/*let template_req = {
			crmId : $rootScope.account.clientId
		}
		DocumentService.getProjTemplsMaxTemplId(template_req).then(function(data){
			$scope.lastTemplateId = data;
		})*/
		console.log($scope.lastTemplateId);
		console.log(selectedTemplate);
		console.log("getProjectCategories function:"+$stateParams.selectedTemplate+"=>"+$stateParams.mode);
	},
	$scope.getProjectTemplates = function(event) {
		let category_element_id = event.target.id;
		let categoryId = parseInt( category_element_id.replace( "category" , "" ) );
		$scope.selected_pcategory_id = categoryId;
		$scope.ptemplatesList = [];
		console.log("getProjectTemplates:"+categoryId);
		$scope.displayMessage = "";
		
		if( $scope.projectId != 0 )
		{
			console.log("selected project details:");
			console.log($scope.searchProject);
			$scope.projectTemplatesList = [];
			let req = {
				templateCategoryId : $scope.selected_pcategory_id,
				categoryMstrId : $scope.mstrCategoryId,
				crmId : $rootScope.account.clientId,
				projId : $scope.projectId
			};
			console.log(req);
			DocumentService.getProjectTemplatesList(req).then(function(data){
				//$scope.projectTemplatesList = data.templatesTOs;
				//$rootScope.projectTemplatesList = data;
				console.log(data);
				console.log("getProjectTemplatesList function");
				if( data.templatesTOs.length == 0 )
				{
					console.log("if condition of data templates");
					$scope.displayMessage = "No templates to display";
				}
				else
				{
					console.log("else condition of data templates");
					$scope.crmName = data.templatesTOs[0].crmName;
					for(let j=0;j<data.templatesTOs.length;j++)
					{
						if( $scope.projectId == data.templatesTOs[j].projectId )
						{
							console.log("project id:"+data.templatesTOs[j].projectId);
							data.templatesTOs[j].projTemplCode = data.templatesTOs[j].templateCode+"-"+data.templatesTOs[j].templateId;														
							$scope.projectTemplatesList.push(data.templatesTOs[j]);
						}
					}
					
				}
				$rootScope.projectTemplatesList = data.templatesTOs;				       
	            for(var proj of data.templatesTOs){
		         proj.formsCount= proj.formsCount == 0 ? "0" : proj.formsCount;
		         proj.versionStatus1="V"+proj.versionStatus+".0";
	             }
			    $scope.gridOptions3.data = angular.copy(data.templatesTOs);
				console.log($rootScope.projectTemplatesList);
				if( $scope.selected_pcategory_id != null ) 
				{
					console.log($scope.templateCategoriesList);
					for(let j=0;j<$scope.templateCategoriesList.length;j++)
					{
						if( categoryId == $scope.templateCategoriesList[j].categoryId )
						{
							console.log("if condition");
							$scope.selected_category_color_code =  $scope.templateCategoriesList[j].colorCode;
						}
					}
				}
			})
			$scope.createNewTmplButton = false;
		}
		else
		{
			$scope.displayMessage = "Please select the project to get the list of templates.";
		}	
	},
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'projTemplCode', displayName: 'Template Id', headerTooltip : "Template Id" },
						{ field: 'templateName', displayName: 'Name of Template', headerTooltip: "Name of Template"},
						{ field: 'versionStatus1', displayName: "Template Version", headerTooltip: "Template Version", },
						{ name: 'formName', displayName: "Form Name", headerTooltip: "Form Name" },
						{ name: 'createdBy', displayName: "Created By", headerTooltip : "Created By" },
						{ field: 'createdOn',cellFilter:'date', displayName: "Creation / Updated (Date)", headerTooltip: "Creation / Updated (Date)"},
						{ field: 'templateType', displayName: "Process Type", headerTooltip: "Process Type", },
						{ name: 'formsCount', displayName: "Number of Forms", headerTooltip : "Number of Forms" },
						{ field: 'status',cellFilter:'replaceStatus', displayName: "Status", headerTooltip: "Status"},
					
						];
					let data = [];
					$scope.gridOptions3 = ngGridService.initGrid($scope, columnDefs, data, "Documents_Templates & Forms_Project Templates");
					$scope.getProjectTemplates();
				}
			});
	$scope.createTemplate = function() {
		let project_id = $scope.projectId;
		var createTemplatePopup = ngDialog.open({
			template: 'views/common/createtemplate.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope', function ($scope) {
				$scope.error = false;
				$scope.errormsgs = [];
				$scope.disableButton = true;
				$scope.nextStageBtn = false;
				$scope.nextBtn = true;
				console.log($scope.templateCategoriesList);
				let selected_ctempl_category = '';
				for(let k=0;k<$scope.templateCategoriesList.length;k++)
				{
					if($scope.templateCategoriesList[k].categoryId==$scope.selected_pcategory_id)
					{
						selected_ctempl_category = $scope.templateCategoriesList[k].categoryName;
					}
				}
				var templcode = selected_ctempl_category.split(' ').map(i=>i.charAt(0)).join('').toUpperCase();
				if(templcode.length == 1){
					templcode = selected_ctempl_category.substring(0,3).toUpperCase();
				}
				//let crm_name = $scope.crmName.substring(0,4).toUpperCase();
				//$scope.templateCode = crm_name+"-"+$scope.searchProject.projCode+"-"+templcode+"-"+($scope.lastTemplateId+1);
				//$scope.templateCode = "ORG-"+$scope.searchProject.projCode+"-"+templcode+"-"+($scope.lastTemplateId+1);
				$scope.templateCode = "ORG-"+$scope.searchProject.projCode+"-"+templcode;
				$scope.selectedTemplCategory = {categoryName:selected_ctempl_category};
				console.log("template code:"+$scope.templateCode);			
				$scope.next = function(templateList) {
					console.log("selected template from next function:"+$scope.selected_pcategory_id);
					if( $scope.selected_template == 'From Sample Templates' || $scope.selected_template == 'From Central Templates' )
					{
						console.log("inside the if condition");
						$scope.error = true;
						$scope.showListDiv = false;
						$scope.showCreateTmplDiv = true;
						$scope.showTemplateTypeDiv = false;
						$scope.nextBtn = false;
						$scope.nextStageBtn = true;
						angular.forEach(templateList, function (value,key) {
							console.log(templateList[key]);
			                if(templateList[key].selected) {
			                    templateList[key].selected = true;
								//forms_cnt++;
			                }
							else
							{
								templateList[key].selected = false;
							}
		            	});
					}
					else if( $scope.selected_template == 'Create Own' )
					{
						console.log("inside the else condition");
						let form_name = document.getElementById("form_name").value;
						let template_name = document.getElementById("template_name").value;
						let template_category = document.getElementById("template_category").value;
						console.log("This is from createNewTemplate function");
						if( form_name != "" && template_name != "" )
						{							
							var template_request = {
								formName : form_name,
								templateName : template_name,
								templateCategoryId : $scope.selected_pcategory_id,
								status : 'DRAFT',
								categoryMstrId : $scope.mstrCategoryId,
								createdBy : $rootScope.account.userId,
								templateType : $scope.templateType,
								crmId : $rootScope.account.clientId,
								versionStatus : 1,
								projectId : project_id,
								templateCode : $scope.templateCode,
								formsCount : 0,
								isNew : 'Y',
								mode : 'NEW_TEMPL',
								fromSource : 'SELF'
							};
							console.log(template_request);							
							$state.go('createtemplate',template_request);					
						}
						else
						{
							$scope.error = true;
							$scope.errormsgs = [];
							if( template_name.trim().length == 0 )
							{
								$scope.errormsgs.push("Please enter the template name");
							}
							if( form_name.trim().length == 0 )
							{
								$scope.errormsgs.push("Please enter the form name");
							}
						}
					}
					else
					{
						GenericAlertService.alertMessage("Please select the option to create template", 'Warning');
						$scope.error = true;
					}
					if( !$scope.error )
					{
						ngDialog.close(createTemplatePopup);
					}
				},
				$scope.templateSelection = function(category) {
					$scope.selected_template = category;
					$scope.selectedTemplate = category;
					if( category == "Create Own" )
					{
						$scope.showCreateTmplDiv = true;
						$scope.showMsgDiv = false;
						$scope.showTemplateTypeDiv = true;
						$scope.showListDiv = false;
					}
					else
					{
						$scope.showCreateTmplDiv = false;
						$scope.showMsgDiv = true;
						$scope.showListDiv = true;
						$scope.showTemplateTypeDiv = false;
						
						let req = {				
								templateStatus : "READY_TO_USE"
							};
						if( category == "From Sample Templates" )
						{
							console.log("if condition of From Sample Templates");
							$scope.templateList = [];
							req.categoryMstrId = 1;
							DocumentService.getTemplatesList(req).then(function(data){
								console.log(data);	
								console.log(data.templatesTOs.length);			
								for(var i=0;i<data.templatesTOs.length;i++)
								{
									$scope.templateList.push(data.templatesTOs[i]);
								}
							})
						}
						else
						{
							console.log("if condition of From Central Templates");							
							$scope.templateList = [];
							req.categoryMstrId = 2;
							DocumentService.getCentralTemplatesList(req).then(function(data){
								console.log(data);	
								console.log(data.templatesTOs.length);			
								for(var i=0;i<data.templatesTOs.length;i++)
								{
									$scope.templateList.push(data.templatesTOs[i]);
								}
							})
						}
					}
					$scope.disableButton = false;
				},
				$scope.nextStage = function() {
					console.log("nextStage function");
					console.log($scope.selected_template);
					
					/*
					let templatesArry = $rootScope.sampleTemplatesList.templatesTOs;
					let current_template_json = {};
					var current_template_data;
					for(let count=0;count<templatesArry.length;count++)
					{
						if( templatesArry[count].selected )
						{
							console.log("if condition of for loop");
							current_template_json = templatesArry[count].templateJson;
							current_template_data = templatesArry[count];
						}
					}
					console.log(current_template_data);
					let form_name = document.getElementById("form_name").value;
					let template_name = document.getElementById("template_name").value;
					let template_category = document.getElementById("template_category").value;
					if( form_name.trim().length !=0 && template_name.trim().length !=0 )
					{
						let template_request = {
							templateId : current_template_data.templateId,
							formName : form_name,
							templateName : template_name,
							templateCategoryId : current_template_data.templateCategoryId,
							status : 'DRAFT',
							categoryMstrId : $scope.mstrCategoryId,
							createdBy : $rootScope.account.userId,
							templateType : current_template_data.templateType,
							templateJson : current_template_data.templateJson,
							mode: "createTemplateCopy",
							versionStatus : 1,
							formsCount : 0
						};
						console.log("tempalte request:");
						console.log(template_request);
						ngDialog.close(createTemplatePopup);
						$state.go('createtemplate',template_request);
					}
					else
					{
						$scope.error = true;
						if( form_name.trim().length ==0 )
						{
							$scope.errormsgs.push("Please enter Form Name.");
						}
						if( template_name.trim().length ==0 )
						{
							$scope.errormsgs.push("Please enter Template Name.");
						}
					}
					*/
					var currentSelectedTemplate;
					for(var j=0;j<$scope.templateList.length;j++)
					{
						if( $scope.templateList[j].selected )
						{
							currentSelectedTemplate = $scope.templateList[j];
						}
					}
					$scope.error = false;
					$scope.errormsgs = [];
					let form_name = document.getElementById("form_name").value;
					let template_name = document.getElementById("template_name").value;
					let template_category = document.getElementById("template_category").value;
					if( form_name.trim().length !=0 && template_name.trim().length !=0 )
					{
						let template_request = {
							templateId : currentSelectedTemplate.templateId,
							formName : $scope.form_name,
							templateName : $scope.template_name,
							templateCategoryId : $scope.selected_pcategory_id,
							status : 'DRAFT',
							categoryMstrId : $scope.mstrCategoryId,
							createdBy : $rootScope.account.userId,
							crmId : $rootScope.account.clientId,
							mode : 'createTemplateCopy',
							versionStatus : 1,
							formsCount : 0,
							templateCode : $scope.templateCode,
							templateType : currentSelectedTemplate.templateType,
							templateJson : currentSelectedTemplate.templateJson,
							projectId : project_id,
							fromSource : $scope.selected_template
						};
						console.log(template_request);
						$state.go('createtemplate',template_request);
						ngDialog.close(createTemplatePopup);
					}
					else
					{
						$scope.error = true;
						if( form_name.trim().length ==0 )
						{
							$scope.errormsgs.push("Please enter Form Name.");
						}
						if( template_name.trim().length ==0 )
						{
							$scope.errormsgs.push("Please enter Template Name.");
						}
					}
				}
			}]
		});
	},
	$scope.changeMode = function(mode) {
		console.log("changeMode:"+mode);
		console.log($rootScope.projectTemplatesList);
		$scope.projectTemplatesList = $rootScope.projectTemplatesList.templatesTOs;
		if(mode == 'editView') {
			$scope.mode = 'edit';
			$scope.projectTemplatesList = [];
			for(var i=0;i<$rootScope.projectTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.projectTemplatesList.templatesTOs[i].status == 'DRAFT' )
				{
					$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[i]);
				}
				else if( $rootScope.projectTemplatesList.templatesTOs[i].status == 'SUBMITTED_FOR_INTERNAL_APPROVAL' && $rootScope.projectTemplatesList.templatesTOs[i].internalApprovedBy == $rootScope.account.userId )
				{
					$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[i]);
				}
				else if( $rootScope.projectTemplatesList.templatesTOs[i].status == "SUBMITTED_FOR_EXTERNAL_APPROVAL" && $rootScope.projectTemplatesList.templatesTOs[i].externalApprovedBy == $rootScope.account.userId && $rootScope.projectTemplatesList.templatesTOs[i].isExternalApprovalRequired == "Y" )
				{
					$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[i]);
				}
			}		
		} 
		else if(mode == 'internalApproval') {
			$scope.projectTemplatesList = [];
			for(var i=0;i<$rootScope.projectTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.projectTemplatesList.templatesTOs[i].status == 'DRAFT' || $rootScope.projectTemplatesList.templatesTOs[i].status == 'WF_STAGES_APPROVED' )
				{
					$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[i]);
				}
			}
			$scope.mode = 'SUBMIT_FOR_INTERNAL_APPROVAL';
		}
		else if(mode == 'externalApproval') {
			console.log("externalApproval block");
			$scope.projectTemplatesList = [];
			for(var i=0;i<$rootScope.projectTemplatesList.templatesTOs.length;i++)
			{
				if( $rootScope.projectTemplatesList.templatesTOs[i].status == 'INTERNAL_APPROVED' && $rootScope.projectTemplatesList.templatesTOs[i].isInternalApproved == 'Y' )
				{
					$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[i]);
				}
			}
			$scope.mode = 'SUBMIT_FOR_EXTERNAL_APPROVAL';
		}
		else
		$scope.mode = "VIEW";		
	},
	$scope.submitForApproval = function(template,mode) {
		console.log("submitForApproval function");
		console.log(mode);
		let isDisplayExternalApprovalRequired = false;
		let displayApproverComments = false;
		let approvalModeArry = mode.split("_");
		let approvalMode = approvalModeArry[2];
		TemplateFactory.approvalUserPopup(template,approvalMode,"SUBMITTED",isDisplayExternalApprovalRequired,displayApproverComments);
	},
	$scope.approveTemplate = function(template,mode) {
		console.log(template);
		console.log(mode);
		let approvalMode = mode+"_APPROVAL";
		var template_request = {
			formName : template.formName,
			templateName : template.templateName,
			templateCategoryId : template.templateCategoryId,
			status : 'DRAFT',
			categoryMstrId : template.categoryMstrId,
			createdBy : $rootScope.account.userId,
			templateType : template.templateType,
			versionStatus : template.versionStatus,
			templateCode : template.templateCode,
			formsCount : 0,
			mode : approvalMode,
			workflowId : template.workflowStageId,
			templateJson : template.templateJson,
			templateId : template.templateId
		};
		console.log(template_request);		
		$state.go('createtemplate',template_request);	
	},
	$scope.editTemplate = function() {
		/*let req = {
			templateCategoryId : $scope.selected_pcategory_id,
			categoryMstrId : $scope.mstrCategoryId,
			crmId : $rootScope.account.clientId,
			projId : $scope.projectId
		};
		console.log(req);
		DocumentService.getCentralTemplatesList(req).then(function(data){				
			$scope.mode = 'edit';
			$scope.centralTemplatesList = data.templatesTOs;
			console.log(data);
		})*/
		$scope.mode = 'edit';			
		$scope.projectTemplatesList = [];
		for(let k=0;k<$rootScope.projectTemplatesList.templatesTOs.length;k++)
		{
			let current_element = $rootScope.projectTemplatesList.templatesTOs[k];
			if( $rootScope.projectTemplatesList.templatesTOs[k].status == 'INPROGRESS' )
			{
				if( current_element.internalApprovalStatus == "SUBMITTED" || current_element.externalApprovalStatus == "SUBMITTED" )
				{
					console.log("if condition of if block");
					if( current_element.internalApprovalStatus == "SUBMITTED" && current_element.internalApprovedBy == $rootScope.account.userId )
					{
						console.log("if condition of internalapprovalstatus submitted");
						current_element.internal_approval_button = true;
						current_element.external_approval_button = false;
					}
					if( current_element.internalApprovalStatus == "APPROVED" && current_element.externalApprovalStatus == "SUBMITTED" && current_element.externalApprovedBy == $rootScope.account.userId )
					{
						current_element.internal_approval_button = false;
						current_element.external_approval_button = true;
					}
					$scope.projectTemplatesList.push(current_element);
				}
			}
			else
			{
				console.log("else condition");
				current_element.internal_approval_button = false;
				current_element.external_approval_button = false;
				if( current_element.status == 'DRAFT' || current_element.status == 'READY_TO_USE' )
				{
					$scope.projectTemplatesList.push(current_element);
				}
				
			}			
		}
		console.log("editTemplate function");
		console.log($scope.projectTemplatesList);
	},
	$scope.editExistingTemplate = function(template_id,project_id) {
		console.log($rootScope.projectTemplatesList);
		let current_ptempl = {};
		for( var cnt=0;cnt<$rootScope.projectTemplatesList.templatesTOs.length;cnt++)
		{
			if( $rootScope.projectTemplatesList.templatesTOs[cnt].templateId == template_id )
			{
				current_ptempl = $rootScope.projectTemplatesList.templatesTOs[cnt];
			}			
		}
		/*
		let template_req = {
			templateId : template_id,
			mode : 'edit',
			categoryMstrId : $scope.mstrCategoryId,
			templateCategoryId : current_stempl.templateCategoryId,
			templateJson : current_stempl.templateJson,
			formsCount : 0,
			versionStatus : 1,
			status : 'READY_TO_USE',
			templateType : current_stempl.templateType,
			workflowId : current_stempl.workflowStageId,
			templateStatus : current_stempl.status
		};
		*/
		console.log(current_ptempl);
		let template_req = {
			templateId : template_id,
			mode : 'edit',
			crmId : $rootScope.account.clientId,
			categoryMstrId : $scope.mstrCategoryId,
			projectId : project_id,
			versionStatus : 1,
			status : 'DRAFT',
			formsCount : 0,
			templateCategoryId : current_ptempl.templateCategoryId,
			templateType : current_ptempl.templateType,
			templateJson : current_ptempl.templateJson,
			workflowId : current_ptempl.workflowStageId,
			templateStatus : current_ptempl.status
		};	
		
		/*
		console.log("editExistingTemplate function");
		console.log($rootScope.sampleTemplatesList);
		let current_stempl = {};
		for( var cnt=0;cnt<$rootScope.sampleTemplatesList.templatesTOs.length;cnt++)
		{
			if( $rootScope.sampleTemplatesList.templatesTOs[cnt].templateId == template_id )
			{
				current_stempl = $rootScope.sampleTemplatesList.templatesTOs[cnt];
			}			
		}
		console.log("current sample template:");
		console.log(current_stempl);
		let template_req = {
			templateId : template_id,
			mode : 'edit',
			categoryMstrId : $scope.mstrCategoryId,
			templateCategoryId : current_stempl.templateCategoryId,
			templateJson : current_stempl.templateJson,
			formsCount : 0,
			versionStatus : 1,
			status : 'READY_TO_USE',
			templateType : current_stempl.templateType
		};*/	
		console.log("editExistingTemplate of Project templates");
		console.log(template_req);
		$state.go('createtemplate',template_req);
	},
	$scope.forInternalApproval = function(){
		//$state.go('projecttemplates',{mode:'SUBMIT_FOR_INTERNAL_APPROVAL',selectedCategory:$scope.selected_pcategory_id});
		$scope.mode = 'SUBMIT_FOR_INTERNAL_APPROVAL';
		console.log($rootScope.projectTemplatesList);
		$scope.projectTemplatesList = [];
		for(let k=0;k<$rootScope.projectTemplatesList.templatesTOs.length;k++)
		{
			if( $rootScope.projectTemplatesList.templatesTOs[k].status == 'DRAFT' )
			{
				$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[k]);
			}
		}
	},
	$scope.forExternalApproval = function(){		
		//$state.go('projecttemplates',{mode:'SUBMIT_FOR_EXTERNAL_APPROVAL',selectedCategory:$scope.selected_pcategory_id});
		$scope.mode = 'SUBMIT_FOR_EXTERNAL_APPROVAL';
		$scope.projectTemplatesList = [];		
		console.log("froExternalapproval");
		console.log($rootScope.projectTemplatesList.templatesTOs);
		for(let k=0;k<$rootScope.projectTemplatesList.templatesTOs.length;k++)
		{
			//$rootScope.projectTemplatesList.templatesTOs[k].status == 'DRAFT'
			if( $rootScope.projectTemplatesList.templatesTOs[k].isExternalApprovalRequired == 'Y' && $rootScope.projectTemplatesList.templatesTOs[k].internalApprovalStatus === 'APPROVED' )
			{
				console.log("if condition of forExternalApproval");
				$scope.projectTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[k]);
			}
		}
	},
	$scope.internalApprovalTemplate = function(template_id,template_category) {
		console.log("internalApprovalTemplate function");
		let template_request = {
			templateId : template_id,
			templateCategoryId : template_category,
			status : 'INTERNAL_APPROVAL',
			createdBy : $rootScope.account.userId,
			categoryMstrId : $scope.mstrCategoryId
		};
		console.log(template_request);
		DocumentService.templateApproval(template_request).then(function(data){	
			//$scope.centralTemplatesList = data.templatesTOs;
			console.log(data);
			GenericAlertService.alertMessage('You approved the template', "Info");
		})
	},
	$scope.externalApprovalTemplate = function(template_id,template_category) {
		console.log("externalApprovalTemplate function");
		let template_request = {
			templateId : template_id,
			templateCategoryId : template_category,
			status : 'EXTERNAL_APPROVAL',
			createdBy : $rootScope.account.userId,
			categoryMstrId : $scope.mstrCategoryId
		};
		console.log(template_request);
		DocumentService.templateApproval(template_request).then(function(data){				
			//$scope.mode = 'edit';
			//$scope.centralTemplatesList = data.templatesTOs;
			console.log(data);
			GenericAlertService.alertMessage('You approved the template', "Info");
		})
	},
	$scope.submitForExtApproval = function(template_id,template_category) {
		console.log(template_id);
		/*let template_request = {
			templateId : template_id,
			templateCategoryId : template_category,
			status : 'SUBMIT_FOR_EXTERNAL_APPROVAL',
			createdBy : $rootScope.account.userId,
			categoryMstrId : $scope.mstrCategoryId
		};
		console.log(template_request);
		DocumentService.templateApproval(template_request).then(function(data){				
			//$scope.mode = 'edit';
			//$scope.centralTemplatesList = data.templatesTOs;
			console.log(data);
		})*/
		var externalApprovalPtemplPopup = ngDialog.open({
			template: 'views/documents/approveruserspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {	
				$scope.title = "External Approver User List";
				$scope.approverUserId = "";
				$scope.approvalMode = "EXTERNAL";
				$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1"
				}
				$scope.users = [];
				UserService.getUsers($scope.userReq).then(function(data) {
					let usersList = data.users;
					for(let j=0;j<usersList.length;j++)
					{
						if( usersList[j].userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[j]);
						}
					}
					console.log(data.users);
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				$scope.submit = function() {
					let template_request = {
						templateId : template_id,
						templateCategoryId : template_category,
						status : 'SUBMIT_FOR_EXTERNAL_APPROVAL',
						createdBy : $scope.approverUserId,
						categoryMstrId : $scope.mstrCategoryId
					};
					console.log(template_request);					
					console.log($scope.approverUserId);	
					if( $scope.approverUserId != "" )
					{
						DocumentService.templateApproval(template_request).then(function(data){				
							//$scope.mode = 'edit';
							//$scope.centralTemplatesList = data.templatesTOs;
							console.log(data);
							GenericAlertService.alertMessage("Your request sent to the Approver", 'Info');
						})
						ngDialog.close(externalApprovalPtemplPopup);
					}
					else
					{
						GenericAlertService.alertMessage("Please select the Approver to submit for approval", 'Error');
					}				
				}
			}]
		});
	},
	$scope.submitForIntApproval = function(template_id,template_category) {
		var projInternalApprovalPopup = ngDialog.open({
			template: 'views/documents/approveruserspopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {	
				$scope.title = "Internal Approver User List";
				$scope.approverUserId = "";
				$scope.approvalMode = "INTERNAL";
				$scope.isexternalapprovalrequired = null;
				$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1"
				}
				$scope.users = [];
				UserService.getUsers($scope.userReq).then(function(data) {
					let usersList = data.users;
					for(let j=0;j<usersList.length;j++)
					{
						if( usersList[j].userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[j]);
						}
					}
					console.log(data.users);
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				$scope.toggleExternalApprovalRequired = function(event) {
					$scope.isexternalapprovalrequired = event.target.checked;
				}
				$scope.submit = function() {
					console.log($scope.isexternalapprovalrequired);
					let template_request = {
						templateId : template_id,
						templateCategoryId : template_category,
						status : 'SUBMIT_FOR_INTERNAL_APPROVAL',
						categoryMstrId : $scope.mstrCategoryId
					};
					if( $scope.approverUserId != "" )
					{
						template_request.createdBy = $scope.approverUserId;
					}
					if( $scope.isexternalapprovalrequired != null )
					{
						template_request.isExternalApprovedRequired = ( $scope.isexternalapprovalrequired == true ) ? "Y" : "N";
					}
					else
					{
						template_request.isExternalApprovedRequired = "N";
					}				
					console.log(template_request);
					if( $scope.approverUserId != "" )
					{
						DocumentService.templateApproval(template_request).then(function(data){
							console.log(data);
							GenericAlertService.alertMessage("Your request sent to the Approver", 'Info');
						})
					}
					else
					{
						GenericAlertService.alertMessage("Please select the Approver", 'Error');
					}
				}				
				if( $scope.approverUserId != "" )
				{					
					ngDialog.close(projInternalApprovalPopup);
				}
			}]
		});
	},
	$scope.fromCentralTemplates = function() {
		console.log("fromCentralTemplates function");
		var transferTemplatesPopup = ngDialog.open({
			template: 'views/documents/centraltemplatespopup.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function($scope,$rootScope) {
				$scope.ctemplatesList = [];
				$scope.loading = false;
				let template_req = {
					templateCategoryId : 10,
					crmId : $rootScope.account.clientId,
					categoryMstrId: 2
				};
				console.log(template_req);
				DocumentService.getCentralTemplatesList(template_req).then(function(data){
					$scope.ctemplatesList = data.templatesTOs;
					console.log($scope.ctemplatesList);
				})
				$scope.checkUncheckAllTemplates = function(){
					if ($scope.checkall) {
				    	$scope.checkall = false;
				    } 
					else {
				    	$scope.checkall = true;
				    }
					angular.forEach($scope.ctemplatesList, function (template) {
				    	template.checked = $scope.checkall;
				   	});
				},
				$scope.updateCheckall = function($index,template) {
				    var templsTotal = $scope.projectTemplatesList.length;
				    var count = 0;
				    angular.forEach($scope.ctemplatesList, function (item) {
				       if(item.checked){
				         count++;
				       }
				    });
				
				    if(templsTotal == count){
						$scope.checkall = true;
				    }else{
				        $scope.checkall = false;
				    }
  				},			
				$scope.transfer = function() {
					//$scope.loading = true;
					$scope.ptemplslist = [];
					for(let k=0;k<$scope.ctemplatesList.length;k++)
					{
						if( $scope.ctemplatesList[k].checked && $scope.ctemplatesList[k].checked !== undefined )
						{
							console.log($scope.ctemplatesList[k]);
							$scope.ptemplslist.push({templateId : $scope.ctemplatesList[k].templateId});
						}
					}
					let req = {
						"templatesTOs": $scope.ptemplslist,
						"projectId": $scope.projectId,
						"userId": $rootScope.account.userId,
						categoryMstrId : 3,
						transferFrom : "From Central Templates"
					}
					console.log(req);
					DocumentService.transferTemplates(req).then(function(data){
						console.log(data);
						GenericAlertService.alertMessage('The selected Central Templates transferred successfully', "Info");
						//$scope.loading = false;
					})
					if($scope.loading == false)
					{
						ngDialog.close(transferTemplatesPopup);
					}
				}				
			}]
		});
	},
	$scope.fetchNewProjectTemplates = function() {
		var projTemplateProposalPopup = ngDialog.open({
			template: 'views/documents/projecttemplatesproposal.html',
			className: 'ngdialog-theme-plain ng-dialogueCustom4',
			closeByDocument: false,
			scope: $scope,
			showClose: false,
			controller: ['$scope','$rootScope', function ($scope,$rootScope) {
				console.log($rootScope.projectTemplatesList);
				$scope.projNewTemplatesList = [];
				$scope.approverUserId = 0;
				$scope.templates_list_count = 0;
				for( let k=0;k<$rootScope.projectTemplatesList.templatesTOs.length;k++)
				{
					if( $rootScope.projectTemplatesList.templatesTOs[k].status == "READY_TO_USE" )
					{
						$scope.projNewTemplatesList.push($rootScope.projectTemplatesList.templatesTOs[k]);
						$scope.templates_list_count++;
					}					
				}
				$scope.selectedAll = false;
				$scope.btnVisible = true;
				// select all items
			    $scope.selectAll = function() {
					angular.forEach($scope.projNewTemplatesList, function(item) {
						item.selected = !$scope.selectedAll;
						$scope.btnVisible = false;
					});
			    };
			
			    // use the array "every" function to test if ALL items are checked
			    $scope.updateTemplCb = function() {
					$scope.selectedAll = $scope.projNewTemplatesList.every(function(item) {
						$scope.btnVisible = false;
			        	return item.selected == true;
					})
			    };
				$scope.userReq = {
					"userName" : null,
					"empCode" : null,
					"status" : "1"
				}
				UserService.getUsers($scope.userReq).then(function(data) {
					$scope.users = [];
					let usersList = data.users;
					for(let k=0;k<usersList.length;k++)
					{
						if( usersList.userId != $rootScope.account.userId )
						{
							$scope.users.push(usersList[k]);
						}
					}
					if ($scope.users.length <= 0) {
						GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
						return;
					}
				});
				$scope.request = function() {
					$scope.proposalTemplates = [];
					angular.forEach($scope.projNewTemplatesList, function(item) {
						if( item.selected != undefined && item.selected == true )
						{
							$scope.proposalTemplates.push({projectTemplateId:item.templateId});
						}
			        	//console.log(item);
			      	});
					let proposal_request = {
						fromProjectId : $scope.projectId,
						crmId : $rootScope.account.clientId,
						proposerUserId : $rootScope.account.userId,
						reviewerUserId : $scope.approverUserId,
						projectTemplatesProposalTOs: $scope.proposalTemplates
					};
					if( $scope.approverUserId == 0 )
					{
						GenericAlertService.alertMessage('Please select the Approver to send request', "Warning");
					}
					else
					{
						if( $scope.proposalTemplates.length > 0 )
						{
							DocumentService.saveProjTemplatesProposal(proposal_request).then(function(data){
								console.log(data);
								if( data.templatesCount > 0 )
								{
									GenericAlertService.alertMessage('Request sent to the Approver', "Info");
								}
							})
						}
						else
						{
							GenericAlertService.alertMessage('No template(s) chosen to send request to the Approver', "Warning");
						}
					}					
					console.log($scope.proposalTemplates);
				}
				ngDialog.close(projTemplateProposalPopup);
			}]
		});
	}
}])
.controller('CreateTemplateCtrl',['$scope',"$rootScope","$state","$stateParams","DocumentService","ngDialog","UserService","GenericAlertService","TemplateFactory",function($scope,$rootScope,$state,$stateParams,DocumentService,ngDialog,UserService,GenericAlertService,TemplateFactory) {
	var json_data;
	$scope.templateCategoryId = $stateParams.templateCategoryId;
	$scope.categoryMstrId = $stateParams.categoryMstrId;
	$scope.templateId = $rootScope.newTemplId;
	$scope.templateJson = $stateParams.selectedTemplateJson;
	$scope.templateType = $stateParams.templateType;
	$scope.mode = $stateParams.mode;
	$scope.templateWorkflowId = $stateParams.workflowId;
	$scope.currentWorkflowData = null;
	$scope.stages = [];
	$scope.isTemplateApprover = false;
	$scope.displayApproveBtn = false;
	$scope.inputs = [];
	$scope.isStageFieldAdded = false;
	$scope.stageFieldsCnt = 0;
	$scope.disableSubmitForApprovalBtn = false;
	$scope.disableAddBtn = false;
	$scope.disableApproveBtn = false;
	$scope.isTemplatedSaved = false;
	$scope.disableSaveBtn = false;
	$scope.currentWorkflowStage = "";
		
	console.log($stateParams);
	let wfApprovalStatusData = {"STAGE1_APPROVAL_SUBMITTED":"Submitted for Stage1 Approval","STAGE2_APPROVAL_SUBMITTED":"Submitted for Stage2 Approval"};
	
	if( $scope.categoryMstrId == 1)
	{
		if( $rootScope.sampleTemplatesList.workflowTOs.length != 0 && $scope.mode == 'edit' && $stateParams.templateStatus == 'DRAFT' )
		{
			for(let k=0;k<$rootScope.sampleTemplatesList.workflowTOs.length;k++)
			{
				console.log($rootScope.sampleTemplatesList.workflowTOs);
				if( $rootScope.sampleTemplatesList.workflowTOs[k].workflowId == $scope.templateWorkflowId )
				{
					//$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage1Name));
					//console.log(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage1Name));
					if( $rootScope.sampleTemplatesList.workflowTOs[k].stage1Name != null )
					{						
						$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage1Name));						
						$scope.stageFieldsCnt++;
					}
					if( $rootScope.sampleTemplatesList.workflowTOs[k].stage2Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage2Name));
						$scope.stageFieldsCnt++;
					}
					if( $rootScope.sampleTemplatesList.workflowTOs[k].stage3Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage3Name));
						$scope.stageFieldsCnt++;
					}
					if( $rootScope.sampleTemplatesList.workflowTOs[k].stage4Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage4Name));
						$scope.stageFieldsCnt++;
					}
					if( $rootScope.sampleTemplatesList.workflowTOs[k].stage5Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage5Name));
						$scope.stageFieldsCnt++;
					}
					if( $rootScope.sampleTemplatesList.workflowTOs[k].stage6Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.sampleTemplatesList.workflowTOs[k].stage6Name));
						$scope.stageFieldsCnt++;
					}
					$scope.workflowId = $rootScope.sampleTemplatesList.workflowTOs[k].workflowId;
					$scope.currentWorkflowData = $rootScope.sampleTemplatesList.workflowTOs[k];
				}
			}
		}
	}
	if( $scope.categoryMstrId == 2 )
	{
		console.log($rootScope.centralTemplatesList);
		if( $rootScope.centralTemplatesList != undefined && $rootScope.centralTemplatesList.workflowTOs.length != 0 && $scope.mode == 'edit' && $stateParams.templateStatus == 'DRAFT' )
		{
			for(let k=0;k<$rootScope.centralTemplatesList.workflowTOs.length;k++)
			{
				console.log($rootScope.centralTemplatesList.workflowTOs);
				if( $rootScope.centralTemplatesList.workflowTOs[k].workflowId == $scope.templateWorkflowId )
				{
					//$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage1Name));
					if( $rootScope.centralTemplatesList.workflowTOs[k].stage1Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage1Name));
					}
					if( $rootScope.centralTemplatesList.workflowTOs[k].stage2Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage2Name));
					}
					if( $rootScope.centralTemplatesList.workflowTOs[k].stage3Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage3Name));
					}
					if( $rootScope.centralTemplatesList.workflowTOs[k].stage4Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage4Name));
					}
					if( $rootScope.centralTemplatesList.workflowTOs[k].stage5Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage5Name));
					}
					if( $rootScope.centralTemplatesList.workflowTOs[k].stage6Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.centralTemplatesList.workflowTOs[k].stage6Name));
					}
					$scope.workflowId = $rootScope.centralTemplatesList.workflowTOs[k].workflowId;
					$scope.currentWorkflowData = $rootScope.centralTemplatesList.workflowTOs[k];
				}
			}
		}
	}
	if( $scope.categoryMstrId == 3 )
	{
		console.log($rootScope.projectTemplatesList);
		if( $rootScope.projectTemplatesList.workflowTOs.length != 0 && $scope.mode == 'edit' && $stateParams.templateStatus == 'DRAFT' )
		{
			for(let k=0;k<$rootScope.projectTemplatesList.workflowTOs.length;k++)
			{
				console.log($rootScope.projectTemplatesList.workflowTOs);
				if( $rootScope.projectTemplatesList.workflowTOs[k].workflowId == $scope.templateWorkflowId )
				{
					//$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage1Name));
					console.log(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage1Name));
					if( $rootScope.projectTemplatesList.workflowTOs[k].stage1Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage1Name));
					}
					if( $rootScope.projectTemplatesList.workflowTOs[k].stage2Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage2Name));
					}
					if( $rootScope.projectTemplatesList.workflowTOs[k].stage3Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage3Name));
					}
					if( $rootScope.projectTemplatesList.workflowTOs[k].stage4Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage4Name));
					}
					if( $rootScope.projectTemplatesList.workflowTOs[k].stage5Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage5Name));
					}
					if( $rootScope.projectTemplatesList.workflowTOs[k].stage6Name != null )
					{
						$scope.inputs.push(JSON.parse($rootScope.projectTemplatesList.workflowTOs[k].stage6Name));
					}
					$scope.workflowId = $rootScope.projectTemplatesList.workflowTOs[k].workflowId;
					$scope.currentWorkflowData = $rootScope.projectTemplatesList.workflowTOs[k];
				}
			}
		}
	}
		
	console.log($scope.workflowId);
	console.log($scope.currentWorkflowData);
	
	var submit_approval_request = null;
	if( $stateParams.mode == 'INTERNAL_APPROVAL' || $stateParams.mode == 'EXTERNAL_APPROVAL' )
	{
		$scope.templateApprovalMode = $stateParams.mode.split("_")[0];
		$scope.displayApproverComments = true;
		$scope.isDisplayExternalApprovalRequired = ( $scope.templateApprovalMode == "INTERNAL" ) ? true : false;
		console.log($scope.templateApprovalMode);
	}
	var current_template_approver_user_id = null;
	if( $scope.workflowId != null && $scope.currentWorkflowData.workflowStatus != null )
	{
		let current_workflow_status = $scope.currentWorkflowData.workflowStatus;
		let current_workflow_stage = current_workflow_status.split("_")[0];
		let current_stage_key = current_workflow_stage.toLowerCase()+"ApproverId";
		let previous_stage_index = parseInt(current_workflow_stage.toLowerCase().replace("stage",""))-1;
		let current_stage_status_key = current_workflow_stage.toLowerCase()+"Status";
		let current_stage_status = "";
		
		//console.log(current_workflow_stage+"->"+previous_stage_index);
		//let current_template_approver_user_id = null;
		angular.forEach($scope.currentWorkflowData,function(value,key){
			if( key == current_stage_key ){
				current_template_approver_user_id = value;
			}
			if( key == current_stage_status_key ) {
				current_stage_status = value;
			}
		});			
		
		console.log(current_stage_status+"->"+$scope.displayApproveBtn+"->"+$scope.currentWorkflowData.workflowStatus.indexOf("APPROVED"));
		if( $scope.currentWorkflowData.workflowStatus.indexOf("APPROVED") > -1 )
		{
			$scope.disableSubmitForApprovalBtn = false;
			$scope.disableAddBtn = false;
			$scope.disableApproveBtn = true;
		}
		else
		{
			$scope.disableSubmitForApprovalBtn = true;
			$scope.disableAddBtn = true;
		}
		console.log($scope.disableAddBtn+"->"+$scope.disableSubmitForApprovalBtn);
	}
	
	console.log("current template approveer userId:"+current_template_approver_user_id+"->"+$scope.disableApproveBtn);
	if( current_template_approver_user_id == $rootScope.account.userId )
	{
		$scope.isTemplateApprover = true;
		$scope.displayApproveBtn = true;
		/*if( $scope.templateType == "WORKFLOW" )
		{
			$scope.disableSaveBtn = true;
		}*/
	}
	console.log($scope.isTemplateApprover);
	/*let test_template_request_json = {
		"templateJson":[{"label":"First Name","tableView":true,"key":"firstName","type":"textfield","input":true},{"label":"Reason for leave","autoExpand":false,"tableView":true,"key":"reasonForLeave","type":"textarea","input":true},{"label":"No. of days leave","mask":false,"spellcheck":true,"tableView":false,"delimiter":false,"requireDecimal":false,"inputFormat":"plain","key":"noOfDaysLeave","type":"number","input":true},{"type":"button","label":"Submit","key":"submit","disableOnInvalid":true,"input":true,"tableView":false}]
	};*/
	let test_template_json = {
		"templateJson":[{"label":"First Name","tableView":true,"key":"firstName","type":"textfield","input":true,"placeholder":"","prefix":"","customClass":"","suffix":"","multiple":false,"defaultValue":null,"protected":false,"unique":false,"persistent":true,"hidden":false,"clearOnHide":true,"refreshOn":"","redrawOn":"","modalEdit":false,"labelPosition":"top","description":"","errorLabel":"","tooltip":"","hideLabel":false,"tabindex":"","disabled":false,"autofocus":false,"dbIndex":false,"customDefaultValue":"","calculateValue":"","calculateServer":false,"widget":{"type":"input"},"attributes":{},"validateOn":"change","validate":{"required":false,"custom":"","customPrivate":false,"strictDateValidation":false,"multiple":false,"unique":false,"minLength":"","maxLength":"","pattern":""},"conditional":{"show":null,"when":null,"eq":""},"overlay":{"style":"","left":"","top":"","width":"","height":""},"allowCalculateOverride":false,"encrypted":false,"showCharCount":false,"showWordCount":false,"properties":{},"allowMultipleMasks":false,"mask":false,"inputType":"text","inputFormat":"plain","inputMask":"","spellcheck":true,"id":"esb23fg"},{"label":"Reason for leave","autoExpand":false,"tableView":true,"key":"reasonForLeave","type":"textarea","input":true,"placeholder":"","prefix":"","customClass":"","suffix":"","multiple":false,"defaultValue":null,"protected":false,"unique":false,"persistent":true,"hidden":false,"clearOnHide":true,"refreshOn":"","redrawOn":"","modalEdit":false,"labelPosition":"top","description":"","errorLabel":"","tooltip":"","hideLabel":false,"tabindex":"","disabled":false,"autofocus":false,"dbIndex":false,"customDefaultValue":"","calculateValue":"","calculateServer":false,"widget":{"type":"input"},"attributes":{},"validateOn":"change","validate":{"required":false,"custom":"","customPrivate":false,"strictDateValidation":false,"multiple":false,"unique":false,"minLength":"","maxLength":"","pattern":"","minWords":"","maxWords":""},"conditional":{"show":null,"when":null,"eq":""},"overlay":{"style":"","left":"","top":"","width":"","height":""},"allowCalculateOverride":false,"encrypted":false,"showCharCount":false,"showWordCount":false,"properties":{},"allowMultipleMasks":false,"mask":false,"inputType":"text","inputFormat":"html","inputMask":"","spellcheck":true,"rows":3,"wysiwyg":false,"editor":"","fixedSize":true,"id":"e61n9c"},{"label":"No. of days leave","mask":false,"spellcheck":true,"tableView":false,"delimiter":false,"requireDecimal":false,"inputFormat":"plain","key":"noOfDaysLeave","type":"number","input":true,"placeholder":"","prefix":"","customClass":"","suffix":"","multiple":false,"defaultValue":null,"protected":false,"unique":false,"persistent":true,"hidden":false,"clearOnHide":true,"refreshOn":"","redrawOn":"","modalEdit":false,"labelPosition":"top","description":"","errorLabel":"","tooltip":"","hideLabel":false,"tabindex":"","disabled":false,"autofocus":false,"dbIndex":false,"customDefaultValue":"","calculateValue":"","calculateServer":false,"widget":{"type":"input"},"attributes":{},"validateOn":"change","validate":{"required":false,"custom":"","customPrivate":false,"strictDateValidation":false,"multiple":false,"unique":false,"min":"","max":"","step":"any","integer":""},"conditional":{"show":null,"when":null,"eq":""},"overlay":{"style":"","left":"","top":"","width":"","height":""},"allowCalculateOverride":false,"encrypted":false,"showCharCount":false,"showWordCount":false,"properties":{},"allowMultipleMasks":false,"id":"e6eqmm7"},{"type":"button","label":"Submit","key":"submit","disableOnInvalid":true,"input":true,"tableView":false,"placeholder":"","prefix":"","customClass":"","suffix":"","multiple":false,"defaultValue":null,"protected":false,"unique":false,"persistent":false,"hidden":false,"clearOnHide":true,"refreshOn":"","redrawOn":"","modalEdit":false,"labelPosition":"top","description":"","errorLabel":"","tooltip":"","hideLabel":false,"tabindex":"","disabled":false,"autofocus":false,"dbIndex":false,"customDefaultValue":"","calculateValue":"","calculateServer":false,"widget":{"type":"input"},"attributes":{},"validateOn":"change","validate":{"required":false,"custom":"","customPrivate":false,"strictDateValidation":false,"multiple":false,"unique":false},"conditional":{"show":null,"when":null,"eq":""},"overlay":{"style":"","left":"","top":"","width":"","height":""},"allowCalculateOverride":false,"encrypted":false,"showCharCount":false,"showWordCount":false,"properties":{},"allowMultipleMasks":false,"size":"md","leftIcon":"","rightIcon":"","block":false,"action":"submit","theme":"primary","dataGridLabel":true,"id":"eo44t5p"}]
	}
	console.log($scope.currentWorkflowData);
	test_template_json.templateJson = JSON.parse($stateParams.templateJson);
	console.log(test_template_json);
	var update_template = "N";
	let approverUserId = null;
	$scope.doGreeting = function()
	{
		//console.log("templateJson inside the doGreeting function:"+$scope.templateJson.components);
		/*Formio.builder(document.getElementById('builder'), {}, {})
	    .then(function(builder) {
	  	  	builder.on('saveComponent', function() {
	  	  		json_data = JSON.stringify(builder.schema);
	  		});
	  	});*/
		console.log(test_template_json);
		// code to display the form with fields		
		/*var builder = new Formio.FormBuilder(document.getElementById('builder'), {
		    "display": "form",
		    "components": test_template_json.templateJson
		});
		var instance;
		var setDisplay = function (display) {
		    builder.setDisplay(display).then(function (i) {
		        instance = i;
		        console.log(JSON.stringify(instance.schema, null, 4));
		    });
		};
		//setDisplay('form');
  	  	var saveFrom = function(builder){
	  	  	builder.on('saveComponent', function() {
	  	  		json_data = JSON.stringify(builder.schema);
	  	  		console.log("saveFrom function");
	  	  		console.log(json_data);
	  		});
  	  	}*/
  	  	
		var builder = new Formio.FormBuilder(document.getElementById('builder'), {
		    "display": "form",
		    "components": test_template_json.templateJson
		});
		builder.instance.ready.then(function() {
		    // Set schema 
		    builder.setForm({components: test_template_json.templateJson});
		  
		    builder.instance.on('saveComponent', function() {
				update_template = "Y";			
		    	json_data = JSON.stringify(builder.instance.schema.components);
		    	console.log(json_data);
		    });
		});
	},
	$scope.submitApproval = function() {
		let approvalStageName = null;
		let approvalStageField = null;
		if( $scope.currentWorkflowData == null )
		{
			approvalStageName = "STAGE1";
			approvalStageField = JSON.stringify($scope.inputs[0]);
			console.log($scope.inputs[0]);
		}
		else
		{
			console.log($scope.currentWorkflowData);
			
			let workflow_status = $scope.currentWorkflowData.workflowStatus;
			if( workflow_status == null )
			{
				let workflow_stage = "STAGE1";
				let workflow_index = parseInt(workflow_stage.replace("STAGE",""));
				console.log(workflow_index);
				approvalStageName = workflow_stage;
				approvalStageField = JSON.stringify($scope.inputs[0]);
				console.log($scope.inputs[0]);
			}
			else
			{
				//let workflow_status =  "STAGE1_APPROVAL_INDEX";
				let workflow_stage = workflow_status.split("_");
				let workflow_index = parseInt(workflow_stage[0].replace("STAGE",""))+1;
				console.log(workflow_index);
				approvalStageName = "STAGE"+workflow_index;
				approvalStageField = JSON.stringify($scope.inputs[workflow_index-1]);
			}
		}
		
		console.log("current stage field",approvalStageField);
		if( approvalStageField == null )
		{
			GenericAlertService.alertMessage("Please add the stage name","Info");
		}
		else
		{
			console.log(approvalStageField.value);
			if( $scope.inputs[0].value == undefined || $scope.inputs[0].value.trim() == "" )
			{
				GenericAlertService.alertMessage("Please enter value under the stage name","Info");
			}
			else
			{
				var approvalSubmitPopup = ngDialog.open({
					template: 'views/documents/createapproveruserspopup.html',
					className: 'ngdialog-theme-plain ng-dialogueCustom4',
					closeByDocument: false,
					scope: $scope,
					showClose: false,
					controller: ['$scope','$rootScope', function ($scope,$rootScope) {
						$scope.title = "Approver User List";
						//$scope.approverUserAccountId = "";
						$scope.approvalMode = "";
						$scope.isexternalapprovalrequired = null;
						$scope.isApprover = true;
						$scope.displayApproverList = true;
						$scope.displayApproverComments = false;
						$scope.btnTitle = "Submit";
						$scope.approverUserReq = {
							"userName" : null,
							"empCode" : null,
							"status" : "1"
						}
						$scope.users = [];
						UserService.getUsers($scope.approverUserReq).then(function(data) {
							let approverUsersList = data.users;
							for(let j=0;j<approverUsersList.length;j++)
							{
								if( approverUsersList[j].userId != $rootScope.account.userId )
								{
									$scope.users.push(approverUsersList[j]);
								}
							}
							if ($scope.users.length <= 0) {
								GenericAlertService.alertMessage('Users not available for given search criteria', "Warning");
								return;
							}
						});
						
						console.log(approvalStageField);
						$scope.submit = function() {
							console.log($scope.approverUserId);
							submit_approval_request = {
								"workflowId" : $scope.templateWorkflowId,
								"approverUserId" : $scope.approverUserId,
								"approvalStageName" : approvalStageName,
								"isFinalApproval" : false,
								"approverStatus" : "SUBMITTED",
								"stageFieldJson" : approvalStageField,
								"categoryMstrId" : $stateParams.categoryMstrId			
							}
							console.log(submit_approval_request);
							if( $scope.templateWorkflowId != null )
							{
								$scope.disableAddBtn = true;
								$scope.disableSubmitForApprovalBtn = true;								
								DocumentService.wfSubmitForApproval(submit_approval_request).then(function(data){
									ngDialog.close(approvalSubmitPopup);
									GenericAlertService.alertMessage("Your request sent to the Approver", 'Info');
									if( $stateParams.categoryMstrId == 1 )
									{	
										$state.go('templates',{selected_category_id:$stateParams.templateCategoryId,templateMode:$stateParams.mode});												
									}
									if( $stateParams.categoryMstrId == 2 )
									{
										$state.go('centraltemplates',{selected_ccategory_id:$stateParams.templateCategoryId});
									}
									if( $stateParams.categoryMstrId == 3 )
									{
										$state.go('projecttemplates',{selectedCategory:$stateParams.templateCategoryId});
									}
								})
							}
							else
							{							
								var saveInfoMessage = GenericAlertService.alertMessageModal("Please click on the Save button to save the template", 'Info');
								saveInfoMesssage.then(function(){
									ngDialog.close(approvalSubmitPopup);
								});
							}		
							//ngDialog.close(approvalSubmitPopup);			
						}				
					}]
				});
			}			
		}		
	},
	$scope.approveTemplate = function(templateMode) {
		console.log(templateMode);
		let templateData = {
			templateId : $stateParams.templateId,
			templateCategoryId : $scope.templateCategoryId,
			categoryMstrId : $scope.categoryMstrId
		};
		console.log(templateData);
		TemplateFactory.approvalUserPopup(templateData,templateMode,'APPROVE',$scope.isDisplayExternalApprovalRequired,$scope.displayApproverComments);
	},
	$scope.approve = function() {
		$scope.isApprover = true;
		console.log($scope.isTemplateApprover);
		console.log("approve function");
		console.log($scope.isApprover);
		//$scope.workflowStatus = $scope.currentWorkflowData.status;		
		
		let isApprovalSignatureExists = false;
		$scope.disableAddBtn = false;
		$scope.disableSubmitForApprovalBtn = false;		
		$scope.approverComments = "";
		let workflow_status = $scope.currentWorkflowData.workflowStatus;
		let approvalStageName = null;
		if( workflow_status == null )
		{
			let workflow_stage = "STAGE1";
			let workflow_index = parseInt(workflow_stage.replace("STAGE",""));
			console.log(workflow_index);
			approvalStageName = workflow_stage;
		}
		else
		{
			let workflow_status = $scope.currentWorkflowData.workflowStatus;
			console.log(workflow_status);
			let workflow_stage = workflow_status.split("_");
			let workflow_index = parseInt(workflow_stage[0].replace("STAGE",""));
			console.log(workflow_index);
			approvalStageName = "STAGE"+workflow_index;
		}
		
		//let approvalStageName = $scope.workflowStatus.split('_');
		let approval_request = {
			"workflowId" : $scope.templateWorkflowId,
			"approvalStageName" : approvalStageName,
			"approverStatus" : "APPROVED",
			"approverComments" : $scope.approverComments,
			"templateId" : $stateParams.templateId,
			"categoryMstrId" : $stateParams.categoryMstrId
		}
				
		if( json_data != null && $scope.templateType == "WORKFLOW" )
		{
			let fields_arry = JSON.parse(json_data);
			let field_index = 1;
			fields_arry.forEach(function(obj) {
				console.log(obj.label);
				if( obj.label.indexOf("Signature") != -1 )
				{
					isApprovalSignatureExists = true;
				}				
			});
		}
		if( isApprovalSignatureExists == true )
		{
			$scope.disableSaveBtn = false;
			if( $scope.isTemplateApprover )
			{
				if( !$scope.isTemplatedSaved )
				{
					GenericAlertService.alertMessage("Please save the template","Warning");				
				}
				else
				{
					GenericAlertService.confirmMessageModal('Is this the final stage of approval', 'Warning', 'YES', 'NO').then(function() {			
						console.log("yes button");
						approval_request.isFinalApproval = true;
						console.log(approval_request);				
						DocumentService.wfTemplateApproval(approval_request).then(function(data){
							console.log(data);
							var wfAlertServiceYesPopup = GenericAlertService.alertMessage("You approved the template", 'Info');
							wfAlertServiceYesPopup.then(function(){
								console.log("yes button",$stateParams);
								if( $stateParams.categoryMstrId == 1 )
								{	
									$state.go('templates',{selected_category_id:$stateParams.templateCategoryId,templateMode:"NEW_TEMPL"});												
								}
								if( $stateParams.categoryMstrId == 2 )
								{
									$state.go('centraltemplates',{selected_ccategory_id:$stateParams.templateCategoryId});
								}
								if( $stateParams.categoryMstrId == 3 )
								{
									$state.go('projecttemplates',{selectedCategory:$stateParams.templateCategoryId});
								}
							});
						}) 
					}, function() {
						approval_request.isFinalApproval = false;
						console.log("no button");
						console.log($scope.isStageFieldAdded);
						DocumentService.wfTemplateApproval(approval_request).then(function(data){
							console.log(data);
							$scope.disableSubmitForApprovalBtn = false;
							var wfAlertServiceNoPopup = GenericAlertService.alertMessageModal("You approved the template", 'Info');
							wfAlertServiceNoPopup.then(function(){
								console.log("no button",$stateParams);
								if( $stateParams.categoryMstrId == 1 )
								{
									$state.go('templates',{selected_category_id:$stateParams.templateCategoryId,templateMode:"NEW_TEMPL"});												
								}
								if( $stateParams.categoryMstrId == 2 )
								{
									$state.go('centraltemplates',{selected_ccategory_id:$stateParams.templateCategoryId});
								}
								if( $stateParams.categoryMstrId == 3 )
								{
									$state.go('projecttemplates',{selectedCategory:$stateParams.templateCategoryId});
								}
							});
						})				
					})
				}				
			}
		}
		else
		{
			GenericAlertService.alertMessage('Approval without approval signature not allowed','Info');
		}
	},
	$scope.addInputField = function() {
		let inputs_length = $scope.inputs.length;
		console.log($scope.stageFieldsCnt);
		if( inputs_length < 5 )
		{
			if( $scope.inputs.length > $scope.stageFieldsCnt )
			{
				GenericAlertService.alertMessage('You cannot add more than stage field','Info');
			}
			else
			{
				$scope.isStageFieldAdded = true;
				$scope.disableSubmitForApprovalBtn = false;						
				$scope.inputs.push({inputType:"text"});
			}			
		}
	},
	$scope.updateTemplate = function() {
		console.log(update_template);
		let template_json = (update_template == "Y") ? json_data : $stateParams.templateJson;
		let template_req = {		
			categoryMstrId : $stateParams.categoryMstrId,
			templateJson : template_json,
			status : $stateParams.status,
			createdBy : $stateParams.createdBy,
			formName : $stateParams.formName,
			templateCategoryId : $stateParams.templateCategoryId,
			templateName : $stateParams.templateName,
			templateType : $scope.templateType,
			versionStatus : $stateParams.versionStatus,
			formsCount : $stateParams.formsCount
		};
		if( $stateParams.mode == "edit" || $stateParams.mode == "NEW_TEMPL" )
		{
			template_req.templateId = $stateParams.templateId;
		}
		if( $stateParams.crmId != null )
		{
			template_req.crmId = $stateParams.crmId;
		}
		if( $stateParams.projectId != null )
		{
			template_req.projId = $stateParams.projectId;
		}
		if( $stateParams.isNew != null )
		{
			template_req.isNew = $stateParams.isNew;
		}
		
		if( $scope.inputs.length != 0 && $stateParams.mode == "edit" )
		{
			for(let k=0;k<$scope.inputs.length;k++)
			{
				let index = k+1;
				if( $scope.inputs[k].value != "" ) {
					if(index==1)
					{
						template_req.stage1Name = JSON.stringify($scope.inputs[k]);
					}	
					else if(index==2)
					{
						template_req.stage2Name = JSON.stringify($scope.inputs[k]);
					}
					else if(index==3)
					{
						template_req.stage3Name = JSON.stringify($scope.inputs[k]);
					}
					else if(index==4)
					{
						template_req.stage4Name = JSON.stringify($scope.inputs[k]);
					}
					else if(index==5)
					{
						template_req.stage5Name = JSON.stringify($scope.inputs[k]);
					}
					else if(index==6)
					{
						template_req.stage6Name = JSON.stringify($scope.inputs[k]);
					}
				}
			}
			console.log($scope.inputs);
			template_req.stagesCount = $scope.inputs.length;
		}
		if( $stateParams.mode == "edit" )
		{
			for(let k=0;k<$scope.inputs.length;k++)
			{
				console.log($scope.inputs[k]);
			}
		}		
		//$scope.isTemplatedSaved = true;
		template_req.templateCode = $stateParams.templateCode;
		if( $stateParams.mode == "createTemplateCopy" ) {
			template_req.mode="createTemplateCopy";
			template_req.isFinalApproval = false;			
			if( $stateParams.templateType == "WORKFLOW" && $scope.inputs.length != 0 )
			{
				template_req.approvalStageName = "STAGE1";
				template_req.stageFieldJson = JSON.stringify($scope.inputs[0]);				
				template_req.approverUserId = submit_approval_request.approverUserId;
			}			
			template_req.fromSource = $stateParams.fromSource;
			template_req.templateId = $stateParams.templateId;
			console.log(template_req);	
			DocumentService.saveNewTemplate(template_req).then(function(data){
				console.log(data);
			})
		}
		else if( $stateParams.mode == "NEW_TEMPL" ) {
			template_req.mode = "newTemplate";
			//console.log(template_req);
			console.log(submit_approval_request);
			template_req.isInternalApproved = "N";
			template_req.isExternalApproved = "N";
			//console.log($scope.inputs.length);
			template_req.isFinalApproval = false;
			if( $stateParams.templateType == "WORKFLOW" && $scope.inputs.length != 0 )
			{
				template_req.approvalStageName = "STAGE1";
				template_req.stageFieldJson = JSON.stringify($scope.inputs[0]);				
				template_req.approverUserId = submit_approval_request.approverUserId;
			}			
			template_req.fromSource = $stateParams.fromSource;
			console.log(template_req);
			
			DocumentService.saveNewTemplate(template_req).then(function(data){
				console.log(data);
			}) //correct
		}
		else
		{
			template_req.workflowId = $scope.workflowId;
			template_req.isFinalApproval = false;
			console.log(template_req);	
			DocumentService.cloneTemplate(template_req).then(function(data){
				console.log("cloneTemplate service");
				console.log(data);
			})
		}
		if( $stateParams.categoryMstrId == 1 )
		{
			if( $scope.templateType == "WORKFLOW" )
			{
				GenericAlertService.alertMessage("You saved the template","Info");
				console.log($scope.inputs);
				$scope.isTemplatedSaved = true;
				$scope.disableSaveBtn = true;						
			}
			else
			{
				$state.go('templates',{selected_category_id:$stateParams.templateCategoryId,templateMode:$stateParams.mode});
			}			
		}
		if( $stateParams.categoryMstrId == 2 )
		{
			/*if( $scope.templateType == "WORKFLOW" )
			{
				GenericAlertService.alertMessage("You saved the template","Info");	
			}
			else
			{
				$state.go('centraltemplates',{selected_ccategory_id:$stateParams.templateCategoryId});
			}*/
			$state.go('centraltemplates',{selected_ccategory_id:$stateParams.templateCategoryId});	
		}
		if( $stateParams.categoryMstrId == 3 )
		{
			if( $scope.templateType == "WORKFLOW" )
			{
				GenericAlertService.alertMessage("You saved the template","Info");		
			}
			else
			{
				$state.go('projecttemplates',{selectedCategory:$stateParams.templateCategoryId});
			}			
		}
	}
}])
.controller('ProjectFormsCtrl',['$scope',"$rootScope","ngDialog","$state","EpsProjectSelectFactory","DocumentService","stylesService","ngGridService",function($scope,$rootScope,ngDialog,$state,EpsProjectSelectFactory,DocumentService,stylesService,ngGridService) {
	$scope.formTypesList = [{type:'Timeline',value:'TIMELINE'},{type:'Workflow',value:'WORKFLOW'}];
	$scope.stylesSvc = stylesService;
	$scope.formType = 'TIMELINE';
	$scope.mstrCategoryId = 3;
	$scope.selected_project = 0;
	$scope.templateCategoriesList = [];
	$scope.projTemplateData = [];
	$scope.projTemplateList = [];
	$scope.templatesIdList = 0;
	$scope.templateCategoryId = 0;
	$scope.templateId = 0;
	$scope.displayMessage = "Please select relevant fields above and click on the Search button";
	$scope.formData = [];
	$scope.gridview = false;
	$scope.registerview = true;
	$scope.disableCreateButton = false;
	
	$scope.getUserProjects = function () {
		var userProjectSelection = EpsProjectSelectFactory.getEPSUserProjects();
		userProjectSelection.then(function (data) {
			console.log('data.searchProject'+JSON.stringify(data.searchProject))
			$scope.searchProject = data.searchProject;
			$scope.selected_project = data.searchProject.projId;
			if( $scope.selected_project != 0 )
			{
				let request = { 
					projId : $scope.selected_project,
					crmId : $rootScope.account.clientId 
				};
				DocumentService.searchProjectTemplates(request).then(function (data) {
					console.log("searchProjectTemplates document service response:");
					console.log(data);
					$rootScope.projTemplatesData = data;
					$scope.projTemplateData = data.templatesTOs;
					$scope.templateCategoriesList = [];
					for( let j=0; j < data.templatesTOs.length; j++ )
					{
						let current_element = data.templatesTOs[j];
						if($scope.templateCategoriesList.filter(categories=>categories.categoryName == current_element.templCategoryname).length == 0 )
						{
							$scope.templateCategoriesList.push({categoryId:data.templatesTOs[j].templateCategoryId,categoryName:data.templatesTOs[j].templCategoryname});
						}
					}
					console.log($scope.templateCategoriesList);
				})
			}
		}, function (error) {
			GenericAlertService.alertMessage("Error occured while selecting EPS Project name", 'Error');
		});
	},
	$scope.displaySelected = function(action) {
		console.log(action);
	},
	$scope.openProjectForm = function() {
		console.log("openProjectForm function");
		console.log($rootScope.projTemplatesData);
		console.log($scope.templateCategory+"->"+$scope.formType+'->'+$scope.selected_project+'->'+$scope.templateId);
		let templJson = null;
		let templFormName = null;
		let templFormType = null;
		let fromSource = null;
		for(let k=0;k<$rootScope.projTemplatesData.templatesTOs.length;k++)
		{
			if( $rootScope.projTemplatesData.templatesTOs[k].templateId == $scope.templateId )
			{
				templJson = $rootScope.projTemplatesData.templatesTOs[k].templateJson != null ? $rootScope.projTemplatesData.templatesTOs[k].templateJson : null;
				templFormName = $rootScope.projTemplatesData.templatesTOs[k].formName;
				templFormType = $rootScope.projTemplatesData.templatesTOs[k].templateType;
				fromSource = $rootScope.projTemplatesData.templatesTOs[k].fromSource;				
			}
		}
		//console.log($scope.projTemplateList);
		let form_request = {
			projectId : $scope.selected_project,
			templateCategoryId : $scope.templateCategory,
			projectTemplateId : $scope.templateId,
			templateJson : templJson,
			formName : templFormName,
			formType : templFormType,
			mode : 'CREATE',
			formVersion : 'V1',
			fromSource : fromSource
		};
		console.log(form_request);		
		$state.go('createform',form_request);
	},
	$scope.onTemplCategoryChange = function(event) {
		console.log("onTemplCategoryChange function");
		console.log($scope.templateCategory);
		console.log($scope.projTemplateData);
		$scope.projTemplateList = [];
		for(let k=0;k<$scope.projTemplateData.length;k++)
		{
			if( $scope.templateCategory == $scope.projTemplateData[k].templateCategoryId && $scope.projTemplateData[k].templateType == "TIMELINE" )
			{
				$scope.projTemplateList.push({templateId:$scope.projTemplateData[k].templateId,templateName:$scope.projTemplateData[k].templateName,formName:$scope.projTemplateData[k].formName,templateType:$scope.projTemplateData[k].templateType});
			}
		}
		console.log($scope.projTemplateList);
	},
	$scope.onTemplTypeChange = function(event) {
		console.log("onTemplTypeChange function");
		console.log($scope.formType);
		//console.log($scope.projTemplateData);
		$scope.projTemplateList = [];
		if( $scope.projTemplateData.length != 0 )
		{
			console.log($scope.templateCategory);
			for(let k=0;k<$scope.projTemplateData.length;k++)
			{
				if( $scope.formType == $scope.projTemplateData[k].templateType && $scope.templateCategory == $scope.projTemplateData[k].templateCategoryId )
				{
					$scope.projTemplateList.push({templateId:$scope.projTemplateData[k].templateId,templateName:$scope.projTemplateData[k].templateName,formName:$scope.projTemplateData[k].formName});
				}
			}
		}
		console.log($scope.projTemplateList);
	},
	$scope.searchProjectForms = function() {
		$scope.displayMessage = "";
		console.log($scope.templateCategory+"->"+$scope.formType+'->'+$scope.selected_project+'->'+$scope.templateId);
		let form_request = {
			projectId : $scope.selected_project,
			templateCategoryId : $scope.templateCategory,
			formType : $scope.formType,
			projectTemplateId : $scope.templateId
		};
		DocumentService.getProjFormsList(form_request).then(function(data){
			console.log(data);
			$scope.formData = data.formsTOs;
		    $scope.gridOptions4.data = angular.copy($scope.formData);
			let formList = data.formsTOs;
			$scope.formList = [];
			
			if( formList.length == 0 )
			{
				$scope.displayMessage = "No forms created yet with the selected project template.";
			}
			else
			{
				$scope.disableCreateButton = true;
				for(let k=0;k<formList.length;k++)
				{
					$scope.formList.push(formList[k]);					
				}
			}
			console.log($scope.formList);
		})
	},
	$scope.editProjectForms = function() {
		console.log($scope.formList);
		angular.forEach($scope.formList, function (value,key) {
			console.log($scope.formList[key]);
            if($scope.formList[key].status=='DRAFT') {
                $scope.formList[key].edit = true;
				//forms_cnt++;
            }
        });
		$scope.gridview = false;
		$scope.registerview = true;
	},
	$scope.editForm = function(event) {
		//console.log(event.target.id);
		let current_element_id = event.target.id;
		let current_elem_id = current_element_id.replace("edit","");
		var forms = [];
		angular.forEach($scope.formList, function (value,key) {
			console.log($scope.formList[key]);
            if($scope.formList[key].formId==current_elem_id) {
                forms.push($scope.formList[key]);
            }
        });
		let form_version = forms[0].formVersion;
		let form_conv = parseInt(form_version.substring(1,2))+1;
		let next_version = "V"+form_conv;
		let form_request = {
			projectId : $scope.selected_project,
			templateCategoryId : $scope.templateCategory,
			projectTemplateId : $scope.templateId,
			templateJson : forms[0].templateJson,
			formJson : forms[0].formJson,
			formName : forms[0].formName,
			formType : forms[0].formType,
			mode : 'EDIT',
			formVersion : next_version,
			formId : forms[0].formId
		};
		console.log(form_request);				
		$state.go('createform',form_request);
		console.log(forms);
	},
	$scope.displayView = function(view) {
		if( view == 'Grid View' )
		{
			$scope.gridview = true;
			$scope.registerview = false;
		}
		else
		{
			$scope.registerview = true;
			$scope.gridview = false;	
		}		
	},
	$scope.reset = function () {
		$scope.searchProject = null;
		$scope.editTreeData = [];
		$scope.formList = [];
		$scope.templateCategoriesList = [];
		$scope.templateCategory = null;
		$scope.templateId = null;
		$scope.projTemplateList = [];
	}
	$scope.$watch(function () { return stylesService.finishedStyling; },
			function (newValue, oldValue) {
				if (newValue) {
					let columnDefs = [
						{ name: 'formId', displayName: 'Form Id', headerTooltip : "Form Id" },
						{ field: 'formName', displayName: 'Form Name', headerTooltip: "Form Name"},
						{ field: 'createdBy', displayName: "Created By", headerTooltip: "Created By", },
						{ field: 'createdOn',cellFilter:'date', displayName: "Creation / Updated (Date)", headerTooltip: "Creation / Updated (Date)"},
						{ field: 'formType', displayName: "Process Type", headerTooltip: "Process Type", },
						{ field: 'status',cellFilter:'replaceStatus', displayName: "Status", headerTooltip: "Status"},
						];
					let data = [];
					$scope.gridOptions4 = ngGridService.initGrid($scope, columnDefs, data, "Documents_Templates & Project Forms");
				}
			});
}])
.controller('WebFormCtrl',['$scope',"$rootScope","$state","GenericAlertService",function($scope,$rootScope,$state,GenericAlertService) {
	console.log("werbwer");
	$scope.myTemplateJson='{"label":"First Name","tableView":true,"key":"firstName","type":"textfield","input":true},{"label":"Reason for leave","autoExpand":false,"tableView":true,"key":"reasonForLeave","type":"textarea","input":true},{"label":"No. of days leave","mask":false,"spellcheck":true,"tableView":false,"delimiter":false,"requireDecimal":false,"inputFormat":"plain","key":"noOfDaysLeave","type":"number","input":true},{"label":"Table","cellAlignment":"left","bordered":true,"highlight":false,"tableView":false,"key":"table","type":"table","input":false,"rows":[[{"components":[{"html":"<h4 style=\"text-align:center;\">H1</h4>","label":"Content","customClass":"bg-red","refreshOnChange":false,"tableView":false,"key":"content","type":"content","input":false}]},{"components":[{"html":"<p>H2</p>","label":"Content","refreshOnChange":false,"tableView":false,"key":"content1","type":"content","input":false}]},{"components":[{"label":"HTML","attrs":[{"attr":"","value":""}],"content":"<span style=\"color:red\">H3</span>","refreshOnChange":false,"tableView":false,"key":"html","type":"htmlelement","input":false}]}],[{"components":[]},{"components":[]},{"components":[]}],[{"components":[]},{"components":[]},{"components":[]}]]},{"type":"button","label":"Submit","key":"submit","disableOnInvalid":true,"input":true,"tableView":false}';
	
	/*Formio.createForm(document.getElementById('formio'), $scope.myTemplateJson).then(function(form) {								
		console.log("edit block");
		console.log(JSON.parse($scope.myTemplateJson));
		form.submission = {
		    data: JSON.parse($scope.myTemplateJson)
		}			
	});	*/
}])
.controller('CreateFormCtrl',['$scope',"$rootScope","$stateParams","$state","DocumentService","GenericAlertService",function($scope,$rootScope,$stateParams,$state,DocumentService,GenericAlertService) {
	console.log($stateParams);
	$scope.templateJson = {"components":JSON.parse($stateParams.templateJson)};
	$scope.resultJson = {};
	$scope.resultFormData = [];
	$scope.templJson = JSON.parse($stateParams.templateJson);
	var fields_counter = 0;
	var fields_length = 0;
	$scope.displayForm = function() {
		Formio.createForm(document.getElementById('formio'), $scope.templateJson).then(function(form) {
			// to display the form fields with filled values in the form
			if( $stateParams.mode == "EDIT" )
			{		
				console.log("edit block");
				console.log(JSON.parse($stateParams.formJson));		
				form.submission = {
				    data: JSON.parse($stateParams.formJson)
				}
			}			
			// Submit the form
			form.on('submit', function(submission) {
				$scope.resultJson = submission.data;
				console.log($scope.resultJson);
				let formcomponents = $scope.templJson;
				console.log($scope.templateJson);
				fields_length = formcomponents.length-1;				
				/*for(let counter=0;counter<formcomponents.length;counter++)
				{
					let current_key = formcomponents[counter].key;			
					$scope.templJson[counter].value = $scope.resultJson[current_key];
					$scope.resultFormData.push($scope.templJson[counter]);
				}*/
				angular.forEach($scope.resultJson,function(value,key){
					console.log(value+"-"+key);
					if( key != "submit" )
					{
						if( value!="" || value != false )
						{
							fields_counter++;
						}
					}					
				});
				console.log("result:");
				console.log($scope.resultFormData);
				console.log(fields_counter+"->"+fields_length);
				console.log($scope.templJson);
				let form_status = ( fields_length != fields_counter ) ? "DRAFT" : "COMPLETED";
				let form_request = {
					projectId : $stateParams.projectId,
					templateCategoryId : $stateParams.templateCategoryId,
					projectTemplateId : $stateParams.projectTemplateId,
					templateJson : $stateParams.templateJson,
					formJson : JSON.stringify($scope.resultJson),
					formName : $stateParams.formName,
					status : form_status,
					formType : $stateParams.formType,
					formVersion : $stateParams.formVersion,
					fromSource : $stateParams.fromSource
				};
				//console.log(form_request);
				if( $stateParams.mode == "EDIT" )
				{
					form_request.formId = $stateParams.formId;
					form_request.mode = "EDIT";
					console.log("edit block");
					console.log(form_request);
					DocumentService.updateProjectForm(form_request).then(function(data){
						GenericAlertService.alertMessage("Form updated successfully", 'Info');
					})
				}
				else
				{
					console.log("created block");
					console.log(form_request);
					form_request.mode = "CREATE";
					DocumentService.createProjectForm(form_request).then(function(data){
						GenericAlertService.alertMessage("Form submitted successfully", 'Info');
					})
				}
				$state.go('projectregisterforms');			
		   	});
		});		
	}
}])
.filter('capitalizeWord', function() {
    return function(input) {	
		return (!!input) ? input.split(' ').map(function(wrd){return wrd.charAt(0).toUpperCase() + wrd.substr(1).toLowerCase();}).join(' ') : '';
    }
})
.filter('replaceStatus', function() {
    return function(input, templateType, currentWfStatus ) {
		var result;
		console.log(currentWfStatus);
		if( templateType == "WORKFLOW" )
		{
			result = input == "DRAFT" && currentWfStatus !=null ? currentWfStatus : input
		}
		else
		{
			result = input == "READY_TO_USE" ? "CURRENT" : input
		}
		return result;
    }
});