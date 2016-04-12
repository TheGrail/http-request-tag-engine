package model;

import java.util.regex.Pattern;

public class ContentTaggingRule {
	private String ruleName;
	private int ruleType;
	private String para;
	private Pattern pattern;
		
	public ContentTaggingRule(String ruleName, int ruleType, String para){
		this.ruleName = ruleName;
		this.ruleType = ruleType;
		this.para = para;
	}
	
	public ContentTaggingRule(String ruleName, int ruleType, Pattern pattern){
		this.ruleName = ruleName;
		this.ruleType = ruleType;
		this.pattern = pattern;
	}

	public String getRuleName() {
		return ruleName;
	}

	public int getRuleType() {
		return ruleType;
	}

	public String getPara() {
		return para;
	}
	
	public Pattern getPattern(){
		return pattern;
	}
	
}