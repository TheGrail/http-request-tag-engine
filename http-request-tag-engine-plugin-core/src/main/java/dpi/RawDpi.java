package dpi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RawDpi {
	private String url;
	private DpiInfo m_dpiInfo;
	
	public RawDpi(String line, DpiInfo info, String filedname){
		String[] cols = line.split(info.getSeperator(), -1);
		m_dpiInfo = info;

		if(filedname.equals("url")){
			url = parse(cols, "url");
			try {
				url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
				url = URLDecoder.decode(url, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
	}
	
	public String parse(String[] cols, String name){
		String fieldname = name;
		FieldInfo field = m_dpiInfo.getFieldInfo(fieldname);
		String text = cols[field.getIndex()];
		String encode = field.getEncode();
		return  DpiUtility.decode(text, encode);
	}
	
	public String getUrl(){
		return url;
	}

}
