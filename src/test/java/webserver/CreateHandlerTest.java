package webserver;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



public class CreateHandlerTest {
	 @Test
   public void checkHandler() {
	 RequestHandlerFactory factory = new simpleRequestHandlerFactory();
	 RequestManagable requestHandler=factory.getHandler("/user/login");
	 
	 assertEquals(requestHandler.getUrl(),"/user/login");
		 
	    }
	 
	 
	 @Test
	public void loginHandelrCheckBody() {
	 RequestHandlerFactory factory = new simpleRequestHandlerFactory();
	 Map<String,String> headerInfo= new HashMap();
	 RequestManagable requestHandler=factory.getHandler(headerInfo);
	 
	// requestHandler.createResponseHeader();
	 //requestHandler.createResponseBody();
	
	 
	 LoginHandler myLoginHandler = new LoginHandler(headerInfo);
	 assertEquals(requestHandler.getUrl(),"/user/login");
	 
	 assertEquals(myLoginHandler.createHeader(),"HTTP/1.1 200 OK \r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: 8\r\n\r\n");
	 requestHandler.response();
	 
	 assertEquals(requestHandler.getUrl(),"/user/login");
		 
	    }
}
