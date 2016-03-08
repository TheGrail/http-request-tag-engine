package model;

import java.util.HashMap;

import dpi.Dpi;

public abstract class BaseModel {
	
	public BaseModel(ModelInfo info){
		loadData(info);
	}
	
	public abstract void loadData(ModelInfo info);
	public abstract String recognize(Dpi dpi);
}
