package mytunessys.dal.connectionFactory;

import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.connectors.MSSQLConnection;

import java.io.IOException;

public class ConnectionFactory {
    /**
     * method that is called for getting factory by given type as a parameters
     * @param type Enum | DataType defined MSSQL or other -> DataBaseType.MSSQL
     * @return Factory for chosen database type connection
     * @throws IOException if not able to retrieve credentials from configuration file
     */
        public static IConnectionFactory getFactory(DatabaseType type) throws IOException {
            switch (type) {
                case MSSQL:
                    return new MSSQLConnection();
                default:
                    throw new IllegalArgumentException("Invalid database type: " + type);
            }
        }
}
