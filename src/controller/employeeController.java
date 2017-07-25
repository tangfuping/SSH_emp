package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.employeeDao;
import entity.Employee;
import servicesDao.employeeServiceDao;
import utils.StringUtil;

@Controller
public class employeeController {
	@Autowired
	employeeServiceDao employDao;
	
	@RequestMapping("/toAddPage")
	public String toAddPage(Employee emp) throws Exception{
		
		return "redirect:/emp_add.action";
		
	}
	@RequestMapping("/emp_add")
	public String emp_add(Employee emp) throws Exception{
		employDao.save(emp);
		return "redirect:/emp_list.action";
		
	}
	
	@RequestMapping("/emp_list")
	public String dispEmployee(Map models,String sqlname) throws Exception{
		if(StringUtil.isNotEmpty(sqlname)){
			models.put("employees", employDao.getBasic(sqlname));}
		else{
			models.put("employees", employDao.query());}
		return "empList";
	}

	@RequestMapping("/emp_del")
	public String deleteEmp(Integer id)throws Exception{
		if(id!=null){
			Employee e=new Employee();
			e.setT_id(id);
			employDao.delete(e);	
		}
		return "redirect:/emp_list.action";	
	}
	
	@RequestMapping("/emp_update")
	public String updateEmpBofer(Integer id,Map models)throws Exception{
			Employee e=employDao.get(id);
			models.put("employee",e);
		return "updateEmp";	
	}
	
	@RequestMapping("/emp_updateEmp")
	public String updateEmp(Employee emp)throws Exception{
			employDao.update(emp);
		return "redirect:/emp_list.action";	
	}
	public employeeServiceDao getEmployDao() {
		return employDao;
	}


	public void setEmployDao(employeeServiceDao employDao) {
		this.employDao = employDao;
	}

	
	
}
