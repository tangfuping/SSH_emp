package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

//HibernateDaoSupport继承HibernateTemplate(该类中存在一个方法 setSessionFactory(SessionFactory))
//HibernateDaoSupport需要外部注入hibernate的Sessionfactory
//每个pojo类对应的单笔业务Dao都要注入sessionfactory
public class daoTemplateImpl<T> extends HibernateDaoSupport implements daoTemplate<T>{
     protected Class claz;
	 
     
     public daoTemplateImpl(){
    	//实例化claz
     ParameterizedType pt =(ParameterizedType) this.getClass().getGenericSuperclass();
 	  this.claz = (Class<T>) pt.getActualTypeArguments()[0];
 	  
     }

	@Autowired
	public void setSessionFactory1(SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		super.setSessionFactory(sessionFactory);
	}






	@Override
	public int save(T t) throws Exception {
		
		getSession().save(t);
		return 1;
	}


	@Override
	public int delete(T t) throws Exception {
		getSession().delete(t);
		return 1;
	}

	@Override
	public int delete(Integer id) throws Exception {
		T temp=(T)getSession().load(claz, id);
		return delete(temp);
	}

	@Override
	public int update(T t) throws Exception {
		getSession().update(t);
		return 1;
	}

	@Override
	public List<T> select() throws Exception {
		String hql="from "+claz.getName();
		Query query=getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public T get(Integer id) throws Exception {	
		return (T)getSession().get(claz, id);
	}

}
