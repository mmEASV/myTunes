package mytunessys.dal.dbConnector;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunessys.bll.exceptions.ConnectionExceptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class MSSQLConnection {

    private static SQLServerDataSource ds = null;
    private static final String MSSQL_FILE = "resources/mssqlConfig.properties";

    /**
     * Creates connection from SQLServerDataSource and binds credentials from environmental variables
     * @return Connection (session) with specific database
     * @throws RuntimeException if failes to connect with the session database
     */

    public static Connection createConnection() {
        Properties properties = loadConfigFile();
        Connection conn = null;

        ds = new SQLServerDataSource();
        ds.setDatabaseName(properties.getProperty("db.name"));
        ds.setUser(properties.getProperty("db.username"));
        ds.setPassword(properties.getProperty("db.password"));
        ds.setServerName(properties.getProperty("db.server"));
        ds.setPortNumber(Integer.parseInt(properties.getProperty("db.port")));
        ds.setTrustServerCertificate(true);

        try {
            conn  = ds.getConnection();
        } catch (SQLServerException e) {
            throw new ConnectionExceptions("Could not establish connection with the database",e.getCause()); // throw custom exception or Logger.SEVER etc
        }
        return conn;
    }

    /**
     * Reads fileInputStream from static final instance variable
     * and loads them into properties
     * @return Properties read from instance variable (pref resources)
     */
    private static Properties loadConfigFile(){
        try(FileInputStream fs = new FileInputStream(MSSQL_FILE)){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }catch(IOException ex){
            throw new RuntimeException(); // We could have custom exception here
        }
    }
}
