'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # General service which is used in all modules.
 */
app.service('generalservice', function() {
    
    var me = this;

    me.getStockCategories = function() {
        return ['WareHouse',
                'StoreYard',
                'PlantYard',
                'Stockpile',
                'WorkSite'];
    };
    
    me.getCompanyCategories = function() {
        return ['Head Company',
                'Sub Company'];
	};
	
    me.getprocures = [ {
		id : 0,
		name : 'Manpower',
		code : "MN"
	}, {
		id : 1,
		name : 'Materials',
		code : "MT"
	}, {
		id : 2,
		name : 'Plants',
		code : "PT"
	}, {
		id : 3,
		name : 'Project Sub Contract',
		code : "SOW"
	}, {
		id : 4,
		name : 'Services',
		code : "SV"
	},
	];

	me.empLeaveTypes = [
		{ code: 'P', desc: 'Present', catagory: 1, displayOrder: 1 },
		{ code: 'AB', desc: 'Absent', catagory: 1, displayOrder: 2 },
		{ code: 'PH', desc: 'Public Holiday', catagory: 1, displayOrder: 3 },
		{ code: 'AL', desc: 'Annual Leave', catagory: 2, displayOrder: 4 },
		{ code: 'LSL', desc: 'Long Service Leave', catagory: 2, displayOrder: 5 },
		{ code: 'SL', desc: 'Sick Leave', catagory: 2, displayOrder: 6 },
		{ code: 'ML', desc: 'Maternity / Parental Leave', catagory: 2, displayOrder: 7 },
		{ code: 'CBL', desc: 'Compassionate & Bereavement Leave', catagory: 2, displayOrder: 8 },
		{ code: 'CSL', desc: 'Community Service Leave', catagory: 2, displayOrder: 9 },
		{ code: 'UPL', desc: 'Unpaid Leave', catagory: 3, displayOrder: 10 }
	];
	
	
	me.empLeaveTypes1 = [		
		{ code: 'AL', desc: 'Annual Leave', catagory: 2, displayOrder: 4 },
		{ code: 'LSL', desc: 'Long Service Leave', catagory: 2, displayOrder: 5 },
		{ code: 'SL', desc: 'Sick Leave', catagory: 2, displayOrder: 6 },
		{ code: 'ML', desc: 'Maternity / Parental Leave', catagory: 2, displayOrder: 7 },
		{ code: 'CBL', desc: 'Compassionate & Bereavement Leave', catagory: 2, displayOrder: 8 },
		{ code: 'CSL', desc: 'Community Service Leave', catagory: 2, displayOrder: 9 },
		{ code: 'UPL', desc: 'Unpaid Leave', catagory: 3, displayOrder: 10 }
	];

	me.plantAttendanceTypes = [
		{ code: 'W', name: 'Working Condition' },
		{ code: 'NW', name: 'Non-Working Day' },
		{ code: 'I', name: 'Idle Condition' },
		{ code: 'UR', name: 'Under Repair' },
	];

	me.employeeCategory =  ['DIRECT','IN-DIRECT'];

	me.leavePayType = ['Paid', 'Unpaid', 'NA'];	 
	
	me.getPreContractTypes = function(){
		return ['Engineering Services','Labour Hire Agreement','Plant Hire Agreement','Supply Agreement','Purchase  Order','Sub Contract agreement','Professional Services agreement'];
	};
	
	me.weeakDays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];

	me.years = ["JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"];

	me.monthly = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
		"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"];
	
	me.lifeStatuses = [ "Construction Phase","Commissioning Phase","Under Renovation/Repairs","Operational Phase","Acquisition Phase","Design Phase"];
	
	me.ownershipStatuses = ["On Lease","Vacant","Sold Out","Not Applicable"];
	
	me.revenueCycles = ["Daily","Weekly","Fortnightly","Monthly","Quarterly","Half Yearly","Yearly"];
	
	me.rents = ["Fixed Amount","Variable Amount"];
	
	me.revenueForecasts = ["Yes","No" ];
	
	me.accountStatus = ["Active","Inactive" ];
	
	me.paymentTermsTypes = ["Single","Installments" ];
	
	me.saleTypes = ["Lease","Hire","Out Right Sale"];
	
	me.financeCodeTypes = ["NonRegular-Allowance", "Pay-Deduction", "Payment-Receiver", "PayRoll",
		"Regular Allowance", "Super Annuation Funds"];
		
    me.taxTypes = ["Individual", "Company"];
    
    me.pymentCycles = ["Daily","Weekly","Fortnightly","Monthly","Quarterly","Half Yearly","Yearly"];
    
    me.fixedVariableChanrges = ["Fixed Amount","Variable Amount"];
    
    me.rentalHistoryStatus = ["Active","Inactive" ];
    
    me.shortTermStatus = ["Active","Inactive" ];
    
	me.modeOfPayments = ["Bank  EFT Transfer","Bank Checqe","By Cash Deposit"];
	
	me.getArrIndexByProp = function(arr, prop, obj) {
		arr.map(arrObj => {
			if (arrObj[prop] === obj[prop]) 
				return arr.indexOf(arrObj);
		});
		return -1;
	}

	me.payRollCycle = ["Weekly","Fortnightly","Monthly","First Quarter in a year","First Half Year","Yearly", "Financial year"];
	
	me.employmentTypes = ["Full Time", "Part Time", "Casual", "Contract"];
	
	me.currentStatus = ["On Transfer", "Relived", "All"];
});
