'use strict';
app.factory('PostAnInvoiceFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope","ProcureService", "UserService","GenericAlertService", "PostInvoiceDeliveryDocketsFactory","PreContractCostPopupFactory", "ProjectInvoiceService",
    function (ngDialog, $q, $filter, $timeout, $rootScope,ProcureService,UserService,GenericAlertService, PostInvoiceDeliveryDocketsFactory,PreContractCostPopupFactory, ProjectInvoiceService) {
        var generatePopUp;
        var service = {};
        service.generateInvoiceDetails = function (actionType,data,userProjMap,selectedData) {
            var deferred = $q.defer();
            generatePopUp = ngDialog.open({
                template: 'views/finance/financemain/costpayables/vendorinvoices/postaninvoicepopup.html',
                scope: $rootScope,
                className: 'ngdialog-theme-plain ng-dialogueCustom0',
                closeByDocument: false,
                showClose: true,
                controller: ['$scope', function ($scope) {
                console.log('PostAnInvoiceFactory   ');
                
                $scope.projCostCodeLabelKeyTO;
                $scope.bankName;
                $scope.accountName;
                $scope.accountNumber;
                $scope.bankCode;
                
                $scope.payableSubCat;
                $scope.unitOfMeasure;
                $scope.procurmentSubCat;
                $scope.totalValue;
                $scope.procurmentSubCat;
                 $scope.meterialPSubData1=[];
                 $scope.meterialPSubData=[];
                $scope.totalamount = 0;
                $scope.dummy_amount;
                $scope.TotalAmout = 0;
                $scope.systemNetInvoiceAmount;
                $scope.systemgstTxtInvoiceAmount;
                $scope.systemTotalInvoiceAmountInclTax;
                $scope.expenditureType;
                
                $scope.asPerNetInvoiceAmount;
                $scope.asPerGSTTaxInvoiceAmount;
                $scope.asPerTotalInvoiceAmount;
                
                $scope.retainedInvoiceAmount;
                $scope.retainedGSTTaxInvoiceAmount;
                $scope.retainedTotalInvoiceAmount;
                
                $scope.netAmountPayable;
                $scope.netAmountGSTTaxInvoiceAmount;
                $scope.netAmountTotalInvoiceAmount;
                
                $scope.costCodeSplit;
                
                $scope.manpowerInvoicePeriod = true;
                $scope.manpowerMobilisationChargesPerProjectPerEmployee = false;
                $scope.manpowerDeMobilisationChargesPerProjectPerEmployee = true;
                $scope.manpowerUtilisationChargesPerHourly = false;
                $scope.manpowerUtilisationChargesPerDaily = false;
                $scope.manpowerUtilisationChargesPerMonthly = false;
                
                $scope.plantsUtilisationChargesPerInvoicePeriodInMonth = false;
                $scope.plantsUtilisationChargesPerInvoicePeriodInDays = false;
                $scope.plantsUtilisationChargesPerHourlyRate = false;
                $scope.plantsDeMobilisationChargesPerDeployment = false;
                $scope.plantsMobilisationChargesPerDeployment = false;
                $scope.plantsContractCostPayments = true;
                
                $scope.minDate = new Date();
                
                
                $scope.calculateTotal = function(rate, qty) {
                	console.log('qty ', qty);
                	$scope.totalamount = $scope.totalamount + (rate*qty);
                	console.log('qty ', $scope.totalamount);
                }
                
                             
               
                $scope.projCostCode = [];
                $scope.materialDelDockets = [];
                $scope.manpowerDelDockets = [];
                $scope.plantsDelDockets = [];
                $scope.serviceDelDockets = [];
                $scope.subDelDockets = [];
                
                $scope.VendorBankAccountDetailsTO  =  {
						"id": 1,
						"bankName":'SBI',
						"accountName":'James',
						"accountNumber":21222222222222,
						"bankCode":'SBIN000988',
						"swiftCode":''
					};
                
                $scope.approverValidateDetailsTO  =  {
                		'approverId': '123',
                		'approverName':'vv',
                		'isSubmitForApprover': false,
                		'isAccountDetailsVerified': false
                };
                
                $scope.docketTabs = [{
                    title: 'Material',
                    url: 'views/finance/financemain/costpayables/vendorinvoices/invoicedeliverydocketsutilisationrecords.html'
                }, {
                    title: 'Man Power',
                    url: 'views/finance/financemain/costpayables/vendorinvoices/invoicedeliverydocketsutilisationmanpowerrecords.html'
                }, {
                    title: 'Plants',
                    url: 'views/finance/financemain/costpayables/vendorinvoices/invoicedeliverydocketsutilisationplantsrecords.html'
                }, {
                    title: 'Services',
                    url: 'views/finance/financemain/costpayables/vendorinvoices/invoicedeliverydocketsutilisationservicesrecords.html'
                }, {
                    title: 'Sub',
                    url: 'views/finance/financemain/costpayables/vendorinvoices/invoicedeliverydocketsutilisationsubrecords.html'
                }];
              
                $scope.paymentDueDate;
                $scope.invoiceExpenditureType;
                $scope.docketCurrentTab = $scope.docketTabs[0].url;
                $scope.docketTabs[1].disabled = false;
                $scope.docketTabs[2].disabled = false;
                $scope.docketTabs[3].disabled = false;
                $scope.docketTabs[4].disabled = false;
                
                //----------METERIAL------------------------
                $scope.procReq = {
						"procureId": 'Services',
						"subProcureName": null,
						"status": "1"
					};
					console.log($scope.procReq);
				ProcureService.getProcureCatgs($scope.procReq).then(function(data) {
					$scope.Meterialdata=data.procureMentCatgTOs;
					for(var i=0; i<$scope.Meterialdata.length; i++){
						if($scope.Meterialdata[i].procureId == 'Materials'){
							 $scope.meterialPSubData1.push($scope.Meterialdata[i].desc);
						}
					}
					$scope.meterialPSubData = $filter('unique')($scope.meterialPSubData1,'$scope.meterialPSubData1');
					console.log( $scope.meterialPSubData1,"data")					
					console.log($scope.meterialPSubData,"unique data")
				})	
                
                $scope.MeterialFilterdata={
				'subcat':null,
				'Payable':null,
				'unitMeas':null
				}
                //$scope.meterialPSubData=['Civil Items','Electrical Items', 'Admin Items']
                $scope.MeterialSearch=function(){
				console.log($scope.MeterialFilterdata.subcat);
				console.log($scope.MeterialFilterdata.Payable);
				console.log($scope.MeterialFilterdata.UnitMeasure)				
				}
                
                //-----------MANPOWER------------------------- ---
                
                $scope.procurementSubList =  ['Full Time (Own Labour)', 'Casual','Contract','Part Time','Sub Contract'];                  
                $scope.ManpowerFilterdata={
				'procurementsub':null,
				'payableCat':null,
				'unitMeas':null
				}           
              	$scope.payableChange = function(value){
	              console.log(value);
	              if(value == 'Full Time (Own Labour)'){
		           $scope.ManpowerFilterdata.payableCat="";
		           $scope.payableCatg=['Recruitment Fee'];
		           $scope.ManpowerFilterdata.unitMeas ="";
		            } 
	              else {
		            $scope.ManpowerFilterdata.payableCat="";
					$scope.payableCatg=['Mobilisation Charges', 'De-Mobilisation Charges', 'Utilisation Charges'];
					 $scope.ManpowerFilterdata.unitMeas ="";
					}
                }
				$scope.unitMeasureChange = function(value) {
                      console.log(value);
                  if(value == 'Recruitment Fee' || value =='Mobilisation Charges'||value =='De-Mobilisation Charges'){
	                     $scope.ManpowerFilterdata.unitMeas ="";
	 					 $scope.unitMeasure = ['Each'];
			          } 
			      else
			      	  {
						$scope.ManpowerFilterdata.unitMeas ="";
				 		$scope.unitMeasure = ['Hourly Rate', 'Daily Rate', 'Monthly Rate']
					  }
				}
				$scope.ManpowerSearch=function(){
                console.log( $scope.ManpowerFilterdata.unitMeas);
                console.log($scope.ManpowerFilterdata.payableCat );
                console.log($scope.ManpowerFilterdata.procurementsub)
                }
                
                //-----------------Plant-----------------------------------
                
                $scope.platSubCat=['Purchase (Own Plant)', 'Hire Purchase','Lease Purchase','Sub Contract']
                $scope.PlantFilterdata={
					'subCat':null,
					'PayCat':null,
					'unitMeas':null
				}
				$scope.PlantSubchange=function(value){
					console.log(value)
					if(value == "Purchase (Own Plant)"){
						$scope.PlantPayableCat=['Contract Price'];
						$scope.PlantFilterdata.PayCat="";
						$scope.PlantFilterdata.unitMeas="";
					}
					else{
						$scope.PlantPayableCat=['Mobilisation Charges', 'De-Mobilisation Charges', 'Utilisation Charges']
					    $scope.PlantFilterdata.PayCat="";
						$scope.PlantFilterdata.unitMeas="";
					}
				}
				$scope.PlantPayableChange=function(value){
					console.log(value)
					if(value=='Contract Price' || value== 'Mobilisation Charges' || value=='De-Mobilisation Charges' ){
						$scope.PlantFilterdata.unitMeas="";
						$scope.PlantUnitMeasure=['Each']
					}
					else{
						$scope.PlantFilterdata.unitMeas="";
						$scope.PlantUnitMeasure=['Hourly Rate', 'Daily Rate', 'Monthly Rate']
					}
				}				
                $scope.PlantSearch=function(){
				console.log($scope.PlantFilterdata);
				
				}
				
				//------------Services-------------------------
				
				$scope.ServiceFilterdata={
					'subCat':null,
					'payCat':null,
					'UnitMeas':null
				}
				$scope.ServiceSubCat=['Engineering Services','Construction Services','Project Management Services']
                $scope.serviceSearch=function(){
				console.log($scope.ServiceFilterdata);
			
				}
				
				//------------Sub--------------------------------
				
				$scope.SubFilterdata={
					'subCat':null,
					'pay':null,
					'UnitMeas':null
				}
				$scope.SubproCat=['Schedule of Rates Basis','Cost Plus % Basis','Contract milestones Basis'];
				$scope.SubSearch=function(){
					console.log($scope.SubFilterdata.subCat);
					console.log($scope.SubFilterdata.pay);
					console.log($scope.SubFilterdata.UnitMeas)
				}
			//----------------------------------------------------------
               $scope.onClickTab = function (dockettab) {
                	//console.log('onclick tab fired   ',dockettab);
                	 if(dockettab.disabled)
                	      return;
                    $scope.docketCurrentTab = dockettab.url;
                }
                $scope.isActiveTab = function (tabUrl) {
                    return tabUrl == $scope.docketCurrentTab;
                },
              
                console.log('**** postan invociefactory ********* ');
                console.log('data.preContractTO.preContractCmpTOs  ==> ');
                  $scope.preContractCmpTOs = data.preContractTO.preContractCmpTOs;
                  $scope.preContractTOId = data.preContractTO.id;
                  console.log($scope.preContractCmpTOs);
                  $scope.preContractDtoTOs = data;
                  $scope.projMaterialClassMap = data.projMaterialClassMap;
			      $scope.preContractTO = data.preContractTO;
                  $scope.storeMap = data.storeMap;
                  $scope.projCostItemMap = data.projCostItemMap;
                  $scope.usersMap = data.usersMap;
                  $scope.companyMap = data.companyMap;
                  $scope.projSOWMap = data.projSOWMap;
                  $scope.procureCategoryMap = data.procureCategoryMap;
                  $scope.userProjMap = userProjMap;
                  $scope.selectedData = selectedData;
                  $scope.vendors = [];
                  $scope.loggedInUser = $scope.account.displayRole;
                  console.log('$scope.preContractTO.preContractMaterialDtlTOs.preContractMaterialCmpTOs  ==>');
                  $scope.materialInvoiceDetails = [];
                  $scope.preContractMaterialDtlTOs = $scope.preContractTO.preContractMaterialDtlTOs;
                  console.log($scope.preContractMaterialDtlTOs);
                  console.log($scope.preContractMaterialDtlTOs.preContractMaterialCmpTOs);
                  var preContractMaterial = $scope.preContractMaterialDtlTOs;
                  angular.forEach(preContractMaterial, function(materialDelDocket, key){
                	  console.log(materialDelDocket); 
            		  $scope.totalamount = $scope.totalamount + (materialDelDocket.preContractMaterialCmpTOs[0].rate*materialDelDocket.preContractMaterialCmpTOs[0].quantity);
            		  console.log( $scope.totalamount); 
            		  $scope.materialDelDockets.push({
            			  "docketId": 98076,
            			  "docketDate": 2020-10-10,
            			  "finishDate": materialDelDocket.finishDate,
            			  "itemCode": materialDelDocket.itemCode,
            			  "itemDesc": materialDelDocket.itemDesc,
            			  "unitOfMeasure": 'cum',
            			  "quantity": materialDelDocket.preContractMaterialCmpTOs[0].quantity,
            			  "processQuantity" : 0,
            			  "claimedQuantity": 0,
            			  "progressQuantity": 0,
            			  "rate": materialDelDocket.preContractMaterialCmpTOs[0].rate,
	  					  "amount": materialDelDocket.preContractMaterialCmpTOs[0].rate*materialDelDocket.preContractMaterialCmpTOs[0].quantity,
	  					  "receiverComments": '',
	  					  "materildDtoId":materialDelDocket.id
            		  });
            		
            	  }); 
                                    
                  $scope.preContractData = { 'clientDetails': undefined };
                //  $scope.selectedData.date = $filter('date')(new Date(), "dd-MMM-yyyy");
                 $scope.selectedVendor = undefined;
                //  console.log(" preContractCmpTOs  ":+ JSON.stringify( $scope.preContractCmpTOs));
                 
                 $scope.openResourceDeliveryDockets =  function(selectedData) {
                	  console.log('selectedData  ', selectedData);
                	  	  var deliveryDocketsPopup = PostInvoiceDeliveryDocketsFactory.openResourceDeliveryDockets('open',selectedData,selectedData,selectedData);
                	  deliveryDocketsPopup.then(function(data) {
  						
  					}, function(error) {
  						GenericAlertService.alertMessage("Error occured while getting service classes", 'Error');
  					});
                	  
                  },
                  $scope.dueDate = function(days){
                  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
 									 "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
	                  var date = new Date($scope.invoiceDate);          
					  date.setDate(date.getDate()+days);
					 date = date.getDate()+"-"+monthNames[date.getMonth()]+"-"+date.getFullYear();
					 $scope.paymentDueDate = date;
                  }
                  $scope.scheduleData = function(){
                  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
 									 "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                  var fromdate = new Date($scope.invoiceFrom);          
					  fromdate.setDate(fromdate.getDate()-1);
				  fromdate = fromdate.getDate()+"-"+monthNames[fromdate.getMonth()]+"-"+fromdate.getFullYear();  
			      var todate = new Date($scope.invoiceTo);          
					  todate.setDate(todate.getDate()+1); 
				  todate = todate.getDate()+"-"+monthNames[todate.getMonth()]+"-"+todate.getFullYear(); 
				  
				  var req = {
								"purchaseId" : selectedData.id,
								"invoceFromDate" :fromdate,
								"invoceToDate" : todate,
								"companyId" : selectedData.preContractCmpTO.companyTO.id,
								"precontractId" : $scope.preContractDtoTOs.preContractTO.id
							};
							ProjectInvoiceService.getInvoiceMaterial(req).then(function(data) {
									console.log(data);
									$scope.materialInvoiceDetails = data;
									
									$scope.totalValue = data.totalValue;
									console.log(data.totalValue);
										console.log(data);
									},
									function(error) {
										GenericAlertService.alertMessage(
												"Error occured while gettting invoice material details",
												'Error');
									});
				     
                  var data = $scope.preContractDtoTOs.preContractTO.id +"  "+fromdate+" "+todate;
                  console.log(data);
                  
                  }
                  $scope.rowSelect = function (position, company) {
					console.log(company);
					  var value1 = 0;
					  var value2 = 0;
					if(company.selected== true){
						for (const [key, value] of Object.entries(company)) {	
						if(key == "progressQty"){
							value1=value;
						}
						if(key =="vendorRate"){
						$scope.TotalAmout+=value1*value;
						}
						};
					}
					else if(company.selected== false){
						for (const [key, value] of Object.entries(company)) {	
						if(key == "progressQty"){
							value2=value;
						}
						if(key =="vendorRate"){
						$scope.TotalAmout-=value2*value;
						}
						};
					}
				  }
				  
                  $scope.addcostcode = function(project) {
                	  console.log('project ', project);
                	  project.id = '1573';
                	               	
                	  
                	  var costCodePopup = PreContractCostPopupFactory.getProjCostDetails(project.id, 'Manpower');
  					  costCodePopup.then(function (data) {
  						$scope.projCostCode.push({ 
  							"labelKeyTO": {
  								"id": data.id,
  	  							"code": data.code,
  	  							"name": data.name
  							},
  							"costCodeAmount":'0',
  	  						"costCodePercentage": 100
  							
  						});
  						  
  						 console.log('$scope.projCostCode.   ', $scope.projCostCode);	  
  						 
  					}, function (error) {
  						GenericAlertService.alertMessage("Error occured while getting cost code details", 'Error');
  					});
                  },
                     
				UserService.findByUserId($rootScope.account.userId).then(function (data) {
					$scope.empId = data.empCode;
					$scope.empName = data.firstName+ +data.lastName;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while getting logged-in employee details", "Error");
				});
							             
                  
                  $scope.save = function() {
                	              	
                      console.log($scope.TotalAmout)
                      $scope.asPerSystemVerification = {
                    		'netInvoiceAmount': $scope.systemNetInvoiceAmount,
                    		'gstInvoiceAmount':$scope.systemgstTxtInvoiceAmount,
                    		'systemTotalInvoiceAmountInclTax': $scope.systemTotalInvoiceAmountInclTax,
                    		'expenditureType':$scope.expenditureType
                      },
                      
                      $scope.asPerInvoice = {
                      		'netInvoiceAmount': $scope.systemNetInvoiceAmount,
                      		'gstInvoiceAmount':$scope.asPerGSTTaxInvoiceAmount,
                      		'totalInvoiceAmount': $scope.asPerTotalInvoiceAmount,
                      		'expenditureType':$scope.expenditureType
                        },
                                         
                      
                      $scope.retainedInvoice = {
                        		'netInvoiceAmount': $scope.retainedInvoiceAmount,
                        		'gstInvoiceAmount':$scope.retainedGSTTaxInvoiceAmount,
                        		'totalInvoiceAmount': $scope.retainedTotalInvoiceAmount,
                        		'expenditureType':$scope.expenditureType
                          },
                      
                                           
                      $scope.netAmount = {
                      		'netInvoiceAmount': $scope.netAmountPayable,
                      		'gstInvoiceAmount':$scope.netAmountGSTTaxInvoiceAmount,
                      		'totalInvoiceAmount': $scope.netAmountTotalInvoiceAmount,
                      		'expenditureType':$scope.expenditureType
                        },
                      
                     $scope.invoiceparticularsTO = {
                    		'epsName':$scope.userProjMap[$scope.selectedData.projId].code,
                    		'epsId':'',
                    		'projectId':$scope.selectedData.projId,
                    		'projectName':$scope.userProjMap[$scope.selectedData.projId].name,
                    		'poNumber':$scope.preContractDtoTOs.preContractTO.code,
                    		'poDescription':$scope.preContractDtoTOs.preContractTO.desc,
                    		'vendorId':$scope.selectedData.preContractCmpTO.companyTO.cmpCode,
                    		'vendorName':$scope.selectedData.preContractCmpTO.companyTO.cmpName,
                    		'originatorEmployeeId':$scope.preContractDtoTOs.preContractTO.preContractReqApprTO.reqUserLabelkeyTO.code,
                    		'originatorEmployeeName':$scope.loggedInUser,
                    		'invocieDate':$scope.invoiceDate,
                    		'invoiceNumber':$scope.invoiceNumber,
                    		'invoiceReceivedDate':$scope.invoiceReceiveddate,
                    		'invoicePeriodFromDate':$scope.invoiceFrom,
                    		'invoicePeriodToDate':$scope.invoiceTo,
                    		'paymentDueDate':$scope.paymentDueDate,
                    		'proposedPaymentDate':$scope.paymentProcessingPeriod,
                    		'expenditureType':$scope.invoiceExpenditureType
                    	};
                	  
                	  console.log('factory save ');
                	  $scope.VendorInvoiceRequest = {
                			 'asPerSystemVerification':$scope.asPerSystemVerification,
                			 'asPerInvoice': $scope.asPerInvoice,
                			 'amountRetained': $scope.retainedInvoice,
                			 'netAmountPayable': $scope.netAmount,
                			 'invoiceparticularsTO': $scope.invoiceparticularsTO,
                			 'assignCostCodesTOList': $scope.projCostCode,
                			 'vendorBankAccountDetailsTO': $scope.VendorBankAccountDetailsTO,
                			 'approverValidateDetailsTO': $scope.approverValidateDetailsTO,
                			 'materialDeliveryDocket':  $scope.materialDelDockets,
                			 'manpowerDeliveryDocketTO':  $scope.manpowerDelDockets,
                			 'plantsDeliveryDocketTO': $scope.plantsDeliveryDocketTO,
                			 'serviceDeliveryDocketTO': $scope.serviceDelDockets,
                			 'subDeliveryDocket': $scope.subDelDockets,
                			 'preContractId':$scope.preContractTOId
                 	  };
                	  
                	  console.log('save metod calling');
                	  console.log($scope.VendorInvoiceRequest);
                	 
                	  ProjectInvoiceService.saveVendorPostInvoice($scope.VendorInvoiceRequest).then(function(data) {
  						GenericAlertService.alertMessage("Project invoice details are saved successfully", 'Info');
  							deferred.resolve(data);
  							ngDialog.close();
  					}, function(error) {
  						GenericAlertService.alertMessage('Post invoice details are failed to Save', 'Error');
  					});
                  }
                  
                }]
            
            
            });
            return deferred.promise;
        }
        return service;
    }]);
