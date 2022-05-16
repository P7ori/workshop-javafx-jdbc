package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Seller implements Comparable<Seller>, Serializable
{
    //STATIC FIELDS
    @Serial private static final long serialVersionUID = 1L;

    //INSTANCE FIELDS
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;
    private Department department;

    //CONSTRUCTORS
    public Seller()
    {
    }

    public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, Department department)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    //PROPERTY METHODS
    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public Date getBirthDate() {return birthDate;}
    public Double getBaseSalary() {return baseSalary;}
    public Department getDepartment() {return department;}

    public void setId(Integer value) {id = value;}
    public void setName(String value) {name = value;}
    public void setEmail(String value) {email = value;}
    public void setBirthDate(Date value) {birthDate = value;}
    public void setBaseSalary(Double value) {baseSalary = value;}
    public void setDepartment(Department value) {department = value;}

    //OVERRIDE METHODS
    @Override public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        Seller other = (Seller) o;
        return id.equals(other.id);
    }

    @Override public int hashCode()
    {
        return "model.entities.Seller".hashCode() + (id == null? 0 : id.hashCode());
    }

    @Override public int compareTo(Seller s)
    {
        return id.compareTo(s.id);
    }

    @Override public String toString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Seller [id = %d, name = %s, email = %s, birth date = %s, salary = %.2f, " +
                "department = (%d, %s)]", id, name, email, sdf.format(birthDate), baseSalary,
                department.getId(), department.getName());
    }
}
