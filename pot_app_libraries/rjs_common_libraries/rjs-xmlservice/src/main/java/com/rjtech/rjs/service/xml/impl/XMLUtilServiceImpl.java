package com.rjtech.rjs.service.xml.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

import com.rjtech.rjs.service.xml.XMLUtilService;

@Service(value = "xmlUtilService")
public class XMLUtilServiceImpl implements XMLUtilService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtilServiceImpl.class);

    /**
     * The marshaller.
     */
    @Autowired
    @Qualifier("xstreamMarshaller")
    protected Marshaller marshaller;

    @Autowired
    @Qualifier("xstreamMarshaller")
    protected Unmarshaller unMarshaller;

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

    public Object getObjectFromXMLString(String xmlString) {
        StringReader reader = null;
        Object returnObj = null;
        if (xmlString != null) {
            try {
                reader = new StringReader(xmlString);

                returnObj = unMarshaller.unmarshal(new StreamSource(reader));
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

        }
        return returnObj;
    }

}
