package com.rjtech.common.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class CommonUtil {

    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

    private static final int DEFAULT_END_YEAR = 2099;
    private static final int DEFAULT_END_MONTH = 11;
    private static final int DEFAULT_END_DATE = 31;

    /**
     * Checks if either null or empty or blank String
     * 
     * @param str
     * @return flag
     */
    public static boolean isBlankStr(String str) {
        return str == null || str.trim().length() == 0; // || "".equals(str) ||
                                                        // "".equals(str.trim());
    }

    /**
     * Checks if not either null or empty or blank String
     * 
     * @param str
     * @return flag
     */
    public static boolean isNotBlankStr(String str) {
        return !isBlankStr(str);
    }

    public static boolean validateName(String name) {
        boolean valid = false;
        Pattern pattern1 = Pattern.compile("^[a-zA-Z]+([\\sa-zA-Z0-9_-]*)");
        Matcher matcher1 = pattern1.matcher(name);
        if (matcher1.matches()) {
            return true;
        }
        return valid;
    }

    public static boolean negativeValueCheck(double value) {
        return value >= 0 ? true : false;

    }

    public static boolean isNonBlankInteger(Integer value) {
        if (value != null && value > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isZeroOrGreater(Integer value) {
        if (value != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBlankInteger(Integer inputValue) {
        return inputValue == null;
    }

    public static boolean isBlankDate(Date inputDate) {
        return inputDate == null || "".equals(inputDate);
    }

    public static boolean isBlankDate(Timestamp inputDate) {
        return isBlankTimeStamp(inputDate);
    }

    public static boolean isBlankTimeStamp(Timestamp inputDate) {
        return inputDate == null || "".equals(inputDate);
    }

    public static boolean isNotBlankDate(Date inputDate) {
        return !isBlankDate(inputDate);
    }

    public static boolean isNotBlankDate(Timestamp inputDate) {
        return !isBlankDate(inputDate);
    }

    public static boolean isNotBlankTimeStamp(Timestamp inputDate) {
        return !isBlankDate(inputDate);
    }

    /*
     * public static Date defaultEndDate() { Calendar cal =
     * GregorianCalendar.getInstance(); cal.set(Calendar.YEAR,
     * DEFAULT_END_YEAR); cal.set(Calendar.MONTH, DEFAULT_END_MONTH);
     * cal.set(Calendar.DATE, DEFAULT_END_DATE); cal.set(Calendar.HOUR_OF_DAY,
     * 0); cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0);
     * cal.set(Calendar.MILLISECOND, 0);
     * 
     * return cal.getTime(); }
     */

    public static boolean validateStaffIdPatternWithChar(String staffId) {
        boolean valid = false;
        Pattern staffIdPattern = Pattern.compile("^[s||S]+(\\d{6})");
        Matcher staffIdPatternMatcher = staffIdPattern.matcher(staffId);
        if (staffIdPatternMatcher.matches()) {
            return true;

        }
        return valid;
    }

    public static boolean validateStaffIdPattern(String staffId) {
        boolean valid = false;
        Pattern staffIdPattern = Pattern.compile("\\d{6}");
        Matcher staffIdPatternMatcher = staffIdPattern.matcher(staffId);
        if (staffIdPatternMatcher.matches()) {
            return true;

        }
        return valid;
    }

    public static boolean validateAmount(Double amount) {
        boolean valid = false;
        Pattern amountPattern = Pattern.compile("^[1-9]+(\\d*\\.?\\d*)");
        Matcher amoutMatcher = amountPattern.matcher(String.valueOf(amount));
        if (amoutMatcher.matches()) {
            return true;

        }
        return valid;
    }

    public static boolean isBlankDouble(Double inputValue) {
        return inputValue == null;
    }

    public static boolean isBlankBigDecimal(BigDecimal inputValue) {
        return inputValue == null;
    }

    public static boolean isBlankLong(Long inputValue) {
        return inputValue == null;
    }

    public static boolean isNonBlankLong(Long value) {
        if (value != null && value > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNonBlankDouble(Double value) {
        if (value != null && value > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNonBlankBigDecimal(BigDecimal value) {
        if (value != null && value.compareTo(BigDecimal.ZERO) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static String getStrDateFromTimeStamp(Date inputDate) {
        String strDate = "";
        if (!isBlankDate(inputDate)) {

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            return sdf.format(inputDate);
        }
        return strDate;
    }

    public static boolean isListHasData(List inputData) {

        boolean listisNotNull = false;

        if (inputData != null && inputData.size() > 0) {
            listisNotNull = true;
        }
        return listisNotNull;
    }

    public static boolean isSetHasData(Set inputData) {

        boolean listisNotNull = false;

        if (inputData != null && inputData.size() > 0) {
            listisNotNull = true;
        }
        return listisNotNull;
    }

    public static Date getBillingPeriodFromInvoiceDate(Date invoiceDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(invoiceDate);
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month - 1);
        return cal.getTime();
    }

    public static Date getInvoiceDateFromBillingPeriod(Date billingPeriod) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(billingPeriod);
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month + 1);
        return cal.getTime();
    }

    public static Date getDateFromStrDDMMYYYY(String inputValue) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(inputValue);
        } catch (ParseException e) {

        }
        return date;
    }

    public static Date getDateFromStrYYYYMMDD(String inputValue) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        try {
            date = sdf.parse(inputValue);
        } catch (ParseException e) {

        }
        return date;
    }

    public static Date getDateFromStrDDMMYYYYWithDash(String inputValue) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            date = sdf.parse(inputValue);
        } catch (ParseException e) {

        }
        return date;
    }

    public static Date getDateFromStrYYYYMMDDWithDash(String inputValue) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(inputValue);
        } catch (ParseException e) {

        }
        return date;
    }

    public static Double getLowValue(Double amountDue, Double totalCurrentMonthCharge) {
        Double lowerValue = new Double(0);

        if (isBlankDouble(amountDue)) {
            amountDue = new Double(0);
        }
        if (isBlankDouble(totalCurrentMonthCharge)) {
            totalCurrentMonthCharge = new Double(0);
        }

        if (amountDue <= totalCurrentMonthCharge) {
            lowerValue = amountDue;
        } else if (totalCurrentMonthCharge <= amountDue) {
            lowerValue = totalCurrentMonthCharge;
        } else if (amountDue <= 0) {
            lowerValue = new Double(0);
        }
        return lowerValue;

    }

    public static String getYYYYMMDDFormat(Date inputDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String inpuDateStr = sdf.format(inputDate);

        return inpuDateStr;

    }

    public static boolean validateNumber(String phoneNumber) {
        boolean valid = false;
        Pattern pattern = Pattern.compile(".*\\D.*");
        Matcher staffIdPatternMatcher = pattern.matcher(phoneNumber);
        if (staffIdPatternMatcher.matches()) {
            return true;

        }
        return valid;
    }

    public static boolean alphaNumberValidation(String staffId) {
        boolean valid = false;
        Pattern pattern1 = Pattern.compile("([\\sa-zA-Z0-9]*)");
        Matcher matcher1 = pattern1.matcher(staffId);
        if (matcher1.matches()) {
            return true;
        }
        return valid;
    }

    public static boolean numberValidation(String phoneNumber) {
        boolean valid = false;
        Pattern pattern = Pattern.compile(".[0-9].*");
        Matcher staffIdPatternMatcher = pattern.matcher(phoneNumber);
        if (staffIdPatternMatcher.matches()) {
            return true;

        }
        return valid;
    }

    public static boolean objectNullCheck(Object value) {
        return value == null;
    }

    public static boolean objectNotNull(Object value) {
        return !objectNullCheck(value);
    }

    public static String getMonthYYYY(Date inputDate) {
        SimpleDateFormat sd = new SimpleDateFormat("MMMM yyyy");
        String inpuDateStr = sd.format(inputDate);
        return inpuDateStr;
    }
    public static String getMonth(Date inputDate){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM");
        String month = formatter.format(inputDate);
        return month;
    }
    
    public static String getInParameterString(String inputArray[]) {
        StringBuffer sb = new StringBuffer();

        if (inputArray.length >= 1) {
            int index = 0;
            for (String input : inputArray) {
                index++;
                if (CommonUtil.isNotBlankStr(input)) {
                    sb.append("'");
                    sb.append(input.trim());

                    if (index < inputArray.length) {
                        sb.append("',");
                    } else {
                        sb.append("'");
                    }
                }

            }
        }

        return sb.toString();
    }

    public static String getInParameterNumber(Number inputArray[]) {
        StringBuffer sb = new StringBuffer();

        if (inputArray.length >= 1) {
            int index = 0;
            for (Number input : inputArray) {
                index++;
                if (input != null) {
                    sb.append(input);
                    if (index < inputArray.length) {
                        sb.append(",");
                    }
                }

            }
        }

        return sb.toString();
    }

    public static String getAmountInDecimalFormat(Object inputValue) {
        NumberFormat formatter = null;
        formatter = new DecimalFormat("#0.00");
        return formatter.format(inputValue);
    }

    public static Date getLastDayDate(Date inputDate) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(inputDate);
        int maxDay = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(inputDate);
        endDate.set(Calendar.DAY_OF_MONTH, maxDay);
        return endDate.getTime();

    }

    public static String getStrFromDateDDMMYYYY(Date inputValue) {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        date = sdf.format(inputValue);

        return date;
    }

    public static String isBlackBerryEnabled(String input) {
        return input.equalsIgnoreCase("1") ? "Yes" : "No";
    }

    public static boolean isMapHasData(Map inputData) {

        boolean mapisNotNull = false;

        if (inputData != null && !inputData.isEmpty()) {
            mapisNotNull = true;
        }
        return mapisNotNull;
    }

    public static String getDDMMMMYYYYFormat(Date inputDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");

        String inpuDateStr = sdf.format(inputDate);

        return inpuDateStr;

    }

    public static boolean validateAmount(String amount) {
        boolean valid = false;
        Pattern amountPattern = Pattern.compile("^[1-9]+(\\d*\\.?\\d*)");
        Matcher amoutMatcher = amountPattern.matcher(String.valueOf(amount));
        if (amoutMatcher.matches()) {
            return true;

        }
        return valid;
    }

    public static Date getInvoiceDateFromEffectiveTO(Date effectiveTO) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(effectiveTO);
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static String trim(String inputStr) {
        return inputStr.trim();
    }

    public static Date getYYYYMMDDFormat(String billingCycle) {

        Date dateFromStr = null;

        if (isNotBlankStr(billingCycle)) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {

                dateFromStr = sdf1.parse(billingCycle);
            } catch (ParseException e) {

            }

        }
        return dateFromStr;
    }

    public static Timestamp getNow() {
        return new Timestamp(new Date().getTime());
    }

    public static Boolean getStatusAsBoolean(String status) {
        if (isNotBlankStr(status)) {
            if ("Y".equalsIgnoreCase(status)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static String appendLikeOperator(String input) {
        StringBuffer sb = new StringBuffer();
        sb.append("%");
        sb.append(input.trim());
        sb.append("%");
        return sb.toString();

    }

    public static AppResp getWarningAppResp() {
        AppResp resp = new AppResp();
        resp.setMessage("Unable To Save");
        resp.setStatus("Warning");
        return resp;
    }

    public static AppResp getSaveAppResp() {
        AppResp resp = new AppResp();
        resp.setMessage("saved/updated successfully");
        resp.setStatus("Info");
        return resp;
    }

    public static AppResp getDeactiveAppResp() {
        AppResp resp = new AppResp();
        resp.setMessage("deactivated successfully");
        resp.setStatus("Info");
        return resp;
    }

    public static AppResp getActiveAppResp() {
        AppResp resp = new AppResp();
        resp.setMessage("Activated successfully");
        resp.setStatus("Info");
        return resp;
    }

    public static AppResp getDeleteAppResp() {
        AppResp resp = new AppResp();
        resp.setMessage("Deleted successfully");
        resp.setStatus("Info");
        return resp;
    }

    public static AppResp getEditAppResp() {
        AppResp resp = new AppResp();
        resp.setMessage("updated successfully");
        resp.setStatus("Info");
        return resp;
    }

    public static String concatCodeName(String code, String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(code);
        sb.append("-");
        sb.append(name);
        return sb.toString();
    }

    public static String getUrl(String restEndPointURI, String path) {
        return AppUtils.getUrl(restEndPointURI + path, null);

    }

    public static String generatePreContractCode(Long projId) {
        String preContractCode = ApplicationConstants.PRE_CONTRACT_PREFIX + "-" + projId + "-";
        long range = (ApplicationConstants.RANDOM_MAX_NUMBER - ApplicationConstants.RANDOM_MIN_NUMBER) + 1;
        long value = (long) ((Math.random() * range) + 1);
        preContractCode += value;
        return preContractCode;
    }

    public static String generatePurchaseOrderCode(Long projId, String parentCode) {
        String preContractCode = ApplicationConstants.PURCHASE_ORDER_PREFIX + "-" + projId + "-" + parentCode + "-";
        long range = (ApplicationConstants.RANDOM_MAX_NUMBER - ApplicationConstants.RANDOM_MIN_NUMBER) + 1;
        long value = (long) ((Math.random() * range) + 1);
        preContractCode += value;
        return preContractCode;
    }

    public static String generateNotificationCode(Long projId) {
        String notificationCode = ApplicationConstants.REQUSITION_PREFIX + "-" + projId + "-";
        long range = (ApplicationConstants.RANDOM_MAX_NUMBER - ApplicationConstants.RANDOM_MIN_NUMBER) + 1;
        long value = (long) ((Math.random() * range) + 1);
        notificationCode += value;
        return notificationCode;
    }

    public static String generateReqApprCode(Long preContractId) {
        String reqApprCode = ApplicationConstants.REQUSITION_PREFIX + "-" + preContractId + "-";
        long range = (ApplicationConstants.RANDOM_MAX_NUMBER - ApplicationConstants.RANDOM_MIN_NUMBER) + 1;
        long value = (long) ((Math.random() * range) + 1);
        reqApprCode += value;
        return reqApprCode;
    }

    public static String generateCountryTaxCode(String country, String provision, String year) {
        String notificationCode = ApplicationConstants.TAX_PREFIX + "-" + country + "-" + provision + "-" + year + "-";
        long range = (ApplicationConstants.RANDOM_MAX_NUMBER - ApplicationConstants.RANDOM_MIN_NUMBER) + 1;
        long value = (long) ((Math.random() * range) + 1);
        notificationCode += value;
        return notificationCode;
    }

    public static Date convertStringToDate(String inputValue) {
        Date date = null;
        if (isNotBlankStr(inputValue)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                date = sdf.parse(inputValue);
            } catch (ParseException e) {
                log.error("Error while parsing data: ", e);
            }
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        String str = null;
        if (objectNotNull(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            str = dateFormat.format(date);
        }
        return str;
    }

    public static String convertDateToDDMMYYYYString(Date date) {
        String str = null;
        if (objectNotNull(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            str = dateFormat.format(date);
        }
        return str;
    }

    public static Date convertDDMMYYYYStringToDate(String inputValue) {
        Date date = null;
        if (isNotBlankStr(inputValue)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date = sdf.parse(inputValue);
            } catch (ParseException e) {

            }
        }
        return date;
    }

    public static String getDDMMYYYFormat(Date date) {
        String str = null;
        if (objectNotNull(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            str = dateFormat.format(date);
        }
        return str;
    }

    public static String getDDMMMYYYFormat(Date date) {
        String str = null;
        if (objectNotNull(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            str = dateFormat.format(date);
        }
        return str;
    }

    public static String getMMMYYYFormat(Date date) {
        String str = null;
        if (objectNotNull(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
            str = dateFormat.format(date);
        }
        return str;
    }

    public static Date getMMMYYYDate(String inputValue) {
        Date date = null;
        if (isNotBlankStr(inputValue)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
            try {
                date = sdf.parse(inputValue);
            } catch (ParseException e) {
                log.error("ParseException ", e);
            }
        }
        return date;
    }

    public static Date substarctInputDays(Date inputDate, Integer numberOfDatys) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.add(Calendar.DATE, numberOfDatys);
        return cal.getTime();
    }

    public static Date substarctInputMonths(Date inputDate, Integer numberOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.add(Calendar.MONTH, numberOfMonth);
        return cal.getTime();
    }

    public static String appendTwoString(String input1, String input2, String symbol) {
        StringBuffer sb = new StringBuffer();
        sb.append(input1+" "); // need to maintain space betwwen first name and last name 
        if (isNotBlankStr(symbol)) {
            sb.append(symbol);
        }
        sb.append(input2);
        return sb.toString();
    }

    public static String AddingAditionalTimeForDate(Date inputDate, Integer numberOfDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(inputDate);
        c.add(Calendar.DATE, numberOfDays);
        return sdf.format(c.getTime());
    }

    public static Date addNumberOfDaysInputDate(Date inputDate, Integer numberOfDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(inputDate);
        c.add(Calendar.DATE, numberOfDays);
        return c.getTime();
    }

    public static BigDecimal ifNullGetDefaultValue(BigDecimal input) {
        if (CommonUtil.objectNullCheck(input)) {

            input = new BigDecimal(0);
        }
        return input;
    }

    public static Double ifNullGetDefaultValue(Double input) {
        if (CommonUtil.objectNullCheck(input)) {

            input = new Double(0);
        }
        return input;
    }

    public static BigDecimal ifNullGetDefaultValue(Long input) {
        BigDecimal big = new BigDecimal(input);
        if (CommonUtil.objectNullCheck(input)) {
            big = new BigDecimal(0);
        }
        return big;
    }

    public static Date removeTimeFromDate(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * @param d
     * @return 0 if value is null
     */
    public static Double getDoubleValue(Double d) {
        return d != null ? d : new Double(0);
    }

    public static String getDateDDMMMYYYY(Calendar cal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        dateFormat.setTimeZone(cal.getTimeZone());
        return dateFormat.format(cal.getTime());
    }

    public static Date getDateDDMMMYYYY(String inputValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        Date date = null;
        try {
            date = dateFormat.parse(inputValue);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static Date convertStringDDMMMToDate(String inputValue) {
        Date date = null;
        if (isNotBlankStr(inputValue)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
            try {
                date = sdf.parse(inputValue);
            } catch (ParseException e) {

            }
        }
        return date;
    }

    public static String convertDateToStringDDMMM(Date date) {
        String str = null;
        if (objectNotNull(date)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM");
            str = dateFormat.format(date);
        }
        return str;
    }

    /**
     * 
     * @param arr1
     * @param arr2
     * @return True if same
     * @return False if not same
     */
    public static boolean compareStringArrays(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length)
            return false;

        String[] arr1Copy = arr1.clone();
        String[] arr2Copy = arr2.clone();

        Arrays.sort(arr1Copy);
        Arrays.sort(arr2Copy);
        for (int i = 0; i < arr1Copy.length; i++) {
            if (!arr1Copy[i].equals(arr2Copy[i]))
                return false;
        }
        return true;
    }

    public static List<Integer> getPrecedingYears(int count) {
        List<Integer> years = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        years.add(cal.get(Calendar.YEAR));

        for (int i = 0; i < count; i++) {
            cal.add(Calendar.YEAR, -1);
            years.add(cal.get(Calendar.YEAR));
        }

        return years;
    }

}
