package sqlite.dao;

import sqlite.model.tb_user;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Inuser {
	private DBOpenHelper helper;// 创建DBOpenHelper对象
	private SQLiteDatabase db;// 创建SQLiteDatabase对象

	public Inuser(Context context)// 定义构造函数
		{
			helper = new DBOpenHelper(context);// 初始化DBOpenHelper对象
		}

		/**
		 * 添加用户
		 */
		public void add(tb_user t) {
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
			// 执行添加收入信息操作
			ContentValues values=new ContentValues();
			values.put("username",t.getusername());
			values.put("passname",t.getpassname());
			db.insert("user", null, values);
			db.close();

		}
}
