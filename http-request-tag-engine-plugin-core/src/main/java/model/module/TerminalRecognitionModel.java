package model.module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import model.BaseModel;
import model.ModelInfo;
import dpi.Dpi;

public class TerminalRecognitionModel extends BaseModel{

	private JedisCluster jc;
	
	public TerminalRecognitionModel(ModelInfo info){
		super(info);
	}
	
	public void loadData(ModelInfo info){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		
		String[] serverList = info.getServer().split(",", -1);
		for(String server : serverList){
			String[] cols = server.split(":", -1);
			String host = cols[0];
			int port = Integer.parseInt(cols[1]);
			jedisClusterNodes.add(new HostAndPort(host, port));
		}		
		jc = new JedisCluster(jedisClusterNodes, poolConfig);
	}
	
	@Override
	public String recognize(Dpi dpi) {
		String ua = dpi.getUserAgent();
		String result = null;
		if(ua.contains("Build")){
			String t = ua.substring(0, ua.indexOf("Build"));
	    	String temp = t.substring(t.lastIndexOf(";")+1, t.length());
	    	if(! filterTerminal(temp)){
	    		//添加映射undo
	    		result = temp;
	    	}
		}
		return result;
	}
	
	private String getStandardTerminal(String temp){
		String key = "Terminal_" + temp; 
		String result = jc.get(key);
		if(result != null){
			return result;
		}else{
			return temp;
		}		
	}
	
	private boolean filterTerminal(String temp){
		boolean tag = false;
		//过滤undo
		return tag;
	}
}
