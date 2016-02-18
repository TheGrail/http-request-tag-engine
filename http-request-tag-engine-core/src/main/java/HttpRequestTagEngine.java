import org.dom4j.DocumentException;


public class HttpRequestTagEngine {
	// 预置参数
	private final String strCoreConfigXml = "/config/core.xml";
	private final String strPluginsConfigXml = "/config/plugins.xml";
	// 引擎核心
	public Engine engine;
	// 构造函数
	public HttpRequestTagEngine() throws DocumentException{
		engine = new Engine(strCoreConfigXml, strPluginsConfigXml);
	}
	// 打标签
	public void tagging(String dpiName, String pluginName, String line){
		
	}
}
