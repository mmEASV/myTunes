package mytunessys.bll.exceptions;

import java.sql.SQLException;

public class CustomException extends SQLException {
    public CustomException(String message, Throwable exception) {
        super(message, exception);
    }
}
