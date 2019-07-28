package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {
	private BufferedReader rd;
	private String method;
	private String path;
	private String version;
	private Map<String,String> header = new HashMap();
	private Map<String,String> parameters  = new HashMap();
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	public HttpRequest(InputStream in) throws IOException{
		this.rd=new BufferedReader(new InputStreamReader(in,"UTF-8"));
		readData();
		
	}
	private void readData() throws IOException {
		String data=rd.readLine();
     	if(data==null) {
     		return;
     	}
     	log.debug("headers : {}",data);
     	
     	
     	String[] header = data.split(" ");
     	method=header[0];
     	path=setParamterAndgetPath(header[1]);
     	
     
     	version=header[2];
   
     	
     	do {
     		data=rd.readLine();
         	log.debug("headers : {}",data);
         	if("".equals(data)) break;
         	createHeader(data);
     	} while(!"".equals(data));
     	createBody();
	}
	private String setParamterAndgetPath(String tmpPath) {
		int urlParametsIdx=tmpPath.indexOf("?");
		
		if(urlParametsIdx>-1) {
			seturlParamaters(tmpPath.substring(urlParametsIdx+1));
			return tmpPath.substring(0,urlParametsIdx);
		}
		
		return tmpPath;
	}
	
	private void seturlParamaters(String paramtersInString) {
	
			//String paramtersInString=path.substring(urlParametsIdx+1);
			
			parameters.putAll(HttpRequestUtils.parseQueryString(paramtersInString));
		
		
	}
	
	private void createHeader(String data) {
		String[] info=data.split(":");
		header.put(info[0].trim(),info[1].trim());
		
	}
	private void createBody() throws NumberFormatException, IOException {
		String lengthInString=header.get("Content-Length");
		if(lengthInString!=null) {
			
			String paramtersInString=IOUtils.readData(rd,Integer.parseInt(lengthInString));
			parameters.putAll(HttpRequestUtils.parseQueryString(paramtersInString));
			
		}
		
		
	}
	

	public String getMethod() {
		
		return method;
	}

	public String getPath() {
		
		return path;
	}
	public String getVersion() {
		return version;
	}

	public String getHeader(String fieldName) {
		// TODO Auto-generated method stub
		return header.get(fieldName);
	}

	public String getParameter(String parameterName) {
		// TODO Auto-generated method stub
		return parameters.get(parameterName);
	}
	
	
	
	

}
