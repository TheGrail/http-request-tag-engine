import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Engine {

	private HashMap<String, DpiConfig> m_mapDpiUtility;
	private HashMap<String, PluginConfig> m_mapPluginUtility;
	private DpiConfig m_currentDpiUtility;
	private Class<?> m_classPlugin;
	private Object m_objectPlugin;
	private Method m_methodTagging;
	
	public Engine(String coreConfigXml, String pluginsConfigXml) throws DocumentException, IOException{
		setCoreConfiguration(coreConfigXml);
		setPluginsConfiguration(pluginsConfigXml);
	}
	
	public void setCoreConfiguration(String coreConfigXml) throws DocumentException, IOException{
		m_mapDpiUtility = new HashMap<String, DpiConfig>();
		//System.out.println(coreConfigXml);
		InputStream inputXml = this.getClass().getResourceAsStream(coreConfigXml);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputXml));
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(br);
        Element configuration = document.getRootElement();
        // 读取DPI配置
        @SuppressWarnings("unchecked")
		List<Element> dpiList = configuration.elements("dpi");		// DPI类型列表
        for(Element dpi : dpiList){
        	String name = dpi.attributeValue("name");
        	String source = dpi.attributeValue("source");
        	String seperator = dpi.attributeValue("seperator");
        	DpiConfig desc = new DpiConfig(name, source, seperator); // ======
        	@SuppressWarnings("unchecked")
			List<Element> fieldList = dpi.elements("field");
        	for(Element field : fieldList){
        		String fieldname = field.attributeValue("name");
        		String index = field.attributeValue("index");
        		String encode = field.attributeValue("encode");
        		String type = field.getTextTrim();
        		desc.setFieldIndex(fieldname, index, encode, type); // =========
        	}
        	m_mapDpiUtility.put(name, desc);
        }
        br.close();
        inputXml.close();
	}
	
	public void setPluginsConfiguration(String pluginsConfigXml) throws DocumentException, IOException{
		m_mapPluginUtility = new HashMap<String, PluginConfig>();
		InputStream inputXml = this.getClass().getResourceAsStream(pluginsConfigXml);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputXml));
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(br);
        Element plugins = document.getRootElement();
        String rootDirectory = plugins.attributeValue("root");
        // 读取Plugin配置
        @SuppressWarnings("unchecked")
		List<Element> pluginList = plugins.elements("plugin");	
        for(Element plugin : pluginList){
        	String name = plugin.attributeValue("name");
        	String filename = plugin.element("filename").getTextTrim();
        	String extension = plugin.element("extension").getTextTrim();
        	String entryclass = plugin.element("entryclass").getTextTrim();
        	PluginConfig desc = new PluginConfig(rootDirectory, name, filename, extension, entryclass);
        	m_mapPluginUtility.put(name, desc);
        }
        br.close();
        inputXml.close();
	}
	
	public void setDpi(String dpiName){
		DpiConfig dpi = m_mapDpiUtility.get(dpiName);
		m_currentDpiUtility = dpi;
	}
	
	public void loadPlugin(String pluginName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, URISyntaxException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException{
		PluginConfig plugin = m_mapPluginUtility.get(pluginName);
		String strPluginJarPath = plugin.m_strRoot + "/" + plugin.m_strFileName + "." + plugin.m_strExtension;
		File filePluginJar = File.createTempFile(plugin.m_strFileName, "." + plugin.m_strExtension);
		filePluginJar.deleteOnExit();
		InputStream in = getClass().getResourceAsStream(strPluginJarPath);
		OutputStream out = new FileOutputStream(filePluginJar);
		IOUtils.copy(in, out);
		in.close();
		out.close();
		registerPlugin(filePluginJar.toURI().toURL(), plugin.m_strEntryClass);
	}
	
	private void registerPlugin(URL url, String entry) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		URLClassLoader loader = new URLClassLoader(new URL[]{ url });
		m_classPlugin = loader.loadClass(entry);
		Constructor<?> constructor = m_classPlugin.getConstructor(String.class, List.class);
		//m_objectPlugin = m_classPlugin.newInstance();
		m_objectPlugin = constructor.newInstance(m_currentDpiUtility.m_strSeperator, m_currentDpiUtility.m_listFields);
	
		// 加载默认接口
		m_methodTagging = m_classPlugin.getMethod("tagging", String.class);
	}
	
	
	public String tagging(String line) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		return (String)m_methodTagging.invoke(m_objectPlugin, line);
	}
}
