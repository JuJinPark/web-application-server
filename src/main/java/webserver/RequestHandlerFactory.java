package webserver;

public interface RequestHandlerFactory {
	RequestManagable getHandler(String url);
}
