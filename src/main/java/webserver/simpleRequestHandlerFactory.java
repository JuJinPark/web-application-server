package webserver;

public class simpleRequestHandlerFactory implements RequestHandlerFactory{

	RequestManagable requestHandler;
	
	@Override
	public RequestManagable getHandler(String url) {
	
		if("/user/login".equals(url)) {
			requestHandler = new LoginHandler(url);
		}else if("/user/create".equals(url)) {
			requestHandler = new UserCreateHandler(url);
		}else if("/user/list".equals("")) {
			requestHandler = new UserListHandler(url);
		}else {
			requestHandler = new BasicHandler(url);
		}
			
		return requestHandler;
	}
	
	

}
