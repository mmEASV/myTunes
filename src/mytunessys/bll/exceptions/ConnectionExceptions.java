package mytunessys.bll.exceptions;

public class ConnectionExceptions extends RuntimeException{
    public ConnectionExceptions(String message,Throwable cause){
        super(message,cause);
    }
}