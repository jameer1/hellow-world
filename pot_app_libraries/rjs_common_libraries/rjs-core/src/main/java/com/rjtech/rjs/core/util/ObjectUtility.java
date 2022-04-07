package com.rjtech.rjs.core.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ObjectUtility {
    private ObjectUtility() {
    }

    public static List<Object> getObjectFields(Class<? extends Object> clazz) {
        List<Object> fieldList = new ArrayList<Object>();
        fieldList.add(clazz.getDeclaredFields());

        Class<?> superClazz = clazz.getSuperclass();

        while (!superClazz.getName().equals("java.lang.Object")) {
            fieldList.add(superClazz.getDeclaredFields());
            superClazz = superClazz.getSuperclass();
        }
        return fieldList;
    }

    public static void sort(Field[] fields) {
        Arrays.sort(fields, new FieldComparator());
    }

    private static class FieldComparator implements Comparator<Object>, Serializable {

        private static final long serialVersionUID = 6490362742173556941L;

        public int compare(Object obj1, Object obj2) {
            Field field1 = (Field) obj1;
            Field field2 = (Field) obj2;

            String a = (String) field1.getName();
            String b = (String) field2.getName();

            return a.compareTo(b);
        }
    }
}
