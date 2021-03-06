package model.module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import dpi.Dpi;
import model.BaseModel;
import model.ModelInfo;

public class SrcIp2UseridRecognitionModel extends BaseModel {

	JedisCluster jc;
	private final String m_strOutputSeperator = "^";
	
	public SrcIp2UseridRecognitionModel(ModelInfo info) {
		super(info);
	}

	@Override
	public void load(ModelInfo info) {
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
		String srcip = dpi.getSourceIp();
		int srcport = dpi.getSourcePort();
		String userid = null;
		
		// non NAT
		if(userid == null){
			String key = "1_" + srcip;
			userid = jc.get(key);
		}
		
		if(userid == null){
			String key = "2_" + srcip;
			Map<String, String> map = jc.hgetAll(key);
			for(Map.Entry<String, String> entry : map.entrySet()){
				String[] portList = entry.getKey().split("_", -1);
				int startPort = Integer.parseInt(portList[0]);
				int endPort = Integer.parseInt(portList[1]);
				if(srcport >= startPort && srcport <= endPort){
					userid = entry.getValue();
					break;
				}
			}
		}
		StringBuilder result = new StringBuilder();
		if(userid == null){
			dpi.setUid(srcip);
			return String.format("%s%s%s", "sourceIp", m_strOutputSeperator, srcip);
		}else{
			dpi.setUid(userid);
			result.append("userId");
			result.append(m_strOutputSeperator);
			result.append(userid);
			return result.toString();
		}
	}

	@Override
	public ArrayList<String> getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getModelIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
}