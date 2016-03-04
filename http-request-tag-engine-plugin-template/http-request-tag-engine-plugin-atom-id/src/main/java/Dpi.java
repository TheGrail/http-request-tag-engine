import java.util.HashMap;
import java.util.List;

public class Dpi extends BasePlugin{
	
	private String uid;
	private String protocolType;
	private String sourceIp;
	private String destinationIp;
	private String sourcePort;
	private String destinationPort;
	private String host;
	private String url;
	private String referer;
	private String ua;	
	private String cookie;
	private String timeStamp;
	
	public Dpi (String line, String seperator, HashMap<String, List<String>> map){
		String[] cols = line.split(seperator, -1);
		
		{
			String fieldname = "userid";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			uid = decode(text, encode);
		}
		
		{
			String fieldname = "host";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			host = decode(text, encode);
		}
		
		{
			String fieldname = "url";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			url = decode(text, encode);
		}
		
		{
			String fieldname = "ua";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			ua = decode(text, encode);
		}
		
		{
			String fieldname = "timestamp";
			Integer index = Integer.parseInt(map.get(fieldname).get(0));
			String text = cols[index];
			String encode = map.get(fieldname).get(1);
			timeStamp = decode(text, encode);
		}
		
	}

	public String getUid() {
		return uid;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public String getDestinationIp() {
		return destinationIp;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public String getDestinationport() {
		return destinationPort;
	}

	public String getHost() {
		return host;
	}

	public String getUrl() {
		return url;
	}

	public String getReferer() {
		return referer;
	}

	public String getUa() {
		return ua;
	}

	public String getCookie() {
		return cookie;
	}

	public String getTimestamp() {
		return timeStamp;
	}

}
