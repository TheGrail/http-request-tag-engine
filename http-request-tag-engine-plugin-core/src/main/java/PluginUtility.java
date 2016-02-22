import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


public class PluginUtility {

	public static String escape(String seperator){
		if(seperator.equals("|")){
			seperator = "\\|";
		}
		return seperator;
	}
	
	public static String decode(String s, String type){
		try {
			if(type == null || type.length() == 0){
				return s;
			}else if(type.equals("base64")){
					return new String(Base64.decodeBase64(s), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encode(String s, String type){
		try {
			if(type == null || type.length() == 0){
				return s;
			}else if(type.equals("base64")){
				return new String(Base64.encodeBase64(s.getBytes()), "utf-8");
			}else if(type.equals("md5")){
				return DigestUtils.md5Hex(s);
			}else if(type.equals("sha1")){
				return DigestUtils.sha1Hex(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
