package com.rjtech.rjs.persistence.parser;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.rjtech.rjs.persistence.entitymanager.RJSEntityManagerFactory;
import com.rjtech.rjs.persistence.exception.RJSIDGenerationException;
import com.rjtech.rjs.persistence.exception.RJSMultipleRJSEntityManagerFactoryConfiguredException;
import com.rjtech.rjs.persistence.idgenerator.RJSIDGeneratorFactory;

public class RJSPrimaryKeyGenerator implements ApplicationContextAware {

    private static Map<String, Boolean> dialectMap = new HashMap<String, Boolean>();

    private static RJSEntityManagerFactory rjsEntityManagerFactory;

    public static RJSEntityManagerFactory getEGSEntityManagerFactory() {
        return rjsEntityManagerFactory;
    }

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RJSEntityManagerFactory> rjsEntityManagerFactoryMap = (Map<String, RJSEntityManagerFactory>) applicationContext
                .getBeansOfType(RJSEntityManagerFactory.class);
        if (rjsEntityManagerFactoryMap == null || rjsEntityManagerFactoryMap.size() > 1) {
            throw new RJSMultipleRJSEntityManagerFactoryConfiguredException(
                    "Multiple RJSEntityManagerFactory configured. Only one is allowed to configure");
        }
        rjsEntityManagerFactory = rjsEntityManagerFactoryMap.values().iterator().next();

    }

    private static EntityManager getEntityManager() {
        EntityManager entityManager = rjsEntityManagerFactory.getEntityManager();
        return entityManager;
    }

    @SuppressWarnings("rawtypes")
    public static void setPrimaryKey(Object entity) {
        boolean isCustomSequenceGeneratorAnnotationPresent = RJSIDGenaratorParser
                .isCustomIDGeneratorPresent(entity.getClass());
        if (!isCustomSequenceGeneratorAnnotationPresent) {
            return;
        }
        if (getIdValue(entity) == null) {
            SingularAttribute singularAttribute = getIdProperty(entity.getClass());
            String fieldName = singularAttribute.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setterMethodName = new StringBuffer().append("set").append(firstLetter)
                    .append(fieldName.substring(1)).toString();
            Method setterMethod = null;
            try {
                setterMethod = entity.getClass().getDeclaredMethod(setterMethodName,
                        new Class[] { singularAttribute.getBindableJavaType() });
                if (RJSIDGenaratorParser.validate(entity.getClass(), fieldName, setterMethod)) {
                    setterMethod.invoke(entity,
                            new Object[] { getCastedValue((BigDecimal) getNextValue(entity, fieldName, setterMethod),
                                    singularAttribute.getBindableJavaType().getName()) });
                }
            } catch (Exception e) {
                throw new RJSIDGenerationException("Could not set the ID of " + entity.getClass().getName(), e);
            }

        }

    }

    private static Object getCastedValue(BigDecimal nextValue, String name) {
        if ("int".equals(name) || "java.lang.Integer".equals(name)) {
            return Integer.valueOf(nextValue.intValue());
        }

        if ("long".equals(name) || "java.lang.Long".equals(name)) {
            return Long.valueOf(nextValue.longValue());
        }
        // default
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static SingularAttribute getIdProperty(Class entityClass) {
        SingularAttribute idAttribute = null;
        Metamodel metamodel = getEntityManager().getMetamodel();
        EntityType entity = metamodel.entity(entityClass);
        Set<SingularAttribute> singularAttributes = entity.getSingularAttributes();
        for (SingularAttribute singularAttribute : singularAttributes) {
            if (singularAttribute.isId()) {
                idAttribute = singularAttribute;
                break;
            }
        }
        if (idAttribute == null) {
            throw new RJSIDGenerationException("id field not found in the entity : " + entityClass.getName());
        }
        return idAttribute;
    }

    private static Object getIdValue(Object entity) {
        return getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);

    }

    private static Object getNextValue(Object entity, String primaryKeyFieldName, Method methodName) {
        String dataBaseObjectName = RJSIDGenaratorParser.parse(entity.getClass(), primaryKeyFieldName, methodName);
        return RJSIDGeneratorFactory.getSequenceGenerator(sequenceSupported()).generate(getEntityManager(),
                dataBaseObjectName);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean sequenceSupported() {

        Map emfPropertyMap = getEntityManager().getEntityManagerFactory().getProperties();
        if (emfPropertyMap.containsKey("hibernate.dialect")) {
            try {
                Class dialect = Class.forName((String) emfPropertyMap.get("hibernate.dialect"));
                if (!dialectMap.containsKey(dialect.getName())) {
                    Method supportsSequences = dialect.getMethod("supportsSequences");
                    Object obj = supportsSequences.invoke(dialect.newInstance());
                    dialectMap.put(dialect.getName(), (Boolean) obj);
                }
                return dialectMap.get(dialect.getName());

            } catch (Exception ex) {
                throw new RJSIDGenerationException("Error while loading the dialect", ex);
            }
        }
        return true;
    }

}
