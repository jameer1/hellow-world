package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class FixedAssetIDGenerator implements IdentifierGenerator {

    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "DEP";
        Connection connection = session.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement
                    .executeQuery("select max(ASSET_ID) as Id from pot_db_test.fixedassets_register_dtl");
            if (rs.next()) {
                int id = rs.getInt("ASSET_ID") + 101;
                String generatedId = prefix + new Integer(id).toString();
                System.out.println("Generated Id: " + generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
