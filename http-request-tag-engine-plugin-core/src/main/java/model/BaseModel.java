package model;

import dpi.Dpi;

public abstract class BaseModel{
	
	public BaseModel(ModelInfo info){
		load(info);
	}
	
	public abstract void load(ModelInfo info);
	public abstract String recognize(Dpi dpi);

}
