package webserver;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class LoginHandler implements RequestManagable {
String url;
Map<String, String> headerInfo;	
Map<String, User> users;

LoginHandler(Map<String, String> headerInfo){
	this.headerInfo=headerInfo;
}


//	if("/user/login".equals(headerInfo.get("url"))) {
//		
//    	//로그인 요청한 아이디와 비밀번호 저장
//    	String content=IOUtils.readData(rd,Integer.parseInt(headerInfo.get("Content-Length")));
//    	Map<String,String> userinfo=HttpRequestUtils.parseQueryString(content);
//
//    	
//    	
//    	//맞는 아이디와 비밀번호인지 체크
//    	if(checkLogin(userinfo.get("userId"),userinfo.get("password"))) {
//    		//맞을시
//    		//쿠키값 주고 index로 리다이렉트
//    		//응답헤더 작성
//      		responseHeader.append(IOUtils.StatusHeaderMaker("302","Redirect"))
//        	.append(IOUtils.otherResposeHeaderMaker("Location","/index.html"))
//        	.append(IOUtils.otherResposeHeaderMaker("Set-Cookie","logined=true; Path=/"))
//         	.append("\r\n");
//    		 dos.writeBytes(responseHeader.toString());
//    		return;
//    	}
//    	//틀릴시
//    	//
//    	body=Files.readAllBytes(new File("./webapp"+"/user/login_failed.html").toPath());
//    	//응답헤더 작성
//    	responseHeader.append(IOUtils.StatusHeaderMaker("200","OK"))
//    	.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"))
//    	.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
//    	.append(IOUtils.otherResposeHeaderMaker("Set-Cookie","logined=false; Path=/"))
//    	.append("\r\n");	
//    	dos.writeBytes(responseHeader.toString());
//    	responseBody(dos, body);
//    		
//    
//    		
//    	}



	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}



	@Override
	public void response() {
		// TODO Auto-generated method stub
		
	}



	public String createHeader() {
		
		return null;
	}
	
	private boolean checkLogin(String userId,String passWord) {
		
		 
	  
	    	User member=DataBase.findUserById(userId);
	    	if(member==null) {
	    		return false;
	    	}
	    
	    	
	    	if(member.getPassword().equals(passWord)) {
	    		
	    		return true;
	    	}
	    	return false;
	    	
	   
		
	}
	
	private User get

}
