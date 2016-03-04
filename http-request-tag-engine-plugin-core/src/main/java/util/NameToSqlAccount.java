package util;

//账号识别SQL目录
public enum NameToSqlAccount {
	OtherEmailRecognition("select type,rule from lbl_la_email_user", 1),
	OtherNetAccount("select type,rule from lbl_la_othernet_user", 2);
	
	private String sql;
	private int index;
	
	private NameToSqlAccount(String sql, int index){
		this.sql = sql;
		this.index = index;
	}
	
	public String getSql(int index){
		for (NameToSqlAccount n : NameToSqlAccount.values()){
			if(n.getIndex() == index){
				return n.sql;
			}
		}
		return null;
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