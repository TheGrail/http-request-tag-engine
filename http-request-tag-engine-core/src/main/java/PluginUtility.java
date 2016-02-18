
public class PluginUtility {
	public String m_strRoot;
	public String m_strName;
	public String m_strLocation;
	public String m_strJar;
	public String m_strEntryClass;
	
	public PluginUtility(String root, String name, String location, String jar, String entryclass){
		m_strRoot = root;
		m_strName = name;
		m_strLocation = location;
		m_strJar = jar;
		m_strEntryClass = entryclass;
	}
}
