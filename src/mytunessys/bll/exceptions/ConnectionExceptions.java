package mytunessys.bll.exceptions;

import java.sql.SQLException;

public class ConnectionExceptions extends SQLException {
    public ConnectionExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
