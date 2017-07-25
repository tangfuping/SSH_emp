package servicesDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.employeeDao;
import entity.Employee;

@Service
public class employeeServiceDao {
 @Autowired
 employeeDao empDao;
 
  public Employee save(Employee e)throws Exception{
	  empDao.save(e);
	  return e;
  }
 
  public boolean delete(Employee e)throws Exception{
	 return empDao.delete(e)>0;
	 
  }

  public boolean update(Employee e)throws Exception{
		 return empDao.update(e)>0;
		 
	  }
  
  public List<Employee> query()throws Exception{
		 return empDao.select();
		 
	  }
  public List<Employee> getBasic(String condition)throws Exception{
		return empDao.getBasic(condition);
	}
  
  public Employee get(Integer id)throws Exception{
	  return empDao.get(id);
  }
  
public employeeDao getEmpDao() {
	return empDao;
}

public void setEmpDao(employeeDao empDao) {
	this.empDao = empDao;
}
 

	
}
