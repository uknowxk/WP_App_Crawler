package parser;

import java.util.*;

import javax.swing.text.html.HTML.Tag;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class UrlParserWindowsPhone {
	/**
	 * get next page, just like clicking the "next" in current page
	 * @param url given
	 * @return next page url
	 */
	public String nextPageLink(String url){
		String genedURL = new String();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding(parser.getEncoding());
			NodeList nodelist = parser.extractAllNodesThatMatch(new AndFilter(new NodeClassFilter(LinkTag.class), new HasAttributeFilter("id")));
			if(nodelist!=null && nodelist.size()>0){
				for(int i=0;i<nodelist.size();i++){
				LinkTag linktag = (LinkTag)nodelist.elementAt(i);
				String idattri = linktag.getAttribute("id");
				if(idattri.equals("nextLink")){
					String nextpagelink = linktag.getAttribute("href");
					genedURL="http://www.windowsphone.com"+nextpagelink;
				}
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genedURL;
	}
	/**
	 * extract all page links that belong to one category
	 * @param url: the first page link of given category
	 * @return all page links including the param url
	 */
	public Set<String> extractCategoryLink(String url){
		Set<String> alllink= new HashSet<String>();
		alllink.add(url);
		String np = new String();
		while((np=nextPageLink(url))!=null && (np=nextPageLink(url)).length()!=0){
			alllink.add(np);
			url=np;
		}
		return alllink;
	}
	/**
	 * extract links, which satisfy the given link filter, from page of given url 
	 * @param url given
	 * @return link set that extracted from page that given link points to
	 */
	public Set<String> extractLinks(String url){
		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding(parser.getEncoding());
			LinkRegexFilter applinkfilter = new LinkRegexFilter("http://www.windowsphone.com/en-us/store/app");
			NodeList list = parser.extractAllNodesThatMatch(applinkfilter);
			for(int i=0; i<list.size();i++){
				Node tag = list.elementAt(i);
				if(tag instanceof LinkTag){
					LinkTag link = (LinkTag)tag;
					String linkurl = link.extractLink();
					links.add(linkurl);
				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return links;
	}
	/**
	 * parse app url from given page
	 * @param url
	 * @return
	 */
	public Set<String> appUrlParser(String url){
		Set<String> appUrlSet = new HashSet<String>();
		appUrlSet = extractLinks(url);
		return appUrlSet;
	}
}
