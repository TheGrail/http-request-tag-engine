package dpi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Dpi {
	public Dpi() {

	}
	
	private String uid;
	//private String protocolType;
	private String sourceIp;
	//private String destinationIp;
	private String sourcePort;
	//private String destinationPort;
	private String host;
	private String url;
	//private String referer;
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
		try {
			url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			URLDecoder.decode(url,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getSourceIp() {
		return sourceIp;
	}

	public int getSourcePort() {
		return Integer.parseInt(sourcePort);
	}

	public String getHost() {
		return host;
	}

	public String getUrl() {
		return url;
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

	public String getUseragent() {
		return useragent;
	}

	public DpiInfo getM_dpiInfo() {
		return m_dpiInfo;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setM_dpiInfo(DpiInfo m_dpiInfo) {
		this.m_dpiInfo = m_dpiInfo;
	}
}