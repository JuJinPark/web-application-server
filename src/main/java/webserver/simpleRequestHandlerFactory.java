package webserver;

import java.io.BufferedReader;
import java.util.Map;

public class simpleRequestHandlerFactory implements RequestHandlerFactory{

	RequestManagable requestHandler;
	
	@Override
	public RequestManagable getHandler(Map<String,String> headerInfo,BufferedReader inputstream) {
	
		if("/user/login".equals(headerInfo.get("url"))) {
			requestHandler = new LoginHandler(headerInfo,inputstream);
		}else if("/user/create".equals(headerInfo.get("url"))) {
			requestHandler = new UserCreateHandler(headerInfo,inputstream);
		}else if("/user/list".equals(headerInfo.get("url"))) {
			requestHandler = new UserListHandler(headerInfo,inputstream);
		}else {
			requestHandler = new BasicHandler(headerInfo,inputstream);
		}
			
		return requestHandler;
	}


	

}
