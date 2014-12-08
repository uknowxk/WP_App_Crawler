/**
 * Download website page with httpcomponent 4.2.5
 */
package utility;
import java.io.*;

import javax.xml.ws.http.HTTPException;

import org.apache.http.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class DownloadPage {
	public String downloadPage(String url) throws Exception{
	String filePath = null;
	String pageSource = new String();
	HttpClient client = new DefaultHttpClient();
	HttpGet httpget = new HttpGet(url); 
	try{
		 HttpResponse response = client.execute(httpget);
		 int statusCode = response.getStatusLine().getStatusCode();
		 if(statusCode !=HttpStatus.SC_OK){
			 System.err.println("Method Failed: "+ response.getStatusLine());
		 }
		 HttpEntity entity = response.getEntity();
		 if(entity != null){
			 pageSource = EntityUtils.toString(entity);
		 }
	 } catch (HTTPException e){
		 System.out.println("Please check your provided http address!");
		 e.printStackTrace();
	 }catch (IOException e){
		 e.printStackTrace();
	 } finally{
		 client.getConnectionManager().shutdown();
	 }
	return pageSource;
	}
}
