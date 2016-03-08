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

public class TerminalRecognitionModel extends BaseModel{

	public TerminalRecognitionModel(ModelInfo info){
		super(info);
	}
	
	public void loadData(ModelInfo info){
		switch(info.getConnectionType()){
			case ModelInfo.CONNECTION_RAWDATA:
				{
					//
				}
				break;
			case ModelInfo.CONNECTION_MYSQL:
				{
					String driver = "com.mysql.jdbc.Driver";
					String url = String.format("jdbc:mysql://%s:%s/%s", info.getHost(), info.getPort(), info.getSchema());
					String username = info.getUsername();
					String password = info.getPassword();
					String sql =  String.format("select * from %s", info.getTable());
					try {
						Class.forName(driver);
						Connection conn = DriverManager.getConnection(url, username, password);
						Statement statement = conn.createStatement();
						ResultSet rs = statement.executeQuery(sql);
						while(rs.next()) {
								System.out.println(rs.getString("rule")); 
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
		return null;
	}

}
