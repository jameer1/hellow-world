package com.rjtech.common.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.providers.ApplicationContextProvider;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.CommonRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class HibernateInterceptor extends EmptyInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HibernateInterceptor.class);

    private static final String STRING_TYPE = "string";
    private static final String LONG_TYPE = "long";

    public HibernateInterceptor() {
       // log.debug("HibernateInterceptor object created");

    }

    private boolean autowireInitialized = false;

    @Autowired
    private CommonRepository userRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
            org.hibernate.type.Type[] types) {

        setCreatedBy(getUserObject(), state, propertyNames, types, entity);

        return true;

    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, org.hibernate.type.Type[] types) {

       // log.info("Setting updatedBy to entity: {}", entity.getClass());
        setUpdatedBy(getUserObject(), currentState, propertyNames, types);

        return true;
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        initAutowire();
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {

        super.afterTransactionCompletion(tx);

    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

    }

    @Override
    public void onCollectionUpdate(Object collection, Serializable key) {
        super.onCollectionUpdate(collection, key);
    }

    @Override
    public void onCollectionRecreate(Object collection, Serializable key) {
        super.onCollectionRecreate(collection, key);
    }

    @Override
    public void onCollectionRemove(Object collection, Serializable key) {
        super.onCollectionRemove(collection, key);
    }

    private void setCreatedBy(UserMstrEntity user, Object[] state, String[] propertyNames,
            org.hibernate.type.Type[] types, Object entity) {

       // log.info("Setting createdBy to entity: {}", entity.getClass());

        setUpdatedBy(user, state, propertyNames, types);
        setClientId(state, propertyNames, types, entity);

        for (int i = 0; i < state.length; i++) {
            if (!types[i].getName().equals(STRING_TYPE) && propertyNames[i].equals("createdBy")) {
                state[i] = user;
            } else if (propertyNames[i].equals("createdOn")) { // set createdOn
                state[i] = CommonUtil.getNow();
            }
        }

    }

    private void setUpdatedBy(UserMstrEntity user, Object[] state, String[] propertyNames,
            org.hibernate.type.Type[] types) {

        for (int i = 0; i < state.length; i++) {
            // set updatedBy
            if (!types[i].getName().equals(STRING_TYPE) && propertyNames[i].equals("updatedBy")) {
                state[i] = user;
            } else if (propertyNames[i].equals("updatedOn")) {
                state[i] = CommonUtil.getNow();
            }
        }

    }

    private void setClientId(Object[] state, String[] propertyNames, org.hibernate.type.Type[] types, Object entity) {
        Long clientId = AppUserUtils.getClientId();
        if (clientId == null) {
            clientId = AppUserUtils.getAdminClientId();
        }
        // Don't set client ID for RoleMstrEntity when Raju is creating a role
        if (clientId == null || (clientId.equals(1L)
                && entity.getClass().getName().equals("com.rjtech.role.model.RoleMstrEntity"))) {
            return;
        }
        //log.info("Setting clientID to entity: {}", entity.getClass());
        initAutowire();
        ClientRegEntity client = clientRegRepository.findOne(clientId);
        for (int i = 0; i < state.length; i++) {
            // set clientId
            if (!(types[i].getName().equals(STRING_TYPE) || types[i].getName().equals(LONG_TYPE))
                    && propertyNames[i].equals("clientId")) {
                state[i] = client;
                break;
            }
        }

    }

    private UserMstrEntity getUserObject() {
        initAutowire();
        return userRepository.findOne(AppUserUtils.getUserId());
    }

    /**
     * Initialize autowire if not done
     */
    private void initAutowire() {
        //log.info("Autowire initialized: {}", autowireInitialized);
        if (!autowireInitialized) {
            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
           // log.info("App context: {}", ctx);
            if (ctx != null) {
                ctx.getAutowireCapableBeanFactory().autowireBeanProperties(this,
                        AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
                autowireInitialized = true;
            }
        }
    }

}
