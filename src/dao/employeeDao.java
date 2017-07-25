package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import entity.Employee;


@Repository
public class employeeDao extends daoTemplateImpl<Employee>{
   /**
    * 模糊查询
    * @param condition eg："张三"
    * @return List<Employee>
    */
public List<Employee> getBasic(String condition) {
	//注意这里的empname是pojo类（实体类）的属性名
	String hql="from "+claz.getName()+" where empname like :name ";
	Query query=getSession().createQuery(hql);
	query.setString("name", "%"+condition+"%");  
	return query.list();
}

	

}
