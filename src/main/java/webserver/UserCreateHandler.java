package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class UserCreateHandler implements RequestManagable {

	HttpRequest request;	
BufferedReader inputstream;


	UserCreateHandler(HttpRequest request,InputStream in) throws UnsupportedEncodingException{
		this.request=request;
		this.inputstream=new BufferedReader(new InputStreamReader(in,"UTF-8"));
	}
	
	@Override
	public void response(DataOutputStream dos){

		
		User member=createUser();
		saveUser(member);
		createSucessResponse(dos);

		
	}
	
	public User createUser() {
		
		String userid;
		String pwd;
		String name;
		String email;
		
		try {
			userid = URLDecoder.decode(request.getParameter("userId"), "UTF-8");
			pwd = URLDecoder.decode(request.getParameter("password"), "UTF-8");
			name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
			email = URLDecoder.decode(request.getParameter("email"), "UTF-8");
			return new User(userid,pwd,name,email);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		
		
	}
	
	public void saveUser(User member) {
		DataBase.addUser(member);
	}
	
	

	
	private void createSucessResponse(DataOutputStream dos) {
		
		
		 try {
			dos.writeBytes(createSuccessHeader());
	            dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	private String createSuccessHeader() {
		StringBuilder responseHeader=new StringBuilder();
		responseHeader.append(IOUtils.StatusHeaderMaker("302","Redirect"))
    	.append(IOUtils.otherResposeHeaderMaker("Location","/index.html"))
     	.append("\r\n");
		return responseHeader.toString();
	}


}
