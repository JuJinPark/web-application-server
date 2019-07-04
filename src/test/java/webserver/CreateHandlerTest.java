package webserver;


import static org.junit.Assert.assertEquals;




import org.junit.Test;



public class CreateHandlerTest {
	 @Test
	    public void parseQueryString() {
	 RequestHandlerFactory factory = new simpleRequestHandlerFactory();
	 RequestManagable requestHandler=factory.getHandler("/user/login");
	 
	 assertEquals(requestHandler.getUrl(),"/user/login");
		 
	    }
}
