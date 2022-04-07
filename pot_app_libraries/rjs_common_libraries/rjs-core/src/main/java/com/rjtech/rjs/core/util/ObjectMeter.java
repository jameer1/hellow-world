package com.rjtech.rjs.core.util;

import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.rjs.core.exception.RJSRuntimeException;

public final class ObjectMeter {

    private ObjectMeter() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(ObjectMeter.class);

    public static <E> int sizeOf(E object) {
        int objSize = 0;

        try {
            if (object != null) {
                objSize = calculateSize((Serializable) object);
            } else {
                LOG.debug("sizeOf(): Cannot measure size of null Object.");
            }
        } catch (NotSerializableException e) {
            LOG.error(
                    "NotSerializableException in sizeOf() : Class of this object does not implement Serializable interface",
                    e);
        } catch (Exception e) {
            LOG.error("Exception in sizeOf() : " + e.getMessage(), e);
        }
        return objSize;
    }

    private static int calculateSize(Serializable object) throws NotSerializableException {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            oos.close();
            baos.close();
            return bytes.length;
        } catch (Exception e) {
            throw new RJSRuntimeException(e);
        } finally {
            oos = null;
            baos = null;
        }
    }
}
