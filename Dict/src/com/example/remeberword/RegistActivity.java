package com.example.remeberword;


import java.util.ArrayList;
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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RegistActivity extends Activity {

	WordDBHelper dbhelper;
	//声明按钮
	private Button register,cancel;
	//声明ToggleButton
	private ToggleButton marriged;
	//声明单选按钮
	private RadioButton male,female;
	//声明文本编辑框
	private EditText username,password,realname;
	//声明下拉列表
	private Spinner position;
	//声明多选按钮
	private CheckBox reading,swimming,running;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbhelper=new WordDBHelper(this);
		setContentView(R.layout.activity_regist);
		username=(EditText)findViewById(R.id.username);
		password=(EditText)findViewById(R.id.password);
		realname=(EditText) findViewById(R.id.realname);
		male=(RadioButton)findViewById(R.id.male);
		female=(RadioButton)findViewById(R.id.female);
		reading=(CheckBox)findViewById(R.id.reading);
		swimming=(CheckBox)findViewById(R.id.swimming);
		running=(CheckBox)findViewById(R.id.running);
		marriged=(ToggleButton)findViewById(R.id.marriged);
		position=(Spinner)findViewById(R.id.position);
		String[] str={"CEO","CFO","PM"};
		ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,str);
		position.setAdapter(aa);
		register=(Button)findViewById(R.id.register);
		cancel=(Button)findViewById(R.id.cancle);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b=new Bundle();
				b.putString("username", "用户名称:\t"+username.getText().toString());
				b.putString("password", "用户密码:\t"+password.getText().toString());
				b.putString("realname", "真实姓名:\t"+realname.getText().toString());
				String gender;
				if(male.isChecked()){
					gender="男";
				}else{
					gender="女 ";	
				}
				b.putString("gender", "性别:\t"+gender);
				String temp="爱好:\t";
				if(reading.isChecked()){
					temp+="阅读、";
				}
				if(swimming.isChecked()){
					temp+="游泳、";
				}
				if(running.isChecked()){
					temp+="跑步";
				}
				b.putString("hobby",temp);
				if(marriged.isChecked()){
					b.putSerializable("marriged", "婚否:\t已婚");
				}
				else{
					b.putSerializable("marriged", "婚否:\t未婚");
				}
				b.putString("position", "职位:\t"+position.getSelectedItem().toString());
				registerUser(username.getText().toString(),password.getText().toString(), realname.getText().toString(),gender, temp);

/*				//int i=dbhelper.insertUser(username.getText().toString(), password.getText().toString(), male.isChecked()?"男":"女", marriged.isChecked(), temp, position.getSelectedItem().toString());
				if(i!=0)
					Toast.makeText(RegistActivity.this, "用户信息注册成功 ", 8000).show();
				else
					Toast.makeText(RegistActivity.this, "用户信息注册失败 ", 8000).show();*/
				Intent intent=new Intent(RegistActivity.this, ResultActivity.class);
				intent.putExtra("data", b);
				
				startActivity(intent);

			}
		});

	}
	private void registerUser(String username,String password,String name,String gender,String favorite){
		// 2. 使用 Apache HTTP 客户端实现
		String urlStr =HttpUtil.BASE_URL+"InsertUserServlet";
		HttpPost request = new HttpPost(urlStr);
		// 如果传递参数个数比较多的话，我们可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("account", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("gender", gender));
		params.add(new BasicNameValuePair("favorite",favorite));
		try {
			//request.addHeader("Content-Type", "text/html");    //这行很重要 
			request.setEntity( new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = new DefaultHttpClient().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				String msg = EntityUtils.toString(response.getEntity());
				showDialog(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		.setCancelable(false)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_regist, menu);
		return true;
	}

}
