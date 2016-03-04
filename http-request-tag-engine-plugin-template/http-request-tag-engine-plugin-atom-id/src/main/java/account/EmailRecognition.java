package account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.MysqlManager;
import model.EmailRecognitionModel;

public class EmailRecognition {
	private EmailRecognitionModel emailRecognitionModel;
	private Pattern pattern;
	
	public EmailRecognition(MysqlManager mysqlManager){
		this.emailRecognitionModel = new EmailRecognitionModel(mysqlManager);
	}
	
	public String getEmail(String host, String url){
		String regex = emailRecognitionModel.getPattern(host);
		String email = null;
		if(regex != null){
			pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(url);
        	if (matcher.find()){                  
        		email = matcher.group(1);
        	}
		}
		return email;		
	}
}
