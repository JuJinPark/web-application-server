package webserver;

import java.util.Map;

public class simpleRequestHandlerFactory implements RequestHandlerFactory{

	RequestManagable requestHandler;
	
	@Override
	public RequestManagable getHandler(Map<String,String> headerInfo) {
	
		if("/user/login".equals(headerInfo.get("url"))) {
			requestHandler = new LoginHandler(headerInfo);
		}else if("/user/create".equals(headerInfo.get("url"))) {
			requestHandler = new UserCreateHandler(headerInfo);
		}else if("/user/list".equals(headerInfo.get("url"))) {
			requestHandler = new UserListHandler(headerInfo);
		}else {
			requestHandler = new BasicHandler(headerInfo);
		}
			
		return requestHandler;
	}
	
	

}
