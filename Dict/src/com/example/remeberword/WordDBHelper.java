package com.example.remeberword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelper extends SQLiteOpenHelper {
	private SQLiteDatabase db;
	private static final String CREATE_SQL="create table wordtable" +
			"(_id integer primary key autoincrement," +
			"word text," +
			"detail text)";
	private static final String CREATE_SQLuser="create table usertable" +
			"(_id integer primary key autoincrement," +
			"username text," +
			"password text," +
			"ssex text," +
			"marrage text," +
			"favorite text," +
			"position text)";


	public WordDBHelper(Context context) {
		super(context, "word.db", null, 2);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db=db;
		db.execSQL(CREATE_SQL);	
		db.execSQL(CREATE_SQLuser);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		this.db=db;
		db.execSQL(CREATE_SQL);	
		db.execSQL(CREATE_SQLuser);

	}
	public int insertUser(String username,
			String password,String ssex,boolean marrage,
			String favorite,String position){
		int i=0;
		db=getWritableDatabase();
		
		ContentValues cv=new ContentValues();
		cv.put("username",username);
		cv.put("password",password);
		cv.put("ssex",ssex);
		cv.put("marrage",marrage);
		cv.put("favorite",favorite);
		cv.put("position",position);
		i=(int) db.insert("usertable", null, cv);
		db.close();
		return i;
	}
	
	public int insertDB(String word,String detail){
		int i=0;
		db=getWritableDatabase();
		
		ContentValues cv=new ContentValues();
		cv.put("detail",detail);
		cv.put("word",word);
		i=(int) db.insert("wordtable", null, cv);
		db.close();
		return i;
	}

	public int deleteWord(){
		db=getWritableDatabase();
		int i=0;

		if(db==null)
			db=getReadableDatabase();
		i=db.delete("wordtable", null,null);
		return i;
	}
	public int deleteWordByWord(String word){
		db=getWritableDatabase();
		int i=0;
		String[] args={word};
		if(db==null)
			db=getReadableDatabase();
		i=db.delete("wordtable", "word=?",args);
		return i;
	}
	public Cursor query(){
		db=getWritableDatabase();
		Cursor c=db.query("wordtable", null, null, null, null, null, "_id asc","");
		return c;			
	}
	public Cursor queryWordById(int id){
		db=getWritableDatabase();
		Cursor c=db.query("wordtable", null, "_id=?", new String[]{String.valueOf(id)}, null, null,  null);
		return c;
				
	}
	public Cursor queryUserByname(String username,String password){
		db=getWritableDatabase();
		Cursor c=db.query("usertable", null, "username=? and password=?", new String[]{username,password}, null, null,null);
		return c;		
	}
	public int updateDB(String word,String detail){
		db=getWritableDatabase();
		int i=0;
		ContentValues cv=new ContentValues();
		cv.put("word", word);
		cv.put("detail", detail);
		String[] args=new String[1];
		args[0]=word;
		i=db.update("wordtable", cv, "word=?", args);
		return i;
	}
	public void close(){
		if(db!=null)
			db.close();
	}

}
