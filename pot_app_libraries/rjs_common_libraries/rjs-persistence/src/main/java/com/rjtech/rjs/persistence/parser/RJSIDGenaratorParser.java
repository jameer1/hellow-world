package com.rjtech.rjs.persistence.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.rjs.core.annotations.RJSIDGenerator;
import com.rjtech.rjs.persistence.exception.RJSIDGeneratorNotFoundException;
import com.rjtech.rjs.persistence.exception.RJSMultipleIDGeneratorPresentException;

public final class RJSIDGenaratorParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RJSIDGenaratorParser.class);

    private RJSIDGenaratorParser() {

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String parse(Class clazz, String primaryKeyName, Method methodName) {
        String sequenceName = clazz.getSimpleName() + "_seq";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            sequenceName = table.name() + "_seq";

        }
        if (clazz.isAnnotationPresent(RJSIDGenerator.class)) {
            RJSIDGenerator rjsIDGenerator = (RJSIDGenerator) clazz.getAnnotation(RJSIDGenerator.class);
            if (rjsIDGenerator.sequenceName() != null && rjsIDGenerator.sequenceName().trim().length() > 0) {
                sequenceName = rjsIDGenerator.sequenceName();
            }
        } else {
            throw new RJSIDGeneratorNotFoundException(
                    "@RJSIDGenerator annotation not found in entity :" + clazz.getName());
        }
        return sequenceName;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean isCustomIDGeneratorPresent(Class clazz) {
        return clazz.isAnnotationPresent(RJSIDGenerator.class);

    }

    @SuppressWarnings("unchecked")
    public static boolean validate(Class clazz, String primaryKeyName, Method methodName)
            throws SecurityException, NoSuchFieldException {

        Field field = clazz.getDeclaredField(primaryKeyName);
        boolean isGeneratedValuePresent = field.isAnnotationPresent(GeneratedValue.class)
                || methodName.isAnnotationPresent(GeneratedValue.class);
        if (isGeneratedValuePresent) {
            throw new RJSMultipleIDGeneratorPresentException(
                    clazz.getCanonicalName() + " : Morethan one ID genertor present");
        }
        return true;

    }

    public static Annotation[] findFieldAnnotation(Class<?> clazz, String fieldName) {
        Annotation[] annotations = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field != null) {
                annotations = field.getAnnotations();
            }
        } catch (SecurityException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return annotations;
    }

    /**
     * <p>
     * findClassAnnotation.
     * </p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @return an array of {@link java.lang.annotation.Annotation} objects.
     */
    public static Annotation[] findClassAnnotation(Class<?> clazz) {
        return clazz.getAnnotations();
    }

    public static Annotation[] findMethodAnnotation(Class<?> clazz, String methodName) {
        Annotation[] annotations = null;
        try {
            Class<?>[] params = null;
            Method method = clazz.getDeclaredMethod(methodName, params);
            if (method != null) {
                annotations = method.getAnnotations();
            }
        } catch (SecurityException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return annotations;
    }

    public static GenerationType getStrategy() {
        return GenerationType.AUTO;
    }
}
