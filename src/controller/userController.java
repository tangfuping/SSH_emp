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
