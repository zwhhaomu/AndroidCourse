package com.example.remeberword;

import com.example.remeberword.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class WordListActivity extends Activity  {
	WordDBHelper dbhelper;
	ListView listView;
	Cursor c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordlist);
		listView=(ListView) findViewById(R.id.wordlist);
		dbhelper=new WordDBHelper(this);
		c = dbhelper.query();
		String[] from = {"_id", "word", "detail"};
		int[] to = { R.id.textid,R.id.textword, R.id.textdetail};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
				R.layout.row, c, from, to);	
		listView.setAdapter(adapter);		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView textid=(TextView)arg1.findViewById(R.id.textid);
				TextView textword=(TextView)arg1.findViewById(R.id.textword);
				TextView textdetail=(TextView)arg1.findViewById(R.id.textdetail);
				int id=Integer.parseInt(textid.getText().toString());
				String word=textword.getText().toString();
				String detail=textdetail.getText().toString();
				exitAlert(id,word,detail);
				
			}
		});
	}
	// 显示对话框
		private void exitAlert(final int id1,final String word,final String detail ){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("编辑单词")
			.setIcon(R.drawable.logo)
			.setCancelable(false)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					
//					int i=dbhelper.deleteWordById(id1);
//					
//					if(i!=0){
//						refreshword();
//						Toast.makeText(getApplicationContext(), "删除 成功 ", Toast.LENGTH_SHORT).show();
//					}
//					
//					else
//						Toast.makeText(getApplicationContext(), "删除 失败 ",  Toast.LENGTH_SHORT).show();
//					
					Intent intent=new Intent(WordListActivity.this,WordManageActivity.class);
					Bundle b=new Bundle();
					b.putInt("id", id1);
					b.putString("word", word);
					b.putString("detail", detail);
					intent.putExtra("data",b);
					startActivity(intent);
					return;
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		

		

}
