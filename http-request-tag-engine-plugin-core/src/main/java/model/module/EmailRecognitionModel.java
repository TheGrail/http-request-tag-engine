package model.module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BaseModel;
import model.ModelInfo;
import dpi.Dpi;

public class EmailRecognitionModel extends BaseModel{
	
	HashMap<String, String> m_mapData;
	
	public EmailRecognitionModel(ModelInfo info){
		super(info);
	}
	
	public void loadData(ModelInfo info){
		switch(info.getConnectionType()){
			case ModelInfo.CONNECTION_RAWDATA:
				{
					m_mapData = info.getRawData();
				}
				break;
			case ModelInfo.CONNECTION_MYSQL:
				{
					m_mapData = new HashMap<String, String>();
					
					String driver = "com.mysql.jdbc.Driver";
					String url = String.format("jdbc:mysql://%s/%s", info.getServer(), info.getSchema());
					String username = info.getUsername();
					String password = info.getPassword();
					String sql =  String.format("select * from %s", info.getTable());
					try {
						Class.forName(driver);
						Connection conn = DriverManager.getConnection(url, username, password);
						Statement statement = conn.createStatement();
						ResultSet rs = statement.executeQuery(sql);
						while(rs.next()) {
							String rule = rs.getString("rule");
							String[] cols = rule.split(",", 2);
							String key = cols[0];
							String value = cols[1];
							m_mapData.put(key, value);
						}
						rs.close();
						conn.close();
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			break;
			case ModelInfo.CONNECTION_REDIS_CLUSTER:
				{
				}
				break;
			default:
				break;
		}
	}

	@Override
	public String recognize(Dpi dpi) {
		String host = dpi.getHost();
		String url = dpi.getUrl();
		if(m_mapData.containsKey(host)){
			Pattern pattern = Pattern.compile(m_mapData.get(host));
			Matcher matcher = pattern.matcher(url);
			if(matcher.find()){
				return matcher.group(1);
			}
		}
		return null;
	}

}
