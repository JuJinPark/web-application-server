package webserver;

public class LoginHandler implements RequestManagable {
String url;
	

LoginHandler(String url){
	this.url=url;
}
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

}
