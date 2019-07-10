package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class UserCreateHandler implements RequestManagable {

	Map<String, String> headerInfo;	
	BufferedReader inputstream;
	
//	
//	String content=IOUtils.readData(rd,Integer.parseInt(headerInfo.get("Content-Length")));
//	//정보 저장
//	saveUser(content);
//	//응답헤더 작성
//	responseHeader.append(IOUtils.StatusHeaderMaker("302","Redirect"))
//	.append(IOUtils.otherResposeHeaderMaker("Location","/index.html"))
// 	.append("\r\n");
//	 dos.writeBytes(responseHeader.toString());
	
//    private void saveUser(String url) {
//        
//    	Map<String,String> userinfo=HttpRequestUtils.parseQueryString(url);
//    	User user=new User(userinfo.get("userId"),userinfo.get("password"),userinfo.get("name"),userinfo.get("email"));
//    	DataBase.addUser(user);
//    	
//    	log.debug("userinfo - id :{}, passward:{}, name:{}, email:{}",user.getUserId(),user.getPassword(),user.getName(),user.getEmail());
//    }

	UserCreateHandler(Map<String,String> headerInfo,BufferedReader inputstream){
		this.headerInfo=headerInfo;
		this.inputstream=inputstream;
	}
	
	@Override
	public void response(DataOutputStream dos){
		// TODO Auto-generated method stub
		String url=getBody();
		User member=createUser(HttpRequestUtils.parseQueryString(url));
		DataBase.addUser(member);
//		setUserInfo(getBody());
//		
//		if(checkPassword(getUser(userinfo.get("userId")),userinfo.get("password"))) {
//			loginSucessResponse(dos);
//			return;
//		}
//    
//		
//		loginFailedResponse(dos);
		
	}
	
	public User createUser(Map<String,String> userinfo) {
		return new User(userinfo.get("userId"),userinfo.get("password"),userinfo.get("name"),userinfo.get("email"));
	}
	
	public void saveUser(User member) {
		DataBase.addUser(member);
	}
	
	
	private String getBody() {
		try {
			return IOUtils.readData(inputstream,Integer.parseInt(headerInfo.get("Content-Length")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//	LoginHandler(Map<String,String> headerInfo,BufferedReader inputstream){
//		this.headerInfo=headerInfo;
//	this.inputstream=inputstream;
//	}


}
