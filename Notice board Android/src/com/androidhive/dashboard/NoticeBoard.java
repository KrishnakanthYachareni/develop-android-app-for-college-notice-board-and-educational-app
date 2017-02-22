package com.androidhive.dashboard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidhive.dashboard.R;
import android.widget.RelativeLayout.LayoutParams;
 
public class NoticeBoard extends Activity {
	
	private SharedPreferences prefs;
	private String prefName = "report";
	
	InputStream is=null;
	String result=null;
	String line=null;
	
	RelativeLayout rl, relative, rl1[];
	LinearLayout linear1;
	ScrollView scroll1;
	
	TextView Text_Owner;
	TextView Text_User[], Text_Date[], Text_Msg[];
	View View_line[];
	CheckBox CB_Admin, CB_HOD, CB_Staff, CB_Student;
	EditText Edit_Msg;
	
	List<String> List_Receiver_Id;
	List<String> List_Out_Se_cnbid, List_Out_Se_uid, List_Out_Date, 
	List_Out_Se_Name, List_Out_Msg, List_Out_MsgId, List_Out_Tag;
	String Se_Name, Se_CNB, Str_Tag, Str_DB_Delete;
	
	String Str_User_Name, Str_User_Id, Str_Msg;
	String Str_ListName, Str_ListTag, Str_ListDt, Str_ListId;
	int Int_cnbid, Int_MsgCount;
	
	String IP_Address;
	
