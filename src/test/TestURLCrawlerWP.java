package test;
import java.io.*;
import parser.UrlParserWindowsPhone;
public class TestURLCrawlerWP {
	public static void main(String args[]){
	UrlParserWindowsPhone testUP = new UrlParserWindowsPhone();
	String a = new String();
	a=testUP.nextPageLink("http://www.windowsphone.com/en-us/store/top-free-apps/tools-productivity/toolsandproductivity");
	}
}
