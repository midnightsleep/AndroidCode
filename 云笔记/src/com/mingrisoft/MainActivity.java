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
    
    EditText et_name, et_password;// ����2��EditText����
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button Btn1 = (Button)findViewById(R.id.register);//��ȡ��ť��Դ    
        Btn1.setOnClickListener(new Button.OnClickListener(){//��������    
            public void onClick(View view) {    
            	Intent intent=new Intent(MainActivity.this,OneActivity.class); 
            	startActivity(intent);
            }    
  
        });
        
        Button Btn2 = (Button)findViewById(R.id.login);//��ȡ��ť��Դ    
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        Btn2.setOnClickListener(new Button.OnClickListener(){//��������    
            public void onClick(View view) {
            	String name=et_name.getText().toString();
				String pass=et_password.getText().toString();
				
				Log.i("TAG",name+"_"+pass);
				Inuser inuser=new Inuser(MainActivity.this);
				boolean flag=inuser.login(name, pass);
				if(flag){
					Log.i("TAG","��¼�ɹ�");
					Toast.makeText(MainActivity.this, "��¼�ɹ�", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(MainActivity.this,Mylist.class); 
	            	startActivity(intent);
				}else{
					Log.i("TAG","��¼ʧ��");
					Toast.makeText(MainActivity.this, "��¼ʧ��", Toast.LENGTH_LONG).show();
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





















