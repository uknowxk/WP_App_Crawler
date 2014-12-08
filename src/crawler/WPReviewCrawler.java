package crawler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import parser.ReviewParser;

public class WPReviewCrawler {
	public static void main(String[] args) throws IOException{
		ReviewParser rp = new ReviewParser();
		//outputpath
		String outputpath = new String("E:\\AppReviews_WP\\");
		File seedsfile = new File("WPApp urls");
		int appnum=0;
		long starttime = System.currentTimeMillis();
		for(File f:seedsfile.listFiles()){
			FileInputStream fs = new FileInputStream(f.getAbsolutePath());
			InputStreamReader isr = new InputStreamReader(fs);
			BufferedReader bfr = new BufferedReader(isr);
			String urlline = new String();
			while((urlline = bfr.readLine())!=null){
				appnum++;
				String[] sline = urlline.split("/");
				//string behind the last / in url is app id
				String appid = sline[sline.length-1];
				File appreview = new File(outputpath+appid+".txt");
				FileOutputStream fos = new FileOutputStream(appreview);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osw);
				rp.parseReviews(urlline, bw);
				bw.close();
				osw.close();
				fos.close();
				if(appnum%100==0){
					long currenttime = System.currentTimeMillis();
					System.out.println("Processed "+ appnum + " apps. Consuming "+ (currenttime-starttime)+" ms.");
				}
			}
			
		}
	}
}
