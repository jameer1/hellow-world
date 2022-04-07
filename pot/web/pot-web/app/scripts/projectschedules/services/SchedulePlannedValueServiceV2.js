'use strict';

app.factory('SchedulePlannedValueServiceV2', ["GenericAlertService", "$filter", function (GenericAlertService, $filter) {

	const m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
	const barProps = {type: 'bar', id: 'y-axis-1'};
    const lineProps = {type: 'line', fill: false, yAxisID: 'y-axis-2'};
    const options = {
		elements: {rectangle: {borderWidth: 1}},
		legend: {display: true, position: 'top'},
		scales: {yAxes: [{id: "y-axis-1", position: "left"}, {id: "y-axis-2", position: "right"}]}
    };
    
	function getLabel(date, selectedTimeScale) {
		if (selectedTimeScale.value === 7)
			return date.getDate() + "-" + m_names[date.getMonth()] + "-" + date.getFullYear();
		else if (selectedTimeScale.value === 30)
			return m_names[date.getMonth()] + "-" + date.getFullYear();
		else if (selectedTimeScale.value === 365)
			return date.getFullYear();
	}
	
	function changeStartDateBasedOnReportsSetting(startDate, reportProjectSetting, selectedTimeScale) {
        if (selectedTimeScale.value === 7) {
            return changeDayAsPerReportSetting(startDate, reportProjectSetting.week);
        } else if (selectedTimeScale.value === 30) {
            return changeDateAsPerReportSetting(startDate, reportProjectSetting.month);
        } else if (selectedTimeScale.value === 365) {
            return changeMonthAsPerReportSetting(startDate, reportProjectSetting.year);
        }
    };
    
    function changeDateAsPerReportSetting(startDate, reportMonth) {
        let dateInMonth = reportMonth;
        let selectedSartDate = new Date(startDate).getDate();
        if (dateInMonth !== selectedSartDate) {
            let iteratedDate;
            let date = new Date(startDate);
            do {
                iteratedDate = new Date(date.setDate(date.getDate() - 1)).getDate();
            } while (dateInMonth != iteratedDate);
            return date;
        } else {
            return startDate;
        }
    };
    
    function changeDayAsPerReportSetting(startDate, reportWeek) {
        let weeks = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        let week = weeks.indexOf(reportWeek);
        let startDateDay = new Date(startDate).getDay();
        if (week != startDateDay) {
            let iteratedWeek;
            let date = new Date(startDate);
            do {
                iteratedWeek = new Date(date.setDate(date.getDate() - 1)).getDay();
            } while (week != iteratedWeek);
            return date;
        } else {
            return startDate;
        }
    };
    
    function changeMonthAsPerReportSetting(startDate, reportYear) {
        let months = new Array("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        let month = months.indexOf(reportYear);
        let selectedSartMonth = new Date(startDate).getMonth();
        if (month != selectedSartMonth) {
            let iteratedMonth;
            let date = new Date(startDate);
            do {
                iteratedMonth = new Date(date.setMonth(date.getMonth() - 1)).getMonth();
            } while (month != iteratedMonth);
            return new Date(date.setDate(1));
        } else {
            return startDate;
        }
    };
    
    function findMinStartMaxFinishDates(selectedItemRows, scheduleActivityDataSetTOs) {
    	let minMaxDates = {minStartDate: null, maxFinishDate: null};
    	for (let i=0; i < selectedItemRows.length; i++) {
    		if (selectedItemRows[i].startDt == null) {
    			if (minMaxDates.minStartDate == null) minMaxDates.minStartDate = new Date(selectedItemRows[i].startDate);
    			if (minMaxDates.minStartDate > new Date(selectedItemRows[i].startDate)) minMaxDates.minStartDate = new Date(selectedItemRows[i].startDate);
    		} else {
    			if (minMaxDates.minStartDate == null) minMaxDates.minStartDate = new Date(selectedItemRows[i].startDt);
    			if (minMaxDates.minStartDate > new Date(selectedItemRows[i].startDt)) minMaxDates.minStartDate = new Date(selectedItemRows[i].startDt);
    		}
    		
			if (selectedItemRows[i].finishDt == null) {
				if (minMaxDates.maxFinishDate == null) minMaxDates.maxFinishDate = new Date(selectedItemRows[i].finishDate);
				if (minMaxDates.maxFinishDate < new Date(selectedItemRows[i].finishDate)) minMaxDates.maxFinishDate = new Date(selectedItemRows[i].finishDate);
			} else {
				if (minMaxDates.maxFinishDate == null) minMaxDates.maxFinishDate = new Date(selectedItemRows[i].finishDt);
				if (minMaxDates.maxFinishDate < new Date(selectedItemRows[i].finishDt)) minMaxDates.maxFinishDate = new Date(selectedItemRows[i].finishDt);
			}
    	}
    	
    	if (scheduleActivityDataSetTOs != null) {
	    	for (let i=0; i < scheduleActivityDataSetTOs.length; i++) {
	    		if (scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs != null &&
	    				scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs.length) {
	    			for (let j=0; j < scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs.length; j++) {
	    				if (minMaxDates.minStartDate > new Date(scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[j].forecastDate))
	    					minMaxDates.minStartDate = new Date(scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[j].forecastDate);
	    	        	if (minMaxDates.maxFinishDate < new Date(scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[j].forecastDate))
	    	        		minMaxDates.maxFinishDate = new Date(scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[j].forecastDate);	
	    			}	    		
	    		}
	    	}
    	}
    	return minMaxDates;
    }
    
    function isWorkingDay(date, projectCalendar, specialWorkingDays, specialNonWorkingDays) {
    	let isWorkingDay = false;
    	switch(date.getDay()){
    	case 0: isWorkingDay = !projectCalendar.sunday; break;
    	case 1: isWorkingDay = !projectCalendar.monday; break;
    	case 2: isWorkingDay = !projectCalendar.tuesday; break;
    	case 3: isWorkingDay = !projectCalendar.wednesday; break;
    	case 4: isWorkingDay = !projectCalendar.thursday; break;
    	case 5: isWorkingDay = !projectCalendar.friday; break;
    	case 6: isWorkingDay = !projectCalendar.saturday; break;
    	}
    	if (isWorkingDay) {
    		for (let j = 0; j < specialNonWorkingDays.length; j++) {
                if (new Date(specialNonWorkingDays[j]).getTime() == date.getTime()) {
                	isWorkingDay = false;
                    break;
                }
            }
    	} else {
    		for (let j = 0; j < specialWorkingDays.length; j++) {
                if (new Date(specialWorkingDays[j]).getTime() == date.getTime()) {
                	isWorkingDay = true;
                    break;
                }
            }
    	}
    	return isWorkingDay;
    }
    
    function createPlanForSelectedCurve(req) {
    	for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
			if (req.data.scheduleActivityDataSetTOs[i].id == -1) {
				req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs = [];
				let minMaxDates = findMinStartMaxFinishDates(req.selectedItemRows, null);
				while (minMaxDates.minStartDate <= minMaxDates.maxFinishDate) {
					let actual = undefined;
					if (req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[1].resourceAssignmentDataValueTOs != null)
						actual = req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[1].resourceAssignmentDataValueTOs.find(actual => new Date(actual.forecastDate).getTime() == new Date(minMaxDates.minStartDate).getTime());
					if (actual == null) actual = {budget: 0};
					req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs.push({forecastDate: new Date(minMaxDates.minStartDate), budget: actual.budget});
					minMaxDates.minStartDate.setDate(minMaxDates.minStartDate.getDate() + 1);
				}
				for (let j=0; j < req.selectedItemRows.length; j++) {
					let numberOfWorkingDays = 0;
					let minMaxDates = {};
					minMaxDates.minStartDate = req.selectedItemRows[j].startDt == null ? new Date(req.selectedItemRows[j].startDate) : new Date(req.selectedItemRows[j].startDt);
					minMaxDates.maxFinishDate = req.selectedItemRows[j].finishDt == null ? new Date(req.selectedItemRows[j].finishDate) : new Date(req.selectedItemRows[j].finishDt);
					let runningDate = angular.copy(minMaxDates.minStartDate);
					while (runningDate <= minMaxDates.maxFinishDate) {
						if (runningDate >= new Date())
							if (isWorkingDay(runningDate, req.projectCalendar, req.specialWorkingDays, req.specialNonWorkingDays)) numberOfWorkingDays++;
						runningDate.setDate(runningDate.getDate() + 1);
					}
					let selectedCurve = req.resourceCurveTypeMap[req.selectedItemRows[j].resourceCurveId];
					let budget = (req.selectedItemRows[j].revisedQty ? req.selectedItemRows[j].revisedQty : req.selectedItemRows[j].originalQty) - (req.selectedItemRows[j].actualQty = req.selectedItemRows[j].actualQty || 0);
					console.log("Start Date: " + minMaxDates.minStartDate + " Finish Date: " + minMaxDates.maxFinishDate + " Number of Working Days: " + numberOfWorkingDays + " Budget: " + budget + " Budget per Week:" + budget/numberOfWorkingDays*5);
					let perUnitValues = [];
					for (let k=1; k <= 10; k++)
	    	            perUnitValues.push(((selectedCurve["range" + k] / numberOfWorkingDays) / 100) * budget);
					let breakdownEachDayToTenParts = numberOfWorkingDays * 10, eachPartInADay = 0, perUnitValueIndex = 0, dataValueIncrementor = 0;
					for (let k=1; k <= breakdownEachDayToTenParts; k++) {
	    	            eachPartInADay += perUnitValues[perUnitValueIndex];
	    	            if (k % 10 == 0) {
	            			let updated = false;
	    	            	do {
	    	            		if (req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[dataValueIncrementor].forecastDate <= minMaxDates.maxFinishDate 
	    	            				&& req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[dataValueIncrementor].forecastDate >= minMaxDates.minStartDate) {
	    	            			if (req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[dataValueIncrementor].forecastDate >= new Date()) {
			    	            		if (isWorkingDay(new Date(req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[dataValueIncrementor].forecastDate), req.projectCalendar, req.specialWorkingDays, req.specialNonWorkingDays)) {
			    	            			req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[dataValueIncrementor].budget += eachPartInADay;
			    	            			updated = true;
			    	            		}
	    	            			}
	    	            		}
    	            			dataValueIncrementor++;
	        	            	if (dataValueIncrementor >= req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs.length) updated = true;
	    	            	} while (!updated)
	    	            	eachPartInADay = 0;
	    	            }
	    	            if (k % numberOfWorkingDays == 0) perUnitValueIndex += 1;
	    	        }
				}
    	        break;
			}
    	}
    }
    
	return {
		createPlannedCurve: function(req) {
			let counter = 0;
        	let label = "", previousMonth = "", previousYear = "";
        	let data = [], cumulativeData = [], total = [], cummulativeTotal = [], labels = [], series = [], resourceAssignmentDataValueTOCounter = [];

			for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
				if (req.data.scheduleActivityDataSetTOs[i].baseline) {
					series.push(req.data.scheduleActivityDataSetTOs[i].datasetName + " (Baseline) Periodical");
					series.push(req.data.scheduleActivityDataSetTOs[i].datasetName + " (Baseline) Cummulative");
				} else if (req.data.scheduleActivityDataSetTOs[i].current) {
					series.push(req.data.scheduleActivityDataSetTOs[i].datasetName + " (Current) Periodical");
					series.push(req.data.scheduleActivityDataSetTOs[i].datasetName + " (Current) Cummulative");
				} else if (req.data.scheduleActivityDataSetTOs[i].id == 0) {
					series.push("Actual Periodical");
					series.push("Actual Cummulative");
				} else if (req.data.scheduleActivityDataSetTOs[i].id == -1) {
					series.push("Current Plan Periodical");
					series.push("Current Plan Cummulative");
					createPlanForSelectedCurve(req);
				} else {
					series.push(req.data.scheduleActivityDataSetTOs[i].datasetName + " Periodical");
					series.push(req.data.scheduleActivityDataSetTOs[i].datasetName + " Cummulative");
				}
				resourceAssignmentDataValueTOCounter.push([0]);
				total.push([]);
				cummulativeTotal.push([]);
				data.push([]);
				cumulativeData.push([]);
			}
			
			let minMaxDates = findMinStartMaxFinishDates(req.selectedItemRows, req.data.scheduleActivityDataSetTOs);
        	minMaxDates.minStartDate = changeStartDateBasedOnReportsSetting(minMaxDates.minStartDate, req.reportProjectSetting, req.selectedTimeScale);
        	while (minMaxDates.minStartDate <= minMaxDates.maxFinishDate) {
        		if (req.selectedTimeScale.value === 7 && counter == 7) {
        			for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
                		data[i].push(total[i][0].toFixed(2));
                		cumulativeData[i].push(cummulativeTotal[i][0].toFixed(2));
                		total[i][0] = 0;
                	}
                	labels.push(label);
                	counter = 0;
                } else if (req.selectedTimeScale.value === 30 && previousMonth != minMaxDates.minStartDate.getMonth() && counter != 0) {
                	for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
                		data[i].push(total[i][0].toFixed(2));
                		cumulativeData[i].push(cummulativeTotal[i][0].toFixed(2));
                		total[i][0] = 0;
                	}
                	labels.push(label);
                	counter = 0;
                } else if (req.selectedTimeScale.value === 365 && previousYear != minMaxDates.minStartDate.getFullYear() && counter != 0) {
                	for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
                		data[i].push(total[i][0].toFixed(2));
                		cumulativeData[i].push(cummulativeTotal[i][0].toFixed(2));
                		total[i][0] = 0;
                	}
                	labels.push(label);
                	counter = 0;
                }
        		if (counter == 0) {
        			label = getLabel(minMaxDates.minStartDate, req.selectedTimeScale);
        			previousMonth = minMaxDates.minStartDate.getMonth();
        			previousYear = minMaxDates.minStartDate.getFullYear();
        		}

        		for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
        			if (req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs != null) {
	        			if (resourceAssignmentDataValueTOCounter[i] < req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs.length) {
	        				if (angular.equals(minMaxDates.minStartDate, new Date(req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[resourceAssignmentDataValueTOCounter[i]].forecastDate))) {
	        					if (isNaN(total[i][0])) total[i][0] = 0;
	        					if (isNaN(cummulativeTotal[i][0])) cummulativeTotal[i][0] = 0;
	        					if (isNaN(req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[resourceAssignmentDataValueTOCounter[i]].budget))
	        						 req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[resourceAssignmentDataValueTOCounter[i]].budget = 0;
	        					total[i][0] += req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[resourceAssignmentDataValueTOCounter[i]].budget;
	        					cummulativeTotal[i][0] += req.data.scheduleActivityDataSetTOs[i].resourceAssignmentDataTOs[0].resourceAssignmentDataValueTOs[resourceAssignmentDataValueTOCounter[i]].budget;
	        					resourceAssignmentDataValueTOCounter[i]++;
	        				} else {
		        				total[i][0] = 0;
		        				cummulativeTotal[i][0] = 0;
		        			}
	        			} else {
	        				total[i][0] = 0;
	        				cummulativeTotal[i][0] = 0;
	        			}
        			} else {
        				total[i][0] = 0;
        				cummulativeTotal[i][0] = 0;
        			}
        		}
        		minMaxDates.minStartDate.setDate(minMaxDates.minStartDate.getDate() + 1);
                counter++;
        	}
        	
        	if (counter > 0) {
        		for (let i = 0; i < req.data.scheduleActivityDataSetTOs.length; i++) {
            		data[i].push(total[i][0].toFixed(2));
            		cumulativeData[i].push(cummulativeTotal[i][0].toFixed(2));
            		total[i][0] = 0;
            	}
            	labels.push(label);
            	counter = 0;
        	}

        	let consolidatedData = [], datasetOverride = [];
        	for (let i = 0; i < data.length; i++) {
        		consolidatedData.push(data[i]);
        		consolidatedData.push(cumulativeData[i]);
        		datasetOverride.push(barProps);
        		datasetOverride.push(lineProps);        		
        	}
        	for (let i=0; i < consolidatedData.length; i++) {
        		for (let j=0; j < consolidatedData[i].length; j++) {
        			if (consolidatedData[i][j] == undefined) consolidatedData[i][j] = 0;
        		}
        	}
        	
        	return {
        		"series": series,
                "data": consolidatedData, 
                "labels": labels,
                "datasetOverride" : datasetOverride,
                "options": options
            };
        }
	};
}]);
