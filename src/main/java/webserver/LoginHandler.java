package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Map;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class LoginHandler implements RequestManagable {
//String url;
	HttpRequest request;	
BufferedReader inputstream;
Map<String,String> userinfo;
//Map<String, User> users;


LoginHandler(HttpRequest request,InputStream in) throws UnsupportedEncodingException{
	this.request=request;
this.inputstream=new BufferedReader(new InputStreamReader(in,"UTF-8"));
}

	@Override
	public void response(DataOutputStream dos){
		// TODO Auto-generated method stub
	
		User loginedUser=getUser(request.getParameter("userId"));
		if(checkPassword(loginedUser,request.getParameter("password"))) {
			loginSucessResponse(dos);
			return;
		}
    
		
		loginFailedResponse(dos);
		
	}
	
	
	
	private void loginFailedResponse(DataOutputStream dos) {
		 try {
			 byte[] body=Files.readAllBytes(new File("./webapp"+"/user/login_failed.html").toPath());
				dos.writeBytes(createLoginFailedHeader(body));
				dos.write(body, 0, body.length);
		        dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
	}
	


	private void loginSucessResponse(DataOutputStream dos) {
		
		
		 try {
			dos.writeBytes(createLoginSuccessHeader());
//			   dos.write(body, 0, body.length);
	            dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	private String createLoginSuccessHeader() {
		StringBuilder responseHeader=new StringBuilder();
		responseHeader.append(IOUtils.StatusHeaderMaker("302","Redirect"))
    	.append(IOUtils.otherResposeHeaderMaker("Location","/index.html"))
    	.append(IOUtils.otherResposeHeaderMaker("Set-Cookie","logined=true; Path=/"))
     	.append("\r\n");
		return responseHeader.toString();
	}
	
	private String createLoginFailedHeader(byte[] body) {
		StringBuilder responseHeader=new StringBuilder();
    	
    
    	responseHeader.append(IOUtils.StatusHeaderMaker("200","OK"))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
    	.append(IOUtils.otherResposeHeaderMaker("Set-Cookie","logined=false; Path=/"))
    	.append("\r\n");	
    	
    	return responseHeader.toString();
    
//    		
	}



//	public void setUserInfo(String body){
//		userinfo=HttpRequestUtils.parseQueryString(body);
//	}
	
//	private String getBody() {
//		try {
//			return IOUtils.readData(inputstream,Integer.parseInt(headerInfo.get("Content-Length")));
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}




	public boolean checkPassword(User member,String passWord) {

	    	if(member==null) {
	    		return false;
	    	}
	    
	    	
	    	if(member.getPassword().equals(passWord)) {
	    		
	    		return true;
	    	}
	    	return false;
	    	
	   
		
	}
	
	private User getUser(String userId) {
		return DataBase.findUserById(userId);
	}

}
