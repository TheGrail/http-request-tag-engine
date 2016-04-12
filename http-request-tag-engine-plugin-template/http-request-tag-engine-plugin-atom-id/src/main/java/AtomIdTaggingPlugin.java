import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;

import model.BaseModel;
import model.module.EmailRecognitionModel;
import model.module.OtherNetRecognitionModel;
import model.module.QQRecognitionModel;
import model.module.SrcIp2UseridRecognitionModel;
import dpi.Dpi;
import dpi.DpiUtility;
import dpi.RawDpi;

public class AtomIdTaggingPlugin extends BasePlugin {
	
	private BaseModel m_modelSrcIp2UseridRecognition;
	private BaseModel m_modelEmailRecognition;
	private BaseModel m_modelOtherNetRecognition;
	private BaseModel m_modelQQRecognition;
	
	private static final int emailModelIndex = 1;
	private static final int otherNetModelIndex = 2;
	private final String m_strOutputSeperator = "^";
	
	private DpiFilter dpiFilter;
	private HashMap<String, ArrayList<Integer>> m_mapHost2ModelIndex;	
	
	public AtomIdTaggingPlugin(String seperator, List<String> fields) throws DocumentException, IOException{
		super(seperator, fields);
		dpiFilter = new DpiFilter();
		m_mapHost2ModelIndex = new HashMap<String, ArrayList<Integer>>();
		m_modelSrcIp2UseridRecognition = new SrcIp2UseridRecognitionModel(getModelInfo("srcip2userid-recognition"));
		m_modelEmailRecognition = new EmailRecognitionModel(getModelInfo("email-recognition"));
		updateMapHost2Model(m_modelEmailRecognition);
		m_modelOtherNetRecognition = new OtherNetRecognitionModel(getModelInfo("other-net-recognition"));
		updateMapHost2Model(m_modelOtherNetRecognition);
		m_modelQQRecognition = new QQRecognitionModel(getModelInfo("qq-recognition"));
	}	
	
	private void updateMapHost2Model(BaseModel recognitionModel) {
		for(String host: recognitionModel.getHost()){		
			if(m_mapHost2ModelIndex.containsKey(host)){
				m_mapHost2ModelIndex.get(host).add(recognitionModel.getModelIndex());
			} 
			else{
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(recognitionModel.getModelIndex());
				m_mapHost2ModelIndex.put(host, temp);
			}
		}
	}
			
	public String tagging(String line){	
		//过滤
		RawDpi rawdpi = new RawDpi(line, getDpiInfo(), "url");
		if(!dpiFilter.filter(rawdpi)){
			Dpi dpi = new Dpi(line, getDpiInfo());
			StringBuilder output = new StringBuilder();
			
			//账号
			{
				String userid = m_modelSrcIp2UseridRecognition.recognize(dpi).trim();
				output.append(userid);
				output.append(m_strOutputSeperator);
			}
			
			//UA
			{
				String ua = dpi.getUserAgent().trim();			
				output.append("userAgent");
				output.append(m_strOutputSeperator);
				output.append(DpiUtility.encode(ua, "md5"));
				output.append(m_strOutputSeperator);
			}
			
			//url账号
			{
				String account = "";							
				String host = dpi.getHost().trim();
				if(m_mapHost2ModelIndex.containsKey(host)){
					for(int modelIndex: m_mapHost2ModelIndex.get(host)){
			        	switch(modelIndex){
		        			case emailModelIndex:
		        			{
		        				account = m_modelEmailRecognition.recognize(dpi);
		        				if(!account.isEmpty()){
		        					output.append("Email");
			        				output.append(m_strOutputSeperator);		        
			        				output.append(account);
		        				}		        				
		        			}
		        			break;		        			
		        			case otherNetModelIndex:
		        			{
		        				account = m_modelOtherNetRecognition.recognize(dpi);
		        				if(!account.isEmpty()){
		        					output.append("OtherNet");
			        				output.append(m_strOutputSeperator);		        
			        				output.append(account);
		        				}
		        			}
		        			break;		        		
		        		default:
			        		break;
			        	}								        			        	
					}
				}
	        	//无账号信息 
	        	if(account.isEmpty()){
	        		output.append("Account");
		        	output.append(m_strOutputSeperator);
		        	output.append("");
		        	output.append(m_strOutputSeperator);
	        	}	
			}
			
			//cookie账号
			{
				String qq = "";							
				String host = dpi.getHost().trim();
				if(host.endsWith("qq.com")){
					qq = m_modelQQRecognition.recognize(dpi);
				}
				output.append("QQ");
	        	output.append(m_strOutputSeperator);
	        	output.append(qq);
			}
			
			AtomIdOutput atomIdOutput = new AtomIdOutput();
			atomIdOutput.setAtomIdContent(output.toString());
			atomIdOutput.setAtomId(DpiUtility.encode(output.toString(), "md5"));
			atomIdOutput.setDpi(JSON.toJSONString(dpi));
			atomIdOutput.setTimeStamp(dpi.getTimestamp());
			
			return JSON.toJSONString(atomIdOutput);			
		}		
		return null;
	}
}