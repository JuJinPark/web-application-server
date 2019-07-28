package webserver;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;



public class CreateHandlerTest {
	 @Test
   public void checkHandler() {
	 RequestHandlerFactory factory = new simpleRequestHandlerFactory();
//	 RequestManagable requestHandler=factory.getHandler("/user/login");
//	 
//	 assertEquals(requestHandler.getUrl(),"/user/login");
//		 
	    }
	 
	 
	 @Test
	public void loginHandelrsetUser() {
	 RequestHandlerFactory factory = new simpleRequestHandlerFactory();
	 Map<String,String> headerInfo= new HashMap();


	 LoginHandler myLoginHandler = new LoginHandler(headerInfo,null);
	 myLoginHandler.setUserInfo("userId=wnwls&password=1216");
	 assertEquals(myLoginHandler.userinfo.get("userId"),"wnwls");
	 assertEquals(myLoginHandler.userinfo.get("password"),"1216");
	

		 
	    }
	 
	 
	 @Test
		public void checkPass() {
		 LoginHandler myLoginHandler = new LoginHandler(null,null);
		 User member = new User("wnwls","1216","jujin","ss@dd");
		assertTrue(myLoginHandler.checkPassword(member,"1216"));
	 }
	 
	 @Test
	public void isSaved() {
		 UserCreateHandler myUsderCreateHandler = new UserCreateHandler(null,null);
		 User member = new User("wnwls","1216","jujin","ss@dd");
		 String url="userId=wnwls&password=1216&name=jujin&email=ss@dd";
		 Map<String,String> userinfo=HttpRequestUtils.parseQueryString(url);
		 User member2=myUsderCreateHandler.createUser(userinfo);
		 myUsderCreateHandler.saveUser(member);
		
		assertEquals(member.toString(),DataBase.findUserById("wnwls").toString());
		
		
	 }
	
		
}
