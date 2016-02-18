
public class BasePlugin {

	public BasePlugin(){
		
	}
	
	public String escapeSeperator(String seperator){
		if(seperator.equals("|")){
			seperator = "\\|";
		}
		return seperator;
	}
}
