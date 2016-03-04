import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.EmailRecognitionModel;
import model.TerminalModel;

import org.apache.commons.codec.binary.Base64;
import org.omg.DynamicAny.DynValueOperations;

import account.EmailRecognition;
import account.OtherNetRecognition;
import terminal.TerminalRecognition;
import util.MysqlManager;


public class AtomIdTaggingPlugin extends BasePlugin implements IPlugin {
	
	private MysqlManager mysqlManager;
	private EmailRecognition emailRecognition;
	private OtherNetRecognition otherNetRecognition;
	private TerminalRecognition terminalRecognition;
	
	private DpiInfo dpiInfo;
	private String seperator;
 
	private String inseperator;

	public AtomIdTaggingPlugin(String seperator, List<String> fields){
		super();
		//配置文件路径在这写死？还是入参？
		String propFilePath = "";
		mysqlManager = new MysqlManager(propFilePath);
		emailRecognition = new EmailRecognition(mysqlManager);
		otherNetRecognition = new OtherNetRecognition(mysqlManager);
		terminalRecognition = new TerminalRecognition(propFilePath);
		dpiInfo = new DpiInfo(seperator, fields);
		inseperator = "^";
	}	
	
	public void tagging(String line){
		System.out.println(line);
		Dpi dpi = new Dpi(line, seperator, dpiInfo.getDpiInfo());
		
		StringBuilder output = new StringBuilder();
		String host = dpi.getHost();
		String url = dpi.getUrl();
		String ua = dpi.getUa();
		
		String email = emailRecognition.getEmail(host, url);
		if(email != null){
			output.append("Email");
			output.append(inseperator);
			output.append(email);
		}
		output.append(inseperator);
		
		String otherNet = otherNetRecognition.getOtherNet(host, url);
		if(otherNet != null){
			output.append("OtherNet");
			output.append(inseperator);
			output.append(otherNet);
		}
		output.append(inseperator);
		
		String terminal = terminalRecognition.getTerminal(ua, host);
		if(terminal != null){
			
		}
				
		System.out.println(output.toString());
	}
	
}
