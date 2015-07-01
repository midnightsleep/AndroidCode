package com.mingrisoft;

import sqlite.dao.Innote;
import sqlite.model.tb_list;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Dairylist extends Activity {
	EditText et_title, et_content;// 创建2个EditText对象
    Button btn_save,btn_cancel;// 创建Button对象
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dairylist);
        
        et_title=(EditText)findViewById(R.id.et_title);
        et_content=(EditText)findViewById(R.id.et_content);
        btn_save=(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        
        btn_save.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		String title=et_title.getText().toString().trim();
        		String content=et_content.getText().toString().trim();
        		
        		Innote innote=new Innote(Dairylist.this);
        		tb_list tl=new tb_list(title,content);
        		innote.add_list(tl);
        		
        		Toast.makeText(Dairylist.this, "【保存成功】", Toast.LENGTH_SHORT).show();
            	
            	Intent intent=new Intent(Dairylist.this,Mylist.class); 
            	startActivity(intent);
        	}
        });

}
	protected void onStart(){
    	super.onStart();
    }
    
    protected void onRestart(){
    	super.onRestart();
    }
    
    protected void onResume(){
    	super.onResume();
    }
    
    protected void onPause(){
    	super.onPause();
    }
    
    protected void onStop(){
    	super.onStop();
    }
    
    protected void onDestroy(){
    	super.onDestroy();
    }
}

