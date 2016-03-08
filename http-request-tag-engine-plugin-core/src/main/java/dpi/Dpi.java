package dpi;
import java.util.HashMap;
import java.util.List;


public class Dpi {
	private String uid;
	private String protocolType;
	private String sourceIp;
	private String destinationIp;
	private String sourcePort;
	private String destinationPort;
	private String host;
	private String url;
	private String referer;
	private String useragent;	
	private String cookie;
	private String timestamp;
	
	private DpiInfo m_dpiInfo;
	
	public Dpi (String line, DpiInfo info){
		String[] cols = line.split(info.getSeperator(), -1);
		m_dpiInfo = info;
		
		uid = parse(cols, "userid");
		host = parse(cols, "host");
		url = parse(cols, "url");
		useragent = parse(cols, "useragent");
		timestamp = parse(cols, "timestamp");
		
	}
	
	public String parse(String[] cols, String name){
		String fieldname = name;
		FieldInfo field = m_dpiInfo.getFieldInfo(fieldname);
		String text = cols[field.getIndex()];
		String encode = field.getEncode();
		return  DpiUtility.decode(text, encode);
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

	public String getUserAgent() {
		return useragent;
	}

	public String getCookie() {
		return cookie;
	}

	public String getTimestamp() {
		return timestamp;
	}
}