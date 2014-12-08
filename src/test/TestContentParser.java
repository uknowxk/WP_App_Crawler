package test;
import java.io.*;
import parser.ContentParserWP;
import utility.DownloadPage;
public class TestContentParser {
	public static void main(String args[]) throws Exception{
	DownloadPage dp = new DownloadPage();
	String html = dp.downloadPage("http://www.windowsphone.com/en-us/store/app/espn-scorecenter/486c5bc7-e253-e011-854c-00237de2db9e");
	ContentParserWP testWP = new ContentParserWP();
	String[] test = testWP.parseRequiredField(html);
	for(int i=0;i<13;i++){
		System.out.println(test[i]);	
	}
	}
}
