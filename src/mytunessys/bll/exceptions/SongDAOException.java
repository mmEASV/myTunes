package mytunessys.bll.exceptions;

import java.sql.SQLException;

public class SongDAOException extends SQLException {
    public SongDAOException(String message,Throwable cause){
        super(message,cause);
    }
}
