package util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }
    
    
    @Test
    public void headermaker() throws Exception{
    StringBuilder result=new StringBuilder();
    	result.append(IOUtils.StatusHeaderMaker("200","OK"))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Type","text/html;charset=utf-8"))
    	.append(IOUtils.otherResposeHeaderMaker("Content-Length","8"))
    	.append("\r\n");
    	
    	assertEquals(result.toString(),"HTTP/1.1 200 OK \r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: 8\r\n\r\n");	
    	
    }
}
