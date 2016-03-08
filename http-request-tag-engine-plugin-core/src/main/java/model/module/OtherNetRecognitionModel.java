package model.module;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BaseModel;
import model.ModelInfo;
import dpi.Dpi;

public class OtherNetRecognitionModel extends BaseModel{

	public OtherNetRecognitionModel(ModelInfo info){
		super(info);
	}
	
	@Override
	public String recognize(Dpi dpi) {
		String host = dpi.getHost();
		String url = dpi.getUrl();
		HashMap<String, String> mapData = getData();
		if(mapData.containsKey(host)){
			Pattern pattern = Pattern.compile(mapData.get(host));
			Matcher matcher = pattern.matcher(url);
			if(matcher.find()){
				return matcher.group(1);
			}
		}
		return null;
	}

}
