package com.rjtech.timemanagement.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class TimeManagementUtil implements Serializable {

    public static List<String> getDays() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Integer day = cal.get(Calendar.DATE);
        Integer year = cal.get(Calendar.YEAR);
        List<String> days = new ArrayList<String>();
        Formatter fmt = new Formatter();
        Formatter month = fmt.format("%tb", cal);
        for (int i = 1; i <= day; i++) {
            if (i > 9) {
                days.add(i + "-" + month + "-" + year);
            } else {
                days.add("0" + i + "-" + month + "-" + year);
            }
        }
        return days;
    }

    public static List<String> getDaysByAttendanceMonth(String attendanceMonth) {
        List<String> days = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        Date inpuDate;
        try {
            inpuDate = sdf.parse(attendanceMonth);
            Date date = new Date();
            Calendar currentCal = Calendar.getInstance();
            currentCal.setTime(date);
            Calendar userCal = Calendar.getInstance();
            userCal.setTime(inpuDate);

            Integer day = 0;

            /*if (currentCal.get(Calendar.MONTH) == userCal.get(Calendar.MONTH)) {
            	day = currentCal.get(Calendar.DATE);
            } else {*/
            day = userCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            /*}*/
            Integer year = userCal.get(Calendar.YEAR);

            Formatter fmt = new Formatter();
            Formatter month = fmt.format("%tb", userCal);
            for (int i = 1; i <= day; i++) {
                if (i > 9) {
                    days.add(i + "-" + month + "-" + year);
                } else {
                    days.add("0" + i + "-" + month + "-" + year);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public static List<Date> getDatesByAttendanceMonth(String attendanceMonth) {
        List<Date> dates = new ArrayList<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        Date inpuDate;
        try {
            inpuDate = sdf.parse(attendanceMonth);
            Date date = new Date();
            Calendar currentCal = Calendar.getInstance();
            currentCal.setTime(date);
            Calendar userCal = Calendar.getInstance();
            userCal.setTime(inpuDate);

            Integer day = 0;

            /*if (currentCal.get(Calendar.MONTH) == userCal.get(Calendar.MONTH)) {
            	day = currentCal.get(Calendar.DATE);
            } else {*/
            day = userCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            /*	}*/
            Integer year = userCal.get(Calendar.YEAR);

            Formatter fmt = new Formatter();
            Formatter month = fmt.format("%tb", userCal);
            String weekStartDate = "0" + 1 + "-" + month + "-" + year;
            String weekendDate = null;
            if (day > 9) {
                weekendDate = day + "-" + month + "-" + year;
            } else {
                weekendDate = "0" + day + "-" + month + "-" + year;
            }
            Date startDate = new Date(weekStartDate);
            Date endDate = new Date(weekendDate);
            dates.add(startDate);
            dates.add(endDate);
        } catch (

        ParseException e) {
            e.printStackTrace();
        }

        return dates;
    }

    public static String getDDMMMMYYYYFormat(Date inputDate) {
        if (inputDate != null || !"".equals(inputDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String inpuDateStr = sdf.format(inputDate);
            return inpuDateStr;
        }
        return null;
    }

    public static String getDayWithDDMMMYYYYFormat(Date inputDate) {
        if (inputDate != null || !"".equals(inputDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM");
            String inpuDateStr = sdf.format(inputDate);
            return inpuDateStr;
        }
        return null;
    }

    public static String getDateWithDDMMMYYYYFormat(Date inputDate) {
        if (inputDate != null || !"".equals(inputDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM-yyyy");
            String inpuDateStr = sdf.format(inputDate);
            return inpuDateStr;
        }
        return null;
    }

    public static Date getStringDDMMMYYYYFormat(String inputDate) {
        if (inputDate != null || !"".equals(inputDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM-yyyy");
            Date inpuDateStr = null;
            try {
                inpuDateStr = sdf.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return inpuDateStr;
        }
        return null;
    }

    public static Date getDateDDMMMYYYYFormat(String inputDate) {
        if (inputDate != null || !"".equals(inputDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MMM-yyyy");
            Date inpuDateStr = null;
            try {
                inpuDateStr = sdf.parse(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return inpuDateStr;
        }
        return null;
    }

    public static Integer getDateDiffernce(Date startDay, Date endDay) {
        Integer differnce = 0;
        //if(startDay!=null && endDay!=null )
        differnce = (int) (endDay.getTime() - startDay.getTime()) / (1000 * 60 * 60 * 24);
        //return differnce;

        return differnce;
    }

    public static void main(String[] aa) {
    }

}
