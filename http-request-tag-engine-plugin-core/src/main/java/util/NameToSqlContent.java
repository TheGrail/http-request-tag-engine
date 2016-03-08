package util;

//内容标签SQL目录
public enum NameToSqlContent {
	BusinessContent("", 3);
	
	private String sql;
	private int index;
	
	private NameToSqlContent(String sql, int index){
		this.sql = sql;
		this.index = index;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}