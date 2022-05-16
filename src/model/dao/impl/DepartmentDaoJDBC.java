package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao
{
    //FIELDS
    private final Connection connection;

    //CONSTRUCTORS
    public DepartmentDaoJDBC(Connection connection)
    {
        this.connection = connection;
    }

    //METHODS
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException
    {
    	Integer id = resultSet.getInt("Id");
    	String name = resultSet.getString("Name");
    	Department department = new Department(id, name);
    	return department;
    }
    
    //OVERRIDE METHODS
    @Override public void insert(Department d)
    {
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement
            (
                "INSERT INTO department (Name) " +
                "VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, d.getName());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
                d.setId(resultSet.getInt(1));

            DB.closeResultSet(resultSet);
        }
        catch(SQLException ex)
        {
            throw new DBException(ex.getMessage());
        }
        finally
        {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override public void update(Department d)
    {

    }

    @Override public void deleteById(Integer id)
    {

    }

    @Override public Department findById(Integer id)
    {
        return null;
    }

    @Override public List<Department> findAll()
    {
        List<Department> deps = new ArrayList<Department>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
        	preparedStatement = connection.prepareStatement(
        			"SELECT * FROM department"
        	);
        	resultSet = preparedStatement.executeQuery();
        	
        	while(resultSet.next())
        		deps.add(instantiateDepartment(resultSet));
        	
        	return deps;
        }
        catch(SQLException ex)
        {
        	throw new DBException(ex.getMessage());
        }
    }
}
