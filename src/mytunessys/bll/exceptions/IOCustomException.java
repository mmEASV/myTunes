package mytunessys.bll.exceptions;

import java.io.IOException;

public class IOCustomException extends IOException {
    public IOCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
