package model;

import java.util.HashMap;
import java.util.List;

import util.MysqlManager;
import util.NameToSqlContent;

//电商内容标签模型
public class BusinessContentModel implements IModel{
	
	private HashMap<String, String[]> m_mapBusinessContent;
	private MysqlManager mysqlManager;
	
	public BusinessContentModel(MysqlManager mysqlManager){
		this.m_mapBusinessContent = new HashMap<String, String[]>();
		this.mysqlManager = mysqlManager;
		loadData();
	}

	public void loadData() {
		List<String> rules = mysqlManager.initFromMysqlContent(NameToSqlContent.BusinessContent);
		if(rules != null){
			for(String rule : rules){
				String temp[] = rule.split(",", -1);
				if(temp.length == 3){
					String[] t = {temp[2], temp[0]};
					//host, (type, pattern)
					m_mapBusinessContent.put(temp[1], t);
				}		
			}
		}			
	}
	
	public String[] getPattern(String host){
		return m_mapBusinessContent.getOrDefault(host, null);
	}

}
