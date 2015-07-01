package sqlite.model;

public class tb_list {
	private String title;
	private String content;
	public tb_list(){
		super();
	}
	
	public tb_list(String title,String content){
		super();
		this.title=title;
		this.content=content;
	}
	
	public  String gettitle(){ 
		return title;
	}
	public void settitle(String title){
		this.title=title;
	}
	
	public String getcontent(){
		return content;
	}
	public void setcontent(String content){
		this.content= content;
	}
	

}

