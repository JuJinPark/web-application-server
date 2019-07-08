package webserver;

import java.io.BufferedReader;
import java.util.Map;

public interface RequestHandlerFactory {
	RequestManagable getHandler(Map<String, String> headerInfo,BufferedReader inputstream);

	
	
	
}
