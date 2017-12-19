package com.olch.library.persist.connections.hsqldb;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by olch0914 on 15.12.2017.
 */
@Component
public class HsqldbConnections {
    private static final String JDBC = "jdbc:hsqldb:file:";
    private static final String DB_PATH = "db/library/library";
    private static final String JDBC_CONNECTION_STRING = JDBC + DB_PATH;
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";

    private Connection connection;

    public Connection getConnection() throws SQLException {
        //   if(connection==null) {
        connection = DriverManager.getConnection(JDBC_CONNECTION_STRING, USERNAME, PASSWORD);
        //      }
        return connection;
    }


}
