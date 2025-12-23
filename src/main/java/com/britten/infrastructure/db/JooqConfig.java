package com.britten.infrastructure.db;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Jooq config (Krasser Kommentar oder? Hoffe der hat euch geholfen :) )
 */
public class JooqConfig {

    public static DSLContext createContext(){
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:sqlite:f1data.db"
            );
            return DSL.using(connection);
        } catch (Exception e){
            throw new RuntimeException("Failed to create jooq context", e);
        }
    }
}
