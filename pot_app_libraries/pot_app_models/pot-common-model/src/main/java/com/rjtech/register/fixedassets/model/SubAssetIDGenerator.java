package com.rjtech.register.fixedassets.model;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.util.Identifiable;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class SubAssetIDGenerator extends SequenceStyleGenerator {

    private static final Logger log = LoggerFactory.getLogger(SubAssetIDGenerator.class);

    String clientCode = AppUserUtils.getClientCode();

    public Serializable generate(SessionImplementor session, Object obj) {
        if (obj instanceof Identifiable) {
            Identifiable identifiable = (Identifiable) obj;
            Serializable id = (Serializable) identifiable.getId();
            if (id != null) {
                return id + "-" + id;
            }
        }

        log.info("=================id generators" + clientCode);
        return clientCode + "-" + super.generate(session, obj);
    }

}
