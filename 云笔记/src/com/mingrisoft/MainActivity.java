package com.mingrisoft;


import sqlite.dao.Inuser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    
    EditText et_name, et_password;// 创建2个EditText对象
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button Btn1 = (Button)findViewById(R.id.register);//获取按钮资源    
        Btn1.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View view) {    
            	Intent intent=new Intent(MainActivity.this,OneActivity.class); 
            	startActivity(intent);
            }    
  
        });
        
        Button Btn2 = (Button)findViewById(R.id.login);//获取按钮资源    
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        Btn2.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View view) {
            	String name=et_name.getText().toString();
				String pass=et_password.getText().toString();
				
				Log.i("TAG",name+"_"+pass);
				Inuser inuser=new Inuser(MainActivity.this);
				boolean flag=inuser.login(name, pass);
				if(flag){
					Log.i("TAG","登录成功");
					Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(MainActivity.this,Mylist.class); 
	            	startActivity(intent);
				}else{
					Log.i("TAG","登录失败");
					Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_LONG).show();
				}
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
    
    
   /* public void click(View view){
    	Intent intent=new Intent(this,OneActivity.class);
    	startActivity(intent);
    }
    */
    
    
    
}





















