package sqlite.model;

public class tb_user{
	private String username;
	private String passname;
	public tb_user(){
		super();
	}
	
	public tb_user(String username,String passname){
		super();
		this.username=username;
		this.passname=passname;
	}
	
	public  String getusername(){ 
		return username;
	}
	public void setusername(String username){
		this.username=username;
	}
	
	public String getpassname(){
		return passname;
	}
	public void setpassname(String passname){
		this.passname= passname;
	}
	

}
