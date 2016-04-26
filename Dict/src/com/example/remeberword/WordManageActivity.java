package com.example.remeberword;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class WordManageActivity extends Activity implements OnClickListener {

	private Button btninsert,btnupdate,btndelete,btnsearch;
	private EditText editTextword,editTextdetail;
	private String word,detail;
	private int i;
	WordDBHelper dbhelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		btninsert=(Button)findViewById(R.id.btninsert);
		btnupdate=(Button)findViewById(R.id.btnupdate);
		btndelete=(Button)findViewById(R.id.btndelete);
		btnsearch=(Button)findViewById(R.id.btnsearch);
		editTextword=(EditText)findViewById(R.id.word);
		editTextdetail=(EditText)findViewById(R.id.detail);
		Intent intent=getIntent();
		Bundle b=intent.getBundleExtra("data");
		editTextword.setText(b.getString("word"));
		editTextdetail.setText(b.getString("detail"));
		dbhelper=new WordDBHelper(this);
		btninsert.setOnClickListener(this);
		btnupdate.setOnClickListener(this);
		btndelete.setOnClickListener(this);
		btnsearch.setOnClickListener(this);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressLint("ShowToast")
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btninsert:
			word=editTextword.getText().toString().trim();
			detail=editTextdetail.getText().toString().trim();
			i=dbhelper.insertDB(word,detail);
			if(i!=0)
				Toast.makeText(WordManageActivity.this, "����¼��ɹ� ", 8000).show();
			else
				Toast.makeText(WordManageActivity.this, "����¼��ʧ�� ", 8000).show();
			break;
		case R.id.btnupdate:
			word=editTextword.getText().toString().trim();
			detail=editTextdetail.getText().toString().trim();
			i=dbhelper.updateDB(word,detail);
			if(i!=0)
				Toast.makeText(WordManageActivity.this, "�����޸ĳɹ� ", 8000).show();
			else
				Toast.makeText(WordManageActivity.this, "�����޸�ʧ�� ", 8000).show();
			break;
		case R.id.btnsearch:
			Intent intent=new Intent(WordManageActivity.this,WordListActivity.class);
			startActivity(intent);
			break;
		case R.id.btndelete:
			word=editTextword.getText().toString().trim();
			i=dbhelper.deleteWordByWord(word);
			if(i!=0)
				Toast.makeText(WordManageActivity.this, "����ɾ���ɹ� ", 8000).show();
			else
				Toast.makeText(WordManageActivity.this, "����ɾ��ʧ�� ", 8000).show();
			break;

		}
	}
	//insertWord(word,detail)
	/*	�������ʵ�ֽ�Android�ĵ��ʸ��µ�Web�������˵����ݿ���*/
	//updateWord(word,detail)
	/*	�������ʵ�ֽ�Android�ĵ��ʸ��µ�Web�������˵����ݿ���*/
}
