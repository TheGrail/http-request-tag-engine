import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import model.BaseModel;
import model.module.EmailRecognitionModel;
import model.module.OtherNetRecognitionModel;
import model.module.TerminalRecognitionModel;
import dpi.Dpi;


public class AtomIdTaggingPlugin extends BasePlugin {
	
	private BaseModel m_modelEmailRecognition;
	private BaseModel m_modelOtherNetRecognition;
	private BaseModel m_modelTerminalRecognition;
	
	private final String m_strOutputSeperator = "^";
	private final String m_strOutputInsideSeperator = "|";
	
	public AtomIdTaggingPlugin(String seperator, List<String> fields) throws DocumentException{
		super(seperator, fields);
		m_modelEmailRecognition = new EmailRecognitionModel(getModelInfo("email-recognition"));
		m_modelOtherNetRecognition = new OtherNetRecognitionModel(getModelInfo("other-net-recognition"));
		m_modelTerminalRecognition = new TerminalRecognitionModel(getModelInfo("terminal-recognition"));
	}	
	
	public String tagging(String line){
		System.out.println(line);
		Dpi dpi = new Dpi(line, getDpiInfo());
		
		StringBuilder output = new StringBuilder();
		
		{
			String userid = dpi.getUid();
			String type = getDpiInfo().getFieldInfo("userid").getType();
			output.append(userid);
			output.append(m_strOutputSeperator);
			output.append(type);
			output.append(m_strOutputSeperator);
		}
		
		{
			String terminal = m_modelTerminalRecognition.recognize(dpi);
			String type = "1";
			output.append(terminal);
			output.append(m_strOutputSeperator);
			output.append(type);
			output.append(m_strOutputSeperator);
		}
		
		{
			String email = m_modelEmailRecognition.recognize(dpi);
			String type = "email";
			output.append(email);
			output.append(m_strOutputSeperator);
			output.append(type);
			output.append(m_strOutputSeperator);
		}
		
		{
			String othernet = m_modelOtherNetRecognition.recognize(dpi);
			String type = "othernet";
			output.append(othernet);
			output.append(m_strOutputSeperator);
			output.append(type);
			output.append(m_strOutputSeperator);
		}
		
		{
			output.append(dpi.getUserAgent());
		}
				
		System.out.println(output.toString());
		return output.toString();
	}
	
}
