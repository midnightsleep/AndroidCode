package com.mingrisoft;

import java.util.List;

import sqlite.dao.Innote;
import sqlite.model.tb_list;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Mylist extends Activity{
	/*********************
	private List<tb_list> list;
	private Innote innote;
	private MyAdapter adapter;
	private ListView show_list;
	**********************/
	
	String strType = "";// 创建字符串，记录管理类型
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylist);
        /********************
        innote=new Innote(this);
        list=innote.queryAll();
        adapter=new MyAdapter();
        show_list.setAdapter(adapter);
        **********************/
 //       ListView show_list=(ListView)findViewById(R.id.show_list);
        Button Btn1 = (Button)findViewById(R.id.createbj);//获取按钮资源    
        Btn1.setOnClickListener(new Button.OnClickListener(){//创建监听    
            public void onClick(View view) {    
            	Intent intent=new Intent(Mylist.this,Dairylist.class); 
            	startActivity(intent);
            }    
  
        });

}
	/************
	private class MyAdapter extends BaseAdapter{
		public int getCount(){
			return list.size();
		}
		public Object getItem(int position){
			return list.get(position);
		}
		public View getView(int position,View convertView,ViewGroup parent){
			View item=convertView!=null?convertView:View.inflate(getApplicationContext(), R.layout.item, null);
		}
	}
	
	*************/
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
