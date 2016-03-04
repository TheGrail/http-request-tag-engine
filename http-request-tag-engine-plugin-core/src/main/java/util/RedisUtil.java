package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//Redis操作工具类
public class RedisUtil {
	
	private Jedis jedis;
	private JedisPool jedisPool;
	
	private String addr;
	private int port;
	private String auth;
	private int maxActive;
	private int maxIdle;
	private int maxWait;
	private int timeout;
	private boolean testOnBorrow;
	private JedisPoolConfig config;
	
	//private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	public RedisUtil(String propFilePath){
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(propFilePath);
			prop.load(in);
		} catch (IOException e) {
			//logger.error("Read config error {}", e.getMessage());
		}			
		initialPool(prop);
	}

	private void initialPool(Properties prop) {
		//参数待定
		this.addr = prop.getProperty("addr", "");
		this.port = Integer.parseInt(prop.getProperty("port", ""));
		this.timeout = Integer.parseInt(prop.getProperty("timeout", ""));
		this.auth = prop.getProperty("auth", "");
		this.maxActive = Integer.parseInt(prop.getProperty("maxActive", ""));
		this.maxIdle = Integer.parseInt(prop.getProperty("maxIdle", ""));
		this.maxWait = Integer.parseInt(prop.getProperty("maxWait", ""));
		this.testOnBorrow = Boolean.parseBoolean(prop.getProperty("testOnBorrow", ""));
		
		this.config = new JedisPoolConfig();
		this.config.setMaxActive(this.maxActive);
		this.config.setMaxIdle(this.maxIdle);
		this.config.setMaxWait(this.maxWait);
		this.config.setTestOnBorrow(this.testOnBorrow);
		
		
		try{
			jedisPool = new JedisPool(config, addr, port, timeout, auth);
		} catch (Exception e){
			//logger.error("Initial jedispool error {}", e.getMessage());
		}			
	}
	
	private synchronized Jedis getJedis(){
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
        	//logger.error("Get jedis error {}", e.getMessage());
            return null;
        }
	}
	
	private void returnResource(final Jedis jedis) {
		if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
	}
	
	public String getStringValue(String key){
		final Jedis jedis = getJedis();
		String value = null;
		if(jedis != null){
			value = jedis.get(key);
			//没有key返回什么？
		}
		returnResource(jedis);
		return value;
	}
}