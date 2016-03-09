import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DpiConfig {
	public String m_strName;
	public String m_strSource;
	public String m_strSeperator;
	public List<String> m_listFields;
	
	public DpiConfig(String name, String source, String seperator){
		m_strName = name;
		m_strSource = source;
		m_strSeperator = seperator;
		m_listFields = new ArrayList<String>();
	}
	
	public void setFieldIndex(String name, String index, String encode, String type){
		m_listFields.add(name);
		m_listFields.add(index);
		m_listFields.add(encode);
		m_listFields.add(type);
	}
}
