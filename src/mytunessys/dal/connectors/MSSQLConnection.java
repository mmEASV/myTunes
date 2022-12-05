package mytunessys.dal.connectors;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunessys.bll.exceptions.ConnectionExceptions;
import mytunessys.bll.exceptions.IOCustomException;

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
     * @throws ConnectionExceptions if failed to connect with the session database
     */
    public static Connection createConnection() throws ConnectionExceptions{
        Connection conn = null;
        try {
            Properties properties = loadConfigFile();
            ds = new SQLServerDataSource();
            ds.setDatabaseName(properties.getProperty("db.name"));
            ds.setUser(properties.getProperty("db.username"));
            ds.setPassword(properties.getProperty("db.password"));
            ds.setServerName(properties.getProperty("db.server"));
            ds.setPortNumber(Integer.parseInt(properties.getProperty("db.port")));
            ds.setTrustServerCertificate(true);
            conn  = ds.getConnection();
        } catch (SQLServerException | IOCustomException ex) {
            throw new ConnectionExceptions(ex.getMessage(), ex.getCause());
        }
        return conn;
    }

    /**
     * Reads fileInputStream from static final instance variable
     * and loads them into properties
     * @return Properties read from instance variable (pref resources)
     */
    private static Properties loadConfigFile() throws IOCustomException{
        try(FileInputStream fs = new FileInputStream(MSSQL_FILE)){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }catch(IOException ex){
            throw new IOCustomException("Could not load credentials from config file ",ex.getCause());
        }
    }
}
