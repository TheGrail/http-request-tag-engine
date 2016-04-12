public class AtomIdOutput {
	
	private String atomId;
	private String atomIdContent;
	private String dpi;
	private String timeStamp;
	
	public AtomIdOutput(){		
	}

	public String getAtomId() {
		return atomId;
	}

	public String getDpi() {
		return dpi;
	}

	public void setAtomId(String atomId) {
		this.atomId = atomId;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getAtomIdContent() {
		return atomIdContent;
	}

	public void setAtomIdContent(String atomIdContent) {
		this.atomIdContent = atomIdContent;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}