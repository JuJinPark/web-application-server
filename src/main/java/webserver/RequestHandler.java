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
import java.util.HashMap;
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
    
    public static DataBase users;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
        	
        	BufferedReader rd= new BufferedReader(new InputStreamReader(in,"UTF-8"));
        	Map<String,String> Http= new HashMap();
         	readHeader(rd,Http);
//         	if(header==null) {
//         		return;
//         	}
//        
//         	String requestType=header[0];
//         	String url=header[1];
//            
        	DataOutputStream dos = new DataOutputStream(out);
            byte[] body = null;
            
            if("POST".equals(Http.get("requestType"))) {
            	
            	if("/user/login".equals(Http.get("url"))) {
            		
            		
            		response200HeaderForLogin(dos,login(Http.get("userId"),Http.get("password")));
            		
            		
            		if(login(Http.get("userId"),Http.get("password"))) {
            			//일단 200응답하고 쿠킵갑으로 어디로 갈지 판단해야함
            			response302Header(dos,"/index.html");
            		}else {
            			response302Header(dos,"/user/login_failed.html");
            		}
            		
            	}else if("/user/create".equals(Http.get("url"))) {
            		String content=IOUtils.readData(rd,Integer.parseInt(Http.get("Content-Length")));
                	
            		usercreatehandler(content);
                	response302Header(dos,"/index.html");
            	}
            	
            	
            	
            	
            	
            }else {
            	body=Files.readAllBytes(new File("./webapp"+Http.get("url")).toPath());
            	
            	response200Header(dos, body.length);
            }
            
            responseBody(dos, body);
            
         
        
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private boolean login(String id,String pwd) {
    	User member=users.findUserById(id);
//    	if(users.findUserById(id)==null) {
//    		return false;
//    	}
    	if(member != null && member.getPassword().equals(pwd)) {
    		return true;
    	}
    	return false;
    	
    }
    private void usercreatehandler(String url) {
    
    	Map<String,String> userinfo=HttpRequestUtils.parseQueryString(url);
    	User user=new User(userinfo.get("userId"),userinfo.get("password"),userinfo.get("name"),userinfo.get("email"));
    	users.addUser(user);
    	
    	log.debug("userinfo - id :{}, passward:{}, name:{}, email:{}",user.getUserId(),user.getPassword(),user.getName(),user.getEmail());
    }
//    private String readBody(BufferedReader rd,int Contentlength) throws IOException {
//    	
//    }
    
    private void readHeader(BufferedReader rd,Map<String,String> map) throws IOException {
    	
    	
	
     	
    	
     	String data=rd.readLine();
     	if(data==null) {
     		return;
     	}
     	log.debug("headers : {}",data);
     	
     	
     	String[] header = data.split(" ");
     	map.put("requestType",header[0]);
     	map.put("url",header[1]);
     	map.put("httpversion",header[2]);
     	
     	do {
     		data=rd.readLine();
         	log.debug("headers : {}",data);
         	if("".equals(data)) return;
         	String[] info=data.split(":");
	  		map.put(info[0].trim(),info[1].trim());
     	} while(!"".equals(data));
//     	{
//    	  
//    	   	
//    	  		
//    	  		
//    	  		
//    	  	 	
//    	  	
//    	  	 	data=rd.readLine();
//    	  	 	log.debug("headers : {}",data);
//    	    	
//    	   }
     	
     	
     	
    }
    
 
    
   

    private void response200HeaderForLogin(DataOutputStream dos,Boolean login) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Set-Cookie: logined=" + login.toString() + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: "+url+"\r\n");

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
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
