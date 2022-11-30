package mytunessys.bll.exceptions;

public class PlaylistDAOException extends RuntimeException{
    public PlaylistDAOException(String message,Throwable cause){
        super(message,cause);
    }
}
