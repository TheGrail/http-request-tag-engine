package model;

import java.util.HashMap;

public class TerminalModel implements IModel {
	private HashMap<String, String> m_mapTerminal;
	
	public TerminalModel(){
		m_mapTerminal = new HashMap<String, String>();
		loadData();
	}

	 public void loadData() {
		m_mapTerminal.put("ABCDEFG", "Huawei");
	}
	
	public String getTerminal(String s){
		return "Huawei";
	}
}
