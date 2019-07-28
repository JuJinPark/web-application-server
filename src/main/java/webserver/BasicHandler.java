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

import util.IOUtils;

public class BasicHandler implements RequestManagable {

	HttpRequest request;	
BufferedReader inputstream;


	
	BasicHandler(HttpRequest request,InputStream in) throws UnsupportedEncodingException{
		this.request=request;
		this.inputstream=new BufferedReader(new InputStreamReader(in,"UTF-8"));
	}



	@Override
	public void response(DataOutputStream dos) {
		// TODO Auto-generated method stub
		getResourceSucessResponse(dos);
	}
	
	private void getResourceSucessResponse(DataOutputStream dos) {
		
		
		 try {
			 byte[] body=Files.readAllBytes(new File("./webapp"+request.getPath()).toPath());
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
    	.append(IOUtils.otherResposeHeaderMaker("Content-Type",getResponseContentType(requestedContentType())))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Length",body.length+""))
    	.append("\r\n");  
		return responseHeader.toString();
	}
	
	private String getResponseContentType(String contentType) {
		
	  	if("text/css".equals(contentType)) {
    		return "text/css";
    	}
	  	return "text/html;charset=utf-8";
		
	}
	
	private String requestedContentType() {
		return request.getHeader("Accept").split(",")[0];
	}
	
	

}
