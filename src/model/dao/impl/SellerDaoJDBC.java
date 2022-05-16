package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SellerDaoJDBC implements SellerDao
{
    //FIELDS
    private final Connection connection;
    private int affectedRows = 0;

    //CONSTRUCTORS
    public SellerDaoJDBC(Connection connection)
    {
        this.connection = connection;
    }

    //PROPERTY METHODS
    public int getAffectedRows() {return affectedRows;}

    //PRIVATE METHODS
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException
    {
        Integer departmentId = resultSet.getInt("DepartmentId");
        String departmentName = resultSet.getString("DepartmentName");

        return new Department(departmentId, departmentName);
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException
    {
        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");
        String email = resultSet.getString("Email");
        Date date = resultSet.getDate("BirthDate");
        Double baseSalary = resultSet.getDouble("BaseSalary");

        return new Seller(id, name, email, date, baseSalary, department);
    }

    //OVERRIDE METHODS
    @Override public void insert(Seller s)
    {
        this.affectedRows = 0;
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller " +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, s.getName());
            preparedStatement.setString(2, s.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            preparedStatement.setDouble(4, s.getBaseSalary());
            preparedStatement.setInt(5, s.getDepartment().getId());

            int affectedRows = preparedStatement.executeUpdate();
            this.affectedRows = affectedRows;

            if(affectedRows > 0)
            {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    int id = resultSet.getInt(1);
                    s.setId(id);
                }
                else
                    throw new DBException("Unexpected error! No rows affected.");

                resultSet.close();
            }
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

    @Override public void update(Seller s)
    {
        this.affectedRows = 0;
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(
                     "UPDATE seller " +
                     "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                     "WHERE Id = ?;",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, s.getName());
            preparedStatement.setString(2, s.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            preparedStatement.setDouble(4, s.getBaseSalary());
            preparedStatement.setInt(5, s.getDepartment().getId());
            preparedStatement.setInt(6, s.getId());

            this.affectedRows = preparedStatement.executeUpdate();
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

    @Override public void deleteById(Integer id)
    {
        affectedRows = 0;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement
            (
                    "DELETE FROM seller " +
                    "WHERE Id = ?",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setInt(1, id);

            affectedRows = preparedStatement.executeUpdate();
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

    @Override public Seller findById(Integer id)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.Name AS DepartmentName \n" +
                            "FROM seller INNER JOIN department\n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "WHERE seller.Id = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) //ResultSet aponta inicialmente para 0, que é o cabeçalho da tabela.
                return null;

            Department department = instantiateDepartment(resultSet);
            return instantiateSeller(resultSet, department);
        }
        catch(SQLException ex)
        {
            throw new DBException(ex.getMessage());
        }
        finally
        {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override public List<Seller> findAll()
    {
        List<Seller> sellers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.Name AS DepartmentName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name");
            resultSet = preparedStatement.executeQuery();


            Set<Department> departments = new HashSet<>();
            while(resultSet.next())
            {
                Department department = new Department(
                        resultSet.getInt("DepartmentId"),
                        resultSet.getString("DepartmentName"));
                departments.add(department);

                int id = resultSet.getInt("DepartmentId");

                department = departments.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
                Seller seller = instantiateSeller(resultSet, department);
                sellers.add(seller);
            }
        }
        catch(SQLException ex)
        {
            throw new DBException(ex.getMessage());
        }
        finally
        {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }

        return sellers;
    }

    @Override public List<Seller> findByDepartment(Department d)
    {
        List<Seller> sellers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.Name AS DepartmentName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name");
            preparedStatement.setInt(1, d.getId());
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
                sellers.add(instantiateSeller(resultSet, d));
        }
        catch(SQLException ex)
        {
            throw new DBException(ex.getMessage());
        }

        return sellers;
    }
}
