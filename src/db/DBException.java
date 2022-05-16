package db;

import java.io.Serial;

public class DBException extends RuntimeException
{
    //FIELDS
    @Serial private static final long serialVersionUID = 1L;

    //CONSTRUCTORS
    public DBException(String message)
    {
        super(message);
    }
}
