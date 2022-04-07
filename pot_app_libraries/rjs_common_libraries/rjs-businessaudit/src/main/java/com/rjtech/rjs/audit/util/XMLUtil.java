package com.rjtech.rjs.audit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.XmlMappingException;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class XMLUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtil.class);

    
    @Autowired
    @Qualifier("xstreamMarshaller")
    protected Marshaller marshaller;


    public String getXMLString(Object object) {

        if (object == null) {
            return "NA";
        }

        String xmlString = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Result result = new StreamResult(bos);

        try {
            marshaller.marshal(object, result);
            byte[] bytes = bos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                xmlString = new String(bos.toByteArray());
            }
        } catch (XmlMappingException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return xmlString;
    }

}
