package mytunessys.bll.exceptions;
/**
 * @author Tomas
 */
public class ApplicationException extends Exception {
    public ApplicationException(String message, Throwable exception) {
        super(message, exception);
    }
}
