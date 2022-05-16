package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB
{
    //STATIC FIELDS
    private static Connection connection;

    //STATIC METHODS
    public static Connection getConnection()
    {
        if(connection == null)
        {
            try (FileInputStream fs = new FileInputStream("db.properties")) {
                Properties properties = new Properties();
                properties.load(fs);
                String url = properties.getProperty("dburl");
                connection = DriverManager.getConnection(url, properties);
            } catch (IOException | SQLException ex) {
                throw new DBException(ex.getMessage());
            }
        }

        return connection;
    }

    public static void closeConnection()
    {
        try
        {
            connection.close();
        }
        catch(SQLException ex)
        {
            throw new DBException(ex.getMessage());
        }
    }

    public static void closeStatement(Statement statement)
    {
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch (SQLException ex)
            {
                throw new DBException(ex.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet)
    {
        if(resultSet != null)
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException ex)
            {
                throw new DBException(ex.getMessage());
            }
        }
    }
}