	AlertDialog.Builder alert, alert_update;
	String Str_Pre_Msg;
	int Int_Pre_MsgId;
	List<Integer> List_Pre_TagList;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.noticeboard);
		
	initialise_variables();		
		
	// Get values from DB..
		
	DB_Get_Out_Count();
		
	DB_UserData();
		
	rl1 = new RelativeLayout[Int_MsgCount];
	Text_User = new TextView[Int_MsgCount];
	Text_Date = new TextView[Int_MsgCount];
	Text_Msg = new TextView[Int_MsgCount];
	View_line = new View[Int_MsgCount];
				
	for(int i=0 ; i<Int_MsgCount; i++) {
			
		rl1[i] = new RelativeLayout(NoticeBoard.this);
			
		DB_Get_TagList(List_Out_MsgId.get(i).toString());
		
		DB_Get_SenderData(List_Out_Se_cnbid.get(i).toString(), 
			List_Out_Se_uid.get(i).toString());
		
		Str_ListId = "(" + Se_CNB + " : " + 
			List_Out_Se_uid.get(i).toString() + ")" ;
			
		Str_ListDt = List_Out_Date.get(i).toString();
			
		for(int x=0; x<List_Out_Tag.size(); x++) {
				
			String dummy = List_Out_Tag.get(x);
			Str_Tag = Str_Tag.concat(dummy).concat("  ");
		}
							
		Str_ListName = "<font color = 'red'>" + Se_Name + "</font>" + " : " + 
			List_Out_Msg.get(i).toString() + "<br/><br/>" + 
			"<font color = 'blue'>" + 
						
			Str_Tag +
						
			"</font>" + "<br/><br/>";
		
		Str_Tag = "";
		List_Out_Tag.clear();
			
		rl1[i].setPadding(10, 10, 10, 0);
			
		Text_User[i] = new TextView(NoticeBoard.this);
		Text_User[i].setText(Str_ListId);
		Text_User[i].setTextSize(15);
		RelativeLayout.LayoutParams param_TextUser = new RelativeLayout.LayoutParams
			((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		param_TextUser.topMargin = 20;
		Text_User[i].setLayoutParams(param_TextUser);
		rl1[i].addView(Text_User[i]);
 
			
		Text_Date[i] = new TextView(NoticeBoard.this);
		Text_Date[i].setText(Str_ListDt);
		Text_Date[i].setTextSize(15);
		RelativeLayout.LayoutParams param_Text_Date = new RelativeLayout.LayoutParams
			((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		param_Text_Date.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		param_Text_Date.topMargin = 20;
		Text_Date[i].setLayoutParams(param_Text_Date);
		rl1[i].addView(Text_Date[i]);
			
			
		Text_Msg[i] = new TextView(NoticeBoard.this);
		Text_Msg[i].setText(Html.fromHtml(Str_ListName));
		Text_Msg[i].setTextSize(14);
		RelativeLayout.LayoutParams param_Text_UserId = new RelativeLayout.LayoutParams
			((int) LayoutParams.MATCH_PARENT, (int) LayoutParams.WRAP_CONTENT);
		param_Text_UserId.topMargin = 60;
		Text_Msg[i].setLayoutParams(param_Text_UserId);
		rl1[i].addView(Text_Msg[i]);
			
		View_line[i] = new View(NoticeBoard.this);
		RelativeLayout.LayoutParams params_line = new RelativeLayout.LayoutParams
			((int) LayoutParams.MATCH_PARENT, 8);
		params_line.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		View_line[i].setLayoutParams(params_line);
		View_line[i].setBackgroundColor(Color.MAGENTA);
		rl1[i].addView(View_line[i]);
						
		linear1.addView(rl1[i]);
	}
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.noticeboard, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
		
	switch(item.getItemId()) {
		
	    case R.id.home :
		finish();
		Intent j = new Intent(NoticeBoard.this, CNB_Login.class);
		startActivity(j);
		return true;
			
	    case R.id.logout :
		finish();
		Intent i = new Intent(NoticeBoard.this, USER_Login.class);
		startActivity(i);
		return true;
				
	    case R.id.update :
		Update_Msg();
		return true;
								
	    case R.id.newmsg : 
		AlertBox_NewMsg();
		return true;
			
	    case R.id.refresh :
		finish();
		Intent intent = new Intent(NoticeBoard.this, NoticeBoard.class);
		startActivity(intent);
	}
		
	return false;
    }
	
    public void Update_Msg() {
		
	// Update Here
		
	alert = new AlertDialog.Builder(NoticeBoard.this);
	alert.setTitle("Update Post");
	relative = new RelativeLayout(NoticeBoard.this);
	relative.setPadding(0, 0, 0, 45);
		
	Edit_Msg = new EditText(NoticeBoard.this);
	Edit_Msg.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
	RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param2.addRule(RelativeLayout.CENTER_HORIZONTAL);
	param2.topMargin = 40;
	Edit_Msg.setLayoutParams(param2);
		
	CB_Admin = new CheckBox(NoticeBoard.this);
	CB_Admin.setText("Admin");
	CB_Admin.setTextColor(Color.RED);
	RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param4.leftMargin = 20;
	param4.topMargin = 140;
	CB_Admin.setLayoutParams(param4);
		
	CB_HOD = new CheckBox(NoticeBoard.this);
	CB_HOD.setText("HOD");
	CB_HOD.setTextColor(Color.RED);
	RelativeLayout.LayoutParams param5 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param5.leftMargin = 200;
	param5.topMargin = 140;
	CB_HOD.setLayoutParams(param5);
		
	CB_Staff = new CheckBox(NoticeBoard.this);
	CB_Staff.setText("Staff");
	CB_Staff.setTextColor(Color.GREEN);
	RelativeLayout.LayoutParams param6 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param6.leftMargin = 20;
	param6.topMargin = 220;
	CB_Staff.setLayoutParams(param6);
		
	CB_Student = new CheckBox(NoticeBoard.this);
	CB_Student.setText("Student");
	CB_Student.setTextColor(Color.GREEN);
	RelativeLayout.LayoutParams param7 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param7.leftMargin = 200;
	param7.topMargin = 220;
	CB_Student.setLayoutParams(param7);
		
	Button But_NewMsg = new Button(NoticeBoard.this);
	But_NewMsg.setText("Update");
	RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams 
		(150, LayoutParams.WRAP_CONTENT);
	param3.addRule(RelativeLayout.CENTER_HORIZONTAL);
	param3.topMargin = 330;
	But_NewMsg.setLayoutParams(param3);
		
	DB_Show_Previous_Msg();
		
	Edit_Msg.setText(Str_Pre_Msg);
	for(int i=0; i<List_Pre_TagList.size(); i++) {
			
		switch(List_Pre_TagList.get(i)) {
			
			case 1 :
				CB_Admin.setChecked(true);
				break;
			case 2 :
				CB_HOD.setChecked(true);
				break;
			case 3 :
				CB_Staff.setChecked(true);
				break;
			case 4 :
				CB_Student.setChecked(true);
				break;
		}
	}
		
	But_NewMsg.setOnClickListener(new View.OnClickListener() {
		
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
				
		Str_Msg = Edit_Msg.getText().toString();
			
		if(Str_Msg.equalsIgnoreCase("")) {
				
			AlertDialog.Builder alert_error = 
				new AlertDialog.Builder(NoticeBoard.this);
			alert_error.setMessage("Enter Message..");
			alert_error.setTitle("Error");
			alert_error.setPositiveButton("OK", null);
			alert_error.show();
		}
		else {
			List_Receiver_Id.clear();
				
			if(CB_Admin.isChecked()) {
				List_Receiver_Id.add("1");
			}
			if(CB_HOD.isChecked()) {
				List_Receiver_Id.add("2");
			}
			if(CB_Staff.isChecked()) {
				List_Receiver_Id.add("3");
			}
			if(CB_Student.isChecked()) {
				List_Receiver_Id.add("4");
			}
				
			if(List_Receiver_Id.size() == 0) {
					
				AlertDialog.Builder alert_error = 
					new AlertDialog.Builder(NoticeBoard.this);
				alert_error.setMessage("Tag Anyone..");
				alert_error.setTitle("Error");
				alert_error.setPositiveButton("OK", null);
				alert_error.show();
			}
				
			else {
					
				DB_Update_Msg();
			}		
		}
            }
	});
		
	relative.addView(Edit_Msg);
	relative.addView(CB_Admin);
	relative.addView(CB_HOD);
	relative.addView(CB_Staff);
	relative.addView(CB_Student);
	relative.addView(But_NewMsg);
	alert.setView(relative);
	alert.setPositiveButton("Cancel", null);
	alert.show();
    }
	
 
    public void DB_Show_Previous_Msg() {
	// TODO Auto-generated method stub
		
	ArrayList<NameValuePair> Name = new ArrayList<NameValuePair>();
		
	Name.add(new BasicNameValuePair("se_cnbid", String.valueOf(Int_cnbid)));
	Name.add(new BasicNameValuePair("se_uid", Str_User_Id));
		
	try {
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP_Address + "DB_Show_Previous_Msg.php");
		post.setEntity(new UrlEncodedFormEntity(Name));
		HttpResponse response = http.execute(post);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}
		
	try {
		BufferedReader buffer = new BufferedReader
				(new InputStreamReader(is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		while((line=buffer.readLine()) != null) {
			
			sb.append(line +"\n");
		}
		is.close();
		result = sb.toString();
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}

	try {
			
		JSONArray jarray = new JSONArray(result);
    		JSONObject jobject = null;
    		
    		Str_Pre_Msg = null;
    		Int_Pre_MsgId = 0;
    		List_Pre_TagList = new ArrayList<Integer>();
    		
    		for(int i=0; i<jarray.length(); i++) {
    			
    			jobject = jarray.getJSONObject(i);
    			
    			Int_Pre_MsgId = jobject.getInt("msg_id");
    			Str_Pre_Msg = jobject.getString("msg");
    			List_Pre_TagList.add(jobject.getInt("re_cndid"));
    		}
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}		
    }
	
    public void DB_Update_Msg() {
		
	ArrayList<NameValuePair> Name = new ArrayList<NameValuePair>();
		
	Name.add(new BasicNameValuePair("msg_id", String.valueOf(Int_Pre_MsgId)));
		
	Name.add(new BasicNameValuePair("se_cnbid", String.valueOf(Int_cnbid)));
	Name.add(new BasicNameValuePair("se_uid", Str_User_Id));
	Name.add(new BasicNameValuePair("msg", Str_Msg));
	Name.add(new BasicNameValuePair("recount", String.valueOf(List_Receiver_Id.size())));
	   	
   	for(int i=0; i<List_Receiver_Id.size(); i++) {
	   		
	    String receiverid = "recnbid" + i;
	   		
	    Name.add(new BasicNameValuePair(receiverid, List_Receiver_Id.get(i).toString()));
   	}
		
	try {
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP_Address + "DB_Update_Msg.php");
		post.setEntity(new UrlEncodedFormEntity(Name));
		HttpResponse response = http.execute(post);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}
		
	try {
		BufferedReader buffer = new BufferedReader
				(new InputStreamReader(is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		while((line=buffer.readLine()) != null) {
			
			sb.append(line +"\n");
		}
		is.close();
		result = sb.toString();
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}
		
	try {
		JSONObject jobject = new JSONObject(result);
		int code = jobject.getInt("code");
	
		AlertDialog.Builder alert_Update_Report = 
			new AlertDialog.Builder(NoticeBoard.this);
		alert_Update_Report.setTitle("Report");
		alert_Update_Report.setPositiveButton("OK", new OnClickListener() {
				
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
					
			finish();
			startActivity(new Intent(NoticeBoard.this, NoticeBoard.class));
		    }
		});
    		
    		if(code == 1) {
    			
			alert_Update_Report.setMessage("Successfully Updated !!!");
			alert_Update_Report.show();
			clear();
    		}
    		else {
    			
    			alert_Update_Report.setMessage("Try Again..");
    			alert_Update_Report.show();
    			clear();
    		}
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}		
    }

    public void AlertBox_NewMsg() {
	// TODO Auto-generated method stub
		
	alert = new AlertDialog.Builder(NoticeBoard.this);
	alert.setTitle("New Post");
	relative = new RelativeLayout(NoticeBoard.this);
	relative.setPadding(0, 0, 0, 45);
	
	Edit_Msg = new EditText(NoticeBoard.this);
	Edit_Msg.setHint("Enter your Message here !!!");
	Edit_Msg.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
	RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param2.addRule(RelativeLayout.CENTER_HORIZONTAL);
	param2.topMargin = 40;
	Edit_Msg.setLayoutParams(param2);
		
	CB_Admin = new CheckBox(NoticeBoard.this);
	CB_Admin.setText("Admin");
	CB_Admin.setTextColor(Color.RED);
	RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param4.leftMargin = 20;
	param4.topMargin = 140;
	CB_Admin.setLayoutParams(param4);
	
	CB_HOD = new CheckBox(NoticeBoard.this);
	CB_HOD.setText("HOD");
	CB_HOD.setTextColor(Color.RED);
	RelativeLayout.LayoutParams param5 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param5.leftMargin = 200;
	param5.topMargin = 140;
	CB_HOD.setLayoutParams(param5);
		
	CB_Staff = new CheckBox(NoticeBoard.this);
	CB_Staff.setText("Staff");
	CB_Staff.setTextColor(Color.GREEN);
	RelativeLayout.LayoutParams param6 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param6.leftMargin = 20;
	param6.topMargin = 220;
	CB_Staff.setLayoutParams(param6);
	
	CB_Student = new CheckBox(NoticeBoard.this);
	CB_Student.setText("Student");
	CB_Student.setTextColor(Color.GREEN);
	RelativeLayout.LayoutParams param7 = new RelativeLayout.LayoutParams 
		((int) LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	param7.leftMargin = 200;
	param7.topMargin = 220;
	CB_Student.setLayoutParams(param7);
		
	Button But_NewMsg = new Button(NoticeBoard.this);
	But_NewMsg.setText("Post");
	RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams 
		(150, LayoutParams.WRAP_CONTENT);
	param3.addRule(RelativeLayout.CENTER_HORIZONTAL);
	param3.topMargin = 330;
	But_NewMsg.setLayoutParams(param3);
		
	But_NewMsg.setOnClickListener(new View.OnClickListener() {
		
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
				
		Str_Msg = Edit_Msg.getText().toString();
			
		if(Str_Msg.equalsIgnoreCase("")) {
				
			AlertDialog.Builder alert_error = 
				new AlertDialog.Builder(NoticeBoard.this);
			alert_error.setMessage("Enter Message..");
			alert_error.setTitle("Error");
			alert_error.setPositiveButton("OK", null);
			alert_error.show();
		}
		else {
				
			List_Receiver_Id.clear();
			
			if(CB_Admin.isChecked()) {
				List_Receiver_Id.add("1");
			}
			if(CB_HOD.isChecked()) {
				List_Receiver_Id.add("2");
			}
			if(CB_Staff.isChecked()) {
				List_Receiver_Id.add("3");
			}
			if(CB_Student.isChecked()) {
				List_Receiver_Id.add("4");
			}
			
			createNewMessage();
		}
	    }
	});
		
	alert.setNegativeButton("Cancel", null);
	
	relative.addView(Edit_Msg);
	relative.addView(CB_Admin);
	relative.addView(CB_HOD);
	relative.addView(CB_Staff);
	relative.addView(CB_Student);
	relative.addView(But_NewMsg);
	alert.setView(relative);
	alert.show();
    }
 
    public void initialise_variables() {
	// TODO Auto-generated method stub
		
	rl = (RelativeLayout) findViewById(R.id.rl);
	Text_Owner = (TextView) findViewById(R.id.textView1);
	
	prefs = getSharedPreferences(prefName, MODE_PRIVATE);
	Str_User_Name = prefs.getString("uname", "");
		
    	IP_Address = prefs.getString("ip", "http://10.0.2.2/cnb/");
 
	Int_cnbid = prefs.getInt("cnb_id", 0);
	Str_User_Id = prefs.getString("uid", null);
	
	List_Receiver_Id = new ArrayList<String>();
	
	Text_Owner.setText(Str_User_Name);
	
	scroll1 = (ScrollView)findViewById(R.id.scrollView1);
	linear1 = (LinearLayout)findViewById(R.id.linear1);
 
	List_Out_Se_cnbid = new ArrayList<String>();
	List_Out_Se_uid = new ArrayList<String>();
	List_Out_Date = new ArrayList<String>();
	List_Out_Se_Name = new ArrayList<String>();
	List_Out_Msg = new ArrayList<String>();
	List_Out_Tag = new ArrayList<String>();
	List_Out_MsgId = new ArrayList<String>();
	
	Str_Tag = "";
    }
	
    public void DB_Get_Out_Count() {
		
	ArrayList<NameValuePair> Name = new ArrayList<NameValuePair>();
		
	Name.add(new BasicNameValuePair("re_cnbid", String.valueOf(Int_cnbid)));
		
	try {
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP_Address + "DB_Get_Out_Count.php");
		post.setEntity(new UrlEncodedFormEntity(Name));
		HttpResponse response = http.execute(post);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}
		
	try {
		BufferedReader buffer = new BufferedReader
				(new InputStreamReader(is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		while((line=buffer.readLine()) != null) {
			
			sb.append(line +"\n");
		}
		is.close();
		result = sb.toString();
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}
		
	try {
		JSONObject jobj = new JSONObject(result);
	
		Int_MsgCount = jobj.getInt("count");
	}
	catch(Exception e) {
		Log.e("DB_Get_Out_Count", e.toString());
	}		
    }

    public void DB_UserData() {
		
	ArrayList<NameValuePair> Name = new ArrayList<NameValuePair>();
	
	Name.add(new BasicNameValuePair("re_cnbid", String.valueOf(Int_cnbid)));
		
	try {
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP_Address + "DB_UserData.php");
		post.setEntity(new UrlEncodedFormEntity(Name));
		HttpResponse response = http.execute(post);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
	}
	catch(Exception e) {
		Log.e("DB_UserData", e.toString());
	}
		
	try {
		BufferedReader buffer = new BufferedReader
				(new InputStreamReader(is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		while((line=buffer.readLine()) != null) {
			
			sb.append(line +"\n");
		}
		is.close();
		result = sb.toString();
	}
	catch(Exception e) {
		Log.e("DB_UserData", e.toString());
	}
		
	try {
		JSONArray jarray = new JSONArray(result);
		JSONObject jobj = null;
	
		for(int i=0; i<jarray.length(); i++) {
		
		    jobj = jarray.getJSONObject(i);
		
		    List_Out_Se_cnbid.add(String.valueOf(jobj.getInt("se_cnbid")));
		    List_Out_Se_uid.add(jobj.getString("se_uid"));
		    List_Out_MsgId.add(jobj.getString("msg_id"));
		    List_Out_Msg.add(jobj.getString("msg"));
		    List_Out_Date.add(jobj.getString("dt"));
		}
	}
	catch(Exception e) {
		Log.e("DB_UserData", e.toString());
	}
    }
	
    public void DB_Get_SenderData(String string1, String string2) {
	// TODO Auto-generated method stub
		
	ArrayList<NameValuePair> Name = new ArrayList<NameValuePair>();
	
	Name.add(new BasicNameValuePair("se_cnbid", string1));
	Name.add(new BasicNameValuePair("se_uid", string2));
	
	try {
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP_Address + "DB_Get_SenderData.php");
		post.setEntity(new UrlEncodedFormEntity(Name));
		HttpResponse response = http.execute(post);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
	}
	catch(Exception e) {
		Log.e("DB_Get_SenderData", e.toString());
	}
		
	try {
		BufferedReader buffer = new BufferedReader
				(new InputStreamReader(is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		while((line=buffer.readLine()) != null) {
			
			sb.append(line +"\n");
		}
		is.close();
		result = sb.toString();
	}
	catch(Exception e) {
		Log.e("DB_Get_SenderData", e.toString());
	}
	
	try {
		JSONObject jobj = new JSONObject(result);
	    	
		Se_CNB = jobj.getString("se_cnb");
		Se_Name = jobj.getString("se_name");
	}
	catch(Exception e) {
		Log.e("DB_Get_SenderData", e.toString());
	}		
    }
	
    public void createNewMessage() {
		
	if(List_Receiver_Id.size() == 0) {
		
		AlertDialog.Builder alert_error = 
			new AlertDialog.Builder(NoticeBoard.this);
		alert_error.setMessage("Tag Anyone..");
		alert_error.setTitle("Error");
		alert_error.setPositiveButton("OK", null);
		alert_error.show();
	}
		
	else {
		
		InsertNewEntry();
	}		
    }
	
    public void InsertNewEntry() {
		
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	 
   	nameValuePairs.add(new BasicNameValuePair("se_cnbid", String.valueOf(Int_cnbid)));
   	nameValuePairs.add(new BasicNameValuePair("se_uid", Str_User_Id));
   	nameValuePairs.add(new BasicNameValuePair("msg", Str_Msg));
   	nameValuePairs.add(new BasicNameValuePair
		("recount", String.valueOf(List_Receiver_Id.size())));
   	
   	for(int i=0; i<List_Receiver_Id.size(); i++) {
   		
   		String receiverid = "recnbid" + i;
   		
   		nameValuePairs.add(new BasicNameValuePair
			(receiverid, List_Receiver_Id.get(i).toString()));
   	}
	    	
   	try
   	{
   		HttpClient httpclient = new DefaultHttpClient();
   		HttpPost httppost = new HttpPost(IP_Address + "InsertNewEntry.php");
   		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   		HttpResponse response = httpclient.execute(httppost); 
   		HttpEntity entity = response.getEntity();
   		is = entity.getContent();		      
   	}
   	catch(Exception e)
   	{
   		Log.e("InsertNewEntry", e.toString());
   	}     
	        
   	try
   	{
   		BufferedReader reader = new BufferedReader
   				(new InputStreamReader(is,"iso-8859-1"),8);
   		StringBuilder sb = new StringBuilder();
   		while ((line = reader.readLine()) != null)
   		{
   			sb.append(line + "\n");
   		}
   		is.close();
   		result = sb.toString();	   		
   	}
   	catch(Exception e)
   	{
   		Log.e("InsertNewEntry", e.toString());
   	}     
	        
   	try
   	{
   		JSONObject json_data = new JSONObject(result);
   		int code=json_data.getInt("code");
	   		
   		AlertDialog.Builder alert_report = 
			new AlertDialog.Builder(NoticeBoard.this);
   		alert_report.setTitle("Report");
   		alert_report.setPositiveButton("OK", new OnClickListener() {
				
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        // TODO Auto-generated method stub
					
		        finish();
		        startActivity(new Intent(NoticeBoard.this, NoticeBoard.class));
		    }
		});
	   		
   		if(code==1)
   		{
   			alert_report.setMessage("Successfully Inserted !!!");
   			alert_report.show();
   			clear();
   		}
   		else
   		{
   			alert_report.setMessage("Try Again..");
   			alert_report.show();
   			clear();
   		}
   	}
   	catch(Exception e)
   	{
   		Log.e("InsertNewEntry", e.toString());
   	}
    }
	
    public void clear() {
	// TODO Auto-generated method stub
		
	Edit_Msg.setText(null);
	CB_Admin.setChecked(false);
	CB_HOD.setChecked(false);
	CB_Staff.setChecked(false);
	CB_Student.setChecked(false);
    }
 

    public void DB_Get_TagList(String str) {
	// TODO Auto-generated method stub
		
	ArrayList<NameValuePair> Name = new ArrayList<NameValuePair>();
	
	Name.add(new BasicNameValuePair("msg_id", str));
		
	try {
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP_Address + "DB_Get_TagList.php");
		post.setEntity(new UrlEncodedFormEntity(Name));
		HttpResponse response = http.execute(post);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
	}
	catch(Exception e) {
		Log.e("DB_Get_TagList", e.toString());
	}
		
	try {
		BufferedReader buffer = new BufferedReader
				(new InputStreamReader(is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		while((line=buffer.readLine()) != null) {
			
			sb.append(line +"\n");
		}
		is.close();
		result = sb.toString();
	}
	catch(Exception e) {
		Log.e("DB_Get_TagList", e.toString());
	}
		
	try {
		JSONArray jarray = new JSONArray(result);
		JSONObject jobj = null;
	
		List<String> dummy = new ArrayList<String>();
	
		for(int i=0; i<jarray.length(); i++) {
		
			jobj = jarray.getJSONObject(i);
			dummy.add(jobj.getString("re_cndid"));
		}
    		
    		for(int i=0; i<dummy.size(); i++) {
    			
    			 int x = Integer.parseInt(dummy.get(i).toString());
    			
    			switch(x) {
    			
    			case 1 : 
    				List_Out_Tag.add("Admin");
    				break;
    			case 2 : 
    				List_Out_Tag.add("HOD");
    				break;
    			case 3 : 
    				List_Out_Tag.add("Staff");
    				break;
    			case 4 :
    				List_Out_Tag.add("Student");
    				break;
			default :
				List_Out_Tag.add("Others");
				break;
    			}
    		}
    		
    		dummy.clear();
	}
	catch(Exception e) {
		Log.e("DB_Get_TagList", e.toString());
	}
    }
 
    @Override
    public void onBackPressed() {
	// TODO Auto-generated method stub
		
	finish();
		
	Intent intent = new Intent(NoticeBoard.this, USER_Login.class);
	startActivity(intent);
    }
}
