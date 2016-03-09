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
					//
				}
			break;
			case ModelInfo.CONNECTION_REDIS_CLUSTER:
				{
					//
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
