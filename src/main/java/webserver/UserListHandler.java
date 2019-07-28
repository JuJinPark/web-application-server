package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class UserListHandler implements RequestManagable {

	HttpRequest request;	
BufferedReader inputstream;

	


	
	UserListHandler(HttpRequest request,InputStream in) throws UnsupportedEncodingException{
		this.request=request;
		this.inputstream=new BufferedReader(new InputStreamReader(in,"UTF-8"));
	}





	@Override
	public void response(DataOutputStream dos) {
		// TODO Auto-generated method stub
		
		if(isLogined()) {
		
			getUserSucessResponse(dos);
			return;
		}
		
	getUserFailedResponse(dos);
		
	}
	private Boolean isLogined() {
		Map<String,String>cookieVal= HttpRequestUtils.parseCookies(request.getHeader("Cookie"));
	
		if(cookieVal.get("logined")==null) {
			return false;
		}
		return Boolean.parseBoolean(cookieVal.get("logined"));
	}
	
	private void getUserSucessResponse(DataOutputStream dos) {
		 try {
			 byte[] body=getUserList();
				dos.writeBytes(createHeader(body));
				dos.write(body, 0, body.length);
		        dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
	}
	





	private void getUserFailedResponse(DataOutputStream dos) {
		
		
		 try {
			 byte[] body=Files.readAllBytes(new File("./webapp"+"/user/login.html").toPath());
				dos.writeBytes(createHeader(body));
				dos.write(body, 0, body.length);
		        dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	private String createHeader(byte[] body) {
		StringBuilder responseHeader=new StringBuilder();
		responseHeader.append(IOUtils.StatusHeaderMaker("200","OK"))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
    	.append("\r\n");  
		return responseHeader.toString();
	}
	
	
	private byte[] getUserList() {
		
			
		return createUserList().getBytes();
	}
	
	private String createUserList() {
		Collection<User>  userList=DataBase.findAll();
		StringBuilder html=new StringBuilder();
		html.append("<table border='1'>");
			for(User user:userList) {
				html.append("<tr>");
				
				html.append("<td>"+user.getUserId()+"</td>");
				html.append("<td>"+user.getName()+"</td>");
				html.append("<td>"+user.getEmail()+"</td>");
				html.append("</tr>");
				//System.out.println(user.getName());
			}
			html.append("</table>");
		
			
			
			return html.toString();
	}



}
