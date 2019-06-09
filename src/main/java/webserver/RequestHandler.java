package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
        	
       
        
        	String url=findRequestedURL(in);
            
            
            DataOutputStream dos = new DataOutputStream(out);
            
            byte[] body = Files.readAllBytes(new File("./webapp"+url).toPath());
       
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private String findRequestedURL(InputStream in) throws IOException {
     	
     	String url;
     	BufferedReader rd= new BufferedReader(new InputStreamReader(in,"UTF-8"));
    	
     	String data=rd.readLine();
     	if(data==null) {
     		return null;
     	}
     	url=data.split(" ")[1];
     	
     	
     	
    	while(!"".equals(data)) {
   	log.debug("headers : {}",data);
  		data=rd.readLine();
    	
    	}
    	return url;
    	
    	
    }
    
    private void checkinput2(InputStream in) throws IOException {
     	
     	
     	BufferedReader rd= new BufferedReader(new InputStreamReader(in,"UTF-8"));
     	StringBuilder response= new StringBuilder();
     	int data=rd.read();
     	if(data==-1) {
     		return;
     	}
     	
     	
    	while(data!=-1) {
//   	log.debug("headers : {}",data);
  		data=rd.read();
  		response.append( (char)data) ;  
    	
    	}
    	System.out.print(response);
    	
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
