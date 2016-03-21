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
	private String cookies;
	private String timestamp;
	
	private DpiInfo m_dpiInfo;
	
	public Dpi (String line, DpiInfo info){
		String[] cols = line.split(info.getSeperator(), -1);
		
		m_dpiInfo = info;
		
		uid = parse(cols, "userid");
		sourceIp = parse(cols, "srcip");
		sourcePort = parse(cols, "srcport");
		host = parse(cols, "host");
		url = parse(cols, "url");
		useragent = parse(cols, "useragent");
		cookies = parse(cols, "cookies");
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

	public int getSourcePort() {
		return Integer.parseInt(sourcePort);
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

	public String getCookies() {
		return cookies;
	}

	public String getTimestamp() {
		return timestamp;
	}
}
