package com.example.remeberword;




import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
	private TabHost tabHost;
	private TextView main_tab_new_message;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        main_tab_new_message=(TextView) findViewById(R.id.main_tab_new_message);
        main_tab_new_message.setVisibility(View.INVISIBLE);
        main_tab_new_message.setText("10");
        
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, LoginActivity.class);
        spec=tabHost.newTabSpec("MySpec").setIndicator("MySpec").setContent(intent);
        tabHost.addTab(spec);
        
        
        
        intent=new Intent().setClass(this, WordListActivity.class);
        spec=tabHost.newTabSpec("WordManage").setIndicator("WordManage").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, Asynctask.class);
        spec=tabHost.newTabSpec("Asynctask").setIndicator("Asynctask").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(1);
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.my_tab:
					tabHost.setCurrentTabByTag("MySpec");
					break;
				case R.id.word_tab:
					tabHost.setCurrentTabByTag("WordManage");
					break;
				case R.id.tab_Asynctask:
					tabHost.setCurrentTabByTag("Asynctask");
					break;
				default:
					//tabHost.setCurrentTabByTag("�ҵĿ���");
					break;
				}
			}
		});
    }
    
   
}