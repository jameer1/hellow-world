package com.rjtech.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtils {

    private static final Logger log = LoggerFactory.getLogger(AppUtils.class);

    private static final String JSON_PARSE_EXCEPTION_MESSAGE = "JsonParseException occured: ";
    private static final String JSON_MAPPING_EXCEPTION_MESSAGE = "JsonMappingException occured: ";
    private static final String IO_EXCEPTION_MESSAGE = "IOException occured: ";

    /**
     */
    public enum FILE_TYPES {
        JPEG("image/jpeg"), PNG("image/png");
        private String value;

        private FILE_TYPES(String value) {
            this.value = value;
        }

        public String getType() {
            return value;
        }
    }

    /**
     * Check if the object is null.
     * 
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Check for not null
     * 
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
        return object != null;
    }

    /**
     * Check if String contains text.
     * 
     * @param value
     * @return
     */
    public static boolean hasText(String value) {
        if (value == null)
            return false;
        return !value.trim().equalsIgnoreCase("");
    }

    /**
     * Used jackson api to convert json string to object
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {   	
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        T t = null;
        try {
            mapper.setSerializationInclusion(Inclusion.NON_NULL);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            t = mapper.readValue(json, clazz);

        } catch (JsonParseException e) {
            log.error(JSON_PARSE_EXCEPTION_MESSAGE, e);
        } catch (JsonMappingException e) {
            log.error(JSON_MAPPING_EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            log.error(IO_EXCEPTION_MESSAGE, e);
        }
        return t;
    }

    /**
     * Used jackson api to convert object to string.
     * 
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {

            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            json = mapper.writeValueAsString(object);

        } catch (JsonParseException e) {
            log.error(JSON_PARSE_EXCEPTION_MESSAGE, e);
        } catch (JsonMappingException e) {
            log.error(JSON_MAPPING_EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            log.error(IO_EXCEPTION_MESSAGE, e);
        }
        return json;
    }

    /**
     * Used jackson api to convert object to string.
     * 
     * @param object
     * @return
     */
    public static String toJsonWithRootWrap(Object object) {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        String json = null;
        try {

            mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);

            mapper.setSerializationInclusion(Inclusion.NON_NULL);
            json = mapper.writeValueAsString(object);

        } catch (JsonParseException e) {
            log.error(JSON_PARSE_EXCEPTION_MESSAGE, e);
        } catch (JsonMappingException e) {
            log.error(JSON_MAPPING_EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            log.error(IO_EXCEPTION_MESSAGE, e);
        }
        return json;
    }

    public static <T> T fromJsonWithRootWrap(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        T t = null;
        try {
            mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);

            mapper.setSerializationInclusion(Inclusion.NON_NULL);
            t = mapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            log.error(JSON_PARSE_EXCEPTION_MESSAGE, e);
        } catch (JsonMappingException e) {
            log.error(JSON_MAPPING_EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            log.error(IO_EXCEPTION_MESSAGE, e);
        }
        return t;
    }

    /**
     * Used jackson api to convert object to string.
     * 
     * @param      <T>
     * @param json
     * @param type
     * @return
     */

    public static <T> T fromJson(String json, TypeReference<T> type) {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        T t = null;
        try {

            t = mapper.readValue(json, type);

        } catch (JsonParseException e) {
            log.error(JSON_PARSE_EXCEPTION_MESSAGE, e);
        } catch (JsonMappingException e) {
            log.error(JSON_MAPPING_EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            log.error(IO_EXCEPTION_MESSAGE, e);
        }
        return t;
    }

    public static String generateUniqueToken(String algorithm) throws NoSuchAlgorithmException {
        String uuid = UUID.randomUUID().toString();
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(uuid.getBytes());
        byte[] sha = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < sha.length; i++) {
            hexString.append(String.format("%02X", sha[i]));

        }
        return hexString.toString();
    }

    /**
     * 
     * @param type
     * @return
     */
    public static String getFileType(String type) {
        if (type.contains("jpg")) {
            return FILE_TYPES.JPEG.getType();
        } else if (type.contains("jpeg")) {
            return FILE_TYPES.JPEG.getType();
        } else if (type.contains("png")) {
            return FILE_TYPES.PNG.getType();
        }
        return "";
    }

    /**
     * 
     * @param inputDate
     * @param inputFormat
     * @param outformat
     * @return
     * @throws ParseException
     */

    public static String getSpecifiedDate(String inputDate, String inputFormat, String outformat)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        Date date = sdf.parse(inputDate);
        sdf = new SimpleDateFormat(outformat);
        return sdf.format(date);
    }

    /**
     * Convert date to specified format.
     * 
     * @param inputDate
     * @param inputFormat
     * @return
     * @throws ParseException
     */
    public static Date getDate(String inputDate, String inputFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        return sdf.parse(inputDate);
    }

    /**
     * Get system date for specified format.
     * 
     * @param outformat
     * @return
     * @throws ParseException
     */

    public static String getCurrentDate(String outformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(outformat);
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 
     * @param is
     * @param packageName
     * @return
     */
    public static <T> T convertXmlToObject(String xmlString, Class<T> clazz) {
        T xmlBean = null;
        try {
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(xmlString.getBytes()));
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            xmlBean = (T) unmarshaller.unmarshal(isr);
        } catch (JAXBException e) {
            log.error("JAXBException occured: ", e);
        }
        return xmlBean;
    }

    public static <T> T convertXmlToObject(InputStreamReader is, Class<T> clazz) {
        T xmlBean = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            xmlBean = (T) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            log.error("JAXBException occured: ", e);
        }
        return xmlBean;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static List<String> getListFromString(String value) {
        List<String> list = new ArrayList<String>();
        if (hasText(value)) {
            String[] values = value.split(",");
            for (String v : values) {
                list.add(v.trim());
            }
            return list;
        }
        return list;
    }

    /**
     * 
     * @param url
     * @param paramMap
     * @return
     */
    public static String getUrl(String url, Map<String, Object> paramMap) {
    	//System.out.print("getUrl start");
    	//System.out.println(url);
        if (hasText(url) && isNotNull(paramMap)) {
            StringBuilder sb = new StringBuilder(url);
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            	System.out.println(entry.getKey()+"->"+entry.getValue());
                //if (!url.contains("?")) {
            	if ( sb.indexOf("?") == -1 ) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
            //System.out.println(sb.toString());
            return sb.toString();
        }        
        //System.out.println("url after append:"+url);
        //System.out.print(" getUrl end");
        return url;
    }

    public static String formatDate(String dateStr) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");//2007-06-04T10:03:26+04:00
        DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = null;
        try {
            Date date = originalFormat.parse(dateStr);
            formattedDate = targetFormat.format(date);

        } catch (Exception e) {
            log.error("Exception occured: ", e);
        }
        return formattedDate;
    }

    public static String formatNumberToString(Object number) {
        return String.format("%03d", number);
    }

}
