package libSource.Database;
import  libSource.Attributes.*;
import  libSource.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by JacKeTUs on 07.04.2017.
 */
public class DBManager {

    private Connection dbConnection;

    public DBManager() {};

    public Connection ConnectSQLiteDB() throws SQLException {

        dbConnection = DriverManager.getConnection("jdbc:sqlite:./db/db.sqlite3");

        System.out.println("Database was opened!");
        return getConnection();
    }

    public Connection getConnection() {
        return dbConnection;
    }

    public ResultSet ExecQuery(String query_str) throws SQLException {
        System.out.println(query_str);
        return dbConnection.createStatement().executeQuery(query_str);
    }

    public void ExecQueryWOResultSet(String query_str) throws SQLException {
        System.out.println(query_str);
        dbConnection.createStatement().executeUpdate(query_str);
    }
    
}
