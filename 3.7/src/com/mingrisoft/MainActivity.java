package com.mingrisoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    
    @Override
    
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
        Log.i("MainActivity","onCreate()");

    }
    
    protected void onStart(){
    	super.onStart();
    	Log.i("MainActivity", "onStart()");
    }
    
    protected void onRestart(){
    	super.onRestart();
    	Log.i("MainActivity", "onRestart()");
    }
    
    protected void onResume(){
    	super.onResume();
    	Log.i("MainActivity", "onResume()");
    }
    
    protected void onPause(){
    	super.onPause();
    	Log.i("MainActivity", "onPause()");
    }
    
    protected void onStop(){
    	super.onStop();
    	Log.i("MainActivity", "onStop()");
    }
    
    protected void onDestroy(){
    	super.onDestroy();
    	Log.i("MainActivity", "onDestroy()");
    }
    
    
   /* public void click(View view){
    	Intent intent=new Intent(this,OneActivity.class);
    	startActivity(intent);
    }
    */
    
    
    
}





















