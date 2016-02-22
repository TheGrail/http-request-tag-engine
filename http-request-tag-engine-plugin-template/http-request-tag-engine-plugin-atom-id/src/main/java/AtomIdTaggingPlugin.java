import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.TerminalModel;

import org.apache.commons.codec.binary.Base64;


public class AtomIdTaggingPlugin extends BasePlugin implements IPlugin {

	TerminalModel terminalModel;
	
	public AtomIdTaggingPlugin(){
		super();
		terminalModel = new TerminalModel();
	}
	
	public void tagging(String line, String seperator, List<String> fields) {
		// 放到BasePlugin里
		seperator = PluginUtility.escape(seperator);
		System.out.println(line);
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
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
		String[] cols = line.split(seperator, -1);
		
		String output = "";
		// 用户账户
		{
			String fieldname = "userid";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			text = PluginUtility.decode(text, encode);
			String type = map.get(fieldname).get(2);
			output += text + "|" + type;
		}
		output += "|";
		// 终端
		{
			String fieldname = "useragent";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			text = PluginUtility.decode(text, encode);
			text = terminalModel.getTerminal(text);
			String type = map.get(fieldname).get(2);
			output += text + "|" + type;
		}
		output += "|";
		// 账号
		{
			String fieldname = "useragent";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			text = PluginUtility.decode(text, encode);
			text = "qq";
			String type = map.get(fieldname).get(2);
			output += text + "|" + type;
		}
		String atomid = PluginUtility.encode(output, "md5");
		output += "|";
		// cookies
		{
			String fieldname = "cookies";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			text = PluginUtility.decode(text, encode);
			String type = map.get(fieldname).get(2);
			output += text;
		}
		output += "|";
		// timestamp
		{
			String fieldname = "timestamp";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			text = PluginUtility.decode(text, encode);
			String type = map.get(fieldname).get(2);
			output += text;
		}
		
		System.out.println(atomid + "|" + output);
	}

}
