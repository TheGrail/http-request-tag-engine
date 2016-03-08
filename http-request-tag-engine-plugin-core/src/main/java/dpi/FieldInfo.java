package dpi;

public class FieldInfo {
	private String name;
	private Integer index;
	private String encode;
	private String type;
	
	public FieldInfo(String name, int index, String encode, String type){
		this.name = name;
		this.index = index;
		this.encode = encode;
		this.type = type;
	}
	
	public String getName(){
		return name;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getEncode(){
		return encode;
	}
	
	public String getType(){
		return type;
	}
}
