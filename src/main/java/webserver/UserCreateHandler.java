package webserver;

public class UserCreateHandler implements RequestManagable {
	String url;
	UserCreateHandler(String url){
		this.url=url;
	}
		@Override
		public String getUrl() {
			// TODO Auto-generated method stub
			return url;
		}

}
