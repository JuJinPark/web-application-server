package webserver;

public class UserListHandler implements RequestManagable {

	String url;
	UserListHandler(String url){
		this.url=url;
	}
		@Override
		public String getUrl() {
			// TODO Auto-generated method stub
			return url;
		}

}
