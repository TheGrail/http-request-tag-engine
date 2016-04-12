import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.BaseModel;
import model.module.JDContentRecognitionModel;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;

import dpi.Dpi;

public class ContentTaggingPlugin extends BasePlugin{
	
	//private final String m_strOutputSeperator = "^";
	//private final String m_strOutputInsideSeperator = "|";
	
	private BaseModel m_modelJDContentRecognition;
	//private BaseModel m_modelWechatBlogContentRecognition;
	private static final int JDContentModelIndex = 3;
	private static final int WechaBlogContentModelIndex = 4;
	
	private HashMap<String, ArrayList<Integer>> m_mapHost2ModelIndex;

	public ContentTaggingPlugin(String sep, List<String> fields)
			throws DocumentException, IOException {
		super(sep, fields);
		m_mapHost2ModelIndex = new HashMap<String, ArrayList<Integer>>();
		m_modelJDContentRecognition = new JDContentRecognitionModel(getModelInfo("jdContent-recognition"));
		updateMapHost2Model(m_modelJDContentRecognition);
		//m_modelWechatBlogContentRecognition = new WechatBlogContentRecognitionModel(getModelInfo("wechatblogContetn-recognition"));
		//updateMapHost2Model(m_modelWechatBlogContentRecognition);
	}
	
	private void updateMapHost2Model(BaseModel contentRecognitionModel) {
		for(String host: contentRecognitionModel.getHost()){		
			if(m_mapHost2ModelIndex.containsKey(host)){
				m_mapHost2ModelIndex.get(host).add(contentRecognitionModel.getModelIndex());
			} 
			else{
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(contentRecognitionModel.getModelIndex());
				m_mapHost2ModelIndex.put(host, temp);
			}
		}
	}

	@Override
	public String tagging(String line) {
		
		Dpi dpi = JSON.parseObject(line, Dpi.class);									
		String host = dpi.getHost();
		
		if(m_mapHost2ModelIndex.containsKey(host)){
			String result = "";
			for(int modelIndex: m_mapHost2ModelIndex.get(host)){
				switch (modelIndex) {
				case JDContentModelIndex:
					result = m_modelJDContentRecognition.recognize(dpi);
					break;					
				case WechaBlogContentModelIndex:
					//result = m_modelWechatBlogContentRecognition.recognize(dpi);
					break;					
				default:
					break;
				}
			}
			if(! result.isEmpty()){
				ContentOutput contentOutput = new ContentOutput();
				contentOutput.setContent(result);
				contentOutput.setTimeStamp(dpi.getTimestamp());
				contentOutput.setUid(dpi.getUid());
				return JSON.toJSONString(contentOutput);
			}
		}
		return null;
	}
}