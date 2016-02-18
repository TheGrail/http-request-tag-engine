
public class PluginUtility {
	public String m_strRoot;
	public String m_strName;
	public String m_strFileName;
	public String m_strExtension;
	public String m_strEntryClass;
	
	public PluginUtility(String root, String name, String filename, String extension, String entryclass){
		m_strRoot = root;
		m_strName = name;
		m_strFileName = filename;
		m_strExtension = extension;
		m_strEntryClass = entryclass;
	}
}
