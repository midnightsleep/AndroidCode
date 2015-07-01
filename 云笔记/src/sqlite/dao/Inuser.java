package sqlite.dao;

import sqlite.model.tb_user;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Inuser {
	private DBOpenHelper helper;// ����DBOpenHelper����
	private SQLiteDatabase db;// ����SQLiteDatabase����

	public Inuser(Context context)// ���幹�캯��
		{
			helper = new DBOpenHelper(context);// ��ʼ��DBOpenHelper����
		}

		/**
		 * ����û�
		 */
		public void add(tb_user t) {
			db = helper.getWritableDatabase();// ��ʼ��SQLiteDatabase����
			// ִ�����������Ϣ����
			ContentValues values=new ContentValues();
			values.put("username",t.getusername());
			values.put("passname",t.getpassname());
			db.insert("tb_user", null, values);
			db.close();

		}
		
		public boolean login(String username,String passname){
			db=helper.getReadableDatabase();
			String sql="select * from tb_user where username=? and passname=?";
			Cursor cursor=db.rawQuery(sql, new String[]{username,passname});		
			if(cursor.moveToFirst()==true){
				cursor.close();
				return true;
			}
			return false;
		}
}
