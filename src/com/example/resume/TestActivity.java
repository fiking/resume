package com.example.resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.resume.model.Education;
import com.example.resume.utils.Global;

public class TestActivity extends Activity  {
	private ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        setContentView(listView);
	}



	 private List<String> getData(){
         
	        List<String> data = new ArrayList<String>();
	        data.add("��������1");
	        data.add("��������2");
	        data.add("��������3");
	        data.add("��������4");
	        data.add("��������5");
	        data.add("��������6");
	        data.add("��������7");
	        data.add("��������8");
	        data.add("��������9");
	        data.add("��������10");
	        data.add("��������11");
	        data.add("��������12");
	        data.add("��������13");
	        data.add("��������14");
	        data.add("��������15");
	        data.add("��������16");
	        data.add("��������17");
	        data.add("��������18");
	        data.add("��������19");
	        data.add("��������20");
	         
	        return data;
	    }
	

	

}
