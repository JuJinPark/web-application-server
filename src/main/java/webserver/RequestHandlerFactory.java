package webserver;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public interface RequestHandlerFactory {
	RequestManagable getHandler(HttpRequest request,InputStream in) throws UnsupportedEncodingException;

	
	
	
}
