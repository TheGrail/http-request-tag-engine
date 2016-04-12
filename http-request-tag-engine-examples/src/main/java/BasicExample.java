import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class BasicExample {

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		String strDistJar = "../dist/http-request-tag-engine-0.0.1-dist.jar";
		String strHttpRequestTagEngine = "HttpRequestTagEngine";

		File fileDistJar = new File(strDistJar);
		URL urlDistJar = fileDistJar.toURI().toURL();
		URLClassLoader classLoader = new URLClassLoader(new URL[]{ urlDistJar });
		Class<?> classHttpRequestTagEngine = classLoader.loadClass(strHttpRequestTagEngine);
		Object engine = classHttpRequestTagEngine.newInstance();
		
		Method methodSetDpi = classHttpRequestTagEngine.getMethod("setDpi", String.class);
		methodSetDpi.invoke(engine, "kafka-GW");
		Method methodLoadPlugin = classHttpRequestTagEngine.getMethod("loadPlugin", String.class);
		methodLoadPlugin.invoke(engine, "atom-id");
		
		Method methodTagging = classHttpRequestTagEngine.getMethod("tagging", String.class);
				
		while(true){
			String s = (String)methodTagging.invoke(engine, "|123456789a|06|49.65.237.246|58.211.85.32|C573|0050|bW1zbnMucXBpYy5jbg==|aHR0cHM6Ly9tbXNucy5xcGljLmNuL21tc25zL0dxSWxlakZUYk5nRXFUTGJ0dm1pYmE1YkNpYVN0TVVkNEVRdDk3c2E0SDZsTmJUZDFBUUZsdjFOeG9PT0t6aHozaDVpY2JFM2hBcmYyOC8xNTA/dHA9d2VicA==||RGFsdmlrLzIuMS4wIChMaW51eDsgVTsgQW5kcm9pZCA1LjAuMjsgWDkwMCBCdWlsZC9DQlhDTk9QNTUwMTEwMjAzMVMp||1458197465|");
			System.out.println(s);
		}
	}
}
