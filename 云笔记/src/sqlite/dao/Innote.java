package sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import sqlite.model.tb_list;
import sqlite.model.tb_user;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Innote {
	private DBOpenHelper helper;// 创建DBOpenHelper对象
	private SQLiteDatabase db;// 创建SQLiteDatabase对象

	public Innote(Context context)// 定义构造函数
		{
			helper = new DBOpenHelper(context);// 初始化DBOpenHelper对象
		}

		/**
		 * 添加笔记
		 */
		public void add_list(tb_list t) {
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
			// 执行添加收入信息操作
			ContentValues values=new ContentValues();
			values.put("title",t.gettitle());
			values.put("content",t.getcontent());
			db.insert("tb_list", null, values);
			db.close();

		}
		
		public List<tb_list> queryAll(){
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
			Cursor c=db.query("acount", null, null, null, null, null,"balance DESC");
			List<tb_list> list=new ArrayList<tb_list>();
			while(c.moveToNext()){
				String title=c.getString(1);
				String content=c.getString(2);
				list.add(new tb_list(title,content));
			}
			c.close();
			db.close();
			return list;
		}
	
}

