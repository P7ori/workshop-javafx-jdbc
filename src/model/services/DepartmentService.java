package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService 
{
	//FIELDS_________________________________________________________
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	//METHODS________________________________________________________
	public List<Department> findAll()
	{
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department obj)
	{
		if(obj.getId() == null)
			dao.insert(obj);
		else
			dao.update(obj);
	}
}
