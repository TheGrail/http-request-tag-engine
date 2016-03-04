package model;

import java.util.HashMap;
import java.util.List;

import util.MysqlManager;
import util.NameToSqlAccount;

public class OtherNetRecognitionModel {
	
	private HashMap<String, String> m_mapOtherNet;
	private MysqlManager mysqlManager;
	
	public OtherNetRecognitionModel(MysqlManager mysqlManager){
		this.m_mapOtherNet = new HashMap<String, String>();
		this.mysqlManager = mysqlManager;
		loadData();	
	}

	private void loadData() {
		List<String> rules = mysqlManager.initFromMysqlAccount(NameToSqlAccount.OtherNetAccount);
		for(String rule : rules){
			String temp[] = rule.split(",", -1);
			if(temp.length == 2){
				m_mapOtherNet.put(temp[0], temp[1]);
			}
		}
	}
	
	public String getPattern(String host){
		return m_mapOtherNet.getOrDefault(host, null);
	}
}
