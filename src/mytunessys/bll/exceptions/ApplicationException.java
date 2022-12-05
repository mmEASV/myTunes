package mytunessys.bll.exceptions;

import java.sql.SQLException;

public class ApplicationException extends SQLException {
    public ApplicationException(String message, Throwable exception) {
        super(message, exception);
    }
}
