package model.module;

import java.util.ArrayList;

import dpi.Dpi;
import model.BaseModel;
import model.ModelInfo;

public class WechatBlogContentRecognitionModel extends BaseModel{
	private final String m_strOutputInsideSeperator = "|";

	public WechatBlogContentRecognitionModel(ModelInfo info) {
		super(info);
	}

	@Override
	public void load(ModelInfo info) {
		// TODO Auto-generated method stub		
	}

	@Override
	public String recognize(Dpi dpi) {
		
		return null;
	}

	@Override
	public ArrayList<String> getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getModelIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
}
