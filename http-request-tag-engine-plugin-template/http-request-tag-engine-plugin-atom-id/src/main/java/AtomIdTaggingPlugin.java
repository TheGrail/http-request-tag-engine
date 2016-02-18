import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;


public class AtomIdTaggingPlugin extends BasePlugin implements IPlugin {

	public AtomIdTaggingPlugin(){
		super();
	}
	
	public void tagging(String line, String seperator, List<String> fields) {
		seperator = escapeSeperator(seperator);
		System.out.println(line);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		for(int i=0; i<fields.size(); i+=3){
			String name = fields.get(i);
			String index = fields.get(i+1);
			String encode = fields.get(i+2);
			List<String> list = new ArrayList<String>();
			list.add(index);
			list.add(encode);
			map.put(name, list);
		}
		String[] cols = line.split(seperator, -1);
		for(Map.Entry<String, List<String>> entry : map.entrySet()){
			String field = entry.getKey();
			List<String> list = entry.getValue();
			String index = list.get(0);
			String encode = list.get(1);
			
			int idx = Integer.parseInt(index);
			String text = cols[idx];
			if(encode.equals("base64")){
				try {
					text = new String(Base64.decodeBase64(text), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}			
			System.out.println(field + " : " + text);
		}
	}

}
