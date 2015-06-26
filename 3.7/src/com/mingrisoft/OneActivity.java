package com.mingrisoft;



import sqlite.dao.DBOpenHelper;
import sqlite.dao.Inuser;
import sqlite.model.tb_user;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OneActivity extends Activity {
    /** Called when the activity is first created. */
    
    EditText et_name1, et_againpassword;// 创建2个EditText对象
    Button tj_regist;// 创建Button对象
  //  private Inuser inuser;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneactivity);
        
        et_name1 = (EditText) findViewById(R.id.et_name1);
        et_againpassword = (EditText) findViewById(R.id.et_againpassword);
        tj_regist = (Button) findViewById(R.id.tj_regist);
        
        Button Btn1 = (Button)findViewById(R.id.tj_regist);//获取按钮资源    
        Btn1.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	String username=et_name1.getText().toString().trim();
            	String passname=et_againpassword.getText().toString().trim();
            	
            	Inuser inuser=new Inuser(OneActivity.this);
            	tb_user t=new tb_user(username,passname);
            	inuser.add(t);
            	
            	Toast.makeText(OneActivity.this, "【注册成功】", Toast.LENGTH_SHORT).show();
            	
            	
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
