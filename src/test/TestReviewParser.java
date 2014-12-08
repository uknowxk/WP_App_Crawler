package test;
import java.io.*;

import parser.ReviewParser;
public class TestReviewParser {
	public static void main(String[] args) throws IOException{
		ReviewParser rp = new ReviewParser();
		String outputfile = new String("test.txt");
		String url = new String("http://www.windowsphone.com/en-us/store/app/nextgen-reader/643381de-4724-e011-854c-00237de2db9e");
		
		FileOutputStream fos = new FileOutputStream(outputfile);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		rp.parseReviews(url, bw);
		bw.close();
		osw.close();
		fos.close();
	}
}
