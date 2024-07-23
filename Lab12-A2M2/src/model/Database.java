package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    /**
     * The Database class manages the connection to a MySQL database.
     * It is intended for the getConnection method to be called from
     * a try-with-resources statement:
     * 
     * try(Connection connection = Database.getConnection)
     * {
     * // code that might throw SQLException goes here
     * }
     * catch(SQLExcpeption e)
     * {
     * // exception code goes here
     * }
     * 
     * 
     */

    public static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            final String mysqlURL = "jdbc:mysql://localhost:3306/pets";

            /**
             * Get username and password from user's
             * environment variables DB_USER and DB_PASS.
             * If the environment variables are not set,
             * user default values of 'root' and 'mysql'.
             */

            String username = System.getenv("DB_USER");
            if (username == null)
                username = "root";
            String password = System.getenv("DB_PASS");
            if (password == null)
                password = "";

            connection = DriverManager.getConnection(mysqlURL, username, password);
        }
        return connection;
    }

}