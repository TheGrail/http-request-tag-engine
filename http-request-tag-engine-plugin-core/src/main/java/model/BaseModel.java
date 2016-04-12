package model;

import java.util.ArrayList;
import dpi.Dpi;

public abstract class BaseModel{
		
	public BaseModel(ModelInfo info){
		load(info);
	}
	
	public abstract void load(ModelInfo info);
	public abstract String recognize(Dpi dpi);
	public abstract ArrayList<String> getHost();
	public abstract int getModelIndex();

}