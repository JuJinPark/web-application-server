package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    
//    public static DataBase users;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
        	DataOutputStream dos = new DataOutputStream(out);
        	//BufferedReader rd= new BufferedReader(new InputStreamReader(in,"UTF-8"));
        	HttpRequest request=new HttpRequest(in);
        	//헤더정보 받아오기
        	//Map<String,String> headerInfo= readHeader(rd);
         	
        	

        	RequestHandlerFactory requestHandlerFactory = new simpleRequestHandlerFactory();
        	
        	RequestManagable requestHandler=requestHandlerFactory.getHandler(request,in);
        	
        	requestHandler.response(dos);
        	
//            byte[] body = null;
//            StringBuilder responseHeader=new StringBuilder();
            

            	
            	/*if("/user/login".equals(headerInfo.get("url"))) {
            		
            	//로그인 요청한 아이디와 비밀번호 저장
            	String content=IOUtils.readData(rd,Integer.parseInt(headerInfo.get("Content-Length")));
            	Map<String,String> userinfo=HttpRequestUtils.parseQueryString(content);

            	
            	
            	//맞는 아이디와 비밀번호인지 체크
            	if(checkLogin(userinfo.get("userId"),userinfo.get("password"))) {
            		//맞을시
            		//쿠키값 주고 index로 리다이렉트
            		//응답헤더 작성
              		responseHeader.append(IOUtils.StatusHeaderMaker("302","Redirect"))
                	.append(IOUtils.otherResposeHeaderMaker("Location","/index.html"))
                	.append(IOUtils.otherResposeHeaderMaker("Set-Cookie","logined=true; Path=/"))
                 	.append("\r\n");
            		 dos.writeBytes(responseHeader.toString());
            		return;
            	}
            	//틀릴시
            	//
            	body=Files.readAllBytes(new File("./webapp"+"/user/login_failed.html").toPath());
            	//응답헤더 작성
            	responseHeader.append(IOUtils.StatusHeaderMaker("200","OK"))
            	.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"))
            	.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
            	.append(IOUtils.otherResposeHeaderMaker("Set-Cookie","logined=false; Path=/"))
            	.append("\r\n");	
            	dos.writeBytes(responseHeader.toString());
            	responseBody(dos, body);
            		
            
            		
            	}else if("/user/create".equals(headerInfo.get("url"))) {
            		// 회원가입할 정보 받아오기
            		String content=IOUtils.readData(rd,Integer.parseInt(headerInfo.get("Content-Length")));
            		//정보 저장
            		saveUser(content);
            		//응답헤더 작성
            		responseHeader.append(IOUtils.StatusHeaderMaker("302","Redirect"))
                	.append(IOUtils.otherResposeHeaderMaker("Location","/index.html"))
                 	.append("\r\n");
            		 dos.writeBytes(responseHeader.toString());
                	
            	}else if("/user/list".equals(headerInfo.get("url"))) {
            		
            		Map<String,String>cookieVal= HttpRequestUtils.parseCookies(headerInfo.get("Cookie"));
            		
            		
            		boolean islLogined;
            		if(cookieVal.get("logined")==null) {
            			islLogined=false;
            		}
            		islLogined =Boolean.parseBoolean(cookieVal.get("logined"));
            		
            		
            	
            	
            		if(islLogined) {
            			Collection<User>  userList=DataBase.findAll();
            		StringBuilder html=new StringBuilder();
            		html.append("<table border='1'>");
            			for(User user:userList) {
            				html.append("<tr>");
            				
            				html.append("<td>"+user.getUserId()+"</td>");
            				html.append("<td>"+user.getName()+"</td>");
            				html.append("<td>"+user.getEmail()+"</td>");
            				html.append("</tr>");
            			}
            			html.append("</table>");
            			
            		body=html.toString().getBytes();
            			
            			
             		}else {
             			
             			body=Files.readAllBytes(new File("./webapp"+"/user/login.html").toPath());
                    	//응답헤더 작성
                    	
             			
             		}
            		
            		
            		responseHeader.append(IOUtils.StatusHeaderMaker("200","OK"))
                	.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"))
                	.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
                	.append("\r\n");  
                	
                	 dos.writeBytes(responseHeader.toString());
                	 responseBody(dos, body);
         			
            		
            		
            		
            	}
            	
            	
            	
            	
            	
           else {
            	
            
            	//각종 html 요청 
            	// 요청 html읽어오기
            	body=Files.readAllBytes(new File("./webapp"+headerInfo.get("url")).toPath());
            	//응답헤더 작성
            	responseHeader.append(IOUtils.StatusHeaderMaker("200","OK"));
            	if("text/css".equals(headerInfo.get("Accept").split(",")[0])) {
            		responseHeader.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/css"));
            	}else {
            		responseHeader.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"));
            	}
    
            	
            	responseHeader.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
            	.append("\r\n");      	
            	 dos.writeBytes(responseHeader.toString());
            	 responseBody(dos, body);
            }
            */
           
            
            
            
         
        
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private boolean checkLogin(String id,String pwd) {
    	User member=DataBase.findUserById(id);
    	if(member==null) {
    		return false;
    	}
    	log.debug("Datbase data {}",DataBase.findAll().size());
    	log.debug("requested id {},requested pass {}",id,pwd);
    	
    	if(member.getPassword().equals(pwd)) {
    		
    		return true;
    	}
    	return false;
    	
    }
    private void saveUser(String url) {
    
    	Map<String,String> userinfo=HttpRequestUtils.parseQueryString(url);
    	User user=new User(userinfo.get("userId"),userinfo.get("password"),userinfo.get("name"),userinfo.get("email"));
    	DataBase.addUser(user);
    	
    	log.debug("userinfo - id :{}, passward:{}, name:{}, email:{}",user.getUserId(),user.getPassword(),user.getName(),user.getEmail());
    }

    
    private Map<String,String> readHeader(BufferedReader rd) throws IOException {
    	
    	Map<String,String> map=new HashMap();
	
     	
    	
     	String data=rd.readLine();
     	if(data==null) {
     		return null;
     	}
     	log.debug("headers : {}",data);
     	
     	
     	String[] header = data.split(" ");
     	map.put("requestType",header[0]);
     	map.put("url",header[1]);
     	map.put("httpversion",header[2]);
     	
     	do {
     		data=rd.readLine();
         	log.debug("headers : {}",data);
         	if("".equals(data)) break;
         	String[] info=data.split(":");
	  		map.put(info[0].trim(),info[1].trim());
     	} while(!"".equals(data));
     	
     	
     	return map;
    }
  


    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    

}
