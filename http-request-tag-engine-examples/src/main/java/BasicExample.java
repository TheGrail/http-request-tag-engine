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
		methodSetDpi.invoke(engine, "GW");
		Method methodLoadPlugin = classHttpRequestTagEngine.getMethod("loadPlugin", String.class);
		methodLoadPlugin.invoke(engine, "atom-id");
		
		Method methodTagging = classHttpRequestTagEngine.getMethod("tagging", String.class);
		methodTagging.invoke(engine, "051083303031^6^114.223.14.170^202.102.94.120^4006^80^aHEuc2luYWpzLmNu^aHR0cHM6Ly9ocS5zaW5hanMuY24vcm49MTQ1NzMzMzYzNTEwMSZsaXN0PXNoNjAwMDE2LHNoNjAwMDE2X2kscnRfaGswMTk4OCxSTUJIS0QsYmtfbmV3X2pyaHk=^aHR0cDovL2ZpbmFuY2Uuc2luYS5jb20uY24vcmVhbHN0b2NrL2NvbXBhbnkvc2g2MDAwMTYvbmMuc2h0bWw=^TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgNS4xKSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvMzEuMC4xNjUwLjYzIFNhZmFyaS81MzcuMzY=^^1^20160307145401");
	}

}
