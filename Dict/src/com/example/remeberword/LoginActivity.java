package com.example.remeberword;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.util.HttpUtil;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	//WordDBHelper dbhelper;
	private Button btnok,btnregist;
	private EditText username,password;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	int count=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//	dbhelper=new WordDBHelper(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		preferences=getSharedPreferences("login",MODE_APPEND);
		editor=preferences.edit();
		btnregist=(Button) findViewById(R.id.regButton);
		btnok=(Button) findViewById(R.id.loginButton);
		username=(EditText) findViewById(R.id.userEditText);
		password=(EditText) findViewById(R.id.pwdEditText);
		btnregist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, RegistActivity.class);
				startActivity(intent);


			}


		});
		//����¼�����
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String user=username.getText().toString();
				String psw=password.getText().toString();
				String msg=login(user,psw);
				saveLoinginfor();
				if(!msg.equals("0")){
					Toast.makeText(getApplication(), "登录成功！", Toast.LENGTH_LONG).show();
					msg=user+","+psw+","+msg;
					Intent intent=new Intent(LoginActivity.this,WordShowActivity.class);
					//intent.putExtra("data",msg);
					startActivity(intent);
					} 
				else
					Toast.makeText(getApplication(), "登录失败！", Toast.LENGTH_LONG).show();
				/*Cursor c=dbhelper.queryUserByname(user, psw);
				if(c.getCount()>0){
					Toast.makeText(getApplication(), "��¼�ɹ�", Toast.LENGTH_LONG).show();
					//msg=user+","+psw+","+msg;
					saveLoinginfor();
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this,WordShowActivity.class);
					startActivity(intent);
				}
				else
					Toast.makeText(getApplication(), "��¼ʧ�ܣ��û��������벻��ȷ��", Toast.LENGTH_LONG).show();*/

			}
		});
	}
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		.setCancelable(false)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	private String login(String username,String password){
		//showDialog("��¼�ɹ�");
		String returnstr="";
		String urlStr = HttpUtil.BASE_URL+"/LoginServlet";
		HttpPost request = new HttpPost(urlStr);
		// ������ݲ��������Ƚ϶�Ļ������ǿ��ԶԴ��ݵĲ������з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		try {
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = new DefaultHttpClient().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				returnstr = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnstr;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			aboutuserinfo("�鿴�ҵĵ�¼��¼");
			break;
		case R.id.exit:
			exitAlert("���Ҫ�˳���");
			break;
		}
		return true;
	}

	// ��ʾ�Ի���
	private void exitAlert(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		.setCancelable(false)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				return;
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// ��ʾ�Ի���
	private void aboutuserinfo(String msg){
		Intent intent=new Intent();
		intent.setClass(LoginActivity.this,UserloginActivity.class);
		startActivity(intent);
	}

	void saveLoinginfor(){
		//д���û���¼��ʱ�������
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"+"hh:mm:ss");
		editor.putString("time",sdf.format(new Date()));
		editor.putString("username", username.getText().toString());
		count=preferences.getInt("count", 0);
		editor.putInt("count", ++count);
		editor.commit();

	}

}



