
package com.rjtech.rjs.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

/**
 * RJSSpringSerializer
 *
 * 
 */
public final class RJSSpringSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RJSSpringSerializer.class);

    /**
     * Get the different fields from the Object and write using DataTypeHandler.
     *
     * @see DataTypeHandler
     */

    private RJSSpringSerializer() {
    }

    /**
     * <p>
     * writeExternal.
     * </p>
     *
     * @param obj    a {@link java.lang.Object} object.
     * @param objOut a {@link java.io.ObjectOutput} object.
     * @throws java.io.IOException if any.
     */
    public static void writeExternal(Object obj, ObjectOutput objOut) throws IOException {
        List<Object> objectFieldsList = ObjectUtility.getObjectFields(obj.getClass());

        Iterator<?> iter = objectFieldsList.iterator();

        while (iter.hasNext()) {
            Field[] objFields = (Field[]) iter.next();

            ObjectUtility.sort(objFields);

            /** Sort the field using ObjectUtility... */

            for (int i = 0; i < objFields.length; i++) {
                int modifier = objFields[i].getModifiers();

                /** Ignore final, static and Transient field's... */

                if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier) || Modifier.isTransient(modifier)) {
                    LOGGER.debug("writeExternal() : ignoring Field : " + objFields[i]);
                } else {
                    DataTypeHandler.writeObject(obj, objFields[i], objOut);
                }

            }
        }
    }

    /**
     * Recreate the Object using DataTypeHandler
     *
     * @param obj a {@link java.lang.Object} object.
     * @param in  a {@link java.io.ObjectInput} object.
     * @throws java.io.IOException if any.
     * @see DataTypeHandler
     */
    public static void readExternal(Object obj, ObjectInput in) throws IOException {
        List<Object> objectFieldsList = ObjectUtility.getObjectFields(obj.getClass());

        Iterator<?> iter = objectFieldsList.iterator();

        while (iter.hasNext()) {
            Field[] objFields = (Field[]) iter.next();

            ObjectUtility.sort(objFields);

            for (int i = 0; i < objFields.length; i++) {
                int modifier = objFields[i].getModifiers();

                /** Ignore final , static and Transient field's... */

                if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier) || Modifier.isTransient(modifier)) {
                    LOGGER.debug("readExternal() : ignoring Field : " + objFields[i]);
                } else {
                    DataTypeHandler.readObject(obj, objFields[i], in);
                }
            }
        }
    }

}
