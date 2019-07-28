package webserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class simpleRequestHandlerFactory implements RequestHandlerFactory{

	RequestManagable requestHandler;
	
	@Override
	public RequestManagable getHandler(HttpRequest request,InputStream in) throws UnsupportedEncodingException {
	
		if("/user/login".equals(request.getPath())) {
			requestHandler = new LoginHandler(request,in);
		}else if("/user/create".equals(request.getPath())) {
			requestHandler = new UserCreateHandler(request,in);
		}else if("/user/list".equals(request.getPath())) {
			requestHandler = new UserListHandler(request,in);
		}else {
			requestHandler = new BasicHandler(request,in);
		}
			
		return requestHandler;
	}


	

}
