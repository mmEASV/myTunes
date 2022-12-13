package mytunessys.dal.connectors;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunessys.dal.AbstractConnectionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


/**
 * @author Tomas Simko
 * MSSQL connector that will load data from config file when instantiated
 * and can be used in try with resources by getting the connection source
 */

public class MSSQLConnection implements AbstractConnectionFactory {

    private static final String MSSQL_FILE = "resources/mssqlConfig.properties";

    private SQLServerDataSource dataSource = null;

    /**
     * Default constructor that will try to load config and set new Datasource
     * @throws IOException if fails load configuration files for this connector
     */
    public MSSQLConnection() throws IOException {
        Properties properties = loadConfigFile();
        this.dataSource = new SQLServerDataSource();
        this.dataSource.setDatabaseName(properties.getProperty("db.name"));
        this.dataSource.setUser(properties.getProperty("db.username"));
        this.dataSource.setPassword(properties.getProperty("db.password"));
        this.dataSource.setServerName(properties.getProperty("db.server"));
        this.dataSource.setPortNumber(Integer.parseInt(properties.getProperty("db.port")));
        this.dataSource.setTrustServerCertificate(true);
    }
    /**
     * Creates connection from SQLServerDataSource and binds credentials from environmental variables
     * @return Connection (session) with specific database
     * @throws SQLServerException if failed to connect with the session database
     */
    @Override
    public Connection createConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * Reads fileInputStream from static final instance variable
     * and loads them into properties
     * @return Properties read from instance variable (pref resources)
     */
    private static Properties loadConfigFile() throws IOException {
        try(FileInputStream fs = new FileInputStream(MSSQL_FILE)){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }
    }
}