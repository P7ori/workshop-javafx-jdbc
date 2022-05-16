package model.entities;

import java.io.Serial;
import java.io.Serializable;

public class Department implements Comparable<Department>, Serializable
{
    //STATIC FIELDS
    @Serial private static final long serialVersionUID = 1L;

    //INSTANCE FIELDS
    private Integer id;
    private String name;

    //CONSTRUCTOR
    public Department()
    {
    }

    public Department(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    //PROPERTIES
    public Integer getId() {return id;}
    public String getName() {return name;}

    public void setId(Integer value) {id = value;}
    public void setName(String value) {name = value;}

    //OVERRIDE METHODS
    @Override public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        Department other = (Department) o;
        return id.equals(other.id) && name.equals(other.name);
    }

    @Override public int hashCode()
    {
        return "model.entities.Department".hashCode() +
                (id == null ? 0 : id.hashCode()) +
                (name == null ? 0 : name.hashCode());
    }

    @Override public int compareTo(Department d)
    {
        return id.compareTo(d.id);
    }

    @Override public String toString()
    {
        return String.format("Department [id = %d, name = %s]", id, name);
    }
}
