package model.module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BaseModel;
import model.ModelInfo;
import dpi.Dpi;

public class OtherNetRecognitionModel extends BaseModel implements Runnable{

	private HashMap<String, String> m_mapData;
	private Statement m_stmtCurrent;
	private String m_strQuery;
	
	private Thread m_threadUpdater;
	private long m_lCheckUpdateInterval;
	private Lock m_threadLock = new ReentrantLock();
	private Condition m_cdnInitialized = m_threadLock.newCondition();
	
	public OtherNetRecognitionModel(ModelInfo info){
		super(info);
	}
	
	public void run() {
		while(true){
			m_threadLock.lock();
			try{
				//System.out.println("update " + this.getClass().getName() + " from mysql");
				ResultSet rs = m_stmtCurrent.executeQuery(m_strQuery);
				m_mapData.clear();
				while(rs.next()) {
					String rule = rs.getString("rule");
					String[] cols = rule.split(",", 2);
					String key = cols[0];
					String value = cols[1];
					m_mapData.put(key, value);
				}
				rs.close();
				m_cdnInitialized.signalAll();
				//System.out.println("updated - " + m_mapData.size());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				m_threadLock.unlock();
				try {
					Thread.sleep(m_lCheckUpdateInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void load(ModelInfo info){
		m_mapData = new HashMap<String, String>();
		m_lCheckUpdateInterval = info.getInterval();
		
		if(info.getConnectionType() == ModelInfo.CONNECTION_MYSQL){
			// 建立MySQL连接，以流缓冲的方式读取，缓存Statement
			try {
				String driver = "com.mysql.jdbc.Driver";
				String url = String.format("jdbc:mysql://%s/%s?defaultFetchSize=1000", info.getServer(), info.getSchema());
				String username = info.getUsername();
				String password = info.getPassword();
				m_strQuery =  String.format("select * from %s", info.getTable());
			
				Class.forName(driver);
			
				Connection conn = DriverManager.getConnection(url, username, password);
				m_stmtCurrent = conn.createStatement();
				m_threadUpdater = new Thread(this, "EmailRecognitionModelUpdater");
				m_threadUpdater.start();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String recognize(Dpi dpi) {
		m_threadLock.lock();
		if(m_mapData == null || m_mapData.size() == 0){
			try {
				m_cdnInitialized.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		m_threadLock.unlock();
		
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
