import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.ContentTaggingRule;
import model.ModelInfo;
import dpi.DpiInfo;

public abstract class BasePlugin {

	private DpiInfo m_dpiInfo;
	private final String m_strModelConfigXml = "/config/models.xml";
	private HashMap<String, ModelInfo> m_mapModelInfo;
	
	public BasePlugin(String sep, List<String> fields) throws DocumentException, IOException{
		m_dpiInfo = new DpiInfo(sep, fields);
		setModelConfiguration(m_strModelConfigXml);
	}
	
	public void setModelConfiguration(String strModelConfigXml) throws DocumentException, IOException{
		m_mapModelInfo = new HashMap<String, ModelInfo>();
		InputStream inputXml = this.getClass().getResourceAsStream(strModelConfigXml);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputXml));
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputXml);
        Element models = document.getRootElement();
        // 读取DPI配置
        @SuppressWarnings("unchecked")
		List<Element> modelList = models.elements("model");		// DPI类型列表
        for(Element model : modelList){
        	String name = model.attributeValue("name");
        	String module = model.attributeValue("module");
        	Element connection = model.element("connection");
        	int type = Integer.parseInt(connection.attributeValue("type"));
        	switch(type){
	        	case ModelInfo.CONNECTION_RAWDATA:
		        	{
		        		Element rows = model.element("rows");
		        		//String seperator = rows.attributeValue("seperator");
		        		@SuppressWarnings("unchecked")
						List<Element> rowList = rows.elements("row");
		        		HashMap<String, String> map = new HashMap<String, String>();
		        		HashMap<String, ContentTaggingRule> ruleMap = new HashMap<String, ContentTaggingRule>();
		        		for(Element row : rowList){
		        			String key = row.elementTextTrim("key");		        			
		        			String value = row.elementTextTrim("value");		        			
		        			map.put(key, value);
		        			Element rule = row.element("rule");
		        			String ruleName = rule.attributeValue("name");
		        			int ruleType = Integer.parseInt(rule.attributeValue("ruletype"));
		        			if(ruleType == 0){
		        				//int startIndex = Integer.parseInt(rule.elementTextTrim("startindex"));
		        				//int endIndex = Integer.parseInt(rule.elementTextTrim("endindex"));
		        				String regex = rule.elementTextTrim("pattern");
		        				//System.out.println("--------------"+regex);
		        				//Pattern pattern = Pattern.compile(regex);
		        				ContentTaggingRule contentTaggingRule = new ContentTaggingRule(ruleName, ruleType, regex);
		        				ruleMap.put(key, contentTaggingRule);
		        			}
		        			else if(ruleType == 1){
		        				String paraName = rule.elementTextTrim("paraname");
		        				ContentTaggingRule contentTaggingRule = new ContentTaggingRule(ruleName, ruleType, paraName);
		        				ruleMap.put(key, contentTaggingRule);
		        			}
		        			
		        		}
		        		m_mapModelInfo.put(name, new ModelInfo(name, module, type, map, ruleMap));		        		
		        	}
		        	break;
		        	
	        	case ModelInfo.CONNECTION_MYSQL:
		        	{
		        		String server = connection.element("server").getTextTrim();
		        		String username = connection.element("username").getTextTrim();
		        		String password = connection.element("password").getTextTrim();
		        		String schema = connection.element("schema").getTextTrim();
		        		String table = connection.element("table").getTextTrim();
		        		long interval = Long.parseLong(connection.element("interval").getTextTrim());
		        		m_mapModelInfo.put(name, new ModelInfo(name, module, type, server, username, password, schema, table, interval));
		        	}
		        	break;
		        	
	        	case ModelInfo.CONNECTION_REDIS_CLUSTER:
		        	{
		        		String server = connection.element("server").getTextTrim();
		        		m_mapModelInfo.put(name, new ModelInfo(name, module, type, server));
		        	}
		        	break;
		        	
	        	default:
	        		break;
        	}
        }
        br.close();
        inputXml.close();
	}
    
    public DpiInfo getDpiInfo(){
    	return m_dpiInfo;
    }
    
    public ModelInfo getModelInfo(String name){
    	return m_mapModelInfo.get(name);
    }
	
	public abstract String tagging(String line);
	
}