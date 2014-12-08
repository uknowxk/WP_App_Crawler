/**
 * Crawl windows phone app url
 * author: Kai Xing
 * date: 07/08/2013
 * version: 1.0.0
 */
package crawler;
import java.io.*;
import java.util.*;

import parser.UrlParserWindowsPhone;

public class URLCrawler {
	public static void main(String args[]) throws Exception{
		
		UrlParserWindowsPhone WPUrl = new UrlParserWindowsPhone();
		File seedsfile = new File("D:\\Java_Workplace\\Crawler\\url-feeds");
		String urlcrawledpath = new String("D:\\Java_Workplace\\Crawler\\WPApp urls");
		File urlcrawled = new File(urlcrawledpath);
		if(!urlcrawled.exists()){
			urlcrawled.mkdir();
		}
		long starttime = System.currentTimeMillis();
		int linkcount = 0;
		for(File f:seedsfile.listFiles()){
			String geneCatg;
			if(f.getName().equals("apps.txt")){
				geneCatg = new String("apps");
			}else geneCatg = new String("games");
			FileInputStream fs = new FileInputStream(f.getAbsolutePath());
			InputStreamReader isr = new InputStreamReader(fs);
			BufferedReader bfr = new BufferedReader(isr);
			String urlline = new String();
			Set<String> uset = new HashSet<String>();
			
			while((urlline=bfr.readLine())!=null){
				linkcount++;
				Set<String> seedurl = new HashSet<String>();
				seedurl=WPUrl.extractCategoryLink(urlline);
				for(String u:seedurl){
					uset.addAll(WPUrl.appUrlParser(u));
				}
				System.out.println("Process "+linkcount+" links");
				long intermidatetime = System.currentTimeMillis();
				System.out.println("running "+(intermidatetime-starttime)+" ms");
			}
			bfr.close();
			isr.close();
			fs.close();
			String outpath = new String(geneCatg+" urls.txt");
			FileOutputStream fos = new FileOutputStream(outpath);
			OutputStreamWriter osw= new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			for(String u:uset){
				bw.write(u+"\r");
			}
			bw.close();
			osw.close();
			fos.close();
		}
	}
}
