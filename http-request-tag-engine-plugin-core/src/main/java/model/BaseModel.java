package model;

import java.util.HashMap;

import dpi.Dpi;

public abstract class BaseModel {
	
	private HashMap<String, String> m_mapData;
	
	public BaseModel(ModelInfo info){
		loadData(info);
	}
	
	public void loadData(ModelInfo info){
		switch(info.getConnectionType()){
			case ModelInfo.CONNECTION_RAWDATA:
				m_mapData = info.getRawData();
				break;
			case ModelInfo.CONNECTION_MYSQL:
				break;
			case ModelInfo.CONNECTION_REDIS_CLUSTER:
				break;
			default:
				break;
		}
	}
	
	public abstract String recognize(Dpi dpi);
	
	public HashMap<String, String> getData(){
		return m_mapData;
	}
}
