package terminal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.RedisUtil;
import model.TerminalModel;

public class TerminalRecognition {
	
	private TerminalModel terminalModel;
	private Pattern pattern;
	private RedisUtil redisUtil;
	
	public TerminalRecognition(String propFilePath){
		this.terminalModel = new TerminalModel(propFilePath);
		this.redisUtil = new RedisUtil(propFilePath);	
	}
	
	public String getTerminal(String ua, String host){
		String terminal = null;
		String regex = terminalModel.getPattern(host);
		
		if(regex != null){
			pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(ua);
        	if (matcher.find()){                  
        		String temp = matcher.group(1);
        		if(! this.filterTerminal(temp)){
        			terminal = this.getStandardTerminal(temp);
        		}
        	}
		}
		
		return terminal;
	}
	
	private boolean filterTerminal(String terminal){
		boolean tag = false;
		
		return tag;
	}
	
	private String getStandardTerminal(String terminal){
		String result = null;
		result = redisUtil.getStringValue(terminal);
		return result;
	}

}