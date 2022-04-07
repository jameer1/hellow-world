
package com.rjtech.rjs.compression.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rjtech.rjs.compression.exception.CompressionServiceException;
import com.rjtech.rjs.core.util.ObjectMeter;
import com.rjtech.rjs.compression.dto.RJSZippedObject;

@Service("rJSCompressionService")
@Lazy
public class RJSCompressionServiceImpl<E, R> implements RJSCompressionService<E, R> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RJSCompressionServiceImpl.class);

    public RJSZippedObject compressObject(E object) throws CompressionServiceException {

        RJSZippedObject rjsZippedObject = null;
        ObjectOutputStream out = null;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        try {

            LOGGER.debug(
                    "compressObject(): Object() Size before Compression : " + ObjectMeter.sizeOf(object) + " Bytes");

            out = new ObjectOutputStream(new DeflaterOutputStream(buf, new Deflater(Deflater.BEST_COMPRESSION)));
            out.writeObject(object);

            out.flush();
            out.close();

            rjsZippedObject = new RJSZippedObject(buf.toByteArray());

            if (rjsZippedObject.getByteArray() != null) {
                LOGGER.debug("compressObject(): Object() Size after Compression : "
                        + rjsZippedObject.getByteArray().length + " Bytes");
            }

        } catch (Exception e) {
            LOGGER.debug(">> Exception in compressObject() : " + e.getMessage());
            throw new CompressionServiceException("Exception in compressing Object. ", e);
        } finally {
            out = null;
        }
        return rjsZippedObject;
    }

    @SuppressWarnings("unchecked")
    public R unCompressObject(RJSZippedObject rjsZippedObject) throws CompressionServiceException {
        ByteArrayInputStream buf = null;
        ObjectInputStream in = null;
        R retVal = null;
        try {
            buf = new ByteArrayInputStream(rjsZippedObject.getByteArray());
            in = new ObjectInputStream(new InflaterInputStream(buf));
            retVal = (R) in.readObject();
        } catch (Exception e) {
            LOGGER.debug(">> Exception in unCompressObject() : " + e.getMessage());
            throw new CompressionServiceException("Exception in unCompressing Object. ", e);
        } finally {
            buf = null;
            in = null;
        }

        return retVal;
    }

}
