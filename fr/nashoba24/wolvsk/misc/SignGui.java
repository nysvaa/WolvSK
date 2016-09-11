package fr.nashoba24.wolvsk.misc;

public class SignGui {

	private String[] lines;
	private Integer id;
	private String name;

	public SignGui(String[] arg0, Integer arg1, String arg2) {
		this.lines = arg0;
		this.id = arg1;
		this.name = arg2;
	}
	
	public String getLine(Integer line) {
		if(line>3 || line<0) {
			return null;
		}
		else {
			return lines[line];
		}
	}
	
	public Integer getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
