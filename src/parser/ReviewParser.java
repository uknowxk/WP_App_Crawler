package parser;
import java.io.BufferedWriter;
import java.util.*;
import javax.swing.text.html.HTML.Tag;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import utility.DownloadPage;

public class ReviewParser {
	
	private DownloadPage dp = new DownloadPage();
	private Parser parser = new Parser();
	private int pagenum = 0;
	/**
	 * Parse link of "more"
	 * @param parser
	 * @param html file
	 * @return link of "more"
	 */
	private String parseMoreLink(String html){
		String morelink = new String();
		try {
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter morelinkfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("a data-os=\"internalContent\" data-ov=\"AppReviews:More reviews\" id=\"moreReviews\""))
						return true;
					return false;
				}
				
			};
			NodeList linklist = parser.extractAllNodesThatMatch(morelinkfilter);
			if(linklist!=null && linklist.size()>0){
				LinkTag lt = (LinkTag)linklist.elementAt(0);
				morelink = lt.extractLink();
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return morelink;
	}
	/**
	 * Extract more reviews from "more" link
	 * @param parser
	 * @param url
	 * @param bf
	 */
	private void parseMoreReviews(String url, BufferedWriter bf){
		try {
			String reviewpage = dp.downloadPage(url);
			parser.setInputHTML(reviewpage);
			parser.setEncoding(parser.getEncoding());
			NodeFilter reviewfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("div class=\"reviewText\" itemprop=\"reviewBody\"")){
						return true;
					}
					return false;
				}
				
			};
			NodeList reviewlist = parser.extractAllNodesThatMatch(reviewfilter);
			if(reviewlist!=null && reviewlist.size()>0){
				for(int i=0;i<reviewlist.size();i++){
					TextNode tx = (TextNode)reviewlist.elementAt(i).getFirstChild();
					bf.write(tx.getText()+"\n");
				}
			}
			/**pagenum++; 
			System.out.println("Processed "+ pagenum + " pages");*/
			//parse link of "more" page
			String morelink = parseMoreLink(reviewpage);
			if(morelink!=null){
				parseMoreReviews(morelink, bf);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Extract reviews in app's page. If there are more reviews, call parseMoreReview method to extract them.
	 * @param url
	 * @param BufferedWriter bf of outputfile
	 */
	public void parseReviews(String url, BufferedWriter bf){
		DownloadPage dp = new DownloadPage();
		try {
			String html = dp.downloadPage(url);
			//parse reviews in app's first page
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter reviewfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("div class=\"reviewText\" itemprop=\"reviewBody\"")){
						return true;
					}
					return false;
				}
				
			};
			NodeList reviewlist = parser.extractAllNodesThatMatch(reviewfilter);
			if(reviewlist!=null && reviewlist.size()>0){
				for(int i=0;i<reviewlist.size();i++){
					TextNode tx = (TextNode)reviewlist.elementAt(i).getFirstChild();
					bf.write(tx.getText()+"\n");
				}
			}
			/*pagenum++;
			System.out.println("Processed " + pagenum + " pages");*/
			//parse link of "more" page
			String morelink = parseMoreLink(html);
			if(morelink!=null){
				parseMoreReviews(morelink, bf);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
