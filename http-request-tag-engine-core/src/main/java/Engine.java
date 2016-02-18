import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Engine {

	HashMap<String, DpiUtility> m_mapDpiUtility;
	HashMap<String, PluginUtility> m_mapPluginUtility;
	
	public Engine(String coreConfigXml, String pluginsConfigXml) throws DocumentException{
		setCoreConfiguration(coreConfigXml);
		setPluginsConfiguration(pluginsConfigXml);
	}
	
	public void setCoreConfiguration(String coreConfigXml) throws DocumentException{
		m_mapDpiUtility = new HashMap<String, DpiUtility>();
		File inputXml = new File(coreConfigXml);  
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputXml);
        Element configuration = document.getRootElement();
        // 读取DPI配置
        List<Element> dpiList = configuration.elements("dpi");		// DPI类型列表
        for(Element dpi : dpiList){
        	String name = dpi.attributeValue("name");
        	String source = dpi.attributeValue("source");
        	String seperator = dpi.attributeValue("seperator");
        	DpiUtility desc = new DpiUtility(name, source, seperator); // ======
        	List<Element> fieldList = dpi.elements("field");
        	for(Element field : fieldList){
        		name = field.attributeValue("name");
        		String index = field.attributeValue("index");
        		String encode = field.attributeValue("encode");
        		desc.setFieldIndex(name, index, encode); // =========
        	}
        	m_mapDpiUtility.put(name, desc);
        }
	}
	
	public void setPluginsConfiguration(String pluginsConfigXml) throws DocumentException{
		m_mapPluginUtility = new HashMap<String, PluginUtility>();
		File inputXml = new File(pluginsConfigXml);  
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputXml);
        Element plugins = document.getRootElement();
        String rootDirectory = plugins.attributeValue("root");
        // 读取Plugin配置
        List<Element> pluginList = plugins.elements("plugin");		// DPI类型列表
        for(Element plugin : pluginList){
        	String name = plugin.attributeValue("name");
        	String location = plugin.element("location").getTextTrim();
        	String jar = plugin.element("jar").getTextTrim();
        	String entryclass = plugin.element("entryclass").getTextTrim();
        	PluginUtility desc = new PluginUtility(rootDirectory, name, location, jar, entryclass);
        	m_mapPluginUtility.put(name, desc);
        }
	}
}
