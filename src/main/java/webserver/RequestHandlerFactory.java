package webserver;

import java.util.Map;

public interface RequestHandlerFactory {
	RequestManagable getHandler(Map<String, String> headerInfo);

	
	
	
}
