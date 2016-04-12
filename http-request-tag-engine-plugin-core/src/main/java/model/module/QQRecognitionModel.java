package model.module;

import java.util.ArrayList;

import dpi.Dpi;
import model.BaseModel;
import model.ModelInfo;

public class QQRecognitionModel extends BaseModel{

	public QQRecognitionModel(ModelInfo info) {
		super(info);
	}

	@Override
	public void load(ModelInfo info) {
		// TODO Auto-generated method stub
		
	}

	public String recognize(Dpi dpi) {
		String cookie = dpi.getCookies();
		String cs[] = cookie.split(";");
		String cc[];
		for(String c: cs){
			cc = c.split("=");
			if(cc.length==2){
				if(cc[0].equals("uin_cookie")){
					return cc[1];
				}
			} 
		}
		return "";
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