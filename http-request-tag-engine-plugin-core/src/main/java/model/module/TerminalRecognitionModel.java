package model.module;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BaseModel;
import model.ModelInfo;
import dpi.Dpi;

public class TerminalRecognitionModel extends BaseModel{

	public TerminalRecognitionModel(ModelInfo info){
		super(info);
	}
	
	@Override
	public String recognize(Dpi dpi) {
		return null;
	}

}
