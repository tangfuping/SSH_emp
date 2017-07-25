JavaWeb项目系列（一）：员工管理系统（SSH框架）


这是一个员工管理系统，应用的是SSH框架Spring+SpringMVC+Hibernate的项目，重构了之前用纯servlet版本和struts+jsp版本（项目源码在我的github），本次项目特意写一篇博客说明带大家了解项目的需求和设计


文章结构：
1. 项目介绍（功能业务逻辑，运用的知识，项目数据库）；
2. 项目架构介绍以及部分关键逻辑代码说明；
3. 源码分享。

 一、项目介绍（功能业务逻辑，运用的知识，项目数据库）

（1）功能介绍：

	* - 增删改查员工。
	* - 增删改查页面显示当前用户信息（session里获取）
	* - 点击button，倒序，正序显示员工列表
	* - 在list页面模糊查询（按姓名查询）
	* - 分页功能
	* - 部分前端代码


（2）运用的知识：
spring，springmvc，hibernate，mysql


	* - 基本数据库知识MySQL
	* - Spring+SpringMVC+Hibernate
	* - （重点）框架的MVC设计模式的应用
	* - （重点）分页查询
	* - 部分前端代码（css的应用）



 (3)项目构建 
  项目分包：MVC架构


	* controller:控制层，写SpringMvc的action
	* dao：数据层，Hibernate对数据的操作
	* entity：实体类和相应的*.hbm.xml(hibernate的类配置文件)
	* servicesDao：业务Dao，对单笔Dao进行业务封装
	* utils：工具类


（4）数据库
   在Mysql中建一个MyTest数据库，表由Hibernate自动生成，但需要在User表中添加一个用户
（5）项目功能截图

登录界面：

系统主页面：


一、项目架构介绍及配置文件

1、后台包结构：                                                       2、前端包结构：

3、用到的jar包（34个，源码在我的github中）：                              

4、web.xml

<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
<display-name>员工管理系统</display-name>
  <welcome-file-list>

    <welcome-file>login.jsp</welcome-file>
  </welcome-file-list>

  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
          classpath:applicationContext.xml
          </param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
    <servlet-name>spring</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>spring</servlet-name>
    <url-pattern>*.action</url-pattern>
  </servlet-mapping>

</web-app>

5、applicationContext.xml   (Spring主配置文件，内含Hibernate配置)

<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

   <context:component-scan base-package="dao,servicesDao"></context:component-scan>
    <!-- 加载配置文件 -->
    <!-- <context:property-placeholder location="classpath:db.properties" /> -->

    <!-- 数据源，使用dbcp -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
        <!-- 指定连接数据库的驱动 -->
        <property name="driverClass" value="org.gjt.mm.mysql.Driver" />
        <!-- 指定连接数据库的URL -->
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/myTest" />
        <!-- 指定连接数据库的用户名 -->
        <property name="user" value="root" />
        <!-- 指定连接数据库的密码 -->
        <property name="password" value="" />
        <!-- 指定连接数据库连接池的最大连接数 -->
        <property name="maxPoolSize" value="4" />
        <!-- 指定连接数据库连接池的最小连接数 -->
        <property name="minPoolSize" value="1" />
        <!-- 指定连接数据库连接池的初始化连接数 -->
        <property name="initialPoolSize" value="1" />
        <!--最大空闲时间,1800秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 用它来解决mysql 8小时自动释放连接的问题 -->
        <property name="maxIdleTime" value="28000" />
    </bean>

     <!-- sqlSessinFactory -->
