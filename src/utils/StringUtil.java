package utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtil {
	public static String datePattern="yyyy-MM-dd kk:mm:ss";
	public static SimpleDateFormat dateFormate=new SimpleDateFormat(datePattern);
	//如果字符串为null或""返回true
 public static boolean isEmpty(String name){
	  if(name==null||"".equals(name))
		  return true;
	  return false;
 }
 
 public static boolean isNotEmpty(String name){
	 return isEmpty(name)!=true;
 }
 public static Date StringToDate(String date) throws Exception{
	 if(isNotEmpty(date)){
		date=date.replaceAll("/", "-").replaceAll(".", "-").replaceAll("\\","-");
		synchronized (dateFormate) {
		 return	dateFormate.parse(date);
		}
	 }
	 return null;
 }
 
 public static String DateToString(Date date){
	 if(date!=null){
		 synchronized (dateFormate) {
			return dateFormate.format(date);
		}
	 }
	 return null;
 }
 


 

 
}
