package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//mysql工具类
public class MysqlManager {
	//private static final Logger logger = LoggerFactory.getLogger(MysqlManager.class);
	
	private String url;
	private String driver;
	private String user;
	private String password;
	private String checkSQL;
	private Integer initSize;
	private Integer maxActive;

	private BasicDataSource bds;

	public MysqlManager(String propFilePath){
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(propFilePath);
			prop.load(in);
		} catch (IOException e) {
			//logger.error("Read config error {}", e.getMessage());
		}
		
		//参数待定
		this.driver = prop.getProperty("", "");
		this.url = prop.getProperty("db_url", "");
		this.user = prop.getProperty("", "");
		this.password = prop.getProperty("", "");
		this.checkSQL = prop.getProperty("", "");
		this.initSize = Integer.parseInt(prop.getProperty("", ""));
		this.maxActive = Integer.parseInt(prop.getProperty("", ""));

		bds = new BasicDataSource();
		bds.setDriverClassName(this.driver);
		bds.setUrl(this.url);
		if (user != null && user.length() > 0) {
			bds.setUsername(this.user);
		}
		if (password != null && password.length() > 0) {
			bds.setPassword(this.password);
		}
		bds.setInitialSize(this.initSize);
		// bds.setDefaultReadOnly(true);
		bds.setMinIdle(2);
		//bds.setMaxIdle(30);
		bds.setMaxWaitMillis(60000);
		bds.setMaxIdle(this.maxActive);
		bds.setTestOnBorrow(true);
		bds.setTestWhileIdle(true);
		if (checkSQL != null && checkSQL.length() > 0) {
			bds.setValidationQuery(this.checkSQL);
		}
	}
	
	//host,pattern在一个字段
	public List<String> initFromMysqlAccount(NameToSqlAccount s){
		Connection conn = null;
		try {
			conn = bds.getConnection();
			Statement stmt = conn.createStatement();

			String sql;
			switch(s){
			case OtherEmailRecognition:
				sql = NameToSqlAccount.OtherEmailRecognition.getSql();
				break;
			default:
				sql = null;
			
			}
			
			ResultSet iprs = stmt.executeQuery(sql);
			List<String> rules = new ArrayList<String>();
			String rule;
			while (iprs.next()) {
				//修改为host,pattern
				
				rule = String.format("%s,%s", iprs.getString(1),iprs.getString(2));
				rules.add(rule);
			}
			iprs.close();
			stmt.close();
			return rules;
		} catch (SQLException e) {
			//logger.error("Mysql sql error {}", e.getMessage());
			return null;
		} finally {
			if (conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					//logger.error("Mysql connection close error {}", e.getMessage());
				}
		}		
	}
	
	//host,pattern,type分开
	public List<String> initFromMysqlContent(NameToSqlContent s){
		Connection conn = null;
		try {
			conn = bds.getConnection();
			Statement stmt = conn.createStatement();

			String sql;
			switch(s){
			case BusinessContent:
				sql = NameToSqlContent.BusinessContent.getSql();
				break;
			default:
				sql = null;
			
			}
			List<String> rules = new ArrayList<String>();
			if(sql != null){
				ResultSet iprs = stmt.executeQuery(sql);			
				String rule;
				while (iprs.next()) {
					//修改为host,pattern,type				
					rule = String.format("%s,%s,%s", iprs.getString(1),iprs.getString(2),iprs.getString(3));
					rules.add(rule);
				}
				iprs.close();
				stmt.close();
			}			
			return rules;

		} catch (SQLException e) {
			//logger.error("Mysql sql error {}", e.getMessage());
			return null;
		} finally {
			if (conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					//logger.error("Mysql connection close error {}", e.getMessage());
				}
		}		
	}
	
	public int getNumActive() {
		return bds.getNumActive();
	}

	public int getNumIdle() {
		return bds.getNumIdle();
	}

	public void close() {
		if (bds != null) {
			try {
				bds.close();
				bds = null;
			} catch (SQLException ex) {
				bds = null;
			}
		}
	}

	public Connection getConn() throws SQLException {
		return bds.getConnection();
	}
}
