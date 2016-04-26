package com.example.remeberword;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.util.HttpUtil;
import com.example.util.StreamTool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class WordShowActivity extends Activity {
	WordDBHelper dbHelper;
	private EditText word,detail;
	private Button btnprior,btnnext,btnedit,updatewordbtn;
	Cursor c ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordshow);
		dbHelper=new WordDBHelper(this);
		word=(EditText) findViewById(R.id.word);
		detail=(EditText) findViewById(R.id.detail);
		btnprior=(Button) findViewById(R.id.btnprior);
		btnnext=(Button) findViewById(R.id.btnnext);
		btnedit=(Button) findViewById(R.id.editbtn);
		updatewordbtn=(Button) findViewById(R.id.updatewordbtn);
		updatewordbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbHelper.deleteWord();
				List<Word> words;
				try {
					words =new WordService().getLastJsonUser();
					for(Word word:words){
						dbHelper.insertDB(word.getWord(), word.getDetail());
					}
					c = dbHelper.query();
					if(c.getCount()>0){
						c.moveToFirst();
						word.setText(c.getString(1));
						detail.setText(c.getString(2));
					}
					else
						Toast.makeText(getApplicationContext(), "没有单词！", 3000).show();

					Intent intent=new Intent(WordShowActivity.this,WordListActivity.class);
					startActivity(intent);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		c = dbHelper.query();
		if(c.getCount()>0){
			c.moveToFirst();
			word.setText(c.getString(1));
			detail.setText(c.getString(2));
		}
		else
			Toast.makeText(getApplicationContext(), "没有单词！", 3000).show();

		btnprior.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(c.moveToPrevious()){
					word.setText(c.getString(1));
					detail.setText(c.getString(2));}
				else
				{
					Toast.makeText(getApplicationContext(), "已经是第一条！", 3000).show();
				}
			}
		});
		btnnext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(c.moveToNext()){
					word.setText(c.getString(1));
					detail.setText(c.getString(2));}
				else
				{
					Toast.makeText(getApplicationContext(), "已经是最后一条！", 3000).show();
				}
			}

		});
		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(WordShowActivity.this,WordManageActivity.class);
				Bundle b=new Bundle();
				b.putString("word", word.getText().toString());
				b.putString("detail", detail.getText().toString());
				intent.putExtra("data",b);
				startActivity(intent);

			}
		});
		dbHelper.close();
	}
	
}
