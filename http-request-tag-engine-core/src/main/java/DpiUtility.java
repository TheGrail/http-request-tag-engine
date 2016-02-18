
public class DpiUtility {
	public String m_strName;
	public String m_strSource;
	public String m_strSeperator;
	public int idxUserId;
	public String encodeUserId;
	public int idxHost;
	public String encodeHost;
	public int idxUrl;
	public String encodeUrl;
	public int idxUserAgent;
	public String encodeUserAgent;
	public int idxCookies;
	public String encodeCookies;
	public int idxTimestamp;
	public String encodeTimestamp;
	
	public DpiUtility(String name, String source, String seperator){
		m_strName = name;
		m_strSource = source;
		m_strSeperator = seperator;
	}
	
	public void setFieldIndex(String name, String index, String encode){
		int idx = Integer.parseInt(index);
		if(name.equals("userid")){
			idxUserId = idx;
			encodeUserId = encode;
		}else if(name.equals("host")){
			idxHost = idx;
			encodeHost = encode;
		}else if(name.equals("url")){
			idxUrl = idx;
			encodeUrl = encode;
		}else if(name.equals("useragent")){
			idxUserAgent = idx;
			encodeUserAgent = encode;
		}else if(name.equals("cookies")){
			idxCookies = idx;
			encodeCookies = encode;
		}else if(name.equals("timestamp")){
			idxTimestamp = idx;
			encodeTimestamp = encode;
		}
	}
}
