package com.rjtech.rjs.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DataTypeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataTypeHandler.class);

    private static final long DEFAULT_LONG_VALUE = -131313;
    private static final double DEFAULT_DOUBLE_VALUE = -131313.0;
    private static final float DEFAULT_FLOAT_VALUE = -131313.0F;
    private static final int DEFAULT_INTEGER_VALUE = -131313;
    private static final short DEFAULT_SHORT_VALUE = -1313;
    private static final String DEFAULT_BOOLEAN_VALUE = "F";

    private DataTypeHandler() {
    }

    public static void writeObject(Object obj, Field field, ObjectOutput objOut) throws IOException {
        try {
            String type = field.getType().getName();

            if ("java.lang.Double".equals(type)) {
                Double db = (Double) getFieldValue(field, field.getName(), obj);
                if (db != null) {
                    objOut.writeDouble(db.doubleValue());
                } else {
                    objOut.writeDouble(DEFAULT_DOUBLE_VALUE);
                }
            } else if ("java.lang.Float".equals(type)) {
                Float db = (Float) getFieldValue(field, field.getName(), obj);
                if (db != null) {
                    objOut.writeFloat(db.floatValue());
                } else {
                    objOut.writeFloat(DEFAULT_FLOAT_VALUE);
                }
            } else if ("java.lang.Integer".equals(type)) {
                Integer db = (Integer) getFieldValue(field, field.getName(), obj);
                if (db != null) {
                    objOut.writeInt(db.intValue());
                } else {
                    objOut.writeInt(DEFAULT_INTEGER_VALUE);
                }
            } else if ("java.lang.Long".equals(type)) {
                Long db = (Long) getFieldValue(field, field.getName(), obj);
                if (db != null) {
                    objOut.writeLong(db.longValue());
                } else {
                    objOut.writeLong(DEFAULT_LONG_VALUE);
                }
            } else if ("java.lang.Short".equals(type)) {
                Short db = (Short) getFieldValue(field, field.getName(), obj);
                if (db != null) {
                    objOut.writeShort(db.shortValue());
                } else {
                    objOut.writeShort(DEFAULT_SHORT_VALUE);
                }
            } else if ("java.lang.Boolean".equals(type)) {
                Boolean db = (Boolean) getFieldValue(field, field.getName(), obj);
                if (db != null) {
                    objOut.writeObject(db.toString());
                } else {
                    objOut.writeObject(DEFAULT_BOOLEAN_VALUE);
                }
            } else {
                Object object = getFieldValue(field, field.getName(), obj);
                objOut.writeObject(object);
            }
        } catch (Exception e) {
            LOGGER.error(">> Exception in writeObject() : " + e.getMessage(), e);
            throw new IOException(e);
        }
    }

    public static void readObject(Object obj, Field field, ObjectInput objIn) throws IOException {
        Double dble2 = null;
        Long lng2 = null;
        Integer intgr2 = null;
        Short shrt2 = null;
        Float flt2 = null;
        Boolean bln2 = null;

        try {
            String type = field.getType().getName();
            if ("java.lang.Double".equals(type)) {
                double db1 = objIn.readDouble();
                if (db1 != DEFAULT_DOUBLE_VALUE) {
                    dble2 = db1;
                }
                setFieldValue(field, field.getName(), obj, dble2);
            } else if ("java.lang.Long".equals(type)) {
                long lg = objIn.readLong();
                if (lg != DEFAULT_LONG_VALUE) {
                    lng2 = Long.valueOf(lg);
                }
                setFieldValue(field, field.getName(), obj, lng2);
            } else if ("java.lang.Integer".equals(type)) {
                int intVal = objIn.readInt();
                if (intVal != DEFAULT_INTEGER_VALUE) {
                    intgr2 = Integer.valueOf(intVal);
                }
                setFieldValue(field, field.getName(), obj, intgr2);
            } else if ("java.lang.Float".equals(type)) {
                float flt = objIn.readFloat();
                if (flt != DEFAULT_FLOAT_VALUE) {
                    flt2 = Float.valueOf(flt);
                }
                setFieldValue(field, field.getName(), obj, flt2);
            } else if ("java.lang.Short".equals(type)) {
                short shrt = objIn.readShort();
                if (shrt != DEFAULT_SHORT_VALUE) {
                    shrt2 = Short.valueOf(shrt);
                }
                setFieldValue(field, field.getName(), obj, shrt2);
            } else if ("java.lang.Boolean".equals(type)) {
                String bln = (String) objIn.readObject();
                if (!bln.equals(DEFAULT_BOOLEAN_VALUE)) {
                    bln2 = new Boolean(bln);
                }
                setFieldValue(field, field.getName(), obj, bln2);
            } else {
                setFieldValue(field, field.getName(), obj, objIn.readObject());
            }
        } catch (Exception e) {
            LOGGER.error(">> Exception in readObject() : " + e.getMessage());
            throw new IOException(e);
        }
    }

    private static void setFieldValue(Field field, String fieldName, Object obj, Object fieldVal) {
        try {
            field.setAccessible(true);
            field.set(obj, fieldVal);
        } catch (Exception e) {
            LOGGER.error("Exception in getFieldValue(): Unable to set " + fieldName + " in " + obj.getClass().getName(),
                    e);
        }
    }

    private static Object getFieldValue(Field field, String fieldName, Object obj) {
        Object fieldValue = null;
        try {
            field.setAccessible(true);
            fieldValue = field.get(obj);
            return fieldValue;
        } catch (Exception e) {
            try {
                LOGGER.debug("Exception in getFieldValue()....", e);
                Method m = obj.getClass().getMethod(fieldName, new Class[] {});
                return m.invoke(obj, new Object[] {});
            } catch (Exception ee) {
                LOGGER.error("Exception in getFieldValue(): Unable to set value for :" + fieldName + " in "
                        + obj.getClass().getName(), ee);
                return null;
            }
        }
    }

}
