package webserver;

public class BasicHandler implements RequestManagable {

	String url;
	BasicHandler(String url){
		this.url=url;
	}
		@Override
		public String getUrl() {
			// TODO Auto-generated method stub
			return url;
		}

}
