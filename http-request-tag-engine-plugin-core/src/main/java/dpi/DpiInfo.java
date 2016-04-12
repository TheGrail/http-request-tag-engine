package dpi;

import java.util.HashMap;
import java.util.List;

public class DpiInfo {

	private HashMap<String, FieldInfo> m_mapName2Field;
	private String m_strSeperator;
	
	public DpiInfo(){
		
	}
	
	public DpiInfo(String sep, List<String> fields){
		m_strSeperator = DpiUtility.escape(sep);
		m_mapName2Field = new HashMap<String, FieldInfo>();
		for(int i=0; i<fields.size(); i+=4){
			String name = fields.get(i);
			String index = fields.get(i+1);
			String encode = fields.get(i+2);
			String type = fields.get(i+3);
			m_mapName2Field.put(name, new FieldInfo(name, Integer.parseInt(index), encode, type));
		}
	}
	
	public FieldInfo getFieldInfo(String name){
		return m_mapName2Field.get(name);
	}
	
	public String getSeperator(){
		return m_strSeperator;
	}
}