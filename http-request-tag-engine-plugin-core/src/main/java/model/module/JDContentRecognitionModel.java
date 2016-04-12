package model.module;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import dpi.Dpi;
import model.BaseModel;
import model.ContentTaggingRule;
import model.ModelInfo;

public class JDContentRecognitionModel extends BaseModel{
	
	private HashMap<String, String> m_mapData;
	private HashMap<String, ContentTaggingRule> m_mapRule;
	private ArrayList<String> m_hosArrayList;
	private final String m_strOutputInsideSeperator = "|";
	
	private static final int pathRuleIndex = 0;
	private static final int paraRuleIndex = 1;
	private static final int modelIndex = 3;

	public JDContentRecognitionModel(ModelInfo info) {
		super(info);	
	}

	@Override
	public void load(ModelInfo info) {
		m_mapData = new HashMap<String, String>();
		m_mapData = info.getRawData();
		m_hosArrayList = new ArrayList<String>();
		//System.out.println(m_mapData);
		for(String key: m_mapData.keySet()){
			//System.out.println(key);
			m_hosArrayList.add(key);
			
		}
		m_mapRule = new HashMap<String, ContentTaggingRule>();
		m_mapRule = info.getMapContentTaggingRule();
	}

	private String getContent(String m_url, ContentTaggingRule contentTaggingRule){		
		try {
			URL url = new URL(m_url);
			int ruleType = contentTaggingRule.getRuleType();			
			switch (ruleType) {
			case pathRuleIndex:
				String path = url.getPath();
				//System.out.println("--------"+path);
				Pattern pattern = Pattern.compile(contentTaggingRule.getPara());
				Matcher matcher = pattern.matcher(path);
				if(matcher.find()){
					//System.out.println("--------"+matcher.group(1));
					return matcher.group(1);
				}				
				break;			
			case paraRuleIndex:
				String query = url.getQuery();
				MultiMap<String> values = new MultiMap<String>(); 
				UrlEncoded.decodeTo(query, values, "UTF-8", 1000);

				String para = contentTaggingRule.getPara();
				if(values.containsKey(para)){
					return values.get(para).get(0);
				}
				break;
			default:
				break;
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public String recognize(Dpi dpi) {
		String host = dpi.getHost();
		if(m_mapData.containsKey(host) && m_mapRule.containsKey(host)){
			StringBuilder sb = new StringBuilder();
			sb.append("JD");
			sb.append(m_strOutputInsideSeperator);
			sb.append(m_mapData.get(host));
			sb.append(m_strOutputInsideSeperator);
			sb.append(getContent(dpi.getUrl(), m_mapRule.get(host)));
			return sb.toString();
		}
		return "";
	}

	@Override
	public ArrayList<String> getHost() {
		// TODO Auto-generated method stub
		return m_hosArrayList;
	}

	@Override
	public int getModelIndex() {
		// TODO Auto-generated method stub
		return modelIndex;
	}
}