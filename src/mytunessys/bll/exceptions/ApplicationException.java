package mytunessys.bll.exceptions;


public class ApplicationException extends Exception {
    public ApplicationException(String message, Throwable exception) {
        super(message, exception);
    }
}
