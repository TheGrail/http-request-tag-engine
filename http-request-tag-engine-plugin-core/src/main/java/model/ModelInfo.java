package model;

import java.util.HashMap;
import java.util.Map;

public class ModelInfo {

	public static final int CONNECTION_RAWDATA = 0;
	public static final int CONNECTION_MYSQL = 1;
	public static final int CONNECTION_REDIS_CLUSTER = 2;
	
	private String m_strName;
	private String m_strModule;
	private int m_strConnectionType;
	private String m_strHost = null;
	private String m_strPort = null;
	private String m_strUsername = null;
	private String m_strPassword = null;
	private String m_strSchema = null;
	private String m_strTable = null;
	private HashMap<String, String> m_mapRawData = null;
	
	// mysql
	public ModelInfo(String name, String module, int type, String host, String port, String username, String password, String schema, String table){
		m_strName = name;
		m_strModule = module;
		m_strConnectionType = type;
		m_strHost = host;
		m_strPort = port;
		m_strUsername = username;
		m_strPassword = password;
		m_strSchema = schema;
		m_strTable = table;
	}
	
	// rawdata
	public ModelInfo(String name, String module, int type, HashMap<String, String> map){
		m_strName = name;
		m_strModule = module;
		m_strConnectionType = type;
		m_mapRawData = map;
	}
	
	public String getName(){
		return m_strName;
	}
	
	public String getModule(){
		return m_strModule;
	}
	
	public int getConnectionType(){
		return m_strConnectionType;
	}
	
	public String getHost(){
		return m_strHost;
	}
	
	public String getPort(){
		return m_strPort;
	}
	
	public String getUsername(){
		return m_strUsername;
	}
	
	public String getPassword(){
		return m_strPassword;
	}
	
	public String getSchema(){
		return m_strSchema;
	}
	
	public String getTable(){
		return m_strTable;
	}
	
	public HashMap<String, String> getRawData(){
		return m_mapRawData;
	}
}
