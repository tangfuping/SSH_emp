package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import entity.User;




@Repository
public class userDao extends daoTemplateImpl<User>{

	 public User login(User u)throws Exception{
		 String hql="from "+claz.getName()+" as u where u.username=:name and u.password=:pwd";
		 Query query=getSession().createQuery(hql);
		 query.setString("name",u.getUsername());
		 query.setString("pwd",u.getPassword());
		 return (User)query.uniqueResult();
		 
	 }
	
}
