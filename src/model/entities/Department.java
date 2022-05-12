package model.entities;

import java.io.Serial;
import java.io.Serializable;

public class Department implements Serializable, Comparable<Department>
{
	@Serial private static final long serialVersionUID = 1L;
	
	//FIELDS
	private Integer id;
	private String name;
	
	//CONSTRUCTORS
	public Department()
	{
	}
	
	public Department(Integer id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	//PROPERTY METHODS
	public Integer getId() {return id;}
	public String getName() {return name;}
	
	public void setId(Integer value) {id = value;}
	public void setName(String value) {name = value;}
	
	//METHODS
	@Override public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Department other = (Department)obj;
		return id.equals(other.id) && name.equals(other.name);
	}
	
	@Override public int hashCode() 
	{
		return "Department".hashCode() + id.hashCode() + name.hashCode();
	}
	
	@Override public int compareTo(Department o) 
	{
		return id.compareTo(o.id);
	}
}
