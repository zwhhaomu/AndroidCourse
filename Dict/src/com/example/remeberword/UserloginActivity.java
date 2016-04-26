package com.example.remeberword;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UserloginActivity extends Activity {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	int count=0;
	private TextView username,loginCount;
	private TextView loginTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userloginfo);
		username=(TextView) findViewById(R.id.username);
		loginCount=(TextView) findViewById(R.id.loingcount);
		loginTime=(TextView) findViewById(R.id.logintime);
		preferences=getSharedPreferences("login",MODE_PRIVATE);
		editor=preferences.edit();
		readLoinginfor();		
	}


	void readLoinginfor(){

		String usernametext=preferences.getString("username", null);
		username.setText("用户名：\t"+usernametext);
		count=preferences.getInt("count",0);
		String time=preferences.getString("time", null);
		loginCount.setText("登录次数：\t"+String.valueOf(count));
		loginTime.setText("最近登录时间：\t"+String.valueOf(time));
/*		List list=new ArrayList();
		list.add(time);
		ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
		loginTime.setAdapter(adapter);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_userlogin, menu);
		return true;
	}

}
