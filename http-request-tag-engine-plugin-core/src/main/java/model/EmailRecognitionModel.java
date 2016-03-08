package model;

import java.util.HashMap;
import java.util.List;

import util.MysqlManager;
import util.NameToSqlAccount;

public class EmailRecognitionModel implements IModel{

	private HashMap<String, String> m_mapEmail;
	private MysqlManager mysqlManager;
	
	public EmailRecognitionModel(MysqlManager mysqlManager){
		this.m_mapEmail = new HashMap<String, String>();
		this.mysqlManager = mysqlManager;
		loadData();		
	}
	
	public void loadData() {
		List<String> rules = mysqlManager.initFromMysqlAccount(NameToSqlAccount.OtherEmailRecognition);
		for(String rule : rules){
			String temp[] = rule.split(",", -1);
			if(temp.length == 2){
				m_mapEmail.put(temp[0], temp[1]);
			}		
		}		
	}
	
	public String getPattern(String host){
		return m_mapEmail.getOrDefault(host, null);
	}

}
