import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dpi.RawDpi;

public class DpiFilter {
	
	private ArrayList<Pattern> urlPostfixFilterTagList;
	private final String m_strFilterConfigXml = "/config/filter.xml";
	private static final int URLPOSTFIX = 0;
	private Matcher matcher;
		
	public DpiFilter() throws DocumentException, UnsupportedEncodingException{
		//xml读入后缀匹配tag
		urlPostfixFilterTagList = new ArrayList<Pattern>(); 
		setFilterConfiguration(m_strFilterConfigXml);		
	}
	
	private void setFilterConfiguration(String strFilterConfigXml) throws DocumentException, UnsupportedEncodingException{
		InputStream inputXml = this.getClass().getResourceAsStream(strFilterConfigXml);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputXml);
        Element rules = document.getRootElement();   
        @SuppressWarnings("unchecked")
		List<Element> ruleList = rules.elements("rule");
        for(Element rule : ruleList){
        	int type = Integer.parseInt(rule.attributeValue("type"));
        	switch(type){
        		case URLPOSTFIX:
        		{
        			Element postfix = rule.element("postfix");
        			urlPostfixFilterTagList.add(Pattern.compile(postfix.getTextTrim()));    			
        		}
        		break;       		
        		default:
	        		break;
        	}
        }		
	}
	
	public boolean filter(RawDpi rawDpi){			
		if(urlPostfixFilter(rawDpi.getUrl())){
			return true;
		}		
		//其他过滤条件undo		
		return false;
	}
	
	private boolean urlPostfixFilter(String url){
		URL m_url;
		try {
			m_url = new URL(url);
			String path = m_url.getPath();
			for(Pattern pattern: urlPostfixFilterTagList){
				matcher = pattern.matcher(path);
				if(matcher.find()){
					return true;
				}
			}			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return false;	
	}
}