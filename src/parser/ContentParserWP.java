package parser;
import java.util.*;

import javax.swing.text.html.HTML.Tag;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ContentParserWP {
	/**
	 * parse app's some features form given html (windows phone store us app page)
	 * @param html
	 * @return String[] store all features
	 */
	public String[] parseRequiredField(String html){
		//0-12 : id, name, generally category(app or game), category,price ,rating value, rating count, publisher, datepublished, last updated, version, app requires, description 
		String[] requiredfields = new String[13];
		try {
			Parser parser = new Parser();
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			//extract id and general category
			//specify a nodefilter to extract nodes whose content contains app's id
			NodeFilter idfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("a data-os=\"getApp\"")){
						return true;
					}else return false;
				}
				
			};
			NodeList idnodelist = parser.extractAllNodesThatMatch(idfilter);
			if(idnodelist!=null && idnodelist.size()>0){
				LinkTag lt = (LinkTag)idnodelist.elementAt(0);
				String categandid = lt.getAttribute("data-ov");
				String[] e1 = categandid.split(";");
				//general category (app or game)
				requiredfields[2]=e1[0];
				String[] e2 = e1[1].split(" ");
				//id
				requiredfields[0]=e2[0];
			}
			//extract name
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter namefilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("h1 itemprop=\"name\"")){
						return true;
					}else return false;
				}
			};
			NodeList namenodelist = parser.extractAllNodesThatMatch(namefilter);
			if(namenodelist!=null && namenodelist.size()>0){
				//get the text information between <h1></h1>
				TextNode tx = (TextNode)namenodelist.elementAt(0).getFirstChild();
				requiredfields[1]=tx.getText();
			}
			//extract category
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter categfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("strong itemprop=\"applicationCategory\"")){
						return true;
					}else return false;
				}
				
			};
			NodeList categnodelist = parser.extractAllNodesThatMatch(categfilter);
			if(categnodelist!=null && categnodelist.size()>0){
				//<strong>test</strong> "text" is a silbing of strong tag
				TextNode tx = (TextNode)categnodelist.elementAt(0).getNextSibling();
				requiredfields[3]=tx.getText();
				//requiredfields[3]=categnodelist.elementAt(0).toString();
			}
			//extract price
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter pricefilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("span itemprop=\"price\""))
						return true;
					else return false;
				}
				
			};
			NodeList pricenodelist = parser.extractAllNodesThatMatch(pricefilter);
			if(pricenodelist!=null && pricenodelist.size()>0){
				TextNode tx = (TextNode)pricenodelist.elementAt(0).getFirstChild();
				requiredfields[4]=tx.getText();
			}
			//extract rating value
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter rvfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("meta itemprop=\"ratingValue\""))
						return true;
					else return false;
				}
				
			};
			NodeList rvnodelist= parser.extractAllNodesThatMatch(rvfilter);
			if(rvnodelist!=null && rvnodelist.size()>0){
				MetaTag mt = (MetaTag)rvnodelist.elementAt(0);
				requiredfields[5]=mt.getAttribute("content");
			}
			//extract rating count
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter rcfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("meta itemprop=\"ratingCount\""))
						return true;
					else return false;
				}
				
			};
			NodeList rcnodelist = parser.extractAllNodesThatMatch(rcfilter);
			if(rcnodelist!=null && rcnodelist.size()>0){
				MetaTag mt = (MetaTag)rcnodelist.elementAt(0);
				requiredfields[6]=mt.getAttribute("content");
			}
			//extract publiser
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter publiserfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					
					if(node.getText().startsWith("div id=\"publisher\""))
						return true;
					else return false;
				}
				
			};
			NodeList pubnodelist = parser.extractAllNodesThatMatch(publiserfilter);
			if(pubnodelist!=null && pubnodelist.size()>0){
				NodeList children = pubnodelist.elementAt(0).getChildren();
				//There are two cases: one is <span>xx</span>; the other is <a>xx</a>. Both <span> and <a> are the 4th children of <div>
				Node child4th = children.elementAt(3);
				TextNode tx = (TextNode)child4th.getFirstChild();
				requiredfields[7]=tx.getText();
			}
			//extract data published
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter datepubfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("meta itemprop=\"datePublished\""))
						return true;
					else return false;
				}
				
			};
			NodeList datepubnodelist = parser.extractAllNodesThatMatch(datepubfilter);
			if(datepubnodelist!=null && datepubnodelist.size()>0){
				MetaTag mt = (MetaTag)datepubnodelist.elementAt(0);
				requiredfields[8]=mt.getAttribute("content");
			}
			//extract date last updated
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter lastupfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("div id=\"releaseDate\""))
						return true;
					else return false;
				}
				
			};
			NodeList lastupnodelist = parser.extractAllNodesThatMatch(lastupfilter);
			if(lastupnodelist!=null && lastupnodelist.size()>0){
				NodeList children = lastupnodelist.elementAt(0).getChildren();
				TextNode tx = (TextNode)children.elementAt(5).getFirstChild();
				requiredfields[9]=tx.getText();
			}
			//extract version
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter versionfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("span itemprop=\"softwareVersion\""))
						return true;
					else return false;
				}
				
			};
			NodeList versionnodelist = parser.extractAllNodesThatMatch(versionfilter);
			if(versionnodelist!=null && versionnodelist.size()>0){
				TextNode tx = (TextNode)versionnodelist.elementAt(0).getFirstChild();
				requiredfields[10]=tx.getText();
			}
			//extract requirement
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter requfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("div id=\"hardwareRequirements\""))
						return true;
					else return false;
				}
				
			};
			NodeList requnodelist = parser.extractAllNodesThatMatch(requfilter);
			if(requnodelist!=null && requnodelist.size()>0){
				NodeList hua = requnodelist.elementAt(0).getChildren();
				//the second child of requnodelist contains all the requiement
				NodeList allrequ = hua.elementAt(3).getChildren();
				requiredfields[11]="";
				for(int i=0; i<allrequ.size();i++){
					if(allrequ.elementAt(i) instanceof TagNode){
						TextNode tx = (TextNode)allrequ.elementAt(i).getFirstChild();
						//use "@@" to separate each requirement
						requiredfields[11]=requiredfields[11]+"@@"+tx.getText();
					}
				}
				//remove the "@@" at the beginning
				requiredfields[11]=requiredfields[11].substring(2);
			}
			//extract description
			parser.setInputHTML(html);
			parser.setEncoding(parser.getEncoding());
			NodeFilter descriptionfilter = new NodeFilter(){

				@Override
				public boolean accept(Node node) {
					// TODO Auto-generated method stub
					if(node.getText().startsWith("pre itemprop=\"description\""))
						return true;
					else return false;
				}
				
			};
			NodeList desnodelist = parser.extractAllNodesThatMatch(descriptionfilter);
			if(desnodelist!=null && desnodelist.size()>0){
				TextNode tx = (TextNode)desnodelist.elementAt(0).getNextSibling();
				requiredfields[12]=tx.getText();
			}
			} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requiredfields;
	}
	
}
