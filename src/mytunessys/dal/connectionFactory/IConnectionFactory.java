package mytunessys.dal.connectionFactory;

import java.sql.Connection;

public interface IConnectionFactory {
    /**
     * method for that creates connection with given type of the database
     * and can be used in try with resources to start using jdbc
     * @return Connection a session with specific database
     * @throws Exception if files to create connection
     */
    Connection createConnection() throws Exception;
}
