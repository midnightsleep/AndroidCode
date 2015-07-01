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
	private DBOpenHelper helper;// ����DBOpenHelper����
	private SQLiteDatabase db;// ����SQLiteDatabase����

	public Innote(Context context)// ���幹�캯��
		{
			helper = new DBOpenHelper(context);// ��ʼ��DBOpenHelper����
		}

		/**
		 * ��ӱʼ�
		 */
		public void add_list(tb_list t) {
			db = helper.getWritableDatabase();// ��ʼ��SQLiteDatabase����
			// ִ�����������Ϣ����
			ContentValues values=new ContentValues();
			values.put("title",t.gettitle());
			values.put("content",t.getcontent());
			db.insert("tb_list", null, values);
			db.close();

		}
		
		public List<tb_list> queryAll(){
			db = helper.getWritableDatabase();// ��ʼ��SQLiteDatabase����
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

