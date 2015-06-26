package sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final int VERSION=1;
	private static final String DBNAME="biji.db";
	
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//db.execSQL("CREATE TABLE tb_user(user varchar(20), passname varchar(20));");
		//创建用户表
		db.execSQL("create table tb_user (user varchar(20),"
				+ "password varchar(20))");
		
		//System.out.print("-----------------------------------------------ok");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
