/**
 * Crawl WP app information
 */
package crawler;
import parser.ContentParserWP;
import java.io.*;

import utility.DownloadPage;

public class WPAppCrawler {
	public static void main(String[] args) throws Exception {
		ContentParserWP contentWP = new ContentParserWP();
		DownloadPage dp = new DownloadPage();
		File seedsfile = new File("WPApp urls");
		String outpath = new String("WindowsPhoneAppDataset.txt");
		FileOutputStream fos = new FileOutputStream(outpath);
		OutputStreamWriter osw= new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		//count app number
		int appnum = 0;
		//time statistics
		long starttime = System.currentTimeMillis();
		for(File f:seedsfile.listFiles()){
			FileInputStream fs = new FileInputStream(f.getAbsolutePath());
			InputStreamReader isr = new InputStreamReader(fs);
			BufferedReader bfr = new BufferedReader(isr);
			String urlline = new String();
			while((urlline=bfr.readLine())!=null){
				String html;
				try {
					html = dp.downloadPage(urlline);
					String[] appinfo = contentWP.parseRequiredField(html);
					//use "##" to separate each field
					for(int i=0;i<13;i++){
						bw.write(appinfo[i]+"##");
					}
					bw.write("\r");
					appnum++;
					if(appnum%100==0){
						long midtime = System.currentTimeMillis();
						System.out.println("Crawled "+appnum+" apps. Using "+(midtime-starttime)+" ms.");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bfr.close();
			isr.close();
			fs.close();
		}
		long endtime = System.currentTimeMillis();
		System.out.println("Total "+appnum+" apps. Using "+(endtime-starttime)+" ms.");
		bw.close();
		osw.close();
		fos.close();
	}
}