<!--     <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        加载mybatis的配置文件
        <property name="configLocation" value="classpath:SqlMapConfig.xml" />
        数据源
        <property name="dataSource" ref="dataSource" />
    </bean>

    mapper批量扫描，从mapper包中扫描出mapper接口，自动创建代理对象并且在spring容器中注册 遵循规范：将mapper.java和mapper.xml映射文件名称保持一致，且在一个目录
        中 自动扫描出来的mapper的bean的id为mapper类名（首字母小写）
    经过扫描后生成的代理bean实例是线程安全的
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        指定扫描的包名 如果扫描多个包，每个包中间使用半角逗号分隔
        <property name="basePackage" value="dao" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    </bean>

    <bean id="transactionManager"
        class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean> -->

    <bean id="sessionFactory"
        class="org.springframework.orm.hibernate3.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="hibernateProperties">
            <props>
                <!-- <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop> -->
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
            </props>
        </property>
        <property name="mappingResources">
            <list>
                <value>entity/User.hbm.xml</value>
                <value>entity/Employee.hbm.xml</value>
            </list>
        </property>
    </bean>

    <bean id="transactionManager"
        class="org.springframework.orm.hibernate3.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory" />
    </bean>

    <tx:advice id="transactionAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="add*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="append*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="insert*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="save*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="update*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="modify*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="edit*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="delete*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="remove*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="repair" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="delAndRepair" propagation="REQUIRED" rollback-for="Exception" />

            <tx:method name="get*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="select*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="find*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="load*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="search*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="datagrid*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="count" propagation="SUPPORTS" rollback-for="Exception" />
             <tx:method name="login*" propagation="SUPPORTS" rollback-for="Exception" />
            <tx:method name="*" propagation="SUPPORTS" rollback-for="Exception" />
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="transactionPointcut" expression="execution(* servicesDao..*(..))" />
        <aop:advisor pointcut-ref="transactionPointcut"
            advice-ref="transactionAdvice" />
    </aop:config>
</beans>


6、spring-servlet.xml   (SpringMVC配置文件)

<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
     xmlns:context="http://www.springframework.org/schema/context"
     xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
     <context:component-scan base-package="controller"/>
     <mvc:annotation-driven/>
     <bean
          class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/" />
          <property name="suffix" value=".jsp" />
     </bean>
     <mvc:default-servlet-handler />
</beans>


二、项目设计思路及关键代码
1、dao层：
  定义了一个接口，一个接口实现方法当做模板，其余的dao需继承模板dao

daoTemplate.java
package dao;

import java.util.List;

public interface daoTemplate<T> {
 public int save(T t)throws Exception;
 public int delete(T t)throws Exception;
 public int delete(Integer id)throws Exception;
 public int update(T t)throws Exception;
 public List<T> select()throws Exception;
 public T get(Integer id)throws Exception;
}


daoTemplateImpl.java
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


 
登录的代码：

@Repository
public class employeeDao extends daoTemplateImpl<Employee>{
   /**
    * 模糊查询
    * @param condition eg："张三"
    * @return List<Employee>
    */
public List<Employee> getBasic(String condition) {
   String hql="from "+claz.getName()+" where empname like :name ";
   Query query=getSession().createQuery(hql);
   query.setString("name", "%"+condition+"%");
   return query.list();
}
}

模糊查询的代码：

public List<Employee> getBasic(String condition) {
   //注意这里的empname是pojo类（实体类）的属性名
   String hql="from "+claz.getName()+" where empname like :name ";
   Query query=getSession().createQuery(hql);
   query.setString("name", "%"+condition+"%");
   return query.list();
}

注意：每个dao需要加上
@Repository注解

2、servicesDao： 
  注意：（1）要加上@Service注解
            （2）将employeeDao做为属性，加上 @Autowired，并且并且get、set方法
                     eg：
 @Autowired
 employeeDao empDao;

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


其实servicesDao也应该写一个模板dao，这里省略了

3、controller层
userController.java：
package controller;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.User;
import servicesDao.userServiceDao;
import utils.ImageUtil;

