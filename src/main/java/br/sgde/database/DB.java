package br.sgde.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    public static Connection getConexao(){

        try{
            var jdbcUrl = DatabaseConfig.getDbUrl();
            var user = DatabaseConfig.getDbUsername();
            var password = DatabaseConfig.getDbPassword();
            return DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
