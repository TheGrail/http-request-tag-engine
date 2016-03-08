import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.jar.JarFile;

import org.dom4j.DocumentException;


public class HttpRequestTagEngine {
	// 预置参数
	private final String strCoreConfigXml = "config/core.xml";
	private final String strPluginsConfigXml = "config/plugins.xml";
	// 引擎核心
	private Engine engine;
	// 构造函数
	public HttpRequestTagEngine() throws DocumentException, IOException{
		engine = new Engine(strCoreConfigXml, strPluginsConfigXml);
	}
	//
	public void setDpi(String dpiName){
		engine.setDpi(dpiName);
	}
	// 动态加载插件
	public void loadPlugin(String pluginName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, URISyntaxException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException{
		engine.loadPlugin(pluginName);
	}
	// 打标签
	public void tagging(String line) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		engine.tagging(line);
	}
}
