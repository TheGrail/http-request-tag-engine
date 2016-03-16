import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.omg.CORBA.PUBLIC_MEMBER;

import model.BaseModel;
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
		inputXml.available();
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
		        		String seperator = rows.attributeValue("seperator");
		        		@SuppressWarnings("unchecked")
						List<Element> rowList = rows.elements("row");
		        		HashMap<String, String> map = new HashMap<String, String>();
		        		for(Element row : rowList){
		        			String key = row.elementTextTrim("key");
		        			String value = row.elementTextTrim("value");
		        			map.put(key, value);
		        		}
		        		m_mapModelInfo.put(name, new ModelInfo(name, module, type, map));
		        		
		        	}
		        	break;
	        	case ModelInfo.CONNECTION_MYSQL:
		        	{
		        		String server = connection.element("server").getTextTrim();
		        		String username = connection.element("username").getTextTrim();
		        		String password = connection.element("password").getTextTrim();
		        		String schema = connection.element("schema").getTextTrim();
		        		String table = connection.element("table").getTextTrim();
		        		m_mapModelInfo.put(name, new ModelInfo(name, module, type, server, username, password, schema, table));
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
	}
    
    public DpiInfo getDpiInfo(){
    	return m_dpiInfo;
    }
    
    public ModelInfo getModelInfo(String name){
    	return m_mapModelInfo.get(name);
    }
	
	public abstract String tagging(String line);
}
