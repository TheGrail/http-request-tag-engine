package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class TerminalModel implements IModel {
	
	private HashMap<String, String> m_mapTerminal;
	private Properties prop;
	private String regex;
	
	public TerminalModel(String propFilePath){
		m_mapTerminal = new HashMap<String, String>();
		this.prop = new Properties();
		this.regex = null;
		loadData(propFilePath);
	}

	 public void loadData(String propFilePath) {
		FileInputStream in;
		try {
			in = new FileInputStream(propFilePath);
			prop.load(in);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public String getPattern(String host){
		if(host.contains("")){
			this.regex = prop.getProperty("", "");
		}
		return regex;
		
	}

	public void loadData() {
		// TODO Auto-generated method stub
		
	}
}
