package account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.MysqlManager;
import model.OtherNetRecognitionModel;

public class OtherNetRecognition {
	private Pattern pattern;
	private OtherNetRecognitionModel otherNetRecognitionModel;
	
	public OtherNetRecognition(MysqlManager mysqlManager){
		this.otherNetRecognitionModel = new OtherNetRecognitionModel(mysqlManager);
	}
	
	public String getOtherNet(String host, String url){
		String regex = otherNetRecognitionModel.getPattern(host);
		String otherNet = null;
		if(regex != null){
			pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(url);
        	if (matcher.find()){                  
        		otherNet = matcher.group(1);
        	}
		}
		return otherNet;		
	}

}