@Controller
public class userController {
 @Autowired
 userServiceDao useDao;
  @RequestMapping("/user_login")
   public String login(User u,String number,HttpSession session) throws Exception{
       String imageCode=(String)session.getAttribute("imageCode");
        if(imageCode.equals(number.toUpperCase())){
            User user=useDao.login(u);
             if(user!=null){
                 session.setAttribute("user", user);
                 return "redirect:/emp_list.action";   
             }

        }
       return "login";
   }
   @RequestMapping("/createValidCode")
   public OutputStream createImg(HttpServletResponse response,HttpSession session)throws Exception{

       // 1.调用工具类，生成验证码及图片
            Map<String, BufferedImage> imageMap = ImageUtil.createImage();
            // 2.从imageMap中取到验证码，并放入session
            String imageCode = imageMap.keySet().iterator().next();
            session.setAttribute("imageCode", imageCode.toUpperCase());
            // 3.从imageMap中取到图片，转为输入流
            BufferedImage image = imageMap.get(imageCode);
            OutputStream output=response.getOutputStream();
            ImageUtil.WriteOutputStream(image,output);           
            return output;
   }


public userServiceDao getUseDao() {
    return useDao;
}

public void setUseDao(userServiceDao useDao) {
    this.useDao = useDao;
}

}


employeeController.java:
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


注意：（1）加@Controller注解
         （2）加
                  @Autowired
                   employeeServiceDao employDao;//get、set方法
         （3）@RequestMapping("/toAddPage")    对应访问的地址为/toAddPage.action
         （4）Map models，可将里面的值传到页面中去显示
         （5）public String emp_add(Employee emp) throws Exception  可接收页面中 name为emp的对象
         （6）请求转发：redirect:/emp_list.action

4、工具类


三、前端代码
empList.jsp
<%@ page language="java" import="java.util.*,entity.Employee"
     pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>emplist</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
     <div id="wrap">
          <div id="top_content">
              <div id="header">
                   <div id="rightheader">
                        <p>
                             当前的用户：
                             ${sessionScope.user.username}<br />
                        </p>
                        <p>
                             <%-- 当前的用户：
                             <%=session.getAttribute("username") %>
                             <!--  el表达式 -->
                             ${sessionScope.user.username}<br /> --%>
                             <%@include file="head.jsp"%>
                             <br />
                        </p>
                   </div>
                   <div id="topheader">
                        <h1 id="title">
                             <!-- <a href="#">main</a> -->
                             <form action="emp_list.action" method="post">
                                  <input type="text" class="inputgri" name="sqlname"
                                      placeholder="请输入你要查询 的姓名" /> <input type="submit" class="button"
                                      value="查询" />
                             </form>
                             <table>
                                  <tr>
                                      <td><form action="emp_listAsc.action" method="post">
                                                <input type="submit" class="button" value="薪水升序排序">
                                           </form></td>
                                      <td><form action="emp_list.action" method="post">
                                                <input type="submit" class="button" value="薪水降序排序">
                                           </form></td>
                                  </tr>

                             </table>
                        </h1>
                   </div>
                   <div id="navigation"></div>
              </div>
              <div id="content">
                   <p id="whereami"></p>
                   <h1>Welcome!</h1>
                   <table class="table">
                        <tr class="table_header">
                             <td>ID</td>
                             <td>Name</td>
                             <td>Salary</td>
                             <td>Age</td>
                             <td>Operation</td>
                        </tr>

                        <%
                             List<Employee> employees = (List<Employee>) request
                                       .getAttribute("employees");
                             for (Employee e : employees) {
                        %>

                        <tr class="row1">
                             <td><%=e.getT_id()%></td>
                             <td><%=e.getEmpname()%></td>
                             <td><%=e.getEmpsalary()%></td>
                             <td><%=e.getEmpage()%></td>
                             <td><a href="emp_del.action?id=<%=e.getT_id()%>"
                                  onclick="return confirm('是否要删除<%=e.getEmpname()%>');">删除</a>&nbsp;<a
                                  href="emp_update.action?id=<%=e.getT_id()%>">修改</a></td>
                        </tr>

                        <%
                             }
                        %>
                   </table>
                   <p>
                        <input type="button" class="button" value="添加员工"
                             onclick="location='addEmp.jsp'" />
                   </p>
              </div>
          </div>
          <div id="footer">
              <div id="footer_bg">ABC@126.com</div>
          </div>
     </div>
</body>
</html>

四、github传送门

https://github.com/tangfuping/SSH_emp.git
       

