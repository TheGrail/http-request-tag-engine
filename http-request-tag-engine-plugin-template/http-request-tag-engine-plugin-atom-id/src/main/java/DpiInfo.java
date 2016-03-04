import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DpiInfo extends BasePlugin{
	
	private HashMap<String, List<String>> map;
	
	public DpiInfo(String seperator, List<String> fields){
		super();
		seperator = escapeSeperator(seperator);
		this.map = new HashMap<String, List<String>>();
		//DPI配置信息
		for(int i=0; i<fields.size(); i+=4){
			String name = fields.get(i);
			String index = fields.get(i+1);
			String encode = fields.get(i+2);
			String type = fields.get(i+3);
			System.out.println(name + "," + index + "," + encode + "," + type);
			List<String> list = new ArrayList<String>();
			list.add(index);
			list.add(encode);
			list.add(type);
			map.put(name, list);
		}		
	}

	public HashMap<String, List<String>> getDpiInfo() {
		return this.map;
	}
}