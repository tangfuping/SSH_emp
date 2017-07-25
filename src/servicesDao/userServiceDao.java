package servicesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.userDao;
import entity.User;
@Service
public class userServiceDao {
   @Autowired
   userDao userDao;
	
	public User login(User u)throws Exception{
		return userDao.login(u);
	}

	public userDao getUserDao() {
		return userDao;
	}

	public void setUserDao(userDao userDao) {
		this.userDao = userDao;
	}
	
}
